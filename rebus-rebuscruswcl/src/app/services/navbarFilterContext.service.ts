/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Injectable } from '@angular/core';
import { ContestoVO } from '../vo/contesto-vo';
import * as config from '../globalparameter';



@Injectable()
export class NavbarFilterContext {

  // INFOFILTRO CONTINENE LA TAB DA VISUALIZZARE QUANDO SI ENTRA DA FUMETTO(CHIAMATA EFFETTUATA NEL COMPONENT WELCOME)
  private infoFiltro: ContestoVO;
  // ELENCO CONTESTI CONTIENE TUTTI I CONTESTI (CHIAMATA EFFETTUARTA IN APP COMPONENT)
  private elencoContesti: Array<ContestoVO>;

  set InfoFiltro(infoFiltro: ContestoVO) {
    this.infoFiltro = infoFiltro;
  }

  get InfoFiltro(): ContestoVO {
    return this.infoFiltro;
  }

  set ElencoContesti(elencoContesti: Array<ContestoVO>) {
    this.elencoContesti = elencoContesti;
  }

  get ElencoContesti(): Array<ContestoVO> {
    return this.elencoContesti;
  }

  isEmptyFilterNavbarContex() {
    if (config.isNullOrVoid(this.infoFiltro) || config.isNullOrVoid(this.infoFiltro.id) || this.elencoContesti.length <= 0) {
      return true;
    }
  }

}
