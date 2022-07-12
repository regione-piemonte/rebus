/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.ContribuzioneService;
import it.csi.rebus.rebuscrus.common.Messages;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.vo.ContribuzioneCompletaVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaVO;

/**
 * @author francesco.mancuso
 * @date 22 nov 2021
 */
@Path("contribuzione")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ContribuzioneResource extends SpringSupportedResource {

	@Autowired
	ContribuzioneService contribuzioneService;

	// ************************* GET *************************//

	// Get DropDow Voce di costi contribuzione
	@GET
	@Path("/getAllVoceCostoContribuzione")
	public Response getAllVoceCostoContribuzione(@Context HttpServletRequest request) {
		return Response.ok(contribuzioneService.getAllVoceCostoContribuzione())
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// Get DropDown Atto asseganzione risorse contribuzione
	@GET
	@Path("/getAllAttoAssegnazioneRisorse")
	public Response getAllAttoAssegnazioneRisorse(@Context HttpServletRequest request) {
		return Response.ok(contribuzioneService.getAllAttoAssegnazioneRisorse())
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// Get DropDow tipo documento quietanza
	@GET
	@Path("/getAllTipoDocumentoQuietanza")
	public Response getAllTipoDocumentoQuietanza(@Context HttpServletRequest request) {
		return Response.ok(contribuzioneService.getAllTipoDocumentoQuietanza())
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// Get DropDown tipo sostituzione
	@GET
	@Path("/getAllTipoSostituzione")
	public Response getAllTipoSostituzione(@Context HttpServletRequest request) {
		return Response.ok(contribuzioneService.getAllTipoSostituzione()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	// Get DropDown fonte di finanziamento
	@GET
	@Path("/getAllFonteFinanziamento")
	public Response getAllFonteFinanziamento(@Context HttpServletRequest request) {
		return Response.ok(contribuzioneService.getAllFonteFinanziamento()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	// Get DropDown fonte di finanziamento by id atto assegnazione
	@GET
	@Path("/getAllFonteFinanziamentoByIdAttoAssegnazione")
	public Response getAllFonteFinanziamentoByIdAttoAssegnazione(@Context HttpServletRequest request,
			@QueryParam("idAttoAssegnazione") Long idAttoAssegnazione) {
		return Response.ok(contribuzioneService.getAllFonteFinanziamentoByIdAttoAssegnazione(idAttoAssegnazione))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// GET CONTRIBUZIONE
	@GET
	@Path("/getContribuzioneCompletaByIdProcedimento")
	public Response getContribuzioneCompletaByIdProcedimento(@Context HttpServletRequest request,
			@QueryParam("idProcedimento") Long idProcedimento) throws UtenteNonAbilitatoException {
//			VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE);
		return Response.ok(contribuzioneService.getContribuzioneCompletaByIdProcedimento(idProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getContribuzioneCompletaById")
	public Response getContribuzioneCompletaById(@Context HttpServletRequest request,
			@QueryParam("idContribuzione") Long idContribuzione) throws UtenteNonAbilitatoException {
//			VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE);
		return Response.ok(contribuzioneService.getContribuzioneCompletaById(idContribuzione))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getDocContribuzione")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getDocContribuzione(@Context HttpServletRequest request, @QueryParam("idDoc") Long idDoc) {
		byte[] a = contribuzioneService.getDocContribuzione(idDoc);
		return Response.ok(a).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getTelaioByIdProcedimento")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTelaioByIdProcedimento(@Context HttpServletRequest request,
			@QueryParam("idProcedimento") Long idProcedimento) {
		return Response.ok(contribuzioneService.getTelaioByIdProcedimento(idProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/getTelaiVeicoloDaSostituire")
	public Response getTelaiVeicoloDaSostituire(@Context HttpServletRequest request) {
		return Response.ok(contribuzioneService.getTelaiVeicoloDaSostituire())
				.header("Access-Control-Allow-Origin", "*").build();
	}


	// ************************* INSERT *************************//

	@POST
	@Path("/inserisciContribuzione")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response insertContribuzione(MultipartFormDataInput input)
			throws JsonParseException, JsonMappingException, IOException, UtenteNonAbilitatoException {

		// VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.INSERIMENTO_CONTRIBUZIONE);

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		Long idContribuzione;

		List<InputPart> contribuzione = uploadForm.get("contribuzione");
		ContribuzioneCompletaVO contribuzioneCompleta = null;
		if (contribuzione != null && !contribuzione.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : contribuzione) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			contribuzioneCompleta = mapper.readValue(body.toString(), ContribuzioneCompletaVO.class);
		}
		List<InputPart> richiesta = uploadForm.get("richiesta");
		InserisciRichiestaVO inserisciRichiesta = null;
		if (richiesta != null && !richiesta.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : richiesta) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			inserisciRichiesta = mapper.readValue(body.toString(), InserisciRichiestaVO.class);
		}

//		// Controllo la contribuzione completa che mi invia il FE, se null ritorno un
//		// errore
		if (contribuzioneCompleta == null || inserisciRichiesta == null) {
			return Response.status(400).entity(new ErroreGestitoException(Messages.ERRORE_SALVATAGGIO_CONTRIBUZIONE))
					.build();
		}

		try {
			idContribuzione = contribuzioneService.insertContribuzione(contribuzioneCompleta, inserisciRichiesta);
			return Response.ok(idContribuzione).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(new ErroreGestitoException(Messages.ERRORE_SALVATAGGIO_CONTRIBUZIONE))
					.build();
		}
	}

	// ************************* ELENCO *************************//

	@GET
	@Path("/elencoContribuzione")
	public Response elencoContribuzione(@Context HttpServletRequest request) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_CONTRIBUZIONE);
		return Response.ok(contribuzioneService.elencoContribuzione()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@POST
	@Path("/filtraElencoContribuzione")
	public Response filtraElencoContribuzione(@Context HttpServletRequest request, FiltroContribuzioneVO filtro)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_CONTRIBUZIONE);
		return Response.ok(contribuzioneService.filtraElencoContribuzione(filtro))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* MODIFICA *************************//

	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateContribuzione")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateContribuzione(MultipartFormDataInput input)
			throws JsonParseException, JsonMappingException, IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE);

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		Long idContribuzione;

		List<InputPart> contribuzione = uploadForm.get("contribuzione");
		ContribuzioneCompletaVO contribuzioneCompleta = null;
		if (contribuzione != null && !contribuzione.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : contribuzione) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			contribuzioneCompleta = mapper.readValue(body.toString(), ContribuzioneCompletaVO.class);
		}

		List<InputPart> vociCosto = uploadForm.get("listaVociCostoDaEliminare");
		List<Long> listaVociCostoDaEliminare = new ArrayList<Long>();
		if (vociCosto != null && !vociCosto.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : vociCosto) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			List<Integer> lista = mapper.readValue(body.toString(), List.class);
			listaVociCostoDaEliminare = lista.stream().mapToLong(Integer::longValue).boxed()
					.collect(Collectors.toList());
		}

		List<InputPart> datiFattura = uploadForm.get("listaDatiFatturaDaEliminare");
		List<Long> listaDatiFatturaDaEliminare = new ArrayList<Long>();
		if (datiFattura != null && !datiFattura.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : datiFattura) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			List<Integer> lista = mapper.readValue(body.toString(), List.class);
			listaDatiFatturaDaEliminare = lista.stream().mapToLong(Integer::longValue).boxed()
					.collect(Collectors.toList());
		}

		List<InputPart> datiBonifico = uploadForm.get("listaDatiBonificoDaEliminare");
		List<Long> listaDatiBonificoDaEliminare = new ArrayList<Long>();
		if (datiBonifico != null && !datiBonifico.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart aut : datiBonifico) {
				body.append(aut.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			List<Integer> lista = mapper.readValue(body.toString(), List.class);
			listaDatiBonificoDaEliminare = lista.stream().mapToLong(Integer::longValue).boxed()
					.collect(Collectors.toList());
		}

//		// Controllo la contribuzione completa che mi invia il FE, se null ritorno un
//		// errore
		if (contribuzioneCompleta == null) {
			return Response.status(400).entity(new ErroreGestitoException(Messages.ERRORE_SALVATAGGIO_CONTRIBUZIONE))
					.build();
		}

		try {
			idContribuzione = contribuzioneService.updateContribuzione(contribuzioneCompleta, listaVociCostoDaEliminare,
					listaDatiFatturaDaEliminare, listaDatiBonificoDaEliminare);
			return Response.ok(idContribuzione).header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(400).entity(new ErroreGestitoException(Messages.ERRORE_SALVATAGGIO_CONTRIBUZIONE))
					.build();
		}
	}
	
	//************************* UTILITY *************************//

	@GET
	@Path("/checkFinalStateIter")
	public Response checkFinalStateIter(@Context HttpServletRequest request, @QueryParam("idStato") Long idStato) {
		return Response.ok(contribuzioneService.checkFinalStateIter(idStato)).header("Access-Control-Allow-Origin", "*")
				.build();
	}


}
