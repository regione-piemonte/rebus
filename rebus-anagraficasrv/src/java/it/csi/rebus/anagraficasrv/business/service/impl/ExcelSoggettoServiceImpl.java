/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.business.manager.ExcelSoggettoReportManager;
import it.csi.rebus.anagraficasrv.business.service.ExcelSoggettoService;
import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;

@Component
public class ExcelSoggettoServiceImpl implements ExcelSoggettoService {

	@Autowired
	private ExcelSoggettoReportManager excelReportManager;

	public byte[] exportRicercaSoggetto(FiltroSoggettoVO filtro) {
		return excelReportManager.exportRicercaSoggetto(filtro);
	}
}
