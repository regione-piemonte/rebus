/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuspTSubProcedimentoKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTSubProcedimentoSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuspTSubProcedimentoDAO extends ParentDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    long countByExample(RebuspTSubProcedimentoSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    int deleteByExample(RebuspTSubProcedimentoSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    int deleteByPrimaryKey(RebuspTSubProcedimentoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    int insert(RebuspTSubProcedimentoKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    int insertSelective(RebuspTSubProcedimentoKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    List<RebuspTSubProcedimentoKey> selectByExample(RebuspTSubProcedimentoSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    int updateByExampleSelective(@Param("record") RebuspTSubProcedimentoKey record, @Param("example") RebuspTSubProcedimentoSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusp_t_sub_procedimento
     *
     * @mbg.generated Tue Mar 17 14:34:44 CET 2020
     */
    int updateByExample(@Param("record") RebuspTSubProcedimentoKey record, @Param("example") RebuspTSubProcedimentoSelector example);
}