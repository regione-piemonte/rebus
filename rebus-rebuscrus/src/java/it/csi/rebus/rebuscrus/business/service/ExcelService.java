/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.io.InputStream;

import it.csi.rebus.rebuscrus.vo.ExcelVO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;

public interface ExcelService {

	ExcelVO readExcelAndWriteOnDB(InputStream excelFileToRead, InputStream excelFileTemplate);
	public byte[] exportRicercaAutobus(FiltroAutobusVO filtro);
	public byte[] excelRicercaContribuzione(FiltroContribuzioneVO filtro);
}