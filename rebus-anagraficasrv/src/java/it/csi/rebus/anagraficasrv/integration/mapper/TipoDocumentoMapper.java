/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.RebusDTipoDocumentoDTO;
import it.csi.rebus.anagraficasrv.vo.TipoDocumentoVO;

@Component
public class TipoDocumentoMapper extends ParentMapper implements EntityMapper<RebusDTipoDocumentoDTO, TipoDocumentoVO> {

	@Override
	public TipoDocumentoVO mapDTOtoVO(RebusDTipoDocumentoDTO dto) {
		TipoDocumentoVO vo = new TipoDocumentoVO();
		vo.setId(dto.getIdTipoDocumento());
		vo.setDescrizione(dto.getDescrizione());
		vo.setOrdinamento(dto.getOrdinamento());
		vo.setDescrizioneEstesa(dto.getDescEstesa());
		return vo;
	}

	@Override
	public RebusDTipoDocumentoDTO mapVOtoDTO(TipoDocumentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
