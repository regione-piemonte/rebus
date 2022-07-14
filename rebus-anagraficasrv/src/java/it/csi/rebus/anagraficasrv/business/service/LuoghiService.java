/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import java.util.List;

import it.csi.rebus.anagraficasrv.vo.ComuneVO;
import it.csi.rebus.anagraficasrv.vo.ProvinciaVO;

public interface LuoghiService {
	List<ProvinciaVO> getProvince();

	List<ComuneVO> getComuniByIdProvincia(Long idProvincia);

	List<ComuneVO> getComuni();

	ProvinciaVO getProvinciaByIdComune(Long idComune);
}
