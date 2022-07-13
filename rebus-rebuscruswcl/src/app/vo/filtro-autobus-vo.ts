/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

/*
 * @Author: Massimo.durando 
 * @Date: 2018-01-12 10:22:42  */
export class FiltroAutobusVO {

    constructor(
        //filtri input
        public denominazioneAzienda: string, // se not azienda
        public targa: string,
        public matricola: string,
        public primoTelaio: string,
        public codiceRichiesta: string,
        public tipoAlimentazione: string,
        public dataPrimaImmatricolazioneDa: string,
        public dataPrimaImmatricolazioneA: string,

        //filtro storico
        public situazioneAl: string,
        public flagIncludiVariazioniPre: string,

        //filtro avanzato
        public autobus: string,
        public autovetture: string,
        public attivo: string,
        public ritirato: string,
        public alienatoAzienda: string,
        public alienatoNoAzienda: string,
        public includiPropPrec: string,
        public daImmatricolare: string,
        public verificatoAziende: string,
        public verificatoAMP: string,
        public bozza: string,
        public richieste: string,
        public rendicontazione: string,

    ) {
    }
}

