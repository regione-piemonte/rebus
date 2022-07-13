/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export class CampagnaVO {

    public idMisurazione?: number;
    public primoTelaio?: string;
    public idDocMisurazione?: number;
    public dataInizio?: Date;
    public dataFine?: Date;
    public idUtenteAggiornamento?: number;
    public idCampagna?: number;
    public dataAggiornamento?: Date;
    public descrizione?: string;
    public idTipoMonitoraggio?: number;
    public codTipoMonitoraggio?: string;
    public dataInizioValidita?: Date;
    public dataFineValidita?: Date;
    public dataInizioRestituzione?: Date;
    public dataFineRestituzione?: Date;

    constructor() {
    }
} 