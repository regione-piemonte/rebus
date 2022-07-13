/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Input, Output, EventEmitter, ChangeDetectorRef } from "@angular/core";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { NgForm, FormGroup, FormControl, FormBuilder, Validators } from "@angular/forms";
import { ProcedimentoService } from "../../services/procedimento.service";
import { TipoProcedimentoVO, MotorizzazioneVO, MotivazioneVO, TipoDocumentoVO, TipoContrattoVO } from "../../vo/extend-vo";
import { Observable } from "rxjs";
import { VeicoloVO } from "../../vo/veicolo-vo";
import { AutobusService } from "../../services/autobus.service";
import { startWith, map } from "rxjs/operators";
import { AllegatoProcVO } from "../../vo/allegato-proc-vo";
import { of } from "rxjs/observable/of";
import { ContrattoProcVO, ContrattoProcDatiVO } from "../../vo/contratto-proc-vo";
import { saveAs } from "file-saver";
import { DocumentService } from "../../services/document.service";
import { AllegatoVeicoloVO } from "../../vo/allegato-veicolo-vo";
import { ErrorRest, TypeErrorRest } from "../../class/error-rest";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { MatSnackBar, MatDialog } from "@angular/material";
import { Router, ActivatedRoute } from "@angular/router";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { TransizioneAutomaVO } from "../../vo/transizione-automa-vo";
import { FiltroProcedimentiVO } from "../../vo/filtro-procedimenti-vo";
import { IterProcedimentoVO } from "../../vo/iter-procedimento-vo";
import { DialogComponent } from "../dialog/dialog.component";
import { UserService } from "../../services/user.service";
import { UserInfo } from "../../vo/funzionario-vo";
import { CancellaDialogComponent } from "../cancelladialog/cancelladialog.component";
import { UtilizzoVO } from "../../vo/utilizzo-vo";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";

@Component({
    selector: 'app-modifica-richiesta-uso-in-linea',
    templateUrl: './modifica-richiesta-uso-in-linea.component.html',
    styleUrls: ['./modifica-richiesta-uso-in-linea.component.scss']
})
@DestroySubscribers()
export class ModificaRichiestaUsoInLineaComponent implements OnInit, OnDestroy {

    feedback: string;
    doubleError: boolean = false;

    id: number;
    azione: string;
    dettaglioRichiesta: DettaglioRichiestaVO;
    funzionario: UserInfo;
    tipoProcedimento: TipoProcedimentoVO;
    veicoli: Array<VeicoloVO>;
    contratti: Array<ContrattoProcVO>;
    tipiContratto: Array<TipoContrattoVO>;
    idTipoContratto: number;
    allegatiVeicoli: Array<AllegatoVeicoloVO> = new Array<AllegatoVeicoloVO>();
    haveAllegati: boolean;
    nomeAllegatoTmp: string;
    tipiDocumento: Array<TipoDocumentoVO>;
    allegati = new Array<AllegatoProcVO>();
    arrayAllegatiFiltrati: Array<number>;
    noteFile: string = "";
    transizioniAutoma: Array<TransizioneAutomaVO>;
    transizioneAutoma: TransizioneAutomaVO;
    notaTransizione: string = "";
    utilizzi: Array<UtilizzoVO>;

    boolFirstLoad: boolean;
    downloadPDF: boolean;

    isFirmaVisible: boolean = false;
    disableNominativoRuoloFirma: boolean = true;
    disableNominativoRuoloFirmaEnte: boolean = true;

    veicoloGroup: FormGroup = new FormGroup({ veicoloForm: new FormControl() });
    veicoloSelected = new Array<VeicoloVO>();
    filteredOptionsVeicolo: Observable<VeicoloVO[]>;
    veicoloCheck = new Array<VeicoloVO>();

    dettaglioRichiestaFirma: DettaglioRichiestaVO;
    loadedDettaglioRichiestaFirma: boolean = true;
    ruoloFirmaLoaded: string;
    nominativoFirmaLoaded: string;

    contrattoGroup: FormGroup = new FormGroup({ contrattoForm: new FormControl() });
    contrattoSelected = new FormControl();
    contrattiSelected = new Array<ContrattoProcVO>();
    filteredOptionsContratto: Observable<ContrattoProcVO[]>;

    tipiContrattoGroup: FormGroup = new FormGroup({ tipiContrattoForm: new FormControl() });
    tipiContrattoSelected = new FormControl();
    filteredOptionsTipiContratto: Observable<TipoContrattoVO[]>;

    tipoDocumentoSelected = new FormControl();
    filteredOptionsTipoDocumento: Observable<TipoDocumentoVO[]>;
    tipoDocumentoGroup: FormGroup = new FormGroup({ tipoDocumentoForm: new FormControl() });

    transizioneGroup: FormGroup = new FormGroup({ transizioneForm: new FormControl() });
    transizioneSelected = new FormControl();

    loadedTipoProcedimento: boolean;
    loadedVeicoli: boolean;
    loadedContratti: boolean;
    loadedTipiContratto: boolean;
    loadedTipiDoc: boolean;
    loadedSave: boolean = true;
    loadComplete: boolean;
    loadedTransizioniAutoma: boolean;
    loadedAvanzaIter: boolean = true;
    loadedDatiContratto: boolean = true;

    richiestaConfermata: boolean;

    dataToday: Date = new Date();

    @ViewChild('dettaglioRichiestaForm') dettaglioRichiestaForm: NgForm;
    @ViewChild('fileInput') fileInput: ElementRef;
    @ViewChild('premesseFile') premesseFile: ElementRef;
    @ViewChild('prescrizioniFile') prescrizioniFile: ElementRef;
    @ViewChild('avanzaIter') inputEl: ElementRef;

    @Input('filtroRicerca') filtroRicerca: FiltroProcedimentiVO;
    @Output('isRicerca') isRicerca = new EventEmitter<boolean>();
    context: string;



    constructor(private procedimentoService: ProcedimentoService,
        private autobusService: AutobusService,
        private userService: UserService,
        private documentService: DocumentService,
        public snackBar: MatSnackBar,
        public dialog: MatDialog,
        private router: Router,
        private route: ActivatedRoute,
        private navbarFilterContext: NavbarFilterContext,
        private changeDetector: ChangeDetectorRef,
        private fb: FormBuilder,
    ) { }

    ngOnInit(): void {
        this.dettaglioRichiesta = new DettaglioRichiestaVO();
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
        });
        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
            this.context = this.navbarFilterContext.InfoFiltro.cod;
        }
        window.scroll(0, 0);
        this.azione = this.route.snapshot.paramMap.get('action');
        this.route.params.subscribe(params => {
            this.id = +params['id']; // (+) converts string 'id' to a number

            this.loadData();
        });
    }

    loadData() {
        this.loadComplete = false;
        if (this.transizioneAutoma != null && this.transizioneAutoma.idStatoIterA == 20) {
            this.allDisabled = false;
        } else if (this.transizioneAutoma != null && this.transizioneAutoma.idStatoIterA == 10) {
            this.allEnabled = false;
        }
        this.procedimentoService.dettaglioRichiesta(this.id, Action.EDIT).subscribe(data => {
            if (data) {
                this.dettaglioRichiesta = new DettaglioRichiestaVO();
                this.dettaglioRichiesta = data;
                this.utilizzi = new Array<UtilizzoVO>();
                if ((this.dettaglioRichiesta.nominativoFirma != null && this.dettaglioRichiesta.nominativoFirma != '') ||
                    (this.dettaglioRichiesta.ruoloFirma != null && this.dettaglioRichiesta.ruoloFirma != '')) {
                    this.ruoloFirmaLoaded = this.dettaglioRichiesta.ruoloFirma;
                    this.nominativoFirmaLoaded = this.dettaglioRichiesta.nominativoFirma;

                }

                if ((this.dettaglioRichiesta.nominativoFirmaEnte != null && this.dettaglioRichiesta.nominativoFirmaEnte != '') ||
                    (this.dettaglioRichiesta.ruoloFirmaEnte != null && this.dettaglioRichiesta.ruoloFirmaEnte != '')) {
                    this.disableNominativoRuoloFirmaEnte = false;
                }
                this.reset();
                this.loadComplete = true;
                this.loadChoices();
                this.loadedTipiContratto = false;
                this.procedimentoService.getTipiContratto().finally(() => {
                    if (this.dettaglioRichiesta && this.tipiContratto) {
                        for (let contratto of this.dettaglioRichiesta.contratti) {
                            let utilizzo = new UtilizzoVO();
                            let tipoContratto: TipoContrattoVO = this.tipiContratto.find(a => a.id === contratto.idTipoContratto);
                            utilizzo.tipoContratto = tipoContratto;
                            utilizzo.contratto = contratto;
                            this.utilizzi.push(utilizzo);
                            this.getDatiContratto(utilizzo);
                            this.sortUtilizzi(this.utilizzi);
                            if (contratto.idTipoContratto === 1) {
                                this.tipiContratto.splice(this.tipiContratto.findIndex(a => a.id === 1), 1);
                            }

                        }
                    }
                }).subscribe(data => {
                    if (data) {
                        this.tipiContratto = data;
                        this.filteredOptionsTipiContratto = this.tipiContrattoGroup.controls['tipiContrattoForm'].valueChanges
                            .pipe(
                                startWith(''),
                                map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                                map(name => name ? this._filterTipiContratto(name) : this.tipiContratto.slice())
                            );
                    }
                    this.loadedTipiContratto = true;
                });
            }
        }, err => {
            if (err.status == 406) { //NON AUTORIZZATO O NON MODIFICABILE
                let azione2: string = "ricerca";
                if (this.azione.startsWith("messaggio")) {
                    azione2 = this.azione;
                }
                this.router.navigate(['/dettaglioRichiesta/' + this.id, { action: azione2 }]);
            }
            CommonsHandleException.authorizationError(err, this.router, '/dettaglioRichiesta/', this.id);
        });

        var interval = setInterval(() => {
            if (this.inputEl) {
                setTimeout(() => {
                    this.inputEl.nativeElement.focus();
                    this.inputEl.nativeElement.scrollIntoView();
                }, 0);
                clearInterval(interval)
            }
        }, 2000);
    }

    loadChoices() {
        this.loadedTipoProcedimento = false;
        let observTipoProc = this.procedimentoService.getTipoProcedimento(this.dettaglioRichiesta.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipoProcedimento = data;
            }
            this.loadedTipoProcedimento = true;
        }, err => {
            CommonsHandleException.handleBlockingError(err, this.router);
        });
        this.loadedVeicoli = false;
        this.autobusService.getVeicoliModifica(this.dettaglioRichiesta.id, this.dettaglioRichiesta.idTipoProcedimento).subscribe(data => {
            this.veicoloCheck = new Array<VeicoloVO>();
            this.veicoloSelected = new Array<VeicoloVO>();
            this.allegatiVeicoli = new Array<AllegatoVeicoloVO>();
            this.veicoli = new Array<VeicoloVO>();
            if (data) {
                this.veicoli = data;
                if (this.veicoli.length == 0) {
                    this.veicoloGroup.controls['veicoloForm'].markAsTouched();
                }
                for (let d of data) {
                    this.veicoloCheck.push(new VeicoloVO(d.idVariazAutobus, d.primoTelaio, d.descClasseAmbEuro, d.descTipoAllestimento, d.selected, d.allegati));
                }
                for (let v of this.dettaglioRichiesta.veicoli) {
                    v['selected'] = true;
                    if (this.veicoli.find(a => a.idVariazAutobus == v.idVariazAutobus)) {
                        this.veicoli.find(a => a.idVariazAutobus == v.idVariazAutobus).selected = true;
                    }
                    if (this.veicoloCheck.find(a => a.idVariazAutobus == v.idVariazAutobus)) {
                        this.veicoloCheck.find(a => a.idVariazAutobus == v.idVariazAutobus).selected = true;
                    }
                    this.veicoloSelected.push(v);
                    if (v.allegati != null && v.allegati.length > 0) {
                        this.haveAllegati = true;
                    }
                    this.veicoloGroup.controls['veicoloForm'].setValue(this.veicoloSelected);
                    for (let a of v.allegati) {
                        a.primoTelaioVeicolo = v.primoTelaio;
                        a.nTargaVeicolo = v.nTarga;
                        this.allegatiVeicoli.push(a);
                    }
                }
                this.filteredOptionsVeicolo = this.veicoloGroup.controls['veicoloForm'].valueChanges
                    .pipe(
                        startWith(''),
                        map((value: any) => (typeof value === 'string' || value == null) ? value : value.primoTelaio),
                        map(name => name ? this._filterVeicolo(name) : this.veicoli.slice())
                    );
            }
            this.loadedVeicoli = true;
        });

        this.loadedContratti = false;
        this.procedimentoService.getContrattiModifica(this.dettaglioRichiesta.id).subscribe(data => {
            if (data) {
                this.contratti = data;
                this.filteredOptionsContratto = this.contrattoGroup.controls['contrattoForm'].valueChanges
                    .pipe(
                        startWith(''),
                        map((value: any) => (typeof value === 'string' || value == null) ? value : value.codIdNazionale),
                        map(name => name ? this._filterContratto(name) : this.contratti.slice())
                    );
            }
            this.loadedContratti = true;
        });

        this.loadedTipiDoc = false;
        this.procedimentoService.getTipiDocumento(this.dettaglioRichiesta.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipiDocumento = data;
                this.filteredOptionsTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].valueChanges
                    .pipe(
                        startWith(''),
                        map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                        map(name => name ? this._filterTipoDocumento(name) : this.tipiDocumento.slice())
                    );
                this.arrayAllegatiFiltrati = new Array<number>();
                for (let allegato of this.dettaglioRichiesta.files) {
                    allegato.isFileUploaded = true;
                    this.arrayAllegatiFiltrati.push(allegato.tipoDocumento.id);
                }
                if (this.tipiDocumento.find(t => t.id == 7)) {
                    this.arrayAllegatiFiltrati.push(this.tipiDocumento.find(t => t.id == 7).id);
                }
                if (this.tipiDocumento.find(t => t.id == 8)) {
                    this.arrayAllegatiFiltrati.push(this.tipiDocumento.find(t => t.id == 8).id);
                }
                this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
            }
            this.loadedTipiDoc = true;

        });
        this.loadedTransizioniAutoma = false;
        this.procedimentoService.getTransizioniAutoma(this.dettaglioRichiesta.id, this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null).idStato).subscribe(data => {
            if (data) {
                this.transizioniAutoma = data;
                if (this.transizioniAutoma.length == 1) {
                    this.transizioneAutoma = this.transizioniAutoma[0];
                    if (this.transizioneGroup && this.transizioneGroup.controls['transizioneForm']) {
                        this.transizioneGroup.controls['transizioneForm'].setValue(this.transizioneAutoma);
                        this.transizioneGroup.controls['transizioneForm'].disable();
                    }
                }
                else {
                    let transizioneDefault = this.transizioniAutoma.find(t => t.flagDefault);
                    this.transizioneGroup.controls['transizioneForm'].setValue(transizioneDefault);
                    this.transizioneGroup.controls['transizioneForm'].enable();
                    this.transizioneAutoma = transizioneDefault;
                }
            }
            this.loadedTransizioniAutoma = true;
        });


        this.boolFirstLoad = true;
    }


    _filterVeicolo(descrizione: string): VeicoloVO[] {
        const filterValue = descrizione.toLowerCase();
        if (this.dettaglioRichiesta.idTipoProcedimento == 1) {
            return this.veicoli.filter(option => option.primoTelaio.toLowerCase().startsWith(filterValue));
        }
        else {
            return this.veicoli.filter(option => option.nTarga.toLowerCase().startsWith(filterValue));
        }
    }

    _filterContratto(descrizione: string): ContrattoProcVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.contratti.filter(option => option.codIdNazionale != null ? option.codIdNazionale.toLowerCase().startsWith(filterValue) : false);
    }

    _filterTipiContratto(descrizione: string): TipoContrattoVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.tipiContratto.filter(option => option.descrizione != null ? option.descrizione.toLowerCase().startsWith(filterValue) : false);
    }

    _filterTipoDocumento(descrizione: string): TipoDocumentoVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.tipiDocumento.filter(option => option.descrizione.toLowerCase().includes(filterValue));
    }

    optionClickedVeicolo(event: Event, comp: VeicoloVO) {
        event.stopPropagation();
        this.toggleSelectionVeicolo(comp);
    }

    toggleSelectionVeicolo(comp: VeicoloVO) {
        let v = this.veicoloCheck.find(a => a.idVariazAutobus == comp.idVariazAutobus);
        v.selected = !v.selected;
        comp.selected = v.selected;
        if (comp.selected) {
            this.veicoloSelected.push(comp);
            if (comp.allegati != null && comp.allegati.length > 0) {
                this.haveAllegati = true;
                for (let a of comp.allegati) {
                    a.primoTelaioVeicolo = comp.primoTelaio;
                    this.allegatiVeicoli.push(a);
                }
            }
        } else {
            while (this.allegatiVeicoli.findIndex(a => a.primoTelaioVeicolo == comp.primoTelaio) != -1) {
                let index = this.allegatiVeicoli.findIndex(a => a.primoTelaioVeicolo == comp.primoTelaio);
                this.allegatiVeicoli.splice(index, 1);
            }
            const i = this.veicoloSelected.findIndex(value => value.idVariazAutobus === comp.idVariazAutobus);
            this.veicoloSelected.splice(i, 1);
            this.haveAllegati = false;
            for (let v of this.veicoloSelected) {
                if (v.allegati != null && v.allegati.length > 0) {
                    this.haveAllegati = true;
                    break;
                }
            }
        }

        this.veicoloGroup.controls['veicoloForm'].setValue(this.veicoloSelected);
    }

    optionClickedContratto(event: Event, comp: ContrattoProcVO) {
        event.stopPropagation();
        this.toggleSelectionContratto(comp);
    }

    toggleSelectionContratto(comp: ContrattoProcVO) {
        comp.selected = !comp.selected;
        if (comp.selected) {
            this.contrattiSelected.push(comp);
        } else {
            const i = this.contrattiSelected.findIndex(value => value.idContratto === comp.idContratto);
            this.contrattiSelected.splice(i, 1);


        }

        this.contrattoGroup.controls['contrattoForm'].setValue(this.contrattiSelected);

    }

    addUtilizzo() {
        if (this.tipiContrattoSelected.value.id == 1) {
            let utilizzo = new UtilizzoVO();
            utilizzo.tipoContratto = this.tipiContrattoSelected.value;
            utilizzo.contratto = this.contrattoSelected.value;
            this.getDatiContratto(utilizzo);
            this.utilizzi.push(utilizzo);
            this.contrattoGroup.controls['contrattoForm'].setValue(null);
            this.contrattoSelected = new FormControl();
        } else {
            for (let c of this.contrattiSelected) {
                let utilizzo = new UtilizzoVO();
                utilizzo.tipoContratto = this.tipiContrattoSelected.value;
                utilizzo.contratto = c;
                this.getDatiContratto(utilizzo);
                //aggiungo l'utilizzo solo se non è già presente
                let i = this.utilizzi.findIndex(a => a.tipoContratto.id == utilizzo.tipoContratto.id && a.contratto.idContratto == utilizzo.contratto.idContratto);
                if (i == -1) {
                    this.utilizzi.push(utilizzo);
                }
                this.contratti[this.contratti.findIndex(a => a.idContratto == c.idContratto)].selected = false;
            }
            this.contrattiSelected = new Array<ContrattoProcVO>();
            this.contrattoGroup.controls['contrattoForm'].setValue(this.contrattiSelected);
        }
        this.tipiContratto.splice(this.tipiContratto.findIndex(a => a.id == this.tipiContrattoSelected.value.id), 1);
        this.tipiContrattoGroup.controls['tipiContrattoForm'].setValue(null);
        this.tipiContrattoGroup.controls['tipiContrattoForm'].markAsUntouched();
        this.contrattoGroup.controls['contrattoForm'].markAsUntouched();
        this.tipiContrattoSelected = new FormControl();
        this.sortUtilizzi(this.utilizzi);

    }

    sortUtilizzi(utilizzi: Array<UtilizzoVO>) {
        utilizzi.sort((a, b) => {
            if (a.tipoContratto.id < b.tipoContratto.id)
                return -1
            if (a.tipoContratto.id > b.tipoContratto.id)
                return 1
            if (a.tipoContratto.id == b.tipoContratto.id) {
                if (a.contratto.idContratto <= b.contratto.idContratto)
                    return -1
                if (a.contratto.idContratto > b.contratto.idContratto)
                    return 1
            }
        })

    }

    deleteUtilizzo(utilizzo: UtilizzoVO) {
        if (utilizzo.tipoContratto.id == 1) {
            this.tipiContratto.push(utilizzo.tipoContratto);
            this.utilizzi.splice(this.utilizzi.findIndex(a => a.tipoContratto.id == utilizzo.tipoContratto.id), 1);
            this.dettaglioRichiesta.contratti.splice(this.dettaglioRichiesta.contratti.findIndex(a => a.idTipoContratto == utilizzo.tipoContratto.id), 1);
        }
        else {
            let i = this.tipiContratto.findIndex(a => a.id == utilizzo.tipoContratto.id);
            if (i == -1) {
                this.tipiContratto.push(utilizzo.tipoContratto);
            }
            this.utilizzi.splice(this.utilizzi.findIndex(a => a.tipoContratto.id == utilizzo.tipoContratto.id && a.contratto.idContratto == utilizzo.contratto.idContratto), 1);
            this.dettaglioRichiesta.contratti.splice(this.dettaglioRichiesta.contratti.findIndex(a => a.idTipoContratto == utilizzo.tipoContratto.id && a.idContratto == utilizzo.contratto.idContratto), 1)
        }
        this.tipiContrattoGroup.controls['tipiContrattoForm'].updateValueAndValidity();
    }

    getDatiContratto(utilizzo: UtilizzoVO) {
        this.procedimentoService.getDatiContratto(utilizzo.contratto.idContratto, this.dettaglioRichiesta.id).subscribe(data => {
            if (data) {
                let datiContratto = new ContrattoProcDatiVO(data);
                utilizzo.datiContratto = datiContratto;
                if (this.funzionario.idAzienda != null && this.funzionario.idAzienda == datiContratto.esecTit.id) {
                    this.isFirmaVisible = true;
                }
                else {
                    this.isFirmaVisible = false;
                }
            }
        });
    }

    changeFirma() {
        if ((this.dettaglioRichiesta.nominativoFirma != null && this.dettaglioRichiesta.nominativoFirma != '') &&
            (this.dettaglioRichiesta.ruoloFirma != null && this.dettaglioRichiesta.ruoloFirma != '')) {
        } 
        if ((this.dettaglioRichiesta.nominativoFirmaEnte != null && this.dettaglioRichiesta.nominativoFirmaEnte != '') &&
            (this.dettaglioRichiesta.ruoloFirmaEnte != null && this.dettaglioRichiesta.ruoloFirmaEnte != '')) {
            this.disableNominativoRuoloFirmaEnte = false;
        } else {
            this.disableNominativoRuoloFirmaEnte = true;
        }

    }

    click(event: any, s: string) {
        if (s == 'Co') {
            this.contrattoSelected.setValue(event.option.value);
            this.contrattoGroup.controls['contrattoForm'].setValue(this.contrattoSelected.value);
        } else if (s == 'TipiCo') {
            this.tipiContrattoSelected.setValue(event.option.value);
        } else if (s == 'Tdproc') {
            this.tipoDocumentoSelected = event.option.value;
        } else if (s == 'Veicolo') {
            let comp = event.option.value;
            this.veicoloSelected.splice(0, 1);
            this.allegatiVeicoli = new Array<AllegatoVeicoloVO>();
            this.haveAllegati = false;
            this.veicoloSelected.push(comp);
            if (comp.allegati != null && comp.allegati.length > 0) {
                this.haveAllegati = true;
                for (let a of comp.allegati) {
                    a.primoTelaioVeicolo = comp.primoTelaio;
                    a.nTargaVeicolo = comp.nTarga;
                    this.allegatiVeicoli.push(a);
                }
            }
        }
    }

    check(s: string) {
        setTimeout(() => {
            if (s == 'Veicolo') {
                if (this.dettaglioRichiesta.idTipoProcedimento != 7) {
                    if (!this.veicoloSelected || this.veicoloSelected.length == 0) {
                        this.veicoloGroup.controls['veicoloForm'].setValue(null);
                    }
                    else if (this.veicoloGroup.controls['veicoloForm'] && this.veicoloGroup.controls['veicoloForm'].value !== this.displayFnVeicolo(this.veicoloSelected)) {
                        this.veicoloGroup.controls['veicoloForm'].setValue(this.veicoloSelected);
                    }
                } else if ((!this.veicoloSelected || this.veicoloSelected.length == 0)
                    || (this.veicoloGroup.controls['veicoloForm']
                        && this.veicoloGroup.controls['veicoloForm'].value !== this.displayFnVeicolo(this.veicoloSelected)
                        && this.veicoloGroup.controls['veicoloForm'].value !== this.veicoloSelected[0])) {
                    this.veicoloSelected = new Array<VeicoloVO>();
                    this.allegatiVeicoli = new Array<AllegatoVeicoloVO>();
                    this.veicoloGroup.controls['veicoloForm'].setValue(this.veicoloSelected);
                }
            }

            else if (s == 'Co') {
                if (!this.contrattoSelected
                    || (this.contrattoGroup.controls['contrattoForm']
                        && this.contrattoSelected.value !== this.contrattoGroup.controls['contrattoForm'].value
                    )) {
                    this.contrattoGroup.controls['contrattoForm'].setValue(null);
                    this.contrattoSelected = new FormControl();
                    this.isFirmaVisible = false;
                    this.dettaglioRichiesta.nominativoFirma = this.nominativoFirmaLoaded;
                    this.dettaglioRichiesta.ruoloFirma = this.ruoloFirmaLoaded;
                }
            }
            else if (s == 'TipiCo') {
                if (!this.tipiContrattoSelected || (this.tipiContrattoGroup.controls['tipiContrattoForm'] && this.tipiContrattoSelected.value !== this.tipiContrattoGroup.controls['tipiContrattoForm'].value)) {
                    this.tipiContrattoGroup.controls['tipiContrattoForm'].setValue(null);
                    this.tipiContrattoSelected = new FormControl();

                }
            }
            else if (s == 'Td') {
                if (!this.tipoDocumentoSelected || (this.tipoDocumentoGroup.controls['tipoDocumentoForm'] && this.tipoDocumentoSelected !== this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value)) {
                    this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
                    this.tipoDocumentoSelected = new FormControl();
                }
            }
        }, 200);
    }


    displayFn(a?: any): string | undefined {
        return a ? a.descrizione : undefined;
    }

    displayFnVeicolo = a => {
        let displayValue: string;
        if (Array.isArray(a)) {
            a.forEach((c, index) => {
                if (this.dettaglioRichiesta.idTipoProcedimento == 1) {
                    if (index === 0) {
                        displayValue = "Telaio: " + c.primoTelaio;
                    } else {
                        displayValue += '; ' + "Telaio: " + c.primoTelaio;
                    }
                } else {
                    if (index === 0) {
                        displayValue = "Targa: " + c.nTarga;
                    } else {
                        displayValue += '; ' + "Targa: " + c.nTarga;
                    }
                }
            });
        } else if (a != null && this.dettaglioRichiesta.idTipoProcedimento == 7) {
            displayValue = "Targa: " + a.nTarga;
        } else {
            displayValue = a;
        }
        return displayValue;
    }

    troncaCaratteri(str: string) {
        let varStr: String;
        if (str && str.toString()) {
            varStr = str.toString();
            if (varStr.length > 50) {
                let varSubStr: String = varStr.substr(0, 47);
                varStr = varSubStr.trim() + "...";
            }
        }

        return varStr != null ? varStr.toString() : null;
    }

    displayFnContratto = a => {
        let s: string = "";
        if (a != null) {
            s = "Cod. Ident. Naz: ";
            if (a.codIdNazionale != null) {
                s += a.codIdNazionale + "; ";
            }
            else {
                s += " - ; "
            }
            s = s + this.troncaCaratteri(a.descrizione);
        }
        return s;
    }

    displayFnContratti = (a: ContrattoProcVO[]) => {
        let displayValue: string;
        if (Array.isArray(a)) {
            a.forEach((c, index) => {
                if (index === 0) {
                    if (c != null) {
                        displayValue = "Cod. Ident. Naz: ";
                        if (c.codIdNazionale != null) {
                            displayValue += c.codIdNazionale + "; ";
                        }
                        else {
                            displayValue += " - ; "
                        }
                    }
                } else {
                    displayValue += '; ';
                    if (c != null) {
                        displayValue += "Cod. Ident. Naz: ";
                        if (c.codIdNazionale != null) {
                            displayValue += c.codIdNazionale + "; ";
                        }
                        else {
                            displayValue += " - ; "
                        }
                    }

                }

            });
        } else {
            displayValue = a;
        }
        return displayValue;
    }

    handleFileInput(files: Array<File>) {
        for (let f of files) {
            let allegato = new AllegatoProcVO();
            allegato.tipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value;
            this.arrayAllegatiFiltrati.push(allegato.tipoDocumento.id);
            allegato.note = this.noteFile;
            allegato.file = f;
            allegato.nomeFile = f.name;
            allegato.isFileUploaded = false;

            allegato.dataCaricamento = new Date();
            this.dettaglioRichiesta.files.push(allegato);
            this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
        }

        this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
        this.tipoDocumentoSelected = new FormControl();
        this.noteFile = '';
        this.fileInput.nativeElement.value = "";
    }

    handleFileInputTxt(file: File, s: string) {
        if (file.type !== 'text/plain') {
            if (s === "PREMESSE") {
                this.premesseFile.nativeElement.value = "";
            }
            if (s === "PRESCRIZIONI") {
                this.prescrizioniFile.nativeElement.value = "";
            }
            this.dialog.open(DialogComponent, {
                height: '200px',
                width: '400px',
                data: {
                    msg: 'Formato file non consentito; selezionare un file di testo.',
                }
            });
        } else {
            let blob = new Blob([file], { type: 'text/plain' });
            let reader = new FileReader();
            reader.readAsText(blob, 'CP1252');
            reader.onload = a => {
                if (s === "PREMESSE") {
                    this.dettaglioRichiesta.premesse = reader.result.toString();
                    this.dettaglioRichiestaForm.form.get('premesse').markAsTouched();
                }
                if (s === "PRESCRIZIONI") {
                    this.dettaglioRichiesta.prescrizioni = reader.result.toString();
                    this.dettaglioRichiestaForm.form.get('prescrizioni').markAsTouched();
                }
            };
        }
    }

    download(allegato: AllegatoVeicoloVO) {
        this.downloadPDF = true;
        let idVeicolo = this.dettaglioRichiesta.veicoli.find(v => v.primoTelaio == allegato.primoTelaioVeicolo).idVariazAutobus;
        this.documentService.getContenutoDocumentoById(idVeicolo, allegato.tipoDocumento.id)
            .subscribe(
                res => {
                    this.downloadPDF = false;
                    saveAs(res, allegato.nomeFile);
                },
                err => {
                    this.downloadPDF = false;
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
    }

    downloadRichiesta(idRichiesta: number, allegato: AllegatoProcVO) {
        this.downloadPDF = true;
        this.documentService.getContenutoDocumentoByIdProcedimento(idRichiesta, allegato.tipoDocumento.id)
            .subscribe(
                res => {
                    this.downloadPDF = false;
                    saveAs(res, allegato.nomeFile);
                },
                err => {
                    this.downloadPDF = false;
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
    }

    downloadRichiestaNotDb(allegato: AllegatoProcVO) {
        saveAs(allegato.file, allegato.nomeFile);
    }

    eliminaAllegatoProcedimentoDb(idRichiesta: number, allegato: AllegatoProcVO) {
        let idTipoDocumento = allegato.tipoDocumento.id;
        this.documentService.eliminaAllegatoProcedimentoDb(idRichiesta, allegato.tipoDocumento.id)
            .subscribe(
                res => {
                    this.snackBar.open("Documento eliminato con successo!", "Chiudi", {
                        duration: 2000,
                    });
                    this.dettaglioRichiesta.files.splice(this.dettaglioRichiesta.files.findIndex(a => a.tipoDocumento.id == idTipoDocumento), 1);
                    this.arrayAllegatiFiltrati.splice(this.arrayAllegatiFiltrati.findIndex(a => a == idTipoDocumento), 1);
                    this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
                },
                err => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
    }


    elimina(idTipoDocumento: number) {
        this.dettaglioRichiesta.files.splice(this.dettaglioRichiesta.files.findIndex(a => a.tipoDocumento.id == idTipoDocumento), 1);
        this.arrayAllegatiFiltrati.splice(this.arrayAllegatiFiltrati.findIndex(a => a == idTipoDocumento), 1);
        this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
    }



    isValidCampiRequired() {
        if (this.dettaglioRichiestaForm && this.dettaglioRichiestaForm.form) {
            if (this.veicoloGroup.controls['veicoloForm'] && this.veicoloGroup.controls['veicoloForm'].hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('noteMotivazione') && this.dettaglioRichiestaForm.form.get('noteMotivazione').hasError('required')) return true;
            //if (this.contrattoGroup.controls['contrattoForm'] && this.contrattoGroup.controls['contrattoForm'].hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('premesse') && this.dettaglioRichiestaForm.form.get('premesse').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('prescrizioni') && this.dettaglioRichiestaForm.form.get('prescrizioni').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte') && this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte') && this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('ruoloFirma') && this.dettaglioRichiestaForm.form.get('ruoloFirma').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('nominativoFirma') && this.dettaglioRichiestaForm.form.get('nominativoFirma').hasError('required')) return true;
            if (!this.utilizzi || this.utilizzi.length == 0) return true;
        }
        return false;
    }

    isCampiInvalid() {
        return false;
    }


    reset() {
        this.veicoloGroup.controls['veicoloForm'].setValue(null);
        this.veicoloSelected = new Array<VeicoloVO>();
        this.contrattiSelected = new Array<ContrattoProcVO>();
        this.contrattoGroup.controls['contrattoForm'].setValue(null);
        this.contrattoSelected = new FormControl();
        this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
        this.tipoDocumentoSelected = new FormControl();
        this.noteFile = "";
        this.transizioneAutoma = new TransizioneAutomaVO()
        this.transizioneGroup.controls['transizioneForm'].setValue(null);
        this.transizioneSelected = new FormControl();
        this.notaTransizione = "";
        this.allegatiVeicoli = new Array<AllegatoVeicoloVO>();
        this.allegati = new Array<AllegatoProcVO>();
    }

    isAvanzaIterDisabled() {
        if ((this.transizioneGroup && this.transizioneGroup.controls['transizioneForm'] && this.transizioneGroup.controls['transizioneForm'].hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('noteAvanzamentoIter') != null && this.dettaglioRichiestaForm.form.get('noteAvanzamentoIter').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte') != null && this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte') != null && this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('premesse') != null && this.dettaglioRichiestaForm.form.get('premesse').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('prescrizioni') != null && this.dettaglioRichiestaForm.form.get('prescrizioni').hasError('required'))
            || ((this.isFirmaVisible || this.dettaglioRichiesta.iters[0].idStato >= 30) && ((this.dettaglioRichiestaForm.form.get('nominativoFirma') && (this.dettaglioRichiestaForm.form.get('nominativoFirma').hasError('required'))) || (this.dettaglioRichiestaForm.form.get('ruoloFirma') && this.dettaglioRichiestaForm.form.get('ruoloFirma').hasError('required'))) && this.transizioneAutoma.idTransizioneAutoma === this.transizioniAutoma[0].idTransizioneAutoma) //&& this.disableNominativoRuoloFirma
            || (this.dettaglioRichiesta.isACaricoEnteVisible && (this.disableNominativoRuoloFirmaEnte))) {
            return true;
        }
        return false;
    }

    avanzaIterRichiesta() {
        this.loadedAvanzaIter = false;
        this.procedimentoService.avanzaIterRichiesta(this.dettaglioRichiesta, this.transizioneAutoma, this.notaTransizione).subscribe(data => {
            this.loadedAvanzaIter = true;
            if (data) {
                window.scrollTo(0, 0);
                this.transizioneGroup.controls['transizioneForm'].reset();
                this.transizioneSelected = new FormControl();
                this.snackBar.open("Operazione avvenuta con successo!", "Chiudi", {
                    duration: 2000,
                });

                setTimeout(() => {
                    if (this.transizioneAutoma.idStatoIterA == 10 || this.transizioneAutoma.idStatoIterA == 20) {
                        this.loadData();
                    } else {
                        let azione2: string = "ricerca";
                        if (this.azione.startsWith("messaggio")) {
                            azione2 = this.azione;
                        }
                        this.router.navigate(['/dettaglioRichiestaUsoInLinea/' + this.id, { action: azione2 }]);
                    }
                }, 2000);

            }
        }, err => {
            let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
            if (errorRest.type == TypeErrorRest.OK) {
                this.feedback = errorRest.message;
            }
            else {
                this.feedback = "Si è verificato un errore in fase di salvataggio";
            }
            console.log("this.feedback =" + this.feedback);
            window.scrollTo(0, 0);
            this.loadedAvanzaIter = true;
        });
    }

    calledSave: boolean;
    save(isAnteprimaPDF: boolean, isAvanzaIter: boolean) {
        this.calledSave = true;
        this.dettaglioRichiesta.veicoli = this.veicoloSelected;
        for (let utilizzo of this.utilizzi) {
            let contratto = this.dettaglioRichiesta.contratti.find(a => a.idTipoContratto == utilizzo.tipoContratto.id && a.idContratto == utilizzo.contratto.idContratto);
            if (!contratto) {
                this.dettaglioRichiesta.contratti.push(utilizzo.contratto);
            }
        }
        this.loadedSave = false;
        this.procedimentoService.modificaRichiesta(this.dettaglioRichiesta).subscribe(data => {
            this.loadedSave = true;
            this.feedback = "";
            let id: number = data;
            window.scrollTo(0, 0);
            this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
                duration: 2000,
            });
            if (isAnteprimaPDF) {
                this.downloadAnteprimaPdf();
            }
            if (isAvanzaIter) {
                this.avanzaIterRichiesta();
            }

        }, error => {
            CommonsHandleException.authorizationError(error, this.router);
            this.feedback = error.error.message;
            console.log("this.feedback =" + this.feedback);
            window.scrollTo(0, 0);
            this.loadedSave = true;
        });
    }

    openDialogAnteprimaPdf() {
        if (this.iter.idStato != 20) {
            let dialogRef = this.dialog.open(CancellaDialogComponent, {
                height: '200px',
                width: '400px',
                data: { msg: 'Eventuali modifiche apportate verranno salvate; si vuole procedere?' }
            });
            dialogRef.afterClosed().subscribe(result => {
                if (result == 'OK') {
                    this.save(true, false);
                }
            });
        }
        else {
            this.downloadAnteprimaPdf();
        }
    }

    openDialogAvanzaIter() {
        if (this.iter.idStato == 10 || this.iter.idStato == 50) {
            let dialogRef = this.dialog.open(CancellaDialogComponent, {
                height: '200px',
                width: '400px',
                data: { msg: 'Eventuali modifiche apportate verranno salvate; si vuole procedere?' }
            });
            dialogRef.afterClosed().subscribe(result => {
                if (result == 'OK') {
                    this.save(false, true);
                }
            });
        }
        else {
            this.avanzaIterRichiesta();
        }
    }

    downloadAnteprimaPdf() {
        this.loadedSave = false;
        let numProgressivo = this.dettaglioRichiesta.numProgressivo.replace(/[\/]/g, '');
        this.documentService.getContenutoAnteprimaPdf(this.dettaglioRichiesta.id, this.iter.idStato, this.dettaglioRichiesta.idTipoProcedimento)
            .subscribe(
                res => {
                    saveAs(res, "PROC_" + numProgressivo + "_Bozza.pdf");
                    this.loadedSave = true;
                },
                error => {
                    CommonsHandleException.authorizationError(error, this.router);
                    this.feedback = error.error.message;
                    console.log("this.feedback =" + this.feedback);
                    window.scrollTo(0, 0);
                    this.loadedSave = true;
                }
            );
    }

    isLoading() {
        if (!this.loadComplete ||
            !this.loadedTipoProcedimento ||
            !this.loadedVeicoli ||
            !this.loadedTipiDoc ||
            !this.loadedContratti ||
            !this.loadedTipiContratto ||
            !this.loadedSave ||
            !this.loadedDettaglioRichiestaFirma ||
            !this.loadedTransizioniAutoma ||
            !this.loadedAvanzaIter) return true;
        return false;
    }

    goBack() {
        if (this.azione === 'inserisci') {
            this.router.navigate(['/inserisciRichiestaUsoInLinea/', this.dettaglioRichiesta.idTipoProcedimento]);
        } else if (this.azione === 'ricerca') {
            this.router.navigate(['/ricercaProcedimenti']);
        } else if (this.azione.startsWith("messaggio")) {
            let array: string[] = this.azione.split('_');
            let idMessaggio: number = +array[1];
            let idElencoMessaggi: number = +array[2];
            this.router.navigate(['/dettaglioMessaggio/' + idMessaggio, { action: idElencoMessaggi }]);
        }
    }

    allDisabled: boolean;
    allEnabled: boolean;
    iter: IterProcedimentoVO;

    ngAfterContentChecked() {
        if (this.dettaglioRichiesta && this.dettaglioRichiesta.iters && !this.richiestaConfermata) {
            this.iter = this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null);
            if (this.iter && this.iter.idStato != 10 && this.iter.idStato != 50 && !this.allDisabled) { //NO BOZZA E NO DA REVISIONARE
                if (this.veicoloGroup.controls["veicoloForm"]) { this.veicoloGroup.controls["veicoloForm"].disable(); }
                if (this.tipoDocumentoGroup.controls["tipoDocumentoForm"]) { this.tipoDocumentoGroup.controls["tipoDocumentoForm"].disable(); }
                if ((<HTMLInputElement>document.getElementById("salva")) != null) { (<HTMLInputElement>document.getElementById("salva")).disabled = true; }
                if (this.dettaglioRichiestaForm.form.get("noteMotivazione")) { this.dettaglioRichiestaForm.form.get("noteMotivazione").disable(); }
                if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].disable(); }
                if (this.tipiContrattoGroup.controls['tipiContrattoForm']) { this.tipiContrattoGroup.controls['tipiContrattoForm'].disable(); }

                this.allDisabled = true;
            } else if (this.iter && (this.iter.idStato == 10 || this.iter.idStato == 50) && !this.allEnabled) {
                if (this.veicoloGroup.controls["veicoloForm"]) { this.veicoloGroup.controls["veicoloForm"].enable(); }
                if (this.tipoDocumentoGroup.controls["tipoDocumentoForm"]) { this.tipoDocumentoGroup.controls["tipoDocumentoForm"].enable(); }
                if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].enable(); }
                if (this.tipiContrattoGroup.controls['tipiContrattoForm']) { this.tipiContrattoGroup.controls['tipiContrattoForm'].enable(); }

                this.allEnabled = true;
            }
        }

    }

    ngAfterViewChecked() {
        this.changeDetector.detectChanges();
    }



    ngOnDestroy(): void {

    }

}

export enum Action {
    EDIT = "E",
    VIEW = "V",
}