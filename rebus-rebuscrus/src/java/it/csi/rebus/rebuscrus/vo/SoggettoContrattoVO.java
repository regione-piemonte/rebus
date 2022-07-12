/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class SoggettoContrattoVO extends ParentVO {
	private Long id;
	private String codiceOss;
	private String denomBreve;

	public String getCodiceOss() {
		return codiceOss;
	}

	public void setCodiceOss(String codiceOss) {
		this.codiceOss = codiceOss;
	}

	public String getDenomBreve() {
		return denomBreve;
	}

	public void setDenomBreve(String denomBreve) {
		this.denomBreve = denomBreve;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
