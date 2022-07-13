/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, OnDestroy, ViewChild, ChangeDetectorRef } from "@angular/core";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { NgForm, FormGroup, FormControl } from "@angular/forms";
import { ProcedimentoService } from "../../services/procedimento.service";
import { TipoProcedimentoVO, MotorizzazioneVO, MotivazioneVO, TipoContrattoVO, TipoDocumentoVO } from "../../vo/extend-vo";
import { Observable } from "rxjs";
import { startWith, map } from "rxjs/operators";
import { InserisciRichiestaVO } from "../../vo/inserisci-richiesta.vo";
import { ContrattoProcVO, ContrattoProcDatiVO } from "../../vo/contratto-proc-vo";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { MatSnackBar } from "@angular/material";
import { Router, ActivatedRoute } from "@angular/router";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { UserService } from "../../services/user.service";
import { UserInfo } from "../../vo/funzionario-vo";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";

@Component({
    selector: 'app-inserisci-richiesta-sostituzione',
    templateUrl: './inserisci-richiesta-sostituzione.component.html',
    styleUrls: ['./inserisci-richiesta-sostituzione.component.scss']
})
@DestroySubscribers()
export class InserisciRichiestaSostituzioneComponent implements OnInit, OnDestroy {

    idTipoProcedimento1: number;
    idTipoProcedimento2: number;
    disableTipoContrattoParent: boolean = false;
    funzionario: UserInfo;
    idTipiProcedimmento: Array<number>;
    richiesteArray: Array<InserisciRichiestaVO>;
    feedback: string;
    loadedSave: boolean = true;
    loadedTipoProcedimento: boolean;
    loadedMotorizzazioni: boolean;
    loadedMotivazioni: boolean;
    loadedContratti: boolean;
    loadedTipiContratto: boolean;
    idTipoProcedimento: number;
    tipoProcedimento: TipoProcedimentoVO;
    motorizzazioni: Array<MotorizzazioneVO>;
    motivazioni: Array<MotivazioneVO>;
    contratti: Array<ContrattoProcVO>;
    tipiContratto: Array<TipoContrattoVO>;
    idTipoContratto: number;
    richiesta: InserisciRichiestaVO = new InserisciRichiestaVO();
    tipoContrattoSelected: number;
    notaMotivazione: string;
    dettaglioRichiestaFirma: DettaglioRichiestaVO;
    loadedDettaglioRichiestaFirma: boolean;
    numProgressivo: string;
    loadedNumProcedimento: boolean;
    datiContratto: ContrattoProcDatiVO;
    enteComm: string;
    esecTit: string;
    capofila: string;
    soggettoRichiedente: string;
    soggettoIntermediario: string;
    isFirmaVisible: boolean = false;
    context: string;

    motorizzazioneGroup: FormGroup = new FormGroup({ motorizzazioneForm: new FormControl() });
    motorizzazioneSelected = new FormControl();
    filteredOptionsMotorizzazione: Observable<MotorizzazioneVO[]>;

    motivazioneGroup: FormGroup = new FormGroup({ motivazioneForm: new FormControl() });
    motivazioneSelected = new FormControl();
    filteredOptionsMotivazione: Observable<MotivazioneVO[]>;

    contrattoGroup: FormGroup = new FormGroup({ contrattoForm: new FormControl() });
    contrattoSelected = new FormControl();
    filteredOptionsContratto: Observable<ContrattoProcVO[]>;


    @ViewChild('richiestaParentForm') richiestaForm: NgForm;


    constructor(private procedimentoService: ProcedimentoService,
        private userService: UserService,
        public snackBar: MatSnackBar,
        private router: Router,
        private route: ActivatedRoute,
        private navbarFilterContext: NavbarFilterContext,
        private changeDetector: ChangeDetectorRef,
    ) { }

    ngOnInit(): void {
        //procedimento di sostituzione
        this.idTipoProcedimento = 2;
        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
            this.context = this.navbarFilterContext.InfoFiltro.cod;
        }
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
        });
        this.route.params.subscribe(params => {
            let idTipoProcedimentoString = params['id'];
            this.idTipiProcedimmento = new Array<number>();
            this.idTipiProcedimmento = idTipoProcedimentoString.split("");

        });
        this.richiesteArray = new Array<InserisciRichiestaVO>();
        this.loadChoices();
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
                        this.richiesta.numProcedimento = data;
                        this.numProgressivo = (new Date()).getFullYear() + "/" + this.tipoProcedimento.codProcedimento + "/" + this.richiesta.numProcedimento;
                    }
                    this.loadedNumProcedimento = true;
                });
            }
            this.loadedTipoProcedimento = true;
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
            }
            this.loadedMotorizzazioni = true;
        });
        this.loadedMotivazioni = false;
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
        if (this.idTipoProcedimento != 1) { // uso in linea
            this.loadedTipiContratto = false;
            this.procedimentoService.getTipiContratto().subscribe(data => {
                if (data) {
                    this.tipiContratto = data;
                }
                this.loadedTipiContratto = true;
            });
        }
        this.loadedDettaglioRichiestaFirma = false;
        this.procedimentoService.getFirmaProcedimento().subscribe(data => {
            if (data) {
                this.dettaglioRichiestaFirma = data;
            }
            this.loadedDettaglioRichiestaFirma = true;
        });
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
        }
    }

    check(s: string) {
        setTimeout(() => {
            if (s == 'Motor') {
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

    setDisabletContrattoSelectedOther(bool) {
        this.disableTipoContrattoParent = bool;
    }

    getRichiestaFromChild(richiesta: InserisciRichiestaVO) {
        this.richiesteArray.push(richiesta);
    }
    pulisciRichiestaFromChild(richiesta: InserisciRichiestaVO) {
        let index = this.richiesteArray.findIndex(a => a.tipoProcedimento.id == richiesta.tipoProcedimento.id);
        if (index != -1) {
            this.richiesteArray.splice(index, 1);
        }
    }

    isValidCampiRequired() {
        if (this.richiestaForm && this.richiestaForm.form) {
            if (this.motorizzazioneGroup.controls['motorizzazioneForm'] && this.motorizzazioneGroup.controls['motorizzazioneForm'].hasError('required')) return true;
            if (this.motivazioneGroup.controls['motivazioneForm'] && this.motivazioneGroup.controls['motivazioneForm'].hasError('required')) return true;
            if (this.richiestaForm.form.get('noteMotivazione') && this.richiestaForm.form.get('noteMotivazione').hasError('required')) return true;
            if (this.contrattoGroup.controls['contrattoForm'] && this.contrattoGroup.controls['contrattoForm'].hasError('required')) return true;

        }
        return false;
    }

    isValidoTrasmetti() {
        if (this.richiesteArray.length != this.idTipiProcedimmento.length) {
            return true;
        }
        if (this.richiestaForm && this.richiestaForm.form) {
            if (this.richiestaForm.form.get('ruoloFirma') && this.richiestaForm.form.get('ruoloFirma').hasError('required')) return true;
            if (this.richiestaForm.form.get('nominativoFirma') && this.richiestaForm.form.get('nominativoFirma').hasError('required')) return true;
        }
        return false;
    }

    isLoading() {
        if (!this.loadedMotorizzazioni || !this.loadedMotivazioni || !this.loadedContratti || !this.loadedTipoProcedimento || !this.loadedNumProcedimento || !this.loadedDettaglioRichiestaFirma) return true;
        return false;
    }

    save() {
        this.loadedSave = false;
        this.richiesta.motorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value;
        this.richiesta.motivazione = this.motivazioneGroup.controls['motivazioneForm'].value;
        this.richiesta.contratto = this.contrattoGroup.controls['contrattoForm'].value;
        this.richiesteArray[0].motorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value;
        this.richiesteArray[0].motivazione = this.motivazioneGroup.controls['motivazioneForm'].value;
        this.richiesteArray[0].contratto = this.contrattoGroup.controls['contrattoForm'].value;
        this.richiesteArray[0].noteMotivazione = this.richiesta.noteMotivazione;
        this.richiesteArray[1].motorizzazione = this.motorizzazioneGroup.controls['motorizzazioneForm'].value;
        this.richiesteArray[1].motivazione = this.motivazioneGroup.controls['motivazioneForm'].value;
        this.richiesteArray[1].contratto = this.contrattoGroup.controls['contrattoForm'].value;
        this.richiesteArray[1].noteMotivazione = this.richiesta.noteMotivazione;
        this.richiesteArray.unshift(this.richiesta);

        this.procedimentoService.inserisciRichiestaSostituzione(this.richiesteArray).subscribe(data => {
            this.loadedSave = true;
            this.feedback = "";
            let id: number = data;
            window.scrollTo(0, 0);
            this.snackBar.open("Salvataggio eseguito correttamente!", "Chiudi", {
                duration: 2000,
            });

            setTimeout(() => { this.router.navigate(['/modificaRichiestaSostituzione/' + id, { action: 'inserisci' }]) }, 2000);
        }, error => {
            CommonsHandleException.authorizationError(error, this.router);
            this.feedback = error.error.message;
            console.log("this.feedback =" + this.feedback);
            this.richiesteArray.splice(0, 1);
            window.scrollTo(0, 0);
            this.loadedSave = true;
        });
    }

    ngAfterViewChecked() {
        this.changeDetector.detectChanges();
    }

    ngOnDestroy(): void {
    }

}