/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.VContrattiSoggettiDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VContrattiSoggettiSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VContrattiSoggettiDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	long countByExample(VContrattiSoggettiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int deleteByExample(VContrattiSoggettiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int insert(VContrattiSoggettiDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int insertSelective(VContrattiSoggettiDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	List<VContrattiSoggettiDTO> selectByExample(VContrattiSoggettiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int updateByExampleSelective(@Param("record") VContrattiSoggettiDTO record,
			@Param("example") VContrattiSoggettiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_contratti_soggetti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int updateByExample(@Param("record") VContrattiSoggettiDTO record,
			@Param("example") VContrattiSoggettiSelector example);
}