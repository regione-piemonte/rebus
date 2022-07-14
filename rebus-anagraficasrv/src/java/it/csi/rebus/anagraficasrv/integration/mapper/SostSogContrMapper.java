/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDTipoSostituzioneDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcTContrattoDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.anagraficasrv.vo.SubentroSubaffidamentoVO;

@Component
public class SostSogContrMapper extends ParentMapper implements EntityMapper<SirtplcRSostSogContrDTO, SubentroSubaffidamentoVO> {

	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;
	@Autowired
	private SirtplcTContrattoDAO sirtplcTContrattoDAO;
	@Autowired
	private SirtplcDTipoSostituzioneDAO sirtplcDTipoSostituzioneDAO;
	@Autowired
	private SoggettoSubentroMapper soggettoSubentroMapper;
	@Autowired
	private TipoSostituzioneMapper tipoSostituzioneMapper;

	@Override
	public SubentroSubaffidamentoVO mapDTOtoVO(SirtplcRSostSogContrDTO dto) {
		SubentroSubaffidamentoVO vo = new SubentroSubaffidamentoVO();
		vo.setId(dto.getIdSostSogContr());
		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(dto.getIdSogGiuridCommittente() != null ? dto.getIdSogGiuridCommittente() : dto.getIdSogGiuridEsecutore());
		vo.setSoggettoSubentrante(soggettoSubentroMapper.mapDTOtoVO(sirtplaTSoggettoGiuridicoDTO));
		SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO.selectByPrimaryKey(dto.getIdContratto());
		sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(dto.getIdSogGiuridCommittente() != null ? sirtplcTContrattoDTO.getIdSogGiuridCommittente() : sirtplcTContrattoDTO.getIdSogGiuridEsecutore());
		vo.setSoggettoContraente(soggettoSubentroMapper.mapDTOtoVO(sirtplaTSoggettoGiuridicoDTO));
		vo.setContraenteGroup(dto.getIdSogGiuridCommittente() != null ? "EC" : "ET");
		vo.setAtto(dto.getAttoSostituzione());
		vo.setData(dto.getDataSostituzione());
		vo.setDataFine(dto.getDataFineSostituzione());
		vo.setTipoSostituzione(tipoSostituzioneMapper.mapDTOtoVO(sirtplcDTipoSostituzioneDAO.selectByPrimaryKey(dto.getIdTipoSostituzione())));
		return vo;
	}

	@Override
	public SirtplcRSostSogContrDTO mapVOtoDTO(SubentroSubaffidamentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
