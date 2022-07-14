/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.util;

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

	public final static String COMPONENT_NAME = "anagraficasrv";
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

	public final static String RADIOBUTTON = "radiobutton";
	public final static String CHECKBOX = "checkbox";
	public final static String TEXT = "text";
	public final static Long INDICE_DECODIFICA_ND = new Long(1);

	public final static Long ID_UTENTE_DESTINATARIO_MESSAGGIO_1 = new Long(1);
	public final static Long TIPO_MESSAGGIO_1 = new Long(1);
	public final static Long TIPO_MESSAGGIO_2 = new Long(2);

	public final static String ID_TIPO_SOG_GIURIDICO_ENTE = new String("E");
	public final static String ID_TIPO_SOG_GIURIDICO_AZIENDA = new String("A");
	public final static String ID_RUOLO_TIPO_SOG_GIURIDICO_ENTE_COMMITTENTE = new String("C");
	public final static String ID_RUOLO_TIPO_SOG_GIURIDICO_ENTE_ESECUTORE = new String("E");
	public final static Long ID_TIPO_SOG_GIURIDICO_ENTE_LONG = new Long(1);
	public final static Long ID_TIPO_SOG_GIURIDICO_AZIENDA_LONG = new Long(2);
	
	public final static String SOGGETTO = "soggetto";
	public final static String CONTRATTO = "contratto";
}
