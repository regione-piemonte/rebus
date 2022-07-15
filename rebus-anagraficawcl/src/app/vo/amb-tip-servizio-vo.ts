/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { AmbitoServizioVO, TipologiaServizioVO } from './drop-down-menu-vo';

export class AmbTipServizioVO {

    public idAmbTipServizio: number;
    public ambitoServizio: AmbitoServizioVO;
    public tipologiaServizio: TipologiaServizioVO;
    public selected: boolean;

    constructor(idAmbTipServizio?: number, ambitoServizio?: AmbitoServizioVO, tipologiaServizio?: TipologiaServizioVO, selected?: boolean) {
        this.idAmbTipServizio = idAmbTipServizio;
        this.ambitoServizio = ambitoServizio;
        this.tipologiaServizio = tipologiaServizio;
        this.selected = selected;
    }
}