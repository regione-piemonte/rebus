/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebuscDTipoDocQuietanzaDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_d_tipo_doc_quietanza.id_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private Double idTipoDocQuietanza;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_d_tipo_doc_quietanza.desc_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private String descTipoDocQuietanza;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_d_tipo_doc_quietanza.data_inizio_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private Date dataInizioValidita;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_d_tipo_doc_quietanza.data_fine_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private Date dataFineValidita;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_d_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_d_tipo_doc_quietanza.id_tipo_doc_quietanza
	 * @return  the value of rebusc_d_tipo_doc_quietanza.id_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Double getIdTipoDocQuietanza() {
		return idTipoDocQuietanza;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_d_tipo_doc_quietanza.id_tipo_doc_quietanza
	 * @param idTipoDocQuietanza  the value for rebusc_d_tipo_doc_quietanza.id_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setIdTipoDocQuietanza(Double idTipoDocQuietanza) {
		this.idTipoDocQuietanza = idTipoDocQuietanza;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_d_tipo_doc_quietanza.desc_tipo_doc_quietanza
	 * @return  the value of rebusc_d_tipo_doc_quietanza.desc_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public String getDescTipoDocQuietanza() {
		return descTipoDocQuietanza;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_d_tipo_doc_quietanza.desc_tipo_doc_quietanza
	 * @param descTipoDocQuietanza  the value for rebusc_d_tipo_doc_quietanza.desc_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDescTipoDocQuietanza(String descTipoDocQuietanza) {
		this.descTipoDocQuietanza = descTipoDocQuietanza == null ? null : descTipoDocQuietanza.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_d_tipo_doc_quietanza.data_inizio_validita
	 * @return  the value of rebusc_d_tipo_doc_quietanza.data_inizio_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_d_tipo_doc_quietanza.data_inizio_validita
	 * @param dataInizioValidita  the value for rebusc_d_tipo_doc_quietanza.data_inizio_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_d_tipo_doc_quietanza.data_fine_validita
	 * @return  the value of rebusc_d_tipo_doc_quietanza.data_fine_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_d_tipo_doc_quietanza.data_fine_validita
	 * @param dataFineValidita  the value for rebusc_d_tipo_doc_quietanza.data_fine_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_d_tipo_doc_quietanza
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idTipoDocQuietanza=").append(idTipoDocQuietanza);
		sb.append(", descTipoDocQuietanza=").append(descTipoDocQuietanza);
		sb.append(", dataInizioValidita=").append(dataInizioValidita);
		sb.append(", dataFineValidita=").append(dataFineValidita);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}