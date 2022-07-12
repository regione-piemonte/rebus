/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.List;

import it.csi.rebus.rebuscrus.security.AuthorizationRoles;

public interface SecurityService {

	List<AuthorizationRoles> getAutorizzazioniPerRuolo(String codRuolo);
	
	void verificaAutorizzazioni(String contesto, Long id, String action);
	
}