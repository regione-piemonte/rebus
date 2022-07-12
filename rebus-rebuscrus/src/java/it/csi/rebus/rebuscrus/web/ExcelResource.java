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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.csi.rebus.rebuscrus.business.service.ExcelService;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.vo.ExcelVO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;

/**
 * @author 70525
 * @date 24 nov 2017
 */
@Path("excel")
public class ExcelResource extends SpringSupportedResource {

	private final String UPLOADED_FILE_PARAMETER_NAME = "uploadedFile";
	private final String UPLOADED_FILE_TEMPLATE_NAME = "uploadTemplate";
	private final String EXCELVO_SESSION_OBJ = "excelVOsessionObj";

	@Autowired
	ExcelService excelService;

	@POST
	@Path("/inserisciExcel")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response inserisciExcel(MultipartFormDataInput input) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.UPLOAD_FILE_XLS,AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);
		InputStream is = getInputStream(input);
		if (is == null) {
			return Response.serverError().build();
		}
		InputStream ist = getTemplateInputStream(input);
		if (ist == null) {
			return Response.serverError().build();
		}
		ExcelVO response = excelService.readExcelAndWriteOnDB(is, ist);
		if (response == null) {
			return Response.serverError().build();
		}

		// in questa prima versione salvo in sessione l'oggetto elaborato
		// (eliminando il blob) in modo da poter poi ottenere
		// con una seconsa chiamata i dettagli
		Response resp = null;
		if (response.isErroreCampiExcel()) {
			resp = Response.ok(response.getExcel(), MediaType.APPLICATION_OCTET_STREAM_TYPE)
					.header("Content-type", "application/vnd.ms-excel").build();
		}

		response.setExcel(null);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		request.getSession().setAttribute(EXCELVO_SESSION_OBJ, response);

		if (response.isErroreCampiExcel()) {
			return resp;
		}
		return Response.ok(null, MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
	}

	@POST
	@Path("/dettaglioErroriExcel")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response dettagliErroriExcel(@Context HttpServletRequest request) {
		ExcelVO errori = (ExcelVO) request.getSession().getAttribute(EXCELVO_SESSION_OBJ);
		request.getSession().removeAttribute(EXCELVO_SESSION_OBJ);
		String msgErrore = " ";
		if (errori != null) {
			if (errori.isErroreCampiExcel()) {
				msgErrore = "Il file caricato contiene errori. Si prega di verificarli guardando il file restituito e ripetere l'operazione. \n";
			}

			if (errori.isErroreCongruenzaCampiExcel()) {
				msgErrore = "I seguenti campi sono incongruenti rispetto al modello: \n";
				for (String item : errori.getMsgErroreCongruenzaCampiExcel()) {
					msgErrore += item + " \n";
				}

			}

			if (errori.isErroreException() && errori.getMsgErroreException() != null) {
				msgErrore += errori.getMsgErroreException() + " \n";
			}

			if (errori.isErroreException() && errori.getMsgErroreException() == null) {
				msgErrore += "Si e' verificato un errore durante la lettura del file. Si prega di riprovare. ";
			}
		}
		// se non ci sono errori allora tutto bene
		if (msgErrore.trim().length() <= 0) {
			msgErrore = "Caricamento File terminato correttamente";
		}

		return Response.ok(msgErrore).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/excelRicerca")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response excelRicerca(@Context HttpServletRequest request, FiltroAutobusVO filtro)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS);

		return Response.ok(excelService.exportRicercaAutobus(filtro), MediaType.APPLICATION_OCTET_STREAM_TYPE)
				/*
				 * .header("content-disposition", "attachment; filename=\"temp.csv\"").build();
				 */
				.header("Content-type", "application/vnd.ms-excel").header("Keep-Alive", "timeout=840").build();
	}

	@POST
	@Path("/excelRicercaContribuzione")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response excelRicercaContribuzione(@Context HttpServletRequest request, FiltroContribuzioneVO filtro)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.ESPORTA_RICERCA_CONTRIBUZIONE);
		return Response.ok(excelService.excelRicercaContribuzione(filtro), MediaType.APPLICATION_OCTET_STREAM_TYPE)
				/*
				 * .header("content-disposition", "attachment; filename=\"temp.csv\"").build();
				 */
				.header("Content-type", "application/vnd.ms-excel").header("Keep-Alive", "timeout=840").build();
	}

	private InputStream getInputStream(MultipartFormDataInput input) {
		try {
			Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
			List<InputPart> inputParts = uploadForm.get(UPLOADED_FILE_PARAMETER_NAME);
			for (InputPart inputPart : inputParts) {
				return inputPart.getBody(InputStream.class, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private InputStream getTemplateInputStream(MultipartFormDataInput input) {
		try {
			Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
			List<InputPart> inputParts = uploadForm.get(UPLOADED_FILE_TEMPLATE_NAME);
			for (InputPart inputPart : inputParts) {
				return inputPart.getBody(InputStream.class, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
