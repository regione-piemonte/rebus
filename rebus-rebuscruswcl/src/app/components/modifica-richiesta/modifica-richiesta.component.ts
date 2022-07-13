/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, ElementRef, Input, Output, EventEmitter, ChangeDetectorRef } from "@angular/core";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { NgForm, FormGroup, FormControl, FormBuilder, Validators } from "@angular/forms";
import { ProcedimentoService } from "../../services/procedimento.service";
import { TipoProcedimentoVO, MotorizzazioneVO, MotivazioneVO, TipoDocumentoVO, TipoContrattoVO, VoceDiCostoVO } from "../../vo/extend-vo";
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
import { VoceDiCostoVeicoloVO } from "../../vo/voci-di-costo-veicolo-vo";
import { UserService } from "../../services/user.service";
import { UserInfo } from "../../vo/funzionario-vo";
import { CancellaDialogComponent } from "../cancelladialog/cancelladialog.component";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";

@Component({
    selector: 'app-modifica-richiesta',
    templateUrl: './modifica-richiesta.component.html',
    styleUrls: ['./modifica-richiesta.component.scss']
})
@DestroySubscribers()
export class ModificaRichiestaComponent implements OnInit {

    feedback: string;

    id: number;
    azione: string;
    dettaglioRichiesta: DettaglioRichiestaVO;
    funzionario: UserInfo;
    tipoProcedimento: TipoProcedimentoVO;
    veicoli: Array<VeicoloVO>;
    motorizzazioni: Array<MotorizzazioneVO>;
    motivazioni: Array<MotivazioneVO>;
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
    vociDiCosto: Array<VoceDiCostoVO>;
    vociDiCostoFiltrate: Array<VoceDiCostoVO>;
    voce: VoceDiCostoVO = new VoceDiCostoVO();
    importoVoce: number;
    boolDisabledContrattoMotorizzazione: boolean;
    boolFirstLoad: boolean;
    datiContratto: ContrattoProcDatiVO;
    enteComm: string;
    esecTit: string;
    capofila: string;
    soggettoRichiedente: string;
    soggettoIntermediario: string;
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
    downloadPdf: boolean;

    motorizzazioneGroup: FormGroup = new FormGroup({ motorizzazioneForm: new FormControl() });
    motorizzazioneSelected = new FormControl();
    filteredOptionsMotorizzazione: Observable<MotorizzazioneVO[]>;

    motivazioneGroup: FormGroup = new FormGroup({ motivazioneForm: new FormControl() });
    motivazioneSelected = new FormControl();
    filteredOptionsMotivazione: Observable<MotivazioneVO[]>;

    importoVoceGroup = new FormGroup({});

    contrattoGroup: FormGroup = new FormGroup({ contrattoForm: new FormControl() });
    contrattoSelected = new FormControl();
    filteredOptionsContratto: Observable<ContrattoProcVO[]>;

    tipoDocumentoSelected = new FormControl();
    filteredOptionsTipoDocumento: Observable<TipoDocumentoVO[]>;
    tipoDocumentoGroup: FormGroup = new FormGroup({ tipoDocumentoForm: new FormControl() });

    transizioneGroup: FormGroup = new FormGroup({ transizioneForm: new FormControl() });
    transizioneSelected = new FormControl();

    loadedTipoProcedimento: boolean;
    loadedVeicoli: boolean;
    loadedMotorizzazioni: boolean;
    loadedMotivazioni: boolean;
    loadedContratti: boolean;
    loadedTipiContratto: boolean;
    loadedTipiDoc: boolean;
    loadedSave: boolean = true;
    loadComplete: boolean;
    loadedTransizioniAutoma: boolean;
    loadedAvanzaIter: boolean = true;
    loadedVociDiCosto: boolean;
    loadedDatiContratto: boolean = true;

    isContrattoChanged: boolean = false;

    richiestaConfermata: boolean;
    isNotSelectedFirma: boolean;

    dataToday: Date = new Date();

    codiceEnte: number;
    visualizza: boolean = false;

    @ViewChild('dettaglioRichiestaForm') dettaglioRichiestaForm: NgForm;
    @ViewChild('fileInput') fileInput: ElementRef;
    @ViewChild('premesseFile') premesseFile: ElementRef;
    @ViewChild('prescrizioniFile') prescrizioniFile: ElementRef;
    @ViewChild('avanzaIter') inputEl: ElementRef;

    @Input('filtroRicerca') filtroRicerca: FiltroProcedimentiVO;
    @Output('isRicerca') isRicerca = new EventEmitter<boolean>();

    @Input() idProcedimento: number;
    @Output() outputToParent = new EventEmitter<DettaglioRichiestaVO>();
    @Output() idTipoProcedimentoEvent = new EventEmitter<number>();
    @Output() outputContrattoSelected = new EventEmitter<FormControl>();
    @Output() outputToParentPulisci = new EventEmitter<DettaglioRichiestaVO>();
    @Input() contrattoSelectedOther = new FormControl();
    @Output() outputMotorizzazioneSelected = new EventEmitter<FormControl>();
    @Input() motorizzazioneSelectedOther = new FormControl();
    @Input() disableTipoContratto: boolean;
    @Output() outputDisableTipoContratto = new EventEmitter<boolean>();
    @Output() outputTipoContrattoSelected = new EventEmitter<FormControl>();
    @Input() tipoContrattoSelectedOther;
    @Output() outputMotivazioneSelected = new EventEmitter<FormControl>();
    @Input() motivazioneSelectedOther = new FormControl();
    context: string;
    @Output() outputNotaMotivazioneSelected = new EventEmitter<FormControl>();
    @Input() notaMotivazioneSelectedOther;


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
    ) {
        this.importoVoceGroup = fb.group({
            'importoVoceForm': ['', [Validators.min(0.01)]]
        });
    }

    ngOnInit(): void {
        this.dettaglioRichiesta = new DettaglioRichiestaVO();
        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
            this.context = this.navbarFilterContext.InfoFiltro.cod;
        }
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.codiceEnte = data.idEnte;
        });
        if (this.idProcedimento == null) {
            window.scroll(0, 0);
            this.azione = this.route.snapshot.paramMap.get('action');
            this.route.params.subscribe(params => {
                this.id = +params['id']; // (+) converts string 'id' to a number

                this.loadData();
            });
        } else {
            this.id = this.idProcedimento;
            this.loadData();
        }
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

                if ((this.dettaglioRichiesta.nominativoFirma != null && this.dettaglioRichiesta.nominativoFirma != '') ||
                    (this.dettaglioRichiesta.ruoloFirma != null && this.dettaglioRichiesta.ruoloFirma != '')) {
                    //this.disableNominativoRuoloFirma = false;
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
            }
        }, err => {
            if (err.status == 406) { //NON AUTORIZZATO O NON MODIFICABILE
                if (this.idProcedimento == null) {
                    let azione2: string = "ricerca";
                    if (this.azione.startsWith("messaggio")) {
                        azione2 = this.azione;
                    }
                    this.router.navigate(['/dettaglioRichiesta/' + this.id, { action: azione2 }]);
                }
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
                    this.veicoloCheck.push(new VeicoloVO(d.idVariazAutobus, d.primoTelaio, d.descClasseAmbEuro, d.descTipoAllestimento, d.selected, d.allegati, d.documento, d.idProcedimento));
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
        if (this.dettaglioRichiesta.idTipoProcedimento != 7 && this.idProcedimento == null) { //CONTRIBUZIONE
            this.loadedMotorizzazioni = false;
            this.procedimentoService.getMotorizzazioni().subscribe(data => {
                if (data) {
                    this.motorizzazioni = data;
                    this.filteredOptionsMotorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].valueChanges
                        .pipe(
                            startWith(''),
                            map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                            map(name => name ? this._filterMotorizzazione(name) : this.motorizzazioni.slice())
                        );
                    if (!this.motorizzazioneSelected.value) {
                        this.motorizzazioneSelected.setValue(this.motorizzazioni.find(m => m.id == this.dettaglioRichiesta.idMotorizzazione));
                    }
                    this.motorizzazioneGroup.controls['motorizzazioneForm'].setValue(this.motorizzazioni.find(m => m.id == this.dettaglioRichiesta.idMotorizzazione));
                }
                this.loadedMotorizzazioni = true;
            });
            this.loadedMotivazioni = false;
            let idProcedimentoMotivazioni = this.dettaglioRichiesta.idTipoProcedimento;
            if (this.idProcedimento != null) {
                idProcedimentoMotivazioni = 2;
            }
            let idIterCorrente = this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null).idStato;
            this.procedimentoService.getMotivazioni(idProcedimentoMotivazioni, idIterCorrente === 10 || idIterCorrente === 50).subscribe(data => {
                if (data) {
                    this.motivazioni = data;
                    if (this.motivazioni.length == 1) {
                        this.dettaglioRichiesta.idMotivazione = this.motivazioni[0].id;
                        this.motivazioneSelected.setValue(this.motivazioni[0]);
                        this.motivazioneGroup.controls['motivazioneForm'].disable();
                    }
                    this.filteredOptionsMotivazione = this.motivazioneGroup.controls['motivazioneForm'].valueChanges
                        .pipe(
                            startWith(''),
                            map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                            map(name => name ? this._filterMotivazione(name) : this.motivazioni.slice())
                        );
                    if (!this.motivazioneSelected.value) {
                        this.motivazioneSelected.setValue(this.motivazioni.find(m => m.id == this.dettaglioRichiesta.idMotivazione));
                    }
                    this.motivazioneGroup.controls['motivazioneForm'].setValue(this.motivazioni.find(m => m.id == this.dettaglioRichiesta.idMotivazione));
                }
                this.loadedMotivazioni = true;
            });
            this.loadedContratti = false;
            this.procedimentoService.getContrattiModifica(this.dettaglioRichiesta.id).subscribe(data => {
                if (data) {
                    this.isContrattoChanged = false;
                    this.contratti = data;
                    this.filteredOptionsContratto = this.contrattoGroup.controls['contrattoForm'].valueChanges
                        .pipe(
                            startWith(''),
                            map((value: any) => (typeof value === 'string' || value == null) ? value : value.codIdNazionale),
                            map(name => name ? this._filterContratto(name) : this.contratti.slice())
                        );
                    if (!this.contrattoSelected.value) {
                        this.contrattoSelected.setValue(this.contratti.find(c => c.idContratto == this.dettaglioRichiesta.contratto.idContratto));
                    }
                    this.contrattoGroup.controls['contrattoForm'].setValue(this.contratti.find(c => c.idContratto == this.dettaglioRichiesta.contratto.idContratto));
                    if (this.idProcedimento == null) {
                        this.loadedDatiContratto = false;
                        this.procedimentoService.getDatiContratto(this.dettaglioRichiesta.contratto.idContratto, this.id).subscribe(data => {
                            if (data) {

                                this.datiContratto = data;

                                this.loadedDettaglioRichiestaFirma = false;
                                this.procedimentoService.getFirmaProcedimento().subscribe(data => {
                                    if (data) {
                                        this.dettaglioRichiestaFirma = data;
                                        if (this.funzionario.idAzienda != null) {
                                            if (this.datiContratto.soggIntermediario && this.funzionario.idAzienda == this.datiContratto.soggIntermediario.id) {
                                                if (!this.dettaglioRichiesta.nominativoFirma || !this.dettaglioRichiesta.ruoloFirma) {
                                                    this.dettaglioRichiesta.nominativoFirma = this.dettaglioRichiestaFirma.nominativoFirma;
                                                    this.dettaglioRichiesta.ruoloFirma = this.dettaglioRichiestaFirma.ruoloFirma;
                                                }
                                                this.isFirmaVisible = true;
                                            } else if (!this.datiContratto.soggIntermediario && this.funzionario.idAzienda == this.datiContratto.esecTit.id) {
                                                this.isFirmaVisible = true;
                                                if (!this.dettaglioRichiesta.nominativoFirma || !this.dettaglioRichiesta.ruoloFirma) {
                                                    this.dettaglioRichiesta.nominativoFirma = this.dettaglioRichiestaFirma.nominativoFirma;
                                                    this.dettaglioRichiesta.ruoloFirma = this.dettaglioRichiestaFirma.ruoloFirma;
                                                }
                                            } else {
                                                this.isFirmaVisible = false;
                                            }
                                        }
                                        else {
                                            this.isFirmaVisible = false;
                                        }
                                    }
                                    this.loadedDettaglioRichiestaFirma = true;
                                });

                                this.enteComm = this.datiContratto.enteComm.codiceOss + " - " + this.datiContratto.enteComm.denomBreve;

                                if (this.codiceEnte != null) {
                                    if (this.datiContratto.enteComm.id == this.codiceEnte)
                                        this.visualizza = true;
                                }

                                this.esecTit = this.datiContratto.esecTit.codiceOss + " - " + this.datiContratto.esecTit.denomBreve;
                                if (this.datiContratto.idTipoRaggruppamento) {
                                    this.esecTit += " (" + this.datiContratto.descTipoRaggruppamento + ")";
                                }
                                this.soggettoRichiedente = this.datiContratto.soggRichiedente.codiceOss + " - " + this.datiContratto.soggRichiedente.denomBreve;
                                if (this.datiContratto.capofila) {
                                    this.capofila = this.datiContratto.capofila.codiceOss + " - " + this.datiContratto.capofila.denomBreve;
                                }
                                if (this.datiContratto.soggIntermediario) {
                                    this.soggettoIntermediario = this.datiContratto.soggIntermediario.codiceOss + " - " + this.datiContratto.soggIntermediario.denomBreve;
                                }
                            }
                            this.loadedDatiContratto = true;
                        });
                    }
                }
                this.loadedContratti = true;
            });
        }
        if (this.dettaglioRichiesta.idTipoProcedimento != 1) { // no prima immatricolazione
            this.loadedTipiContratto = false;
            this.procedimentoService.getTipiContratto().subscribe(data => {
                if (data) {
                    this.tipiContratto = data;
                }
                this.loadedTipiContratto = true;
            });
        }
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
        this.loadTransizioneAutoma();
        if (this.dettaglioRichiesta.idTipoProcedimento == 7) {
            this.loadedVociDiCosto = false;
            this.procedimentoService.getVociDiCosto().subscribe(data => {
                if (data) {
                    this.vociDiCosto = data;
                    this.vociDiCostoFiltrate = new Array<VoceDiCostoVO>();
                    for (let d of data) {
                        let v = new VoceDiCostoVO();
                        v.id = d.id;
                        v.descrizione = d.descrizione;
                        this.vociDiCostoFiltrate.push(v);
                    }
                    for (let voce of this.dettaglioRichiesta.vociDiCostoVeicolo) {
                        voce.descrizioneVoceCosto = this.vociDiCosto.find(v => v.id == voce.idVoceCosto).descrizione;
                        this.vociDiCostoFiltrate.splice(this.vociDiCostoFiltrate.findIndex(v => v.id == voce.idVoceCosto), 1);
                    }
                }
                this.loadedVociDiCosto = true;
            });
        }

        this.boolFirstLoad = true;
        //serve per confermare al caricamento della pagina i sottoprocedimenti per le sostituzioni 
        var interval = setInterval(() => {
            if (this.loadedTipoProcedimento && this.loadedVeicoli && this.loadedTipiDoc && this.loadedTransizioniAutoma) {
                if (this.idProcedimento != null) {
                    this.save(false, false);
                    clearInterval(interval);
                } else {
                    clearInterval(interval);
                }
            }
        }, 2000);
    }

    loadTransizioneAutoma() {
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
    }


    private _filterVeicolo(descrizione: string): VeicoloVO[] {
        const filterValue = descrizione.toLowerCase();
        if (this.dettaglioRichiesta.idTipoProcedimento == 1) {
            return this.veicoli.filter(option => option.primoTelaio.toLowerCase().startsWith(filterValue));
        }
        else {
            return this.veicoli.filter(option => option.nTarga.toLowerCase().startsWith(filterValue));
        }
    }

    private _filterMotorizzazione(descrizione: string): MotorizzazioneVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.motorizzazioni.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
    }

    private _filterMotivazione(descrizione: string): MotivazioneVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.motivazioni.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
    }

    private _filterContratto(descrizione: string): ContrattoProcVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.contratti.filter(option => option.codIdNazionale != null ? option.codIdNazionale.toLowerCase().startsWith(filterValue) : false);
    }

    private _filterTipoDocumento(descrizione: string): TipoDocumentoVO[] {
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
        if (s == 'Motor') {
            this.motorizzazioneSelected.setValue(event.option.value);
        } else if (s == 'Motiv') {
            this.motivazioneSelected.setValue(event.option.value);
            if (!event.option.value.flgMotivAltro) {
                this.dettaglioRichiestaForm.form.get("noteMotivazione").setValue(null);
            }
        } else if (s == 'Co') {
            this.contrattoSelected.setValue(event.option.value);
            this.contrattoGroup.controls['contrattoForm'].setValue(this.contrattoSelected.value);
            this.capofila = null;
            this.soggettoIntermediario = null;
            this.procedimentoService.getDatiContratto(event.option.value.idContratto, this.id).subscribe(data => {
                if (data) {
                    this.datiContratto = data;
                    if (this.funzionario.idAzienda != null) {
                        if (this.datiContratto.soggIntermediario && this.funzionario.idAzienda == this.datiContratto.soggIntermediario.id) {
                            this.isFirmaVisible = true;
                            if (!this.dettaglioRichiesta.nominativoFirma || !this.dettaglioRichiesta.ruoloFirma) {
                                this.dettaglioRichiesta.nominativoFirma = this.dettaglioRichiestaFirma.nominativoFirma;
                                this.dettaglioRichiesta.ruoloFirma = this.dettaglioRichiestaFirma.ruoloFirma;
                            }
                        } else if (!this.datiContratto.soggIntermediario && this.funzionario.idAzienda == this.datiContratto.esecTit.id) {
                            this.isFirmaVisible = true;
                            if (!this.dettaglioRichiesta.nominativoFirma || !this.dettaglioRichiesta.ruoloFirma) {
                                this.dettaglioRichiesta.nominativoFirma = this.dettaglioRichiestaFirma.nominativoFirma;
                                this.dettaglioRichiesta.ruoloFirma = this.dettaglioRichiestaFirma.ruoloFirma;
                            }
                        } else {
                            this.isFirmaVisible = false;
                            this.dettaglioRichiesta.nominativoFirma = null;
                            this.dettaglioRichiesta.ruoloFirma = null;
                        }
                    }
                    else {
                        this.isFirmaVisible = false;
                        this.dettaglioRichiesta.nominativoFirma = null;
                        this.dettaglioRichiesta.ruoloFirma = null;
                    }
                    this.enteComm = this.datiContratto.enteComm.codiceOss + " - " + this.datiContratto.enteComm.denomBreve;
                    this.esecTit = this.datiContratto.esecTit.codiceOss + " - " + this.datiContratto.esecTit.denomBreve;
                    this.soggettoRichiedente = this.datiContratto.soggRichiedente.codiceOss + " - " + this.datiContratto.soggRichiedente.denomBreve;
                    if (this.datiContratto.capofila) {
                        this.capofila = this.datiContratto.capofila.codiceOss + " - " + this.datiContratto.capofila.denomBreve;
                    }
                    if (this.datiContratto.soggIntermediario) {
                        this.soggettoIntermediario = this.datiContratto.soggIntermediario.codiceOss + " - " + this.datiContratto.soggIntermediario.denomBreve;
                    }
                }
            });
            if (this.dettaglioRichiesta.contratto.idContratto != this.contrattoSelected.value.idContratto) {
                this.isContrattoChanged = true;
            } else {
                this.isContrattoChanged = false;
            }
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
            else if (s == 'Motor') {
                if (!this.motorizzazioneSelected
                    || (this.motorizzazioneGroup.controls['motorizzazioneForm']
                        && this.motorizzazioneSelected.value !== this.motorizzazioneGroup.controls['motorizzazioneForm'].value
                        && this.motorizzazioneGroup.controls['motorizzazioneForm'].value !== this.motorizzazioni.find(m => m.id == this.dettaglioRichiesta.idMotorizzazione))) {
                    this.motorizzazioneGroup.controls['motorizzazioneForm'].setValue(null);
                    this.motorizzazioneSelected = new FormControl();
                }
            }
            else if (s == 'Motiv') {
                if (!this.motivazioneSelected
                    || (this.motivazioneGroup.controls['motivazioneForm']
                        && this.motivazioneSelected.value !== this.motivazioneGroup.controls['motivazioneForm'].value
                        && this.motivazioneGroup.controls['motivazioneForm'].value !== this.motivazioni.find(m => m.id == this.dettaglioRichiesta.idMotivazione))) {
                    this.motivazioneGroup.controls['motivazioneForm'].setValue(null);
                    this.motivazioneSelected = new FormControl();
                    this.dettaglioRichiesta.noteMotivazione = '';
                }
            }
            else if (s == 'Co') {
                if (!this.contrattoSelected
                    || (this.contrattoGroup.controls['contrattoForm']
                        && this.contrattoSelected.value !== this.contrattoGroup.controls['contrattoForm'].value
                        && this.contrattoGroup.controls['contrattoForm'].value !== this.contratti.find(c => c.idContratto == this.dettaglioRichiesta.contratto.idContratto))) {
                    this.contrattoGroup.controls['contrattoForm'].setValue(null);
                    this.contrattoSelected = new FormControl();
                    this.datiContratto = null;
                    this.enteComm = null;
                    this.esecTit = null;
                    this.capofila = null;
                    this.soggettoIntermediario = null;
                    this.soggettoRichiedente = null;
                    this.isFirmaVisible = false;
                    this.dettaglioRichiesta.nominativoFirma = null;
                    this.dettaglioRichiesta.ruoloFirma = null;
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

    cleanContratto() {
        this.contrattoGroup.controls['contrattoForm'].setValue(null);
        this.contrattoSelected = new FormControl();
        this.datiContratto = null;
        this.enteComm = null;
        this.esecTit = null;
        this.capofila = null;
        this.soggettoIntermediario = null;
        this.soggettoRichiedente = null;
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
        this.downloadPdf = true;
        let idVeicolo = this.dettaglioRichiesta.veicoli.find(v => v.primoTelaio == allegato.primoTelaioVeicolo).idVariazAutobus;
        this.documentService.getContenutoDocumentoById(idVeicolo, allegato.tipoDocumento.id)
            .subscribe(
                res => {
                    this.downloadPdf = false;
                    saveAs(res, allegato.nomeFile);
                },
                err => {
                    this.downloadPdf = false;
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
    }

    downloadRichiesta(idRichiesta: number, allegato: AllegatoProcVO) {
        this.downloadPdf = true;
        this.documentService.getContenutoDocumentoByIdProcedimento(idRichiesta, allegato.tipoDocumento.id)
            .subscribe(
                res => {
                    this.downloadPdf = false;
                    saveAs(res, allegato.nomeFile);
                },
                err => {
                    this.downloadPdf = false;
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

    addVociDiCosto() {
        let voceVeicolo = new VoceDiCostoVeicoloVO();
        voceVeicolo.idVoceCosto = this.voce.id;
        voceVeicolo.descrizioneVoceCosto = this.voce.descrizione;
        voceVeicolo.idProcedimento = this.id;
        voceVeicolo.importo = this.importoVoce;
        this.dettaglioRichiesta.vociDiCostoVeicolo.push(voceVeicolo);
        this.vociDiCostoFiltrate.splice(this.vociDiCostoFiltrate.findIndex(v => v.id == this.voce.id), 1);
        this.voce = new VoceDiCostoVO();
        this.importoVoce = null;
    }

    removeVociDiCosto(index: number) {
        let v1 = this.vociDiCosto.find(v => v.id == this.dettaglioRichiesta.vociDiCostoVeicolo[index].idVoceCosto);
        let v2 = new VoceDiCostoVO();
        v2.id = v1.id;
        v2.descrizione = v1.descrizione;
        this.vociDiCostoFiltrate.push(v2);
        this.vociDiCostoFiltrate.sort((a, b) => a.id - b.id);
        this.dettaglioRichiesta.vociDiCostoVeicolo.splice(index, 1);
    }

    isAddVociDiCostoDisabled() {
        if (this.voce.id == null || this.importoVoce == null || this.importoVoce <= 0) {
            return true;
        } else {
            return false;
        }
    }

    isNoteRichiestaRequired() {
        if (this.dettaglioRichiesta && this.dettaglioRichiesta.idTipoProcedimento === 7) {
            if ((!this.isAddVociDiCostoDisabled() && this.voce.id === 3) || (this.dettaglioRichiesta.vociDiCostoVeicolo.find(a => a.id === 3))) {//ALTRO
                return true;
            }
        }
        return false;
    }

    isValidCampiRequired() {
        if (this.dettaglioRichiestaForm && this.dettaglioRichiestaForm.form) {
            if (this.veicoloGroup.controls['veicoloForm'] && this.veicoloGroup.controls['veicoloForm'].hasError('required')) return true;
            if (this.dettaglioRichiesta.idTipoProcedimento != 7) {
                if (this.motorizzazioneGroup.controls['motorizzazioneForm'] && this.motorizzazioneGroup.controls['motorizzazioneForm'].hasError('required')) return true;
                if (this.motivazioneGroup.controls['motivazioneForm'] && this.motivazioneGroup.controls['motivazioneForm'].hasError('required')) return true;
                if (this.dettaglioRichiestaForm.form.get('noteMotivazione') && this.dettaglioRichiestaForm.form.get('noteMotivazione').hasError('required')) return true;
                if (this.contrattoGroup.controls['contrattoForm'] && this.contrattoGroup.controls['contrattoForm'].hasError('required')) return true;
            } else { //CONTRIBUZIONE
                if (this.dettaglioRichiesta.vociDiCostoVeicolo.length == 0 && !(this.voce.id != null && this.importoVoce != null)) return true;
                if (this.dettaglioRichiestaForm.form.get('note') && this.dettaglioRichiestaForm.form.get('note').hasError('required')) return true;
            }
            if (this.dettaglioRichiestaForm.form.get('premesse') && this.dettaglioRichiestaForm.form.get('premesse').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('prescrizioni') && this.dettaglioRichiestaForm.form.get('prescrizioni').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte') && this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte') && this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('ruoloFirma') && this.dettaglioRichiestaForm.form.get('ruoloFirma').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('nominativoFirma') && this.dettaglioRichiestaForm.form.get('nominativoFirma').hasError('required')) return true;
        }
        return false;
    }

    isCampiInvalid() {
        if (this.dettaglioRichiesta.idTipoProcedimento == 7) {
            if (this.importoVoceGroup.controls['importoVoceForm'] && this.importoVoceGroup.controls['importoVoceForm'].hasError('min')) return true;
        }
        return false;
    }

    abilita() {
        this.outputToParentPulisci.emit(this.dettaglioRichiesta);
        this.richiestaConfermata = false;
        if (this.veicoloGroup.controls["veicoloForm"]) { this.veicoloGroup.controls["veicoloForm"].enable(); }
        if (this.tipoDocumentoGroup.controls["tipoDocumentoForm"]) { this.tipoDocumentoGroup.controls["tipoDocumentoForm"].enable(); }
        if (this.motorizzazioneGroup.controls["motorizzazioneForm"]) { this.motorizzazioneGroup.controls["motorizzazioneForm"].enable(); }
        if (this.motivazioneGroup.controls["motivazioneForm"]) { this.motivazioneGroup.controls["motivazioneForm"].enable(); }
        if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].enable(); }
        if (this.dettaglioRichiestaForm.form.get("tipoContratto")) { this.dettaglioRichiestaForm.form.get("tipoContratto").enable(); }

    }

    reset() {
        this.veicoloGroup.controls['veicoloForm'].setValue(null);
        this.veicoloSelected = new Array<VeicoloVO>();
        if (this.dettaglioRichiesta.idTipoProcedimento != 7) {
            if (!this.boolDisabledContrattoMotorizzazione) {
                this.motorizzazioneGroup.controls['motorizzazioneForm'].setValue(null);
                this.motorizzazioneSelected = new FormControl();
                this.motivazioneGroup.controls['motivazioneForm'].setValue(null);
                this.motivazioneSelected = new FormControl();
                this.contrattoGroup.controls['contrattoForm'].setValue(null);
                this.contrattoSelected = new FormControl();
            }
            this.motivazioneGroup.controls['motivazioneForm'].setValue(null);
            this.motivazioneSelected = new FormControl();
        } else {
            this.voce = new VoceDiCostoVO;
            this.importoVoceGroup.controls['importoVoceForm'].setValue(null);
            this.importoVoce = null;
        }
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
                        this.router.navigate(['/dettaglioRichiesta/' + this.id, { action: azione2 }]);
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
        if (this.dettaglioRichiesta.idTipoProcedimento != 7 && this.idProcedimento == null) {
            this.dettaglioRichiesta.idMotorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value.id;
            this.dettaglioRichiesta.idMotivazione = this.motivazioneGroup.controls['motivazioneForm'].value.id;
            this.contrattoSelected.setValue(this.contrattoGroup.controls['contrattoForm'].value);
            this.dettaglioRichiesta.contratto = this.contrattoGroup.controls['contrattoForm'].value;
        }
        if (this.idProcedimento == null) {
            this.loadedSave = false;
            if (this.dettaglioRichiesta.idTipoProcedimento == 7) {
                if (this.voce.id != null && this.importoVoce != null) {
                    this.addVociDiCosto();
                }
            }

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
                this.isContrattoChanged = false;
                this.loadTransizioneAutoma();

            }, error => {
                CommonsHandleException.authorizationError(error, this.router);
                this.feedback = error.error.message;
                console.log("this.feedback =" + this.feedback);
                window.scrollTo(0, 0);
                this.loadedSave = true;
            });
        } else {
            this.richiestaConfermata = true;
            if (this.veicoloGroup.controls["veicoloForm"]) { this.veicoloGroup.controls["veicoloForm"].disable(); }
            if (this.motorizzazioneGroup.controls["motorizzazioneForm"]) { this.motorizzazioneGroup.controls["motorizzazioneForm"].disable(); }
            if (this.motivazioneGroup.controls["motivazioneForm"]) { this.motivazioneGroup.controls["motivazioneForm"].disable(); }
            if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].disable(); }
            if (this.dettaglioRichiestaForm.form.get("tipoContratto")) { this.dettaglioRichiestaForm.form.get("tipoContratto").disable(); }
            if (this.tipoDocumentoGroup.controls["tipoDocumentoForm"]) { this.tipoDocumentoGroup.controls["tipoDocumentoForm"].disable(); }
            this.outputToParent.emit(this.dettaglioRichiesta);
        }
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
        if (!this.loadComplete || !this.loadedTipoProcedimento || !this.loadedVeicoli || !this.loadedTipiDoc || !this.loadedDatiContratto || !this.loadedSave || !this.loadedDettaglioRichiestaFirma || !this.loadedTransizioniAutoma || !this.loadedAvanzaIter) return true;
        if (this.dettaglioRichiesta.idTipoProcedimento != 7 && this.idProcedimento == null) {
            if (!this.loadedMotorizzazioni || !this.loadedMotivazioni || !this.loadedContratti) return true;
            if (this.dettaglioRichiesta.idTipoProcedimento != 1 && !this.loadedTipiContratto) return true;
        } else {
            if (this.dettaglioRichiesta.idTipoProcedimento == 7) {
                if (!this.loadedVociDiCosto) return true;
            }
        }
        return false;
    }

    goBack() {
        if (this.azione === 'inserisci') {
            this.router.navigate(['/inserisciRichiesta/', this.dettaglioRichiesta.idTipoProcedimento]);
        } else if (this.azione === 'ricerca') {
            this.router.navigate(['/ricercaProcedimenti']);
        } else if (this.azione.startsWith("messaggio")) {
            let array: string[] = this.azione.split('_');
            let idMessaggio: number = +array[1];
            let idElencoMessaggi: number = +array[2];
            let idContesto: number = +array[3];
            this.router.navigate(['/dettaglioMessaggio/' + idMessaggio, { action: idElencoMessaggi, idContesto: idContesto }]);
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
                if (this.dettaglioRichiesta.idTipoProcedimento != 7) {
                    if (this.motorizzazioneGroup.controls["motorizzazioneForm"]) { this.motorizzazioneGroup.controls["motorizzazioneForm"].disable(); }
                    if (this.motivazioneGroup.controls["motivazioneForm"]) { this.motivazioneGroup.controls["motivazioneForm"].disable(); }
                    if (this.dettaglioRichiestaForm.form.get("noteMotivazione")) { this.dettaglioRichiestaForm.form.get("noteMotivazione").disable(); }
                    if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].disable(); }
                    if (this.dettaglioRichiestaForm.form.get("tipoContratto")) { this.dettaglioRichiestaForm.form.get("tipoContratto").disable(); }
                } else {
                    if (this.importoVoceGroup.controls["importoVoceForm"]) { this.importoVoceGroup.controls["importoVoceForm"].disable(); }
                }
                this.allDisabled = true;
            } else if (this.iter && (this.iter.idStato == 10 || this.iter.idStato == 50) && !this.allEnabled) {
                if (this.veicoloGroup.controls["veicoloForm"]) { this.veicoloGroup.controls["veicoloForm"].enable(); }
                if (this.tipoDocumentoGroup.controls["tipoDocumentoForm"]) { this.tipoDocumentoGroup.controls["tipoDocumentoForm"].enable(); }
                if (this.dettaglioRichiesta.idTipoProcedimento != 7) {
                    if (this.motorizzazioneGroup.controls["motorizzazioneForm"]) { this.motorizzazioneGroup.controls["motorizzazioneForm"].enable(); }
                    if (this.motivazioneGroup.controls["motivazioneForm"]) { this.motivazioneGroup.controls["motivazioneForm"].enable(); }
                    if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].enable(); }
                    if (this.dettaglioRichiestaForm.form.get("tipoContratto")) { this.dettaglioRichiestaForm.form.get("tipoContratto").enable(); }
                } else {
                    if (this.dettaglioRichiestaForm.form.get("voceDiCosto")) { this.dettaglioRichiestaForm.form.get("voceDiCosto").enable(); }
                    if (this.importoVoceGroup.controls["importoVoceForm"]) { this.importoVoceGroup.controls["importoVoceForm"].enable(); }
                }
                this.allEnabled = true;
            }
        }

    }

    ngAfterViewChecked() {
        this.changeDetector.detectChanges();
    }

    disabledContrattoMotorizzazione(bool: boolean) {
        if (bool) {
            this.boolDisabledContrattoMotorizzazione = true;
            this.motorizzazioneGroup.controls['motorizzazioneForm'].disable();
            this.contrattoGroup.controls['contrattoForm'].disable();
        }
        else {
            this.boolDisabledContrattoMotorizzazione = false;
            this.motorizzazioneGroup.controls['motorizzazioneForm'].enable();
            this.contrattoGroup.controls['contrattoForm'].enable();
        }
    }

    handleClick(value) {
    }

    changeNotaMotivazione(value) {
    }

    ngOnChanges() {
    }

}

export enum Action {
    EDIT = "E",
    VIEW = "V",
}