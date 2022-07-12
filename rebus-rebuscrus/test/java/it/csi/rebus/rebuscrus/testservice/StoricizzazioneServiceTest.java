/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.testservice;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.RicercaAutobusService;
import it.csi.rebus.rebuscrus.business.service.impl.StoricizzazioneServiceImpl;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.testbase.RebuscrusJunitClassRunner;
import it.csi.rebus.rebuscrus.testbase.TestBaseService;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;

/**
 * @author 
 * @date 11 mar 2018
 *
 *       note: disabilitare nel service eventuale accesso a RequestContextHolder
 */
@RunWith(RebuscrusJunitClassRunner.class)
public class StoricizzazioneServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(StoricizzazioneServiceTest.class);

	@Autowired
	private StoricizzazioneServiceImpl storicizzazioneServiceImpl;

	@Autowired
	private RebusTVariazAutobusDAO rebusTVariazAutobusDAO;

	@Test
	public void testStoricizza() {
		logger.info("START");
		
		Long idUtente = 1L;
		Long idVariazAutobus = 41153L;
		
		RebusTVariazAutobusDTO autobus = rebusTVariazAutobusDAO.selectByPrimaryKey(idVariazAutobus);

		storicizzazioneServiceImpl.storicizzaAutobus(autobus, idUtente, new Date());
	}

}
