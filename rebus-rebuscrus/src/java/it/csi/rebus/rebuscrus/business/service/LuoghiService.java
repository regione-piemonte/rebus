/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import it.csi.rebus.rebuscrus.vo.ProvinciaVO;

public interface LuoghiService {

	ProvinciaVO getProvinciaByIdComune(Long idComune);
}
