/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import it.csi.rebus.anagraficasrv.integration.iride.Ruolo;
import it.csi.rebus.anagraficasrv.security.UserInfo;

public interface UserService {

	UserInfo getDettaglioFunzionario(UserInfo user);

	UserInfo setIdAziendaUtente(Long id);
	
	UserInfo setRuoloUtente(Ruolo ruolo);

	String getCodiceRuoloUtente();

}