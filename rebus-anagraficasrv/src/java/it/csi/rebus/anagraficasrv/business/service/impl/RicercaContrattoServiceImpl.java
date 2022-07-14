/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.rebus.anagraficasrv.business.service.RicercaContrattoService;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcTProrogaContrattoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.custom.RicercaDAO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTProrogaContrattoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTProrogaContrattoSelector;
import it.csi.rebus.anagraficasrv.integration.mapper.ContrattoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.ProrogheMapper;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.security.CodeRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.ContrattoVO;
import it.csi.rebus.anagraficasrv.vo.FiltroContrattoVO;
import it.csi.rebus.anagraficasrv.vo.ProrogaVO;

@Service
public class RicercaContrattoServiceImpl implements RicercaContrattoService {

	@Autowired
	private RicercaDAO ricercaDAO;
	@Autowired
	private SirtplcTProrogaContrattoDAO sirtplcTProrogaContrattoDAO;
	@Autowired
	private ContrattoMapper contrattoMapper;
	@Autowired
	private ProrogheMapper prorogheMapper;

	@Override
	public List<ContrattoVO> elencoContratto() {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_CONTRATTI);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		FiltroContrattoVO filtro = new FiltroContrattoVO();
		filtro.setFlagIncludiCessate("N");
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		List<SirtplcTContrattoDTO> contrattiDTO = ricercaDAO.getElencoContratto(filtro);
		List<ContrattoVO> contrattiVO = new ArrayList<>();
		for (SirtplcTContrattoDTO dto : contrattiDTO) {
			ContrattoVO c = contrattoMapper.mapDTOtoVO(dto);
			SirtplcTProrogaContrattoSelector sirtplcTProrogaContrattoSelector = new SirtplcTProrogaContrattoSelector();
			sirtplcTProrogaContrattoSelector.createCriteria().andIdContrattoEqualTo(dto.getIdContratto());
			List<SirtplcTProrogaContrattoDTO> prorogheDTO = sirtplcTProrogaContrattoDAO
					.selectByExample(sirtplcTProrogaContrattoSelector);
			List<ProrogaVO> prorogheVO = new ArrayList<ProrogaVO>();
			for (SirtplcTProrogaContrattoDTO p : prorogheDTO) {
				prorogheVO.add(prorogheMapper.mapDTOtoVO(p));
			}
			prorogheVO.sort(new ProrogaComparator());
			c.setProroghe(prorogheVO);
			contrattiVO.add(c);
		}

		return contrattiVO;
	}

	@Override
	public List<ContrattoVO> filtraElencoContratto(FiltroContrattoVO filtro) {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_CONTRATTI);
		filtro.prepareSQL();
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		List<SirtplcTContrattoDTO> contrattiDTO = ricercaDAO.getElencoContratto(filtro);
		List<ContrattoVO> contrattiVO = new ArrayList<>();
		for (SirtplcTContrattoDTO dto : contrattiDTO) {
			ContrattoVO c = contrattoMapper.mapDTOtoVO(dto);
			if (filtro.getCodIdentificativo() != null) {
				String filterTrim = filtro.getCodIdentificativo()
						.substring(1, filtro.getCodIdentificativo().length() - 1).toUpperCase();
				if (c.getCodRegionale().toUpperCase().contains(filterTrim) || (c.getCodIdNazionale() != null
						&& c.getCodIdNazionale().toUpperCase().contains(filterTrim))) {
					SirtplcTProrogaContrattoSelector sirtplcTProrogaContrattoSelector = new SirtplcTProrogaContrattoSelector();
					sirtplcTProrogaContrattoSelector.createCriteria().andIdContrattoEqualTo(dto.getIdContratto());
					List<SirtplcTProrogaContrattoDTO> prorogheDTO = sirtplcTProrogaContrattoDAO
							.selectByExample(sirtplcTProrogaContrattoSelector);
					List<ProrogaVO> prorogheVO = new ArrayList<ProrogaVO>();
					for (SirtplcTProrogaContrattoDTO p : prorogheDTO) {
						prorogheVO.add(prorogheMapper.mapDTOtoVO(p));
					}
					prorogheVO.sort(new ProrogaComparator());
					c.setProroghe(prorogheVO);
					contrattiVO.add(c);
				}
			} else {
				SirtplcTProrogaContrattoSelector sirtplcTProrogaContrattoSelector = new SirtplcTProrogaContrattoSelector();
				sirtplcTProrogaContrattoSelector.createCriteria().andIdContrattoEqualTo(dto.getIdContratto());
				List<SirtplcTProrogaContrattoDTO> prorogheDTO = sirtplcTProrogaContrattoDAO
						.selectByExample(sirtplcTProrogaContrattoSelector);
				List<ProrogaVO> prorogheVO = new ArrayList<ProrogaVO>();
				for (SirtplcTProrogaContrattoDTO p : prorogheDTO) {
					prorogheVO.add(prorogheMapper.mapDTOtoVO(p));
				}
				prorogheVO.sort(new ProrogaComparator());
				c.setProroghe(prorogheVO);
				contrattiVO.add(c);
			}

		}

		return contrattiVO;
	}

}

class ProrogaComparator implements Comparator<ProrogaVO> {

	public int compare(ProrogaVO o1, ProrogaVO o2) {

		return o2.getDataFineProroga().compareTo(o1.getDataFineProroga());
	}

}
