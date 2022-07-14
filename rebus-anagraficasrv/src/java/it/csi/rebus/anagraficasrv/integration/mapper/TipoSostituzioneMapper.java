/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoSostituzioneDTO;
import it.csi.rebus.anagraficasrv.vo.TipoSostituzioneVO;

@Component
public class TipoSostituzioneMapper extends ParentMapper implements EntityMapper<SirtplcDTipoSostituzioneDTO, TipoSostituzioneVO> {

	@Override
	public TipoSostituzioneVO mapDTOtoVO(SirtplcDTipoSostituzioneDTO dto) {
		TipoSostituzioneVO vo = new TipoSostituzioneVO();
		vo.setId(dto.getIdTipoSostituzione());
		vo.setDescrizione(dto.getDescTipoSostituzione());
		return vo;
	}

	@Override
	public SirtplcDTipoSostituzioneDTO mapVOtoDTO(TipoSostituzioneVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
