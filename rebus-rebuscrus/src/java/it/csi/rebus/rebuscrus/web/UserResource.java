/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.UserService;
import it.csi.rebus.rebuscrus.integration.iride.Ruolo;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;

@Path("user")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class UserResource extends SpringSupportedResource {

	@Autowired
	UserService userService;

	@GET
	@Path("/verificaUtente")
	public Response verificaFunzionario(@Context SecurityContext sc) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo == null) {
			Response.noContent().build();
		}
		return Response.ok().entity(userInfo).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/localLogout")
	public Response localLogout(@Context HttpServletRequest req) {
		req.getSession().invalidate();
		return Response.ok().build();
	}

	@GET
	@Path("/setIdAziendaUtente")
	public Response setIdAziendaUtente(@Context HttpServletRequest req, @QueryParam("id") Long id) {

		return Response.ok(userService.setIdAziendaUtente(id)).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/setRuoloUtente")
	public Response setRuoloUtente(@Context HttpServletRequest request, Ruolo ruolo) {

		return Response.ok(userService.setRuoloUtente(ruolo)).header("Access-Control-Allow-Origin", "*").build();
	}
}
