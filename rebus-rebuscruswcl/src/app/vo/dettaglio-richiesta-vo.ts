/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { VeicoloVO } from "./veicolo-vo";
import { ContrattoProcVO } from "./contratto-proc-vo";
import { AllegatoProcVO } from "./allegato-proc-vo";
import { IterProcedimentoVO } from "./iter-procedimento-vo";
import { SubProcedimentoVO } from "./sub-procedimento-vo";
import { VoceDiCostoVeicoloVO } from "./voci-di-costo-veicolo-vo";

export class DettaglioRichiestaVO {
    id: number;
    idTipoProcedimento: number;
    veicoli: Array<VeicoloVO>;
    idMotorizzazione: number;
    idMotivazione: number;
    noteMotivazione: string;
    nota: string;
    contratto: ContrattoProcVO;
    files: Array<AllegatoProcVO>;
    iters: Array<IterProcedimentoVO>;
    ruoloFirma: string;
    nominativoFirma: string;
    flgFirmaDigitaleEnte: boolean;
    flgFirmaDigitale: boolean;
    subProcedimento: SubProcedimentoVO;
    scenarioScaricaPDF: number;
    isACaricoEnteVisible: boolean;
    ruoloFirmaEnte: string;
    nominativoFirmaEnte: string;
    premesse: string;
    prescrizioni: string;
    vociDiCostoVeicolo: Array<VoceDiCostoVeicoloVO>;
    numProgressivo: string;

    flgAllegaLinea: boolean;

    contratti: Array<ContrattoProcVO>;

    constructor() {
        if (!this.veicoli) this.veicoli = new Array<VeicoloVO>();
        if (!this.files) this.files = new Array<AllegatoProcVO>();
        if (!this.iters) this.iters = new Array<IterProcedimentoVO>();
        if (!this.vociDiCostoVeicolo) this.vociDiCostoVeicolo = new Array<VoceDiCostoVeicoloVO>();
        if (!this.contratti) this.contratti = new Array<ContrattoProcVO>();
    }
}