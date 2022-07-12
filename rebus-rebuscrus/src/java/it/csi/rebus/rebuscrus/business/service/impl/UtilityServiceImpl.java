/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.helper.ExcelHelper;
import it.csi.rebus.rebuscrus.business.service.UtilityService;
import it.csi.rebus.rebuscrus.excel.Validator.Combo;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipologiaDimensDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplDContestoDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.RicercaDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipologiaDimensSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplDContestoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplDContestoSelector;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.ComboVO;
import it.csi.rebus.rebuscrus.vo.ContestoVO;
import it.csi.rebus.rebuscrus.vo.DescrizioneVO;

/**
 * @author massimo.durando
 * @date 15 dic 2017
 */

@Component
public class UtilityServiceImpl implements UtilityService {

	@Autowired
	private ExcelHelper excelHelper;

	@Autowired
	private RebusDTipologiaDimensDAO rebusDTipologiaDimensDAO;
	
	@Autowired 
	private SirtplDContestoDAO sirtplDContestoDAO;
	
	@Autowired 
	private RicercaDAO ricercaDAO;	
	

	@Override
	public List<ComboVO> getDecodificheCombo() {
		excelHelper.loadTable();
		return getComboList(excelHelper.getMapDecodifiche());
	}

	public List<ComboVO> getComboList(HashMap<Combo, HashMap<String, Long>> map) {
		List<ComboVO> elenco = new ArrayList<>();

		if (map != null) {
			for (Map.Entry<Combo, HashMap<String, Long>> entry : map.entrySet()) {
				ComboVO comboVo = new ComboVO();
				comboVo.setIdentificativo(entry.getKey().toString());
				List<DescrizioneVO> descrizioni = new ArrayList<>();
				comboVo.setValori(descrizioni);
				for (Map.Entry<String, Long> entry2 : entry.getValue().entrySet()) {
					DescrizioneVO descr = new DescrizioneVO();
					descr.setCodice(entry2.getValue());
					descr.setDescrizione(entry2.getKey());
					descrizioni.add(descr);
				}

				elenco.add(comboVo);
			}
		}

		return elenco;
	}

	@Override
	public RebusDTipologiaDimensDTO getTipologiaDimensionale(Long idTipoAllestimento, Double lunghezza) {
		RebusDTipologiaDimensSelector example = new RebusDTipologiaDimensSelector();
		example.createCriteria().andIdTipoAllestimentoEqualTo(idTipoAllestimento).andLunghezzaMinLessThanOrEqualTo(lunghezza).andLunghezzaMaxGreaterThanOrEqualTo(lunghezza);

		RebusDTipologiaDimensDTO tipo = null;

		try {
			tipo = rebusDTipologiaDimensDAO.selectByExample(example).get(0);
		} catch (Exception e) {
			// TODO: Log error
		}

		return tipo;
	}

	@Override
	public List<ContestoVO> getElencoContesto() {
		List<ContestoVO> contestiVO = new ArrayList<ContestoVO>();
		SirtplDContestoSelector sel = new SirtplDContestoSelector();
		sel.createCriteria();
		List<SirtplDContestoDTO> contestiDTO = sirtplDContestoDAO.selectByExample(sel);
		for(SirtplDContestoDTO contestoDTO:contestiDTO) {
			ContestoVO contestoVO = new ContestoVO();
			contestoVO.setId(contestoDTO.getIdContesto());
			contestoVO.setCod(contestoDTO.getCodContesto());
			contestoVO.setDescrizione(contestoDTO.getDescContesto());
			contestiVO.add(contestoVO);
		}
			return contestiVO;
	}

	@Override
	public ContestoVO getContestoHome() {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		ContestoVO contestoVO = new ContestoVO();

		if (SecurityUtils.isUtenteServizioOGestoreDati()) {
			return ricercaDAO.getContestoHome(null, null,null);
		}
		
		if (userInfo.getIdAzienda() != null) {
			contestoVO = ricercaDAO.getContestoHome(null,userInfo.getIdAzienda(),userInfo.getIdUtente());
		}
		else if (userInfo.getIdEnte() != null) {
			contestoVO = ricercaDAO.getContestoHome(userInfo.getIdEnte(),null,userInfo.getIdUtente());
			
		}
		return contestoVO;
	}

}
