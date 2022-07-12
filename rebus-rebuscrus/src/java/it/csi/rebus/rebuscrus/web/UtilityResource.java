/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import java.util.HashMap;

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

import it.csi.rebus.rebuscrus.business.service.RicercaAutobusService;
import it.csi.rebus.rebuscrus.business.service.UtilityService;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensDTO;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;

/**
 * @author 72262
 * @date 09 feb 2018
 */
@Path("utilita")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class UtilityResource extends SpringSupportedResource {

	@Autowired
	UtilityService utilityService;
	
	@Autowired
	RicercaAutobusService ricercaAutobusService;

	@GET
	@Path("/elencoDecodifiche")
	public Response elencoDecodifiche() {
		return Response.ok(utilityService.getDecodificheCombo()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getContesto")
	public Response getContesto(@Context HttpServletRequest request) {
		return Response.ok(utilityService.getElencoContesto()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getContestoHome")
	public Response getContestoHome(@Context HttpServletRequest request) {
		return Response.ok(utilityService.getContestoHome()).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/progressivoTipoDimensione")
	public Response getProgressivoTipoDimensione(@Context HttpServletRequest request,
			AutobusDettagliatoVO autobusRequest) {
		RebusDTipologiaDimensDTO tipologia = utilityService.getTipologiaDimensionale(autobusRequest.idTipoAllestimento,
				autobusRequest.getLunghezza());
		String descrizione = (tipologia != null) ? tipologia.getTipologiaDimens() : "ND";
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("descrizione", descrizione);
		return Response.ok(result).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	// ************************* ELENCO AZIENDE *************************//
	
	@GET
	@Path("/elencoAziende")
	public Response elencoAziende(@Context HttpServletRequest request) throws Exception {
		return Response.ok(ricercaAutobusService.elencoAziende()).header("Access-Control-Allow-Origin", "*").build();
	}
}
