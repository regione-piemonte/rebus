/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTDatoBancarioDTO;
import it.csi.rebus.anagraficasrv.vo.DatiBancariVO;

@Component
public class DatoBancarioMapper extends ParentMapper implements EntityMapper<SirtplaTDatoBancarioDTO, DatiBancariVO> {

	@Override
	public DatiBancariVO mapDTOtoVO(SirtplaTDatoBancarioDTO dto) {
		DatiBancariVO vo = new DatiBancariVO();
		vo.setId(dto.getIdDatoBancario());
		vo.setIban(dto.getIban());
		vo.setNote(dto.getNote());
		vo.setDoatpl(dto.getDoatpl());
		return vo;
	}

	@Override
	public SirtplaTDatoBancarioDTO mapVOtoDTO(DatiBancariVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
