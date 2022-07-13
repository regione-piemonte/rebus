/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { OnInit, Component } from "@angular/core";
import { ProcedimentoService } from "../../services/procedimento.service";
import { ActivatedRoute, Router } from "@angular/router";
import { DocumentService } from "../../services/document.service";
import { DettaglioRichiestaVO } from "../../vo/dettaglio-richiesta-vo";
import { CommonsHandleException } from "../../commons/commons-handle-exception";
import { MotivazioneVO, MotorizzazioneVO, TipoProcedimentoVO } from "../../vo/extend-vo";
import { AllegatoProcVO } from "../../vo/allegato-proc-vo";
import { ErrorRest } from "../../class/error-rest";
import { saveAs } from "file-saver"
import { UserService } from "../../services/user.service";
import { UserInfo } from "../../vo/funzionario-vo";
import { AuthorizationRoles } from "../../class/authorization-roles";
import { ContrattoProcDatiVO } from "../../vo/contratto-proc-vo";

@Component({
    selector: 'app-dettaglio-richiesta-sostituzione',
    templateUrl: './dettaglio-richiesta-sostituzione.component.html',
    styleUrls: ['./dettaglio-richiesta-sostituzione.component.scss']
})

export class DettaglioRichiestaSostituzioneComponent implements OnInit {

    id: number;
    azione: string;
    dettaglioRichiesta: DettaglioRichiestaVO;
    tipoProcedimento: TipoProcedimentoVO;
    idSubProcedimenti: Array<number>;
    datiContratto: ContrattoProcDatiVO;
    documentoAutorizzazione: AllegatoProcVO;
    motorizzazione: MotorizzazioneVO;
    motivazione: MotivazioneVO;

    isScaricaPDFAbilitato: boolean;
    funzionario: UserInfo;

    loadedRichiesta: boolean;
    loadedDoc: boolean = true;
    loadedTipoProcedimento: boolean;
    loadedDatiContratto: boolean;
    loadedMotorizzazione: boolean;
    loadedMotivazione: boolean;

    downloadPDF: boolean;


    constructor(private procedimentoService: ProcedimentoService,
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router,
        private documentService: DocumentService,
    ) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.azione = this.route.snapshot.paramMap.get('action');
            this.id = +params['id']; // (+) converts string 'id' to a number
            this.userService.funzionarioVo$.subscribe(data => {
                this.funzionario = data;
                this.isScaricaPDFAbilitato = this.funzionario.authority.includes(AuthorizationRoles.SCARICA_PDF);
            });
            this.loadedRichiesta = false;
            this.procedimentoService.dettaglioRichiesta(this.id, Action.VIEW).subscribe(data => {
                if (data) {
                    this.dettaglioRichiesta = data;
                    let indexDocumentoAutorizzazione = this.dettaglioRichiesta.files ? this.dettaglioRichiesta.files.findIndex(el => {
                        return el.tipoDocumento.id == 8;
                    }) : null;

                    if (indexDocumentoAutorizzazione != null && indexDocumentoAutorizzazione !== -1) {
                        this.documentoAutorizzazione = this.dettaglioRichiesta.files[indexDocumentoAutorizzazione];
                        this.dettaglioRichiesta.files.splice(indexDocumentoAutorizzazione, 1);
                    }
                    this.idSubProcedimenti = new Array<number>();
                    this.idSubProcedimenti.push(this.dettaglioRichiesta.subProcedimento.idSubProcedimento1);
                    this.idSubProcedimenti.push(this.dettaglioRichiesta.subProcedimento.idSubProcedimento2);
                    this.loadData();
                }
                this.loadedRichiesta = true;
            }, (err) => {
                CommonsHandleException.authorizationError(err, this.router);
            });
        }, err => {
            CommonsHandleException.handleBlockingError(err, this.router);
        });
    }

    loadData() {
        this.loadedTipoProcedimento = false;
        this.procedimentoService.getTipoProcedimento(this.dettaglioRichiesta.idTipoProcedimento).subscribe(data => {
            if (data) {
                this.tipoProcedimento = data;
            }
            this.loadedTipoProcedimento = true;
        });
        this.loadedMotorizzazione = false;
        this.procedimentoService.getMotorizzazioni().subscribe(data => {
            if (data) {
                this.motorizzazione = data.find(m => m.id == this.dettaglioRichiesta.idMotorizzazione);
            }
            this.loadedMotorizzazione = true;
        });
        this.loadedMotivazione = false;
        let idProcedimentoMotivazioni = this.dettaglioRichiesta.idTipoProcedimento;
        this.procedimentoService.getMotivazioni(2, false).subscribe(data => {
            if (data) {
                this.motivazione = data.find(m => m.id == this.dettaglioRichiesta.idMotivazione);
            }
            this.loadedMotivazione = true;
        });
        this.loadedDatiContratto = false;
        this.procedimentoService.getDatiContratto(this.dettaglioRichiesta.contratto.idContratto, this.id).subscribe(data => {
            if (data) {
                this.datiContratto = data;
            }
            this.loadedDatiContratto = true;
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
            this.router.navigate(['/dettaglioMessaggio/' + idMessaggio, { action: idElencoMessaggi }]);
        }
    }

    isLoading() {
        if (!this.loadedRichiesta || !this.loadedTipoProcedimento || !this.loadedDoc || !this.loadedDatiContratto || !this.loadedMotorizzazione || !this.loadedMotivazione) return true;
        return false;
    }

}

export enum Action {
    EDIT = "E",
    VIEW = "V",
}