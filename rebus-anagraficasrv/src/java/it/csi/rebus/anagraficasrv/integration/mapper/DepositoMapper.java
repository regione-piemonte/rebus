/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.business.service.LuoghiService;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTDepositoDTO;
import it.csi.rebus.anagraficasrv.vo.DepositoVO;
import it.csi.rebus.anagraficasrv.vo.UbicazioneVO;

@Component
public class DepositoMapper extends ParentMapper implements EntityMapper<SirtplaTDepositoDTO, DepositoVO> {

	@Autowired
	private LuoghiService luoghiService;

	@Override
	public DepositoVO mapDTOtoVO(SirtplaTDepositoDTO dto) {
		DepositoVO vo = new DepositoVO();
		vo.setId(dto.getIdDeposito());
		vo.setDenominazione(dto.getDenomDeposito());
		vo.setTelefono(dto.getTelefonoDeposito());
		vo.setDepositoPrevalenteFlg(dto.getFlagDepositoPrevalente());
		UbicazioneVO ubicazione = new UbicazioneVO();
		ubicazione.setTop(dto.getToponimoDeposito());
		ubicazione.setIndirizzo(dto.getIndirizzoDeposito());
		ubicazione.setCivico(dto.getNumCivicoDeposito());
		ubicazione.setIdComune(dto.getIdComuneDeposito());
		if (dto.getIdComuneDeposito() != null) {
			ubicazione.setIdProvincia(luoghiService.getProvinciaByIdComune(dto.getIdComuneDeposito()).getId());
			ubicazione.setDescProvincia(luoghiService.getProvinciaByIdComune(dto.getIdComuneDeposito()).getSigla());
		}
		ubicazione.setCap(dto.getCapDeposito());
		vo.setUbicazione(ubicazione);
		return vo;
	}

	@Override
	public SirtplaTDepositoDTO mapVOtoDTO(DepositoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
