/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.util;

import javax.servlet.ServletContext;

/**
 * @author fabio.fenoglio
 * @date 17 ott 2017
 */
public abstract class SpringSupportedResource {

	public boolean springBeansInjected = false;
	
	public void contextInitialized(ServletContext sc) {
		
	}

	public boolean isSpringBeansInjected() {
		return springBeansInjected;
	}

	public void setSpringBeansInjected(boolean springBeansInjected) {
		this.springBeansInjected = springBeansInjected;
	}

}
