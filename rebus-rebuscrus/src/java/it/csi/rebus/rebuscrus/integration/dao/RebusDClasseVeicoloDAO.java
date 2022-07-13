/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao;

import it.csi.rebus.rebuscrus.integration.dto.RebusDClasseVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDClasseVeicoloSelector;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RebusDClasseVeicoloDAO extends ParentDAO {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	long countByExample(RebusDClasseVeicoloSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int deleteByExample(RebusDClasseVeicoloSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int deleteByPrimaryKey(Long idClasseVeicolo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int insert(RebusDClasseVeicoloDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int insertSelective(RebusDClasseVeicoloDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	List<RebusDClasseVeicoloDTO> selectByExample(RebusDClasseVeicoloSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	RebusDClasseVeicoloDTO selectByPrimaryKey(Long idClasseVeicolo);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByExampleSelective(@Param("record") RebusDClasseVeicoloDTO record, @Param("example") RebusDClasseVeicoloSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByExample(@Param("record") RebusDClasseVeicoloDTO record, @Param("example") RebusDClasseVeicoloSelector example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByPrimaryKeySelective(RebusDClasseVeicoloDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table rebus_d_classe_veicolo
	 * @mbg.generated  Mon Oct 21 17:24:13 CEST 2019
	 */
	int updateByPrimaryKey(RebusDClasseVeicoloDTO record);
}