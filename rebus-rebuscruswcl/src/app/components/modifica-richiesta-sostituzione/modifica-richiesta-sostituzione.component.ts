/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from "@angular/core";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { ProcedimentoService } from "../../services/procedimento.service";
import { MatSnackBar, MatDialog } from "@angular/material";
import { ActivatedRoute, Router } from "@angular/router";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { MotivazioneVO, MotorizzazioneVO, TipoContrattoVO, TipoProcedimentoVO } from "../../vo/extend-vo";
import { TransizioneAutomaVO } from "../../vo/transizione-automa-vo";
import { FormControl, NgForm, FormGroup } from "@angular/forms";
import { DialogComponent } from "../dialog/dialog.component";
import { DocumentService } from "../../services/document.service";
import { ErrorRest, TypeErrorRest } from "../../class/error-rest";
import { saveAs } from "file-saver"
import { UserService } from "../../services/user.service";
import { UserInfo } from "../../vo/funzionario-vo";
import { AuthorizationRoles } from "../../class/authorization-roles";
import { AllegatoProcVO } from "../../vo/allegato-proc-vo";
import { ContrattoProcDatiVO, ContrattoProcVO } from "../../vo/contratto-proc-vo";
import { CancellaDialogComponent } from "../cancelladialog/cancelladialog.component";
import { Observable } from "rxjs";
import { startWith, map } from "rxjs/operators";
import { IterProcedimentoVO } from "../../vo/iter-procedimento-vo";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";
import * as config from "../../globalparameter"

@Component({
    selector: 'app-modifica-richiesta-sostituzione',
    templateUrl: './modifica-richiesta-sostituzione.component.html',
    styleUrls: ['./modifica-richiesta-sostituzione.component.scss']
})
@DestroySubscribers()
export class ModificaRichiestaSostituzioneComponent implements OnInit, OnDestroy {

    isScaricaPDFAbilitato: boolean;
    funzionario: UserInfo;

    id: number;
    azione: string;
    dettaglioRichiesta: DettaglioRichiestaVO;
    idStatoIterCorrente: number;
    tipoProcedimento: TipoProcedimentoVO;
    idSubProcedimenti: Array<number>;
    richiesteArray: Array<DettaglioRichiestaVO>;
    disableTipoContrattoParent: boolean = false;
    tipoContrattoSelected: number;
    dettaglioRichiestaFirma: DettaglioRichiestaVO;
    loadedDettaglioRichiestaFirma: boolean = true;
    ruoloFirmaLoaded: string;
    nominativoFirmaLoaded: string;

    transizioniAutoma: Array<TransizioneAutomaVO>;
    transizioneAutoma: TransizioneAutomaVO;
    notaTransizione: string;

    datiContratto: ContrattoProcDatiVO;
    enteComm: string;
    esecTit: string;
    capofila: string;
    soggettoRichiedente: string;
    soggettoIntermediario: string;
    isFirmaVisible: boolean = false;
    notaMotivazione: string;

    disableNominativoRuoloFirma: boolean = true;
    disableNominativoRuoloFirmaEnte: boolean = true;

    transizioneGroup: FormGroup = new FormGroup({ transizioneForm: new FormControl() });
    transizioneSelected = new FormControl();


    feedback: string;
    loadComplete: boolean;
    loadedTipoProcedimento: boolean;
    loadedTransizioniAutoma: boolean;
    loadedMotorizzazioni: boolean;
    loadedMotivazioni: boolean;
    loadedContratti: boolean;
    loadedTipiContratto: boolean;
    loadedAvanzaIter: boolean = true;
    loadedSave: boolean = true;
    loadedDoc: boolean = true;
    loadedDatiContratto: boolean = true;
    idTipoContratto: number;
    isContrattoChanged: boolean = false;

    motorizzazioni: Array<MotorizzazioneVO>;
    motivazioni: Array<MotivazioneVO>;
    contratti: Array<ContrattoProcVO>;
    tipiContratto: Array<TipoContrattoVO>;

    motorizzazioneGroup: FormGroup = new FormGroup({ motorizzazioneForm: new FormControl() });
    motorizzazioneSelected = new FormControl();
    filteredOptionsMotorizzazione: Observable<MotorizzazioneVO[]>;

    motivazioneGroup: FormGroup = new FormGroup({ motivazioneForm: new FormControl() });
    motivazioneSelected = new FormControl();
    filteredOptionsMotivazione: Observable<MotivazioneVO[]>;

    idContesto: number;
    contrattoGroup: FormGroup = new FormGroup({ contrattoForm: new FormControl() });
    contrattoSelected = new FormControl();
    filteredOptionsContratto: Observable<ContrattoProcVO[]>;

    @ViewChild('richiestaParentForm') dettaglioRichiestaForm: NgForm;
    @ViewChild('premesseFile') premesseFile: ElementRef;
    @ViewChild('prescrizioniFile') prescrizioniFile: ElementRef;
    @ViewChild('avanzaIter') inputEl: ElementRef;
    context: string;


    codiceEnte: number;
    visualizza: boolean = false;

    constructor(private procedimentoService: ProcedimentoService,
        private documentService: DocumentService,
        private userService: UserService,
        private snackBar: MatSnackBar,
        public dialog: MatDialog,
        public navbarFilterContext: NavbarFilterContext,
        private route: ActivatedRoute,
        private router: Router,
    ) { }

    ngOnInit(): void {
        window.scroll(0, 0);
        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
            this.context = this.navbarFilterContext.InfoFiltro.cod;
        }

        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.isScaricaPDFAbilitato = this.funzionario.authority.includes(AuthorizationRoles.SCARICA_PDF);
            this.codiceEnte = data.idEnte;

        });
        this.dettaglioRichiesta = new DettaglioRichiestaVO();
        this.azione = this.route.snapshot.paramMap.get('action');


        this.route.params.subscribe(params => {
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.idContesto = +params['idContesto'];
            this.loadData();
        });

    }

    loadData() {
        this.loadComplete = false;
        this.richiesteArray = new Array<DettaglioRichiestaVO>();
        if (this.transizioneAutoma != null && this.transizioneAutoma.idStatoIterA == 20) {
            this.allDisabled = false;
        } else if (this.transizioneAutoma != null && this.transizioneAutoma.idStatoIterA == 10) {
            this.allEnabled = false;
        }
        this.procedimentoService.dettaglioRichiesta(this.id, Action.EDIT).subscribe(data => {
            if (data) {
                this.dettaglioRichiesta = data;
                this.idStatoIterCorrente = this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null).idStato;
                this.idSubProcedimenti = new Array<number>();
                if ((this.dettaglioRichiesta.nominativoFirma != null && this.dettaglioRichiesta.nominativoFirma != '') ||
                    (this.dettaglioRichiesta.ruoloFirma != null && this.dettaglioRichiesta.ruoloFirma != '')) {
                    this.ruoloFirmaLoaded = this.dettaglioRichiesta.ruoloFirma;
                    this.nominativoFirmaLoaded = this.dettaglioRichiesta.nominativoFirma;
                    if (this.idStatoIterCorrente == 10 || this.idStatoIterCorrente == 30 || this.idStatoIterCorrente == 50) {
                        this.disableNominativoRuoloFirma = false;
                    }
                }
                if ((this.dettaglioRichiesta.nominativoFirmaEnte != null && this.dettaglioRichiesta.nominativoFirmaEnte != '') ||
                    (this.dettaglioRichiesta.ruoloFirmaEnte != null && this.dettaglioRichiesta.ruoloFirmaEnte != '')) {
                    this.disableNominativoRuoloFirmaEnte = false;
                }
                if (!config.isNullOrVoid(this.dettaglioRichiesta.subProcedimento) && !config.isNullOrVoid(this.dettaglioRichiesta.subProcedimento.idSubProcedimento1)) {
                    this.idSubProcedimenti.push(this.dettaglioRichiesta.subProcedimento.idSubProcedimento1);
                }
                if (!config.isNullOrVoid(this.dettaglioRichiesta.subProcedimento) && !config.isNullOrVoid(this.dettaglioRichiesta.subProcedimento.idSubProcedimento2)) {
                    this.idSubProcedimenti.push(this.dettaglioRichiesta.subProcedimento.idSubProcedimento2);
                }
                this.loadChoices();
                this.loadComplete = true;
            }
        }, err => {
            if (err.status == 406) { //NON AUTORIZZATO O NON MODIFICABILE
                let azione2: string = "ricerca";
                if (this.azione.startsWith("messaggio")) {
                    azione2 = this.azione;
                }
                this.router.navigate(['/dettaglioRichiestaSostituzione/' + this.id, { action: azione2 }]);
            }
            CommonsHandleException.authorizationError(err, this.router, '/dettaglioRichiestaSostituzione/', this.id);
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
        this.procedimentoService.getTipoProcedimento(this.dettaglioRichiesta.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipoProcedimento = data;
            }
            this.loadedTipoProcedimento = true;
        }, err => {
            CommonsHandleException.handleBlockingError(err, this.router);
        });

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
            this.loadedContratti = true;
        });

        this.idTipoContratto = this.dettaglioRichiesta.contratto.idTipoContratto;
        if (this.dettaglioRichiesta.idTipoProcedimento != 1) { // no prima immatricolazione
            this.loadedTipiContratto = false;
            this.procedimentoService.getTipiContratto().subscribe(data => {
                if (data) {
                    this.tipiContratto = data;
                }
                this.loadedTipiContratto = true;
            });
        }
        this.loadTransizioneAutoma();
    }

    loadTransizioneAutoma() {
        this.loadedTransizioniAutoma = false;
        this.notaTransizione = "";
        this.procedimentoService.getTransizioniAutoma(this.dettaglioRichiesta.id, this.idStatoIterCorrente).subscribe(data => {
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
                    this.transizioneAutoma = transizioneDefault;
                }
            }
            this.loadedTransizioniAutoma = true;
        });
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

    downloadRichiesta(idRichiesta: number, allegato: AllegatoProcVO) {
        this.loadedDoc = false;
        this.documentService.getContenutoDocumentoByIdProcedimento(idRichiesta, allegato.tipoDocumento.id)
            .subscribe(
                res => {
                    saveAs(res, allegato.nomeFile);
                    this.loadedDoc = true;
                },
                err => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                    this.loadedDoc = true;
                }
            );
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

    avanzaIterRichiesta() {
        this.loadedAvanzaIter = false;
        this.procedimentoService.avanzaIterRichiesta(this.dettaglioRichiesta, this.transizioneAutoma, this.notaTransizione).subscribe(data => {
            this.loadedAvanzaIter = true;
            if (data) {
                window.scrollTo(0, 0);
                this.snackBar.open("Operazione avvenuta con successo!", "Chiudi", {
                    duration: 2000,
                });
                this.transizioneGroup.controls['transizioneForm'].reset();
                this.transizioneSelected = new FormControl();

                setTimeout(() => {
                    if (this.transizioneAutoma.idStatoIterA == 10 || this.transizioneAutoma.idStatoIterA == 20) {
                        this.loadData();
                    } else {
                        let azione2: string = "ricerca";
                        if (this.azione.startsWith("messaggio")) {
                            azione2 = this.azione;
                        }
                        this.router.navigate(['/dettaglioRichiestaSostituzione/' + this.id, { action: azione2 }]);
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

    isAvanzaIterDisabled() {
        if ((this.transizioneGroup && this.transizioneGroup.controls['transizioneForm'] && this.transizioneGroup.controls['transizioneForm'].hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('noteAvanzamentoIter') != null && this.dettaglioRichiestaForm.form.get('noteAvanzamentoIter').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte') != null && this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte') != null && this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('premesse') != null && this.dettaglioRichiestaForm.form.get('premesse').hasError('required'))
            || (this.dettaglioRichiestaForm.form.get('prescrizioni') != null && this.dettaglioRichiestaForm.form.get('prescrizioni').hasError('required'))
            || ((this.isFirmaVisible || this.dettaglioRichiesta.iters[0].idStato >= 30) && ((this.dettaglioRichiestaForm.form.get('nominativoFirma') && (this.dettaglioRichiestaForm.form.get('nominativoFirma').hasError('required'))) || (this.dettaglioRichiestaForm.form.get('ruoloFirma') && this.dettaglioRichiestaForm.form.get('ruoloFirma').hasError('required'))) && this.transizioneAutoma.idTransizioneAutoma === this.transizioniAutoma[0].idTransizioneAutoma)
            || (this.dettaglioRichiesta.isACaricoEnteVisible && (this.disableNominativoRuoloFirmaEnte))
        ) {
            return true;
        }
        return false;
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
        }
    }

    check(s: string) {
        setTimeout(() => {
            if (s == 'Motor') {
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
            s = s + a.descrizione;
        }
        return s;
    }

    getRichiestaFromChild(richiesta: DettaglioRichiestaVO) {
        this.richiesteArray.push(richiesta);
    }

    pulisciRichiestaFromChild(richiesta: DettaglioRichiestaVO) {
        let index = this.richiesteArray.findIndex(a => a.idTipoProcedimento == richiesta.idTipoProcedimento);
        if (index != -1) {
            this.richiesteArray.splice(index, 1);
        }
    }

    isTrasmettiDisabled() {
        if (this.richiesteArray.length != 2 && (this.idStatoIterCorrente == 10 || this.idStatoIterCorrente == 50)
            || (this.dettaglioRichiesta.isACaricoEnteVisible && (this.disableNominativoRuoloFirmaEnte))) {
            return true;
        }

        return false;
    }


    isValidCampiRequired() {
        if (this.dettaglioRichiestaForm && this.dettaglioRichiestaForm.form) {
            if (this.transizioneGroup.controls['transizioneForm'] && this.transizioneGroup.controls['transizioneForm'].hasError('required')) return true;
            if (this.motorizzazioneGroup.controls['motorizzazioneForm'] && this.motorizzazioneGroup.controls['motorizzazioneForm'].hasError('required')) return true;
            if (this.motivazioneGroup.controls['motivazioneForm'] && this.motivazioneGroup.controls['motivazioneForm'].hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('noteMotivazione') && this.dettaglioRichiestaForm.form.get('noteMotivazione').hasError('required')) return true;
            if (this.contrattoGroup.controls['contrattoForm'] && this.contrattoGroup.controls['contrattoForm'].hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('premesse') && this.dettaglioRichiestaForm.form.get('premesse').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('prescrizioni') && this.dettaglioRichiestaForm.form.get('prescrizioni').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte') && this.dettaglioRichiestaForm.form.get('ruoloFirmaEnte').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte') && this.dettaglioRichiestaForm.form.get('nominativoFirmaEnte').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('ruoloFirma') && this.dettaglioRichiestaForm.form.get('ruoloFirma').hasError('required')) return true;
            if (this.dettaglioRichiestaForm.form.get('nominativoFirma') && this.dettaglioRichiestaForm.form.get('nominativoFirma').hasError('required')) return true;
        }
        return false;
    }

    isAnteprimaDisabled() {
        if ((this.idStatoIterCorrente == 10 || this.idStatoIterCorrente == 30 || this.idStatoIterCorrente == 40 || this.idStatoIterCorrente == 50) && this.isTrasmettiDisabled()) {
            return true;
        }
        if ((this.transizioneGroup && this.transizioneGroup.controls['transizioneForm'] && this.transizioneGroup.controls['transizioneForm'].hasError('required'))
            || (this.dettaglioRichiestaForm && this.dettaglioRichiestaForm.form.get('noteAvanzamentoIter') && this.dettaglioRichiestaForm.form.get('noteAvanzamentoIter').hasError('required'))
            || this.isAvanzaIterDisabled()) {
            return true;
        }
        return false;
    }

    save(isAnteprimaPDF: boolean, isAvanzaIter: boolean) {

        this.loadedSave = false;

        this.dettaglioRichiesta.idMotorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value.id;
        this.dettaglioRichiesta.idMotivazione = this.motivazioneGroup.controls['motivazioneForm'].value.id;

        this.dettaglioRichiesta.contratto = this.contrattoGroup.controls['contrattoForm'].value;
        this.richiesteArray[0].idMotorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value.id;
        this.richiesteArray[0].idMotivazione = this.motivazioneGroup.controls['motivazioneForm'].value.id;
        this.richiesteArray[0].contratto = this.contrattoGroup.controls['contrattoForm'].value;
        this.richiesteArray[0].noteMotivazione = this.dettaglioRichiesta.noteMotivazione;
        this.richiesteArray[1].idMotorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value.id;
        this.richiesteArray[1].idMotivazione = this.motivazioneGroup.controls['motivazioneForm'].value.id;
        this.richiesteArray[1].contratto = this.contrattoGroup.controls['contrattoForm'].value;
        this.richiesteArray[1].noteMotivazione = this.dettaglioRichiesta.noteMotivazione;
        this.richiesteArray.unshift(this.dettaglioRichiesta);
        this.procedimentoService.modificaRichiestaSostituzione(this.richiesteArray).subscribe(data => {
            this.loadedSave = true;
            this.richiesteArray = new Array<DettaglioRichiestaVO>();
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
            this.richiesteArray = new Array<DettaglioRichiestaVO>();
            window.scrollTo(0, 0);
            this.loadedSave = true;
        });
    }
    idTipoProcedimentoChild: number;

    getIdTipoProcedimentoFromChild(event: number) {
        this.idTipoProcedimentoChild = event;
    }

    openDialogAnteprimaPdf() {
        if (this.idStatoIterCorrente != 20) {
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
        if (this.idStatoIterCorrente == 10 || this.idStatoIterCorrente == 50) {
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
        this.documentService.getContenutoAnteprimaPdf(this.dettaglioRichiesta.id, this.idStatoIterCorrente, this.dettaglioRichiesta.idTipoProcedimento)
            .subscribe(
                res => {
                    saveAs(res, "PROC_" + numProgressivo + "_Bozza.pdf");
                    this.loadedSave = true;
                },
                error => {
                    CommonsHandleException.authorizationError(error, this.router);
                    this.feedback = error.error.message;
                    console.error(error.error.message);
                    this.loadedSave = true;
                }
            );
    }

    goBack() {
        if (this.azione === 'inserisci') {
            let id = '3' + this.idTipoProcedimentoChild;
            this.router.navigate(['/inserisciRichiestaSostituzione/', id]);
        } else if (this.azione === 'ricerca') {
            this.router.navigate(['/ricercaProcedimenti']);
        } else
            if (this.azione.startsWith("messaggio")) {
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
        if (this.dettaglioRichiesta && this.dettaglioRichiesta.iters) {
            this.iter = this.dettaglioRichiesta.iters.find(a => a.dataFineValidita == null);
            if (this.iter && this.iter.idStato != 10 && this.iter.idStato != 50 && !this.allDisabled) { //NO BOZZA E NO DA REVISIONARE
                if (this.motorizzazioneGroup.controls["motorizzazioneForm"]) { this.motorizzazioneGroup.controls["motorizzazioneForm"].disable(); }
                if (this.motivazioneGroup.controls["motivazioneForm"]) { this.motivazioneGroup.controls["motivazioneForm"].disable(); }
                if (this.dettaglioRichiestaForm.form.get("noteMotivazione")) { this.dettaglioRichiestaForm.form.get("noteMotivazione").disable(); }
                if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].disable(); }
                this.allDisabled = true;
            } else if (this.iter && (this.iter.idStato == 10 || this.iter.idStato == 50) && !this.allEnabled) {
                if (this.motorizzazioneGroup.controls["motorizzazioneForm"]) { this.motorizzazioneGroup.controls["motorizzazioneForm"].enable(); }
                if (this.motivazioneGroup.controls["motivazioneForm"]) { this.motivazioneGroup.controls["motivazioneForm"].enable(); }
                if (this.contrattoGroup.controls["contrattoForm"]) { this.contrattoGroup.controls["contrattoForm"].enable(); }
                this.allEnabled = true;
            }
        }

    }


    isLoading() {
        if (!this.loadComplete ||
            !this.loadedTipoProcedimento ||
            !this.loadedTransizioniAutoma ||
            !this.loadedAvanzaIter ||
            !this.loadedDettaglioRichiestaFirma ||
            !this.loadedSave ||
            !this.loadedDoc ||
            !this.loadedMotorizzazioni ||
            !this.loadedMotivazioni ||
            !this.loadedContratti ||
            !this.loadedTipiContratto) return true;
        return false;
    }



    ngOnDestroy(): void {

    }

}

export enum Action {
    EDIT = "E",
    VIEW = "V",
}