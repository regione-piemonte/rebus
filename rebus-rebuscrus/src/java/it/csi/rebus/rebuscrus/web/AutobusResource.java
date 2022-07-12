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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.CrudAutobusService;
import it.csi.rebus.rebuscrus.business.service.RicercaAutobusService;
import it.csi.rebus.rebuscrus.business.service.SecurityService;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.vo.AutobusDettagliatoVO;
import it.csi.rebus.rebuscrus.vo.AutobusVO;
import it.csi.rebus.rebuscrus.vo.ContribuzioneCompletaVO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;

@Path("autobus")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class AutobusResource extends SpringSupportedResource {

	private static final String UPLOADED_FILE_PATH = "no_mame";

	@Autowired
	CrudAutobusService crudAutobusService;

	@Autowired
	SecurityService securityService;
	
	@Autowired
	RicercaAutobusService ricercaAutobusService;

	// ************************* GET *************************//

	@GET
	@Path("/veicoliInserisci")
	public Response getVeicoliInserisci(@Context HttpServletRequest request,
			@QueryParam("idTipoProcedimento") Long idTipoProcedimento) {
		return Response.ok(crudAutobusService.getVeicoliInserisci(idTipoProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/veicoliModifica")
	public Response getVeicoliModifica(@Context HttpServletRequest request,
			@QueryParam("idProcedimento") Long idoProcedimento,
			@QueryParam("idTipoProcedimento") Long idTipoProcedimento) {
		return Response.ok(crudAutobusService.getVeicoliModifica(idoProcedimento, idTipoProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/depositi")
	public Response getDepositi(@Context HttpServletRequest request, @QueryParam("idAzienda") Long idAzienda) {
		return Response.ok(crudAutobusService.getDepositi(idAzienda)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/misurazioni")
	public Response getMisurazioni(@Context HttpServletRequest request, @QueryParam("primoTelaio") String primoTelaio) {
		return Response.ok(crudAutobusService.getMisurazioni(primoTelaio)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/depositoById")
	public Response getDepositoById(@Context HttpServletRequest request, @QueryParam("idDeposito") Long idDeposito) {
		return Response.ok(crudAutobusService.getDepositoById(idDeposito)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/getAutobusForContribuzioneAzienda")
	public Response getAutobusForContribuzioneAzienda() {
		return Response.ok(crudAutobusService.getAutobusForContribuzioneAzienda())
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getAllPortabiciForAutobus")
	public Response getAllPortabiciForAutobus(@Context HttpServletRequest request) {
		return Response.ok(crudAutobusService.getAllPortabiciForAutobus()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/getAllSistemaLocalizzazioneForAutobus")
	public Response getAllSistemaLocalizzazioneForAutobus(@Context HttpServletRequest request) {
		return Response.ok(crudAutobusService.getAllSistemaLocalizzazioneForAutobus())
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getAllSistemaVideosorveglianzaForAutobus")
	public Response getAllSistemaVideosorveglianzaForAutobus(@Context HttpServletRequest request) {
		return Response.ok(crudAutobusService.getAllSistemaVideosorveglianzaForAutobus())
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getDocVariazAutobusForInfo")
	public Response getDocVariazAutobusForInfo(@Context HttpServletRequest request, @QueryParam("idVa") Long idVa) {
		return Response.ok(crudAutobusService.getDocVariazAutobusForInfo(idVa))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipologiaDimensione")
	public Response tipologiaDimensione(@Context HttpServletRequest request,
			@QueryParam("idTipoAllestimento") Long idTipoAllestimento) throws Exception {
		return Response.ok(crudAutobusService.tipologiaDimensione(idTipoAllestimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* DETTAGLIO *************************//

	@GET
	@Path("/dettaglioAutobus/{id}")
	public Response dettaglioAutobus(@Context HttpServletRequest request, @PathParam("id") long id,
			@QueryParam("action") String action) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS);
		
		securityService.verificaAutorizzazioni(Constants.AUTOBUS, id, action);
		
		return Response
				.ok(crudAutobusService.dettaglioAutobus(id, action))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* DELETE *************************//

	@GET
	@Path("/eliminaAutobus")
	public Response eliminaAutobus(@Context HttpServletRequest request, @QueryParam("idBus") String idsVariazBus)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA);
		crudAutobusService.eliminaAutobus(idsVariazBus.split(","));
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* UPDATE *************************//

	@POST
	@Path("/modificaAutobus")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response modificaAutobus(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_BUS);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		AutobusVO autobusRequest = null;

		List<InputPart> isUpload = uploadForm.get("isUpload");
		Boolean isUploadB = null;
		if (isUpload != null && !isUpload.isEmpty()) {
			String bol = isUpload.get(0).getBodyAsString();
			isUploadB = new Boolean(bol);
		}
		List<InputPart> autobus = uploadForm.get("autobus");
		if (autobus != null && !autobus.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : autobus) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			autobusRequest = mapper.readValue(body.toString(), AutobusVO.class);
		}
		crudAutobusService.updateAutobus(autobusRequest, isUploadB);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* INSERT *************************//

	@POST
	@Path("/inserisciAutobus")
	@Consumes("multipart/form-data")
	public Response inserisciAutobus(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		AutobusDettagliatoVO autobusRequest = null;
		ContribuzioneCompletaVO contribuzioneRequest = null;

		List<InputPart> autobus = uploadForm.get("autobus");
		if (autobus != null && !autobus.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : autobus) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			autobusRequest = mapper.readValue(body.toString(), AutobusDettagliatoVO.class);
		}

		return Response.ok(crudAutobusService.inserisciAutobus(autobusRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* ELENCO *************************//

	@GET
	@Path("/elencoAutobus")
	public Response elencoAutobus(@Context HttpServletRequest request) throws Exception, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS);
		return Response.ok(ricercaAutobusService.elencoAutobus()).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/filtraElencoAutobus")
	public Response filtraElencoAutobus(@Context HttpServletRequest request, FiltroAutobusVO filtro)
			throws Exception, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS);
		return Response.ok(ricercaAutobusService.filtraElencoAutobus(filtro)).header("Access-Control-Allow-Origin", "*")
				.build();
	}
}
