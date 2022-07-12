/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AziendaVO;

@Component
public class AziendaMapper extends ParentMapper implements EntityMapper<RebusTAziendeDTO, AziendaVO> {

	@Override
	public AziendaVO mapDTOtoVO(RebusTAziendeDTO dto) {
		AziendaVO vo = new AziendaVO();
		vo.setId(dto.getIdAzienda());
		vo.setDescrizione(dto.getDenominazione());
		vo.setRagioneSociale(dto.getNaturaGiuridica());
		return vo;
	}

	@Override
	public RebusTAziendeDTO mapVOtoDTO(AziendaVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
