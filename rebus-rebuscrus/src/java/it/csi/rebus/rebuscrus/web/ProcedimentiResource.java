/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.ProcedimentiService;
import it.csi.rebus.rebuscrus.business.service.SecurityService;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.util.UploadFileUtils;
import it.csi.rebus.rebuscrus.vo.AllegatoProcVO;
import it.csi.rebus.rebuscrus.vo.DettaglioRichiestaVO;
import it.csi.rebus.rebuscrus.vo.FiltroProcedimentiVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaUsoInLineaVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaVO;
import it.csi.rebus.rebuscrus.vo.TransizioneAutomaVO;

@Path("procedimenti")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ProcedimentiResource extends SpringSupportedResource {

	@Autowired
	ProcedimentiService procedimentiService;

	@Autowired
	SecurityService securityService;

	// ************************* GET *************************//

	@GET
	@Path("/elencoTipologia")
	public Response elencoTipologia(@Context HttpServletRequest request) {
		return Response.ok(procedimentiService.elencoTipologia()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/elencoStato")
	public Response elencoStato(@Context HttpServletRequest request) {
		return Response.ok(procedimentiService.elencoStato()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipoProcedimento")
	public Response getTipoProcedimento(@Context HttpServletRequest request, @QueryParam("id") Long id) {
		return Response.ok(procedimentiService.getTipoProcedimento(id)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/tipoProcedimentoByIdProc")
	public Response getTipoProcedimentoByIdProc(@Context HttpServletRequest request, @QueryParam("id") Long id) {
		return Response.ok(procedimentiService.getTipoProcedimentoByIdProc(id))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/descrizioneAziendaMessaggioProc")
	public Response descrizioneAziendaMessaggioProc(@Context HttpServletRequest request,
			@QueryParam("idProcedimento") Long idProcedimento) {
		return Response.ok().entity(procedimentiService.descrizioneAziendaMessaggioProc(idProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/motorizzazioni")
	public Response getMotorizzazioni(@Context HttpServletRequest request) {
		return Response.ok(procedimentiService.getMotorizzazioni()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/motivazioni")
	public Response getMotivazioni(@Context HttpServletRequest request,
			@QueryParam("idTipoProcedimento") Long idTipoProcedimento,
			@QueryParam("hasDataFilter") boolean hasDataFilter) {
		return Response.ok(procedimentiService.getMotivazioni(idTipoProcedimento, hasDataFilter))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/contrattiInserisci")
	public Response getContrattiInserisci(@Context HttpServletRequest request) {
		return Response.ok(procedimentiService.getContrattiInserisci()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/contrattiModifica")
	public Response getContrattiModifica(@Context HttpServletRequest request,
			@QueryParam("idProcedimento") Long idProcedimento) {
		return Response.ok(procedimentiService.getContrattiModifica(idProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/datiContratto")
	public Response getDatiContratto(@Context HttpServletRequest request, @QueryParam("idContratto") Long idContratto,
			@QueryParam("idProcedimento") Long idProcedimento) {
		return Response.ok(procedimentiService.getDatiContratto(idContratto, idProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipiContratto")
	public Response getTipiContratto(@Context HttpServletRequest request) {
		return Response.ok(procedimentiService.getTipiContratto()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipiDocumento")
	public Response getTipiDocumento(@Context HttpServletRequest request,
			@QueryParam("idTipoProcedimento") Long idTipoProcedimento) {
		return Response.ok(procedimentiService.getTipiDocumento(idTipoProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/firmaProcedimenti")
	public Response firmaProcedimento(@Context HttpServletRequest request) {
		return Response.ok(procedimentiService.getFirmaProcedimento()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/tipiMessaggio")
	public Response getTipiMessaggio(@Context HttpServletRequest request, @QueryParam("idContesto") Long idContesto) {
		return Response.ok(procedimentiService.getTipiMessaggio(idContesto)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/vociDiCosto")
	public Response getVociDiCosto(@Context HttpServletRequest request) {
		return Response.ok(procedimentiService.getVociDiCosto()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/numProcedimento")
	public Response getNumProcedimento(@Context HttpServletRequest request,
			@QueryParam("idTipoProcedimento") Long idTipoProcedimento) throws Exception {
		return Response.ok(procedimentiService.getNumProcedimento(idTipoProcedimento))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* ELENCO PROCEDIMENTI *************************//

	@GET
	@Path("/elencoProcedimenti")
	public Response elencoProcedimenti(@Context HttpServletRequest request) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_PROCEDIMENTI);
		return Response.ok(procedimentiService.elencoProcedimenti()).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/filtraElencoProcedimenti")
	public Response filtraElencoProcedimenti(@Context HttpServletRequest request, FiltroProcedimentiVO filtro)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_PROCEDIMENTI);
		return Response.ok(procedimentiService.filtraElencoProcedimenti(filtro))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* DELETE *************************//

	@GET
	@Path("/eliminaProcedimento")
	public Response eliminaProcedimento(@Context HttpServletRequest request,
			@QueryParam("idProcedimento") Long idProcedimento) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.ELIMINA_PROCEDIMENTO);
		procedimentiService.eliminaProcedimento(idProcedimento);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* INSERT *************************//

	@POST
	@Path("/inserisciRichiesta")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response inserisciRichiesta(MultipartFormDataInput input) throws Exception, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.INSERIMENTO_PROCEDIMENTO);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		int numFile = 0;

		for (String key : uploadForm.keySet()) {
			if (key.contains("file")) {
				numFile++;
			}
		}

		uploadForm.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});

		// Richiesta
		InserisciRichiestaVO richiestaRequest = null;
		List<InputPart> richiesta = uploadForm.get("richiesta");
		if (richiesta != null && !richiesta.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : richiesta) {
				body.append(s.getBodyAsString());
			}

			ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			Map<String, Object> mappaPorcoValente = mapper.readValue(body.toString(), Map.class);

			for (String key : mappaPorcoValente.keySet()) {
				System.out.println("\n\nCHIAVE " + key + " con valore " + mappaPorcoValente.get(key));
			}
			richiestaRequest = mapper.readValue(body.toString(), InserisciRichiestaVO.class);
		}

		// FILE
		if (numFile > 0) {
			for (int i = 0; i < numFile; i++) {
				List<InputPart> file = uploadForm.get("file" + i);
				byte[] fileByte = null;
				String fileName = new String();
				// Map<String, byte[]> fileMap = new HashMap<>();
				if (file != null && !file.isEmpty()) {
					try {
						for (InputPart inputPart : file) {

							MultivaluedMap<String, String> header = inputPart.getHeaders();
							fileName = UploadFileUtils.getFileName(header);

							InputStream inputStream = inputPart.getBody(InputStream.class, null);
							fileByte = IOUtils.toByteArray(inputStream);
							AllegatoProcVO AllegatoProcVO = richiestaRequest.getFiles().get(i);
							AllegatoProcVO.setFile(fileByte);
							AllegatoProcVO.setNomeFile(fileName);
							richiestaRequest.getFiles().set(i, AllegatoProcVO);
						}
					} catch (IOException e) {
						throw new RuntimeException("Errore estrazione file in upload", e);
					}
				}
			}
		}

		return Response.ok(procedimentiService.inserisciRichiesta(richiestaRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/inserisciRichiestaUsoInLinea")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response inserisciRichiestaUsoInLinea(MultipartFormDataInput input)
			throws Exception, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.INSERIMENTO_PROCEDIMENTO);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		int numFile = 0;

		for (String key : uploadForm.keySet()) {
			if (key.contains("file")) {
				numFile++;
			}
		}

		uploadForm.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});

		// Richiesta
		InserisciRichiestaUsoInLineaVO richiestaRequest = null;
		List<InputPart> richiesta = uploadForm.get("richiesta");
		if (richiesta != null && !richiesta.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : richiesta) {
				body.append(s.getBodyAsString());
			}

			ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

			richiestaRequest = mapper.readValue(body.toString(), InserisciRichiestaUsoInLineaVO.class);
		}

		// FILE
		if (numFile > 0) {
			for (int i = 0; i < numFile; i++) {
				List<InputPart> file = uploadForm.get("file" + i);
				byte[] fileByte = null;
				String fileName = new String();
				// Map<String, byte[]> fileMap = new HashMap<>();
				if (file != null && !file.isEmpty()) {
					try {
						for (InputPart inputPart : file) {

							MultivaluedMap<String, String> header = inputPart.getHeaders();
							fileName = UploadFileUtils.getFileName(header);

							InputStream inputStream = inputPart.getBody(InputStream.class, null);
							fileByte = IOUtils.toByteArray(inputStream);
							AllegatoProcVO AllegatoProcVO = richiestaRequest.getFiles().get(i);
							AllegatoProcVO.setFile(fileByte);
							AllegatoProcVO.setNomeFile(fileName);
							richiestaRequest.getFiles().set(i, AllegatoProcVO);
						}
					} catch (IOException e) {
						throw new RuntimeException("Errore estrazione file in upload", e);
					}
				}
			}
		}

		return Response.ok(procedimentiService.inserisciRichiestaUsoInLinea(richiestaRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/inserisciRichiestaSostituzione")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response inserisciRichiestaSostituzione(MultipartFormDataInput input)
			throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.INSERIMENTO_PROCEDIMENTO);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		int numFile = 0;
		int numRichieste = 0;

		for (String key : uploadForm.keySet()) {
			if (key.contains("file")) {
				numFile++;
			}
		}
		for (String key : uploadForm.keySet()) {
			if (key.contains("richiesta")) {
				numRichieste++;
			}
		}

		uploadForm.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});

		List<InserisciRichiestaVO> richiestaRequests = new ArrayList<>();
		if (numRichieste > 0) {
			for (int i = 0; i < numRichieste; i++) {
				InserisciRichiestaVO richiestaRequest = null;
				List<InputPart> richiesta = uploadForm.get("richiesta" + i);
				if (richiesta != null && !richiesta.isEmpty()) {
					StringBuilder body = new StringBuilder();
					for (InputPart s : richiesta) {
						body.append(s.getBodyAsString());
					}

					ObjectMapper mapper = new ObjectMapper()
							.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
					Map<String, Object> mappaPorcoValente = mapper.readValue(body.toString(), Map.class);

					for (String key : mappaPorcoValente.keySet()) {
						System.out.println("\n\nCHIAVE " + key + " con valore " + mappaPorcoValente.get(key));
					}
					richiestaRequest = mapper.readValue(body.toString(), InserisciRichiestaVO.class);

					if (numFile > 0) {
						for (int j = 0; j < numFile; j++) {
							List<InputPart> file = uploadForm
									.get("file" + richiestaRequest.getTipoProcedimento().getId() + j);
							byte[] fileByte = null;
							String fileName = new String();
							if (file != null && !file.isEmpty()) {
								try {
									for (InputPart inputPart : file) {

										MultivaluedMap<String, String> header = inputPart.getHeaders();
										fileName = UploadFileUtils.getFileName(header);

										InputStream inputStream = inputPart.getBody(InputStream.class, null);
										fileByte = IOUtils.toByteArray(inputStream);
										AllegatoProcVO AllegatoProcVO = richiestaRequest.getFiles().get(j);
										AllegatoProcVO.setFile(fileByte);
										AllegatoProcVO.setNomeFile(fileName);
										richiestaRequest.getFiles().set(j, AllegatoProcVO);
									}
								} catch (IOException e) {
									throw new RuntimeException("Errore estrazione file in upload", e);
								}
							}
						}
					}
					richiestaRequests.add(richiestaRequest);
				}
			}
		}
		return Response.ok(procedimentiService.inserisciRichiestaSostituzione(richiestaRequests))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* DETTAGLIO *************************//

	@GET
	@Path("/dettaglioRichiesta/{id}")
	public Response dettaglioRichiesta(@Context HttpServletRequest request, @PathParam("id") long id,
			@QueryParam("action") String action) throws Exception, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.DETTAGLIO_PROCEDIMENTO);

		securityService.verificaAutorizzazioni(Constants.PROCEDIMENTI, id, action);

		return Response.ok(procedimentiService.dettaglioRichiesta(id, action))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* UPDATE *************************//

	@POST
	@Path("/modificaRichiesta")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response modificaRichiesta(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_PROCEDIMENTO);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		int numFile = 0;

		for (String key : uploadForm.keySet()) {
			if (key.contains("file")) {
				numFile++;
			}
		}

		// Richiesta
		DettaglioRichiestaVO richiestaRequest = null;
		List<InputPart> richiesta = uploadForm.get("modificaRichiesta");
		if (richiesta != null && !richiesta.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : richiesta) {
				body.append(s.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			richiestaRequest = mapper.readValue(body.toString(), DettaglioRichiestaVO.class);
		}
		// FILE
		if (numFile > 0) {
			for (int i = 0; i < numFile; i++) {
				List<InputPart> file = uploadForm.get("file" + i);
				byte[] fileByte = null;
				String fileName = new String();
				if (file != null && !file.isEmpty()) {
					try {
						for (InputPart inputPart : file) {

							MultivaluedMap<String, String> header = inputPart.getHeaders();
							fileName = UploadFileUtils.getFileName(header);

							InputStream inputStream = inputPart.getBody(InputStream.class, null);
							fileByte = IOUtils.toByteArray(inputStream);
							AllegatoProcVO AllegatoProcVO = richiestaRequest.getFiles().get(i);
							AllegatoProcVO.setFile(fileByte);
							AllegatoProcVO.setNomeFile(fileName);
							richiestaRequest.getFiles().set(i, AllegatoProcVO);
						}
					} catch (IOException e) {
						throw new RuntimeException("Errore estrazione file in upload", e);
					}
				}
			}
		}

		return Response.ok(procedimentiService.modificaRichiesta(richiestaRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/modificaRichiestaSostituzione")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response modificaRichiestaSostituzione(MultipartFormDataInput input)
			throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_PROCEDIMENTO);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		int numFile = 0;
		int numRichieste = 0;

		for (String key : uploadForm.keySet()) {
			if (key.contains("file")) {
				numFile++;
			}
		}
		for (String key : uploadForm.keySet()) {
			if (key.contains("richiesta")) {
				numRichieste++;
			}
		}

		uploadForm.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});

		List<DettaglioRichiestaVO> richiestaRequests = new ArrayList<>();
		if (numRichieste > 0) {
			for (int i = 0; i < numRichieste; i++) {
				DettaglioRichiestaVO richiestaRequest = null;
				List<InputPart> richiesta = uploadForm.get("richiesta" + i);
				if (richiesta != null && !richiesta.isEmpty()) {
					StringBuilder body = new StringBuilder();
					for (InputPart s : richiesta) {
						body.append(s.getBodyAsString());
					}

					ObjectMapper mapper = new ObjectMapper()
							.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
					Map<String, Object> mappaPorcoValente = mapper.readValue(body.toString(), Map.class);

					for (String key : mappaPorcoValente.keySet()) {
						System.out.println("\n\nCHIAVE " + key + " con valore " + mappaPorcoValente.get(key));
					}
					richiestaRequest = mapper.readValue(body.toString(), DettaglioRichiestaVO.class);

					if (numFile > 0) {
						for (int j = 0; j < numFile; j++) {
							List<InputPart> file = uploadForm
									.get("file" + richiestaRequest.getIdTipoProcedimento() + j);
							byte[] fileByte = null;
							String fileName = new String();
							if (file != null && !file.isEmpty()) {
								try {
									for (InputPart inputPart : file) {
										MultivaluedMap<String, String> header = inputPart.getHeaders();
										fileName = UploadFileUtils.getFileName(header);

										InputStream inputStream = inputPart.getBody(InputStream.class, null);
										fileByte = IOUtils.toByteArray(inputStream);
										AllegatoProcVO AllegatoProcVO = richiestaRequest.getFiles().get(j);
										AllegatoProcVO.setFile(fileByte);
										AllegatoProcVO.setNomeFile(fileName);
										richiestaRequest.getFiles().set(j, AllegatoProcVO);
									}
								} catch (IOException e) {
									throw new RuntimeException("Errore estrazione file in upload", e);
								}
							}
						}
					}
					richiestaRequests.add(richiestaRequest);
				}
			}
		}
		return Response.ok(procedimentiService.modificaRichiestaSostituzione(richiestaRequests))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ************************* TRANSAZIONE AUTOMA *************************//

	@GET
	@Path("/transizioniAutoma")
	public Response getTransizioniAutoma(@Context HttpServletRequest request,
			@QueryParam("idProcedimento") Long idProcedimento,
			@QueryParam("idStatoIterRichiesta") Long idStatoIterRichiesta, @QueryParam("parte") String parte)
			throws Exception {
		return Response.ok(procedimentiService.getTransizioniAutoma(idProcedimento, idStatoIterRichiesta, parte))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/avanzaIterRichiesta")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response avanzaIterRichiesta(MultipartFormDataInput input) throws Exception {
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		// Richiesta
		DettaglioRichiestaVO richiestaRequest = null;
		List<InputPart> richiesta = uploadForm.get("dettaglioRichiesta");
		if (richiesta != null && !richiesta.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : richiesta) {
				body.append(s.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			richiestaRequest = mapper.readValue(body.toString(), DettaglioRichiestaVO.class);
		}

		// Transizione Automa
		TransizioneAutomaVO transizioneRequest = null;
		List<InputPart> transizione = uploadForm.get("transizioneAutoma");
		if (transizione != null && !transizione.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : transizione) {
				body.append(s.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			transizioneRequest = mapper.readValue(body.toString(), TransizioneAutomaVO.class);
		}
		// Transizione Automa
		String notaTransizione = null;
		List<InputPart> nota = uploadForm.get("notaTransizione");
		if (nota != null && !nota.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : nota) {
				notaTransizione = s.getBodyAsString();
			}
		}
		notaTransizione = notaTransizione.substring(1, notaTransizione.length() - 1);

		return Response
				.ok(procedimentiService.avanzaIterRichiesta(richiestaRequest, transizioneRequest, notaTransizione))
				.header("Access-Control-Allow-Origin", "*").build();
	}

}
