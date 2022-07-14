/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoPercorenzaDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoPercorenzaSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplcDTipoPercorenzaDAO extends ParentDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    long countByExample(SirtplcDTipoPercorenzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int deleteByExample(SirtplcDTipoPercorenzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int deleteByPrimaryKey(Long idTipoPercorrenza);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int insert(SirtplcDTipoPercorenzaDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int insertSelective(SirtplcDTipoPercorenzaDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    List<SirtplcDTipoPercorenzaDTO> selectByExample(SirtplcDTipoPercorenzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    SirtplcDTipoPercorenzaDTO selectByPrimaryKey(Long idTipoPercorrenza);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByExampleSelective(@Param("record") SirtplcDTipoPercorenzaDTO record, @Param("example") SirtplcDTipoPercorenzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByExample(@Param("record") SirtplcDTipoPercorenzaDTO record, @Param("example") SirtplcDTipoPercorenzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByPrimaryKeySelective(SirtplcDTipoPercorenzaDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sirtplc_d_tipo_percorenza
     *
     * @mbg.generated Mon Nov 04 11:33:48 CET 2019
     */
    int updateByPrimaryKey(SirtplcDTipoPercorenzaDTO record);
}