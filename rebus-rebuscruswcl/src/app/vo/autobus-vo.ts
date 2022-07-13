/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/
import { CampagnaVO } from "./campagna-vo";
import { Cronologia } from "./cronologia";
import { DocVariazAutobusVO } from "./doc-variaz-autobus-vo";
import { EmissioniVo } from "./emissioni-vo";
import { PortabiciVo } from "./portabici-vo";
import { Procedimenti } from "./procedimenti";

/*
 * @Author: Massimo.durando 
 * @Date: 2018-01-12 10:22:42  */
export class AutobusVO {

    public id?: number;
    public idAzienda?: number;
    public idTipoAllestimento?: number;
    public idImpiantiAudio?: number;
    public idImpiantiVisivi?: number;
    public idClasseVeicolo?: number;
    public idTipoImmatricolazione?: number;
    public idTipoAlimentazione?: number;
    public idClasseAmbientale?: number;
    public idProprietaLeasing?: number;
    public bando?: number;
    public idDeposito: number;
    public depositoStr: string;
    public idDotazioneDisabili?: number;
    public prezzoTotAcquisto?: string;
    public prezzoTotAcquistoStr?: string;
    public contributoPubblicoAcquisto?: string;
    public contributoPubblicoAcquistoStr?: string;
    public lunghezza?: string;
    public lunghezzaStr?: string;
    public notaRiservataAzienda?: string;
    public notaRiservataAmp?: string;
    public notaRiservataRp?: string;
    public dataAlienazione?: Date;
    public flgConteggiatoMiv?: string;
    public numeroPorte?: number;
    public postiCarrozzina?: string;
    public flgImpiantoCondizionamento?: string;
    public flgCabinaGuidaIsolata?: string;
    public altriDispositiviPrevenzInc?: string;
    public dispositiviPrevenzInc?: string;
    public annoSostProg?: string;
    public flgOtx?: string;
    public flgAvm?: string;
    public flgAlienato?: string; //rimane
    public flgContapasseggeri?: string;
    public progrTipoDimens?: string;
    public flgDuePiani?: string;
    public flgSnodato?: string;
    public caratteristicheParticolari?: string;
    public altraAlimentazione?: string;
    public telaio?: string;
    public note?: string;
    public enteAutorizzPrimaImm?: string;
    public flgRilevatoreBip?: string;
    public flgFiltroFap?: string;
    public flgVeicoloAssicurato?: string;
    public dataUltimaRevisione?: Date;
    public primoTelaio?: string;
    public targa?: string;
    public daImmatricolare: boolean;
    public matricola?: string;
    public dataPrimaImmatricolazione?: Date;
    public dataScadVincoliNoAlien?: string;
    public dataUltimaImmatricolazione?: Date;
    public dataAggiornamento?: Date;
    public tipoAllestimento?: string;
    public tipologiaDimensionale?: string;
    public contributo?: string;
    public flagVerificaAzienda?: string;
    public flagVerificaAmp?: string;
    public marca?: string;
    public modello?: string;
    public nPostiSedere?: string;
    public nPostiInPiedi?: string;
    public nPostiRiservati?: string;
    public omologazioneDirettivaEuropea?: string;
    public azienda?: string;
    public impiantiAudio?: string;
    public impiantiVisivi?: string;
    public classeVeicolo?: string;
    public tipoImmatricolazione?: string;
    public tipoAlimentazione?: string;
    public proprietaLeasing?: string;
    public classeAmbientale?: string;
    public dotazioneDisabili?: string;
    public dispositiviPrevIncidenti?: Array<Number>;
    public idAltriDispositiviPrevInc?: number;
    public flgRichiestaContr?: any;
    public motivazione?: string;

    public fkPortabici: number;
    public portabici: string;
	public fkSistemaVideosorveglianza: number;
    public sistemaVideosorveglianza: string;
	public fkSistemaLocalizzazione: number;
    public tipologiaAvm: string;
	public flgBipCablato: boolean;
	public flgContapasseggeriIntegrato: boolean;
	public flgSistemiProtezioneAutista: boolean;
	public altriAllestimenti: string;

	public flgContribuzione: boolean;


    public flgDaImmatricolare: boolean;
    


    idDocMisurazione: number;
    idUtenteAggiornamento: number;


    //documenti allegati
    public idDocumento?: number;
    public noteDocumento?: string;
    public descrizioneDocumento?: string;
    public file?: any;
    public dataCaricamentoDoc?: Date;
    public nomeFile?: string;
    public documentiAutobus: DocVariazAutobusVO[];

    //isFileUpload
    public isFileUpload?: string;
    public cronologia?: Array<Cronologia>;
    public procedimenti?: Array<Procedimenti>;

    public campagna?: Array<CampagnaVO>;
    
    public emissioni?: Array<EmissioniVo>;
    public portabiciList?: Array<PortabiciVo>;
    public campagnaE?: Array<CampagnaVO>;
    public campagnaP?: Array<CampagnaVO>;

    //
    public idProcedimento: number;

    //contribuzione 2
    public idCategoriaVeicolo: number;
    public categoriaVeicolo: string;
    public statoTpl: string;

    public sostituito: boolean;

    constructor() {
    }
} 