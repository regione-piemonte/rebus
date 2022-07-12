/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
//subaffidataria consorziata; usato in ProcedimentoDAO (query selectSogContr()) per pdf richieste
public class RichiestaPdfVO extends ParentVO {
	private Long idSostSogContrRaggr;
	private Long idContrattoRaggrupp;
	private Long idSoggettoGiuridico;
	private String attoSostituzione;
	private Long idTipoSostituzione;
	
	public Long getIdSostSogContrRaggr() {
		return idSostSogContrRaggr;
	}
	public void setIdSostSogContrRaggr(Long idSostSogContrRaggr) {
		this.idSostSogContrRaggr = idSostSogContrRaggr;
	}
	public Long getIdContrattoRaggrupp() {
		return idContrattoRaggrupp;
	}
	public void setIdContrattoRaggrupp(Long idContrattoRaggrupp) {
		this.idContrattoRaggrupp = idContrattoRaggrupp;
	}
	public Long getIdSoggettoGiuridico() {
		return idSoggettoGiuridico;
	}
	public void setIdSoggettoGiuridico(Long idSoggettoGiuridico) {
		this.idSoggettoGiuridico = idSoggettoGiuridico;
	}
	public String getAttoSostituzione() {
		return attoSostituzione;
	}
	public void setAttoSostituzione(String attoSostituzione) {
		this.attoSostituzione = attoSostituzione;
	}
	public Long getIdTipoSostituzione() {
		return idTipoSostituzione;
	}
	public void setIdTipoSostituzione(Long idTipoSostituzione) {
		this.idTipoSostituzione = idTipoSostituzione;
	}
}


