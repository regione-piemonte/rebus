/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoEnteDTO;
import it.csi.rebus.anagraficasrv.vo.TipoEnteVO;

@Component
public class TipoEnteMapper extends ParentMapper implements EntityMapper<SirtplaDTipoEnteDTO, TipoEnteVO> {

	@Override
	public TipoEnteVO mapDTOtoVO(SirtplaDTipoEnteDTO dto) {
		TipoEnteVO vo = new TipoEnteVO();
		vo.setId(dto.getIdTipoEnte());
		vo.setDescrizione(dto.getDescTipoEnte());
		return vo;
	}

	@Override
	public SirtplaDTipoEnteDTO mapVOtoDTO(TipoEnteVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
