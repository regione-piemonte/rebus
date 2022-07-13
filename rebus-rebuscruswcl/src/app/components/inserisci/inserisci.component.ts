/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { Component, OnInit, OnDestroy, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { MatCheckbox, MatSnackBar } from '@angular/material';
import { NgForm } from '@angular/forms';
import { ViewChild } from '@angular/core';
import { DestroySubscribers } from '../../decorator/destroy-subscribers';
import { ErrorRest } from '../../class/error-rest';
import { CommonsHandleException } from '../../commons/commons-handle-exception';
import { AutobusLabel } from '../../commons/labels/autobus-label';
import { ComboVO } from '../../vo/combo-vo';
import { InserisciAutobusVO } from '../../vo/inserisci-autobus-vo';
import { UserInfo } from '../../vo/funzionario-vo';
import { UtilityService } from '../../services/utility.service';
import { UserService } from '../../services/user.service';
import { AutobusService } from '../../services/autobus.service';
import { DocumentService } from '../../services/document.service';
import { UtenteAzEnteVO } from '../../vo/utenteAzEnte-vo';
import { Subscription } from 'rxjs';
import { EntitaContributoPubblicoANDPrezzoTotaleAcquisto } from '../../directive/entitaContributoPubblicoPrezzoTotraleAcquisto.directive';
import { TipologiaDimensioneVO } from '../../vo/tiplogia-dimensione-vo';
import { DepositoVO } from '../../vo/deposito-vo';
import { NavbarFilterContext } from '../../services/navbarFilterContext.service';
import { saveAs } from 'file-saver';
import * as config from '../../globalparameter';
import { PortabiciAutobusVO } from '../../vo/autobus/portabici-autobus-VO';
import { SistemaLocalizzazioneVO } from '../../vo/autobus/sistema-localizzazione-VO';
import { SistemaVideosorveglianzaVO } from '../../vo/autobus/sistema-videosorveglianza-VO';
import { DocVariazAutobusVO } from '../../vo/doc-variaz-autobus-vo';

interface DepositiGroup {
    disabled?: boolean;
    name: string;
    deposito: DepositoVO[];
}

@Component({
    selector: 'app-inserisci',
    templateUrl: './inserisci.component.html',
    styleUrls: ['./inserisci.component.scss'],
    encapsulation: ViewEncapsulation.None,
})
@DestroySubscribers()
export class InserisciComponent implements OnInit, OnDestroy {
    subscribers: any;   // subscribers that are unsubscribe OnDestroy
    autobus: InserisciAutobusVO;
    aziende: any;
    azienda: any;
    documenti: any;
    comboChoices: ComboVO[];
    depositi: DepositoVO[];
    depositoGroup: DepositiGroup[];
    isDepSelectedPrevalente: boolean;
    indirizzoDepSelected: string;
    telefonoDepSelected: string;
    feedback: string;
    doubleMessages: string[] = [];
    fristMessages: string = "";
    secondMessages: string = "";
    doubleError: boolean = false;
    notValid: string = "valid";
    isDisableCheck: boolean = false;
    isDisableCheckAlien: boolean = false;
    AutobusLabel = AutobusLabel;
    funzionario: UserInfo;
    RUOLO_AMP: string = "AMP"
    isUtenteAzienda: boolean;
    loadComplete: boolean = false;
    tipologiaDimensione: TipologiaDimensioneVO;
    loadedDepositi: boolean;
    context: string;
    listOfPortabiciAutobus: PortabiciAutobusVO[] = [];
    listOfSistemaLocalizzazione: SistemaLocalizzazioneVO[] = [];
    listOfSistemaVideosorveglianza: SistemaVideosorveglianzaVO[] = [];
    listOfDocVariazAutobusVO: DocVariazAutobusVO[] = [];
    listOfDocToShow: any[] = []; // lista dei documenti che vengono mostrati nella dorp down, vengono rimossi e aggiunti i doc in base aquelli inseriti
    listOfCategorieVeicolo: any[] = [];


    isLogoUploaded: boolean = false;


    flagSelezionato: string;
    tipoAlienato: any[] = []

    dataDisabled: boolean = true

    filtroDataPrimaImmatricolazione = (d: Date | null): boolean => {
        let isMinoreUgualeDiUltimaImm: boolean;
        let isMinoreDiUltimaRev: boolean;
        let isMinoreDiAlienazione: boolean;
        if (this.autobus != null) {
            if (this.autobus.dataUltimaImmatricolazione) {
                isMinoreUgualeDiUltimaImm = true;
            }
            if (this.autobus.dataUltimaRevisione) {
                isMinoreDiUltimaRev = true;
            }
            if (this.autobus.dataAlienazione) {
                isMinoreDiAlienazione = true;
            }
            if (isMinoreUgualeDiUltimaImm && isMinoreDiUltimaRev && isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.autobus.dataUltimaImmatricolazione && d < this.autobus.dataUltimaRevisione && d < this.autobus.dataAlienazione;
            } else if (isMinoreUgualeDiUltimaImm && isMinoreDiUltimaRev) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.autobus.dataUltimaImmatricolazione && d < this.autobus.dataUltimaRevisione;
            } else if (isMinoreUgualeDiUltimaImm && isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.autobus.dataUltimaImmatricolazione && d < this.autobus.dataAlienazione;
            } else if (isMinoreDiUltimaRev && isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d < this.autobus.dataUltimaRevisione && d < this.autobus.dataAlienazione;
            } else if (isMinoreUgualeDiUltimaImm) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d <= this.autobus.dataUltimaImmatricolazione;
            } else if (isMinoreDiUltimaRev) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d < this.autobus.dataUltimaRevisione;
            } else if (isMinoreDiAlienazione) {
                return (d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900)) && d < this.autobus.dataAlienazione;
            }
        }
        return d.getFullYear() >= 1980 || (d.getDate() === 1 && d.getMonth() === 0 && d.getFullYear() === 1900);
    }
    filtroDataUltimaImmatricolazione = (d: Date | null): boolean => {
        if (this.autobus != null) {
            if (this.autobus.dataPrimaImmatricolazione != null && this.autobus.dataAlienazione != null) {
                if (this.autobus.dataPrimaImmatricolazione.getDate() === 1 && this.autobus.dataPrimaImmatricolazione.getMonth() === 0 && this.autobus.dataPrimaImmatricolazione.getFullYear() === 1900)
                    return d.getFullYear() >= 1980 && d < this.autobus.dataAlienazione;
                else
                    return d >= this.autobus.dataPrimaImmatricolazione && d < this.autobus.dataAlienazione;
            }
            if (this.autobus.dataPrimaImmatricolazione != null) {
                if (this.autobus.dataPrimaImmatricolazione.getDate() === 1 && this.autobus.dataPrimaImmatricolazione.getMonth() === 0 && this.autobus.dataPrimaImmatricolazione.getFullYear() === 1900)
                    return d.getFullYear() >= 1980;
                else
                    return d >= this.autobus.dataPrimaImmatricolazione;
            }
            if (this.autobus.dataAlienazione != null) {
                return d.getFullYear() >= 1980 && d < this.autobus.dataAlienazione;
            }
        }
        return d.getFullYear() >= 1980;
    }
    filtroDataUltimaRevisione = (d: Date | null): boolean => {
        let isMaggioreDiPrimaImm: boolean;
        let isMinoreDiAlienazione: boolean;
        if (this.autobus != null) {
            if (this.autobus.dataPrimaImmatricolazione) {
                isMaggioreDiPrimaImm = true;
            }
            if (this.autobus.dataAlienazione) {
                isMinoreDiAlienazione = true;
            }

            if (isMaggioreDiPrimaImm && isMinoreDiAlienazione) {
                return d.getFullYear() >= 1980 && d > this.autobus.dataPrimaImmatricolazione && d < this.autobus.dataAlienazione;
            } else if (isMaggioreDiPrimaImm) {
                return d.getFullYear() >= 1980 && d > this.autobus.dataPrimaImmatricolazione;
            } else if (isMinoreDiAlienazione) {
                return d.getFullYear() >= 1980 && d < this.autobus.dataAlienazione;
            }
        }
        return d.getFullYear() >= 1980;
    }
    filtroDataAlienazione = (d: Date | null): boolean => {
        let isMaggioreDiPrimaImm: boolean;
        let isMaggioreDiUltimaImm: boolean;
        let isMaggioreDiUltimaRev: boolean;
        if (this.autobus != null) {
            if (this.autobus.dataPrimaImmatricolazione) {
                isMaggioreDiPrimaImm = true;
            }
            if (this.autobus.dataUltimaImmatricolazione) {
                isMaggioreDiUltimaImm = true;
            }
            if (this.autobus.dataUltimaRevisione) {
                isMaggioreDiUltimaRev = true;
            }
            if (isMaggioreDiPrimaImm && isMaggioreDiUltimaImm && isMaggioreDiUltimaRev) {
                return d > this.autobus.dataPrimaImmatricolazione && d > this.autobus.dataUltimaImmatricolazione && d > this.autobus.dataUltimaRevisione;
            } else if (isMaggioreDiPrimaImm && isMaggioreDiUltimaImm) {
                return d > this.autobus.dataPrimaImmatricolazione && d > this.autobus.dataUltimaImmatricolazione;
            } else if (isMaggioreDiPrimaImm && isMaggioreDiUltimaRev) {
                return d > this.autobus.dataPrimaImmatricolazione && d > this.autobus.dataUltimaRevisione;
            } else if (isMaggioreDiUltimaImm && isMaggioreDiUltimaRev) {
                return d > this.autobus.dataUltimaImmatricolazione && d > this.autobus.dataUltimaRevisione;
            } else if (isMaggioreDiPrimaImm) {
                return d.getFullYear() >= 1980 && d > this.autobus.dataPrimaImmatricolazione;
            } else if (isMaggioreDiUltimaImm) {
                return d > this.autobus.dataUltimaImmatricolazione;
            } else if (isMaggioreDiUltimaRev) {
                return d > this.autobus.dataUltimaRevisione;
            }
        }
        return d.getFullYear() >= 1980;
    }

    @ViewChild('fileInput') fileInput: any;
    @ViewChild('doubleErrorP') doubleErrorP: any;
    @ViewChild('flagContribuito') flagContribuito: any;
    @ViewChild('flgRichiestaContr') flgRichiestaContr: any;
    @ViewChild('contributoPubblicoAcquisto') contributoPubblicoAcquisto: any;
    subscriberUtenteAzEnte: Subscription;
    dettaglioUtenteAzEnte: UtenteAzEnteVO;
    entitaContributoPubblicoANDPrezzoTotaleAcquisto: EntitaContributoPubblicoANDPrezzoTotaleAcquisto = new EntitaContributoPubblicoANDPrezzoTotaleAcquisto();


    constructor(
        private utilityService: UtilityService,
        private router: Router,
        public snackBar: MatSnackBar,
        private userService: UserService,
        private navbarFilterContext: NavbarFilterContext,
        private autobusService: AutobusService,
        private documentService: DocumentService
    ) { }



    ngOnInit() {
        if (this.navbarFilterContext == null || this.navbarFilterContext.ElencoContesti == null) {
            var intervalContesti = setInterval(() => {
                this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
                if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                    this.context = this.navbarFilterContext.InfoFiltro.cod;
                }
                clearInterval(intervalContesti);
            }, 200);
        } else {
            this.navbarFilterContext.InfoFiltro = this.navbarFilterContext.ElencoContesti.find(c => c.id === 1);
            if (this.navbarFilterContext != null && this.navbarFilterContext.InfoFiltro != null) {
                this.context = this.navbarFilterContext.InfoFiltro.cod;
            }
        }

        this.reset();
        this.loadChoices();
    }

    ngOnDestroy(): void {
        this.reset();
    }


    private loadChoices() {
        this.comboChoices = [];
        this.listOfCategorieVeicolo = [];
        this.aziende = [];
        this.documenti = [];
        this.utilityService.elencoDecodifiche();
        this.utilityService.elencoAziende();
        this.documentService.elencoDocumento(1);


        this.subscribers = this.utilityService.comboVO$.subscribe(
            (data) => {
                this.comboChoices = data
                this.listOfCategorieVeicolo = data.filter(a => a.identificativo == 'CATEGORIA_VEICOLO')[0].valori;
                // SETTO DI DEFAULT IL VALORE AUTOBUS
                this.autobus.idCategoriaVeicolo = 2;
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        );



        this.subscribers = this.utilityService.elencoAziende$.subscribe((data) => {
            this.aziende = data;
            this.userService.funzionarioVo$.subscribe(data2 => {
                this.funzionario = data2;
                this.isUtenteAzienda = this.funzionario.idAzienda != null && this.funzionario.idAzienda > 0;
                if (this.isUtenteAzienda) {
                    this.azienda = this.aziende.find(a => a.id == this.funzionario.idAzienda);
                    this.loadDepositi(this.funzionario.idAzienda);
                }
                this.loadComplete = true;
            });
        }, (err) => {
            let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
            console.error(errorRest.message);
        }
        )

        this.subscribers = this.documentService.elencoDocumento$.subscribe(
            (data) => {
                this.documenti = JSON.parse(JSON.stringify(data));
                this.listOfDocToShow = JSON.parse(JSON.stringify(this.documenti));
                this.listOfDocToShow.forEach(element => {
                    if (element.idTipoDocumento == 1) {
                        if (!config.isNullOrVoid(this.autobus.flgContribuzione) && this.autobus.flgContribuzione) {
                            element.descrizione = element.descrizione + " **"
                        }
                    }
                });
            },
            (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            }
        )

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

    loadDepositi(idAzienda: number) {
        this.loadedDepositi = false;
        this.autobus.idDeposito = null;
        this.isDepSelectedPrevalente = false;
        this.indirizzoDepSelected = null;
        this.telefonoDepSelected = null;
        this.depositoGroup = new Array<DepositiGroup>();
        this.subscribers = this.autobusService.getDepositi(idAzienda).subscribe(
            (data) => {
                if (data) {
                    let depositiTmp: DepositoVO[] = new Array<DepositoVO>();
                    this.depositi = new Array<DepositoVO>();
                    for (let d of data) {
                        depositiTmp.push(d);
                        this.depositi.push(d);
                    }
                    if (this.depositi.length > 0) {
                        let dep = depositiTmp.splice(depositiTmp.findIndex(d => d.isPrevalente), 1); //metto il deposito prevalente al primo posto
                        if (depositiTmp.length > 0) {
                            this.depositoGroup.push(
                                {
                                    name: 'Altri depositi',
                                    deposito: depositiTmp
                                }
                            )
                        }
                        if (dep.length > 0) {
                            this.depositoGroup.unshift({
                                name: 'Deposito principale',
                                deposito: [dep[0]]
                            })
                            this.autobus.idDeposito = dep[0].id;
                            this.isDepSelectedPrevalente = dep[0].isPrevalente;
                            this.indirizzoDepSelected = dep[0].indirizzo;
                            this.telefonoDepSelected = dep[0].telefono;
                        }
                    }
                }
            }, (err) => {
                let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                console.error(errorRest.message);
            });
        this.loadedDepositi = true;
    }

    changeDeposito(event: any) {
        let dep = this.depositi.find(d => d.id === event.value);
        this.indirizzoDepSelected = dep.indirizzo;
        this.telefonoDepSelected = dep.telefono;
        this.isDepSelectedPrevalente = dep.isPrevalente;
    }

    daImmatricolareOnChange() {
        if (this.autobus.daImmatricolare) {
            this.autobus.idTipoImmatricolazione = 0;
            this.form.form.get('tipoImmatricolazione').disable();
            this.autobus.dataPrimaImmatricolazione = new Date("1/1/1900");
            this.form.form.get('dataPrimaImmatricolazione').disable();
            this.autobus.targa = null;
            this.form.form.get('targa').disable();
            this.autobus.enteAutorizzPrimaImm = "ND";
            this.form.form.get('enteAutorizzPrimaImm').disable();
            this.autobus.dataUltimaImmatricolazione = null;
            this.form.form.get('dataUltimaImmatricolazione').disable();
            this.autobus.dataUltimaRevisione = null;
            this.form.form.get('dataUltimaRevisione').disable();
            this.flagSelezionato = null;
            this.autobus.flgAlienato = null;
            if (this.autobus.primoTelaio != null) {
                this.autobus.telaio = this.autobus.primoTelaio;
            }
            this.form.form.get('telaio').disable();
            this.form.form.get('flagVerificaAzienda').disable();
            this.form.form.get('contributoPubblicoAcquisto').disable();
            this.form.form.get('contributoPubblicoAcquisto').setValue("");
            this.isDisableCheck = false;
            this.autobus.flgRichiestaContr = false;
            this.autobus.dataScadVincoliNoAlien = null;
            this.form.form.get('flgVeicoloAssicurato').disable();
            this.autobus.flgVeicoloAssicurato = false;
            this.form.form.get('flgAlienato').disable();
            this.autobus.flagAlienato = false;
            this.form.form.get('dataAlienazione').disable();
            this.autobus.dataAlienazione = null;
            this.autobus.idDocumento = 5;
        } else {
            this.form.form.get('tipoImmatricolazione').enable();
            this.form.form.get('dataPrimaImmatricolazione').enable();
            this.form.form.get('targa').enable();
            this.form.form.get('enteAutorizzPrimaImm').enable();
            this.form.form.get('dataUltimaImmatricolazione').enable();
            this.form.form.get('dataUltimaRevisione').enable();
            this.form.form.get('telaio').enable();
            this.form.form.get('flagVerificaAzienda').enable();
            this.form.form.get('contributoPubblicoAcquisto').enable();
            this.form.form.get('flgVeicoloAssicurato').enable();
            this.form.form.get('flgAlienato').enable();
            this.form.form.get('dataAlienazione').disable();
            this.autobus.idDocumento = 1;
            this.flagSelezionato = 'N';
            this.autobus.flgAlienato = 'N';
            this.autobus.dataAlienazione = null;
        }
    }

    primoTelaioChanged() {
        if (this.autobus.daImmatricolare) {
            this.autobus.telaio = this.autobus.primoTelaio;
        }
    }

    public reset() {
        this.autobus = new InserisciAutobusVO();
        this.setAutobusDefaultValue(this.autobus);

        window.scrollTo(0, 0);
    }

    public async save() {
        //resetto per non ricaricare l'errore che essendo salvato in array non si resetta
        this.doubleError = false;
        if (!this.isValidVerificaAzienda()) {
            window.scrollTo(0, 0);
            return;
        }
        if (!this.isValidCampi()) {
            window.scrollTo(0, 0);
            return;
        }

        if (this.isUtenteAzienda) {
            this.autobus.idAzienda = this.azienda.id;
        }
        this.listOfDocVariazAutobusVO.forEach(async element => {
            let doc: DocVariazAutobusVO;
            doc = Object.assign({}, element);
            doc.documento = await this.getByteArrayForBE(element.documento) || null;
            this.autobus.documentiAutobus.push(doc);
        });

        this.autobus.lunghezza = this.autobus.lunghezza != null ? new String(this.autobus.lunghezza).replace(",", ".") : null;
        this.autobus.numeroPorte = this.autobus.numeroPorte;
        this.autobus.nPostiSedere = this.autobus.nPostiSedere != null ? new String(this.autobus.nPostiSedere).replace(",", ".") : null;
        this.autobus.nPostiInPiedi = this.autobus.nPostiInPiedi != null ? new String(this.autobus.nPostiInPiedi).replace(",", ".") : null;
        this.autobus.nPostiRiservati = this.autobus.nPostiRiservati != null ? new String(this.autobus.nPostiRiservati).replace(",", ".") : null;
        this.autobus.postiCarrozzina = this.autobus.postiCarrozzina != null ? new String(this.autobus.postiCarrozzina).replace(",", ".") : null;
        this.autobus.annoSostProg = this.autobus.annoSostProg != null ? new String(this.autobus.annoSostProg).replace(",", ".") : null;
        this.autobus.prezzoTotAcquisto = this.autobus.prezzoTotAcquisto != null ? new String(this.autobus.prezzoTotAcquisto).replace(",", ".") : null;
        this.autobus.contributoPubblicoAcquisto = this.autobus.contributoPubblicoAcquisto != null ? new String(this.autobus.contributoPubblicoAcquisto).replace(",", ".") : null;
        this.autobus.targa = this.autobus.targa != null ? new String(this.autobus.targa).replace(/\s+/g, '') : null;

        setTimeout(() => {
            this.insertAutobus();
        }, 100);


    }

    insertAutobus() {
        this.autobusService.inserisciBus(this.autobus).subscribe(
            (result) => {
                let id: number = result;
                this.snackBar.open("Autobus inserito con successo", "Chiudi", {
                    duration: 2000,
                });

                setTimeout(() => { this.router.navigate(['/modificaBus/' + id, { action: 'inserisci' }]) }, 2000);
            },
            (error) => {
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
            }
        );
    }



    public getChoices(identificativo: string) {
        for (let elem of this.comboChoices) {
            if (identificativo === elem.identificativo)
                return elem.valori;
        }

        return [];
    }

    private setAutobusDefaultValue(autobus: InserisciAutobusVO) {
        autobus.idTipoImmatricolazione = 0;
        autobus.idTipoAllestimento = 0;
        autobus.idCategoriaVeicolo = 0;
        autobus.idTipoAlimentazione = 0;
        autobus.idClasseAmbientale = 0;
        autobus.idClasseVeicolo = 0;
        autobus.idDotazioneDisabili = 0;
        autobus.idAltriDispositiviPrevInc = new Array<number>();
        autobus.idAltriDispositiviPrevInc.push(0);
        autobus.idImpiantiAudio = 0;
        autobus.idImpiantiVisivi = 0;
        autobus.idProprietaLeasing = 0;
        autobus.flgCabinaGuidaIsolata = false;
        autobus.flgFiltroFap = false;
        autobus.flgVeicoloAssicurato = false;
        autobus.flgOtx = false;
        autobus.flgDuePiani = false;
        autobus.flgSnodato = false;
        autobus.flgRichiestaContr = false;
        this.isDisableCheck = false;
        autobus.flagAlienato = false;
        autobus.flagVerificaAmp = null;
        autobus.flagVerificaAzienda = false;
        autobus.flgRilevatoreBip = false;
        autobus.marca = "ND";
        autobus.modello = "ND";
        autobus.enteAutorizzPrimaImm = "ND";
        autobus.dataPrimaImmatricolazione = new Date(1900, 0, 1);
        autobus.lunghezza = "999";
        autobus.nPostiInPiedi = "999";
        autobus.nPostiSedere = "999";
        autobus.postiCarrozzina = "999";
        autobus.flgAlienato = 'N';
        autobus.idDocumento = 1;
        autobus.noteDocumento = "";
        this.fileInput.nativeElement.value = "";
        if (this.depositi) {
            let dep = this.depositi.find(d => d.isPrevalente);
            if (dep) {
                this.autobus.idDeposito = dep.id;
                this.isDepSelectedPrevalente = dep.isPrevalente;
                this.indirizzoDepSelected = dep.indirizzo;
                this.telefonoDepSelected = dep.telefono;
            } else {
                this.isDepSelectedPrevalente = false;
                this.indirizzoDepSelected = null;
                this.telefonoDepSelected = null;
            }
        }


        this.flagSelezionato = this.autobus.flgAlienato;
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

        if (this.autobus.flgAlienato === 'S')
            this.dataDisabled = false;
    }

    private isValidCampi() {
        let fields = [];

        // contributoPubblicoAcquisto invalid 
        var prezzoInt = Number(this.autobus.contributoPubblicoAcquisto);
        if (isNaN(prezzoInt)) {
            fields.push(AutobusLabel["entitaContrPub"]);
        }

        // prezzoTotAcquisto invalid 
        prezzoInt = Number(this.autobus.prezzoTotAcquisto);
        if (isNaN(prezzoInt)) {
            fields.push(AutobusLabel["prezzoTotAcquisto"]);
        }

        this.feedback = `
            Alcuni campi il non hanno il formato corretto.
            Correggere i seguenti campi: ${fields.join(", ")}
        `;

        return fields.length === 0;
    }

    /**
    * Il sistema verifica che determinati campi obbligatori non presentino dati temporanei di default, 
    * introdotti massivamente con l’upload di primo impianto, o anche manualmente dall’utente:
    * - vedi analisi per dettaglio campi
    * 
    * todo: move to service
    **/

    /**
     * Tipo immatricolazione, Data prima immatricolazione, Ente che ha Autorizzato, Marca, Modello, Allestimento, Alimentazione, 
     * Omologazione classe, Lunghezza, Due Piani, Snodato, Posti a Sedere, Posti in Piedi, Posti Carrozzina, Dotazione Disabili, 
     * Impianti Audio, Impianti Visivi, Impianto Condizionamento, Conta Passeggeri, OTX, AVM, , Proprietà/Leasing
     */
    private isValidVerificaAzienda() {
        let fields = [];
        if (this.autobus.flagVerificaAzienda) {
            // Tipo Immatricolazione <> ‘ND’
            if (this.autobus.idTipoImmatricolazione === null || this.autobus.idTipoImmatricolazione === 0) {
                fields.push(AutobusLabel["idTipoImmatricolazione"]);
            }
            // Data Prima Immatricolazione <> ‘01/01/1900’
            let defaultDate = new Date(1900, 0, 1);
            if (this.autobus.dataPrimaImmatricolazione === null || (this.autobus.dataPrimaImmatricolazione.getTime() === defaultDate.getTime())) {
                fields.push(AutobusLabel["dataPrimaImmatricolazione"]);
            }
            // Ente che ha autorizzato…. <> ‘ND’
            if (this.autobus.enteAutorizzPrimaImm === null || this.autobus.enteAutorizzPrimaImm.toUpperCase() === "ND") {
                fields.push(AutobusLabel["enteAutorizzPrimaImm"]);
            }
            // Marca <> ‘ND’
            if (this.autobus.marca === null || this.autobus.marca.toUpperCase() === "ND") {
                fields.push(AutobusLabel["marca"]);
            }
            // Modello <> ‘ND’
            if (this.autobus.modello === null || this.autobus.modello.toUpperCase() === "ND") {
                fields.push(AutobusLabel["modello"]);
            }
            // Allestimento <> ‘ND’
            if (this.autobus.idTipoAllestimento === null || this.autobus.idTipoAllestimento === 0) {
                fields.push(AutobusLabel["idTipoAllestimento"]);
            }
            // Alimentazione <> ‘ND’
            if (this.autobus.idTipoAlimentazione === null || this.autobus.idTipoAlimentazione === 0) {
                fields.push(AutobusLabel["idTipoAlimentazione"]);
            }
            //classe ambientale <> 'ND'
            if (this.autobus.idClasseAmbientale === null || this.autobus.idClasseAmbientale === 0) {
                fields.push(AutobusLabel["idClasseAmbientale"]);
            }
            //classe ambientale <> 'ND'
            if (this.autobus.idCategoriaVeicolo === null || this.autobus.idCategoriaVeicolo === 0) {
                fields.push(AutobusLabel["idCategoriaVeicolo"]);
            }
            // Classe Veicolo <> ‘ND’
            if (this.autobus.idClasseVeicolo === null || this.autobus.idClasseVeicolo === 0) {
                fields.push(AutobusLabel["idClasseVeicolo"]);
            }
            // Lunghezza <> 999
            if (this.autobus.lunghezza === null || this.autobus.lunghezza === "999" || this.checkLunghezza(Number(this.autobus.lunghezza))) {
                fields.push(AutobusLabel["lunghezza"]);
            }
            // Due Piani <> ‘ND’
            if (this.autobus.flgDuePiani === null) {
                fields.push(AutobusLabel["flgDuePiani"]);
            }
            // Snodato <> ‘ND’
            if (this.autobus.flgSnodato === null) {
                fields.push(AutobusLabel["flgSnodato"]);
            }
            // Posti a sedere <> 999
            if (this.autobus.nPostiSedere === null || this.autobus.nPostiSedere === "999") {
                fields.push(AutobusLabel["nPostiSedere"]);
            }
            // Posti in piedi <> 999
            if (this.autobus.nPostiInPiedi === null || this.autobus.nPostiInPiedi === "999") {
                fields.push(AutobusLabel["nPostiInPiedi"]);
            }
            // Posti carrozzina <> 999
            if (this.autobus.postiCarrozzina === null || this.autobus.postiCarrozzina === "999") {
                fields.push(AutobusLabel["postiCarrozzina"]);
            }
            // Dotazione Disabili <> ‘ND’
            if (this.autobus.idDotazioneDisabili === null || this.autobus.idDotazioneDisabili === 0) {
                fields.push(AutobusLabel["idDotazioneDisabili"]);
            }
            // Impianti Audio <> ‘ND’
            if (this.autobus.idImpiantiAudio === null || this.autobus.idImpiantiAudio === 0) {
                fields.push(AutobusLabel["idImpiantiAudio"]);
            }
            // Impianti Visivi <> ‘ND’
            if (this.autobus.idImpiantiVisivi === null || this.autobus.idImpiantiVisivi === 0) {
                fields.push(AutobusLabel["idImpiantiVisivi"]);
            }
            // Impianto Condizionamento <> ‘ND’
            if (this.autobus.flgImpiantoCondizionamento === null) {
                fields.push(AutobusLabel["flgImpiantoCondizionamento"]);
            }
            // Contapasseggeri <> ‘ND’
            if (this.autobus.flgContapasseggeri === null) {
                fields.push(AutobusLabel["flgContapasseggeri"]);
            }
            // AVM <> ‘ND’
            if (this.autobus.flgAvm === null) {
                fields.push(AutobusLabel["flgAvm"]);
            }
            // Deposito <> null
            if (!this.autobus.idDeposito) {
                fields.push(AutobusLabel["deposito"]);
            }
            // Proprietà/Leasing <> ‘ND’
            if (this.autobus.idProprietaLeasing === null || this.autobus.idProprietaLeasing === 0) {
                fields.push(AutobusLabel["idProprietaLeasing"]);
            }
        } else {
            if (this.autobus.daImmatricolare) {
                if (this.autobus.idClasseAmbientale === null || this.autobus.idClasseAmbientale === 0) {
                    fields.push(AutobusLabel["idClasseAmbientale"]);
                }
            }
        }
        if (fields.length > 0) {
            this.feedback = `
                Alcuni campi non risultano essere stati valorizzati correttamente.
                Compilare anche ${fields.join(", ")}
            `;
        }

        return fields.length === 0;
    }

    public setMock() {
        this.autobus = this.getMockInserimento();
    }

    private getMockInserimento() {
        let autobus = new InserisciAutobusVO();
        // Dati identificativi e d'immatricolazione
        autobus.idAzienda = 12;
        autobus.telaio = "22giovedi03/1";
        autobus.idTipoImmatricolazione = 0;
        autobus.primoTelaio = "22giovedi03/1";
        autobus.dataPrimaImmatricolazione = new Date(2000, 0, 1);
        autobus.targa = "22giovedi03/1";
        autobus.enteAutorizzPrimaImm = "GTT";
        autobus.matricola = "IJ00";
        autobus.dataUltimaImmatricolazione = new Date(10, 3, 18);
        autobus.contributoPubblicoAcquisto = "10000";

        //Caratteristica Fisica e Tecnologiche
        autobus.marca = "IVECO";
        autobus.idTipoAllestimento = 1;
        autobus.modello = "VAN";
        autobus.idTipoAlimentazione = 0;
        autobus.omologazioneDirettivaEuropea = "Omologazione Direttiva EU";
        //omologazione classe
        autobus.caratteristicheParticolari = "Graffi";
        autobus.idClasseAmbientale = 0;
        autobus.idClasseVeicolo = 0;
        autobus.lunghezza = "999";
        autobus.numeroPorte = 5;
        autobus.nPostiInPiedi = "999";
        autobus.nPostiSedere = "999";
        autobus.nPostiRiservati = "999";
        autobus.postiCarrozzina = "999";

        //Dotazioni Specifiche
        autobus.idDotazioneDisabili = 0;
        autobus.idAltriDispositiviPrevInc;
        autobus.idImpiantiAudio = 0;
        autobus.idImpiantiVisivi = 0;
        autobus.altriDispositiviPrevenzInc = "estintore";
        //Dati Amministrativi ed Economici
        autobus.prezzoTotAcquisto = "10";
        autobus.idProprietaLeasing = 0;
        autobus.dataUltimaRevisione = new Date(1, 3, 18);
        autobus.tipologiaDimensionale = "100";
        autobus.flgVeicoloAssicurato;
        autobus.annoSostProg = "2019";

        // Verifica e Note
        autobus.flagVerificaAmp;
        autobus.notaRiservataAzienda = "nota azienda";
        autobus.flagVerificaAmp;
        autobus.notaRiservataAmp = "note amp";
        autobus.note = "note";
        this.setAutobusDefaultValue(autobus);
        return autobus;
    }

    @ViewChild('autobusForm') form: NgForm;
    refreshDirective() {
        this.form.form.get('dataPrimaImmatricolazione').markAsUntouched();
        this.form.form.get('dataUltimaImmatricolazione').markAsUntouched();
        this.form.form.get('dataUltimaRevisione').markAsUntouched();
        this.form.form.get('dataAlienazione').markAsUntouched();
        this.form.form.get('contributoPubblicoAcquisto').markAsTouched();
        this.form.form.get('prezzoTotAcquisto').markAsTouched();
    }



    changeAllestimento(choice) {
        if (choice.codice == 1) {
            this.autobus.idTipoAllestimento = 10;
        }
    }

    changeCategoriaVeicolo(choice) {
        if (choice.codice != 10) {
            this.autobus.idCategoriaVeicolo = 2;
        }
    }

    public popolaScadenzaVincoli() {
        this.refreshDirective();
        if (this.autobus.dataPrimaImmatricolazione != null && (this.autobus.contributoPubblicoAcquisto != null && this.autobus.contributoPubblicoAcquisto != "") &&
            this.autobus.idTipoAllestimento != 0) {

            let year = this.autobus.dataPrimaImmatricolazione.getFullYear();
            let day = this.autobus.dataPrimaImmatricolazione.getDate();
            let month = this.autobus.dataPrimaImmatricolazione.getMonth();

            if (this.autobus.idTipoAllestimento == 1) {
                year = year + 8;
            } else if (this.autobus.idTipoAllestimento == 3) {
                year = year + 10;
            }
            let newDate = new Date(year, month, day);

            this.autobus.dataScadVincoliNoAlien = newDate;

            let n: Number = new Number(this.autobus.contributoPubblicoAcquisto);
            if (this.autobus.idTipoAllestimento == 2 || n == 0) {
                this.autobus.dataScadVincoliNoAlien = null;
            }
        } else if ((this.autobus.dataPrimaImmatricolazione == null
            && (this.autobus.contributoPubblicoAcquisto == null || this.autobus.contributoPubblicoAcquisto == "")
            && this.autobus.idTipoAllestimento == 0)
            ||
            (this.autobus.dataPrimaImmatricolazione == null
                || (this.autobus.contributoPubblicoAcquisto == null || this.autobus.contributoPubblicoAcquisto == "")
                || this.autobus.idTipoAllestimento == 0)
            || (this.autobus.dataPrimaImmatricolazione != null
                && (this.autobus.contributoPubblicoAcquisto == null || this.autobus.contributoPubblicoAcquisto == "")
                || this.autobus.idTipoAllestimento == 0)) {
            this.autobus.dataScadVincoliNoAlien = null;
        }
    }


    public popolaTipologiaDimensionale(lunghezza: number, idtipoAllestimento: number) {
        if (idtipoAllestimento != null && lunghezza != null) {
            this.autobus.tipologiaDimensionale = "Calcolo tipologia in corso...";
            this.utilityService.progressivoTipoDimensione(lunghezza, idtipoAllestimento);
            this.subscribers = this.utilityService.tipologiaDimesionale$.subscribe(
                (data) => this.autobus.tipologiaDimensionale = data.toString(),
                (err) => {
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            )
        }

        if (idtipoAllestimento != null) {
            this.autobusService.getTipolgiaDimensione(idtipoAllestimento).subscribe(data => {
                if (data) {
                    this.tipologiaDimensione = data;
                }
            },
                (err) => {
                    console.error(err);
                });
        }
    }
    public getTipologiaDimensionale(idtipoAllestimento: number) {
        if (idtipoAllestimento != null && idtipoAllestimento != 0) {
            this.autobusService.getTipolgiaDimensione(idtipoAllestimento).subscribe(data => {
                if (data) {
                    this.tipologiaDimensione = data;
                    if (this.autobus.lunghezza != null) {
                        let lunghezza = Number(this.autobus.lunghezza);
                        if ((lunghezza <= this.tipologiaDimensione.lunghezzaMax && lunghezza >= this.tipologiaDimensione.lunghezzaMin) || lunghezza == 999) {
                            this.formGroup.form.get('lunghezza').setErrors(null);
                        } else {
                            this.formGroup.form.get('lunghezza').setErrors({
                                tooLong: true
                            });
                        }
                    }
                }
            },
                (err) => {
                    console.error(err);
                });
        }
        else {
            this.autobus.lunghezza = "999";
            this.autobus.tipologiaDimensionale = null;
        }
    }

    troncaTreDigitali(numero: string) {
        this.utilityService.troncaTreDigitali(numero, this.autobus);
    }

    troncaDueDigitali(numero: string, numToTrunc: string) {
        this.utilityService.troncaDueDigitali(numero, numToTrunc, this.autobus);
    }

    troncaDecimali(numero: string, numToTrunc: string) {
        this.utilityService.troncaDecimali(numero, numToTrunc, this.autobus);
    }

    markAsTouched() {
        this.form.form.get('contributoPubblicoAcquisto').updateValueAndValidity();
        this.form.form.get('prezzoTotAcquisto').updateValueAndValidity();
    }



    entitaContributoPublicoValue() {
        if (this.autobus.contributoPubblicoAcquisto != null && this.autobus.contributoPubblicoAcquisto != "") {
            let contributo = this.autobus.contributoPubblicoAcquisto != null ? new String(this.autobus.contributoPubblicoAcquisto).replace(",", ".") : null;
            let n: Number = new Number(contributo);
            if (n > 0) {
                this.isDisableCheck = true;
                this.autobus.flgRichiestaContr = true;
                this.form.form.get('flgRichiestaContr').disable();
            }
            else
                this.isDisableCheck = false;
        }
        else {
            this.isDisableCheck = false;
            this.form.form.get('flgRichiestaContr').enable();
        }
    }

    changeEntitaContributoPubblico() {
        this.popolaScadenzaVincoli();
        this.troncaDueDigitali(this.autobus.contributoPubblicoAcquisto, 'entita');
        this.entitaContributoPublicoValue();
    }

    dataAlienazioneValue() {
        if (this.autobus.dataAlienazione != null) {
            this.autobus.flagAlienato = true;
            this.isDisableCheckAlien = true;
        }
        if (this.autobus.dataAlienazione == null) {
            this.autobus.flagAlienato = false;
            this.isDisableCheckAlien = false;
        }
    }

    changeFlagAlienato() {
        if (!this.autobus.flagAlienato) {
            this.autobus.dataAlienazione = null;
        }




    }

    changeFlagAlienato2(field: string) {

        this.autobus.flgAlienato = field;

        if (this.autobus.flgAlienato != 'S') {
            this.autobus.dataAlienazione = null;
            this.dataDisabled = true;
        }

        else this.dataDisabled = false;
    }



    filterDispositiviPrevInc(choice) {
        if (choice.codice == 0) {
            this.autobus.idAltriDispositiviPrevInc = new Array();
            this.autobus.idAltriDispositiviPrevInc.push(0);
        }
        else {
            this.autobus.idAltriDispositiviPrevInc = this.autobus.idAltriDispositiviPrevInc.filter(val => {
                return val != 0;
            })
        }
    }
    //forzo la stessa classe ambientale e alimentazione per elettrico e metano
    changeClasseAmbientale(choice) {
        if (choice.codice == 6) {
            this.autobus.idClasseAmbientale = 9;
        }
        else if (choice.codice == 4) {
            this.autobus.idClasseAmbientale = 10;
        } else {
            if ((this.autobus.idClasseAmbientale != 0) && (this.autobus.idClasseAmbientale == 9 || this.autobus.idClasseAmbientale == 10))
                this.autobus.idClasseAmbientale = 0
        }
    }

    changeIdAlimentazione(choice) {
        if (choice.codice == 9) {
            this.autobus.idTipoAlimentazione = 6;
        }
        else if (choice.codice == 10) {
            this.autobus.idTipoAlimentazione = 4;
        }
        else {
            if ((this.autobus.idTipoAlimentazione != 0) && (this.autobus.idTipoAlimentazione == 6 || this.autobus.idTipoAlimentazione == 4))
                this.autobus.idTipoAlimentazione = 0
        }
    }


    @ViewChild('autobusForm') formGroup: NgForm;
    isValidCampiRequired() {
        if (this.formGroup && this.formGroup.form) {
            if (this.formGroup.form.get('altraAlimentazione') && this.formGroup.form.get('altraAlimentazione').hasError('required')) return true;
            if (this.formGroup.form.get('altriDisositiviPrevenzInc') && this.formGroup.form.get('altriDisositiviPrevenzInc').hasError('required')) return true;
            if (this.formGroup.form.get('annoSostProg') && this.formGroup.form.get('annoSostProg').hasError('required')) return true;
            if (this.formGroup.form.get('azienda') && this.formGroup.form.get('azienda').hasError('required')) return true;
            if (this.formGroup.form.get('caratteristicheParticolari') && this.formGroup.form.get('caratteristicheParticolari').hasError('required')) return true;
            if (this.formGroup.form.get('classeVeicolo') && this.formGroup.form.get('classeVeicolo').hasError('required')) return true;
            if (this.formGroup.form.get('contributo') && this.formGroup.form.get('contributo').hasError('required')) return true;
            if (this.formGroup.form.get('contributoPubblicoAcquisto') && (this.formGroup.form.get('contributoPubblicoAcquisto').hasError('prezzoTotAcquistoisString') || this.formGroup.form.get('contributoPubblicoAcquisto').hasError('contributoPubblicoAcquistoMAXPrezzoTotAcquisto'))) return true;
            if (this.formGroup.form.get('prezzoTotAcquisto') && (this.formGroup.form.get('prezzoTotAcquisto').hasError('prezzoTotAcquistoisString') || this.formGroup.form.get('prezzoTotAcquisto').hasError('prezzoTotAcquistoMINcontributoPubblicoAcquisto'))) return true;
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
            if (this.formGroup.form.get('idTipoAlimentazione') && this.formGroup.form.get('idTipoAlimentazione').hasError('required')) return true;
            if (this.formGroup.form.get('idTipoAllestimento') && this.formGroup.form.get('idTipoAllestimento').hasError('required')) return true;
            if (this.formGroup.form.get('idTipoImmatricolazione') && this.formGroup.form.get('idTipoImmatricolazione').hasError('required')) return true;
            if (this.formGroup.form.get('lunghezza') && (this.formGroup.form.get('lunghezza').hasError('required') || this.formGroup.form.get('lunghezza').hasError('tooLong'))) return true;
            if (this.formGroup.form.get('marca') && this.formGroup.form.get('marca').hasError('required')) return true;
            if (this.formGroup.form.get('matricola') && this.formGroup.form.get('matricola').hasError('required')) return true;
            if (this.formGroup.form.get('modello') && this.formGroup.form.get('modello').hasError('required')) return true;
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
            if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('required')) return true;
        }
        return false;
    }



    isCampiInvalid() {
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
        if (this.formGroup.form.get('prezzoTotAcquisto') && this.formGroup.form.get('prezzoTotAcquisto').hasError('prezzoTotAcquistoMINContributoPubblicoAcquisto')) return true;
        // contributoPubblicoAcquisto
        if (this.formGroup.form.get('contributoPubblicoAcquisto') && this.formGroup.form.get('contributoPubblicoAcquisto').hasError('contributoPubblicoAcquistoMAXPrezzoTotAcquisto')) return true;

        // dataAlienazione
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('alienazioneMinoreUgualePrimaImm')) return true;
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('alienazioneMinoreUgualeUltimaImm')) return true;
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('alienazioneMinoreUgualeUltimaRev')) return true;
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('valoreNonConsentito')) return true;
        if (this.formGroup.form.get('dataAlienazione') && this.formGroup.form.get('dataAlienazione').hasError('dataScadVincoliNoAlien')) return true;

        //lunghezza autobus
        if (this.formGroup.form.get('lunghezza') && this.form.form.get('lunghezza').hasError('tooLong')) return true;

        // annoSostProg
        if (this.formGroup.form.get('annoSostProg') && this.formGroup.form.get('annoSostProg').hasError('primaMaggioreSostProg')) return true;
    }

    changeLunghezza(lunghezza: string) {
        lunghezza = lunghezza != null ? lunghezza : '0';
        this.troncaTreDigitali(lunghezza);
        lunghezza = lunghezza.replace(",", ".");
        let regex: RegExpMatchArray = lunghezza.match("[0-9]+([,\.][0-9]{0,3})?");
        if (regex != null) {
            lunghezza = regex[0];
        }
        this.autobus.lunghezza = lunghezza;
        let l: number = lunghezza != null ? Number(lunghezza) : 0;
        this.popolaTipologiaDimensionale(l, this.autobus.idTipoAllestimento);
        if ((l <= this.tipologiaDimensione.lunghezzaMax && l >= this.tipologiaDimensione.lunghezzaMin) || l == 999) {
            this.formGroup.form.get('lunghezza').setErrors(null);
        } else {
            this.formGroup.form.get('lunghezza').setErrors({
                tooLong: true
            });
        }
    }

    checkLunghezza(l: number) {
        if (isNaN(l)) {
            return false;
        }
        if (this.tipologiaDimensione == null || (l <= this.tipologiaDimensione.lunghezzaMax && l >= this.tipologiaDimensione.lunghezzaMin) || l == 999) {
            return false

        } else {
            return true;

        }
    }

    verificaAnnoSostProg(calledFromTemplate: boolean) {
        let primaMaggioreSostProg: boolean = false;
        if (this.autobus.dataPrimaImmatricolazione && this.autobus.annoSostProg) {
            primaMaggioreSostProg = this.autobus.dataPrimaImmatricolazione.getFullYear() > Number(this.autobus.annoSostProg) ? true : false;
        }
        if (this.formGroup && this.formGroup.form) {
            if (primaMaggioreSostProg) {
                this.formGroup.form.get('annoSostProg').setErrors({
                    primaMaggioreSostProg: primaMaggioreSostProg
                });
            }
            if (!primaMaggioreSostProg && this.autobus.annoSostProg) {
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

        if (this.autobus.dataPrimaImmatricolazione && this.autobus.dataUltimaImmatricolazione)
            primaMaggioreDiUltima = this.autobus.dataPrimaImmatricolazione.getTime() > this.autobus.dataUltimaImmatricolazione.getTime() ? true : false;
        if (this.autobus.dataPrimaImmatricolazione && this.autobus.dataUltimaRevisione)
            primaMaggioreUgualeUltimaRevisione = this.autobus.dataPrimaImmatricolazione.getTime() >= this.autobus.dataUltimaRevisione.getTime() ? true : false;
        if (this.autobus.dataAlienazione && this.autobus.dataPrimaImmatricolazione)
            primaMaggioreUgualeAlienazione = this.autobus.dataPrimaImmatricolazione.getTime() >= this.autobus.dataAlienazione.getTime() ? true : false;
        if (this.autobus.dataPrimaImmatricolazione && this.autobus.annoSostProg) {
            primaMaggioreSostProg = this.autobus.dataPrimaImmatricolazione.getFullYear() > Number(this.autobus.annoSostProg) ? true : false;
        }
        if (this.autobus.dataPrimaImmatricolazione) {
            if (this.autobus.dataPrimaImmatricolazione.getFullYear() < 1980)
                valoreNonConsentito = true;
            if (this.autobus.dataPrimaImmatricolazione.getFullYear() == 1900 && this.autobus.dataPrimaImmatricolazione.getMonth() == 0 && this.autobus.dataPrimaImmatricolazione.getDay() == 1)
                valoreNonConsentito = false;
        }
        if (this.formGroup && this.formGroup.form) {
            if (primaMaggioreDiUltima || primaMaggioreUgualeUltimaRevisione || primaMaggioreUgualeAlienazione || valoreNonConsentito || primaMaggioreSostProg) {
                this.formGroup.form.get('dataPrimaImmatricolazione').setErrors({
                    primaMaggioreDiUltima: primaMaggioreDiUltima,
                    primaMaggioreUgualeUltimaRevisione: primaMaggioreUgualeUltimaRevisione,
                    primaMaggioreUgualeAlienazione: primaMaggioreUgualeAlienazione,
                    primaMaggioreSostProg: primaMaggioreSostProg,
                    valoreNonConsentito: valoreNonConsentito
                });
            }
            if (!primaMaggioreDiUltima && !primaMaggioreUgualeAlienazione && !primaMaggioreUgualeUltimaRevisione && !valoreNonConsentito && !primaMaggioreSostProg && this.autobus.dataPrimaImmatricolazione) {
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

        if (this.autobus.dataUltimaImmatricolazione && this.autobus.dataPrimaImmatricolazione)
            ultimaMinoreDiPrima = this.autobus.dataUltimaImmatricolazione.getTime() < this.autobus.dataPrimaImmatricolazione.getTime() ? true : false;
        if (this.autobus.dataUltimaImmatricolazione && this.autobus.dataAlienazione)
            ultimaMaggioreUgualeDataAlienazione = this.autobus.dataUltimaImmatricolazione.getTime() >= this.autobus.dataAlienazione.getTime() ? true : false;
        if (this.autobus.dataUltimaImmatricolazione)
            valoreNonConsentito = this.autobus.dataUltimaImmatricolazione.getFullYear() < 1980 ? true : false;
        if (this.formGroup && this.formGroup.form) {
            if (ultimaMinoreDiPrima || ultimaMaggioreUgualeDataAlienazione || valoreNonConsentito) {
                this.formGroup.form.get('dataUltimaImmatricolazione').setErrors({
                    ultimaMinoreDiPrima: ultimaMinoreDiPrima,
                    ultimaMaggioreUgualeDataAlienazione: ultimaMaggioreUgualeDataAlienazione,
                    valoreNonConsentito: valoreNonConsentito
                });
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
        this.formGroup.form.get('dataUltimaImmatricolazione').setValue(null);
    }

    verificaDataUltimaRevisione(calledFromTemplate: boolean) {

        let ultimaRevMinoreUgualePrimaImm: boolean = false;
        let ultimaRevMaggioreUgualeDataAlien: boolean = false;
        let valoreNonConsentito: boolean = false;

        if (this.autobus.dataUltimaRevisione && this.autobus.dataPrimaImmatricolazione)
            ultimaRevMinoreUgualePrimaImm = this.autobus.dataUltimaRevisione.getTime() <= this.autobus.dataPrimaImmatricolazione.getTime() ? true : false;
        if (this.autobus.dataUltimaRevisione && this.autobus.dataAlienazione)
            ultimaRevMaggioreUgualeDataAlien = this.autobus.dataUltimaRevisione.getTime() >= this.autobus.dataAlienazione.getTime() ? true : false;
        if (this.autobus.dataUltimaRevisione) {
            valoreNonConsentito = this.autobus.dataUltimaRevisione.getFullYear() < 1980 ? true : false;
        }

        if (this.formGroup && this.formGroup.form) {
            if (ultimaRevMinoreUgualePrimaImm || ultimaRevMaggioreUgualeDataAlien || valoreNonConsentito) {
                this.formGroup.form.get('dataUltimaRevisione').setErrors({
                    ultimaRevMinoreUgualePrimaImm: ultimaRevMinoreUgualePrimaImm,
                    ultimaRevMaggioreUgualeDataAlien: ultimaRevMaggioreUgualeDataAlien,
                    valoreNonConsentito: valoreNonConsentito
                });
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

        if (this.autobus.dataAlienazione && this.autobus.dataPrimaImmatricolazione)
            alienazioneMinoreUgualePrimaImm = this.autobus.dataAlienazione.getTime() <= this.autobus.dataPrimaImmatricolazione.getTime() ? true : false;
        if (this.autobus.dataAlienazione && this.autobus.dataUltimaImmatricolazione)
            alienazioneMinoreUgualeUltimaImm = this.autobus.dataAlienazione.getTime() <= this.autobus.dataUltimaImmatricolazione.getTime() ? true : false;
        if (this.autobus.dataAlienazione && this.autobus.dataUltimaRevisione)
            alienazioneMinoreUgualeUltimaRev = this.autobus.dataAlienazione.getTime() <= this.autobus.dataUltimaRevisione.getTime() ? true : false;
        if (this.autobus.dataAlienazione) {
            valoreNonConsentito = this.autobus.dataAlienazione.getFullYear() < 1980 ? true : false;
        }

        if (this.formGroup && this.formGroup.form) {
            if (alienazioneMinoreUgualePrimaImm || alienazioneMinoreUgualeUltimaImm || alienazioneMinoreUgualeUltimaRev || valoreNonConsentito) {
                this.formGroup.form.get('dataAlienazione').setErrors({
                    alienazioneMinoreUgualePrimaImm: alienazioneMinoreUgualePrimaImm,
                    alienazioneMinoreUgualeUltimaImm: alienazioneMinoreUgualeUltimaImm,
                    alienazioneMinoreUgualeUltimaRev: alienazioneMinoreUgualeUltimaRev,
                    valoreNonConsentito: valoreNonConsentito
                });
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


    @ViewChild('flgContapasseggeriIntegrato') flgContapasseggeriIntegrato: MatCheckbox;

    resetFieldsDotazioniSpecifiche() {
        if (this.autobus.flgContribuzione != true) {
            this.autobus.fkSistemaLocalizzazione = undefined;
            this.autobus.flgContapasseggeriIntegrato = null;
            this.autobus.flgBipCablato = null;
            this.autobus.flgSistemiProtezioneAutista = null;
            this.autobus.fkSistemaVideosorveglianza = undefined;
            this.autobus.fkPortabici = undefined;
            this.autobus.altriAllestimenti = undefined;
            this.listOfDocToShow = JSON.parse(JSON.stringify(this.documenti));
        } else {
            this.listOfDocToShow = JSON.parse(JSON.stringify(this.documenti));
            this.listOfDocToShow.forEach(element => {
                if (element.idTipoDocumento == 1) {
                    element.descrizione = element.descrizione + " **"
                }
            });
        }
        if (this.autobus.flgAvm == false || this.autobus.flgAvm == undefined) {
            this.autobus.fkSistemaLocalizzazione = undefined;
        }
        if (this.autobus.flgContapasseggeri == false || this.autobus.flgContapasseggeri == undefined) {
            this.autobus.flgContapasseggeriIntegrato = null;
        }
        if (this.autobus.flgRilevatoreBip == true) {
            this.autobus.flgBipCablato = null;
        }
    }

    /****************************************************************************************************************************
    *
    *                                           SEZIONE DOCUMENTI
    *  
    *****************************************************************************************************************************/

    uploadDoc(files: File[]) {
        this.autobus.file = files[0];
        this.autobus.nomeFile = files[0].name
    }

    handleFileInput() {
        if (
            !config.isNullOrVoid(this.autobus.idDocumento) &&
            !config.isNullOrVoid(this.autobus.nomeFile)
        ) {
            let allegato: DocVariazAutobusVO = new DocVariazAutobusVO();
            let doc = this.listOfDocToShow.find(a => a.idTipoDocumento == this.autobus.idDocumento);
            let indexDoc = this.listOfDocToShow.findIndex(a => a.idTipoDocumento == this.autobus.idDocumento);
            let descDoc = doc.descrizione;
            this.listOfDocToShow.splice(indexDoc, 1); // rimuovo il tipo doc che e' stato inserito
            allegato.documento = this.autobus.file;
            allegato.nomeFile = this.autobus.nomeFile
            allegato.idTipoDocumento = this.autobus.idDocumento;
            allegato.note = this.autobus.noteDocumento;
            allegato.dataCaricamento = new Date();
            allegato.descEstesa = descDoc;

            this.listOfDocVariazAutobusVO.push(allegato);

            this.autobus.idDocumento = null;
            this.autobus.noteDocumento = null;
            this.autobus.file = null;
            this.autobus.nomeFile = null;
            this.fileInput.nativeElement.value = "";
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
                if (this.autobus.flgContribuzione) {
                    if (!config.isNullOrVoid(this.autobus.flgContribuzione) && this.autobus.flgContribuzione) {
                        element.descrizione = element.descrizione + " **"
                    }
                }
            }
        });
        this.listOfDocToShow.sort((a, b) => a.idTipoDocumento < b.idTipoDocumento ? -1 : a.idTipoDocumento > b.idTipoDocumento ? 1 : 0);
    }


    downloadDoc(index: number) {
        let nameDoc = this.listOfDocVariazAutobusVO[index].nomeFile;
        let file = this.listOfDocVariazAutobusVO[index].documento;
        saveAs(file, nameDoc);
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

    checkData(data: Date, name: string) {
        if (data == null)
            this.form.form.get(name).setValue(null);
    }
}
