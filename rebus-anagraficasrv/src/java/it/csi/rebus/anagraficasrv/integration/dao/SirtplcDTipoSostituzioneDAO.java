/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoSostituzioneDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoSostituzioneSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplcDTipoSostituzioneDAO extends ParentDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    long countByExample(SirtplcDTipoSostituzioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int deleteByExample(SirtplcDTipoSostituzioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int deleteByPrimaryKey(Long idTipoSostituzione);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int insert(SirtplcDTipoSostituzioneDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int insertSelective(SirtplcDTipoSostituzioneDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    List<SirtplcDTipoSostituzioneDTO> selectByExample(SirtplcDTipoSostituzioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    SirtplcDTipoSostituzioneDTO selectByPrimaryKey(Long idTipoSostituzione);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByExampleSelective(@Param("record") SirtplcDTipoSostituzioneDTO record, @Param("example") SirtplcDTipoSostituzioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByExample(@Param("record") SirtplcDTipoSostituzioneDTO record, @Param("example") SirtplcDTipoSostituzioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByPrimaryKeySelective(SirtplcDTipoSostituzioneDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_sostituzione
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByPrimaryKey(SirtplcDTipoSostituzioneDTO record);
}