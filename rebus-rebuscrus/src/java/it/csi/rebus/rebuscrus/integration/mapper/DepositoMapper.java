/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.service.LuoghiService;
import it.csi.rebus.rebuscrus.integration.dao.SirtplDComuneDAO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplDComuneDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaTDepositoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.DepositoVO;
import it.csi.rebus.rebuscrus.vo.ProvinciaVO;

@Component
public class DepositoMapper extends ParentMapper implements EntityMapper<SirtplaTDepositoDTO, DepositoVO> {

	@Autowired
	private SirtplDComuneDAO sirtplDComuneDAO;
	@Autowired
	private LuoghiService luoghiService;

	@Override
	public DepositoVO mapDTOtoVO(SirtplaTDepositoDTO dto) {
		DepositoVO vo = new DepositoVO();
		vo.setId(dto.getIdDeposito());
		vo.setDenominazione(dto.getDenomDeposito());
		vo.setTelefono(dto.getTelefonoDeposito());
		vo.setIsPrevalente(dto.getFlagDepositoPrevalente());
		SirtplDComuneDTO sirtplDComuneDTO = null;
		ProvinciaVO provinciaVO = null;
		if (dto.getIdComuneDeposito() != null) {
			sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(dto.getIdComuneDeposito());
			provinciaVO = luoghiService.getProvinciaByIdComune(dto.getIdComuneDeposito());
		}
		vo.setIndirizzo(getIndirizzoCompleto(dto.getToponimoDeposito(), dto.getIndirizzoDeposito(), dto.getNumCivicoDeposito(), dto.getCapDeposito(),
				sirtplDComuneDTO != null ? sirtplDComuneDTO.getDenomComune() : null, provinciaVO != null ? provinciaVO.getSigla() : null));
		return vo;
	}

	@Override
	public SirtplaTDepositoDTO mapVOtoDTO(DepositoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getIndirizzoCompleto(String top, String indirizzo, String civico, String cap, String denomComune, String siglaProvincia) {
		String s = "";
		boolean minus = false;
		if (top != null) {
			s += top + " ";
			minus = true;
		}
		if (indirizzo != null) {
			s += indirizzo + " ";
			minus = true;
		}
		if (civico != null) {
			s += civico + " ";
			minus = true;
		}
		if (minus && (cap != null || denomComune != null))
			s += "- ";
		if (cap != null)
			s += cap + " ";
		if (denomComune != null)
			s += denomComune;
		if (siglaProvincia != null)
			s += " (" + siglaProvincia + ")";

		return s;
	}

}
