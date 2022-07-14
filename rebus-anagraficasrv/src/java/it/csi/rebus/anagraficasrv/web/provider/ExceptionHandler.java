/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import it.csi.rebus.anagraficasrv.common.exception.ErroreGestitoException;
import it.csi.rebus.anagraficasrv.common.exception.ModificaNonAbilitataException;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.vo.ExceptionVO;

@Provider
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {

	private static Logger logger = Logger.getLogger("Rebus.ExceptionHandler");
	private static final String MSG_ERROR = "Attenzione si è verificato un problema bloccante con il server";

	public Response toResponse(RuntimeException exception) {
		if (exception instanceof ErroreGestitoException) {
			return handleErroreGestitoException((ErroreGestitoException) exception);
		}
		if (exception instanceof UtenteNonAbilitatoException) {
			return handleUtenteNonAbilitatoException((UtenteNonAbilitatoException) exception);
		}
		if (exception instanceof ModificaNonAbilitataException) {
			return handleModificaNonAbilitataException((ModificaNonAbilitataException) exception);
		}

		return handleGenericException(exception);

	}

	protected Response handleErroreGestitoException(ErroreGestitoException exception) {
		ErroreGestitoException erroreGestitoException = new ErroreGestitoException(exception.getMessage(), exception.getCodice());
		return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(new ExceptionVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), erroreGestitoException)).build();
	}

	protected Response handleUtenteNonAbilitatoException(UtenteNonAbilitatoException exception) {
		return Response.status(HttpStatus.UNAUTHORIZED.value()).entity(new ExceptionVO(HttpStatus.UNAUTHORIZED.value(), exception)).build();
	}

	protected Response handleModificaNonAbilitataException(ModificaNonAbilitataException exception) {
		return Response.status(HttpStatus.UNAUTHORIZED.value()).entity(new ExceptionVO(HttpStatus.UNAUTHORIZED.value(), exception)).build();
	}

	protected Response handleGenericException(RuntimeException exception) {
		logger.error("ECCEZIONE NON GESTITA:", exception);
		RuntimeException runtimeException = new RuntimeException(MSG_ERROR);
		return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(new ExceptionVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), runtimeException)).build();
	}

}