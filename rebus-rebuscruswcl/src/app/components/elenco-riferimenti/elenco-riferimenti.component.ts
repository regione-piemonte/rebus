/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit, ViewChild } from '@angular/core';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { MatTableDataSource, MatSort, MatPaginator, PageEvent, MatDialog, DateAdapter } from '@angular/material';
import { UserInfo } from '../../vo/funzionario-vo';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { CancellaDialogComponent } from '../cancelladialog/cancelladialog.component';
import { saveAs } from "file-saver"
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { RiferimentiNormativiVO } from '../../vo/riferimenti-normativi-vo';
import { RiferimentiNormativiService } from '../../services/riferimenti-normativi.service';


@Component({
  selector: 'app-elenco-riferimenti',
  templateUrl: './elenco-riferimenti.component.html',
  styleUrls: ['./elenco-riferimenti.component.scss']
})

@DestroySubscribers()
export class ElencoRiferimentiComponent implements OnInit {
  //columns
  staticColumnsRiferimento: Array<String> = ['tipologia', 'descrizione', 'dataCaricamento', 'azioni'];

  isUtenteAzienda: boolean;
  funzionario: UserInfo;
  displayedColumns: Array<String>;
  ricercaRichieste: Array<RiferimentiNormativiVO> = new Array<RiferimentiNormativiVO>();

  //DATASOURCE
  dataSource: RiferimentiDatasource;
  loadedPDF : boolean;
  filtraDescrizione : string;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  defaultPageSize: number;
  pageSizeOption: Array<Number>
  pageEvent: PageEvent;


  public subscribers: any = {};
  isModificaAbilita: boolean;
  isDownloadAblita: boolean;


  constructor(
    private riferimentiNormativiService: RiferimentiNormativiService,
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
    this.subscribers.elencoRiferimenti = this.riferimentiNormativiService.elencoRiferimenti();
    this.userService.funzionarioVo$.subscribe(data => {
      this.funzionario = data;
      this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
      this.isModificaAbilita = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_RIFERIMENTI);
      this.isDownloadAblita = this.funzionario.authority.includes(AuthorizationRoles.SCARICA_RIFERIMENTI);
  
    });
    this.loadedPDF = true;

    this.riferimentiNormativiService.elencoRiferimento$.subscribe(data => {
      this.ricercaRichieste = data;
      this.displayedColumns = this.staticColumnsRiferimento;
      this.setDataSource(data);
    });
  }

  setDataSource(data: Array<RiferimentiNormativiVO>){
    this.dataSource = new RiferimentiDatasource(data);

      this.dataSource.paginator = this.paginator;
      this.dataSource.sortingDataAccessor = (item, property) => {
        switch (property) {
          case 'dataCaricamento': return new Date(item.dataCaricamento);
          default: return item[property];
        }
      };
      this.dataSource.sort = this.sort;
  }


  eliminaProcedimentopopup(idDocumento: number) {

    let dialogRef = this.dialog.open(CancellaDialogComponent, {
      height: '200px',
      width: '400px',
      data: { msg: 'Sei sicuro di voler eliminare i docuemnti selezionati?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'OK') {
        this.riferimentiNormativiService.eliminaRiferimentoNormativo(idDocumento)
      }
    });
  }

  scaricaPDF(idDocumento: number){
    this.loadedPDF = false;
    let nomeFile: string;
    nomeFile = this.ricercaRichieste.find(r => r.idDocumento == idDocumento).nomeFile;
        this.riferimentiNormativiService.getDocumentoByIdDocumento(idDocumento).subscribe(
            res => {
              if (res != null) {
                saveAs(res, nomeFile);
              }
              this.loadedPDF = true;
            },
            error => {
              CommonsHandleException.authorizationError(error, this.router);
              console.error(error.error.message);
              this.loadedPDF = true;
            }
          );
  }
  filtraElenco(s : string){
    if(s ==""){
    this.setDataSource(this.ricercaRichieste);
    this.filtraDescrizione="";
    }
    else{
      let filtra = this.ricercaRichieste.filter(x => x.descrizione.toLowerCase().includes(this.filtraDescrizione.toLowerCase().trim()));
      this.setDataSource(filtra);
    }
  }

  troncaCaratteri(str) {
    let varStr: String;
    if (str && str.toString()) {
      varStr = str.toString();
      if (varStr.length > 40) {
        let varSubStr: String = varStr.substr(0, 37);
        varStr = varSubStr.trim() + "...";
      }
    }
    return varStr.toString();
  }

  ngOnDestroy() {
  }
}

export class RiferimentiDatasource extends MatTableDataSource<RiferimentiNormativiVO> {
  constructor(data: RiferimentiNormativiVO[]) {
    super(data);

  }

}
