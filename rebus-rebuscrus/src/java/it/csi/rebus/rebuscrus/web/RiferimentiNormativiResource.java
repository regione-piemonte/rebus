/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import java.io.IOException;
import java.io.InputStream;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.RiferimentiNormativiService;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.util.UploadFileUtils;
import it.csi.rebus.rebuscrus.vo.InserisciRiferimentoVO;

@Path("riferimentiNormativi")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class RiferimentiNormativiResource extends SpringSupportedResource {

	@Autowired
	RiferimentiNormativiService riferimentiNormativiService;

	@GET
	@Path("/elencoriferimentiNormativi")
	public Response elencoRiferimenti(@Context HttpServletRequest request) {
		return Response.ok(riferimentiNormativiService.elencoRiferimenti()).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/eliminaRiferimentoNormativo")
	public Response eliminaRiferimentoNormativo(@Context HttpServletRequest request, @QueryParam("idDocumento") Long idDocumento) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_RIFERIMENTI);
		riferimentiNormativiService.eliminaRiferimento(idDocumento);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getDocumentoByIdDocumento")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getDocumentoByIdDocumento(@QueryParam("idDocumento") Long idDocumento) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.SCARICA_RIFERIMENTI);
		return Response.ok().entity(riferimentiNormativiService.getDocumentoByIdDocumento(idDocumento)).build();
	}

	@POST
	@Path("/inserisciRiferimento")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response inserisciRiferimento(MultipartFormDataInput input) throws IOException, UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_RIFERIMENTI);
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

		uploadForm.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});

		// Richiesta
		InserisciRiferimentoVO riferimentoRequest = new InserisciRiferimentoVO();
		List<InputPart> riferimento = uploadForm.get("descrizione");
		if (riferimento != null && !riferimento.isEmpty()) {
			StringBuilder body = new StringBuilder();
			for (InputPart s : riferimento) {
				body.append(s.getBodyAsString());
			}

			riferimentoRequest.setDescrizione(body.toString());
			System.out.println("\nDescrizione " + riferimentoRequest.getDescrizione());
		}

		// FILE

		List<InputPart> file = uploadForm.get("uploadedFile");
		byte[] fileByte = null;
		String fileName = new String();
		if (file != null && !file.isEmpty()) {
			try {
				for (InputPart inputPart : file) {

					MultivaluedMap<String, String> header = inputPart.getHeaders();
					fileName = UploadFileUtils.getFileName(header);

					InputStream inputStream = inputPart.getBody(InputStream.class, null);
					fileByte = IOUtils.toByteArray(inputStream);
					riferimentoRequest.setFile(fileByte);
					riferimentoRequest.setNomeFile(fileName);
				}
			} catch (IOException e) {
				throw new RuntimeException("Errore estrazione file in upload", e);
			}

		}

		return Response.ok(riferimentiNormativiService.inserisciRiferimento(riferimentoRequest))
				.header("Access-Control-Allow-Origin", "*").build();
	}

}
