/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.TipoContrattoVO;

@Component
public class TipoContrattoMapper extends ParentMapper implements EntityMapper<RebuspDTipoContrattoDTO, TipoContrattoVO> {

	@Override
	public TipoContrattoVO mapDTOtoVO(RebuspDTipoContrattoDTO dto) {
		TipoContrattoVO vo = new TipoContrattoVO();
		vo.setId(dto.getIdTipoContratto());
		vo.setDescrizione(dto.getDescTipoContratto());
		return vo;
	}

	@Override
	public RebuspDTipoContrattoDTO mapVOtoDTO(TipoContrattoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
