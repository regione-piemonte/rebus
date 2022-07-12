/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class TipoSostituzioneVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date dataFineValidita;
	private Date dataInizioValidita;
	private String descTipoSostituzione;
	private Double idTipoSostituzione;

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

	public String getDescTipoSostituzione() {
		return descTipoSostituzione;
	}

	public void setDescTipoSostituzione(String descTipoSostituzione) {
		this.descTipoSostituzione = descTipoSostituzione;
	}

	public Double getIdTipoSostituzione() {
		return idTipoSostituzione;
	}

	public void setIdTipoSostituzione(Double idTipoSostituzione) {
		this.idTipoSostituzione = idTipoSostituzione;
	}

}
