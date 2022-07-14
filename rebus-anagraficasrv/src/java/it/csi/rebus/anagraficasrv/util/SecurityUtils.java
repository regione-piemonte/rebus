/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.csi.rebus.anagraficasrv.common.Messages;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;

public class SecurityUtils {

	public static UserInfo getCurrentUserInfo() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		if (userInfo == null)
			throw new RuntimeException("UTENTE NON AUTENTICATO");
		return userInfo;
	}

	public static Long getCurrentIdFunzionario() {
		UserInfo ui = getCurrentUserInfo();
		if (ui == null)
			return null;
		return ui.getIdFunzionario();
	}

	/*
	 * verifica che un utente abbia una delle autorizzazioni passate
	 */
	public static void assertAutorizzazioni(AuthorizationRoles... roles) {
		Boolean autorizzato = Boolean.FALSE;
		for (AuthorizationRoles u : roles) {
			autorizzato = isAutorizzato(u);
			if (autorizzato)
				return;
		}
		throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_AUTORIZZATO);

	}

	/*
	 * verifica che un utente abbia tutte le autorizzazioni passate
	 */
	public static void assertMultipleAutorizzazioni(AuthorizationRoles... roles) {
		for (AuthorizationRoles u : roles) {
			if (!isAutorizzato(u))
				throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_AUTORIZZATO);
		}

	}

	public static boolean isAutorizzato(AuthorizationRoles autorizzazione) {
		UserInfo ui = getCurrentUserInfo();
		if (ui.getAuthority().contains(autorizzazione.getId()))
			return true;
		return false;
	}

	public static boolean isUtenteServizioOGestoreDati() {
		UserInfo ui = getCurrentUserInfo();
		if (ui.getCodFisc().equals("AAAAAA00B77B000F") || ui.getCodFisc().equals("AAAAAA00A11F000N")) {
			return true;
		}

		return false;

	}

}
