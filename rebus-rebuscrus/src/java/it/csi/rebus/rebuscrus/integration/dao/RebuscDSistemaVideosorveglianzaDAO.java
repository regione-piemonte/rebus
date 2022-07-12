/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuscDSistemaVideosorveglianzaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDSistemaVideosorveglianzaSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuscDSistemaVideosorveglianzaDAO extends ParentDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    long countByExample(RebuscDSistemaVideosorveglianzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int deleteByExample(RebuscDSistemaVideosorveglianzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int deleteByPrimaryKey(Double idSistemaVideosorveglianza);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int insert(RebuscDSistemaVideosorveglianzaDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int insertSelective(RebuscDSistemaVideosorveglianzaDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    List<RebuscDSistemaVideosorveglianzaDTO> selectByExample(RebuscDSistemaVideosorveglianzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    RebuscDSistemaVideosorveglianzaDTO selectByPrimaryKey(Double idSistemaVideosorveglianza);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int updateByExampleSelective(@Param("record") RebuscDSistemaVideosorveglianzaDTO record, @Param("example") RebuscDSistemaVideosorveglianzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int updateByExample(@Param("record") RebuscDSistemaVideosorveglianzaDTO record, @Param("example") RebuscDSistemaVideosorveglianzaSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int updateByPrimaryKeySelective(RebuscDSistemaVideosorveglianzaDTO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusc_d_sistema_videosorveglianza
     *
     * @mbg.generated Tue Jan 04 08:25:47 CET 2022
     */
    int updateByPrimaryKey(RebuscDSistemaVideosorveglianzaDTO record);
}