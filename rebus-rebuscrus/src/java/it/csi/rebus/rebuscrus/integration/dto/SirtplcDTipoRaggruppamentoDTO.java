/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;

public class SirtplcDTipoRaggruppamentoDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sirtplc_d_tipo_raggruppamento.id_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	private Long idTipoRaggruppamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sirtplc_d_tipo_raggruppamento.desc_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	private String descTipoRaggruppamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtplc_d_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sirtplc_d_tipo_raggruppamento.id_tipo_raggruppamento
	 * @return  the value of sirtplc_d_tipo_raggruppamento.id_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	public Long getIdTipoRaggruppamento() {
		return idTipoRaggruppamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sirtplc_d_tipo_raggruppamento.id_tipo_raggruppamento
	 * @param idTipoRaggruppamento  the value for sirtplc_d_tipo_raggruppamento.id_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	public void setIdTipoRaggruppamento(Long idTipoRaggruppamento) {
		this.idTipoRaggruppamento = idTipoRaggruppamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sirtplc_d_tipo_raggruppamento.desc_tipo_raggruppamento
	 * @return  the value of sirtplc_d_tipo_raggruppamento.desc_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	public String getDescTipoRaggruppamento() {
		return descTipoRaggruppamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sirtplc_d_tipo_raggruppamento.desc_tipo_raggruppamento
	 * @param descTipoRaggruppamento  the value for sirtplc_d_tipo_raggruppamento.desc_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	public void setDescTipoRaggruppamento(String descTipoRaggruppamento) {
		this.descTipoRaggruppamento = descTipoRaggruppamento == null ? null : descTipoRaggruppamento.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipo_raggruppamento
	 * @mbg.generated  Mon May 25 09:43:33 CEST 2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idTipoRaggruppamento=").append(idTipoRaggruppamento);
		sb.append(", descTipoRaggruppamento=").append(descTipoRaggruppamento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}