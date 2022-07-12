/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.common.exception;

import org.springframework.http.HttpStatus;

public class ModificaNonAbilitataException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpStatus status;

	public ModificaNonAbilitataException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ModificaNonAbilitataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ModificaNonAbilitataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ModificaNonAbilitataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ModificaNonAbilitataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
