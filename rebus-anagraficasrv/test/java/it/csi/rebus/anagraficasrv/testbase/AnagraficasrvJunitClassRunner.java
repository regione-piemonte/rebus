/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.testbase;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * @author 
 * @date 11 mar 2018
 */
public class AnagraficasrvJunitClassRunner extends SpringJUnit4ClassRunner {

	public AnagraficasrvJunitClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	static {
		String log4jLocation = "test/java/it/csi/rebus/anagraficasrv/testContext/log4j.properties";
		try {
			Log4jConfigurer.initLogging(log4jLocation);
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j at location: " + log4jLocation);
		}
	}

}
