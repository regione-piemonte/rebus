/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class FiltroSoggettoVO {
    public idTipoSoggGiurid: number;
    public codOssNaz: string;
    public descrizione: string;
    public codFiscale: string;
    public flagIncludiCessate: string;
    public flagIncludiNonAttive: string;
    public flagIncludiAttive: string;

    constructor(
        idTipoSoggGiurid: number,
        codOssNaz: string,
        descrizione: string,
        codFiscale: string,
        flagIncludiCessate: string,
        flagIncludiNonAttive: string,
        flagIncludiAttive: string,
    ) {
        this.idTipoSoggGiurid = idTipoSoggGiurid;
        this.codOssNaz = codOssNaz;
        this.descrizione = descrizione;
        this.codFiscale = codFiscale;
        this.flagIncludiCessate = flagIncludiCessate;
        this.flagIncludiNonAttive = flagIncludiNonAttive;
        this.flagIncludiAttive = flagIncludiAttive;
    }
}

