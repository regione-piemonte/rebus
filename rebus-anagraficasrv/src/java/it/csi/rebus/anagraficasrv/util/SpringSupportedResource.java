/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

/**
 * @author fabio.fenoglio
 * @date 17 ott 2017
 */
public abstract class SpringSupportedResource {

	public boolean springBeansInjected = false;
	
	private static Logger logger = Logger.getLogger(SpringSupportedResource.class);

	public void contextInitialized(ServletContext sc) {
		logger.info("inizializzo risorsa " + this.getClass().getSimpleName());
	}

	public boolean isSpringBeansInjected() {
		return springBeansInjected;
	}

	public void setSpringBeansInjected(boolean springBeansInjected) {
		this.springBeansInjected = springBeansInjected;
	}

}
