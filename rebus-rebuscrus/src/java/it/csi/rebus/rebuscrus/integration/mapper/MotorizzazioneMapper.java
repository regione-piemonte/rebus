/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotorizzazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.MotorizzazioneVO;

@Component
public class MotorizzazioneMapper extends ParentMapper implements EntityMapper<RebuspDMotorizzazioneDTO, MotorizzazioneVO> {

	@Override
	public MotorizzazioneVO mapDTOtoVO(RebuspDMotorizzazioneDTO dto) {
		MotorizzazioneVO vo = new MotorizzazioneVO();
		vo.setId(dto.getIdMotorizzazione());
		vo.setDescrizione(dto.getDescMotorizzazione()+" - "+dto.getIndirizzo());
		return vo;
	}

	@Override
	public RebuspDMotorizzazioneDTO mapVOtoDTO(MotorizzazioneVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
