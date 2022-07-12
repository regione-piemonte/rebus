/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspTSubProcedimentoKey;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.SubProcedimentoVO;

@Component
public class SubProcedimentoMapper extends ParentMapper implements EntityMapper<RebuspTSubProcedimentoKey, SubProcedimentoVO> {

	@Override
	public SubProcedimentoVO mapDTOtoVO(RebuspTSubProcedimentoKey dto) {
		SubProcedimentoVO vo = new SubProcedimentoVO();
		vo.setIdProcedimento(dto.getIdProcedimento());
		vo.setIdSubProcedimento1(dto.getIdSubProcedimento1());
		vo.setIdSubProcedimento2(dto.getIdSubProcedimento2());
		return vo;
	}

	@Override
	public RebuspTSubProcedimentoKey mapVOtoDTO(SubProcedimentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
