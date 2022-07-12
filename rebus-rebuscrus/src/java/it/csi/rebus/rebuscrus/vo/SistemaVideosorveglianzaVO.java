/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class SistemaVideosorveglianzaVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Double idSistemaVideosorveglianza;
	private String descSistemaVideosorveglianza;
	private Date dataInizioValidita;
	private Date dataFineValidita;

	public Double getIdSistemaVideosorveglianza() {
		return idSistemaVideosorveglianza;
	}

	public void setIdSistemaVideosorveglianza(Double idSistemaVideosorveglianza) {
		this.idSistemaVideosorveglianza = idSistemaVideosorveglianza;
	}

	public String getDescSistemaVideosorveglianza() {
		return descSistemaVideosorveglianza;
	}

	public void setDescSistemaVideosorveglianza(String descSistemaVideosorveglianza) {
		this.descSistemaVideosorveglianza = descSistemaVideosorveglianza;
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