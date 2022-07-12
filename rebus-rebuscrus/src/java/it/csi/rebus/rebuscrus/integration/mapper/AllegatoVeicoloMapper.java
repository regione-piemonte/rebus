/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoDocumentoDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AllegatoVeicoloVO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;

@Component
public class AllegatoVeicoloMapper extends ParentMapper implements EntityMapper<RebusRDocVariazAutobusDTO, AllegatoVeicoloVO> {

	@Autowired
	private RebusDTipoDocumentoDAO rebusDTipoDocumentoDAO;
	@Autowired
	private TipoDocumentoMapper tipoDocumentoMapper;

	@Override
	public AllegatoVeicoloVO mapDTOtoVO(RebusRDocVariazAutobusDTO dto) {
		AllegatoVeicoloVO vo = new AllegatoVeicoloVO();
		vo.setNomeFile(dto.getNomeFile());
		vo.setDataCaricamento(dto.getDataCaricamento());
		vo.setNote(dto.getNote());

		RebusDTipoDocumentoDTO rebusDTipoDocumentoDTO = rebusDTipoDocumentoDAO.selectByPrimaryKey(dto.getIdTipoDocumento());
		vo.setTipoDocumento(tipoDocumentoMapper.mapDTOtoVO(rebusDTipoDocumentoDTO));
		return vo;
	}

	@Override
	public RebusRDocVariazAutobusDTO mapVOtoDTO(AllegatoVeicoloVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
