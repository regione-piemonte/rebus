/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebuscTAttoAssegnazioneDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_atto_assegnazione.id_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private Long idAttoAssegnazione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_atto_assegnazione.desc_breve
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private String descBreve;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_atto_assegnazione.desc_estesa
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private String descEstesa;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_atto_assegnazione.data_inizio_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private Date dataInizioValidita;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_atto_assegnazione.data_fine_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private Date dataFineValidita;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_atto_assegnazione.id_atto_assegnazione
	 * @return  the value of rebusc_t_atto_assegnazione.id_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Long getIdAttoAssegnazione() {
		return idAttoAssegnazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_atto_assegnazione.id_atto_assegnazione
	 * @param idAttoAssegnazione  the value for rebusc_t_atto_assegnazione.id_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setIdAttoAssegnazione(Long idAttoAssegnazione) {
		this.idAttoAssegnazione = idAttoAssegnazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_atto_assegnazione.desc_breve
	 * @return  the value of rebusc_t_atto_assegnazione.desc_breve
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public String getDescBreve() {
		return descBreve;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_atto_assegnazione.desc_breve
	 * @param descBreve  the value for rebusc_t_atto_assegnazione.desc_breve
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDescBreve(String descBreve) {
		this.descBreve = descBreve == null ? null : descBreve.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_atto_assegnazione.desc_estesa
	 * @return  the value of rebusc_t_atto_assegnazione.desc_estesa
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public String getDescEstesa() {
		return descEstesa;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_atto_assegnazione.desc_estesa
	 * @param descEstesa  the value for rebusc_t_atto_assegnazione.desc_estesa
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDescEstesa(String descEstesa) {
		this.descEstesa = descEstesa == null ? null : descEstesa.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_atto_assegnazione.data_inizio_validita
	 * @return  the value of rebusc_t_atto_assegnazione.data_inizio_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_atto_assegnazione.data_inizio_validita
	 * @param dataInizioValidita  the value for rebusc_t_atto_assegnazione.data_inizio_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_atto_assegnazione.data_fine_validita
	 * @return  the value of rebusc_t_atto_assegnazione.data_fine_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_atto_assegnazione.data_fine_validita
	 * @param dataFineValidita  the value for rebusc_t_atto_assegnazione.data_fine_validita
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idAttoAssegnazione=").append(idAttoAssegnazione);
		sb.append(", descBreve=").append(descBreve);
		sb.append(", descEstesa=").append(descEstesa);
		sb.append(", dataInizioValidita=").append(dataInizioValidita);
		sb.append(", dataFineValidita=").append(dataFineValidita);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}