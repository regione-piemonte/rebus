/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import it.csi.rebus.anagraficasrv.util.SpringSupportedResource;
import it.csi.rebus.anagraficasrv.web.ContrattoResource;
import it.csi.rebus.anagraficasrv.web.ElencoContrattoResource;
import it.csi.rebus.anagraficasrv.web.ElencoSoggettoResource;
import it.csi.rebus.anagraficasrv.web.EnteResource;
import it.csi.rebus.anagraficasrv.web.ExcelContrattoResource;
import it.csi.rebus.anagraficasrv.web.ExcelSoggettoResource;
import it.csi.rebus.anagraficasrv.web.LogoutResource;
import it.csi.rebus.anagraficasrv.web.LuoghiResource;
import it.csi.rebus.anagraficasrv.web.RinnovoContrattoResource;
import it.csi.rebus.anagraficasrv.web.SoggettoResource;
import it.csi.rebus.anagraficasrv.web.UserResource;
import it.csi.rebus.anagraficasrv.web.provider.ContentTypeSetter;
import it.csi.rebus.anagraficasrv.web.provider.ExceptionHandler;
import it.csi.rebus.anagraficasrv.web.provider.SpringInjectorInterceptor;

@ApplicationPath("restfacade")
public class RebusRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public RebusRestApplication() {
		singletons.add(new UserResource());
		singletons.add(new ExceptionHandler());
		singletons.add(new ContentTypeSetter());
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new SoggettoResource());
		singletons.add(new LuoghiResource());
		singletons.add(new EnteResource());
		singletons.add(new ContrattoResource());
		singletons.add(new ElencoSoggettoResource());
		singletons.add(new ElencoContrattoResource());
		singletons.add(new ExcelContrattoResource());
		singletons.add(new ExcelSoggettoResource());
		singletons.add(new LogoutResource());
		singletons.add(new RinnovoContrattoResource());

		for (Object c : singletons) {
			if (c instanceof SpringSupportedResource) {
				SpringApplicationContextHelper.registerRestEasyController(c);
			}
		}
	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}