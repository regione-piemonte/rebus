/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { StringMap } from "@angular/core/src/render3/jit/compiler_facade_interface";

export class FiltroContrattoVO {
    public codIdentificativo: string;
    public numRepertorio: string;
    public enteCommittente: string;
    public soggEsecutore: string;
    public dataStipulaDa: Date;
    public dataStipulaA: Date;
    public flagIncludiCessate: string;
    public descrizione: string;

    constructor(
        codIdentificativo: string,
        numRepertorio: string,
        enteCommittente: string,
        soggEsecutore: string,
        descrizione: string,
        dataStipulaDa: Date,
        dataStipulaA: Date,
        flagIncludiCessate: string,

    ) {
        this.codIdentificativo = codIdentificativo;
        this.numRepertorio = numRepertorio;
        this.enteCommittente = enteCommittente;
        this.soggEsecutore = soggEsecutore;
        this.descrizione=descrizione;
        this.dataStipulaDa = dataStipulaDa;
        this.dataStipulaA = dataStipulaA;
        this.flagIncludiCessate = flagIncludiCessate;


    }
}

