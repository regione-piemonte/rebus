/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import java.util.List;

import it.csi.rebus.anagraficasrv.vo.EnteVO;
import it.csi.rebus.anagraficasrv.vo.TipoEnteVO;

public interface EnteService {

	List<TipoEnteVO> getTipiEnte();

	List<EnteVO> getEnti();

	List<TipoRaggruppamentoVO> getTipiRaggruppamento();
}
