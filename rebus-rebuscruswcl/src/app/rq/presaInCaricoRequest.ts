/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Riccardo.bova 
 * @Date: 2017-11-07 11:15:45  
 */
import { DomandaPresaInCarico } from "./domandaPresaInCarico";

export class PresaInCaricoRequest {

    constructor(public domandePresaIncarico: Array<DomandaPresaInCarico>) {

    }

}