/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';



@Injectable()
export class FilterUtilsService {

  private infoFiltro: any;

  set InfoFiltro(infoFiltro: any) {
    this.infoFiltro = infoFiltro;
  }

  get InfoFiltro(): any {
    return this.infoFiltro;
  }


}
