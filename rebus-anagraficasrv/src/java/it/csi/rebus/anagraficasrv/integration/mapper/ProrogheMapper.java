/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTProrogaContrattoDTO;
import it.csi.rebus.anagraficasrv.vo.ProrogaVO;

@Component
public class ProrogheMapper extends ParentMapper implements EntityMapper<SirtplcTProrogaContrattoDTO, ProrogaVO> {

	@Override
	public ProrogaVO mapDTOtoVO(SirtplcTProrogaContrattoDTO dto) {
		ProrogaVO vo = new ProrogaVO();
		vo.setIdProroga(dto.getIdProrogaContratto());
		vo.setIdContratto(dto.getIdContratto());
		vo.setAttoProroga(dto.getAttoProroga());
		vo.setDataFineProroga(dto.getDataFineProroga());
		return vo;
	}

	@Override
	public SirtplcTProrogaContrattoDTO mapVOtoDTO(ProrogaVO vo) {
		SirtplcTProrogaContrattoDTO dto = new SirtplcTProrogaContrattoDTO();
		dto.setIdProrogaContratto(vo.getIdProroga());
		dto.setIdContratto(vo.getIdContratto());
		dto.setAttoProroga(vo.getAttoProroga());
		dto.setDataFineProroga(vo.getDataFineProroga());
		return dto;
	}

}
