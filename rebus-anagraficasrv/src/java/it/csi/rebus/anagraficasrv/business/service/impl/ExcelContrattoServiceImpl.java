/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.business.manager.ExcelReportContrattiManager;
import it.csi.rebus.anagraficasrv.business.service.ExcelContrattoService;
import it.csi.rebus.anagraficasrv.security.CodeRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.FiltroContrattoVO;

@Component
public class ExcelContrattoServiceImpl implements ExcelContrattoService {

	@Autowired
	ExcelReportContrattiManager excelReportManager;

	@Override
	public byte[] exportRicercaContratti(FiltroContrattoVO filtro) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		if (StringUtils.isBlank(filtro.getFlagIncludiCessate())) {
			filtro.setFlagIncludiCessate("N");
		}
		filtro.prepareSQL();

		return excelReportManager.exportRicercaAutobus(filtro);
	}
}
