/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

public class FonteFinanziamentoVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idFonteFinanziamento;
	private Long idAttoAssegnazione;
	private String descBreve;
	private String descEstesa;


	public String getDescBreve() {
		return descBreve;
	}

	public void setDescBreve(String descBreve) {
		this.descBreve = descBreve;
	}

	public String getDescEstesa() {
		return descEstesa;
	}

	public void setDescEstesa(String descEstesa) {
		this.descEstesa = descEstesa;
	}

	public Long getIdAttoAssegnazione() {
		return idAttoAssegnazione;
	}

	public void setIdAttoAssegnazione(Long idAttoAssegnazione) {
		this.idAttoAssegnazione = idAttoAssegnazione;
	}

	public Long getIdFonteFinanziamento() {
		return idFonteFinanziamento;
	}

	public void setIdFonteFinanziamento(Long idFonteFinanziamento) {
		this.idFonteFinanziamento = idFonteFinanziamento;
	}

}
