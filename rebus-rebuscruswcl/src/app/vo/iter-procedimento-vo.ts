/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class IterProcedimentoVO {
    id: number;
    idProcedimento: number;
    idStato: number;
    descStato: string;
    dataInizioValidita: Date;
    dataFineValidita: Date;
    utente: string;
    note: string;
}