/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.TipoProcedimentoVO;

@Component
public class TipoProcedimentoMapper extends ParentMapper implements EntityMapper<RebuspDTipoProcedimentoDTO, TipoProcedimentoVO> {

	@Override
	public TipoProcedimentoVO mapDTOtoVO(RebuspDTipoProcedimentoDTO dto) {
		TipoProcedimentoVO vo = new TipoProcedimentoVO();
		vo.setId(dto.getIdTipoProcedimento());
		vo.setDescrizione(dto.getDescTipoProcedimento());
		vo.setCodProcedimento(dto.getCodTipoProcedimento());
		return vo;
	}

	@Override
	public RebuspDTipoProcedimentoDTO mapVOtoDTO(TipoProcedimentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
