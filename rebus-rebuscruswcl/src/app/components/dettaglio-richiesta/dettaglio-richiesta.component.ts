/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { OnInit, Component, Input } from "@angular/core";
import { ProcedimentoService } from "../../services/procedimento.service";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { ActivatedRoute, Router } from "@angular/router";
import { TipoProcedimentoVO, MotorizzazioneVO, MotivazioneVO, TipoContrattoVO, VoceDiCostoVO } from "../../vo/extend-vo";
import { AllegatoVeicoloVO } from "../../vo/allegato-veicolo-vo";
import { DocumentService } from "../../services/document.service";
import { ErrorRest } from "../../class/error-rest";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { saveAs } from "file-saver"
import { AllegatoProcVO } from "../../vo/allegato-proc-vo";
import { ContrattoProcDatiVO } from "../../vo/contratto-proc-vo";
import { VeicoloVO } from "../../vo/veicolo-vo";

@Component({
    selector: 'app-dettaglio-richiesta',
    templateUrl: './dettaglio-richiesta.component.html',
    styleUrls: ['./dettaglio-richiesta.component.scss']
})

export class DettaglioRichiestaComponent implements OnInit {

    id: number;
    azione: string;
    dettaglioRichiesta: DettaglioRichiestaVO = new DettaglioRichiestaVO();
    tipoProcedimento: TipoProcedimentoVO;
    motorizzazione: MotorizzazioneVO;
    motivazione: MotivazioneVO;
    tipoContratto: TipoContrattoVO;
    allegatiVeicoli: Array<AllegatoVeicoloVO> = new Array<AllegatoVeicoloVO>();
    vociDiCosto: Array<VoceDiCostoVO>;
    datiContratto: ContrattoProcDatiVO;
    documentoAutorizzazione: AllegatoProcVO;

    loadedRichiesta: boolean;
    loadedTipoProcedimento: boolean
    loadedMotorizzazione: boolean;
    loadedMotivazione: boolean;
    loadedTipiContratto: boolean;
    loadedVociDiCosto: boolean;
    loadedPDF: boolean = true;;
    loadedDatiContratto: boolean;
    downloadPDF: boolean;

    @Input() idProcedimento;

    constructor(private procedimentoService: ProcedimentoService,
        private route: ActivatedRoute,
        private router: Router,
        private documentService: DocumentService,
    ) { }

    ngOnInit(): void {
        if (this.idProcedimento == null) {
            this.route.params.subscribe(params => {
                this.azione = this.route.snapshot.paramMap.get('action');
                this.id = +params['id']; // (+) converts string 'id' to a number
                this.loadData();
            }, err => {
                CommonsHandleException.handleBlockingError(err, this.router);
            });
        } else {
            this.id = this.idProcedimento;
            this.loadData();
        }
    }

    loadData() {
        this.procedimentoService.dettaglioRichiesta(this.id, Action.VIEW).subscribe(data => {
            if (data) {

                this.dettaglioRichiesta = data;
                let indexDocumentoAutorizzazione = this.dettaglioRichiesta.files ? this.dettaglioRichiesta.files.findIndex(el => {
                    return el.tipoDocumento.id == 8;
                }) : null;
                if (indexDocumentoAutorizzazione != null && indexDocumentoAutorizzazione != -1) {
                    this.documentoAutorizzazione = this.dettaglioRichiesta.files[indexDocumentoAutorizzazione];
                    this.dettaglioRichiesta.files.splice(indexDocumentoAutorizzazione, 1);
                }
                for (let v of this.dettaglioRichiesta.veicoli) {
                    for (let a of v.allegati) {
                        a.primoTelaioVeicolo = v.primoTelaio;
                        a.nTargaVeicolo = v.nTarga;
                        this.allegatiVeicoli.push(a);
                    }
                }
                this.loadChoices();
            }
            this.loadedRichiesta = true;
        }, (err) => {
            CommonsHandleException.authorizationError(err, this.router);
        });
    }

    loadChoices() {
        this.loadedTipoProcedimento = false;
        this.procedimentoService.getTipoProcedimento(this.dettaglioRichiesta.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipoProcedimento = data;
            }
            this.loadedTipoProcedimento = true;
        });
        if (this.dettaglioRichiesta.idTipoProcedimento != 7) {
            this.loadedMotorizzazione = false;
            this.procedimentoService.getMotorizzazioni().subscribe(data => {
                if (data) {
                    this.motorizzazione = data.find(m => m.id == this.dettaglioRichiesta.idMotorizzazione);
                }
                this.loadedMotorizzazione = true;
            });
            this.loadedMotivazione = false;
            let idProcedimentoMotivazioni = this.dettaglioRichiesta.idTipoProcedimento;
            if (this.idProcedimento != null) {
                idProcedimentoMotivazioni = 2;
            }
            this.procedimentoService.getMotivazioni(idProcedimentoMotivazioni, false).subscribe(data => {
                if (data) {
                    this.motivazione = data.find(m => m.id == this.dettaglioRichiesta.idMotivazione);
                }
                this.loadedMotivazione = true;
            });
            if (this.idProcedimento == null) {
                this.loadedDatiContratto = false;
                this.procedimentoService.getDatiContratto(this.dettaglioRichiesta.contratto.idContratto, this.id).subscribe(data => {
                    if (data) {
                        this.datiContratto = data;
                    }
                    this.loadedDatiContratto = true;
                });
            }
            if (this.dettaglioRichiesta.idTipoProcedimento == 5) { // uso in linea
                this.loadedTipiContratto = false;
                this.procedimentoService.getTipiContratto().subscribe(data => {
                    if (data) {
                        this.tipoContratto = data.find(a => a.id == this.dettaglioRichiesta.contratto.idTipoContratto);
                    }
                    this.loadedTipiContratto = true;
                });
            }
        } else {
            this.loadedVociDiCosto = false;
            this.procedimentoService.getVociDiCosto().subscribe(data => {
                if (data) {
                    this.vociDiCosto = data;
                    for (let voce of this.dettaglioRichiesta.vociDiCostoVeicolo) {
                        voce.descrizioneVoceCosto = this.vociDiCosto.find(v => v.id == voce.idVoceCosto).descrizione;
                    }
                }
                this.loadedVociDiCosto = true;
            });
        }
    }

    //aggiunto per nuova sezione Veicoli jira-144
    downloadVeicolo(veicolo: VeicoloVO) {
        this.downloadPDF = true;
        let idVeicolo = this.dettaglioRichiesta.veicoli.find(v => v.primoTelaio == veicolo.primoTelaio).idVariazAutobus;
        let idAllegato = veicolo.allegati[0].tipoDocumento.id
        this.documentService.getContenutoDocumentoById(idVeicolo, idAllegato)
            .subscribe(
                res => {
                    this.downloadPDF = false;
                    saveAs(res, veicolo.allegati[0].nomeFile);
                },
                err => {
                    this.downloadPDF = false;
                    let errorRest: ErrorRest = CommonsHandleException.handleNotBlockingError(err);
                    console.error(errorRest.message);
                }
            );
    }



    dettagliobus(value: number) {
        //RECUPERA BANDO SEL E SETTORE SEL
        this.router.navigate(['/dettaglioBus', value],
            {
                queryParams: {
                },
            });
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

    goBack() {
        if (this.azione === 'ricerca') {
            this.router.navigate(['/ricercaProcedimenti']);
        }
        if (this.azione.startsWith("procedimento")) {
            let array: String[] = this.azione.split('_');
            let id = array[1];
            this.router.navigate(['/dettaglioBus/' + id]);

        }
        else if (this.azione.startsWith("messaggio")) {

            let array: string[] = this.azione.split('_');
            let idMessaggio: number = +array[1];
            let idElencoMessaggi: number = +array[2];
            let idContesto: number = +array[3];
            this.router.navigate(['/dettaglioMessaggio/' + idMessaggio, { action: idElencoMessaggi, idContesto: idContesto }]);

        }
    }

    isLoading() {
        if (!this.loadedRichiesta || !this.loadedTipoProcedimento) return true;
        if (this.dettaglioRichiesta.idTipoProcedimento != 7) {
            if (!this.loadedMotorizzazione || !this.loadedMotivazione || !this.loadedPDF) return true;
            if (this.dettaglioRichiesta.idTipoProcedimento == 5 && !this.loadedTipiContratto) return true;
            if (!this.idProcedimento && !this.loadedDatiContratto) return true;
        }
        return false;
    }

    ngAfterViewInit() {
        window.scrollTo(0, 0);
    }

}


export enum Action {
    EDIT = "E",
    VIEW = "V",
}