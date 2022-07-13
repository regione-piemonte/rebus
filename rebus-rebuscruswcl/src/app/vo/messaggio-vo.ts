/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { AutobusVO } from "./autobus-vo";
export class MessaggioVO {


	public idMessaggio?: number;
	public fkTipoMessaggio?: number;
	public fkUtenteMittente?: number;
	public fkUtenteDestinatario?: number;
	public fkVariazAutobus?: number;
	public fkStoriaVariazAutobus?: number;
	public fkVariazAutobusSucc?: number;
	public messaggio?: string;
	public dataCreazione: Date = null;
	public flgLetto?: string;
	public dataLettura: Date = null;
	public flgArchiviato?: string;
	public dataArchiviazione: Date = null
	public targa: string;
	public azienda: string;
	public descTipoMessaggio: string;
	public variazioneAutobus: AutobusVO;
	public variazioneStoricoAutobus: AutobusVO;
	public fkProcedimento: number;
	public note: string;
	public testoMessaggioSistema: string;
	public fkTipoMessaggioSistema: number;

	constructor() { }


}