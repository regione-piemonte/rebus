/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotivazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.integration.mapper.EntityMapper;
import it.csi.rebus.rebuscrus.integration.mapper.ParentMapper;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.MotivazioneVO;

@Component
public class MotivazioneMapper extends ParentMapper implements EntityMapper<RebuspDMotivazioneDTO, MotivazioneVO> {

	@Override
	public MotivazioneVO mapDTOtoVO(RebuspDMotivazioneDTO dto) {
		MotivazioneVO vo = new MotivazioneVO();
		vo.setId(dto.getIdMotivazione());
		vo.setDescrizione(dto.getDescMotivazione());
		vo.setFlgMotivAltro(dto.getFlgMotivAltro());
		return vo;
	}

	@Override
	public RebuspDMotivazioneDTO mapVOtoDTO(MotivazioneVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
