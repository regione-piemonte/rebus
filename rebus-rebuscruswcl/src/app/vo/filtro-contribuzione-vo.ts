/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class FiltroContribuzioneVO {
    public primoTelaio: string;
    public idFonteFinanziamento: number;
    public fonteFinanziamento: string;
    public idTipoAlimentazione: number;
    public tipoAlimentazione: string;
    public idTipoAllestimento: number;
    public tipoAllestimento: string;
    public flgVerificatoAzienda: string;
    public flgVerificatoAmp: string;
    public idProcedimento: number;
    public idAzienda: number;
    public azienda: string;
    public flgRendicontazione: string;
    public targa: string;
    public trasmessaA: string;
    public trasmessaB: string;
    public validitaA: string;
    public validitaB: string;

    public aziendaRicerca: string;

    constructor(
        primoTelaio: string,
        idFonteFinanziamento: number,
        fonteFinanziamento: string,
        idTipoAlimentazione: number,
        tipoAlimentazione: string,
        idTipoAllestimento: number,
        tipoAllestimento: string,
        flgVerificatoAzienda: string,
        flgVerificatoAmp: string,
        idProcedimento: number,
        azienda: string,
        idAzienda: number,
        targa: string,
        flgRendicontazione: string,
        trasmessaA: string,
        trasmessaB: string,
        validitaA: string,
        validitaB: string,
        aziendaRicerca: string,


    ) {
        this.primoTelaio = primoTelaio;
        this.idFonteFinanziamento = idFonteFinanziamento;
        this.fonteFinanziamento = fonteFinanziamento;
        this.idTipoAlimentazione = idTipoAlimentazione;
        this.tipoAlimentazione = tipoAlimentazione;
        this.idTipoAllestimento = idTipoAllestimento;
        this.tipoAllestimento = tipoAllestimento;
        this.flgVerificatoAzienda = flgVerificatoAzienda;
        this.flgVerificatoAmp = flgVerificatoAmp;
        this.idProcedimento = idProcedimento;
        this.azienda = azienda;
        this.targa = targa;
        this.idAzienda = idAzienda;
        this.flgRendicontazione = flgRendicontazione;
        this.trasmessaA = trasmessaA;
        this.trasmessaB = trasmessaB;
        this.validitaA = validitaA;
        this.validitaB = validitaB;
        this.aziendaRicerca = aziendaRicerca;
    }
}

