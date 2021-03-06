/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebuscTDatoBonificoDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_dato_bonifico.id_dato_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	private Long idDatoBonifico;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_dato_bonifico.data_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	private Date dataBonifico;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_dato_bonifico.importo_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	private Double importoBonifico;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_dato_bonifico.cro
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	private String cro;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_dato_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_dato_bonifico.id_dato_bonifico
	 * @return  the value of rebusc_t_dato_bonifico.id_dato_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public Long getIdDatoBonifico() {
		return idDatoBonifico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_dato_bonifico.id_dato_bonifico
	 * @param idDatoBonifico  the value for rebusc_t_dato_bonifico.id_dato_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public void setIdDatoBonifico(Long idDatoBonifico) {
		this.idDatoBonifico = idDatoBonifico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_dato_bonifico.data_bonifico
	 * @return  the value of rebusc_t_dato_bonifico.data_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public Date getDataBonifico() {
		return dataBonifico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_dato_bonifico.data_bonifico
	 * @param dataBonifico  the value for rebusc_t_dato_bonifico.data_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public void setDataBonifico(Date dataBonifico) {
		this.dataBonifico = dataBonifico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_dato_bonifico.importo_bonifico
	 * @return  the value of rebusc_t_dato_bonifico.importo_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public Double getImportoBonifico() {
		return importoBonifico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_dato_bonifico.importo_bonifico
	 * @param importoBonifico  the value for rebusc_t_dato_bonifico.importo_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public void setImportoBonifico(Double importoBonifico) {
		this.importoBonifico = importoBonifico;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_dato_bonifico.cro
	 * @return  the value of rebusc_t_dato_bonifico.cro
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public String getCro() {
		return cro;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_dato_bonifico.cro
	 * @param cro  the value for rebusc_t_dato_bonifico.cro
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	public void setCro(String cro) {
		this.cro = cro == null ? null : cro.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_bonifico
	 * @mbg.generated  Fri Mar 04 15:16:01 CET 2022
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idDatoBonifico=").append(idDatoBonifico);
		sb.append(", dataBonifico=").append(dataBonifico);
		sb.append(", importoBonifico=").append(importoBonifico);
		sb.append(", cro=").append(cro);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}