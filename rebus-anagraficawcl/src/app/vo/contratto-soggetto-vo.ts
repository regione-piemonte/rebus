/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class ContrattoSoggettoVO {

    public idSoggettoGiuridico: number;
    public idContratto: number;
    public contratto: String;
    public enteCommittente: String;
    public dataInizioValidita: Date;
    public dataFineValidita: Date;
    public ruolo: String;
    public capofila: String;
    public soggettoAffidante: String;
    public dataInizioaAttivita: String;
    public dataFineAttivita: String;
    public scaduto: Boolean;

    constructor(){ }
}