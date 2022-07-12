/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.MessaggiService;
import it.csi.rebus.rebuscrus.business.service.SecurityService;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.vo.MessaggioVo;

@Path("messaggi")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class MessaggiResource extends SpringSupportedResource {

	private static Logger logger = Logger
			.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + "MessaggiResource");

	@Autowired
	MessaggiService messaggiService;

	@Autowired
	SecurityService securityService;

	@GET
	@Path("/calcolaNumMessaggi")
	public Response calcolaNumMessaggi(@Context HttpServletRequest request) {
		logger.info("calcolaNumMessaggi :: called");
		return Response.ok(messaggiService.calcolaNumMessaggi()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/elencoMessaggi")
	public Response elencoMessaggi(@Context HttpServletRequest request, @QueryParam("filter") Long filter)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MESSAGGISTICA);
		return Response.ok(messaggiService.elencoMessaggi(filter)).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/checkMessaggiNonLetti")
	public Response checkMessaggiNonLetti(@Context HttpServletRequest request, @QueryParam("filter") Long filter) {
		return Response.ok(messaggiService.checkMessaggiNonLetti(filter)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/dettaglioMessaggio")
	public Response dettaglioMessaggio(@Context HttpServletRequest request, @QueryParam("idMessaggio") Long idMessaggio)
			throws UtenteNonAbilitatoException {
//				VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MESSAGGISTICA);
		securityService.verificaAutorizzazioni(Constants.MESSAGGI, idMessaggio, null);

		return Response.ok(messaggiService.dettaglioMessaggio(idMessaggio)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/dettaglioUtenteAzEnte")
	public Response dettaglioUtenteAzEnte(@Context HttpServletRequest request, @QueryParam("idUtente") Long idUtente) {
		return Response.ok(messaggiService.dettaglioUtenteAzEnte(idUtente)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/ripristinaNonLetto")
	public Response ripristinaNonLetto(@Context HttpServletRequest request,
			@QueryParam("idMessaggio") Long idMessaggio) {
		messaggiService.ripristinaNonLetto(idMessaggio);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/segnaComeLetto")
	public Response segnaComeLetto(@Context HttpServletRequest request, @QueryParam("idMessaggio") Long idMessaggio) {
		messaggiService.segnaComeLetto(idMessaggio);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/inserisciMessaggio")
	@Consumes("multipart/form-data")
	public Response inserisciMessaggio(MultipartFormDataInput input) throws IOException {
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		MessaggioVo messaggioRequest = null;

		List<InputPart> messaggio = uploadForm.get("messaggio");
		if (messaggio != null && !messaggio.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : messaggio) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			messaggioRequest = mapper.readValue(body.toString(), MessaggioVo.class);
		}

		return Response.ok(messaggiService.inserisciMessaggio(messaggioRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

}
