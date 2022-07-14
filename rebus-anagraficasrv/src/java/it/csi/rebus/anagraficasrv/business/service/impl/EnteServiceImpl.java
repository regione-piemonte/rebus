/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.anagraficasrv.business.service.EnteService;
import it.csi.rebus.anagraficasrv.business.service.TipoRaggruppamentoVO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDRuoloSogGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDTipoEnteDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDTipoSogGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDTipoRaggruppamentoDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDRuoloSogGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDRuoloSogGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoEnteDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoEnteSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoRaggruppamentoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoRaggruppamentoSelector;
import it.csi.rebus.anagraficasrv.integration.mapper.EnteMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.TipoEnteMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.TipoRaggruppamentoMapper;
import it.csi.rebus.anagraficasrv.util.Constants;
import it.csi.rebus.anagraficasrv.vo.EnteVO;
import it.csi.rebus.anagraficasrv.vo.TipoEnteVO;

@Service
public class EnteServiceImpl implements EnteService {

	@Autowired
	private SirtplaDTipoEnteDAO sirtplaDTipoEnteDAO;
	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;
	@Autowired
	private SirtplcDTipoRaggruppamentoDAO sirtplcDTipoRaggruppamentoDAO;
	@Autowired
	private SirtplaDTipoSogGiuridicoDAO sirtplaDTipoSogGiuridicoDAO;
	@Autowired
	private SirtplaDRuoloSogGiuridicoDAO sirtplaDRuoloSogGiuridicoDAO;
	@Autowired
	private TipoEnteMapper tipoEnteMapper;
	@Autowired
	private TipoRaggruppamentoMapper tipoRaggruppamentoMapper;
	@Autowired
	private EnteMapper enteMapper;

	@Override
	public List<TipoEnteVO> getTipiEnte() {
		SirtplaDTipoEnteSelector sirtplaDTipoEnteSelector = new SirtplaDTipoEnteSelector();
		sirtplaDTipoEnteSelector.setOrderByClause("desc_tipo_ente");
		List<SirtplaDTipoEnteDTO> tipi = sirtplaDTipoEnteDAO.selectByExample(sirtplaDTipoEnteSelector);
		List<TipoEnteVO> tipiEnte = new ArrayList<>();
		for (SirtplaDTipoEnteDTO t : tipi) {
			tipiEnte.add(tipoEnteMapper.mapDTOtoVO(t));
		}
		return tipiEnte;
	}

	@Override
	public List<EnteVO> getEnti() {
		SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		SirtplaDTipoSogGiuridicoSelector sirtplaDTipoSogGiuridicoSelector = new SirtplaDTipoSogGiuridicoSelector();
		SirtplaDRuoloSogGiuridicoSelector sirtplaDRuoloSogGiuridicoSelector = new SirtplaDRuoloSogGiuridicoSelector();
		sirtplaDRuoloSogGiuridicoSelector.createCriteria().andCodRuoloSogGiuridicoEqualTo(Constants.ID_RUOLO_TIPO_SOG_GIURIDICO_ENTE_COMMITTENTE);
		List<SirtplaDRuoloSogGiuridicoDTO> ruoliSogGiurid = sirtplaDRuoloSogGiuridicoDAO.selectByExample(sirtplaDRuoloSogGiuridicoSelector);
		sirtplaDTipoSogGiuridicoSelector.createCriteria();
		for(SirtplaDRuoloSogGiuridicoDTO ruoloSogGiurid:ruoliSogGiurid) {
			sirtplaDTipoSogGiuridicoSelector.or().andIdRuoloSogGiuridicoEqualTo(ruoloSogGiurid.getIdRuoloSogGiuridico());
		}
		List<SirtplaDTipoSogGiuridicoDTO> idEnti = sirtplaDTipoSogGiuridicoDAO.selectByExample(sirtplaDTipoSogGiuridicoSelector);
		sirtplaTSoggettoGiuridicoSelector.createCriteria();
		for (SirtplaDTipoSogGiuridicoDTO idEnte : idEnti ) {
			sirtplaTSoggettoGiuridicoSelector.or().andIdTipoSogGiuridicoEqualTo(idEnte.getIdTipoSogGiuridico());
		}
		sirtplaTSoggettoGiuridicoSelector.setOrderByClause("denom_soggetto_giuridico");
		List<SirtplaTSoggettoGiuridicoDTO> enti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		List<EnteVO> entiVO = new ArrayList<>();
		for (SirtplaTSoggettoGiuridicoDTO e : enti) {
			EnteVO enteTmp = enteMapper.mapDTOtoVO(e);
			enteTmp.setIdRuolo(sirtplaDTipoSogGiuridicoDAO.selectByPrimaryKey(e.getIdTipoSogGiuridico()).getIdRuoloSogGiuridico());
			entiVO.add(enteTmp);
		}
		return entiVO;
	}

	@Override
	public List<TipoRaggruppamentoVO> getTipiRaggruppamento() {
		SirtplcDTipoRaggruppamentoSelector sirtplaDTipoRaggruppamentoSelector = new SirtplcDTipoRaggruppamentoSelector();
		sirtplaDTipoRaggruppamentoSelector.setOrderByClause("id_tipo_raggruppamento");
		List<SirtplcDTipoRaggruppamentoDTO> tipi = sirtplcDTipoRaggruppamentoDAO.selectByExample(sirtplaDTipoRaggruppamentoSelector);
		List<TipoRaggruppamentoVO> tipiRaggruppamento = new ArrayList<>();
		for (SirtplcDTipoRaggruppamentoDTO t : tipi) {
			tipiRaggruppamento.add(tipoRaggruppamentoMapper.mapDTOtoVO(t));
		}
		return tipiRaggruppamento;
	}

}
