/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.anagraficasrv.business.service.LuoghiService;
import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;

@Path("luoghi")
public class LuoghiResource extends SpringSupportedResource {
	
	@Autowired
	private LuoghiService luoghiService;

	@GET
	@Path("/province")
	public Response getProvince(@Context HttpServletRequest request) {

		return Response.ok(luoghiService.getProvince()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/comuniByIdProvincia")
	public Response getComuniByIdProvincia(@Context HttpServletRequest request,
			@QueryParam("idProvincia") Long idProvincia) {

		return Response.ok().entity(luoghiService.getComuniByIdProvincia(idProvincia))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/comuni")
	public Response getComuni(@Context HttpServletRequest request) {

		return Response.ok(luoghiService.getComuni()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/provinciaByIdComune")
	public Response getProvinciaByIdComune(@Context HttpServletRequest request, @QueryParam("idComune") Long idComune) {

		return Response.ok().entity(luoghiService.getProvinciaByIdComune(idComune))
				.header("Access-Control-Allow-Origin", "*").build();
	}

}
