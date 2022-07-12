/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.map.ObjectMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.service.ContribuzioneService;
import it.csi.rebus.rebuscrus.business.service.ProcedimentiService;
import it.csi.rebus.rebuscrus.integration.dao.RebuscDTipoDocQuietanzaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscDTipoSostituzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscRDocContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscRDocDatoMessaServizioDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscRFatturaBonificoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscROggettoFatturaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscRVoceCostoFornituraDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTAssegnazioneRisorseDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTCostoFornituraDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTDatoBonificoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTDatoFatturaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTDatoMessaServizioDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTDatoSpesaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTFonteFinanziamentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuscTOrdineAcquistoDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ExcelDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDTipoDocQuietanzaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDTipoDocQuietanzaSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDTipoSostituzioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscDTipoSostituzioneSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRDocContribuzioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRDocContribuzioneSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRDocDatoMessaServizioKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRDocDatoMessaServizioSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRFatturaBonificoKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRFatturaBonificoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscROggettoFatturaKey;
import it.csi.rebus.rebuscrus.integration.dto.RebuscROggettoFatturaSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRVoceCostoFornituraDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscRVoceCostoFornituraSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTAssegnazioneRisorseDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTContribuzioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTContribuzioneSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTCostoFornituraDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoBonificoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoBonificoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoFatturaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoFatturaSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoMessaServizioDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTDatoSpesaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTFonteFinanziamentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTFonteFinanziamentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuscTOrdineAcquistoDTO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaContribuzioneDTO;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.CodeRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.AssegnazioneRisorseVO;
import it.csi.rebus.rebuscrus.vo.AttoAssegnazioneRisorseVO;
import it.csi.rebus.rebuscrus.vo.ContribuzioneCompletaVO;
import it.csi.rebus.rebuscrus.vo.ContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.CostoFornituraVO;
import it.csi.rebus.rebuscrus.vo.DatoBonificoVO;
import it.csi.rebus.rebuscrus.vo.DatoFatturaVO;
import it.csi.rebus.rebuscrus.vo.DatoMessaServizioVO;
import it.csi.rebus.rebuscrus.vo.DatoSpesaVO;
import it.csi.rebus.rebuscrus.vo.DocContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.DocDatoMessaServizioVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.FonteFinanziamentoVO;
import it.csi.rebus.rebuscrus.vo.InserisciRichiestaVO;
import it.csi.rebus.rebuscrus.vo.OrdineAcquistoVO;
import it.csi.rebus.rebuscrus.vo.ResponseStringVO;
import it.csi.rebus.rebuscrus.vo.TipoDocumentoQuietanzaVO;
import it.csi.rebus.rebuscrus.vo.TipoSostituzioneVO;
import it.csi.rebus.rebuscrus.vo.VoceCostoFornituraVO;
import it.csi.rebus.rebuscrus.vo.VoceDiCostoContribuzioneVO;

/**
 * @author francesco.mancuso
 * @date 22 nov 2021
 */

@Component
public class ContribuzioneServiceImpl implements ContribuzioneService {

	@Autowired
	private RebuscDTipoDocQuietanzaDAO rebuscDTipoDocQuietanzaDAO;

	@Autowired
	private RebuscDTipoSostituzioneDAO rebuscDTipoSostituzioneDAO;

	@Autowired
	private RebuscTAssegnazioneRisorseDAO rebuscTAssegnazioneRisorseDAO;

	@Autowired
	private RebuscTOrdineAcquistoDAO rebuscTOrdineAcquistoDAO;

	@Autowired
	private RebuscRVoceCostoFornituraDAO rebuscRVoceCostoFornituraDAO;

	@Autowired
	private RebuscTCostoFornituraDAO rebuscTCostoFornituraDAO;

	@Autowired
	private RebuscTDatoSpesaDAO rebuscTDatoSpesaDAO;

	@Autowired
	private ContribuzioneDAO contribuzioneDAO;

	@Autowired
	private RebuscTDatoMessaServizioDAO rebuscTDatoMessaServizioDAO;

	@Autowired
	private RebuscRDocDatoMessaServizioDAO rebuscRDocDatoMessaServizioDAO;

	@Autowired
	private RebuscTContribuzioneDAO rebuscTContribuzioneDAO;

	@Autowired
	private RebuscTDatoFatturaDAO rebuscTDatoFatturaDAO;

	@Autowired
	private RebuscROggettoFatturaDAO rebuscROggettoFatturaDAO;

	@Autowired
	private RebuscRDocContribuzioneDAO rebuscRDocContribuzioneDAO;

	@Autowired
	private RebuscTFonteFinanziamentoDAO rebuscTFonteFinanziamentoDAO;

	@Autowired
	private RebuscTDatoBonificoDAO rebuscTDatoBonificoDAO;

	@Autowired
	private RebuscRFatturaBonificoDAO rebuscRFatturaBonificoDAO;

	@Autowired
	private ProcedimentiService procedimentiService;

	@Autowired
	private Mapper dozerMapper;

	@Autowired
	private ExcelDAO excel;

	// Get DropDown Voce di costo Contribuzione
	@Override
	public List<VoceDiCostoContribuzioneVO> getAllVoceCostoContribuzione() {
		List<VoceDiCostoContribuzioneVO> rebusVoceCosto = contribuzioneDAO.getAllVociCosto();
		return rebusVoceCosto;
	}

	// Get DropDown Atto asseganzione risorse contribuzione
	@Override
	public List<AttoAssegnazioneRisorseVO> getAllAttoAssegnazioneRisorse() {
		List<AttoAssegnazioneRisorseVO> result = contribuzioneDAO.getAllAttoAssegnazione();
		return result;
	}

	// Get DropDown tipo documento quietanza
	@Override
	public List<TipoDocumentoQuietanzaVO> getAllTipoDocumentoQuietanza() {
		List<TipoDocumentoQuietanzaVO> result = new ArrayList<TipoDocumentoQuietanzaVO>();
		RebuscDTipoDocQuietanzaSelector sel = new RebuscDTipoDocQuietanzaSelector();
		List<RebuscDTipoDocQuietanzaDTO> rebuscDTipoDocQuietanza = rebuscDTipoDocQuietanzaDAO.selectByExample(sel);
		for (RebuscDTipoDocQuietanzaDTO rebuscDTipoDocQuietanzaDTO : rebuscDTipoDocQuietanza) {
			result.add(dozerMapper.map(rebuscDTipoDocQuietanzaDTO, TipoDocumentoQuietanzaVO.class));
		}
		return result;
	}

	// Get DropDown tipo sostituzione
	@Override
	public List<TipoSostituzioneVO> getAllTipoSostituzione() {
		List<TipoSostituzioneVO> result = new ArrayList<TipoSostituzioneVO>();
		RebuscDTipoSostituzioneSelector sel = new RebuscDTipoSostituzioneSelector();
		List<RebuscDTipoSostituzioneDTO> rebuscDTipoSostituzione = rebuscDTipoSostituzioneDAO.selectByExample(sel);
		for (RebuscDTipoSostituzioneDTO rebuscDTipoSostituzioneDTO : rebuscDTipoSostituzione) {
			result.add(dozerMapper.map(rebuscDTipoSostituzioneDTO, TipoSostituzioneVO.class));
		}
		return result;
	}

	// Get DropDown fonte di finanziamento by id atto assegnazione
	@Override
	public List<FonteFinanziamentoVO> getAllFonteFinanziamentoByIdAttoAssegnazione(Long idAttoAssegnazione) {
		List<FonteFinanziamentoVO> result = new ArrayList<FonteFinanziamentoVO>();
		List<FonteFinanziamentoVO> rebuscTFonteFinanziamento = contribuzioneDAO
				.selectByIdAttoAssegnazione(idAttoAssegnazione);
		for (FonteFinanziamentoVO rebuscTFonteFinanziamentoDTO : rebuscTFonteFinanziamento) {
			result.add(dozerMapper.map(rebuscTFonteFinanziamentoDTO, FonteFinanziamentoVO.class));
		}
		return result;
	}

	public List<FonteFinanziamentoVO> getAllFonteFinanziamento() {
		List<FonteFinanziamentoVO> result = new ArrayList<FonteFinanziamentoVO>();
		RebuscTFonteFinanziamentoSelector sel = new RebuscTFonteFinanziamentoSelector();
		List<RebuscTFonteFinanziamentoDTO> finanziamenti = rebuscTFonteFinanziamentoDAO.selectByExample(sel);
		for (RebuscTFonteFinanziamentoDTO finanziamento : finanziamenti) {
			result.add(dozerMapper.map(finanziamento, FonteFinanziamentoVO.class));
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertAssegnazioneRisorse(AssegnazioneRisorseVO assegnazioneRisorse) {
		RebuscTAssegnazioneRisorseDTO record = dozerMapper.map(assegnazioneRisorse,
				RebuscTAssegnazioneRisorseDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setDataAggiornamento(new Date());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		rebuscTAssegnazioneRisorseDAO.insertSelective(record);
		return record.getIdAssegnazioneRisorse();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertOrdineAcquisto(OrdineAcquistoVO ordineAcquisto) {
		RebuscTOrdineAcquistoDTO record = dozerMapper.map(ordineAcquisto, RebuscTOrdineAcquistoDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setDataAggiornamento(new Date());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		rebuscTOrdineAcquistoDAO.insertSelective(record);
		return record.getIdOrdineAcquisto();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public HashMap<Long, Long> insertVoceCostoFornitura(List<VoceCostoFornituraVO> voceCostoFornitura,
			Long idCostoFornitura) {
		HashMap<Long, Long> map = new HashMap<Long, Long>();
		for (VoceCostoFornituraVO voce : voceCostoFornitura) {
			RebuscRVoceCostoFornituraDTO record = dozerMapper.map(voce, RebuscRVoceCostoFornituraDTO.class);
			record.setIdCostoFornitura(idCostoFornitura);
			rebuscRVoceCostoFornituraDAO.insertSelective(record);
			map.put(record.getIdVoceCosto(), record.getIdVoceCostoFornitura());
		}

		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertCostoFornitura(CostoFornituraVO costoFornitura, Long idDocCostoFornitura) {
		RebuscTCostoFornituraDTO record = dozerMapper.map(costoFornitura, RebuscTCostoFornituraDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setDataAggiornamento(new Date());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		record.setIdDocContribuzione(idDocCostoFornitura);
		rebuscTCostoFornituraDAO.insertSelective(record);
		return record.getIdCostoFornitura();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertDatoSpesa(DatoSpesaVO datoSpesa) {
		RebuscTDatoSpesaDTO record = dozerMapper.map(datoSpesa, RebuscTDatoSpesaDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		record.setDataAggiornamento(new Date());
		rebuscTDatoSpesaDAO.insertSelective(record);
		return record.getIdDatoSpesa();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertDatoMessaServizio(DatoMessaServizioVO datoMessaServizio) {
		RebuscTDatoMessaServizioDTO record = dozerMapper.map(datoMessaServizio, RebuscTDatoMessaServizioDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		record.setDataAggiornamento(new Date());
		rebuscTDatoMessaServizioDAO.insertSelective(record);
		return record.getIdDatoMessaServizio();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertDocDatoMessaServizio(Long idDatoMessaServizio, Long idDocumento) {
		RebuscRDocDatoMessaServizioKey record = new RebuscRDocDatoMessaServizioKey();
		record.setIdDatoMessaServizio(idDatoMessaServizio);
		record.setIdDocContribuzione(idDocumento);
		rebuscRDocDatoMessaServizioDAO.insert(record);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertDatoFattura(DatoFatturaVO datoFattura, Long idDoc) {
		RebuscTDatoFatturaDTO record = dozerMapper.map(datoFattura, RebuscTDatoFatturaDTO.class);
		record.setIdDocContribuzione(idDoc);
		rebuscTDatoFatturaDAO.insertSelective(record);
		return record.getIdDatoFattura();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertOggettoFattura(Long idDatoFattura, Long idVoceCostoFornitura) {
		RebuscROggettoFatturaKey record = new RebuscROggettoFatturaKey();
		record.setIdDatoFattura(idDatoFattura);
		record.setIdVoceCostoFornitura(idVoceCostoFornitura);
		rebuscROggettoFatturaDAO.insertSelective(record);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertDocContribuzione(DocContribuzioneVO datiDocumento) {
		ObjectMapper objectMapper = new ObjectMapper();
		RebuscRDocContribuzioneDTO record = new RebuscRDocContribuzioneDTO();
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setNomeFile(datiDocumento.getNomeFile());
		try {
			record.setDocumento(objectMapper.readValue(datiDocumento.getDocumento(), byte[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		record.setIdTipoDocumento(datiDocumento.getIdTipoDocumento());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		record.setDataCaricamento(new Date());
		rebuscRDocContribuzioneDAO.insertSelective(record);
		return record.getIdDocContribuzione();
	}

	// PER OTTERENE I DOC NEL FE RESTITUIRE SOLO L'ARRAI DI BYTE
	@Override
	public DocContribuzioneVO getDocumentoById(Long id) {
		RebuscRDocContribuzioneDTO record = rebuscRDocContribuzioneDAO.selectByPrimaryKey(id);
		DocContribuzioneVO doc = dozerMapper.map(record, DocContribuzioneVO.class);
		return doc;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long insertContribuzione(ContribuzioneCompletaVO contribuzioneCompleta,
			InserisciRichiestaVO richiestaRequest) {
		Long idAssegnazioneRisorse;
		Long idOrdineAcquisto;
		Long idCostoFornitura;
		Long idDatoSpesa;
		Long idDatoMessaServizio;
		Long idContribuzione;
		Long idDocContrattuale = null;
		Long idProcedimento = null;
		try {
			idProcedimento = procedimentiService.inserisciRichiesta(richiestaRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Salvo gli oggetti assegnazione risorse, ordine acquisto dato spesa e dato
		// messa servizio e salvo gli id da inserire nell'oggetto
		// contribuzione
		idAssegnazioneRisorse = insertAssegnazioneRisorse(contribuzioneCompleta.getAssegnazioneRisorse());
		idOrdineAcquisto = insertOrdineAcquisto(contribuzioneCompleta.getOrdineAcquisto());
		idDatoSpesa = insertDatoSpesa(contribuzioneCompleta.getDatoSpesa());
//		 Se presente inserisco il documento e poi procedo con l'inserimento del costo
//		 fornitura
//		 INSERT DOCUMENTO CONTRIBUZIONE
		if (contribuzioneCompleta.getDocumentoContribuzione().getNomeFile() != null) {
			idDocContrattuale = insertDocContribuzione(contribuzioneCompleta.getDocumentoContribuzione());
		}

		HashMap<Long, Long> vociCosto;
		// inserisco il costo fronitura
		idCostoFornitura = insertCostoFornitura(contribuzioneCompleta.getCostoFornitura(), idDocContrattuale);
		idDatoSpesa = insertDatoSpesa(contribuzioneCompleta.getDatoSpesa());
		if (!contribuzioneCompleta.getVociCosto().isEmpty()) {
			// se le voci costo sono piene faccio l'insert che mi restituisce un hash map
			// key: id_voce_costo, value: id_voce_costo_fornitura
			vociCosto = insertVoceCostoFornitura(contribuzioneCompleta.getVociCosto(), idCostoFornitura);

			if (!contribuzioneCompleta.getDatiFattura().isEmpty()) {
				List<DatoFatturaVO> datiFattura = contribuzioneCompleta.getDatiFattura();
				// ciclo i dati fattura e salvo il documento
				for (DatoFatturaVO dato : datiFattura) {
					Long idDocumento = null;
					if (dato.getDocumento().getDocumento() != null) {
						idDocumento = insertDocContribuzione(dato.getDocumento());
					}
					// setto l'id_dato_spesa e inserisco il dato fattura
					dato.setIdDatoSpesa(idDatoSpesa);
					Long idDatoFattura = insertDatoFattura(dato, idDocumento);
					if (!dato.getListaBonifici().isEmpty()) {
						insertDatoBonificoAndFatturaBonifico(dato.getListaBonifici(), idDatoFattura);
					}
					// INSERIRE LA PARTE DEI BONIFICI
					if (!vociCosto.isEmpty() && !dato.getListaIdVoceCosto().isEmpty()) {
						// mi riprendo l'id_voce_costo_fornitura confrontando la key con l'id_voce costo
						// che ho in dato fattura
						for (Long idVoceCosto : dato.getListaIdVoceCosto()) {
							Long idVoceCostoFornitura = vociCosto.get(idVoceCosto);
							// inserisco l'oggeto fattura passando l'id_dato_fattura e l'id_voce_costo
							insertOggettoFattura(idDatoFattura, idVoceCostoFornitura);
						}
					}
				}
			} 
		} else if (!contribuzioneCompleta.getDatiFattura().isEmpty()) {
			for (DatoFatturaVO dato : contribuzioneCompleta.getDatiFattura()) {
				Long idDocumento = null;
				if (dato.getDocumento().getDocumento() != null) {
					idDocumento = insertDocContribuzione(dato.getDocumento());
				}
				dato.setIdDatoSpesa(idDatoSpesa);
				Long idDatoFattura = insertDatoFattura(dato, idDocumento);
			}
		}

		idDatoMessaServizio = insertDatoMessaServizio(contribuzioneCompleta.getDatoMessaServizio());

		// INSERT DOCUMENTO ATTO D'OBBLIGO
		if (contribuzioneCompleta.getDocumentoAttoObbligo().getNomeFile() != null) {
			Long idDocAttoObbligo = insertDocContribuzione(contribuzioneCompleta.getDocumentoAttoObbligo());
			insertDocDatoMessaServizio(idDatoMessaServizio, idDocAttoObbligo);
		}

		// INSERT DOCUMENTO GARANZIA
		if (contribuzioneCompleta.getDocumentoGaranzia().getNomeFile() != null) {
			Long idDocGaranzia = insertDocContribuzione(contribuzioneCompleta.getDocumentoGaranzia());
			insertDocDatoMessaServizio(idDatoMessaServizio, idDocGaranzia);
		}

		// INSERIRE CHIAMATA PER INSERIMENTO DOCUMENTI MESSA IN SERVIZIO(TRE DOC)
		// INSERIRE CHIAMATA PER DOC MESSA SERVIZIO DOVE VANNO INSERITI ID DOC + ID
		// MESSA IN SERVIZIO
		ContribuzioneVO contribuzione = contribuzioneCompleta.getContribuzione();
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		contribuzione.setIdUtenteAggiornamento(userInfo.getIdUtente());
		contribuzione.setDataAggiornamento(new Date());
		contribuzione.setIdAssegnazioneRisorse(idAssegnazioneRisorse);
		contribuzione.setIdOrdineAcquisto(idOrdineAcquisto);
		contribuzione.setIdCostoFornitura(idCostoFornitura);
		contribuzione.setIdDatoSpesa(idDatoSpesa);
		contribuzione.setIdDatoMessaServizio(idDatoMessaServizio);
		contribuzione.setIdProcedimento(idProcedimento);
		RebuscTContribuzioneDTO record = dozerMapper.map(contribuzione, RebuscTContribuzioneDTO.class);
		rebuscTContribuzioneDAO.insertSelective(record);
		return record.getIdContribuzione();
	}

	@Override
	public List<VExportRicercaContribuzioneDTO> filtraElencoContribuzione(FiltroContribuzioneVO filtro) {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_CONTRIBUZIONE);
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

		List<VExportRicercaContribuzioneDTO> lista = contribuzioneDAO.getElencoContribuzione(filtro);


		return lista;

	}

	@Override
	public List<VExportRicercaContribuzioneDTO> elencoContribuzione() {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.RICERCA_CONTRIBUZIONE);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		FiltroContribuzioneVO filtro = new FiltroContribuzioneVO();
		filtro.setFlagStatoCorrente("S");
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

		List<VExportRicercaContribuzioneDTO> lista = contribuzioneDAO.getElencoContribuzione(filtro);

		return lista;

	}

	// GET CONTRIBUZIONE

	@Override
	public ContribuzioneVO getContribuzioneByIdProcedimento(Long idProcedimento) {
		ContribuzioneVO result = new ContribuzioneVO();
		try {
			RebuscTContribuzioneSelector selector = new RebuscTContribuzioneSelector();
			selector.createCriteria().andIdProcedimentoEqualTo(idProcedimento);
			List<RebuscTContribuzioneDTO> listContribuzione = rebuscTContribuzioneDAO.selectByExample(selector);
			if (!listContribuzione.isEmpty()) {
				RebuscTContribuzioneDTO rebuscTContribuzione = listContribuzione.get(0);
				if (result != null) {
					result = dozerMapper.map(rebuscTContribuzione, ContribuzioneVO.class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ContribuzioneVO getContribuzioneById(Long idContribuzione) {
		ContribuzioneVO result = new ContribuzioneVO();
		try {
			RebuscTContribuzioneDTO contribuzione = rebuscTContribuzioneDAO.selectByPrimaryKey(idContribuzione);
			result = dozerMapper.map(contribuzione, ContribuzioneVO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public AssegnazioneRisorseVO getAssegnazioneRisorseById(Long idAassegnazioneRisorse) {
		AssegnazioneRisorseVO result = new AssegnazioneRisorseVO();
		RebuscTAssegnazioneRisorseDTO rebuscTAssegnazioneRisorse = rebuscTAssegnazioneRisorseDAO
				.selectByPrimaryKey(idAassegnazioneRisorse);
		result = dozerMapper.map(rebuscTAssegnazioneRisorse, AssegnazioneRisorseVO.class);
		return result;
	}

	@Override
	public OrdineAcquistoVO getOrdineAcquistoById(Long idOrdineAcquisto) {
		OrdineAcquistoVO result = new OrdineAcquistoVO();
		RebuscTOrdineAcquistoDTO rebuscTOrdineAcquistoDTO = rebuscTOrdineAcquistoDAO
				.selectByPrimaryKey(idOrdineAcquisto);
		result = dozerMapper.map(rebuscTOrdineAcquistoDTO, OrdineAcquistoVO.class);
		return result;
	}

	@Override
	public CostoFornituraVO getCostoFornituraById(Long idCostoFornitura) {
		CostoFornituraVO result = new CostoFornituraVO();
		RebuscTCostoFornituraDTO rebuscTCostoFornituraDTO = rebuscTCostoFornituraDAO
				.selectByPrimaryKey(idCostoFornitura);
		result = dozerMapper.map(rebuscTCostoFornituraDTO, CostoFornituraVO.class);
		return result;
	}

	@Override
	public List<VoceCostoFornituraVO> getVoceCostoFornituraByIdCostoFornitura(Long idCostoFornitura) {
		List<VoceCostoFornituraVO> result = new ArrayList<VoceCostoFornituraVO>();
		RebuscRVoceCostoFornituraSelector selector = new RebuscRVoceCostoFornituraSelector();
		selector.createCriteria().andIdCostoFornituraEqualTo(idCostoFornitura);
		List<RebuscRVoceCostoFornituraDTO> listaVociCosto = rebuscRVoceCostoFornituraDAO.selectByExample(selector);
		if (!listaVociCosto.isEmpty()) {
			for (RebuscRVoceCostoFornituraDTO rebuscRVoceCostoFornituraDTO : listaVociCosto) {
				result.add(dozerMapper.map(rebuscRVoceCostoFornituraDTO, VoceCostoFornituraVO.class));
			}
		}
		return result;
	}

	@Override
	public DatoSpesaVO getDatoSpesaById(Long idDatoSpesa) {
		DatoSpesaVO result = new DatoSpesaVO();
		RebuscTDatoSpesaDTO rebuscTDatoSpesaDTO = rebuscTDatoSpesaDAO.selectByPrimaryKey(idDatoSpesa);
		result = dozerMapper.map(rebuscTDatoSpesaDTO, DatoSpesaVO.class);
		return result;
	}

	@Override
	public byte[] getDocContribuzione(Long idDocumento) {
		if (idDocumento == null || idDocumento == null)
			throw new InvalidParameterException();

		// recupero da DB
		RebuscRDocContribuzioneSelector selector = new RebuscRDocContribuzioneSelector();
		selector.createCriteria().andIdDocContribuzioneEqualTo(idDocumento);

		List<RebuscRDocContribuzioneDTO> fileDTO = rebuscRDocContribuzioneDAO.selectByExampleWithBLOBs(selector);
		if (fileDTO == null || fileDTO.size() < 1) {
			throw new InvalidParameterException("id documento e tipo non validi");
		}
		return fileDTO.get(0).getDocumento();
	}

	@Override
	public DocContribuzioneVO getInfoDocContribuzione(Long idDocumento) {
		DocContribuzioneVO result = contribuzioneDAO.selectDocContribuzioneForInfo(idDocumento);
		return result;
	}

	@Override
	public DatoMessaServizioVO getDatoMessaServizioById(Long idDatoMessaServizio) {
		DatoMessaServizioVO result = new DatoMessaServizioVO();
		RebuscTDatoMessaServizioDTO rebuscTDatoMessaServizioDTO = rebuscTDatoMessaServizioDAO
				.selectByPrimaryKey(idDatoMessaServizio);
		result = dozerMapper.map(rebuscTDatoMessaServizioDTO, DatoMessaServizioVO.class);
		return result;
	}

	@Override
	public List<DocDatoMessaServizioVO> getDocDatoMessaServizioByIdDatoMessaServizio(Long idDatoMessaServizio) {
		List<DocDatoMessaServizioVO> result = new ArrayList<DocDatoMessaServizioVO>();
		RebuscRDocDatoMessaServizioSelector selector = new RebuscRDocDatoMessaServizioSelector();
		selector.createCriteria().andIdDatoMessaServizioEqualTo(idDatoMessaServizio);
		List<RebuscRDocDatoMessaServizioKey> list = rebuscRDocDatoMessaServizioDAO.selectByExample(selector);
		for (RebuscRDocDatoMessaServizioKey datoMessaServizioKey : list) {
			result.add(dozerMapper.map(datoMessaServizioKey, DocDatoMessaServizioVO.class));
		}
		return result;
	}

	@Override
	public List<DatoFatturaVO> getDatoFatturaByIdDatoSpesa(Long idDatoSpesa) {
		List<DatoFatturaVO> result = new ArrayList<DatoFatturaVO>();
		RebuscTDatoFatturaSelector selector = new RebuscTDatoFatturaSelector();
		selector.createCriteria().andIdDatoSpesaEqualTo(idDatoSpesa);
		List<RebuscTDatoFatturaDTO> list = rebuscTDatoFatturaDAO.selectByExample(selector);
		for (RebuscTDatoFatturaDTO rebuscTDatoFatturaDTO2 : list) {
			result.add(dozerMapper.map(rebuscTDatoFatturaDTO2, DatoFatturaVO.class));
		}
		return result;
	}

	@Override
	public List<Long> getOggettoFatturaByIdDatoFattura(Long idDatoFattura) {
		List<Long> result = new ArrayList<Long>();
		RebuscROggettoFatturaSelector selector = new RebuscROggettoFatturaSelector();
		selector.createCriteria().andIdDatoFatturaEqualTo(idDatoFattura);
		List<RebuscROggettoFatturaKey> list = rebuscROggettoFatturaDAO.selectByExample(selector);
		for (RebuscROggettoFatturaKey rebuscROggettoFatturaKey : list) {
			result.add(rebuscROggettoFatturaKey.getIdVoceCostoFornitura());
		}
		return result;
	}

	@Override
	public ContribuzioneCompletaVO getContribuzioneCompletaById(Long idContribuzione) {
		ContribuzioneVO contribuzione = getContribuzioneById(idContribuzione);
		ContribuzioneCompletaVO contribuzioneCompleta = new ContribuzioneCompletaVO();
		if (contribuzione == null) {
			return null;
		} else {
			contribuzioneCompleta.setContribuzione(contribuzione);
		}

		if (contribuzione.getIdAssegnazioneRisorse() != null) {
			contribuzioneCompleta
					.setAssegnazioneRisorse(getAssegnazioneRisorseById(contribuzione.getIdAssegnazioneRisorse()));
		} else {
			contribuzioneCompleta.setAssegnazioneRisorse(new AssegnazioneRisorseVO());
		}

		if (contribuzione.getIdOrdineAcquisto() != null) {
			contribuzioneCompleta.setOrdineAcquisto(getOrdineAcquistoById(contribuzione.getIdOrdineAcquisto()));
		} else {
			contribuzioneCompleta.setOrdineAcquisto(new OrdineAcquistoVO());
		}

		if (contribuzione.getIdCostoFornitura() != null) {
			contribuzioneCompleta.setCostoFornitura(getCostoFornituraById(contribuzione.getIdCostoFornitura()));
			if (contribuzioneCompleta.getCostoFornitura().getIdDocContribuzione() != null) {
				contribuzioneCompleta.setDocumentoContribuzione(
						getInfoDocContribuzione(contribuzioneCompleta.getCostoFornitura().getIdDocContribuzione()));
			} else {
				contribuzioneCompleta.setDocumentoContribuzione(null);
			}
		} else {
			contribuzioneCompleta.setCostoFornitura(new CostoFornituraVO());
			contribuzioneCompleta.setDocumentoContribuzione(null);
		}

		if (contribuzione.getIdCostoFornitura() != null) {
			contribuzioneCompleta
					.setVociCosto(getVoceCostoFornituraByIdCostoFornitura(contribuzione.getIdCostoFornitura()));
		} else {
			contribuzioneCompleta.setVociCosto(new ArrayList<VoceCostoFornituraVO>());
		}

		if (contribuzione.getIdDatoSpesa() != null) {
			contribuzioneCompleta.setDatoSpesa(getDatoSpesaById(contribuzione.getIdDatoSpesa()));
			contribuzioneCompleta.setDatiFattura(getDatoFatturaByIdDatoSpesa(contribuzione.getIdDatoSpesa()));
			if (!contribuzioneCompleta.getDatiFattura().isEmpty()) {
				for (DatoFatturaVO datoFattura : contribuzioneCompleta.getDatiFattura()) {
					datoFattura.setDocumento(getInfoDocContribuzione(datoFattura.getIdDocContribuzione()));
					datoFattura.setListaIdVoceCosto(getOggettoFatturaByIdDatoFattura(datoFattura.getIdDatoFattura()));
					if (datoFattura.getIdTipoDocQuietanza() == null) {
						// EFFETUO LA RICERCA DEI BONIFICI TRAMITE L'ID DATO FATTURA
						// E INSERISCO IL RIUSLTATO NELLA LISTA BONIFICI
						List<DatoBonificoVO> list = contribuzioneDAO
								.getDatoBonficoByIdDatoFattura(datoFattura.getIdDatoFattura());
						datoFattura.setListaBonifici(list);
					}
				}
			}
		} else {
			contribuzioneCompleta.setDatiFattura(new ArrayList<DatoFatturaVO>());
		}

		if (contribuzione.getIdDatoMessaServizio() != null) {
			contribuzioneCompleta
					.setDatoMessaServizio(getDatoMessaServizioById(contribuzione.getIdDatoMessaServizio()));
			List<DocDatoMessaServizioVO> listDocDatoMessaServizio = getDocDatoMessaServizioByIdDatoMessaServizio(
					contribuzione.getIdDatoMessaServizio());
			for (DocDatoMessaServizioVO docDatoMessaServizio : listDocDatoMessaServizio) {
				DocContribuzioneVO documento = getInfoDocContribuzione(docDatoMessaServizio.getIdDocContribuzione());
				switch (Math.toIntExact(documento.getIdTipoDocumento())) {
				case 1:
					contribuzioneCompleta.setDocumentoCartaCircolazione(documento);
					break;
				case 43:
					contribuzioneCompleta.setDocumentoGaranzia(documento);
					break;
				case 44:
					contribuzioneCompleta.setDocumentoAttoObbligo(documento);
					break;
				case 45:
					contribuzioneCompleta.setDocumentoAlienazione(documento);
					break;
				case 46:
					contribuzioneCompleta.setDocumentoMisureEmissioni(documento);
					break;
				default:
					break;
				}
			}
		} else {
			contribuzioneCompleta.setDatoMessaServizio(new DatoMessaServizioVO());
		}

		return contribuzioneCompleta;
	}

	@Override
	public ContribuzioneCompletaVO getContribuzioneCompletaByIdProcedimento(Long idProcedimento) {
		ContribuzioneVO contribuzione = getContribuzioneByIdProcedimento(idProcedimento);
		ContribuzioneCompletaVO contribuzioneCompleta = new ContribuzioneCompletaVO();
		if (contribuzione == null) {
			return null;
		} else {
			contribuzioneCompleta.setContribuzione(contribuzione);
		}

		if (contribuzione.getIdAssegnazioneRisorse() != null) {
			contribuzioneCompleta
					.setAssegnazioneRisorse(getAssegnazioneRisorseById(contribuzione.getIdAssegnazioneRisorse()));
		} else {
			contribuzioneCompleta.setAssegnazioneRisorse(new AssegnazioneRisorseVO());
		}

		if (contribuzione.getIdOrdineAcquisto() != null) {
			contribuzioneCompleta.setOrdineAcquisto(getOrdineAcquistoById(contribuzione.getIdOrdineAcquisto()));
		} else {
			contribuzioneCompleta.setOrdineAcquisto(new OrdineAcquistoVO());
		}

		if (contribuzione.getIdCostoFornitura() != null) {
			contribuzioneCompleta.setCostoFornitura(getCostoFornituraById(contribuzione.getIdCostoFornitura()));
			if (contribuzioneCompleta.getCostoFornitura().getIdDocContribuzione() != null) {
				contribuzioneCompleta.setDocumentoContribuzione(
						getInfoDocContribuzione(contribuzioneCompleta.getCostoFornitura().getIdDocContribuzione()));
			} else {
				contribuzioneCompleta.setDocumentoContribuzione(new DocContribuzioneVO());
			}
		} else {
			contribuzioneCompleta.setCostoFornitura(new CostoFornituraVO());
			contribuzioneCompleta.setDocumentoContribuzione(new DocContribuzioneVO());
		}

		if (contribuzione.getIdCostoFornitura() != null) {
			contribuzioneCompleta
					.setVociCosto(getVoceCostoFornituraByIdCostoFornitura(contribuzione.getIdCostoFornitura()));
		} else {
			contribuzioneCompleta.setVociCosto(new ArrayList<VoceCostoFornituraVO>());
		}

		if (contribuzione.getIdDatoSpesa() != null) {
			contribuzioneCompleta.setDatoSpesa(getDatoSpesaById(contribuzione.getIdDatoSpesa()));
			contribuzioneCompleta.setDatiFattura(getDatoFatturaByIdDatoSpesa(contribuzione.getIdDatoSpesa()));
			if (!contribuzioneCompleta.getDatiFattura().isEmpty()) {
				for (DatoFatturaVO datoFattura : contribuzioneCompleta.getDatiFattura()) {
					datoFattura.setDocumento(getInfoDocContribuzione(datoFattura.getIdDocContribuzione()));
					datoFattura.setListaIdVoceCosto(getOggettoFatturaByIdDatoFattura(datoFattura.getIdDatoFattura()));
					if (datoFattura.getIdTipoDocQuietanza() == null) {
						// EFFETUO LA RICERCA DEI BONIFICI TRAMITE L'ID DATO FATTURA
						// E INSERISCO IL RIUSLTATO NELLA LISTA BONIFICI
						List<DatoBonificoVO> list = contribuzioneDAO
								.getDatoBonficoByIdDatoFattura(datoFattura.getIdDatoFattura());
						datoFattura.setListaBonifici(list);
					}
				}
			}
		} else {
			contribuzioneCompleta.setDatiFattura(new ArrayList<DatoFatturaVO>());
		}

		if (contribuzione.getIdDatoMessaServizio() != null) {
			contribuzioneCompleta
					.setDatoMessaServizio(getDatoMessaServizioById(contribuzione.getIdDatoMessaServizio()));
			List<DocDatoMessaServizioVO> listDocDatoMessaServizio = getDocDatoMessaServizioByIdDatoMessaServizio(
					contribuzione.getIdDatoMessaServizio());
			for (DocDatoMessaServizioVO docDatoMessaServizio : listDocDatoMessaServizio) {
				DocContribuzioneVO documento = getInfoDocContribuzione(docDatoMessaServizio.getIdDocContribuzione());
				switch (Math.toIntExact(documento.getIdTipoDocumento())) {
				case 1:
					contribuzioneCompleta.setDocumentoCartaCircolazione(documento);
					break;
				case 43:
					contribuzioneCompleta.setDocumentoGaranzia(documento);
					break;
				case 44:
					contribuzioneCompleta.setDocumentoAttoObbligo(documento);
					break;
				case 45:
					contribuzioneCompleta.setDocumentoAlienazione(documento);
					break;
				case 46:
					contribuzioneCompleta.setDocumentoMisureEmissioni(documento);
					break;
				default:
					break;
				}
			}
		} else {
			contribuzioneCompleta.setDatoMessaServizio(new DatoMessaServizioVO());
		}

		return contribuzioneCompleta;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateAssegnazioneRisorse(AssegnazioneRisorseVO assegnazioneRisorse) {
		RebuscTAssegnazioneRisorseDTO record = dozerMapper.map(assegnazioneRisorse,
				RebuscTAssegnazioneRisorseDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setDataAggiornamento(new Date());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		rebuscTAssegnazioneRisorseDAO.updateByPrimaryKey(record);
		return record.getIdAssegnazioneRisorse();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateOrdineAcquisto(OrdineAcquistoVO ordineAcquisto) {
		RebuscTOrdineAcquistoDTO record = dozerMapper.map(ordineAcquisto, RebuscTOrdineAcquistoDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setDataAggiornamento(new Date());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		rebuscTOrdineAcquistoDAO.updateByPrimaryKey(record);
		return record.getIdOrdineAcquisto();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateDatoSpesa(DatoSpesaVO datoSpesa) {
		RebuscTDatoSpesaDTO record = dozerMapper.map(datoSpesa, RebuscTDatoSpesaDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		record.setDataAggiornamento(new Date());
		rebuscTDatoSpesaDAO.updateByPrimaryKey(record);
		return record.getIdDatoSpesa();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateCostoFornitura(CostoFornituraVO costoFornitura) {
		RebuscTCostoFornituraDTO record = dozerMapper.map(costoFornitura, RebuscTCostoFornituraDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setDataAggiornamento(new Date());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		rebuscTCostoFornituraDAO.updateByPrimaryKey(record);
		return record.getIdCostoFornitura();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateDatoMessaServizio(DatoMessaServizioVO datoMessaServizio) {
		RebuscTDatoMessaServizioDTO record = dozerMapper.map(datoMessaServizio, RebuscTDatoMessaServizioDTO.class);
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		record.setDataAggiornamento(new Date());
		rebuscTDatoMessaServizioDAO.updateByPrimaryKey(record);
		return record.getIdDatoMessaServizio();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDocDatoMessaServizio(Long oldDocumentId, DocContribuzioneVO newDocument,
			Long IdDatoMessaServizio) {
		// elimino la voce della tabella doc_data_messa_servizio tramite l'id del
		// vecchio documento
		RebuscRDocDatoMessaServizioSelector selector = new RebuscRDocDatoMessaServizioSelector();
		selector.createCriteria().andIdDocContribuzioneEqualTo(oldDocumentId);
		rebuscRDocDatoMessaServizioDAO.deleteByExample(selector);
		
//		Elimino il vecchio documento
		rebuscRDocContribuzioneDAO.deleteByPrimaryKey(oldDocumentId);

//		Se il nome del file e diverso da null vuol dire che il documento e stato modificato.
		if (newDocument.getNomeFile() != null) {
			// inserisco il nuovo documento
			Long idDoc = insertDocContribuzione(newDocument);
			// inserisco la nuova voce di messa servizio
			insertDocDatoMessaServizio(IdDatoMessaServizio, idDoc);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateDocContribuzione(DocContribuzioneVO datiDocumento) {
		ObjectMapper objectMapper = new ObjectMapper();
		RebuscRDocContribuzioneDTO record = new RebuscRDocContribuzioneDTO();
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		record.setNomeFile(datiDocumento.getNomeFile());
		try {
			record.setDocumento(objectMapper.readValue(datiDocumento.getDocumento(), byte[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		record.setIdDocContribuzione(datiDocumento.getIdDocContribuzione());
		record.setIdTipoDocumento(datiDocumento.getIdTipoDocumento());
		record.setIdUtenteAggiornamento(userInfo.getIdUtente());
		record.setDataCaricamento(new Date());
		rebuscRDocContribuzioneDAO.updateByPrimaryKeyWithBLOBs(record);
		return record.getIdDocContribuzione();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long updateContribuzione(ContribuzioneCompletaVO newContribuzione, List<Long> listaVociCostoDaEliminare,
			List<Long> listaDatiFatturaDaEliminare, List<Long> listaDatiBonificoDaEliminare) {
		ContribuzioneCompletaVO oldContribuzione = getContribuzioneCompletaById(
				newContribuzione.getContribuzione().getIdContribuzione());

		try {

			// Modifico gli oggetti assegnazione risorse, ordine acquisto dato spesa e dato
			// messa servizio
			updateAssegnazioneRisorse(newContribuzione.getAssegnazioneRisorse());
			updateOrdineAcquisto(newContribuzione.getOrdineAcquisto());
			updateDatoSpesa(newContribuzione.getDatoSpesa());

//		 UPDATE DOCUMENTO CONTRIBUZIONE
//		Se l'id del documento di contribuzione e uguale a null vuol dire che il documento e stato modificato o cancellato
			if (newContribuzione.getDocumentoContribuzione().getIdDocContribuzione() == null
					&& newContribuzione.getDocumentoContribuzione().getNomeFile() != null) {
				if (oldContribuzione.getDocumentoContribuzione() == null) {
					Long idDoc = insertDocContribuzione(newContribuzione.getDocumentoContribuzione());
					newContribuzione.getCostoFornitura().setIdDocContribuzione(idDoc);
				} else {
//					Se il nome del file e diverso da null vuol dire che il documento e stato modificato.
					if (newContribuzione.getDocumentoContribuzione().getNomeFile() != null) {
//						Elimino il vecchio documento
						newContribuzione.getDocumentoContribuzione().setIdDocContribuzione(
								oldContribuzione.getDocumentoContribuzione().getIdDocContribuzione());
						updateDocContribuzione(newContribuzione.getDocumentoContribuzione());
					} else {
//						Altrimenti setto l'id del documento a null, questo vuol dire che il documento e stato eliminato
						newContribuzione.getCostoFornitura().setIdDocContribuzione(null);
						rebuscRDocContribuzioneDAO.deleteByPrimaryKey(
								oldContribuzione.getDocumentoContribuzione().getIdDocContribuzione());
					}
				}
			} else if (newContribuzione.getDocumentoContribuzione().getIdDocContribuzione() == null
					&& oldContribuzione.getDocumentoContribuzione() != null) {
				newContribuzione.getCostoFornitura().setIdDocContribuzione(null);
			}

//		// Modifica costo fronitura
			updateCostoFornitura(newContribuzione.getCostoFornitura());
			if (newContribuzione.getDocumentoContribuzione().getIdDocContribuzione() == null
					&& oldContribuzione.getDocumentoContribuzione() != null
					&& oldContribuzione.getDocumentoContribuzione().getIdDocContribuzione() != null) {
				rebuscRDocContribuzioneDAO
				.deleteByPrimaryKey(oldContribuzione.getDocumentoContribuzione().getIdDocContribuzione());
			}

			HashMap<Long, Long> vociCosto = new HashMap<>();

			updateDatoSpesa(newContribuzione.getDatoSpesa());

			// Elimino i dati bonifico e la voci di fattura_bonifico
			if (!listaDatiBonificoDaEliminare.isEmpty()) {
				for (Long idDatoBonifico : listaDatiBonificoDaEliminare) {
					RebuscRFatturaBonificoSelector selector = new RebuscRFatturaBonificoSelector();
					selector.createCriteria().andIdDatoBonificoEqualTo(idDatoBonifico);
					rebuscRFatturaBonificoDAO.deleteByExample(selector);
					rebuscTDatoBonificoDAO.deleteByPrimaryKey(idDatoBonifico);
				}
			}

//		Elimino i dati fattura che sono stati eliminati dall'elenco
			if (!listaDatiFatturaDaEliminare.isEmpty()) {
				for (Long idDatoFattura : listaDatiFatturaDaEliminare) {
					RebuscROggettoFatturaSelector selector = new RebuscROggettoFatturaSelector();
					selector.createCriteria().andIdDatoFatturaEqualTo(idDatoFattura);
					rebuscROggettoFatturaDAO.deleteByExample(selector);
					rebuscTDatoFatturaDAO.deleteByPrimaryKey(idDatoFattura);
					for (DatoFatturaVO dato : oldContribuzione.getDatiFattura()) {
						if (dato.getIdDatoFattura().equals(idDatoFattura)) {
							if (dato.getIdDocContribuzione() != null) {
								rebuscRDocContribuzioneDAO.deleteByPrimaryKey(dato.getIdDocContribuzione());
							}
						}
					}
				}
			}

//			Elimino le voci costo che sono state eliminate dall'elenco
			if (!listaVociCostoDaEliminare.isEmpty()) {
				for (Long idVoceCosto : listaVociCostoDaEliminare) {
					rebuscRVoceCostoFornituraDAO.deleteByPrimaryKey(idVoceCosto);
				}
			}

			if (!newContribuzione.getVociCosto().isEmpty()) {
				// inserisco le nuove voci costo in un array
				ArrayList<VoceCostoFornituraVO> newVociCosto = new ArrayList<>();
				for (VoceCostoFornituraVO voceCostoFornitura : newContribuzione.getVociCosto()) {
					if (!voceCostoFornitura.isPresentIn(oldContribuzione.getVociCosto())) {
						newVociCosto.add(voceCostoFornitura);
					}
				}
				// se ci sono nuove voci costo faccio l'insert che mi restituisce un hash map
				// key: id_voce_costo, value: id_voce_costo_fornitura
				if (!newVociCosto.isEmpty()) {
					vociCosto = insertVoceCostoFornitura(newVociCosto,
							oldContribuzione.getCostoFornitura().getIdCostoFornitura());
				}

				// inserisco all'HashMap le veccie voci costo che non sono state eliminate
				for (VoceCostoFornituraVO voceCostoFornitura : newContribuzione.getVociCosto()) {
					if (!vociCosto.containsKey(voceCostoFornitura.getIdVoceCosto())) {
						vociCosto.put(voceCostoFornitura.getIdVoceCosto(),
								voceCostoFornitura.getIdVoceCostoFornitura());

					}
				}

				// inserisco nell'array i nuovi dati fattura che sono stati aggiunti
				ArrayList<DatoFatturaVO> newDatiFattura = new ArrayList<>();
				for (DatoFatturaVO datoFattura : newContribuzione.getDatiFattura()) {
					// controllo se sono nuovi dati fattura
					if (!datoFattura.isPresentIn(oldContribuzione.getDatiFattura())) {
						newDatiFattura.add(datoFattura);
					} else if (!datoFattura.getListaBonifici().isEmpty()) {
						// se non sono nuovi controllo che non siano state aggiunte
						// nuovi bonifici alla fattura
						for (DatoBonificoVO datoBonificoVO : datoFattura.getListaBonifici()) {
							if (datoBonificoVO.getIdDatoBonifico() == null) {
								// Se l'id dato bonifico e' null faccio l'inserimento del
								// dato bonifico e di fattura bonifico
								RebuscTDatoBonificoSelector selector = new RebuscTDatoBonificoSelector();
								selector.createCriteria().andDataBonificoEqualTo(datoBonificoVO.getDataBonifico())
										.andImportoBonificoEqualTo(datoBonificoVO.getImportoBonifico())
										.andCroEqualTo(datoBonificoVO.getCro());
								List<RebuscTDatoBonificoDTO> savedData = rebuscTDatoBonificoDAO
										.selectByExample(selector);
								RebuscRFatturaBonificoKey fatturaBonifico = new RebuscRFatturaBonificoKey();
								RebuscTDatoBonificoDTO bonifico = new RebuscTDatoBonificoDTO();
								if (savedData.isEmpty()) {
									bonifico = dozerMapper.map(datoBonificoVO, RebuscTDatoBonificoDTO.class);
									rebuscTDatoBonificoDAO.insert(bonifico);
									fatturaBonifico.setIdDatoBonifico(bonifico.getIdDatoBonifico());
								} else {
									fatturaBonifico.setIdDatoBonifico(savedData.get(0).getIdDatoBonifico());
								}
								fatturaBonifico.setIdDatoFattura(datoFattura.getIdDatoFattura());
								rebuscRFatturaBonificoDAO.insert(fatturaBonifico);
							}
						}

					}
				}

				if (!newDatiFattura.isEmpty()) {
					// ciclo i dati fattura e salvo il documento
					for (DatoFatturaVO dato : newDatiFattura) {
						Long idDocumento = null;
						if (dato.getDocumento().getDocumento() != null) {
							idDocumento = insertDocContribuzione(dato.getDocumento());
						}
						// setto l'id_dato_spesa e inserisco il dato fattura
						dato.setIdDatoSpesa(oldContribuzione.getDatoSpesa().getIdDatoSpesa());
						Long idDatoFattura = insertDatoFattura(dato, idDocumento);
						for (DatoBonificoVO datoBonificoVO : dato.getListaBonifici()) {
							if (datoBonificoVO.getIdDatoBonifico() == null) {
								// Se l'id dato bonifico e' null faccio l'inserimento del
								// dato bonifico e di fattura bonifico
								RebuscTDatoBonificoSelector selector = new RebuscTDatoBonificoSelector();
								selector.createCriteria().andDataBonificoEqualTo(datoBonificoVO.getDataBonifico())
										.andImportoBonificoEqualTo(datoBonificoVO.getImportoBonifico())
										.andCroEqualTo(datoBonificoVO.getCro());
								List<RebuscTDatoBonificoDTO> savedData = rebuscTDatoBonificoDAO
										.selectByExample(selector);
								RebuscRFatturaBonificoKey fatturaBonifico = new RebuscRFatturaBonificoKey();
								RebuscTDatoBonificoDTO bonifico = new RebuscTDatoBonificoDTO();
								if (savedData.isEmpty()) {
									bonifico = dozerMapper.map(datoBonificoVO, RebuscTDatoBonificoDTO.class);
									try {
									rebuscTDatoBonificoDAO.insert(bonifico);
									}
									catch(Exception e){
										e.printStackTrace();
										
									}
									fatturaBonifico.setIdDatoBonifico(bonifico.getIdDatoBonifico());
								} else {
									fatturaBonifico.setIdDatoBonifico(savedData.get(0).getIdDatoBonifico());
								}
								fatturaBonifico.setIdDatoFattura(idDatoFattura);
								rebuscRFatturaBonificoDAO.insert(fatturaBonifico);
							}
						}

						if (!vociCosto.isEmpty() && !dato.getListaIdVoceCosto().isEmpty()) {
							// mi riprendo l'id_voce_costo_fornitura confrontando la key con l'id_voce costo
							// che ho in dato fattura
							for (Long idVoceCosto : dato.getListaIdVoceCosto()) {
								Long idVoceCostoFornitura = vociCosto.get(idVoceCosto);
								// inserisco l'oggeto fattura passando l'id_dato_fattura e l'id_voce_costo
								insertOggettoFattura(idDatoFattura, idVoceCostoFornitura);
							}
						}
					}
				}
				// entra qui quando ci sono solo dati fattura di tipo quietanza
			} else if (newContribuzione.getVociCosto().isEmpty() && !newContribuzione.getDatiFattura().isEmpty()) {
				if (!newContribuzione.getDatiFattura().isEmpty()) {
					// ciclo i dati fattura e salvo il documento
					for (DatoFatturaVO dato : newContribuzione.getDatiFattura()) {
						if (Objects.isNull(dato.getIdDatoFattura())) {
							Long idDocumento = null;
							if (dato.getDocumento().getDocumento() != null) {
								idDocumento = insertDocContribuzione(dato.getDocumento());
							}
							// setto l'id_dato_spesa e inserisco il dato fattura
							dato.setIdDatoSpesa(oldContribuzione.getDatoSpesa().getIdDatoSpesa());
							Long idDatoFattura = insertDatoFattura(dato, idDocumento);
							if (!vociCosto.isEmpty() && !dato.getListaIdVoceCosto().isEmpty()) {
								// mi riprendo l'id_voce_costo_fornitura confrontando la key con l'id_voce costo
								// che ho in dato fattura
								for (Long idVoceCosto : dato.getListaIdVoceCosto()) {
									Long idVoceCostoFornitura = vociCosto.get(idVoceCosto);
									// inserisco l'oggeto fattura passando l'id_dato_fattura e l'id_voce_costo
									insertOggettoFattura(idDatoFattura, idVoceCostoFornitura);
								}
							}
						}
					}
				}
			}

			updateDatoMessaServizio(newContribuzione.getDatoMessaServizio());
//		------------------------------------- MODIFICA DEI DOCUMENTI ------------------------------------------

//		UPDATE DOCUMENTO ATTO D'OBBLIGO
			if (newContribuzione.getDocumentoAttoObbligo().getIdDocContribuzione() == null
					&& newContribuzione.getDocumentoAttoObbligo().getNomeFile() != null) {
				if (oldContribuzione.getDocumentoAttoObbligo() == null) {
					Long id = insertDocContribuzione(newContribuzione.getDocumentoAttoObbligo());
					insertDocDatoMessaServizio(newContribuzione.getDatoMessaServizio().getIdDatoMessaServizio(), id);
				} else {
					updateDocDatoMessaServizio(oldContribuzione.getDocumentoAttoObbligo().getIdDocContribuzione(),
							newContribuzione.getDocumentoAttoObbligo(),
							oldContribuzione.getDatoMessaServizio().getIdDatoMessaServizio());
				}
			} else if (newContribuzione.getDocumentoAttoObbligo().getIdDocContribuzione() == null 
					&& oldContribuzione.getDocumentoAttoObbligo() != null) {
				if (oldContribuzione.getDocumentoAttoObbligo().getNomeFile() != null) {					
					RebuscRDocDatoMessaServizioSelector selector = new RebuscRDocDatoMessaServizioSelector();
					selector.createCriteria().andIdDocContribuzioneEqualTo(oldContribuzione.getDocumentoAttoObbligo().getIdDocContribuzione());
					rebuscRDocDatoMessaServizioDAO.deleteByExample(selector);
				}
			}


//		UPDATE DOCUMENTO GARANZIA
			if (newContribuzione.getDocumentoGaranzia().getIdDocContribuzione() == null
					&& newContribuzione.getDocumentoGaranzia().getNomeFile() != null) {
				if (oldContribuzione.getDocumentoGaranzia() == null) {
					Long id = insertDocContribuzione(newContribuzione.getDocumentoGaranzia());
					insertDocDatoMessaServizio(newContribuzione.getDatoMessaServizio().getIdDatoMessaServizio(), id);
				} else {
					updateDocDatoMessaServizio(oldContribuzione.getDocumentoGaranzia().getIdDocContribuzione(),
							newContribuzione.getDocumentoGaranzia(),
							oldContribuzione.getDatoMessaServizio().getIdDatoMessaServizio());
				}
			} else if (newContribuzione.getDocumentoGaranzia().getIdDocContribuzione() == null
					&& oldContribuzione.getDocumentoGaranzia() != null) {
				if (oldContribuzione.getDocumentoGaranzia().getNomeFile() != null) {					
					RebuscRDocDatoMessaServizioSelector selector = new RebuscRDocDatoMessaServizioSelector();
					selector.createCriteria().andIdDocContribuzioneEqualTo(oldContribuzione.getDocumentoGaranzia().getIdDocContribuzione());
					rebuscRDocDatoMessaServizioDAO.deleteByExample(selector);
				}
			}

			UserInfo userInfo = SecurityUtils.getCurrentUserInfo();

			ContribuzioneVO contribuzione = oldContribuzione.getContribuzione();
			contribuzione.setIdUtenteAggiornamento(userInfo.getIdUtente());
			contribuzione.setDataAggiornamento(new Date());
			RebuscTContribuzioneDTO record = dozerMapper.map(contribuzione, RebuscTContribuzioneDTO.class);
			rebuscTContribuzioneDAO.updateByPrimaryKey(record);
			return contribuzione.getIdContribuzione();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResponseStringVO getTelaioByIdProcedimento(Long IdProcediemnto) {
		ResponseStringVO result = new ResponseStringVO();
		result.setResponse(contribuzioneDAO.getTelaio(IdProcediemnto));
		return result;
	}

	@Override
	public boolean checkFinalStateIter(Long idStato) {
		Long stato = contribuzioneDAO.finalStateIter(idStato);
		if (stato != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertDatoBonificoAndFatturaBonifico(List<DatoBonificoVO> listBonifici, Long idDatoFattura) {
		try {
			for (DatoBonificoVO datoBonificoVO : listBonifici) {
				RebuscTDatoBonificoSelector selector = new RebuscTDatoBonificoSelector();
				// cerco se e' presente un bonifico sul db
				selector.createCriteria().andDataBonificoEqualTo(datoBonificoVO.getDataBonifico())
				.andImportoBonificoEqualTo(datoBonificoVO.getImportoBonifico())
				.andCroEqualTo(datoBonificoVO.getCro());
				List<RebuscTDatoBonificoDTO> savedData = rebuscTDatoBonificoDAO.selectByExample(selector);
				// se la lista e' vuota salvo il bonifico e poi la fattura bonifico
				if (savedData.isEmpty()) {
					// salvo il bonifico
					RebuscTDatoBonificoDTO bonifico = dozerMapper.map(datoBonificoVO, RebuscTDatoBonificoDTO.class);
					rebuscTDatoBonificoDAO.insertSelective(bonifico);
					RebuscRFatturaBonificoKey fatturaBonifico = new RebuscRFatturaBonificoKey();
					fatturaBonifico.setIdDatoBonifico(bonifico.getIdDatoBonifico());
					fatturaBonifico.setIdDatoFattura(idDatoFattura);
					rebuscRFatturaBonificoDAO.insert(fatturaBonifico);
				} else {
					// se il dato e' presente allora salvo solo la fattura bonifico
					// l'id del bonifico lo prendo dalla ricerca effettuata prima
					RebuscRFatturaBonificoKey fatturaBonifico = new RebuscRFatturaBonificoKey();
					fatturaBonifico.setIdDatoBonifico(savedData.get(0).getIdDatoBonifico());
					fatturaBonifico.setIdDatoFattura(idDatoFattura);
					rebuscRFatturaBonificoDAO.insert(fatturaBonifico);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getTelaiVeicoloDaSostituire() {
		UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
		List<String> result = new ArrayList<String>();
		if (userInfo.getIdAzienda() != null) {
			result = contribuzioneDAO.getTelaiVeicoloDaSostituire(userInfo.getIdAzienda());
		}
		return result;
	}

}
