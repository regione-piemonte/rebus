/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrattoRaggruppDTO;
import it.csi.rebus.anagraficasrv.vo.ContrattoRaggruppamentoVO;

@Component
public class ContrattoRaggruppamentoMapper extends ParentMapper implements EntityMapper<SirtplcRContrattoRaggruppDTO, ContrattoRaggruppamentoVO> {

	@Override
	public ContrattoRaggruppamentoVO mapDTOtoVO(SirtplcRContrattoRaggruppDTO dto) {
		ContrattoRaggruppamentoVO vo = new ContrattoRaggruppamentoVO();
		vo.setIdContrattoRaggruppamento(dto.getIdContrattoRaggrupp());
		vo.setIdContratto(dto.getIdContratto());
		vo.setIdSoggettoGiuridico(dto.getIdSoggettoGiuridico());
		vo.setCapofila(dto.getCapofila());
		return vo;
	}

	@Override
	public SirtplcRContrattoRaggruppDTO mapVOtoDTO(ContrattoRaggruppamentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
