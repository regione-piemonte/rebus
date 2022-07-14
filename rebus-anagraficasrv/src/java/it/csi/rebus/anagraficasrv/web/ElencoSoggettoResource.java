/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.anagraficasrv.business.service.RicercaSoggettoService;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;
import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;

@Path("elencoS")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public class ElencoSoggettoResource extends SpringSupportedResource {

	@Autowired
	RicercaSoggettoService ricercaSoggettoService;

	@GET
	@Path("/elencoSoggetto")
	public Response elencoSoggetto(@Context HttpServletRequest request) throws UtenteNonAbilitatoException {
//			VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_SOGGETTI);

		return Response.ok(ricercaSoggettoService.elencoSoggetto()).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/filtraElencoSoggetto")
	public Response filtraElencoSoggetto(@Context HttpServletRequest request, FiltroSoggettoVO filtro)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_SOGGETTI);

		return Response.ok(ricercaSoggettoService.filtraElencoSoggetto(filtro))
				.header("Access-Control-Allow-Origin", "*").build();
	}

}
