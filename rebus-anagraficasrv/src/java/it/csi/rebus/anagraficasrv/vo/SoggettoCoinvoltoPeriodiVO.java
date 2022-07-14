/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class SoggettoCoinvoltoPeriodiVO extends ParentVO {
	private Long idContratto;
	private String alias;
	private String ruolo;
	private String dataInizio;
	private String dataFine;
	private String soggettoAffidante;
	private String soggettoSostituito;
	private String atto;
	private Boolean scaduto;
	//private Long ord;
	private Long idSoggettoGiuridico;
	
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
	
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getSoggettoAffidante() {
		return soggettoAffidante;
	}
	public void setSoggettoAffidante(String soggettoAffidante) {
		this.soggettoAffidante = soggettoAffidante;
	}
	public String getSoggettoSostituito() {
		return soggettoSostituito;
	}
	public void setSoggettoSostituito(String soggettoSostituito) {
		this.soggettoSostituito = soggettoSostituito;
	}
	public String getAtto() {
		return atto;
	}
	public void setAtto(String atto) {
		this.atto = atto;
	}
	/*
	public Long getOrd() {
		return ord;
	}
	public void setOrd(Long ord) {
		this.ord = ord;
	}*/
	public Long getIdSoggettoGiuridico() {
		return idSoggettoGiuridico;
	}
	public void setIdSoggettoGiuridico(Long idSoggettoGiuridico) {
		this.idSoggettoGiuridico = idSoggettoGiuridico;
	}
	public Boolean getScaduto() {
		return scaduto;
	}
	public void setScaduto(Boolean scaduto) {
		this.scaduto = scaduto;
	}
	
	
	
}
