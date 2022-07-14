/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service;

import java.util.List;

import it.csi.rebus.anagraficasrv.vo.ContrattoVO;
import it.csi.rebus.anagraficasrv.vo.FiltroContrattoVO;

public interface RicercaContrattoService {

	List<ContrattoVO> elencoContratto();

	List<ContrattoVO> filtraElencoContratto(FiltroContrattoVO filtro);

}
