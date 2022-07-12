/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.testservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.MessaggiService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dto.RebusTMessaggiDTO;
import it.csi.rebus.rebuscrus.testbase.RebuscrusJunitClassRunner;
import it.csi.rebus.rebuscrus.testbase.TestBaseService;
import it.csi.rebus.rebuscrus.vo.MessaggioVo;

@RunWith(RebuscrusJunitClassRunner.class)
public class MessaggiServiceTest extends TestBaseService {

	protected static Logger logger = Logger.getLogger(MessaggiServiceTest.class);
	
	@Autowired
	private MessaggiService messaggiServer;
	
	@Test
	public void calcolaNumMessaggiTest() {
		Long numMessaggi = null;
		try {
			numMessaggi  = messaggiServer.calcolaNumMessaggi();
		} catch (ErroreGestitoException e) {
			logger.error("Eccezione gestita", e);
			throw e;
		} catch (Exception e) {
			logger.error("Eccezione generica: ", e);
		}

		if (numMessaggi != 0) {
			System.out.println("servizio ok");
		} else {
			System.out.println("lista vuota");
		}
		
	}
	
	@Test
	public void elencoMessaggiTest() {
		List<RebusTMessaggiDTO> elencoMessaggi = new ArrayList<RebusTMessaggiDTO>();
		
		try {
			elencoMessaggi  = messaggiServer.elencoMessaggi(Long.valueOf(1));
		} catch (ErroreGestitoException e) {
			logger.error("Eccezione gestita", e);
			throw e;
		} catch (Exception e) {
			logger.error("Eccezione generica: ", e);
		}

		if (elencoMessaggi != null ) {
			System.out.println("servizio ok");
			System.out.println("elenco messaggi size == "+elencoMessaggi.size());
		} else {
			System.out.println("lista vuota");
		}
		
	}
	
	@Test
	public void dettaglioMessaggioTest() {
		MessaggioVo messaggio = new MessaggioVo();
		
		try {
			messaggio  = messaggiServer.dettaglioMessaggio(new Long(1));
		} catch (ErroreGestitoException e) {
			logger.error("Eccezione gestita", e);
			throw e;
		} catch (Exception e) {
			logger.error("Eccezione generica: ", e);
		}

		if (messaggio != null ) {
			System.out.println("servizio ok");
			System.out.println(" messaggo == "+messaggio);
		} else {
			System.out.println("nessun messaggio presente");
		}
		
	}
	
	
	
	
	
}
