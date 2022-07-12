/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuscTAttoAssegnazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AttoAssegnazioneRisorseVO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;

@Component
public class AttoAssegnazioneRisorseMapper extends ParentMapper implements EntityMapper<RebuscTAttoAssegnazioneDTO, AttoAssegnazioneRisorseVO> {

	@Override
	public AttoAssegnazioneRisorseVO mapDTOtoVO(RebuscTAttoAssegnazioneDTO dto) {
		
//		AttoAssegnazioneRisorseVO vo = new AttoAssegnazioneRisorseVO();
//		vo.setDataFineValidita(dto.getDataFineValidita());
//		vo.setDataInizioValidita(dto.getDataInizioValidita());
//		vo.setDescBreve(dto.getDescBreve());
//		vo.setDescEstesa(dto.getDescEstesa());
//		vo.setIdAttoAssegnazione(dto.getIdAttoAssegnazione());
		if (dto == null) return null;
		return getDozerMapper().map(dto, AttoAssegnazioneRisorseVO.class);
	}

	@Override
	public RebuscTAttoAssegnazioneDTO mapVOtoDTO(AttoAssegnazioneRisorseVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
