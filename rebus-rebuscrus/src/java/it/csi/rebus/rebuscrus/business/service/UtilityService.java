/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.List;

import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensDTO;
import it.csi.rebus.rebuscrus.vo.ComboVO;
import it.csi.rebus.rebuscrus.vo.ContestoVO;

public interface UtilityService {

	List<ComboVO> getDecodificheCombo();

	RebusDTipologiaDimensDTO getTipologiaDimensionale(Long idTipoAllestimento, Double lunghezza);

	List<ContestoVO> getElencoContesto();
	
	ContestoVO getContestoHome();
	
}