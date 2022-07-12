/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class SirtplRUtenteSogGiuridDTO extends ParentDTO implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_r_utente_sog_giurid.id_utente_sog_giurid
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    private Long idUtenteSogGiurid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_r_utente_sog_giurid.id_utente
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    private Long idUtente;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_r_utente_sog_giurid.id_soggetto_giuridico
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    private Long idSoggettoGiuridico;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_r_utente_sog_giurid.data_inizio_validita
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    private Date dataInizioValidita;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_r_utente_sog_giurid.data_fine_validita
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    private Date dataFineValidita;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sirtpl_r_utente_sog_giurid
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_r_utente_sog_giurid.id_utente_sog_giurid
     *
     * @return the value of sirtpl_r_utente_sog_giurid.id_utente_sog_giurid
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public Long getIdUtenteSogGiurid() {
        return idUtenteSogGiurid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_r_utente_sog_giurid.id_utente_sog_giurid
     *
     * @param idUtenteSogGiurid the value for sirtpl_r_utente_sog_giurid.id_utente_sog_giurid
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public void setIdUtenteSogGiurid(Long idUtenteSogGiurid) {
        this.idUtenteSogGiurid = idUtenteSogGiurid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_r_utente_sog_giurid.id_utente
     *
     * @return the value of sirtpl_r_utente_sog_giurid.id_utente
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public Long getIdUtente() {
        return idUtente;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_r_utente_sog_giurid.id_utente
     *
     * @param idUtente the value for sirtpl_r_utente_sog_giurid.id_utente
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_r_utente_sog_giurid.id_soggetto_giuridico
     *
     * @return the value of sirtpl_r_utente_sog_giurid.id_soggetto_giuridico
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public Long getIdSoggettoGiuridico() {
        return idSoggettoGiuridico;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_r_utente_sog_giurid.id_soggetto_giuridico
     *
     * @param idSoggettoGiuridico the value for sirtpl_r_utente_sog_giurid.id_soggetto_giuridico
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public void setIdSoggettoGiuridico(Long idSoggettoGiuridico) {
        this.idSoggettoGiuridico = idSoggettoGiuridico;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_r_utente_sog_giurid.data_inizio_validita
     *
     * @return the value of sirtpl_r_utente_sog_giurid.data_inizio_validita
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_r_utente_sog_giurid.data_inizio_validita
     *
     * @param dataInizioValidita the value for sirtpl_r_utente_sog_giurid.data_inizio_validita
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_r_utente_sog_giurid.data_fine_validita
     *
     * @return the value of sirtpl_r_utente_sog_giurid.data_fine_validita
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_r_utente_sog_giurid.data_fine_validita
     *
     * @param dataFineValidita the value for sirtpl_r_utente_sog_giurid.data_fine_validita
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtpl_r_utente_sog_giurid
     *
     * @mbg.generated Mon Feb 03 12:51:15 CET 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idUtenteSogGiurid=").append(idUtenteSogGiurid);
        sb.append(", idUtente=").append(idUtente);
        sb.append(", idSoggettoGiuridico=").append(idSoggettoGiuridico);
        sb.append(", dataInizioValidita=").append(dataInizioValidita);
        sb.append(", dataFineValidita=").append(dataFineValidita);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}