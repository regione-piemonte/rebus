/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplDProvinciaDTO;
import it.csi.rebus.anagraficasrv.vo.ProvinciaVO;

@Component
public class ProvinciaMapper extends ParentMapper implements EntityMapper<SirtplDProvinciaDTO, ProvinciaVO> {

	@Override
	public ProvinciaVO mapDTOtoVO(SirtplDProvinciaDTO dto) {
		ProvinciaVO vo = new ProvinciaVO();
		vo.setId(dto.getIdProvincia());
		vo.setDenominazione(dto.getDenomProvincia());
		vo.setSigla(dto.getSiglaProvincia());
		return vo;
	}

	@Override
	public SirtplDProvinciaDTO mapVOtoDTO(ProvinciaVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
