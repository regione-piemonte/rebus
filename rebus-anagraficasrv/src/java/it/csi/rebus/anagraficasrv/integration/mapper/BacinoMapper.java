/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDBacinoDTO;
import it.csi.rebus.anagraficasrv.vo.BacinoVO;

@Component
public class BacinoMapper extends ParentMapper implements EntityMapper<SirtplcDBacinoDTO, BacinoVO> {

	@Override
	public BacinoVO mapDTOtoVO(SirtplcDBacinoDTO dto) {
		BacinoVO vo = new BacinoVO();
		vo.setId(dto.getIdBacino());
		vo.setDescrizione(dto.getDenomBacino());
		return vo;
	}

	@Override
	public SirtplcDBacinoDTO mapVOtoDTO(BacinoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
