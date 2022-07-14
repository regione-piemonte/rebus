/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class FiltroSoggettoVO extends ParentVO {

	private Long idTipoSoggGiurid;
	private String codOssNaz;
	private String descrizione;
	private String codFiscale;
	private String flagIncludiCessate;
	private String flagIncludiNonAttive;
	private String flagIncludiAttive;
	private Long idSoggettoGiuridico;

	public FiltroSoggettoVO() {
	}

	public FiltroSoggettoVO(Long idTipoSoggGiurid, String codOssNaz, String descrizione, String codFiscale, String flagIncludiCessate, String flagIncludiNonAttive, String flagIncludiAttive) {
		this.idTipoSoggGiurid = idTipoSoggGiurid;
		this.codOssNaz = (codOssNaz != null && codOssNaz.trim().length() > 0) ? codOssNaz : null;
		this.descrizione = (descrizione != null && descrizione.trim().length() > 0) ? descrizione : null;
		this.codFiscale = (codFiscale != null && codFiscale.trim().length() > 0) ? codFiscale : null;
		this.flagIncludiCessate = flagIncludiCessate;
		this.flagIncludiNonAttive = flagIncludiNonAttive;
		this.flagIncludiAttive = flagIncludiAttive;
	}

	public Long getIdTipoSoggGiurid() {
		return idTipoSoggGiurid;
	}

	public void setIdTipoSoggGiurid(Long idTipoSoggGiurid) {
		this.idTipoSoggGiurid = idTipoSoggGiurid;
	}

	public String getCodOssNaz() {
		return codOssNaz;
	}

	public void setCodOssNaz(String codOssNaz) {
		this.codOssNaz = codOssNaz;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getFlagIncludiCessate() {
		return flagIncludiCessate;
	}

	public void setFlagIncludiCessate(String flagIncludiCessate) {
		this.flagIncludiCessate = flagIncludiCessate;
	}

	public String getFlagIncludiNonAttive() {
		return flagIncludiNonAttive;
	}

	public void setFlagIncludiNonAttive(String flagIncludiNonAttive) {
		this.flagIncludiNonAttive = flagIncludiNonAttive;
	}

	public Long getIdSoggettoGiuridico() {
		return idSoggettoGiuridico;
	}

	public void setIdSoggettoGiuridico(Long idSoggettoGiuridico) {
		this.idSoggettoGiuridico = idSoggettoGiuridico;
	}

	public void prepareSQL() {
		if (this.codOssNaz != null && this.codOssNaz.trim().length() > 0) {
			this.codOssNaz = "%" + this.codOssNaz.trim() + "%";
		} else
			this.codOssNaz = null;

		if (this.descrizione != null && this.descrizione.trim().length() > 0) {
			this.descrizione = "%" + this.descrizione.trim() + "%";
		} else
			this.descrizione = null;

		if (this.codFiscale != null && this.codFiscale.trim().length() > 0) {
			this.codFiscale = "%" +  this.codFiscale.trim() + "%" ;
		} else
			this.codFiscale = null;
		if (this.flagIncludiCessate != null && this.flagIncludiCessate.length() == 0)
			this.flagIncludiCessate = null;
		if (this.flagIncludiNonAttive != null && this.flagIncludiNonAttive.length() == 0)
			this.flagIncludiNonAttive = null;
		if (this.flagIncludiAttive != null && this.flagIncludiAttive.length() == 0)
			this.flagIncludiAttive = null;

	}

	public String getFlagIncludiAttive() {
		return flagIncludiAttive;
	}

	public void setFlagIncludiAttive(String flagIncludiAttive) {
		this.flagIncludiAttive = flagIncludiAttive;
	}
}
