/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.manager.ZipReportManager;
import it.csi.rebus.rebuscrus.business.service.ZipService;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;

/**
 * @author 70525
 * @date 24 nov 2017
 */
@Component
public class ZipServiceImpl implements ZipService {

	@Autowired
	private ZipReportManager zipReportManager;
	
	@Transactional( timeout = 840 ) 
	public byte[] zipRicercaContribuzione(FiltroContribuzioneVO filtro) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo.getIdAzienda() != null) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		filtro.prepareSQL();
		return zipReportManager.zipRicercaContribuzione(filtro);
	}

	
}
