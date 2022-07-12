/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.facade.IrideServFacade;
import it.csi.rebus.rebuscrus.business.service.UserService;
import it.csi.rebus.rebuscrus.common.Messages;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.integration.dao.RebusRUtenteAzEnteDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTAziendeDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTEntiDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTUtentiDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTEntiDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTEntiSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTUtentiDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTUtentiSelector;
import it.csi.rebus.rebuscrus.integration.iride.Ruolo;
import it.csi.rebus.rebuscrus.integration.iride.UseCase;
import it.csi.rebus.rebuscrus.integration.mapper.AziendaMapper;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.AziendaVO;

/**
 * @author massimo.durando
 * @date 15 dic 2017
 */

@Component
public class UserServiceImpl implements UserService {

	protected static final Logger LOG = Logger
			.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + ".security");

	@Autowired
	private RebusTUtentiDAO rebusTUtentiDAO;

	@Autowired
	private RebusRUtenteAzEnteDAO rebusRUtenteAzEnteDAO;

	@Autowired
	private RebusTEntiDAO rebusTEntiDAO;

	@Autowired
	private RebusTAziendeDAO rebusTAziendeDAO;

	@Autowired
	private IrideServFacade irideServFacade;

	@Autowired
	private AziendaMapper aziendaMapper;

	@Override
	public UserInfo getDettaglioFunzionario(UserInfo user) {
		RebusTUtentiSelector selector = new RebusTUtentiSelector();
		selector.createCriteria().andCfEqualTo(user.getCodFisc()).andDataCreazioneLessThanOrEqualTo(new Date())
				.andDataCancellazioneIsNull();
		List<RebusTUtentiDTO> utenti = getRebusTUtentiDAO().selectByExample(selector);
		if (utenti != null) {
			// considero il primo abbinamento con ente trovato!
			// try {
			RebusTUtentiDTO utenteDTO = utenti.get(0);

			RebusRUtenteAzEnteSelector selA = new RebusRUtenteAzEnteSelector();
			selA.createCriteria().andFkUtenteEqualTo(utenteDTO.getIdUtente()).andDataFineValiditaIsNull();
			List<RebusRUtenteAzEnteDTO> azEnti = getRebusRUtenteAzEnteDAO().selectByExample(selA);

			if (azEnti == null || azEnti.size() == 0) {
				throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_ABILITATO);
			}

			if (azEnti.size() == 1) {
				RebusRUtenteAzEnteDTO utAzEnteDto = azEnti.get(0);

				// dettagli azienda
				if (utAzEnteDto.getFkAzienda() != null) {
					RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
					rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(utAzEnteDto.getFkAzienda());
					List<RebusTAziendeDTO> aziendeDTO = getRebusTAziendeDAO().selectByExample(rebusTAziendeSelector);
					if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
						user.setIdAzienda(aziendeDTO.get(0).getIdAzienda());
						user.setAziendaDesc(aziendeDTO.get(0).getDenominazione());
					}
				}
				if (utAzEnteDto.getFkEnte() != null) {
					RebusTEntiSelector rebusTEntiSelector = new RebusTEntiSelector();
					rebusTEntiSelector.createCriteria().andIdEnteEqualTo(utAzEnteDto.getFkEnte());
					List<RebusTEntiDTO> enteDTO = getRebusTEntiDAO().selectByExample(rebusTEntiSelector);
					if (enteDTO != null && enteDTO.get(0) != null) {
						user.setIdEnte(enteDTO.get(0).getIdEnte());
						user.setEnte(enteDTO.get(0).getDenominazione());

					}
				}
			} else if (azEnti.size() > 1) {
				List<AziendaVO> aziende = new ArrayList<>();
				for (RebusRUtenteAzEnteDTO uae : azEnti) {
					if (uae.getFkAzienda() != null) {
						RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
						rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(uae.getFkAzienda());
						List<RebusTAziendeDTO> aziendeDTO = getRebusTAziendeDAO()
								.selectByExample(rebusTAziendeSelector);
						if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
							aziende.add(aziendaMapper.mapDTOtoVO(aziendeDTO.get(0)));
						}
					}
				}
				if (aziende.size() > 1) {
					aziende.sort(new AziendeComparator());
					user.setAziende(aziende);
				} else if (aziende.size() == 1) {
					user.setAziendaDesc(aziende.get(0).getDescrizione());
					user.setIdAzienda(aziende.get(0).getId());
				}
			}
			user.setIdUtente(utenteDTO.getIdUtente());
			return user;

		} else {
			throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_ABILITATO);
		}

	}

	public RebusTUtentiDAO getRebusTUtentiDAO() {
		return rebusTUtentiDAO;
	}

	public void setRebusTUtentiDAO(RebusTUtentiDAO rebusTUtentiDAO) {
		this.rebusTUtentiDAO = rebusTUtentiDAO;
	}

	public RebusRUtenteAzEnteDAO getRebusRUtenteAzEnteDAO() {
		return rebusRUtenteAzEnteDAO;
	}

	public void setRebusRUtenteAzEnteDAO(RebusRUtenteAzEnteDAO rebusRUtenteAzEnteDAO) {
		this.rebusRUtenteAzEnteDAO = rebusRUtenteAzEnteDAO;
	}

	public RebusTEntiDAO getRebusTEntiDAO() {
		return rebusTEntiDAO;
	}

	public void setRebusTEntiDAO(RebusTEntiDAO rebusTEntiDAO) {
		this.rebusTEntiDAO = rebusTEntiDAO;
	}

	public RebusTAziendeDAO getRebusTAziendeDAO() {
		return rebusTAziendeDAO;
	}

	public void setRebusTAziendeDAO(RebusTAziendeDAO rebusTAziendeDAO) {
		this.rebusTAziendeDAO = rebusTAziendeDAO;
	}

	@Override
	public UserInfo setIdAziendaUtente(Long idAzienda) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo != null) {
			userInfo.setIdAzienda(idAzienda);
		}
		return userInfo;
	}

	@Override
	public UserInfo setRuoloUtente(Ruolo ruolo) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo != null) {
			userInfo.setRuolo(ruolo);
			List<String> autorityTmp = new ArrayList<>();
			for (UseCase u : userInfo.getUsecase()) {
				Ruolo[] ruoliTmp = null;
				try {
					ruoliTmp = irideServFacade.findRuoliForPersonaInUseCase(userInfo.getIdentita(), u);
				} catch (Exception e) {
					LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
				}
				for (Ruolo ruoloTmp : ruoliTmp) {
					if (ruoloTmp.getCodiceRuolo().equals(ruolo.getCodiceRuolo())) {
						autorityTmp.add(u.getId());
					}
				}

			}
			userInfo.setAuthority(autorityTmp);
		}

		return userInfo;
	}

}

class AziendeComparator implements Comparator<AziendaVO> {

	@Override
	public int compare(AziendaVO o1, AziendaVO o2) {
		return o1.getDescrizione().compareTo(o2.getDescrizione());
	}

}
