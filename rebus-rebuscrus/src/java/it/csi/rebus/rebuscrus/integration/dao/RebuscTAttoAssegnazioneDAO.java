/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuscTAttoAssegnazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTAttoAssegnazioneSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuscTAttoAssegnazioneDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	long countByExample(RebuscTAttoAssegnazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int deleteByExample(RebuscTAttoAssegnazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int deleteByPrimaryKey(Long idAttoAssegnazione);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int insert(RebuscTAttoAssegnazioneDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int insertSelective(RebuscTAttoAssegnazioneDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	List<RebuscTAttoAssegnazioneDTO> selectByExample(RebuscTAttoAssegnazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	RebuscTAttoAssegnazioneDTO selectByPrimaryKey(Long idAttoAssegnazione);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int updateByExampleSelective(@Param("record") RebuscTAttoAssegnazioneDTO record,
			@Param("example") RebuscTAttoAssegnazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int updateByExample(@Param("record") RebuscTAttoAssegnazioneDTO record,
			@Param("example") RebuscTAttoAssegnazioneSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int updateByPrimaryKeySelective(RebuscTAttoAssegnazioneDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_atto_assegnazione
	 * @mbg.generated  Fri Nov 19 15:37:42 CET 2021
	 */
	int updateByPrimaryKey(RebuscTAttoAssegnazioneDTO record);
}