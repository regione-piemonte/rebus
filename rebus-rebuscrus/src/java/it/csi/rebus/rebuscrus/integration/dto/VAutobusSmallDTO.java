/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class VAutobusSmallDTO extends ParentDTO implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.data_aggiornamento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Date dataAggiornamento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.id_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Long idVariazAutobus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.primo_telaio
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private String primoTelaio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.fk_bando
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Long fkBando;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.marca
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private String marca;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.modello
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private String modello;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.contribuito
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private String contribuito;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.scad_vincoli_no_alien
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Date scadVincoliNoAlien;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.data_prima_immatricolazione
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Date dataPrimaImmatricolazione;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column v_autobus_small.fk_tipo_allestimento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private Long fkTipoAllestimento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table v_autobus_small
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.data_aggiornamento
	 * @return  the value of v_autobus_small.data_aggiornamento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.data_aggiornamento
	 * @param dataAggiornamento  the value for v_autobus_small.data_aggiornamento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.id_variaz_autobus
	 * @return  the value of v_autobus_small.id_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Long getIdVariazAutobus() {
		return idVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.id_variaz_autobus
	 * @param idVariazAutobus  the value for v_autobus_small.id_variaz_autobus
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setIdVariazAutobus(Long idVariazAutobus) {
		this.idVariazAutobus = idVariazAutobus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.primo_telaio
	 * @return  the value of v_autobus_small.primo_telaio
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public String getPrimoTelaio() {
		return primoTelaio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.primo_telaio
	 * @param primoTelaio  the value for v_autobus_small.primo_telaio
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio == null ? null : primoTelaio.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.fk_bando
	 * @return  the value of v_autobus_small.fk_bando
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Long getFkBando() {
		return fkBando;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.fk_bando
	 * @param fkBando  the value for v_autobus_small.fk_bando
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setFkBando(Long fkBando) {
		this.fkBando = fkBando;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.marca
	 * @return  the value of v_autobus_small.marca
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.marca
	 * @param marca  the value for v_autobus_small.marca
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setMarca(String marca) {
		this.marca = marca == null ? null : marca.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.modello
	 * @return  the value of v_autobus_small.modello
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public String getModello() {
		return modello;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.modello
	 * @param modello  the value for v_autobus_small.modello
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setModello(String modello) {
		this.modello = modello == null ? null : modello.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.contribuito
	 * @return  the value of v_autobus_small.contribuito
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public String getContribuito() {
		return contribuito;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.contribuito
	 * @param contribuito  the value for v_autobus_small.contribuito
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setContribuito(String contribuito) {
		this.contribuito = contribuito == null ? null : contribuito.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.scad_vincoli_no_alien
	 * @return  the value of v_autobus_small.scad_vincoli_no_alien
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Date getScadVincoliNoAlien() {
		return scadVincoliNoAlien;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.scad_vincoli_no_alien
	 * @param scadVincoliNoAlien  the value for v_autobus_small.scad_vincoli_no_alien
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setScadVincoliNoAlien(Date scadVincoliNoAlien) {
		this.scadVincoliNoAlien = scadVincoliNoAlien;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.data_prima_immatricolazione
	 * @return  the value of v_autobus_small.data_prima_immatricolazione
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Date getDataPrimaImmatricolazione() {
		return dataPrimaImmatricolazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.data_prima_immatricolazione
	 * @param dataPrimaImmatricolazione  the value for v_autobus_small.data_prima_immatricolazione
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setDataPrimaImmatricolazione(Date dataPrimaImmatricolazione) {
		this.dataPrimaImmatricolazione = dataPrimaImmatricolazione;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column v_autobus_small.fk_tipo_allestimento
	 * @return  the value of v_autobus_small.fk_tipo_allestimento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public Long getFkTipoAllestimento() {
		return fkTipoAllestimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column v_autobus_small.fk_tipo_allestimento
	 * @param fkTipoAllestimento  the value for v_autobus_small.fk_tipo_allestimento
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	public void setFkTipoAllestimento(Long fkTipoAllestimento) {
		this.fkTipoAllestimento = fkTipoAllestimento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus_small
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", dataAggiornamento=").append(dataAggiornamento);
		sb.append(", idVariazAutobus=").append(idVariazAutobus);
		sb.append(", primoTelaio=").append(primoTelaio);
		sb.append(", fkBando=").append(fkBando);
		sb.append(", marca=").append(marca);
		sb.append(", modello=").append(modello);
		sb.append(", contribuito=").append(contribuito);
		sb.append(", scadVincoliNoAlien=").append(scadVincoliNoAlien);
		sb.append(", dataPrimaImmatricolazione=").append(dataPrimaImmatricolazione);
		sb.append(", fkTipoAllestimento=").append(fkTipoAllestimento);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}