/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;

public interface ExcelSoggettoService {

	public byte[] exportRicercaSoggetto(FiltroSoggettoVO filtro);
}
