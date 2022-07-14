/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.mapper;

public interface EntityMapper<DTOType, VOType> {

	public VOType mapDTOtoVO(DTOType dto);

	public DTOType mapVOtoDTO(VOType vo);
}
