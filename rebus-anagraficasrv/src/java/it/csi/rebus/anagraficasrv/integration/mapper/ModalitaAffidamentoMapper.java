/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDModalitaAffidamentoDTO;
import it.csi.rebus.anagraficasrv.vo.ModalitaAffidamentoVO;

@Component
public class ModalitaAffidamentoMapper extends ParentMapper implements EntityMapper<SirtplcDModalitaAffidamentoDTO, ModalitaAffidamentoVO> {

	@Override
	public ModalitaAffidamentoVO mapDTOtoVO(SirtplcDModalitaAffidamentoDTO dto) {
		ModalitaAffidamentoVO vo = new ModalitaAffidamentoVO();
		vo.setId(dto.getIdModalitaAffidamento());
		vo.setDescrizione(dto.getDescModalitaAffidamento());
		return vo;
	}

	@Override
	public SirtplcDModalitaAffidamentoDTO mapVOtoDTO(ModalitaAffidamentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
