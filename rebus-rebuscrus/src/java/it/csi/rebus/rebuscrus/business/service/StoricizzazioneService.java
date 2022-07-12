/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.Date;

import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;

/**
 * @author riccardo.bova
 * @date 02 mar 2018
 */
public interface StoricizzazioneService {
	long storicizzaAutobus(RebusTVariazAutobusDTO autobus, Long idUtente, Date now);
}
