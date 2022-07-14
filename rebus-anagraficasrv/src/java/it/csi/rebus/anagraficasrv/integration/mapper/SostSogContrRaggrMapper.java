/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDTipoSostituzioneDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRContrattoRaggruppDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrattoRaggruppDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrRaggrDTO;
import it.csi.rebus.anagraficasrv.vo.SubentroSubaffidamentoVO;

@Component
public class SostSogContrRaggrMapper extends ParentMapper implements EntityMapper<SirtplcRSostSogContrRaggrDTO, SubentroSubaffidamentoVO> {

	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;
	@Autowired
	private SirtplcRContrattoRaggruppDAO sirtplcRContrattoRaggruppDAO;
	@Autowired
	private SirtplcDTipoSostituzioneDAO sirtplcDTipoSostituzioneDAO;
	@Autowired
	private SoggettoSubentroMapper soggettoSubentroMapper;
	@Autowired
	private TipoSostituzioneMapper tipoSostituzioneMapper;

	@Override
	public SubentroSubaffidamentoVO mapDTOtoVO(SirtplcRSostSogContrRaggrDTO dto) {
		SubentroSubaffidamentoVO vo = new SubentroSubaffidamentoVO();
		vo.setId(dto.getIdSostSogContrRaggr());
		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(dto.getIdSoggettoGiuridico());
		vo.setSoggettoSubentrante(soggettoSubentroMapper.mapDTOtoVO(sirtplaTSoggettoGiuridicoDTO));
		SirtplcRContrattoRaggruppDTO sirtplcRContrattoRaggruppDTO = sirtplcRContrattoRaggruppDAO.selectByPrimaryKey(dto.getIdContrattoRaggrupp());
		sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(sirtplcRContrattoRaggruppDTO.getIdSoggettoGiuridico());
		vo.setSoggettoContraente(soggettoSubentroMapper.mapDTOtoVO(sirtplaTSoggettoGiuridicoDTO));
		vo.setContraenteGroup("R");
		vo.setAtto(dto.getAttoSostituzione());
		vo.setData(dto.getDataSostituzione());
		vo.setTipoSostituzione(tipoSostituzioneMapper.mapDTOtoVO(sirtplcDTipoSostituzioneDAO.selectByPrimaryKey(dto.getIdTipoSostituzione())));
		return vo;
	}

	@Override
	public SirtplcRSostSogContrRaggrDTO mapVOtoDTO(SubentroSubaffidamentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
