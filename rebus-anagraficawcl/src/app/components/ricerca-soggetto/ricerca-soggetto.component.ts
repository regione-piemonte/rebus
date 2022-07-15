/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { ElencoSoggettoComponent } from '../elenco-soggetto/elenco-soggetto.component';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { Router } from '@angular/router';
import { DateAdapter } from '@angular/material';
import { UserInfo } from '../../vo/funzionario-vo';
import { UserService } from '../../services/user.service';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { saveAs } from "file-saver";
import { FiltroSoggettoVO } from '../../vo/filtro-soggetto';
import { SoggettoFilterService } from '../../services/soggettoFilter.service';
import { SoggettoService } from '../../services/soggetto.service';
import { TipoSoggettoGiuridicoVO } from '../../vo/tipo-soggetto-giuridico-vo';
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { ExcelSoggettoService } from '../../services/excel-soggetto.service';

@Component({
  selector: 'app-ricerca-soggetto',
  templateUrl: './ricerca-soggetto.component.html',
  styleUrls: ['./ricerca-soggetto.component.scss']
})

@DestroySubscribers()
export class RicercaSoggettoComponent implements OnInit {
  tipiSoggGiurid: Array<TipoSoggettoGiuridicoVO>;
  tipoSoggGiuridLoaded: boolean;
  downloadInCorso: boolean;
  ricercaInCorso: boolean;
  filtro: FiltroSoggettoVO;
  isUtenteAzienda: boolean;
  funzionario: UserInfo;
  checkedIncludiCessate: boolean = false;
  checkedIncludiNonAttive: boolean = false;
  checkedIncludiAttive: boolean = true;
  feedback: string;
  @ViewChild(ElencoSoggettoComponent)
  private elencoComponent: ElencoSoggettoComponent;

  enableExcel: boolean;

  public subscribers: any = {};

  constructor(
    private router: Router,
    private soggettoService: SoggettoService,
    private userService: UserService,
    private excelSoggettoService: ExcelSoggettoService,
    private filterUtilsService: SoggettoFilterService,
    private cdRef: ChangeDetectorRef,
    private dateAdapter: DateAdapter<Date>) {
    dateAdapter.setLocale('it-IT');
  }
  ngOnInit() {

    if (this.filterUtilsService.InfoFiltro) {
      this.filtro = this.filterUtilsService.InfoFiltro;
      this.checkedIncludiCessate = this.filterUtilsService.InfoFiltro.flagIncludiCessate === "S" ? true : false;
      this.checkedIncludiNonAttive = this.filterUtilsService.InfoFiltro.flagIncludiNonAttive === "S" ? true : false;
      this.checkedIncludiAttive = this.filterUtilsService.InfoFiltro.flagIncludiAttive === "S" ? true : false;
      this.elencoComponent.aggiorna(this.filtro);
    }
    else {
      this.elencoComponent.aggiorna(null);
      this.filtro = new FiltroSoggettoVO(null, "", "", "", "N", "N", "S");
    }

    this.userService.funzionarioVo$.subscribe(data => {
      this.funzionario = data;
      this.enableExcel = data.authority.includes(AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_SOGGETTI);
    });
    this.tipoSoggGiuridLoaded = false;
    this.soggettoService.getTipiSoggettoGiuridico(true).subscribe(data => {
      if (data) {
        this.tipiSoggGiurid = data;
      }
      this.tipoSoggGiuridLoaded = true;
    });

  }

  isLoading(event: boolean) {
    this.ricercaInCorso = event;
  }

  aggiorna() {
    this.ricercaInCorso = true;
    //MEMORIZZA FILTRI DI RICERCA
    if (this.checkedIncludiAttive != null) {
      this.filtro.flagIncludiAttive = this.checkedIncludiAttive == true ? "S" : "N";
    }
    if (this.checkedIncludiNonAttive != null) {
      this.filtro.flagIncludiNonAttive = this.checkedIncludiNonAttive == true ? "S" : "N";
    }
    if (this.checkedIncludiCessate != null) {
      this.filtro.flagIncludiCessate = this.checkedIncludiCessate == true ? "S" : "N";
    }
    this.filterUtilsService.InfoFiltro = this.filtro;
    this.elencoComponent.aggiorna(this.filtro);
  }

  scaricaExcel() {
    this.downloadInCorso = true;
    const body = this.filtro;


    let date: Date = new Date();
    let month = addZero((date.getUTCMonth() + 1)).toString(); //months from 1-12
    let day = addZero(date.getUTCDate()).toString();
    function addZero(i) {
      if (i < 10) i = "0" + i;
      return i;
    }
    let year = addZero(date.getUTCFullYear()).toString();
    let hour = addZero(date.getHours()).toString();
    let minutes = addZero(date.getMinutes()).toString();
    let dateS: String = year + month + day + "_" + hour + minutes

    this.excelSoggettoService.excelSoggettoRicerca(body).subscribe(res => {
      let nameFile = "Ricerca_Soggetti_" + dateS + '.xls';
      saveAs(res, nameFile);
      this.downloadInCorso = false;
    },
      (error) => {
        CommonsHandleException.authorizationError(error, this.router);
        let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(error);
        if (errorRest.type == TypeErrorRest.OK) {
          this.feedback = errorRest.message;
        }
        else {
          this.feedback = "Si è verificato un errore in fase di salvataggio";
        }
        console.error("this.feedback =" + this.feedback);
        window.scrollTo(0, 0);
        this.downloadInCorso = false;
        console.error(error);
      });
  }




  pulisci() {
    this.elencoComponent.aggiorna(null);
    this.filterUtilsService.InfoFiltro = null;
    this.checkedIncludiCessate = false;
    this.checkedIncludiNonAttive = false;
    this.checkedIncludiAttive = true;
    this.filtro = new FiltroSoggettoVO(null, "", "", "", "N", "N", "S");
  }

  changeIncludiAttiveFilter(e) {
    this.filtro.flagIncludiAttive = e.checked ? 'S' : 'N';
  }

  changeIncludiNonAttiveFilter(e) {
    this.filtro.flagIncludiNonAttive = e.checked ? 'S' : 'N';
  }

  changeIncludiCessateFilter(e) {
    this.filtro.flagIncludiCessate = e.checked ? 'S' : 'N';
  }

  ngAfterContentChecked() {
    this.cdRef.detectChanges();
  }

  ngOnDestroy() {
  }
}
