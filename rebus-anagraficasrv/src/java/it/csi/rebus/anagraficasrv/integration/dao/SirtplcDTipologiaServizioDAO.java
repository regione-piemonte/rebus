/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipologiaServizioDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipologiaServizioSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplcDTipologiaServizioDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	long countByExample(SirtplcDTipologiaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByExample(SirtplcDTipologiaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByPrimaryKey(Long idTipologiaServizio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insert(SirtplcDTipologiaServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insertSelective(SirtplcDTipologiaServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	List<SirtplcDTipologiaServizioDTO> selectByExample(SirtplcDTipologiaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	SirtplcDTipologiaServizioDTO selectByPrimaryKey(Long idTipologiaServizio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExampleSelective(@Param("record") SirtplcDTipologiaServizioDTO record,
			@Param("example") SirtplcDTipologiaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExample(@Param("record") SirtplcDTipologiaServizioDTO record,
			@Param("example") SirtplcDTipologiaServizioSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKeySelective(SirtplcDTipologiaServizioDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_tipologia_servizio
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKey(SirtplcDTipologiaServizioDTO record);
}