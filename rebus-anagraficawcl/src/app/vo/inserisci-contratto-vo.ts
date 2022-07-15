/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { AllegatoVO } from './allegato-vo';

export class InserisciContrattoVO {
    //DATI Generali
    public descrizione: string;
    public codiceIdentificativoNazionale: string;
    public numeroRepertorio: string;
    public idBacino: number;
    public idTipoAffidamento: number;
    public idModalitaAffidamento: number;
    public idAmbTipoServizi: Array<number>;
    public accordoDiProgramma: string;
    public grossCost: boolean;
    public cig: string;
    public dataStipula: Date;
    public dataInizioValidita: Date;
    public dataFineValidita: Date;

    //Dati Contraenti

    public idEnteCommittente: number;
    public idSoggettoEsecutore: number;
    public idTipoSoggettoEsecutore: number;
    public idTipoRaggruppamento: number;
    public idAziendaMandataria: number;
    public idComposizioneRaggruppamento: Array<number>;
    
    //Allegato
    public files: Array<AllegatoVO>;

    constructor(){
        this.files = new Array<AllegatoVO>();
        this.idComposizioneRaggruppamento = new Array<number>();
        this.idAmbTipoServizi = new Array<number>();
    }
}