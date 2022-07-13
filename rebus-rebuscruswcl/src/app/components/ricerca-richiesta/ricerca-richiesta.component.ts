/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, OnInit, ViewChild, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { UserInfo } from '../../vo/funzionario-vo';
import { DateAdapter, MatSnackBar, MatAutocompleteTrigger } from '@angular/material';
import { UserService } from '../../services/user.service';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { FiltroProcedimentiVO } from '../../vo/filtro-procedimenti-vo';
import { RichiestaFilterService } from '../../services/contrattoFilter.service';
import { TipoProcedimentoVO, StatoProcedimentoVO } from '../../vo/extend-vo';
import { FormGroup, FormControl, NgForm } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { ElencoRichiestaComponent } from '../elenco-richiesta/elenco-richiesta.component';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { ProcedimentoService } from '../../services/procedimento.service';

@Component({
    selector: 'app-ricerca-richiesta',
    templateUrl: './ricerca-richiesta.component.html',
    styleUrls: ['./ricerca-richiesta.component.scss']
})

@DestroySubscribers()
export class RicercaRichiestaComponent implements OnInit,OnDestroy {
    downloadInCorso: boolean;
    ricercaInCorso: boolean;
    filtro: FiltroProcedimentiVO;
    isUtenteAzienda: boolean;
    funzionario: UserInfo;
    checkedStatoCorrente: boolean = true;

    dataIn: Date;
    dataFin: Date;
    dataToday: Date = new Date();

    @ViewChild('searchForm') formGroup: NgForm;

    @ViewChild(ElencoRichiestaComponent)
    private elencoComponent: ElencoRichiestaComponent;

    enableExcel: boolean;

    public subscribers: any = {};
    loadedTipoProcedimento: boolean;
    tipoProcedimento: Array<TipoProcedimentoVO>;
    loadedStatoProcedimento: boolean;
    statoProcedimento: Array<StatoProcedimentoVO> = [];

    tipoProcedimentoGroup: FormGroup = new FormGroup({ tipoProcedimentoForm: new FormControl() });
    tipoProcedimentoSelected = new FormControl();
    filteredOptionsTipoProcedimento: Observable<TipoProcedimentoVO[]>;

    statoProcedimentoGroup: FormGroup = new FormGroup({ statoProcedimentoForm: new FormControl() });
    statoProcedimentoSelected = new FormControl();
    filteredOptionsStatoProcedimento: Observable<StatoProcedimentoVO[]>;
    isRicercaAbilitata: boolean;
    subscriptionEliminaProcedimento: Subscription;
    context: string;

    constructor(
        private userService: UserService,
        private procedimentoService: ProcedimentoService,
        private filterUtilsService: RichiestaFilterService,
        private navbarFilterContext: NavbarFilterContext,
        public snackBar: MatSnackBar,
        private cdRef: ChangeDetectorRef,
        private dateAdapter: DateAdapter<Date>) {
        dateAdapter.setLocale('it-IT');
    }
    ngOnInit() {
        if(this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti==null){
            var intervalContesti = setInterval(() => {
                this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c=>c.id===2);
            if(this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null){
                this.context = this.navbarFilterContext.InfoFiltro.cod;
            }
                    clearInterval(intervalContesti);
            }, 200);
        }else{
            this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c=>c.id===2);
            if(this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null){
                this.context = this.navbarFilterContext.InfoFiltro.cod;
            }
        }

        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.isRicercaAbilitata = this.funzionario.authority.includes(AuthorizationRoles.RICERCA_PROCEDIMENTI);
            this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.enableExcel = data.authority.includes(AuthorizationRoles.SCARICA_EXCEL_PROCEDIMENTO);

            if (this.filterUtilsService.InfoFiltro) {
                this.filtro = this.filterUtilsService.InfoFiltro;
                this.checkedStatoCorrente = this.filterUtilsService.InfoFiltro.flagStatoCorrente === "S" ? true : false;
                this.elencoComponent.aggiorna(this.filtro);
            }
            else {
                this.filtro = new FiltroProcedimentiVO(null, null, null, null, null, null, "S", null);
               this.elencoComponent.aggiorna(null);
                
            }
        });

        this.subscriptionEliminaProcedimento = this.procedimentoService.eliminaProcedimento$.subscribe(data => {

            this.aggiorna();
            this.snackBar.open("Procedimento eliminato correttamente!", "Chiudi", {
                duration: 2000,
            });
        });

        this.loadChoices();
    }

    loadChoices() {
        this.loadedTipoProcedimento = false;
        this.procedimentoService.getElencoTipologia().subscribe(data => { 
            if (data) {
                this.tipoProcedimento = data;
                let index = this.tipoProcedimento.findIndex(a => a.id == 7);
                this.tipoProcedimento.splice(index, 1);
                this.filteredOptionsTipoProcedimento = this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].valueChanges
                    .pipe(
                        startWith(''),
                        map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                        map(name => name ? this._filterTipoProcedimento(name) : this.tipoProcedimento.slice())
                    );
                if (this.filtro.tipologia) {
                    this.tipoProcedimentoSelected.setValue(this.tipoProcedimento.find(a => a.id == this.filtro.tipologia));
                    this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].setValue(this.tipoProcedimento.find(a => a.id == this.filtro.tipologia));
                }
            }
            this.loadedTipoProcedimento = true;
        });

        this.loadedStatoProcedimento = false;
        this.procedimentoService.getElencoStato().subscribe(data => {
            if (data) {

                // FILTRO GLI STATI ELIMINANDO QUELLI RELATIVI ALLA RENDICONTAZIONE(NON DIVISIBILI PER 10)
                data.forEach(element => {
                    if(element.id % 10 == 0){
                        this.statoProcedimento.push(element);
                    }
                });
                this.filteredOptionsStatoProcedimento = this.statoProcedimentoGroup.controls['statoProcedimentoForm'].valueChanges
                    .pipe(
                        startWith(''),
                        map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                        map(name => name ? this._filterStatoProcedimento(name) : this.statoProcedimento.slice())
                    );
                if (this.filtro.stato) {
                    this.statoProcedimentoSelected.setValue(this.statoProcedimento.find(a => a.id == this.filtro.stato));
                    this.statoProcedimentoGroup.controls['statoProcedimentoForm'].setValue(this.statoProcedimento.find(a => a.id == this.filtro.stato));
                }
            }
            this.loadedStatoProcedimento = true;
        });
        if (this.filtro.dataStipulaDa) {
            this.dataIn = this.filtro.dataStipulaDa;
        }
        if (this.filtro.dataStipulaA) {
            this.dataFin = this.filtro.dataStipulaA;
        }
    }

    checkDataStato(dataIn: Date, dataFin: Date) {
        let haveError: boolean = false;
        if (dataIn && dataFin) {
            if (dataIn > dataFin) {
                this.formGroup.form.get('dataInizio').setErrors({ daMaggioreA: true });
                this.formGroup.form.get('dataFine').setErrors({ aMinoreDa: true });
                this.formGroup.form.get('dataInizio').markAsTouched();
                this.formGroup.form.get('dataFine').markAsTouched();
                haveError = true;
            }
        } else if (!dataIn) {
            this.formGroup.form.get('dataInizio').setValue(null);
        } else if (!dataFin) {
            this.formGroup.form.get('dataFine').setValue(null);
        }
        if (!haveError) {
            this.formGroup.form.get('dataInizio').setErrors(null);
            this.formGroup.form.get('dataFine').setErrors(null);
            this.formGroup.form.get('dataInizio').markAsUntouched();
            this.formGroup.form.get('dataFine').markAsUntouched();
        }
    }


    isLoadingChild(event: boolean) {
        this.ricercaInCorso = event;
    }

    aggiorna() {
        //MEMORIZZA FILTRI DI RICERCA
        if (this.dataIn != null) {
            this.filtro.dataStipulaDa = this.dataIn;
        }
        if (this.dataFin != null) {
            this.filtro.dataStipulaA = this.dataFin;
        }
        if (this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'] != null && this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].value != null) {
            this.filtro.tipologia = this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].value.id;
        }
        if (this.statoProcedimentoGroup.controls['statoProcedimentoForm'] != null && this.statoProcedimentoGroup.controls['statoProcedimentoForm'].value != null) {
            this.filtro.stato = this.statoProcedimentoGroup.controls['statoProcedimentoForm'].value.id;
        }
        if (this.checkedStatoCorrente != null) {
            this.filtro.flagStatoCorrente = this.checkedStatoCorrente == true ? "S" : "N";
        }

        this.filterUtilsService.InfoFiltro = this.filtro;
        this.elencoComponent.aggiorna(this.filtro);
    }

    selectionMade(event: Event, trigger: MatAutocompleteTrigger) {
        event.stopPropagation();
        trigger.openPanel();
    }

    //metodi per filtrare la ricerca nelle combobox
    private _filterTipoProcedimento(descrizione: string): TipoProcedimentoVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.tipoProcedimento.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
    }

    private _filterStatoProcedimento(descrizione: string): StatoProcedimentoVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.statoProcedimento.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
    }
    //per selezionare valori nelle combobox
    click(event: any, s: string) {
        if (s == 'TipoProc') {
            this.tipoProcedimentoSelected = event.option.value;
        } else if (s == 'StatoProc') {
            this.statoProcedimentoSelected = event.option.value;
        }
    }
    //necessario per pulire le combobox
    check(s: string) {
        setTimeout(() => {
            if (s == 'TipoProc') {
                if (!this.tipoProcedimentoSelected || (this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'] && this.tipoProcedimentoSelected !== this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].value)) {
                    this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].setValue(null);
                    this.tipoProcedimentoSelected = new FormControl();
                }
            }
            else if (s == 'StatoProc') {
                if (!this.statoProcedimentoSelected || (this.statoProcedimentoGroup.controls['statoProcedimentoForm'] && this.statoProcedimentoSelected !== this.statoProcedimentoGroup.controls['statoProcedimentoForm'].value)) {
                    this.statoProcedimentoGroup.controls['statoProcedimentoForm'].setValue(null);
                    this.statoProcedimentoSelected = new FormControl();
                }
            }
        }, 200);
    }

    displayFn(a?: any): string | undefined {
        return a ? a.descrizione : undefined;
    }

    pulisci() {
        this.elencoComponent.aggiorna(null);
        this.filterUtilsService.InfoFiltro = null;
        this.ricercaInCorso = true;
        this.checkedStatoCorrente = true;
        this.filtro = new FiltroProcedimentiVO(null, null, null, null, null, null, "S", null);
        //resetto le due combobox
        this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].setValue(null);
        this.tipoProcedimentoSelected = new FormControl();
        this.statoProcedimentoGroup.controls['statoProcedimentoForm'].setValue(null);
        this.statoProcedimentoSelected = new FormControl();
        this.dataIn = null;
        this.dataFin = null;
    }

    changeStatoCorrenteFilter(e) {
        this.filtro.flagStatoCorrente = e.checked ? 'S' : 'N';
    }

    ngAfterContentChecked() {
        this.cdRef.detectChanges();
    }

    ngOnDestroy() {
        this.subscriptionEliminaProcedimento.unsubscribe();
    }

    isLoading() {
        if (this.ricercaInCorso || this.downloadInCorso || (!this.loadedStatoProcedimento) || (!this.loadedTipoProcedimento)) return true;
        return false;
    }

    svuotaFlitri(value: string){
        if (value === 'tipologia') {
            this.tipoProcedimentoGroup.controls['tipoProcedimentoForm'].setValue(null);
        }
        if (value === 'stato') {
            this.statoProcedimentoGroup.controls['statoProcedimentoForm'].setValue(null);
        }
    }
}
