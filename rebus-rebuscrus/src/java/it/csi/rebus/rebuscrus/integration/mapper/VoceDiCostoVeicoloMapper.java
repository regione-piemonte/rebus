/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspRVoceCostoVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoVeicoloVO;

@Component
public class VoceDiCostoVeicoloMapper extends ParentMapper implements EntityMapper<RebuspRVoceCostoVeicoloDTO, VoceDiCostoVeicoloVO> {

	@Override
	public VoceDiCostoVeicoloVO mapDTOtoVO(RebuspRVoceCostoVeicoloDTO dto) {
		VoceDiCostoVeicoloVO vo = new VoceDiCostoVeicoloVO();
		vo.setId(dto.getIdVoceCostoVeicolo());
		vo.setIdVoceCosto(dto.getIdVoceCosto());
		vo.setIdProcedimento(dto.getIdProcedimento());
		vo.setPrimoTelaio(dto.getPrimoTelaio());
		vo.setImporto(dto.getImporto());
		return vo;
	}

	@Override
	public RebuspRVoceCostoVeicoloDTO mapVOtoDTO(VoceDiCostoVeicoloVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
