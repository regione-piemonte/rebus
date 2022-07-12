/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dao.SirtplcTContrattoDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcVO;

@Component
public class ContrattoProcMapper extends ParentMapper implements EntityMapper<RebuspRProcContrattoDTO, ContrattoProcVO> {

	@Autowired
	private SirtplcTContrattoDAO sirtplcTContrattoDAO;

	@Override
	public ContrattoProcVO mapDTOtoVO(RebuspRProcContrattoDTO dto) {
		ContrattoProcVO vo = new ContrattoProcVO();
		vo.setIdProcContratto(dto.getIdProcContratto());
		vo.setIdContratto(dto.getIdContratto());
		vo.setIdProcedimento(dto.getIdProcedimento());
		vo.setIdProcedimento(dto.getIdProcedimento());
		vo.setIdTipoContratto(dto.getIdTipoContratto());
		SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO.selectByPrimaryKey(dto.getIdContratto());
		vo.setCodIdNazionale(sirtplcTContrattoDTO.getCodIdNazionale());
		vo.setDescrizione(sirtplcTContrattoDTO.getDescContratto());
		return vo;
	}

	@Override
	public RebuspRProcContrattoDTO mapVOtoDTO(ContrattoProcVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
