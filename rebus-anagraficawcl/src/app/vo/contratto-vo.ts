/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { ContrattoRaggruppamentoVO } from './contratto-raggruppamento-vo';
import { AmbTipServizioVO } from './amb-tip-servizio-vo';
import { ProrogaVO } from './proroga-vo';
import { AllegatoVO } from './allegato-vo';
import { SubentroSubaffidamentoVO } from './subentro-subaffidamento-vo';
import { SoggettoCoinvoltoVO } from './soggetto-coinvolto-vo';
import { SoggettoCoinvoltoPeriodiVO } from './soggetto-coinvolto-periodi-vo';

export class ContrattoVO {
    public idContratto: number;
    public idContrattoPadre: number;
    public codIdNazionale: string;
    public codRegionale: string;
    public numRepertorio: string;
    public idSogGiuridCommittente: number;
    public idNaturaGiuridicaCommittente: number;
    public idSogGiuridEsecutore: number;
    public idTipoSogGiuridEsec: number;
    public idNaturaGiuridicaEsec: number;
    public idTipoRaggrSogGiuridEsec: number;
    public idTipoAffidamento: number;
    public idModalitaAffidamento: number;
    public idAziendaMandataria :number;
    public accordoProgramma: string;
    public grossCost: boolean;
    public accordoDiProgramma: string;
    public CIG: string;
    public dataStipula: Date;
    public descContratto: string;
    public idBacino: number;
    public ambTipServizio: Array<AmbTipServizioVO>;
    public dataInizioValidita: Date;
    public dataFineValidita: Date;
    public contrattoRaggruppamentoVOs: Array<ContrattoRaggruppamentoVO>;
    public proroghe: Array<ProrogaVO>;
    public subentriSubaffidamenti: Array<SubentroSubaffidamentoVO>;
    public allegati: Array<AllegatoVO>;
    public dataAggiornamento: Date;
    public soggettiCoinvolti: Array<SoggettoCoinvoltoVO>;
    public soggettiCoinvoltiPeriodi: Array<SoggettoCoinvoltoPeriodiVO>;
    public dataFiltroSoggetto: Date;

    constructor() {
        this.contrattoRaggruppamentoVOs = new Array<ContrattoRaggruppamentoVO>();
        this.ambTipServizio = new Array<AmbTipServizioVO>();
        this.proroghe = new Array<ProrogaVO>();
        this.subentriSubaffidamenti = new Array<SubentroSubaffidamentoVO>();
        this.allegati = new Array<AllegatoVO>();
        this.soggettiCoinvolti = new Array<SoggettoCoinvoltoVO>();
    }
}
