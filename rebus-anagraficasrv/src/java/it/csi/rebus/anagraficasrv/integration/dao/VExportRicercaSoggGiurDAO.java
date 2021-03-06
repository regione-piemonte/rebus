/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.VExportRicercaSoggGiurDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VExportRicercaSoggGiurSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VExportRicercaSoggGiurDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_export_ricerca_sogg_giur
	 * @mbg.generated  Tue Aug 04 17:47:30 CEST 2020
	 */
	long countByExample(VExportRicercaSoggGiurSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_export_ricerca_sogg_giur
	 * @mbg.generated  Tue Aug 04 17:47:30 CEST 2020
	 */
	int deleteByExample(VExportRicercaSoggGiurSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_export_ricerca_sogg_giur
	 * @mbg.generated  Tue Aug 04 17:47:30 CEST 2020
	 */
	int insert(VExportRicercaSoggGiurDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_export_ricerca_sogg_giur
	 * @mbg.generated  Tue Aug 04 17:47:30 CEST 2020
	 */
	int insertSelective(VExportRicercaSoggGiurDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_export_ricerca_sogg_giur
	 * @mbg.generated  Tue Aug 04 17:47:30 CEST 2020
	 */
	List<VExportRicercaSoggGiurDTO> selectByExample(VExportRicercaSoggGiurSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_export_ricerca_sogg_giur
	 * @mbg.generated  Tue Aug 04 17:47:30 CEST 2020
	 */
	int updateByExampleSelective(@Param("record") VExportRicercaSoggGiurDTO record,
			@Param("example") VExportRicercaSoggGiurSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtpl_trasv.v_export_ricerca_sogg_giur
	 * @mbg.generated  Tue Aug 04 17:47:30 CEST 2020
	 */
	int updateByExample(@Param("record") VExportRicercaSoggGiurDTO record,
			@Param("example") VExportRicercaSoggGiurSelector example);
}