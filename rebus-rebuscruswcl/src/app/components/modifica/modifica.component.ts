/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, ChangeDetectorRef, ViewChild, ViewEncapsulation, OnDestroy } from "@angular/core";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { NgForm } from "@angular/forms";
import { MatSnackBar, DateAdapter } from "@angular/material";
import { Router, ActivatedRoute } from "@angular/router";
import { CommonsDeleteSubscribe } from "../../commons/commons-delete-subscribe";
import { ErrorRest } from "../../class/error-rest";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { ModificaLabel } from "../../commons/labels/modifica";
import { saveAs } from "file-saver";
import { DocumentoVO } from "../../vo/documento-vo";
import { ComboVO, TipoCombo, DescrizioneVO } from "../../vo/combo-vo";
import { AutobusLabel } from '../../commons/labels/autobus-label';
import { AutobusVO } from "../../vo/autobus-vo";
import { UserInfo } from "../../vo/funzionario-vo";
import { UtilityService } from "../../services/utility.service";
import { UserService } from "../../services/user.service";
import { AutobusService, Action } from "../../services/autobus.service";
import { DocumentService } from "../../services/document.service";
import { AuthorizationRoles } from "../../class/authorization-roles";
import { TipologiaDimensioneVO } from "../../vo/tiplogia-dimensione-vo";
import { DepositoVO } from "../../vo/deposito-vo";
import { NavbarFilterContext } from "../../services/navbarFilterContext.service";
import { PortabiciVo } from "../../vo/portabici-vo";
import { EmissioniVo } from "../../vo/emissioni-vo";

import { CampagnaVO } from "../../vo/campagna-vo";
import { MisurazioniVO } from "../../vo/misurazioni-vo";
import * as config from '../../globalparameter';
import { PortabiciAutobusVO } from "../../vo/autobus/portabici-autobus-VO";
import { SistemaLocalizzazioneVO } from "../../vo/autobus/sistema-localizzazione-VO";
import { SistemaVideosorveglianzaVO } from "../../vo/autobus/sistema-videosorveglianza-VO";
import { DocVariazAutobusVO } from "../../vo/doc-variaz-autobus-vo";


interface DepositiGroup {
    disabled?: boolean;
    name: string;
    deposito: DepositoVO[];
}

@Component({
    selector: 'app-modifica',
    templateUrl: './modifica.component.html',
    styleUrls: ['./modifica.component.scss'],
    encapsulation: ViewEncapsulation.None,
})
@DestroySubscribers()
export class ModificaComponent implements OnInit, OnDestroy {
    documenti: Array<DocumentoVO> = [];
    documentoSelezionato: DocumentoVO;
    id: number;
    isUtenteAmp: boolean;
    isValid: boolean;
    dettaglioBus: AutobusVO;
    loadComplete: boolean;
    comboVO: ComboVO[];
    depositi: DepositoVO[];
    depositoGroup: DepositiGroup[];
    isDepSelectedPrevalente: boolean;
    indirizzoDepSelected: string;
    telefonoDepSelected: string;
    loadedDepositi: boolean;
    funzionario: UserInfo;
    feedback: string;
    doubleMessages: string[] = [];
    fristMessages: string = "";
    secondMessages: string = "";
    doubleError: boolean = false;
    isDisableCheck: boolean = false;
    isDisableCheckAlien: boolean = false;
    notValid: string = "valid";
    AutobusLabel = AutobusLabel;
    private subscribers: any;
    private fieldsToMap;    // Campi che devono mappare i valori da S/N a boolean
    flags: Array<Boolean>;
    // ModificaLabel = ModificaLabel; //for use in template
    errorFields: Array<String>;
    disableFlagVerificatoAzienda;
    pagMess: string = null;

    //decodifiche select
    classeVeicolo: DescrizioneVO[];
    categoriaVeicolo: DescrizioneVO[];
    tipoEuro: DescrizioneVO[];
    tipiAlimentazione: DescrizioneVO[];
    tipiDispositiviPrevenzione: DescrizioneVO[];
    tipoAudio: DescrizioneVO[];
    tipoVideo: DescrizioneVO[];
    tipoProprieta: DescrizioneVO[];
    tipoAllestimento: DescrizioneVO[];
    tipoFacilitazioni: DescrizioneVO[];
    tipoImmatricolazione: DescrizioneVO[];
    isUtenteAzienda: boolean;
    isUtenteServizio: boolean;
    tipologiaDimensione: TipologiaDimensioneVO;

    daImmatricolareDisabled: boolean;
    daImmatricolareDisabling: boolean;
    erroreLunghezza: boolean;

    isFromInserisci: boolean;
    firstTime: boolean = true;

    campagnaE: CampagnaVO = new CampagnaVO();
    emiss: EmissioniVo = new EmissioniVo();
    campagnaP: CampagnaVO = new CampagnaVO();
    bici: PortabiciVo = new PortabiciVo();

    flgMonitoraggioEmissioni: boolean = false;
    flgMonitoraggioPortabici: boolean = false;

    loadedMisurazioni: boolean;
    misurazioni: MisurazioniVO[];
    emissioni: MisurazioniVO[];
    portabici: MisurazioniVO[];
    listOfPortabiciAutobus: PortabiciAutobusVO[] = [];
    listOfSistemaLocalizzazione: SistemaLocalizzazioneVO[] = [];
    listOfSistemaVideosorveglianza: SistemaVideosorveglianzaVO[] = [];
    listOfDocVariazAutobusVO: DocVariazAutobusVO[] = [];
    listOfDocToShow: any[] = []; // lista dei documenti che vengono mostrati nella dorp down, vengono rimossi e aggiunti i doc in base aquelli inseriti
    disabledAlienato: boolean;

    azione: string;

    @ViewChild('fileInput') fileInput: any;
    panelOpenState: boolean = false;

    flagSelezionato: string;
    tipoAlienato: any[] = []
    dataDisabled: boolean = true;



    filtroDataPrimaImmatricolazione = (d: Date | null): boolean => {
        let isMinoreUgualeDiUltimaImm: boolean;
        let isMinoreDiUltimaRev: boolean;
        let isMinoreDiAlienazione: boolean;
        if (this.dettaglioBus != null) {
            if (this.dettaglioBus.dataUltimaImmatricolazione) {
                isMinoreUgualeDiUltimaImm = true;
            }
            if (this.dettaglioBus.dataUltimaRevisione) {
                isMinoreDiUltimaRev = true;
            }
            if (this.dettaglioBus.dataAlienazione) {
                isMinoreDiAlienazione = true;
            }
            if (isMinoreUgualeDiUltimaImm && isMinoreDiUltimaRev && isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.dettaglioBus.dataUltimaImmatricolazione && d < this.dettaglioBus.dataUltimaRevisione && d < this.dettaglioBus.dataAlienazione;
            } else if (isMinoreUgualeDiUltimaImm && isMinoreDiUltimaRev) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.dettaglioBus.dataUltimaImmatricolazione && d < this.dettaglioBus.dataUltimaRevisione;
            } else if (isMinoreUgualeDiUltimaImm && isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.dettaglioBus.dataUltimaImmatricolazione && d < this.dettaglioBus.dataAlienazione;
            } else if (isMinoreDiUltimaRev && isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d < this.dettaglioBus.dataUltimaRevisione && d < this.dettaglioBus.dataAlienazione;
            } else if (isMinoreUgualeDiUltimaImm) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.dettaglioBus.dataUltimaImmatricolazione;
            } else if (isMinoreDiUltimaRev) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d < this.dettaglioBus.dataUltimaRevisione;
            } else if (isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d < this.dettaglioBus.dataAlienazione;
            }
        }
        return d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900);
    }
    filtroDataUltimaImmatricolazione = (d: Date | null): boolean => {
        if (this.dettaglioBus != null) {
            if (this.dettaglioBus.dataPrimaImmatricolazione != null && this.dettaglioBus.dataAlienazione != null) {
                if (this.dettaglioBus.dataPrimaImmatricolazione.getDate() === 1 && this.dettaglioBus.dataPrimaImmatricolazione.getMonth() === 0 && this.dettaglioBus.dataPrimaImmatricolazione.getFullYear() === 1900)
                    return d.getFullYear() >= 1980 && d < this.dettaglioBus.dataAlienazione;
                else
                    return d >= this.dettaglioBus.dataPrimaImmatricolazione && d < this.dettaglioBus.dataAlienazione;
            }
            if (this.dettaglioBus.dataPrimaImmatricolazione != null) {
                if (this.dettaglioBus.dataPrimaImmatricolazione.getDate() === 1 && this.dettaglioBus.dataPrimaImmatricolazione.getMonth() === 0 && this.dettaglioBus.dataPrimaImmatricolazione.getFullYear() === 1900)
                    return d.getFullYear() >= 1980;
                else
                    return d >= this.dettaglioBus.dataPrimaImmatricolazione;
            }
            if (this.dettaglioBus.dataAlienazione != null) {
                return d.getFullYear() >= 1980 && d < this.dettaglioBus.dataAlienazione;
            }
        }
        return d.getFullYear() >= 1980;
    }
    filtroDataUltimaRevisione = (d: Date | null): boolean => {
        let isMaggioreDiPrimaImm: boolean;
        let isMinoreDiAlienazione: boolean;
        if (this.dettaglioBus != null) {
            if (this.dettaglioBus.dataPrimaImmatricolazione) {
                isMaggioreDiPrimaImm = true;
            }
            if (this.dettaglioBus.dataAlienazione) {
                isMinoreDiAlienazione = true;
            }

            if (isMaggioreDiPrimaImm && isMinoreDiAlienazione) {
                return d.getFullYear() >= 1980 && d > this.dettaglioBus.dataPrimaImmatricolazione && d < this.dettaglioBus.dataAlienazione;
            } else if (isMaggioreDiPrimaImm) {
                return d.getFullYear() >= 1980 && d > this.dettaglioBus.dataPrimaImmatricolazione;
            } else if (isMinoreDiAlienazione) {
                return d.getFullYear() >= 1980 && d < this.dettaglioBus.dataAlienazione;
            }
        }
        return d.getFullYear() >= 1980;
    }
    filtroDataAlienazione = (d: Date | null): boolean => {
        let isMaggioreDiPrimaImm: boolean;
        let isMaggioreDiUltimaImm: boolean;
        let isMaggioreDiUltimaRev: boolean;
        if (this.dettaglioBus != null) {
            if (this.dettaglioBus.dataPrimaImmatricolazione) {
                isMaggioreDiPrimaImm = true;
            }
            if (this.dettaglioBus.dataUltimaImmatricolazione) {
                isMaggioreDiUltimaImm = true;
            }
            if (this.dettaglioBus.dataUltimaRevisione) {
                isMaggioreDiUltimaRev = true;
            }
            if (isMaggioreDiPrimaImm && isMaggioreDiUltimaImm && isMaggioreDiUltimaRev) {
                return d > this.dettaglioBus.dataPrimaImmatricolazione && d > this.dettaglioBus.dataUltimaImmatricolazione && d > this.dettaglioBus.dataUltimaRevisione;
            } else if (isMaggioreDiPrimaImm && isMaggioreDiUltimaImm) {
                return d > this.dettaglioBus.dataPrimaImmatricolazione && d > this.dettaglioBus.dataUltimaImmatricolazione;
            } else if (isMaggioreDiPrimaImm && isMaggioreDiUltimaRev) {
                return d > this.dettaglioBus.dataPrimaImmatricolazione && d > this.dettaglioBus.dataUltimaRevisione;
            } else if (isMaggioreDiUltimaImm && isMaggioreDiUltimaRev) {
                return d > this.dettaglioBus.dataUltimaImmatricolazione && d > this.dettaglioBus.dataUltimaRevisione;
            } else if (isMaggioreDiPrimaImm) {
                return d.getFullYear() >= 1980 && d > this.dettaglioBus.dataPrimaImmatricolazione;
            } else if (isMaggioreDiUltimaImm) {
                return d > this.dettaglioBus.dataUltimaImmatricolazione;
            } else if (isMaggioreDiUltimaRev) {
                return d > this.dettaglioBus.dataUltimaRevisione;
            }
        }
        return d.getFullYear() >= 1980;
    }
    context: string;

    constructor(
        public snackBar: MatSnackBar,
        private utilityService: UtilityService,
        private userService: UserService,
        private router: Router,
        private route: ActivatedRoute,
        private cdRef: ChangeDetectorRef,
        private dateAdapter: DateAdapter<Date>,
        private autobusService: AutobusService,
        private navbarFilterContext: NavbarFilterContext,
        private documentService: DocumentService,
    ) {
        dateAdapter.setLocale('it-IT');
    }
    ngOnDestroy(): void {
    }

    ngOnInit() {
        this.dettaglioBus = new AutobusVO();
        this.documentoSelezionato = new DocumentoVO();
        this.documentService.elencoDocumento(1);
        this.isValid = true;
        this.disableFlagVerificatoAzienda = false;
        this.flags = [];
        this.errorFields = [];
        this.fieldsToMap = [
            'flgSnodato', 'flgConteggiatoMiv', 'flgVeicoloAssicurato', 'flgAvm', 'flgOtx', 'flgContapasseggeri',
            'flgRilevatoreBip', 'flgImpiantoCondizionamento', 'flgDuePiani', 'flgCabinaGuidaIsolata',
            'flagVerificaAzienda', 'flagVerificaAmp', 'flgFiltroFap', 'contributo', 'flagAlienato', 'flgRichiestaContr'
        ];

        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
            this.context = this.navbarFilterContext.InfoFiltro.cod;
        }

        this.pagMess = this.route.snapshot.paramMap.get('PagMess');
        CommonsDeleteSubscribe.deleteSubscribeBykey(this.subscribers, 'route');
        CommonsDeleteSubscribe.deleteSubscribeBykey(this.subscribers, 'dettaglioBus');


        this.azione = this.route.snapshot.paramMap.get('action');
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.isUtenteAmp = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS_AMP);
            this.isUtenteServizio = this.funzionario.authority.includes(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);
        });

        this.utilityService.elencoDecodifiche();
        this.subscribers = this.utilityService.comboVO$.subscribe(data => {
            this.comboVO = data;
            this.comboVO.forEach(item => this.setComboOptions(item));
        });

        if (this.route.snapshot.paramMap.get('action') == 'inserisci') {
            this.isFromInserisci = true;
        }
        else if (this.route.snapshot.paramMap.get('action') == 'ricerca') {
            this.isFromInserisci = false;
        }

        this.subscribers.route = this.route.params.subscribe(params => {
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.loadDettaglioAutobus();
            this.firstTime = true;
        });
        this.loadDropDownContribuzione();
    }

    @ViewChild('dettaglioBusForm') form: NgForm;
    refreshDirective() {
        this.form.form.get('dataPrimaImmatricolazione').markAsUntouched();
        this.form.form.get('dataUltimaImmatricolazione').markAsUntouched();
        this.form.form.get('dataUltimaRevisione').markAsUntouched();
        this.form.form.get('dataAlienazione').markAsUntouched();
    }

    //pulisce input data se passo un parametro di tipo stringa
    checkData(data: Date, name: string) {
        if (data == null)
            this.form.form.get(name).setValue(null);
    }

    private loadDettaglioAutobus() {
        this.loadComplete = false;
        this.listOfDocVariazAutobusVO = [];

        this.subscribers.dettaglioBus = this.autobusService.dettaglioAutobus2(this.id, Action.EDIT).subscribe(data => {
            this.dettaglioBus = data;
            this.loadDescrizioneDocumenti();
            this.flagSelezionato = this.dettaglioBus.flgAlienato;
            this.disabledAlienato = this.dettaglioBus.flgAlienato === 'S' || this.dettaglioBus.flgAlienato === null ? true : false;
            this.tipoAlienato = [];

            let attivo = {
                id: 'N',
                desc: 'Attivo'
            }
            let ritirato = {
                id: 'R',
                desc: 'Ritirato'
            }
            let alienato = {
                id: 'S',
                desc: 'Alienato'
            }

            this.tipoAlienato.push(attivo, ritirato, alienato);

            this.dettaglioBus.campagnaE = this.dettaglioBus.campagna.filter(v => v.idCampagna == 1)
            this.dettaglioBus.campagnaP = this.dettaglioBus.campagna.filter(v => v.idCampagna == 2)

            if (this.dettaglioBus.emissioni)
                this.flgMonitoraggioEmissioni = true;
            if (this.dettaglioBus.portabici)
                this.flgMonitoraggioPortabici = true;
            if (this.dettaglioBus.idAzienda) {
                this.loadDepositi(this.dettaglioBus.idAzienda);
            }


            if (this.dettaglioBus.idTipoAllestimento != null && this.dettaglioBus.idTipoAllestimento != 0) {
                this.autobusService.getTipolgiaDimensione(this.dettaglioBus.idTipoAllestimento).subscribe(data => {
                    if (data) {
                        this.tipologiaDimensione = data;
                    }
                    this.loadComplete = true;
                    this.checkLunghezza(Number(this.dettaglioBus.lunghezza));
                },
                    (err) => {
                        console.error(err);
                    });
            }
            else {
                this.loadComplete = true;
            }


            this.createFlags();

            this.convertDateJs();
            this.entitaContributoPublicoValue();
            this.disableFlagVerificatoAzienda = (this.dettaglioBus.flagVerificaAzienda === "S");
            if (!this.cdRef['destroyed']) {
                this.cdRef.detectChanges();
            }

            if (this.dettaglioBus.flgAlienato === 'S')
                this.dataDisabled = false;

            if (!this.dettaglioBus.daImmatricolare) {
                this.daImmatricolareDisabled = true;
                this.daImmatricolareDisabling = false;
            } else {
                this.daImmatricolareDisabled = false;
                this.daImmatricolareDisabling = true;
            }
            if (!config.isNullOrVoid(this.dettaglioBus.documentiAutobus)) {
                this.listOfDocVariazAutobusVO = JSON.parse(JSON.stringify(this.dettaglioBus.documentiAutobus));
                // Inserisco la descrizione del tipo documento nel documento
                this.listOfDocVariazAutobusVO.forEach(element => {
                    element.descEstesa = this.listOfDocToShow.find(a => a.idTipoDocumento == element.idTipoDocumento).descrizione;
                    // per la visualizzazione della data
                    element.dataCaricamento = new Date(element.dataCaricamento);
                    // rimuovo le voci dei documenti già inseriti
                    let index = this.listOfDocToShow.findIndex(a => a.idTipoDocumento == element.idTipoDocumento);
                    if (index !== -1) {
                        this.listOfDocToShow.splice(index, 1);
                    }
                });
            }

            this.dettaglioBus.flgRichiestaContr = this.dettaglioBus.flgRichiestaContr == 'S' ? true : false;
        }, (err) => {
            CommonsHandleException.authorizationError(err, this.router, '/dettaglioBus/', this.id);
        });
    }



    isUpload: boolean = false;

    private loadDescrizioneDocumenti() {
        this.documentService.elencoDocumento(1);
        this.subscribers = this.documentService.elencoDocumento$.subscribe(
            (data) => {
                this.documenti = JSON.parse(JSON.stringify(data));
                this.listOfDocToShow = JSON.parse(JSON.stringify(this.documenti));
                this.listOfDocToShow.forEach(element => {
                    if (element.idTipoDocumento == 1) {
                        if (!config.isNullOrVoid(this.dettaglioBus.flgContribuzione) && this.dettaglioBus.flgContribuzione) {
                            element.descrizione = element.descrizione + " **"
                        }
                    }
                });
                for (let index = 0; index < this.documenti.length; index++) {
                    let doc: DocumentoVO = this.documenti[index];
                    if (doc.idTipoDocumento == this.dettaglioBus.idDocumento)
                        this.documentoSelezionato = doc;
                }
                if (this.dettaglioBus.daImmatricolare && this.dettaglioBus.isFileUpload == 'noFile') {
                    this.dettaglioBus.idDocumento = 5;
                }
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        )
    }



    loadDepositi(idAzienda: number) {
        this.loadedDepositi = false;
        this.isDepSelectedPrevalente = false;
        this.indirizzoDepSelected = null;
        this.telefonoDepSelected = null;

        this.subscribers = this.autobusService.getDepositi(idAzienda).subscribe(
            (data) => {
                if (data) {

                    let depositiTmp: DepositoVO[] = new Array<DepositoVO>();
                    this.depositi = new Array<DepositoVO>();
                    this.depositoGroup = new Array<DepositiGroup>();
                    for (let d of data) {
                        depositiTmp.push(d);
                        this.depositi.push(d);
                    }

                    if (this.depositi.length > 0) {
                        let depPrev = depositiTmp.splice(depositiTmp.findIndex(d => d.isPrevalente), 1); //metto il deposito prevalente al primo posto
                        //this.depositi.unshift(depPrev);
                        if (depositiTmp.length > 0) {
                            this.depositoGroup.push(
                                {
                                    name: 'Altri depositi',
                                    deposito: depositiTmp
                                }
                            )
                        }
                        if (depPrev.length > 0) {
                            this.depositoGroup.unshift({
                                name: 'Deposito principale',
                                deposito: [depPrev[0]]
                            })
                        }
                        //this.depositi.unshift(depPrev[0]);
                        let dep: DepositoVO;
                        if (this.dettaglioBus.idDeposito) {
                            //se non è in altri depositi ,è dep principale
                            dep = this.depositi.find(d => d.id === this.dettaglioBus.idDeposito);
                            if (dep) {
                                this.dettaglioBus.idDeposito = dep.id;
                                this.isDepSelectedPrevalente = dep.isPrevalente;
                                this.indirizzoDepSelected = dep.indirizzo;
                                this.telefonoDepSelected = dep.telefono;
                            }
                        }
                    }
                }

            }, (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            });
        this.loadedDepositi = true;
    }

    //prova nuova gestione
    loadMisurazioni(primoTelaio: string) {
        this.loadedMisurazioni = false;

        this.subscribers = this.autobusService.getMisurazioni(primoTelaio).subscribe(
            (data) => {
                if (data) {
                    this.misurazioni = new Array<MisurazioniVO>();
                    this.emissioni = new Array<MisurazioniVO>();
                    this.portabici = new Array<MisurazioniVO>();

                    for (let d of data) {
                        this.misurazioni.push(d);
                        if (this.misurazioni) {
                            for (let data of this.misurazioni) {
                                data.dataFineRestituzione = new Date(data.dataFineRestituzione);
                                data.dataFineValidita = new Date(data.dataFineValidita);
                                data.dataAggiornamento = new Date(data.dataAggiornamento);
                                data.dataInizioRestituzione = new Date(data.dataInizioRestituzione);
                                data.dataInizioValidita = new Date(data.dataInizioValidita);
                                data.dataInizio = new Date(data.dataInizio);
                                data.dataFine = new Date(data.dataFine);
                            }
                        }

                        if (d.idCampagna == 1) {
                            this.emissioni.push(d);
                        }
                        if (d.idCampagna == 2) {
                            this.portabici.push(d);
                        }


                    }

                    if (this.misurazioni.length > 0) {

                    }
                }

            }, (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            });
        this.loadedMisurazioni = true;
    }

    changeDeposito(event: any) {
        let dep = this.depositi.find(d => d.id === event.value);
        this.indirizzoDepSelected = dep.indirizzo;
        this.telefonoDepSelected = dep.telefono;
        this.isDepSelectedPrevalente = dep.isPrevalente;
    }

    changeAllestimento(code) {
        if (code == 1) {
            this.dettaglioBus.idTipoAllestimento = 10;
        }
    }

    changeCategoriaVeicolo(code) {
        if (code != 10) {
            this.dettaglioBus.idCategoriaVeicolo = 2;
        }
    }


    private convertDateJs() {
        if (this.dettaglioBus.dataPrimaImmatricolazione)
            this.dettaglioBus.dataPrimaImmatricolazione = new Date(this.dettaglioBus.dataPrimaImmatricolazione);
        if (this.dettaglioBus.dataUltimaImmatricolazione)
            this.dettaglioBus.dataUltimaImmatricolazione = new Date(this.dettaglioBus.dataUltimaImmatricolazione);
        if (this.dettaglioBus.dataAlienazione)
            this.dettaglioBus.dataAlienazione = new Date(this.dettaglioBus.dataAlienazione);
        if (this.dettaglioBus.dataUltimaRevisione)
            this.dettaglioBus.dataUltimaRevisione = new Date(this.dettaglioBus.dataUltimaRevisione);
        if (this.dettaglioBus.dataCaricamentoDoc)
            this.dettaglioBus.dataCaricamentoDoc = new Date(this.dettaglioBus.dataCaricamentoDoc);
        if (this.dettaglioBus.campagna) {
            for (let data of this.dettaglioBus.campagna) {
                data.dataFineRestituzione = new Date(data.dataFineRestituzione);
                data.dataFineValidita = new Date(data.dataFineValidita);
                data.dataAggiornamento = new Date(data.dataAggiornamento);
                data.dataInizioRestituzione = new Date(data.dataInizioRestituzione);
                data.dataInizioValidita = new Date(data.dataInizioValidita);
                data.dataInizio = new Date(data.dataInizio);
                data.dataFine = new Date(data.dataFine);
            }

        }


        if (this.dettaglioBus.campagnaP) {
            for (let data of this.dettaglioBus.campagnaP) {
                data.dataFineRestituzione = new Date(data.dataFineRestituzione);
                data.dataFineValidita = new Date(data.dataFineValidita);
                data.dataAggiornamento = new Date(data.dataAggiornamento);
                data.dataInizioRestituzione = new Date(data.dataInizioRestituzione);
                data.dataInizioValidita = new Date(data.dataInizioValidita);
                data.dataInizio = new Date(data.dataInizio);
                data.dataFine = new Date(data.dataFine);
            }

        }
        if (this.dettaglioBus.campagnaE) {
            for (let data of this.dettaglioBus.campagnaE) {
                data.dataFineRestituzione = new Date(data.dataFineRestituzione);
                data.dataFineValidita = new Date(data.dataFineValidita);
                data.dataAggiornamento = new Date(data.dataAggiornamento);
                data.dataInizioRestituzione = new Date(data.dataInizioRestituzione);
                data.dataInizioValidita = new Date(data.dataInizioValidita);
                data.dataInizio = new Date(data.dataInizio);
                data.dataFine = new Date(data.dataFine);
            }

        }

        if (this.portabici) {
            for (let data of this.portabici) {
                data.dataFineRestituzione = new Date(data.dataFineRestituzione);
                data.dataFineValidita = new Date(data.dataFineValidita);
                data.dataAggiornamento = new Date(data.dataAggiornamento);
                data.dataInizioRestituzione = new Date(data.dataInizioRestituzione);
                data.dataInizioValidita = new Date(data.dataInizioValidita);
                data.dataInizio = new Date(data.dataInizio);
                data.dataFine = new Date(data.dataFine);
            }
        }

    }
    private setComboOptions(item) {
        switch (item.identificativo) {
            case TipoCombo.TIPO_ALIMENTAZIONE:
                this.tipiAlimentazione = item.valori;
                break;
            case TipoCombo.CLASSE_VEICOLO:
                this.classeVeicolo = item.valori;
                break;
            case TipoCombo.TIPO_ALLESTIMENTO:
                this.tipoAllestimento = item.valori;
                break;
            case TipoCombo.TIPO_AUDIO:
                this.tipoAudio = item.valori;
                break;
            case TipoCombo.TIPO_DISPOSITIVI_PREVENZIONE:
                this.tipiDispositiviPrevenzione = item.valori;
                break;
            case TipoCombo.TIPO_EURO:
                this.tipoEuro = item.valori;
                break;
            case TipoCombo.TIPO_FACILITAZIONI:
                this.tipoFacilitazioni = item.valori;
                break;
            case TipoCombo.TIPO_IMMATRICOLAZIONE:
                this.tipoImmatricolazione = item.valori;
                break;
            case TipoCombo.TIPO_VIDEO:
                this.tipoVideo = item.valori;
                break;
            case TipoCombo.PROPRIETA_LEASING:
                this.tipoProprieta = item.valori;
                break;
            case TipoCombo.CATEGORIA_VEICOLO:
                this.categoriaVeicolo = item.valori;
                if (config.isNullOrVoid(this.dettaglioBus.idCategoriaVeicolo)) {
                    // SETTO DI DEFAULT IL VALORE AUTOBUS
                    this.dettaglioBus.idCategoriaVeicolo = 2;
                }
                break;
            default:
                break;
        }
    }

    daImmatricolareOnChange() {
        if (this.dettaglioBus.daImmatricolare) {
            this.dettaglioBus.idTipoImmatricolazione = 0;
            this.dettaglioBus.dataPrimaImmatricolazione = new Date("1/1/1900");
            this.dettaglioBus.targa = null;
            this.dettaglioBus.enteAutorizzPrimaImm = "ND";
            this.dettaglioBus.dataUltimaImmatricolazione = null;
            this.daImmatricolareDisabling = true;
            this.dettaglioBus.flgVeicoloAssicurato = 'N';
            this.dettaglioBus['flagAlienato'] = 'N';
            this.form.form.get('contributoPubblicoAcquisto').disable();
            this.dettaglioBus.contributoPubblicoAcquisto = "";
            this.dettaglioBus.flgRichiestaContr = 'N';
            this.isDisableCheck = false;
            this.dettaglioBus.dataScadVincoliNoAlien = null;
            this.flagSelezionato = null;
            this.dettaglioBus.flgAlienato = null;
            this.disabledAlienato = true;
            this.dettaglioBus.dataUltimaRevisione = null;
            this.dettaglioBus.dataAlienazione = null;
            this.dettaglioBus.prezzoTotAcquisto = null;
            this.form.form.get('dataUltimaRevisione').disable();
            this.dataDisabled = true;
            if (this.dettaglioBus.primoTelaio != null) {
                this.dettaglioBus.telaio = this.dettaglioBus.primoTelaio;
            }
            this.dettaglioBus.idDocumento = 5;
        } else {
            this.daImmatricolareDisabling = false;
            this.dettaglioBus.idDocumento = 1;
            this.flagSelezionato = 'N';
            this.dettaglioBus.flgAlienato = 'N';
            this.dettaglioBus.dataAlienazione = null;
            this.disabledAlienato = false;
        }
    }

    private isValidCampi() {
        let fields = [];

        // contributoPubblicoAcquisto invalid 
        var prezzoInt = Number(this.dettaglioBus.contributoPubblicoAcquisto);
        if (isNaN(prezzoInt)) {
            fields.push(AutobusLabel["entitaContrPub"]);
        }

        // prezzoTotAcquisto invalid 
        prezzoInt = Number(this.dettaglioBus.prezzoTotAcquisto);
        if (isNaN(prezzoInt)) {
            fields.push(AutobusLabel["prezzoTotAcquisto"]);
        }
        prezzoInt = Number(this.dettaglioBus.lunghezza);
        if (isNaN(prezzoInt)) {
            fields.push(AutobusLabel["lunghezza"]);
        }

        this.feedback = `
            Alcuni campi il non hanno il formato corretto.
            Correggere i seguenti campi: ${fields.join(", ")}
        `;

        return fields.length === 0;
    }




    public aggiornaBus() {
        CommonsDeleteSubscribe.deleteSubscribeBykey(this.subscribers, 'modifica');

        if (!this.isValidCampi()) {
            window.scrollTo(0, 0);
            return;
        }
        else {
            this.feedback = "";
        }
        this.isValid = this.checkValidity();


        if (this.isValid) {
            this.dettaglioBus.documentiAutobus = [];
            this.listOfDocVariazAutobusVO.forEach(async element => {
                let doc: DocVariazAutobusVO;
                doc = Object.assign({}, element);
                doc.documento = await this.getByteArrayForBE(element.documento) || null;
                this.dettaglioBus.documentiAutobus.push(doc);
            });
            this.loadComplete = false;
            this.doubleError = false;
            this.dettaglioBus.numeroPorte = this.dettaglioBus.numeroPorte;
            this.dettaglioBus.lunghezza = this.dettaglioBus.lunghezza != null ? new String(this.dettaglioBus.lunghezza).replace(",", ".") : null;
            this.dettaglioBus.nPostiSedere = this.dettaglioBus.nPostiSedere != null ? new String(this.dettaglioBus.nPostiSedere).replace(",", ".") : null;
            this.dettaglioBus.nPostiInPiedi = this.dettaglioBus.nPostiInPiedi != null ? new String(this.dettaglioBus.nPostiInPiedi).replace(",", ".") : null;
            this.dettaglioBus.nPostiRiservati = this.dettaglioBus.nPostiRiservati != null ? new String(this.dettaglioBus.nPostiRiservati).replace(",", ".") : null;
            this.dettaglioBus.postiCarrozzina = this.dettaglioBus.postiCarrozzina != null ? new String(this.dettaglioBus.postiCarrozzina).replace(",", ".") : null;
            this.dettaglioBus.annoSostProg = this.dettaglioBus.annoSostProg != null ? new String(this.dettaglioBus.annoSostProg).replace(",", ".") : null;
            this.dettaglioBus.prezzoTotAcquisto = this.dettaglioBus.prezzoTotAcquisto != null ? new String(this.dettaglioBus.prezzoTotAcquisto).replace(",", ".") : null;
            this.dettaglioBus.contributoPubblicoAcquisto = this.dettaglioBus.contributoPubblicoAcquisto != null ? new String(this.dettaglioBus.contributoPubblicoAcquisto).replace(",", ".") : null;
            this.dettaglioBus.targa = this.dettaglioBus.targa != null ? new String(this.dettaglioBus.targa).replace(/\s+/g, '') : null;
            this.dettaglioBus.flgRichiestaContr = this.dettaglioBus.flgRichiestaContr == true ? 'S' : 'N';
            setTimeout(() => {
                this.updateAutobus();
            }, 100);


        }
        else {
            window.scrollTo(0, 0);
        }
    }

    updateAutobus() {
        this.subscribers.modifica = this.autobusService.modificaBus(this.dettaglioBus, this.isUpload).subscribe(
            (data) => {
                this.loadComplete = true;
                this.snackBar.open("Salvataggio eseguito correttamente!", null, {
                    duration: 2000,
                });

                this.loadDettaglioAutobus();
                this.feedback = "";
                return data;
            },
            (error) => {
                CommonsHandleException.authorizationError(error, this.router);
                this.feedback = error.error.message;
                this.loadComplete = true;
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
            });
    }

    /** 
     * Verifico se ci sono campi non validi per il submit
     * I campi non validi li aggiungo a 'errorFields'.
     * 
     * Se il campo è di tipo comboVO (idTipoImmatricolazione, idTipoAllestimento, ...) ciclo tutti i dettagli,
     * verifico che il valore selezionato in dettaglioBus della combo associata non è "ND"
     * 
     * Ogni case corrisponde ad un campo della quale si effettua la validazione
     * 
     * todo: Creare servizio di validazione dedicato
     */
    private checkValidity() {
        this.errorFields = [];
        let isValid = true;
        let option, value;

        if (this.dettaglioBus.flagVerificaAzienda == 'S') {
            for (let key in this.dettaglioBus) {
                value = this.dettaglioBus[key];

                switch (key) {
                    case "idTipoImmatricolazione":
                        for (let elem of this.tipoImmatricolazione) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idTipoAllestimento":
                        for (let elem of this.tipoAllestimento) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idTipoAlimentazione":
                        for (let elem of this.tipiAlimentazione) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idCategoriaVeicolo":
                        for (let elem of this.categoriaVeicolo) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idClasseVeicolo":
                        for (let elem of this.classeVeicolo) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idProprietaLeasing":
                        for (let elem of this.tipoProprieta) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idClasseAmbientale":
                        for (let elem of this.tipoEuro) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idDotazioneDisabili":
                        for (let elem of this.tipoFacilitazioni) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idImpiantiVisivi":
                        for (let elem of this.tipoVideo) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "idImpiantiAudio":
                        for (let elem of this.tipoAudio) {
                            if (elem.codice === value && elem.descrizione === "ND") {
                                isValid = false;
                                this.errorFields.push(ModificaLabel[key]);
                            }
                        }
                        break;
                    case "tmpIndirizzoDep":
                        if (value === null || value === "" || value === "ND") {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "enteAutorizzPrimaImm":
                        if (value === null || value === "" || value === "ND") {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "lunghezza":
                        if (isNaN(parseFloat(value)) || value === 999 || value === '999' || this.checkLunghezza(value)) {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);

                        }
                        break;
                    case "flgDuePiani":
                        if (value === 'U') {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "flgSnodato":
                        if (value === 'U') {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "modello":
                        if (value === null || value === "" || value === "ND") {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "nPostiInPiedi":
                        if (value === "999" || value == null || isNaN(parseFloat(value))) {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "nPostiSedere":
                        if (value === "999" || value == null || isNaN(parseFloat(value))) {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "postiCarrozzina":
                        if (value === "999" || value == null || isNaN(parseFloat(value))) {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "flgImpiantoCondizionamento":
                        if (value === 'U') {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "flgContapasseggeri":
                        if (value === 'U') {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "flgAvm":
                        if (value === 'U') {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "marca":
                        if (value === null || value === "" || value === "ND") {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                    case "dataPrimaImmatricolazione":
                        let defaultDate = new Date(1900, 0, 1);

                        if (this.dettaglioBus.dataPrimaImmatricolazione.getTime() === defaultDate.getTime()) {
                            isValid = false;
                            this.errorFields.push(ModificaLabel[key]);
                        }
                        break;
                }
            }
        }
        else {
            if (this.dettaglioBus.daImmatricolare) {
                for (let key in this.dettaglioBus) {
                    value = this.dettaglioBus[key];

                    switch (key) {
                        case "idClasseAmbientale":
                            for (let elem of this.tipoEuro) {
                                if (elem.codice === value && elem.descrizione === "ND") {
                                    isValid = false;
                                    this.errorFields.push(ModificaLabel[key]);
                                }
                            }
                            break;
                    }
                }
            }
        }
        return isValid;
    }



    public goBack() {
        let azione = this.route.snapshot.paramMap.get('action');
        if (this.pagMess != null) {
            let idContesto = +this.route.snapshot.paramMap.get('idContesto');
            let idMessaggio = +this.route.snapshot.paramMap.get('PagMess');
            let azione = this.route.snapshot.paramMap.get('azione') || this.route.snapshot.paramMap.get('action');
            this.router.navigate(['/dettaglioMessaggio/' + idMessaggio, { action: azione, idContesto: idContesto }]);
        } else {
            if (azione == 'ricerca') {
                this.router.navigate(['/ricercabusazienda', { action: 'modifica' }]);
            } else {
                window.history.back();
            }
        }
    }

    public isCheckbox(field) {
        return (this.dettaglioBus[field] === 'S' || this.dettaglioBus[field] == 'N');
    }

    /** 
    * Crea un array-specchio di flags in base ai campi di dettaglioBus
    * 
    * dettaglioBus[duePiani] = "S"     =>  flags["duePiani"] = true
    * dettaglioBus[duePiani] = "N"     =>  flags["duePiani"] = false
    * dettaglioBus[duePiani] = ...     =>  flags["duePiani"] = null 
    */
    private createFlags() {
        this.flags = [];

        for (let field of this.fieldsToMap) {
            let value = null;

            if (this.dettaglioBus[field] === 'S' || this.dettaglioBus[field] === 'N' || this.dettaglioBus[field] === 'U') {
                value = (this.dettaglioBus[field] === "S") ? true : value;
                value = (this.dettaglioBus[field] === "N") ? false : value;
                value = (this.dettaglioBus[field] === "U") ? null : value;
            }
            this.flags[field] = value;
        }
    }

    updateFlags(field) {
        if (this.dettaglioBus[field] == 'S')
            this.flags[field] = true;
        if (this.dettaglioBus[field] == 'N')
            this.flags[field] = false;
    }
    updateFieldDettaglioBus(field: string) {
        this.dettaglioBus[field] = (this.flags[field] == true) ? 'S' : 'N';
        if (field === 'flagAlienato') {
            if (!this.flags[field]) {
                this.dettaglioBus.dataAlienazione = null;
            }
        }
    }

    updateFieldDettaglioBus2(field: string) {
        this.dettaglioBus.flgAlienato = field;
        this.flags['flagAlienato'] = field
        if (this.dettaglioBus.flgAlienato === 'S')
            this.dataDisabled = false;
        else {
            this.dataDisabled = true;
            this.dettaglioBus.dataAlienazione = null
        }
    }


    public popolaTipologiaDimensionale(lunghezza: number, idtipoAllestimento: number) {
        if (idtipoAllestimento != null && lunghezza != null) {
            this.dettaglioBus.tipologiaDimensionale = "Calcolo tipologia in corso...";

            this.utilityService.progressivoTipoDimensione(lunghezza, idtipoAllestimento);
            this.subscribers = this.utilityService.tipologiaDimesionale$.subscribe(
                (data) => this.dettaglioBus.tipologiaDimensionale = data.toString(),
                (err) => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            )
        }
    }

    public getTipologiaDimensionale(idtipoAllestimento: number) {
        if (idtipoAllestimento != null && idtipoAllestimento != 0) {
            this.autobusService.getTipolgiaDimensione(idtipoAllestimento).subscribe(data => {
                if (data) {
                    this.tipologiaDimensione = data;
                }
            },
                (err) => {
                    console.error(err);
                });
        }
        else {
            this.dettaglioBus.lunghezza = "999";
            this.dettaglioBus.tipologiaDimensionale = null;
        }
    }

    public popolaScadenzaVincoli() {
        this.refreshDirective();
        //ABILITO LABEL
        if (this.dettaglioBus.dataPrimaImmatricolazione != null &&
            (this.dettaglioBus.contributoPubblicoAcquisto != null && this.dettaglioBus.contributoPubblicoAcquisto != "")
            && this.dettaglioBus.idTipoAllestimento != 0) {

            let year = this.dettaglioBus.dataPrimaImmatricolazione.getFullYear();
            let day = this.dettaglioBus.dataPrimaImmatricolazione.getDate();
            let month = this.dettaglioBus.dataPrimaImmatricolazione.getMonth();


            if (this.dettaglioBus.idTipoAllestimento == 1) {
                year = year + 8;
            } else if (this.dettaglioBus.idTipoAllestimento == 3) {
                year = year + 10;
            }
            let newDate = new Date(year, month, day);
            this.dettaglioBus.dataScadVincoliNoAlien = newDate.toLocaleString('it-IT', { day: 'numeric', month: 'numeric', year: 'numeric' });

            let n: Number = new Number(this.dettaglioBus.contributoPubblicoAcquisto);
            if (this.dettaglioBus.idTipoAllestimento == 2 || n == 0) {
                this.dettaglioBus.dataScadVincoliNoAlien = null;
            }
        } else if ((this.dettaglioBus.dataPrimaImmatricolazione == null
            && (this.dettaglioBus.contributoPubblicoAcquisto == null || this.dettaglioBus.contributoPubblicoAcquisto == "")
            && this.dettaglioBus.idTipoAllestimento == 0)
            ||
            (this.dettaglioBus.dataPrimaImmatricolazione == null
                || (this.dettaglioBus.contributoPubblicoAcquisto == null || this.dettaglioBus.contributoPubblicoAcquisto == "")
                || this.dettaglioBus.idTipoAllestimento == 0)
            || (this.dettaglioBus.dataPrimaImmatricolazione != null
                && (this.dettaglioBus.contributoPubblicoAcquisto == null || this.dettaglioBus.contributoPubblicoAcquisto == "")
                || this.dettaglioBus.idTipoAllestimento == 0)) {
            this.dettaglioBus.dataScadVincoliNoAlien = null;
        }
    }



    troncaTreDigitali(numero: string) {
        this.utilityService.troncaTreDigitali(numero, this.dettaglioBus);
    }

    troncaDueDigitali(numero: string, numToTrunc: string) {
        this.utilityService.troncaDueDigitali(numero, numToTrunc, this.dettaglioBus);
    }

    troncaDecimali(numero: string, numToTrunc: string) {
        this.utilityService.troncaDecimali(numero, numToTrunc, this.dettaglioBus);
    }

    filterDispositiviPrevInc(choice) {
        if (choice.codice == 0) {
            this.dettaglioBus.dispositiviPrevIncidenti = new Array();
            this.dettaglioBus.dispositiviPrevIncidenti.push(0);
        }
        else {
            this.dettaglioBus.dispositiviPrevIncidenti = this.dettaglioBus.dispositiviPrevIncidenti.filter(val => {
                return val != 0;
            })
        }
    }

    //forzo la stessa classe ambientale e alimentazione per elettrico e metano
    changeClasseAmbientale(choice) {
        if (choice.codice == 6) {
            this.dettaglioBus.idClasseAmbientale = 9;
        }
        else if (choice.codice == 4) {
            this.dettaglioBus.idClasseAmbientale = 10;
        } else {
            if ((this.dettaglioBus.idClasseAmbientale != 0) && (this.dettaglioBus.idClasseAmbientale == 9 || this.dettaglioBus.idClasseAmbientale == 10))
                this.dettaglioBus.idClasseAmbientale = 0
        }
    }

    changeIdAlimentazione(choice) {
        if (choice.codice == 9) {
            this.dettaglioBus.idTipoAlimentazione = 6;
        }
        else if (choice.codice == 10) {
            this.dettaglioBus.idTipoAlimentazione = 4;
        }
        else {
            if ((this.dettaglioBus.idTipoAlimentazione != 0) && (this.dettaglioBus.idTipoAlimentazione == 6 || this.dettaglioBus.idTipoAlimentazione == 4))
                this.dettaglioBus.idTipoAlimentazione = 0
        }
    }

    changeEntitaContributoPubblico() {
        this.troncaDueDigitali(this.dettaglioBus.contributoPubblicoAcquisto, 'entita')
        this.entitaContributoPublicoValue();
        this.popolaScadenzaVincoli();
    }

    entitaContributoPublicoValue() {
        if (this.dettaglioBus.contributoPubblicoAcquisto != null && this.dettaglioBus.contributoPubblicoAcquisto != "") {
            let contributo = this.dettaglioBus.contributoPubblicoAcquisto != null ? new String(this.dettaglioBus.contributoPubblicoAcquisto).replace(",", ".") : null;
            let n: Number = new Number(contributo);
            if (n > 0) {
                this.isDisableCheck = true;
                this.dettaglioBus.flgRichiestaContr = 'S';
                this.flags['flgRichiestaContr'] = false;
            }
            else {
                this.isDisableCheck = false;
            }
        }
        else {
            this.isDisableCheck = false;
        }
    }

    @ViewChild('dettaglioBusForm') formGroup: NgForm;
    isValidCampiRequired() {
        if (this.formGroup && this.formGroup.form) {
            if (this.formGroup.form.get('altraAlimentazione') && this.formGroup.form.get('altraAlimentazione').hasError('required')) return true;
            if (this.formGroup.form.get('altriDisositiviPrevenzInc') && this.formGroup.form.get('altriDisositiviPrevenzInc').hasError('required')) return true;
            if (this.formGroup.form.get('annoSostProg') && this.formGroup.form.get('annoSostProg').hasError('required')) return true;
            if (this.formGroup.form.get('azienda') && this.formGroup.form.get('azienda').hasError('required')) return true;
            if (this.formGroup.form.get('caratteristicheParticolari') && this.formGroup.form.get('caratteristicheParticolari').hasError('required')) return true;
            if (this.formGroup.form.get('classeVeicolo') && this.formGroup.form.get('classeVeicolo').hasError('required')) return true;
            if (this.formGroup.form.get('contributo') && this.formGroup.form.get('contributo').hasError('required')) return true;
            if (this.formGroup.form.get('contributoPubblicoAcquisto') && (this.formGroup.form.get('contributoPubblicoAcquisto').hasError('prezzoTotAcquistoisString') || this.formGroup.form.get('contributoPubblicoAcquisto').hasError('contributoPubblicoAcquistoMAXPrezzoTotAcquistoModifica'))) return true;
            if (this.formGroup.form.get('prezzoTotAcquisto') && (this.formGroup.form.get('prezzoTotAcquisto').hasError('prezzoTotAcquistoisString') || this.formGroup.form.get('prezzoTotAcquisto').hasError('prezzoTotAcquistoMINContributoPubblicoAcquistoModifica'))) return true;
            if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('required')) return true;
            if (this.formGroup.form.get('dataCaricamentoDoc') && this.formGroup.form.get('dataCaricamentoDoc').hasError('required')) return true;
            if (this.formGroup.form.get('dataPrimaImmatricolazione') && this.formGroup.form.get('dataPrimaImmatricolazione').hasError('required')) return true;
            if (this.formGroup.form.get('dataScadVincoliNoAlien') && this.formGroup.form.get('dataScadVincoliNoAlien').hasError('required')) return true;
            if (this.formGroup.form.get('dataUltimaImmatricolazione') && this.formGroup.form.get('dataUltimaImmatricolazione').hasError('required')) return true;
            if (this.formGroup.form.get('dataUltimaRevisione') && this.formGroup.form.get('dataUltimaRevisione').hasError('required')) return true;
            if (this.formGroup.form.get('documento') && this.formGroup.form.get('documento').hasError('required')) return true;
            if (this.formGroup.form.get('enteAutorizzPrimaImm') && this.formGroup.form.get('enteAutorizzPrimaImm').hasError('required')) return true;
            if (this.formGroup.form.get('idDotazioneDisabili') && this.formGroup.form.get('idDotazioneDisabili').hasError('required')) return true;
            if (this.formGroup.form.get('idImpiantiAudio') && this.formGroup.form.get('idImpiantiAudio').hasError('required')) return true;
            if (this.formGroup.form.get('idImpiantiVisivi') && this.formGroup.form.get('idImpiantiVisivi').hasError('required')) return true;
            if (this.formGroup.form.get('idProprietaLeasing') && this.formGroup.form.get('idProprietaLeasing').hasError('required')) return true;
            if (this.formGroup.form.get('idTipoAllestimento') && this.formGroup.form.get('idTipoAllestimento').hasError('required')) return true;
            if (this.formGroup.form.get('idTipoAlimentazione') && this.formGroup.form.get('idTipoAlimentazione').hasError('required')) return true;
            if (this.formGroup.form.get('idTipoImmatricolazione') && this.formGroup.form.get('idTipoImmatricolazione').hasError('required')) return true;
            if (this.formGroup.form.get('lunghezza') && (this.formGroup.form.get('lunghezza').hasError('required') || this.formGroup.form.get('lunghezza').hasError('tooLong'))) return true;
            if (this.formGroup.form.get('marca') && this.formGroup.form.get('marca').hasError('required')) return true;
            if (this.formGroup.form.get('matricola') && this.formGroup.form.get('matricola').hasError('required')) return true;
            if (this.formGroup.form.get('modello') && this.formGroup.form.get('modello').hasError('required')) return true;
            if (this.formGroup.form.get('idCategoriaVeicolo') && this.formGroup.form.get('idCategoriaVeicolo').hasError('required')) return true;

            if (this.formGroup.form.get('nPostiInPiedi') && this.formGroup.form.get('nPostiInPiedi').hasError('required')) return true;
            if (this.formGroup.form.get('nPostiRiservati') && this.formGroup.form.get('nPostiRiservati').hasError('required')) return true;
            if (this.formGroup.form.get('nPostiSedere') && this.formGroup.form.get('nPostiSedere').hasError('required')) return true;
            if (this.formGroup.form.get('notaRiservataAmp') && this.formGroup.form.get('notaRiservataAmp').hasError('required')) return true;
            if (this.formGroup.form.get('notaRiservataAzienda') && this.formGroup.form.get('notaRiservataAzienda').hasError('required')) return true;
            if (this.formGroup.form.get('note') && this.formGroup.form.get('note').hasError('required')) return true;
            if (this.formGroup.form.get('noteDocumento') && this.formGroup.form.get('noteDocumento').hasError('required')) return true;
            if (this.formGroup.form.get('numeroPorte') && this.formGroup.form.get('numeroPorte').hasError('required')) return true;
            if (this.formGroup.form.get('omologazioneDirettivaEuropea') && this.formGroup.form.get('omologazioneDirettivaEuropea').hasError('required')) return true;
            if (this.formGroup.form.get('postiCarrozzina') && this.formGroup.form.get('postiCarrozzina').hasError('required')) return true;
            if (this.formGroup.form.get('prezzoTotAcquisto') && this.formGroup.form.get('prezzoTotAcquisto').hasError('required')) return true;
            if (this.formGroup.form.get('primoTelaio') && this.formGroup.form.get('primoTelaio').hasError('required')) return true;
            if (this.formGroup.form.get('targa') && this.formGroup.form.get('targa').hasError('required')) return true;
            if (this.formGroup.form.get('telaio') && this.formGroup.form.get('telaio').hasError('required')) return true;
            if (this.formGroup.form.get('tipologiaDimensionale') && this.formGroup.form.get('tipologiaDimensionale').hasError('required')) return true;
            if (this.formGroup.form.get('dispositiviPrevInc') && this.formGroup.form.get('dispositiviPrevInc').hasError('required')) return true;
            if (this.formGroup.form.get('motivazione') && this.formGroup.form.get('motivazione').hasError('required')) return true;
            if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('required')) return true;
        }
        return false;
    }

    markAsTouched() {
        this.form.form.get('contributoPubblicoAcquisto').updateValueAndValidity();
        this.form.form.get('prezzoTotAcquisto').updateValueAndValidity();
    }


    isCampiInvalid() {


        if (this.formGroup.form.get('numeroPorte') && this.formGroup.form.get('numeroPorte').errors) return true;
        // dataPrimaImmatricolazione
        if (this.formGroup.form.get('dataPrimaImmatricolazione') && this.formGroup.form.get('dataPrimaImmatricolazione').hasError('primaMaggioreDiUltima')) return true;
        if (this.formGroup.form.get('dataPrimaImmatricolazione') && this.formGroup.form.get('dataPrimaImmatricolazione').hasError('primaMaggioreUgualeUltimaRevisione')) return true;
        if (this.formGroup.form.get('dataPrimaImmatricolazione') && this.formGroup.form.get('dataPrimaImmatricolazione').hasError('primaMaggioreUgualeAlienazione')) return true;
        if (this.formGroup.form.get('dataPrimaImmatricolazione') && this.formGroup.form.get('dataPrimaImmatricolazione').hasError('valoreNonConsentito')) return true;
        if (this.formGroup.form.get('dataPrimaImmatricolazione') && this.formGroup.form.get('dataPrimaImmatricolazione').hasError('primaMaggioreSostProg')) return true;

        // dataUltimaImmatricolazione
        if (this.formGroup.form.get('dataUltimaImmatricolazione') && this.formGroup.form.get('dataUltimaImmatricolazione').hasError('ultimaMinoreDiPrima')) return true;
        if (this.formGroup.form.get('dataUltimaImmatricolazione') && this.formGroup.form.get('dataUltimaImmatricolazione').hasError('ultimaMaggioreUgualeDataAlienazione')) return true;
        if (this.formGroup.form.get('dataUltimaImmatricolazione') && this.formGroup.form.get('dataUltimaImmatricolazione').hasError('valoreNonConsentito')) return true;

        // dataUltimaRevisione
        if (this.formGroup.form.get('dataUltimaRevisione') && this.formGroup.form.get('dataUltimaRevisione').hasError('ultimaRevMinoreUgualePrimaImm')) return true;
        if (this.formGroup.form.get('dataUltimaRevisione') && this.formGroup.form.get('dataUltimaRevisione').hasError('ultimaRevMaggioreUgualeDataAlien')) return true;
        if (this.formGroup.form.get('dataUltimaRevisione') && this.formGroup.form.get('dataUltimaRevisione').hasError('valoreNonConsentito')) return true;

        // prezzoTotAcquisto
        if (this.formGroup.form.get('prezzoTotAcquisto') && this.formGroup.form.get('prezzoTotAcquisto').hasError('prezzoTotAcquistoMINContributoPubblicoAcquistoModifica')) return true;
        // contributoPubblicoAcquisto
        if (this.formGroup.form.get('contributoPubblicoAcquisto') && this.formGroup.form.get('contributoPubblicoAcquisto').hasError('contributoPubblicoAcquistoMAXPrezzoTotAcquistoModifica')) return true;

        // dataAlienazione
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('alienazioneMinoreUgualePrimaImm')) return true;
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('alienazioneMinoreUgualeUltimaImm')) return true;
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('alienazioneMinoreUgualeUltimaRev')) return true;
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('valoreNonConsentito')) return true;

        //lunghezza autobus
        if (this.formGroup.form.get('lunghezza') && this.form.form.get('lunghezza').hasError('tooLong')) return true;

        // annoSostProg
        if (this.formGroup.form.get('annoSostProg') && this.formGroup.form.get('annoSostProg').hasError('primaMaggioreSostProg')) return true;


    }


    changeLunghezza(lunghezza: string) {
        lunghezza = lunghezza != null ? lunghezza : '0';
        if (typeof lunghezza !== 'string') {
            lunghezza = '' + lunghezza;
        }
        this.troncaTreDigitali(lunghezza);
        lunghezza = lunghezza.replace(",", ".");
        let regex: RegExpMatchArray = lunghezza.match("[0-9]+([,\.][0-9]{0,3})?");
        if (regex != null) {
            lunghezza = regex[0];
        }
        let l: number = lunghezza != null ? Number(lunghezza) : 0;
        this.dettaglioBus.lunghezza = lunghezza;
        this.popolaTipologiaDimensionale(l, this.dettaglioBus.idTipoAllestimento);
        if (this.checkLunghezza(l)) {
            this.formGroup.form.get('lunghezza').markAsTouched();
            this.formGroup.form.get('lunghezza').setErrors({
                tooLong: true
            });
            this.formGroup.form.get('lunghezza').updateValueAndValidity();
        }
        else {

            this.formGroup.form.get('lunghezza').setErrors(null);
        }

    }
    checkLunghezza(l: number) {
        if (isNaN(l)) {
            return false;
        }
        if (this.tipologiaDimensione == null || (l <= this.tipologiaDimensione.lunghezzaMax && l >= this.tipologiaDimensione.lunghezzaMin) || l == 999) {
            this.erroreLunghezza = false;
            return false

        } else {
            this.erroreLunghezza = true;
            return true;

        }
    }

    verificaAnnoSostProg(calledFromTemplate: boolean) {
        let primaMaggioreSostProg: boolean = false;
        if (this.dettaglioBus.dataPrimaImmatricolazione && this.dettaglioBus.annoSostProg) {
            primaMaggioreSostProg = this.dettaglioBus.dataPrimaImmatricolazione.getFullYear() > Number(this.dettaglioBus.annoSostProg) ? true : false;
        }
        if (this.formGroup && this.formGroup.form) {
            if (primaMaggioreSostProg) {
                this.formGroup.form.get('annoSostProg').setErrors({
                    primaMaggioreSostProg: primaMaggioreSostProg
                });
            }
            if (!primaMaggioreSostProg && this.dettaglioBus.annoSostProg) {
                this.formGroup.form.get('annoSostProg').markAsUntouched();
            } else {
                this.formGroup.form.get('annoSostProg').markAsTouched();
            }
        }


        if (calledFromTemplate) {
            this.verificaDataPrimaImmatricolazione(false);
        }
    }

    verificaDataPrimaImmatricolazione(calledFromTemplate: boolean) {
        let primaMaggioreDiUltima: boolean = false;
        let primaMaggioreUgualeUltimaRevisione: boolean = false;
        let primaMaggioreUgualeAlienazione: boolean = false;
        let primaMaggioreSostProg: boolean = false;
        let valoreNonConsentito: boolean = false;


        if (this.dettaglioBus.dataPrimaImmatricolazione && this.dettaglioBus.dataUltimaImmatricolazione)
            primaMaggioreDiUltima = this.dettaglioBus.dataPrimaImmatricolazione.getTime() > this.dettaglioBus.dataUltimaImmatricolazione.getTime() ? true : false;
        if (this.dettaglioBus.dataPrimaImmatricolazione && this.dettaglioBus.dataUltimaRevisione)
            primaMaggioreUgualeUltimaRevisione = this.dettaglioBus.dataPrimaImmatricolazione.getTime() >= this.dettaglioBus.dataUltimaRevisione.getTime() ? true : false;
        if (this.dettaglioBus.dataAlienazione && this.dettaglioBus.dataPrimaImmatricolazione)
            primaMaggioreUgualeAlienazione = this.dettaglioBus.dataPrimaImmatricolazione.getTime() >= this.dettaglioBus.dataAlienazione.getTime() ? true : false;
        if (this.dettaglioBus.dataPrimaImmatricolazione && this.dettaglioBus.annoSostProg) {
            primaMaggioreSostProg = this.dettaglioBus.dataPrimaImmatricolazione.getFullYear() > Number(this.dettaglioBus.annoSostProg) ? true : false;
        }
        if (this.dettaglioBus.dataPrimaImmatricolazione) {
            if (this.dettaglioBus.dataPrimaImmatricolazione.getFullYear() < 1980)
                valoreNonConsentito = true;
            if (this.dettaglioBus.dataPrimaImmatricolazione.getFullYear() == 1900 && this.dettaglioBus.dataPrimaImmatricolazione.getMonth() == 0 && this.dettaglioBus.dataPrimaImmatricolazione.getDay() == 1)
                valoreNonConsentito = false;
        }
        if (this.formGroup && this.formGroup.form && this.dettaglioBus.dataPrimaImmatricolazione != null) {
            if (primaMaggioreDiUltima || primaMaggioreUgualeUltimaRevisione || primaMaggioreUgualeAlienazione || valoreNonConsentito || primaMaggioreSostProg) {
                this.formGroup.form.get('dataPrimaImmatricolazione').setErrors({
                    primaMaggioreDiUltima: primaMaggioreDiUltima,
                    primaMaggioreUgualeUltimaRevisione: primaMaggioreUgualeUltimaRevisione,
                    primaMaggioreUgualeAlienazione: primaMaggioreUgualeAlienazione,
                    primaMaggioreSostProg: primaMaggioreSostProg,
                    valoreNonConsentito: valoreNonConsentito
                });
            } else {
                this.formGroup.form.get('dataPrimaImmatricolazione').setErrors(null);
            }
            if (!primaMaggioreDiUltima && !primaMaggioreUgualeAlienazione && !primaMaggioreUgualeUltimaRevisione && !valoreNonConsentito && !primaMaggioreSostProg) {
                this.formGroup.form.get('dataPrimaImmatricolazione').markAsUntouched();
            } else {
                this.formGroup.form.get('dataPrimaImmatricolazione').markAsTouched();
            }
        }
        if (calledFromTemplate) {
            this.verificaDataUltimaImmatricolazione(false);
            this.verificaDataUltimaRevisione(false);
            this.verificaDataAlienazione(false);
            this.verificaAnnoSostProg(false);
        }
    }

    verificaDataUltimaImmatricolazione(calledFromTemplate: boolean) {

        let ultimaMinoreDiPrima: boolean = false;
        let ultimaMaggioreUgualeDataAlienazione: boolean = false;
        let valoreNonConsentito: boolean = false;

        if (this.dettaglioBus.dataUltimaImmatricolazione && this.dettaglioBus.dataPrimaImmatricolazione)
            ultimaMinoreDiPrima = this.dettaglioBus.dataUltimaImmatricolazione.getTime() < this.dettaglioBus.dataPrimaImmatricolazione.getTime() ? true : false;
        if (this.dettaglioBus.dataUltimaImmatricolazione && this.dettaglioBus.dataAlienazione)
            ultimaMaggioreUgualeDataAlienazione = this.dettaglioBus.dataUltimaImmatricolazione.getTime() >= this.dettaglioBus.dataAlienazione.getTime() ? true : false;
        if (this.dettaglioBus.dataUltimaImmatricolazione)
            valoreNonConsentito = this.dettaglioBus.dataUltimaImmatricolazione.getFullYear() < 1980 ? true : false;
        if (this.formGroup && this.formGroup.form && this.dettaglioBus.dataUltimaImmatricolazione != null) {
            if (ultimaMinoreDiPrima || ultimaMaggioreUgualeDataAlienazione || valoreNonConsentito) {
                this.formGroup.form.get('dataUltimaImmatricolazione').setErrors({
                    ultimaMinoreDiPrima: ultimaMinoreDiPrima,
                    ultimaMaggioreUgualeDataAlienazione: ultimaMaggioreUgualeDataAlienazione,
                    valoreNonConsentito: valoreNonConsentito
                });
            } else {
                this.formGroup.form.get('dataUltimaImmatricolazione').setErrors(null);
            }
            if (!ultimaMinoreDiPrima && !ultimaMaggioreUgualeDataAlienazione && !valoreNonConsentito) {
                this.formGroup.form.get('dataUltimaImmatricolazione').markAsUntouched();
            } else {
                this.formGroup.form.get('dataUltimaImmatricolazione').markAsTouched();
            }
        }
        if (calledFromTemplate) {
            this.verificaDataPrimaImmatricolazione(false);
            this.verificaDataUltimaRevisione(false);
            this.verificaDataAlienazione(false);
        }
    }

    verificaDataUltimaRevisione(calledFromTemplate: boolean) {

        let ultimaRevMinoreUgualePrimaImm: boolean = false;
        let ultimaRevMaggioreUgualeDataAlien: boolean = false;
        let valoreNonConsentito: boolean = false;

        if (this.dettaglioBus.dataUltimaRevisione && this.dettaglioBus.dataPrimaImmatricolazione)
            ultimaRevMinoreUgualePrimaImm = this.dettaglioBus.dataUltimaRevisione.getTime() <= this.dettaglioBus.dataPrimaImmatricolazione.getTime() ? true : false;
        if (this.dettaglioBus.dataUltimaRevisione && this.dettaglioBus.dataAlienazione)
            ultimaRevMaggioreUgualeDataAlien = this.dettaglioBus.dataUltimaRevisione.getTime() >= this.dettaglioBus.dataAlienazione.getTime() ? true : false;
        if (this.dettaglioBus.dataUltimaRevisione) {
            valoreNonConsentito = this.dettaglioBus.dataUltimaRevisione.getFullYear() < 1980 ? true : false;
        }

        if (this.formGroup && this.formGroup.form && this.dettaglioBus.dataUltimaRevisione != null) {
            if (ultimaRevMinoreUgualePrimaImm || ultimaRevMaggioreUgualeDataAlien || valoreNonConsentito) {
                this.formGroup.form.get('dataUltimaRevisione').setErrors({
                    ultimaRevMinoreUgualePrimaImm: ultimaRevMinoreUgualePrimaImm,
                    ultimaRevMaggioreUgualeDataAlien: ultimaRevMaggioreUgualeDataAlien,
                    valoreNonConsentito: valoreNonConsentito
                });
            } else {
                this.formGroup.form.get('dataUltimaRevisione').setErrors(null);
            }
            if (!ultimaRevMinoreUgualePrimaImm && !ultimaRevMaggioreUgualeDataAlien && !valoreNonConsentito) {
                this.formGroup.form.get('dataUltimaRevisione').markAsUntouched();
            } else {
                this.formGroup.form.get('dataUltimaRevisione').markAsTouched();
            }
        }
        if (calledFromTemplate) {
            this.verificaDataUltimaImmatricolazione(false);
            this.verificaDataPrimaImmatricolazione(false);
            this.verificaDataAlienazione(false);
        }
    }

    verificaDataAlienazione(calledFromTemplate: boolean) {

        let alienazioneMinoreUgualePrimaImm: boolean = false;
        let alienazioneMinoreUgualeUltimaImm: boolean = false;
        let alienazioneMinoreUgualeUltimaRev: boolean = false;
        let valoreNonConsentito: boolean = false;

        if (this.dettaglioBus.dataAlienazione && this.dettaglioBus.dataPrimaImmatricolazione)
            alienazioneMinoreUgualePrimaImm = this.dettaglioBus.dataAlienazione.getTime() <= this.dettaglioBus.dataPrimaImmatricolazione.getTime() ? true : false;
        if (this.dettaglioBus.dataAlienazione && this.dettaglioBus.dataUltimaImmatricolazione)
            alienazioneMinoreUgualeUltimaImm = this.dettaglioBus.dataAlienazione.getTime() <= this.dettaglioBus.dataUltimaImmatricolazione.getTime() ? true : false;
        if (this.dettaglioBus.dataAlienazione && this.dettaglioBus.dataUltimaRevisione)
            alienazioneMinoreUgualeUltimaRev = this.dettaglioBus.dataAlienazione.getTime() <= this.dettaglioBus.dataUltimaRevisione.getTime() ? true : false;
        if (this.dettaglioBus.dataAlienazione) {
            valoreNonConsentito = this.dettaglioBus.dataAlienazione.getFullYear() < 1980 ? true : false;
        }

        if (this.formGroup && this.formGroup.form && this.dettaglioBus.dataAlienazione != null) {
            if (alienazioneMinoreUgualePrimaImm || alienazioneMinoreUgualeUltimaImm || alienazioneMinoreUgualeUltimaRev || valoreNonConsentito) {
                this.formGroup.form.get('dataAlienazione').setErrors({
                    alienazioneMinoreUgualePrimaImm: alienazioneMinoreUgualePrimaImm,
                    alienazioneMinoreUgualeUltimaImm: alienazioneMinoreUgualeUltimaImm,
                    alienazioneMinoreUgualeUltimaRev: alienazioneMinoreUgualeUltimaRev,
                    valoreNonConsentito: valoreNonConsentito
                });
            } else {
                this.formGroup.form.get('dataAlienazione').setErrors(null);
            }
            if (!alienazioneMinoreUgualePrimaImm && !alienazioneMinoreUgualeUltimaImm && !alienazioneMinoreUgualeUltimaRev && !valoreNonConsentito) {
                this.formGroup.form.get('dataAlienazione').markAsUntouched();
            } else {
                this.formGroup.form.get('dataAlienazione').markAsTouched();
            }
        }
        if (calledFromTemplate) {
            this.verificaDataUltimaImmatricolazione(false);
            this.verificaDataPrimaImmatricolazione(false);
            this.verificaDataUltimaRevisione(false);
        }
    }

    ngAfterContentChecked() {
        this.cdRef.detectChanges();
        if (this.firstTime && this.dettaglioBus && this.dettaglioBus.id && this.formGroup.form && (this.formGroup.form.get('dataPrimaImmatricolazione') && this.dettaglioBus.dataPrimaImmatricolazione) && (this.formGroup.form.get('dataUltimaImmatricolazione')) && (this.formGroup.form.get('dataUltimaRevisione')) && (this.formGroup.form.get('dataAlienazione')) && this.loadComplete) {
            this.verificaDataPrimaImmatricolazione(false);
            this.verificaDataUltimaImmatricolazione(false);
            this.verificaDataUltimaRevisione(false);
            this.verificaDataAlienazione(false);
            this.firstTime = false;
        }
    }

    ngAfterViewInit() {
        window.scrollTo(0, 0);
    }

    addEmissione() {
        this.campagnaE.dataAggiornamento = new Date(Date.now());
        let lunghezza = this.dettaglioBus.campagna.length;
        this.campagnaE.idMisurazione = lunghezza + 1;
        this.campagnaE.codTipoMonitoraggio = "E";
        this.campagnaE.idCampagna = 1;
        this.emiss.idMisurazione = lunghezza + 1;
        this.campagnaE.dataFineRestituzione = this.dettaglioBus.campagnaE[0].dataFineRestituzione;
        this.campagnaE.dataFineValidita = this.dettaglioBus.campagnaE[0].dataFineValidita;
        this.campagnaE.dataInizioValidita = this.dettaglioBus.campagnaE[0].dataInizioValidita;
        this.campagnaE.dataInizioRestituzione = this.dettaglioBus.campagnaE[0].dataInizioRestituzione;
        this.campagnaE.descrizione = this.dettaglioBus.campagnaE[0].descrizione;
        this.campagnaE.primoTelaio = this.dettaglioBus.campagnaE[0].primoTelaio;
        this.dettaglioBus.campagnaE.push(this.campagnaE);
        this.dettaglioBus.emissioni.push(this.emiss);
        this.dettaglioBus.campagna.push(this.campagnaE);
        this.campagnaE = new CampagnaVO();
        this.emiss = new EmissioniVo();

    }

    addPortabici() {
        this.campagnaP.dataAggiornamento = new Date(Date.now());
        let lunghezza = this.dettaglioBus.campagna.length;
        this.campagnaP.idMisurazione = lunghezza + 1;
        this.campagnaP.codTipoMonitoraggio = "E";
        this.campagnaP.idCampagna = 1;
        this.bici.idMisurazione = lunghezza + 1;
        this.campagnaP.dataFineRestituzione = this.dettaglioBus.campagnaP[0].dataFineRestituzione;
        this.campagnaP.dataFineValidita = this.dettaglioBus.campagnaP[0].dataFineValidita;
        this.campagnaP.dataInizioValidita = this.dettaglioBus.campagnaP[0].dataInizioValidita;
        this.campagnaP.dataInizioRestituzione = this.dettaglioBus.campagnaP[0].dataInizioRestituzione;
        this.campagnaP.descrizione = this.dettaglioBus.campagnaP[0].descrizione;
        this.campagnaP.primoTelaio = this.dettaglioBus.campagnaP[0].primoTelaio;
        this.dettaglioBus.campagnaP.push(this.campagnaP);
        this.dettaglioBus.portabiciList.push(this.bici);
        this.dettaglioBus.campagna.push(this.campagnaP);
        this.campagnaP = new CampagnaVO();
        this.bici = new EmissioniVo();
    }

    getElencoPortabici(m: number) {
        return this.dettaglioBus.portabiciList.filter(v => v.idMisurazione == m)
    }

    getElencoEmissioni(m: number) {
        return this.dettaglioBus.emissioni.filter(v => v.idMisurazione == m)
    }

    /**********************************************************************************************************************************************
     * 
     *                                             CONTRIBUZIONE E RENDICONTAZIONE 
     * 
     *********************************************************************************************************************************************/



    //*****************************************************************  DROP DOWN  ************************************************************************/

    loadDropDownContribuzione() {

        this.subscribers = this.autobusService.getAllPortabiciForAutobus().subscribe(
            (data) => this.listOfPortabiciAutobus = data,
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

        this.subscribers = this.autobusService.getAllSistemaLocalizzazioneForAutobus().subscribe(
            (data) => {
                this.listOfSistemaLocalizzazione = data;
                let index = this.listOfSistemaLocalizzazione.findIndex(a => a.descSistemaLocalizzazione === 'NO');
                this.listOfSistemaLocalizzazione.splice(index, 1);
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );
        this.subscribers = this.autobusService.getAllSistemaVideosorveglianzaForAutobus().subscribe(
            (data) => this.listOfSistemaVideosorveglianza = data,
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );
    }



    resetFieldsDotazioniSpecifiche() {
        if (this.dettaglioBus.flgContribuzione != true) {
            this.dettaglioBus.fkSistemaLocalizzazione = undefined;
            this.dettaglioBus.flgContapasseggeriIntegrato = null;
            this.dettaglioBus.flgBipCablato = null;
            this.dettaglioBus.flgSistemiProtezioneAutista = null;
            this.dettaglioBus.fkSistemaVideosorveglianza = undefined;
            this.dettaglioBus.fkPortabici = undefined;
            this.dettaglioBus.altriAllestimenti = undefined;
            this.listOfDocToShow = JSON.parse(JSON.stringify(this.documenti));
        } else {
            this.listOfDocToShow = JSON.parse(JSON.stringify(this.documenti));
            this.listOfDocToShow.forEach(element => {
                if (element.idTipoDocumento == 1) {
                    element.descrizione = element.descrizione + " **"
                }
            });
        }
        if (this.dettaglioBus.flgAvm == 'N' || this.dettaglioBus.flgAvm == undefined) {
            this.dettaglioBus.fkSistemaLocalizzazione = undefined;
        }
        if (this.dettaglioBus.flgContapasseggeri == 'N' || this.dettaglioBus.flgContapasseggeri == undefined) {
            this.dettaglioBus.flgContapasseggeriIntegrato = null;
        }
        if (this.dettaglioBus.flgRilevatoreBip == 'S') {
            this.dettaglioBus.flgBipCablato = null;
        }
    }


    //*****************************************************************  DOCUMENTI  ************************************************************************/


    uploadDoc(files: File[]) {
        this.dettaglioBus.file = files[0];
        this.dettaglioBus.nomeFile = files[0].name
    }

    handleFileInput() {
        if (
            !config.isNullOrVoid(this.dettaglioBus.idDocumento) &&
            !config.isNullOrVoid(this.dettaglioBus.nomeFile)
        ) {
            // this.files = files[0];
            let allegato: DocVariazAutobusVO = new DocVariazAutobusVO();
            let doc = this.listOfDocToShow.find(a => a.idTipoDocumento == this.dettaglioBus.idDocumento);
            let indexDoc = this.listOfDocToShow.findIndex(a => a.idTipoDocumento == this.dettaglioBus.idDocumento);
            let descDoc = doc.descrizione;
            this.listOfDocToShow.splice(indexDoc, 1); // rimuovo il tipo doc che e' stato inserito
            allegato.documento = this.dettaglioBus.file;
            allegato.nomeFile = this.dettaglioBus.nomeFile
            allegato.idTipoDocumento = this.dettaglioBus.idDocumento;
            allegato.note = this.dettaglioBus.noteDocumento;
            allegato.dataCaricamento = new Date();
            allegato.descEstesa = descDoc;

            this.listOfDocVariazAutobusVO.push(allegato);

            this.dettaglioBus.idDocumento = null;
            this.dettaglioBus.noteDocumento = null;
            this.dettaglioBus.file = null;
            this.dettaglioBus.nomeFile = null;
            this.fileInput.nativeElement.value = "";
        }
    }

    // METODO PER SCARICARE I DOCUMENTI DELL'AUTOBUS
    downloadDoc(index: string) {
        let nameDoc = this.listOfDocVariazAutobusVO[index].nomeFile;
        let file;
        if (!config.isNullOrVoid(this.listOfDocVariazAutobusVO[index].idVariazAutobus)) {
            this.documentService.getContenutoDocumentoById(this.dettaglioBus.id, this.listOfDocVariazAutobusVO[index].idTipoDocumento)
                .subscribe(
                    res => {
                        saveAs(res, nameDoc);
                    },
                    err => {
                        let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                        console.error(errorRest.message);
                    }
                );
        } else {
            file = this.listOfDocVariazAutobusVO[index].documento;
            saveAs(file, nameDoc);
        }
    }

    // ELIMINA I DOCUMENTI DELL'AUTOBUS
    eliminaDoc(index: number) {
        let tipoDoc = this.documenti.find(a => a.idTipoDocumento == this.listOfDocVariazAutobusVO[index].idTipoDocumento);
        this.listOfDocToShow.push(tipoDoc); // inserisco nella lista il tipo doc che viene eliminato
        this.listOfDocVariazAutobusVO.splice(index, 1);
        // ordino gli oggetti in base all'id tipo documento

        this.listOfDocToShow = JSON.parse(JSON.stringify(this.documenti));
        this.listOfDocToShow.forEach(element => {
            if (element.idTipoDocumento == 1) {
                if (!config.isNullOrVoid(this.dettaglioBus.flgContribuzione) && this.dettaglioBus.flgContribuzione) {
                    element.descrizione = element.descrizione + " **"
                }
            }
        });
        this.listOfDocToShow.sort((a, b) => a.idTipoDocumento < b.idTipoDocumento ? -1 : a.idTipoDocumento > b.idTipoDocumento ? 1 : 0);
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

}

