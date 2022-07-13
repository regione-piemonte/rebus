/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { AllegatoProcVO } from "./allegato-proc-vo";
import { VeicoloVO } from "./veicolo-vo";
import { MotorizzazioneVO, MotivazioneVO, TipoProcedimentoVO, VoceDiCostoVO } from "./extend-vo";
import { ContrattoProcVO } from "./contratto-proc-vo";

export class InserisciRichiestaVO {
    tipoProcedimento: TipoProcedimentoVO;
    veicoli: Array<VeicoloVO>;
    motorizzazione: MotorizzazioneVO;
    motivazione: MotivazioneVO;
    noteMotivazione: string;
    nota: string;
    nominativoFirma: string;
    ruoloFirma: string;
    flgFirmaDigitale: Boolean = false;
    flgAllegaLinea: Boolean = false;
    contratto: ContrattoProcVO;
    files: Array<AllegatoProcVO>;
    ruoloFirmaEnte: string;
    nominativoFirmaEnte: string;
    premesse: string;
    prescrizioni: string;
    vociDiCosto: Array<VoceDiCostoVO>;
    numProcedimento: number;

    constructor() {
        if (!this.tipoProcedimento) this.tipoProcedimento = new TipoProcedimentoVO();
        if (!this.veicoli) this.veicoli = new Array<VeicoloVO>();
        if (!this.motorizzazione) this.motorizzazione = new MotorizzazioneVO();
        if (!this.motivazione) this.motivazione = new MotivazioneVO();
        if (!this.contratto) this.contratto = new ContrattoProcVO();
        if (!this.files) this.files = new Array<AllegatoProcVO>();
        if (!this.vociDiCosto) this.vociDiCosto = new Array<VoceDiCostoVO>();
    }
}