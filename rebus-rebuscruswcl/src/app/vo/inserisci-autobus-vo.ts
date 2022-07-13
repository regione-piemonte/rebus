/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

import { Binary } from "@angular/compiler";
import { DocVariazAutobusVO } from "./doc-variaz-autobus-vo";

export class InserisciAutobusVO {

    id: number;

    // Dati identificativi e d'immatricolazione 
    idAzienda: number = null;
    telaio: string = null;
    idTipoImmatricolazione: number = null;
    primoTelaio: string = null;
    dataPrimaImmatricolazione: Date = null;
    targa: string = null;
    daImmatricolare: boolean;
    enteAutorizzPrimaImm: string = null;
    matricola: string = null;
    dataUltimaImmatricolazione: Date = null;

    // Caratteristiche Fisiche e Tecnologiche
    marca: string = null;
    idTipoAllestimento: number = null;
    modello: string = null;
    idTipoAlimentazione: number = null;
    omologazioneDirettivaEuropea: string = null;
    altraAlimentazione: string = null;
    idClasseAmbientale: number = null;
    caratteristicheParticolari: string = null;
    idClasseVeicolo: number = null;
    lunghezza: string = null;
    flgDuePiani: boolean = null;
    flgSnodato: boolean = null;
    flgCabinaGuidaIsolata: boolean = null;
    numeroPorte: number = null;
    nPostiInPiedi: string = null;
    nPostiSedere: string = null;
    nPostiRiservati: string = null;
    postiCarrozzina: string = null;

    // Dotazioni specifiche
    idDotazioneDisabili: number = null;
    idAltriDispositiviPrevInc: Array<number> = null;
    idImpiantiAudio: number = null;
    idImpiantiVisivi: number = null;
    altriDispositiviPrevenzInc: string = null;
    flgImpiantoCondizionamento: boolean = null;
    flgRilevatoreBip: boolean = null;
    flgContapasseggeri: boolean = null;
    flgOtx: boolean = null;
    flgAvm: boolean = null;
    flgFiltroFap: boolean = null;

    // Dati amministrativi ed Economici        
    idDeposito: number = null;
    prezzoTotAcquisto: string = null;
    idProprietaLeasing: number = null;
    contributoPubblicoAcquisto: string = null;
    dataUltimaRevisione: Date = null;
    tipologiaDimensionale: string = null;
    flgVeicoloAssicurato: boolean = null;
    flgConteggiatoMiv: boolean = null;
    // flagContribuito true in base al valore di : contributoPubblicoAcquisto        
    flgRichiestaContr: boolean = null;
    flagAlienato: boolean = null;
    flgAlienato: string = null;
    dataAlienazione: Date = null;
    dataScadVincoliNoAlien: Date = null;
    annoSostProg: string = null;

    // Verifica e Note
    flagVerificaAzienda: boolean = null;
    notaRiservataAzienda: string = null;
    flagVerificaAmp: boolean = null;
    notaRiservataAmp: string = null;
    note: string = null;


    //documenti allegati
    idDocumento: number = null;
    noteDocumento: string = null;
    file: any = null;
    nomeFile: string = null;
    documentiAutobus: DocVariazAutobusVO[];


    fkPortabici: number = null;
    fkSistemaVideosorveglianza: number = null;
    fkSistemaLocalizzazione: number = null;
    flgBipCablato: boolean = null;
    flgContapasseggeriIntegrato: boolean = null;
    flgSistemiProtezioneAutista: boolean = null;
    altriAllestimenti: string = null;
    flgContribuzione: boolean = false;

    categoriaVeicolo?: string = null;
    idCategoriaVeicolo: number = null;

    constructor() {
        this.documentiAutobus = [];
    }
}