/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import it.csi.rebus.rebuscrus.integration.dto.custom.VeicoliDTO;

public interface VeicoliDAO {

	public List<VeicoliDTO> getViewVeicoli(@Param("id") Long id);

}