/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.util;

public class Constants {

	private static java.util.ResourceBundle res;

	static {
		try {
			res = java.util.ResourceBundle.getBundle("/application");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static java.util.ResourceBundle getRes() {
		return res;
	}

	public final static String COMPONENT_NAME = "rebuscrus";
	public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";

	public final static String RUOLO_SERVIZIO = "SERVIZIO_REBUS";
	public final static String RUOLO_AMP = "AMP";
	public final static String RUOLO_REGIONE = "REGIONE_REBUS";
	public final static String RUOLO_ENTE = "ENTE_REBUS";
	public final static String RUOLO_AZIENDA = "AZIENDA_REBUS";
	public final static String RUOLO_MINISTERO = "CONSULTATORE_REBUS";
	public final static String RUOLO_MONITORAGGIO = "MONITORAGGIO";
	public final static String RUOLO_GESTORE_DATI = "GESTORE_DATI_REBUS";

	public final static String FLAG_SI = "S";
	public final static String FLAG_NO = "N";
	public final static String FLAG_UNDEFINED = "U";

	public final static String RADIOBUTTON = "radiobutton";
	public final static String CHECKBOX = "checkbox";
	public final static String TEXT = "text";
	public final static Long INDICE_DECODIFICA_ND = new Long(1);

	public final static Long ID_UTENTE_DESTINATARIO_MESSAGGIO_12 = new Long(12);
	public final static Long TIPO_MESSAGGIO_1 = Long.valueOf(1);
	public final static Long TIPO_MESSAGGIO_2 = Long.valueOf(2);
	public final static Long TIPO_MESSAGGIO_3 = Long.valueOf(3);
	public final static Long TIPO_MESSAGGIO_4 = Long.valueOf(4);
	public final static Long TIPO_MESSAGGIO_40 = Long.valueOf(40);
	public final static Long TIPO_MESSAGGIO_41 = Long.valueOf(41);
 
	public final static Long ID_STATO_ITER_PROC_IN_BOZZA = Long.valueOf(10);

	public final static Long IS_DOCUMENTO_WORD = Long.valueOf(1);
	public final static Long IS_DOCUMENTO_PDF = Long.valueOf(2);
	public final static Long IS_DOCUMENTO_TXT = Long.valueOf(3);
	
	//rebus_d_tipo_messaggio
	public final static Long TIPO_MESSAGGIO_20 = Long.valueOf(20); // Invio parte A
	public final static Long TIPO_MESSAGGIO_21 = Long.valueOf(21); // Invio parte B
	public final static Long TIPO_MESSAGGIO_22 = Long.valueOf(22); // Revisione A
	public final static Long TIPO_MESSAGGIO_23 = Long.valueOf(23); // Revisione B
	public final static Long TIPO_MESSAGGIO_24 = Long.valueOf(24); // Verificata A
	public final static Long TIPO_MESSAGGIO_25 = Long.valueOf(25); // Verificata B
	
	// contesto utilizzato per la verifica delle autorizzazioni
	public final static String AUTOBUS = "autobus";
	public final static String PROCEDIMENTI = "procedimenti";
	public final static String MESSAGGI = "messaggi";

}
