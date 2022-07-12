/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.service.DocumentoService;
import it.csi.rebus.rebuscrus.business.service.PDFService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoDocumentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRDocVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspRProcDocumentoDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoDocumentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoSelector;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.DocumentoVO;

/**
 * @author riccardo.bova
 * @date 05 giu 2018
 */
@Component
public class DocumentoServiceImpl implements DocumentoService {

	@Autowired
	private RebusDTipoDocumentoDAO rebusDTipoDocumentoDAO;
	@Autowired
	private RebusRDocVariazAutobusDAO rebusRDocVariazAutobusDAO;
	@Autowired
	private RebuspRProcDocumentoDAO rebuspRProcDocumentoDAO;
	@Autowired
	private PDFService pdfService;

	@Override
	public List<DocumentoVO> elencoDocumento(Long idContesto) {
		List<DocumentoVO> data = new ArrayList<DocumentoVO>();
		DocumentoVO documento;
		RebusDTipoDocumentoSelector example = new RebusDTipoDocumentoSelector();
		example.createCriteria().andIdContestoEqualTo(idContesto);
		example.setOrderByClause("id_tipo_documento");
		for (RebusDTipoDocumentoDTO elem : rebusDTipoDocumentoDAO.selectByExample(example)) {
			documento = new DocumentoVO();
			documento.setIdTipoDocumento(elem.getIdTipoDocumento());
			documento.setDescrizione(elem.getDescrizione());
			documento.setDescEstesa(elem.getDescEstesa());
			data.add(documento);
		}
		return data;
	}

	@Override
	public byte[] getContenutoDocumentoByIdVarAutobusAndTipo(Long idVarAutobus, Long idTipoDocumento) {
		if (idVarAutobus == null || idTipoDocumento == null)
			throw new InvalidParameterException();

		// recupero da DB
		RebusRDocVariazAutobusSelector selector = new RebusRDocVariazAutobusSelector();
		selector.createCriteria().andIdVariazAutobusEqualTo(idVarAutobus).andIdTipoDocumentoEqualTo(idTipoDocumento);

		List<RebusRDocVariazAutobusDTO> fileDTO = rebusRDocVariazAutobusDAO.selectByExampleWithBLOBs(selector);
		if (fileDTO == null || fileDTO.size() < 1) {
			throw new InvalidParameterException("id documento e tipo non validi");
		}
		return fileDTO.get(0).getDocumento();
	}

	// return tree if rebus_r_doc_variaz_autobus has value
	public boolean getContenutoDocumentoByIdVarAutobus(Long idVarAutobus) {
		if (idVarAutobus == null)
			throw new InvalidParameterException();
		RebusRDocVariazAutobusSelector selector = new RebusRDocVariazAutobusSelector();
		selector.createCriteria().andIdVariazAutobusEqualTo(idVarAutobus);
		List<RebusRDocVariazAutobusDTO> fileDTO = rebusRDocVariazAutobusDAO.selectByExample(selector);
		if (fileDTO == null || fileDTO.size() < 1) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public void eliminaDocumento(AutobusVO autobus) {
		RebusRDocVariazAutobusSelector rebusRDocVarAutobusSelector = new RebusRDocVariazAutobusSelector();
		rebusRDocVarAutobusSelector.createCriteria().andIdTipoDocumentoEqualTo(autobus.getIdDocumento())
				.andIdVariazAutobusEqualTo(autobus.getId());
		rebusRDocVariazAutobusDAO.deleteByExample(rebusRDocVarAutobusSelector);
	}

	@Override
	public byte[] getContenutoDocumentoByIdProcedimentoAndTipo(Long idProcedimento, Long idTipoDocumento) {
		if (idProcedimento == null || idTipoDocumento == null)
			throw new InvalidParameterException();

		// recupero da DB
		RebuspRProcDocumentoSelector selector = new RebuspRProcDocumentoSelector();
		selector.createCriteria().andIdProcedimentoEqualTo(idProcedimento).andIdTipoDocumentoEqualTo(idTipoDocumento);

		List<RebuspRProcDocumentoDTO> fileDTO = rebuspRProcDocumentoDAO.selectByExampleWithBLOBs(selector);
		if (fileDTO == null || fileDTO.size() < 1) {
			throw new InvalidParameterException("id documento e tipo non validi");
		}
		return fileDTO.get(0).getDocumento();
	}

	@Override
	public byte[] getContenutoAnteprimaPdf(Long idProcedimento, Long idStatoProc, Long idTipoProc) throws Exception {
		try {
			if (idStatoProc.equals(new Long(10)) || idStatoProc.equals(new Long(20)) || idStatoProc.equals(new Long(30))
					|| idStatoProc.equals(new Long(50))) {
				// DA TRASMETTERE AD ENTE COMMITTENTE/INTERMEDIARIO
				if (idTipoProc.equals(new Long(2))) {
					// SOSTITUZIONE
					return pdfService.generatePDF(idProcedimento, new Long(3), Boolean.TRUE);
					// IDTIPOSTAMPA = RICHIESTA NULLA OSTA SOSTITUZIONE
				} else { // NON SOSTITUZIONE
					return pdfService.generatePDF(idProcedimento, new Long(1), Boolean.TRUE);
					// IDTIPOSTAMPA = RICHIESTA NULLA OSTA

				}
			}
			if (idStatoProc.equals(new Long(40))) {
				// AUTORIZZATO
				if (idTipoProc.equals(new Long(2))) {
					// SOSTITUZIONE
					return pdfService.generatePDF(idProcedimento, new Long(4), Boolean.TRUE);
					// IDTIPOSTAMPA = RILASCIO NULLA OSTA SOSTITUZIONE
				} else { // NON SOSTITUZIONE
					return pdfService.generatePDF(idProcedimento, new Long(2), Boolean.TRUE);
					// IDTIPOSTAMPA = RILASCIO NULLA OSTA
				}
			}
		} catch (Exception e) {
			throw new ErroreGestitoException("errore nella creazione del PDF", "501");
		}
		return null;
	}

	@Override
	public void eliminaAllegatoProcedimentoDb(Long idProcedimento, Long idTipoDocumento) {
		if (idProcedimento == null || idTipoDocumento == null)
			throw new InvalidParameterException();

		// recupero da DB
		RebuspRProcDocumentoSelector selector = new RebuspRProcDocumentoSelector();
		selector.createCriteria().andIdProcedimentoEqualTo(idProcedimento).andIdTipoDocumentoEqualTo(idTipoDocumento);

		List<RebuspRProcDocumentoDTO> fileDTO = rebuspRProcDocumentoDAO.selectByExampleWithBLOBs(selector);
		if (fileDTO == null || fileDTO.size() < 1) {
			throw new InvalidParameterException("id documento e tipo non validi");
		}
		rebuspRProcDocumentoDAO.deleteByExample(selector);

	}
}
