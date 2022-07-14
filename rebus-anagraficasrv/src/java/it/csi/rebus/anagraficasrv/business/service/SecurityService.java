/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import java.util.List;

import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;

public interface SecurityService {

	List<AuthorizationRoles> getAutorizzazioniPerRuolo(String codRuolo);

	void verificaAutorizzazioni(String contesto, Long id);

}