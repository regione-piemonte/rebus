/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class SoggettoRichiestaVO extends ParentVO {
	private Long idSogGiuridCommittente;
	private Long idSogGiuridEsecutore;
	private Long fkAzienda;
	
	public Long getIdSogGiuridCommittente() {
		return idSogGiuridCommittente;
	}
	public void setIdSogGiuridCommittente(Long idSogGiuridCommittente) {
		this.idSogGiuridCommittente = idSogGiuridCommittente;
	}
	public Long getIdSogGiuridEsecutore() {
		return idSogGiuridEsecutore;
	}
	public void setIdSogGiuridEsecutore(Long idSogGiuridEsecutore) {
		this.idSogGiuridEsecutore = idSogGiuridEsecutore;
	}
	public Long getFkAzienda() {
		return fkAzienda;
	}
	public void setFkAzienda(Long fkAzienda) {
		this.fkAzienda = fkAzienda;
	}

}
