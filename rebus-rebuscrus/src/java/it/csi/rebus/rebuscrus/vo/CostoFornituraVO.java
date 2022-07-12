/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class CostoFornituraVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idCostoFornitura;
	private Long idDocContribuzione;
	private Double costoAmmissibileReg;
	private Double costoAmmissibileFf;
	private Long idUtenteAggiornamento;
	private Date dataAggiornamento;

	public Long getIdCostoFornitura() {
		return idCostoFornitura;
	}

	public void setIdCostoFornitura(Long idCostoFornitura) {
		this.idCostoFornitura = idCostoFornitura;
	}

	public Long getIdDocContribuzione() {
		return idDocContribuzione;
	}

	public void setIdDocContribuzione(Long idDocContribuzione) {
		this.idDocContribuzione = idDocContribuzione;
	}

	public Double getCostoAmmissibileReg() {
		return costoAmmissibileReg;
	}

	public void setCostoAmmissibileReg(Double costoAmmissibileReg) {
		this.costoAmmissibileReg = costoAmmissibileReg;
	}

	public Double getCostoAmmissibileFf() {
		return costoAmmissibileFf;
	}

	public void setCostoAmmissibileFf(Double costoAmmissibileFf) {
		this.costoAmmissibileFf = costoAmmissibileFf;
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
