/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDAmbitoServizioDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDAmbitoServizioSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplcDAmbitoServizioDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	long countByExample(SirtplcDAmbitoServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByExample(SirtplcDAmbitoServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByPrimaryKey(Long idAmbitoServizio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insert(SirtplcDAmbitoServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insertSelective(SirtplcDAmbitoServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	List<SirtplcDAmbitoServizioDTO> selectByExample(SirtplcDAmbitoServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	SirtplcDAmbitoServizioDTO selectByPrimaryKey(Long idAmbitoServizio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExampleSelective(@Param("record") SirtplcDAmbitoServizioDTO record,
			@Param("example") SirtplcDAmbitoServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExample(@Param("record") SirtplcDAmbitoServizioDTO record,
			@Param("example") SirtplcDAmbitoServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKeySelective(SirtplcDAmbitoServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_ambito_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKey(SirtplcDAmbitoServizioDTO record);
}