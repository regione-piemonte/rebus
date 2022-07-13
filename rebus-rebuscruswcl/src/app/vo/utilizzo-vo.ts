/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { ContrattoProcDatiVO, ContrattoProcVO } from "./contratto-proc-vo";
import { TipoContrattoVO } from "./extend-vo";

export class UtilizzoVO {
    contratto: ContrattoProcVO;
    tipoContratto: TipoContrattoVO;
    datiContratto: ContrattoProcDatiVO;
}