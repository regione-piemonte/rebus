/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { UbicazioneVO } from './ubicazione-vo';

export class DepositoVO {
    public denominazione: string;
    public ubicazione: UbicazioneVO;
    public telefono: string;
    public depositoPrevalenteFlg: boolean;

    constructor(){
        if(!this.ubicazione) this.ubicazione = new UbicazioneVO();
    }
}