/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDBacinoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDBacinoSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SirtplcDBacinoDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	long countByExample(SirtplcDBacinoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByExample(SirtplcDBacinoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int deleteByPrimaryKey(Long idBacino);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insert(SirtplcDBacinoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int insertSelective(SirtplcDBacinoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	List<SirtplcDBacinoDTO> selectByExample(SirtplcDBacinoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	SirtplcDBacinoDTO selectByPrimaryKey(Long idBacino);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExampleSelective(@Param("record") SirtplcDBacinoDTO record,
			@Param("example") SirtplcDBacinoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByExample(@Param("record") SirtplcDBacinoDTO record, @Param("example") SirtplcDBacinoSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKeySelective(SirtplcDBacinoDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sirtplc_d_bacino
	 * @mbg.generated  Mon Nov 04 11:33:48 CET 2019
	 */
	int updateByPrimaryKey(SirtplcDBacinoDTO record);
}