/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import it.csi.rebus.anagraficasrv.business.service.ContrattoService;
import it.csi.rebus.anagraficasrv.business.service.SecurityService;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.util.Constants;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;
import it.csi.rebus.anagraficasrv.util.UploadFileUtils;
import it.csi.rebus.anagraficasrv.vo.AllegatoVO;
import it.csi.rebus.anagraficasrv.vo.ContrattoVO;
import it.csi.rebus.anagraficasrv.vo.InserisciContrattoVO;

@Path("contratto")
public class ContrattoResource extends SpringSupportedResource {
	private static final String UPLOADED_FILE_PATH = "no_mame";

	@Autowired
	private ContrattoService contrattoService;

	@Autowired
	private SecurityService securityService;

	// ------------------------- GET ------------------------- //

	@GET
	@Path("/getContenutoDocumentoById")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getContenutoDocumentoById(@QueryParam("idContratto") Long idContratto,
			@QueryParam("idDocumento") Long idDocumento) {
		return Response.ok()
				.entity(contrattoService.getContenutoDocumentoByIdContrattoAndTipo(idContratto, idDocumento)).build();
	}

	@GET
	@Path("/bacini")
	public Response getBacini(@Context HttpServletRequest request) {

		return Response.ok(contrattoService.getBacini()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipiAffidamento")
	public Response getTipiAffidamento(@Context HttpServletRequest request) {

		return Response.ok(contrattoService.getTipiAffidamento()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/modalitaAffidamento")
	public Response getModalitaAffidamento(@Context HttpServletRequest request) {

		return Response.ok(contrattoService.getModalitaAffidamento()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/tipologieServizio")
	public Response getTipologieServizio(@Context HttpServletRequest request) {

		return Response.ok(contrattoService.getTipologieServizio()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/ambitoTipoServizio")
	public Response getAmbTipServizio(@Context HttpServletRequest request) {

		return Response.ok(contrattoService.getAmbTipServizio()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/ambitiServizioByIdTipologiaServizio")
	public Response getAmbitiServizioByIdTipologiaServizio(@Context HttpServletRequest request,
			@QueryParam("idTipologiaServizio") Long idTipologiaServizio) {

		return Response.ok().entity(contrattoService.getAmbitiServizioByIdTipologiaServizio(idTipologiaServizio))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/descrizioneAmbTipServiziobyId")
	public Response getDescrizioneAmbTipServiziobyId(@Context HttpServletRequest request,
			@QueryParam("idAmbTipServizio") Long idAmbTipServizio) {

		return Response.ok().entity(contrattoService.getDescrizioneAmbTipServiziobyId(idAmbTipServizio))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipiDocumento")
	public Response getTipiDocumento(@Context HttpServletRequest request) {

		return Response.ok(contrattoService.getTipiDocumento()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/tipiSostituzione")
	public Response getTipiSostituzione(@Context HttpServletRequest request) {

		return Response.ok(contrattoService.getTipiSostituzione()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/aziendaMandataria")
	public Response getAziendaMandataria(@Context HttpServletRequest request,
			@QueryParam("idContratto") Long idContratto) {

		return Response.ok(contrattoService.getAziendaMandataria(idContratto))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/contrattiAmbitiTipologiaServizio")
	public Response getContrattiAmbitiTipologiaServizio(@Context HttpServletRequest request,
			@QueryParam("idContratto") Long idContratto) {

		return Response.ok(contrattoService.getContrattiAmbitiTipologiaServizio(idContratto))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/soggettiSubentro")
	public Response getSoggettiSubentro(@Context HttpServletRequest request,
			@QueryParam("idContratto") Long idContratto,
			@QueryParam("idTipoSoggContraente") Long idTipoSoggContraente) {

		return Response.ok(contrattoService.getSoggettiSubentro(idContratto, idTipoSoggContraente))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/filtraSoggettiCoinvolti")
	public Response filtraSoggettiCoinvolti(@Context HttpServletRequest request,
			@QueryParam("idContratto") Long idContratto, @QueryParam("dataFiltro") String dataFiltro) {

		Date date1 = new Date();
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dataFiltro);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Response.ok(contrattoService.filtraSoggettiCoinvolti(idContratto, date1))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/dettaglioContratto/{id}")
	public Response dettaglioContratto(@Context HttpServletRequest request, @PathParam("id") long id,
			@QueryParam("action") String action) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_CONTRATTO);
		securityService.verificaAutorizzazioni(Constants.CONTRATTO, id);
		return Response.ok(contrattoService.dettaglioContratto(id, action)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	// ------------------------- INSERISCI ------------------------- //

	@POST
	@Path("/inserisciContratto")
	public Response inserisciContratto(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_CONTRATTO);

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

		uploadForm.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});

		// Contratto
		InserisciContrattoVO contrattoRequest = null;
		List<InputPart> contratto = uploadForm.get("contratto");
		if (contratto != null && !contratto.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : contratto) {
				body.append(s.getBodyAsString());
			}

			ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			Map<String, Object> mappaPorcoValente = mapper.readValue(body.toString(), Map.class);

			for (String key : mappaPorcoValente.keySet()) {
				System.out.println("\n\nCHIAVE " + key + " con valore " + mappaPorcoValente.get(key));
			}
			contrattoRequest = mapper.readValue(body.toString(), InserisciContrattoVO.class);
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
							AllegatoVO AllegatoVO = contrattoRequest.getFiles().get(i);
							AllegatoVO.setFileByte(fileByte);
							AllegatoVO.setNomeFile(fileName);
							contrattoRequest.getFiles().set(i, AllegatoVO);
						}
					} catch (IOException e) {
						throw new RuntimeException("Errore estrazione file in upload", e);
					}
				}
			}
		}

		return Response.ok(contrattoService.inserisciContratto(contrattoRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	// ------------------------- MODIFICA ------------------------- //

	@POST
	@Path("/modificaContratto")
	public Response modificaContratto(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_ANAGRAFICA_CONTRATTO);

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		int numFile = 0;

		for (String key : uploadForm.keySet()) {
			if (key.contains("file")) {
				numFile++;
			}
		}
		// Contratto
		ContrattoVO contrattoRequest = null;
		List<InputPart> contratto = uploadForm.get("modificaContratto");
		if (contratto != null && !contratto.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : contratto) {
				body.append(s.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper().disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			contrattoRequest = mapper.readValue(body.toString(), ContrattoVO.class);
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
							AllegatoVO AllegatoVO = contrattoRequest.getAllegati().get(i);
							AllegatoVO.setFileByte(fileByte);
							AllegatoVO.setNomeFile(fileName);
							contrattoRequest.getAllegati().set(i, AllegatoVO);
						}
					} catch (IOException e) {
						throw new RuntimeException("Errore estrazione file in upload", e);
					}
				}
			}
		}

		return Response.ok(contrattoService.modificaContratto(contrattoRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

}
