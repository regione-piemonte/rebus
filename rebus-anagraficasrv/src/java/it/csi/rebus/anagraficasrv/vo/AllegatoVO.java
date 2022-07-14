/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;


@SuppressWarnings("serial")
public class AllegatoVO extends ParentVO {
	private Long idAllegato;
	private Long idTipoDocumento;
	private String descrizioneTipoDocumento;
	private String nomeFile;
	private String noteFile;
	private byte[] fileByte;
	private Date dataCaricamento;
	
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
	public String getNoteFile() {
		return noteFile;
	}
	public void setNoteFile(String noteFile) {
		this.noteFile = noteFile;
	}
	public byte[] getFileByte() {
		return fileByte;
	}
	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
	public String getDescrizioneTipoDocumento() {
		return descrizioneTipoDocumento;
	}
	public void setDescrizioneTipoDocumento(String descrizioneTipoDocumento) {
		this.descrizioneTipoDocumento = descrizioneTipoDocumento;
	}
	public Long getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	} 

}
