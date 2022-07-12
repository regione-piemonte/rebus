/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.service.RiferimentiNormativiService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dao.RebuspTDocumentoDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTDocumentoSelector;
import it.csi.rebus.rebuscrus.integration.mapper.RiferimentiNormativiMapper;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.InserisciRiferimentoVO;
import it.csi.rebus.rebuscrus.vo.RiferimentiNormativiVO;

@Component
public class RiferimentiNormativiServiceImpl implements RiferimentiNormativiService {
	@Autowired
	private RebuspTDocumentoDAO rebuspTDocumentoDAO;

	@Autowired
	private RiferimentiNormativiMapper riferimentiNormativiMapper;

	@Override
	public List<RiferimentiNormativiVO> elencoRiferimenti() {
		 SecurityUtils.assertAutorizzazioni(AuthorizationRoles.SCARICA_RIFERIMENTI);

		List<RiferimentiNormativiVO> riferimenti = new ArrayList<RiferimentiNormativiVO>();
		List<RebuspTDocumentoDTO> riferimentiDTO = rebuspTDocumentoDAO.selectByExample(new RebuspTDocumentoSelector());

		for (RebuspTDocumentoDTO dto : riferimentiDTO) {
			RiferimentiNormativiVO vo = riferimentiNormativiMapper.mapDTOtoVO(dto);
			riferimenti.add(vo);
		}

		return riferimenti;

	}

	@Transactional
	@Override
	public void eliminaRiferimento(Long idDocumento) {
		// controllo se il procedimento e eliminabile
		if (idDocumento != null) {
			rebuspTDocumentoDAO.deleteByPrimaryKey(idDocumento);
		}

	}

	@Override
	@Transactional
	public Long inserisciRiferimento(InserisciRiferimentoVO riferimentoRequest) {
		if (riferimentoRequest == null) {
			throw new InvalidParameterException("Riferimento non valorizzato");
		}

		if (!riferimentoRequest.getNomeFile().toLowerCase().matches(".*\\.do.*") && !riferimentoRequest.getNomeFile().toLowerCase().matches(".*\\.pdf.*")) {
			throw new ErroreGestitoException("Formato file del documento allegato non consentito! Formati consentiti: PDF, DOC, DOCX.", "TFNC");
		}

		Date now = new Date();

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		RebuspTDocumentoDTO rebuspTDocumentoDTO = new RebuspTDocumentoDTO();
		rebuspTDocumentoDTO.setDocumento(riferimentoRequest.getFile());
		rebuspTDocumentoDTO.setDescrizione(riferimentoRequest.getDescrizione());
		rebuspTDocumentoDTO.setNomeFile(riferimentoRequest.getNomeFile());
		rebuspTDocumentoDTO.setDataInserimento(now);
		rebuspTDocumentoDTO.setIdUtenteInserimento(userInfo.getIdUtente());
		try {
			rebuspTDocumentoDAO.insert(rebuspTDocumentoDTO);
		} catch (Exception e) {
			throw new ErroreGestitoException("File presente a sistema", "TFNC");
		}
		return rebuspTDocumentoDTO.getIdDocumento();
	}

	@Override
	public byte[] getDocumentoByIdDocumento(Long idDocumento) {
		if (idDocumento == null)
			throw new InvalidParameterException();
		RebuspTDocumentoSelector selector = new RebuspTDocumentoSelector();
		selector.createCriteria().andIdDocumentoEqualTo(idDocumento);
		List<RebuspTDocumentoDTO> documentsDTO = rebuspTDocumentoDAO.selectByExampleWithBLOBs(selector);
		if (documentsDTO == null || documentsDTO.isEmpty()) {
			throw new InvalidParameterException("id documento non valido");
		}
		return documentsDTO.get(0).getDocumento();

	}

}
