/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class ExtendVO {
    id: number;
    descrizione: string;
}
export class StatoProcedimentoVO extends ExtendVO { }
export class TipoProcedimentoVO extends ExtendVO {
    codProcedimento: string;
}
export class MotorizzazioneVO extends ExtendVO { }
export class MotivazioneVO extends ExtendVO {
    flgMotivAltro: boolean;
}
export class TipoContrattoVO extends ExtendVO { }
export class TipoDocumentoVO extends ExtendVO { }
export class VoceDiCostoVO extends ExtendVO {
    importo: number;
}