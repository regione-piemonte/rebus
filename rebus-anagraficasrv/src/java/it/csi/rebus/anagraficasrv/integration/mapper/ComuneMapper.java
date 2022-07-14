/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplDComuneDTO;
import it.csi.rebus.anagraficasrv.vo.ComuneVO;

@Component
public class ComuneMapper extends ParentMapper implements EntityMapper<SirtplDComuneDTO, ComuneVO> {

	@Override
	public ComuneVO mapDTOtoVO(SirtplDComuneDTO dto) {
		ComuneVO vo = new ComuneVO();
		vo.setId(dto.getIdComune());
		vo.setDenominazione(dto.getDenomComune());
		return vo;
	}

	@Override
	public SirtplDComuneDTO mapVOtoDTO(ComuneVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
