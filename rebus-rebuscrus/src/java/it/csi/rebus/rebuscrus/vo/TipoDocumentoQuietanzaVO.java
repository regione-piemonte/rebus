/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class TipoDocumentoQuietanzaVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date dataFineValidita;
	private Date dataInizioValidita;
	private String descTipoDocQuietanza;
	private Double idTipoDocQuietanza;

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public String getDescTipoDocQuietanza() {
		return descTipoDocQuietanza;
	}

	public Double getIdTipoDocQuietanza() {
		return idTipoDocQuietanza;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public void setDescTipoDocQuietanza(String descTipoDocQuietanza) {
		this.descTipoDocQuietanza = descTipoDocQuietanza;
	}

	public void setIdTipoDocQuietanza(Double idTipoDocQuietanza) {
		this.idTipoDocQuietanza = idTipoDocQuietanza;
	}

}
