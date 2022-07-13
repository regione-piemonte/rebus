/*************************************************
Copyright Regione Piemonte - 2022
 SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { AfterContentChecked, Component, Input, OnDestroy, OnInit, ViewChild, ViewEncapsulation, ChangeDetectorRef } from "@angular/core";
import { FormControl, FormGroup, NgForm } from "@angular/forms";
import { ErrorRest, TypeErrorRest } from "../../class/error-rest";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { AutobusLabel } from "../../commons/labels/autobus-label";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";
import { AssegnazioneRisorseVO } from "../../vo/contribuzione/assegnazione-risorse-vo";
import { AttoAssegnazioneRisorseVo } from "../../vo/contribuzione/atto-assegnazione-risorse-vo";
import { ContribuzioneCompletaVO } from "../../vo/contribuzione/contribuzione-completa-vo";
import { ContribuzioneVO } from "../../vo/contribuzione/contribuzione-vo";
import { CostoFornituraVO } from "../../vo/contribuzione/costo-fornitura-vo";
import { DatoFatturaVO } from "../../vo/contribuzione/dato-fattura-vo";
import { DatoMessaServizioVO } from "../../vo/contribuzione/dato-messa-servizio-vo";
import { DatoSpesaVO } from "../../vo/contribuzione/dato-spesa-vo";
import { DocContribuzioneVO } from "../../vo/contribuzione/doc-contribuzione-vo";
import { FonteFinanziamentoVO } from "../../vo/contribuzione/fonte-finanziamento-vo";
import { OrdineAcquistoVO } from "../../vo/contribuzione/ordine-acquisto-vo";
import { TipoDocumentoQuietanzaVO } from "../../vo/contribuzione/tipo-documento-quietanza-vo";
import { TipoSostituzioneVO } from "../../vo/contribuzione/tipo-sostituzione-vo";
import { VoceDiCostoContribuzioneVO } from "../../vo/contribuzione/voce-costo-contribuzione-vo";
import { VoceCostoFornituraVO } from "../../vo/contribuzione/voce-costo-fornitura-vo";
import * as config from '../../globalparameter';
import { ContribuzioneService } from "../../services/contribuzione.service";
import { saveAs } from "file-saver";
import { DocumentService } from "../../services/document.service";
import { DocumentoVO } from "../../vo/documento-vo";
import { ActivatedRoute, Router } from "@angular/router";
import { ProcedimentoService } from "../../services/procedimento.service";
import { TipoProcedimentoVO } from "../../vo/extend-vo";
import { InserisciRichiestaVO } from "../../vo/inserisci-richiesta.vo";
import { AutobusService } from "../../services/autobus.service";
import { Observable } from "rxjs";
import { map, startWith } from 'rxjs/operators';
import { UserService } from "../../services/user.service";
import { CodeRoles } from "../../class/code-roles";
import { MatDialog, MatExpansionPanel, MatSnackBar } from "@angular/material";
import { VeicoloVO } from "../../vo/veicolo-vo";
import { TransizioneAutomaVO } from "../../vo/transizione-automa-vo";
import { CancellaDialogComponent } from "../cancelladialog/cancelladialog.component";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { IterProcedimentoVO } from "../../vo/iter-procedimento-vo";
import { DatoBonificoVO } from "../../vo/contribuzione/dato-bonifico-vo";


@Component({
    selector: 'app-inserisci-contribuzione',
    templateUrl: './inserisci-contribuzione.component.html',
    styleUrls: ['./inserisci-contribuzione.component.scss'],
    encapsulation: ViewEncapsulation.None,
})
@DestroySubscribers()
export class InserisciContribuzioneComponent implements OnInit, OnDestroy, AfterContentChecked {

    @ViewChild("panelParteA") panelParteA: MatExpansionPanel;
    @ViewChild("panelParteB") panelParteB: MatExpansionPanel;
    @ViewChild("page") page: any;
    @ViewChild('contribuzioneForm') contribuzioneForm: NgForm;

    config = config;

    @Input() idProcedimento: number;
    private subscribers: any;
    idTipoProcedimento: number;
    idTipiProcedimmento: Array<number>;
    numProgressivo: string;
    numProcedimento: number;
    tipoProcedimento: TipoProcedimentoVO;
    loadedTipoProcedimento: boolean;
    loadedNumProcedimento: boolean;
    richiesta: InserisciRichiestaVO = new InserisciRichiestaVO();
    listOfTelai: string[] = [];
    myControl = new FormControl();
    filteredOptions: Observable<string[]>;
    feedback: string;
    doubleMessages: string[] = [];
    fristMessages: string = "";
    secondMessages: string = "";
    doubleError: boolean = false;
    documenti: Array<DocumentoVO> = [];
    isAmpOrRp: boolean = false;
    isAzienda: boolean = false;

    context: string;
    AutobusLabel = AutobusLabel;
    documentiContribuzione: any[];
    idAttoAssegnazioneRisorse: number;
    contributoPubblico: number;
    listOggettoFatturaDatoSpesa: any[] = [];
    listFatturaBonifico: any[] = [];

    /* DROP DOWN*/
    listOfVociCosto: VoceDiCostoContribuzioneVO[] = [];
    listOfAttiAssegnazioneRisorse: AttoAssegnazioneRisorseVo[] = [];
    listOfTipiDocumentoQuietanza: TipoDocumentoQuietanzaVO[] = [];
    listOfTipiSostituzione: TipoSostituzioneVO[] = [];
    listOfFonteFinanziamento: FonteFinanziamentoVO[] = [];
    listOfTelaiVeicoloDaSostituire: string[] = [];
    /* OGGETTI SEZIONI */
    assegnazioneRisorse: AssegnazioneRisorseVO = new AssegnazioneRisorseVO();
    ordineAcquisto: OrdineAcquistoVO = new OrdineAcquistoVO();
    costoFornitura: CostoFornituraVO = new CostoFornituraVO();
    datoSpesa: DatoSpesaVO = new DatoSpesaVO();
    datoFattura: DatoFatturaVO = new DatoFatturaVO();
    datoMessaServizio: DatoMessaServizioVO = new DatoMessaServizioVO();
    contribuzione: ContribuzioneVO = new ContribuzioneVO();
    contribuzioneCompleta: ContribuzioneCompletaVO = new ContribuzioneCompletaVO();
    datoBonifico: DatoBonificoVO = new DatoBonificoVO();
    /* DOCUMENTI */
    documentoVoceCosto: DocContribuzioneVO = new DocContribuzioneVO();
    documentoFatturaSpesa: DocContribuzioneVO = new DocContribuzioneVO();
    documentoCartaCircolazione: DocContribuzioneVO = new DocContribuzioneVO();
    documentoAttoObbligo: DocContribuzioneVO = new DocContribuzioneVO();
    documentoGaranzia: DocContribuzioneVO = new DocContribuzioneVO();
    documentoAlienazione: DocContribuzioneVO = new DocContribuzioneVO();
    documentoQuietanza: DocContribuzioneVO = new DocContribuzioneVO();
    documentoMisuraEmissioni: DocContribuzioneVO = new DocContribuzioneVO();

    idVoceCosto: any;
    importoVoceCosto: any;
    listOfVociCostoToShow: VoceDiCostoContribuzioneVO[] = [];
    listOfOggettoFattura: any[] = []; // usato nella drop down oggetto fattura
    listOfOggettoBonifico: any[] = []; // usato nella drop down oggetto bonifico
    listOfDatoFattura: DatoFatturaVO[] = []; // lista di data fattuta che passo al BE
    listOfDatoFatturaToShow: DatoFatturaVO[] = []; // lista oggetto fattura che mostro nella schermata 
    listOfDatoQuietanzaToShow: any[] = []; // lista quietanza che mostro nella schermata 
    listOfDatoBonificoToShow: any[] = []; // lista bonifici che mostro nella schermata 
    listOfVoceCostoFornitura: VoceCostoFornituraVO[] = [];
    listOfVoceCostoFornituraToShow: any[] = [];
    lengthVociCosto: number = 0;

    // MODIFICA
    idProcedimentoUpdate: number;
    idVariazAutobus: number;
    telaioSelected: string;
    disableTelaio: boolean = false;
    loadedContribuzioneToUpdate: boolean = false;
    isSaved: boolean = false;
    idContribuzioneSaved: number;
    listOfDeletedIdVociCosto: any[] = [];
    listOfDeletedIdDatiFattura: any[] = [];
    listOfDeletedIdDatiBonifico: any[] = [];

    //ITER PROCEDIMENTO
    statoTransizioneA: number = 0; // Usato per mostrare l'iter o l'errore
    statoTransizioneB: number = 0; // Usato per mostrare l'iter o l'errore
    transizioniAutomaA: Array<TransizioneAutomaVO>;
    transizioneAutomaA: TransizioneAutomaVO;
    transizioneGroupA: FormGroup = new FormGroup({ transizioneForm: new FormControl() });
    notaTransizioneA: string = "";
    dettaglioRichiesta: DettaglioRichiestaVO;
    loadedTransizioniAutomaA: boolean = false;
    isContrattoChangedA: boolean = false;
    transizioniAutomaB: Array<TransizioneAutomaVO>;
    transizioneAutomaB: TransizioneAutomaVO;
    transizioneGroupB: FormGroup = new FormGroup({ transizioneForm: new FormControl() });
    notaTransizioneB: string = "";
    loadedTransizioniAutomaB: boolean = false;
    isContrattoChangedB: boolean = false;
    richiestaConfermata: boolean;
    azione: string;
    loadedTransizione: boolean = false;

    disabledParteA: boolean = false;
    disabledParteB: boolean = false;






    constructor(private contribuzioneService: ContribuzioneService, private documentService: DocumentService,
        private route: ActivatedRoute, private procedimentoService: ProcedimentoService, private autobusService: AutobusService, private userService: UserService,
        private router: Router, public snackBar: MatSnackBar, public dialog: MatDialog, private cdr: ChangeDetectorRef,) {
    }
    ngOnDestroy(): void {
        this.contribuzioneService.resetFields();
    }

    ngOnInit(): void {
        this.idVariazAutobus = this.contribuzioneService.idVariazAutobus;
        this.idProcedimentoUpdate = this.contribuzioneService.idProcedimento;
        this.telaioSelected = this.contribuzioneService.primoTelaio;
        // se viene passato solo l'id del procedimento senza il telaio
        // effettuo una chiamata per recuperare il telaio
        if (config.isNullOrVoid(this.telaioSelected) && !config.isNullOrVoid(this.idProcedimentoUpdate)) {
            this.contribuzioneService.getTelaioByIdProcedimento(this.idProcedimentoUpdate).subscribe(data => {
                this.telaioSelected = data.response;
            });
        }
        this.userService.funzionarioVo$.subscribe(data => {
            if (data.ruolo === null) {
                // FIX ERRORE RUOLO
                this.userService.funzionarioVo$.takeLast(1).subscribe(data => {
                    if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AMP || data.ruolo.codiceRuolo === CodeRoles.RUOLO_REGIONE) {
                        // I CAMPI ABILITATI SONO QUELLI PER AMP O RP
                        this.isAzienda = false;
                        this.isAmpOrRp = true;
                    } else if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AZIENDA || data.ruolo.codiceRuolo === CodeRoles.RUOLO_PILOTA_AZIENDA) {
                        // I CAMPI ABILITATI SONO QUELLI PER AZIENDA
                        this.isAzienda = true;
                        this.isAmpOrRp = false;
                    }
                });
            } else {
                if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AMP || data.ruolo.codiceRuolo === CodeRoles.RUOLO_REGIONE) {
                    // I CAMPI ABILITATI SONO QUELLI PER AMP O RP
                    this.isAzienda = false;
                    this.isAmpOrRp = true;
                } else if (data.ruolo.codiceRuolo === CodeRoles.RUOLO_AZIENDA || data.ruolo.codiceRuolo === CodeRoles.RUOLO_PILOTA_AZIENDA) {
                    // I CAMPI ABILITATI SONO QUELLI PER AZIENDA
                    this.isAzienda = true;
                    this.isAmpOrRp = false;
                }
            }
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
        });
        this.loadDropDownContribuzione();
        if (!config.isNullOrVoid(this.idProcedimentoUpdate)) {
            this.getContribuzioneForUpdate();
            this.getDocAutobus();
        }
        //Carico la carta di circolazione per l'iserimento treamite matita
        if (!config.isNullOrVoid(this.telaioSelected) && config.isNullOrVoid(this.idProcedimentoUpdate)) {
            this.getDocAutobus();
        }
    }

    ngAfterContentChecked(): void {
        this.cdr.detectChanges();
    }


    loadedPage() {
        if (
            !config.isNullOrVoid(this.tipoProcedimento) &&
            !config.isNullOrVoid(this.numProcedimento) &&
            (!config.isNullOrVoid(this.documenti) && this.documenti.length > 0) &&
            (!config.isNullOrVoid(this.listOfVociCosto) && this.listOfVociCosto.length > 0) &&
            (!config.isNullOrVoid(this.listOfTipiDocumentoQuietanza) && this.listOfTipiDocumentoQuietanza.length > 0) &&
            (!config.isNullOrVoid(this.listOfTipiSostituzione) && this.listOfTipiSostituzione.length > 0) &&
            (!config.isNullOrVoid(this.documentiContribuzione) && this.documentiContribuzione.length > 0)
        ) {
            return false;
        }
        if (
            config.isNullOrVoid(this.idProcedimentoUpdate) &&
            (!config.isNullOrVoid(this.listOfTelai) && this.listOfTelai.length > 0)
        ) {
            return true;
        }
        if (
            (!config.isNullOrVoid(this.idProcedimentoUpdate) ||
                !config.isNullOrVoid(this.idContribuzioneSaved)
            ) &&
            this.loadedContribuzioneToUpdate &&
            this.loadedTransizione
        ) {
            return false;
        }
        return true;

    }


    private _filter(value: string): string[] {
        const filterValue = value.toLowerCase();

        return this.listOfTelai.filter(option => option.toLowerCase().includes(filterValue));
    }

    calculateContributoPubblico() {
        this.contributoPubblico = 0;
        if (this.assegnazioneRisorse.contributoStatale != null) {
            this.contributoPubblico += this.assegnazioneRisorse.contributoStatale;
        }
        if (this.assegnazioneRisorse.contributoRegionaleAgg != null) {
            this.contributoPubblico += this.assegnazioneRisorse.contributoRegionaleAgg;
        }
    }

    calculateContributoStataleErogato() {
        this.assegnazioneRisorse.contributoStatale = 0;
        if (!config.isNullOrVoid(this.datoSpesa.importoAnticipo)) {
            this.assegnazioneRisorse.contributoStatale += this.datoSpesa.importoAnticipo;
        }
        if (!config.isNullOrVoid(this.datoSpesa.importoSaldo)) {
            this.assegnazioneRisorse.contributoStatale += this.datoSpesa.importoSaldo;
        }
        this.calculateContributoPubblico();
    }

    // Svuoto i campi se il cig e' null, undefined o stringa vuota
    checkCig() {
        if (config.isNullOrVoid(this.ordineAcquisto.cig)) {
            this.ordineAcquisto.dataAggiudicazione = undefined;
            this.ordineAcquisto.dataStipula = undefined;
            this.ordineAcquisto.numeroOrdine = undefined;
            this.ordineAcquisto.dataOrdine = undefined;
        }
    }

    //*****************************************************************  DROP DOWN  ************************************************************************/
    loadFontiFinanziamento() {
        if (this.idAttoAssegnazioneRisorse) {
            this.subscribers = this.contribuzioneService.getAllFonteFinanziamentoByIdAttoAssegnazione(this.idAttoAssegnazioneRisorse).subscribe(
                (data) => { this.listOfFonteFinanziamento = data },
                (err) => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
        }
    }

    loadDropDownContribuzione() {
        this.loadedTipoProcedimento = false;
        this.loadedNumProcedimento = false;
        this.subscribers = this.autobusService.getAutobusForContribuzioneAzienda().subscribe(
            (data) => {
                this.listOfTelai = data;
                // Se la lsita dei telai è vuota mostro l'errore 
                if (this.listOfTelai.length <= 0) {
                    this.myControl.markAsTouched();
                }

                this.filteredOptions = this.myControl.valueChanges.pipe(
                    startWith(''),
                    map(value => this._filter(value)),
                );
                if (!config.isNullOrVoid(this.telaioSelected)) {
                    this.disableTelaio = true;
                    this.myControl.setValue(this.telaioSelected);
                }
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );
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

        this.documentService.elencoDocumento(1);

        this.subscribers = this.documentService.elencoDocumento$.subscribe(
            (data) => this.documenti = data,
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            });

        this.subscribers = this.contribuzioneService.getAllVoceCostoContribuzione().subscribe(
            (data) => {
                this.listOfVociCosto = data;
                this.listOfVociCosto.forEach(element => {
                    if (element.idVoceCosto == 1) {
                        element.descVoceCosto = element.descVoceCosto + " *"
                    }
                });
                //lista che mostro da cui rimuovo e aggiungo le voci di costo in base a quelle che vengono scelte
                this.listOfVociCostoToShow = JSON.parse(JSON.stringify(this.listOfVociCosto));
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

        this.subscribers = this.contribuzioneService.getAllAttoAssegnazioneRisorse().subscribe(
            (data) => {
                this.listOfAttiAssegnazioneRisorse = data;
                if (this.listOfAttiAssegnazioneRisorse.length <= 0) {
                    this.contribuzioneForm.form.get('idAttoAssegnazione').markAsTouched();
                }
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

        this.subscribers = this.contribuzioneService.getAllTipoDocumentoQuietanza().subscribe(
            (data) => {
                this.listOfTipiDocumentoQuietanza = data;
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

        this.subscribers = this.contribuzioneService.getAllTipoSostituzione().subscribe(
            (data) => this.listOfTipiSostituzione = data,
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

        this.documentService.elencoDocumento(5);
        this.subscribers = this.documentService.elencoDocumentoContribuzione$.subscribe(
            (data) => {
                this.documentiContribuzione = data;
                this.loadLabelDocumenti();
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

        this.subscribers = this.contribuzioneService.getTelaiVeicoloDaSostituire().subscribe(
            (data) => {
                this.listOfTelaiVeicoloDaSostituire = data;
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );  
    }

    setErrorVeicoloDaSostituire() {
        if (this.listOfTelaiVeicoloDaSostituire.length <= 0) {
            this.contribuzioneForm.form.get('veicoloDaSostituire').setErrors({
                "emptyList": true
            })
            this.contribuzioneForm.form.get('veicoloDaSostituire').markAsTouched();
        }
    }

    //--------------------------------------------- SEZIONE VOCI COSTO FORNITURE -----------------------------------------------

    addToVociCostoForniturte() {
        // controllo che i campi idVoceCosto e importo
        if (this.idVoceCosto != null && this.importoVoceCosto != null) {
            let desc = this.listOfVociCostoToShow.find(a => a.idVoceCosto == this.idVoceCosto).descVoceCosto;
            // creo la voce di costo da mostrare nella lista
            let voce = {
                idVoceCosto: this.idVoceCosto,
                descVoceCosto: desc,
                importo: this.importoVoceCosto
            }
            this.listOfVoceCostoFornituraToShow.push(voce);
            // rimuovo la voce di costo dalla drop down
            let index = this.listOfVociCostoToShow.findIndex(a => a.idVoceCosto == this.idVoceCosto);
            this.listOfVociCostoToShow.splice(index, 1);
            // setto a null, dopo l'inserimento nella lista, la voce e l'importo
            this.idVoceCosto = null;
            this.importoVoceCosto = null;
        }
    }

    removeVociCostoForniturte(index: any) {
        //cerco la voce di costo da reinserire nella dorp down e ordino l'array in base all id voce costo
        let voce = this.listOfVociCosto.find(a => a.idVoceCosto == this.listOfVoceCostoFornituraToShow[index].idVoceCosto);
        // controllo se la voce costo è presente nelle fatture
        let voceCostoUtilizzata = this.listOfDatoFatturaToShow.find(a => a.listaIdVoceCosto.includes(voce.idVoceCosto));
        //controllo che la voce non venga utilizzata, ma ancora non è stata aggiunta la fattura alla lista
        let voceCostoUtilizzata2 = this.listOggettoFatturaDatoSpesa.find(a => a.idVoceCosto == voce.idVoceCosto);
        //  se la voce costo è presente nelle fatture mostro il dialog e non elimino la voce
        if (!config.isNullOrVoid(voceCostoUtilizzata) || !config.isNullOrVoid(voceCostoUtilizzata2)) {
            this.dialog.open(CancellaDialogComponent, {
                width: '450px',
                data: {
                    msg: 'Per eliminare la voce costo selezionata è necessario eliminare la fattura a questa relativa!',
                    showConferma: false,
                }
            });
            return;
        }
        //se l'idContribuzioneSaved è valorizzato mi salvo l'idVoceCostoFornitura da passare al BE per eliminare la voce 
        if (!config.isNullOrVoid(this.idContribuzioneSaved) && !config.isNullOrVoid(this.listOfVoceCostoFornituraToShow[index].idVoceCostoFornitura)) {
            this.listOfDeletedIdVociCosto.push(this.listOfVoceCostoFornituraToShow[index].idVoceCostoFornitura)
        }
        this.listOfVociCostoToShow.push(voce);
        // rimuovo la voce dalla drop down degli oggetti fattura
        let indexOggettofattura = this.listOfOggettoFattura.findIndex(a => a.idVoceCosto == voce.idVoceCosto);
        this.listOfOggettoFattura.splice(indexOggettofattura, 1);
        this.lengthVociCosto = this.lengthVociCosto - 1;
        this.listOfVociCostoToShow = this.listOfVociCostoToShow.sort((a, b) => (a.idVoceCosto < b.idVoceCosto ? -1 : 1));
        // rimuovo l'oggetto dalla lista delle voci tramite l'index
        this.listOfVoceCostoFornituraToShow.splice(index, 1);
    }

    // creo la lista delle voci di costo forniture da inviare al BE(metodo richiamto nel salva)
    createListaVoceCostoFornituraToSave() {
        this.listOfVoceCostoFornitura = [];
        //inserisco nella lista delle voci forniture id e voce se non ha cliccato sul pulsante aggiungi
        if (this.idVoceCosto != null && this.importoVoceCosto != null) {
            let voce: any = {
                idVoceCosto: this.idVoceCosto,
                importo: this.importoVoceCosto,
            }
            this.listOfVoceCostoFornitura.push(voce);
        }
        //inserisco nella lista delle voci forniture le voci presenti nella lista aggiunte tramite il pulsante aggiungi
        if (this.listOfVoceCostoFornituraToShow.length > 0) {
            for (const element of this.listOfVoceCostoFornituraToShow) {
                let voce: any = {
                    idVoceCosto: element.idVoceCosto,
                    importo: element.importo,
                    idVoceCostoFornitura: element.idVoceCostoFornitura,
                    idCostoFornitura: element.idCostoFornitura
                }
                this.listOfVoceCostoFornitura.push(voce);
            }
        }
    }

    //--------------------------------------------- SEZIONE OGGETTI FATTURA ----------------------------------------

    //Abilità i campi di documentazione spesa quando viene inserita una voce costo nella lista
    //oppure quando viene selezionata una voce costo e compilato il campo importo
    enableOggettoFattura() {
        if (this.listOfOggettoFattura.length > 0 ||
            (!config.isNullOrVoid(this.idVoceCosto) && !config.isNullOrVoid(this.importoVoceCosto))) {
            return false;
        }
        return true;
    }

    enableBonifico() {
        if (this.listOfDatoFatturaToShow.length > 0 ||
            (!config.isNullOrVoid(this.datoFattura.numero) &&
                !config.isNullOrVoid(this.datoFattura.data) &&
                this.listOggettoFatturaDatoSpesa.length > 0 &&
                !config.isNullOrVoid(this.datoFattura.importo) &&
                !config.isNullOrVoid(this.documentoFatturaSpesa.nomeFile)
            )) {
            return false;
        }
        return true;
    }

    createOggettoFattura() {
        if (this.idVoceCosto != null && this.importoVoceCosto != null) {
            let oggettoFattura = this.listOfVociCostoToShow.find(a => a.idVoceCosto == this.idVoceCosto);
            if (this.listOfVoceCostoFornituraToShow.length == this.lengthVociCosto) {
                if (this.listOfOggettoFattura.length > 0) {
                    this.listOfOggettoFattura.splice(this.listOfOggettoFattura.length - 1, 1);
                }
                this.listOfOggettoFattura.push(oggettoFattura);
            } else {
                this.lengthVociCosto = this.listOfVoceCostoFornituraToShow.length;
                this.listOfOggettoFattura.push(oggettoFattura);
            }
        }
    }

    datoFatturaValid() {
        if (
            !config.isNullOrVoid(this.datoFattura.numero) &&
            !config.isNullOrVoid(this.datoFattura.data) &&
            !config.isNullOrVoid(this.datoFattura.importo) &&
            !config.isNullOrVoid(this.documentoFatturaSpesa.nomeFile) &&
            this.listOggettoFatturaDatoSpesa.length > 0
        ) {
            return true;
        } else {
            return false;
        }
    }

    addListaDatoFattura() {
        let dato: DatoFatturaVO = new DatoFatturaVO();
        if (this.datoFatturaValid()) {
            let descOggetto: string = this.listOggettoFatturaDatoSpesa.map((a: any) => a.descVoceCosto).join(", ")
            // creo l'oggetto per la lista dato fattura
            dato.numero = this.datoFattura.numero;
            dato.data = new Date(this.datoFattura.data);
            dato.vociCosto = descOggetto;
            dato.listaIdVoceCosto = this.listOggettoFatturaDatoSpesa.map(a => a.idVoceCosto);
            dato.importo = this.datoFattura.importo;
            dato.documento = null;

            // inserisco il documento
            if (this.documentoFatturaSpesa.nomeFile != null) {
                dato.documento = this.documentoFatturaSpesa;
            }
            this.listOfDatoFatturaToShow.push(dato);
            // rimuovo le voci selezionate dalla lista degli oggetti fattura
            dato.listaIdVoceCosto.forEach(element => {
                let index = this.listOfOggettoFattura.findIndex(a => a.idVoceCosto == element);
                this.listOfOggettoFattura.splice(index, 1);
            });
            // svuoto i campi per l'oggetto dato fattura
            this.datoFattura = new DatoFatturaVO();
            this.listOggettoFatturaDatoSpesa = [];
            this.documentoFatturaSpesa = new DocContribuzioneVO();
            this.addToVociCostoForniturte();
        }
        this.loadLabelDocumenti();
        this.createOggettoBonifico();
    }

    datoQuietanzaValid() {
        if (
            !config.isNullOrVoid(this.datoFattura.idTipoDocQuietanza) &&
            !config.isNullOrVoid(this.datoFattura.nrQuietanzaAzForn) &&
            !config.isNullOrVoid(this.datoFattura.dataQuietanzaAzForn) &&
            !config.isNullOrVoid(this.documentoQuietanza.nomeFile)
        ) {
            return true;
        } else {
            return false;
        }
    }

    addListQuietanza() {
        let dato: any;
        //  QUIETANZA
        if (this.datoQuietanzaValid()) {
            let tipoDoc = this.listOfTipiDocumentoQuietanza.find(a => a.idTipoDocQuietanza == this.datoFattura.idTipoDocQuietanza).descTipoDocQuietanza;
            dato = {
                tipoDoc: tipoDoc,
                idTipoDocQuietanza: this.datoFattura.idTipoDocQuietanza,
                nrQuietanzaAzForn: this.datoFattura.nrQuietanzaAzForn,
                dataQuietanzaAzForn: new Date(this.datoFattura.dataQuietanzaAzForn),
                documento: null,
            }
            if (this.documentoQuietanza.nomeFile != null) {
                dato.documento = this.documentoQuietanza;
            }
            this.listOfDatoQuietanzaToShow.push(dato);
            // svuoto i campi dopo l'iserimento
            this.datoFattura = new DatoFatturaVO();
            this.documentoQuietanza = new DocContribuzioneVO();
        }
        this.loadLabelDocumenti();
    }

    //rimuovo dalla lista l'oggetto
    removeListaDatoFattura(index: any) {
        let fattura = this.listOfDatoFatturaToShow.find(a => a.numero == this.listOfDatoFatturaToShow[index].numero &&
            new Date(a.data).toLocaleDateString() == new Date(this.listOfDatoFatturaToShow[index].data).toLocaleDateString());
        // controllo se la fattura e' presente nei bonifici
        let voceFatturaUtilizzata = this.listOfDatoBonificoToShow.find(
            a => a.listFatture.find(b => b.numero == fattura.numero &&
                new Date(b.data).toLocaleDateString() == new Date(fattura.data).toLocaleDateString()));
        //controllo se la voce viene utilizzata, ma ancora non e' stata aggiunta alla lista
        let voceFatturaUtilizzata2 = this.listFatturaBonifico.find(a => a.numero == fattura.numero &&
            new Date(a.data).toLocaleDateString() == new Date(fattura.data).toLocaleDateString());
        //  se la fattura è presente ni bonifici mostro il dialog e non elimino la voce
        if (!config.isNullOrVoid(voceFatturaUtilizzata) || !config.isNullOrVoid(voceFatturaUtilizzata2)) {
            this.dialog.open(CancellaDialogComponent, {
                width: '450px',
                data: {
                    msg: 'Per eliminare la fattura selezionata è necessario eliminare il bonifico a questo relativo!',
                    showConferma: false,
                }
            });
            return;
        }
        // inserisco l'oggetto fattura nella lista
        //se l'idContribuzioneSaved è valorizzato mi salvo l'idDatoFattura da passare al BE per eliminare la voce 
        if (!config.isNullOrVoid(this.idContribuzioneSaved)) {
            this.listOfDeletedIdDatiFattura.push(this.listOfDatoFatturaToShow[index].idDatoFattura)
        }
        if (this.listOfDatoFatturaToShow.length > 0) {
            this.listOfDatoFatturaToShow[index].listaIdVoceCosto.forEach(element => {
                let oggettoFattura = this.listOfVoceCostoFornituraToShow.find(a => a.idVoceCosto == element);
                this.listOfOggettoFattura.push(oggettoFattura);
            });
            // rimuovo la voce dalla lista dell'oggetto bonifico
            this.removeOggettoBonifico(this.listOfDatoFatturaToShow[index])
            // rimuovo la voce dato fattura 
            this.listOfDatoFatturaToShow.splice(index, 1);
        }
    }
    //rimuovo dalla lista l'oggetto
    removeListaDatoQuietanza(index: any) {
        if (!config.isNullOrVoid(this.idContribuzioneSaved) && !config.isNullOrVoid(this.listOfDatoQuietanzaToShow[index].idDatoFattura)) {
            this.listOfDeletedIdDatiFattura.push(this.listOfDatoQuietanzaToShow[index].idDatoFattura)
        }
        if (this.listOfDatoQuietanzaToShow.length > 0) {
            // rimuovo la voce quietanza 
            this.listOfDatoQuietanzaToShow.splice(index, 1);
        }
    }

    // metodo di download del file dalla lista 
    downloadDocOggettoFattura(index: any, tipoDoc: any, idDoc: number) {
        let oggetto;
        if (tipoDoc === "fattura") {
            oggetto = this.listOfDatoFatturaToShow[index];
        } else {
            oggetto = this.listOfDatoQuietanzaToShow[index];
        }
        let nameDoc = oggetto.documento.nomeFile;
        let file = oggetto.documento.documento;
        if (!config.isNullOrVoid(idDoc)) {
            this.downloadDocModifica(idDoc, nameDoc);
            return;
        }
        saveAs(file, nameDoc);
    }

    createListaDatoFatturaToSave() {
        this.listOfDatoFattura = [];
        if (this.listOfDatoFatturaToShow.length > 0) {
            this.listOfDatoFatturaToShow.forEach(async element => {
                let datoFattura: DatoFatturaVO = new DatoFatturaVO();
                datoFattura.numero = element.numero || null;
                datoFattura.data = new Date(element.data) || null;
                datoFattura.importo = element.importo || null;
                datoFattura.idDatoFattura = element.idDatoFattura;
                datoFattura.idDatoSpesa = element.idDatoSpesa;
                datoFattura.idDocContribuzione = element.idDocContribuzione;
                datoFattura.idTipoDocQuietanza = element.idTipoDocQuietanza;
                datoFattura.listaBonifici = element.listaBonifici;
                if (element.documento != null) {
                    // clono l'oggetto per evitare che venga modificato anche quello nella lista                  
                    datoFattura.documento = Object.assign({}, element.documento);
                    datoFattura.documento.documento = await this.getByteArrayForBE(datoFattura.documento.documento);
                }
                if (element.listaIdVoceCosto.length > 0) {
                    element.listaIdVoceCosto.forEach(voceCosto => {
                        datoFattura.listaIdVoceCosto.push(voceCosto)
                    });
                }
                this.listOfDatoFattura.push(datoFattura);
            });
        }
        if (this.listOfDatoQuietanzaToShow.length > 0) {
            this.listOfDatoQuietanzaToShow.forEach(async element => {
                let datoFattura: DatoFatturaVO = new DatoFatturaVO();
                datoFattura.nrQuietanzaAzForn = element.nrQuietanzaAzForn || null;
                datoFattura.dataQuietanzaAzForn = element.dataQuietanzaAzForn || null;
                datoFattura.idTipoDocQuietanza = element.idTipoDocQuietanza || null;
                datoFattura.idDatoFattura = element.idDatoFattura;
                datoFattura.idDatoSpesa = element.idDatoSpesa;
                datoFattura.idDocContribuzione = element.idDocContribuzione;
                datoFattura.idTipoDocQuietanza = element.idTipoDocQuietanza;
                if (element.documento != null) {
                    datoFattura.documento = Object.assign({}, element.documento);
                    datoFattura.documento.documento = await this.getByteArrayForBE(datoFattura.documento.documento);
                }
                this.listOfDatoFattura.push(datoFattura);
            });
        }
    }

    //************************************************************  SEZIONE BONIFICI  *******************************************************************/

    //Creo gli oggetti da mostrare nella drop down oggetto bonifico 
    createOggettoBonifico() {
        this.listOfOggettoBonifico = [];
        this.listOfDatoFatturaToShow.forEach(element => {
            let dato: any = {
                numero: element.numero,
                data: new Date(element.data),
                importo: element.importo,
            }
            this.listOfOggettoBonifico.push(dato);
        });
        if (!config.isNullOrVoid(this.datoFattura.numero) &&
            !config.isNullOrVoid(this.datoFattura.data) &&
            this.listOggettoFatturaDatoSpesa.length > 0 &&
            !config.isNullOrVoid(this.datoFattura.importo) &&
            !config.isNullOrVoid(this.documentoFatturaSpesa.nomeFile)
        ) {
            let dato: any = {
                numero: this.datoFattura.numero,
                data: new Date(this.datoFattura.data),
                importo: this.datoFattura.importo,
            }
            this.listOfOggettoBonifico.push(dato);
        }
    }

    removeOggettoBonifico(oggettoBonifico: any) {
        //cerco l'oggetto da rimuover dalla drop down
        let index = this.listOfOggettoBonifico.findIndex(
            a => a.numero == oggettoBonifico.numero &&
                a.importo == oggettoBonifico.importo &&
                new Date(a.data).toLocaleDateString() == new Date(oggettoBonifico.data).toLocaleDateString());
        this.listOfOggettoBonifico.splice(index, 1);
    }

    addListaDatoBonifico() {
        let dato: any;
        if (this.datoBonificoValid()) {
            this.addListaDatoFattura();
            let numeriFatture: string = this.listFatturaBonifico.map((a: any) => a.numero).join(", Fattura Nr: ")
            // this.listFatturaBonifico.forEach(element => {
            //     ele
            // });
            this.listFatturaBonifico.forEach(element => {
                let index = this.listOfDatoFatturaToShow.findIndex(a => a.numero == element.numero &&
                    new Date(a.data).toLocaleDateString() == new Date(element.data).toLocaleDateString());
                if (!config.isNullOrVoid(index)) {
                    this.listOfDatoFatturaToShow[index].listaBonifici.push(this.datoBonifico);
                }
            });
            // creo l'oggetto per la lista dato bonifico
            dato = {
                data: new Date(this.datoBonifico.dataBonifico),
                importo: this.datoBonifico.importoBonifico,
                cro: this.datoBonifico.cro,
                numeriFatture: "Fattura Nr: " + numeriFatture,
                listFatture: this.listFatturaBonifico,
            }
            this.listOfDatoBonificoToShow.push(dato);
            // svuoto i campi per l'oggetto dato fattura
            this.datoBonifico = new DatoBonificoVO();
            this.listFatturaBonifico = [];
            // this.addListaDatoFattura();
        }
    }

    datoBonificoValid() {
        if (
            !config.isNullOrVoid(this.datoBonifico.importoBonifico) &&
            !config.isNullOrVoid(this.datoBonifico.dataBonifico) &&
            !config.isNullOrVoid(this.datoBonifico.cro) &&
            this.listFatturaBonifico.length > 0
        ) {
            return true;
        } else {
            return false;
        }
    }

    //rimuovo dalla lista l'oggetto
    removeListaDatoBonifico(index: any) {
        if (!config.isNullOrVoid(this.idContribuzioneSaved) && !config.isNullOrVoid(this.listOfDatoBonificoToShow[index].idDatoBonifico)) {
            this.listOfDeletedIdDatiBonifico.push(this.listOfDatoBonificoToShow[index].idDatoBonifico)
        }
        if (this.listOfDatoBonificoToShow.length > 0) {
            this.listOfDatoBonificoToShow.splice(index, 1);
        }
    }

    createListOfBonificiForGet(list: DatoFatturaVO[]) {
        let dato: any;
        let listBonifici = [];
        list = list.filter(a => a.idTipoDocQuietanza == null && a.listaBonifici.length > 0);
        list.forEach(element => {
            // INSERISCO TUTTI I BONIFICI IN UNA LISTA 
            listBonifici = listBonifici.concat(element.listaBonifici);
            // RIMUOVO GLI OGGETTI BONIFICO DOPPI DALL'ARRAY
            listBonifici = listBonifici.filter((test, index, array) =>
                index === array.findIndex((findTest) =>
                    findTest.idDatoBonifico === test.idDatoBonifico
                )
            );
        });
        listBonifici.forEach((bonifico) => {
            let listFatture = [];
            this.listOfDatoFatturaToShow.forEach(element => {
                // CONTROLLO SE NELLA FATTURA E' CONTENUTO IL BONIFICO
                if (element.listaBonifici.some(a => a.idDatoBonifico == bonifico.idDatoBonifico)) {
                    // INSERISCO IL BONIFICO
                    listFatture.push(element)
                }
            })
            // CREO LA STRINGA PER L'OGGETTO BONIFICO
            let numeriFatture: string = listFatture.map((a: any) => a.numero).join(", Fattura Nr: ")
            dato = {
                data: new Date(bonifico.dataBonifico),
                importo: bonifico.importoBonifico,
                cro: bonifico.cro,
                idDatoBonifico: bonifico.idDatoBonifico,
                numeriFatture: "Fattura Nr: " + numeriFatture,
                listFatture: listFatture,
            }
            this.listOfDatoBonificoToShow.push(dato)
        })
    }

    //*****************************************************************  DOCUMENTI  ************************************************************************/

    loadLabelDocumenti() {
        // DOCUMENTAZIONE CONTRATTUALE
        let tipoDocVoceCosto = this.documentiContribuzione.find(a => a.idTipoDocumento == 40);
        this.documentoVoceCosto.idTipoDocumento = tipoDocVoceCosto.idTipoDocumento || null;
        this.documentoVoceCosto.label = tipoDocVoceCosto.descrizione || null;
        this.documentoVoceCosto.descEstesa = tipoDocVoceCosto.descEstesa || null;
        // DOCUMENTO FATTURA
        let tipoDocFattura = this.documentiContribuzione.find(a => a.idTipoDocumento == 41);
        this.documentoFatturaSpesa.idTipoDocumento = tipoDocFattura.idTipoDocumento || null;
        this.documentoFatturaSpesa.label = tipoDocFattura.descrizione || null;
        this.documentoFatturaSpesa.descEstesa = tipoDocFattura.descEstesa || null;
        // DOCUMENTO QUIETANZA
        let tipoDocQuietanza = this.documentiContribuzione.find(a => a.idTipoDocumento == 42);
        this.documentoQuietanza.idTipoDocumento = tipoDocQuietanza.idTipoDocumento || null;
        this.documentoQuietanza.label = tipoDocQuietanza.descrizione || null;
        this.documentoQuietanza.descEstesa = tipoDocQuietanza.descEstesa || null;
        // DOCUMENTO CARTA CIRCOLAZIONE
        let tipoDocCartaCircolazione: any = this.documenti.find(a => a.idTipoDocumento == 1);
        this.documentoCartaCircolazione.idTipoDocumento = tipoDocCartaCircolazione.idTipoDocumento || null;
        this.documentoCartaCircolazione.label = tipoDocCartaCircolazione.descrizione || null;
        this.documentoCartaCircolazione.descEstesa = tipoDocCartaCircolazione.descEstesa || null;
        // DOCUMENTO ATTO D'OBBLIGO
        let tipoDocAttoObbligo = this.documentiContribuzione.find(a => a.idTipoDocumento == 44);
        this.documentoAttoObbligo.idTipoDocumento = tipoDocAttoObbligo.idTipoDocumento || null;
        this.documentoAttoObbligo.label = tipoDocAttoObbligo.descrizione || null;
        this.documentoAttoObbligo.descEstesa = tipoDocAttoObbligo.descEstesa || null;
        // DOCUMENTO GARANZIA
        let tipoDocGaranzia = this.documentiContribuzione.find(a => a.idTipoDocumento == 43);
        this.documentoGaranzia.idTipoDocumento = tipoDocGaranzia.idTipoDocumento || null;
        this.documentoGaranzia.label = tipoDocGaranzia.descrizione || null;
        this.documentoGaranzia.descEstesa = tipoDocGaranzia.descEstesa || null;
        // DOCUMENTO ALIENAZIONE
        let tipoDocAlienazione = this.documentiContribuzione.find(a => a.idTipoDocumento == 45);
        this.documentoAlienazione.idTipoDocumento = tipoDocAlienazione.idTipoDocumento || null;
        this.documentoAlienazione.label = tipoDocAlienazione.descrizione || null;
        this.documentoAlienazione.descEstesa = tipoDocAlienazione.descEstesa || null;
        // DOCUMENTO MISURA EMISSIONI
        let tipoDocMisuraEmissioni = this.documentiContribuzione.find(a => a.idTipoDocumento == 46);
        this.documentoMisuraEmissioni.idTipoDocumento = tipoDocMisuraEmissioni.idTipoDocumento || null;
        this.documentoMisuraEmissioni.label = tipoDocMisuraEmissioni.descrizione || null;
        this.documentoMisuraEmissioni.descEstesa = tipoDocMisuraEmissioni.descEstesa || null;
    }

    uploadDoc(files: File[], value: string) {
        switch (value) {
            case 'voceCosto':
                this.documentoVoceCosto.nomeFile = files[0].name;
                this.documentoVoceCosto.documento = files[0];
                break;
            case 'fatturaSpesa':
                this.documentoFatturaSpesa.nomeFile = files[0].name;
                this.documentoFatturaSpesa.documento = files[0];
                break;
            case 'cartaCircolazione':
                this.documentoCartaCircolazione.nomeFile = files[0].name;
                this.documentoCartaCircolazione.documento = files[0];
                break;
            case 'attoObbligo':
                this.documentoAttoObbligo.nomeFile = files[0].name;
                this.documentoAttoObbligo.documento = files[0];
                break;
            case 'garanzia':
                this.documentoGaranzia.nomeFile = files[0].name;
                this.documentoGaranzia.documento = files[0];
                break;
            case 'alienazione':
                this.documentoAlienazione.nomeFile = files[0].name;
                this.documentoAlienazione.documento = files[0];
                break;
            case 'quietanza':
                this.documentoQuietanza.nomeFile = files[0].name;
                this.documentoQuietanza.documento = files[0];
                break;
            case 'misuraEmissioni':
                this.documentoMisuraEmissioni.nomeFile = files[0].name;
                this.documentoMisuraEmissioni.documento = files[0];
                break;

            default:
                break;
        }
    }

    eliminaDoc(value: string) {
        switch (value) {
            case 'voceCosto':
                this.documentoVoceCosto = new DocContribuzioneVO();
                break;
            case 'fatturaSpesa':
                this.documentoFatturaSpesa = new DocContribuzioneVO();
                break;
            case 'cartaCircolazione':
                this.documentoCartaCircolazione = new DocContribuzioneVO();
                break;
            case 'attoObbligo':
                this.documentoAttoObbligo = new DocContribuzioneVO();
                break;
            case 'garanzia':
                this.documentoGaranzia = new DocContribuzioneVO();
                break;
            case 'alienazione':
                this.documentoAlienazione = new DocContribuzioneVO();
                break;
            case 'quietanza':
                this.documentoQuietanza = new DocContribuzioneVO();
                break;
            case 'misuraEmissioni':
                this.documentoMisuraEmissioni = new DocContribuzioneVO();
                break;

            default:
                break;
        }
        this.loadLabelDocumenti();
    }

    downloadDoc(value: string, idDoc?: number) {
        let nameDoc: string;
        let file: any;
        switch (value) {
            case 'voceCosto':
                nameDoc = this.documentoVoceCosto.nomeFile;
                file = this.documentoVoceCosto.documento;
                break;
            case 'fatturaSpesa':
                nameDoc = this.documentoFatturaSpesa.nomeFile;
                file = this.documentoFatturaSpesa.documento;
                break;
            case 'cartaCircolazione':
                nameDoc = this.documentoCartaCircolazione.nomeFile;
                file = this.documentoCartaCircolazione.documento;
                break;
            case 'attoObbligo':
                nameDoc = this.documentoAttoObbligo.nomeFile;
                file = this.documentoAttoObbligo.documento;
                break;
            case 'garanzia':
                nameDoc = this.documentoGaranzia.nomeFile;
                file = this.documentoGaranzia.documento;
                break;
            case 'alienazione':
                nameDoc = this.documentoAlienazione.nomeFile;
                file = this.documentoAlienazione.documento;
                break;
            case 'quietanza':
                nameDoc = this.documentoQuietanza.nomeFile;
                file = this.documentoQuietanza.documento;
                break;
            case 'misuraEmissioni':
                nameDoc = this.documentoMisuraEmissioni.nomeFile;
                file = this.documentoMisuraEmissioni.documento;
                break;

            default:
                break;
        }
        if (!config.isNullOrVoid(idDoc)) {
            this.downloadDocModifica(idDoc, nameDoc);
            return;
        }

        saveAs(file, nameDoc);
    }

    downloadDocModifica(idDoc: number, nameDoc: string) {
        let doc;
        this.subscribers = this.contribuzioneService.getDocContribuzione(idDoc).subscribe(
            data => {
                doc = data;
                saveAs(doc, nameDoc);
            }
        );
    }

    // DOWNLOAD CARTA DI CIRCOLAZIONE
    downloadCartaCircolazione() {
        this.documentService.getContenutoDocumentoById(this.contribuzioneService.idVariazAutobus, this.documentoCartaCircolazione.idTipoDocumento)
            .subscribe(
                res => {
                    saveAs(res, this.documentoCartaCircolazione.nomeFile);
                },
                err => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
    }

    // Metodi per la creazione del byte[] da inviare ail BE
    async readFile(file) {
        return new Promise<ArrayBuffer>((resolve, reject) => {
            // Create file reader
            let reader = new FileReader()

            // Register event listeners
            reader.addEventListener("loadend", (e: any) => resolve(e.target.result))
            reader.addEventListener("error", reject)

            // Read file
            reader.readAsArrayBuffer(file)
        })
    }

    async processFile(file) {
        return new Uint8Array(await this.readFile(file))
    }

    // trasformo l'ArrayBuffer in un byte[] da inviare al BE 
    async getByteArrayForBE(file) {
        let array = []
        // let uint8Array: any;
        if (file == null) {
            return null;
        }
        let uint8Array = await this.processFile(file);
        if (uint8Array != null || uint8Array != undefined) {
            for (var i = 0; i < uint8Array.byteLength; i++) {
                array[i] = uint8Array[i];
            }
        }
        return JSON.stringify(array);
    }

    //------------------------------------------------ SEZIONE SALVATAGGIO CONTRIBUZIONE ---------------------------------------

    async mapContribuzioneToSave() {
        this.isSaved = true;
        if (
            !config.isNullOrVoid(this.datoFattura.idTipoDocQuietanza) &&
            !config.isNullOrVoid(this.datoFattura.nrQuietanzaAzForn) &&
            !config.isNullOrVoid(this.datoFattura.dataQuietanzaAzForn) &&
            !config.isNullOrVoid(this.documentoQuietanza.nomeFile)
        ) {
            this.addListQuietanza();
        }
        if (!config.isNullOrVoid(this.datoFattura.numero) &&
            !config.isNullOrVoid(this.datoFattura.data) &&
            !config.isNullOrVoid(this.datoFattura.importo) &&
            !config.isNullOrVoid(this.documentoFatturaSpesa.nomeFile) &&
            this.listOggettoFatturaDatoSpesa.length > 0
        ) {
            this.addListaDatoFattura();
        }
        this.createListaVoceCostoFornituraToSave();
        await this.createListaDatoFatturaToSave();
        this.addListaDatoBonifico();
        this.contribuzioneCompleta.assegnazioneRisorse = this.assegnazioneRisorse;
        this.contribuzioneCompleta.ordineAcquisto = this.ordineAcquisto;
        this.contribuzioneCompleta.costoFornitura = this.costoFornitura;
        this.contribuzioneCompleta.vociCosto = this.listOfVoceCostoFornitura;
        this.contribuzioneCompleta.datoSpesa = this.datoSpesa;
        this.contribuzioneCompleta.datoMessaServizio = this.datoMessaServizio;
        this.contribuzioneCompleta.contribuzione = this.contribuzione;
        this.contribuzioneCompleta.datiFattura = this.listOfDatoFattura;

        this.contribuzioneCompleta.documentoContribuzione = Object.assign({}, this.documentoVoceCosto);
        this.contribuzioneCompleta.documentoContribuzione.documento = await this.getByteArrayForBE(this.documentoVoceCosto.documento) || null;
        this.contribuzioneCompleta.documentoCartaCircolazione = Object.assign({}, this.documentoCartaCircolazione);
        this.contribuzioneCompleta.documentoCartaCircolazione.documento = await this.getByteArrayForBE(this.documentoCartaCircolazione.documento) || null;
        this.contribuzioneCompleta.documentoAttoObbligo = Object.assign({}, this.documentoAttoObbligo);
        this.contribuzioneCompleta.documentoAttoObbligo.documento = await this.getByteArrayForBE(this.documentoAttoObbligo.documento) || null;
        this.contribuzioneCompleta.documentoGaranzia = Object.assign({}, this.documentoGaranzia);
        this.contribuzioneCompleta.documentoGaranzia.documento = await this.getByteArrayForBE(this.documentoGaranzia.documento) || null;
        this.contribuzioneCompleta.documentoAlienazione = Object.assign({}, this.documentoAlienazione);
        this.contribuzioneCompleta.documentoAlienazione.documento = await this.getByteArrayForBE(this.documentoAlienazione.documento) || null;
        this.contribuzioneCompleta.documentoMisureEmissioni = Object.assign({}, this.documentoMisuraEmissioni);
        this.contribuzioneCompleta.documentoMisureEmissioni.documento = await this.getByteArrayForBE(this.documentoMisuraEmissioni.documento) || null;


        //RICHIESTA
        this.richiesta.veicoli = [];
        this.richiesta.tipoProcedimento = this.tipoProcedimento;
        this.richiesta.numProcedimento = this.numProcedimento;
        let veicolo: VeicoloVO = new VeicoloVO();
        if (!config.isNullOrVoid(this.telaioSelected)) {
            veicolo.primoTelaio = this.telaioSelected;
        } else {
            veicolo.primoTelaio = this.myControl.value;
        }
        this.richiesta.veicoli.push(veicolo);
        this.listOfOggettoFattura = [];

        setTimeout(() => {
            if (config.isNullOrVoid(this.idContribuzioneSaved)) {
                this.saveContribuzione();
            } else {
                this.updateContribuzione();
            }
        }, 100);
    }

    async saveContribuzione() {
        this.isSaved = true;
        this.contribuzioneService.insertContribuzione(this.contribuzioneCompleta, this.richiesta).subscribe(data => {
            this.idContribuzioneSaved = data;
            this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
                duration: 2000,
            });
            this.getContribuzioneForUpdate(true);
            this.isSaved = false;
        }, error => {
            CommonsHandleException.authorizationError(error, this.router);
            this.feedback = error.error.message;
            console.log("this.feedback =" + this.feedback);
            if (this.feedback.includes("\n")) {
                this.doubleError = true;
                console.log("this.doubleError =" + this.doubleError);
                this.doubleMessages = this.feedback.split('\n');
                console.log("this.doubleMessages =" + this.doubleMessages);
                this.fristMessages = this.doubleMessages[0];
                console.log("this.fristMessages =" + this.fristMessages);
                this.secondMessages = this.doubleMessages[1];
                console.log("this.secondMessages =" + this.secondMessages);
                this.feedback = "";
                console.log("this.feedback =" + this.feedback);
            }
            this.isSaved = false;
            window.scrollTo(0, 0);
        });
    }

    // PULISCI CAMPI
    resetFields(reset?: any) {

        /* OGGETTI SEZIONI */
        this.assegnazioneRisorse = new AssegnazioneRisorseVO();
        this.ordineAcquisto = new OrdineAcquistoVO();
        this.costoFornitura = new CostoFornituraVO();
        this.datoSpesa = new DatoSpesaVO();
        this.datoFattura = new DatoFatturaVO();
        this.datoMessaServizio = new DatoMessaServizioVO();
        this.contribuzione = new ContribuzioneVO();
        this.contribuzioneCompleta = new ContribuzioneCompletaVO();
        this.datoBonifico = new DatoBonificoVO();
        /* DOCUMENTI */
        this.documentoVoceCosto = new DocContribuzioneVO();
        this.documentoFatturaSpesa = new DocContribuzioneVO();
        this.documentoCartaCircolazione = new DocContribuzioneVO();
        this.documentoAttoObbligo = new DocContribuzioneVO();
        this.documentoGaranzia = new DocContribuzioneVO();
        this.documentoAlienazione = new DocContribuzioneVO();
        this.documentoQuietanza = new DocContribuzioneVO();
        this.documentoMisuraEmissioni = new DocContribuzioneVO();
        this.idAttoAssegnazioneRisorse = undefined;
        this.contributoPubblico = undefined;
        this.idVoceCosto = undefined;
        this.importoVoceCosto = undefined;
        this.myControl.setValue(undefined);
        if (reset) {
            // eliminare in ordine: bonifico -> fattura/quietanza -> voce costo altrimenti si apre il dialog e non elimina le voci
            for (let index = this.listOfDatoBonificoToShow.length - 1; index >= 0; index--) {
                this.removeListaDatoBonifico(index);
            }
            for (let index = this.listOfDatoFatturaToShow.length - 1; index >= 0; index--) {
                this.removeListaDatoFattura(index);
            }
            for (let index = this.listOfDatoQuietanzaToShow.length - 1; index >= 0; index--) {
                this.removeListaDatoQuietanza(index);
            }
            for (let index = this.listOfVoceCostoFornituraToShow.length - 1; index >= 0; index--) {
                this.removeVociCostoForniturte(index);
            }
        }
        this.loadLabelDocumenti();

    }

    isValidCampiRequired() {
        if (this.contribuzioneForm && this.contribuzioneForm.form) {

            if (this.myControl.hasError('required')) return true;
            if (this.contribuzioneForm.form.get('idAttoAssegnazione') && this.contribuzioneForm.form.get('idAttoAssegnazione').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('fonteFinanziamento') && this.contribuzioneForm.form.get('fonteFinanziamento').hasError('required')) return true;

            if (this.contribuzioneForm.form.get('dataAggiudicazione') && this.contribuzioneForm.form.get('dataAggiudicazione').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('dataStipula') && this.contribuzioneForm.form.get('dataStipula').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('numeroOrdine') && this.contribuzioneForm.form.get('numeroOrdine').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('dataOrdine') && this.contribuzioneForm.form.get('dataOrdine').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('voceCosto') && this.contribuzioneForm.form.get('voceCosto').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('importo') && this.contribuzioneForm.form.get('importo').hasError('required')) return true;

            //QUIETANZA
            if (this.contribuzioneForm.form.get('tipoDocumentoQuietanza') && this.contribuzioneForm.form.get('tipoDocumentoQuietanza').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('numeroQuietanza') && this.contribuzioneForm.form.get('numeroQuietanza').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('dataQuietanza') && this.contribuzioneForm.form.get('dataQuietanza').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('uploadFileQuietanza') && this.contribuzioneForm.form.get('uploadFileQuietanza').hasError('required')) return true;
            //controllo se il documento e' stato inserito
            if (!config.isNullOrVoid(this.datoFattura.idTipoDocQuietanza) &&
                !config.isNullOrVoid(this.datoFattura.nrQuietanzaAzForn) &&
                !config.isNullOrVoid(this.datoFattura.dataQuietanzaAzForn) &&
                config.isNullOrVoid(this.documentoQuietanza.nomeFile)
            ) {
                return true;
            }
            //FATTURA
            if (this.contribuzioneForm.form.get('numeroFattura') && this.contribuzioneForm.form.get('numeroFattura').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('dataFattura') && this.contribuzioneForm.form.get('dataFattura').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('importoFattura') && this.contribuzioneForm.form.get('importoFattura').hasError('required')) return true;
            //controllo se e' stata inserita almeno una voce costo
            if (!config.isNullOrVoid(this.datoFattura.numero) &&
                !config.isNullOrVoid(this.datoFattura.data) &&
                !config.isNullOrVoid(this.datoFattura.importo) &&
                !config.isNullOrVoid(this.documentoFatturaSpesa.nomeFile) &&
                this.listOggettoFatturaDatoSpesa.length <= 0
            ) {
                return true;
            }
            //controllo se il documento e' stato inserito
            if (!config.isNullOrVoid(this.datoFattura.numero) &&
                !config.isNullOrVoid(this.datoFattura.data) &&
                !config.isNullOrVoid(this.datoFattura.importo) &&
                this.listOggettoFatturaDatoSpesa.length > 0 &&
                config.isNullOrVoid(this.documentoFatturaSpesa.nomeFile)
            ) {
                return true;
            }
            //BONIFICI
            if (this.contribuzioneForm.form.get('dataBonifico') && this.contribuzioneForm.form.get('dataBonifico').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('importoBonifico') && this.contribuzioneForm.form.get('importoBonifico').hasError('required')) return true;
            if (this.contribuzioneForm.form.get('cro') && this.contribuzioneForm.form.get('cro').hasError('required')) return true;
            //controllo se e' stata inserita almeno una voce fattura
            if (!config.isNullOrVoid(this.datoBonifico.dataBonifico) &&
                !config.isNullOrVoid(this.datoBonifico.importoBonifico) &&
                !config.isNullOrVoid(this.datoBonifico.cro) &&
                this.listFatturaBonifico.length <= 0
            ) {
                return true;
            }
        }
    }


    /**********************************************************************************************************************************************
     * 
     *                                                              MODIFICA 
     * 
     *********************************************************************************************************************************************/

    mapContribuzioneToUpdate(value, fontiFinanziamento) {
        this.listOfDatoFatturaToShow = [];
        this.listOfDatoQuietanzaToShow = [];
        this.listOfDatoBonificoToShow = [];
        this.assegnazioneRisorse = value.assegnazioneRisorse;
        this.ordineAcquisto = value.ordineAcquisto;
        this.idAttoAssegnazioneRisorse = fontiFinanziamento.find(a => a.idFonteFinanziamento === this.assegnazioneRisorse.idFonteFinanziamento).idAttoAssegnazione;
        this.ordineAcquisto.dataAggiudicazione = config.isNullOrVoid(this.ordineAcquisto.dataAggiudicazione) ? null : new Date(this.ordineAcquisto.dataAggiudicazione);
        this.ordineAcquisto.dataOrdine = config.isNullOrVoid(this.ordineAcquisto.dataOrdine) ? null : new Date(this.ordineAcquisto.dataOrdine);
        this.ordineAcquisto.dataStipula = config.isNullOrVoid(this.ordineAcquisto.dataStipula) ? null : new Date(this.ordineAcquisto.dataStipula);
        this.calculateContributoPubblico();
        this.loadFontiFinanziamento();
        this.costoFornitura = value.costoFornitura;
        this.datoMessaServizio = value.datoMessaServizio;
        this.datoSpesa = value.datoSpesa;
        this.listOfVoceCostoFornituraToShow = value.vociCosto;
        this.datoSpesa.dataDeterminaRpAmpAnt = config.isNullOrVoid(this.datoSpesa.dataDeterminaRpAmpAnt) ? null : new Date(this.datoSpesa.dataDeterminaRpAmpAnt);
        this.datoSpesa.dataAttoLiquidazioneRpAmpAnt = config.isNullOrVoid(this.datoSpesa.dataAttoLiquidazioneRpAmpAnt) ? null : new Date(this.datoSpesa.dataAttoLiquidazioneRpAmpAnt);
        this.datoSpesa.dataDeterminaAmpAzAnt = config.isNullOrVoid(this.datoSpesa.dataDeterminaAmpAzAnt) ? null : new Date(this.datoSpesa.dataDeterminaAmpAzAnt);
        this.datoSpesa.dataAttoLiquidazioneAmpAzAnt = config.isNullOrVoid(this.datoSpesa.dataAttoLiquidazioneAmpAzAnt) ? null : new Date(this.datoSpesa.dataAttoLiquidazioneAmpAzAnt);
        this.datoSpesa.dataDeterminaAmpAzSal = config.isNullOrVoid(this.datoSpesa.dataDeterminaAmpAzSal) ? null : new Date(this.datoSpesa.dataDeterminaAmpAzSal);
        this.datoSpesa.dataAttoLiquidazioneAmpAzSal = config.isNullOrVoid(this.datoSpesa.dataAttoLiquidazioneAmpAzSal) ? null : new Date(this.datoSpesa.dataAttoLiquidazioneAmpAzSal);
        this.datoSpesa.dataDeterminaRpAmpSal = config.isNullOrVoid(this.datoSpesa.dataDeterminaRpAmpSal) ? null : new Date(this.datoSpesa.dataDeterminaRpAmpSal);
        this.datoSpesa.dataAttoLiquidazioneRpAmpSal = config.isNullOrVoid(this.datoSpesa.dataAttoLiquidazioneRpAmpSal) ? null : new Date(this.datoSpesa.dataAttoLiquidazioneRpAmpSal);
        this.documentoVoceCosto = value.documentoContribuzione || new DocContribuzioneVO();
        this.documentoAttoObbligo = value.documentoAttoObbligo || new DocContribuzioneVO();
        this.documentoGaranzia = value.documentoGaranzia || new DocContribuzioneVO();
        this.documentoAlienazione = value.documentoAlienazione || new DocContribuzioneVO();
        this.documentoMisuraEmissioni = value.documentoMisureEmissioni || new DocContribuzioneVO();
        this.contribuzione = value.contribuzione;
        this.contribuzione.dataAggiornamento = config.isNullOrVoid(this.contribuzione.dataAggiornamento) ? null : new Date(this.contribuzione.dataAggiornamento);
        this.listOfVociCostoToShow = JSON.parse(JSON.stringify(this.listOfVociCosto));
        this.createListOfVociCostoForGet();
        this.createListOfOggettofatturaAndQuietanzaGet(value.datiFattura);
        this.createListOfBonificiForGet(value.datiFattura);
        this.loadLabelDocumenti();
        this.idContribuzioneSaved = value.contribuzione.idContribuzione;
        this.idProcedimentoUpdate = this.contribuzione.idProcedimento;
        this.createOggettoBonifico();
        this.listOfDeletedIdDatiFattura = [];
        this.loadedContribuzioneToUpdate = false;
        if (!config.isNullOrVoid(this.datoMessaServizio.primoTelaioSost)) {
            let index = this.listOfTelaiVeicoloDaSostituire.findIndex(a => a == this.datoMessaServizio.primoTelaioSost);
            if (index != -1) {
                this.listOfTelaiVeicoloDaSostituire.splice(index, 1);
                this.listOfTelaiVeicoloDaSostituire.unshift(this.datoMessaServizio.primoTelaioSost);
            } else {
                this.listOfTelaiVeicoloDaSostituire.unshift(this.datoMessaServizio.primoTelaioSost);
            }
        }
    }

    getContribuzioneForUpdate(isInsertOrUpdate?: boolean) {
        this.loadedContribuzioneToUpdate = true;
        this.loadedTransizione = true;
        let fontiFinanziamento: FonteFinanziamentoVO[];
        this.subscribers = this.contribuzioneService.getAllFonteFinanziamento().subscribe(
            data => {
                fontiFinanziamento = data;
            }
        );
        if (isInsertOrUpdate) {
            if (!config.isNullOrVoid(this.idContribuzioneSaved)) {
                this.subscribers = this.contribuzioneService.getContribuzioneCompletaById(this.idContribuzioneSaved).subscribe(
                    data => {
                        this.mapContribuzioneToUpdate(data, fontiFinanziamento);
                        this.getDocAutobus();
                        this.loadDettaglioRichiesta();
                    },
                    (error) => {
                        CommonsHandleException.authorizationError(error, this.router);
                        console.error(error.error.message);
                    });
            }
        } else {
            this.subscribers = this.contribuzioneService.getContribuzioneCompletaByIdProcedimento(this.idProcedimentoUpdate).subscribe(
                data => {
                    this.mapContribuzioneToUpdate(data, fontiFinanziamento);
                    this.loadDettaglioRichiesta();
                },
                (error) => {
                    CommonsHandleException.authorizationError(error, this.router);
                    console.error(error.error.message);
                });
        }
    }

    // METODO PER OTTENRE IL DOCUMENTO DELLA CARTA DI CIRCOLAZIONE
    getDocAutobus() {
        if (!config.isNullOrVoid(this.contribuzioneService.idVariazAutobus)) {
            this.subscribers = this.autobusService.getDocVariazAutobusForInfo(this.contribuzioneService.idVariazAutobus).subscribe(
                data => {
                    let cartaDiCircolazione = data.find(a => a.idTipoDocumento == 1);
                    if (!config.isNullOrVoid(cartaDiCircolazione)) {
                        this.documentoCartaCircolazione.dataCaricamento = cartaDiCircolazione.dataCaricamento;
                        this.documentoCartaCircolazione.descEstesa = cartaDiCircolazione.descEstesa;
                        this.documentoCartaCircolazione.documento = cartaDiCircolazione.documento;
                        this.documentoCartaCircolazione.idTipoDocumento = cartaDiCircolazione.idTipoDocumento;
                        this.documentoCartaCircolazione.nomeFile = cartaDiCircolazione.nomeFile;
                    }
                },
                (err) => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                });
        }
    }

    createListOfVociCostoForGet() {
        // INSERISCO LA DESCRIZIONE DELLA VOCE COSTO CHE RICEVO DAL BE
        this.listOfVoceCostoFornituraToShow = this.listOfVoceCostoFornituraToShow.map(a => {
            return {
                ...a,
                descVoceCosto: this.listOfVociCosto.find(b => b.idVoceCosto === a.idVoceCosto).descVoceCosto,
            }
        })
        //RIMUOVO LE VOCI COSTO FORNITURA CHE SONO GIA STATE INSERITE
        this.listOfVoceCostoFornituraToShow.forEach(element => {
            let index = this.listOfVociCostoToShow.findIndex(a => a.idVoceCosto == element.idVoceCosto);
            let voce = this.listOfVociCosto.find(a => a.idVoceCosto == element.idVoceCosto);
            //CREO LA LISTA DEGLI OGGETTI FATTURA
            if (!config.isNullOrVoid(voce)) {
                this.listOfOggettoFattura.push(voce);
            }
            if (index !== -1) {
                this.listOfVociCostoToShow.splice(index, 1);
            }
        });
    }

    createListOfOggettofatturaAndQuietanzaGet(list: any[]) {
        list = list.map(a => {
            return {
                ...a,
                descOggetto: ""
            }
        })
        list.forEach(element => {
            if (config.isNullOrVoid(element.idTipoDocQuietanza)) {
                let listaIdVoceCosto = [];
                let listVoci = [];
                element.listaIdVoceCosto.forEach(voce => {
                    let idVoceCosto = this.listOfVoceCostoFornituraToShow.find(a => a.idVoceCostoFornitura == voce).idVoceCosto
                    let voceCosto = this.listOfVoceCostoFornituraToShow.find(a => a.idVoceCosto == idVoceCosto).descVoceCosto
                    let index = this.listOfOggettoFattura.findIndex(a => a.descVoceCosto === voceCosto);
                    this.listOfOggettoFattura.splice(index, 1);
                    listVoci.push(voceCosto)
                    listaIdVoceCosto.push(idVoceCosto)
                })
                element.listaIdVoceCosto = listaIdVoceCosto;

                element.numero = element.numero || null;
                element.data = new Date(element.data) || null;
                element.importo = element.importo || null;
                element.vociCosto = listVoci.join(", ")
                this.listOfDatoFatturaToShow.push(element);
            } else {
                element.dataQuietanzaAzForn = new Date(element.dataQuietanzaAzForn);
                element.tipoDoc = this.listOfTipiDocumentoQuietanza.find(a => a.idTipoDocQuietanza == element.idTipoDocQuietanza).descTipoDocQuietanza;
                this.listOfDatoQuietanzaToShow.push(element);
            }
        });

    }

    updateContribuzione() {
        this.contribuzioneService.updateContribuzione(this.contribuzioneCompleta, this.listOfDeletedIdVociCosto, this.listOfDeletedIdDatiFattura, this.listOfDeletedIdDatiBonifico).subscribe(data => {
            this.idContribuzioneSaved = data;
            this.resetFields();
            this.snackBar.open("Modifica eseguita correttamente!", "Chiudi", {
                duration: 2000,
            });
            this.listOfDeletedIdVociCosto = [];
            this.listOfDeletedIdDatiFattura = [];
            this.listOfDeletedIdDatiBonifico = [];
            if (config.isNullOrVoid(this.idContribuzioneSaved)) {
                this.getContribuzioneForUpdate();
            }
            this.getContribuzioneForUpdate(true);
            this.isSaved = false;
        }, error => {
            this.listOfDeletedIdVociCosto = [];
            this.listOfDeletedIdDatiFattura = [];
            this.listOfDeletedIdDatiBonifico = [];
            CommonsHandleException.authorizationError(error, this.router);
            this.feedback = error.error.message;
            console.log("this.feedback =" + this.feedback);
            if (this.feedback.includes("\n")) {
                this.doubleError = true;
                console.log("this.doubleError =" + this.doubleError);
                this.doubleMessages = this.feedback.split('\n');
                console.log("this.doubleMessages =" + this.doubleMessages);
                this.fristMessages = this.doubleMessages[0];
                console.log("this.fristMessages =" + this.fristMessages);
                this.secondMessages = this.doubleMessages[1];
                console.log("this.secondMessages =" + this.secondMessages);
                this.feedback = "";
                console.log("this.feedback =" + this.feedback);
            }
            window.scrollTo(0, 0);
            this.isSaved = false;
        });
    }

    /**********************************************************************************************************************************************
     * 
     *                                                              ITER CONTRIBUZIONE
     * 
     *********************************************************************************************************************************************/

    loadTransizioneAutomaParteA() {
        this.procedimentoService.getTransizioniAutoma(this.dettaglioRichiesta.id, this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null).idStato, config.PARTE_A).subscribe((data: any) => {
            this.loadedTransizioniAutomaA = true;
            if (data) {
                this.transizioniAutomaA = data;
                this.transizioniAutomaA.forEach(element => {
                    this.statoTransizioneA = element.returnTransizione > this.statoTransizioneA ? element.returnTransizione : this.statoTransizioneA;
                });
                if (this.statoTransizioneA == 0) {
                    this.transizioneAutomaA = this.transizioniAutomaA[this.transizioniAutomaA.length - 1];
                    this.transizioneGroupA.controls['transizioneForm'].setValue(null);
                    this.transizioneGroupA.controls['transizioneForm'].disable();
                } else if (this.statoTransizioneA == 1) {
                    this.transizioniAutomaA = this.transizioniAutomaA.filter(a => a.returnTransizione == 1);
                    if (this.transizioniAutomaA.length == 1) {
                        this.transizioneAutomaA = this.transizioniAutomaA[0];
                        if (this.transizioneGroupA && this.transizioneGroupA.controls['transizioneForm']) {
                            this.transizioneGroupA.controls['transizioneForm'].setValue(this.transizioneAutomaA);
                            this.transizioneGroupA.controls['transizioneForm'].disable();
                        }
                    }
                    else {
                        let transizioneDefault = this.transizioniAutomaA.find(t => t.flagDefault);
                        this.transizioneGroupA.controls['transizioneForm'].setValue(transizioneDefault);
                        this.transizioneGroupA.controls['transizioneForm'].enable();
                        this.transizioneAutomaA = transizioneDefault;
                    }
                } else {
                    this.transizioniAutomaA = this.transizioniAutomaA.filter(a => a.returnTransizione == this.statoTransizioneA);
                }
            }
            this.loadedTransizioniAutomaA = false;
        });
    }

    loadTransizioneAutomaParteB() {
        this.procedimentoService.getTransizioniAutoma(this.dettaglioRichiesta.id, this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null).idStato, config.PARTE_B).subscribe((data: any) => {
            this.loadedTransizioniAutomaB = true;
            if (data) {
                this.transizioniAutomaB = data;
                this.transizioniAutomaB.forEach(element => {
                    this.statoTransizioneB = element.returnTransizione > this.statoTransizioneB ? element.returnTransizione : this.statoTransizioneB;
                });
                if (this.statoTransizioneB == 0) {
                    this.transizioneAutomaB = this.transizioniAutomaB[this.transizioniAutomaB.length - 1];
                    this.transizioneGroupB.controls['transizioneForm'].setValue(null);
                    this.transizioneGroupB.controls['transizioneForm'].disable();
                } else if (this.statoTransizioneB == 1) {
                    this.transizioniAutomaB = this.transizioniAutomaB.filter(a => a.returnTransizione == 1);
                    if (this.transizioniAutomaB.length == 1) {
                        this.transizioneAutomaB = this.transizioniAutomaB[0];
                        if (this.transizioneGroupB && this.transizioneGroupB.controls['transizioneForm']) {
                            this.transizioneGroupB.controls['transizioneForm'].setValue(this.transizioneAutomaB);
                            this.transizioneGroupB.controls['transizioneForm'].disable();
                        }
                    }
                    else {
                        let transizioneDefault = this.transizioniAutomaB.find(t => t.flagDefault);
                        this.transizioneGroupB.controls['transizioneForm'].setValue(transizioneDefault);
                        this.transizioneGroupB.controls['transizioneForm'].enable();
                        this.transizioneAutomaB = transizioneDefault;
                    }
                } else {
                    this.transizioniAutomaB = this.transizioniAutomaB.filter(a => a.returnTransizione == this.statoTransizioneB);
                }
            }
            this.loadedTransizioniAutomaB = false;
        });
    }


    loadDettaglioRichiesta() {
        this.procedimentoService.dettaglioRichiesta(this.idProcedimentoUpdate, Action.EDIT).subscribe(data => {
            if (data) {
                this.dettaglioRichiesta = new DettaglioRichiestaVO();
                this.dettaglioRichiesta = data;
                let isFinalStateRendicontazione;
                this.contribuzioneService.checkFinalStateIter(this.dettaglioRichiesta.iters[0].idStato).subscribe(data => {
                    isFinalStateRendicontazione = data;
                    if (isFinalStateRendicontazione) {
                        this.router.navigate(['/dettaglioBus', this.idVariazAutobus],
                            {
                                fragment: 'rendicontazione',
                                queryParams: {},
                            });
                    } else {
                        this.disableFields();
                        this.loadTransizioneAutomaParteA();
                        this.loadTransizioneAutomaParteB();
                        this.loadedTransizione = this.loadedTransizioniAutomaA && this.loadedTransizioniAutomaB;
                    }
                })
            }
        }, (error) => {
            CommonsHandleException.authorizationError(error, this.router);
        });
    }

    openDialogAvanzaIter(parte: string) {
        // Apro il dialog per il salvataggio delle modifiche
        let dialogRef = this.dialog.open(CancellaDialogComponent, {
            height: '200px',
            width: '400px',
            data: { msg: 'Eventuali modifiche apportate verranno salvate; si vuole procedere?' }
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result == 'OK') {
                this.mapContribuzioneToSave();
                this.avanzaIterRichiesta(parte);
            }
        });
    }

    allDisabled: boolean;
    allEnabled: boolean;
    iter: IterProcedimentoVO;

    avanzaIterRichiesta(parte: string) {
        let transizioneAutoma;
        let notaTransizione;
        if (parte == "A") {
            transizioneAutoma = this.transizioneAutomaA;
            notaTransizione = this.notaTransizioneA;
        } else {
            transizioneAutoma = this.transizioneAutomaB;
            notaTransizione = this.notaTransizioneB;
        }
        this.procedimentoService.avanzaIterRichiesta(this.dettaglioRichiesta, transizioneAutoma, notaTransizione).subscribe(data => {
            if (data) {
                this.getContribuzioneForUpdate();
                if (parte == "A") {
                    this.transizioneGroupA.controls['transizioneForm'].reset();
                } else {
                    this.transizioneGroupB.controls['transizioneForm'].reset();
                }
                this.snackBar.open("Operazione avvenuta con successo!", "Chiudi", {
                    duration: 2000,
                });
            }
        }, err => {
            let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
            console.error(err)
            if (errorRest.type == TypeErrorRest.OK) {
                this.feedback = errorRest.message;
            }
            else {
                this.feedback = "Si è verificato un errore in fase di salvataggio";
            }
            console.log("this.feedback =" + this.feedback);
            window.scrollTo(0, 0);
        });
    }

    goBack() {
        this.route.fragment.subscribe(fragment => {
            if (fragment == 'dettaglioMessaggio') {
                this.router.navigate(['/messaggi/1']);
            } else {
                this.router.navigate(['/ricercaContribuzione']);
            }
        });
    }


    disableFields() {
        let lastStateParteA: any; // check ultimo stato dell'iter di parte A
        let lastStateParteB: any;  // check ultimo stato dell'iter di parte A
        let checkStateParteA: any;
        let checkStateParteB: any;
        let stato41 = this.dettaglioRichiesta.iters.find(a => a.idStato == 41);
        let stato51 = this.dettaglioRichiesta.iters.find(a => a.idStato == 51);
        let stato42 = this.dettaglioRichiesta.iters.find(a => a.idStato == 42);
        let stato52 = this.dettaglioRichiesta.iters.find(a => a.idStato == 52);


        if (this.isAzienda) {
            // l'ultimo stato e' ugaule a 41 o 61 disabilito la parte A
            lastStateParteA = this.dettaglioRichiesta.iters.some(
                a => (config.isNullOrVoid(a.dataFineValidita) && a.idStato == 41) || a.idStato == 61
            )
            // se lo sato 41 e' presente e il 51 no disabilito la parte A
            // oppure se sono presenti entrambi e lo stato piu' recente e' il 41 disabilito la parte A
            // checkStateParteA = !config.isNullOrVoid(stato41) && config.isNullOrVoid(stato51) || stato41.dataInizioValidita > stato51.dataInizioValidita;
            checkStateParteA = !config.isNullOrVoid(stato41) && config.isNullOrVoid(stato51);
            if (!checkStateParteA && !config.isNullOrVoid(stato41) && !config.isNullOrVoid(stato51)) {
                checkStateParteA = stato41.dataInizioValidita > stato51.dataInizioValidita;
            }
            // se l'ultimo stato e' ugaule a 42 disabilito la Parte B
            lastStateParteB = this.dettaglioRichiesta.iters.some(
                a => config.isNullOrVoid(a.dataFineValidita) && (a.idStato == 42) || a.idStato == 62
            )
            // se lo sato 42 e' presente e il 52 no disabilito la parte A
            // oppure se sono presenti entrambi e lo stato piu' recente e' il 42 disabilito la parte A
            checkStateParteB = !config.isNullOrVoid(stato42) && config.isNullOrVoid(stato52);
            if (!checkStateParteB && !config.isNullOrVoid(stato42) && !config.isNullOrVoid(stato52)) {
                checkStateParteB = stato42.dataInizioValidita > stato52.dataInizioValidita;
            }
        }

        if (this.isAmpOrRp) {
            // controllo se l'ultimo stato e' ugaule a 10, 51 o 61 disabilito la parte A
            lastStateParteA = this.dettaglioRichiesta.iters.some(
                a => (config.isNullOrVoid(a.dataFineValidita) && a.idStato == 10 || a.idStato == 51) || a.idStato == 61
            )
            // se sono presenti lo stato 41 e 51 
            // e lo stato piu' recente e' il 51 disabilito la parte A
            checkStateParteA = !config.isNullOrVoid(stato51) && !config.isNullOrVoid(stato41) && stato51.dataInizioValidita > stato41.dataInizioValidita;

            // controllo se l'ultimo stato e' ugaule a 10 o 52 disabilito la parte A
            lastStateParteB = this.dettaglioRichiesta.iters.some(
                a => config.isNullOrVoid(a.dataFineValidita) && (a.idStato == 10 || a.idStato == 52) || a.idStato == 62
            )
            // se sono presenti lo stato 42 e 52 
            // e lo stato piu' recente e' il 52 disabilito la parte B
            checkStateParteB = !config.isNullOrVoid(stato52) && !config.isNullOrVoid(stato42) && stato52.dataInizioValidita > stato42.dataInizioValidita;

        }
        this.disabledParteA = lastStateParteA || checkStateParteA;
        this.disabledParteB = lastStateParteB || checkStateParteB;
    }

    checkAutoCompleteTelaio() {
        // Controllo se quello inserito nel campo è incluso nella lista, se non e' contenuto 
        // svuoto il campo
        if (!this.listOfTelai.includes(this.myControl.value)) {
            this.myControl.setValue("");
        }
    }

    checkData(data: Date, name: string) {
        if (data == null)
            this.contribuzioneForm.form.get(name).setValue(null);
    }

}

export enum Action {
    EDIT = "E",
    VIEW = "V",
}