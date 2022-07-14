/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.common.exception;

public class RebusException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RebusException() {
		// TODO Auto-generated constructor stub
	}

	public RebusException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RebusException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public RebusException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RebusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
