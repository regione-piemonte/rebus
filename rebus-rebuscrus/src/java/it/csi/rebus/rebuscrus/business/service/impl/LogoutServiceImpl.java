/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.rebuscrus.business.service.LogoutService;
import it.csi.rebus.rebuscrus.common.Messages;
import it.csi.rebus.rebuscrus.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.rebuscrus.integration.dao.RebusRUtenteAzEnteDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTAziendeDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTUtentiDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTUtentiDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTUtentiSelector;
import it.csi.rebus.rebuscrus.integration.mapper.AziendaMapper;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.AziendaVO;

@Service
public class LogoutServiceImpl implements LogoutService {

	@Autowired
	private RebusTUtentiDAO rebusTUtentiDAO;

	@Autowired
	private RebusRUtenteAzEnteDAO rebusRUtenteAzEnteDAO;

	@Autowired
	private RebusTAziendeDAO rebusTAziendeDAO;

	@Autowired
	private AziendaMapper aziendaMapper;

	@Override
	public List<AziendaVO> getAziendeFunzionario() {
		RebusTUtentiSelector selector = new RebusTUtentiSelector();
		selector.createCriteria().andIdUtenteEqualTo(SecurityUtils.getCurrentUserInfo().getIdUtente());
		List<RebusTUtentiDTO> utenti = rebusTUtentiDAO.selectByExample(selector);
		if (utenti != null) {
			// considero il primo, l'id è chiave primaria
			RebusTUtentiDTO utenteDTO = utenti.get(0);

			RebusRUtenteAzEnteSelector selA = new RebusRUtenteAzEnteSelector();
			selA.createCriteria().andFkUtenteEqualTo(utenteDTO.getIdUtente()).andDataFineValiditaIsNull();
			List<RebusRUtenteAzEnteDTO> azEnti = rebusRUtenteAzEnteDAO.selectByExample(selA);

			if (azEnti == null || azEnti.size() == 0) {
				throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_ABILITATO);
			}

			if (azEnti.size() > 1) {
				List<AziendaVO> aziende = new ArrayList<>();
				for (RebusRUtenteAzEnteDTO uae : azEnti) {
					if (uae.getFkAzienda() != null) {
						RebusTAziendeSelector rebusTAziendeSelector = new RebusTAziendeSelector();
						rebusTAziendeSelector.createCriteria().andIdAziendaEqualTo(uae.getFkAzienda());
						List<RebusTAziendeDTO> aziendeDTO = rebusTAziendeDAO.selectByExample(rebusTAziendeSelector);
						if (aziendeDTO != null && aziendeDTO.size() > 0 && aziendeDTO.get(0) != null) {
							aziende.add(aziendaMapper.mapDTOtoVO(aziendeDTO.get(0)));
						}
					}
				}
				aziende.sort(new AziendeComparator());
				return aziende;
			}
		}
		return new ArrayList<>();
	}
}
