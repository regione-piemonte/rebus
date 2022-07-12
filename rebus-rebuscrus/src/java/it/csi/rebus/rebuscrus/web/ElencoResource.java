/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.rebus.rebuscrus.business.service.RicercaAutobusService;
import it.csi.rebus.rebuscrus.util.SpringSupportedResource;

@Path("elenco")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ElencoResource extends SpringSupportedResource {

	@Autowired
	RicercaAutobusService ricercaAutobusService;

}
