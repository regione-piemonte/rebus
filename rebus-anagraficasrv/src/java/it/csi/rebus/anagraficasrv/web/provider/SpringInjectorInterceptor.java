/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web.provider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import it.csi.rebus.anagraficasrv.business.SpringApplicationContextHelper;

@Provider
public class SpringInjectorInterceptor implements PreProcessInterceptor {

	// Preprocessor sostituito da ContainerRequestFilter nel jaxrs 2.0
	// jboss 6.4 usa jboss-jaxrs-api_1.1_spec-1.0.1.Final-redhat-3.jar
	// non aggiorno la lib x non bypass la lib del server
	public ServerResponse preProcess(HttpRequest request, ResourceMethod method) throws Failure, WebApplicationException {
		SpringApplicationContextHelper.injectSpringBeansIntoRestEasyService(method.getResourceClass().getName());
		return null;
	}
}