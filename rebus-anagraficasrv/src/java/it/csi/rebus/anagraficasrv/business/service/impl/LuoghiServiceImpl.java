/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.anagraficasrv.business.service.LuoghiService;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplDComuneDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplDProvinciaDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplDComuneDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplDComuneSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplDProvinciaDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplDProvinciaSelector;
import it.csi.rebus.anagraficasrv.integration.mapper.ComuneMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.ProvinciaMapper;
import it.csi.rebus.anagraficasrv.vo.ComuneVO;
import it.csi.rebus.anagraficasrv.vo.ProvinciaVO;

@Service
public class LuoghiServiceImpl implements LuoghiService {

	@Autowired
	private SirtplDComuneDAO sirtplDComuneDAO;
	@Autowired
	private SirtplDProvinciaDAO sirtplDProvinciaDAO;

	@Autowired
	private ComuneMapper comuneMapper;
	@Autowired
	private ProvinciaMapper provinciaMapper;

	@Override
	public List<ProvinciaVO> getProvince() {
		SirtplDProvinciaSelector sirtplDProvinciaSelector = new SirtplDProvinciaSelector();
		sirtplDProvinciaSelector.setOrderByClause("denom_provincia");
		sirtplDProvinciaSelector.createCriteria().andDataFineValiditaIsNull();
		List<SirtplDProvinciaDTO> prov = sirtplDProvinciaDAO.selectByExample(sirtplDProvinciaSelector);
		List<ProvinciaVO> province = new ArrayList<>();
		for (SirtplDProvinciaDTO p : prov) {
			province.add(provinciaMapper.mapDTOtoVO(p));
		}
		return province;
	}

	@Override
	public List<ComuneVO> getComuniByIdProvincia(Long idProvincia) {
		SirtplDComuneSelector sirtplDComuniSelector = new SirtplDComuneSelector();
		sirtplDComuniSelector.setOrderByClause("denom_comune");
		sirtplDComuniSelector.createCriteria().andIdProvinciaEqualTo(idProvincia).andDataFineValiditaIsNull();
		List<SirtplDComuneDTO> com = sirtplDComuneDAO.selectByExample(sirtplDComuniSelector);
		List<ComuneVO> comuni = new ArrayList<>();
		for (SirtplDComuneDTO c : com) {
			comuni.add(comuneMapper.mapDTOtoVO(c));
		}
		return comuni;

	}

	@Override
	public List<ComuneVO> getComuni() {
		SirtplDComuneSelector sirtplDComuniSelector = new SirtplDComuneSelector();
		sirtplDComuniSelector.setOrderByClause("denom_comune");
		List<SirtplDComuneDTO> com = sirtplDComuneDAO.selectByExample(sirtplDComuniSelector);
		List<ComuneVO> comuni = new ArrayList<>();
		for (SirtplDComuneDTO c : com) {
			comuni.add(comuneMapper.mapDTOtoVO(c));
		}
		return comuni;
	}

	@Override
	public ProvinciaVO getProvinciaByIdComune(Long idComune) {
		SirtplDComuneDTO com = sirtplDComuneDAO.selectByPrimaryKey(idComune);
		return provinciaMapper.mapDTOtoVO(sirtplDProvinciaDAO.selectByPrimaryKey(com.getIdProvincia()));
	}

}
