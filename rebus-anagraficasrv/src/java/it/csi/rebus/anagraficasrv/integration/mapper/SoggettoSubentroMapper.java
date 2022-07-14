/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.vo.SoggettoSubentroVO;

@Component
public class SoggettoSubentroMapper extends ParentMapper implements EntityMapper<SirtplaTSoggettoGiuridicoDTO, SoggettoSubentroVO> {

	@Override
	public SoggettoSubentroVO mapDTOtoVO(SirtplaTSoggettoGiuridicoDTO dto) {
		SoggettoSubentroVO vo = new SoggettoSubentroVO();
		vo.setId(dto.getIdSoggettoGiuridico());
		vo.setDenomBreve(dto.getDenominazioneBreve());
		vo.setDenomSoggGiurid(dto.getDenomSoggettoGiuridico());
		vo.setDenomAaep(dto.getDenominazioneAaep());
		vo.setIdTipoSoggGiurid(dto.getIdTipoSogGiuridico());
		return vo;
	}

	@Override
	public SirtplaTSoggettoGiuridicoDTO mapVOtoDTO(SoggettoSubentroVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
