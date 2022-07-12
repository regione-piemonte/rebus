/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebusTUtentiDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.FunzionarioVO;

@Component
public class UtenteMapper extends ParentMapper implements EntityMapper<RebusTUtentiDTO, FunzionarioVO> {

	@Override
	public FunzionarioVO mapDTOtoVO(RebusTUtentiDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, FunzionarioVO.class);
	}

	@Override
	public RebusTUtentiDTO mapVOtoDTO(FunzionarioVO vo) {
		if (vo == null)
			return null;
		return getDozerMapper().map(vo, RebusTUtentiDTO.class);
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, AutobusDettagliatoVO.class);
	}

}
