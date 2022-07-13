
/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, ChangeDetectorRef, ViewEncapsulation } from '@angular/core';
import { ElencoComponent } from '../elenco/elenco.component';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { HttpClient } from '@angular/common/http';
import { DateAdapter } from '@angular/material';
import { NgForm } from '@angular/forms';
import { FiltroAutobusVO } from '../../vo/filtro-autobus-vo';
import { DescrizioneVO, ComboVO, TipoCombo } from '../../vo/combo-vo';
import { UserInfo } from '../../vo/funzionario-vo';
import { UserService } from '../../services/user.service';
import { FilterUtilsService } from '../../services/filter.service';
import { UtilityService } from '../../services/utility.service';
import { AutobusService } from '../../services/autobus.service';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { saveAs } from "file-saver";
import { ErrorRest, TypeErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { CodeRoles } from '../../class/code-roles';
import { _MatTabHeaderMixinBase } from '@angular/material/tabs/typings/tab-header';
import { ActivatedRoute, Router } from "@angular/router";
import { ExcelService } from '../../services/excel.service';

@Component({
    selector: 'app-ricercaautobus',
    templateUrl: 'ricercaautobus.component.html',
    styleUrls: ['./ricercaautobus.component.css'],
    encapsulation: ViewEncapsulation.None
})

@DestroySubscribers()
export class RicercaAutobusAziendaComponent implements OnInit {
    filtro: FiltroAutobusVO;
    filtroincorso: boolean;
    downloadincorso: boolean;
    isUtenteAzienda: boolean = false;
    funzionario: UserInfo;

    checked: boolean = false;


    tipiAlimentazione: DescrizioneVO[];
    feedback: string;
    isEnableExcel: boolean;

    //filtri storici
    checkedIncludiVariazioniPre: boolean = false;

    //filtri avanzati
    autobus: boolean = true;
    autovetture: boolean = false;
    attivo: boolean = true;
    ritirato: boolean = false;
    alienato: boolean = false;
    includiPropPrec: boolean = false;
    daImmatricolare: boolean = true;

    verificatoAziende: boolean = true;
    verificatoAMP: boolean = false;
    bozza: boolean = false;
    richieste: boolean = false;
    rendicontazione: boolean = false;

    proprieta: string = null;
    immatricola: string = null;
    bozzaS: string = null;
    azione: string;

    @ViewChild('searchForm') formGroup: NgForm;
    @ViewChild(ElencoComponent)
    private elencoComponent: ElencoComponent;

    enableExcel: boolean;

    public subscribers: any = {};
    context: string;
    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private userService: UserService,
        private autobusService: AutobusService,
        private filterUtilsService: FilterUtilsService,
        private excelService: ExcelService,
        private navbarFilterContext: NavbarFilterContext,
        private utilityService: UtilityService,
        private cdRef: ChangeDetectorRef,
        private dateAdapter: DateAdapter<Date>) {
        dateAdapter.setLocale('it-IT');
    }


    ngOnInit() {
        this.filtroincorso = false;
        if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
            var intervalContesti = setInterval(() => {
                this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
                if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                    this.context = this.navbarFilterContext.InfoFiltro.cod;
                }
                clearInterval(intervalContesti);
            }, 200);
        } else {
            this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
            if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                this.context = this.navbarFilterContext.InfoFiltro.cod;
            }
        }

        this.azione = this.route.snapshot.paramMap.get('action');


        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            if (data.ruolo === null) {
                this.userService.funzionarioVo$.takeLast(1).subscribe(data => {
                    if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AZIENDA || data.ruolo.codiceRuolo === CodeRoles.RUOLO_PILOTA_AZIENDA) {
                        this.isUtenteAzienda = true
                    }
                });
            } else {
                if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AZIENDA || data.ruolo.codiceRuolo === CodeRoles.RUOLO_PILOTA_AZIENDA) {
                    this.isUtenteAzienda = true
                }
            }

            this.enableExcel = data.authority.includes(AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS);
            if (this.filterUtilsService.InfoFiltro) {
                this.filtro = this.filterUtilsService.InfoFiltro;
                if (!this.isUtenteAzienda && this.azione == null) {
                    this.filtro.includiPropPrec = 'N'
                    this.filtro.daImmatricolare = "N"
                }
                else if (this.isUtenteAzienda && this.azione == null) {
                    this.filtro.includiPropPrec = null
                    this.filtro.bozza = "S"
                }

                this.autobus = this.filterUtilsService.InfoFiltro.autobus === "S" ? true : false;
                this.autovetture = this.filterUtilsService.InfoFiltro.autovetture === "S" ? true : false;
                this.attivo = this.filterUtilsService.InfoFiltro.attivo === "S" ? true : false;
                this.ritirato = this.filterUtilsService.InfoFiltro.ritirato === "S" ? true : false;

                if (this.isUtenteAzienda) {
                    this.alienato = this.filterUtilsService.InfoFiltro.alienatoAzienda === "S" ? true : false;
                }
                else {
                    this.alienato = this.filterUtilsService.InfoFiltro.alienatoNoAzienda === "S" ? true : false;
                }

                this.daImmatricolare = this.filterUtilsService.InfoFiltro.daImmatricolare === "S" ? true : false;
                this.verificatoAziende = this.filterUtilsService.InfoFiltro.verificatoAziende === "S" ? true : false;
                this.verificatoAMP = this.filterUtilsService.InfoFiltro.verificatoAMP === "S" ? true : false;
                this.bozza = this.filterUtilsService.InfoFiltro.bozza === "S" ? true : false;
                this.rendicontazione = this.filterUtilsService.InfoFiltro.rendicontazione === "S" ? true : false;
                this.richieste = this.filterUtilsService.InfoFiltro.richieste === "S" ? true : false;
                this.includiPropPrec = this.filterUtilsService.InfoFiltro.includiPropPrec === "S" ? true : false;
                //storico
                this.checkedIncludiVariazioniPre = this.filterUtilsService.InfoFiltro.flagIncludiVariazioniPre === "S" ? true : false;
                this.elencoComponent.aggiorna(this.filtro);
            }
            else {
                if (!this.isUtenteAzienda) {
                    this.proprieta = 'N'
                    this.immatricola = "N"
                }
                else if (this.isUtenteAzienda) {
                    this.proprieta = null
                    this.bozzaS = "S"
                    this.immatricola = "S"
                }

                this.filtro = new FiltroAutobusVO("", "", "", "", "", "", "", "", "", "", "S", null, "S", null, null, null, this.proprieta, this.immatricola, "S", null, this.bozzaS, null, null);
                if (this.isUtenteAzienda) this.elencoComponent.aggiorna(this.filtro);
            }

        });

        if (this.azione != 'dettaglio' && this.azione != 'modifica') {
            this.pulisci();
        }

        this.autobusService.eliminaAutobus$.subscribe(data => {
            this.aggiorna();
        });

        this.utilityService.elencoDecodifiche();
        this.subscribers = this.utilityService.comboVO$.subscribe(data => {
            let comboVO: ComboVO[] = data;
            comboVO.forEach(
                item => {
                    if (item.identificativo == TipoCombo.TIPO_ALIMENTAZIONE) {
                        this.tipiAlimentazione = item.valori;
                    }
                }
            );
        });
    }

    checkDataPrimaImmatricolazione(dataIn: Date, dataFin: Date) {
        let haveError: boolean = false;
        if (dataIn && dataFin) {
            if (dataIn > dataFin) {
                this.formGroup.form.get('dataPrimaImmatricolazioneDa').setErrors({ daMaggioreA: true });
                this.formGroup.form.get('dataPrimaImmatricolazioneA').setErrors({ aMinoreDa: true });
                this.formGroup.form.get('dataPrimaImmatricolazioneDa').markAsTouched();
                this.formGroup.form.get('dataPrimaImmatricolazioneA').markAsTouched();
                haveError = true;
            }
        } else {
            if (!dataIn) {
                this.formGroup.form.get('dataPrimaImmatricolazioneDa').setValue(null);
            } if (!dataFin) {
                this.formGroup.form.get('dataPrimaImmatricolazioneA').setValue(null);
            }
        }
        if (!haveError) {
            this.formGroup.form.get('dataPrimaImmatricolazioneDa').setErrors(null);
            this.formGroup.form.get('dataPrimaImmatricolazioneA').setErrors(null);
            this.formGroup.form.get('dataPrimaImmatricolazioneDa').markAsUntouched();
            this.formGroup.form.get('dataPrimaImmatricolazioneA').markAsUntouched();
        }
    }

    isLoading(event: boolean) {
        this.filtroincorso = event;
    }

    aggiorna() {
        if (this.filtro.autobus == null || this.filtro.autobus === "") {
            this.filtro.autobus = "S";
        }

        if (this.isUtenteAzienda === false) {
            this.proprieta = 'N'
            this.immatricola = "N"
        }
        else if (this.isUtenteAzienda === true) {
            this.proprieta = null
            this.bozzaS = "S"
        }

        this.filterUtilsService.InfoFiltro = this.filtro;
        this.elencoComponent.aggiorna(this.filtro);
    }

    scaricaExcel(name: string) {
        this.downloadincorso = true;
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

        this.excelService.exportRicercaAutobus(body).timeout(1000000).subscribe(res => {
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
        this.filterUtilsService.InfoFiltro = null;
        this.checkedIncludiVariazioniPre = false;
        this.autobus = true;
        this.autovetture = false;
        this.attivo = true;
        this.ritirato = false;
        this.alienato = false;
        this.includiPropPrec = false;
        this.daImmatricolare = true;
        this.verificatoAziende = true;
        this.verificatoAMP = false;
        this.bozza = false;
        this.richieste = false;
        this.rendicontazione = false;
        if (this.isUtenteAzienda === false) {
            this.proprieta = 'N'
            this.daImmatricolare = false
            this.immatricola = "N"
        }
        else if (this.isUtenteAzienda === true) {
            this.proprieta = null
            this.bozza = true
            this.bozzaS = "S"

        }

        this.filtro = new FiltroAutobusVO("", "", "", "", "", "", "", "", "", "", "S", null, "S", null, null, null, this.proprieta, this.immatricola, "S", null, this.bozzaS, null, null);

        if (this.isUtenteAzienda) {
            this.elencoComponent.aggiorna(this.filtro);
            this.filtroincorso = true;
        } else {
            this.elencoComponent.resetElencoAutobus();
        }
    }

    checkProcedimenti(e) {
        this.filtro.codiceRichiesta = e.checked ? 'S' : 'N';
        if (this.filtro.codiceRichiesta === 'S') {
            this.filtro.codiceRichiesta = "";
        }

    }

    changeCheckedIncludiVariazioniPre(e) {
        this.filtro.flagIncludiVariazioniPre = e.checked ? 'S' : 'N';

    }

    changeSituazioneAl() {
        if (this.filtro.situazioneAl == null || this.filtro.situazioneAl.length == 0) {
            this.filtro.flagIncludiVariazioniPre = 'N';
            this.checkedIncludiVariazioniPre = false;
        }
        else {
            this.filtro.codiceRichiesta = "";

        }
    }

    //----------------------filtri avanzati
    changeAutobus(e) {
        this.filtro.autobus = e.checked ? 'S' : 'N';
    }

    chengeAutovetture(e) {
        this.filtro.autovetture = e.checked ? 'S' : 'N';
    }

    chengeRitirato(e) {
        this.filtro.ritirato = e.checked ? 'S' : 'N';
    }

    chengeAttivo(e) {
        this.filtro.attivo = e.checked ? 'S' : 'N';
    }

    chengeAlienato(e) {
        if (this.isUtenteAzienda) {
            this.filtro.alienatoAzienda = e.checked ? 'S' : 'N';
        }
        else { this.filtro.alienatoNoAzienda = e.checked ? 'S' : 'N'; }
    }

    chengeIncludiPropPrec(e) {
        this.filtro.includiPropPrec = e.checked ? 'S' : 'N';
    }

    chengeDaImmatricolare(e) {
        this.filtro.daImmatricolare = e.checked ? 'S' : 'N';
    }

    chengeVerificatoAziende(e) {
        this.filtro.verificatoAziende = e.checked ? 'S' : 'N';
    }
    chengeVerificatoAMP(e) {
        this.filtro.verificatoAMP = e.checked ? 'S' : 'N';
    }
    chengeBozza(e) {
        this.filtro.bozza = e.checked ? 'S' : 'N';
    }
    chengeRichieste(e) {
        this.filtro.richieste = e.checked ? 'S' : 'N';
        if (this.filtro.richieste === 'S')
            this.filtro.codiceRichiesta = "";
    }
    chengeRendicontazione(e) {
        this.filtro.rendicontazione = e.checked ? 'S' : 'N';
    }

    ngAfterContentChecked() {
        this.cdRef.detectChanges();
    }

    ngOnDestroy() {
    }

}