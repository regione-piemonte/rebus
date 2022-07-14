/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class SoggettoSubentroVO extends ParentVO {
	private Long id;
	private String denomBreve;
	private String denomSoggGiurid;
	private String denomAaep;
	private Long idTipoSoggGiurid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenomBreve() {
		return denomBreve;
	}

	public void setDenomBreve(String denomBreve) {
		this.denomBreve = denomBreve;
	}

	public String getDenomSoggGiurid() {
		return denomSoggGiurid;
	}

	public void setDenomSoggGiurid(String denomSoggGiurid) {
		this.denomSoggGiurid = denomSoggGiurid;
	}

	public String getDenomAaep() {
		return denomAaep;
	}

	public void setDenomAaep(String denomAaep) {
		this.denomAaep = denomAaep;
	}

	public Long getIdTipoSoggGiurid() {
		return idTipoSoggGiurid;
	}

	public void setIdTipoSoggGiurid(Long idTipoSoggGiurid) {
		this.idTipoSoggGiurid = idTipoSoggGiurid;
	}
}
