/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dao.RebusTUtentiDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDStatoIterDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplRUtenteSogGiuridDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTUtentiDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTUtentiSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTIterProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplRUtenteSogGiuridDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.IterProcedimentoVO;

@Component
public class IterProcedimentoMapper extends ParentMapper implements EntityMapper<RebuspTIterProcedimentoDTO, IterProcedimentoVO> {

	@Autowired
	private RebuspDStatoIterDAO rebuspDStatoIterDAO;
	@Autowired
	private RebusTUtentiDAO rebusTUtentiDAO;
	@Autowired
	private SirtplRUtenteSogGiuridDAO sirtplRUtenteSogGiuridDAO;

	@Override
	public IterProcedimentoVO mapDTOtoVO(RebuspTIterProcedimentoDTO dto) {
		IterProcedimentoVO vo = new IterProcedimentoVO();
		vo.setId(dto.getIdIterProcedimento());
		vo.setIdProcedimento(dto.getIdProcedimento());
		vo.setIdStato(dto.getIdStatoIter());
		vo.setDescStato(rebuspDStatoIterDAO.selectByPrimaryKey(dto.getIdStatoIter()).getDescStatoIter());
		vo.setDataInizioValidita(dto.getDataInizioValidita());
		vo.setDataFineValidita(dto.getDataFineValidita());
		SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDAO.selectByPrimaryKey(dto.getIdUtenteSogGiurid());
		RebusTUtentiSelector rebusTUtentiSelector = new RebusTUtentiSelector();
		rebusTUtentiSelector.createCriteria().andIdUtenteEqualTo(sirtplRUtenteSogGiuridDTO.getIdUtente());
		List<RebusTUtentiDTO> utenti = rebusTUtentiDAO.selectByExample(rebusTUtentiSelector);
		if (utenti != null && utenti.size() > 0) {
			vo.setUtente(utenti.get(0).getNome() + " " + utenti.get(0).getCognome());
		}
		vo.setNote(dto.getNote());
		return vo;
	}

	@Override
	public RebuspTIterProcedimentoDTO mapVOtoDTO(IterProcedimentoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
