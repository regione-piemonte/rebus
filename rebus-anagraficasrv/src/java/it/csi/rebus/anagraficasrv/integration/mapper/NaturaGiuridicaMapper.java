/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDNaturaGiuridicaDTO;
import it.csi.rebus.anagraficasrv.vo.NaturaGiuridicaVO;

@Component
public class NaturaGiuridicaMapper extends ParentMapper implements EntityMapper<SirtplaDNaturaGiuridicaDTO, NaturaGiuridicaVO> {

	@Override
	public NaturaGiuridicaVO mapDTOtoVO(SirtplaDNaturaGiuridicaDTO dto) {
		NaturaGiuridicaVO vo = new NaturaGiuridicaVO();
		vo.setId(dto.getIdNaturaGiuridica());
		vo.setDescrizione(dto.getDescNaturaGiuridica());
		vo.setDescrizioneBreve(dto.getDescBreveNaturaGiuridica());
		return vo;
	}

	@Override
	public SirtplaDNaturaGiuridicaDTO mapVOtoDTO(NaturaGiuridicaVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
