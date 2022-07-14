/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoAffidamentoDTO;
import it.csi.rebus.anagraficasrv.vo.TipoAffidamentoVO;

@Component
public class TipoAffidamentoMapper extends ParentMapper implements EntityMapper<SirtplcDTipoAffidamentoDTO, TipoAffidamentoVO> {

	@Override
	public TipoAffidamentoVO mapDTOtoVO(SirtplcDTipoAffidamentoDTO dto) {
		TipoAffidamentoVO vo = new TipoAffidamentoVO();
		vo.setId(dto.getIdTipoAffidamento());
		vo.setDescrizione(dto.getDescTipoAffidamento());
		return vo;
	}

	@Override
	public SirtplcDTipoAffidamentoDTO mapVOtoDTO(TipoAffidamentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
