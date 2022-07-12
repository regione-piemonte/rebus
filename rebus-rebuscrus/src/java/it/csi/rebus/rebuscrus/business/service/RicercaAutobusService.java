/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.List;

import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaAutobusDTO;
import it.csi.rebus.rebuscrus.vo.AziendaVO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;

public interface RicercaAutobusService {

	List<VExportRicercaAutobusDTO> elencoAutobus() throws Exception;

	List<AziendaVO> elencoAziende() throws Exception;

	List<VExportRicercaAutobusDTO> filtraElencoAutobus(FiltroAutobusVO filtro) throws Exception;

}