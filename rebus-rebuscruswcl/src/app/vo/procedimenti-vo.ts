/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class ProcedimentiVO {
    public idProcedimento: number;
    public progressivoRichiesta: string;
    public idTipologia: number;
    public tipologia: string;
    public richiedente: string;
    public idStato: number;
    public stato: string;
    public dataStipulaDa: Date;
    public dataStipulaA: Date;
    public gestoreContratto: string;
    public isAbilitatoDettaglio: Boolean;
    public isAbilitatoModifica: Boolean;
    public isAbilitatoElimina: Boolean;
    public isAbilitatoScaricaPDFAzienda: Boolean;
    public isAbilitatoScaricaPDFEnte: Boolean;
}