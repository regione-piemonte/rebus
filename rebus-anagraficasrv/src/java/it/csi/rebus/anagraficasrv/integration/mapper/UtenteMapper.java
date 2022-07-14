/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplTUtenteDTO;
import it.csi.rebus.anagraficasrv.vo.FunzionarioVO;

@Component
public class UtenteMapper extends ParentMapper implements EntityMapper<SirtplTUtenteDTO, FunzionarioVO> {

	@Override
	public FunzionarioVO mapDTOtoVO(SirtplTUtenteDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, FunzionarioVO.class);
	}

	@Override
	public SirtplTUtenteDTO mapVOtoDTO(FunzionarioVO vo) {
		if (vo == null)
			return null;
		return getDozerMapper().map(vo, SirtplTUtenteDTO.class);
	}

}
