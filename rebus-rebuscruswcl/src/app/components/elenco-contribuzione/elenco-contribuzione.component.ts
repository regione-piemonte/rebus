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
import { CancellaDialogComponent } from '../cancelladialog/cancelladialog.component';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { ContribuzioneService } from '../../services/contribuzione.service';
import { ContribuzioneVO } from '../../vo/contribuzione/contribuzione-vo';
import { FiltroContribuzioneVO } from '../../vo/filtro-contribuzione-vo';
import { AutobusLabel } from '../../commons/labels/autobus-label';

@Component({
  selector: 'app-elenco-contribuzione',
  templateUrl: './elenco-contribuzione.component.html',
  styleUrls: ['./elenco-contribuzione.component.scss']
})

@DestroySubscribers()
export class ElencoContribuzioneComponent implements OnInit {
  //columns
  staticColumnsContribuzione: Array<String> = ['telaio', 'fonteFinanziamento', 'tipoAlimentazione', 'tipoAllestimento', 'trasmessaA', 'validataA', 'trasmessaB', 'validataB', 'flgVerificatoAzienda', 'flgVerificatoAmp', 'azioni'];

  staticColumnsEnte: Array<String> = ['azienda', 'telaio', 'fonteFinanziamento', 'tipoAlimentazione', 'tipoAllestimento', 'trasmessaA', 'validataA', 'trasmessaB', 'validataB', 'flgVerificatoAzienda', 'flgVerificatoAmp', 'azioni'];



  isUtenteAzienda: boolean;
  isInserimentoAbilitato: boolean;
  isModificaAbilitata: boolean;
  isCancellaAbilitato: boolean;
  isConsultaAbilitato: boolean;
  isScaricaPDFAbilitato: boolean;
  isScaricaZipAbilitato: boolean;
  funzionario: UserInfo;
  displayedColumns: Array<String>;
  ricercaContribzione: Array<ContribuzioneVO> = new Array<ContribuzioneVO>();
  AutobusLabel = AutobusLabel;
  //DATASOURCE
  dataSource: CotribuzioneDatasource;


  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  defaultPageSize: number;
  pageSizeOption: Array<Number>
  pageEvent: PageEvent;

  loadedPDF: boolean = true;

  isAzienda: boolean = false;
  isContribuzioneCompleta: boolean = false;


  @Output() isLoadingEvent = new EventEmitter<boolean>();

  public subscribers: any = {};

  constructor(
    private contribuzioneService: ContribuzioneService,
    private userService: UserService,
    private router: Router,
    public dialog: MatDialog,
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

      //non sono azienda
      this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
      this.isInserimentoAbilitato = this.funzionario.authority.includes(AuthorizationRoles.INSERIMENTO_CONTRIBUZIONE);
      this.isModificaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE);
      this.isConsultaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE);
      this.isCancellaAbilitato = this.funzionario.authority.includes(AuthorizationRoles.ELIMINA_CONTRIBUZIONE);
      this.isScaricaPDFAbilitato = this.funzionario.authority.includes(AuthorizationRoles.ESPORTA_RICERCA_CONTRIBUZIONE);
      this.isScaricaZipAbilitato = this.funzionario.authority.includes(AuthorizationRoles.GENERA_ZIP);
    });
  }



  aggiorna(filtro: FiltroContribuzioneVO) {
    this.isLoadingEvent.emit(true);
    if (filtro) {
      this.contribuzioneService.filtraElencoContribuzione(filtro).subscribe(data => {
        if (data) {
          this.setElencoContribuzione(data);
        }
      }, error => {
        CommonsHandleException.authorizationError(error, this.router);
      });
    } else {
      this.contribuzioneService.elencoContribuzione().subscribe(data => {
        if (data) {
          this.setElencoContribuzione(data);
        }
      }, error => {
        CommonsHandleException.authorizationError(error, this.router);
      });
    }
  }

  setElencoContribuzione(data: FiltroContribuzioneVO[]) {
    if (this.isUtenteAzienda) {
      this.displayedColumns = this.staticColumnsContribuzione;
    } else {
      this.displayedColumns = this.staticColumnsEnte;
    }
    this.dataSource = new CotribuzioneDatasource(data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.isLoadingEvent.emit(false);
  }

  pad(num: number, size: number): string {
    let s = num + "";
    while (s.length < size) s = "0" + s;
    return s;
  }

  resetElencoContribuzione() {
    this.setElencoContribuzione(new Array<FiltroContribuzioneVO>());
  }

  // rimanda alla modifica 
  modificaContribuzione(idProcedimento: number, primoTelaio: string, idVariazAutobus: number) {
    this.contribuzioneService.idProcedimento = idProcedimento;
    this.contribuzioneService.primoTelaio = primoTelaio;
    this.contribuzioneService.idVariazAutobus = idVariazAutobus;
    this.router.navigate(['/inserisciContribuzione/7']); //andrebbe modificaContribuzione
  }


  //rimanda all inserimento - non ho l'id contribuzione
  inserisciContribuzione(primoTelaio: string, idVariazAutobus: number) {
    this.contribuzioneService.primoTelaio = primoTelaio;
    this.contribuzioneService.idProcedimento = null;
    this.contribuzioneService.idVariazAutobus = idVariazAutobus;
    this.router.navigate(['/inserisciContribuzione/7']);
  }

  replacePoint(str) {
    return (str && str.toString()) ? str.toString().replace(".", ",") : "";
  }

  eliminaContribuzionepopup(idProcedimento: number) {
    let dialogRef = this.dialog.open(CancellaDialogComponent, {
      height: '200px',
      width: '400px',
      data: { msg: 'Sei sicuro di voler eliminare i procedimenti selezionati?' }
    });
  }


  dettaglioBus(value: number) {
    //RECUPERA BANDO SEL E SETTORE SEL

    this.router.navigate(['/dettaglioBus', value],
      {
        fragment: 'rendicontazione',
        queryParams: {
        },
      });
  }


  troncaCaratteri(str) {
    let varStr: String = "";
    if (str && str.toString()) {
      varStr = str.toString();
      if (varStr.length > 35) {
        let varSubStr: String = varStr.substr(0, 32);
        varStr = varSubStr.trim() + "...";
      }
    }
    return varStr.toString();
  }


  troncaCaratteriAzienda(str) {
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

export class CotribuzioneDatasource extends MatTableDataSource<FiltroContribuzioneVO> {
  constructor(data: FiltroContribuzioneVO[]) {
    super(data);

  }

}
