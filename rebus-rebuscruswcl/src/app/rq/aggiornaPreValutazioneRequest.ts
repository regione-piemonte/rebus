
/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Parametro } from "./parametro";

export class AggiornaPreValutazioneRequest {

        constructor(public idDomanda: number, public idSpecifica, public parametri: Array<Parametro>) { }
}