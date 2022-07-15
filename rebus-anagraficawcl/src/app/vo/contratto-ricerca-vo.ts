/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { NaturaGiuridicaVO } from './natura-giuridica-vo';
import { SoggettoGiuridicoVO } from './soggetto-giuridico-vo';
import { EnteVO } from './ente-vo';
import { SoggettoEsecutoreVO } from './soggetto-esecutore-vo';

export class ContrattoRicercaVO {
    public id: number;
    public codIdentificativo: string;
    public codRegionale: string;
    public numRepertorio: string;
    public CIG: string;
    public enteCommittente: EnteVO;
    public naturaGiuridicaEnte: NaturaGiuridicaVO;
    public soggEsecutore: SoggettoEsecutoreVO;
    public naturaGiuridicaEsec: NaturaGiuridicaVO;
    public dataScadenza: Date;
    public contrattoCessato: boolean;
    public prorogato: boolean;
    public dataFineUltimaProroga: Date;
    public dataAggiornamento: Date;
    public descContratto: string;

    constructor(){
        if(this.enteCommittente==null) this.enteCommittente = new EnteVO();
        if(this.soggEsecutore==null) this.soggEsecutore = new SoggettoEsecutoreVO();
        if(this.naturaGiuridicaEnte==null) this.naturaGiuridicaEnte = new NaturaGiuridicaVO();
        if(this.naturaGiuridicaEsec==null) this.naturaGiuridicaEsec = new NaturaGiuridicaVO();
    }
}
