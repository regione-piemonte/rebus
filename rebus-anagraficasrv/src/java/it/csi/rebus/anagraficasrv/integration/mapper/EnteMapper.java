/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.vo.EnteVO;

@Component
public class EnteMapper extends ParentMapper implements EntityMapper<SirtplaTSoggettoGiuridicoDTO, EnteVO> {

	@Override
	public EnteVO mapDTOtoVO(SirtplaTSoggettoGiuridicoDTO dto) {
		EnteVO vo = new EnteVO();
		vo.setId(dto.getIdSoggettoGiuridico());
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
	public SirtplaTSoggettoGiuridicoDTO mapVOtoDTO(EnteVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
