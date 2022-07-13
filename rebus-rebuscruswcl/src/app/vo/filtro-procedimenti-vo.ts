/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class FiltroProcedimentiVO {
    public tipologia: number;
    public progressivoRichiesta: string;
    public richiedente: string;
    public stato: number;
    public dataStipulaDa: Date;
    public dataStipulaA: Date;
    public flagStatoCorrente: string;
    public targa: string;

    constructor(
        tipologia: number,
        progressivoRichiesta: string,
        richiedente: string,
        stato: number,
        dataStipulaDa: Date,
        dataStipulaA: Date,
        flagStatoCorrente: string,
        targa: string,
    ) {
        this.tipologia = tipologia;
        this.progressivoRichiesta = progressivoRichiesta;
        this.richiedente = richiedente;
        this.stato = stato;
        this.dataStipulaDa = dataStipulaDa;
        this.dataStipulaA = dataStipulaA;
        this.flagStatoCorrente = flagStatoCorrente;
        this.targa = targa;
    }
}

