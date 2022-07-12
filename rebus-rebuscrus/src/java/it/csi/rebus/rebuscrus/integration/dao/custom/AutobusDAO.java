/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusDTO;
import it.csi.rebus.rebuscrus.vo.DocVariazAutobusVO;

public interface AutobusDAO {

	List<String> getAutobusForContribuzioneAzienda(@Param("fkAzienda") Long fkAzienda);
	
	List<DocVariazAutobusVO> getDocVariazAutobusForInfo(@Param("idVariazAutobus") Long idVariazAutobus);
	
	void insertDocVariazAutobus(List<RebusRDocVariazAutobusDTO> documentList);
}
