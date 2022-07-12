/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;

public class RebusDTipoMessaggioDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_messaggio.id_tipo_messaggio
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private Long idTipoMessaggio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_messaggio.descrizione
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private String descrizione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_messaggio.id_contesto
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private Long idContesto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_d_tipo_messaggio
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_messaggio.id_tipo_messaggio
	 * @return  the value of rebus_d_tipo_messaggio.id_tipo_messaggio
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public Long getIdTipoMessaggio() {
		return idTipoMessaggio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_messaggio.id_tipo_messaggio
	 * @param idTipoMessaggio  the value for rebus_d_tipo_messaggio.id_tipo_messaggio
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public void setIdTipoMessaggio(Long idTipoMessaggio) {
		this.idTipoMessaggio = idTipoMessaggio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_messaggio.descrizione
	 * @return  the value of rebus_d_tipo_messaggio.descrizione
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_messaggio.descrizione
	 * @param descrizione  the value for rebus_d_tipo_messaggio.descrizione
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione == null ? null : descrizione.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_messaggio.id_contesto
	 * @return  the value of rebus_d_tipo_messaggio.id_contesto
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public Long getIdContesto() {
		return idContesto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_messaggio.id_contesto
	 * @param idContesto  the value for rebus_d_tipo_messaggio.id_contesto
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public void setIdContesto(Long idContesto) {
		this.idContesto = idContesto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_tipo_messaggio
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idTipoMessaggio=").append(idTipoMessaggio);
		sb.append(", descrizione=").append(descrizione);
		sb.append(", idContesto=").append(idContesto);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}