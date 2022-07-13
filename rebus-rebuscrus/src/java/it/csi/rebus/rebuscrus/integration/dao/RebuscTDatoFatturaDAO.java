/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoFatturaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoFatturaSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuscTDatoFatturaDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	long countByExample(RebuscTDatoFatturaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int deleteByExample(RebuscTDatoFatturaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int deleteByPrimaryKey(Long idDatoFattura);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int insert(RebuscTDatoFatturaDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int insertSelective(RebuscTDatoFatturaDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	List<RebuscTDatoFatturaDTO> selectByExample(RebuscTDatoFatturaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	RebuscTDatoFatturaDTO selectByPrimaryKey(Long idDatoFattura);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int updateByExampleSelective(@Param("record") RebuscTDatoFatturaDTO record,
			@Param("example") RebuscTDatoFatturaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int updateByExample(@Param("record") RebuscTDatoFatturaDTO record,
			@Param("example") RebuscTDatoFatturaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int updateByPrimaryKeySelective(RebuscTDatoFatturaDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_fattura
	 * @mbg.generated  Thu Dec 16 16:26:20 CET 2021
	 */
	int updateByPrimaryKey(RebuscTDatoFatturaDTO record);
}