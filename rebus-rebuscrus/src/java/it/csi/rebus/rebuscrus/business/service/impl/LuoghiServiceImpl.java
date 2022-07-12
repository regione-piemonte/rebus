/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.rebuscrus.business.service.LuoghiService;
import it.csi.rebus.rebuscrus.integration.dao.SirtplDComuneDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplDProvinciaDAO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplDComuneDTO;
import it.csi.rebus.rebuscrus.integration.mapper.ProvinciaMapper;
import it.csi.rebus.rebuscrus.vo.ProvinciaVO;

@Service
public class LuoghiServiceImpl implements LuoghiService {

	@Autowired
	private SirtplDComuneDAO sirtplDComuneDAO;
	@Autowired
	private SirtplDProvinciaDAO sirtplDProvinciaDAO;
	@Autowired
	private ProvinciaMapper provinciaMapper;

	@Override
	public ProvinciaVO getProvinciaByIdComune(Long idComune) {
		SirtplDComuneDTO com = sirtplDComuneDAO.selectByPrimaryKey(idComune);
		return provinciaMapper.mapDTOtoVO(sirtplDProvinciaDAO.selectByPrimaryKey(com.getIdProvincia()));
	}

}
