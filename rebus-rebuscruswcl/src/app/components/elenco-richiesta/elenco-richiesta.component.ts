/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { MatTableDataSource, MatSort, MatPaginator, PageEvent, MatDialog, DateAdapter } from '@angular/material';
import { UserInfo } from '../../vo/funzionario-vo';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { ProcedimentiVO } from '../../vo/procedimenti-vo';
import { CancellaDialogComponent } from '../cancelladialog/cancelladialog.component';
import { saveAs } from "file-saver"
import { ErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { ProcedimentoService } from '../../services/procedimento.service';
import { DocumentService } from '../../services/document.service';
import { FiltroProcedimentiVO } from '../../vo/filtro-procedimenti-vo';
import { CodeRoles } from '../../class/code-roles';


@Component({
  selector: 'app-elenco-richiesta',
  templateUrl: './elenco-richiesta.component.html',
  styleUrls: ['./elenco-richiesta.component.scss']
})

@DestroySubscribers()
export class ElencoRichiestaComponent implements OnInit {
  //columns
  staticColumnsProcedimento: Array<String> = ['progressivoRichiesta', 'tipologia', 'richiedente', 'stato', 'dataStato', 'azioni'];

  isUtenteAzienda: boolean;
  isModificaAbilitata: boolean;
  isCancellaAbilitato: boolean;
  isConsultaAbilitato: boolean;
  isScaricaPDFAbilitato: boolean;
  funzionario: UserInfo;
  displayedColumns: Array<String>;
  ricercaRichieste: Array<ProcedimentiVO> = new Array<ProcedimentiVO>();

  //DATASOURCE
  dataSource: RichiestaDatasource;


  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  defaultPageSize: number;
  pageSizeOption: Array<Number>
  pageEvent: PageEvent;

  loadedPDF: boolean = true;

  isAmp: boolean = false;

  @Output() isLoadingEvent = new EventEmitter<boolean>();

  public subscribers: any = {};

  constructor(
    private procedimentoService: ProcedimentoService,
    private userService: UserService,
    private router: Router,
    public dialog: MatDialog,
    private documentService: DocumentService,
    private dateAdapter: DateAdapter<Date>) {
    dateAdapter.setLocale('it-IT');
  }

  ngOnInit() {

    this.pageSizeOption = [200, 1000, 2500, 5000];
    this.defaultPageSize = 200;

    ///SOTTOSCRIZIONE AL SERVICE RICHIAMATO NELLA INBOX
    this.dataSource = null;
    this.userService.funzionarioVo$.subscribe(data => {
      this.funzionario = data;
      if (data.ruolo === null) {
        // FIX ERRORE RUOLO
        this.userService.funzionarioVo$.takeLast(1).subscribe(data => {
          if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AZIENDA || data.ruolo.codiceRuolo === CodeRoles.RUOLO_PILOTA_AZIENDA) {
            this.isUtenteAzienda = true
          }
          if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AMP) {
            this.isAmp = true
          }
        });
      } else {
        if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AZIENDA || data.ruolo.codiceRuolo === CodeRoles.RUOLO_PILOTA_AZIENDA) {
          // I CAMPI ABILITATI SONO QUELLI PER AZIENDA
          this.isUtenteAzienda = true
        }
        if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AMP) {
          this.isAmp = true
        }
      }
      this.isModificaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_PROCEDIMENTO);
      this.isConsultaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.DETTAGLIO_PROCEDIMENTO);
      this.isCancellaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.ELIMINA_PROCEDIMENTO);
      this.isScaricaPDFAbilitato = this.funzionario.authority.includes(AuthorizationRoles.SCARICA_PDF);
    });

    if (!this.isUtenteAzienda) {
      this.staticColumnsProcedimento.splice(3, 0, 'gestoreContratto');
    }

  }

  aggiorna(filtro: FiltroProcedimentiVO) {
    this.isLoadingEvent.emit(true);
    if (filtro) {
      this.procedimentoService.filtraElencoRichiesta(filtro).subscribe(data => {
        this.setElencoRichieste(data);
      }, error => {
        CommonsHandleException.authorizationError(error, this.router);;
      });
    } else {
      this.procedimentoService.elencoRichiesta().subscribe(data => {
        this.setElencoRichieste(data);
      }, error => {
        CommonsHandleException.authorizationError(error, this.router);
      });
    }
  }

  setElencoRichieste(data: ProcedimentiVO[]) {
    this.displayedColumns = this.staticColumnsProcedimento;
    this.dataSource = new RichiestaDatasource(data);

    this.dataSource.paginator = this.paginator;
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'dataStato': return new Date(item.dataStipulaDa);
        //ordinamento custom per num progressivo in quanto quello di default sortava per ASCII 
        case 'progressivoRichiesta': {
          if (item.progressivoRichiesta) {
            let years = this.pad(Number(item.progressivoRichiesta.split('/')[0]), 6);;
            let cod = item.progressivoRichiesta.split('/')[1];
            let serial = this.pad(Number(item.progressivoRichiesta.split('/')[2]), 6);
            return years + cod + serial;
          }
          return "";
        }
        default: return item[property];
      }
    };

    this.dataSource.sort = this.sort;
    this.isLoadingEvent.emit(false);
  }

  pad(num: number, size: number): string {
    let s = num + "";
    while (s.length < size) s = "0" + s;
    return s;
  }


  dettaglioRichiesta(value: number) {
    if (this.dataSource.data.find(r => r.idProcedimento === value).idTipologia == 2) {
      this.router.navigate(['/dettaglioRichiestaSostituzione/' + value, { action: 'ricerca' }]);
    } else if (this.dataSource.data.find(r => r.idProcedimento === value).idTipologia == 5) {
      this.router.navigate(['/dettaglioRichiestaUsoInLinea/' + value, { action: 'ricerca' }]);
    } else {
      this.router.navigate(['/dettaglioRichiesta/' + value, { action: 'ricerca' }]);
    }
  }

  modificaRichiesta(value: number) {
    if (this.dataSource.data.find(r => r.idProcedimento === value).idTipologia == 2) {
      this.router.navigate(['/modificaRichiestaSostituzione/' + value, { action: 'ricerca' }]);
    } else if (this.dataSource.data.find(r => r.idProcedimento === value).idTipologia == 5) {
      this.router.navigate(['/modificaRichiestaUsoInLinea/' + value, { action: 'ricerca' }]);
    }
    else {
      this.router.navigate(['/modificaRichiesta/' + value, { action: 'ricerca' }]);
    }
  }

  replacePoint(str) {
    return (str && str.toString()) ? str.toString().replace(".", ",") : "";
  }
  eliminaProcedimentopopup(idProcedimento: number) {

    let dialogRef = this.dialog.open(CancellaDialogComponent, {
      height: '200px',
      width: '400px',
      data: { msg: 'Sei sicuro di voler eliminare i procedimenti selezionati?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'OK') {
        this.procedimentoService.eliminaProcedimento(idProcedimento);

      }
    });
  }

  scaricaPDF(id: number, idTipologia: number, n: number, progressivoRichiesta: string) {
    this.loadedPDF = false;
    const re = new RegExp(/[/]/, 'g');
    progressivoRichiesta = progressivoRichiesta.replace(re, "");
    this.procedimentoService.getTipiDocumento(idTipologia).subscribe(data => {
      if (data) {
        let tipoDoc = data.find(t => t.id == n);
        let nomeFile;
        if (tipoDoc != null) {
          nomeFile = tipoDoc.descrizione;
        } else {
          nomeFile = "Unknown"
        }
        nomeFile += " " + progressivoRichiesta;
        if (n == 7) {
          nomeFile += "-R";
        }
        else if (n == 8) {
          nomeFile += "-A";
        }
        nomeFile += ".pdf";
        this.documentService.getContenutoDocumentoByIdProcedimento(id, n)
          .subscribe(
            res => {
              if (res != null) {
                saveAs(res, nomeFile);
                //apre il pdf in una nuova tab mettendo come titolo della tab il nome del file
                
                var newWindow = window.open(URL.createObjectURL(res), "_blank");
                setTimeout(function () { newWindow.document.title = nomeFile; }, 500);
              }
              this.loadedPDF = true;

            },
            err => {
              let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
              console.error(errorRest.message);
              this.loadedPDF = true;
            }
          );
      }
    });

  }

  troncaCaratteri(str) {
    let varStr: String = "";
    if (str && str.toString()) {
      varStr = str.toString();
      if (varStr.length > 40) {
        let varSubStr: String = varStr.substr(0, 37);
        varStr = varSubStr.trim() + "...";
      }
    }
    return varStr.toString();
  }

  troncaCaratteriGestoreContratto(str) {
    let varStr: String = "";
    if (str && str.toString()) {
      varStr = str.toString();
      if (varStr.length > 20) {
        let varSubStr: String = varStr.substr(0, 17);
        varStr = varSubStr.trim() + "...";
      }
    }
    return varStr.toString();
  }

  ngOnDestroy() {
  }
}

export class RichiestaDatasource extends MatTableDataSource<ProcedimentiVO> {
  constructor(data: ProcedimentiVO[]) {
    super(data);

  }

}
