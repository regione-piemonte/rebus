/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class UtenteAzEnteVO extends ParentVO {
	
	 private Long idRUtenteAzEnte;

	  private Long fkUtente;

	  private Long fkAzienda;

	  private Long fkEnte;

	public Long getIdRUtenteAzEnte() {
		return idRUtenteAzEnte;
	}

	public void setIdRUtenteAzEnte(Long idRUtenteAzEnte) {
		this.idRUtenteAzEnte = idRUtenteAzEnte;
	}

	public Long getFkUtente() {
		return fkUtente;
	}

	public void setFkUtente(Long fkUtente) {
		this.fkUtente = fkUtente;
	}

	public Long getFkAzienda() {
		return fkAzienda;
	}

	public void setFkAzienda(Long fkAzienda) {
		this.fkAzienda = fkAzienda;
	}

	public Long getFkEnte() {
		return fkEnte;
	}

	public void setFkEnte(Long fkEnte) {
		this.fkEnte = fkEnte;
	}

}
