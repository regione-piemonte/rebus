/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoDTO;
import it.csi.rebus.anagraficasrv.vo.TipoSoggettoGiuridicoVO;

@Component
public class TipoSogGiuridicoMapper extends ParentMapper implements EntityMapper<SirtplaDTipoSogGiuridicoDTO, TipoSoggettoGiuridicoVO> {

	@Override
	public TipoSoggettoGiuridicoVO mapDTOtoVO(SirtplaDTipoSogGiuridicoDTO dto) {
		TipoSoggettoGiuridicoVO vo = new TipoSoggettoGiuridicoVO();
		vo.setId(dto.getIdTipoSogGiuridico());
		vo.setDescrizione(dto.getDescTipoSogGiuridico());
		vo.setIdRuolo(dto.getIdRuoloSogGiuridico());
		return vo;
	}

	@Override
	public SirtplaDTipoSogGiuridicoDTO mapVOtoDTO(TipoSoggettoGiuridicoVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
