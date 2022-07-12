/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class SistemaBigliettazioneVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Double idSistemaBigliettazione;
	private String descSistemaBigliettazione;
	private Date dataInizioValidita;
	private Date dataFineValidita;

	public Double getIdSistemaBigliettazione() {
		return idSistemaBigliettazione;
	}

	public void setIdSistemaBigliettazione(Double idSistemaBigliettazione) {
		this.idSistemaBigliettazione = idSistemaBigliettazione;
	}

	public String getDescSistemaBigliettazione() {
		return descSistemaBigliettazione;
	}

	public void setDescSistemaBigliettazione(String descSistemaBigliettazione) {
		this.descSistemaBigliettazione = descSistemaBigliettazione;
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