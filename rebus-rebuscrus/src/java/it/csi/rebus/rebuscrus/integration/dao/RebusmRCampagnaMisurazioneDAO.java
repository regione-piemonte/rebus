/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebusmRCampagnaMisurazioneKey;
import it.csi.rebus.rebuscrus.integration.dto.RebusmRCampagnaMisurazioneSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebusmRCampagnaMisurazioneDAO extends ParentDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    long countByExample(RebusmRCampagnaMisurazioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    int deleteByExample(RebusmRCampagnaMisurazioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    int deleteByPrimaryKey(RebusmRCampagnaMisurazioneKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    int insert(RebusmRCampagnaMisurazioneKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    int insertSelective(RebusmRCampagnaMisurazioneKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    List<RebusmRCampagnaMisurazioneKey> selectByExample(RebusmRCampagnaMisurazioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    int updateByExampleSelective(@Param("record") RebusmRCampagnaMisurazioneKey record, @Param("example") RebusmRCampagnaMisurazioneSelector example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rebusm_r_campagna_misurazione
     *
     * @mbg.generated Fri Dec 10 10:30:29 CET 2021
     */
    int updateByExample(@Param("record") RebusmRCampagnaMisurazioneKey record, @Param("example") RebusmRCampagnaMisurazioneSelector example);
}