/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class AggiornaEsitoRequest {

        constructor(public codStatoIstruttoria: string,
                public idDomanda: number,
                public note: string,
                public esito: number,
                public idMotivazioneEsito: number) { }
}