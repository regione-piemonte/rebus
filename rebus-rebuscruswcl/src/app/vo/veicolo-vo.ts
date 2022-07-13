/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { AllegatoVeicoloVO } from "./allegato-veicolo-vo";

export class VeicoloVO {
    idVariazAutobus: number;
    idProcedimento: number;
    primoTelaio: string;
    documento: string;
    nTarga: string;
    descClasseAmbEuro: string;
    descTipoAllestimento: string;
    lunghezza: number;
    marca: string;
    modello: string;
    dataPrimaImmatricolazione: Date;
    dataUltimaImmatricolazione: Date;
    selected: boolean;
    allegati: Array<AllegatoVeicoloVO>;

    constructor(idVariazAutobus?: number, primoTelaio?: string, descClasseAmbEuro?: string, descTipoAllestimento?: string, selected?: boolean, allegati?: Array<AllegatoVeicoloVO>, documento?: string, idProcedimento?: number) {
        this.idVariazAutobus = idVariazAutobus;
        this.primoTelaio = primoTelaio;
        this.descClasseAmbEuro = descClasseAmbEuro;
        this.descTipoAllestimento = descTipoAllestimento;
        this.selected = selected;
        this.allegati = allegati;
        this.documento = documento;
        this.idProcedimento = idProcedimento;

        if (!this.allegati) this.allegati = new Array<AllegatoVeicoloVO>();
    }
}

