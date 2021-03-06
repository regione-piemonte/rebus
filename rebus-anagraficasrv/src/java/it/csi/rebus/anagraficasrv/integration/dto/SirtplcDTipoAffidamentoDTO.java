/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dto;

import java.io.Serializable;

public class SirtplcDTipoAffidamentoDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sirtplc_d_tipo_affidamento.id_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	private Long idTipoAffidamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sirtplc_d_tipo_affidamento.desc_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	private String descTipoAffidamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtplc_d_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sirtplc_d_tipo_affidamento.id_tipo_affidamento
	 * @return  the value of sirtplc_d_tipo_affidamento.id_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public Long getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sirtplc_d_tipo_affidamento.id_tipo_affidamento
	 * @param idTipoAffidamento  the value for sirtplc_d_tipo_affidamento.id_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void setIdTipoAffidamento(Long idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sirtplc_d_tipo_affidamento.desc_tipo_affidamento
	 * @return  the value of sirtplc_d_tipo_affidamento.desc_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public String getDescTipoAffidamento() {
		return descTipoAffidamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sirtplc_d_tipo_affidamento.desc_tipo_affidamento
	 * @param descTipoAffidamento  the value for sirtplc_d_tipo_affidamento.desc_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void setDescTipoAffidamento(String descTipoAffidamento) {
		this.descTipoAffidamento = descTipoAffidamento == null ? null : descTipoAffidamento.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipo_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idTipoAffidamento=").append(idTipoAffidamento);
		sb.append(", descTipoAffidamento=").append(descTipoAffidamento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}