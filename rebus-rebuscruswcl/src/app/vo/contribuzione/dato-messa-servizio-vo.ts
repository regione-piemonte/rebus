/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class DatoMessaServizioVO{

	idDatoMessaServizio: number;
	idTipoSostituzione: number;
	nrCartaCircolazione: string;
	flgPannello: boolean;
	idUtenteAggiornamento: number;
	dataAggiornamento: Date;
	primoTelaioSost: string;

    constructor(){
		this.flgPannello = null;
		this.idTipoSostituzione = null;
    }
}