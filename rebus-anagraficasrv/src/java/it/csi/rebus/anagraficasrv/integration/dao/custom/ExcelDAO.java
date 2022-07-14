/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.dao.custom;

import java.util.List;

import it.csi.rebus.anagraficasrv.integration.dto.VExportRicercaContrattiDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VExportRicercaSoggGiurDTO;
import it.csi.rebus.anagraficasrv.vo.FiltroContrattoVO;
import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;

public interface ExcelDAO {

	List<VExportRicercaContrattiDTO> getExcelContrattoReport(FiltroContrattoVO filtro);

	List<VExportRicercaSoggGiurDTO> getExcelSoggettoReport(FiltroSoggettoVO filtro);
	
}
