/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import it.csi.rebus.rebuscrus.util.SpringSupportedResource;
import it.csi.rebus.rebuscrus.web.AutobusResource;
import it.csi.rebus.rebuscrus.web.ContribuzioneResource;
import it.csi.rebus.rebuscrus.web.DocumentResource;
import it.csi.rebus.rebuscrus.web.ElencoResource;
import it.csi.rebus.rebuscrus.web.ExcelResource;
import it.csi.rebus.rebuscrus.web.LogoutResource;
import it.csi.rebus.rebuscrus.web.MessaggiResource;
import it.csi.rebus.rebuscrus.web.ProcedimentiResource;
import it.csi.rebus.rebuscrus.web.RiferimentiNormativiResource;
import it.csi.rebus.rebuscrus.web.UserResource;
import it.csi.rebus.rebuscrus.web.UtilityResource;
import it.csi.rebus.rebuscrus.web.ZipResource;
import it.csi.rebus.rebuscrus.web.provider.ContentTypeSetter;
import it.csi.rebus.rebuscrus.web.provider.ExceptionHandler;
import it.csi.rebus.rebuscrus.web.provider.SpringInjectorInterceptor;

@ApplicationPath("restfacade")
public class RebusRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public RebusRestApplication() {
		singletons.add(new UserResource());
		singletons.add(new ExcelResource());
		singletons.add(new AutobusResource());
		singletons.add(new ExceptionHandler());
		singletons.add(new UtilityResource());
		singletons.add(new ElencoResource());
		singletons.add(new DocumentResource());
		singletons.add(new ContentTypeSetter());
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new ProcedimentiResource());
		singletons.add(new MessaggiResource());
		singletons.add(new RiferimentiNormativiResource());
		singletons.add(new LogoutResource());
		singletons.add(new ContribuzioneResource());
		singletons.add(new ZipResource());

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