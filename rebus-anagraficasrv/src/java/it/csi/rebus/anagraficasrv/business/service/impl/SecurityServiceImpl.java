/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.business.service.SecurityService;
import it.csi.rebus.anagraficasrv.common.Messages;
import it.csi.rebus.anagraficasrv.common.exception.ErroreGestitoException;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplTUtenteDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.VSoggettiCoinvoltiDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplTUtenteDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiSelector;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.Constants;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;

@Component
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private VSoggettiCoinvoltiDAO vSoggettiCoinvoltiDAO;
	
	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;

	@Override
	public List<AuthorizationRoles> getAutorizzazioniPerRuolo(String codRuolo) {
		switch (codRuolo) {
		case Constants.RUOLO_SERVIZIO:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS, AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS, AuthorizationRoles.MODIFICA_BUS,
					AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AZIENDE,
					AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA);
		case Constants.RUOLO_AMP:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS, AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA, AuthorizationRoles.MODIFICA_BUS);
		case Constants.RUOLO_REGIONE:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS, AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA);
		case Constants.RUOLO_ENTE:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS);
		case Constants.RUOLO_AZIENDA:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS, AuthorizationRoles.UPLOAD_FILE_XLS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS, AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS, AuthorizationRoles.MODIFICA_BUS,
					AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS, AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA);
		case Constants.RUOLO_MINISTERO:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS);
		case Constants.RUOLO_MONITORAGGIO:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AZIENDE,
					AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA

			);
		case Constants.RUOLO_GESTORE_DATI:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA_REBUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS, AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS, AuthorizationRoles.MODIFICA_BUS,
					AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AZIENDE,
					AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA);
		default:
			return new ArrayList<>();
		}
	}
	
	@Override
	public void verificaAutorizzazioni(String contesto, Long id) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

		switch (contesto) {
		case Constants.SOGGETTO:
			
			SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectAttivoTpl(id);
			
			//404: Not found
			if (sirtplaTSoggettoGiuridicoDTO==null) {
				throw new ErroreGestitoException(Messages.DATO_NON_TROVATO, "404");
			}

			// Se sei ente hai le autorizzazioni per accedere
			if ((userInfo.getIdAzienda() == null && userInfo.getIdEnte() != null)
					|| (userInfo.getIdAzienda().equals(id))) {
				return;
			}

			throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_AUTORIZZATO);

		case Constants.CONTRATTO:
			List<VSoggettiCoinvoltiDTO> soggetti = new ArrayList<VSoggettiCoinvoltiDTO>();
			VSoggettiCoinvoltiSelector vSoggettiCoinvoltiSelector = new VSoggettiCoinvoltiSelector();
			vSoggettiCoinvoltiSelector.createCriteria().andIdcEqualTo(id);
			soggetti = vSoggettiCoinvoltiDAO.selectByExample(vSoggettiCoinvoltiSelector);
			
			//404: Not found
			if (soggetti.isEmpty()) {
				throw new ErroreGestitoException(Messages.DATO_NON_TROVATO, "404");
			}
			
			if (userInfo.getIdAzienda() == null && userInfo.getIdEnte() != null) {
				return;
			}

			//controllo anche lo storico delle aziende inerenti al contratto
			for (VSoggettiCoinvoltiDTO sogg : soggetti) {
				if (sogg.getIdSg().equals(userInfo.getIdAzienda())) {
					return;
				}

			}

			throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_AUTORIZZATO);

		}

	}
}
