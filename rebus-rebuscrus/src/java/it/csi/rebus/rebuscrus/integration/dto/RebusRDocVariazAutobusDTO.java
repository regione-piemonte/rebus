/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebusRDocVariazAutobusDTO extends RebusRDocVariazAutobusKey implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_r_doc_variaz_autobus.nome_file
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private String nomeFile;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_r_doc_variaz_autobus.data_caricamento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Date dataCaricamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_r_doc_variaz_autobus.note
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_r_doc_variaz_autobus.fk_utente
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Long fkUtente;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_r_doc_variaz_autobus.documento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private byte[] documento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_r_doc_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_r_doc_variaz_autobus.nome_file
	 * @return  the value of rebus_r_doc_variaz_autobus.nome_file
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_r_doc_variaz_autobus.nome_file
	 * @param nomeFile  the value for rebus_r_doc_variaz_autobus.nome_file
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile == null ? null : nomeFile.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_r_doc_variaz_autobus.data_caricamento
	 * @return  the value of rebus_r_doc_variaz_autobus.data_caricamento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_r_doc_variaz_autobus.data_caricamento
	 * @param dataCaricamento  the value for rebus_r_doc_variaz_autobus.data_caricamento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_r_doc_variaz_autobus.note
	 * @return  the value of rebus_r_doc_variaz_autobus.note
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_r_doc_variaz_autobus.note
	 * @param note  the value for rebus_r_doc_variaz_autobus.note
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_r_doc_variaz_autobus.fk_utente
	 * @return  the value of rebus_r_doc_variaz_autobus.fk_utente
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Long getFkUtente() {
		return fkUtente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_r_doc_variaz_autobus.fk_utente
	 * @param fkUtente  the value for rebus_r_doc_variaz_autobus.fk_utente
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setFkUtente(Long fkUtente) {
		this.fkUtente = fkUtente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_r_doc_variaz_autobus.documento
	 * @return  the value of rebus_r_doc_variaz_autobus.documento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public byte[] getDocumento() {
		return documento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_r_doc_variaz_autobus.documento
	 * @param documento  the value for rebus_r_doc_variaz_autobus.documento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_r_doc_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
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
		sb.append(", fkUtente=").append(fkUtente);
		sb.append(", documento=").append(documento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}