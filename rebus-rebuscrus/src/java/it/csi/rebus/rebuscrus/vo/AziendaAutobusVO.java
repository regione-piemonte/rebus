/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class AziendaAutobusVO extends ParentVO {
	// VO utilizzato per il controllo degli accessi da url
	// query in commonDAO (getAziendaAutobusByIdVa)

	private static final long serialVersionUID = 1L;

	private String primoTelaio;
	private Long fkAzienda;
	private Date dataAggiornamento;
	private Date dataAlienazione;

	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio;
	}

	public Long getFkAzienda() {
		return fkAzienda;
	}

	public void setFkAzienda(Long fkAzienda) {
		this.fkAzienda = fkAzienda;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Date getDataAlienazione() {
		return dataAlienazione;
	}

	public void setDataAlienazione(Date dataAlienazione) {
		this.dataAlienazione = dataAlienazione;
	}

}