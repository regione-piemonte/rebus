/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
export class ContribuzioneVO{

	idContribuzione: number;
	idAssegnazioneRisorse: number;
	idOrdineAcquisto: number;
	idCostoFornitura: number;
	idDatoSpesa: number;
	idDatoMessaServizio: number;
	idProcedimento: number;
	noteAzienda: string;
	noteRpAmp: string;
	idUtenteAggiornamento: number;
	dataAggiornamento: Date;

    constructor(){
    }
}