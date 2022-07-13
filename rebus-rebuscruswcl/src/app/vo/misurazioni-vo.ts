/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { EmissioniVo } from "./emissioni-vo";
import { PortabiciVo } from "./portabici-vo";

export class MisurazioniVO {
  public idMisurazione?: number;
  public primoTelaio?: string;
  public idDocMisurazione?: number;
  public dataInizio?: Date;
  public dataFine?: Date;
  public idUtenteAggiornamento?: number;
  public dataAggiornamento?: Date;

  public idCampagna?: number;
  public descrizione?: string;
  public idTipoMonitoraggio?: number;
  public codTipoMonitoraggio?: string;
  public dataInizioValidita?: Date;
  public dataFineValidita?: Date;
  public dataInizioRestituzione?: Date;
  public dataFineRestituzione?: Date;

  public emissioni?: Array<EmissioniVo>;
  public portabici?: Array<PortabiciVo>;
  
  constructor() {


  }
}