/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.List;

import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaStoriaAutobusDTO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;

public interface ExcelDAO {
	public List<RebusTVariazAutobusDTO> getPrimoTelaioByPrimoTelaio(java.util.List input);
	public List<RebusTVariazAutobusDTO> getTargheByTarghe(java.util.List input);
	List<VExportRicercaAutobusDTO> getExcelReport(FiltroAutobusVO filtro);
	List<VExportRicercaStoriaAutobusDTO> getExcelReportStorico(FiltroAutobusVO filtro);

}