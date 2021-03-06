/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuscRDocDatoMessaServizioKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRDocDatoMessaServizioSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuscRDocDatoMessaServizioDAO extends ParentDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    long countByExample(RebuscRDocDatoMessaServizioSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    int deleteByExample(RebuscRDocDatoMessaServizioSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    int deleteByPrimaryKey(RebuscRDocDatoMessaServizioKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    int insert(RebuscRDocDatoMessaServizioKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    int insertSelective(RebuscRDocDatoMessaServizioKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    List<RebuscRDocDatoMessaServizioKey> selectByExample(RebuscRDocDatoMessaServizioSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    int updateByExampleSelective(@Param("record") RebuscRDocDatoMessaServizioKey record, @Param("example") RebuscRDocDatoMessaServizioSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_r_doc_dato_messa_servizio
     *
     * @mbg.generated Wed Nov 24 17:39:06 CET 2021
     */
    int updateByExample(@Param("record") RebuscRDocDatoMessaServizioKey record, @Param("example") RebuscRDocDatoMessaServizioSelector example);
}