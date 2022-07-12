/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.common.exception;

/**
 * @author riccardo.bova
 * @date 22 nov 2017
 */
public class UtenteNonAbilitatoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3037763913146773820L;
	
	int code = 401;

	public UtenteNonAbilitatoException() {
		// TODO Auto-generated constructor stub
	}

	public UtenteNonAbilitatoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public UtenteNonAbilitatoException(String message, int code) {
		super(message);
		code = this.code;
		// TODO Auto-generated constructor stub
	}

	public UtenteNonAbilitatoException(Throwable cause) {
		super(cause); // TODO Auto-generated constructor stub
	}

	public UtenteNonAbilitatoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UtenteNonAbilitatoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
