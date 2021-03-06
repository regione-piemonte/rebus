/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotivazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotivazioneSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuspDMotivazioneDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	long countByExample(RebuspDMotivazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int deleteByExample(RebuspDMotivazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int deleteByPrimaryKey(Long idMotivazione);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int insert(RebuspDMotivazioneDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int insertSelective(RebuspDMotivazioneDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	List<RebuspDMotivazioneDTO> selectByExample(RebuspDMotivazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	RebuspDMotivazioneDTO selectByPrimaryKey(Long idMotivazione);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int updateByExampleSelective(@Param("record") RebuspDMotivazioneDTO record, @Param("example") RebuspDMotivazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int updateByExample(@Param("record") RebuspDMotivazioneDTO record, @Param("example") RebuspDMotivazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int updateByPrimaryKeySelective(RebuspDMotivazioneDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusp_d_motivazione
	 * @mbg.generated  Fri Jan 24 16:11:33 CET 2020
	 */
	int updateByPrimaryKey(RebuspDMotivazioneDTO record);
}