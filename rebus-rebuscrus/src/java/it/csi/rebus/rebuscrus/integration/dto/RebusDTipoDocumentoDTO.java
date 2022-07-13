/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;

public class RebusDTipoDocumentoDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_documento.id_tipo_documento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	private Long idTipoDocumento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_documento.descrizione
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	private String descrizione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_documento.desc_estesa
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	private String descEstesa;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_documento.id_contesto
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	private Long idContesto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_tipo_documento.ordinamento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	private Long ordinamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_d_tipo_documento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_documento.id_tipo_documento
	 * @return  the value of rebus_d_tipo_documento.id_tipo_documento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_documento.id_tipo_documento
	 * @param idTipoDocumento  the value for rebus_d_tipo_documento.id_tipo_documento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_documento.descrizione
	 * @return  the value of rebus_d_tipo_documento.descrizione
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_documento.descrizione
	 * @param descrizione  the value for rebus_d_tipo_documento.descrizione
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione == null ? null : descrizione.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_documento.desc_estesa
	 * @return  the value of rebus_d_tipo_documento.desc_estesa
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public String getDescEstesa() {
		return descEstesa;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_documento.desc_estesa
	 * @param descEstesa  the value for rebus_d_tipo_documento.desc_estesa
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public void setDescEstesa(String descEstesa) {
		this.descEstesa = descEstesa == null ? null : descEstesa.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_documento.id_contesto
	 * @return  the value of rebus_d_tipo_documento.id_contesto
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public Long getIdContesto() {
		return idContesto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_documento.id_contesto
	 * @param idContesto  the value for rebus_d_tipo_documento.id_contesto
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public void setIdContesto(Long idContesto) {
		this.idContesto = idContesto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_tipo_documento.ordinamento
	 * @return  the value of rebus_d_tipo_documento.ordinamento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public Long getOrdinamento() {
		return ordinamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_tipo_documento.ordinamento
	 * @param ordinamento  the value for rebus_d_tipo_documento.ordinamento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_tipo_documento
	 * @mbg.generated  Tue Mar 02 19:50:12 CET 2021
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idTipoDocumento=").append(idTipoDocumento);
		sb.append(", descrizione=").append(descrizione);
		sb.append(", descEstesa=").append(descEstesa);
		sb.append(", idContesto=").append(idContesto);
		sb.append(", ordinamento=").append(ordinamento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}