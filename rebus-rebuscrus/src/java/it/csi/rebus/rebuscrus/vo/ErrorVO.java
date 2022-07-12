/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

/**
 * @author 70525
 * @date 27 ott 2017
 * VO che gestisce gli i dati degli errori 
 * String message;
 */
public class ErrorVO extends ParentVO {

	private static final long serialVersionUID = 665124395919554374L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorVO [message=" + message + "]";
	}

}
