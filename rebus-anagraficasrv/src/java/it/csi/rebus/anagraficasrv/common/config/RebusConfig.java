/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RebusConfig {

	@Value( "${iride_service_endpoint_url}" )
	private String irideServiceEndpointUrl;

	public String getIrideServiceEndpointUrl() {
		return irideServiceEndpointUrl;
	}

	public void setIrideServiceEndpointUrl(String irideServiceEndpointUrl) {
		this.irideServiceEndpointUrl = irideServiceEndpointUrl;
	}
}
