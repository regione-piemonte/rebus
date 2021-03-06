/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAziendeDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAziendeSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebusTVariazAziendeDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	long countByExample(RebusTVariazAziendeSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int deleteByExample(RebusTVariazAziendeSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int deleteByPrimaryKey(Long idVariazAzienda);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int insert(RebusTVariazAziendeDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int insertSelective(RebusTVariazAziendeDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	List<RebusTVariazAziendeDTO> selectByExample(RebusTVariazAziendeSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	RebusTVariazAziendeDTO selectByPrimaryKey(Long idVariazAzienda);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByExampleSelective(@Param("record") RebusTVariazAziendeDTO record, @Param("example") RebusTVariazAziendeSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByExample(@Param("record") RebusTVariazAziendeDTO record, @Param("example") RebusTVariazAziendeSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByPrimaryKeySelective(RebusTVariazAziendeDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_t_variaz_aziende
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByPrimaryKey(RebusTVariazAziendeDTO record);
}