/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebuspDTipoProcedimentoDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_d_tipo_procedimento.id_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	private Long idTipoProcedimento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_d_tipo_procedimento.cod_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	private String codTipoProcedimento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_d_tipo_procedimento.desc_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	private String descTipoProcedimento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_d_tipo_procedimento.data_inizio_validita
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	private Date dataInizioValidita;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusp_d_tipo_procedimento.data_fine_validita
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	private Date dataFineValidita;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusp_d_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_d_tipo_procedimento.id_tipo_procedimento
	 * @return  the value of rebusp_d_tipo_procedimento.id_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public Long getIdTipoProcedimento() {
		return idTipoProcedimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_d_tipo_procedimento.id_tipo_procedimento
	 * @param idTipoProcedimento  the value for rebusp_d_tipo_procedimento.id_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public void setIdTipoProcedimento(Long idTipoProcedimento) {
		this.idTipoProcedimento = idTipoProcedimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_d_tipo_procedimento.cod_tipo_procedimento
	 * @return  the value of rebusp_d_tipo_procedimento.cod_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public String getCodTipoProcedimento() {
		return codTipoProcedimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_d_tipo_procedimento.cod_tipo_procedimento
	 * @param codTipoProcedimento  the value for rebusp_d_tipo_procedimento.cod_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public void setCodTipoProcedimento(String codTipoProcedimento) {
		this.codTipoProcedimento = codTipoProcedimento == null ? null : codTipoProcedimento.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_d_tipo_procedimento.desc_tipo_procedimento
	 * @return  the value of rebusp_d_tipo_procedimento.desc_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public String getDescTipoProcedimento() {
		return descTipoProcedimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_d_tipo_procedimento.desc_tipo_procedimento
	 * @param descTipoProcedimento  the value for rebusp_d_tipo_procedimento.desc_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public void setDescTipoProcedimento(String descTipoProcedimento) {
		this.descTipoProcedimento = descTipoProcedimento == null ? null : descTipoProcedimento.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_d_tipo_procedimento.data_inizio_validita
	 * @return  the value of rebusp_d_tipo_procedimento.data_inizio_validita
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_d_tipo_procedimento.data_inizio_validita
	 * @param dataInizioValidita  the value for rebusp_d_tipo_procedimento.data_inizio_validita
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusp_d_tipo_procedimento.data_fine_validita
	 * @return  the value of rebusp_d_tipo_procedimento.data_fine_validita
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusp_d_tipo_procedimento.data_fine_validita
	 * @param dataFineValidita  the value for rebusp_d_tipo_procedimento.data_fine_validita
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_tipo_procedimento
	 * @mbg.generated  Tue Apr 21 10:30:14 CEST 2020
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idTipoProcedimento=").append(idTipoProcedimento);
		sb.append(", codTipoProcedimento=").append(codTipoProcedimento);
		sb.append(", descTipoProcedimento=").append(descTipoProcedimento);
		sb.append(", dataInizioValidita=").append(dataInizioValidita);
		sb.append(", dataFineValidita=").append(dataFineValidita);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}