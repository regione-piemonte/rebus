/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.business.facade.IrideServFacade;
import it.csi.rebus.anagraficasrv.business.service.UserService;
import it.csi.rebus.anagraficasrv.common.Messages;
import it.csi.rebus.anagraficasrv.common.exception.UtenteNonAbilitatoException;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplRUtenteSogGiuridDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplTUtenteDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDTipoSogGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplRUtenteSogGiuridDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplRUtenteSogGiuridSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplTUtenteDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplTUtenteSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.iride.Ruolo;
import it.csi.rebus.anagraficasrv.integration.iride.UseCase;
import it.csi.rebus.anagraficasrv.integration.mapper.AziendaMapper;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.Constants;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.AziendaVO;
import it.csi.rebus.anagraficasrv.vo.ProrogaVO;

@Component
public class UserServiceImpl implements UserService {

	protected static final Logger LOG = Logger.getLogger(it.csi.rebus.anagraficasrv.util.Constants.COMPONENT_NAME + ".security");
	
	@Autowired
	private SirtplTUtenteDAO sirtplTUtenteDAO;

	@Autowired
	private SirtplRUtenteSogGiuridDAO sirtplRUtenteSogGiuridDAO;

	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;

	@Autowired
	private SirtplaDTipoSogGiuridicoDAO sirtplaDTipoSogGiuridicoDAO;
	
	@Autowired
	private IrideServFacade irideServFacade;

	@Autowired
	private AziendaMapper aziendaMapper;

	@Override
	public UserInfo getDettaglioFunzionario(UserInfo user) {
		SirtplTUtenteSelector selector = new SirtplTUtenteSelector();
		selector.createCriteria().andCodiceFiscaleEqualTo(user.getCodFisc());
		selector.createCriteria().andDataInizioValiditaLessThanOrEqualTo(new Date());
		selector.createCriteria().andDataFineValiditaIsNull();
		List<SirtplTUtenteDTO> utenti = getSirtplTUtenteDAO().selectByExample(selector);
		if (utenti != null) {
			// considero il primo abbinamento con ente trovato!
			SirtplTUtenteDTO utenteDTO = utenti.get(0);

			SirtplRUtenteSogGiuridSelector selA = new SirtplRUtenteSogGiuridSelector();
			selA.createCriteria().andIdUtenteEqualTo(utenteDTO.getIdUtente());
			List<SirtplRUtenteSogGiuridDTO> azEnti = getSirtplRUtenteSogGiuridDAO().selectByExample(selA);

			if (azEnti == null || azEnti.size() == 0) {
				throw new UtenteNonAbilitatoException(Messages.UTENTE_NON_ABILITATO);
			}

			if (azEnti.size() == 1) {
				SirtplRUtenteSogGiuridDTO utAzEnteDto = azEnti.get(0);

				if (utAzEnteDto.getIdSoggettoGiuridico() != null) {
					SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
					sirtplaTSoggettoGiuridicoSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(utAzEnteDto.getIdSoggettoGiuridico());
					List<SirtplaTSoggettoGiuridicoDTO> soggettiDTO = getSirtplaTSoggettoGiuridicoDAO().selectByExample(sirtplaTSoggettoGiuridicoSelector);
					if (soggettiDTO != null && soggettiDTO.size() > 0 && soggettiDTO.get(0) != null) {
						SirtplaDTipoSogGiuridicoDTO tipoSogGiuridico = sirtplaDTipoSogGiuridicoDAO.selectByPrimaryKey(soggettiDTO.get(0).getIdTipoSogGiuridico());
						if (tipoSogGiuridico.getCodTipoSogGiuridico().equalsIgnoreCase(Constants.ID_TIPO_SOG_GIURIDICO_AZIENDA)) {
							user.setIdAzienda(soggettiDTO.get(0).getIdSoggettoGiuridico());
							user.setAziendaDesc(soggettiDTO.get(0).getDenominazioneBreve());
						} else if (tipoSogGiuridico.getCodTipoSogGiuridico().equalsIgnoreCase(Constants.ID_TIPO_SOG_GIURIDICO_ENTE)) {
							user.setIdEnte(soggettiDTO.get(0).getIdSoggettoGiuridico());
							user.setEnte(soggettiDTO.get(0).getDenominazioneBreve());
						}
					}
				}
			} else if (azEnti.size() > 1) {
				List<AziendaVO> aziende = new ArrayList<>();
				for (SirtplRUtenteSogGiuridDTO usg : azEnti) {
					if (usg.getIdSoggettoGiuridico() != null) {
						SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(usg.getIdSoggettoGiuridico());
						if (sirtplaTSoggettoGiuridicoDTO != null && sirtplaTSoggettoGiuridicoDTO.getIdTipoSogGiuridico().equals(Constants.ID_TIPO_SOG_GIURIDICO_AZIENDA_LONG)) {
							aziende.add(aziendaMapper.mapDTOtoVO(sirtplaTSoggettoGiuridicoDTO));
						}
					}
				} 
				if (aziende.size() > 1) {
					aziende.sort(new AziendeComparator());
					user.setAziende(aziende);
				}else if(aziende.size() == 1) {
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

	public SirtplTUtenteDAO getSirtplTUtenteDAO() {
		return sirtplTUtenteDAO;
	}

	public void setSirtplTUtenteDAO(SirtplTUtenteDAO sirtplTUtenteDAO) {
		this.sirtplTUtenteDAO = sirtplTUtenteDAO;
	}

	public SirtplRUtenteSogGiuridDAO getSirtplRUtenteSogGiuridDAO() {
		return sirtplRUtenteSogGiuridDAO;
	}

	public void setSirtplRUtenteSogGiuridDAO(SirtplRUtenteSogGiuridDAO sirtplRUtenteSogGiuridDAO) {
		this.sirtplRUtenteSogGiuridDAO = sirtplRUtenteSogGiuridDAO;
	}

	public SirtplaTSoggettoGiuridicoDAO getSirtplaTSoggettoGiuridicoDAO() {
		return sirtplaTSoggettoGiuridicoDAO;
	}

	public void setSirtplaTSoggettoGiuridicoDAO(SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO) {
		this.sirtplaTSoggettoGiuridicoDAO = sirtplaTSoggettoGiuridicoDAO;
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
	public String getCodiceRuoloUtente() {
		return SecurityUtils.getCurrentUserInfo().getRuolo().getCodiceRuolo();
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
					for(Ruolo ruoloTmp:ruoliTmp) {
						if(ruoloTmp.getCodiceRuolo().equals(ruolo.getCodiceRuolo())) {
							autorityTmp.add(u.getId());
						}
					}
						
			}
			userInfo.setAuthority(autorityTmp);
		}
		return userInfo;
	}

}

class AziendeComparator implements Comparator<AziendaVO>{

	@Override
	public int compare(AziendaVO o1, AziendaVO o2) {
		return o1.getDescrizione().compareTo(o2.getDescrizione());
	}
	
}
