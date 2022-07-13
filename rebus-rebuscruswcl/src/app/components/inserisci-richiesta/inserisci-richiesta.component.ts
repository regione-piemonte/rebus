/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, OnDestroy, ViewChild, ElementRef, Input, Output, EventEmitter, ChangeDetectorRef, AfterViewChecked } from "@angular/core";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { NgForm, FormGroup, FormControl, FormBuilder, Validators } from "@angular/forms";
import { ProcedimentoService } from "../../services/procedimento.service";
import { TipoProcedimentoVO, MotorizzazioneVO, MotivazioneVO, TipoContrattoVO, TipoDocumentoVO, VoceDiCostoVO } from "../../vo/extend-vo";
import { Observable } from "rxjs";
import { VeicoloVO } from "../../vo/veicolo-vo";
import { AutobusService } from "../../services/autobus.service";
import { startWith, map } from "rxjs/operators";
import { InserisciRichiestaVO } from "../../vo/inserisci-richiesta.vo";
import { AllegatoProcVO } from "../../vo/allegato-proc-vo";
import { ContrattoProcVO, ContrattoProcDatiVO } from "../../vo/contratto-proc-vo";
import { saveAs } from "file-saver";
import { DocumentService } from "../../services/document.service";
import { AllegatoVeicoloVO } from "../../vo/allegato-veicolo-vo";
import { ErrorRest } from "../../class/error-rest";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { MatSnackBar } from "@angular/material";
import { Router, ActivatedRoute } from "@angular/router";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { UserService } from "../../services/user.service";
import { UserInfo } from "../../vo/funzionario-vo";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";
import { UtilityService } from "../../services/utility.service";


@Component({
    selector: 'app-inserisci-richiesta',
    templateUrl: './inserisci-richiesta.component.html',
    styleUrls: ['./inserisci-richiesta.component.scss']
})
@DestroySubscribers()
export class InserisciRichiestaComponent implements OnInit, AfterViewChecked, OnDestroy {

    idTipoProcedimento: number;
    idTipiProcedimmento: Array<number>;
    richiestaConfermata: boolean;
    richiestaMaiConfermata: boolean = true;

    @Input() disableTipoContratto: boolean;

    @Output() outputDisableTipoContratto = new EventEmitter<boolean>();

    @Input() idProcedimento: number;

    @Output() outputToParent = new EventEmitter<InserisciRichiestaVO>();

    @Output() outputToParentPulisci = new EventEmitter<InserisciRichiestaVO>();

    @Output() outputContrattoSelected = new EventEmitter<FormControl>();

    @Input() contrattoSelectedOther;

    @Output() outputMotorizzazioneSelected = new EventEmitter<FormControl>();

    @Input() motorizzazioneSelectedOther;

    @Output() outputMotivazioneSelected = new EventEmitter<FormControl>();

    @Input() motivazioneSelectedOther;

    @Output() outputNotaMotivazioneSelected = new EventEmitter<FormControl>();

    @Input() notaMotivazioneSelectedOther;

    @Output() outputTipoContrattoSelected = new EventEmitter<FormControl>();

    @Input() tipoContrattoSelectedOther;



    feedback: string;

    richiesta: InserisciRichiestaVO = new InserisciRichiestaVO();
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
    arrayAllegatiDaFiltrare: Array<number>;
    arrayAllegatiFiltrati: Array<TipoDocumentoVO>;
    tipoDoc: TipoDocumentoVO = new TipoDocumentoVO();
    noteFile: string = "";
    dettaglioRichiestaFirma: DettaglioRichiestaVO;
    vociDiCostoFiltrate: Array<VoceDiCostoVO>;
    voce: VoceDiCostoVO = new VoceDiCostoVO();
    importoVoce: number;
    boolDisabledContrattoMotorizzazione: boolean;
    numProgressivo: string;
    numProcedimento: number;
    datiContratto: ContrattoProcDatiVO;
    enteComm: string;
    esecTit: string;
    capofila: string;
    soggettoRichiedente: string;
    soggettoIntermediario: string;
    isFirmaVisible: boolean = false;



    veicoloGroup: FormGroup = new FormGroup({ veicoloForm: new FormControl() });
    veicoloSelected = new Array<VeicoloVO>();
    filteredOptionsVeicolo: Observable<VeicoloVO[]>;

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

    loadedTipoProcedimento: boolean;
    loadedVeicoli: boolean;
    loadedMotorizzazioni: boolean;
    loadedMotivazioni: boolean;
    loadedContratti: boolean;
    loadedTipiContratto: boolean;
    loadedTipiDoc: boolean;
    loadedDettaglioRichiestaFirma: boolean = true;
    loadedVociDiCosto: boolean;
    loadedSave: boolean = true;
    loadedNumProcedimento: boolean;
    context: string;

    dataToday: Date = new Date();

    @ViewChild('richiestaForm') richiestaForm: NgForm;
    @ViewChild('fileInput') fileInput: ElementRef;

    constructor(private procedimentoService: ProcedimentoService,
        private autobusService: AutobusService,
        private documentService: DocumentService,
        private userService: UserService,
        private navbarFilterContext: NavbarFilterContext,
        public snackBar: MatSnackBar,
        private router: Router,
        private route: ActivatedRoute,
        private changeDetector: ChangeDetectorRef,
        private fb: FormBuilder,
    ) {
        this.importoVoceGroup = fb.group({
            'importoVoceForm': ['', [Validators.min(0.01)]]
        });
    }

    ngOnInit(): void {
        if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
            var intervalContesti = setInterval(() => {
                this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 2);
                if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                    this.context = this.navbarFilterContext.InfoFiltro.cod;
                }
                clearInterval(intervalContesti);
            }, 200);
        } else {
            this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 2);
            if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                this.context = this.navbarFilterContext.InfoFiltro.cod;
            }
        }
        this.richiestaConfermata = false;
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
        });
        this.route.params.subscribe(params => {
            let idTipoProcedimentoString = params['id'];
            if (this.idProcedimento == null) {
                this.idTipoProcedimento = +params['id'];  // (+) converts string 'id' to a number
            }
            else {
                this.idTipoProcedimento = +this.idProcedimento;
            }
            this.idTipiProcedimmento = new Array<number>();
            this.idTipiProcedimmento = idTipoProcedimentoString.split("");
            this.boolDisabledContrattoMotorizzazione = false;
            this.reset();
            this.markAllAsUntouched();
            this.loadChoices();
        });
    }

    loadChoices() {
        this.loadedTipoProcedimento = false;
        this.loadedNumProcedimento = false;
        this.procedimentoService.getTipoProcedimento(this.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipoProcedimento = data;
                this.richiesta.tipoProcedimento = data;
                this.procedimentoService.getNumProcedimento(this.idTipoProcedimento).subscribe(data => {
                    if (data) {
                        this.numProcedimento = data;
                        this.numProgressivo = (new Date()).getFullYear() + "/" + this.tipoProcedimento.codProcedimento + "/" + this.numProcedimento;
                    }
                    this.loadedNumProcedimento = true;
                });
            }
            this.loadedTipoProcedimento = true;
        });

        this.loadedVeicoli = false;
        this.autobusService.getVeicoliInserisci(this.idTipoProcedimento).subscribe(data => {
            if (data) {

                this.veicoli = data;
                if (this.veicoli.length == 0) {
                    this.veicoloGroup.controls['veicoloForm'].markAsTouched();
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
        if (this.idTipoProcedimento != 7 && this.idProcedimento == null) {
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
                }
                this.loadedMotorizzazioni = true;
            });
            this.loadedMotivazioni = false;
            if (this.idProcedimento == null) {
                this.procedimentoService.getMotivazioni(this.idTipoProcedimento, true).subscribe(data => {
                    if (data) {
                        this.motivazioni = data;
                        if (this.motivazioni.length == 1) {
                            this.richiesta.motivazione = this.motivazioni[0];
                            this.motivazioneSelected.setValue(this.motivazioni[0]);
                            this.motivazioneGroup.controls['motivazioneForm'].setValue(this.motivazioni[0]);
                            this.motivazioneGroup.controls['motivazioneForm'].disable();
                        }
                        this.filteredOptionsMotivazione = this.motivazioneGroup.controls['motivazioneForm'].valueChanges
                            .pipe(
                                startWith(''),
                                map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                                map(name => name ? this._filterMotivazione(name) : this.motivazioni.slice())
                            );
                    }
                    this.loadedMotivazioni = true;
                });
            } else {
                this.procedimentoService.getMotivazioni(2, true).subscribe(data => {
                    if (data) {
                        this.motivazioni = data;
                        this.filteredOptionsMotivazione = this.motivazioneGroup.controls['motivazioneForm'].valueChanges
                            .pipe(
                                startWith(''),
                                map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                                map(name => name ? this._filterMotivazione(name) : this.motivazioni.slice())
                            );
                    }
                    this.loadedMotivazioni = true;
                });
            }
            this.loadedContratti = false;
            this.procedimentoService.getContrattiInserisci().subscribe(data => {
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
        }
        this.loadedTipiDoc = false;
        this.procedimentoService.getTipiDocumento(this.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipiDocumento = data;
                this.arrayAllegatiDaFiltrare = new Array<number>();
                if (this.tipiDocumento.find(t => t.id == 7)) {
                    this.arrayAllegatiDaFiltrare.push(this.tipiDocumento.find(t => t.id == 7).id);
                }
                if (this.tipiDocumento.find(t => t.id == 8)) {
                    this.arrayAllegatiDaFiltrare.push(this.tipiDocumento.find(t => t.id == 8).id);
                }
                this.arrayAllegatiFiltrati = this.tipiDocumento.filter(a => !this.arrayAllegatiDaFiltrare.find(b => b == a.id))
            }
            this.loadedTipiDoc = true;

        });
        this.loadedDettaglioRichiestaFirma = false;
        this.procedimentoService.getFirmaProcedimento().subscribe(data => {
            if (data) {
                this.dettaglioRichiestaFirma = data;
            }
            this.loadedDettaglioRichiestaFirma = true;
        });
        if (this.idTipoProcedimento == 7) {
            this.loadedVociDiCosto = false;
            this.procedimentoService.getVociDiCosto().subscribe(data => {
                if (data) {
                    this.vociDiCostoFiltrate = data;
                }
                this.loadedVociDiCosto = true;
            });
        }

    }

    _filterVeicolo(descrizione: string): VeicoloVO[] {
        const filterValue = descrizione.toLowerCase();
        if (this.idTipoProcedimento == 1) {
            return this.veicoli.filter(option => option.primoTelaio.toLowerCase().startsWith(filterValue));
        }
        else {
            return this.veicoli.filter(option => option.nTarga.toLowerCase().startsWith(filterValue));
        }
    }

    _filterMotorizzazione(descrizione: string): MotorizzazioneVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.motorizzazioni.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
    }

    _filterMotivazione(descrizione: string): MotivazioneVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.motivazioni.filter(option => option.descrizione.toLowerCase().startsWith(filterValue));
    }

    _filterContratto(descrizione: string): ContrattoProcVO[] {
        const filterValue = descrizione.toLowerCase();
        return this.contratti.filter(option => option.codIdNazionale != null ? option.codIdNazionale.toLowerCase().startsWith(filterValue) : false);
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
        comp.selected = !comp.selected;
        if (comp.selected) {
            this.veicoloSelected.push(comp);
            if (comp.allegati != null && comp.allegati.length > 0) {
                this.haveAllegati = true;
                for (let a of comp.allegati) {
                    a.primoTelaioVeicolo = comp.primoTelaio;
                    a.nTargaVeicolo = comp.nTarga;
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

    click(event: any, s: string) {
        if (s == 'Motor') {
            this.motorizzazioneSelected = event.option.value;
        } else if (s == 'Motiv') {
            this.motivazioneSelected = event.option.value;
            if (!event.option.value.flgMotivAltro) {
                this.richiestaForm.form.get("noteMotivazione").setValue(null);
            }
        } else if (s == 'Co') {
            this.contrattoSelected = event.option.value;
            this.capofila = null;
            this.soggettoIntermediario = null;
            this.procedimentoService.getDatiContratto(event.option.value.idContratto, -1).subscribe(data => {
                if (data) {
                    this.datiContratto = data;
                    if (this.funzionario.idAzienda != null) {
                        if (this.datiContratto.soggIntermediario && this.funzionario.idAzienda == this.datiContratto.soggIntermediario.id) {
                            this.isFirmaVisible = true;
                            this.richiesta.nominativoFirma = this.dettaglioRichiestaFirma.nominativoFirma;
                            this.richiesta.ruoloFirma = this.dettaglioRichiestaFirma.ruoloFirma;
                        } else if (!this.datiContratto.soggIntermediario && this.funzionario.idAzienda == this.datiContratto.esecTit.id) {
                            this.isFirmaVisible = true;
                            this.richiesta.nominativoFirma = this.dettaglioRichiestaFirma.nominativoFirma;
                            this.richiesta.ruoloFirma = this.dettaglioRichiestaFirma.ruoloFirma;
                        } else {
                            this.isFirmaVisible = false;
                            this.richiesta.nominativoFirma = "";
                            this.richiesta.ruoloFirma = "";
                        }
                    }
                    else {
                        this.isFirmaVisible = false;
                        this.richiesta.nominativoFirma = "";
                        this.richiesta.ruoloFirma = "";
                    }
                    this.enteComm = this.datiContratto.enteComm.codiceOss + " - " + this.datiContratto.enteComm.denomBreve;
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
            });
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
                if (this.idTipoProcedimento != 7) {
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
                if (!this.motorizzazioneSelected || (this.motorizzazioneGroup.controls['motorizzazioneForm'] && this.motorizzazioneSelected !== this.motorizzazioneGroup.controls['motorizzazioneForm'].value)) {
                    this.motorizzazioneGroup.controls['motorizzazioneForm'].setValue(null);
                    this.motorizzazioneSelected = new FormControl();
                }
            }
            else if (s == 'Motiv') {
                if (!this.motivazioneSelected || (this.motivazioneGroup.controls['motivazioneForm'] && this.motivazioneSelected !== this.motivazioneGroup.controls['motivazioneForm'].value)) {
                    this.motivazioneGroup.controls['motivazioneForm'].setValue(null);
                    this.motivazioneSelected = new FormControl();
                }
            }
            else if (s == 'Co') {
                if (!this.contrattoSelected || (this.contrattoGroup.controls['contrattoForm'] && this.contrattoSelected !== this.contrattoGroup.controls['contrattoForm'].value)) {
                    this.contrattoGroup.controls['contrattoForm'].setValue(null);
                    this.contrattoSelected = new FormControl();
                    this.datiContratto = null;
                    this.enteComm = null;
                    this.esecTit = null;
                    this.capofila = null;
                    this.soggettoIntermediario = null;
                    this.soggettoRichiedente = null;
                    this.richiesta.nominativoFirma = "";
                    this.richiesta.ruoloFirma = "";
                    this.isFirmaVisible = false;
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
                if (this.idTipoProcedimento == 1) {
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
        } else if (a != null && this.idTipoProcedimento == 7) {
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

    dataOggi: string = "";
    handleFileInput(files: Array<File>) {
        for (let f of files) {
            let allegato = new AllegatoProcVO();
            allegato.tipoDocumento = this.tipoDoc;
            this.arrayAllegatiDaFiltrare.push(allegato.tipoDocumento.id);
            allegato.note = this.noteFile;
            allegato.file = f;
            allegato.nomeFile = f.name;
            allegato.dataCaricamento = new Date();
            this.dataOggi = this.dataToday.getDate() + "/" + (this.dataToday.getMonth() + 1).toString() + "/" + this.dataToday.getFullYear();
            this.richiesta.files.push(allegato);
            this.arrayAllegatiFiltrati = this.tipiDocumento.filter(a => !this.arrayAllegatiDaFiltrare.find(b => b == a.id));
        }
        this.tipoDoc = new TipoDocumentoVO();
        this.noteFile = '';
        this.fileInput.nativeElement.value = "";
    }

    download(allegato: AllegatoVeicoloVO) {
        let idVeicolo = this.veicoloSelected.find(v => v.primoTelaio == allegato.primoTelaioVeicolo).idVariazAutobus;
        this.documentService.getContenutoDocumentoById(idVeicolo, allegato.tipoDocumento.id)
            .subscribe(
                res => {
                    saveAs(res, allegato.nomeFile);
                },
                err => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
    }

    downloadRichiestaNotDb(allegato: AllegatoProcVO) {
        saveAs(allegato.file, allegato.nomeFile);
    }

    elimina(idTipoDocumento: number) {
        this.richiesta.files.splice(this.richiesta.files.findIndex(a => a.tipoDocumento.id == idTipoDocumento), 1);
        this.arrayAllegatiDaFiltrare.splice(this.arrayAllegatiDaFiltrare.findIndex(a => a == idTipoDocumento), 1);
        this.arrayAllegatiFiltrati = this.tipiDocumento.filter(a => !this.arrayAllegatiDaFiltrare.find(b => b == a.id));
    }

    addVociDiCosto() {
        this.voce.importo = this.importoVoce;
        this.richiesta.vociDiCosto.push(this.voce);
        this.vociDiCostoFiltrate.splice(this.vociDiCostoFiltrate.findIndex(v => v.id == this.voce.id), 1);
        this.voce = new VoceDiCostoVO();
        this.importoVoce = null;
    }

    removeVociDiCosto(index: number) {
        this.vociDiCostoFiltrate.push(this.richiesta.vociDiCosto[index]);
        this.vociDiCostoFiltrate.sort((a, b) => a.id - b.id);
        this.richiesta.vociDiCosto.splice(index, 1);
    }

    isAddVociDiCostoDisabled() {
        if (this.voce.id == null || this.importoVoce == null || this.importoVoce <= 0) {
            return true;
        } else {
            return false;
        }
    }

    isNoteRichiestaRequired() {
        if (this.tipoProcedimento && this.tipoProcedimento.id === 7) {
            if ((!this.isAddVociDiCostoDisabled() && this.voce.id === 3) || (this.richiesta.vociDiCosto.find(a => a.id === 3))) {//ALTRO
                return true;
            }
        }
        return false;
    }

    isValidCampiRequired() {
        if (this.richiestaForm && this.richiestaForm.form) {
            if (this.veicoloGroup.controls['veicoloForm'] && this.veicoloGroup.controls['veicoloForm'].hasError('required')) return true;
            if (this.idTipoProcedimento != 7) {
                if (this.motorizzazioneGroup.controls['motorizzazioneForm'] && this.motorizzazioneGroup.controls['motorizzazioneForm'].hasError('required')) return true;
                if (this.motivazioneGroup.controls['motivazioneForm'] && this.motivazioneGroup.controls['motivazioneForm'].hasError('required')) return true;
                if (this.richiestaForm.form.get('noteMotivazione') && this.richiestaForm.form.get('noteMotivazione').hasError('required')) return true;
                if (this.contrattoGroup.controls['contrattoForm'] && this.contrattoGroup.controls['contrattoForm'].hasError('required')) return true;
            } else { //CONTRIBUZIONE
                if (this.richiesta.vociDiCosto.length == 0 && !(this.voce.id != null && this.importoVoce != null)) return true;
                if (this.richiestaForm.form.get('note') && this.richiestaForm.form.get('note').hasError('required')) return true;
            }
            if (this.richiestaForm.form.get('ruoloFirma') && this.richiestaForm.form.get('ruoloFirma').hasError('required')) return true;
            if (this.richiestaForm.form.get('nominativoFirma') && this.richiestaForm.form.get('nominativoFirma').hasError('required')) return true;

        }
        return false;
    }

    isCampiInvalid() {
        if (this.idTipoProcedimento == 7) {
            if (this.importoVoceGroup.controls['importoVoceForm'] && this.importoVoceGroup.controls['importoVoceForm'].hasError('min')) return true;
        }
        return false;
    }

    reset() {
        if (this.idTipoProcedimento != 7) {
            this.outputToParentPulisci.emit(this.richiesta);
            if (!this.boolDisabledContrattoMotorizzazione) {
                this.motorizzazioneGroup.controls['motorizzazioneForm'].setValue(null);
                this.motorizzazioneSelected = new FormControl();
                this.motivazioneGroup.controls['motivazioneForm'].setValue(null);
                this.motivazioneSelected = new FormControl();
                this.contrattoGroup.controls['contrattoForm'].setValue(null);
                this.contrattoSelected = new FormControl();
                this.datiContratto = null;
                this.enteComm = null;
                this.esecTit = null;
                this.capofila = null;
                this.soggettoIntermediario = null;
                this.soggettoRichiedente = null;
            }
            this.motivazioneGroup.controls['motivazioneForm'].setValue(null);
            this.motivazioneSelected = new FormControl();
        } else {
            this.voce = new VoceDiCostoVO;
            this.importoVoceGroup.controls['importoVoceForm'].setValue(null);
            this.importoVoce = null;
        }
        this.richiesta = new InserisciRichiestaVO();
        if (this.veicoloSelected && this.veicoloSelected.length > 0) {
            let veicoli = new Array<VeicoloVO>();
            for (let v of this.veicoloSelected) {
                veicoli.push(v);
            }
            for (let v of veicoli) {
                this.toggleSelectionVeicolo(v);
            }
        }
        this.veicoloGroup.controls['veicoloForm'].setValue(null);
        this.veicoloSelected = new Array<VeicoloVO>();
        this.allegatiVeicoli = new Array<AllegatoVeicoloVO>();
        this.tipoDoc = new TipoDocumentoVO();
        if (this.tipiDocumento) {
            this.arrayAllegatiDaFiltrare = new Array<number>();
            if (this.tipiDocumento.find(t => t.id == 7)) {
                this.arrayAllegatiDaFiltrare.push(this.tipiDocumento.find(t => t.id == 7).id);
            }
            if (this.tipiDocumento.find(t => t.id == 8)) {
                this.arrayAllegatiDaFiltrare.push(this.tipiDocumento.find(t => t.id == 8).id);
            }
            this.arrayAllegatiFiltrati = this.tipiDocumento.filter(a => !this.arrayAllegatiDaFiltrare.find(b => b == a.id));
        }
        this.noteFile = "";
        this.feedback = "";
        this.richiestaConfermata = false;
        this.enableField();
        this.markAllAsUntouched();
    }

    abilita() {
        this.richiestaConfermata = false;
        this.richiestaMaiConfermata = true;
        if (this.veicoloGroup.controls["veicoloForm"]) { this.veicoloGroup.controls["veicoloForm"].enable(); }
        if (this.richiestaForm.form.get("tipoDocumento")) { this.richiestaForm.form.get("tipoDocumento").enable(); }
        if (this.motorizzazioneGroup.controls["motorizzazioneForm"]) { this.motorizzazioneGroup.controls["motorizzazioneForm"].enable(); }
        if (this.motivazioneGroup.controls["motivazioneForm"]) { this.motivazioneGroup.controls["motivazioneForm"].enable(); }
        if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].enable(); }
        if (this.richiestaForm.form.get("tipoContratto")) { this.richiestaForm.form.get("tipoContratto").enable(); }

    }

    save() {
        this.richiestaConfermata = true;
        this.richiestaMaiConfermata = false;
        this.richiesta.tipoProcedimento = this.tipoProcedimento;
        this.richiesta.veicoli = this.veicoloSelected;
        this.richiesta.numProcedimento = this.numProcedimento;
        if (this.idTipoProcedimento != 7 && this.idProcedimento == null) {
            this.richiesta.motorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value;
            this.richiesta.motivazione = this.motivazioneGroup.controls['motivazioneForm'].value;
            this.richiesta.contratto = this.contrattoGroup.controls['contrattoForm'].value;
            this.disableField();
            }
        if (this.idProcedimento == null) {
            if (this.idTipoProcedimento == 7) {
                if (this.voce.id != null && this.importoVoce != null) {
                    this.addVociDiCosto();
                }
            }
            this.procedimentoService.inserisciRichiesta(this.richiesta).subscribe(data => {
                this.loadedSave = true;
                this.feedback = "";
                let id: number = data;
                window.scrollTo(0, 0);
                this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
                    duration: 2000,
                });

                setTimeout(() => { this.router.navigate(['/modificaRichiesta/' + id, { action: 'inserisci' }]) }, 2000);
            }, error => {
                this.richiestaConfermata = false;
                this.enableField();
                CommonsHandleException.authorizationError(error, this.router);
                this.feedback = error.error.message;
                console.log("this.feedback =" + this.feedback);
                window.scrollTo(0, 0);
                this.loadedSave = true;
            });
        }
        else {
            this.richiesta.nominativoFirma = "";
            this.richiesta.ruoloFirma = "";
            this.outputToParent.emit(this.richiesta);
        }
    }

    isLoading() {
        if (!this.loadedTipoProcedimento || !this.loadedNumProcedimento || !this.loadedVeicoli || !this.loadedTipiDoc || !this.loadedSave || !this.loadedDettaglioRichiestaFirma) return true;
        if (this.idTipoProcedimento != 7 && this.idProcedimento == null) {
            if (!this.loadedMotorizzazioni || !this.loadedMotivazioni || !this.loadedContratti) return true;
        } else {
            if (this.idTipoProcedimento == 7) {
                if (!this.loadedVociDiCosto) return true;
            }
        }
        return false;
    }

    markAllAsUntouched() {
        if (this.veicoloGroup != null && this.veicoloGroup.controls['veicoloForm'] != null) this.veicoloGroup.controls['veicoloForm'].markAsUntouched();
        if (this.richiestaForm.form != null && this.richiestaForm.form.get("note") != null) this.richiestaForm.form.get("note").markAsUntouched();
        if (this.idTipoProcedimento != 7) {
            if (this.motorizzazioneGroup != null && this.motorizzazioneGroup.controls['motorizzazioneForm'] != null) this.motorizzazioneGroup.controls['motorizzazioneForm'].markAsUntouched();
            if (this.motivazioneGroup != null && this.motivazioneGroup.controls['motivazioneForm'] != null) this.motivazioneGroup.controls['motivazioneForm'].markAsUntouched();
            if (this.richiestaForm.form != null && this.richiestaForm.form.get("noteMotivazione") != null) this.richiestaForm.form.get("noteMotivazione").markAsUntouched();
            if (this.contrattoGroup != null && this.contrattoGroup.controls['contrattoForm'] != null) this.contrattoGroup.controls['contrattoForm'].markAsUntouched();
        } else {
            if (this.importoVoceGroup != null && this.importoVoceGroup.controls['importoVoceForm'] != null) this.importoVoceGroup.controls['importoVoceForm'].markAsUntouched();
        }
    }

    disableField() {
        this.veicoloGroup.controls['veicoloForm'].disable();
        this.motorizzazioneGroup.controls['motorizzazioneForm'].disable();
        this.motivazioneGroup.controls['motivazioneForm'].disable();
        this.contrattoGroup.controls['contrattoForm'].disable();
        this.richiestaForm.form.get("tipoDocumento").disable();
    }

    enableField() {
        if (this.richiestaForm && this.richiestaForm.form && this.richiestaForm.form.get("tipoDocumento")) {
            this.richiestaForm.form.get("tipoDocumento").enable();
        }
        if (!this.boolDisabledContrattoMotorizzazione) {
            this.contrattoGroup.controls['contrattoForm'].enable();
            this.motorizzazioneGroup.controls['motorizzazioneForm'].enable();
            this.motivazioneGroup.controls['motivazioneForm'].enable();
        }
        this.motivazioneGroup.controls['motivazioneForm'].enable();
        this.veicoloGroup.controls['veicoloForm'].enable();
    }

    ngOnDestroy(): void {
    }

    handleClick(value) {
        this.outputTipoContrattoSelected.emit(value);

    }

    changeNotaMotivazione(value) {
        // this.outputNotaMotivazioneSelected.emit(value);
    }

    ngAfterViewChecked() {
        this.changeDetector.detectChanges();
    }
}