/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { OnInit, Component } from "@angular/core";
import { ProcedimentoService } from "../../services/procedimento.service";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { ActivatedRoute, Router } from "@angular/router";
import { TipoProcedimentoVO, TipoContrattoVO} from "../../vo/extend-vo";
import { AllegatoVeicoloVO } from "../../vo/allegato-veicolo-vo";
import { DocumentService } from "../../services/document.service";
import { ErrorRest } from "../../class/error-rest";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { saveAs } from "file-saver"
import { AllegatoProcVO } from "../../vo/allegato-proc-vo";
import { ContrattoProcDatiVO } from "../../vo/contratto-proc-vo";
import { UtilizzoVO } from "../../vo/utilizzo-vo";
import { VeicoloVO } from "../../vo/veicolo-vo";

@Component({
    selector: 'app-dettaglio-richiesta-uso-in-linea',
    templateUrl: './dettaglio-richiesta-uso-in-linea.component.html',
    styleUrls: ['./dettaglio-richiesta-uso-in-linea.component.scss']
})

export class DettaglioRichiestaUsoInLineaComponent implements OnInit {

    id: number;
    azione: string;
    dettaglioRichiesta: DettaglioRichiestaVO;
    tipoProcedimento: TipoProcedimentoVO;
    tipiContratto: Array<TipoContrattoVO>;
    allegatiVeicoli: Array<AllegatoVeicoloVO> = new Array<AllegatoVeicoloVO>();
    documentoAutorizzazione: AllegatoProcVO;
    utilizzi: Array<UtilizzoVO>;

    loadedRichiesta: boolean;
    loadedTipoProcedimento: boolean
    loadedTipiContratto: boolean;
    loadedPDF: boolean = true;;
    loadedDatiContratto: boolean;
    downloadPDF: boolean;

    constructor(private procedimentoService: ProcedimentoService,
        private route: ActivatedRoute,
        private router: Router,
        private documentService: DocumentService,
    ) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.azione = this.route.snapshot.paramMap.get('action');
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.loadData();
        }, err => {
            CommonsHandleException.handleBlockingError(err, this.router);
        });
    }

    loadData() {
        this.procedimentoService.dettaglioRichiesta(this.id, Action.VIEW).subscribe(data => {
            if (data) {
                this.dettaglioRichiesta = data;
                this.utilizzi = new Array<UtilizzoVO>();
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
                this.loadedTipiContratto = false;
                this.procedimentoService.getTipiContratto().finally(() => {
                    if (this.dettaglioRichiesta && this.tipiContratto) {
                        for (let contratto of this.dettaglioRichiesta.contratti) {
                            let utilizzo = new UtilizzoVO();
                            let tipoContratto: TipoContrattoVO = this.tipiContratto.find(a => a.id === contratto.idTipoContratto);
                            utilizzo.tipoContratto = tipoContratto;
                            utilizzo.contratto = contratto;
                            this.utilizzi.push(utilizzo);
                            this.getDatiContratto(utilizzo);
                            this.sortUtilizzi(this.utilizzi);
                            if (contratto.idTipoContratto === 1) {
                                this.tipiContratto.splice(this.tipiContratto.findIndex(a => a.id === 1), 1);
                            }

                        }
                    }
                }).subscribe(data => {
                    if (data) {
                        this.tipiContratto = data;
                    }
                    this.loadedTipiContratto = true;
                });
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

    }

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

    getDatiContratto(utilizzo: UtilizzoVO) {
        this.procedimentoService.getDatiContratto(utilizzo.contratto.idContratto, this.dettaglioRichiesta.id).subscribe(data => {
            if (data) {
                let datiContratto = new ContrattoProcDatiVO(data);
                utilizzo.datiContratto = datiContratto;
            }
        });
    }

    sortUtilizzi(utilizzi: Array<UtilizzoVO>) {
        utilizzi.sort((a, b) => {
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

    goBack() {
        if (this.azione === 'ricerca') {
            this.router.navigate(['/ricercaProcedimenti']);
        }

        if (this.azione.startsWith("procedimento")) {
            let array: String[] = this.azione.split('_');
            let id = array[1];
            this.router.navigate(['/dettaglioBus/' + id]);

        } else if (this.azione.startsWith("messaggio")) {
            let array: string[] = this.azione.split('_');
            let idMessaggio: number = +array[1];
            let idElencoMessaggi: number = +array[2];
            this.router.navigate(['/dettaglioMessaggio/' + idMessaggio, { action: idElencoMessaggi }]);
        }
    }

    isLoading() {
        if (!this.loadedRichiesta || !this.loadedTipoProcedimento || !this.loadedPDF || !this.loadedTipiContratto) return true;
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