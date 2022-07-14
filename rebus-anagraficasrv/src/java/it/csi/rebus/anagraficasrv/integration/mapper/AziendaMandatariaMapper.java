/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.vo.AziendaMandatariaVO;

@Component
public class AziendaMandatariaMapper extends ParentMapper implements EntityMapper<SirtplaTSoggettoGiuridicoDTO, AziendaMandatariaVO> {

	@Override
	public AziendaMandatariaVO mapDTOtoVO(SirtplaTSoggettoGiuridicoDTO dto) {
		AziendaMandatariaVO vo = new AziendaMandatariaVO();
		vo.setId(dto.getIdSoggettoGiuridico());
		vo.setDescrizione(dto.getDenomSoggettoGiuridico());
		return vo;
	}

	@Override
	public SirtplaTSoggettoGiuridicoDTO mapVOtoDTO(AziendaMandatariaVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
