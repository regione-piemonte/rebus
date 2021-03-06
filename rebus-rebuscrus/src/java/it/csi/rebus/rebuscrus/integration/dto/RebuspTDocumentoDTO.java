/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebuspTDocumentoDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_t_documento.id_documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	private Long idDocumento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_t_documento.descrizione
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	private String descrizione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_t_documento.nome_file
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	private String nomeFile;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_t_documento.data_inserimento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	private Date dataInserimento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_t_documento.id_utente_inserimento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	private Long idUtenteInserimento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_t_documento.documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	private byte[] documento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusp_t_documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_t_documento.id_documento
	 * @return  the value of rebusp_t_documento.id_documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public Long getIdDocumento() {
		return idDocumento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_t_documento.id_documento
	 * @param idDocumento  the value for rebusp_t_documento.id_documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_t_documento.descrizione
	 * @return  the value of rebusp_t_documento.descrizione
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_t_documento.descrizione
	 * @param descrizione  the value for rebusp_t_documento.descrizione
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione == null ? null : descrizione.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_t_documento.nome_file
	 * @return  the value of rebusp_t_documento.nome_file
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_t_documento.nome_file
	 * @param nomeFile  the value for rebusp_t_documento.nome_file
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile == null ? null : nomeFile.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_t_documento.data_inserimento
	 * @return  the value of rebusp_t_documento.data_inserimento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public Date getDataInserimento() {
		return dataInserimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_t_documento.data_inserimento
	 * @param dataInserimento  the value for rebusp_t_documento.data_inserimento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_t_documento.id_utente_inserimento
	 * @return  the value of rebusp_t_documento.id_utente_inserimento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public Long getIdUtenteInserimento() {
		return idUtenteInserimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_t_documento.id_utente_inserimento
	 * @param idUtenteInserimento  the value for rebusp_t_documento.id_utente_inserimento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public void setIdUtenteInserimento(Long idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_t_documento.documento
	 * @return  the value of rebusp_t_documento.documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public byte[] getDocumento() {
		return documento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_t_documento.documento
	 * @param documento  the value for rebusp_t_documento.documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_t_documento
	 * @mbg.generated  Tue Apr 14 11:06:44 CEST 2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idDocumento=").append(idDocumento);
		sb.append(", descrizione=").append(descrizione);
		sb.append(", nomeFile=").append(nomeFile);
		sb.append(", dataInserimento=").append(dataInserimento);
		sb.append(", idUtenteInserimento=").append(idUtenteInserimento);
		sb.append(", documento=").append(documento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}