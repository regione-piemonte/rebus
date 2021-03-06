/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebuspRProcDocumentoDTO extends RebuspRProcDocumentoKey implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_r_proc_documento.nome_file
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	private String nomeFile;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_r_proc_documento.data_caricamento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	private Date dataCaricamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_r_proc_documento.note
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_r_proc_documento.id_utente_aggiornamento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	private Long idUtenteAggiornamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_r_proc_documento.documento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	private byte[] documento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusp_r_proc_documento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_r_proc_documento.nome_file
	 * @return  the value of rebusp_r_proc_documento.nome_file
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_r_proc_documento.nome_file
	 * @param nomeFile  the value for rebusp_r_proc_documento.nome_file
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile == null ? null : nomeFile.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_r_proc_documento.data_caricamento
	 * @return  the value of rebusp_r_proc_documento.data_caricamento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_r_proc_documento.data_caricamento
	 * @param dataCaricamento  the value for rebusp_r_proc_documento.data_caricamento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_r_proc_documento.note
	 * @return  the value of rebusp_r_proc_documento.note
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_r_proc_documento.note
	 * @param note  the value for rebusp_r_proc_documento.note
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_r_proc_documento.id_utente_aggiornamento
	 * @return  the value of rebusp_r_proc_documento.id_utente_aggiornamento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_r_proc_documento.id_utente_aggiornamento
	 * @param idUtenteAggiornamento  the value for rebusp_r_proc_documento.id_utente_aggiornamento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_r_proc_documento.documento
	 * @return  the value of rebusp_r_proc_documento.documento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public byte[] getDocumento() {
		return documento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_r_proc_documento.documento
	 * @param documento  the value for rebusp_r_proc_documento.documento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_r_proc_documento
	 * @mbg.generated  Tue Mar 31 12:22:34 CEST 2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", nomeFile=").append(nomeFile);
		sb.append(", dataCaricamento=").append(dataCaricamento);
		sb.append(", note=").append(note);
		sb.append(", idUtenteAggiornamento=").append(idUtenteAggiornamento);
		sb.append(", documento=").append(documento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}