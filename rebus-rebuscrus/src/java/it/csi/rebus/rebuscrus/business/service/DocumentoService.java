/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service;

import java.util.List;

import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.DocumentoVO;

/**
 * @author riccardo.bova
 * @date 05 giu 2018
 */
public interface DocumentoService {

	List<DocumentoVO> elencoDocumento(Long idContesto);

	byte[] getContenutoDocumentoByIdVarAutobusAndTipo(Long idVarAutobus, Long idTipoDocumento);
	
	boolean getContenutoDocumentoByIdVarAutobus(Long idVarAutobus);

	void eliminaDocumento(AutobusVO autobus);

	byte[] getContenutoDocumentoByIdProcedimentoAndTipo(Long idProcedimento, Long idTipoDocumento);

	byte[] getContenutoAnteprimaPdf(Long idProcedimento, Long idStatoProc, Long idTipoProc) throws Exception;

	void eliminaAllegatoProcedimentoDb(Long idProcedimento, Long idTipoDocumento);

}
