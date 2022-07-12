/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

public interface PDFService {

	byte[] generatePDF(Long idProcedimento, Long idTipoStampa, Boolean isAnteprima) throws Exception;

}
