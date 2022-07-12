/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto.custom;

import java.util.Date;

import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;

public class AutobusDto extends RebusTVariazAutobusDTO{
	private Date dtPrimaImmatricolazione;
	private Date dataAlienazione;
	private String marca;
	private String modello;
	private Long fkDispositiviPrevenzInc;
	
	public Date getDtPrimaImmatricolazione() {
		return dtPrimaImmatricolazione;
	}
	public void setDtPrimaImmatricolazione(Date dtPrimaImmatricolazione) {
		this.dtPrimaImmatricolazione = dtPrimaImmatricolazione;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public Date getDataAlienazione() {
		return dataAlienazione;
	}
	public void setDataAlienazione(Date dataAlienazione) {
		this.dataAlienazione = dataAlienazione;
	}
	public Long getFkDispositiviPrevenzInc() {
		return fkDispositiviPrevenzInc;
	}
	public void setFkDispositiviPrevenzInc(Long fkDispositiviPrevenzInc) {
		this.fkDispositiviPrevenzInc = fkDispositiviPrevenzInc;
	}
}
