/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class SirtplDComuneDTO extends ParentDTO implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.id_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private Long idComune;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.cod_istat_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private String codIstatComune;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.cod_belfiore_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private String codBelfioreComune;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.denom_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private String denomComune;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.id_provincia
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private Long idProvincia;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.data_inizio_validita
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private Date dataInizioValidita;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.data_fine_validita
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private Date dataFineValidita;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.dt_id_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private Double dtIdComune;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.dt_id_comune_prev
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private Double dtIdComunePrev;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtpl_d_comune.dt_id_comune_next
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private Double dtIdComuneNext;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sirtpl_d_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.id_comune
     *
     * @return the value of sirtpl_d_comune.id_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public Long getIdComune() {
        return idComune;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.id_comune
     *
     * @param idComune the value for sirtpl_d_comune.id_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.cod_istat_comune
     *
     * @return the value of sirtpl_d_comune.cod_istat_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public String getCodIstatComune() {
        return codIstatComune;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.cod_istat_comune
     *
     * @param codIstatComune the value for sirtpl_d_comune.cod_istat_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setCodIstatComune(String codIstatComune) {
        this.codIstatComune = codIstatComune == null ? null : codIstatComune.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.cod_belfiore_comune
     *
     * @return the value of sirtpl_d_comune.cod_belfiore_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public String getCodBelfioreComune() {
        return codBelfioreComune;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.cod_belfiore_comune
     *
     * @param codBelfioreComune the value for sirtpl_d_comune.cod_belfiore_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setCodBelfioreComune(String codBelfioreComune) {
        this.codBelfioreComune = codBelfioreComune == null ? null : codBelfioreComune.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.denom_comune
     *
     * @return the value of sirtpl_d_comune.denom_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public String getDenomComune() {
        return denomComune;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.denom_comune
     *
     * @param denomComune the value for sirtpl_d_comune.denom_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setDenomComune(String denomComune) {
        this.denomComune = denomComune == null ? null : denomComune.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.id_provincia
     *
     * @return the value of sirtpl_d_comune.id_provincia
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public Long getIdProvincia() {
        return idProvincia;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.id_provincia
     *
     * @param idProvincia the value for sirtpl_d_comune.id_provincia
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setIdProvincia(Long idProvincia) {
        this.idProvincia = idProvincia;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.data_inizio_validita
     *
     * @return the value of sirtpl_d_comune.data_inizio_validita
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.data_inizio_validita
     *
     * @param dataInizioValidita the value for sirtpl_d_comune.data_inizio_validita
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.data_fine_validita
     *
     * @return the value of sirtpl_d_comune.data_fine_validita
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.data_fine_validita
     *
     * @param dataFineValidita the value for sirtpl_d_comune.data_fine_validita
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.dt_id_comune
     *
     * @return the value of sirtpl_d_comune.dt_id_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public Double getDtIdComune() {
        return dtIdComune;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.dt_id_comune
     *
     * @param dtIdComune the value for sirtpl_d_comune.dt_id_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setDtIdComune(Double dtIdComune) {
        this.dtIdComune = dtIdComune;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.dt_id_comune_prev
     *
     * @return the value of sirtpl_d_comune.dt_id_comune_prev
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public Double getDtIdComunePrev() {
        return dtIdComunePrev;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.dt_id_comune_prev
     *
     * @param dtIdComunePrev the value for sirtpl_d_comune.dt_id_comune_prev
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setDtIdComunePrev(Double dtIdComunePrev) {
        this.dtIdComunePrev = dtIdComunePrev;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtpl_d_comune.dt_id_comune_next
     *
     * @return the value of sirtpl_d_comune.dt_id_comune_next
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public Double getDtIdComuneNext() {
        return dtIdComuneNext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtpl_d_comune.dt_id_comune_next
     *
     * @param dtIdComuneNext the value for sirtpl_d_comune.dt_id_comune_next
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    public void setDtIdComuneNext(Double dtIdComuneNext) {
        this.dtIdComuneNext = dtIdComuneNext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtpl_d_comune
     *
     * @mbg.generated Tue Mar 10 12:11:55 CET 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idComune=").append(idComune);
        sb.append(", codIstatComune=").append(codIstatComune);
        sb.append(", codBelfioreComune=").append(codBelfioreComune);
        sb.append(", denomComune=").append(denomComune);
        sb.append(", idProvincia=").append(idProvincia);
        sb.append(", dataInizioValidita=").append(dataInizioValidita);
        sb.append(", dataFineValidita=").append(dataFineValidita);
        sb.append(", dtIdComune=").append(dtIdComune);
        sb.append(", dtIdComunePrev=").append(dtIdComunePrev);
        sb.append(", dtIdComuneNext=").append(dtIdComuneNext);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}