/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import it.csi.rebus.rebuscrus.integration.dto.VAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;

public interface EntityMapper<DTOType, VOType> {

	public VOType mapDTOtoVO(DTOType dto);

	public DTOType mapVOtoDTO(VOType vo);

	public AutobusDettagliatoVO mapDTOtoDettVO(VAutobusDTO dto);

}
