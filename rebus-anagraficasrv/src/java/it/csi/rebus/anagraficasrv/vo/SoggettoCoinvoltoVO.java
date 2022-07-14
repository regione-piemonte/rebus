/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class SoggettoCoinvoltoVO extends ParentVO {
	private Long idContratto;
	private String alias;
	private String ruolo;
	private String perContoDi;
	private Long idSoggettoGiuridico;
	private Date dataInizio;
	private Date dataFine;
	
	public Long getIdContratto() {
		return idContratto;
	}
	public void setIdContratto(Long idContratto) {
		this.idContratto = idContratto;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getPerContoDi() {
		return perContoDi;
	}
	public void setPerContoDi(String perContoDi) {
		this.perContoDi = perContoDi;
	}
	public Long getIdSoggettoGiuridico() {
		return idSoggettoGiuridico;
	}
	public void setIdSoggettoGiuridico(Long idSoggettoGiuridico) {
		this.idSoggettoGiuridico = idSoggettoGiuridico;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	
}
