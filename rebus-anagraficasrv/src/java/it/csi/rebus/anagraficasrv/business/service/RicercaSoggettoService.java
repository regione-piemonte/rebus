/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import java.util.List;

import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoGiuridicoVO;

public interface RicercaSoggettoService {

	List<SoggettoGiuridicoVO> elencoSoggetto();
	List<SoggettoGiuridicoVO> filtraElencoSoggetto(FiltroSoggettoVO filtro);
}
