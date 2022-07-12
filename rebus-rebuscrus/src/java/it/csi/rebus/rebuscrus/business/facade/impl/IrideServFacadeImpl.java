/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.facade.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.facade.IrideServFacade;
import it.csi.rebus.rebuscrus.common.config.RebusConfig;
import it.csi.rebus.rebuscrus.integration.iride.Application;
import it.csi.rebus.rebuscrus.integration.iride.Identita;
import it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException;
import it.csi.rebus.rebuscrus.integration.iride.PolicyEnforcerBaseService;
import it.csi.rebus.rebuscrus.integration.iride.PolicyEnforcerBaseServiceServiceLocator;
import it.csi.rebus.rebuscrus.integration.iride.Ruolo;
import it.csi.rebus.rebuscrus.integration.iride.UseCase;

@Component
public class IrideServFacadeImpl implements IrideServFacade, InitializingBean {

	@Autowired
	private RebusConfig rebusConfig;

	private PolicyEnforcerBaseService binding;

	public Ruolo[] findRuoliForPersonaInApplication(Identita identita, Application application) throws java.lang.Exception {
		Ruolo[] ruoloArray = null;

		try {
			PolicyEnforcerBaseServiceServiceLocator locator = new PolicyEnforcerBaseServiceServiceLocator();
			locator.setPolicyEnforcerBaseEndpointAddress(rebusConfig.getIrideServiceEndpointUrl());
			binding = (PolicyEnforcerBaseService) locator.getPolicyEnforcerBase();
			ruoloArray = binding.findRuoliForPersonaInApplication(identita, application);
		} catch (java.lang.Exception ex) {
			throw ex;
		}

		return ruoloArray;
	}

	public Identita identificaUserPassword(String user, String pwd) throws java.lang.Exception {
		PolicyEnforcerBaseServiceServiceLocator locator = new PolicyEnforcerBaseServiceServiceLocator();
		locator.setPolicyEnforcerBaseEndpointAddress(rebusConfig.getIrideServiceEndpointUrl());
		binding = (PolicyEnforcerBaseService) locator.getPolicyEnforcerBase();

		return binding.identificaUserPassword(user, pwd);
	}

	@Override
	public String getInfoPersonaInUseCase(Identita identita, String codRuoloCompleto) throws Exception {
		return binding.getInfoPersonaInUseCase(identita, new it.csi.rebus.rebuscrus.integration.iride.UseCase(null, codRuoloCompleto));
	}

	@Override
	public UseCase[] findUseCasesForPersonaInApplication(Identita identita, String codApplicativo) throws java.lang.Exception {
		UseCase[] useCaseArray = null;

		try {
			useCaseArray = new UseCase[] {};

			useCaseArray = binding.findUseCasesForPersonaInApplication(identita, new Application(codApplicativo));
		} catch (java.lang.Exception ex) {
			// TOIMPL Auto-generated catch block
			ex.printStackTrace();
			throw ex;
		}

		return useCaseArray;
	}
	
	public Ruolo[] findRuoliForPersonaInUseCase(Identita identita, UseCase useCase) throws java.lang.Exception {
		Ruolo[] ruoloArray = null;

		try {
			PolicyEnforcerBaseServiceServiceLocator locator = new PolicyEnforcerBaseServiceServiceLocator();
			locator.setPolicyEnforcerBaseEndpointAddress(rebusConfig.getIrideServiceEndpointUrl());
			binding = (PolicyEnforcerBaseService) locator.getPolicyEnforcerBase();
			ruoloArray = binding.findRuoliForPersonaInUseCase(identita, useCase);
		} catch (java.lang.Exception ex) {
			throw ex;
		}

		return ruoloArray;
	}

	@Override
	public Identita getIdentitaFromToken(String token)
			// throws MalformedIdTokenException
			throws Exception {
		Identita identita = new Identita();

		int slash1Index = token.indexOf('/');
		if (slash1Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setCodFiscale(token.substring(0, slash1Index));
		int slash2Index = token.indexOf('/', slash1Index + 1);
		if (slash2Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setNome(token.substring(slash1Index + 1, slash2Index));
		int slash3Index = token.indexOf('/', slash2Index + 1);
		if (slash3Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setCognome(token.substring(slash2Index + 1, slash3Index));
		int slash4Index = token.indexOf('/', slash3Index + 1);
		if (slash4Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setIdProvider(token.substring(slash3Index + 1, slash4Index));
		int slash5Index = token.indexOf('/', slash4Index + 1);
		if (slash5Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setTimestamp(token.substring(slash4Index + 1, slash5Index));
		int slash6Index = token.indexOf('/', slash5Index + 1);
		if (slash6Index == -1) {
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		} else {
			identita.setLivelloAutenticazione(Integer.parseInt(token.substring(slash5Index + 1, slash6Index)));
			identita.setMac(token.substring(slash6Index + 1));
		}
		return identita;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		PolicyEnforcerBaseServiceServiceLocator locator = new PolicyEnforcerBaseServiceServiceLocator();
		locator.setPolicyEnforcerBaseEndpointAddress(rebusConfig.getIrideServiceEndpointUrl());
		binding = (PolicyEnforcerBaseService) locator.getPolicyEnforcerBase();
	}

}
