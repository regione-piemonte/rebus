/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.ZipService;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;

/**
 * @author 70525
 * @date 24 nov 2017
 */
@Path("zip")
public class ZipResource extends SpringSupportedResource {

	@Autowired
	ZipService zipService;

	@POST
	@Path("/zipRicercaContribuzione")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response zipRicercaContribuzione(@Context HttpServletRequest request, FiltroContribuzioneVO filtro)
			throws UtenteNonAbilitatoException {
//		VERIFICA ACCESSI CA_MOD
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.GENERA_ZIP);
		return Response.ok(zipService.zipRicercaContribuzione(filtro), MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.header("Content-type", "application/zip").header("Keep-Alive", "timeout=840").build();
	}

}
