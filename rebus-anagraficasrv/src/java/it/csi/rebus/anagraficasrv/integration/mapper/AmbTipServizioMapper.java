/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRAmbTipServizioDTO;
import it.csi.rebus.anagraficasrv.vo.AmbTipServizioVO;

@Component
public class AmbTipServizioMapper extends ParentMapper implements EntityMapper<SirtplcRAmbTipServizioDTO, AmbTipServizioVO> {

	@Override
	public AmbTipServizioVO mapDTOtoVO(SirtplcRAmbTipServizioDTO dto) {
		AmbTipServizioVO vo = new AmbTipServizioVO();
		vo.setIdAmbTipServizio(dto.getIdAmbTipServizio());
		return vo;
	}

	@Override
	public SirtplcRAmbTipServizioDTO mapVOtoDTO(AmbTipServizioVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
