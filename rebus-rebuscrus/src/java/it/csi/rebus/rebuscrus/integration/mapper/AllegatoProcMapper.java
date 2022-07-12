/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoDocumentoDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AllegatoProcVO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;

@Component
public class AllegatoProcMapper extends ParentMapper implements EntityMapper<RebuspRProcDocumentoDTO, AllegatoProcVO> {
	@Autowired
	private RebusDTipoDocumentoDAO rebusDTipoDocumentoDAO;
	@Autowired
	private TipoDocumentoMapper tipoDocumentoMapper;

	@Override
	public AllegatoProcVO mapDTOtoVO(RebuspRProcDocumentoDTO dto) {
		AllegatoProcVO vo = new AllegatoProcVO();
		vo.setIdProcedimento(dto.getIdProcedimento());
		vo.setTipoDocumento(tipoDocumentoMapper.mapDTOtoVO(rebusDTipoDocumentoDAO.selectByPrimaryKey(dto.getIdTipoDocumento())));
		vo.setDataCaricamento(dto.getDataCaricamento());
		vo.setNomeFile(dto.getNomeFile());
		vo.setNote(dto.getNote());
		return vo;
	}

	@Override
	public RebuspRProcDocumentoDTO mapVOtoDTO(AllegatoProcVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
