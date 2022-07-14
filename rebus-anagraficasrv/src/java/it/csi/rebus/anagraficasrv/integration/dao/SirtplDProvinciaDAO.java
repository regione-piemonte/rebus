/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplDProvinciaDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplDProvinciaSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplDProvinciaDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	long countByExample(SirtplDProvinciaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByExample(SirtplDProvinciaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByPrimaryKey(Long idProvincia);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insert(SirtplDProvinciaDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insertSelective(SirtplDProvinciaDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	List<SirtplDProvinciaDTO> selectByExample(SirtplDProvinciaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	SirtplDProvinciaDTO selectByPrimaryKey(Long idProvincia);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExampleSelective(@Param("record") SirtplDProvinciaDTO record,
			@Param("example") SirtplDProvinciaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExample(@Param("record") SirtplDProvinciaDTO record,
			@Param("example") SirtplDProvinciaSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKeySelective(SirtplDProvinciaDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_d_provincia
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKey(SirtplDProvinciaDTO record);
}