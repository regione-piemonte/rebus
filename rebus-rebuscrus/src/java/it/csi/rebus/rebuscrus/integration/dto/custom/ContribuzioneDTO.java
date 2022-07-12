/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto.custom;

public class ContribuzioneDTO {

	private Long idFonteFinanziamento;
	private Long idAttoAssegnazione;
	private String descBreve;
	private String descEstesa;

	public Long getIdFonteFinanziamento() {
		return idFonteFinanziamento;
	}

	public void setIdFonteFinanziamento(Long idFonteFinanziamento) {
		this.idFonteFinanziamento = idFonteFinanziamento;
	}

	public Long getIdAttoAssegnazione() {
		return idAttoAssegnazione;
	}

	public void setIdAttoAssegnazione(Long idAttoAssegnazione) {
		this.idAttoAssegnazione = idAttoAssegnazione;
	}

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

}
