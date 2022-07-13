/*************************************************
Copyright Regione Piemonte - 2022
SPDX-License-Identifier: EUPL-1.2-or-later
***************************************************/

export enum AutobusLabel {

    azienda = "Azienda",
    telaio = "Telaio",
    idTipoImmatricolazione = "Tipo immatricolazione",
    primoTelaio = "Primo telaio",
    dataPrimaImmatricolazione = "Data prima immatricolazione",
    targa = "Targa",
    enteAutorizzPrimaImm = "Ente che ha autorizzato l'ultima immatricolazione",
    matricola = "Matricola aziendale",
    dataUltimaImmatricolazione = "Data ultima immatricolazione",
    marca = "Marca",
    idTipoAllestimento = "Allestimento",
    categoriaVeicolo = "Categoria Veicolo",
    modello = "Modello",
    idTipoAlimentazione = "Alimentazione",
    omologazioneDirettivaEuropea = " Omologazione Direttiva EU",
    altraAlimentazione = " Altra alimentazione",
    idClasseAmbientale = "Classe ambientale",
    caratteristicheParticolari = "Caratteristiche particolari",
    idClasseVeicolo = "Classe veicolo",
    lunghezza = "Lunghezza (mt.)",
    flgDuePiani = "Due Piani",
    flgSnodato = "Snodato",
    flgCabinaGuidaIsolata = "Cabina Guida Isolata",
    numeroPorte = "Porte",
    nPostiInPiedi = "Posti in Piedi",
    nPostiSedere = "Posti a Sedere",
    nPostiRiservati = "Posti Riservati",
    postiCarrozzina = "Posti Carrozzina",
    daContribuire = "Da contribuire",


    // DOTAZIONI SPECIFICHE
    idDotazioneDisabili = "Facilitazioni Disabili",
    dispositiviPrevSelezionati = "Dispositivi Prevenzione Incidenti",
    idImpiantiAudio = "Impianti Audio",
    idImpiantiVisivi = "Impianti Visivi",
    altriDispositiviPrevenzInc = "Altri Dispositivi Prevenzione Incidenti",
    flgImpiantoCondizionamento = "Impianto Condizionamento",
    flgRilevatoreBip = "Sistema Bigliettazione elettronica (BIP)",
    predisposizioneCablaggio = "Predisposizione cablaggio",
    flgContapasseggeri = "Conta Passeggeri",
    bigliettazioneElettronica = "Integrato con bigliettazione elettronica",
    portabiciAutobus = "Portabici",
    sistemaVideosorveglianza = "Sistemi videosorveglianza a bordo",
    tipologiaAVM = "Tipologia",
    sistemaProtezioneAutista = "Sistema protezione autista (anche anti-Covid)",
    altriAllestimenti = "Altri Allestimenti",
    flgOtx = "OTX",
    flgAvm = "AVM",
    flgFiltroFap = "Filtro FAP",

    //DEPOSITO
    deposito = "Deposito",
    denominazioneDeposito = "Denominazione",
    depositoPrevalente = "Deposito Principale",
    indirizzoDeposito = "Indirizzo",
    telefonoDeposito = "Telefono",

    // DATI AMMINISTRATIVI ED ECONOMICI
    nuovaProceduraRendicontazione = "Ante nuova procedura di rendicontazione",
    prezzoTotAcquisto = "Prezzo totale d'acquisto (€)",
    idProprietaLeasing = "Proprietà/Leasing",
    entitaContrPub = "Entità Contributo Pubblico (€)",
    dataUltimaRevisione = "Data Ultima Revisione",
    tipologiaDimensionale = "Tipologia dimensionale autobus",
    flgVeicoloAssicurato = "Assicurato",
    flgConteggiatoMiv = "Conteggiato MIV",
    contribuito = "Contribuito",
    contributoPubblicoAcquisto = "Autobus già acquistato da contribuire",
    flgAlienato = "Alienato",
    dataAlienazione = "Data alienazione",
    dataScadVincoliNoAlien = "Scadenza vincoli non alienabilità",
    annoSostProg = "Anno Sostituzione Programmata",

    // VERIFICA E NOTE
    flagVerificaAzienda = "Verificato Azienda",
    notaRiservataAzienda = "Nota Azienda",
    flagVerificaAmp = "Verificato AMP",
    notaRiservataAmp = "Nota AMP",
    note = "Note",
    motivazione = "Motivazione",

    //MISUTRAZIONI
    emissione = "Monitoriaggio emissioni",
    portabici = "Monitoriaggio portabici",

    //CONTRIBUZIONE
    veicoloDaContribuire = "Veicolo da contribuire",
    /** Assegnazione delle risorse **/
    assegnazioneRisorseContributi = "Assegnazione delle risorse",
    fonteDiFinanziamento = "Fonte di finanziamento",
    attoAssegnazioneRisorse = "Atto assegnazione risorse",
    contributoPubblico = "Contributo pubblico erogato €",
    contributoStatale = "Contributo statale erogato €",
    contributoRegionale = "Contributo regionale aggiuntivo erogato €",
    /** Ordine di acquisto **/
    ordineAcquisto = "Ordine di acquisto",
    cupMaster = "CUP master",
    cup = "CUP",
    dataAggiudicazione = "Data aggiudicazione",
    dataStipula = "Data stipula",
    cig = "CIG",
    numeroOrdine = "Nr. Ordine",
    dataOrdine = "Data ordine",
    fornitore = "Fornitore",
    /** Voci di costo delle forniture **/
    vociCostoForniture = "Voci di costo delle forniture",
    documentazioneContrattuale = "Documentazione contrattuale",
    voceDiCosto = "Voce di costo",
    importoVoceCosto = "Importo (Iva esclusa)",
    importo = "Importo",
    costoCriteriRegionali = "Costo complessivo ammissibile ai sensi dei criteri regionali (€)",
    costoFonteFinanziamento = "Costo complessivo ammissibile ai sensi della fonte di finanziamento (€)",
    /** Documentazione di spesa, bonifici e di pagamento**/
    aziendaFornitore = "Azienda TPL - Fornitore",
    documentazioneSpesa = "Documentazione di spesa",
    numeroFattura = "Nr. Fattura",
    dataFattura = "Data fattura",
    fatture = "Fatture",
    oggettoFattura = "Oggetto Fattura",
    bonifici = "Bonifici",
    dataBonifico = "Data",
    oggettoBonifico = "Oggetto Bonifico",
    cro = "CRO",
    fatturaAcquisto="Fattura d'acquisto",
    documentoFattura = "Fattura d'acquisto in copia conforme all'originale",
    tipoDocumentoQuietanza = "Tipologia documento di quietanza",
    numeroQuietanza = "Nr. Quietanza di pagamento",
    dataQuietanza = "Data Quietanza di pagamento",
    docQuietanza = "Documento di quietanza",
    /** Contributi erogabili **/
    contributiErogabili = "Contributi erogabili (Anticipo)",
    regioneAMP = "Da Regione a AMP",
    AMPAzienda = "Da AMP ad Azienda TPL",
    contributoCriteriRegionali = "Contributo erogabile ai sensi dei criteri regionali (€)",
    contributoFonteFinanziamento = "Contributo erogabile ai sensi della fonte di finanziamento (€)",
    numeroAttoLiquidazione = "Nr. Atto di liquidazione",
    dataAttoLiquidazione = "Data Atto di liquidazione",
    numeroDetermina = "N. Determina",
    dataDetermina = "Data Determina",
    anticipo = "ANTICIPO (€)",
    saldo = "SALDO (€)",


    /** Messa in servizio, rispetto dei vincoli e all'assolvimento delle azioni di pubblicita' **/
    messaInServizio = "Messa in servizio, rispetto dei vincoli e all'assolvimento delle azioni di pubblicità",
    numeroCartaCircolazione = "Nr. Carta di Circolazione/DU",
    cartaCircolazioneDU = "Carta di Circolazione/DU",
    cartaCircolazione = "Carta di circolazione",
    attoObbligo = "Atto d'obbligo",
    garanzia = "Garanzia ex art. 16, comma 6 della L.R. 1/2000 e s.m.i.",
    presenzaPannelloPubblicita = "Presenza pannello pubblicità fonte finanziamento",
    veicoloDaSostituire = "Veicolo da sostiutuire",
    tipoSostituzione = "Tipo sostituzione",
    documentazioneAlienazione = "Documentazione alienazione",
    documentoMisureEmissioni = "Dichiarazione relativa misure emissioni",
    /** Stato della rendicontazione **/
    statoRendicontazione = "Stato della rendicontazione",
    rendicontataParteA = "Rendicontata Parte A",
    validataParteA = "Validata Parte A",
    rendicontataParteB = "Rendicontata Parte B",
    validataParteB = "Validata Parte B",
    notaAziendaRendicontazione = "Nota azienda sulla rendicontazione",
    notaRPAMPRendicontazione = "Nota RP/AMP sulla rendicontazione",

    /***************************************************
    *   PARTE B
    ****************************************************/

    contributiErogabiliSaldo = "Contributi erogabili (Saldo)",
    documentazionePagamento = "Documentazione di pagamento (quietanza)",
}