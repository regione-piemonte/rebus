/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Massimo.durando 
 * @Date: 2018-01-12 10:22:42  
 **/
export class ComboVO {

    constructor(
        public identificativo: TipoCombo,
        public valori: DescrizioneVO[]
    ) { }
}


export class DescrizioneVO {

    constructor(
        public codice: number,
        public descrizione: string
    ) { }
}

export enum TipoCombo {
    CLASSE_VEICOLO = "CLASSE_VEICOLO",
    TIPO_EURO = "TIPO_EURO",
    TIPO_ALIMENTAZIONE = "TIPO_ALIMENTAZIONE",
    TIPO_DISPOSITIVI_PREVENZIONE = "TIPO_DISPOSITIVI_PREVENZIONE",
    TIPO_AUDIO = "TIPO_AUDIO",
    TIPO_VIDEO = "TIPO_VIDEO",
    TIPO_ALLESTIMENTO = "TIPO_ALLESTIMENTO",
    TIPO_FACILITAZIONI = "TIPO_FACILITAZIONI",
    TIPO_IMMATRICOLAZIONE = "TIPO_IMMATRICOLAZIONE",
    PROPRIETA_LEASING = "PROPRIETA_LEASING",
    CATEGORIA_VEICOLO= "CATEGORIA_VEICOLO"


}

