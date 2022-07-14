/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class SoggettoEsecutoreVO extends ParentVO implements Comparable<SoggettoEsecutoreVO> {

	private Long id;
	private String denominazione; // denom soggetto giuridico
	private String denomBreve;
	private String denomAaep;
	private Long idTipoSoggettoEsecutore;
	private Long idRuolo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDenomBreve() {
		return denomBreve;
	}

	public void setDenomBreve(String denomBreve) {
		this.denomBreve = denomBreve;
	}

	public String getDenomAaep() {
		return denomAaep;
	}

	public void setDenomAaep(String denomAaep) {
		this.denomAaep = denomAaep;
	}

	public Long getIdTipoSoggettoEsecutore() {
		return idTipoSoggettoEsecutore;
	}

	public void setIdTipoSoggettoEsecutore(Long idTipoSoggettoEsecutore) {
		this.idTipoSoggettoEsecutore = idTipoSoggettoEsecutore;
	}

	@Override
	public int compareTo(SoggettoEsecutoreVO o) {
		return this.denominazione.toLowerCase().compareTo(o.getDenominazione().toLowerCase());
	}

	public Long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

}
