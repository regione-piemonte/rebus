/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.anagraficasrv.business.service.ContrattoService;
import it.csi.rebus.anagraficasrv.common.exception.ErroreGestitoException;
import it.csi.rebus.anagraficasrv.integration.dao.RebusDTipoDocumentoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaDTipoSogGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDAmbitoServizioDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDBacinoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDModalitaAffidamentoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDTipoAffidamentoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDTipoSostituzioneDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcDTipologiaServizioDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRAmbTipServizioDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRContrAmbTipServDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRContrattoRaggruppDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRSostSogContrDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcRSostSogContrRaggrDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcTContrattoAllegatoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcTContrattoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.SirtplcTProrogaContrattoDAO;
import it.csi.rebus.anagraficasrv.integration.dao.VSoggettiCoinvoltiDAO;
import it.csi.rebus.anagraficasrv.integration.dao.VSoggettiCoinvoltiPeriodiDAO;
import it.csi.rebus.anagraficasrv.integration.dao.custom.RicercaDAO;
import it.csi.rebus.anagraficasrv.integration.dto.RebusDTipoDocumentoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.RebusDTipoDocumentoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaDTipoSogGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplaTSoggettoGiuridicoSelector.Criteria;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDAmbitoServizioDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDAmbitoServizioSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDBacinoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDBacinoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDModalitaAffidamentoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDModalitaAffidamentoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoAffidamentoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoAffidamentoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoSostituzioneDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipoSostituzioneSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipologiaServizioDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcDTipologiaServizioSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRAmbTipServizioDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRAmbTipServizioSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrAmbTipServKey;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrAmbTipServSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrattoRaggruppDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrattoRaggruppSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrRaggrDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrRaggrSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRSostSogContrSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoAllegatoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoAllegatoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTContrattoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTProrogaContrattoDTO;
import it.csi.rebus.anagraficasrv.integration.dto.SirtplcTProrogaContrattoSelector;
import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiPeriodiDTO;
import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiPeriodiSelector;
import it.csi.rebus.anagraficasrv.integration.dto.VSoggettiCoinvoltiSelector;
import it.csi.rebus.anagraficasrv.integration.mapper.AmbTipServizioMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.AmbitoServizioMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.BacinoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.ContrattoAmbitoServizioMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.ContrattoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.ContrattoRaggruppamentoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.ModalitaAffidamentoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.ProrogheMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.SoggettoSubentroMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.SostSogContrMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.SostSogContrRaggrMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.TipoAffidamentoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.TipoDocumentoMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.TipoSostituzioneMapper;
import it.csi.rebus.anagraficasrv.integration.mapper.TipologiaServizioMapper;
import it.csi.rebus.anagraficasrv.security.AuthorizationRoles;
import it.csi.rebus.anagraficasrv.security.CodeRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.AllegatoVO;
import it.csi.rebus.anagraficasrv.vo.AmbTipServizioVO;
import it.csi.rebus.anagraficasrv.vo.AmbitoServizioVO;
import it.csi.rebus.anagraficasrv.vo.AziendaMandatariaVO;
import it.csi.rebus.anagraficasrv.vo.BacinoVO;
import it.csi.rebus.anagraficasrv.vo.ContrAmbTipServVO;
import it.csi.rebus.anagraficasrv.vo.ContrattoRaggruppamentoVO;
import it.csi.rebus.anagraficasrv.vo.ContrattoVO;
import it.csi.rebus.anagraficasrv.vo.InserisciContrattoVO;
import it.csi.rebus.anagraficasrv.vo.ModalitaAffidamentoVO;
import it.csi.rebus.anagraficasrv.vo.ProrogaVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoCoinvoltoPeriodiVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoCoinvoltoVO;
import it.csi.rebus.anagraficasrv.vo.SoggettoSubentroVO;
import it.csi.rebus.anagraficasrv.vo.SubentroSubaffidamentoVO;
import it.csi.rebus.anagraficasrv.vo.TipoAffidamentoVO;
import it.csi.rebus.anagraficasrv.vo.TipoDocumentoVO;
import it.csi.rebus.anagraficasrv.vo.TipoSostituzioneVO;
import it.csi.rebus.anagraficasrv.vo.TipologiaServizioVO;

@Service
public class ContrattoServiceImpl implements ContrattoService {

	@Autowired
	private SirtplcTContrattoDAO sirtplcTContrattoDAO;
	@Autowired
	private SirtplcRContrattoRaggruppDAO sirtplcRContrattoRaggruppDAO;
	@Autowired
	private SirtplcTContrattoAllegatoDAO sirtplcTContrattoAllegatoDAO;
	@Autowired
	private SirtplcDBacinoDAO sirtplcDBacinoDAO;
	@Autowired
	private SirtplcDTipoAffidamentoDAO sirtplcDTipoAffidamentoDAO;
	@Autowired
	private SirtplcDModalitaAffidamentoDAO sirtplcDModalitaAffidamentoDAO;
	@Autowired
	private SirtplcDTipologiaServizioDAO sirtplcDTipologiaServizioDAO;
	@Autowired
	private SirtplcDAmbitoServizioDAO sirtplcDAmbitoServizioDAO;
	@Autowired
	private RebusDTipoDocumentoDAO rebusDTipoDocumentoDAO;
	@Autowired
	private SirtplcRAmbTipServizioDAO sirtplcRAmbTipServizioDAO;
	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;
	@Autowired
	private SirtplcRContrAmbTipServDAO sirtplcRContrAmbTipServDAO;
	@Autowired
	private SirtplcTProrogaContrattoDAO sirtplcTProrogaContrattoDAO;
	@Autowired
	private SirtplcDTipoSostituzioneDAO sirtplcDTipoSostituzioneDAO;
	@Autowired
	private SirtplcRSostSogContrDAO sirtplcRSostSogContrDAO;
	@Autowired
	private SirtplcRSostSogContrRaggrDAO sirtplcRSostSogContrRaggrDAO;
	@Autowired
	private VSoggettiCoinvoltiDAO vSoggettiCoinvoltiDAO;
	@Autowired
	private VSoggettiCoinvoltiPeriodiDAO vSoggettiCoinvoltiPeriodiDAO;
	@Autowired
	private SirtplaDTipoSogGiuridicoDAO sirtplaDTipoSogGiuridicoDAO;
	@Autowired
	private RicercaDAO ricercaDAO;
	@Autowired
	private BacinoMapper bacinoMapper;
	@Autowired
	private TipoAffidamentoMapper tipoAffidamentoMapper;
	@Autowired
	private ModalitaAffidamentoMapper modalitaAffidamentoMapper;
	@Autowired
	private TipologiaServizioMapper tipologiaServizioMapper;
	@Autowired
	private AmbitoServizioMapper ambitoServizioMapper;
	@Autowired
	private TipoDocumentoMapper tipoDocumentoMapper;
	@Autowired
	private ContrattoMapper contrattoMapper;
	@Autowired
	private ContrattoRaggruppamentoMapper contrattoRaggruppamentoMapper;
	@Autowired
	private ContrattoAmbitoServizioMapper contrattoAmbitoServizioMapper;
	@Autowired
	private AmbTipServizioMapper ambTipServizioMapper;
	@Autowired
	private ProrogheMapper prorogheMapper;
	@Autowired
	private TipoSostituzioneMapper tipoSostituzioneMapper;
	@Autowired
	private SoggettoSubentroMapper soggettoSubentroMapper;
	@Autowired
	private SostSogContrMapper sostSogContrMapper;
	@Autowired
	private SostSogContrRaggrMapper sostSogContrRaggrMapper;

	@Override
	public List<BacinoVO> getBacini() {
		SirtplcDBacinoSelector sirtplcDBacinoSelector = new SirtplcDBacinoSelector();
		sirtplcDBacinoSelector.setOrderByClause("denom_bacino");
		List<SirtplcDBacinoDTO> bacini = sirtplcDBacinoDAO.selectByExample(sirtplcDBacinoSelector);
		List<BacinoVO> baciniVO = new ArrayList<>();
		for (SirtplcDBacinoDTO b : bacini) {
			baciniVO.add(bacinoMapper.mapDTOtoVO(b));
		}
		return baciniVO;
	}

	@Override
	public List<TipoAffidamentoVO> getTipiAffidamento() {
		SirtplcDTipoAffidamentoSelector sirtplcDTipoAffidamentoSelector = new SirtplcDTipoAffidamentoSelector();
		sirtplcDTipoAffidamentoSelector.setOrderByClause("desc_tipo_affidamento");
		List<SirtplcDTipoAffidamentoDTO> tipi = sirtplcDTipoAffidamentoDAO
				.selectByExample(sirtplcDTipoAffidamentoSelector);
		List<TipoAffidamentoVO> tipiAffidamentoVO = new ArrayList<>();
		for (SirtplcDTipoAffidamentoDTO t : tipi) {
			tipiAffidamentoVO.add(tipoAffidamentoMapper.mapDTOtoVO(t));
		}
		return tipiAffidamentoVO;
	}

	@Override
	public List<ModalitaAffidamentoVO> getModalitaAffidamento() {
		SirtplcDModalitaAffidamentoSelector sirtplcDModalitaAffidamentoSelector = new SirtplcDModalitaAffidamentoSelector();
		sirtplcDModalitaAffidamentoSelector.setOrderByClause("desc_modalita_affidamento");
		List<SirtplcDModalitaAffidamentoDTO> mod = sirtplcDModalitaAffidamentoDAO
				.selectByExample(sirtplcDModalitaAffidamentoSelector);
		List<ModalitaAffidamentoVO> modalitaAffidamentoVO = new ArrayList<>();
		for (SirtplcDModalitaAffidamentoDTO m : mod) {
			modalitaAffidamentoVO.add(modalitaAffidamentoMapper.mapDTOtoVO(m));
		}
		return modalitaAffidamentoVO;
	}

	@Override
	public List<AmbTipServizioVO> getAmbTipServizio() {
		SirtplcRAmbTipServizioSelector sirtplcRAmbTipServizioSelector = new SirtplcRAmbTipServizioSelector();
		sirtplcRAmbTipServizioSelector.setOrderByClause("id_tipologia_servizio,id_ambito_servizio");
		List<SirtplcRAmbTipServizioDTO> ambTipServizi = sirtplcRAmbTipServizioDAO
				.selectByExample(sirtplcRAmbTipServizioSelector);
		List<AmbTipServizioVO> ambTipServiziVO = new ArrayList<>();
		for (SirtplcRAmbTipServizioDTO t : ambTipServizi) {
			AmbTipServizioVO var = ambTipServizioMapper.mapDTOtoVO(t);
			SirtplcDTipologiaServizioDTO tipologiaServizio = sirtplcDTipologiaServizioDAO
					.selectByPrimaryKey(t.getIdTipologiaServizio());
			var.setTipologiaServizio(tipologiaServizioMapper.mapDTOtoVO(tipologiaServizio));
			SirtplcDAmbitoServizioDTO ambitoServizio = sirtplcDAmbitoServizioDAO
					.selectByPrimaryKey(t.getIdAmbitoServizio());
			var.setAmbitoServizio(ambitoServizioMapper.mapDTOtoVO(ambitoServizio));
			ambTipServiziVO.add(var);
		}
		return ambTipServiziVO;
	}

	@Override
	public String getDescrizioneAmbTipServiziobyId(Long id) {
		SirtplcRAmbTipServizioDTO ambTipoServizio = sirtplcRAmbTipServizioDAO.selectByPrimaryKey(id);
		SirtplcDTipologiaServizioDTO tipologiaServizio = sirtplcDTipologiaServizioDAO
				.selectByPrimaryKey(ambTipoServizio.getIdTipologiaServizio());
		SirtplcDAmbitoServizioDTO ambitoServizio = sirtplcDAmbitoServizioDAO
				.selectByPrimaryKey(ambTipoServizio.getIdAmbitoServizio());

		return tipologiaServizio.getDescTipologiaServizio() + " - " + ambitoServizio.getDescAmbitoServizio();
	}

	@Override
	public List<TipologiaServizioVO> getTipologieServizio() {
		SirtplcDTipologiaServizioSelector sirtplcDTipologiaServizioSelector = new SirtplcDTipologiaServizioSelector();
		sirtplcDTipologiaServizioSelector.setOrderByClause("id_tipologia_servizio");
		List<SirtplcDTipologiaServizioDTO> tipologie = sirtplcDTipologiaServizioDAO
				.selectByExample(sirtplcDTipologiaServizioSelector);
		List<TipologiaServizioVO> tipologieServizioVO = new ArrayList<>();
		for (SirtplcDTipologiaServizioDTO t : tipologie) {
			tipologieServizioVO.add(tipologiaServizioMapper.mapDTOtoVO(t));
		}
		return tipologieServizioVO;
	}

	@Override
	public List<AmbitoServizioVO> getAmbitiServizioByIdTipologiaServizio(Long idTipologiaServizio) {
		SirtplcRAmbTipServizioSelector sirtplcRAmbTipServizioSelector = new SirtplcRAmbTipServizioSelector();
		sirtplcRAmbTipServizioSelector.createCriteria().andIdTipologiaServizioEqualTo(idTipologiaServizio);
		List<SirtplcRAmbTipServizioDTO> ambTips = sirtplcRAmbTipServizioDAO
				.selectByExample(sirtplcRAmbTipServizioSelector);
		if (ambTips.size() == 0)
			return new ArrayList<AmbitoServizioVO>();
		SirtplcDAmbitoServizioSelector sirtplcDAmbitoServizioSelector = new SirtplcDAmbitoServizioSelector();
		sirtplcDAmbitoServizioSelector.setOrderByClause("desc_ambito_servizio");
		for (SirtplcRAmbTipServizioDTO ambTip : ambTips) {
			sirtplcDAmbitoServizioSelector.or(sirtplcDAmbitoServizioSelector.createCriteria()
					.andIdAmbitoServizioEqualTo(ambTip.getIdAmbitoServizio()));
		}
		List<SirtplcDAmbitoServizioDTO> ambiti = sirtplcDAmbitoServizioDAO
				.selectByExample(sirtplcDAmbitoServizioSelector);
		List<AmbitoServizioVO> ambitiServizioVO = new ArrayList<>();
		for (SirtplcDAmbitoServizioDTO a : ambiti) {
			ambitiServizioVO.add(ambitoServizioMapper.mapDTOtoVO(a));
		}
		return ambitiServizioVO;
	}

	@Override
	public List<TipoDocumentoVO> getTipiDocumento() {
		RebusDTipoDocumentoSelector rebusDTipoDocumentoSelector = new RebusDTipoDocumentoSelector();
		rebusDTipoDocumentoSelector.createCriteria().andIdContestoEqualTo(Long.valueOf(3));
		rebusDTipoDocumentoSelector.setOrderByClause("ordinamento");
		List<RebusDTipoDocumentoDTO> tipi = rebusDTipoDocumentoDAO.selectByExample(rebusDTipoDocumentoSelector);
		List<TipoDocumentoVO> tipiDocumentoVO = new ArrayList<>();
		for (RebusDTipoDocumentoDTO t : tipi) {
			tipiDocumentoVO.add(tipoDocumentoMapper.mapDTOtoVO(t));
		}
		return tipiDocumentoVO;
	}

	@Override
	public List<AziendaMandatariaVO> getAziendaMandataria(Long idContratto) {
		List<AziendaMandatariaVO> aziendaMandatariaVO = ricercaDAO.getAziendaMandataria(idContratto);
		return aziendaMandatariaVO;
	}

	@Override
	public List<ContrAmbTipServVO> getContrattiAmbitiTipologiaServizio(Long idContratto) {
		SirtplcRContrAmbTipServSelector sirtplcRContrAmbTipServSelector = new SirtplcRContrAmbTipServSelector();
		sirtplcRContrAmbTipServSelector.createCriteria().andIdContrattoEqualTo(idContratto);
		List<SirtplcRContrAmbTipServKey> contrattiAmbTipS = sirtplcRContrAmbTipServDAO
				.selectByExample(sirtplcRContrAmbTipServSelector);
		if (contrattiAmbTipS.size() == 0)
			return new ArrayList<ContrAmbTipServVO>();

		List<ContrAmbTipServVO> contrAmbTipServVO = new ArrayList<>();
		for (SirtplcRContrAmbTipServKey c : contrattiAmbTipS) {
			contrAmbTipServVO.add(contrattoAmbitoServizioMapper.mapDTOtoVO(c));
		}
		return contrAmbTipServVO;
	}

	@Transactional
	@Override
	public Long inserisciContratto(InserisciContrattoVO contrattoRequest) {
		if (contrattoRequest == null) {
			throw new InvalidParameterException("Contratto non valorizzato");
		}
		for (AllegatoVO f : contrattoRequest.getFiles()) {
			if (!f.getNomeFile().toLowerCase().contains(".pdf") && !f.getNomeFile().toLowerCase().contains(".zip")
					&& !f.getNomeFile().toLowerCase().contains(".7z")) {
				throw new ErroreGestitoException(
						"Formato file del documento allegato non consentito! Formato consentiti: PDF , zip , 7z.",
						"TFNC");
			}
		}

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		// check if codice identificativo nazionale esiste gia
		if (contrattoRequest.getCodiceIdentificativoNazionale() != null) {
			this.checkCodiceIdentificativoNazionale(contrattoRequest.getCodiceIdentificativoNazionale());
		}
		SirtplcTContrattoDTO sirtplcTContrattoDTO = new SirtplcTContrattoDTO();
		sirtplcTContrattoDTO.setIdContrattoPadre(null);
		sirtplcTContrattoDTO.setDescContratto(contrattoRequest.getDescrizione().trim());
		sirtplcTContrattoDTO.setCodIdNazionale(contrattoRequest.getCodiceIdentificativoNazionale() != null
				? contrattoRequest.getCodiceIdentificativoNazionale().trim().toUpperCase()
				: null);
		sirtplcTContrattoDTO.setNumRepertorio(contrattoRequest.getNumeroRepertorio() != null
				? contrattoRequest.getNumeroRepertorio().toUpperCase().trim()
				: null);
		sirtplcTContrattoDTO.setIdBacino(contrattoRequest.getIdBacino());
		sirtplcTContrattoDTO.setIdTipoAffidamento(contrattoRequest.getIdTipoAffidamento());
		sirtplcTContrattoDTO.setIdModalitaAffidamento(contrattoRequest.getIdModalitaAffidamento());
		sirtplcTContrattoDTO.setAccordoProgramma(
				contrattoRequest.getAccordoDiProgramma() != null ? contrattoRequest.getAccordoDiProgramma().trim()
						: null);
		sirtplcTContrattoDTO.setGrossCost(contrattoRequest.isGrossCost());
		sirtplcTContrattoDTO
				.setCig(contrattoRequest.getCig() != null ? contrattoRequest.getCig().toUpperCase().trim() : null);
		sirtplcTContrattoDTO.setDataStipula(contrattoRequest.getDataStipula());
		sirtplcTContrattoDTO.setDataAggiornamento(new Date());
		sirtplcTContrattoDTO.setIdSogGiuridCommittente(contrattoRequest.getIdEnteCommittente());
		sirtplcTContrattoDTO.setIdTipoSogGiuridEsec(contrattoRequest.getIdTipoSoggettoEsecutore());
		sirtplcTContrattoDTO.setIdSogGiuridEsecutore(contrattoRequest.getIdSoggettoEsecutore());
		sirtplcTContrattoDTO.setIdTipoRaggrSogGiuridEsec(contrattoRequest.getIdTipoRaggruppamento());
		sirtplcTContrattoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
		sirtplcTContrattoDTO.setDataFineValidita(contrattoRequest.getDataFineValidita());
		sirtplcTContrattoDTO.setDataInizioValidita(contrattoRequest.getDataInizioValidita());

		int idContratto = sirtplcTContrattoDAO.insert(sirtplcTContrattoDTO);

		for (Long id : contrattoRequest.getIdAmbTipoServizi()) {
			SirtplcRContrAmbTipServKey sirtplcRContrAmbTipServKey = new SirtplcRContrAmbTipServKey();
			sirtplcRContrAmbTipServKey.setIdContratto(Long.valueOf(sirtplcTContrattoDTO.getIdContratto()));
			sirtplcRContrAmbTipServKey.setIdAmbTipServizio(id);
			sirtplcRContrAmbTipServDAO.insert(sirtplcRContrAmbTipServKey);
		}

		// Se nella combo Tipo soggetto esecutore titolare e selezionata la voce
		// Raggruppamento
		if (contrattoRequest.getIdTipoSoggettoEsecutore().equals(Long.valueOf(3))) {
			SirtplcRContrattoRaggruppDTO sirtplcRContrattoRaggruppDTO = new SirtplcRContrattoRaggruppDTO();
			sirtplcRContrattoRaggruppDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
			sirtplcRContrattoRaggruppDTO.setIdSoggettoGiuridico(contrattoRequest.getIdAziendaMandataria());
			sirtplcRContrattoRaggruppDTO.setCapofila(true);
			sirtplcRContrattoRaggruppDAO.insert(sirtplcRContrattoRaggruppDTO);

			for (Long id : contrattoRequest.getIdComposizioneRaggruppamento()) {
				sirtplcRContrattoRaggruppDTO = new SirtplcRContrattoRaggruppDTO();
				sirtplcRContrattoRaggruppDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
				sirtplcRContrattoRaggruppDTO.setIdSoggettoGiuridico(id);
				sirtplcRContrattoRaggruppDTO.setCapofila(false);
				sirtplcRContrattoRaggruppDAO.insert(sirtplcRContrattoRaggruppDTO);
			}
		}

		// gestione allegato
		if (contrattoRequest.getFiles() != null) {
			for (AllegatoVO file : contrattoRequest.getFiles()) {
				SirtplcTContrattoAllegatoDTO sirtplcTContrattoAllegatoDTO = new SirtplcTContrattoAllegatoDTO();
				sirtplcTContrattoAllegatoDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
				sirtplcTContrattoAllegatoDTO.setIdTipoDocumento(file.getIdTipoDocumento());
				sirtplcTContrattoAllegatoDTO.setNote(file.getNoteFile());
				sirtplcTContrattoAllegatoDTO.setAllegato(file.getFileByte());
				sirtplcTContrattoAllegatoDTO.setDataAggiornamento(file.getDataCaricamento());
				sirtplcTContrattoAllegatoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				sirtplcTContrattoAllegatoDTO.setNomeFile(file.getNomeFile());
				sirtplcTContrattoAllegatoDAO.insert(sirtplcTContrattoAllegatoDTO);
			}
		}

		return sirtplcTContrattoDTO.getIdContratto();
	}

	@Transactional
	@Override
	public Long modificaContratto(ContrattoVO contrattoRequest) {
		if (contrattoRequest == null) {
			throw new InvalidParameterException("Contratto non valorizzato");
		}
		if (contrattoRequest.getAllegati() == null) {
			for (AllegatoVO f : contrattoRequest.getAllegati()) {
				if (!f.getNomeFile().toLowerCase().contains(".pdf")) {
					throw new ErroreGestitoException(
							"Formato file del documento allegato non consentito! Formato consentito: PDF.", "TFNC");
				}
			}
		}

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		boolean isUtenteRegioneoServizi = userInfo.getRuolo() != null
				&& (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_REGIONE.getId())
						|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_SERVIZIO.getId()));
		boolean isAMP = userInfo.getRuolo() != null
				&& (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AMP.getId()));

		SirtplcTContrattoDTO sirtplcTContrattoDTO = new SirtplcTContrattoDTO();
		sirtplcTContrattoDTO = sirtplcTContrattoDAO.selectByPrimaryKey(contrattoRequest.getIdContratto());
		// check if codice identificativo nazionale esiste gia
		if ((sirtplcTContrattoDTO.getCodIdNazionale() == null || (sirtplcTContrattoDTO.getCodIdNazionale() != null
				&& (sirtplcTContrattoDTO.getCodIdNazionale().equals(""))))
				&& contrattoRequest.getCodIdNazionale() != null) {
			this.checkCodiceIdentificativoNazionale(contrattoRequest.getCodIdNazionale());
		}

		if (contrattoRequest.getSubentriSubaffidamenti() != null) {
			List<Long> idSubentriSubaff = new ArrayList<>();
			List<Long> idSubentriSubaffRaggr = new ArrayList<>();
			for (SubentroSubaffidamentoVO sub : contrattoRequest.getSubentriSubaffidamenti()) {
				if (sub.getId() == null) {
					if (sub.getContraenteGroup().equals("EC") || sub.getContraenteGroup().equals("ET")) {
						// ente commmittente o esecutore titolare
						SirtplcRSostSogContrDTO sirtplcRSostSogContrDTO = new SirtplcRSostSogContrDTO();
						sirtplcRSostSogContrDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
						if (sub.getContraenteGroup().equals("EC")) {
							sirtplcRSostSogContrDTO.setIdSogGiuridCommittente(sub.getSoggettoSubentrante().getId());
						} else {
							sirtplcRSostSogContrDTO.setIdSogGiuridEsecutore(sub.getSoggettoSubentrante().getId());
						}
						sirtplcRSostSogContrDTO.setAttoSostituzione(sub.getAtto());
						sirtplcRSostSogContrDTO.setDataSostituzione(sub.getData());
						sirtplcRSostSogContrDTO.setDataFineSostituzione(sub.getDataFine());
						sirtplcRSostSogContrDTO.setIdTipoSostituzione(sub.getTipoSostituzione().getId());
						sirtplcRSostSogContrDAO.insert(sirtplcRSostSogContrDTO);
						idSubentriSubaff.add(sirtplcRSostSogContrDTO.getIdSostSogContr());

					} else if (sub.getContraenteGroup().equals("R")) {
						// raggruppamento
						SirtplcRSostSogContrRaggrDTO sirtplcRSostSogContrRaggrDTO = new SirtplcRSostSogContrRaggrDTO();
						SirtplcRContrattoRaggruppSelector sirtplcRContrattoRaggruppSelector = new SirtplcRContrattoRaggruppSelector();
						sirtplcRContrattoRaggruppSelector.createCriteria()
								.andIdContrattoEqualTo(sirtplcTContrattoDTO.getIdContratto())
								.andIdSoggettoGiuridicoEqualTo(sub.getSoggettoContraente().getId());
						List<SirtplcRContrattoRaggruppDTO> sirtplcRContrattoRaggruppDTOs = sirtplcRContrattoRaggruppDAO
								.selectByExample(sirtplcRContrattoRaggruppSelector);
						if (sirtplcRContrattoRaggruppDTOs != null && sirtplcRContrattoRaggruppDTOs.size() == 1) {
							sirtplcRSostSogContrRaggrDTO.setIdContrattoRaggrupp(
									sirtplcRContrattoRaggruppDTOs.get(0).getIdContrattoRaggrupp());
						}
						sirtplcRSostSogContrRaggrDTO.setIdSoggettoGiuridico(sub.getSoggettoSubentrante().getId());
						sirtplcRSostSogContrRaggrDTO.setAttoSostituzione(sub.getAtto());
						sirtplcRSostSogContrRaggrDTO.setDataSostituzione(sub.getData());
						sirtplcRSostSogContrRaggrDTO.setDataFineSostituzione(sub.getDataFine());
						sirtplcRSostSogContrRaggrDTO.setIdTipoSostituzione(sub.getTipoSostituzione().getId());
						sirtplcRSostSogContrRaggrDAO.insert(sirtplcRSostSogContrRaggrDTO);
						idSubentriSubaffRaggr.add(sirtplcRSostSogContrRaggrDTO.getIdSostSogContrRaggr());
					}
				} else {
					if (sub.getContraenteGroup().equals("EC") || sub.getContraenteGroup().equals("ET")) {
						idSubentriSubaff.add(sub.getId());
					} else if (sub.getContraenteGroup().equals("R")) {
						idSubentriSubaffRaggr.add(sub.getId());
					}
				}
			}

			// cancello i subentri che sono a db e che sono stati cancellati sull
			// applicativo da utenti servizi o regione
			SirtplcRSostSogContrSelector sirtplcRSostSogContrsel = new SirtplcRSostSogContrSelector();
			sirtplcRSostSogContrsel.createCriteria().andIdContrattoEqualTo(sirtplcTContrattoDTO.getIdContratto());
			List<SirtplcRSostSogContrDTO> subentriSubaffDB = sirtplcRSostSogContrDAO
					.selectByExample(sirtplcRSostSogContrsel);
			for (SirtplcRSostSogContrDTO subentroSubaff : subentriSubaffDB) {
				if (!idSubentriSubaff.contains(subentroSubaff.getIdSostSogContr())) {
					sirtplcRSostSogContrDAO.deleteByPrimaryKey(subentroSubaff.getIdSostSogContr());
				}
			}

			SirtplcRContrattoRaggruppSelector sirtplcRContrattoRaggruppSelector = new SirtplcRContrattoRaggruppSelector();
			sirtplcRContrattoRaggruppSelector.createCriteria()
					.andIdContrattoEqualTo(sirtplcTContrattoDTO.getIdContratto());
			List<SirtplcRContrattoRaggruppDTO> sirtplcRContrattoRaggruppDTOs = sirtplcRContrattoRaggruppDAO
					.selectByExample(sirtplcRContrattoRaggruppSelector);
			if (sirtplcRContrattoRaggruppDTOs != null && sirtplcRContrattoRaggruppDTOs.size() > 0) {
				SirtplcRSostSogContrRaggrSelector sirtplcRSostSogContrRaggrSelector = new SirtplcRSostSogContrRaggrSelector();
				sirtplcRSostSogContrRaggrSelector.createCriteria();
				for (SirtplcRContrattoRaggruppDTO sirtplcRContrattoRaggruppDTO : sirtplcRContrattoRaggruppDTOs) {
					sirtplcRSostSogContrRaggrSelector.or()
							.andIdContrattoRaggruppEqualTo(sirtplcRContrattoRaggruppDTO.getIdContrattoRaggrupp());
				}
				List<SirtplcRSostSogContrRaggrDTO> subentriSubaffRaggrDB = sirtplcRSostSogContrRaggrDAO
						.selectByExample(sirtplcRSostSogContrRaggrSelector);
				for (SirtplcRSostSogContrRaggrDTO subentroSubaffRaggDB : subentriSubaffRaggrDB) {
					if (!idSubentriSubaffRaggr.contains(subentroSubaffRaggDB.getIdSostSogContrRaggr())) {
						sirtplcRSostSogContrRaggrDAO.deleteByPrimaryKey(subentroSubaffRaggDB.getIdSostSogContrRaggr());
					}
				}
			}

		}

		if (isUtenteRegioneoServizi || isAMP) {
			sirtplcTContrattoDTO.setIdContrattoPadre(null);
			sirtplcTContrattoDTO.setIdBacino(contrattoRequest.getIdBacino());
			sirtplcTContrattoDTO.setIdTipoAffidamento(contrattoRequest.getIdTipoAffidamento());
			sirtplcTContrattoDTO.setIdModalitaAffidamento(contrattoRequest.getIdModalitaAffidamento());
			sirtplcTContrattoDTO.setAccordoProgramma(
					contrattoRequest.getAccordoProgramma() != null ? contrattoRequest.getAccordoProgramma().trim()
							: null);
			sirtplcTContrattoDTO.setGrossCost(contrattoRequest.getGrossCost());
			sirtplcTContrattoDTO
					.setCig(contrattoRequest.getCIG() != null ? contrattoRequest.getCIG().toUpperCase().trim() : null);
			sirtplcTContrattoDTO.setIdSogGiuridCommittente(contrattoRequest.getIdSogGiuridCommittente());
			sirtplcTContrattoDTO.setIdTipoSogGiuridEsec(contrattoRequest.getIdTipoSogGiuridEsec());
			sirtplcTContrattoDTO.setIdSogGiuridEsecutore(contrattoRequest.getIdSogGiuridEsecutore());
			sirtplcTContrattoDTO.setIdTipoRaggrSogGiuridEsec(contrattoRequest.getIdTipoRaggrSogGiuridEsec());
			sirtplcTContrattoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());

			List<Long> idContrattiRaggr = new ArrayList<>();
			for (ContrattoRaggruppamentoVO contrattoRaggr : contrattoRequest.getContrattoRaggruppamentoVOs()) {
				if (contrattoRaggr.getIdContrattoRaggruppamento() != null)
					idContrattiRaggr.add(contrattoRaggr.getIdContrattoRaggruppamento());
			}

			SirtplcRContrattoRaggruppSelector select = new SirtplcRContrattoRaggruppSelector();
			select.createCriteria().andIdContrattoEqualTo(contrattoRequest.getIdContratto());
			List<SirtplcRContrattoRaggruppDTO> contrattoRaggrDTOlist = sirtplcRContrattoRaggruppDAO
					.selectByExample(select);
			for (SirtplcRContrattoRaggruppDTO contrattoRaggrToCheck : contrattoRaggrDTOlist) {
				if (!idContrattiRaggr.contains(contrattoRaggrToCheck.getIdContrattoRaggrupp())) {
					try {
						sirtplcRContrattoRaggruppDAO.deleteByPrimaryKey(contrattoRaggrToCheck.getIdContrattoRaggrupp());
					} catch (Exception e) {
						throw new ErroreGestitoException(
								"Impossibile eliminare i raggruppamenti che hanno subentri o subaffidamenti", "DR");
					}
				}
			}
			// Se nella combo Tipo soggetto esecutore titolare e selezionata la voce
			// Raggruppamento
			if (contrattoRequest.getIdTipoSogGiuridEsec().equals(Long.valueOf(3))) {
				for (ContrattoRaggruppamentoVO contrattoRaggr : contrattoRequest.getContrattoRaggruppamentoVOs()) {
					if (contrattoRaggr.getIdContrattoRaggruppamento() != null) {
						SirtplcRContrattoRaggruppDTO contrattoRaggrDTO = sirtplcRContrattoRaggruppDAO
								.selectByPrimaryKey(contrattoRaggr.getIdContrattoRaggruppamento());
						contrattoRaggrDTO.setIdContratto(contrattoRequest.getIdContratto());
						contrattoRaggrDTO.setCapofila(contrattoRaggr.getCapofila());
						contrattoRaggrDTO.setIdSoggettoGiuridico(contrattoRaggr.getIdSoggettoGiuridico());
						sirtplcRContrattoRaggruppDAO.updateByPrimaryKeySelective(contrattoRaggrDTO);
					} else {
						SirtplcRContrattoRaggruppDTO sirtplcRContrattoRaggruppDTO = new SirtplcRContrattoRaggruppDTO();
						sirtplcRContrattoRaggruppDTO.setIdContratto(contrattoRequest.getIdContratto());
						sirtplcRContrattoRaggruppDTO.setIdSoggettoGiuridico(contrattoRaggr.getIdSoggettoGiuridico());
						sirtplcRContrattoRaggruppDTO.setCapofila(contrattoRaggr.getCapofila());
						sirtplcRContrattoRaggruppDAO.insert(sirtplcRContrattoRaggruppDTO);
					}
				}
			}
		}

		sirtplcTContrattoDTO.setDataFineValidita(contrattoRequest.getDataFineValidita());
		sirtplcTContrattoDTO.setDescContratto(contrattoRequest.getDescContratto());
		sirtplcTContrattoDTO.setCodIdNazionale(
				contrattoRequest.getCodIdNazionale() != null ? contrattoRequest.getCodIdNazionale().trim().toUpperCase()
						: null);
		sirtplcTContrattoDTO.setDataAggiornamento(new Date());
		sirtplcTContrattoDTO.setNumRepertorio(
				contrattoRequest.getNumRepertorio() != null ? contrattoRequest.getNumRepertorio().toUpperCase().trim()
						: null);
		sirtplcTContrattoDTO.setDataStipula(contrattoRequest.getDataStipula());
		sirtplcTContrattoDTO.setDataInizioValidita(contrattoRequest.getDataInizioValidita());
		SirtplcRContrAmbTipServSelector SirtplcRContrAmbTipServSelector = new SirtplcRContrAmbTipServSelector();
		SirtplcRContrAmbTipServSelector.createCriteria().andIdContrattoEqualTo(sirtplcTContrattoDTO.getIdContratto());
		List<SirtplcRContrAmbTipServKey> sirtplcRContrAmbTipServKeys = sirtplcRContrAmbTipServDAO
				.selectByExample(SirtplcRContrAmbTipServSelector);
		List<Long> ids = new ArrayList<>();
		for (AmbTipServizioVO ambTip : contrattoRequest.getAmbTipServizio()) {
			ids.add(ambTip.getIdAmbTipServizio());
		}
		if (sirtplcRContrAmbTipServKeys != null && sirtplcRContrAmbTipServKeys.size() > 0) {
			for (SirtplcRContrAmbTipServKey k : sirtplcRContrAmbTipServKeys) {
				if (!ids.contains(k.getIdAmbTipServizio())) {
					sirtplcRContrAmbTipServDAO.deleteByPrimaryKey(k);
				} else {
					ids.remove(k.getIdAmbTipServizio());
				}
			}
		}

		for (AmbTipServizioVO ambTip : contrattoRequest.getAmbTipServizio()) {
			if (ids.contains(ambTip.getIdAmbTipServizio())) {
				SirtplcRContrAmbTipServKey sirtplcRContrAmbTipServKey = new SirtplcRContrAmbTipServKey();
				sirtplcRContrAmbTipServKey.setIdContratto(Long.valueOf(sirtplcTContrattoDTO.getIdContratto()));
				sirtplcRContrAmbTipServKey.setIdAmbTipServizio(ambTip.getIdAmbTipServizio());
				sirtplcRContrAmbTipServDAO.insert(sirtplcRContrAmbTipServKey);
			}
		}
		sirtplcTContrattoDAO.updateByPrimaryKey(sirtplcTContrattoDTO);

		// gestione proroghe
		SirtplcTProrogaContrattoSelector sirtplcTProrogaContrattoSelector = new SirtplcTProrogaContrattoSelector();
		sirtplcTProrogaContrattoSelector.createCriteria().andIdContrattoEqualTo(contrattoRequest.getIdContratto());

		List<SirtplcTProrogaContrattoDTO> proroghe = sirtplcTProrogaContrattoDAO
				.selectByExample(sirtplcTProrogaContrattoSelector);

		// controllo su dimensione per eseguire la delete (altrimenti confilitto con
		// insert)
		if (proroghe.size() >= contrattoRequest.getProroghe().size()) {
			// genero list per supporto
			List<Long> idProroghe = new ArrayList<>();
			for (ProrogaVO d : contrattoRequest.getProroghe()) {
				if (d.getIdProroga() != null)
					idProroghe.add(d.getIdProroga());
			}

			for (SirtplcTProrogaContrattoDTO d : proroghe) {
				if (!idProroghe.contains(d.getIdProrogaContratto())) {
					sirtplcTProrogaContrattoDAO.deleteByPrimaryKey(d.getIdProrogaContratto());
				}
			}
		}
		// eseguito sempre perche controllo su getIdProroga = null quindi non genera
		// configlitti
		for (ProrogaVO p : contrattoRequest.getProroghe()) {
			if (p.getIdProroga() == null) {
				p.setIdContratto(contrattoRequest.getIdContratto());
				SirtplcTProrogaContrattoDTO sirtplcTProrogaContrattoDTO = prorogheMapper.mapVOtoDTO(p);
				sirtplcTProrogaContrattoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				sirtplcTProrogaContrattoDTO.setDataAggiornamento(new Date());
				sirtplcTProrogaContrattoDAO.insert(sirtplcTProrogaContrattoDTO);
			}
		}

		SirtplcTContrattoAllegatoSelector sirtplcTProrogaContrattoAllegatoSelector = new SirtplcTContrattoAllegatoSelector();
		sirtplcTProrogaContrattoAllegatoSelector.createCriteria()
				.andIdContrattoEqualTo(contrattoRequest.getIdContratto());

		List<SirtplcTContrattoAllegatoDTO> allegati2 = sirtplcTContrattoAllegatoDAO
				.selectByExample(sirtplcTProrogaContrattoAllegatoSelector);
		List<Long> idAllegati = new ArrayList<>();
		for (AllegatoVO d : contrattoRequest.getAllegati()) {
			if (d.getIdAllegato() != null)
				idAllegati.add(d.getIdAllegato());
		}

		for (SirtplcTContrattoAllegatoDTO d : allegati2) {
			if (!idAllegati.contains(d.getIdContrattoAllegato())) {
				sirtplcTContrattoAllegatoDAO.deleteByPrimaryKey(d.getIdContrattoAllegato());
			}
		}
		// gestione allegato

		for (AllegatoVO file : contrattoRequest.getAllegati()) {
			if (file.getIdAllegato() == null) {
				SirtplcTContrattoAllegatoDTO sirtplcTContrattoAllegatoDTO = new SirtplcTContrattoAllegatoDTO();
				sirtplcTContrattoAllegatoDTO.setIdContratto(sirtplcTContrattoDTO.getIdContratto());
				sirtplcTContrattoAllegatoDTO.setIdTipoDocumento(file.getIdTipoDocumento());
				sirtplcTContrattoAllegatoDTO.setNote(file.getNoteFile());
				sirtplcTContrattoAllegatoDTO.setAllegato(file.getFileByte());
				sirtplcTContrattoAllegatoDTO.setDataAggiornamento(file.getDataCaricamento());
				sirtplcTContrattoAllegatoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				sirtplcTContrattoAllegatoDTO.setNomeFile(file.getNomeFile());
				sirtplcTContrattoAllegatoDAO.insert(sirtplcTContrattoAllegatoDTO);
			}
		}

		return sirtplcTContrattoDTO.getIdContratto();
	}

	private void checkCodiceIdentificativoNazionale(String cod) {

		SirtplcTContrattoSelector sel = new SirtplcTContrattoSelector();
		sel.createCriteria().andCodIdNazionaleEqualTo(cod.toUpperCase());
		List<SirtplcTContrattoDTO> contratti = sirtplcTContrattoDAO.selectByExample(sel);
		if (contratti.size() > 0) {
			throw new ErroreGestitoException("Codice Nazionale esistente. Cambiare il campo codice nazionale", "TFND");
		}

	}

	@Override
	public ContrattoVO dettaglioContratto(long id, String action) {
		if (StringUtils.isEmpty(action) || action.equals(Action.VIEW.value))
			SecurityUtils.assertAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_CONTRATTO);
		else if (action.equals(Action.EDIT.value))
			SecurityUtils.assertMultipleAutorizzazioni(AuthorizationRoles.VISUALIZZA_DETTAGLI_ANAGRAFICA_CONTRATTO,
					AuthorizationRoles.MODIFICA_ANAGRAFICA_CONTRATTO);

		SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO.selectByPrimaryKey(id);
		ContrattoVO contratto = contrattoMapper.mapDTOtoVO(sirtplcTContrattoDTO);

		SirtplcRContrattoRaggruppSelector sirtplcRContrattoRaggruppSelector = new SirtplcRContrattoRaggruppSelector();
		sirtplcRContrattoRaggruppSelector.createCriteria().andIdContrattoEqualTo(id);
		List<SirtplcRContrattoRaggruppDTO> sirtplcRContrattoRaggruppDTOs = sirtplcRContrattoRaggruppDAO
				.selectByExample(sirtplcRContrattoRaggruppSelector);

		List<ContrattoRaggruppamentoVO> contrattoRaggruppamentoVOs = new ArrayList<>();
		if (sirtplcRContrattoRaggruppDTOs != null && sirtplcRContrattoRaggruppDTOs.size() > 0) {
			for (SirtplcRContrattoRaggruppDTO dto : sirtplcRContrattoRaggruppDTOs) {
				contrattoRaggruppamentoVOs.add(contrattoRaggruppamentoMapper.mapDTOtoVO(dto));
			}
		}
		contratto.setContrattoRaggruppamentoVOs(contrattoRaggruppamentoVOs);

		SirtplcRContrAmbTipServSelector sirtplcRContrAmbTipServSelector = new SirtplcRContrAmbTipServSelector();
		sirtplcRContrAmbTipServSelector.createCriteria().andIdContrattoEqualTo(sirtplcTContrattoDTO.getIdContratto());
		List<SirtplcRContrAmbTipServKey> sirtplcRContrAmbTipServKeys = sirtplcRContrAmbTipServDAO
				.selectByExample(sirtplcRContrAmbTipServSelector);
		List<AmbTipServizioVO> ambTipServVOs = new ArrayList<>();
		for (SirtplcRContrAmbTipServKey k : sirtplcRContrAmbTipServKeys) {
			SirtplcRAmbTipServizioDTO sirtplcRAmbTipServizioDTO = sirtplcRAmbTipServizioDAO
					.selectByPrimaryKey(k.getIdAmbTipServizio());
			AmbTipServizioVO ambTipServizioVO = ambTipServizioMapper.mapDTOtoVO(sirtplcRAmbTipServizioDTO);
			ambTipServizioVO.setAmbitoServizio(ambitoServizioMapper.mapDTOtoVO(
					sirtplcDAmbitoServizioDAO.selectByPrimaryKey(sirtplcRAmbTipServizioDTO.getIdAmbitoServizio())));
			ambTipServizioVO.setTipologiaServizio(tipologiaServizioMapper.mapDTOtoVO(sirtplcDTipologiaServizioDAO
					.selectByPrimaryKey(sirtplcRAmbTipServizioDTO.getIdTipologiaServizio())));
			ambTipServVOs.add(ambTipServizioVO);
		}

		contratto.setAmbTipServizio(ambTipServVOs);

		SirtplcTProrogaContrattoSelector sirtplcTProrogaContrattoSelector = new SirtplcTProrogaContrattoSelector();
		sirtplcTProrogaContrattoSelector.createCriteria().andIdContrattoEqualTo(id);
		List<SirtplcTProrogaContrattoDTO> prorogheDTO = sirtplcTProrogaContrattoDAO
				.selectByExample(sirtplcTProrogaContrattoSelector);
		List<ProrogaVO> prorogheVO = new ArrayList<ProrogaVO>();
		for (SirtplcTProrogaContrattoDTO p : prorogheDTO) {
			prorogheVO.add(prorogheMapper.mapDTOtoVO(p));
		}
		Collections.sort(prorogheVO); // la proroga con data maggiore va per
										// prima
		contratto.setProroghe(prorogheVO);

		SirtplcRSostSogContrSelector sirtplcRSostSogContrSelector = new SirtplcRSostSogContrSelector();
		sirtplcRSostSogContrSelector.createCriteria().andIdContrattoEqualTo(sirtplcTContrattoDTO.getIdContratto());
		List<SirtplcRSostSogContrDTO> sirtplcRSostSogContrDTOs = sirtplcRSostSogContrDAO
				.selectByExample(sirtplcRSostSogContrSelector);
		List<SubentroSubaffidamentoVO> subs = new ArrayList<>();
		if (sirtplcRSostSogContrDTOs != null) {
			for (SirtplcRSostSogContrDTO dto : sirtplcRSostSogContrDTOs) {
				subs.add(sostSogContrMapper.mapDTOtoVO(dto));
			}
		}
		if (sirtplcRContrattoRaggruppDTOs != null && sirtplcRContrattoRaggruppDTOs.size() > 0) {
			List<Long> idContrRaggrs = new ArrayList<>();
			for (SirtplcRContrattoRaggruppDTO cr : sirtplcRContrattoRaggruppDTOs) {
				idContrRaggrs.add(cr.getIdContrattoRaggrupp());
			}
			SirtplcRSostSogContrRaggrSelector sirtplcRSostSogContrRaggrSelector = new SirtplcRSostSogContrRaggrSelector();
			sirtplcRSostSogContrRaggrSelector.createCriteria().andIdContrattoRaggruppIn(idContrRaggrs);
			List<SirtplcRSostSogContrRaggrDTO> sirtplcRSostSogContrRaggrDTOs = sirtplcRSostSogContrRaggrDAO
					.selectByExample(sirtplcRSostSogContrRaggrSelector);
			if (sirtplcRSostSogContrRaggrDTOs != null) {
				for (SirtplcRSostSogContrRaggrDTO dto : sirtplcRSostSogContrRaggrDTOs) {
					subs.add(sostSogContrRaggrMapper.mapDTOtoVO(dto));
				}
			}
		}
		Collections.sort(subs, Collections.reverseOrder());
		contratto.setSubentriSubaffidamenti(subs);

		if (action.equals(Action.VIEW.value)) {
			Date dataFiltroSoggettiCoinvolti = null;
			Date today = new Date();
			if (contratto.getProroghe() != null && contratto.getProroghe().size() > 0) {
				dataFiltroSoggettiCoinvolti = contratto.getProroghe().get(0).getDataFineProroga();
			} else {
				dataFiltroSoggettiCoinvolti = contratto.getDataFineValidita();
			}
			VSoggettiCoinvoltiSelector vSoggettiCoinvoltiSelector = new VSoggettiCoinvoltiSelector();
			if (dataFiltroSoggettiCoinvolti != null && dataFiltroSoggettiCoinvolti.before(today)) {
				vSoggettiCoinvoltiSelector.createCriteria().andIdcEqualTo(id)
						.andDFinGreaterThanOrEqualTo(dataFiltroSoggettiCoinvolti)
						.andDIniLessThanOrEqualTo(dataFiltroSoggettiCoinvolti);
				contratto.setDataFiltroSoggetto(dataFiltroSoggettiCoinvolti);
			} else {
				vSoggettiCoinvoltiSelector.createCriteria().andIdcEqualTo(id).andDFinGreaterThanOrEqualTo(today)
						.andDIniLessThanOrEqualTo(today);
				contratto.setDataFiltroSoggetto(today);
			}
			List<VSoggettiCoinvoltiDTO> soggettiCoinvoltiDTO = new ArrayList<>();
			try {
				soggettiCoinvoltiDTO = vSoggettiCoinvoltiDAO.selectByExample(vSoggettiCoinvoltiSelector);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			List<SoggettoCoinvoltoVO> soggettiCoinvoltiTmp = new ArrayList<>();
			for (VSoggettiCoinvoltiDTO soggetto : soggettiCoinvoltiDTO) {
				SoggettoCoinvoltoVO soggettoVO = new SoggettoCoinvoltoVO();
				soggettoVO.setIdContratto(soggetto.getIdc());
				soggettoVO.setAlias(soggetto.getAlias());
				soggettoVO.setRuolo(soggetto.getRuolo());
				soggettoVO.setPerContoDi(soggetto.getPerContoDi());
				soggettoVO.setIdSoggettoGiuridico(soggetto.getIdSg());
				soggettoVO.setDataInizio(soggetto.getdIni());
				soggettoVO.setDataFine(soggetto.getdFin());
				soggettiCoinvoltiTmp.add(soggettoVO);
			}
			contratto.setSoggettiCoinvolti(soggettiCoinvoltiTmp);
		}

		VSoggettiCoinvoltiPeriodiSelector vSoggettiCoinvoltiPeriodiSelector = new VSoggettiCoinvoltiPeriodiSelector();
		vSoggettiCoinvoltiPeriodiSelector.createCriteria().andIdcEqualTo(id);
		List<VSoggettiCoinvoltiPeriodiDTO> soggettiCoinvoltiPeriodiDTO = new ArrayList<>();
		try {
			soggettiCoinvoltiPeriodiDTO = vSoggettiCoinvoltiPeriodiDAO
					.selectByExample(vSoggettiCoinvoltiPeriodiSelector);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<SoggettoCoinvoltoPeriodiVO> soggettiCoinvoltiPeriodiTmp = new ArrayList<>();
		for (VSoggettiCoinvoltiPeriodiDTO soggetto : soggettiCoinvoltiPeriodiDTO) {
			SoggettoCoinvoltoPeriodiVO soggettoPeriodiVO = new SoggettoCoinvoltoPeriodiVO();
			soggettoPeriodiVO.setIdContratto(soggetto.getIdc());
			soggettoPeriodiVO.setAlias(soggetto.getAlias());
			soggettoPeriodiVO.setRuolo(soggetto.getRuolo());
			soggettoPeriodiVO.setDataInizio(soggetto.getdIni());
			soggettoPeriodiVO.setDataFine(soggetto.getdFin());
			soggettoPeriodiVO.setSoggettoAffidante(soggetto.getSgAff());
			soggettoPeriodiVO.setSoggettoSostituito(soggetto.getSgSost());
			soggettoPeriodiVO.setAtto(soggetto.getAtto());
			soggettoPeriodiVO.setScaduto(soggetto.getScaduto());
			soggettoPeriodiVO.setIdSoggettoGiuridico(soggetto.getIdSg());

			soggettiCoinvoltiPeriodiTmp.add(soggettoPeriodiVO);
		}
		contratto.setSoggettiCoinvoltiPeriodi(soggettiCoinvoltiPeriodiTmp);

		SirtplcTContrattoAllegatoSelector sirtplcTContrattoAllegatoSelector = new SirtplcTContrattoAllegatoSelector();
		sirtplcTContrattoAllegatoSelector.createCriteria().andIdContrattoEqualTo(id);
		sirtplcTContrattoAllegatoSelector.setOrderByClause("data_aggiornamento desc");
		List<SirtplcTContrattoAllegatoDTO> allegatiDTO = sirtplcTContrattoAllegatoDAO
				.selectByExample(sirtplcTContrattoAllegatoSelector);
		List<AllegatoVO> allegatiVO = new ArrayList<AllegatoVO>();
		if (allegatiDTO.size() > 0) {
			List<TipoDocumentoVO> tipiDocumento = getTipiDocumento();
			for (SirtplcTContrattoAllegatoDTO allegato : allegatiDTO) {
				AllegatoVO allegatoVO = new AllegatoVO();
				allegatoVO.setIdAllegato(allegato.getIdContrattoAllegato());
				allegatoVO.setIdTipoDocumento(allegato.getIdTipoDocumento());
				for (TipoDocumentoVO tipoDocumento : tipiDocumento) {
					if (tipoDocumento.getId().equals(allegato.getIdTipoDocumento())) {
						allegatoVO.setDescrizioneTipoDocumento(tipoDocumento.getDescrizione());
						break;
					}
				}
				allegatoVO.setNoteFile(allegato.getNote());
				allegatoVO.setDataCaricamento(allegato.getDataAggiornamento());
				allegatoVO.setNomeFile(allegato.getNomeFile());
				allegatiVO.add(allegatoVO);
			}
		}
		contratto.setAllegati(allegatiVO);
		return contratto;
	}

	enum Action {
		EDIT("E"), VIEW("V");

		private String value;

		private Action(String value) {
			this.value = value;
		}
	}

	@Override
	public byte[] getContenutoDocumentoByIdContrattoAndTipo(Long idContratto, Long idDocumento) {
		if (idContratto == null || idDocumento == null)
			throw new InvalidParameterException();

		// recupero da DB
		SirtplcTContrattoAllegatoSelector selector = new SirtplcTContrattoAllegatoSelector();
		selector.createCriteria().andIdContrattoEqualTo(idContratto).andIdContrattoAllegatoEqualTo(idDocumento);

		List<SirtplcTContrattoAllegatoDTO> fileDTO = sirtplcTContrattoAllegatoDAO.selectByExampleWithBLOBs(selector);
		if (fileDTO == null || fileDTO.size() < 1) {
			throw new InvalidParameterException("id documento e tipo non validi");
		}
		return fileDTO.get(0).getAllegato();
	}

	@Override
	public List<TipoSostituzioneVO> getTipiSostituzione() {
		List<SirtplcDTipoSostituzioneDTO> sirtplcDTipoSostituzioneDTOs = sirtplcDTipoSostituzioneDAO
				.selectByExample(new SirtplcDTipoSostituzioneSelector());
		List<TipoSostituzioneVO> tipi = new ArrayList<>();
		if (sirtplcDTipoSostituzioneDTOs != null) {
			for (SirtplcDTipoSostituzioneDTO dto : sirtplcDTipoSostituzioneDTOs) {
				tipi.add(tipoSostituzioneMapper.mapDTOtoVO(dto));
			}
		}
		return tipi;
	}

	@Override
	public List<SoggettoSubentroVO> getSoggettiSubentro(Long idContratto, Long idTipoSoggContraente) {
		ContrattoVO contrattoVO = dettaglioContratto(idContratto, "E");
		SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		List<Long> ids = new ArrayList<>();
		ids.add(contrattoVO.getIdSogGiuridCommittente());
		ids.add(contrattoVO.getIdSogGiuridEsecutore());
		if (contrattoVO.getContrattoRaggruppamentoVOs() != null) {
			for (ContrattoRaggruppamentoVO r : contrattoVO.getContrattoRaggruppamentoVOs()) {
				ids.add(r.getIdSoggettoGiuridico());
			}
		}
		sirtplaTSoggettoGiuridicoSelector.createCriteria().andIdSoggettoGiuridicoNotIn(ids)
				.andIdTipoSogGiuridicoEqualTo(idTipoSoggContraente).andDataCessazioneIsNull();
		sirtplaTSoggettoGiuridicoSelector.or().andIdSoggettoGiuridicoNotIn(ids)
				.andIdTipoSogGiuridicoEqualTo(idTipoSoggContraente).andDataCessazioneGreaterThan(new Date());
		if (idTipoSoggContraente != null) {
			SirtplaDTipoSogGiuridicoSelector sirtplaDTipoSogGiuridicoSelector = new SirtplaDTipoSogGiuridicoSelector();
			sirtplaDTipoSogGiuridicoSelector.createCriteria().andIdRuoloSogGiuridicoEqualTo(idTipoSoggContraente);
			List<SirtplaDTipoSogGiuridicoDTO> tipiSogg = sirtplaDTipoSogGiuridicoDAO
					.selectByExample(sirtplaDTipoSogGiuridicoSelector);
			List<Long> idTipi = new ArrayList<>();
			for (SirtplaDTipoSogGiuridicoDTO tipoSogg : tipiSogg) {
				idTipi.add(tipoSogg.getIdTipoSogGiuridico());
			}
			SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelectorTmp = new SirtplaTSoggettoGiuridicoSelector();
			Criteria criteria = sirtplaTSoggettoGiuridicoSelectorTmp.createCriteria().andIdTipoEnteIn(idTipi);
			sirtplaTSoggettoGiuridicoSelector.getOredCriteria().add(criteria);
		}
		sirtplaTSoggettoGiuridicoSelector.setOrderByClause("denom_soggetto_giuridico");
		List<SirtplaTSoggettoGiuridicoDTO> sirtplaTSoggettoGiuridicoDTOs = sirtplaTSoggettoGiuridicoDAO
				.selectByExample(sirtplaTSoggettoGiuridicoSelector);
		List<SoggettoSubentroVO> soggSub = new ArrayList<>();
		for (SirtplaTSoggettoGiuridicoDTO dto : sirtplaTSoggettoGiuridicoDTOs) {
			soggSub.add(soggettoSubentroMapper.mapDTOtoVO(dto));
		}
		return soggSub;
	}

	public List<SoggettoCoinvoltoVO> filtraSoggettiCoinvolti(Long id, Date filtro) {

		VSoggettiCoinvoltiSelector vSoggettiCoinvoltiSelector = new VSoggettiCoinvoltiSelector();

		vSoggettiCoinvoltiSelector.createCriteria().andIdcEqualTo(id).andDFinGreaterThanOrEqualTo(filtro)
				.andDIniLessThanOrEqualTo(filtro);
		List<VSoggettiCoinvoltiDTO> soggettiCoinvoltiDTO = vSoggettiCoinvoltiDAO
				.selectByExample(vSoggettiCoinvoltiSelector);
		List<SoggettoCoinvoltoVO> soggettiCoinvoltiTmp = new ArrayList<>();
		for (VSoggettiCoinvoltiDTO soggetto : soggettiCoinvoltiDTO) {
			SoggettoCoinvoltoVO soggettoVO = new SoggettoCoinvoltoVO();
			soggettoVO.setIdContratto(soggetto.getIdc());
			soggettoVO.setAlias(soggetto.getAlias());
			soggettoVO.setRuolo(soggetto.getRuolo());
			soggettoVO.setPerContoDi(soggetto.getPerContoDi());
			soggettoVO.setIdSoggettoGiuridico(soggetto.getIdSg());
			soggettoVO.setDataInizio(soggetto.getdIni());
			soggettoVO.setDataFine(soggetto.getdFin());
			soggettiCoinvoltiTmp.add(soggettoVO);
		}
		return soggettiCoinvoltiTmp;

	}

}
