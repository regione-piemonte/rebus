/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class RebuscTCostoFornituraDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_costo_fornitura.id_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	private Long idCostoFornitura;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_costo_fornitura.id_doc_contribuzione
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	private Long idDocContribuzione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_costo_fornitura.costo_ammissibile_reg
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	private Double costoAmmissibileReg;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_costo_fornitura.costo_ammissibile_ff
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	private Double costoAmmissibileFf;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_costo_fornitura.id_utente_aggiornamento
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	private Long idUtenteAggiornamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebusc_t_costo_fornitura.data_aggiornamento
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	private Date dataAggiornamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_costo_fornitura.id_costo_fornitura
	 * @return  the value of rebusc_t_costo_fornitura.id_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Long getIdCostoFornitura() {
		return idCostoFornitura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_costo_fornitura.id_costo_fornitura
	 * @param idCostoFornitura  the value for rebusc_t_costo_fornitura.id_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setIdCostoFornitura(Long idCostoFornitura) {
		this.idCostoFornitura = idCostoFornitura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_costo_fornitura.id_doc_contribuzione
	 * @return  the value of rebusc_t_costo_fornitura.id_doc_contribuzione
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Long getIdDocContribuzione() {
		return idDocContribuzione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_costo_fornitura.id_doc_contribuzione
	 * @param idDocContribuzione  the value for rebusc_t_costo_fornitura.id_doc_contribuzione
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setIdDocContribuzione(Long idDocContribuzione) {
		this.idDocContribuzione = idDocContribuzione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_costo_fornitura.costo_ammissibile_reg
	 * @return  the value of rebusc_t_costo_fornitura.costo_ammissibile_reg
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Double getCostoAmmissibileReg() {
		return costoAmmissibileReg;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_costo_fornitura.costo_ammissibile_reg
	 * @param costoAmmissibileReg  the value for rebusc_t_costo_fornitura.costo_ammissibile_reg
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setCostoAmmissibileReg(Double costoAmmissibileReg) {
		this.costoAmmissibileReg = costoAmmissibileReg;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_costo_fornitura.costo_ammissibile_ff
	 * @return  the value of rebusc_t_costo_fornitura.costo_ammissibile_ff
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Double getCostoAmmissibileFf() {
		return costoAmmissibileFf;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_costo_fornitura.costo_ammissibile_ff
	 * @param costoAmmissibileFf  the value for rebusc_t_costo_fornitura.costo_ammissibile_ff
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setCostoAmmissibileFf(Double costoAmmissibileFf) {
		this.costoAmmissibileFf = costoAmmissibileFf;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_costo_fornitura.id_utente_aggiornamento
	 * @return  the value of rebusc_t_costo_fornitura.id_utente_aggiornamento
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Long getIdUtenteAggiornamento() {
		return idUtenteAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_costo_fornitura.id_utente_aggiornamento
	 * @param idUtenteAggiornamento  the value for rebusc_t_costo_fornitura.id_utente_aggiornamento
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
		this.idUtenteAggiornamento = idUtenteAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebusc_t_costo_fornitura.data_aggiornamento
	 * @return  the value of rebusc_t_costo_fornitura.data_aggiornamento
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebusc_t_costo_fornitura.data_aggiornamento
	 * @param dataAggiornamento  the value for rebusc_t_costo_fornitura.data_aggiornamento
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_costo_fornitura
	 * @mbg.generated  Thu Dec 16 16:16:59 CET 2021
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idCostoFornitura=").append(idCostoFornitura);
		sb.append(", idDocContribuzione=").append(idDocContribuzione);
		sb.append(", costoAmmissibileReg=").append(costoAmmissibileReg);
		sb.append(", costoAmmissibileFf=").append(costoAmmissibileFf);
		sb.append(", idUtenteAggiornamento=").append(idUtenteAggiornamento);
		sb.append(", dataAggiornamento=").append(dataAggiornamento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}