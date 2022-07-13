/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef, AfterViewChecked } from "@angular/core";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { NgForm, FormGroup, FormControl, FormBuilder } from "@angular/forms";
import { ProcedimentoService } from "../../services/procedimento.service";
import { TipoProcedimentoVO, TipoContrattoVO, TipoDocumentoVO } from "../../vo/extend-vo";
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
import { ErrorRest } from "../../class/error-rest";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { MatSnackBar } from "@angular/material";
import { Router, ActivatedRoute } from "@angular/router";
import { UserService } from "../../services/user.service";
import { UserInfo } from "../../vo/funzionario-vo";
import { InserisciRichiestaUsoInLineaVO } from "../../vo/inserisci-richiesta-uso-in-linea.vo";
import { UtilizzoVO } from "../../vo/utilizzo-vo";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";


@Component({
    selector: 'app-inserisci-richiesta-uso-in-linea',
    templateUrl: './inserisci-richiesta-uso-in-linea.component.html',
    styleUrls: ['./inserisci-richiesta-uso-in-linea.component.scss']
})
@DestroySubscribers()
export class InserisciRichiestaUsoInLineaComponent implements OnInit, AfterViewChecked {

    idTipoProcedimento: number;
    idTipiProcedimmento: Array<number>;
    richiestaConfermata: boolean;

    feedback: string;

    richiesta: InserisciRichiestaUsoInLineaVO = new InserisciRichiestaUsoInLineaVO();
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
    numProgressivo: string;
    numProcedimento: number;
    isFirmaVisible: boolean = false;

    veicoloGroup: FormGroup = new FormGroup({ veicoloForm: new FormControl() });
    veicoloSelected = new Array<VeicoloVO>();
    filteredOptionsVeicolo: Observable<VeicoloVO[]>;


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

    loadedTipoProcedimento: boolean;
    loadedVeicoli: boolean;
    loadedContratti: boolean;
    loadedTipiContratto: boolean;
    loadedTipiDoc: boolean;
    loadedDettaglioRichiestaFirma: boolean = true;
    loadedVociDiCosto: boolean;
    loadedSave: boolean = true;
    loadedNumProcedimento: boolean;


    dataToday: Date = new Date();

    @ViewChild('richiestaForm') richiestaForm: NgForm;
    @ViewChild('fileInput') fileInput: ElementRef;
    context: string;

    constructor(private procedimentoService: ProcedimentoService,
        private autobusService: AutobusService,
        private documentService: DocumentService,
        private userService: UserService,
        public snackBar: MatSnackBar,
        private router: Router,
        private route: ActivatedRoute,
        private changeDetector: ChangeDetectorRef,
        private navbarFilterContext: NavbarFilterContext,
        private fb: FormBuilder,
    ) {
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
            this.idTipoProcedimento = +params['id'];  // (+) converts string 'id' to a number
            this.idTipiProcedimmento = new Array<number>();
            this.idTipiProcedimmento = idTipoProcedimentoString.split("");
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
        // uso in linea -- Inserimento da combo di id_tipo_ruolo
        this.loadedTipiContratto = false;
        this.procedimentoService.getTipiContratto().subscribe(data => {
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


        this.loadedTipiDoc = false;
        this.procedimentoService.getTipiDocumento(this.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipiDocumento = data;
                this.arrayAllegatiFiltrati = new Array<number>();
                if (this.tipiDocumento.find(t => t.id == 7)) {
                    this.arrayAllegatiFiltrati.push(this.tipiDocumento.find(t => t.id == 7).id);
                }
                if (this.tipiDocumento.find(t => t.id == 8)) {
                    this.arrayAllegatiFiltrati.push(this.tipiDocumento.find(t => t.id == 8).id);
                }
                this.filteredOptionsTipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].valueChanges
                    .pipe(
                        startWith(''),
                        map((value: any) => (typeof value === 'string' || value == null) ? value : value.descrizione),
                        map(name => name ? this._filterTipoDocumento(name) : this.tipiDocumento.slice())
                    );
                this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
            }
            this.loadedTipiDoc = true;

        });
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
            this.richiesta.utilizzi.push(utilizzo);
            this.contrattoGroup.controls['contrattoForm'].setValue(null);
            this.contrattoSelected = new FormControl();
        } else {
            for (let c of this.contrattiSelected) {
                let utilizzo = new UtilizzoVO();
                utilizzo.tipoContratto = this.tipiContrattoSelected.value;
                utilizzo.contratto = c;
                this.getDatiContratto(utilizzo);
                let i = this.richiesta.utilizzi.findIndex(a => a.tipoContratto.id == utilizzo.tipoContratto.id && a.contratto.idContratto == utilizzo.contratto.idContratto);
                if (i == -1) {
                    this.richiesta.utilizzi.push(utilizzo);
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
        this.richiesta.utilizzi.sort((a, b) => {
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
            this.richiesta.utilizzi.splice(this.richiesta.utilizzi.findIndex(a => a.tipoContratto.id == utilizzo.tipoContratto.id), 1);
        }
        else {
            let i = this.tipiContratto.findIndex(a => a.id == utilizzo.tipoContratto.id);
            if (i == -1) {
                this.tipiContratto.push(utilizzo.tipoContratto);
            }
            this.richiesta.utilizzi.splice(this.richiesta.utilizzi.findIndex(a => a.tipoContratto.id == utilizzo.tipoContratto.id && a.contratto.idContratto == utilizzo.contratto.idContratto), 1)
        }
        this.tipiContrattoGroup.controls['tipiContrattoForm'].updateValueAndValidity();
    }

    getDatiContratto(utilizzo: UtilizzoVO) {
        this.procedimentoService.getDatiContratto(utilizzo.contratto.idContratto, -1).subscribe(data => {
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

    click(event: any, s: string) {
        if (s == 'Co') {
            this.contrattoSelected.setValue(event.option.value);
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

            else if (s == 'Co') {
                if (!this.contrattoSelected || (this.contrattoGroup.controls['contrattoForm'] && this.contrattoSelected.value !== this.contrattoGroup.controls['contrattoForm'].value)) {
                    this.contrattoGroup.controls['contrattoForm'].setValue(null);
                    this.contrattoSelected = new FormControl();
                    this.isFirmaVisible = false;
                }
            }
            else if (s == 'TipiCo') {
                if (!this.tipiContrattoSelected || (this.tipiContrattoGroup.controls['tipiContrattoForm'] && this.tipiContrattoSelected.value !== this.tipiContrattoGroup.controls['tipiContrattoForm'].value)) {
                    this.tipiContrattoGroup.controls['tipiContrattoForm'].setValue(null);
                    this.tipiContrattoSelected = new FormControl();

                }
            }
            else if (s == 'Tdproc') {
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

    dataOggi: string = "";
    handleFileInput(files: Array<File>) {
        for (let f of files) {
            let allegato = new AllegatoProcVO();
            allegato.tipoDocumento = this.tipoDocumentoGroup.controls['tipoDocumentoForm'].value;
            this.arrayAllegatiFiltrati.push(allegato.tipoDocumento.id);
            allegato.note = this.noteFile;
            allegato.file = f;
            allegato.nomeFile = f.name;
            allegato.dataCaricamento = new Date();
            this.dataOggi = this.dataToday.getDate() + "/" + (this.dataToday.getMonth() + 1).toString() + "/" + this.dataToday.getFullYear();
            this.richiesta.files.push(allegato);
            this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
        }

        this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
        this.tipoDocumentoSelected = new FormControl();
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

    elimina(idTipoDocumento: number) {
        this.richiesta.files.splice(this.richiesta.files.findIndex(a => a.tipoDocumento.id == idTipoDocumento), 1);
        this.arrayAllegatiFiltrati.splice(this.arrayAllegatiFiltrati.findIndex(a => a == idTipoDocumento), 1);
        this.filteredOptionsTipoDocumento = of(this.tipiDocumento.filter(a => !this.arrayAllegatiFiltrati.find(b => b == a.id)));
    }

    isNoteRichiestaRequired() {
        return false;
    }

    isValidCampiRequired() {
        if (this.richiestaForm && this.richiestaForm.form) {
            if (this.veicoloGroup.controls['veicoloForm'] && this.veicoloGroup.controls['veicoloForm'].hasError('required')) return true;
            if (!this.richiesta.utilizzi || this.richiesta.utilizzi.length == 0) return true;

        }
        return false;
    }

    isCampiInvalid() {
        if (this.veicoli.length == 0) return true;
        return false;
    }

    reset() {
        this.contrattoGroup.controls['contrattoForm'].setValue(null);
        this.contrattoSelected = new FormControl();
        this.richiesta = new InserisciRichiestaUsoInLineaVO();
        this.veicoloGroup.controls['veicoloForm'].setValue(null);
        this.veicoloSelected = new Array<VeicoloVO>();
        this.allegatiVeicoli = new Array<AllegatoVeicoloVO>();
        this.tipoDocumentoGroup.controls['tipoDocumentoForm'].setValue(null);
        this.tipoDocumentoSelected = new FormControl();
        this.enableField();
        this.filteredOptionsTipoDocumento = of(this.tipiDocumento);
        this.noteFile = "";
        this.feedback = "";
        this.richiestaConfermata = false;
        this.markAllAsUntouched();
    }

    save() {
        this.loadedSave = false;
        this.richiesta.veicoli = this.veicoloSelected;
        this.richiesta.numProcedimento = this.numProcedimento;
        this.disableField();
        this.procedimentoService.inserisciRichiestaUsoInLinea(this.richiesta).subscribe(data => {
            this.loadedSave = true;
            this.feedback = "";
            let id: number = data;
            window.scrollTo(0, 0);
            this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
                duration: 2000,
            });
        }, error => {
            this.enableField();
            CommonsHandleException.authorizationError(error, this.router);
            this.feedback = error.error.message;
            console.log("this.feedback =" + this.feedback);
            window.scrollTo(0, 0);
            this.loadedSave = true;
        });

    }

    isLoading() {
        if (!this.loadedTipoProcedimento || !this.loadedNumProcedimento || !this.loadedVeicoli || !this.loadedTipiDoc || !this.loadedSave || !this.loadedDettaglioRichiestaFirma) return true;
        if (!this.loadedContratti) return true;
        if (this.idTipoProcedimento != 1 && !this.loadedTipiContratto) return true;

        return false;
    }

    markAllAsUntouched() {
        if (this.veicoloGroup != null && this.veicoloGroup.controls['veicoloForm'] != null) this.veicoloGroup.controls['veicoloForm'].markAsUntouched();
        if (this.richiestaForm.form != null && this.richiestaForm.form.get("note") != null) this.richiestaForm.form.get("note").markAsUntouched();
        if (this.richiestaForm.form != null && this.richiestaForm.form.get("noteMotivazione") != null) this.richiestaForm.form.get("noteMotivazione").markAsUntouched();
        if (this.contrattoGroup != null && this.contrattoGroup.controls['contrattoForm'] != null) this.contrattoGroup.controls['contrattoForm'].markAsUntouched();
    }

    disableField() {
        this.veicoloGroup.controls['veicoloForm'].disable();

        this.contrattoGroup.controls['contrattoForm'].disable();
        this.tipoDocumentoGroup.controls['tipoDocumentoForm'].disable();
    }

    enableField() {
        this.tipoDocumentoGroup.controls['tipoDocumentoForm'].enable();
        this.contrattoGroup.controls['contrattoForm'].enable();

        this.veicoloGroup.controls['veicoloForm'].enable();
    }



    ngOnChanges() {

    }

    ngAfterViewChecked() {
        this.changeDetector.detectChanges();
    }


}