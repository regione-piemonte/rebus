/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { UserInfo } from '../../vo/funzionario-vo';
import { ElencoContrattoComponent } from '../elenco-contratto/elenco-contratto.component';
import { Router } from '@angular/router';
import { DateAdapter } from '@angular/material';
import { UserService } from '../../services/user.service';
import { FiltroContrattoVO } from '../../vo/filtro-contratto-vo';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { ContrattoFilterService } from '../../services/contrattoFilter.service';
import { NgForm } from '@angular/forms';
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { ExcelContrattoService } from '../../services/excel-contratto.service';



@Component({
    selector: 'app-ricerca-contratto',
    templateUrl: './ricerca-contratto.component.html',
    styleUrls: ['./ricerca-contratto.component.scss']
})

@DestroySubscribers()
export class RicercaContrattoComponent implements OnInit {
    downloadInCorso: boolean;
    ricercaInCorso: boolean;
    filtro: FiltroContrattoVO;
    isUtenteAzienda: boolean;
    funzionario: UserInfo;
    checkedIncludiCessate: boolean = false;
    feedback: string;

    dataIn: Date;
    dataFin: Date;
    dataToday: Date = new Date();

    @ViewChild('searchForm') formGroup: NgForm;

    @ViewChild(ElencoContrattoComponent)
    private elencoComponent: ElencoContrattoComponent;

    enableExcel: boolean;

    public subscribers: any = {};

    constructor(
        private router: Router,
        private userService: UserService,
        private filterUtilsService: ContrattoFilterService,
        private excelContrattoService: ExcelContrattoService,
        private cdRef: ChangeDetectorRef,
        private dateAdapter: DateAdapter<Date>) {
        dateAdapter.setLocale('it-IT');
    }
    ngOnInit() {


        if (this.filterUtilsService.InfoFiltro) {
            this.filtro = this.filterUtilsService.InfoFiltro;
            this.checkedIncludiCessate = this.filterUtilsService.InfoFiltro.flagIncludiCessate === "S" ? true : false;
            this.dataIn = this.filterUtilsService.InfoFiltro.dataStipulaDa;
            this.dataFin = this.filterUtilsService.InfoFiltro.dataStipulaA;
            this.elencoComponent.aggiorna(this.filtro);
        }
        else {
            this.elencoComponent.aggiorna(null);
            this.filtro = new FiltroContrattoVO("", "", "", "", "", null, null, "");
        }

        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            //this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.enableExcel = data.authority.includes(AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_CONTRATTI);
        });

    }

    checkDataStipula(dataIn: Date, dataFin: Date) {
        let haveError: boolean = false;
        if (dataIn && dataFin) {
            if (dataIn > dataFin) {
                this.formGroup.form.get('dataInizio').setErrors({ daMaggioreA: true });
                this.formGroup.form.get('dataFine').setErrors({ aMinoreDa: true });
                this.formGroup.form.get('dataInizio').markAsTouched();
                this.formGroup.form.get('dataFine').markAsTouched();

                haveError = true;
            }
        } else {
            if (!dataIn) {
                this.formGroup.form.get('dataInizio').setValue(null);
            } if (!dataFin) {
                this.formGroup.form.get('dataFine').setValue(null);
            }

        }
        if (!haveError) {
            this.formGroup.form.get('dataInizio').setErrors(null);
            this.formGroup.form.get('dataFine').setErrors(null);
            this.formGroup.form.get('dataInizio').markAsUntouched();
            this.formGroup.form.get('dataFine').markAsUntouched();
        }
    }

    isLoading(event: boolean) {
        this.ricercaInCorso = event;
    }

    aggiorna() {
        //MEMORIZZA FILTRI DI RICERCA
        this.filtro.dataStipulaDa = this.dataIn;
        this.filtro.dataStipulaA = this.dataFin;
        if (this.filtro.codIdentificativo == null && this.filtro.dataStipulaA == null && this.filtro.dataStipulaDa == null && this.filtro.enteCommittente == null && this.filtro.soggEsecutore == null && this.filtro.descrizione == null)
            this.filtro.flagIncludiCessate = "S";
        this.filterUtilsService.InfoFiltro = this.filtro;
        this.elencoComponent.aggiorna(this.filtro);
    }

    scaricaExcel() {
        this.downloadInCorso = true;
        let body = this.filtro;


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

        this.excelContrattoService.excelRicercaContratti(body).timeout(1000000).subscribe(res => {
            this.downloadInCorso = false;
            let nameFile = "Ricerca_Contratti_" + dateS + '.xls';
            saveAs(res, nameFile);
        },
            error => {
                CommonsHandleException.authorizationError(error, this.router);
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(error);
                if (errorRest.type == TypeErrorRest.OK) {
                    this.feedback = errorRest.message;
                }
                else {
                    this.feedback = "Si è verificato un errore in fase di salvataggio";
                }
                window.scrollTo(0, 0);
                this.downloadInCorso = false;
            });

    }

    pulisci() {
        this.elencoComponent.aggiorna(null);
        this.filterUtilsService.InfoFiltro = null;
        this.checkedIncludiCessate = false;
        this.filtro = new FiltroContrattoVO("", "", "", "", "", null, null, "N");
        this.dataIn = null;
        this.dataFin = null;
    }

    changeIncludiCessateFilter(e) {
        this.filtro.flagIncludiCessate = e.checked ? 'S' : 'N';
    }

    ngAfterContentChecked() {
        this.cdRef.detectChanges();
    }


    ngOnDestroy() {
    }

    getStyle() {
        if (this.formGroup.form && this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('daMaggioreA')) {
            return 'rgb(99, 97, 97);';
        }

        return 'white';
    }

    getBackground() {
        if (this.formGroup.form && this.formGroup.form.get('dataInizio') && this.formGroup.form.get('dataInizio').hasError('daMaggioreA')) {
            return 'rgb(192, 192, 192)';
        }
        return 'rgb(214,87,42)';
    }
}
