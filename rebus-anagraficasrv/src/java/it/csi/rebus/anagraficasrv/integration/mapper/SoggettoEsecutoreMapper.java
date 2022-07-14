/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.vo.SoggettoEsecutoreVO;

@Component
public class SoggettoEsecutoreMapper extends ParentMapper implements EntityMapper<SirtplaTSoggettoGiuridicoDTO, SoggettoEsecutoreVO> {

	@Override
	public SoggettoEsecutoreVO mapDTOtoVO(SirtplaTSoggettoGiuridicoDTO dto) {
		SoggettoEsecutoreVO vo = new SoggettoEsecutoreVO();
		vo.setId(dto.getIdSoggettoGiuridico());
		vo.setIdTipoSoggettoEsecutore(dto.getIdTipoSogGiuridico());
		if (dto.getDenomSoggettoGiuridico() != null) {
			vo.setDenominazione(dto.getDenomSoggettoGiuridico());
		} else {
			vo.setDenominazione(dto.getDenominazioneBreve());
		}
		vo.setDenomBreve(dto.getDenominazioneBreve());
		vo.setDenomAaep(dto.getDenominazioneAaep());
		return vo;
	}

	@Override
	public SirtplaTSoggettoGiuridicoDTO mapVOtoDTO(SoggettoEsecutoreVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
