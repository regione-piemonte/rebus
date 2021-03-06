/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoMessaServizioDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoMessaServizioSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebuscTDatoMessaServizioDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	long countByExample(RebuscTDatoMessaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int deleteByExample(RebuscTDatoMessaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int deleteByPrimaryKey(Long idDatoMessaServizio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int insert(RebuscTDatoMessaServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int insertSelective(RebuscTDatoMessaServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	List<RebuscTDatoMessaServizioDTO> selectByExample(RebuscTDatoMessaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	RebuscTDatoMessaServizioDTO selectByPrimaryKey(Long idDatoMessaServizio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int updateByExampleSelective(@Param("record") RebuscTDatoMessaServizioDTO record,
			@Param("example") RebuscTDatoMessaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int updateByExample(@Param("record") RebuscTDatoMessaServizioDTO record,
			@Param("example") RebuscTDatoMessaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int updateByPrimaryKeySelective(RebuscTDatoMessaServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebusc_t_dato_messa_servizio
	 * @mbg.generated  Wed Mar 16 10:58:09 CET 2022
	 */
	int updateByPrimaryKey(RebuscTDatoMessaServizioDTO record);
}