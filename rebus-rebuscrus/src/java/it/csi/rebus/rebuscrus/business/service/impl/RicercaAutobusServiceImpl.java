/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.service.RicercaAutobusService;
import it.csi.rebus.rebuscrus.integration.dao.RebusTAziendeDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ExcelDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTAziendeSelector;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaAutobusDTO;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.CodeRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.AziendaVO;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;

/**
 * @author massimo.durando
 * @date 15 dic 2017
 */

@Component
public class RicercaAutobusServiceImpl implements RicercaAutobusService {

	@Autowired
	private RebusTAziendeDAO rebusTaziendaDAO;

	@Autowired
	private ExcelDAO excel;

	@Override
	public List<VExportRicercaAutobusDTO> elencoAutobus() throws Exception {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		FiltroAutobusVO filtro = new FiltroAutobusVO();
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			if (userInfo.getIdAzienda() != null) {
				filtro.setIdAzienda(userInfo.getIdAzienda());
			}
		}

		List<VExportRicercaAutobusDTO> bus = excel.getExcelReport(filtro);
		for (VExportRicercaAutobusDTO b : bus) {
			if (b.getFlgVerificatoAmp().equals("U")) {
				b.setFlgVerificatoAmp("-");
			}

		}
		return bus;
	}

	@Override
	public List<AziendaVO> elencoAziende() throws Exception {
		List<AziendaVO> data = new ArrayList<AziendaVO>();
		AziendaVO azienda;

		RebusTAziendeSelector example = new RebusTAziendeSelector();
		example.setOrderByClause("denominazione");

		for (RebusTAziendeDTO elem : rebusTaziendaDAO.selectByExample(example)) {
			azienda = new AziendaVO();
			azienda.setId(elem.getIdAzienda());
			azienda.setDescrizione(elem.getDenominazione());
			azienda.setRagioneSociale(elem.getNaturaGiuridica());

			data.add(azienda);
		}
		return data;

	}

	@Override
	public List<VExportRicercaAutobusDTO> filtraElencoAutobus(FiltroAutobusVO filtro) throws Exception {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_ANAGRAFICA_BUS);
		filtro.prepareSQL();
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			if (userInfo.getIdAzienda() != null) {
				filtro.setIdAzienda(userInfo.getIdAzienda());
			}
		}

		List<VExportRicercaAutobusDTO> bus = excel.getExcelReport(filtro);

		for (VExportRicercaAutobusDTO b : bus) {
			if (b.getFlgVerificatoAmp().equals("U")) {
				b.setFlgVerificatoAmp("-");
			}

		}
		return bus;
	}

}
