/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuscDVoceCostoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoVO;

@Component
public class VoceDiCostoMapper extends ParentMapper implements EntityMapper<RebuscDVoceCostoDTO, VoceDiCostoVO> {

	@Override
	public VoceDiCostoVO mapDTOtoVO(RebuscDVoceCostoDTO dto) {
		VoceDiCostoVO vo = new VoceDiCostoVO();
		vo.setId(dto.getIdVoceCosto());
		vo.setDescrizione(dto.getDescVoceCosto());
		return vo;
	}

	@Override
	public RebuscDVoceCostoDTO mapVOtoDTO(VoceDiCostoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
