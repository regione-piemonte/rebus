/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;

public class RebusRDocVariazAutobusKey extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_r_doc_variaz_autobus.id_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Long idVariazAutobus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_r_doc_variaz_autobus.id_tipo_documento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Long idTipoDocumento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_r_doc_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_r_doc_variaz_autobus.id_variaz_autobus
	 * @return  the value of rebus_r_doc_variaz_autobus.id_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Long getIdVariazAutobus() {
		return idVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_r_doc_variaz_autobus.id_variaz_autobus
	 * @param idVariazAutobus  the value for rebus_r_doc_variaz_autobus.id_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setIdVariazAutobus(Long idVariazAutobus) {
		this.idVariazAutobus = idVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_r_doc_variaz_autobus.id_tipo_documento
	 * @return  the value of rebus_r_doc_variaz_autobus.id_tipo_documento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_r_doc_variaz_autobus.id_tipo_documento
	 * @param idTipoDocumento  the value for rebus_r_doc_variaz_autobus.id_tipo_documento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
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
		sb.append(", idVariazAutobus=").append(idVariazAutobus);
		sb.append(", idTipoDocumento=").append(idTipoDocumento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}