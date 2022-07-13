/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-23 11:15:45  
 */
export class RecuperaCriteriRequest {

  constructor(public idDomanda: number, public codStatoIstruttoria: string,
    public idBando: number) {
  }

}