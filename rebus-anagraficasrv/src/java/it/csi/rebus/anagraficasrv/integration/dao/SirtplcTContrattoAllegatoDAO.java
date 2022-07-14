/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoAllegatoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoAllegatoSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplcTContrattoAllegatoDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	long countByExample(SirtplcTContrattoAllegatoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int deleteByExample(SirtplcTContrattoAllegatoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int deleteByPrimaryKey(Long idContrattoAllegato);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int insert(SirtplcTContrattoAllegatoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int insertSelective(SirtplcTContrattoAllegatoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	List<SirtplcTContrattoAllegatoDTO> selectByExampleWithBLOBs(SirtplcTContrattoAllegatoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	List<SirtplcTContrattoAllegatoDTO> selectByExample(SirtplcTContrattoAllegatoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	SirtplcTContrattoAllegatoDTO selectByPrimaryKey(Long idContrattoAllegato);

	SirtplcTContrattoAllegatoDTO selectById(Long idContratto);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int updateByExampleSelective(@Param("record") SirtplcTContrattoAllegatoDTO record,
			@Param("example") SirtplcTContrattoAllegatoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int updateByExampleWithBLOBs(@Param("record") SirtplcTContrattoAllegatoDTO record,
			@Param("example") SirtplcTContrattoAllegatoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int updateByExample(@Param("record") SirtplcTContrattoAllegatoDTO record,
			@Param("example") SirtplcTContrattoAllegatoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int updateByPrimaryKeySelective(SirtplcTContrattoAllegatoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int updateByPrimaryKeyWithBLOBs(SirtplcTContrattoAllegatoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_t_contratto_allegato
	 * @mbg.generated  Mon Nov 25 16:39:57 CET 2019
	 */
	int updateByPrimaryKey(SirtplcTContrattoAllegatoDTO record);
}