/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.DocumentoService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;

@Path("document")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class DocumentResource extends SpringSupportedResource {

	@Autowired
	DocumentoService documentoService;

	@GET
	@Path("/getContenutoDocumentoById")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getContenutoDocumentoById(@QueryParam("idVarAutobus") Long idVarAutobus,
			@QueryParam("idTipoDocumento") Long idTipoDocumento) throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.SCARICA_RIFERIMENTI);
		return Response.ok()
				.entity(documentoService.getContenutoDocumentoByIdVarAutobusAndTipo(idVarAutobus, idTipoDocumento))
				.build();
	}

	@GET
	@Path("/elencoDocumento")
	public Response elencoDocumento(@Context HttpServletRequest request, @QueryParam("idContesto") Long idContesto) {
		return Response.ok(documentoService.elencoDocumento(idContesto)).header("Access-Control-Allow-Origin", "*")
				.build();
	}

	@GET
	@Path("/getContenutoDocumentoByIdProcedimento")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getContenutoDocumentoByIdProcedimento(@QueryParam("idProcedimento") Long idProcedimento,
			@QueryParam("idTipoDocumento") Long idTipoDocumento) {
		return Response.ok()
				.entity(documentoService.getContenutoDocumentoByIdProcedimentoAndTipo(idProcedimento, idTipoDocumento))
				.build();
	}

	@DELETE
	@Path("/eliminaAllegatoProcedimentoDb")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response eliminaAllegatoProcedimentoDb(@QueryParam("idProcedimento") Long idProcedimento,
			@QueryParam("idTipoDocumento") Long idTipoDocumento) {
		documentoService.eliminaAllegatoProcedimentoDb(idProcedimento, idTipoDocumento);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getContenutoAnteprimaPdf")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getContenutoAnteprimaPdf(@QueryParam("idProcedimento") Long idProcedimento, @QueryParam("idStatoProc") Long idStatoProc, @QueryParam("idTipoProc") Long idTipoProc) 
			throws UtenteNonAbilitatoException{
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.SCARICA_PDF);
		
		try {
			return Response.ok().entity(documentoService.getContenutoAnteprimaPdf(idProcedimento, idStatoProc, idTipoProc)).build();
		} catch (Exception e) {
			return Response.ok().entity(new ErroreGestitoException(e.getMessage())).build();
		}		
	}

}
