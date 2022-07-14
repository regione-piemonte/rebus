/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipologiaServizioDTO;
import it.csi.rebus.anagraficasrv.vo.TipologiaServizioVO;

@Component
public class TipologiaServizioMapper extends ParentMapper implements EntityMapper<SirtplcDTipologiaServizioDTO, TipologiaServizioVO> {

	@Override
	public TipologiaServizioVO mapDTOtoVO(SirtplcDTipologiaServizioDTO dto) {
		TipologiaServizioVO vo = new TipologiaServizioVO();
		vo.setId(dto.getIdTipologiaServizio());
		vo.setDescrizione(dto.getDescTipologiaServizio());
		return vo;
	}

	@Override
	public SirtplcDTipologiaServizioDTO mapVOtoDTO(TipologiaServizioVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
