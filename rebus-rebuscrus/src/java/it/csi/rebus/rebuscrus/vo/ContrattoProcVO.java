/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class ContrattoProcVO extends ParentVO {
	private Long idContratto;
	private Long idProcContratto;
	private Long idProcedimento;
	private String codIdNazionale;
	private String descrizione;
	private Long idTipoContratto;

	public Long getIdContratto() {
		return idContratto;
	}

	public void setIdContratto(Long idContratto) {
		this.idContratto = idContratto;
	}

	public Long getIdProcContratto() {
		return idProcContratto;
	}

	public void setIdProcContratto(Long idProcContratto) {
		this.idProcContratto = idProcContratto;
	}

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public String getCodIdNazionale() {
		return codIdNazionale;
	}

	public void setCodIdNazionale(String codIdNazionale) {
		this.codIdNazionale = codIdNazionale;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getIdTipoContratto() {
		return idTipoContratto;
	}

	public void setIdTipoContratto(Long idTipoContratto) {
		this.idTipoContratto = idTipoContratto;
	}
}
