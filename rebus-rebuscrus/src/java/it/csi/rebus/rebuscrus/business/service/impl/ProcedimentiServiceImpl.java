/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.service.MessaggiService;
import it.csi.rebus.rebuscrus.business.service.PDFService;
import it.csi.rebus.rebuscrus.business.service.ProcedimentiService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.common.exception.ModificaNonAbilitataException;
import it.csi.rebus.rebuscrus.integration.dao.RebusDTipoMessaggioDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusRDocVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebusTVariazAutobusDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscDVoceCostoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDMotivazioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDMotorizzazioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDTipoContrattoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDTipoProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDTransizioneAutomaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspRProcContrattoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspRProcDocumentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspRProcVeicoloDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspRVoceCostoVeicoloDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspTIterProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspTProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspTSubProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplRUtenteSogGiuridDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcDTipoRaggruppamentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcRContrattoRaggruppDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcRSostSogContrDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcRSostSogContrRaggrDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcTContrattoDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.CommonDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.VeicoliDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoMessaggioDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusDTipoMessaggioSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRDocVariazAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusTVariazAutobusSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDVoceCostoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDVoceCostoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspCTipoProcTipoDocSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotivazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotivazioneSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotivazioneSelector.Criteria;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotorizzazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotorizzazioneSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDStatoIterDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoContrattoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoProcedimentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTransizioneAutomaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTransizioneAutomaSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcContrattoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcVeicoloKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcVeicoloSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRVoceCostoVeicoloDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRVoceCostoVeicoloSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTIterProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTIterProcedimentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTSubProcedimentoKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTSubProcedimentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplRUtenteSogGiuridDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplRUtenteSogGiuridSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcDTipoRaggruppamentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcRContrattoRaggruppDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcRContrattoRaggruppSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcRSostSogContrDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcRSostSogContrRaggrDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcRSostSogContrRaggrSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcRSostSogContrSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.VeicoliDTO;
import it.csi.rebus.rebuscrus.integration.mapper.AllegatoProcMapper;
import it.csi.rebus.rebuscrus.integration.mapper.AllegatoVeicoloMapper;
import it.csi.rebus.rebuscrus.integration.mapper.ContrattoProcMapper;
import it.csi.rebus.rebuscrus.integration.mapper.IterProcedimentoMapper;
import it.csi.rebus.rebuscrus.integration.mapper.MotivazioneMapper;
import it.csi.rebus.rebuscrus.integration.mapper.MotorizzazioneMapper;
import it.csi.rebus.rebuscrus.integration.mapper.StatoProcedimentoMapper;
import it.csi.rebus.rebuscrus.integration.mapper.SubProcedimentoMapper;
import it.csi.rebus.rebuscrus.integration.mapper.TipoContrattoMapper;
import it.csi.rebus.rebuscrus.integration.mapper.TipoDocumentoMapper;
import it.csi.rebus.rebuscrus.integration.mapper.TipoProcedimentoMapper;
import it.csi.rebus.rebuscrus.integration.mapper.TransizioneAutomaMapper;
import it.csi.rebus.rebuscrus.integration.mapper.VoceDiCostoMapper;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.CodeRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.AllegatoProcVO;
import it.csi.rebus.rebuscrus.vo.AllegatoVeicoloVO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcDatiVO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcVO;
import it.csi.rebus.rebuscrus.vo.DettaglioRichiestaVO;
import it.csi.rebus.rebuscrus.vo.FiltroProcedimentiVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaUsoInLineaVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaVO;
import it.csi.rebus.rebuscrus.vo.IterProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.MessaggioVo;
import it.csi.rebus.rebuscrus.vo.MotivazioneVO;
import it.csi.rebus.rebuscrus.vo.MotorizzazioneVO;
import it.csi.rebus.rebuscrus.vo.ProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.RichiestaPdfVO;
import it.csi.rebus.rebuscrus.vo.SoggettoContrattoVO;
import it.csi.rebus.rebuscrus.vo.StatoProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.TipoContrattoVO;
import it.csi.rebus.rebuscrus.vo.TipoDocumentoVO;
import it.csi.rebus.rebuscrus.vo.TipoProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.TransizioneAutomaVO;
import it.csi.rebus.rebuscrus.vo.UtilizzoVO;
import it.csi.rebus.rebuscrus.vo.VeicoloVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoVeicoloVO;

@Component
public class ProcedimentiServiceImpl implements ProcedimentiService {

	@Autowired
	private SirtplRUtenteSogGiuridDAO sirtplRUtenteSogGiuridDAO;
	@Autowired
	private ProcedimentoDAO procedimentoDAO;
	@Autowired
	private RebuspDTipoProcedimentoDAO rebuspDTipoProcedimentoDAO;
	@Autowired
	private RebuspDMotorizzazioneDAO rebuspDMotorizzazioneDAO;
	@Autowired
	private RebuspDMotivazioneDAO rebuspDMotivazioneDAO;
	@Autowired
	private RebuspTProcedimentoDAO rebuspTProcedimentoDAO;
	@Autowired
	private RebuspTIterProcedimentoDAO rebuspTIterProcedimentoDAO;
	@Autowired
	private RebuspRProcVeicoloDAO rebuspRProcVeicoloDAO;
	@Autowired
	private RebuspRProcContrattoDAO rebuspRProcContrattoDAO;
	@Autowired
	private RebuspRProcDocumentoDAO rebuspRProcDocumentoDAO;
	@Autowired
	private RebusTVariazAutobusDAO rebusTVariazAutobusDAO;
	@Autowired
	private RebusRDocVariazAutobusDAO rebusRDocVariazAutobusDAO;
	@Autowired
	private RebuspDTipoContrattoDAO rebuspDTipoContrattoDAO;
	@Autowired
	private RebusDTipoMessaggioDAO rebusDTipoMessaggioDAO;
	@Autowired
	private RebuspDTransizioneAutomaDAO rebuspDTransizioneAutomaDAO;
	@Autowired
	private MessaggiService messaggiService;
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private RebuspTSubProcedimentoDAO rebuspTSubProcedimentoDAO;
	@Autowired
	private SirtplcTContrattoDAO sirtplcTContrattoDAO;
	@Autowired
	private RebuscDVoceCostoDAO rebuscDVoceCostoDAO;
	@Autowired
	private RebuspRVoceCostoVeicoloDAO rebuspRVoceCostoVeicoloDAO;
	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;
	@Autowired
	private SirtplcRContrattoRaggruppDAO sirtplcRContrattoRaggruppDAO;
	@Autowired
	private SirtplcRSostSogContrDAO sirtplcRSostSogContrDAO;
	@Autowired
	private SirtplcRSostSogContrRaggrDAO sirtplcRSostSogContrRaggrDAO;
	@Autowired
	private SirtplcDTipoRaggruppamentoDAO sirtplcDTipoRaggruppamentoDAO;
	@Autowired
	private TipoProcedimentoMapper tipoProcedimentoMapper;
	@Autowired
	private MotorizzazioneMapper motorizzazioneMapper;
	@Autowired
	private MotivazioneMapper motivazioneMapper;
	@Autowired
	private TipoDocumentoMapper tipoDocumentoMapper;
	@Autowired
	private StatoProcedimentoMapper statoProcedimentoMapper;
	@Autowired
	private AllegatoVeicoloMapper allegatoVeicoloMapper;
	@Autowired
	private ContrattoProcMapper contrattoProcMapper;
	@Autowired
	private AllegatoProcMapper allegatoProcMapper;
	@Autowired
	private IterProcedimentoMapper iterProcedimentoMapper;
	@Autowired
	private TipoContrattoMapper tipoContrattoMapper;
	@Autowired
	private TransizioneAutomaMapper transizioneAutomaMapper;
	@Autowired
	private SubProcedimentoMapper subProcedimentoMapper;
	@Autowired
	private VoceDiCostoMapper voceDiCostoMapper;
	@Autowired
	private PDFService pdfService;
	@Autowired
	private VeicoliDAO veicoliDAO;
	@Autowired
	private ContribuzioneDAO contribuzioneDAO;

	@Override
	public List<ProcedimentoVO> elencoProcedimenti() {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_PROCEDIMENTI);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		FiltroProcedimentiVO filtro = new FiltroProcedimentiVO();
		filtro.setFlagStatoCorrente("S");
		// se ho ruolo azienda
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		// se ho ruolo Regione
		else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_REGIONE.getId())) {
			filtro.setIsRegionePiemonte("S");
		}
		// se ho ruolo servizio

		else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.SERVIZIO.getId())) {
			filtro.setIsServizio("S");
			
		}

		// se ho ruolo AMP o Ente
		else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_ENTE.getId())) {
			filtro.setIdEnte(userInfo.getIdEnte());

		} else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AMP.getId())) {
			filtro.setIsAMP("S");
		}

		List<ProcedimentoVO> procedimenti = procedimentoDAO.getProcedimenti(filtro);

		for (ProcedimentoVO p : procedimenti) {
			setConditionVisualizzazioneProcedimenti(p);
		}
		return procedimenti;

	}

	@Override
	public List<ProcedimentoVO> filtraElencoProcedimenti(FiltroProcedimentiVO filtro) {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_PROCEDIMENTI);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		filtro.prepareSQL();
		// se ho ruolo azienda
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			filtro.setIdAzienda(userInfo.getIdAzienda());
		}
		// se ho ruolo Regione
		else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_REGIONE.getId())) {
			filtro.setIdStatoIter(Long.valueOf(60));
			filtro.setIsRegionePiemonte("S");
		}
		// se ho ruolo servizio

		else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.SERVIZIO.getId())) {
			filtro.setIsServizio("S");

		}
		// se ho ruolo AMP o Ente
		else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_ENTE.getId())) {
			filtro.setIdEnte(userInfo.getIdEnte());

		} else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AMP.getId())) {
			filtro.setIsAMP("S");
		}

		List<ProcedimentoVO> procedimenti = procedimentoDAO.getProcedimenti(filtro);

		for (ProcedimentoVO p : procedimenti) {
			setConditionVisualizzazioneProcedimenti(p);
		}

		return procedimenti;
	}

	private void setGestoreContratto(ProcedimentoVO p) {
		Long contratto = Long.valueOf(-1);
		SirtplaTSoggettoGiuridicoDTO gestore = new SirtplaTSoggettoGiuridicoDTO();
		// ContrattoProcDatiVO datiContratto;
		RebuspTSubProcedimentoSelector rebuspTSubProcedimentoSelector = new RebuspTSubProcedimentoSelector();
		rebuspTSubProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(p.getIdProcedimento());
		List<RebuspTSubProcedimentoKey> sub = rebuspTSubProcedimentoDAO.selectByExample(rebuspTSubProcedimentoSelector);
		if (sub.isEmpty()) {
			RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
			rebuspRProcContrattoSelector.createCriteria().andIdProcedimentoEqualTo(p.getIdProcedimento());
			List<RebuspRProcContrattoDTO> procContratto = rebuspRProcContrattoDAO
					.selectByExample(rebuspRProcContrattoSelector);
			if (!procContratto.isEmpty()) {
				contratto = procContratto.get(0).getIdContratto();
				gestore = procedimentoDAO.getGestoreContrattoProcedimento(contratto, p.getIdProcedimento());			}

		} else {
			RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
			rebuspRProcContrattoSelector.createCriteria().andIdProcedimentoEqualTo(sub.get(0).getIdSubProcedimento1());
			contratto = rebuspRProcContrattoDAO.selectByExample(rebuspRProcContrattoSelector).get(0).getIdContratto();
			gestore = procedimentoDAO.getGestoreContrattoProcedimentoSostituzione(contratto, p.getIdProcedimento());
		}
		if (gestore != null) {
			p.setGestoreContratto(gestore.getDenominazioneBreve());
		} else {
			p.setGestoreContratto("");
		}
	}

	@Override
	public TipoProcedimentoVO getTipoProcedimento(Long id) {
		RebuspDTipoProcedimentoDTO rebuspDTipoProcedimentoDTO = rebuspDTipoProcedimentoDAO.selectByPrimaryKey(id);
		return tipoProcedimentoMapper.mapDTOtoVO(rebuspDTipoProcedimentoDTO);
	}

	@Override
	public TipoProcedimentoVO getTipoProcedimentoByIdProc(Long id) {
		RebuspTProcedimentoDTO rebuspTProcedimentoDTO = rebuspTProcedimentoDAO.selectByPrimaryKey(id);
		RebuspDTipoProcedimentoDTO rebuspDTipoProcedimentoDTO = rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(rebuspTProcedimentoDTO.getIdTipoProcedimento());
		return tipoProcedimentoMapper.mapDTOtoVO(rebuspDTipoProcedimentoDTO);
	}

	@Override
	public List<MotorizzazioneVO> getMotorizzazioni() {
		RebuspDMotorizzazioneSelector rebuspDMotorizzazioneSelector = new RebuspDMotorizzazioneSelector();
		rebuspDMotorizzazioneSelector.setOrderByClause("desc_motorizzazione");
		List<RebuspDMotorizzazioneDTO> rebuspDMotorizzazioneDTOs = rebuspDMotorizzazioneDAO
				.selectByExample(rebuspDMotorizzazioneSelector);
		List<MotorizzazioneVO> motorizzazioneVOs = new ArrayList<>();
		for (RebuspDMotorizzazioneDTO dto : rebuspDMotorizzazioneDTOs) {
			motorizzazioneVOs.add(motorizzazioneMapper.mapDTOtoVO(dto));
		}
		return motorizzazioneVOs;
	}

	@Override
	public List<MotivazioneVO> getMotivazioni(Long idTipoProcedimento, boolean hasDataFilter) {
		Date now = new Date();
		RebuspDMotivazioneSelector rebuspDMotivazioneSelector = new RebuspDMotivazioneSelector();
		Criteria criteria = rebuspDMotivazioneSelector.createCriteria();
		criteria.andIdTipoProcedimentoEqualTo(idTipoProcedimento);
		if (hasDataFilter) {
			criteria.andDataFineValiditaGreaterThanOrEqualTo(now).andDataInizioValiditaLessThanOrEqualTo(now);
			rebuspDMotivazioneSelector.or().andIdTipoProcedimentoEqualTo(idTipoProcedimento).andDataFineValiditaIsNull()
					.andDataInizioValiditaLessThanOrEqualTo(now);
		}
		rebuspDMotivazioneSelector.setOrderByClause("id_motivazione");
		List<RebuspDMotivazioneDTO> rebuspDMotivazioneDTOs = rebuspDMotivazioneDAO
				.selectByExample(rebuspDMotivazioneSelector);
		List<MotivazioneVO> motivazioneVOs = new ArrayList<>();
		for (RebuspDMotivazioneDTO dto : rebuspDMotivazioneDTOs) {
			motivazioneVOs.add(motivazioneMapper.mapDTOtoVO(dto));
		}
		return motivazioneVOs;
	}

	@Override
	public List<ContrattoProcVO> getContrattiInserisci() {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		return ricercaContratti(userInfo.getIdAzienda(), null);
	}

	@Override
	public List<ContrattoProcVO> getContrattiModifica(Long idProcedimento) {
		RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
		rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento)
				.andIdStatoIterEqualTo(new Long(10)); // BOZZA
		rebuspTIterProcedimentoSelector.setOrderByClause("data_inizio_validita");
		List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
				.selectByExample(rebuspTIterProcedimentoSelector);
		RebuspTIterProcedimentoDTO iterDto = null;
		if (rebuspTIterProcedimentoDTOs != null && rebuspTIterProcedimentoDTOs.size() > 0) {
			iterDto = rebuspTIterProcedimentoDTOs.get(0);
		}

		SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDAO
				.selectByPrimaryKey(iterDto.getIdUtenteSogGiurid());
		Long idAzienda = null;
		if (sirtplRUtenteSogGiuridDTO != null) {
			idAzienda = sirtplRUtenteSogGiuridDTO.getIdSoggettoGiuridico();
		}

		return ricercaContratti(idAzienda, idProcedimento);
	}

	private List<ContrattoProcVO> ricercaContratti(Long idAzienda, Long idProcedimento) {
		List<ContrattoProcVO> contrattoProcVOs = procedimentoDAO.getContratti(idAzienda, idProcedimento);
		return contrattoProcVOs;
	}

	@Override
	public List<TipoDocumentoVO> getTipiDocumento(Long idTipoProcedimento) {
		RebuspCTipoProcTipoDocSelector rebuspCTipoProcTipoDocSelector = new RebuspCTipoProcTipoDocSelector();
		rebuspCTipoProcTipoDocSelector.createCriteria().andIdTipoDocumentoEqualTo(idTipoProcedimento);
		List<RebusDTipoDocumentoDTO> tipi = procedimentoDAO.getTipiDocumento(idTipoProcedimento);
		List<TipoDocumentoVO> tipiDocumentoVO = new ArrayList<>();
		for (RebusDTipoDocumentoDTO t : tipi) {
			tipiDocumentoVO.add(tipoDocumentoMapper.mapDTOtoVO(t));
		}
		return tipiDocumentoVO;
	}

	@Transactional
	@Override
	public Long inserisciRichiesta(InserisciRichiestaVO richiestaRequest) throws ErroreGestitoException {
		if (richiestaRequest == null) {
			throw new InvalidParameterException("Richiesta non valorizzata");
		}
		for (AllegatoProcVO f : richiestaRequest.getFiles()) {
			if (!f.getNomeFile().toLowerCase().contains(".pdf")) {
				throw new ErroreGestitoException(
						"Formato file del documento allegato non consentito! Formato consentiti: PDF.", "TFNC");
			}
		}

		Date now = new Date();

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		SirtplRUtenteSogGiuridSelector sirtplRUtenteSogGiuridSelector = new SirtplRUtenteSogGiuridSelector();
		Long idSoggettoGiuridico = userInfo.getIdAzienda() != null ? userInfo.getIdAzienda() : userInfo.getIdEnte();
		sirtplRUtenteSogGiuridSelector.createCriteria().andIdUtenteEqualTo(userInfo.getIdUtente())
				.andIdSoggettoGiuridicoEqualTo(idSoggettoGiuridico);
		List<SirtplRUtenteSogGiuridDTO> sirtplRUtenteSogGiuridDTOs = sirtplRUtenteSogGiuridDAO
				.selectByExample(sirtplRUtenteSogGiuridSelector);
		SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = new SirtplRUtenteSogGiuridDTO();
		if (sirtplRUtenteSogGiuridDTOs != null && sirtplRUtenteSogGiuridDTOs.size() > 0) {
			sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDTOs.get(0);
			if (sirtplRUtenteSogGiuridDTOs.size() > 1) {
				for (SirtplRUtenteSogGiuridDTO dto : sirtplRUtenteSogGiuridDTOs) {
					if (dto.getDataInizioValidita().after(sirtplRUtenteSogGiuridDTO.getDataInizioValidita())
							&& dto.getDataInizioValidita().before(now)) {
						sirtplRUtenteSogGiuridDTO = dto;
					}
				}
			}
		}
		RebuspTProcedimentoDTO rebuspTProcedimentoDTO = new RebuspTProcedimentoDTO();
		rebuspTProcedimentoDTO.setIdTipoProcedimento(richiestaRequest.getTipoProcedimento().getId());
		rebuspTProcedimentoDTO.setIdMotorizzazione(richiestaRequest.getMotorizzazione().getId());
		rebuspTProcedimentoDTO.setIdMotivazione(richiestaRequest.getMotivazione().getId());
		rebuspTProcedimentoDTO.setTestoMotivAltro(richiestaRequest.getNoteMotivazione());
		rebuspTProcedimentoDTO.setRuoloFirma(richiestaRequest.getRuoloFirma());
		rebuspTProcedimentoDTO.setNominativoFirma(richiestaRequest.getNominativoFirma());
		rebuspTProcedimentoDTO.setNote(richiestaRequest.getNota());
		rebuspTProcedimentoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
		rebuspTProcedimentoDTO.setDataAggiornamento(now);
		rebuspTProcedimentoDTO.setFlgFirmaDigitale(richiestaRequest.getFlgFirmaDigitale());
		rebuspTProcedimentoDTO.setNominativoFirmaEnte(richiestaRequest.getNominativoFirmaEnte());
		// controllare
		rebuspTProcedimentoDTO.setRuoloFirmaEnte(richiestaRequest.getRuoloFirmaEnte());
		rebuspTProcedimentoDTO.setPremesse(richiestaRequest.getPremesse());
		rebuspTProcedimentoDTO.setPrescrizioni(richiestaRequest.getPrescrizioni());
		rebuspTProcedimentoDTO.setFlgAllegaLinee(richiestaRequest.getFlgAllegaLinea()); 
		try {
			rebuspTProcedimentoDTO
					.setNumProcedimento(getNumProcedimento(richiestaRequest.getTipoProcedimento().getId()));
		} catch (Exception e) {
			throw new ErroreGestitoException(e.getMessage());
		}
		rebuspTProcedimentoDAO.insert(rebuspTProcedimentoDTO);

		RebuspTIterProcedimentoDTO rebuspTIterProcedimentoDTO = new RebuspTIterProcedimentoDTO();
		rebuspTIterProcedimentoDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
		rebuspTIterProcedimentoDTO.setIdStatoIter(Constants.ID_STATO_ITER_PROC_IN_BOZZA);
		rebuspTIterProcedimentoDTO.setDataInizioValidita(now);
		rebuspTIterProcedimentoDTO.setIdUtenteSogGiurid(sirtplRUtenteSogGiuridDTO.getIdUtenteSogGiurid());

		rebuspTIterProcedimentoDAO.insert(rebuspTIterProcedimentoDTO);
		if (richiestaRequest.getVeicoli() != null) {
			for (VeicoloVO v : richiestaRequest.getVeicoli()) {

				// controllo per evitare che in una richiesta di Uso in LInea,
				// Reimmatricolazione,Sostituzione,Alienazione
				// venga indicato come titolare su un contratto un veicolo che e
				// gia stato indicato
				// come titolare su un contratto in un'altra richiesta
				RebuspRProcVeicoloDTO rebuspRProcVeicoloDTO = new RebuspRProcVeicoloDTO();
				rebuspRProcVeicoloDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
				rebuspRProcVeicoloDTO.setPrimoTelaio(v.getPrimoTelaio());
				rebuspRProcVeicoloDTO.setDataAggiornamento(now);
				rebuspRProcVeicoloDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				rebuspRProcVeicoloDAO.insert(rebuspRProcVeicoloDTO);
			}
		}
		if (rebuspTProcedimentoDTO.getIdTipoProcedimento() != 7) {
			RebuspRProcContrattoDTO rebuspRProcContrattoDTO = new RebuspRProcContrattoDTO();
			rebuspRProcContrattoDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
			rebuspRProcContrattoDTO.setIdContratto(richiestaRequest.getContratto().getIdContratto());
			rebuspRProcContrattoDTO.setDataAggiornamento(now);
			rebuspRProcContrattoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
			rebuspRProcContrattoDAO.insert(rebuspRProcContrattoDTO);
		}
		if (richiestaRequest.getFiles() != null) {
			for (AllegatoProcVO a : richiestaRequest.getFiles()) {
				RebuspRProcDocumentoDTO rebuspRProcDocumentoDTO = new RebuspRProcDocumentoDTO();
				rebuspRProcDocumentoDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
				rebuspRProcDocumentoDTO.setIdTipoDocumento(a.getTipoDocumento().getId());
				rebuspRProcDocumentoDTO.setDocumento(a.getFile());
				rebuspRProcDocumentoDTO.setNomeFile(a.getNomeFile());
				rebuspRProcDocumentoDTO.setDataCaricamento(a.getDataCaricamento());
				rebuspRProcDocumentoDTO.setNote(a.getNote());
				rebuspRProcDocumentoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				rebuspRProcDocumentoDAO.insert(rebuspRProcDocumentoDTO);
			}
		}
		if (richiestaRequest.getTipoProcedimento().getId().equals(new Long(7))) { // CONTRIBUZIONE
			for (VoceDiCostoVO v : richiestaRequest.getVociDiCosto()) {
				RebuspRVoceCostoVeicoloDTO rebuspRVoceCostoVeicoloDTO = new RebuspRVoceCostoVeicoloDTO();
				rebuspRVoceCostoVeicoloDTO.setIdVoceCosto(v.getId());
				rebuspRVoceCostoVeicoloDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
				rebuspRVoceCostoVeicoloDTO.setPrimoTelaio(richiestaRequest.getVeicoli().get(0).getPrimoTelaio());
				rebuspRVoceCostoVeicoloDTO.setImporto(v.getImporto());
				rebuspRVoceCostoVeicoloDAO.insert(rebuspRVoceCostoVeicoloDTO);
			}
		}
		return rebuspTProcedimentoDTO.getIdProcedimento();
	}

	@Override
	public Long inserisciRichiestaUsoInLinea(InserisciRichiestaUsoInLineaVO richiestaRequest)
			throws ErroreGestitoException {
		if (richiestaRequest == null) {
			throw new InvalidParameterException("Richiesta non valorizzata");
		}
		for (AllegatoProcVO f : richiestaRequest.getFiles()) {
			if (!f.getNomeFile().toLowerCase().contains(".pdf")) {
				throw new ErroreGestitoException(
						"Formato file del documento allegato non consentito! Formato consentiti: PDF.", "TFNC");
			}
		}

		List<Long> idTipiContratto = new ArrayList<>();
		for (UtilizzoVO utilizzo : richiestaRequest.getUtilizzi()) {
			idTipiContratto.add(utilizzo.getTipoContratto().getId());
		}

		try {
			this.checkVeicoliContrattiUsoInLinea(richiestaRequest.getVeicoli(), idTipiContratto);
		} catch (Exception e) {
			throw new ErroreGestitoException(e.getMessage(), "UIL");
		}

		Date now = new Date();

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		SirtplRUtenteSogGiuridSelector sirtplRUtenteSogGiuridSelector = new SirtplRUtenteSogGiuridSelector();
		Long idSoggettoGiuridico = userInfo.getIdAzienda() != null ? userInfo.getIdAzienda() : userInfo.getIdEnte();
		sirtplRUtenteSogGiuridSelector.createCriteria().andIdUtenteEqualTo(userInfo.getIdUtente())
				.andIdSoggettoGiuridicoEqualTo(idSoggettoGiuridico);
		List<SirtplRUtenteSogGiuridDTO> sirtplRUtenteSogGiuridDTOs = sirtplRUtenteSogGiuridDAO
				.selectByExample(sirtplRUtenteSogGiuridSelector);
		SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = new SirtplRUtenteSogGiuridDTO();
		if (sirtplRUtenteSogGiuridDTOs != null && sirtplRUtenteSogGiuridDTOs.size() > 0) {
			sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDTOs.get(0);
			if (sirtplRUtenteSogGiuridDTOs.size() > 1) {
				for (SirtplRUtenteSogGiuridDTO dto : sirtplRUtenteSogGiuridDTOs) {
					if (dto.getDataInizioValidita().after(sirtplRUtenteSogGiuridDTO.getDataInizioValidita())
							&& dto.getDataInizioValidita().before(now)) {
						sirtplRUtenteSogGiuridDTO = dto;
					}
				}
			}
		}
		RebuspTProcedimentoDTO rebuspTProcedimentoDTO = new RebuspTProcedimentoDTO();
		rebuspTProcedimentoDTO.setIdTipoProcedimento(richiestaRequest.getTipoProcedimento().getId());
		rebuspTProcedimentoDTO.setRuoloFirma(richiestaRequest.getRuoloFirma());
		rebuspTProcedimentoDTO.setNominativoFirma(richiestaRequest.getNominativoFirma());
		rebuspTProcedimentoDTO.setNote(richiestaRequest.getNota());
		rebuspTProcedimentoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
		rebuspTProcedimentoDTO.setDataAggiornamento(now);
		rebuspTProcedimentoDTO.setNominativoFirmaEnte(richiestaRequest.getNominativoFirmaEnte());
		rebuspTProcedimentoDTO.setRuoloFirmaEnte(richiestaRequest.getRuoloFirmaEnte());
		rebuspTProcedimentoDTO.setFlgFirmaDigitale(richiestaRequest.getFlgFirmaDigitale());

		// controllare
		rebuspTProcedimentoDTO.setPremesse(richiestaRequest.getPremesse());
		rebuspTProcedimentoDTO.setPrescrizioni(richiestaRequest.getPrescrizioni());

		try {
			rebuspTProcedimentoDTO
					.setNumProcedimento(getNumProcedimento(richiestaRequest.getTipoProcedimento().getId()));
		} catch (Exception e) {
			throw new ErroreGestitoException(e.getMessage());
		}
		rebuspTProcedimentoDAO.insert(rebuspTProcedimentoDTO);

		RebuspTIterProcedimentoDTO rebuspTIterProcedimentoDTO = new RebuspTIterProcedimentoDTO();
		rebuspTIterProcedimentoDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
		rebuspTIterProcedimentoDTO.setIdStatoIter(Constants.ID_STATO_ITER_PROC_IN_BOZZA);
		rebuspTIterProcedimentoDTO.setDataInizioValidita(now);
		rebuspTIterProcedimentoDTO.setIdUtenteSogGiurid(sirtplRUtenteSogGiuridDTO.getIdUtenteSogGiurid());

		rebuspTIterProcedimentoDAO.insert(rebuspTIterProcedimentoDTO);
		if (richiestaRequest.getVeicoli() != null) {
			for (VeicoloVO v : richiestaRequest.getVeicoli()) {

				// controllo per evitare che in una richiesta di Uso in LInea,
				// Reimmatricolazione,Sostituzione,Alienazione
				// venga indicato come titolare su un contratto un veicolo che e
				// gia stato indicato
				// come titolare su un contratto in un'altra richiesta
				RebuspRProcVeicoloDTO rebuspRProcVeicoloDTO = new RebuspRProcVeicoloDTO();
				rebuspRProcVeicoloDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
				rebuspRProcVeicoloDTO.setPrimoTelaio(v.getPrimoTelaio());
				rebuspRProcVeicoloDTO.setDataAggiornamento(now);
				rebuspRProcVeicoloDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				rebuspRProcVeicoloDAO.insert(rebuspRProcVeicoloDTO);
			}
		}

		// getUtilizzi: restituisce dati Ruoli
		for (UtilizzoVO utilizzo : richiestaRequest.getUtilizzi()) {
			RebuspRProcContrattoDTO rebuspRProcContrattoDTO = new RebuspRProcContrattoDTO();
			rebuspRProcContrattoDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
			rebuspRProcContrattoDTO.setIdContratto(utilizzo.getContratto().getIdContratto());
			rebuspRProcContrattoDTO.setIdTipoContratto(utilizzo.getTipoContratto().getId());
			rebuspRProcContrattoDTO.setDataAggiornamento(now);
			rebuspRProcContrattoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
			rebuspRProcContrattoDAO.insert(rebuspRProcContrattoDTO);
		}

		if (richiestaRequest.getFiles() != null) {
			for (AllegatoProcVO a : richiestaRequest.getFiles()) {
				RebuspRProcDocumentoDTO rebuspRProcDocumentoDTO = new RebuspRProcDocumentoDTO();
				rebuspRProcDocumentoDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
				rebuspRProcDocumentoDTO.setIdTipoDocumento(a.getTipoDocumento().getId());
				rebuspRProcDocumentoDTO.setDocumento(a.getFile());
				rebuspRProcDocumentoDTO.setNomeFile(a.getNomeFile());
				rebuspRProcDocumentoDTO.setDataCaricamento(a.getDataCaricamento());
				rebuspRProcDocumentoDTO.setNote(a.getNote());
				rebuspRProcDocumentoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				rebuspRProcDocumentoDAO.insert(rebuspRProcDocumentoDTO);
			}
		}

		return rebuspTProcedimentoDTO.getIdProcedimento();

	}

	@Override
	public List<StatoProcedimentoVO> elencoStato() {
		List<RebuspDStatoIterDTO> stati = procedimentoDAO.getElencoStatiRichieste();
		List<StatoProcedimentoVO> statiVO = new ArrayList<>();
		for (RebuspDStatoIterDTO s : stati) {
			StatoProcedimentoVO stato = statoProcedimentoMapper.mapDTOtoVO(s);
			statiVO.add(stato);
		}
		return statiVO;
	}

	@Override
	public List<TipoProcedimentoVO> elencoTipologia() {

		RebuspDTipoProcedimentoSelector rebuspDTipoProcedimentoSelector = new RebuspDTipoProcedimentoSelector();
		rebuspDTipoProcedimentoSelector.createCriteria();
		rebuspDTipoProcedimentoSelector.setOrderByClause("id_tipo_procedimento");
		List<RebuspDTipoProcedimentoDTO> tipologie = rebuspDTipoProcedimentoDAO
				.selectByExample(rebuspDTipoProcedimentoSelector);
		List<TipoProcedimentoVO> tipologieVO = new ArrayList<>();
		for (RebuspDTipoProcedimentoDTO t : tipologie) {
			TipoProcedimentoVO tipologia = tipoProcedimentoMapper.mapDTOtoVO(t);
			tipologieVO.add(tipologia);
		}
		return tipologieVO;
	}

	enum Action {
		EDIT("E"), VIEW("V");

		private String value;

		private Action(String value) {
			this.value = value;
		}
	}

	@Override
	public DettaglioRichiestaVO dettaglioRichiesta(long id, String action) throws Exception {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		Date dataInizioProcedimento = null;
		Calendar calendar = Calendar.getInstance();
		DettaglioRichiestaVO richiesta = new DettaglioRichiestaVO();
		RebuspTProcedimentoDTO rebuspTProcedimentoDTO = rebuspTProcedimentoDAO.selectByPrimaryKey(id);
		if (StringUtils.isEmpty(action) || action.equals(Action.VIEW))
			SecurityUtils.assertAutorizzazioni(AuthorizationRoles.DETTAGLIO_PROCEDIMENTO);
		else if (action.equals(Action.EDIT.value) && rebuspTProcedimentoDTO.getIdTipoProcedimento() != Long.valueOf(7))
			SecurityUtils.assertMultipleAutorizzazioni(AuthorizationRoles.DETTAGLIO_PROCEDIMENTO,
					AuthorizationRoles.MODIFICA_PROCEDIMENTO);
		else if (action.equals(Action.EDIT.value)
				&& rebuspTProcedimentoDTO.getIdTipoProcedimento() == Long.valueOf(7)) {
			SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_DETTAGLI_CONTRIBUZIONE);
		}
		richiesta.setId(id);
		richiesta.setNota(rebuspTProcedimentoDTO.getNote());
		richiesta.setIdMotivazione(rebuspTProcedimentoDTO.getIdMotivazione());
		richiesta.setIdMotorizzazione(rebuspTProcedimentoDTO.getIdMotorizzazione());
		richiesta.setIdTipoProcedimento(rebuspTProcedimentoDTO.getIdTipoProcedimento());
		richiesta.setNoteMotivazione(rebuspTProcedimentoDTO.getTestoMotivAltro());
		richiesta.setRuoloFirma(rebuspTProcedimentoDTO.getRuoloFirma());
		richiesta.setNominativoFirma(rebuspTProcedimentoDTO.getNominativoFirma());
		richiesta.setFlgFirmaDigitaleEnte(rebuspTProcedimentoDTO.getFlgFirmaDigitaleEnte());
		richiesta.setFlgFirmaDigitale(rebuspTProcedimentoDTO.getFlgFirmaDigitale());
		richiesta.setRuoloFirmaEnte(rebuspTProcedimentoDTO.getRuoloFirmaEnte());
		richiesta.setNominativoFirmaEnte(rebuspTProcedimentoDTO.getNominativoFirmaEnte());
		richiesta.setPremesse(rebuspTProcedimentoDTO.getPremesse());
		richiesta.setPrescrizioni(rebuspTProcedimentoDTO.getPrescrizioni());
		richiesta.setIsACaricoEnteVisible(Boolean.FALSE);

		richiesta.setFlgAllegaLinea(rebuspTProcedimentoDTO.getFlgAllegaLinee()); 
		
		if (richiesta.getIdTipoProcedimento() != 2) { // NON SOSTITUZIONE

			List<VeicoloVO> veicoloVOs = new ArrayList<>();

			List<VeicoliDTO> veicoli = veicoliDAO.getViewVeicoli(id);
			for (VeicoliDTO dto : veicoli) {
				VeicoloVO v = new VeicoloVO();
				v.setIdProcedimento(dto.getIdP());
				v.setPrimoTelaio(dto.getTelaio());
				v.setnTarga(dto.getTarga());
				v.setIdVariazAutobus(dto.getIdVa());
				v.setDescClasseAmbEuro(dto.getClasse());
				v.setDescTipoAllestimento(dto.getAllestimento());
				v.setLunghezza(dto.getLunghezza());
				v.setDataUltimaImmatricolazione(dto.getDataUltimaImmatricolazione());
				v.setDocumento(dto.getDocumento());
				v.setIdTipoDoc(dto.getIdTipoDoc());
				v.setMarca(dto.getMarca());
				v.setModello(dto.getModello());
				v.setDataPrimaImmatricolazione(dto.getDataPrimaImmatricolazione());
				// }
				RebusRDocVariazAutobusSelector rebusRDocVariazAutobusSelector = new RebusRDocVariazAutobusSelector();
				rebusRDocVariazAutobusSelector.createCriteria().andIdVariazAutobusEqualTo(dto.getIdVa());
				List<RebusRDocVariazAutobusDTO> rebusRDocVariazAutobusDTOs = rebusRDocVariazAutobusDAO
						.selectByExampleWithBLOBs(rebusRDocVariazAutobusSelector);
				List<AllegatoVeicoloVO> allegati = new ArrayList<>();
				for (RebusRDocVariazAutobusDTO dto2 : rebusRDocVariazAutobusDTOs) {
					allegati.add(allegatoVeicoloMapper.mapDTOtoVO(dto2));
				}
				v.setAllegati(allegati);
				veicoloVOs.add(v);
			}
			richiesta.setVeicoli(veicoloVOs);
			if (richiesta.getIdTipoProcedimento() != 5) {
				RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
				rebuspRProcContrattoSelector.createCriteria().andIdProcedimentoEqualTo(id);
				List<RebuspRProcContrattoDTO> rebuspRProcContrattoDTOs = rebuspRProcContrattoDAO
						.selectByExample(rebuspRProcContrattoSelector);
				if (rebuspRProcContrattoDTOs != null && rebuspRProcContrattoDTOs.size() == 1) {
					richiesta.setContratto(contrattoProcMapper.mapDTOtoVO(rebuspRProcContrattoDTOs.get(0)));
					if (action.equals(Action.EDIT.value)) {
						SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO
								.selectByPrimaryKey(rebuspRProcContrattoDTOs.get(0).getIdContratto());
						if (userInfo.getIdEnte() != null
								&& userInfo.getIdEnte().equals(sirtplcTContrattoDTO.getIdSogGiuridCommittente())) {
							richiesta.setIsACaricoEnteVisible(Boolean.TRUE);
						}
					}

				}
			} else {
				RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
				rebuspRProcContrattoSelector.createCriteria().andIdProcedimentoEqualTo(id);
				List<RebuspRProcContrattoDTO> rebuspRProcContrattoDTOs = rebuspRProcContrattoDAO
						.selectByExample(rebuspRProcContrattoSelector);
				List<ContrattoProcVO> contrattiTmp = new ArrayList<ContrattoProcVO>();
				for (RebuspRProcContrattoDTO rebuspRProcContrattoDTO : rebuspRProcContrattoDTOs) {
					ContrattoProcVO contrattoProcVO = contrattoProcMapper.mapDTOtoVO(rebuspRProcContrattoDTO);
					contrattiTmp.add(contrattoProcVO);
					if (action.equals(Action.EDIT.value)) {
						SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO
								.selectByPrimaryKey(rebuspRProcContrattoDTOs.get(0).getIdContratto());
						if (userInfo.getIdEnte() != null
								&& userInfo.getIdEnte().equals(sirtplcTContrattoDTO.getIdSogGiuridCommittente())
								&& !richiesta.getIsACaricoEnteVisible()) {
							richiesta.setIsACaricoEnteVisible(Boolean.TRUE);
						}
					}
				}
				richiesta.setContratti(contrattiTmp);
			}
		}
		RebuspRProcDocumentoSelector rebuspRProcDocumentoSelector = new RebuspRProcDocumentoSelector();
		rebuspRProcDocumentoSelector.createCriteria().andIdProcedimentoEqualTo(id);
		List<RebuspRProcDocumentoDTO> rebuspRProcDocumentoDTOs = rebuspRProcDocumentoDAO
				.selectByExample(rebuspRProcDocumentoSelector);
		List<AllegatoProcVO> allegati = new ArrayList<>();
		if (rebuspRProcDocumentoDTOs != null && rebuspRProcDocumentoDTOs.size() > 0) {
			for (RebuspRProcDocumentoDTO dto : rebuspRProcDocumentoDTOs) {
				allegati.add(allegatoProcMapper.mapDTOtoVO(dto));
			}
		}
		richiesta.setFiles(allegati);

		RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
		rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(id);
		rebuspTIterProcedimentoSelector.setOrderByClause("data_inizio_validita desc");
		List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
				.selectByExample(rebuspTIterProcedimentoSelector);
		List<IterProcedimentoVO> iterProcedimentoVOs = new ArrayList<>();
		ProcedimentoVO p = new ProcedimentoVO();
		p.setIdProcedimento(id);
		p.setIdTipologia(rebuspTProcedimentoDTO.getIdTipoProcedimento());
		if (rebuspTIterProcedimentoDTOs != null && rebuspTIterProcedimentoDTOs.size() > 0) {
			for (RebuspTIterProcedimentoDTO dto : rebuspTIterProcedimentoDTOs) {
				iterProcedimentoVOs.add(iterProcedimentoMapper.mapDTOtoVO(dto));
				if (dto.getDataFineValidita() == null) {
					p.setIdStato(dto.getIdStatoIter());
				}
			}
			/*
			 * Collections.sort(iterProcedimentoVOs, new Comparator<IterProcedimentoVO>() {
			 * 
			 * @Override public int compare(IterProcedimentoVO o1, IterProcedimentoVO o2) {
			 * return o1.getDataInizioValidita().compareTo(o2.getDataInizioValidita()); }
			 * });
			 */
			dataInizioProcedimento = iterProcedimentoVOs.get(iterProcedimentoVOs.size() - 1).getDataInizioValidita();
			calendar.setTime(dataInizioProcedimento);

			richiesta.setIters(iterProcedimentoVOs);
		}
		if (richiesta.getIters() != null && richiesta.getIters().get(0).getIdStato() >= 40
				&& ((richiesta.getNominativoFirmaEnte() == null
						|| (richiesta.getNominativoFirmaEnte() != null && richiesta.getNominativoFirmaEnte().isEmpty()))
						|| (richiesta.getRuoloFirmaEnte() == null || (richiesta.getRuoloFirmaEnte() != null
								&& richiesta.getRuoloFirmaEnte().isEmpty())))) {

			if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_ENTE.getId())
					|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AMP.getId())) {
				RebuspTProcedimentoDTO procedimentoTMP = procedimentoDAO
						.getProcedimentoForFirmaEnte(userInfo.getIdEnte());
				RebuspTProcedimentoDTO procedimentoTMPPre = procedimentoDAO.getProcedimentoForPremessePrescrizioni(
						userInfo.getIdEnte(), richiesta.getIdTipoProcedimento());
				if (procedimentoTMP != null) {
					richiesta.setNominativoFirmaEnte(procedimentoTMP.getNominativoFirmaEnte());
					richiesta.setRuoloFirmaEnte(procedimentoTMP.getRuoloFirmaEnte());
					// controllare
				}
				if (procedimentoTMPPre != null) {
					richiesta.setPremesse(procedimentoTMPPre.getPremesse());
					richiesta.setPrescrizioni(procedimentoTMPPre.getPrescrizioni());
				}
			}
		}
		// setto lo scenario per lo scarico del pdf
		richiesta.setScenarioScaricaPDF(null);
		setConditionVisualizzazioneProcedimenti(p);
		if (p.getIsAbilitatoScaricaPDFAzienda()) {
			richiesta.setScenarioScaricaPDF(Long.valueOf(1));
		} else if (p.getIsAbilitatoScaricaPDFEnte()) {
			richiesta.setScenarioScaricaPDF(Long.valueOf(2));
		}
		if (action.equals(Action.EDIT.value) && p.getIdStato().compareTo(new Long(40)) < 0
				&& richiesta.getIsACaricoEnteVisible()) {
			richiesta.setIsACaricoEnteVisible(Boolean.FALSE);
		}
		if (action.equals(Action.VIEW.value) && p.getIdStato().compareTo(new Long(60)) >= 0) {
			richiesta.setIsACaricoEnteVisible(Boolean.TRUE);
		}

		if (action.equals(Action.EDIT.value) && !p.getIsAbilitatoModifica()) {
			throw new ModificaNonAbilitataException();
		}

		if (richiesta.getIdTipoProcedimento() == 2) { // SOSTITUZIONE
			RebuspTSubProcedimentoSelector rebuspTSubProcedimentoSelector = new RebuspTSubProcedimentoSelector();
			rebuspTSubProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(richiesta.getId());
			List<RebuspTSubProcedimentoKey> rebuspTSubProcedimentoKeys = rebuspTSubProcedimentoDAO
					.selectByExample(rebuspTSubProcedimentoSelector);
			if (rebuspTSubProcedimentoKeys != null && rebuspTSubProcedimentoKeys.size() > 0) {
				richiesta.setSubProcedimento(subProcedimentoMapper.mapDTOtoVO(rebuspTSubProcedimentoKeys.get(0)));

				RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
				rebuspRProcContrattoSelector.createCriteria()
						.andIdProcedimentoEqualTo(rebuspTSubProcedimentoKeys.get(0).getIdSubProcedimento1());
				rebuspRProcContrattoSelector.or()
						.andIdProcedimentoEqualTo(rebuspTSubProcedimentoKeys.get(0).getIdSubProcedimento2());
				List<RebuspRProcContrattoDTO> rebuspRProcContrattoDTOs = rebuspRProcContrattoDAO
						.selectByExample(rebuspRProcContrattoSelector);
				if (rebuspRProcContrattoDTOs != null && rebuspRProcContrattoDTOs.size() > 0) {
					richiesta.setContratto(contrattoProcMapper.mapDTOtoVO(rebuspRProcContrattoDTOs.get(0)));
					if (action.equals(Action.EDIT.value) && rebuspRProcContrattoDTOs.get(0).getIdContratto()
							.equals(rebuspRProcContrattoDTOs.get(1).getIdContratto())) {
						SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO
								.selectByPrimaryKey(rebuspRProcContrattoDTOs.get(0).getIdContratto());
						if (userInfo.getIdEnte() != null
								&& userInfo.getIdEnte().equals(sirtplcTContrattoDTO.getIdSogGiuridCommittente())) {
							richiesta.setIsACaricoEnteVisible(Boolean.TRUE);
						}
					}
				}

			}

		}
		// NUM PROGRESSIVO PER TUTTI QUELLI CHE NON SONO SUBPROCEDIMENTI
		RebuspTSubProcedimentoSelector rebuspTSubProcedimentoSelector = new RebuspTSubProcedimentoSelector();
		rebuspTSubProcedimentoSelector.createCriteria().andIdSubProcedimento1EqualTo(richiesta.getId());
		rebuspTSubProcedimentoSelector.or().andIdSubProcedimento2EqualTo(richiesta.getId());
		List<RebuspTSubProcedimentoKey> rebuspTSubProcedimentoKeys = rebuspTSubProcedimentoDAO
				.selectByExample(rebuspTSubProcedimentoSelector);
		if (rebuspTSubProcedimentoKeys == null || rebuspTSubProcedimentoKeys.size() == 0) {
			RebuspDTipoProcedimentoDTO rebuspDTipoProcedimentoDTO = rebuspDTipoProcedimentoDAO
					.selectByPrimaryKey(richiesta.getIdTipoProcedimento());
			richiesta.setNumProgressivo(
					calendar.get(Calendar.YEAR) + "/" + rebuspDTipoProcedimentoDTO.getCodTipoProcedimento() + "/"
							+ rebuspTProcedimentoDTO.getNumProcedimento());
		}
		return richiesta;
	}

	@Transactional
	@Override
	public Long modificaRichiesta(DettaglioRichiestaVO richiestaRequest) {
		if (richiestaRequest == null) {
			throw new InvalidParameterException("Richiesta non valorizzata");
		}		
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.MODIFICA_PROCEDIMENTO);

		if (richiestaRequest.getFiles() != null) {
			for (AllegatoProcVO f : richiestaRequest.getFiles()) {
				if (!f.getNomeFile().toLowerCase().contains(".pdf")) {
					throw new ErroreGestitoException(
							"Formato file del documento allegato non consentito! Formato consentito: PDF.", "TFNC");
				}
			}
		}

		if (richiestaRequest.getIdTipoProcedimento().equals(Long.valueOf(5))) {
			List<Long> idTipiContratto = new ArrayList<>();
			for (ContrattoProcVO contratto : richiestaRequest.getContratti()) {
				idTipiContratto.add(contratto.getIdTipoContratto());
			}

			try {
				this.checkVeicoliContrattiUsoInLinea(richiestaRequest.getVeicoli(), idTipiContratto);
			} catch (Exception e) {
				throw new ErroreGestitoException(e.getMessage(), "UIL");
			}

		}
		Date now = new Date();
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		RebuspTProcedimentoDTO rebuspTProcedimentoDTO = rebuspTProcedimentoDAO
				.selectByPrimaryKey(richiestaRequest.getId());
		if (rebuspTProcedimentoDTO != null) {
			for (IterProcedimentoVO iter : richiestaRequest.getIters()) {
				// SALVATAGGIO DATI INSERITI DALL'ENTE COMMITTENTE NELLA
				// SEZIONE "A CARICO DELL'ENTE"
				if (iter.getIdStato() >= Long.valueOf(40) && iter.getDataFineValidita() == null) {
					rebuspTProcedimentoDTO.setPremesse(richiestaRequest.getPremesse());
					rebuspTProcedimentoDTO.setPrescrizioni(richiestaRequest.getPrescrizioni());
					rebuspTProcedimentoDTO.setFlgFirmaDigitaleEnte(richiestaRequest.getFlgFirmaDigitaleEnte());
					rebuspTProcedimentoDTO.setRuoloFirmaEnte(richiestaRequest.getRuoloFirmaEnte());
					rebuspTProcedimentoDTO.setNominativoFirmaEnte(richiestaRequest.getNominativoFirmaEnte());
					// controllare
					break;
				}
			}
			if (richiestaRequest.getIdTipoProcedimento() == 2) { // SOSTITUZIONE
				rebuspTProcedimentoDTO.setIdTipoProcedimento(richiestaRequest.getIdTipoProcedimento());
				rebuspTProcedimentoDTO.setIdMotorizzazione(richiestaRequest.getIdMotorizzazione());
				rebuspTProcedimentoDTO.setIdMotivazione(richiestaRequest.getIdMotivazione());
				rebuspTProcedimentoDTO.setTestoMotivAltro(richiestaRequest.getNoteMotivazione());
				rebuspTProcedimentoDTO.setRuoloFirma(richiestaRequest.getRuoloFirma());
				rebuspTProcedimentoDTO.setNote(richiestaRequest.getNota());
				// controllare
				rebuspTProcedimentoDTO.setNominativoFirma(richiestaRequest.getNominativoFirma());
				rebuspTProcedimentoDTO.setFlgFirmaDigitale(richiestaRequest.getFlgFirmaDigitale());
				rebuspTProcedimentoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				rebuspTProcedimentoDTO.setDataAggiornamento(now);
				rebuspTProcedimentoDTO.setFlgAllegaLinee(richiestaRequest.getFlgAllegaLinea());
				
				rebuspTProcedimentoDAO.updateByPrimaryKey(rebuspTProcedimentoDTO);
			} else {
				rebuspTProcedimentoDTO.setIdTipoProcedimento(richiestaRequest.getIdTipoProcedimento());
				rebuspTProcedimentoDTO.setIdMotorizzazione(richiestaRequest.getIdMotorizzazione());
				rebuspTProcedimentoDTO.setIdMotivazione(richiestaRequest.getIdMotivazione());
				rebuspTProcedimentoDTO.setTestoMotivAltro(richiestaRequest.getNoteMotivazione());
				rebuspTProcedimentoDTO.setRuoloFirma(richiestaRequest.getRuoloFirma());
				rebuspTProcedimentoDTO.setNominativoFirma(richiestaRequest.getNominativoFirma());
				rebuspTProcedimentoDTO.setFlgFirmaDigitale(richiestaRequest.getFlgFirmaDigitale());
				rebuspTProcedimentoDTO.setNote(richiestaRequest.getNota());
				rebuspTProcedimentoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
				rebuspTProcedimentoDTO.setDataAggiornamento(now);
				rebuspTProcedimentoDTO.setFlgAllegaLinee(richiestaRequest.getFlgAllegaLinea());
				rebuspTProcedimentoDAO.updateByPrimaryKey(rebuspTProcedimentoDTO);

				RebuspRProcVeicoloSelector rebuspRProcVeicoloSelector = new RebuspRProcVeicoloSelector();
				rebuspRProcVeicoloSelector.createCriteria().andIdProcedimentoEqualTo(richiestaRequest.getId());
				List<RebuspRProcVeicoloDTO> rebuspRProcVeicoloDTOs = rebuspRProcVeicoloDAO
						.selectByExample(rebuspRProcVeicoloSelector);
				List<String> primoTelaios = new ArrayList<>();

				for (RebuspRProcVeicoloDTO dto : rebuspRProcVeicoloDTOs) {
					primoTelaios.add(dto.getPrimoTelaio());
				}

				for (VeicoloVO v : richiestaRequest.getVeicoli()) {
					// evenutali controlli futuri
					if (primoTelaios != null && primoTelaios.size() > 0 && primoTelaios.contains(v.getPrimoTelaio())) {
						primoTelaios.remove(v.getPrimoTelaio());
						continue;
					}

					// controllo per evitare che in una richiesta di Uso in
					// LInea,
					// Reimmatricolazione,Sostituzione,Alienazione
					// venga indicato come titolare su un contratto un veicolo
					// che e
					// gia stato indicato
					// come titolare su un contratto in un'altra richiesta
					RebuspRProcVeicoloDTO rebuspRProcVeicoloDTO = new RebuspRProcVeicoloDTO();
					rebuspRProcVeicoloDTO.setIdProcedimento(rebuspTProcedimentoDTO.getIdProcedimento());
					rebuspRProcVeicoloDTO.setPrimoTelaio(v.getPrimoTelaio());
					rebuspRProcVeicoloDTO.setDataAggiornamento(now);
					rebuspRProcVeicoloDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
					try {
						rebuspRProcVeicoloDAO.insert(rebuspRProcVeicoloDTO);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

				}
				if (primoTelaios != null && primoTelaios.size() > 0) {
					for (String pt : primoTelaios) {
						RebuspRProcVeicoloKey key = new RebuspRProcVeicoloKey();
						key.setIdProcedimento(richiestaRequest.getId());
						key.setPrimoTelaio(pt);
						rebuspRProcVeicoloDAO.deleteByPrimaryKey(key);
					}
				}

				if (richiestaRequest.getIdTipoProcedimento() != 5) {
					RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
					rebuspRProcContrattoSelector.createCriteria().andIdProcedimentoEqualTo(richiestaRequest.getId());
					List<RebuspRProcContrattoDTO> rebuspRProcContrattoDTOs = rebuspRProcContrattoDAO
							.selectByExample(rebuspRProcContrattoSelector);
					if (rebuspRProcContrattoDTOs != null && rebuspRProcContrattoDTOs.size() == 1) {
						// dubbio. controllo altrimenti si spacca
						if (richiestaRequest.getContratto() != null) {
							rebuspRProcContrattoDTOs.get(0)
									.setIdContratto(richiestaRequest.getContratto().getIdContratto());
							rebuspRProcContrattoDTOs.get(0)
									.setIdTipoContratto(richiestaRequest.getContratto().getIdTipoContratto());
						}
						rebuspRProcContrattoDTOs.get(0).setDataAggiornamento(now);
						rebuspRProcContrattoDTOs.get(0).setIdUtenteAggiornamento(userInfo.getIdUtente());
						rebuspRProcContrattoDAO.updateByPrimaryKey(rebuspRProcContrattoDTOs.get(0));
					}
				} else {
					// logica per eliminare i contratti,aggiungere nuovi contratti o modificare gli
					// esistenti
					RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
					rebuspRProcContrattoSelector.createCriteria().andIdProcedimentoEqualTo(richiestaRequest.getId());
					List<RebuspRProcContrattoDTO> rebuspRProcContrattoDTOs = rebuspRProcContrattoDAO
							.selectByExample(rebuspRProcContrattoSelector);
					List<Long> idProcContratti = new ArrayList<>();
					for (ContrattoProcVO c : richiestaRequest.getContratti()) {
						if (c.getIdProcContratto() != null)
							idProcContratti.add(c.getIdProcContratto());
					}

					for (RebuspRProcContrattoDTO procContratto : rebuspRProcContrattoDTOs) {
						if (!idProcContratti.contains(procContratto.getIdProcContratto())) {
							rebuspRProcContrattoDAO.deleteByPrimaryKey(procContratto.getIdProcContratto());
						}
					}

					RebuspRProcContrattoDTO rebuspRProcContrattoDTO = null;
					for (ContrattoProcVO c : richiestaRequest.getContratti()) {
						if (c.getIdProcContratto() == null) {
							rebuspRProcContrattoDTO = new RebuspRProcContrattoDTO();
						} else {
							rebuspRProcContrattoDTO = rebuspRProcContrattoDAO
									.selectByPrimaryKey(c.getIdProcContratto());
						}
						rebuspRProcContrattoDTO.setIdContratto(c.getIdContratto());
						rebuspRProcContrattoDTO.setIdTipoContratto(c.getIdTipoContratto());
						rebuspRProcContrattoDTO.setIdProcedimento(richiestaRequest.getId());
						rebuspRProcContrattoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
						rebuspRProcContrattoDTO.setDataAggiornamento(now);

						if (c.getIdProcContratto() == null)
							rebuspRProcContrattoDAO.insert(rebuspRProcContrattoDTO);
						else
							rebuspRProcContrattoDAO.updateByPrimaryKey(rebuspRProcContrattoDTO);
					}

				}

				RebuspRProcDocumentoSelector rebuspRProcDocumentoSelector = new RebuspRProcDocumentoSelector();
				rebuspRProcDocumentoSelector.createCriteria().andIdProcedimentoEqualTo(richiestaRequest.getId());
				List<RebuspRProcDocumentoDTO> rebuspRProcDocumentoDTOs = rebuspRProcDocumentoDAO
						.selectByExample(rebuspRProcDocumentoSelector);
				List<Long> idTipoDocs = new ArrayList<>();
				for (RebuspRProcDocumentoDTO dto : rebuspRProcDocumentoDTOs) {
					// LISTA ID TIPI DOC SALVATI SU DB
					idTipoDocs.add(dto.getIdTipoDocumento());
				}
				boolean updated = false;
				for (AllegatoProcVO a : richiestaRequest.getFiles()) {
					if (a.getFile() != null && a.getFile().length > 0) {
						// SE IL FILE E'NUOVO
						if (idTipoDocs != null && idTipoDocs.size() > 0
								&& idTipoDocs.contains(a.getTipoDocumento().getId())) {
							// SE ESISTE GIA' UN FILE DI QUEL TIPO SUL DB LO
							// AGGIORNO
							for (RebuspRProcDocumentoDTO dto : rebuspRProcDocumentoDTOs) {
								if (dto.getIdTipoDocumento() == a.getTipoDocumento().getId()) {
									dto.setDocumento(a.getFile());
									dto.setNomeFile(a.getNomeFile());
									dto.setDataCaricamento(a.getDataCaricamento());
									dto.setNote(a.getNote());
									dto.setIdUtenteAggiornamento(userInfo.getIdUtente());
									rebuspRProcDocumentoDAO.updateByPrimaryKeyWithBLOBs(dto);
									idTipoDocs.remove(a.getTipoDocumento().getId());
									updated = true;
									continue;
								}
							}
						}
						if (!updated) {
							// SE NON ESISTE ANCORA UN FILE DI QUEL TIPO SU DB
							// LO
							// AGGIUNGO
							RebuspRProcDocumentoDTO rebuspRProcDocumentoDTO = new RebuspRProcDocumentoDTO();
							rebuspRProcDocumentoDTO.setIdProcedimento(richiestaRequest.getId());
							rebuspRProcDocumentoDTO.setIdTipoDocumento(a.getTipoDocumento().getId());
							rebuspRProcDocumentoDTO.setDocumento(a.getFile());
							rebuspRProcDocumentoDTO.setNomeFile(a.getNomeFile());
							rebuspRProcDocumentoDTO.setDataCaricamento(a.getDataCaricamento());
							rebuspRProcDocumentoDTO.setNote(a.getNote());
							rebuspRProcDocumentoDTO.setIdUtenteAggiornamento(userInfo.getIdUtente());
							rebuspRProcDocumentoDAO.insert(rebuspRProcDocumentoDTO);
						} else {
							updated = false;
						}
					} else {
						// SE IL FILE E' GIA' SALVATO SUL DB E NON DEVO
						// MODIFICARLO
						if (idTipoDocs != null && idTipoDocs.size() > 0
								&& idTipoDocs.contains(a.getTipoDocumento().getId())) {
							idTipoDocs.remove(a.getTipoDocumento().getId());
						}
					}
				}
				// SE IL FILE E' PRESENTE SUL DB MA E' STATO CANCELLATO DALLA
				// RICHIESTA LO ELIMINO DAL DB
				if (idTipoDocs != null && idTipoDocs.size() > 0) {
					for (Long id : idTipoDocs) {
						RebuspRProcDocumentoKey key = new RebuspRProcDocumentoKey();
						key.setIdProcedimento(richiestaRequest.getId());
						key.setIdTipoDocumento(id);
						rebuspRProcDocumentoDAO.deleteByPrimaryKey(key);
					}
				}
				if (richiestaRequest.getIdTipoProcedimento().equals(new Long(7))) { // CONTRIBUZIONE
					RebuspRVoceCostoVeicoloSelector rebuspRVoceCostoVeicoloSelector = new RebuspRVoceCostoVeicoloSelector();
					rebuspRVoceCostoVeicoloSelector.createCriteria()
							.andIdProcedimentoEqualTo(rebuspTProcedimentoDTO.getIdProcedimento());
					List<RebuspRVoceCostoVeicoloDTO> rebuspRVoceCostoVeicoloDTOs = rebuspRVoceCostoVeicoloDAO
							.selectByExample(rebuspRVoceCostoVeicoloSelector);
					Map<Long, RebuspRVoceCostoVeicoloDTO> vociDB = new HashMap<>();
					for (RebuspRVoceCostoVeicoloDTO dto : rebuspRVoceCostoVeicoloDTOs) {
						vociDB.put(dto.getIdVoceCostoVeicolo(), dto);
					}
					for (VoceDiCostoVeicoloVO v : richiestaRequest.getVociDiCostoVeicolo()) {
						if (v.getId() != null) { // già salvate su DB
							vociDB.get(v.getId()).setPrimoTelaio(richiestaRequest.getVeicoli().get(0).getPrimoTelaio());
							rebuspRVoceCostoVeicoloDAO.updateByPrimaryKey(vociDB.get(v.getId()));
							vociDB.remove(v.getId());

						}
					}
					for (RebuspRVoceCostoVeicoloDTO d : vociDB.values()) { // cancellate
						rebuspRVoceCostoVeicoloDAO.deleteByPrimaryKey(d.getIdVoceCostoVeicolo());
					}

					for (VoceDiCostoVeicoloVO vo : richiestaRequest.getVociDiCostoVeicolo()) {
						if (vo.getId() == null) { // non salvate su DB
							RebuspRVoceCostoVeicoloDTO rebuspRVoceCostoVeicoloDTO = new RebuspRVoceCostoVeicoloDTO();
							rebuspRVoceCostoVeicoloDTO.setIdProcedimento(vo.getIdProcedimento());
							rebuspRVoceCostoVeicoloDTO.setIdVoceCosto(vo.getIdVoceCosto());
							rebuspRVoceCostoVeicoloDTO
									.setPrimoTelaio(richiestaRequest.getVeicoli().get(0).getPrimoTelaio());
							rebuspRVoceCostoVeicoloDTO.setImporto(vo.getImporto());
							rebuspRVoceCostoVeicoloDAO.insert(rebuspRVoceCostoVeicoloDTO);
						}
					}
				}
			}
		}
		return richiestaRequest.getId();
	}

	@Transactional
	@Override
	public void eliminaProcedimento(Long idProcedimento) {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.ELIMINA_PROCEDIMENTO);
		// controllo se il procedimento e eliminabile
		ProcedimentoVO p = new ProcedimentoVO();
		p.setIdProcedimento(idProcedimento);
		p.setIdStato(getidStatoIterCorrente(idProcedimento));
		setConditionVisualizzazioneProcedimenti(p);
		if (!p.getIsAbilitatoElimina()) {
			throw new InvalidParameterException("Procedimento non eliminabile");
		}
		if (idProcedimento != null) {
			RebuspTProcedimentoDTO procedimento = rebuspTProcedimentoDAO.selectByPrimaryKey(idProcedimento);
			// se procedimento sostituzione richiamo stesso metodo eliminazione
			// con idprocedimento = idsubprocedimento
			if (procedimento.getIdTipoProcedimento() == 2) {
				RebuspTSubProcedimentoSelector rebuspTSubProcedimentoSelector = new RebuspTSubProcedimentoSelector();
				rebuspTSubProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
				List<RebuspTSubProcedimentoKey> subProcedimenti = rebuspTSubProcedimentoDAO
						.selectByExample(rebuspTSubProcedimentoSelector);
				for (RebuspTSubProcedimentoKey subProcedimento : subProcedimenti) {
					rebuspTSubProcedimentoDAO.deleteByPrimaryKey(subProcedimento);
					eliminaProcedimento(subProcedimento.getIdSubProcedimento1());
					eliminaProcedimento(subProcedimento.getIdSubProcedimento2());
				}
			}

			// recupero id proc contratto
			RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
			rebuspRProcContrattoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
			// da scommentare quando verra sviluppata la componente con
			// contratto linea
			rebuspRProcContrattoDAO.deleteByExample(rebuspRProcContrattoSelector);

			// recupero documenti da eliminare
			RebuspRProcDocumentoSelector documentoSelector = new RebuspRProcDocumentoSelector();
			documentoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
			rebuspRProcDocumentoDAO.deleteByExample(documentoSelector);

			// recupero iter procedimento da cancellare
			RebuspTIterProcedimentoSelector iterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
			iterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
			rebuspTIterProcedimentoDAO.deleteByExample(iterProcedimentoSelector);

			// recupero voci di costo da eliminare
			if (procedimento.getIdTipoProcedimento().equals(new Long(7))) {
				RebuspRVoceCostoVeicoloSelector rebuspRVoceCostoVeicoloSelector = new RebuspRVoceCostoVeicoloSelector();
				rebuspRVoceCostoVeicoloSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
				rebuspRVoceCostoVeicoloDAO.deleteByExample(rebuspRVoceCostoVeicoloSelector);
			}

			// recupero veicoli da eliminare
			RebuspRProcVeicoloSelector procVeicoloSelector = new RebuspRProcVeicoloSelector();
			procVeicoloSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
			rebuspRProcVeicoloDAO.deleteByExample(procVeicoloSelector);

			rebuspTProcedimentoDAO.deleteByPrimaryKey(idProcedimento);

		}

	}

	@Override
	public List<TipoContrattoVO> getTipiContratto() {
		RebuspDTipoContrattoSelector rebuspDTipoContrattoSelector = new RebuspDTipoContrattoSelector();
		rebuspDTipoContrattoSelector.setOrderByClause("id_tipo_ruolo");
		List<RebuspDTipoContrattoDTO> rebuspDTipoContrattoDTOs = rebuspDTipoContrattoDAO
				.selectByExample(rebuspDTipoContrattoSelector);
		List<TipoContrattoVO> tipoContrattoVOs = new ArrayList<>();
		for (RebuspDTipoContrattoDTO dto : rebuspDTipoContrattoDTOs) {
			tipoContrattoVOs.add(tipoContrattoMapper.mapDTOtoVO(dto));
		}
		return tipoContrattoVOs;
	}

	@Override
	public List<TransizioneAutomaVO> getTransizioniAutoma(Long idProcedimento, Long idStatoIterRichiesta, String parte)
			throws Exception {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		String parteToReplace = parte != null ? "'" + parte.toUpperCase().substring(0, 1) + "'" : "'Z'";
		RebuspDTransizioneAutomaSelector rebuspDTransizioneAutomaSelector = new RebuspDTransizioneAutomaSelector();
		rebuspDTransizioneAutomaSelector.createCriteria().andIdStatoIterDaEqualTo(idStatoIterRichiesta)
				.andDataFineValiditaIsNull();
		rebuspDTransizioneAutomaSelector.setOrderByClause("id_transizione_automa");
		List<RebuspDTransizioneAutomaDTO> rebuspDTransizioneAutomaDTOs = rebuspDTransizioneAutomaDAO
				.selectByExample(rebuspDTransizioneAutomaSelector);
		List<TransizioneAutomaVO> transizioneAutomaVOs = new ArrayList<>();
		RebuspTProcedimentoDTO proc = null;
		try {
			proc = rebuspTProcedimentoDAO.selectByPrimaryKey(idProcedimento);
		} catch (Exception e) {
			System.out.println("Message String = " + e.getMessage());
		}
		for (RebuspDTransizioneAutomaDTO dto : rebuspDTransizioneAutomaDTOs) {
			String query = dto.getCondizioni() != null ? dto.getCondizioni()
					.replace("$$ID_PROC", idProcedimento.toString()).replace("$$PARTE", parteToReplace.toString())
					.replace("$$RUOLO", "'" + userInfo.getRuolo().getCodiceRuolo().toString() + "'") : null;

			if (query != null) {
				Integer eseguiQuery = null;
				try {
					eseguiQuery = commonDAO.eseguiQuery(query);
				} catch (Exception e) {
					System.out.println("eseguiQuery error = " + e.getMessage());
				}
				if (eseguiQuery != null) {
					if (eseguiQuery == 3) {
						TransizioneAutomaVO mapDTOtoVO = transizioneAutomaMapper.mapDTOtoVO(dto);
						mapDTOtoVO.setReturnTransizione(3);
						transizioneAutomaVOs.add(mapDTOtoVO);
						break;
					}
					if (eseguiQuery == 2) {
						TransizioneAutomaVO mapDTOtoVO = transizioneAutomaMapper.mapDTOtoVO(dto);
						mapDTOtoVO.setReturnTransizione(2);
						transizioneAutomaVOs.add(mapDTOtoVO);
					}
					if (eseguiQuery == 1) {
						TransizioneAutomaVO mapDTOtoVO = transizioneAutomaMapper.mapDTOtoVO(dto);
						mapDTOtoVO.setReturnTransizione(1);
						transizioneAutomaVOs.add(mapDTOtoVO);
					}
					if (proc != null) {
						if (eseguiQuery == 0 && proc.getIdTipoProcedimento().equals(new Long(7))) {
							TransizioneAutomaVO mapDTOtoVO = transizioneAutomaMapper.mapDTOtoVO(dto);
							mapDTOtoVO.setReturnTransizione(0);
							transizioneAutomaVOs.add(mapDTOtoVO);
						}
					}
				}

			} else {
				TransizioneAutomaVO mapDTOtoVO = transizioneAutomaMapper.mapDTOtoVO(dto);
				transizioneAutomaVOs.add(mapDTOtoVO);
			}

		}
		return transizioneAutomaVOs;
	}

	@Override
	@Transactional
	public Long avanzaIterRichiesta(DettaglioRichiestaVO richiestaRequest, TransizioneAutomaVO transizioneRequest,
			String notaTransizione) throws ErroreGestitoException {
		Date now = new Date();

		RebuspTIterProcedimentoDTO rebuspTIterProcedimentoDTO = new RebuspTIterProcedimentoDTO();
		for (IterProcedimentoVO iter : richiestaRequest.getIters()) {
			if (iter.getDataFineValidita() == null) {
				rebuspTIterProcedimentoDTO = rebuspTIterProcedimentoDAO.selectByPrimaryKey(iter.getId());
				break;
			}
		}
		if (rebuspTIterProcedimentoDTO != null) {
			rebuspTIterProcedimentoDTO.setDataFineValidita(now);
			rebuspTIterProcedimentoDAO.updateByPrimaryKey(rebuspTIterProcedimentoDTO);
		}
		RebuspTIterProcedimentoDTO iterDto = new RebuspTIterProcedimentoDTO();
		iterDto.setIdProcedimento(richiestaRequest.getId());
		iterDto.setIdStatoIter(transizioneRequest.getIdStatoIterA());
		iterDto.setNote(notaTransizione);
		iterDto.setDataInizioValidita(now);

		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		SirtplRUtenteSogGiuridSelector sirtplRUtenteSogGiuridSelector = new SirtplRUtenteSogGiuridSelector();
		Long idSoggettoGiuridico = userInfo.getIdAzienda() != null ? userInfo.getIdAzienda() : userInfo.getIdEnte();
		sirtplRUtenteSogGiuridSelector.createCriteria().andIdUtenteEqualTo(userInfo.getIdUtente())
				.andIdSoggettoGiuridicoEqualTo(idSoggettoGiuridico);
		List<SirtplRUtenteSogGiuridDTO> sirtplRUtenteSogGiuridDTOs = sirtplRUtenteSogGiuridDAO
				.selectByExample(sirtplRUtenteSogGiuridSelector);
		SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = new SirtplRUtenteSogGiuridDTO();
		if (sirtplRUtenteSogGiuridDTOs != null && sirtplRUtenteSogGiuridDTOs.size() > 0) {
			sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDTOs.get(0);
			if (sirtplRUtenteSogGiuridDTOs.size() > 1) {
				for (SirtplRUtenteSogGiuridDTO dto : sirtplRUtenteSogGiuridDTOs) {
					if (dto.getDataInizioValidita().after(sirtplRUtenteSogGiuridDTO.getDataInizioValidita())
							&& dto.getDataInizioValidita().before(new Date())) {
						sirtplRUtenteSogGiuridDTO = dto;
					}
				}
			}
		}
		iterDto.setIdUtenteSogGiurid(sirtplRUtenteSogGiuridDTO.getIdUtenteSogGiurid());

		rebuspTIterProcedimentoDAO.insert(iterDto);
		if (!richiestaRequest.getIdTipoProcedimento().equals(new Long(7))) {
			Long idContratto = Long.valueOf(-1);
			if (richiestaRequest.getIdTipoProcedimento().equals(new Long(2))) {
				RebuspRProcContrattoSelector rebuspRProcContrattoSelector = new RebuspRProcContrattoSelector();
				rebuspRProcContrattoSelector.createCriteria()
						.andIdProcedimentoEqualTo(richiestaRequest.getSubProcedimento().getIdSubProcedimento1());
				rebuspRProcContrattoSelector.or()
						.andIdProcedimentoEqualTo(richiestaRequest.getSubProcedimento().getIdSubProcedimento2());
				List<RebuspRProcContrattoDTO> rebuspRProcContrattoDTOs = rebuspRProcContrattoDAO
						.selectByExample(rebuspRProcContrattoSelector);
				if (rebuspRProcContrattoDTOs != null && rebuspRProcContrattoDTOs.size() > 0) {
					idContratto = rebuspRProcContrattoDTOs.get(0).getIdContratto();
				}
			} else {
				if (!richiestaRequest.getIdTipoProcedimento().equals(new Long(5))) {
					idContratto = richiestaRequest.getContratto().getIdContratto();
				} else {
					for (ContrattoProcVO c : richiestaRequest.getContratti()) {
						if (c.getIdTipoContratto().equals(Long.valueOf(1))) {
							idContratto = c.getIdContratto();
							break;
						}
					}
				}
			}

			if (transizioneRequest.getIdTipoMessaggio() != null) {
				try {
					this.inviaMessaggio(richiestaRequest.getId(), richiestaRequest.getIdTipoProcedimento(),
							transizioneRequest.getIdTipoMessaggio(), idContratto, now, iterDto.getIdStatoIter());
				} catch (Exception e) {
					e.printStackTrace();
					throw new ErroreGestitoException("Errore nell'invio del messaggio", "MESS");
				}
			}
		}

		if (richiestaRequest.getIdTipoProcedimento().equals(new Long(7))) {
			try {
				this.inviaMessaggio(richiestaRequest.getId(), richiestaRequest.getIdTipoProcedimento(),
						transizioneRequest.getIdTipoMessaggio(), null, now, iterDto.getIdStatoIter());
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'invio del messaggio", "MESS");
			}
		}

		if (richiestaRequest.getSubProcedimento() != null) {
			List<Long> idSubs = new ArrayList<>();
			idSubs.add(richiestaRequest.getSubProcedimento().getIdSubProcedimento1());
			idSubs.add(richiestaRequest.getSubProcedimento().getIdSubProcedimento2());
			for (Long idSub : idSubs) {
				RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
				rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(idSub);
				List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
						.selectByExample(rebuspTIterProcedimentoSelector);
				RebuspTIterProcedimentoDTO iter = null;

				for (RebuspTIterProcedimentoDTO dto : rebuspTIterProcedimentoDTOs) {
					if (dto.getDataFineValidita() == null) {
						iter = dto;
						break;
					}
				}
				if (iter != null) {
					iter.setDataFineValidita(now);
					rebuspTIterProcedimentoDAO.updateByPrimaryKey(iter);
				}
				RebuspTIterProcedimentoDTO iterSub = new RebuspTIterProcedimentoDTO();
				iterSub.setIdProcedimento(idSub);
				iterSub.setIdStatoIter(transizioneRequest.getIdStatoIterA());
				iterSub.setNote(notaTransizione);
				iterSub.setDataInizioValidita(now);
				iterSub.setIdUtenteSogGiurid(sirtplRUtenteSogGiuridDTO.getIdUtenteSogGiurid());

				rebuspTIterProcedimentoDAO.insert(iterSub);

			}
		}
		RebuspTProcedimentoDTO rebuspTProcedimentoDTO = rebuspTProcedimentoDAO
				.selectByPrimaryKey(richiestaRequest.getId());
		rebuspTProcedimentoDTO.setRuoloFirma(richiestaRequest.getRuoloFirma());
		rebuspTProcedimentoDTO.setNominativoFirma(richiestaRequest.getNominativoFirma());
		rebuspTProcedimentoDTO.setFlgFirmaDigitale(richiestaRequest.getFlgFirmaDigitale());
		// AUTORIZZAZIONE/ NON AUTORIZZAZIONE / RICHIESTA RIVISIONE -->
		// SALVATAGGIO DATI INSERITI DALL'ENTE COMMITTENTE
		if (transizioneRequest.getIdStatoIterA() > Long.valueOf(30)) {
			rebuspTProcedimentoDTO.setPremesse(richiestaRequest.getPremesse());
			rebuspTProcedimentoDTO.setPrescrizioni(richiestaRequest.getPrescrizioni());
			rebuspTProcedimentoDTO.setRuoloFirmaEnte(richiestaRequest.getRuoloFirmaEnte());
			rebuspTProcedimentoDTO.setNominativoFirmaEnte(richiestaRequest.getNominativoFirmaEnte());
			rebuspTProcedimentoDTO.setFlgFirmaDigitaleEnte(richiestaRequest.getFlgFirmaDigitaleEnte());
		}
		rebuspTProcedimentoDAO.updateByPrimaryKey(rebuspTProcedimentoDTO);

		// GENERAZIONE PDF E SALVATAGGIO SU DB - TUTTE LE RICHIESTE TRANNE
		// ID = 7 (CONTRIBUZIONE)
		try {
			if (!richiestaRequest.getIdTipoProcedimento().equals(new Long(7))) {
				// NO CONTRIBUZIONE
				if (transizioneRequest.getIdStatoIterA().equals(new Long(40))) {
					// TRASMESSO AD ENTE COMMITTENTE
					if (richiestaRequest.getIdTipoProcedimento().equals(new Long(2))) {
						// SOSTITUZIONE
						pdfService.generatePDF(richiestaRequest.getId(), new Long(3), Boolean.FALSE);
						// IDTIPOSTAMPA = RICHIESTA NULLA OSTA SOSTITUZIONE
					} else { // NON SOSTITUZIONE
						pdfService.generatePDF(richiestaRequest.getId(), new Long(1), Boolean.FALSE);
						// IDTIPOSTAMPA = RICHIESTA NULLA OSTA

					}
				}
				if (transizioneRequest.getIdStatoIterA().equals(new Long(60))) {
					// AUTORIZZATO
					if (richiestaRequest.getIdTipoProcedimento().equals(new Long(2))) {
						// SOSTITUZIONE
						pdfService.generatePDF(richiestaRequest.getId(), new Long(4), Boolean.FALSE);
						// IDTIPOSTAMPA = RILASCIO NULLA OSTA SOSTITUZIONE
					} else { // NON SOSTITUZIONE
						pdfService.generatePDF(richiestaRequest.getId(), new Long(2), Boolean.FALSE);
						// IDTIPOSTAMPA = RILASCIO NULLA OSTA
					}
				}
			}
		} catch (Exception e) {
			throw new ErroreGestitoException("errore nella creazione del PDF");
		}

		return iterDto.getIdIterProcedimento();
	}

	private void inviaMessaggio(Long idProcedimento, Long idTipoProcedimento, Long idTipoMessaggio, Long idContratto,
			Date now, Long idStatoIter) {
		Long utenteDestinatario = Long.valueOf(-1);
		List<Long> listUtentiDastinatari = new ArrayList<Long>();
		Long gestoreContratto = null;
		String telaio = null;
		if (idStatoIter != 20) {
			MessaggioVo input = new MessaggioVo();
			if (idContratto != null) {
				ContrattoProcDatiVO datiContratto = this.getDatiContratto(idContratto, idProcedimento);
				if (idStatoIter == 40) {
					utenteDestinatario = procedimentoDAO
							.getDestinatarioMessaggioProcedimento(datiContratto.getEnteComm().getId());
				} else if (idStatoIter == 30) {
					// recupero desc azienda e destinatario
					// descAzienda =
					// procedimentoDAO.getDescAziendaFromProcedimento(idProcedimento);

					// utenteDestinatario =
					// this.getGestoreContratto(idTipoProcedimento, idContratto,
					// idProcedimento);
					utenteDestinatario = procedimentoDAO
							.getDestinatarioMessaggioProcedimento(datiContratto.getSoggIntermediario().getId());
				} else {
					// recupero desc ente e destinatario
					// descAzienda =
					// procedimentoDAO.getDescEnteFromProcedimentoPerMessaggio(idProcedimento);

					utenteDestinatario = procedimentoDAO
							.getDestinatarioMessaggioProcedimento(datiContratto.getSoggRichiedente().getId());

				}
				// invio messaggio di richista autorizzata quando soggetto
				// intermediario esiste anche a lui
				// oltre che all'utente che ha aperto il procedimento
				if (idStatoIter == 60) {
					if (datiContratto.getSoggIntermediario() != null) {

						gestoreContratto = procedimentoDAO
								.getDestinatarioMessaggioProcedimento(datiContratto.getSoggIntermediario().getId());
						if (gestoreContratto != null) {
							input.setFkUtenteDestinatario(gestoreContratto);
						}
					}
				}
			} else {
				if (idStatoIter == 41 || idStatoIter == 42) {
					// RECUPERO GLI ID DI REGIONE E DI AMP
					// DA INSERIRE NEL RILASCIO (CHIEDERE AD ANTONIO)
					Long rp = procedimentoDAO.getDestinatarioMessaggioProcedimento(9996L);
					listUtentiDastinatari.add(rp);
				} else if (idStatoIter == 51 || idStatoIter == 52 || idStatoIter == 61 || idStatoIter == 62) {
					utenteDestinatario = contribuzioneDAO.getDestinatarioMessaggioContribuzione(idProcedimento);
				}

			}

			final String blanks15 = "               ";// 15 blanks
			final String blanks5 = "     ";// 5 blanks
			StringBuilder messaggio = new StringBuilder();

			messaggio.append("$$TIMESTAMP");
			messaggio.append(blanks5 + blanks5); // 10 blanks

			String descProcedimento = rebuspDTipoProcedimentoDAO.selectByPrimaryKey(idTipoProcedimento)
					.getDescTipoProcedimento();

			String descMessaggio = rebusDTipoMessaggioDAO.selectByPrimaryKey(idTipoMessaggio).getDescrizione();

			String[] descMessaggioArray = new String[2];
			// rendicontazione
			if (idContratto == null) {
				telaio = contribuzioneDAO.getTelaio(idProcedimento);
				descMessaggioArray = descMessaggio.split("\\$\\$TELAIO");
				messaggio.append(descMessaggioArray[0].replace("$$TIPO_PROC", descProcedimento));
				messaggio.append(telaio);
				messaggio.append(blanks5);
				messaggio.append(descMessaggioArray[1].trim());
				for (int i = 0; i < 30 - descMessaggioArray[1].trim().length(); i++) {
					messaggio.append(" ");
				}
			} else {
				descMessaggioArray = descMessaggio.split("\\$\\$TIPO_PROC");
				messaggio.append(descMessaggioArray[0]);
				messaggio.append(descProcedimento.trim());
				for (int i = 0; i < 30 - descProcedimento.trim().length(); i++) {
					messaggio.append(" ");
				}
				try {

					if (descMessaggioArray.length > 1) {

						messaggio.append(descMessaggioArray[1].trim());
						for (int i = 0; i < 40 - descMessaggioArray[1].trim().length(); i++) {
							messaggio.append(" ");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			messaggio.append("da parte di");
			messaggio.append(blanks5 + blanks5);

			// recupero desc azienda
			String descAzienda = procedimentoDAO.getDescAziendaFromProcedimento(idProcedimento);
			messaggio.append(descAzienda);

			input.setMessaggio(messaggio.toString());
			input.setDataCreazione(now);
			input.setFkTipoMessaggio(idTipoMessaggio);
			// mi recupero l'id variaz autobus e lo setto nel messaggio
			// questo serve nel dettaglio messaggio per fare la redirect sul dettaglio
			// autobus
			if (idTipoProcedimento.equals(new Long(7))) {
				RebusTVariazAutobusSelector sel = new RebusTVariazAutobusSelector();
				sel.createCriteria().andPrimoTelaioEqualTo(telaio);
				List<RebusTVariazAutobusDTO> va = rebusTVariazAutobusDAO.selectByExample(sel);
				input.setFkVariazAutobus(va.get(0).getIdVariazAutobus());
			} else {
				input.setFkVariazAutobus(null);
			}
			input.setFkStoriaVariazAutobus(null);
			input.setFkProcedimento(idProcedimento);
			if (gestoreContratto != null && !gestoreContratto.equals(new Long(-1))) {
				input.setFkUtenteDestinatario(gestoreContratto);
				messaggiService.inserisciMessaggio(input);
			}
			if (utenteDestinatario != null && !utenteDestinatario.equals(new Long(-1))) {
				input.setFkUtenteDestinatario(utenteDestinatario);
				messaggiService.inserisciMessaggio(input);
			} else {
				if (!listUtentiDastinatari.isEmpty()) {
					for (Long idUtente : listUtentiDastinatari) {
						input.setFkUtenteDestinatario(idUtente);
						messaggiService.inserisciMessaggio(input);
					}
				}
			}

		}
	}

	@Override
	public Long getGestoreContratto(Long idTipoProcedimento, Long idContratto, Long idProcedimento) {
		Long utenteDestinatario = null;
		SirtplcTContrattoDTO contratto = sirtplcTContrattoDAO.selectByPrimaryKey(idContratto);

		if (contratto.getIdTipoSogGiuridEsec() != Long.valueOf(3)) {
			SirtplcRSostSogContrSelector sirtplcRSostContrSelector = new SirtplcRSostSogContrSelector();
			sirtplcRSostContrSelector.createCriteria().andIdContrattoEqualTo(idContratto);
			List<SirtplcRSostSogContrDTO> sost = sirtplcRSostSogContrDAO.selectByExample(sirtplcRSostContrSelector);
			if (!sost.isEmpty()) {
				for (SirtplcRSostSogContrDTO s : sost) {
					if (s.getIdSogGiuridEsecutore() != null) {
						if (s.getIdTipoSostituzione().equals(Long.valueOf(1))) {
							utenteDestinatario = contratto.getIdSogGiuridEsecutore();
						} else if (s.getIdTipoSostituzione().equals(Long.valueOf(2))) {
							utenteDestinatario = this.getSubentroEnteCommittenteoEsecutoreTitolare(idContratto, false)
									.get(0).getIdSogGiuridEsecutore();
						}

					} else {
						utenteDestinatario = this.getSubentroEnteCommittenteoEsecutoreTitolare(idContratto, true).get(0)
								.getIdSogGiuridCommittente();
					}
				}

			} else {
				utenteDestinatario = procedimentoDAO.getDestinatarioMessaggioProcedimentoEnteCommittente(idContratto);
			}
		} else {
			SirtplcRContrattoRaggruppSelector sirtplcRContrSelector = new SirtplcRContrattoRaggruppSelector();
			sirtplcRContrSelector.createCriteria().andIdContrattoEqualTo(idContratto).andCapofilaEqualTo(true);
			List<SirtplcRContrattoRaggruppDTO> contrattoRaggrupp = sirtplcRContrattoRaggruppDAO
					.selectByExample(sirtplcRContrSelector);
			if (!contrattoRaggrupp.isEmpty()
					&& this.getSubentroRaggruppamento(contrattoRaggrupp.get(0).getIdContrattoRaggrupp()) != null) {
				List<SirtplcRSostSogContrRaggrDTO> subentroRaggrupp = this
						.getSubentroRaggruppamento(contrattoRaggrupp.get(0).getIdContrattoRaggrupp());
				if (subentroRaggrupp != null && subentroRaggrupp.size() > 0) {
					utenteDestinatario = subentroRaggrupp.get(0).getIdSoggettoGiuridico();
				} else {
					utenteDestinatario = contrattoRaggrupp.get(0).getIdSoggettoGiuridico();
				}

			} else {
				if (idTipoProcedimento == 2) {
					utenteDestinatario = procedimentoDAO
							.getDestinatarioMessaggioProcedimentoSostituzioneConsorzio(idContratto, idProcedimento);
				} else {
					utenteDestinatario = procedimentoDAO.getDestinatarioMessaggioProcedimentoConsorzio(idContratto,
							idProcedimento);
				}
			}
		}

		return utenteDestinatario;

	}

	@Override
	public List<Long> getTipiMessaggio(Long idContesto) {
		RebusDTipoMessaggioSelector rebusDTipoMessaggioSelector = new RebusDTipoMessaggioSelector();
		rebusDTipoMessaggioSelector.createCriteria().andIdContestoEqualTo(Long.valueOf(idContesto)); // contesto due:
																										// richieste
		List<RebusDTipoMessaggioDTO> rebusDTipoMessaggioDTO = rebusDTipoMessaggioDAO
				.selectByExample(rebusDTipoMessaggioSelector);
		List<Long> tipiMessaggio = new ArrayList<Long>();
		for (RebusDTipoMessaggioDTO dto : rebusDTipoMessaggioDTO) {
			if (!tipiMessaggio.contains(dto.getIdTipoMessaggio())) {
				tipiMessaggio.add(dto.getIdTipoMessaggio());
			}
		}
		return tipiMessaggio;
	}

	@Override
	public String descrizioneAziendaMessaggioProc(Long idProcedimento) {
		String descAzienda = procedimentoDAO.getDescAziendaFromProcedimento(idProcedimento);
		return descAzienda;
	}

	@Override
	public String getNoteMessaggio(Long idProcedimento, Long idTipoMessaggio, Date dataCreazione) {
		String noteMessaggio = procedimentoDAO.getNoteMessaggio(idProcedimento, idTipoMessaggio, dataCreazione);
		return noteMessaggio;
	}

	public RebuspTProcedimentoDTO getProcedimentoByKey(Long key) {
		RebuspTProcedimentoDTO procedimento = rebuspTProcedimentoDAO.selectByPrimaryKey(key);
		return procedimento;
	}

	private void setConditionVisualizzazioneProcedimenti(ProcedimentoVO p) {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		p.setIsAbilitatoDettaglio(false);
		p.setIsAbilitatoElimina(false);
		p.setIsAbilitatoModifica(false);
		p.setIsAbilitatoScaricaPDFAzienda(false);
		p.setIsAbilitatoScaricaPDFEnte(false);
		RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
		rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(p.getIdProcedimento())
				.andIdStatoIterEqualTo(Long.valueOf(10));
		rebuspTIterProcedimentoSelector.setOrderByClause("data_inizio_validita");
		List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
				.selectByExample(rebuspTIterProcedimentoSelector);
		RebuspTIterProcedimentoDTO rebuspTIterProcedimentoDTO;
		boolean isAbilitatoElimina = false;
		if (!rebuspTIterProcedimentoDTOs.isEmpty()) {
			rebuspTIterProcedimentoDTO = rebuspTIterProcedimentoDTOs.get(0);
			if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.SERVIZIO.getId())) {
				if (!p.getIdStato().equals(Long.valueOf(60)) && !p.getIdStato().equals(Long.valueOf(70))) {
					p.setIsAbilitatoModifica(true);
				}
				if (p.getIdStato() != null
						&& (p.getIdStato().equals(Long.valueOf(10)) || p.getIdStato().equals(Long.valueOf(20)))) {
					p.setIsAbilitatoElimina(true);
				}
				if (p.getIdStato() != null && (p.getIdStato().equals(Long.valueOf(40)))) {
					p.setIsAbilitatoScaricaPDFAzienda(true);
				}
				if (p.getIdStato() != null && (p.getIdStato().equals(Long.valueOf(60)))) {
					p.setIsAbilitatoScaricaPDFEnte(true);
				}
				return;
			}
			SirtplRUtenteSogGiuridDTO utenteSoggGiuridico = sirtplRUtenteSogGiuridDAO
					.selectByPrimaryKey(rebuspTIterProcedimentoDTO.getIdUtenteSogGiurid());
			List<Long> soggGiuridico = procedimentoDAO.getSoggettoAbilitatoModifica(p.getIdProcedimento());
			if ((userInfo.getIdAzienda() != null
					&& userInfo.getIdAzienda().equals(utenteSoggGiuridico.getIdSoggettoGiuridico()))
					|| (userInfo.getIdEnte() != null
							&& userInfo.getIdEnte().equals(utenteSoggGiuridico.getIdSoggettoGiuridico()))) {
				p.setIsAbilitatoDettaglio(true);
				isAbilitatoElimina = true;
				if (p.getIdStato().equals(Long.valueOf(10)) || p.getIdStato().equals(Long.valueOf(20))
						|| p.getIdStato().equals(Long.valueOf(50))) {
					p.setIsAbilitatoModifica(true);
				}

			}
			if (!soggGiuridico.isEmpty() && (soggGiuridico.contains(userInfo.getIdAzienda())
					|| soggGiuridico.contains(userInfo.getIdEnte()))) {
				p.setIsAbilitatoModifica(true);
			}
		}

		Long enteCommittente = procedimentoDAO.getEnteAbilitatoModifica(p.getIdProcedimento());
		if (enteCommittente != null && userInfo.getIdEnte() != null
				&& enteCommittente.compareTo(userInfo.getIdEnte()) == 0) {
			p.setIsAbilitatoModifica(true);
		}

		if (p.getIdStato() != null
				&& (p.getIdStato().equals(Long.valueOf(10)) || p.getIdStato().equals(Long.valueOf(20)))
				&& isAbilitatoElimina) {
			p.setIsAbilitatoElimina(true);
		}
		Long soggGiuridicoAbilitatoScaricaPDF = null;
		Long enteAbilitatoScaricaPDF = null;
		if (p.getIdTipologia() != null && p.getIdTipologia().equals(new Long(2))) { // SOSTITUZIONE
			RebuspTSubProcedimentoSelector rebuspTSubProcedimentoSelector = new RebuspTSubProcedimentoSelector();
			rebuspTSubProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(p.getIdProcedimento());
			List<RebuspTSubProcedimentoKey> rebuspTSubProcedimentoKeys = rebuspTSubProcedimentoDAO
					.selectByExample(rebuspTSubProcedimentoSelector);
			if (rebuspTSubProcedimentoKeys != null && rebuspTSubProcedimentoKeys.size() > 0) {
				soggGiuridicoAbilitatoScaricaPDF = procedimentoDAO
						.getSoggettoAbilitatoScaricaPDF(rebuspTSubProcedimentoKeys.get(0).getIdSubProcedimento1());
				enteAbilitatoScaricaPDF = procedimentoDAO
						.getEnteAbilitatoScaricaPDF(rebuspTSubProcedimentoKeys.get(0).getIdSubProcedimento1());
			}
		} else {
			soggGiuridicoAbilitatoScaricaPDF = procedimentoDAO.getSoggettoAbilitatoScaricaPDF(p.getIdProcedimento());
			enteAbilitatoScaricaPDF = procedimentoDAO.getEnteAbilitatoScaricaPDF(p.getIdProcedimento());
		}

		if (soggGiuridicoAbilitatoScaricaPDF != null
				&& (soggGiuridicoAbilitatoScaricaPDF.equals(userInfo.getIdAzienda())
						|| soggGiuridicoAbilitatoScaricaPDF.equals(userInfo.getIdEnte()))) {
			p.setIsAbilitatoScaricaPDFAzienda(true);
		}

		if (enteAbilitatoScaricaPDF != null && (enteAbilitatoScaricaPDF.equals(userInfo.getIdAzienda())
				|| enteAbilitatoScaricaPDF.equals(userInfo.getIdEnte()))) {
			p.setIsAbilitatoScaricaPDFEnte(true);
		}
		if (p.getIdTipologia() != null && p.getIdTipologia().equals(new Long(7))) {
			p.setIsAbilitatoModifica(true);
		}

	}

	@Override
	@Transactional
	public Long inserisciRichiestaSostituzione(List<InserisciRichiestaVO> richiestaRequests) {
		if (richiestaRequests == null || richiestaRequests.size() == 0) {
			throw new InvalidParameterException("Richiesta non valorizzata");
		}
		boolean isAlienazioneReimmatricolazione = false;
		// check se sono stati selezionati gli stessi veicoli nei
		// sottoprocedimenti nel caso alienazione + reimmatricolazione
		List<Long> idVeicoli = new ArrayList<Long>();
		for (InserisciRichiestaVO richiestaRequest : richiestaRequests) {

			Long idTipoProcedimento = richiestaRequest.getTipoProcedimento().getId();
			if (idTipoProcedimento != 2) {
				if (idTipoProcedimento == 4) {
					isAlienazioneReimmatricolazione = true;
				}
				for (VeicoloVO veicolo : richiestaRequest.getVeicoli()) {
					idVeicoli.add(veicolo.getIdVariazAutobus());
				}
			}
		}
		if (isAlienazioneReimmatricolazione) {
			Set<Long> set = new HashSet<Long>(idVeicoli);

			if (set.size() < idVeicoli.size()) {
				throw new ErroreGestitoException(
						"ATTENZIONE !!! Almeno un veicolo e' stato selezionato per entrambi i sottoprocedimenti.",
						"VD");
			}
		}
		RebuspTSubProcedimentoKey rebuspTSubProcedimentoKey = new RebuspTSubProcedimentoKey();

		for (InserisciRichiestaVO richiestaRequest : richiestaRequests) {

			Long idTipoProcedimento = richiestaRequest.getTipoProcedimento().getId();
			Long idProcedimento = inserisciRichiesta(richiestaRequest);
			if (idTipoProcedimento == 2) {
				rebuspTSubProcedimentoKey.setIdProcedimento(idProcedimento);
			}
			if (idTipoProcedimento == 3) { // ALIENAZIONE
				rebuspTSubProcedimentoKey.setIdSubProcedimento1(idProcedimento);
			} else {
				rebuspTSubProcedimentoKey.setIdSubProcedimento2(idProcedimento);
			}

		}

		rebuspTSubProcedimentoDAO.insert(rebuspTSubProcedimentoKey);

		return rebuspTSubProcedimentoKey.getIdProcedimento();
	}

	@Override
	@Transactional
	public Long modificaRichiestaSostituzione(List<DettaglioRichiestaVO> richiestaRequests) {
		if (richiestaRequests == null || richiestaRequests.size() == 0) {
			throw new InvalidParameterException("Richiesta non valorizzata");
		}
		boolean isAlienazioneReimmatricolazione = false;
		// check se sono stati selezionati gli stessi veicoli nei
		// sottoprocedimenti nel caso alienazione + reimmatricolazione
		List<Long> idVeicoli = new ArrayList<Long>();
		for (DettaglioRichiestaVO richiestaRequest : richiestaRequests) {

			Long idTipoProcedimento = richiestaRequest.getIdTipoProcedimento();
			if (idTipoProcedimento != 2) {
				if (idTipoProcedimento == 4) {
					isAlienazioneReimmatricolazione = true;
				}
				for (VeicoloVO veicolo : richiestaRequest.getVeicoli()) {
					idVeicoli.add(veicolo.getIdVariazAutobus());
				}
			}
		}
		if (isAlienazioneReimmatricolazione) {
			Set<Long> set = new HashSet<Long>(idVeicoli);

			if (set.size() < idVeicoli.size()) {
				throw new ErroreGestitoException(
						"ATTENZIONE !!! Almeno un veicolo e' stato selezionato per entrambi i sottoprocedimenti.",
						"VD");
			}
		}
		for (DettaglioRichiestaVO richiestaRequest : richiestaRequests) {

			modificaRichiesta(richiestaRequest);

		}

		return new Long(1);
	}

	@Override
	public DettaglioRichiestaVO getFirmaProcedimento() {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		DettaglioRichiestaVO richiesta = new DettaglioRichiestaVO();
		if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
				|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
			RebuspTProcedimentoDTO rebuspTProcedimentoDTO = procedimentoDAO
					.getProcedimentoForFirma(userInfo.getIdAzienda());
			if (rebuspTProcedimentoDTO != null) {
				richiesta.setId(rebuspTProcedimentoDTO.getIdProcedimento());
				richiesta.setIdMotorizzazione(rebuspTProcedimentoDTO.getIdMotorizzazione());
				richiesta.setIdTipoProcedimento(rebuspTProcedimentoDTO.getIdTipoProcedimento());
				richiesta.setIdMotivazione(rebuspTProcedimentoDTO.getIdMotivazione());
				richiesta.setNoteMotivazione(rebuspTProcedimentoDTO.getTestoMotivAltro());
				richiesta.setRuoloFirma(rebuspTProcedimentoDTO.getRuoloFirma());
				richiesta.setNominativoFirma(rebuspTProcedimentoDTO.getNominativoFirma());
				richiesta.setNota(rebuspTProcedimentoDTO.getNote());
			}
		}
		return richiesta;
	}

	private Long getidStatoIterCorrente(Long idProcedimento) {
		Long idStatoIter = Long.valueOf(-1);
		RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
		rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento)
				.andDataFineValiditaIsNull();
		List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
				.selectByExample(rebuspTIterProcedimentoSelector);
		if (!rebuspTIterProcedimentoDTOs.isEmpty()) {
			idStatoIter = rebuspTIterProcedimentoDTOs.get(0).getIdStatoIter();
		}

		return idStatoIter;
	}

	@Override
	public List<VoceDiCostoVO> getVociDiCosto() {
		RebuscDVoceCostoSelector rebuspDVoceCostoSelector = new RebuscDVoceCostoSelector();
		rebuspDVoceCostoSelector.setOrderByClause("id_voce_costo");
		List<RebuscDVoceCostoDTO> rebuscDVoceCostoDTOs = rebuscDVoceCostoDAO.selectByExample(rebuspDVoceCostoSelector);
		List<VoceDiCostoVO> vociDiCosto = new ArrayList<>();
		for (RebuscDVoceCostoDTO dto : rebuscDVoceCostoDTOs) {
			vociDiCosto.add(voceDiCostoMapper.mapDTOtoVO(dto));
		}
		return vociDiCosto;
	}

	@Override
	public Long getNumProcedimento(Long idTipoProcedimento) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		String inizio = "01/01/";
		String fine = "31/12/";
		inizio += Integer.toString(calendar.get(Calendar.YEAR));
		fine += Integer.toString(calendar.get(Calendar.YEAR));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date inizioAnno = format.parse(inizio);
		Date fineAnno = format.parse(fine);
		Long numProcedimento = procedimentoDAO.countForNumProcedimento(inizioAnno, fineAnno, idTipoProcedimento);
		if (numProcedimento != null) {
			return numProcedimento + 1;
		} else {
			return Long.valueOf(1);
		}
	}

	@Override
	public ContrattoProcDatiVO getDatiContratto(Long idContratto, Long idProcedimento) {
		ContrattoProcDatiVO vo = new ContrattoProcDatiVO();
		SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO.selectByPrimaryKey(idContratto);
		vo.setCodRegionale("CDS" + String.format("%04d", sirtplcTContrattoDTO.getIdContratto()));
		vo.setCodIdNazionale(sirtplcTContrattoDTO.getCodIdNazionale());
		vo.setDescrizione(sirtplcTContrattoDTO.getDescContratto());
		// check se esiste un subentro per ente committente o esecutore titolare
		List<SirtplcRSostSogContrDTO> sostContr = getSubentroEnteCommittenteoEsecutoreTitolare(
				sirtplcTContrattoDTO.getIdContratto(), true);
		SoggettoContrattoVO sogg = new SoggettoContrattoVO();
		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO;
		// ente committente
		if (sostContr != null && sostContr.size() > 0) {
			sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
					.selectByPrimaryKey(sostContr.get(0).getIdSogGiuridCommittente());
			sogg.setId(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
			sogg.setCodiceOss(sirtplaTSoggettoGiuridicoDTO.getCodOsservatorioNaz());
			sogg.setDenomBreve(sirtplaTSoggettoGiuridicoDTO.getDenominazioneBreve());
			vo.setEnteComm(sogg);
		} else {
			sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
					.selectByPrimaryKey(sirtplcTContrattoDTO.getIdSogGiuridCommittente());
			sogg.setId(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
			sogg.setCodiceOss(sirtplaTSoggettoGiuridicoDTO.getCodOsservatorioNaz());
			sogg.setDenomBreve(sirtplaTSoggettoGiuridicoDTO.getDenominazioneBreve());
			vo.setEnteComm(sogg);
		}

		sogg = new SoggettoContrattoVO();
		sostContr = new ArrayList<SirtplcRSostSogContrDTO>();
		// esecutore titolare
		sostContr = getSubentroEnteCommittenteoEsecutoreTitolare(sirtplcTContrattoDTO.getIdContratto(), false);
		if (sostContr != null && sostContr.size() > 0) {
			sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
					.selectByPrimaryKey(sostContr.get(0).getIdSogGiuridEsecutore());
			sogg.setId(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
			sogg.setCodiceOss(sirtplaTSoggettoGiuridicoDTO.getCodOsservatorioNaz());
			sogg.setDenomBreve(sirtplaTSoggettoGiuridicoDTO.getDenominazioneBreve());
			vo.setEsecTit(sogg);
		} else {
			sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
					.selectByPrimaryKey(sirtplcTContrattoDTO.getIdSogGiuridEsecutore());
			sogg.setId(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
			sogg.setCodiceOss(sirtplaTSoggettoGiuridicoDTO.getCodOsservatorioNaz());
			sogg.setDenomBreve(sirtplaTSoggettoGiuridicoDTO.getDenominazioneBreve());
			vo.setEsecTit(sogg);
		}

		Long idSoggettoRichiedente = null;
		if (idProcedimento.equals(new Long(-1))) { // INSERIMENTO
			UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
			idSoggettoRichiedente = userInfo.getIdAzienda() != null ? userInfo.getIdAzienda() : userInfo.getIdEnte();
		} else { // MODIFICA
			RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
			rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(idProcedimento)
					.andIdStatoIterEqualTo(new Long(10)); // BOZZA
			rebuspTIterProcedimentoSelector.setOrderByClause("data_inizio_validita");
			List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
					.selectByExample(rebuspTIterProcedimentoSelector);
			RebuspTIterProcedimentoDTO iterDto = null;
			if (rebuspTIterProcedimentoDTOs != null && rebuspTIterProcedimentoDTOs.size() > 0) {
				iterDto = rebuspTIterProcedimentoDTOs.get(0);
			}
			if (iterDto != null) {
				SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDAO
						.selectByPrimaryKey(iterDto.getIdUtenteSogGiurid());
				if (sirtplRUtenteSogGiuridDTO != null) {
					idSoggettoRichiedente = sirtplRUtenteSogGiuridDTO.getIdSoggettoGiuridico();
				}
			}
		}
		sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO.selectByPrimaryKey(idSoggettoRichiedente);
		sogg = new SoggettoContrattoVO();
		sogg.setId(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
		sogg.setCodiceOss(sirtplaTSoggettoGiuridicoDTO.getCodOsservatorioNaz());
		sogg.setDenomBreve(sirtplaTSoggettoGiuridicoDTO.getDenominazioneBreve());
		vo.setSoggRichiedente(sogg);

		boolean isRichiedenteSubaffidataria = false;
		SirtplcRSostSogContrSelector sirtplcRSostSogContrSelector = new SirtplcRSostSogContrSelector();
		sirtplcRSostSogContrSelector.createCriteria().andIdContrattoEqualTo(idContratto)
				.andIdTipoSostituzioneEqualTo(new Long(1)).andIdSogGiuridCommittenteEqualTo(idSoggettoRichiedente);
		sirtplcRSostSogContrSelector.or().andIdContrattoEqualTo(idContratto).andIdTipoSostituzioneEqualTo(new Long(1))
				.andIdSogGiuridEsecutoreEqualTo(idSoggettoRichiedente);
		List<SirtplcRSostSogContrDTO> sirtplcRSostSogContrDTOs = sirtplcRSostSogContrDAO
				.selectByExample(sirtplcRSostSogContrSelector);

		final Long idTipoSostituzione = new Long(1);
		//query subaffidataria consorziata
		List<RichiestaPdfVO> subConsorziata = procedimentoDAO.selectSogContr(idContratto, idTipoSostituzione, idSoggettoRichiedente);

		//set default
		vo.setIsSubaffidataria(false);
		vo.setIsSubaffidatariaConsorziata(false);
		
		if (sirtplcRSostSogContrDTOs != null && sirtplcRSostSogContrDTOs.size() > 0) {
			isRichiedenteSubaffidataria = true;
			List<Long> idSubaffidatarieList = new ArrayList<>();
			for (SirtplcRSostSogContrDTO sg : sirtplcRSostSogContrDTOs) {
				idSubaffidatarieList.add(sg.getIdSogGiuridEsecutore() != null ? sg.getIdSogGiuridEsecutore() : null);
			}
			vo.setIsSubaffidataria(true);
			vo.setListaSubaffidatarie(idSubaffidatarieList);
		} 
		
		//controllo per subaffidatario consorizata
		
		else if (subConsorziata != null && subConsorziata.size() > 0) {
			vo.setIsSubaffidatariaConsorziata(true);
		} 

		if (!sirtplcTContrattoDTO.getIdTipoSogGiuridEsec().equals(new Long(3)) && isRichiedenteSubaffidataria) {
			vo.setSoggIntermediario(vo.getEsecTit());
		} else if (sirtplcTContrattoDTO.getIdTipoSogGiuridEsec().equals(new Long(3))) {
			SirtplcRContrattoRaggruppSelector sirtplcRContrattoRaggruppSelector = new SirtplcRContrattoRaggruppSelector();
			sirtplcRContrattoRaggruppSelector.createCriteria().andIdContrattoEqualTo(idContratto)
					.andCapofilaEqualTo(Boolean.TRUE);
			List<SirtplcRContrattoRaggruppDTO> sirtplcRContrattoRaggruppDTOs = sirtplcRContrattoRaggruppDAO
					.selectByExample(sirtplcRContrattoRaggruppSelector);
			if (sirtplcRContrattoRaggruppDTOs != null && sirtplcRContrattoRaggruppDTOs.size() == 1) {
				// check se esiste un subentro per il raggruppamento
				List<SirtplcRSostSogContrRaggrDTO> sostContrRaggr = getSubentroRaggruppamento(
						sirtplcRContrattoRaggruppDTOs.get(0).getIdContrattoRaggrupp());
				if (sostContrRaggr != null && sostContrRaggr.size() > 0) {
					sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
							.selectByPrimaryKey(sostContrRaggr.get(0).getIdSoggettoGiuridico());
					sogg = new SoggettoContrattoVO();
					sogg.setId(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
					sogg.setCodiceOss(sirtplaTSoggettoGiuridicoDTO.getCodOsservatorioNaz());
					sogg.setDenomBreve(sirtplaTSoggettoGiuridicoDTO.getDenominazioneBreve());
					vo.setCapofila(sogg);
					vo.setSoggIntermediario(sogg);
				} else {
					sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
							.selectByPrimaryKey(sirtplcRContrattoRaggruppDTOs.get(0).getIdSoggettoGiuridico());
					sogg = new SoggettoContrattoVO();
					sogg.setId(sirtplaTSoggettoGiuridicoDTO.getIdSoggettoGiuridico());
					sogg.setCodiceOss(sirtplaTSoggettoGiuridicoDTO.getCodOsservatorioNaz());
					sogg.setDenomBreve(sirtplaTSoggettoGiuridicoDTO.getDenominazioneBreve());
					vo.setCapofila(sogg);
					vo.setSoggIntermediario(sogg);
				}
			}
			SirtplcDTipoRaggruppamentoDTO sirtplcDTipoRaggruppamentoDTO = sirtplcDTipoRaggruppamentoDAO
					.selectByPrimaryKey(sirtplcTContrattoDTO.getIdTipoRaggrSogGiuridEsec());
			vo.setIdTipoRaggruppamento(sirtplcDTipoRaggruppamentoDTO.getIdTipoRaggruppamento());
			vo.setDescTipoRaggruppamento(sirtplcDTipoRaggruppamentoDTO.getDescTipoRaggruppamento());
		}

		return vo;
	}

	private boolean checkExistVeicoloTitolareContratto(Long tipoProcedimento, String primoTelaioVeicolo,
			Long idProcedimento) {

		if (tipoProcedimento == 2 || tipoProcedimento == 3 || tipoProcedimento == 4 || tipoProcedimento == 5) {
			if (procedimentoDAO.getVeicoliTitolariContratto(primoTelaioVeicolo, idProcedimento) > 0) {
				return true;
			}
		}

		return false;
	}

	private List<SirtplcRSostSogContrDTO> getSubentroEnteCommittenteoEsecutoreTitolare(Long idContratto,
			boolean isCommittente) {
		// check se esiste un subentro per ente committente o esecutore titolare
		SirtplcRSostSogContrSelector sostSelector = new SirtplcRSostSogContrSelector();
		if (isCommittente) {
			sostSelector.createCriteria().andIdContrattoEqualTo(idContratto)
					.andIdTipoSostituzioneEqualTo(Long.valueOf(2)).andIdSogGiuridCommittenteIsNotNull();
		} else {
			sostSelector.createCriteria().andIdContrattoEqualTo(idContratto)
					.andIdTipoSostituzioneEqualTo(Long.valueOf(2)).andIdSogGiuridEsecutoreIsNotNull();
		}
		sostSelector.setOrderByClause("id_sost_sog_contr DESC");
		return sirtplcRSostSogContrDAO.selectByExample(sostSelector);
	}

	private List<SirtplcRSostSogContrRaggrDTO> getSubentroRaggruppamento(Long idContrattoRaggrupp) {
		SirtplcRSostSogContrRaggrSelector sostRaggrSelector = new SirtplcRSostSogContrRaggrSelector();
		sostRaggrSelector.createCriteria().andIdContrattoRaggruppEqualTo(idContrattoRaggrupp)
				.andIdTipoSostituzioneEqualTo(Long.valueOf(2));
		sostRaggrSelector.setOrderByClause("id_sost_sog_contr_raggr DESC");
		return sirtplcRSostSogContrRaggrDAO.selectByExample(sostRaggrSelector);
	}

	private void checkVeicoliContrattiUsoInLinea(List<VeicoloVO> veicoli, List<Long> idTipiContratto) {
		boolean contrattoTitolare = false;
		if (idTipiContratto.contains(Long.valueOf(1))) {
			contrattoTitolare = true;
			List<RebuspRProcVeicoloDTO> veicoliResult = procedimentoDAO.checkVeicoliContrattiUsoInLinea(Long.valueOf(1),
					veicoli);
			if (veicoliResult != null && veicoliResult.size() > 0) {
				throw new ErroreGestitoException(
						"ATTENZIONE !!! Veicoli gia' selezionati come titolari in altri contratto", "VTUIL");
			}
		}
		if (!contrattoTitolare && idTipiContratto.contains(Long.valueOf(3))) {
			List<RebuspRProcVeicoloDTO> veicoliResult = procedimentoDAO.checkVeicoliContrattiUsoInLinea(Long.valueOf(1),
					veicoli);
			if (veicoliResult == null || (veicoliResult != null && veicoliResult.size() == 0)) {
				throw new ErroreGestitoException(
						"ATTENZIONE !!! Veicoli selezionati devono essere titolari in altri contratto", "VSUIL");
			}
		}

	}

}
