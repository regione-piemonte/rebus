/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { Router, ActivatedRoute } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import { saveAs } from "file-saver";
import { ErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { AutobusVO } from '../../vo/autobus-vo';
import { UserInfo } from '../../vo/funzionario-vo';
import { UserService } from '../../services/user.service';
import { AutobusService, Action } from '../../services/autobus.service';
import { DocumentService } from '../../services/document.service';
import { AuthorizationRoles } from '../../class/authorization-roles';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { ProcedimentoService } from '../../services/procedimento.service';
import { AutobusLabel } from "../../commons/labels/autobus-label";
import { ContribuzioneService } from "../../services/contribuzione.service";
import * as config from '../../globalparameter';
import { AssegnazioneRisorseVO } from "../../vo/contribuzione/assegnazione-risorse-vo";
import { AttoAssegnazioneRisorseVo } from "../../vo/contribuzione/atto-assegnazione-risorse-vo";
import { ContribuzioneCompletaVO } from "../../vo/contribuzione/contribuzione-completa-vo";
import { DatoFatturaVO } from "../../vo/contribuzione/dato-fattura-vo";
import { FonteFinanziamentoVO } from "../../vo/contribuzione/fonte-finanziamento-vo";
import { TipoDocumentoQuietanzaVO } from "../../vo/contribuzione/tipo-documento-quietanza-vo";
import { TipoSostituzioneVO } from "../../vo/contribuzione/tipo-sostituzione-vo";
import { VoceDiCostoContribuzioneVO } from "../../vo/contribuzione/voce-costo-contribuzione-vo";
import { VoceCostoFornituraVO } from "../../vo/contribuzione/voce-costo-fornitura-vo";
import { IterProcedimentoVO } from "../../vo/iter-procedimento-vo";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { TransizioneAutomaVO } from "../../vo/transizione-automa-vo";
import { DocVariazAutobusVO } from '../../vo/doc-variaz-autobus-vo';



@Component({
    selector: 'app-dettaglio',
    templateUrl: './dettaglio.component.html',
    styleUrls: ['./dettaglio.component.scss'],
    encapsulation: ViewEncapsulation.None
})

export class DettaglioComponent implements OnInit {
    id: number;
    dettaglioBus: AutobusVO;
    denomDeposito: string;
    isDepositoPrevalente: string;
    indirizzoDeposito: string;
    telefonoDeposito: string;
    isUtenteAzienda: boolean;
    loadComplete: boolean;
    funzionario: UserInfo;
    isUtenteServizio: boolean;
    isUtenteAmp: boolean;
    isDeposito: boolean;
    crono: boolean
    proc: boolean
    private subscribers: any;
    context: string;
    downloadR: boolean;
    downloadA: boolean;


    AutobusLabel = AutobusLabel;
    contribuzione: ContribuzioneCompletaVO;

    listOfFonteFinanziamento: FonteFinanziamentoVO[] = [];
    listOfAttiAssegnazioneRisorse: AttoAssegnazioneRisorseVo[] = [];

    tipoFonte: string;
    tipoAtti: string;
    tipo: string;
    tipoQuietanza: any;
    idAttoAssegnazioneRisorse: number;
    contributoPubblico: number;

    assegnazioneRisorse: AssegnazioneRisorseVO = new AssegnazioneRisorseVO();
    lista: any[] = [];
    allVoceCosto: VoceCostoFornituraVO[] = [];
    listOfVoceCostoFornituraToShow: any[] = [];
    listOfVociCosto: VoceDiCostoContribuzioneVO[] = [];
    listOfVociCostoToShow: VoceDiCostoContribuzioneVO[] = [];
    listOfOggettoFattura: any[] = [];
    listOfTipiSostituzione: TipoSostituzioneVO[] = [];
    listOfTipiDocumentoQuietanza: TipoDocumentoQuietanzaVO[] = [];
    listOfDatoBonificoToShow: any[] = [];

    allQuietanza: DatoFatturaVO[] = [];
    allFattura: any[] = [];

    documentiContribuzione: any[] = [];
    documenti: any[] = [];
    desc: any;

    listOfDatoFatturaToShow: any[] = [];
    listOfDatoQuietanzaToShow: any[] = [];

    loadContribuzione: boolean;
    loadDettaglioRich: boolean = true;

    fragment: string;
    pannello: any;

    //ITER PROCEDIMENTO
    iter: IterProcedimentoVO;

    transizioniAutomaA: Array<TransizioneAutomaVO>;
    transizioneAutomaA: TransizioneAutomaVO;
    transizioneGroupA: FormGroup = new FormGroup({ transizioneForm: new FormControl() });
    notaTransizioneA: string = "";
    dettaglioRichiesta: DettaglioRichiestaVO;
    loadedTransizioniAutomaA: boolean;
    isContrattoChangedA: boolean = false;
    transizioniAutomaB: Array<TransizioneAutomaVO>;
    transizioneAutomaB: TransizioneAutomaVO;
    transizioneGroupB: FormGroup = new FormGroup({ transizioneForm: new FormControl() });
    notaTransizioneB: string = "";
    loadedTransizioniAutomaB: boolean;
    isContrattoChangedB: boolean = false;
    richiestaConfermata: boolean;
    azione: string;
    statoTransizioneA: number; // Usato per mostrare l'iter o l'errore
    statoTransizioneB: number; // Usato per mostrare l'iter o l'errore

    listOfDocVariazAutobusVO: DocVariazAutobusVO[] = [];

    prova: string = '270px'


    pagMess: string = null;
    // @ViewChild("rendicontazione") rendicontazione: any;
    @ViewChild("rendicontazione") inputEl: ElementRef;


    constructor(
        private procedimentoService: ProcedimentoService,
        private router: Router,
        private userService: UserService,
        private route: ActivatedRoute,
        private navbarFilterContext: NavbarFilterContext,
        private autobusService: AutobusService,

        private cdRef: ChangeDetectorRef,
        private documentService: DocumentService,
        private contribuzioneService: ContribuzioneService) { }

    ngOnInit() {
        if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
            this.context = this.navbarFilterContext.InfoFiltro.cod;
        }
        this.dettaglioRichiesta = new DettaglioRichiestaVO();
        this.assegnazioneRisorse = null;
        this.contribuzione = null;
        this.userService.funzionarioVo$.subscribe(data => {
            this.funzionario = data;
            this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
            this.isUtenteServizio = this.funzionario.authority.includes(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);
            this.isUtenteAmp = this.funzionario.authority.includes(AuthorizationRoles.MODIFICA_BUS_AMP);
        });


        this.pagMess = this.route.snapshot.paramMap.get('PagMess');

        this.subscribers = this.route.params.subscribe(params => {
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.callDettaglioAutobus();
        });

        this.loadDropDownContribuzione();


    }



    callDettaglioAutobus() {
        this.loadComplete = false;
        this.dettaglioBus = null;
        this.subscribers = this.autobusService.dettaglioAutobus2(this.id, Action.VIEW).subscribe(async data => {
            this.dettaglioBus = data;
            if (data.cronologia) {
                this.dettaglioBus.cronologia = data.cronologia
                this.crono = true;
            }
            if (this.dettaglioBus.idDeposito) {
                this.isDeposito = true;
                this.autobusService.getDepositoById(this.dettaglioBus.idDeposito).subscribe(data2 => {
                    if (data2) {
                        this.denomDeposito = data2.denominazione;
                        this.isDepositoPrevalente = data2.isPrevalente == null ? "NO" : (data2.isPrevalente ? "SI" : "NO");
                        this.indirizzoDeposito = data2.indirizzo;
                        this.telefonoDeposito = data2.telefono;
                    }
                });
            }
            if (!config.isNullOrVoid(this.dettaglioBus.documentiAutobus)) {
                this.listOfDocVariazAutobusVO = JSON.parse(JSON.stringify(this.dettaglioBus.documentiAutobus));
                // Inserisco la descrizione del tipo documento nel documento
                this.listOfDocVariazAutobusVO.forEach(element => {
                    element.descEstesa = this.documenti.find(a => a.idTipoDocumento == element.idTipoDocumento).descrizione;
                    // per la visualizzazione della data
                    element.dataCaricamento = new Date(element.dataCaricamento);
                    // Tipo documento carta di circolazione
                    // setto la carta di circolazione
                    if (element.idTipoDocumento == 1) {
                        this.dettaglioBus.idDocumento = element.idTipoDocumento;
                        this.dettaglioBus.noteDocumento = element.note;
                        this.dettaglioBus.descrizioneDocumento = element.descEstesa;
                        this.dettaglioBus.nomeFile = element.nomeFile;
                    }
                });
            }
            if (this.dettaglioBus.idProcedimento != null) {
                this.loadDettaglioRich = false;
                this.loadContribuzione = false;
                this.callContribuzione();
            }
            else this.loadContribuzione = true;
            this.route.fragment.subscribe(fragment => {
                if (fragment === 'rendicontazione') {
                    var interval = setInterval(() => {
                        if (this.inputEl) {
                            setTimeout(() => {
                                // this.inputEl.nativeElement.focus();
                                this.inputEl.nativeElement.scrollIntoView();
                            }, 0);
                            clearInterval(interval)
                        }
                    }, 200);
                }
            });

            this.loadComplete = true;
        }, (err) => {
            CommonsHandleException.authorizationError(err, this.router, '/dettaglioBus/', this.id);
        });


    }


    loadDropDownContribuzione() {
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
                //lista che mostro da cui rimuovo e aggiungo le voci di costo in base a quelle che vengono scelte
                this.listOfVociCostoToShow = JSON.parse(JSON.stringify(this.listOfVociCosto));
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );
        this.subscribers = this.contribuzioneService.getAllAttoAssegnazioneRisorse().subscribe(
            (data) => this.listOfAttiAssegnazioneRisorse = data,
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
    }


    isLoading() {
        if (this.loadComplete && this.loadContribuzione && this.loadDettaglioRich)
            return true;
        return false;
    }

    //contribuzione
    callContribuzione() {
        let fontiFinanziamento: FonteFinanziamentoVO[];
        this.subscribers = this.contribuzioneService.getAllFonteFinanziamento().subscribe(
            data => {
                fontiFinanziamento = data;
            }
        );
        this.documentService.elencoDocumento(1);
        this.subscribers = this.documentService.elencoDocumento$.subscribe(
            (data) => {
                this.documenti = data;
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            });

        this.documentService.elencoDocumento(5);
        this.subscribers = this.documentService.elencoDocumentoContribuzione$.subscribe(
            (data) => {
                this.documentiContribuzione = data;
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        )

        this.subscribers = this.contribuzioneService.getContribuzioneCompletaByIdProcedimento(this.dettaglioBus.idProcedimento).subscribe(
            data => {
                if (data) {
                    this.contribuzione = data;
                    this.loadDettaglioRichiesta();
                    this.assegnazioneRisorse = data.assegnazioneRisorse;

                    this.idAttoAssegnazioneRisorse = fontiFinanziamento.find(a => a.idFonteFinanziamento === this.assegnazioneRisorse.idFonteFinanziamento).idAttoAssegnazione;
                    this.loadAttiAssegnazione(this.idAttoAssegnazioneRisorse);
                    this.loadFontiFinanziamento(this.assegnazioneRisorse.idFonteFinanziamento);
                    if (this.contribuzione.datoMessaServizio.flgPannello == true) {
                        this.pannello = 'Si';
                    } else if (config.isNullOrVoid(this.contribuzione.datoMessaServizio.flgPannello)) {
                        this.pannello = '-';
                    } else {
                        this.pannello = 'No';
                    }
                    this.calculateContributoPubblico()
                    this.allVoceCosto = data.vociCosto;
                    this.addToVociCostoForniture(this.allVoceCosto);


                    this.lista = this.contribuzione.datiFattura;
                    this.lista = this.lista.map(a => {
                        return {
                            ...a,
                            descOggetto: ""
                        }
                    })
                    for (const elem of this.lista) {
                        if (elem.idTipoDocQuietanza == null) {
                            let listaIdVoceCosto = [];
                            let listVoci = [];
                            elem.listaIdVoceCosto.forEach(voce => {
                                let idVoceCosto = this.allVoceCosto.find(a => a.idVoceCostoFornitura == voce).idVoceCosto;
                                let voceCosto = this.listOfVociCosto.find(a => a.idVoceCosto == idVoceCosto).descVoceCosto;
                                listVoci.push(voceCosto)
                                listaIdVoceCosto.push(idVoceCosto)

                            })
                            elem.listaIdVoceCosto = listaIdVoceCosto;
                            elem.numero = elem.numero || null;
                            elem.data = new Date(elem.data) || null;
                            elem.importo = elem.importo || null;
                            elem.vociCosto = listVoci//.join(", ")
                            elem.rowHeight = (48 * listVoci.length / 1.4) + 'px';
                            this.listOfDatoFatturaToShow.push(elem);
                        }
                        else {
                            elem.dataQuietanzaAzForn = new Date(elem.dataQuietanzaAzForn);
                            elem.tipoDoc = this.listOfTipiDocumentoQuietanza.find(a => a.idTipoDocQuietanza == elem.idTipoDocQuietanza).descTipoDocQuietanza;
                            this.listOfDatoQuietanzaToShow.push(elem);
                        }
                    }
                    this.createListOfBonificiForGet(this.contribuzione.datiFattura);
                    this.loadLabelDocumenti();
                    if (this.contribuzione != null) {
                        this.loadContribuzione = true;
                    }
                }
            }, (error) => {
                CommonsHandleException.authorizationError(error, this.router);
            });

    }

    loadDettaglioRichiesta() {
        this.procedimentoService.dettaglioRichiesta(this.dettaglioBus.idProcedimento, Action.EDIT).subscribe(data => {

            if (data) {
                this.dettaglioRichiesta = data;
                this.loadDettaglioRich = true;
            }
            else this.loadDettaglioRich = true;
        }, (error) => {
            CommonsHandleException.authorizationError(error, this.router);
        });
    }


    loadSostituzione(idSostituzione: number) {
        this.subscribers = this.contribuzioneService.getAllTipoSostituzione().subscribe(
            (data) => {
                if (data) {
                    this.listOfTipiSostituzione = data;
                    this.tipo = this.listOfTipiSostituzione.find(t => t.idTipoSostituzione == idSostituzione).descTipoSostituzione;
                }
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

    }

    loadLabelDocumenti() {
        // DOCUMENTAZIONE CONTRATTUALE
        if (this.contribuzione.documentoContribuzione) {
            let tipoDocVoceCosto = this.documentiContribuzione.find(a => a.idTipoDocumento == 40);
            this.contribuzione.documentoContribuzione.idTipoDocumento = tipoDocVoceCosto.idTipoDocumento || null;
            this.contribuzione.documentoContribuzione.label = tipoDocVoceCosto.descrizione || null;
            this.contribuzione.documentoContribuzione.descEstesa = tipoDocVoceCosto.descEstesa || null;
        }
        for (const elem of this.listOfDatoFatturaToShow) {
            // DOCUMENTO FATTURA
            let tipoDocFattura = this.documentiContribuzione.find(a => a.idTipoDocumento == 41);
            elem.documento.idTipoDocumento = tipoDocFattura.idTipoDocumento || null;
            elem.documento.label = tipoDocFattura.descrizione || null;
            elem.documento.descEstesa = tipoDocFattura.descEstesa || null;
        }
        // DOCUMENTO QUIETANZA
        for (const elem of this.listOfDatoQuietanzaToShow) {
            let tipoDocQuietanza = this.documentiContribuzione.find(a => a.idTipoDocumento == 42);
            elem.documento.idTipoDocumento = tipoDocQuietanza.idTipoDocumento || null;
            elem.documento.label = tipoDocQuietanza.descrizione || null;
            elem.documento.descEstesa = tipoDocQuietanza.descEstesa || null;
        }

        // DOCUMENTO CARTA CIRCOLAZIONE
        if (this.contribuzione.documentoCartaCircolazione) {
            let tipoDocCartaCircolazione: any = this.documenti.find(a => a.idTipoDocumento == 1);
            this.contribuzione.documentoCartaCircolazione.idTipoDocumento = tipoDocCartaCircolazione.idTipoDocumento || null;
            this.contribuzione.documentoCartaCircolazione.label = tipoDocCartaCircolazione.descrizione || null;
            this.contribuzione.documentoCartaCircolazione.descEstesa = tipoDocCartaCircolazione.descEstesa || null;
        }
        // DOCUMENTO ATTO D'OBBLIGO
        if (this.contribuzione.documentoAttoObbligo) {
            let tipoDocAttoObbligo = this.documentiContribuzione.find(a => a.idTipoDocumento == 44);
            this.contribuzione.documentoAttoObbligo.idTipoDocumento = tipoDocAttoObbligo.idTipoDocumento || null;
            this.contribuzione.documentoAttoObbligo.label = tipoDocAttoObbligo.descrizione || null;
            this.contribuzione.documentoAttoObbligo.descEstesa = tipoDocAttoObbligo.descEstesa || null;
        }
        // DOCUMENTO GARANZIA
        if (this.contribuzione.documentoGaranzia) {
            let tipoDocGaranzia = this.documentiContribuzione.find(a => a.idTipoDocumento == 43);
            this.contribuzione.documentoGaranzia.idTipoDocumento = tipoDocGaranzia.idTipoDocumento || null;
            this.contribuzione.documentoGaranzia.label = tipoDocGaranzia.descrizione || null;
            this.contribuzione.documentoGaranzia.descEstesa = tipoDocGaranzia.descEstesa || null;
        }
        // DOCUMENTO ALIENAZIONE
        if (this.contribuzione.documentoAlienazione) {
            let tipoDocAlienazione = this.documentiContribuzione.find(a => a.idTipoDocumento == 45);
            this.contribuzione.documentoAlienazione.idTipoDocumento = tipoDocAlienazione.idTipoDocumento || null;
            this.contribuzione.documentoAlienazione.label = tipoDocAlienazione.descrizione || null;
            this.contribuzione.documentoAlienazione.descEstesa = tipoDocAlienazione.descEstesa || null;
        }
        // DOCUMENTO MISURA EMISSIONI
        if (this.contribuzione.documentoMisureEmissioni) {
            let tipoDocMisuraEmissioni = this.documentiContribuzione.find(a => a.idTipoDocumento == 46);
            this.contribuzione.documentoMisureEmissioni.idTipoDocumento = tipoDocMisuraEmissioni.idTipoDocumento || null;
            this.contribuzione.documentoMisureEmissioni.label = tipoDocMisuraEmissioni.descrizione || null;
            this.contribuzione.documentoMisureEmissioni.descEstesa = tipoDocMisuraEmissioni.descEstesa || null;
        }
    }

    //fondo Finanziamento descrizione
    loadFontiFinanziamento(idFonteFinanziamento: number) {
        this.subscribers = this.contribuzioneService.getAllFonteFinanziamentoByIdAttoAssegnazione(this.idAttoAssegnazioneRisorse).subscribe(
            (data) => {
                if (data) {
                    this.tipoFonte = data.find(t => t.idFonteFinanziamento == idFonteFinanziamento).descBreve;
                }
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );
    }

    loadAttiAssegnazione(idAttoAssegnazioneRisorse: number) {
        this.subscribers = this.contribuzioneService.getAllAttoAssegnazioneRisorse().subscribe(
            (data) => {
                this.tipoAtti = data.find(t => t.idAttoAssegnazione == this.idAttoAssegnazioneRisorse).descBreve;
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );
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


    addToVociCostoForniture(allVoceCosto: any) {
        this.subscribers = this.contribuzioneService.getAllVoceCostoContribuzione().subscribe(
            (data) => {
                this.listOfVociCosto = data;
                for (const element of this.allVoceCosto) {
                    let desc = this.listOfVociCosto.find(a => a.idVoceCosto == element.idVoceCosto).descVoceCosto;
                    let voce = {
                        idVoceCosto: element.idVoceCosto,
                        descVoceCosto: desc,
                        importo: element.importo
                    }
                    this.listOfVoceCostoFornituraToShow.push(voce);
                }
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );

    }


    downloadDoc(idDoc: number, nameDoc: string) {
        let doc;
        this.subscribers = this.contribuzioneService.getDocContribuzione(idDoc).subscribe(
            data => {
                doc = data;
                saveAs(doc, nameDoc);
            }
        );
    }

    public selectedTab(e) {
        switch (e.index) {
            case 0:
                this.router.navigateByUrl('/ricercabusazienda');
                break;
            case 1:
                this.router.navigateByUrl('/upload');
                break;
            case 2:
                this.router.navigateByUrl('/download');
                break;

            default:
                break;
        }
    }

    goBack() {

        this.route.fragment.subscribe(fragment => {
            if (this.pagMess != null) {
                let idContesto = +this.route.snapshot.paramMap.get('idContesto');
                let idMessaggio = +this.route.snapshot.paramMap.get('PagMess');
                let azione = (this.route.snapshot.paramMap.get('action') || this.route.snapshot.paramMap.get('azione'));
                this.router.navigate(['/dettaglioMessaggio/' + idMessaggio, { action: azione, idContesto: idContesto }]);


            }
            else {
                if (fragment == 'rendicontazione') {
                    this.router.navigate(['/ricercaContribuzione']);
                } else {
                    this.router.navigate(['/ricercabusazienda', { action: 'dettaglio' }]);
                }
            }
        });
    }


    public isSelect(field: string) {
        if (field == 'S') return "SI";
        if (field == 'N') return 'NO';
        else return "ND";
    }

    public isBoolean(field: any) {
        if (field == true) return "SI";
        if (field == false) return 'NO';
        return '';
    }

    public isChecked(field: string) {
        if (field == 'S') return "SI";
        if (field == 'N') return 'NO';
        return '';

    }

    ngAfterViewInit() {
        this.cdRef.detectChanges();
        window.scrollTo(0, 0);
        const fragment = document.createRange().createContextualFragment("<script type='text/javascript'>" +
            "alert('123123213');" +
            "</script>");
        document.createElement('div').appendChild(fragment);
    }

    //controllo esistenza deposito
    hasDeposito() {
        //variabile inizializzata da this.dettaglioBus.idDeposito
        if (!this.isDeposito) {
            return false;
        }

        else return true;
    }

    hasCrono() {
        //variabile inizializzata da this.dettaglioBus.idDeposito
        if (!this.crono) {
            return false;
        }

        else return true;
    }


    //action: procedimento
    dettaglioVeicolo(value: number) {
        if (this.dettaglioBus.procedimenti.find(r => r.idProc === value).idTipoProc == 2) {
            this.router.navigate(['/dettaglioRichiestaSostituzione/' + value, { action: 'ricerca' }]);
        } else if (this.dettaglioBus.procedimenti.find(r => r.idProc === value).idTipoProc == 5) {
            this.router.navigate(['/dettaglioRichiestaUsoInLinea/' + value, { action: 'ricerca' }]);
        } else {
            this.router.navigate(['/dettaglioRichiesta/' + value, { action: 'ricerca' }]);
        }
    }

    scaricaPDF(idProc: number, richiesta: String, n: number, numProc: number) {
        this.downloadR = false;
        let nomeFile = richiesta.split(' ').join('_');
        this.documentService.getContenutoDocumentoByIdProcedimento(idProc, n)
            .subscribe(
                res => {
                    if (res != null) {
                        saveAs(res, nomeFile);
                    }
                    this.downloadR = true;
                },
                err => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                    this.downloadR = true;
                }
            );


    }


    //gestione scaricamento allegato
    download(index: number, tipoDoc?: string) {
        let nameDoc;
        let tipoDocumento;
        if (!config.isNullOrVoid(index)) {
            nameDoc = this.listOfDocVariazAutobusVO[index].nomeFile;
            tipoDocumento = this.listOfDocVariazAutobusVO[index].idTipoDocumento;
        }
        if (config.isNullOrVoid(index) && tipoDoc == "carta") {
            nameDoc = this.dettaglioBus.nomeFile;
            tipoDocumento = this.dettaglioBus.idDocumento;
        }
        this.documentService.getContenutoDocumentoById(this.dettaglioBus.id, tipoDocumento)
            .subscribe(
                res => {
                    saveAs(res, nameDoc);
                },
                err => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
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
            let numeriFatture: any[] = listFatture.map((a: any) => 'Fattura Nr: ' + a.numero);
            dato = {
                data: new Date(bonifico.dataBonifico),
                importo: bonifico.importoBonifico,
                cro: bonifico.cro,
                idDatoBonifico: bonifico.idDatoBonifico,
                numeriFatture: numeriFatture,
                listFatture: listFatture,
                rowHeight: (48 * listFatture.length / 1.4) + 'px'
            }
            this.listOfDatoBonificoToShow.push(dato)
        })
    }
}