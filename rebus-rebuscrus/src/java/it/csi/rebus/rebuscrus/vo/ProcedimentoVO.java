/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class ProcedimentoVO extends ParentVO {

	private Long idProcedimento;
	private String progressivoRichiesta;
	private Long idTipologia;
	private String tipologia;
	private String richiedente;
	private Long idStato;
	private String stato;
	private Date dataStipulaDa;
	private Date dataStipulaA;
	private String gestoreContratto;
	private Boolean isAbilitatoDettaglio;
	private Boolean isAbilitatoModifica;
	private Boolean isAbilitatoElimina;
	private Boolean isAbilitatoScaricaPDFAzienda;
	private Boolean isAbilitatoScaricaPDFEnte;

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	
	public String getProgressivoRichiesta() {
		return progressivoRichiesta;
	}

	public void setProgressivoRichiesta(String progressivoRichiesta) {
		this.progressivoRichiesta = progressivoRichiesta;
	}

	public Long getIdTipologia() {
		return idTipologia;
	}

	public void setIdTipologia(Long idTipologia) {
		this.idTipologia = idTipologia;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getDataStipulaDa() {
		return dataStipulaDa;
	}

	public void setDataStipulaDa(Date dataStipulaDa) {
		this.dataStipulaDa = dataStipulaDa;
	}

	public Date getDataStipulaA() {
		return dataStipulaA;
	}

	public void setDataStipulaA(Date dataStipulaA) {
		this.dataStipulaA = dataStipulaA;
	}

	public Boolean getIsAbilitatoDettaglio() {
		return isAbilitatoDettaglio;
	}

	public void setIsAbilitatoDettaglio(Boolean isAbilitatoDettaglio) {
		this.isAbilitatoDettaglio = isAbilitatoDettaglio;
	}

	public Boolean getIsAbilitatoModifica() {
		return isAbilitatoModifica;
	}

	public void setIsAbilitatoModifica(Boolean isAbilitatoModifica) {
		this.isAbilitatoModifica = isAbilitatoModifica;
	}

	public Boolean getIsAbilitatoElimina() {
		return isAbilitatoElimina;
	}

	public void setIsAbilitatoElimina(Boolean isAbilitatoElimina) {
		this.isAbilitatoElimina = isAbilitatoElimina;
	}

	public Boolean getIsAbilitatoScaricaPDFAzienda() {
		return isAbilitatoScaricaPDFAzienda;
	}

	public void setIsAbilitatoScaricaPDFAzienda(Boolean isAbilitatoScaricaPDFAzienda) {
		this.isAbilitatoScaricaPDFAzienda = isAbilitatoScaricaPDFAzienda;
	}

	public Boolean getIsAbilitatoScaricaPDFEnte() {
		return isAbilitatoScaricaPDFEnte;
	}

	public void setIsAbilitatoScaricaPDFEnte(Boolean isAbilitatoScaricaPDFEnte) {
		this.isAbilitatoScaricaPDFEnte = isAbilitatoScaricaPDFEnte;
	}

	public String getGestoreContratto() {
		return gestoreContratto;
	}

	public void setGestoreContratto(String gestoreContratto) {
		this.gestoreContratto = gestoreContratto;
	}

}
