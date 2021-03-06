/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dto;

import java.io.Serializable;

public class SirtplcDTipologiaServizioDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sirtplc_d_tipologia_servizio.id_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	private Long idTipologiaServizio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column sirtplc_d_tipologia_servizio.desc_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	private String descTipologiaServizio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sirtplc_d_tipologia_servizio.id_tipologia_servizio
	 * @return  the value of sirtplc_d_tipologia_servizio.id_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public Long getIdTipologiaServizio() {
		return idTipologiaServizio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sirtplc_d_tipologia_servizio.id_tipologia_servizio
	 * @param idTipologiaServizio  the value for sirtplc_d_tipologia_servizio.id_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void setIdTipologiaServizio(Long idTipologiaServizio) {
		this.idTipologiaServizio = idTipologiaServizio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column sirtplc_d_tipologia_servizio.desc_tipologia_servizio
	 * @return  the value of sirtplc_d_tipologia_servizio.desc_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public String getDescTipologiaServizio() {
		return descTipologiaServizio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column sirtplc_d_tipologia_servizio.desc_tipologia_servizio
	 * @param descTipologiaServizio  the value for sirtplc_d_tipologia_servizio.desc_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	public void setDescTipologiaServizio(String descTipologiaServizio) {
		this.descTipologiaServizio = descTipologiaServizio == null ? null : descTipologiaServizio.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idTipologiaServizio=").append(idTipologiaServizio);
		sb.append(", descTipologiaServizio=").append(descTipologiaServizio);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}