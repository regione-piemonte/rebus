/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class AttoAssegnazioneRisorseVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idAttoAssegnazione;
	private Date dataFineValidita;
	private Date dataInizioValidita;
	private String descBreve;
	private String descEstesa;

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
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

	public Long getIdAttoAssegnazione() {
		return idAttoAssegnazione;
	}

	public void setIdAttoAssegnazione(Long idAttoAssegnazione) {
		this.idAttoAssegnazione = idAttoAssegnazione;
	}

}
