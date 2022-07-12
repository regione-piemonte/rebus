/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.integration.dto.RebusTStoriaVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;

/**
 * @author riccardo.bova
 * @date 02 mar 2018
 */
@Component
public class StoricizzaMapper extends ParentMapper {

	public RebusTVariazAutobusDTO mapTStoriaVartoTVariaz(RebusTStoriaVariazAutobusDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, RebusTVariazAutobusDTO.class);
	}

	public RebusTStoriaVariazAutobusDTO mapTVariazToTStoriaVar(RebusTVariazAutobusDTO dto) {
		if (dto == null)
			return null;
		return getDozerMapper().map(dto, RebusTStoriaVariazAutobusDTO.class);
	}

}
