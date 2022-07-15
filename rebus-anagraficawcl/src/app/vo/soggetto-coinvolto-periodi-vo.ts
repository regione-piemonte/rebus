/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class SoggettoCoinvoltoPeriodiVO {

    public idContratto: number;
    public alias: String;
    public ruolo: String;
    public dataInizio: String;
    public dataFine: String;
    public soggettoAffidante: String;
    public soggettoSostituito: String;
    public atto: String;
    public scaduto: Boolean;
    public idSoggettoGiuridico: number;
    
    constructor(){ }
}