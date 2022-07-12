/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.ResponseExcelDto;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.ExcelVO;

@Component
public class ExcelResponseMapper extends ParentMapper implements EntityMapper<ResponseExcelDto, ExcelVO> {

	@Override
	public ExcelVO mapDTOtoVO(ResponseExcelDto dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, ExcelVO.class);
	}

	@Override
	public ResponseExcelDto mapVOtoDTO(ExcelVO vo) {
		if (vo == null)
			return null;
		return getDozerMapper().map(vo, ResponseExcelDto.class);
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, AutobusDettagliatoVO.class);
	}

}
