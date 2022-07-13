/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { DatoBonificoVO } from "./dato-bonifico-vo";
import { DocContribuzioneVO } from "./doc-contribuzione-vo";

export class DatoFatturaVO {

	idDatoFattura: number;
	idDatoSpesa: number;
	idDocContribuzione: number;
	numero: string;
	data: Date;
	importo: number;
	nrQuietanzaAzForn: string;
	dataQuietanzaAzForn: Date;
	idTipoDocQuietanza: number;
	documento: DocContribuzioneVO;
	listaIdVoceCosto: number[];
	listaBonifici: DatoBonificoVO[];
	vociCosto: string; //stringa contenente le voci costo


    constructor() {
		this.listaIdVoceCosto = [];
		this.listaBonifici = [];
    }
}