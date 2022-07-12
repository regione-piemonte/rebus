/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.service.MessaggiService;
import it.csi.rebus.rebuscrus.business.service.SecurityService;
import it.csi.rebus.rebuscrus.common.Messages;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.integration.dao.custom.CommonDAO;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.AziendaAutobusVO;
import it.csi.rebus.rebuscrus.vo.MessaggioVo;

@Component
@Deprecated
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	MessaggiService messaggiService;

	protected static final Logger LOG = Logger
			.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + "SecurityService");

	@Override
	public List<AuthorizationRoles> getAutorizzazioniPerRuolo(String codRuolo) {
		switch (codRuolo) {
		case Constants.RUOLO_SERVIZIO:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS, AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS, AuthorizationRoles.MODIFICA_BUS,
					AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AZIENDE,
					AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ACCESSO_SEZIONE_CONTRIBUZIONE, AuthorizationRoles.RICERCA_CONTRIBUZIONE,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE, AuthorizationRoles.INSERIMENTO_CONTRIBUZIONE,
					AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE, AuthorizationRoles.ELIMINA_CONTRIBUZIONE,
					AuthorizationRoles.ESPORTA_RICERCA_CONTRIBUZIONE, AuthorizationRoles.GENERA_ZIP,
					AuthorizationRoles.MESSAGGI, AuthorizationRoles.MESSAGGI_RICHIESTE,
					AuthorizationRoles.MESSAGGI_RENDICONTRAZIONE);
		case Constants.RUOLO_AMP:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS, AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA, AuthorizationRoles.MODIFICA_BUS,
					AuthorizationRoles.ACCESSO_SEZIONE_CONTRIBUZIONE, AuthorizationRoles.RICERCA_CONTRIBUZIONE,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE,
					AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE,
					AuthorizationRoles.ESPORTA_RICERCA_CONTRIBUZIONE, AuthorizationRoles.GENERA_ZIP,
					AuthorizationRoles.MESSAGGI, AuthorizationRoles.MESSAGGI_RICHIESTE,
					AuthorizationRoles.MESSAGGI_RENDICONTRAZIONE);
		case Constants.RUOLO_REGIONE:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS, AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ACCESSO_SEZIONE_CONTRIBUZIONE, AuthorizationRoles.RICERCA_CONTRIBUZIONE,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE,
					AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE,
					AuthorizationRoles.ESPORTA_RICERCA_CONTRIBUZIONE, AuthorizationRoles.GENERA_ZIP,
					AuthorizationRoles.DETTAGLIO_PROCEDIMENTO, AuthorizationRoles.MESSAGGI_RENDICONTRAZIONE);
		case Constants.RUOLO_ENTE:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS);
		case Constants.RUOLO_AZIENDA:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA, AuthorizationRoles.UPLOAD_FILE_XLS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS, AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS, AuthorizationRoles.MODIFICA_BUS,
					AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS, AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ACCESSO_SEZIONE_CONTRIBUZIONE, AuthorizationRoles.RICERCA_CONTRIBUZIONE,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE, AuthorizationRoles.INSERIMENTO_CONTRIBUZIONE,
					AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE, AuthorizationRoles.ELIMINA_CONTRIBUZIONE,
					AuthorizationRoles.ESPORTA_RICERCA_CONTRIBUZIONE, AuthorizationRoles.GENERA_ZIP,
					AuthorizationRoles.MESSAGGI, AuthorizationRoles.MESSAGGI_RICHIESTE);
		case Constants.RUOLO_MINISTERO:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS);
		case Constants.RUOLO_MONITORAGGIO:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.RICERCA_ANAGRAFICA_BUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AZIENDE,
					AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ACCESSO_SEZIONE_CONTRIBUZIONE, AuthorizationRoles.RICERCA_CONTRIBUZIONE,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE

			);
		case Constants.RUOLO_GESTORE_DATI:
			return Arrays.asList(AuthorizationRoles.ACCESSO_AL_SISTEMA,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AUTOBUS, AuthorizationRoles.DOWNLOAD_FILE_XLS,
					AuthorizationRoles.RICERCA_ANAGRAFICA_BUS, AuthorizationRoles.INSERIMENTO_ANAGRAFICA_BUS,
					AuthorizationRoles.CONSULTAZIONE_DETTAGLIO_ANAGRAFICA_BUS, AuthorizationRoles.MODIFICA_BUS,
					AuthorizationRoles.CANCELLAZIONE_AUTOBUS_NON_VERIFICATO_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_BUS,
					AuthorizationRoles.ACCESSO_SEZIONE_ANAGRAFICA_AZIENDE,
					AuthorizationRoles.RICERCA_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ESPORTA_ELENCO_ANAGRAFICA_AZIENDA,
					AuthorizationRoles.ACCESSO_SEZIONE_CONTRIBUZIONE, AuthorizationRoles.RICERCA_CONTRIBUZIONE,
					AuthorizationRoles.VISUALIZZA_DETTAGLI_CONTRIBUZIONE, AuthorizationRoles.INSERIMENTO_CONTRIBUZIONE,
					AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE, AuthorizationRoles.ELIMINA_CONTRIBUZIONE,
					AuthorizationRoles.ESPORTA_RICERCA_CONTRIBUZIONE, AuthorizationRoles.GENERA_ZIP,
					AuthorizationRoles.MESSAGGI, AuthorizationRoles.MESSAGGI_RICHIESTE,
					AuthorizationRoles.MESSAGGI_RENDICONTRAZIONE);
		default:
			return new ArrayList<>();
		}
	}

	@Override
	public void verificaAutorizzazioni(String contesto, Long id, String action) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

		switch (contesto) {
		case Constants.AUTOBUS:
			// Chiamata per ottenere la lista delle aziende legate ad un autobus
			List<AziendaAutobusVO> listAutobus = commonDAO.getAziendaAutobusByIdVa(id);

			// Si presuppone che un'azienda che ha alienato un veicolo non diventi di nuovo
			// proprietario

			// 404: not found
			if (listAutobus.isEmpty()) {
				throw new ErroreGestitoException(Messages.DATO_NON_TROVATO, "404");
			}

			// Se sei ente hai le autorizzazioni per accedere
			if (userInfo.getIdAzienda() == null && userInfo.getIdEnte() != null) {
				return;
			}

			for (AziendaAutobusVO aziendaAutobusVO : listAutobus) {
				if (aziendaAutobusVO.getFkAzienda().equals(userInfo.getIdAzienda())) {
//					Sezioni da aggiungere quando verra' allineata la parte della matita lato FE
//					Nella ricerca autobus. Quindi verra' inserita la redirect se la data alienzaine 
//					e' diversa da null (Rimuovere solo i commenti)
					return;
				}
			}

			// return codice 401 non autorizzato
			throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_AUTORIZZATO);

		case Constants.PROCEDIMENTI:

			List<Long> listProcedimenti = commonDAO.getSoggettiById(id);

			if (listProcedimenti.isEmpty()) {
				throw new ErroreGestitoException(Messages.DATO_NON_TROVATO, "404");
			}

			// Se seiF ente hai le autorizzazioni per accedere
			if (userInfo.getIdAzienda() == null && userInfo.getIdEnte() != null) {
				return;
			}

			for (Long soggetto : listProcedimenti) {
				if (soggetto.equals(userInfo.getIdAzienda())) {
					return;
				}
			}
			throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_AUTORIZZATO);

		case Constants.MESSAGGI:

			MessaggioVo dettaglioMessaggio = messaggiService.dettaglioMessaggio(id);

			if (dettaglioMessaggio == null) {
				throw new ErroreGestitoException(Messages.DATO_NON_TROVATO, "404");
			}

			// Se sei ente hai le autorizzazioni per accedere
			if (userInfo.getIdAzienda() == null && userInfo.getIdEnte() != null) {
				return;
			}

			if (userInfo.getIdUtente().equals(dettaglioMessaggio.getFkUtenteDestinatario())) {
				return;
			} else {
				throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_AUTORIZZATO);
			}

		default:
			// eccezzione not foud
			break;
		}
	}

}
