/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class dropDownMenuVO {
    public id: number;
    public descrizione: string;

}

export class BacinoVO extends dropDownMenuVO { }
export class TipoAffidamentoVO extends dropDownMenuVO { }
export class ModalitaAffidamentoVO extends dropDownMenuVO { }
export class TipologiaServizioVO extends dropDownMenuVO { }
export class AmbitoServizioVO extends dropDownMenuVO { }
export class TipoRaggruppamentoVO extends dropDownMenuVO {
    public idRuolo: number;
}
export class AziendaMandatariaVO extends dropDownMenuVO {
    public idRuolo: number;
    public idContratto: number;
    public idContrattoRaggruppamento: number;
 }
export class ComposizioneRaggruppamentoVO extends dropDownMenuVO {
    public idContrattoRaggruppamento: number;
    public selected: boolean;
    public idRuolo: number;
}
export class TipoDocumentoVO extends dropDownMenuVO {
    descrizioneEstesa: string;
    ordinamento: number;
}
export class TipoSostituzioneVO extends dropDownMenuVO { }
