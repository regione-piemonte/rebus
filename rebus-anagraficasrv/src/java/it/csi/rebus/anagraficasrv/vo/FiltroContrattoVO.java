/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class FiltroContrattoVO extends ParentVO {
	private String codIdentificativo;
	private String numRepertorio;
	private String enteCommittente;
	private String soggEsecutore;
	private String descrizione;
	private Date dataStipulaDa;
	private Date dataStipulaA;
	private String flagIncludiCessate;
	private Long idAzienda;

	public FiltroContrattoVO() {
	}

	public FiltroContrattoVO(String codIdentificativo, String numRepertorio, String enteCommittente, String soggEsecutore,String descrizione,  Date dataStipulaDa, Date dataStipulaA, String flagIncludiCessate) {
		super();
		this.codIdentificativo = codIdentificativo;
		this.numRepertorio = numRepertorio;
		this.enteCommittente = enteCommittente;
		this.soggEsecutore = soggEsecutore;
		this.setDescrizione(descrizione);
		this.dataStipulaDa = dataStipulaDa;
		this.dataStipulaA = dataStipulaA;
		this.flagIncludiCessate = flagIncludiCessate;
	}

	public String getCodIdentificativo() {
		return codIdentificativo;
	}

	public void setCodIdentificativo(String codIdentificativo) {
		this.codIdentificativo = codIdentificativo;
	}

	public String getNumRepertorio() {
		return numRepertorio;
	}

	public void setNumRepertorio(String numRepertorio) {
		this.numRepertorio = numRepertorio;
	}

	public String getEnteCommittente() {
		return enteCommittente;
	}

	public void setEnteCommittente(String enteCommittente) {
		this.enteCommittente = enteCommittente;
	}

	public String getSoggEsecutore() {
		return soggEsecutore;
	}

	public void setSoggEsecutore(String soggEsecutore) {
		this.soggEsecutore = soggEsecutore;
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

	public String getFlagIncludiCessate() {
		return flagIncludiCessate;
	}

	public void setFlagIncludiCessate(String flagIncludiCessate) {
		this.flagIncludiCessate = flagIncludiCessate;
	}

	public void prepareSQL() {
		if (this.codIdentificativo != null && this.codIdentificativo.trim().length() > 0) {
			this.codIdentificativo = "%" + this.codIdentificativo.trim() + "%";
		} else
			this.codIdentificativo = null;
		
		if (this.numRepertorio != null && this.numRepertorio.trim().length() > 0) {
			this.numRepertorio = "%" + this.numRepertorio.trim() +"%";
		} else
			this.numRepertorio = null;
		
		if (this.enteCommittente != null && this.enteCommittente.trim().length() > 0) {
			this.enteCommittente = "%" + this.enteCommittente.trim() + "%";
		} else
			this.enteCommittente = null;
		
		if (this.soggEsecutore != null && this.soggEsecutore.trim().length() > 0) {
			this.soggEsecutore = "%" + this.soggEsecutore.trim() + "%";
		} else
			this.soggEsecutore = null;
		
		if (this.flagIncludiCessate != null && this.flagIncludiCessate.length() == 0)
			this.flagIncludiCessate = null;
		
		if (this.descrizione != null && this.descrizione.trim().length() > 0) {
			this.descrizione = "%" + this.descrizione.trim() + "%";
		} else
			this.descrizione = null;
	}

	public Long getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Long idAzienda) {
		this.idAzienda = idAzienda;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
