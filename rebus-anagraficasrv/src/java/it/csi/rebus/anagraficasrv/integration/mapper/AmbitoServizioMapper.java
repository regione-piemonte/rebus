/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDAmbitoServizioDTO;
import it.csi.rebus.anagraficasrv.vo.AmbitoServizioVO;

@Component
public class AmbitoServizioMapper extends ParentMapper implements EntityMapper<SirtplcDAmbitoServizioDTO, AmbitoServizioVO> {

	@Override
	public AmbitoServizioVO mapDTOtoVO(SirtplcDAmbitoServizioDTO dto) {
		AmbitoServizioVO vo = new AmbitoServizioVO();
		vo.setId(dto.getIdAmbitoServizio());
		vo.setDescrizione(dto.getDescAmbitoServizio());
		return vo;
	}

	@Override
	public SirtplcDAmbitoServizioDTO mapVOtoDTO(AmbitoServizioVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
