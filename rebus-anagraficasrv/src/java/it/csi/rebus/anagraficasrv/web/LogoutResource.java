/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.anagraficasrv.business.service.LogoutService;
import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;

@Path("logout")
public class LogoutResource extends SpringSupportedResource {

	@Autowired
	private LogoutService logoutService;

	@GET
	@Path("/aziendeFunzionario")
	public Response getAziendeFunzionario(@Context HttpServletRequest req) {
		return Response.ok(logoutService.getAziendeFunzionario()).header("Access-Control-Allow-Origin", "*").build();
	}
}
