/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.common;

public class Messages {

	// LOG
	public final static String AUDIT_LOG_MSG_PRESA_IN_CARICO = "Il funzionario %s ha preso in carico la domanda come: %s";
	public final static String AUDIT_LOG_OP_PRESA_IN_CARICO = "presa in carico";

	public final static String AUDIT_LOG_MSG_ESITO_AMMISSIBILITA = "Il funzionario %s ha impostato l'esito della domanda %s a %s come:%s";
	public final static String AUDIT_LOG_OP_ESITO_AMMISSIBILITA = "esito ammissibilita";

	public final static String AUDIT_LOG_OP_INIZIO_PRE_VALUTAZIONE = "inizio pre-valutazione";
	public final static String AUDIT_LOG_MSG_INIZIO_PRE_VALUTAZIONE = "Il funzionario %s ha iniziato la pre-valutazione della domanda %s come Istruttore amministrativo.";

	public final static String AUDIT_LOG_OP_RIAPERTURA_PRE_VALUTAZIONE = "riapertura pre-valutazione";
	public final static String AUDIT_LOG_MSG_RIAPERTURA_PRE_VALUTAZIONE = "Il funzionario %s ha riaperto la pre-valutazione della domanda %s come Istruttore amministrativo.";

	public final static String AUDIT_LOG_OP_CHIUSA_PRE_VALUTAZIONE = "chiusura pre-valutazione ";
	public final static String AUDIT_LOG_MSG_CHIUSA_PRE_VALUTAZIONE = "Il funzionario %s ha chiuso la pre-valutazione della domanda %s come Istruttore amministrativo.";

	public final static String AUDIT_LOG_OP_APERTURA_VALUTAZIONE = "apertura valutazione";
	public final static String AUDIT_LOG_MSG_APERTURA_VALUTAZIONE = "Il funzionario %s ha iniziato la valutazione della domanda %s in quanto Decisore";

	public final static String AUDIT_LOG_OP_CHIUSURA_VALUTAZIONE = "chiusura valutazione";
	public final static String AUDIT_LOG_MSG_CHIUSURA_VALUTAZIONE = "Il funzionario %s ha chiuso la valutazione della domanda %s come Decisore";

	// GESTIONE VALUTAZIONE
	public final static String VALUTAZIONE_DOMANDA_ESTERNA = "Valutazione gestita esternamente";
	public final static String VALUTAZIONE_DOMANDA_NON_PREVISTA = "Valutazione non prevista per il bando";
	public final static String VALUTAZIONE_DOMANDA_SPECIFICHE_NON_VALORIZZATE = "Non tutte le specifiche sono state compilate: ";

	public final static String PUNTEGGIO_MINIMO_NON_RAGGIUNTO = "Non e' stato raggiunto il punteggio minimo previsto dal bando.";
	public final static String SPECIFICHE_NON_COMPILATE = "Non tutte le specifiche sono state compilate.";
	public final static String PUNTEGGIO_MINIMO_CRITERIO_BLOCCANTE = "Non e' stato raggiunto il punteggio minimo per almeno un criterio bloccante.";

	public final static String UTENTE_NON_ABILITATO = "Il soggetto collegato non risulta correttamente censito nel sistema. Contattare l'assistenza";
	public final static String SOGGETTO_NON_ASSOCIATO_A_SETTORE = "Il soggetto collegato non risulta associato ad alcun settore organizzativo. Contattare l'assistenza";
	public final static String UTENTE_NON_ABILITATO_A_DOMANDA = "L'utente collegato non e' abilitato a visualizzare la domanda indicata";
	public final static String DOMANDA_INESISTENTE = "Il numero domanda indicato non esiste";

	public final static String INDEX_DOCUMENTO_NON_TROVATO = "Documento non trovato. Contattare l'assistenza.";
	public final static String INDEX_GENERIC_ERRROR = "Il sistema Index non risponde nei tempi o non e' disponibile.";
	
	//CONTRIBUZIONE 
	public final static String ERRORE_SALVATAGGIO_CONTRIBUZIONE = "Errore nel salvataggio della contribuzione. Contattare l'assistenza.";
	
	public final static String TRANSIZIONE_AUTOMA_STATO_2 = "Attenzione sono presenti campi del veicolo ancora da compilare";
	
	//COMBO
	public final static String ERRORE_CARICAMENTO_TIPO_ALIMENTAZIONE = "Errore nel caricamenti dei Tipi di Alimentazione";
	public final static String ERRORE_CARICAMENTO_PROPIETA_LEASING = "Errore nel caricamenti delle Propieta' Leasing";
	public final static String ERRORE_TIPO_DISPOSITIVI_PREVENZIONE = "Errore nel caricamenti dei Tipi Dispositivi Prevenzione";
	public final static String ERRORE_CARICAMENTO_CLASSE_VEICOLO = "Errore nel caricamenti delle Classi Veicolo";
	public final static String ERRORE_CARICAMENTO_TIPO_IMMATRICOLAZIONE = "Errore nel caricamenti dei Tipi di Immatricolazione";
	public final static String ERRORE_CARICAMENTO_TIPO_VIDEO = "Errore nel caricamenti dei Tipo Video";
	public final static String ERRORE_CARICAMENTO_TIPO_AUDIO = "Errore nel caricamenti dei Tipo Audio";
	public final static String ERRORE_CARICAMENTO_TIPO_FACILITAZIONI = "Errore nel caricamenti dei Tipi di Facilitazione";
	public final static String ERRORE_CARICAMENTO_TIPO_EURO = "Errore nel caricamenti dei Tipo Euro";
	public final static String ERRORE_CARICAMENTO_TIPO_ALLESTIMENTO = "Errore nel caricamenti dei Tipo Allestimento";
	public final static String ERRORE_CARICAMENTO_CATEGORIA_VEICOLO = "Errore nel caricamenti delle Categorie Veicolo";

	//AUTORIZZAZIONI
	public final static String UTENTE_NON_AUTORIZZATO = "Utente non abilitato all'operazione richiesta!";
	public final static String UTENTE_NON_AUTORIZZATO_MODIFICA = "Utente non abilitato alla modifica richiesta!";
	public final static String DATO_NON_TROVATO = "Dato non presente!";
	
}
