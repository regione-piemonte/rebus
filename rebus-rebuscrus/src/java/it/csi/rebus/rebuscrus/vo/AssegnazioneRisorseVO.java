/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class AssegnazioneRisorseVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idAssegnazioneRisorse;
	private Long idFonteFinanziamento;
	private Double contributoStatale;
	private Double contributoRegionaleAgg;
	private Long idUtenteAggiornamento;
	private Date dataAggiornamento;

	public Long getIdAssegnazioneRisorse() {
		return idAssegnazioneRisorse;
	}

	public void setIdAssegnazioneRisorse(Long idAssegnazioneRisorse) {
		this.idAssegnazioneRisorse = idAssegnazioneRisorse;
	}

	public Long getIdFonteFinanziamento() {
		return idFonteFinanziamento;
	}

	public void setIdFonteFinanziamento(Long idFonteFinanziamento) {
		this.idFonteFinanziamento = idFonteFinanziamento;
	}

	public Double getContributoStatale() {
		return contributoStatale;
	}

	public void setContributoStatale(Double contributoStatale) {
		this.contributoStatale = contributoStatale;
	}

	public Double getContributoRegionaleAgg() {
		return contributoRegionaleAgg;
	}

	public void setContributoRegionaleAgg(Double contributoRegionaleAgg) {
		this.contributoRegionaleAgg = contributoRegionaleAgg;
	}

	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

}
