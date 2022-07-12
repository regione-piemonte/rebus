/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;

@Component
public class DettaglioAutobusMapper extends ParentMapper implements EntityMapper<VAutobusDTO, AutobusVO> {

	@Override
	public AutobusVO mapDTOtoVO(VAutobusDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, AutobusVO.class);
	}

	// TODO: discuterne con andrea

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, AutobusDettagliatoVO.class);
	}

	@Override
	public VAutobusDTO mapVOtoDTO(AutobusVO vo) {
		if (vo == null)
			return null;
		return getDozerMapper().map(vo, VAutobusDTO.class);
	}

}
