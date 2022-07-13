/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class AssegnazioneRisorseVO {
    idAssegnazioneRisorse: number;
	idFonteFinanziamento: number;
	contributoStatale: number;
	contributoRegionaleAgg: number;
	idUtenteAggiornamento: number;
	dataAggiornamento: Date;

	constructor(){
		this.contributoRegionaleAgg = 0;
	}
}