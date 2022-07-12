/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.sql.Date;

@SuppressWarnings("serial")
public class FiltroProcedimentiVO extends ParentVO {
	
	private Long tipologia;
	private String progressivoRichiesta;
	private String richiedente;
	private Long stato;
	private Date dataStipulaDa;
	private Date dataStipulaA;
	private String flagStatoCorrente;
	private Long idAzienda;
	private Long idEnte;
	private Long idStatoIter;
	private String isAMP;
	private String isRegionePiemonte;
	private String isServizio;
	private String targa;
	
	
	
	public FiltroProcedimentiVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FiltroProcedimentiVO(Long tipologia,String progressivoRichiesta, String richiedente, Long stato, Date dataStipulaDa,
			Date dataStipulaA, String flagStatoCorrente,Long idAzienda, String targa) {
		super();
		this.tipologia = tipologia;
		this.progressivoRichiesta = (progressivoRichiesta != null && progressivoRichiesta.trim().length() > 0) ? progressivoRichiesta.trim() : null;
		this.richiedente = (richiedente != null && richiedente.trim().length() > 0) ? richiedente.trim() : null;
		this.stato = stato;
		this.dataStipulaDa = dataStipulaDa;
		this.dataStipulaA = dataStipulaA;
		this.flagStatoCorrente = flagStatoCorrente;
		this.idAzienda = idAzienda;
		this.targa=(targa != null && targa.trim().length() > 0) ? targa.trim() : null;
		
	}
	public Long getTipologia() {
		return tipologia;
	}
	public void setTipologia(Long tipologia) {
		this.tipologia = tipologia;
	}
	public String getProgressivoRichiesta() {
		return progressivoRichiesta;
	}
	public void setProgressivoRichiesta(String progressivoRichiesta) {
		
		this.progressivoRichiesta = (progressivoRichiesta != null && progressivoRichiesta.trim().length() > 0) ? progressivoRichiesta : null;

	}
	public String getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(String richiedente) {
	this.richiedente = (richiedente != null && richiedente.trim().length() > 0) ? richiedente : null;
	}
	public Long getStato() {
		return stato;
	}
	public void setStato(Long stato) {
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
	public String getFlagStatoCorrente() {
		return flagStatoCorrente;
	}
	public void setFlagStatoCorrente(String flagStatoCorrente) {
		this.flagStatoCorrente = flagStatoCorrente;
	}
	
	public Long getIdAzienda() {
		return idAzienda;
	}
	public void setIdAzienda(Long idAzienda) {
		this.idAzienda = idAzienda;
	}
	public Long getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(Long idEnte) {
		this.idEnte = idEnte;
	}
	public Long getIdStatoIter() {
		return idStatoIter;
	}
	public void setIdStatoIter(Long idStatoIter) {
		this.idStatoIter = idStatoIter;
	}
	public String getIsAMP() {
		return isAMP;
	}
	public void setIsAMP(String isAMP) {
		this.isAMP = isAMP;
	}
	
	public String getIsRegionePiemonte() {
		return isRegionePiemonte;
	}
	public void setIsRegionePiemonte(String isRegionePiemonte) {
		this.isRegionePiemonte = isRegionePiemonte;
	}
	public String getIsServizio() {
		return isServizio;
	}
	public void setIsServizio(String isServizio) {
		this.isServizio = isServizio;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = (targa != null && targa.trim().length() > 0) ? targa : null;
		
	}
	
	public void prepareSQL() {
		
		if (this.richiedente != null && this.richiedente.trim().length() > 0) {
			this.richiedente = "%" + this.richiedente.trim() + "%";
		};
		if (this.progressivoRichiesta != null && this.progressivoRichiesta.trim().length() > 0) {
			this.progressivoRichiesta = "%" + this.progressivoRichiesta.trim() + "%";
		};
		if (this.targa != null && this.targa.trim().length() > 0) {
			this.targa = "%" + this.targa.trim() + "%";
		};
		
	}



}
