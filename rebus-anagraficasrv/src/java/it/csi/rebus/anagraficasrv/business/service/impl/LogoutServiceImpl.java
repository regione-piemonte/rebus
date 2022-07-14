/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.anagraficasrv.business.service.LogoutService;
import it.csi.rebus.anagraficasrv.common.Messages;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplRUtenteSogGiuridDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplTUtenteDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplRUtenteSogGiuridDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplRUtenteSogGiuridSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplTUtenteDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.mapper.AziendaMapper;
import it.csi.rebus.anagraficasrv.util.Constants;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.AziendaVO;

@Service
public class LogoutServiceImpl implements LogoutService {

	@Autowired
	private SirtplTUtenteDAO sirtplTUtenteDAO;

	@Autowired
	private SirtplRUtenteSogGiuridDAO sirtplRUtenteSogGiuridDAO;

	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;

	@Autowired
	private AziendaMapper aziendaMapper;

	@Override
	public List<AziendaVO> getAziendeFunzionario() {
		SirtplTUtenteDTO utenteDTO = sirtplTUtenteDAO.selectByPrimaryKey((SecurityUtils.getCurrentUserInfo().getIdUtente()));

		SirtplRUtenteSogGiuridSelector selA = new SirtplRUtenteSogGiuridSelector();
		selA.createCriteria().andIdUtenteEqualTo(utenteDTO.getIdUtente());
		List<SirtplRUtenteSogGiuridDTO> azEnti = sirtplRUtenteSogGiuridDAO.selectByExample(selA);

		if (azEnti == null || azEnti.size() == 0) {
			throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_ABILITATO);
		}

		if (azEnti.size() > 1) {
			List<AziendaVO> aziende = new ArrayList<>();
			for (SirtplRUtenteSogGiuridDTO usg : azEnti) {
				if (usg.getIdSoggettoGiuridico() != null) {
					SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(usg.getIdSoggettoGiuridico());
					if (sirtplaTSoggettoGiuridicoDTO != null && sirtplaTSoggettoGiuridicoDTO.getIdTipoSogGiuridico().equals(Constants.ID_TIPO_SOG_GIURIDICO_AZIENDA_LONG)) {
						aziende.add(aziendaMapper.mapDTOtoVO(sirtplaTSoggettoGiuridicoDTO));
					}
				}
			}
			aziende.sort(new AziendeComparator());
			return aziende;
		}

		return new ArrayList<>();
	}

}
