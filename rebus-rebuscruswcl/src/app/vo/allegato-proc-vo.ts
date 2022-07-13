/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { TipoDocumentoVO } from "./extend-vo";

export class AllegatoProcVO {
    idProcedimento: number;
    tipoDocumento: TipoDocumentoVO;
    file: File;
    nomeFile: string;
    dataCaricamento: Date;
    note: string;
    isFileUploaded: boolean;

    constructor() {
        if (!this.tipoDocumento) this.tipoDocumento = new TipoDocumentoVO();
    }
}