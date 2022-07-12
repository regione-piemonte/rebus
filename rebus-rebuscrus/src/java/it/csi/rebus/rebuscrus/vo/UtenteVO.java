/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

/**
 * @author 70525
 * @date 27 ott 2017
 * VO che gestisce gli i dati degli utenti 
 */
public class UtenteVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String codiceFiscale;
	private String cognome;
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "FunzionarioVO [id=" + id + ", codiceFiscale=" + codiceFiscale + ", cognome=" + cognome + ", nome=" + nome + "]";
	}

}
