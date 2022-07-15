/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ContrattoService } from '../../services/contratto.service';
import { ContrattoVO } from '../../vo/contratto-vo';
import { EnteService } from '../../services/ente.service';
import { SoggettoService } from '../../services/soggetto.service';
import { ContrAmbTipSerVO } from '../../vo/contr-amb-tip-serv-vo';
import { ErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { Subject } from 'rxjs/Rx';
import { takeUntil } from 'rxjs/operators';
import { Moment } from 'moment';
import * as moment from 'moment';
import { SoggettoCoinvoltoVO } from 'src/app/vo/soggetto-coinvolto-vo';
import { UserService } from '../../services/user.service';
import { UserInfo } from 'src/app/vo/funzionario-vo';

@Component({
  selector: 'app-dettaglio-contratto',
  templateUrl: './dettaglio-contratto.component.html',
  styleUrls: ['./dettaglio-contratto.component.scss']
})
export class DettaglioContrattoComponent implements OnInit, OnDestroy {

  loadComplete: boolean;
  id: number;
  dettaglioContratto: ContrattoVO;
  descBacino: string;
  descTipoAffidamento: string;
  descModalitaAffidamento: string;
  descTipologiaServizio: string;
  descAmbitoServizio: string;
  denomEnteCommittente: string;
  denomSoggTitolare: string;
  descTipoSoggEsecTitolare: string;
  descTipoRaggruppamento: string;
  denomAziendaMandataria: string;
  denomCompRaggrs: string;
  denomCompRaggrArray: Array<string>;
  contrattoAmbtipoServizioArrayVO: Array<ContrAmbTipSerVO>;
  contrattoAmbtipoServizioArray: Array<string>;
  tipoDocumentoArray: Array<string>;
  azione: string;
  dataFiltro: Moment;
  dataFiltroLocal: Moment;
  dataFiltroMin: Moment;
  soggettiCoinvoltiOrigin: Array<SoggettoCoinvoltoVO>;
  private unsubscribe = new Subject<void>();
  funzionario: UserInfo;
  isUtenteRegioneoServizi: boolean;
  isAmp: boolean;

  constructor(private route: ActivatedRoute,
    private contrattoService: ContrattoService,
    private enteService: EnteService,
    private soggettoService: SoggettoService,
    private userService: UserService,
    private router: Router,
  ) { }

  ngOnInit() {
    window.scroll(0, 0);
    this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number
      this.azione = this.route.snapshot.paramMap.get('action');
      this.userService.funzionarioVo$.subscribe(data => {
        this.funzionario = data;
        if (this.funzionario.ruolo === null) {
          this.userService.funzionarioVo$.takeLast(1).subscribe(data => {
            this.funzionario = data;
            this.isUtenteRegioneoServizi = this.funzionario.ruolo.codiceRuolo.includes("REGIONE_REBUS") || this.funzionario.ruolo.codiceRuolo.includes("SERVIZIO_REBUS");
            this.isAmp = this.funzionario.ruolo.codiceRuolo.includes("AMP");
          })
        }
        else {
          this.isUtenteRegioneoServizi = this.funzionario.ruolo.codiceRuolo.includes("REGIONE_REBUS") || this.funzionario.ruolo.codiceRuolo.includes("SERVIZIO_REBUS");
          this.isAmp = this.funzionario.ruolo.codiceRuolo.includes("AMP");
        }
      });
      this.loadDettaglioContrattoAndChoices();
    });
  }

  ngOnDestroy() {
    // Emit something to stop all Observables
    this.unsubscribe.next();
    // Complete the notifying Observable to remove it
    this.unsubscribe.complete();
  }

  loadDettaglioContrattoAndChoices() {
    this.loadComplete = false;
    this.contrattoService.dettaglioContratto(this.id, Action.VIEW);
    this.contrattoService.dettaglioContratto$.pipe(
      takeUntil(this.unsubscribe)
    ).subscribe(data => {
      this.dettaglioContratto = new ContrattoVO();
      this.dettaglioContratto = data;
      this.soggettiCoinvoltiOrigin = this.dettaglioContratto.soggettiCoinvolti;
      this.dataFiltro = moment(this.dettaglioContratto.dataFiltroSoggetto);
      this.dataFiltroLocal = moment(this.dettaglioContratto.dataFiltroSoggetto);
      this.dataFiltroMin = moment(this.dettaglioContratto.dataInizioValidita);
      this.loadChoices();
    });
  }

  loadChoices() {
    this.contrattoService.getBacini().subscribe(data => {
      this.descBacino = data.find(a => a.id == this.dettaglioContratto.idBacino) != null ? data.find(a => a.id == this.dettaglioContratto.idBacino).descrizione : null;
    });
    this.contrattoService.getTipiAffidamento().subscribe(data => {
      this.descTipoAffidamento = data.find(a => a.id == this.dettaglioContratto.idTipoAffidamento) != null ? data.find(a => a.id == this.dettaglioContratto.idTipoAffidamento).descrizione : null;
    });
    this.contrattoService.getModalitaAffidamento().subscribe(data => {
      this.descModalitaAffidamento = data.find(a => a.id == this.dettaglioContratto.idModalitaAffidamento) != null ? data.find(a => a.id == this.dettaglioContratto.idModalitaAffidamento).descrizione : null;
    });
    this.contrattoService.getContrattiAmbitoTipSerVO(this.dettaglioContratto.idContratto).subscribe(data => {
      this.contrattoAmbtipoServizioArray = new Array<string>();
      this.contrattoAmbtipoServizioArrayVO = new Array<ContrAmbTipSerVO>();
      this.contrattoAmbtipoServizioArrayVO = data;
      for (let contrAmbTip of this.contrattoAmbtipoServizioArrayVO) {
        this.contrattoService.getDescrizioneAmbTipServiziobyId(contrAmbTip.idAmbTipServizio).subscribe(data => {
          if (!this.contrattoAmbtipoServizioArray.find(a => a == data)) {
            this.contrattoAmbtipoServizioArray.push(data);
          }
        })
      }
      this.loadComplete = true;
    })
    this.enteService.getEnti().subscribe(data => {
      this.denomEnteCommittente = data.find(a => a.id == this.dettaglioContratto.idSogGiuridCommittente) != null ? data.find(a => a.id == this.dettaglioContratto.idSogGiuridCommittente).denominazione : null;
    });
    this.soggettoService.getSoggettiEsecutoriTitolari(null, null).subscribe(data => {
      this.denomSoggTitolare = data.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore) != null ? data.find(a => a.id == this.dettaglioContratto.idSogGiuridEsecutore).denominazione : null;
    });
    this.soggettoService.getTipiSoggettoGiuridico(null).subscribe(data => {
      this.descTipoSoggEsecTitolare = data.find(a => a.id == this.dettaglioContratto.idTipoSogGiuridEsec) != null ? data.find(a => a.id == this.dettaglioContratto.idTipoSogGiuridEsec).descrizione : null;
    });
    this.enteService.getTipiRaggruppamento().subscribe(data => {
      this.descTipoRaggruppamento = data.find(a => a.id == this.dettaglioContratto.idTipoRaggrSogGiuridEsec) != null ? data.find(a => a.id == this.dettaglioContratto.idTipoRaggrSogGiuridEsec).descrizione : null;
    });
    let idAziendaMandataria: number;
    let idCompRaggrs: Array<number> = new Array<number>();
    if (this.dettaglioContratto.contrattoRaggruppamentoVOs != null) {
      for (let contrRaggr of this.dettaglioContratto.contrattoRaggruppamentoVOs) {
        if (contrRaggr.capofila) {
          idAziendaMandataria = contrRaggr.idSoggettoGiuridico;
        }
        else {
          idCompRaggrs.push(contrRaggr.idSoggettoGiuridico);
        }
      }
    }
    if (idAziendaMandataria != null) {
      this.contrattoService.getAziendaMandataria(this.dettaglioContratto.idContratto).subscribe(data => {
        this.denomAziendaMandataria = data.find(a => a.id == idAziendaMandataria) != null ? data.find(a => a.id == idAziendaMandataria).descrizione : null;
        this.denomCompRaggrs = "";
        this.denomCompRaggrArray = new Array<string>();
        for (let i = 0; i < idCompRaggrs.length; i++) {
          this.denomCompRaggrs += data.find(a => a.id == idCompRaggrs[i]).descrizione;
          this.denomCompRaggrArray[i] = data.find(a => a.id == idCompRaggrs[i]).descrizione;
          if (i != idCompRaggrs.length - 1)
            this.denomCompRaggrs += ", ";
        }
      });
    }
    if (this.dettaglioContratto.allegati.length > 0) {
      this.tipoDocumentoArray = new Array<string>();
      this.contrattoService.getTipiDocumento().subscribe(data => {
        for (let allegato of this.dettaglioContratto.allegati) {
          this.tipoDocumentoArray.push(data.find(a => a.id == allegato.idTipoDocumento) != null ? data.find(a => a.id == allegato.idTipoDocumento).descrizione : null);
        }
      });
    }
  }

  download(idDocumento: number) {
    this.contrattoService.getContenutoDocumentoById(this.dettaglioContratto.idContratto, idDocumento)
      .subscribe(
        res => {
          saveAs(res, this.dettaglioContratto.allegati.find(a => a.idAllegato == idDocumento).nomeFile);
        },
        err => {
          let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
          console.error(errorRest.message);
        }
      );
  }

  dettaglioSoggetto(value: number) {
    let azione: string = "contratto_" + this.dettaglioContratto.idContratto;
    this.router.navigate(['/dettaglioSoggetto/' + value, { action: azione }]);
  }

  filtroSoggettiCoinvolti() {
    this.contrattoService.filtraSoggettiCoinvolti(this.dettaglioContratto.idContratto, this.dataFiltroLocal.toDate()).subscribe(data => {
      if (data) {
        this.dettaglioContratto.soggettiCoinvolti = data;
      }
    });
  }

  pulisci() {
    this.dettaglioContratto.soggettiCoinvolti = this.soggettiCoinvoltiOrigin;
    this.dataFiltroLocal = this.dataFiltro;
  }

  goBack() {
    if (this.azione == null) {
      this.router.navigate(['/ricercaContratto/']);
    }
    else {
      let array: String[] = this.azione.split('_');
      let id = array[1];
      this.router.navigate(['/dettaglioSoggetto/' + id]);
    }
  }

}

export enum Action {
  EDIT = "E",
  VIEW = "V",
}
