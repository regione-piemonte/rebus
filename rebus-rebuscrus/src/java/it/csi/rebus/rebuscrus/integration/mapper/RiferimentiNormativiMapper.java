/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebuspTDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.RiferimentiNormativiVO;


@Component
public class RiferimentiNormativiMapper extends ParentMapper implements EntityMapper<RebuspTDocumentoDTO, RiferimentiNormativiVO> {

	@Override
	public RiferimentiNormativiVO mapDTOtoVO(RebuspTDocumentoDTO dto) {
		RiferimentiNormativiVO vo = new RiferimentiNormativiVO();
		vo.setIdDocumento(dto.getIdDocumento());
		vo.setDescrizione(dto.getDescrizione());
		vo.setDataCaricamento(dto.getDataInserimento());
		vo.setNomeFile(dto.getNomeFile());
		if(dto.getNomeFile().toLowerCase().matches(".*\\.do.*")) {
			vo.setTipo(Constants.IS_DOCUMENTO_WORD);
		}
		else if(dto.getNomeFile().toLowerCase().matches(".*\\.pdf.*")) {
			vo.setTipo(Constants.IS_DOCUMENTO_PDF);
		}
		else if(dto.getNomeFile().toLowerCase().matches(".*\\.txt.*")) {
			vo.setTipo(Constants.IS_DOCUMENTO_TXT);
		}
		else {
			vo.setTipo(Long.valueOf(-1));
		}

		return vo;
	}

	@Override
	public RebuspTDocumentoDTO mapVOtoDTO(RiferimentiNormativiVO vo) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
