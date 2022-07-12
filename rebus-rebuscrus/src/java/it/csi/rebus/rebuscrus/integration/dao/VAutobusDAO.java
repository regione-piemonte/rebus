/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VAutobusDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus
	 * @mbg.generated  Fri Jun 03 15:03:20 CEST 2022
	 */
	long countByExample(VAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus
	 * @mbg.generated  Fri Jun 03 15:03:20 CEST 2022
	 */
	int deleteByExample(VAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus
	 * @mbg.generated  Fri Jun 03 15:03:20 CEST 2022
	 */
	int insert(VAutobusDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus
	 * @mbg.generated  Fri Jun 03 15:03:20 CEST 2022
	 */
	int insertSelective(VAutobusDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus
	 * @mbg.generated  Fri Jun 03 15:03:20 CEST 2022
	 */
	List<VAutobusDTO> selectByExample(VAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus
	 * @mbg.generated  Fri Jun 03 15:03:20 CEST 2022
	 */
	int updateByExampleSelective(@Param("record") VAutobusDTO record, @Param("example") VAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_autobus
	 * @mbg.generated  Fri Jun 03 15:03:20 CEST 2022
	 */
	int updateByExample(@Param("record") VAutobusDTO record, @Param("example") VAutobusSelector example);
}