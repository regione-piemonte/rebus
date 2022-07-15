/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { NaturaGiuridicaVO } from './natura-giuridica-vo';
import { TipoSoggettoGiuridicoVO } from './tipo-soggetto-giuridico-vo';

export class SoggettoRicercaVO {
    public id: number;
    tipologia: TipoSoggettoGiuridicoVO;
    public codOsservatorioNaz: string;
    public codRegionale: string;
    public denominazione: string;
    public partitaIva: string;
    public naturaGiuridica: NaturaGiuridicaVO;
    public aziendaNonAttiva: boolean;
    public aziendaCessata: boolean;
    public dataAggiornamento: Date;

    constructor(){
        if(this.naturaGiuridica==null) this.naturaGiuridica = new NaturaGiuridicaVO();
        if(this.tipologia==null) this.tipologia = new TipoSoggettoGiuridicoVO();
    }
}