/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.anagraficasrv.business.service.RinnovoContrattoService;
import it.csi.rebus.anagraficasrv.common.exception.ErroreGestitoException;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRContrAmbTipServDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRContrattoRaggruppDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRSostSogContrDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRSostSogContrRaggrDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcTContrattoAllegatoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcTContrattoDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrAmbTipServKey;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrAmbTipServSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrattoRaggruppDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrattoRaggruppSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrRaggrDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrRaggrSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoAllegatoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoAllegatoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoSelector;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;

@Service
public class RinnovoContrattoServiceImpl implements RinnovoContrattoService {

	@Autowired
	private SirtplcTContrattoDAO sirtplcTContrattoDAO;
	@Autowired
	private SirtplcRContrattoRaggruppDAO sirtplcRContrattoRaggruppDAO;
	@Autowired
	private SirtplcTContrattoAllegatoDAO sirtplcTContrattoAllegatoDAO;
	@Autowired
	private SirtplcRContrAmbTipServDAO sirtplcRContrAmbTipServDAO;
	@Autowired
	private SirtplcRSostSogContrDAO sirtplcRSostSogContrDAO;
	@Autowired
	private SirtplcRSostSogContrRaggrDAO sirtplcRSostSogContrRaggrDAO;
	

	
	private void checkCodiceIdentificativoNazionale(String cod) {

		SirtplcTContrattoSelector sel = new SirtplcTContrattoSelector();
		sel.createCriteria().andCodIdNazionaleEqualTo(cod.toUpperCase());
		List<SirtplcTContrattoDTO> contratti = sirtplcTContrattoDAO.selectByExample(sel);
		if (contratti.size() > 0) {
			throw new ErroreGestitoException("Codice Nazionale esistente. Cambiare il campo codice nazionale", "TFND");
		}

	}

	
	enum Action {
		EDIT("E"), VIEW("V");

		private String value;

		private Action(String value) {
			this.value = value;
		}
	}

	@Transactional
	@Override
	public Long creaRinnovoContratto(Long idContratto) {
		if (idContratto == null) {
			throw new InvalidParameterException("Errore nella duplicazione del Contratto");
		}

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		// check if codice identificativo nazionale esiste gia
		SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO.selectByPrimaryKey(idContratto);
		sirtplcTContrattoDTO.setIdContrattoPadre(idContratto);
		sirtplcTContrattoDTO.setIdContratto(null);
		sirtplcTContrattoDTO.setNumRepertorio(null);
		sirtplcTContrattoDTO.setDataStipula(null);
		sirtplcTContrattoDTO.setDataAggiornamento(new Date());
		sirtplcTContrattoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
		sirtplcTContrattoDTO.setDataFineValidita(null);
		sirtplcTContrattoDTO.setDataInizioValidita(null);

		sirtplcTContrattoDAO.insert(sirtplcTContrattoDTO);
		
		SirtplcRContrAmbTipServSelector contrAmbTipServSelector = new SirtplcRContrAmbTipServSelector(); 
		contrAmbTipServSelector.createCriteria().andIdContrattoEqualTo(idContratto);
		List<SirtplcRContrAmbTipServKey> ambTipoServizi = sirtplcRContrAmbTipServDAO.selectByExample(contrAmbTipServSelector);
		for (SirtplcRContrAmbTipServKey id : ambTipoServizi) {
			SirtplcRContrAmbTipServKey sirtplcRContrAmbTipServKey = new SirtplcRContrAmbTipServKey();
			sirtplcRContrAmbTipServKey.setIdContratto(Long.valueOf(sirtplcTContrattoDTO.getIdContratto()));
			sirtplcRContrAmbTipServKey.setIdAmbTipServizio(id.getIdAmbTipServizio());
			sirtplcRContrAmbTipServDAO.insert(sirtplcRContrAmbTipServKey);
		}

		// Se nella combo Tipo soggetto esecutore titolare e selezionata la voce
		// Raggruppamento
		SirtplcRContrattoRaggruppSelector contrattoRaggruppSelector = new SirtplcRContrattoRaggruppSelector(); 
		contrattoRaggruppSelector.createCriteria().andIdContrattoEqualTo(idContratto);
		List<SirtplcRContrattoRaggruppDTO> contrattoRaggruppList = sirtplcRContrattoRaggruppDAO.selectByExample(contrattoRaggruppSelector);
		for (SirtplcRContrattoRaggruppDTO contrattoRaggr : contrattoRaggruppList ) {
			SirtplcRContrattoRaggruppDTO sirtplcRContrattoRaggruppDTO = new SirtplcRContrattoRaggruppDTO();
			sirtplcRContrattoRaggruppDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
			sirtplcRContrattoRaggruppDTO.setIdSoggettoGiuridico(contrattoRaggr.getIdSoggettoGiuridico());
			sirtplcRContrattoRaggruppDTO.setCapofila(contrattoRaggr.getCapofila());
			sirtplcRContrattoRaggruppDAO.insert(sirtplcRContrattoRaggruppDTO);

		}

		// gestione allegato
		SirtplcTContrattoAllegatoSelector contrattoAllegatoSelector = new SirtplcTContrattoAllegatoSelector(); 
		contrattoAllegatoSelector.createCriteria().andIdContrattoEqualTo(idContratto);
		List<SirtplcTContrattoAllegatoDTO> contrattoAllegatoList = sirtplcTContrattoAllegatoDAO.selectByExample(contrattoAllegatoSelector);
			for (SirtplcTContrattoAllegatoDTO file : contrattoAllegatoList) {
				SirtplcTContrattoAllegatoDTO sirtplcTContrattoAllegatoDTO = new SirtplcTContrattoAllegatoDTO();
				sirtplcTContrattoAllegatoDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
				sirtplcTContrattoAllegatoDTO.setIdTipoDocumento(file.getIdTipoDocumento());
				sirtplcTContrattoAllegatoDTO.setNote(file.getNote());
				sirtplcTContrattoAllegatoDTO.setAllegato(file.getAllegato());
				sirtplcTContrattoAllegatoDTO.setDataAggiornamento(file.getDataAggiornamento());
				sirtplcTContrattoAllegatoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				sirtplcTContrattoAllegatoDTO.setNomeFile(file.getNomeFile());
				sirtplcTContrattoAllegatoDAO.insert(sirtplcTContrattoAllegatoDTO);
			}
			
			
		//gestione subentri/subaffidamenti
			SirtplcRSostSogContrSelector sostSogContrSelector = new SirtplcRSostSogContrSelector();
			sostSogContrSelector.createCriteria().andIdContrattoEqualTo(idContratto);
			List<SirtplcRSostSogContrDTO> sostSogContrList = sirtplcRSostSogContrDAO.selectByExample(sostSogContrSelector);
				for (SirtplcRSostSogContrDTO sostSogContr : sostSogContrList) {
							SirtplcRSostSogContrDTO sirtplcRSostSogContrDTO = new SirtplcRSostSogContrDTO();
							sirtplcRSostSogContrDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
							sirtplcRSostSogContrDTO.setIdSogGiuridCommittente(sostSogContr.getIdSogGiuridCommittente());
							sirtplcRSostSogContrDTO.setAttoSostituzione(sostSogContr.getAttoSostituzione());
							sirtplcRSostSogContrDTO.setDataSostituzione(sostSogContr.getDataSostituzione());
							sirtplcRSostSogContrDTO.setIdTipoSostituzione(sostSogContr.getIdTipoSostituzione());
							sirtplcRSostSogContrDAO.insert(sirtplcRSostSogContrDTO);
				}
				
				SirtplcRContrattoRaggruppSelector sirtplcRContrattoRaggruppSelector = new SirtplcRContrattoRaggruppSelector();
				sirtplcRContrattoRaggruppSelector.createCriteria().andIdContrattoEqualTo(sirtplcTContrattoDTO.getIdContratto());
				List<SirtplcRContrattoRaggruppDTO> sirtplcRContrattoRaggruppDTOs = sirtplcRContrattoRaggruppDAO.selectByExample(sirtplcRContrattoRaggruppSelector);
				SirtplcRSostSogContrRaggrSelector sostSogContrRaggrSelector = new SirtplcRSostSogContrRaggrSelector();
				sostSogContrRaggrSelector.createCriteria();
				for(SirtplcRContrattoRaggruppDTO sirtplcRContrattoRaggruppDTO:sirtplcRContrattoRaggruppDTOs) {
					sostSogContrRaggrSelector.or().andIdContrattoRaggruppEqualTo(sirtplcRContrattoRaggruppDTO.getIdContrattoRaggrupp());
				}
				 List<SirtplcRSostSogContrRaggrDTO> sostSogContrRaggrList = sirtplcRSostSogContrRaggrDAO.selectByExample(sostSogContrRaggrSelector);
					for ( SirtplcRSostSogContrRaggrDTO sostSogContrRaggr : sostSogContrRaggrList) {
								SirtplcRSostSogContrRaggrDTO sirtplcRSostSogContrRaggrDTO = new SirtplcRSostSogContrRaggrDTO();
								sirtplcRSostSogContrRaggrDTO.setIdContrattoRaggrupp(sostSogContrRaggr.getIdContrattoRaggrupp());
								sirtplcRSostSogContrRaggrDTO.setIdSoggettoGiuridico(sostSogContrRaggr.getIdSoggettoGiuridico());
								sirtplcRSostSogContrRaggrDTO.setAttoSostituzione(sostSogContrRaggr.getAttoSostituzione());
								sirtplcRSostSogContrRaggrDTO.setDataSostituzione(sostSogContrRaggr.getDataSostituzione());
								sirtplcRSostSogContrRaggrDTO.setIdTipoSostituzione(sostSogContrRaggr.getIdTipoSostituzione());
								sirtplcRSostSogContrRaggrDAO.insert(sirtplcRSostSogContrRaggrDTO);
				}
							

		return sirtplcTContrattoDTO.getIdContratto();
	}

}
