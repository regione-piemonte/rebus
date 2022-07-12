/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspDTransizioneAutomaDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.TransizioneAutomaVO;

@Component
public class TransizioneAutomaMapper extends ParentMapper
		implements EntityMapper<RebuspDTransizioneAutomaDTO, TransizioneAutomaVO> {

	@Override
	public TransizioneAutomaVO mapDTOtoVO(RebuspDTransizioneAutomaDTO dto) {
		TransizioneAutomaVO vo = new TransizioneAutomaVO();
		vo.setIdTransizioneAutoma(dto.getIdTransizioneAutoma());
		vo.setIdStatoIterDa(dto.getIdStatoIterDa());
		vo.setIdStatoIterA(dto.getIdStatoIterA());
		vo.setTitolo(dto.getTitolo());
		vo.setLabel(dto.getLabel());
		vo.setTesto(dto.getTesto());
		vo.setCondizioni(dto.getCondizioni());
		vo.setFlgNoteObbligatorie(dto.getFlgNoteObbligatorie());
		vo.setIdTipoMessaggio(dto.getIdTipoMessaggio());
		vo.setDataInizioValidita(dto.getDataInizioValidita());
		vo.setDataFineValidita(dto.getDataFineValidita());
		vo.setFlagDefault(dto.getFlagDefault());

		return vo;
	}

	@Override
	public RebuspDTransizioneAutomaDTO mapVOtoDTO(TransizioneAutomaVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
