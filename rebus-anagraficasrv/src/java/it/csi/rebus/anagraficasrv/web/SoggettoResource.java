/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
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

import org.apache.poi.util.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.anagraficasrv.business.service.SecurityService;
import it.csi.rebus.anagraficasrv.business.service.SoggettoService;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.util.Constants;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;
import it.csi.rebus.anagraficasrv.util.UploadFileUtils;
import it.csi.rebus.anagraficasrv.vo.InserisciSoggettoGiuridicoVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoGiuridicoVO;

@Path("soggetto")
public class SoggettoResource extends SpringSupportedResource {

	private static final String UPLOADED_FILE_PATH = "no_mame";

	@Autowired
	private SoggettoService soggettoService;

	@Autowired
	private SecurityService securityService;

	@GET
	@Path("/tipiSoggettoGiuridico")
	public Response getTipiSoggettoGiuridico(@Context HttpServletRequest request,
			@QueryParam("idTipiSoggettoGiuridico") String idTipiSoggettoGiuridico) {

		return Response
				.ok(soggettoService.getTipiSoggettoGiuridico(
						idTipiSoggettoGiuridico == null ? null : Boolean.valueOf(idTipiSoggettoGiuridico)))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/natureGiuridiche")
	public Response getNatureGiuridiche(@Context HttpServletRequest request) {
		return Response.ok(soggettoService.getNatureGiuridiche()).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/inserisciSoggetto")
	public Response inserisciSoggetto(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.INSERIMENTO_ANAGRAFICA_SOGGETTO);

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		// FILE
		List<InputPart> file = uploadForm.get("file");
		byte[] fileByte = null;
		String fileName = UPLOADED_FILE_PATH;
		if (file != null && !file.isEmpty()) {
			for (InputPart inputPart : file) {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = UploadFileUtils.getFileName(header);

				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				fileByte = IOUtils.toByteArray(inputStream);
			}
		}
		// SOGGETTO
		InserisciSoggettoGiuridicoVO soggettoRequest = null;
		List<InputPart> soggetto = uploadForm.get("soggetto");
		if (soggetto != null && !soggetto.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : soggetto) {
				body.append(s.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			soggettoRequest = mapper.readValue(body.toString(), InserisciSoggettoGiuridicoVO.class);
		}
		return Response.ok(soggettoService.inserisciSoggetto(soggettoRequest, fileByte, fileName))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/dettaglioSoggetto/{id}")
	public Response dettaglioSoggetto(@Context HttpServletRequest request, @PathParam("id") long id,
			@QueryParam("action") String action)

			throws UtenteNonAbilitatoException {
//			VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_SOGGETTO);
		securityService.verificaAutorizzazioni(Constants.SOGGETTO, id);
		return Response.ok(soggettoService.dettaglioSoggetto(id, action)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@POST
	@Path("/modificaSoggetto")
	public Response modificaSoggetto(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_ANAGRAFICA_SOGGETTO);

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		// FILE
		List<InputPart> file = uploadForm.get("file");
		byte[] fileByte = null;
		String fileName = UPLOADED_FILE_PATH;
		if (file != null && !file.isEmpty()) {
			for (InputPart inputPart : file) {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = UploadFileUtils.getFileName(header);

				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				fileByte = IOUtils.toByteArray(inputStream);
			}
		}
		// SOGGETTO
		SoggettoGiuridicoVO soggettoRequest = null;
		List<InputPart> soggetto = uploadForm.get("soggetto");
		if (soggetto != null && !soggetto.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : soggetto) {
				body.append(s.getBodyAsString());
			}
			ObjectMapper mapper = new ObjectMapper();
			soggettoRequest = mapper.readValue(body.toString(), SoggettoGiuridicoVO.class);
		}
		return Response.ok(soggettoService.modificaSoggetto(soggettoRequest, fileByte, fileName))
				.header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/soggettiEsecutoriTitolari")
	public Response getSoggettiEsecutoriTitolari(@Context HttpServletRequest request,
			@QueryParam("idSoggettiEsecutoriTitolari") String idSoggettiEsecutoriTitolari,
			@QueryParam("idSoggettoGiuridico") Long idSoggettoGiuridico) {

		return Response.ok(soggettoService.getSoggettiEsecutoriTitolari(
				idSoggettiEsecutoriTitolari == null ? null : Boolean.valueOf(idSoggettiEsecutoriTitolari),
				idSoggettoGiuridico)).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/logoByIdSoggetto")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getLogoByIdSoggetto(@QueryParam("id") Long id) {
		return Response.ok().entity(soggettoService.getLogoByIdSoggetto(id)).build();
	}

	@DELETE
	@Path("/eliminaLogoByIdSoggetto")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response eliminaLogoByIdSoggetto(@QueryParam("idLogo") Long idLogo) {
		soggettoService.eliminaLogoOnDb(idLogo);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
}
