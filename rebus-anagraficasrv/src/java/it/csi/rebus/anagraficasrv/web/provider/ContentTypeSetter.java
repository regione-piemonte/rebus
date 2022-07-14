/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web.provider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

/**
 * @author riccardo.bova
 */
@Provider
@ServerInterceptor
public class ContentTypeSetter implements PreProcessInterceptor {

	/*
	 * @see
	 * org.jboss.resteasy.spi.interception.PreProcessInterceptor#preProcess(org.
	 * jboss.resteasy.spi.HttpRequest, org.jboss.resteasy.core.ResourceMethod)
	 */
	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod resourceMethodInvoker) throws Failure, WebApplicationException {
		request.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY, "*/*; charset=UTF-8");
		return null;
	}
}