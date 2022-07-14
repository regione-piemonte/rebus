/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.anagraficasrv.business.service.RinnovoContrattoService;
import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;

@Path("rinnovoContratto")
public class RinnovoContrattoResource extends SpringSupportedResource {

	@Autowired
	private RinnovoContrattoService rinnovoContrattoService;

	@POST
	@Path("/creaRinnovoContratto")
	public Response creaRinnovoContratto(@Context HttpServletRequest request,Long idContratto) {
			return Response.ok(rinnovoContrattoService.creaRinnovoContratto(idContratto)).header("Access-Control-Allow-Origin", "*").build();
	}

}
