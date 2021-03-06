/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebusTEntiDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTEntiSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebusTEntiDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_enti
	 * @mbg.generated  Wed Dec 04 19:05:15 CET 2019
	 */
	long countByExample(RebusTEntiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_enti
	 * @mbg.generated  Wed Dec 04 19:05:15 CET 2019
	 */
	int deleteByExample(RebusTEntiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_enti
	 * @mbg.generated  Wed Dec 04 19:05:15 CET 2019
	 */
	int insert(RebusTEntiDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_enti
	 * @mbg.generated  Wed Dec 04 19:05:15 CET 2019
	 */
	int insertSelective(RebusTEntiDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_enti
	 * @mbg.generated  Wed Dec 04 19:05:15 CET 2019
	 */
	List<RebusTEntiDTO> selectByExample(RebusTEntiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_enti
	 * @mbg.generated  Wed Dec 04 19:05:15 CET 2019
	 */
	int updateByExampleSelective(@Param("record") RebusTEntiDTO record, @Param("example") RebusTEntiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_enti
	 * @mbg.generated  Wed Dec 04 19:05:15 CET 2019
	 */
	int updateByExample(@Param("record") RebusTEntiDTO record, @Param("example") RebusTEntiSelector example);
}