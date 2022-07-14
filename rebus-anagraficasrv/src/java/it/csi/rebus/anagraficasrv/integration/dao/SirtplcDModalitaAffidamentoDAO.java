/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDModalitaAffidamentoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDModalitaAffidamentoSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplcDModalitaAffidamentoDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	long countByExample(SirtplcDModalitaAffidamentoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByExample(SirtplcDModalitaAffidamentoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByPrimaryKey(Long idModalitaAffidamento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insert(SirtplcDModalitaAffidamentoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insertSelective(SirtplcDModalitaAffidamentoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	List<SirtplcDModalitaAffidamentoDTO> selectByExample(SirtplcDModalitaAffidamentoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	SirtplcDModalitaAffidamentoDTO selectByPrimaryKey(Long idModalitaAffidamento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExampleSelective(@Param("record") SirtplcDModalitaAffidamentoDTO record,
			@Param("example") SirtplcDModalitaAffidamentoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExample(@Param("record") SirtplcDModalitaAffidamentoDTO record,
			@Param("example") SirtplcDModalitaAffidamentoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKeySelective(SirtplcDModalitaAffidamentoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_modalita_affidamento
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKey(SirtplcDModalitaAffidamentoDTO record);
}