/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.anagraficasrv.business.service.SoggettoService;
import it.csi.rebus.anagraficasrv.common.exception.ErroreGestitoException;
import it.csi.rebus.anagraficasrv.common.exception.ModificaNonAbilitataException;
import it.csi.rebus.anagraficasrv.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDNaturaGiuridicaDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDRuoloSogGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDTipoSogGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaRSogGiuridDepositoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTDatoBancarioDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTDepositoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.VContrattiSoggettiDAO;
import it.csi.rebus.anagraficasrv.integration.dao.custom.RicercaDAO;
import it.csi.rebus.anagraficasrv.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.anagraficasrv.integration.dto.RebusTVariazAutobusSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDNaturaGiuridicaDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDNaturaGiuridicaSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDRuoloSogGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDRuoloSogGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaRSogGiuridDepositoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaRSogGiuridDepositoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTDatoBancarioDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTDatoBancarioSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTDepositoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTDepositoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.VContrattiSoggettiDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VContrattiSoggettiSelector;
import it.csi.rebus.anagraficasrv.integration.mapper.DatoBancarioMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.DepositoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.NaturaGiuridicaMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.SoggettoEsecutoreMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.SoggettoGiuridicoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.TipoSogGiuridicoMapper;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.ContrattoSoggettoVO;
import it.csi.rebus.anagraficasrv.vo.DatiBancariVO;
import it.csi.rebus.anagraficasrv.vo.DepositoVO;
import it.csi.rebus.anagraficasrv.vo.InserisciSoggettoGiuridicoVO;
import it.csi.rebus.anagraficasrv.vo.NaturaGiuridicaVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoEsecutoreVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoGiuridicoVO;
import it.csi.rebus.anagraficasrv.vo.TipoSoggettoGiuridicoVO;

@Service
public class SoggettoServiceImpl implements SoggettoService {

	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;
	@Autowired
	private SirtplaTDepositoDAO sirtplaTDepositoDAO;
	@Autowired
	private SirtplaDTipoSogGiuridicoDAO sirtplaDTipoSogGiuridicoDAO;
	@Autowired
	private SirtplaDNaturaGiuridicaDAO sirtplaDNaturaGiuridicaDAO;
	@Autowired
	private SirtplaRSogGiuridDepositoDAO sirtplaRSogGiuridDepositoDAO;
	@Autowired
	private SirtplaTDatoBancarioDAO sirtplaTDatoBancarioDAO;
	@Autowired
	private SirtplaDRuoloSogGiuridicoDAO sirtplaDRuoloSogGiuridicoDAO;
	@Autowired
	private RicercaDAO ricercaDAO;
	@Autowired
	private TipoSogGiuridicoMapper tipoSogGiuridicoMapper;
	@Autowired
	private NaturaGiuridicaMapper naturaGiuridicaMapper;
	@Autowired
	private SoggettoGiuridicoMapper soggettoGiuridicoMapper;
	@Autowired
	private DepositoMapper depositoMapper;
	@Autowired
	private DatoBancarioMapper datoBancarioMapper;
	@Autowired
	private SoggettoEsecutoreMapper soggettoEsecutoreMapper;
	@Autowired
	private RebusTVariazAutobusDAO rebusTVariazAutobusDAO;
	@Autowired
	private VContrattiSoggettiDAO vContrattiSoggettiDAO;

	@Override
	public List<TipoSoggettoGiuridicoVO> getTipiSoggettoGiuridico(Boolean bol) {

		SirtplaDRuoloSogGiuridicoSelector sirtplaDRuoloSogGiuridicoSelector = new SirtplaDRuoloSogGiuridicoSelector();
		SirtplaDTipoSogGiuridicoSelector sirtplaDTipoSogGiuridicoSelector = new SirtplaDTipoSogGiuridicoSelector();

		if (bol == null) {
			sirtplaDTipoSogGiuridicoSelector.createCriteria().andValidoPerSogGiuridEqualTo(true);
		} else {
			String[] valoriSogGiurid = { "C", "E" };
			sirtplaDRuoloSogGiuridicoSelector.createCriteria()
					.andCodRuoloSogGiuridicoIn(Arrays.asList(valoriSogGiurid));
			List<SirtplaDRuoloSogGiuridicoDTO> ruoliSogGiurid = sirtplaDRuoloSogGiuridicoDAO
					.selectByExample(sirtplaDRuoloSogGiuridicoSelector);
			sirtplaDTipoSogGiuridicoSelector.createCriteria();
			for (SirtplaDRuoloSogGiuridicoDTO ruoloSogGiurid : ruoliSogGiurid) {
				sirtplaDTipoSogGiuridicoSelector.or()
						.andIdRuoloSogGiuridicoEqualTo(ruoloSogGiurid.getIdRuoloSogGiuridico())
						.andValidoPerSogGiuridEqualTo(true);
			}
		}
		sirtplaDTipoSogGiuridicoSelector.setOrderByClause("desc_tipo_sog_giuridico");
		List<SirtplaDTipoSogGiuridicoDTO> tipi = sirtplaDTipoSogGiuridicoDAO
				.selectByExample(sirtplaDTipoSogGiuridicoSelector);
		List<TipoSoggettoGiuridicoVO> tipiSoggettoGiuridico = new ArrayList<>();
		for (SirtplaDTipoSogGiuridicoDTO t : tipi) {
			tipiSoggettoGiuridico.add(tipoSogGiuridicoMapper.mapDTOtoVO(t));
		}
		return tipiSoggettoGiuridico;
	}

	@Override
	public List<NaturaGiuridicaVO> getNatureGiuridiche() {
		SirtplaDNaturaGiuridicaSelector sirtplaDNaturaGiuridicaSelector = new SirtplaDNaturaGiuridicaSelector();
		sirtplaDNaturaGiuridicaSelector.setOrderByClause("desc_natura_giuridica");
		List<SirtplaDNaturaGiuridicaDTO> nat = sirtplaDNaturaGiuridicaDAO
				.selectByExample(sirtplaDNaturaGiuridicaSelector);
		List<NaturaGiuridicaVO> nature = new ArrayList<>();
		for (SirtplaDNaturaGiuridicaDTO n : nat) {
			nature.add(naturaGiuridicaMapper.mapDTOtoVO(n));
		}
		return nature;
	}

	@Override
	@Transactional
	public Long inserisciSoggetto(InserisciSoggettoGiuridicoVO soggetto, byte[] file, String filename) {
		if (soggetto == null) {
			throw new InvalidParameterException("Soggetto non valorizzato");
		}

		if (!(filename.toLowerCase().contains(".jpg") || filename.toLowerCase().contains(".jpeg")
				|| filename.toLowerCase().contains(".png")) && file != null) {
			throw new ErroreGestitoException(
					"Formato file del documento allegato non consentito! Formati consentiti: jpg, jpeg, png.", "TFNC");
		}
		SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector;
		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		sirtplaTSoggettoGiuridicoSelector.createCriteria()
				.andCodOsservatorioNazEqualTo(soggetto.getCodOsservatorioNaz())
				.andIdTipoSogGiuridicoEqualTo(soggetto.getIdTipoSoggettoGiuridico());
		List<SirtplaTSoggettoGiuridicoDTO> soggetti;
		soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		if (soggetti != null && soggetti.size() > 0) {
			throw new ErroreGestitoException("Codice Nazionale duplicato", "TFND");
		}

		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		sirtplaTSoggettoGiuridicoSelector.createCriteria().andCodiceFiscaleEqualTo(soggetto.getCodiceFiscale())
				.andIdTipoSogGiuridicoEqualTo(soggetto.getIdTipoSoggettoGiuridico());
		soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		if (soggetti != null && soggetti.size() > 0) {
			throw new ErroreGestitoException("Codice Fiscale duplicato", "CFD");
		}

		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		sirtplaTSoggettoGiuridicoSelector.createCriteria().andPartitaIvaEqualTo(soggetto.getPartitaIva())
				.andIdTipoSogGiuridicoEqualTo(soggetto.getIdTipoSoggettoGiuridico());
		soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		if (soggetti != null && soggetti.size() > 0) {
			throw new ErroreGestitoException("Partita IVA duplicata", "PID");
		}

		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		// CSR-BIP non obbligatorio quindi anche null
		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		if (soggetto.getCodCsrBip() != null) {
			sirtplaTSoggettoGiuridicoSelector.createCriteria().andCodCsrBipEqualTo(soggetto.getCodCsrBip());
			soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
			if (soggetti != null && soggetti.size() > 0) {
				throw new ErroreGestitoException("Codice CSR-BIP duplicato", "CBD");
			}
		}

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		Date now = new Date();

		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = new SirtplaTSoggettoGiuridicoDTO();
		sirtplaTSoggettoGiuridicoDTO.setIdTipoSogGiuridico(soggetto.getIdTipoSoggettoGiuridico());
		sirtplaTSoggettoGiuridicoDTO.setIdNaturaGiuridica(soggetto.getIdNaturaGiuridica());
		sirtplaTSoggettoGiuridicoDTO.setDenominazioneBreve(soggetto.getDenomBreve());
		sirtplaTSoggettoGiuridicoDTO.setDenominazioneAaep(soggetto.getDenomAAEP());

		sirtplaTSoggettoGiuridicoDTO.setCodCsrBip(soggetto.getCodCsrBip());

		sirtplaTSoggettoGiuridicoDTO.setDenomSoggettoGiuridico(soggetto.getDenomOsservatorioNaz());
		sirtplaTSoggettoGiuridicoDTO.setPartitaIva(soggetto.getPartitaIva());
		sirtplaTSoggettoGiuridicoDTO.setCodiceFiscale(
				soggetto.getCodiceFiscale() != null ? soggetto.getCodiceFiscale().toUpperCase() : null);
		sirtplaTSoggettoGiuridicoDTO.setCodOsservatorioNaz(soggetto.getCodOsservatorioNaz());
		sirtplaTSoggettoGiuridicoDTO.setNomeRapprLegale(soggetto.getNomeRappresentanteLegale());
		sirtplaTSoggettoGiuridicoDTO.setCognomeRapprLegale(soggetto.getCognomeRappresentanteLegale());
		sirtplaTSoggettoGiuridicoDTO.setToponimoSedeLegale(soggetto.getUbicazioneSede().getTop());
		sirtplaTSoggettoGiuridicoDTO.setIndirizzoSedeLegale(soggetto.getUbicazioneSede().getIndirizzo());
		sirtplaTSoggettoGiuridicoDTO.setNumCivicoSedeLegale(soggetto.getUbicazioneSede().getCivico());
		sirtplaTSoggettoGiuridicoDTO.setCapSedeLegale(soggetto.getUbicazioneSede().getCap());
		sirtplaTSoggettoGiuridicoDTO.setIdComuneSedeLegale(soggetto.getUbicazioneSede().getIdComune());
		sirtplaTSoggettoGiuridicoDTO.setTelefonoSedeLegale(soggetto.getTelefonoSede());
		sirtplaTSoggettoGiuridicoDTO.setFaxSedeLegale(soggetto.getFax());
		sirtplaTSoggettoGiuridicoDTO.setEmailSedeLegale(soggetto.getEmail());
		sirtplaTSoggettoGiuridicoDTO.setPecSedeLegale(soggetto.getPec());
		sirtplaTSoggettoGiuridicoDTO.setIndirizzoWeb(soggetto.getIndirizzoWeb());
		sirtplaTSoggettoGiuridicoDTO.setNumeroVerde(soggetto.getNumeroVerde());
		sirtplaTSoggettoGiuridicoDTO.setNote(soggetto.getNote());
		sirtplaTSoggettoGiuridicoDTO.setLogo(file);
		sirtplaTSoggettoGiuridicoDTO.setIdTipoEnte(soggetto.getIdTipoEnte());
		sirtplaTSoggettoGiuridicoDTO.setDescrizione(soggetto.getDescrizione());
		sirtplaTSoggettoGiuridicoDTO.setDataInizioAttivita(soggetto.getDataInizio());
		sirtplaTSoggettoGiuridicoDTO.setDataCessazione(soggetto.getDataCessazione());
		sirtplaTSoggettoGiuridicoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
		sirtplaTSoggettoGiuridicoDTO.setDataAggiornamento(now);

		sirtplaTSoggettoGiuridicoDAO.insert(sirtplaTSoggettoGiuridicoDTO);

		SirtplaTDepositoDTO sirtplaTDepositoDTO = null;

		for (DepositoVO d : soggetto.getDepositi()) {
			sirtplaTDepositoDTO = new SirtplaTDepositoDTO();
			sirtplaTDepositoDTO.setDenomDeposito(d.getDenominazione());
			sirtplaTDepositoDTO.setToponimoDeposito(d.getUbicazione().getTop());
			sirtplaTDepositoDTO.setIndirizzoDeposito(d.getUbicazione().getIndirizzo());
			sirtplaTDepositoDTO.setNumCivicoDeposito(d.getUbicazione().getCivico());
			sirtplaTDepositoDTO.setCapDeposito(d.getUbicazione().getCap());
			sirtplaTDepositoDTO.setIdComuneDeposito(d.getUbicazione().getIdComune());
			sirtplaTDepositoDTO.setTelefonoDeposito(d.getTelefono());
			sirtplaTDepositoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
			sirtplaTDepositoDTO.setDataAggiornamento(now);
			sirtplaTDepositoDTO.setFlagDepositoPrevalente(d.getDepositoPrevalenteFlg());

			if (sirtplaTDepositoDTO.getDenomDeposito() == null && sirtplaTDepositoDTO.getToponimoDeposito() == null
					&& sirtplaTDepositoDTO.getIndirizzoDeposito() == null
					&& sirtplaTDepositoDTO.getNumCivicoDeposito() == null
					&& sirtplaTDepositoDTO.getCapDeposito() == null && sirtplaTDepositoDTO.getIdComuneDeposito() == null
					&& sirtplaTDepositoDTO.getTelefonoDeposito() == null) {
				continue;
			}

			sirtplaTDepositoDAO.insert(sirtplaTDepositoDTO);

			SirtplaRSogGiuridDepositoDTO sirtplaRSogGiuridDepositoDTO = new SirtplaRSogGiuridDepositoDTO();
			sirtplaRSogGiuridDepositoDTO.setIdSoggettoGiuridico(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
			sirtplaRSogGiuridDepositoDTO.setIdDeposito(sirtplaTDepositoDTO.getIdDeposito());
			sirtplaRSogGiuridDepositoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
			sirtplaRSogGiuridDepositoDTO.setDataAggiornamento(now);

			sirtplaRSogGiuridDepositoDAO.insert(sirtplaRSogGiuridDepositoDTO);
		}

		SirtplaTDatoBancarioDTO sirtplaTDatoBancarioDTO = null;
		for (DatiBancariVO d : soggetto.getDatiBancari()) {
			sirtplaTDatoBancarioDTO = new SirtplaTDatoBancarioDTO();
			sirtplaTDatoBancarioDTO.setIdSoggettoGiuridico(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
			sirtplaTDatoBancarioDTO.setIban(d.getIban() != null ? d.getIban().toUpperCase() : null);
			sirtplaTDatoBancarioDTO.setNote(d.getNote());
			sirtplaTDatoBancarioDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
			sirtplaTDatoBancarioDTO.setDataAggiornamento(now);
			if (d.getDoatpl() == null) {
				sirtplaTDatoBancarioDTO.setDoatpl(false);
			} else
				sirtplaTDatoBancarioDTO.setDoatpl(d.getDoatpl());

			if (sirtplaTDatoBancarioDTO.getIban() == null && sirtplaTDatoBancarioDTO.getNote() == null) {
				continue;
			}

			sirtplaTDatoBancarioDAO.insert(sirtplaTDatoBancarioDTO);
		}

		return sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico();
	}

	@Override
	public List<SoggettoEsecutoreVO> getSoggettiEsecutoriTitolari(Boolean bol, Long idSoggettoGiuridico) {
		List<SoggettoEsecutoreVO> soggettiGiuridici = ricercaDAO.getSoggettoEsecutore(bol, idSoggettoGiuridico);
		return soggettiGiuridici;
	}

	@Override
	public SoggettoGiuridicoVO dettaglioSoggetto(long id, String action) {
		if (StringUtils.isEmpty(action) || action.equals(Action.VIEW.value)) {
			SecurityUtils.assertAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_SOGGETTO);
		} else if (action.equals(Action.EDIT.value)) {
			SecurityUtils.assertMultipleAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_SOGGETTO,
					AuthorizationRoles.MODIFICA_ANAGRAFICA_SOGGETTO);
		}

		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectAttivoTpl(id);
		SoggettoGiuridicoVO soggetto = soggettoGiuridicoMapper.mapDTOtoVO(sirtplaTSoggettoGiuridicoDTO);
		if (sirtplaTSoggettoGiuridicoDTO.getAttivoTpl()) {
			soggetto.setAziendaAttiva(Boolean.TRUE);
		} else {
			soggetto.setAziendaAttiva(Boolean.FALSE);
		}

		if (action.equals(Action.EDIT.value)) {
			if (soggetto.getAziendaCessata()) {
				throw new ModificaNonAbilitataException("Il soggetto \u00E8 cessato: modifica non disponibile.");
			}
		}

		SirtplaRSogGiuridDepositoSelector sirtplaRSogGiuridDepositoSelector = new SirtplaRSogGiuridDepositoSelector();
		sirtplaRSogGiuridDepositoSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(id);
		List<SirtplaRSogGiuridDepositoDTO> sirtplaRSogGiuridDepositoDTOs = sirtplaRSogGiuridDepositoDAO
				.selectByExample(sirtplaRSogGiuridDepositoSelector);
		List<DepositoVO> depositi = new ArrayList<>();
		for (SirtplaRSogGiuridDepositoDTO sd : sirtplaRSogGiuridDepositoDTOs) {
			depositi.add(depositoMapper.mapDTOtoVO(sirtplaTDepositoDAO.selectByPrimaryKey(sd.getIdDeposito())));
		}

		Collections.sort(depositi, new Comparator<DepositoVO>() {
			public int compare(DepositoVO d1, DepositoVO d2) {
				if (d1.getDepositoPrevalenteFlg() != null && d1.getDepositoPrevalenteFlg())
					return -1;
				else if (d2.getDepositoPrevalenteFlg() == null || !d2.getDepositoPrevalenteFlg()) {
					return d1.getDenominazione().compareTo(d2.getDenominazione());
				} else
					return 0;
			}
		});
		soggetto.setDepositi(depositi);
		SirtplaTDatoBancarioSelector sirtplaTDatoBancarioSelector = new SirtplaTDatoBancarioSelector();
		sirtplaTDatoBancarioSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(id);
		List<SirtplaTDatoBancarioDTO> sirtplaTDatoBancarioDTOs = sirtplaTDatoBancarioDAO
				.selectByExample(sirtplaTDatoBancarioSelector);
		List<DatiBancariVO> datiBancari = new ArrayList<>();
		for (SirtplaTDatoBancarioDTO db : sirtplaTDatoBancarioDTOs) {
			datiBancari.add(datoBancarioMapper.mapDTOtoVO(db));
		}
		Collections.sort(datiBancari, new Comparator<DatiBancariVO>() {
			public int compare(DatiBancariVO d1, DatiBancariVO d2) {
				if (d1.getDoatpl() != null && d1.getDoatpl())
					return -1;
				else if (d2.getDoatpl() == null || !d2.getDoatpl()) {
					return d1.getIban().compareTo(d2.getIban());
				} else
					return 0;
			}
		});
		soggetto.setDatiBancari(datiBancari);

		if (action.equals(Action.VIEW.value)) {
			VContrattiSoggettiSelector vContrattiSoggettiSelector = new VContrattiSoggettiSelector();
			vContrattiSoggettiSelector.createCriteria().andIdSgEqualTo(id);
			List<VContrattiSoggettiDTO> vContrattiSoggettiDTO = vContrattiSoggettiDAO
					.selectByExample(vContrattiSoggettiSelector);
			List<ContrattoSoggettoVO> contrattoSoggettoTmp = new ArrayList<>();
			for (VContrattiSoggettiDTO contrattoSogg : vContrattiSoggettiDTO) {
				ContrattoSoggettoVO contrattoSoggVO = new ContrattoSoggettoVO();
				contrattoSoggVO.setIdSoggettoGiuridico(contrattoSogg.getIdSg());
				contrattoSoggVO.setIdContratto(contrattoSogg.getIdC());
				contrattoSoggVO.setContratto(contrattoSogg.getContratto());
				contrattoSoggVO.setEnteCommittente(contrattoSogg.getEnteComm());
				contrattoSoggVO.setDataInizioValidita(contrattoSogg.getdIniC());
				contrattoSoggVO.setDataFineValidita(contrattoSogg.getdFinC());
				contrattoSoggVO.setRuolo(contrattoSogg.getRuolo());
				contrattoSoggVO.setCapofila(contrattoSogg.getCapofila());
				contrattoSoggVO.setSoggettoAffidante(contrattoSogg.getSgAff());
				contrattoSoggVO.setDataInizioaAttivita(contrattoSogg.getdIniA());
				contrattoSoggVO.setDataFineAttivita(contrattoSogg.getdFinA());
				contrattoSoggVO.setScaduto(contrattoSogg.getScaduto());
				contrattoSoggettoTmp.add(contrattoSoggVO);
			}
			soggetto.setContrattiSoggetti(contrattoSoggettoTmp);
		}

		return soggetto;
	}

	enum Action {
		EDIT("E"), VIEW("V");

		private String value;

		private Action(String value) {
			this.value = value;
		}

	}

	@Override
	@Transactional
	public Long modificaSoggetto(SoggettoGiuridicoVO soggettoRequest, byte[] fileByte, String fileName) {
		if (soggettoRequest == null) {
			throw new InvalidParameterException("Soggetto non valorizzato");
		}

		if (!(fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png"))
				&& fileByte != null) {
			throw new ErroreGestitoException(
					" Formato file del documento allegato non consentito! Formati consentiti: jpg, jpeg, png.", "TFNC");
		}

		SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector;
		List<SirtplaTSoggettoGiuridicoDTO> soggetti;
		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		sirtplaTSoggettoGiuridicoSelector.createCriteria()
				.andCodOsservatorioNazEqualTo(soggettoRequest.getCodOsservatorioNaz())
				.andIdTipoSogGiuridicoEqualTo(soggettoRequest.getIdTipoSoggettoGiuridico())
				.andIdSoggettoGiuridicoNotEqualTo(soggettoRequest.getId())
				.andIdTipoSogGiuridicoEqualTo(soggettoRequest.getIdTipoSoggettoGiuridico());
		;
		soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		if (soggetti != null && soggetti.size() > 0) {
			throw new ErroreGestitoException("Codice Nazionale duplicato", "TFND");
		}
		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		sirtplaTSoggettoGiuridicoSelector.createCriteria().andCodiceFiscaleEqualTo(soggettoRequest.getCodiceFiscale())
				.andIdSoggettoGiuridicoNotEqualTo(soggettoRequest.getId())
				.andIdTipoSogGiuridicoEqualTo(soggettoRequest.getIdTipoSoggettoGiuridico());

		soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		if (soggetti != null && soggetti.size() > 0) {
			throw new ErroreGestitoException("Codice Fiscale duplicato", "CFD");
		}
		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		sirtplaTSoggettoGiuridicoSelector.createCriteria().andPartitaIvaEqualTo(soggettoRequest.getPartitaIva())
				.andIdSoggettoGiuridicoNotEqualTo(soggettoRequest.getId())
				.andIdTipoSogGiuridicoEqualTo(soggettoRequest.getIdTipoSoggettoGiuridico());
		soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		if (soggetti != null && soggetti.size() > 0) {
			throw new ErroreGestitoException("Partita IVA duplicata", "PID");
		}

		sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		if (soggettoRequest.getCodCsrBip() != null) {
			sirtplaTSoggettoGiuridicoSelector.createCriteria().andCodCsrBipEqualTo(soggettoRequest.getCodCsrBip())
					.andIdSoggettoGiuridicoNotEqualTo(soggettoRequest.getId());

			soggetti = sirtplaTSoggettoGiuridicoDAO.selectByExample(sirtplaTSoggettoGiuridicoSelector);
			if (soggetti != null && soggetti.size() > 0) {
				throw new ErroreGestitoException("Codice CSR-BIP duplicato", "CBD");
			}
		}
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		Date now = new Date();
		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(soggettoRequest.getId());
		if (sirtplaTSoggettoGiuridicoDTO.getIdTipoSogGiuridico().equals(new Long(2))) {
			sirtplaTSoggettoGiuridicoDTO.setDenominazioneAaep(soggettoRequest.getDenomAAEP());
			sirtplaTSoggettoGiuridicoDTO.setNomeRapprLegale(soggettoRequest.getNomeRappresentanteLegale());
			sirtplaTSoggettoGiuridicoDTO.setCognomeRapprLegale(soggettoRequest.getCognomeRappresentanteLegale());
			sirtplaTSoggettoGiuridicoDTO.setDataInizioAttivita(soggettoRequest.getDataInizio());
			sirtplaTSoggettoGiuridicoDTO.setDataCessazione(soggettoRequest.getDataCessazione());
		} else {
			sirtplaTSoggettoGiuridicoDTO.setDescrizione(soggettoRequest.getDescrizione());
		}
		sirtplaTSoggettoGiuridicoDTO.setDenominazioneBreve(soggettoRequest.getDenomBreve());
		sirtplaTSoggettoGiuridicoDTO.setDenomSoggettoGiuridico(soggettoRequest.getDenomOsservatorioNaz());
		sirtplaTSoggettoGiuridicoDTO.setCodOsservatorioNaz(soggettoRequest.getCodOsservatorioNaz());
		sirtplaTSoggettoGiuridicoDTO.setPartitaIva(soggettoRequest.getPartitaIva());
		sirtplaTSoggettoGiuridicoDTO.setCodCsrBip(soggettoRequest.getCodCsrBip());
		sirtplaTSoggettoGiuridicoDTO.setCodiceFiscale(
				soggettoRequest.getCodiceFiscale() != null ? soggettoRequest.getCodiceFiscale().toUpperCase() : null);
		sirtplaTSoggettoGiuridicoDTO.setNote(soggettoRequest.getNote());
		if (fileByte != null) {
			sirtplaTSoggettoGiuridicoDTO.setLogo(fileByte);
		}
		sirtplaTSoggettoGiuridicoDTO.setToponimoSedeLegale(soggettoRequest.getUbicazioneSede().getTop());
		sirtplaTSoggettoGiuridicoDTO.setIndirizzoSedeLegale(soggettoRequest.getUbicazioneSede().getIndirizzo());
		sirtplaTSoggettoGiuridicoDTO.setNumCivicoSedeLegale(soggettoRequest.getUbicazioneSede().getCivico());
		sirtplaTSoggettoGiuridicoDTO.setIdComuneSedeLegale(soggettoRequest.getUbicazioneSede().getIdComune());
		sirtplaTSoggettoGiuridicoDTO.setCapSedeLegale(soggettoRequest.getUbicazioneSede().getCap());
		sirtplaTSoggettoGiuridicoDTO.setTelefonoSedeLegale(soggettoRequest.getTelefonoSede());
		sirtplaTSoggettoGiuridicoDTO.setFaxSedeLegale(soggettoRequest.getFax());
		sirtplaTSoggettoGiuridicoDTO.setEmailSedeLegale(soggettoRequest.getEmail());
		sirtplaTSoggettoGiuridicoDTO.setPecSedeLegale(soggettoRequest.getPec());
		sirtplaTSoggettoGiuridicoDTO.setIndirizzoWeb(soggettoRequest.getIndirizzoWeb());
		sirtplaTSoggettoGiuridicoDTO.setNumeroVerde(soggettoRequest.getNumeroVerde());

		sirtplaTSoggettoGiuridicoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
		sirtplaTSoggettoGiuridicoDTO.setDataAggiornamento(now);

		sirtplaTSoggettoGiuridicoDAO.updateByPrimaryKeyWithBLOBs(sirtplaTSoggettoGiuridicoDTO);

		SirtplaTDepositoDTO sirtplaTDepositoDTO = null;
		// recupero i depositi per il soggetto giuridico attuale
		List<Long> idDepositi = new ArrayList<>();
		for (DepositoVO d : soggettoRequest.getDepositi()) {
			if (d.getId() != null)
				idDepositi.add(d.getId());
		}
		SirtplaRSogGiuridDepositoSelector sogGiuridDepositiSelector = new SirtplaRSogGiuridDepositoSelector();
		sogGiuridDepositiSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(soggettoRequest.getId());
		List<SirtplaRSogGiuridDepositoDTO> sogGiuridDepositi = sirtplaRSogGiuridDepositoDAO
				.selectByExample(sogGiuridDepositiSelector);
		if (sogGiuridDepositi.size() > 0) {
			SirtplaTDepositoSelector depositiSelector = new SirtplaTDepositoSelector();
			depositiSelector.createCriteria();
			for (SirtplaRSogGiuridDepositoDTO d : sogGiuridDepositi) {
				depositiSelector.or().andIdDepositoEqualTo(d.getIdDeposito());
			}
			List<SirtplaTDepositoDTO> depositiDB = sirtplaTDepositoDAO.selectByExample(depositiSelector);

			for (SirtplaTDepositoDTO d : depositiDB) {
				if (!idDepositi.contains(d.getIdDeposito())) {
					if (this.checkAutobusDeposito(d.getIdDeposito())) {
						throw new ErroreGestitoException("Impossibile eliminare il deposito per la presenza di autobus",
								"TFNC");
					}
					SirtplaRSogGiuridDepositoSelector sirtplaRSogGiuridDepositoSelector = new SirtplaRSogGiuridDepositoSelector();
					sirtplaRSogGiuridDepositoSelector.createCriteria().andIdDepositoEqualTo(d.getIdDeposito());
					sirtplaRSogGiuridDepositoDAO.deleteByExample(sirtplaRSogGiuridDepositoSelector);
					sirtplaTDepositoDAO.deleteByPrimaryKey(d.getIdDeposito());
				}
			}
		}
		for (DepositoVO d : soggettoRequest.getDepositi()) {
			if (d.getId() == null)
				sirtplaTDepositoDTO = new SirtplaTDepositoDTO();
			else
				sirtplaTDepositoDTO = sirtplaTDepositoDAO.selectByPrimaryKey(d.getId());

			sirtplaTDepositoDTO.setDenomDeposito(d.getDenominazione());
			sirtplaTDepositoDTO.setToponimoDeposito(d.getUbicazione().getTop());
			sirtplaTDepositoDTO.setIndirizzoDeposito(d.getUbicazione().getIndirizzo());
			sirtplaTDepositoDTO.setNumCivicoDeposito(d.getUbicazione().getCivico());
			sirtplaTDepositoDTO.setCapDeposito(d.getUbicazione().getCap());
			sirtplaTDepositoDTO.setIdComuneDeposito(d.getUbicazione().getIdComune());
			sirtplaTDepositoDTO.setTelefonoDeposito(d.getTelefono());
			sirtplaTDepositoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
			sirtplaTDepositoDTO.setDataAggiornamento(now);
			sirtplaTDepositoDTO.setFlagDepositoPrevalente(d.getDepositoPrevalenteFlg());

			if (sirtplaTDepositoDTO.getDenomDeposito() == null && sirtplaTDepositoDTO.getToponimoDeposito() == null
					&& sirtplaTDepositoDTO.getIndirizzoDeposito() == null
					&& sirtplaTDepositoDTO.getNumCivicoDeposito() == null
					&& sirtplaTDepositoDTO.getCapDeposito() == null && sirtplaTDepositoDTO.getIdComuneDeposito() == null
					&& sirtplaTDepositoDTO.getTelefonoDeposito() == null) {
				continue;
			}
			if (d.getId() == null) {
				sirtplaTDepositoDAO.insert(sirtplaTDepositoDTO);

				SirtplaRSogGiuridDepositoDTO sirtplaRSogGiuridDepositoDTO = new SirtplaRSogGiuridDepositoDTO();
				sirtplaRSogGiuridDepositoDTO
						.setIdSoggettoGiuridico(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
				sirtplaRSogGiuridDepositoDTO.setIdDeposito(sirtplaTDepositoDTO.getIdDeposito());
				sirtplaRSogGiuridDepositoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				sirtplaRSogGiuridDepositoDTO.setDataAggiornamento(now);

				sirtplaRSogGiuridDepositoDAO.insert(sirtplaRSogGiuridDepositoDTO);
			} else {
				sirtplaTDepositoDAO.updateByPrimaryKey(sirtplaTDepositoDTO);
			}
		}
		SirtplaTDatoBancarioSelector datoBancarioSelector = new SirtplaTDatoBancarioSelector();
		datoBancarioSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(soggettoRequest.getId());
		List<SirtplaTDatoBancarioDTO> datiBancariDB = sirtplaTDatoBancarioDAO.selectByExample(datoBancarioSelector);
		List<Long> idDatiBancari = new ArrayList<>();
		for (DatiBancariVO d : soggettoRequest.getDatiBancari()) {
			if (d.getId() != null)
				idDatiBancari.add(d.getId());
		}

		for (SirtplaTDatoBancarioDTO d : datiBancariDB) {
			if (!idDatiBancari.contains(d.getIdDatoBancario())) {
				sirtplaTDatoBancarioDAO.deleteByPrimaryKey(d.getIdDatoBancario());
			}
		}

		SirtplaTDatoBancarioDTO sirtplaTDatoBancarioDTO = null;
		for (DatiBancariVO d : soggettoRequest.getDatiBancari()) {
			if (d.getId() == null) {
				sirtplaTDatoBancarioDTO = new SirtplaTDatoBancarioDTO();
			} else {
				sirtplaTDatoBancarioDTO = sirtplaTDatoBancarioDAO.selectByPrimaryKey(d.getId());
			}
			sirtplaTDatoBancarioDTO.setIdSoggettoGiuridico(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
			sirtplaTDatoBancarioDTO.setIban(d.getIban() != null ? d.getIban().toUpperCase() : null);
			sirtplaTDatoBancarioDTO.setNote(d.getNote());
			sirtplaTDatoBancarioDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
			sirtplaTDatoBancarioDTO.setDataAggiornamento(now);
			if (d.getDoatpl() == null) {
				sirtplaTDatoBancarioDTO.setDoatpl(false);
			} else
				sirtplaTDatoBancarioDTO.setDoatpl(d.getDoatpl());

			if (sirtplaTDatoBancarioDTO.getIban() == null && sirtplaTDatoBancarioDTO.getNote() == null) {
				continue;
			}

			if (d.getId() == null)
				sirtplaTDatoBancarioDAO.insert(sirtplaTDatoBancarioDTO);
			else
				sirtplaTDatoBancarioDAO.updateByPrimaryKey(sirtplaTDatoBancarioDTO);
		}

		return sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico();

	}

	@Override
	public byte[] getLogoByIdSoggetto(Long id) {
		return this.sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(id).getLogo();
	}

	private boolean checkAutobusDeposito(Long idDeposito) {
		RebusTVariazAutobusSelector sel = new RebusTVariazAutobusSelector();
		sel.createCriteria().andFkDepositoEqualTo(idDeposito);
		List<RebusTVariazAutobusDTO> res = rebusTVariazAutobusDAO.selectByExample(sel);
		if (!res.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public void eliminaLogoOnDb(Long idLogo) {
		SirtplaTSoggettoGiuridicoDTO soggetto = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(idLogo);
		soggetto.setLogo(null);
		sirtplaTSoggettoGiuridicoDAO.updateByPrimaryKeyWithBLOBs(soggetto);

	}
}
