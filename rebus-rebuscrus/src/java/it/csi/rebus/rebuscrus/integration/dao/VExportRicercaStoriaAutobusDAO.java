/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaStoriaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaStoriaAutobusSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VExportRicercaStoriaAutobusDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_export_ricerca_storia_autobus
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	long countByExample(VExportRicercaStoriaAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_export_ricerca_storia_autobus
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	int deleteByExample(VExportRicercaStoriaAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_export_ricerca_storia_autobus
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	int insert(VExportRicercaStoriaAutobusDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_export_ricerca_storia_autobus
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	int insertSelective(VExportRicercaStoriaAutobusDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_export_ricerca_storia_autobus
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	List<VExportRicercaStoriaAutobusDTO> selectByExample(VExportRicercaStoriaAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_export_ricerca_storia_autobus
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	int updateByExampleSelective(@Param("record") VExportRicercaStoriaAutobusDTO record,
			@Param("example") VExportRicercaStoriaAutobusSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table v_export_ricerca_storia_autobus
	 * @mbg.generated  Fri Mar 11 10:23:20 CET 2022
	 */
	int updateByExample(@Param("record") VExportRicercaStoriaAutobusDTO record,
			@Param("example") VExportRicercaStoriaAutobusSelector example);
}