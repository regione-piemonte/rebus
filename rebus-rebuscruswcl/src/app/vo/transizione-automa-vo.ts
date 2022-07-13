/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class TransizioneAutomaVO {
    idTransizioneAutoma: number;
    idStatoIterDa: number;
    idStatoIterA: number;
    titolo: string;
    label: string;
    testo: string;
    condizione: string;
    flgNoteObbligatorie: boolean;
    idTipoMessaggio: number;
    dataInizioValidita: Date;
    dataFineValidita: Date;
    flagDefault: boolean;
    returnTransizione: number;
}