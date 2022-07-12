/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.List;

import it.csi.rebus.rebuscrus.integration.dto.custom.CronologiaDTO;

public interface CronologiaDAO {
	public List<CronologiaDTO> getCronologia(String primoTelaio);

}
