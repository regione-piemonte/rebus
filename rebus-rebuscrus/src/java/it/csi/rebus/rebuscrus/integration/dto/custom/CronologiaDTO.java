/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto.custom;

import java.util.Date;

public class CronologiaDTO {

	private String primoTelaio;
	private String denominazioneBreve;
	private Date dataAlienazione;
	
	public String getPrimoTelaio() {
		return primoTelaio;
	}
	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio;
	}
	public String getDenominazioneBreve() {
		return denominazioneBreve;
	}
	public void setDenominazioneBreve(String denominazioneBreve) {
		this.denominazioneBreve = denominazioneBreve;
	}
	public Date getDataAlienazione() {
		return dataAlienazione;
	}
	public void setDataAlienazione(Date dataAlienazione) {
		this.dataAlienazione = dataAlienazione;
	}

}
