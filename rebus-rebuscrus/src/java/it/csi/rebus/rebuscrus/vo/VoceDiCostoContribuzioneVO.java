/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class VoceDiCostoContribuzioneVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dataFineValidita;
	private Date dataInizioValidita;
	private String descVoceCosto;
	private Long idVoceCosto;

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public String getDescVoceCosto() {
		return descVoceCosto;
	}

	public Long getIdVoceCosto() {
		return idVoceCosto;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public void setDescVoceCosto(String descVoceCosto) {
		this.descVoceCosto = descVoceCosto;
	}

	public void setIdVoceCosto(Long idVoceCosto) {
		this.idVoceCosto = idVoceCosto;
	}

}
