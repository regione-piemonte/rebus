/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class DocVariazAutobusVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idVariazAutobus;
	private Long idTipoDocumento;
	private String nomeFile;
	private Date dataCaricamento;
	private String note;
	private Long fkUtente;
	private String documento;
	private String descEstesa;

	public Long getIdVariazAutobus() {
		return idVariazAutobus;
	}

	public void setIdVariazAutobus(Long idVariazAutobus) {
		this.idVariazAutobus = idVariazAutobus;
	}

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getFkUtente() {
		return fkUtente;
	}

	public void setFkUtente(Long fkUtente) {
		this.fkUtente = fkUtente;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getDescEstesa() {
		return descEstesa;
	}

	public void setDescEstesa(String descEstesa) {
		this.descEstesa = descEstesa;
	}

}
