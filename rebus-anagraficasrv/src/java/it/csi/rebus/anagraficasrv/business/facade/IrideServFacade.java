/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.facade;

import it.csi.rebus.anagraficasrv.integration.iride.Application;
import it.csi.rebus.anagraficasrv.integration.iride.Identita;
import it.csi.rebus.anagraficasrv.integration.iride.Ruolo;
import it.csi.rebus.anagraficasrv.integration.iride.UseCase;

public interface IrideServFacade {

	public Ruolo[] findRuoliForPersonaInApplication(Identita identita, Application application) throws java.lang.Exception;

	public Identita identificaUserPassword(String user, String pwd) throws java.lang.Exception;

	String getInfoPersonaInUseCase(Identita identita, String codRuoloCompleto) throws Exception;

	Identita getIdentitaFromToken(String token) throws Exception;

	UseCase[] findUseCasesForPersonaInApplication(Identita identita, String codApplicativo) throws Exception;
	
	public Ruolo[] findRuoliForPersonaInUseCase(Identita identita, UseCase useCase) throws java.lang.Exception;

}
