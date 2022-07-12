/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebusmTMisurazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.VeicoloDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.MisurazioniVO;
import it.csi.rebus.rebuscrus.vo.VeicoloVO;

@Component
public class MisurazioniMapper extends ParentMapper implements EntityMapper<RebusmTMisurazioneDTO, MisurazioniVO> {

	@Override
	public MisurazioniVO mapDTOtoVO(RebusmTMisurazioneDTO dto) {
		MisurazioniVO vo = new MisurazioniVO();
		vo.setIdMisurazioni(dto.getIdMisurazione());
		vo.setDataFine(dto.getDataFine());
		vo.setDataInizio(dto.getDataInizio());
		vo.setPrimoTelaio(dto.getPrimoTelaio());
		vo.setDataAggiornamento(dto.getDataAggiornamento());
		vo.setIdDocMisurazione(dto.getIdDocMisurazione());
		vo.setIdUtenteAggiornamento(dto.getIdUtenteAggiornamento());

		return vo;
	}

	@Override
	public RebusmTMisurazioneDTO mapVOtoDTO(MisurazioniVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}



}
