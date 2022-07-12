/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspDStatoIterDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.StatoProcedimentoVO;

@Component
public class StatoProcedimentoMapper extends ParentMapper implements EntityMapper<RebuspDStatoIterDTO, StatoProcedimentoVO> {

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatoProcedimentoVO mapDTOtoVO(RebuspDStatoIterDTO dto) {
		StatoProcedimentoVO vo = new StatoProcedimentoVO();
		vo.setId(dto.getIdStatoIter());
		vo.setDescrizione(dto.getDescStatoIter());
		return vo;
	}

	@Override
	public RebuspDStatoIterDTO mapVOtoDTO(StatoProcedimentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}


}
