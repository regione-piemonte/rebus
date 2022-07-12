/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class PortabiciAutobusVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Double idPortabici;
	private String descPortabici;
	private Date dataInizioValidita;
	private Date dataFineValidita;

	public Double getIdPortabici() {
		return idPortabici;
	}

	public void setIdPortabici(Double idPortabici) {
		this.idPortabici = idPortabici;
	}

	public String getDescPortabici() {
		return descPortabici;
	}

	public void setDescPortabici(String descPortabici) {
		this.descPortabici = descPortabici;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

}