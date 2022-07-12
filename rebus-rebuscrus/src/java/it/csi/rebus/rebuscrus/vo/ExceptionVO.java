/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

public class ExceptionVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionVO(Integer status, String message, Exception exception) {
		super();
		this.status = status;
		this.message = message;
		this.exception = exception;
	}

	public ExceptionVO(Integer status, Exception exception) {
		super();
		this.status = status;
		this.message = exception.getMessage();
		this.exception = exception;
	}

	private Integer status;
	private String message;
	private Exception exception;
	
	public ExceptionVO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}
