/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild } from '@angular/core';
import { ElencoContribuzioneComponent } from '../elenco-contribuzione/elenco-contribuzione.component';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { HttpClient } from '@angular/common/http';
import { DateAdapter } from '@angular/material';
import { NgForm } from '@angular/forms';
import { DescrizioneVO, ComboVO, TipoCombo } from '../../vo/combo-vo';
import { UserInfo } from '../../vo/funzionario-vo';
import { UserService } from '../../services/user.service';
import { UtilityService } from '../../services/utility.service';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { saveAs } from "file-saver";
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { AssegnazioneRisorseVO } from "../../vo/contribuzione/assegnazione-risorse-vo";
import { ContribuzioneService } from "../../services/contribuzione.service";
import { FonteFinanziamentoVO } from "../../vo/contribuzione/fonte-finanziamento-vo";
import { FiltroContribuzioneVO } from '../../vo/filtro-contribuzione-vo';
import { AziendaVO } from '../../vo/azienda-vo';
import { RendicontazioneFilterService } from '../../services/rendicontazioneFilter.service';
import { ZipService } from '../../services/zip.service';
import { Router } from '@angular/router';
import { ExcelService } from '../../services/excel.service';

@Component({
    selector: 'app-ricerca-contribuzione',
    templateUrl: './ricerca-contribuzione.component.html',
    styleUrls: ['./ricerca-contribuzione.component.scss']
})
@DestroySubscribers()
export class RicercaContribuzioneComponent implements OnInit {

    filtro: FiltroContribuzioneVO;
    ricercaInCorso: boolean; //loading ricerca
    downloadincorso: boolean; //excel

    isUtenteAzienda: boolean;
    funzionario: UserInfo;

    loadedFonteFinanziamento: boolean;
    loadedAlimentazione: boolean;
    loadedAzienda: boolean;

    //dropdown
    idAttoAssegnazioneRisorse: number;
    assegnazioneRisorse: AssegnazioneRisorseVO;
    listOfFonteFinanziamento: FonteFinanziamentoVO[];
    tipiAlimentazione: DescrizioneVO[];

    tipoAzienda: AziendaVO[];

    isAzienda: boolean;
    isRicercaContribuzione: boolean;
    downloadZip: boolean; //excel

    checkedRendicontazione: boolean = false; // rendicontazione
    feedback: string;

    @ViewChild('searchForm') formGroup: NgForm;
    @ViewChild(ElencoContribuzioneComponent)
    private elencoComponentContribuzione: ElencoContribuzioneComponent; //da capire per elenco tabella

    enableExcel: boolean;
    enableZip: boolean;

    public subscribers: any = {};
    context: string;
    constructor(
        private userService: UserService,
        private filterUtilsService: RendicontazioneFilterService,
        private navbarFilterContext: NavbarFilterContext,
        private utilityService: UtilityService,
        private http: HttpClient,
        private contribuzioneService: ContribuzioneService,
        private zipService: ZipService,
        private excelService: ExcelService,
        private router: Router,
        private dateAdapter: DateAdapter<Date>) {
        dateAdapter.setLocale('it-IT');
    }


    ngOnInit() {
        //navbar
        if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
            var intervalContesti = setInterval(() => {
                this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 5);
                if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                    this.context = this.navbarFilterContext.InfoFiltro.cod;
                }
                clearInterval(intervalContesti);
            }, 200);
        } else {
            this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 5);
            if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                this.context = this.navbarFilterContext.InfoFiltro.cod;
            }
        }

        this.isAzienda = false;

        //carico valori già all'inizio come Richieste
        this.userService.funzionarioVo$.subscribe(data => {

            this.ricercaInCorso = false;
            this.funzionario = data;
            this.isRicercaContribuzione = this.funzionario.authority.includes(AuthorizationRoles.RICERCA_CONTRIBUZIONE);
            this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.enableZip = data.authority.includes(AuthorizationRoles.GENERA_ZIP);

            if (data.idAzienda != null) {
                this.isAzienda = true;
            }
            if (this.filterUtilsService.InfoFiltro) {
                this.filtro = this.filterUtilsService.InfoFiltro;
                this.checkedRendicontazione = this.filterUtilsService.InfoFiltro.flgRendicontazione === "S" ? true : false;
                this.elencoComponentContribuzione.aggiorna(this.filtro); //ricerca
            }
            else {
                this.filtro = new FiltroContribuzioneVO(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
                this.elencoComponentContribuzione.aggiorna(null);
            }

        });
        this.loadChoices(); //carica dropdown

    }

    loadChoices() {
        //dropdown azienda
        this.loadedAzienda = false;
        this.utilityService.elencoAziende();
        this.subscribers = this.utilityService.elencoAziende$.subscribe((data) => {
            this.tipoAzienda = data;
            this.loadedAzienda = true;
        }, (err) => {
            let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
            console.error(errorRest.message);
        }
        )

        //dropdown fonte di finanziamento
        this.loadedFonteFinanziamento = false;
        this.subscribers = this.contribuzioneService.getAllFonteFinanziamento().subscribe(
            (data) => {
                this.listOfFonteFinanziamento = data;
                this.loadedFonteFinanziamento = true;
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

        this.loadedAlimentazione = false;
        //dropdown alimentazione
        this.utilityService.elencoDecodifiche();
        this.subscribers = this.utilityService.comboVO$.subscribe(data => {
            if (data) {
                let comboVO: ComboVO[] = data;
                comboVO.forEach(
                    item => {
                        if (item.identificativo == TipoCombo.TIPO_ALIMENTAZIONE) {
                            this.tipiAlimentazione = item.valori;
                        }
                    }
                );
            }
            this.loadedAlimentazione = true;
        });
    }


    changeRendicontata(e) {
        this.filtro.flgRendicontazione = e.checked ? 'S' : 'N';
        if (this.filtro.flgRendicontazione === 'N')
            this.checkedRendicontazione = false
    }

    //CARICAMENTO
    isLoadingChild(event: boolean) {
        this.ricercaInCorso = event;
    }

    scaricaExcel(name: string) {
        this.downloadincorso = true;

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

        this.excelService.excelRicercaContribuzione(body).timeout(1000000).subscribe(res => {
            this.downloadincorso = false;
            let nameFile = name + dateS + '.xls';
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
                this.downloadincorso = false;
            });
    }


    pulisci() {
        if (this.isUtenteAzienda) {
            this.ricercaInCorso = true;
        } else {
            this.elencoComponentContribuzione.resetElencoContribuzione();
        }
        this.elencoComponentContribuzione.aggiorna(null);
        this.filterUtilsService.InfoFiltro = null;
        this.checkedRendicontazione = false;
        this.filtro = new FiltroContribuzioneVO(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }


    scaricaZip(name: string) {
        this.downloadZip = true;

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


        this.zipService.zipRicercaContribuzione(body).timeout(1000000).subscribe(res => {
            this.downloadZip = false;
            let nameFile = name + dateS + '.zip';
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
                this.downloadZip = false;
                console.error(error);
            });
    }

    aggiorna() {
        this.filterUtilsService.InfoFiltro = this.filtro;
        this.elencoComponentContribuzione.aggiorna(this.filtro);
    }


    ngOnDestroy() {
    }


    isLoading() {
        if (this.ricercaInCorso || this.downloadincorso || this.downloadZip)
            return true;

        return false;
    }



}