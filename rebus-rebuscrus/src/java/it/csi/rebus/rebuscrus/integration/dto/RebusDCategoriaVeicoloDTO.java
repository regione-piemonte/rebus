/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;

public class RebusDCategoriaVeicoloDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_categoria_veicolo.id_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private Long idCategoriaVeicolo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_categoria_veicolo.cod_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private String codCategoriaVeicolo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column rebus_d_categoria_veicolo.categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private String categoriaVeicolo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table rebus_d_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_categoria_veicolo.id_categoria_veicolo
	 * @return  the value of rebus_d_categoria_veicolo.id_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public Long getIdCategoriaVeicolo() {
		return idCategoriaVeicolo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_categoria_veicolo.id_categoria_veicolo
	 * @param idCategoriaVeicolo  the value for rebus_d_categoria_veicolo.id_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public void setIdCategoriaVeicolo(Long idCategoriaVeicolo) {
		this.idCategoriaVeicolo = idCategoriaVeicolo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_categoria_veicolo.cod_categoria_veicolo
	 * @return  the value of rebus_d_categoria_veicolo.cod_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public String getCodCategoriaVeicolo() {
		return codCategoriaVeicolo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_categoria_veicolo.cod_categoria_veicolo
	 * @param codCategoriaVeicolo  the value for rebus_d_categoria_veicolo.cod_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public void setCodCategoriaVeicolo(String codCategoriaVeicolo) {
		this.codCategoriaVeicolo = codCategoriaVeicolo == null ? null : codCategoriaVeicolo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column rebus_d_categoria_veicolo.categoria_veicolo
	 * @return  the value of rebus_d_categoria_veicolo.categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public String getCategoriaVeicolo() {
		return categoriaVeicolo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column rebus_d_categoria_veicolo.categoria_veicolo
	 * @param categoriaVeicolo  the value for rebus_d_categoria_veicolo.categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	public void setCategoriaVeicolo(String categoriaVeicolo) {
		this.categoriaVeicolo = categoriaVeicolo == null ? null : categoriaVeicolo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_categoria_veicolo
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", idCategoriaVeicolo=").append(idCategoriaVeicolo);
		sb.append(", codCategoriaVeicolo=").append(codCategoriaVeicolo);
		sb.append(", categoriaVeicolo=").append(categoriaVeicolo);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}