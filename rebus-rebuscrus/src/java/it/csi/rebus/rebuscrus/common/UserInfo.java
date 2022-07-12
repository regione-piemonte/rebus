/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.common;

public class UserInfo implements java.io.Serializable {

	private static final long serialVersionUID = -7999400417528768575L;

	private String nome;
	private java.lang.String cognome;
	private java.lang.String codFisc;
	private java.lang.String ente;
	private java.lang.String ruolo;
	private java.lang.String idIride;
	private java.lang.String codRuolo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public java.lang.String getCognome() {
		return cognome;
	}

	public void setCognome(java.lang.String cognome) {
		this.cognome = cognome;
	}

	public java.lang.String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(java.lang.String codFisc) {
		this.codFisc = codFisc;
	}

	public java.lang.String getEnte() {
		return ente;
	}

	public void setEnte(java.lang.String ente) {
		this.ente = ente;
	}

	public java.lang.String getRuolo() {
		return ruolo;
	}

	public void setRuolo(java.lang.String ruolo) {
		this.ruolo = ruolo;
	}

	public java.lang.String getIdIride() {
		return idIride;
	}

	public void setIdIride(java.lang.String idIride) {
		this.idIride = idIride;
	}

	public java.lang.String getCodRuolo() {
		return codRuolo;
	}

	public void setCodRuolo(java.lang.String codRuolo) {
		this.codRuolo = codRuolo;
	}

}