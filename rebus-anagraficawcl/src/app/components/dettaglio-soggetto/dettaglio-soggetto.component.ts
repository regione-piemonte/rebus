/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit } from '@angular/core';
import { SoggettoService } from '../../services/soggetto.service';
import { EnteService } from '../../services/ente.service';
import { LuoghiService } from '../../services/luoghi.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SoggettoGiuridicoVO } from '../../vo/soggetto-giuridico-vo';
import { UbicazioneVO } from '../../vo/ubicazione-vo';
import { TipoSoggettoGiuridicoVO } from '../../vo/tipo-soggetto-giuridico-vo';
import { NaturaGiuridicaVO } from '../../vo/natura-giuridica-vo';
import { TipoEnteVO } from '../../vo/tipo-ente-vo';
import { ErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { ComuneVO } from '../../vo/comune-vo';
import { MatDialog } from '@angular/material';
import { ImageDialogComponent } from '../image-dialog/image-dialog.component';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-dettaglio-soggetto',
  templateUrl: './dettaglio-soggetto.component.html',
  styleUrls: ['./dettaglio-soggetto.component.scss']
})
export class DettaglioSoggettoComponent implements OnInit {

  loadComplete: boolean;
  loadTipiSoggetto: boolean;
  loadComuni: boolean;
  id: number;
  dettaglioSoggetto: SoggettoGiuridicoVO = new SoggettoGiuridicoVO;
  ubicazioneSede: UbicazioneVO = new UbicazioneVO();
  tipoSoggettoGiuridico: TipoSoggettoGiuridicoVO;
  naturaGiuridica: NaturaGiuridicaVO;
  tipoEnte: TipoEnteVO;
  comuneSede: ComuneVO;
  provincia: string;
  indirizzoSedeCompleto: string = "";
  indirizziDepCompleti: Array<string> = new Array<string>();
  azione: string;

  constructor(private soggettoService: SoggettoService,
    private enteService: EnteService,
    private luoghiService: LuoghiService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private router: Router,
    private sanitizer: DomSanitizer) { }

  ngOnInit() {
    window.scroll(0, 0);
    this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number
      this.azione = this.route.snapshot.paramMap.get('action');

      this.loadDettaglioSoggettoAndChoices();
    });
  }

  loadDettaglioSoggettoAndChoices() {
    this.loadComplete = false;
    this.soggettoService.dettaglioSoggetto(this.id, Action.VIEW);

    this.soggettoService.dettaglioSoggetto$.subscribe(data => {
      this.dettaglioSoggetto = data;

      this.ubicazioneSede = this.dettaglioSoggetto.ubicazioneSede;
      this.loadComplete = true;
      this.loadChoices();
    });
  }

  loadChoices() {
    this.loadTipiSoggetto = false;
    this.soggettoService.getTipiSoggettoGiuridico(true).subscribe(data1 => {
      this.tipoSoggettoGiuridico = data1.find(a => a.id == this.dettaglioSoggetto.idTipoSoggettoGiuridico);
      this.dettaglioSoggetto.idRuoloTipoSoggettoGiuridico = this.tipoSoggettoGiuridico.idRuolo;
      if (this.tipoSoggettoGiuridico.idRuolo == 2) {
        this.soggettoService.getNatureGiuridiche().subscribe(data2 => {
          this.naturaGiuridica = data2.find(b => b.id == this.dettaglioSoggetto.idNaturaGiuridica);
        });
      }
      else {
        this.enteService.getTipiEnte().subscribe(data3 => {
          this.tipoEnte = data3.find(c => c.id == this.dettaglioSoggetto.idTipoEnte);
        });
      }
      this.loadTipiSoggetto = true;
    });
    this.loadComuni = false;
    this.luoghiService.getComuni().subscribe(data1 => {
      let ubicazioneSede: UbicazioneVO = this.dettaglioSoggetto.ubicazioneSede;
      if (data1) {
        if (ubicazioneSede.idComune != null) {
          this.comuneSede = data1.find(a => a.id == ubicazioneSede.idComune);
          this.luoghiService.getProvinciaByIdComune(this.comuneSede.id).subscribe(data2 => {
            this.indirizzoSedeCompleto = this.getIndirizzoCompleto(ubicazioneSede.top, ubicazioneSede.indirizzo, ubicazioneSede.civico, ubicazioneSede.cap, this.comuneSede.denominazione, data2.sigla);
          });
        }
        else {
          this.indirizzoSedeCompleto = this.getIndirizzoCompleto(ubicazioneSede.top, ubicazioneSede.indirizzo, ubicazioneSede.civico, ubicazioneSede.cap, null, null);
        }
        for (let dep of this.dettaglioSoggetto.depositi) {
          let ubicazioneDep = dep.ubicazione;
          let comuneDep: ComuneVO;
          if (ubicazioneDep.idComune != null) {
            comuneDep = data1.find(a => a.id == ubicazioneDep.idComune);
            this.indirizziDepCompleti.push(this.getIndirizzoCompleto(ubicazioneDep.top, ubicazioneDep.indirizzo, ubicazioneDep.civico, ubicazioneDep.cap, comuneDep.denominazione, ubicazioneDep.descProvincia));
          }
          else {
            this.indirizziDepCompleti.push(this.getIndirizzoCompleto(ubicazioneDep.top, ubicazioneDep.indirizzo, ubicazioneDep.civico, ubicazioneDep.cap, null, null));
          }
        }
      }
      this.loadComuni = true;
    });
  }

  getIndirizzoCompleto(top: string, indirizzo: string, civico: string, cap: string, denomComune: string, provincia: string) {
    let minus: boolean = false;
    let s: string = "";
    if (top != null && top.length > 0) {
      s += top + " ";
      minus = true;
    }
    if (indirizzo != null && indirizzo.length > 0) {
      s += indirizzo + " ";
      minus = true;
    }
    if (civico != null && civico.length > 0) {
      s += civico + " ";
      minus = true;
    }
    if (minus)
      s += "- ";
    if (cap != null && cap.length > 0)
      s += cap + " ";
    if (denomComune != null && denomComune.length > 0)
      s += denomComune + " (" + provincia + ")";
    return s;
  }

  getIndirizzi(i: number) {
    return this.indirizziDepCompleti[i];
  }

  downloadDoc() {
    this.soggettoService.getLogoByIdSoggetto(this.id).subscribe(res => {
      saveAs(res, "logo.jpg");
    }, err => {
      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      console.error(errorRest.message);
    });
  }

  hasDepositi() {
    if (this.dettaglioSoggetto.depositi.length == 0)
      return false;
    else
      return true;
  }

  hasDatiBancari() {
    if (this.dettaglioSoggetto.datiBancari.length == 0)
      return false;
    else
      return true;
  }

  hasSede() {
    if (this.indirizzoSedeCompleto == "" && (this.dettaglioSoggetto.telefonoSede == null || this.dettaglioSoggetto.telefonoSede.length == 0) && (this.dettaglioSoggetto.fax == null || this.dettaglioSoggetto.fax.length == 0) && (this.dettaglioSoggetto.email == null || this.dettaglioSoggetto.email.length == 0) && (this.dettaglioSoggetto.pec == null || this.dettaglioSoggetto.pec.length == 0) && (this.dettaglioSoggetto.indirizzoWeb == null || this.dettaglioSoggetto.indirizzoWeb.length == 0) && (this.dettaglioSoggetto.numeroVerde == null || this.dettaglioSoggetto.numeroVerde.length == 0))
      return false;
    else return true;
  }
  
  private image: any;
  viewLogo() {
    this.soggettoService.getLogoByIdSoggetto(this.id).subscribe(res => {
      let objectURL = URL.createObjectURL(res);
      this.image = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      const dialogRef = this.dialog.open(ImageDialogComponent, {
        width: '600px',
        data: { image: this.image }
      });

      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }, err => {
      let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
      console.error(errorRest.message);
    });
  }

  dettaglioContratto(value: number) {
    let azione: string = "soggetto_" + this.dettaglioSoggetto.id;
    this.router.navigate(['/dettaglioContratto/' + value, { action: azione }]);
  }

  goBack() {
    if (this.azione == null) {
      this.router.navigate(['/ricercaSoggetto/']);
    }
    else {
      let array: String[] = this.azione.split('_');
      let id = array[1];
      this.router.navigate(['/dettaglioContratto/' + id]);
    }
  }

  isLoading() {
    if (!this.loadComuni || !this.loadTipiSoggetto) return true;
    return false;
  }

  troncaCaratteri(str) {
    let varStr: String;
    if (str && str.toString()) {
      varStr = str.toString();
      if (varStr.length > 75) {
        let varSubStr: String = varStr.substr(0, 70);
        varStr = varSubStr.trim() + "...";
      }
    }
    return varStr.toString();
  }

}

export enum Action {
  EDIT = "E",
  VIEW = "V",
}
