/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VSoggettiCoinvoltiDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_soggetti_coinvolti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	long countByExample(VSoggettiCoinvoltiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_soggetti_coinvolti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int deleteByExample(VSoggettiCoinvoltiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_soggetti_coinvolti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int insert(VSoggettiCoinvoltiDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_soggetti_coinvolti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int insertSelective(VSoggettiCoinvoltiDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_soggetti_coinvolti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	List<VSoggettiCoinvoltiDTO> selectByExample(VSoggettiCoinvoltiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_soggetti_coinvolti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int updateByExampleSelective(@Param("record") VSoggettiCoinvoltiDTO record,
			@Param("example") VSoggettiCoinvoltiSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_soggetti_coinvolti
	 * @mbg.generated  Mon Mar 08 11:33:08 CET 2021
	 */
	int updateByExample(@Param("record") VSoggettiCoinvoltiDTO record,
			@Param("example") VSoggettiCoinvoltiSelector example);
}