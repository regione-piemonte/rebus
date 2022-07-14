/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class DatiBancariVO extends ParentVO {
	private Long id;
	private String iban;
	private String note;
	private Boolean doatpl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getDoatpl() {
		return doatpl;
	}

	public void setDoatpl(Boolean doatpl) {
		this.doatpl = doatpl;
	}

}
