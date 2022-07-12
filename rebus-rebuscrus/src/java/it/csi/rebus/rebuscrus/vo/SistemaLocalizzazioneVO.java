/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class SistemaLocalizzazioneVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Double idSistemaLocalizzazione;
	private String descSistemaLocalizzazione;
	private Date dataInizioValidita;
	private Date dataFineValidita;

	public Double getIdSistemaLocalizzazione() {
		return idSistemaLocalizzazione;
	}

	public void setIdSistemaLocalizzazione(Double idSistemaLocalizzazione) {
		this.idSistemaLocalizzazione = idSistemaLocalizzazione;
	}

	public String getDescSistemaLocalizzazione() {
		return descSistemaLocalizzazione;
	}

	public void setDescSistemaLocalizzazione(String descSistemaLocalizzazione) {
		this.descSistemaLocalizzazione = descSistemaLocalizzazione;
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