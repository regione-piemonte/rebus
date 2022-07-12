/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class DepositoVO extends ParentVO implements Comparable<DepositoVO> {
	private Long id;
	private String denominazione;
	private Boolean isPrevalente;
	private String telefono;
	private String indirizzo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Boolean getIsPrevalente() {
		return isPrevalente;
	}

	public void setIsPrevalente(Boolean isPrevalente) {
		this.isPrevalente = isPrevalente;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Override
	public int compareTo(DepositoVO o) {
		return this.denominazione.compareTo(o.getDenominazione());
	}
}
