/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.business.service.TipoRaggruppamentoVO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoRaggruppamentoDTO;

@Component
public class TipoRaggruppamentoMapper extends ParentMapper implements EntityMapper<SirtplcDTipoRaggruppamentoDTO, TipoRaggruppamentoVO> {

	@Override
	public TipoRaggruppamentoVO mapDTOtoVO(SirtplcDTipoRaggruppamentoDTO dto) {
		TipoRaggruppamentoVO vo = new TipoRaggruppamentoVO();
		vo.setId(dto.getIdTipoRaggruppamento());
		vo.setDescrizione(dto.getDescTipoRaggruppamento());
		return vo;
	}

	@Override
	public SirtplcDTipoRaggruppamentoDTO mapVOtoDTO(TipoRaggruppamentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
