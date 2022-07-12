/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.manager.ExcelReportManager;
import it.csi.rebus.rebuscrus.business.manager.ExcelUploadManager;
import it.csi.rebus.rebuscrus.business.service.ExcelService;
import it.csi.rebus.rebuscrus.integration.dto.custom.ResponseExcelDto;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.ExcelVO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;

import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;

/**
 * @author 
 * @date 24 nov 2017
 */
@Component
public class ExcelServiceImpl implements ExcelService {

	private static Logger logger = Logger.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + "ExcelServiceImpl");

	@Autowired
	private ExcelUploadManager excelUploadManager;
	@Autowired
	private ExcelReportManager excelReportManager;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public ExcelVO readExcelAndWriteOnDB(InputStream excelFileToRead, InputStream excelFileTemplate) {

		it.csi.rebus.rebuscrus.security.UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

		// Accesso consentito per le seguenti autorizzazioni LOAD_EXCEL e LOAD_EXCEL_PRIMO_IMPIANTO
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.UPLOAD_FILE_XLS, AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);

		logger.info("ExcelServiceImpl::readExcelAndWriteOnDB userInfo.getIdAzienda():" + userInfo.getIdAzienda());

		ResponseExcelDto response = null;

		Boolean isPrimoImpianto = SecurityUtils.isAutorizzato(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);// rebus 16 //no id Azienda
		Boolean isUploadExcel = SecurityUtils.isAutorizzato(AuthorizationRoles.UPLOAD_FILE_XLS);// rebus 4 //SI IDaZIENDA NEL PROFILO

		if (!isPrimoImpianto && isUploadExcel && userInfo.getIdAzienda() == null) {
			response = new ResponseExcelDto();
			response.setErroreException(true);
			response.setMsgErroreException("Non e' presente idAzienda");
		} else {
			response = excelUploadManager.letturaControlloDatiExcel(excelFileToRead , excelFileTemplate);
		}

		logger.info("ExcelServiceImpl::readExcelAndWriteOnDB response.isErroreCampiExcel():" + response.isErroreCampiExcel());
		logger.info("ExcelServiceImpl::readExcelAndWriteOnDB response.isCaricamentoDatiOK():" + response.isCaricamentoDatiOK());
		logger.info("ExcelServiceImpl::readExcelAndWriteOnDB response.isErroreException():" + response.isErroreException());
		logger.info("ExcelServiceImpl::readExcelAndWriteOnDB response.getMsgErroreException():" + response.getMsgErroreException());
		logger.info("ExcelServiceImpl::readExcelAndWriteOnDB response.isErroreCongruenzaCampiExcel():" + response.isErroreCongruenzaCampiExcel());
		if (response.isErroreCongruenzaCampiExcel()) {
			for (String errore : response.getMsgErroreCongruenzaCampiExcel())
				logger.info("ExcelServiceImpl::readExcelAndWriteOnDB errore:" + errore);
		}

		long recordInseriti = 0;

		if (response.isCaricamentoDatiOK()) {
			/*
			 * Se non ci sono stati problemi inserisco i dati sul db
			 */
			excelUploadManager.scrivoDatiExcelSuDB(response);
			recordInseriti = response.getMezzi().size();
		}

		ExcelVO result = new ExcelVO();
		result.setCaricamentoDatiOK(response.isCaricamentoDatiOK());
		result.setErroreCampiExcel(response.isErroreCampiExcel());
		result.setErroreCongruenzaCampiExcel(response.isErroreCongruenzaCampiExcel());
		result.setErroreException(response.isErroreException());
		result.setMsgErroreException(response.getMsgErroreException());
		result.setMsgErroreCongruenzaCampiExcel(response.getMsgErroreCongruenzaCampiExcel());
		result.setExcel(response.getExcel());
		result.setRecordInseriti(recordInseriti);
		
		return result;
	}
	
	@Transactional( timeout = 840 ) 
	public byte[] exportRicercaAutobus(FiltroAutobusVO filtro) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo.getIdAzienda() != null) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		filtro.prepareSQL();
		// Verifico se e stato richiesto un excel di uno storico o di un dato attuale
		if (filtro.getSituazioneAl() != null)
			return excelReportManager.exportRicercaStoricoAutobus(filtro);
		return excelReportManager.exportRicercaAutobus(filtro);
	}
	
	@Transactional( timeout = 840 ) 
	public byte[] excelRicercaContribuzione(FiltroContribuzioneVO filtro) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo.getIdAzienda() != null) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		filtro.prepareSQL();
		// Verifico se e stato richiesto un excel di uno storico o di un dato attuale
		return excelReportManager.excelRicercaContribuzione(filtro);
	}
}
