/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import it.csi.rebus.rebuscrus.integration.iride.Ruolo;
import it.csi.rebus.rebuscrus.security.UserInfo;

public interface UserService {

	UserInfo getDettaglioFunzionario(UserInfo user);

	UserInfo setIdAziendaUtente(Long idAzienda);

	UserInfo setRuoloUtente(Ruolo ruolo);

}