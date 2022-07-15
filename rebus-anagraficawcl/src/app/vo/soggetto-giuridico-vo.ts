/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { UbicazioneVO } from './ubicazione-vo';
import { DepositoVO } from './deposito-vo';
import { DatiBancariVO } from './dati-bancari-vo';
import { ContrattoSoggettoVO } from './contratto-soggetto-vo';

export class SoggettoGiuridicoVO {
    public id: number;

    //DATI IDENTIFICATIVI
    public idTipoSoggettoGiuridico: number;
    public idRuoloTipoSoggettoGiuridico: number;
    public denomBreve: string;
    public denomAAEP: string;
    public denominazioneRicerca: string;
    public codCsrBip: number;
    public denomOsservatorioNaz: string;
    public codOsservatorioNaz: string;
    public codRegionale: string;
    public idNaturaGiuridica: number;
    public nomeRappresentanteLegale: string;
    public cognomeRappresentanteLegale: string;
    public partitaIva: string;
    public codiceFiscale: string;
    public dataInizio: Date;
    public dataCessazione: Date;
    public aziendaAttiva: boolean;
    public aziendaCessata: boolean;
    public note: string;
    public idTipoEnte: number;
    public descrizione: string;
    public isLogoUploaded: boolean;
    public logo: File;

    //SEDE LEGALE E CONTATTI

    public ubicazioneSede: UbicazioneVO;
    public telefonoSede: string;
    public fax: string;
    public email: string;
    public pec: string;
    public indirizzoWeb: string;
    public numeroVerde: string;

    public depositi: Array<DepositoVO>;

    public datiBancari: Array<DatiBancariVO>;

    public dataAggiornamento: Date;

    public contrattiSoggetti: Array<ContrattoSoggettoVO>;


    constructor(){
        if(!this.ubicazioneSede) this.ubicazioneSede = new UbicazioneVO();
        if(!this.depositi) this.depositi = new Array<DepositoVO>();
        if(!this.datiBancari) this.datiBancari = new Array<DatiBancariVO>();
        if(!this.contrattiSoggetti) this.contrattiSoggetti = new Array<ContrattoSoggettoVO>();
    }
}
