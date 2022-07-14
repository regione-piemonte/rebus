/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ContrattoSoggettoVO extends ParentVO {
	
	private Long idSoggettoGiuridico;
	private Long idContratto;
	private String contratto;
	private String enteCommittente;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private String ruolo;
	private String capofila;
	private String soggettoAffidante;
	private String dataInizioaAttivita;
	private String dataFineAttivita;
	private Boolean scaduto;
	
	
	public Long getIdSoggettoGiuridico() {
		return idSoggettoGiuridico;
	}
	public void setIdSoggettoGiuridico(Long idSoggettoGiuridico) {
		this.idSoggettoGiuridico = idSoggettoGiuridico;
	}
	public Long getIdContratto() {
		return idContratto;
	}
	public void setIdContratto(Long idContratto) {
		this.idContratto = idContratto;
	}
	public String getContratto() {
		return contratto;
	}
	public void setContratto(String contratto) {
		this.contratto = contratto;
	}
	public String getEnteCommittente() {
		return enteCommittente;
	}
	public void setEnteCommittente(String enteCommittente) {
		this.enteCommittente = enteCommittente;
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
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getCapofila() {
		return capofila;
	}
	public void setCapofila(String capofila) {
		this.capofila = capofila;
	}
	public String getSoggettoAffidante() {
		return soggettoAffidante;
	}
	public void setSoggettoAffidante(String soggettoAffidante) {
		this.soggettoAffidante = soggettoAffidante;
	}
	public String getDataInizioaAttivita() {
		return dataInizioaAttivita;
	}
	public void setDataInizioaAttivita(String dataInizioaAttivita) {
		this.dataInizioaAttivita = dataInizioaAttivita;
	}
	public String getDataFineAttivita() {
		return dataFineAttivita;
	}
	public void setDataFineAttivita(String dataFineAttivita) {
		this.dataFineAttivita = dataFineAttivita;
	}
	public Boolean getScaduto() {
		return scaduto;
	}
	public void setScaduto(Boolean scaduto) {
		this.scaduto = scaduto;
	}
	
	
	
}
