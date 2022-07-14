/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDNaturaGiuridicaDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDNaturaGiuridicaDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.vo.AziendaVO;

@Component
public class AziendaMapper extends ParentMapper implements EntityMapper<SirtplaTSoggettoGiuridicoDTO, AziendaVO> {

	@Autowired
	private SirtplaDNaturaGiuridicaDAO sirtplaDNaturaGiuridicaDAO;

	@Override
	public AziendaVO mapDTOtoVO(SirtplaTSoggettoGiuridicoDTO dto) {
		AziendaVO vo = new AziendaVO();
		vo.setId(dto.getIdSoggettoGiuridico());
		vo.setDescrizione(dto.getDenominazioneBreve());
		SirtplaDNaturaGiuridicaDTO sirtplaDNaturaGiuridicaDTO = sirtplaDNaturaGiuridicaDAO.selectByPrimaryKey(dto.getIdNaturaGiuridica());
		vo.setRagioneSociale(sirtplaDNaturaGiuridicaDTO.getDescNaturaGiuridica());
		return vo;
	}

	@Override
	public SirtplaTSoggettoGiuridicoDTO mapVOtoDTO(AziendaVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
