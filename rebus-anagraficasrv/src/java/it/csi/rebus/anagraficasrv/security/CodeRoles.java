/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.security;

public enum CodeRoles {
	RUOLO_AZIENDA("AZIENDA_REBUS"),
	RUOLO_PILOTA_AZIENDA("AZIENDA_PILOTA_REBUS"),
	RUOLO_REGIONE("REGIONE_REBUS"),
	RUOLO_SERVIZIO("SERVIZIO_REBUS"),
	RUOLO_ENTE("ENTE_REBUS"),
	RUOLO_AMP("AMP");
	
	
	
	private String id;

	private CodeRoles(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
