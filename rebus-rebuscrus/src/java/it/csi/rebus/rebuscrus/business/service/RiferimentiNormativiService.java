/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.List;

import it.csi.rebus.rebuscrus.vo.InserisciRiferimentoVO;
import it.csi.rebus.rebuscrus.vo.RiferimentiNormativiVO;

public interface RiferimentiNormativiService {

	List<RiferimentiNormativiVO> elencoRiferimenti();

	Long inserisciRiferimento(InserisciRiferimentoVO riferimentoRequest);

	void eliminaRiferimento(Long idDocumento);

	byte[] getDocumentoByIdDocumento(Long idDocumento);


}