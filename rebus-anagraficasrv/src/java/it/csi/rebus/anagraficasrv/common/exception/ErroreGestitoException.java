/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.common.exception;

public class ErroreGestitoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codice;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public ErroreGestitoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErroreGestitoException(String message, String codice) {
		super(message);
		this.codice = codice;
		// TODO Auto-generated constructor stub
	}

	public ErroreGestitoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ErroreGestitoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErroreGestitoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
