/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.anagraficasrv.business.service.ExcelContrattoService;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.FiltroContrattoVO;

@Path("excel")
public class ExcelContrattoResource extends it.csi.rebus.anagraficasrv.util.SpringSupportedResource {

	@Autowired
	ExcelContrattoService excelService;

	@POST
	@Path("/excelRicercaContratti")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response excelRicerca(@Context HttpServletRequest request, FiltroContrattoVO filtro)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_CONTRATTI);

		return Response.ok(excelService.exportRicercaContratti(filtro), MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.header("Content-type", "application/vnd.ms-excel").build();
	}

}
