/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class DatoSpesaVO {

	idDatoSpesa: number;
	contributoErogabileReg: number;
	contributoRegionaleFf: number;

	nrAttoLiquidazioneRpAmpAnt: string;
	dataAttoLiquidazioneRpAmpAnt: Date;
	nrDeterminaRpAmpAnt: string;
	dataDeterminaRpAmpAnt: Date;
	nrAttoLiquidazioneAmpAzAnt: string;
	dataAttoLiquidazioneAmpAzAnt: Date;
	nrDeterminaAmpAzAnt: string;
	dataDeterminaAmpAzAnt: Date;

	nrAttoLiquidazioneRpAmpSal: string;
	dataAttoLiquidazioneRpAmpSal: Date;
	nrDeterminaRpAmpSal: string;
	dataDeterminaRpAmpSal: Date;
	nrAttoLiquidazioneAmpAzSal: string;
	dataAttoLiquidazioneAmpAzSal: Date;
	nrDeterminaAmpAzSal: string;
	dataDeterminaAmpAzSal: Date;

	idUtenteAggiornamento: number;
	dataAggiornamento: Date;
	importoAnticipo: number;
	importoSaldo: number;
    
    constructor() {

    }
}