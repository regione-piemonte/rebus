/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrAmbTipServKey;
import it.csi.rebus.anagraficasrv.vo.ContrAmbTipServVO;

@Component
public class ContrattoAmbitoServizioMapper extends ParentMapper implements EntityMapper<SirtplcRContrAmbTipServKey, ContrAmbTipServVO> {

	@Override
	public ContrAmbTipServVO mapDTOtoVO(SirtplcRContrAmbTipServKey dto) {
		ContrAmbTipServVO vo = new ContrAmbTipServVO();
		vo.setIdContratto(dto.getIdContratto());
		vo.setIdAmbTipServizio(dto.getIdAmbTipServizio());
		return vo;
	
	}

	@Override
	public SirtplcRContrAmbTipServKey mapVOtoDTO(ContrAmbTipServVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
