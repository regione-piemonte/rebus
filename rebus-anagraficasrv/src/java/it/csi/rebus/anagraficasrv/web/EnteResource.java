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

import it.csi.rebus.anagraficasrv.business.service.EnteService;
import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;

@Path("ente")
public class EnteResource extends SpringSupportedResource {

	@Autowired
	private EnteService enteService;

	@GET
	@Path("/tipiEnte")
	public Response getTipiEnte(@Context HttpServletRequest request) {

		return Response.ok(enteService.getTipiEnte()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/enti")
	public Response getEnti(@Context HttpServletRequest request) {

		return Response.ok(enteService.getEnti()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipiRaggruppamento")
	public Response getTipiRaggruppamento(@Context HttpServletRequest request) {

		return Response.ok(enteService.getTipiRaggruppamento()).header("Access-Control-Allow-Origin", "*").build();
	}
}
