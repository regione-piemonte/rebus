/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuscTAssegnazioneRisorseDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTAssegnazioneRisorseSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuscTAssegnazioneRisorseDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	long countByExample(RebuscTAssegnazioneRisorseSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int deleteByExample(RebuscTAssegnazioneRisorseSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int deleteByPrimaryKey(Long idAssegnazioneRisorse);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int insert(RebuscTAssegnazioneRisorseDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int insertSelective(RebuscTAssegnazioneRisorseDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	List<RebuscTAssegnazioneRisorseDTO> selectByExample(RebuscTAssegnazioneRisorseSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	RebuscTAssegnazioneRisorseDTO selectByPrimaryKey(Long idAssegnazioneRisorse);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int updateByExampleSelective(@Param("record") RebuscTAssegnazioneRisorseDTO record,
			@Param("example") RebuscTAssegnazioneRisorseSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int updateByExample(@Param("record") RebuscTAssegnazioneRisorseDTO record,
			@Param("example") RebuscTAssegnazioneRisorseSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int updateByPrimaryKeySelective(RebuscTAssegnazioneRisorseDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_assegnazione_risorse
	 * @mbg.generated  Thu Dec 16 15:42:27 CET 2021
	 */
	int updateByPrimaryKey(RebuscTAssegnazioneRisorseDTO record);
}