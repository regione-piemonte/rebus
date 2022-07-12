/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.List;

import it.csi.rebus.rebuscrus.integration.dto.RebusTMessaggiDTO;
import it.csi.rebus.rebuscrus.vo.MessaggioVo;
import it.csi.rebus.rebuscrus.vo.UtenteAzEnteVO;

public interface MessaggiService {

	Long calcolaNumMessaggi();

	List<RebusTMessaggiDTO> elencoMessaggi(Long idFilter);

	Boolean checkMessaggiNonLetti(Long idFilter);

	MessaggioVo dettaglioMessaggio(Long idMessaggio);

	Long inserisciMessaggio(MessaggioVo input);

	void ripristinaNonLetto(Long idMessaggio);

	void segnaComeLetto(Long idMessaggio);

	UtenteAzEnteVO dettaglioUtenteAzEnte(Long idUtente);

}
