/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { TipoDocumentoVO } from "./extend-vo";

export class AllegatoVeicoloVO {
    tipoDocumento: TipoDocumentoVO;
    note: string;
    file: File;
    nomeFile: string;
    dataCaricamento: Date;
    primoTelaioVeicolo: string;
    nTargaVeicolo: string;

    constructor(){
        if(!this.tipoDocumento) this.tipoDocumento = new TipoDocumentoVO(); 
    }
}