/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class SirtplaRSogGiuridDepositoDTO extends SirtplaRSogGiuridDepositoKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpla_r_sog_giurid_deposito.id_utente_aggiornamento
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    private Long idUtenteAggiornamento;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpla_r_sog_giurid_deposito.data_aggiornamento
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    private Date dataAggiornamento;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sirtpla_r_sog_giurid_deposito
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpla_r_sog_giurid_deposito.id_utente_aggiornamento
     *
     * @return the value of sirtpla_r_sog_giurid_deposito.id_utente_aggiornamento
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    public Long getIdUtenteAggiornamento() {
        return idUtenteAggiornamento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpla_r_sog_giurid_deposito.id_utente_aggiornamento
     *
     * @param idUtenteAggiornamento the value for sirtpla_r_sog_giurid_deposito.id_utente_aggiornamento
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    public void setIdUtenteAggiornamento(Long idUtenteAggiornamento) {
        this.idUtenteAggiornamento = idUtenteAggiornamento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpla_r_sog_giurid_deposito.data_aggiornamento
     *
     * @return the value of sirtpla_r_sog_giurid_deposito.data_aggiornamento
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    public Date getDataAggiornamento() {
        return dataAggiornamento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpla_r_sog_giurid_deposito.data_aggiornamento
     *
     * @param dataAggiornamento the value for sirtpla_r_sog_giurid_deposito.data_aggiornamento
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    public void setDataAggiornamento(Date dataAggiornamento) {
        this.dataAggiornamento = dataAggiornamento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtpla_r_sog_giurid_deposito
     *
     * @mbg.generated Tue Aug 25 12:39:41 CEST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idUtenteAggiornamento=").append(idUtenteAggiornamento);
        sb.append(", dataAggiornamento=").append(dataAggiornamento);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}