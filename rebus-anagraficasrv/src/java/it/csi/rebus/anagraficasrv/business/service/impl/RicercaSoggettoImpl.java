/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.anagraficasrv.business.service.RicercaSoggettoService;
import it.csi.rebus.anagraficasrv.integration.dao.custom.RicercaDAO;
import it.csi.rebus.anagraficasrv.integration.dto.custom.SoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.mapper.SoggettoGiuridicoMapper;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.security.CodeRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoGiuridicoVO;

@Service
public class RicercaSoggettoImpl implements RicercaSoggettoService {

	@Autowired
	private RicercaDAO ricercaDAO;
	@Autowired
	private SoggettoGiuridicoMapper soggettoGiuridicoMapper;
	
	

	@Override
	public List<SoggettoGiuridicoVO> elencoSoggetto() {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_SOGGETTI);
		FiltroSoggettoVO filtro = new FiltroSoggettoVO();
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		filtro.setFlagIncludiCessate("N");
		filtro.setFlagIncludiAttive("S");
		filtro.setFlagIncludiNonAttive("N");

		// se ho ruolo azienda o ruolo ente
		if (userInfo.getRuolo() != null) {
			if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId()) || userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
				filtro.setIdSoggettoGiuridico(userInfo.getIdAzienda());
			} else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_ENTE.getId())) {
				filtro.setIdSoggettoGiuridico(userInfo.getIdEnte());
			}
		}

		List<SoggettoGiuridicoDTO> soggettiDTO = ricercaDAO.getElencoSoggetto(filtro);
		List<SoggettoGiuridicoVO> soggettiVO = new ArrayList<>();
		for (SoggettoGiuridicoDTO dto : soggettiDTO) {
			SoggettoGiuridicoVO vo = soggettoGiuridicoMapper.mapDTOtoVO(dto);
			vo.setAziendaAttiva(Boolean.TRUE);
			vo.setAziendaCessata(Boolean.FALSE);
			soggettiVO.add(vo);
		}

		return soggettiVO;
	}

	@Override
	public List<SoggettoGiuridicoVO> filtraElencoSoggetto(FiltroSoggettoVO filtro) {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_SOGGETTI);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		filtro.prepareSQL();
		// se ho ruolo azienda o ruolo ente
		if (userInfo.getRuolo() != null) {
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId()) || userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			filtro.setIdSoggettoGiuridico(userInfo.getIdAzienda());
		} else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_ENTE.getId())) {
			filtro.setIdSoggettoGiuridico(userInfo.getIdEnte());
		}
		}
		

		List<SoggettoGiuridicoDTO> soggettiDTO = ricercaDAO.getElencoSoggetto(filtro);
		List<SoggettoGiuridicoVO> soggettiVO = new ArrayList<>();
		for (SoggettoGiuridicoDTO dto : soggettiDTO) {
			SoggettoGiuridicoVO vo = soggettoGiuridicoMapper.mapDTOtoVO(dto);
			if(dto.getAttivoTpl()) {
				vo.setAziendaAttiva(Boolean.TRUE);
			}
			else vo.setAziendaAttiva(Boolean.FALSE);
			if(dto.getCessato()) {
				vo.setAziendaCessata(Boolean.TRUE);
			}
			else vo.setAziendaCessata(Boolean.FALSE);
			
			if(filtro.getCodOssNaz()!=null) {
				String filterTrim = filtro.getCodOssNaz().substring(1,filtro.getCodOssNaz().length()-1).toUpperCase();
				if(vo.getCodRegionale().toUpperCase().contains(filterTrim) || 
						(vo.getCodOsservatorioNaz() != null && vo.getCodOsservatorioNaz().toUpperCase().contains(filterTrim)) ) {
					soggettiVO.add(vo);
				}
			}else {
				soggettiVO.add(vo);
			}
			
		}

		return soggettiVO;
	}

}
