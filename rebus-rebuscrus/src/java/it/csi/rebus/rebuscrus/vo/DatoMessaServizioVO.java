/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class DatoMessaServizioVO extends ParentVO {

	private static final long serialVersionUID = 1L;
	
	private Long idDatoMessaServizio;
	private Double idTipoSostituzione;
	private String nrCartaCircolazione;
	private Boolean flgPannello;
	private Long idUtenteAggiornamento;
	private Date dataAggiornamento;
	private String primoTelaioSost;

	public Long getIdDatoMessaServizio() {
		return idDatoMessaServizio;
	}

	public void setIdDatoMessaServizio(Long idDatoMessaServizio) {
		this.idDatoMessaServizio = idDatoMessaServizio;
	}

	public Double getIdTipoSostituzione() {
		return idTipoSostituzione;
	}

	public void setIdTipoSostituzione(Double idTipoSostituzione) {
		this.idTipoSostituzione = idTipoSostituzione;
	}

	public String getNrCartaCircolazione() {
		return nrCartaCircolazione;
	}

	public void setNrCartaCircolazione(String nrCartaCircolazione) {
		this.nrCartaCircolazione = nrCartaCircolazione;
	}

	public Boolean getFlgPannello() {
		return flgPannello;
	}

	public void setFlgPannello(Boolean flgPannello) {
		this.flgPannello = flgPannello;
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

	public String getPrimoTelaioSost() {
		return primoTelaioSost;
	}

	public void setPrimoTelaioSost(String primoTelaioSost) {
		this.primoTelaioSost = primoTelaioSost;
	}

}
