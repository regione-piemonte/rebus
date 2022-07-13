/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class ContrattoProcVO {
    idContratto: number;
    idProcContratto: number;
    idProcedimento: number;
    codIdNazionale: string;
    descrizione: string;
    idTipoContratto: number;
    selected: boolean;
}

export class ContrattoProcDatiVO {
    codIdNazionale: string;
    codRegionale: string;
    descrizione: string;
    enteComm: SoggettoContrattoVO;
    esecTit: SoggettoContrattoVO;
    capofila: SoggettoContrattoVO;
    soggRichiedente: SoggettoContrattoVO;
    soggIntermediario: SoggettoContrattoVO;
    idTipoRaggruppamento: number;
    descTipoRaggruppamento: string;
    enteCommString: string;
    esecTitString: string;
    capofilaString: string;
    soggRichiedenteString: string;
    soggIntermediarioString: string;

    constructor(data: ContrattoProcDatiVO) {
        this.codIdNazionale = data.codIdNazionale;
        this.codRegionale = data.codRegionale;
        this.descrizione = data.descrizione;
        this.enteComm = data.enteComm;
        this.esecTit = data.esecTit;
        this.capofila = data.capofila;
        this.soggRichiedente = data.soggRichiedente;
        this.soggIntermediario = data.soggIntermediario;
        this.idTipoRaggruppamento = data.idTipoRaggruppamento;
        this.descTipoRaggruppamento = data.descTipoRaggruppamento;
        this.enteCommString = this.enteComm.codiceOss + " - " + this.enteComm.denomBreve;
        this.esecTitString = this.esecTit.codiceOss + " - " + this.esecTit.denomBreve;
        if (this.idTipoRaggruppamento) {
            this.esecTitString += " (" + this.descTipoRaggruppamento + ")";
        }
        this.soggRichiedenteString = this.soggRichiedente.codiceOss + " - " + this.soggRichiedente.denomBreve;
        if (this.capofila) {
            this.capofilaString = this.capofila.codiceOss + " - " + this.capofila.denomBreve;
        } else {
            this.capofilaString = null;
        }
        if (this.soggIntermediario) {
            this.soggIntermediarioString = this.soggIntermediario.codiceOss + " - " + this.soggIntermediario.denomBreve;
        } else {
            this.soggIntermediarioString = null;
        }
    }
}

export class SoggettoContrattoVO {
    id: number;
    codiceOss: string;
    denomBreve: string;
}