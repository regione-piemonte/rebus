/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { AllegatoProcVO } from "./allegato-proc-vo";
import { VeicoloVO } from "./veicolo-vo";
import { TipoProcedimentoVO } from "./extend-vo";
import { UtilizzoVO } from "./utilizzo-vo";

export class InserisciRichiestaUsoInLineaVO {
    tipoProcedimento: TipoProcedimentoVO;
    veicoli: Array<VeicoloVO>;
    nota: string;
    utilizzi: Array<UtilizzoVO>;
    ruoloFirma: string;
    nominativoFirma: string;
    flgFirmaDigitale: Boolean;
    ruoloFirmaEnte: string;
    nominativoFirmaEnte: string;
    premesse: string;
    prescrizioni: string;
    files: Array<AllegatoProcVO>;
    numProcedimento: number;

    constructor() {
        if (!this.tipoProcedimento) this.tipoProcedimento = new TipoProcedimentoVO();
        if (!this.veicoli) this.veicoli = new Array<VeicoloVO>();
        if (!this.utilizzi) this.utilizzi = new Array<UtilizzoVO>();
        if (!this.files) this.files = new Array<AllegatoProcVO>();

    }
}