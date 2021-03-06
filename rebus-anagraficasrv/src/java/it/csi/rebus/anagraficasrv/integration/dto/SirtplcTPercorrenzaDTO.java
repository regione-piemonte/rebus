/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dto;

import java.io.Serializable;
import java.util.Date;

public class SirtplcTPercorrenzaDTO extends ParentDTO implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.id_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private Long idPercorrenza;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.id_contratto
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private Long idContratto;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.id_tipo_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private Long idTipoPercorrenza;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.anno
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private Long anno;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.versione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private Long versione;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.chilometri_programmati
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private Long chilometriProgrammati;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.data_compilazione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private Date dataCompilazione;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sirtplc_t_percorrenza.note
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sirtplc_t_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.id_percorrenza
     *
     * @return the value of sirtplc_t_percorrenza.id_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public Long getIdPercorrenza() {
        return idPercorrenza;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.id_percorrenza
     *
     * @param idPercorrenza the value for sirtplc_t_percorrenza.id_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setIdPercorrenza(Long idPercorrenza) {
        this.idPercorrenza = idPercorrenza;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.id_contratto
     *
     * @return the value of sirtplc_t_percorrenza.id_contratto
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public Long getIdContratto() {
        return idContratto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.id_contratto
     *
     * @param idContratto the value for sirtplc_t_percorrenza.id_contratto
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setIdContratto(Long idContratto) {
        this.idContratto = idContratto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.id_tipo_percorrenza
     *
     * @return the value of sirtplc_t_percorrenza.id_tipo_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public Long getIdTipoPercorrenza() {
        return idTipoPercorrenza;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.id_tipo_percorrenza
     *
     * @param idTipoPercorrenza the value for sirtplc_t_percorrenza.id_tipo_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setIdTipoPercorrenza(Long idTipoPercorrenza) {
        this.idTipoPercorrenza = idTipoPercorrenza;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.anno
     *
     * @return the value of sirtplc_t_percorrenza.anno
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public Long getAnno() {
        return anno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.anno
     *
     * @param anno the value for sirtplc_t_percorrenza.anno
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setAnno(Long anno) {
        this.anno = anno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.versione
     *
     * @return the value of sirtplc_t_percorrenza.versione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public Long getVersione() {
        return versione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.versione
     *
     * @param versione the value for sirtplc_t_percorrenza.versione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setVersione(Long versione) {
        this.versione = versione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.chilometri_programmati
     *
     * @return the value of sirtplc_t_percorrenza.chilometri_programmati
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public Long getChilometriProgrammati() {
        return chilometriProgrammati;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.chilometri_programmati
     *
     * @param chilometriProgrammati the value for sirtplc_t_percorrenza.chilometri_programmati
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setChilometriProgrammati(Long chilometriProgrammati) {
        this.chilometriProgrammati = chilometriProgrammati;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.data_compilazione
     *
     * @return the value of sirtplc_t_percorrenza.data_compilazione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public Date getDataCompilazione() {
        return dataCompilazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.data_compilazione
     *
     * @param dataCompilazione the value for sirtplc_t_percorrenza.data_compilazione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setDataCompilazione(Date dataCompilazione) {
        this.dataCompilazione = dataCompilazione;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sirtplc_t_percorrenza.note
     *
     * @return the value of sirtplc_t_percorrenza.note
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sirtplc_t_percorrenza.note
     *
     * @param note the value for sirtplc_t_percorrenza.note
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_t_percorrenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idPercorrenza=").append(idPercorrenza);
        sb.append(", idContratto=").append(idContratto);
        sb.append(", idTipoPercorrenza=").append(idTipoPercorrenza);
        sb.append(", anno=").append(anno);
        sb.append(", versione=").append(versione);
        sb.append(", chilometriProgrammati=").append(chilometriProgrammati);
        sb.append(", dataCompilazione=").append(dataCompilazione);
        sb.append(", note=").append(note);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}