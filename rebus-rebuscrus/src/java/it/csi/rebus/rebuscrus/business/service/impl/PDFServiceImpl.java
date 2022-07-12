/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.business.service.DocumentoService;
import it.csi.rebus.rebuscrus.business.service.LuoghiService;
import it.csi.rebus.rebuscrus.business.service.PDFService;
import it.csi.rebus.rebuscrus.business.service.ProcedimentiService;
import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.integration.dao.RebusRUtenteAzEnteDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDMotivazioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDMotorizzazioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDTipoProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspDTipoStampaDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspRProcDocumentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.RebuspTIterProcedimentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplDComuneDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplRUtenteSogGiuridDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplaDNaturaGiuridicaDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplaTSoggettoGiuridicoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcDBacinoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcDTipoRaggruppamentoDAO;
import it.csi.rebus.rebuscrus.integration.dao.SirtplcTContrattoDAO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebusRUtenteAzEnteSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotivazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDMotorizzazioneDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoStampaDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspDTipoStampaSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspRProcDocumentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTIterProcedimentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.RebuspTIterProcedimentoSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplDComuneDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplRUtenteSogGiuridDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaDNaturaGiuridicaDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaTSoggettoGiuridicoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplaTSoggettoGiuridicoSelector;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcDTipoRaggruppamentoDTO;
import it.csi.rebus.rebuscrus.integration.dto.SirtplcTContrattoDTO;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.SecurityUtils;
import it.csi.rebus.rebuscrus.vo.AllegatoVeicoloVO;
import it.csi.rebus.rebuscrus.vo.ContrattoProcDatiVO;
import it.csi.rebus.rebuscrus.vo.DettaglioRichiestaVO;
import it.csi.rebus.rebuscrus.vo.IterProcedimentoVO;
import it.csi.rebus.rebuscrus.vo.ProvinciaVO;
import it.csi.rebus.rebuscrus.vo.TransizioneAutomaVO;
import it.csi.rebus.rebuscrus.vo.VeicoloVO;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRJdtCompiler;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class PDFServiceImpl implements PDFService {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private ProcedimentiService procedimentiService;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private LuoghiService luoghiService;
	@Autowired
	private RebuspDTipoStampaDAO rebuspDTipoStampaDAO;
	@Autowired
	private SirtplcTContrattoDAO sirtplcTContrattoDAO;
	@Autowired
	private SirtplaTSoggettoGiuridicoDAO sirtplaTSoggettoGiuridicoDAO;
	@Autowired
	private SirtplDComuneDAO sirtplDComuneDAO;
	@Autowired
	private RebuspDMotorizzazioneDAO rebuspDMotorizzazioneDAO;
	@Autowired
	private RebuspDMotivazioneDAO rebuspDMotivazioneDAO;
	@Autowired
	private RebuspDTipoProcedimentoDAO rebuspDTipoProcedimentoDAO;
	@Autowired
	private RebuspTIterProcedimentoDAO rebuspTIterProcedimentoDAO;
	@Autowired
	private RebusRUtenteAzEnteDAO rebusRUtenteAzEnteDAO;
	@Autowired
	private SirtplcDBacinoDAO sirtplcDBacinoDAO;
	@Autowired
	private RebuspRProcDocumentoDAO rebuspRProcDocumentoDAO;
	@Autowired
	private SirtplcDTipoRaggruppamentoDAO sirtplcDTipoRaggruppamentoDAO;
	@Autowired
	private SirtplRUtenteSogGiuridDAO sirtplRUtenteSogGiuridDAO;
	@Autowired
	private SirtplaDNaturaGiuridicaDAO sirtplaDNaturaGiuridicaDAO;

	private Map<String, Object> parameters;

	@Override
	@Transactional
	public byte[] generatePDF(Long idProcedimento, Long idTipoStampa, Boolean isAnteprima) throws Exception {
		SecurityUtils.assertAutorizzazioni(AuthorizationRoles.SCARICA_PDF);

		if (idProcedimento == null || idTipoStampa == null)
			throw new InvalidParameterException();

		DettaglioRichiestaVO dettaglioRichiesta = procedimentiService.dettaglioRichiesta(idProcedimento, "V");

		RebuspDTipoStampaSelector rebuspDTipoStampaSelector = new RebuspDTipoStampaSelector();
		rebuspDTipoStampaSelector.createCriteria().andIdTipoStampaEqualTo(idTipoStampa);
		List<RebuspDTipoStampaDTO> rebuspDTipoStampaDTOs = rebuspDTipoStampaDAO
				.selectByExampleWithBLOBs(rebuspDTipoStampaSelector);

		byte[] result = null;

		if (rebuspDTipoStampaDTOs != null && rebuspDTipoStampaDTOs.size() > 0) {

			byte[] template = rebuspDTipoStampaDTOs.get(0).getTemplate();

			JasperDesign jDesign = JRXmlLoader.load(new ByteArrayInputStream(template));

			JRJdtCompiler compiler = new JRJdtCompiler(DefaultJasperReportsContext.getInstance());

			JasperReport jReport = compiler.compileReport(jDesign);

			JasperReport jSubReport = null;

			rebuspDTipoStampaSelector = new RebuspDTipoStampaSelector();
			rebuspDTipoStampaSelector.createCriteria().andIdTipoStampaEqualTo(new Long(5));
			List<RebuspDTipoStampaDTO> subReports = rebuspDTipoStampaDAO
					.selectByExampleWithBLOBs(rebuspDTipoStampaSelector);
			if (subReports != null && subReports.size() > 0) {
				byte[] templateSubReport = subReports.get(0).getTemplate();
				JasperDesign jDesignSubReport = JRXmlLoader.load(new ByteArrayInputStream(templateSubReport));
				jSubReport = compiler.compileReport(jDesignSubReport);
			}

			try (Connection conn = dataSource.getConnection();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
				parameters = new HashMap<String, Object>();
				parameters.put("is_anteprima", isAnteprima);
				parameters.put("subreportParameter", jSubReport);
				if (isAnteprima) {
					rebuspDTipoStampaSelector = new RebuspDTipoStampaSelector();
					rebuspDTipoStampaSelector.createCriteria().andIdTipoStampaEqualTo(new Long(6));
					List<RebuspDTipoStampaDTO> immagineSfondoAnteprimaList = rebuspDTipoStampaDAO
							.selectByExampleWithBLOBs(rebuspDTipoStampaSelector);
					if (immagineSfondoAnteprimaList != null && immagineSfondoAnteprimaList.size() > 0) {
						byte[] immagineSfondoAnteprima = immagineSfondoAnteprimaList.get(0).getTemplate();
						if (immagineSfondoAnteprima != null) {
							InputStream in = new ByteArrayInputStream(immagineSfondoAnteprima);
							BufferedImage img = ImageIO.read(in);
							parameters.put("sfondo_bozza", img);
						}
					}
				}

				if (idTipoStampa.equals(new Long(1))) {
					// richiesta nulla osta
					setParametersScenario1(dettaglioRichiesta);
				} else if (idTipoStampa.equals(new Long(2))) {
					// rilascio nulla osta - trasmessa ente committente
					setParametersScenario2(dettaglioRichiesta);
				} else if (idTipoStampa.equals(new Long(3))) {
					// richiesta nulla osta sostituzione
					setParametersScenario1Sostituzione(dettaglioRichiesta);
				} else if (idTipoStampa.equals(new Long(4))) {
					// rilascio nulla osta sostituzione - trasmessa ente committente
					setParametersScenario2Sostituzione(dettaglioRichiesta);
				}
				JasperPrint print = JasperFillManager.fillReport(jReport, parameters, conn);
				JasperExportManager.exportReportToPdfStream(print, outputStream);
				result = outputStream.toByteArray();

				// ACCODAMENTO ALLEGATI VEICOLI AL PDF
				if (idTipoStampa.equals(new Long(1)) || idTipoStampa.equals(new Long(3))) {

					if (!dettaglioRichiesta.getIdTipoProcedimento().equals(new Long(2L))) { // NON
																							// SOSTITUZIONE

						// prendo carta di circolazione per procedimenti di tipo != 1
						// perndo certificato del costruttore per prima immatricolazione (tipo proc =1)
						Long idTipoDocumento;
						if (dettaglioRichiesta.getIdTipoProcedimento().equals(Long.valueOf(1))) {
							idTipoDocumento = Long.valueOf(5);
						} else {
							idTipoDocumento = Long.valueOf(1);
						}
						PDDocument pdf = PDDocument.load(result);
						InputStream pdfInputStream = new ByteArrayInputStream(result);

						PDFMergerUtility PDFmerger = new PDFMergerUtility();
						PDFmerger.setDestinationStream(outputStream);
						PDFmerger.addSource(pdfInputStream);

						for (VeicoloVO v : dettaglioRichiesta.getVeicoli()) {
							// prendo solo la carta di circolazione
							byte[] all;
							try {
								all = documentoService.getContenutoDocumentoByIdVarAutobusAndTipo(
										v.getIdVariazAutobus(), idTipoDocumento);
							} catch (InvalidParameterException e) {
								all = null;
							}
							if (all != null) {
								PDDocument allegato = PDDocument.load(all);
								InputStream allInputStream = new ByteArrayInputStream(all);
								PDFmerger.addSource(allInputStream);
								allegato.close();
							}
						}
						PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
						pdf.close();
						result = outputStream.toByteArray();
					} else { // SOSTITUZIONE
						DettaglioRichiestaVO dettaglioRichiestaAlienazione = procedimentiService.dettaglioRichiesta(
								dettaglioRichiesta.getSubProcedimento().getIdSubProcedimento1(), "VIEW");
						DettaglioRichiestaVO dettaglioRichiestaImmOReImm = procedimentiService.dettaglioRichiesta(
								dettaglioRichiesta.getSubProcedimento().getIdSubProcedimento2(), "VIEW");
						Long idTipoDocumento;
						if (dettaglioRichiestaImmOReImm.getIdTipoProcedimento().equals(Long.valueOf(1))) {
							idTipoDocumento = Long.valueOf(5);
						} else {
							idTipoDocumento = Long.valueOf(1);
						}

						PDDocument pdf = PDDocument.load(result);
						InputStream pdfInputStream = new ByteArrayInputStream(result);

						PDFMergerUtility PDFmerger = new PDFMergerUtility();
						PDFmerger.setDestinationStream(outputStream);
						PDFmerger.addSource(pdfInputStream);

						for (VeicoloVO v : dettaglioRichiestaImmOReImm.getVeicoli()) {
							// prendo carta di circolazione per procedimenti di tipo reimmatricolazione
							// perndo certificato del costruttore per prima immatricolazione
							byte[] all;
							try {
								all = documentoService.getContenutoDocumentoByIdVarAutobusAndTipo(
										v.getIdVariazAutobus(), idTipoDocumento);
							} catch (InvalidParameterException e) {
								all = null;
							}
							if (all != null) {
								PDDocument allegato = PDDocument.load(all);
								InputStream allInputStream = new ByteArrayInputStream(all);
								PDFmerger.addSource(allInputStream);
								allegato.close();
							}
						}
						for (VeicoloVO v : dettaglioRichiestaAlienazione.getVeicoli()) {
							// prendo solo la carta di circolazione
							byte[] all;
							try {
								all = documentoService.getContenutoDocumentoByIdVarAutobusAndTipo(
										v.getIdVariazAutobus(), new Long(1L));
							} catch (InvalidParameterException e) {
								all = null;
							}
							if (all != null) {
								PDDocument allegato = PDDocument.load(all);
								InputStream allInputStream = new ByteArrayInputStream(all);
								PDFmerger.addSource(allInputStream);
								allegato.close();
							}
						}
						PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
						pdf.close();
						result = outputStream.toByteArray();
					}
				}

				// SALVATAGGIO PDF SU DB SE NON E' UN'ANTEPRIMA
				if (!isAnteprima) {
					Long idTipoDoc = null;
					if (idTipoStampa.equals(new Long(1)) || idTipoStampa.equals(new Long(3))) {
						idTipoDoc = new Long(7);
					} else {
						idTipoDoc = new Long(8);
					}
					RebuspRProcDocumentoSelector rebuspRProcDocumentoSelector = new RebuspRProcDocumentoSelector();
					rebuspRProcDocumentoSelector.createCriteria().andIdProcedimentoEqualTo(dettaglioRichiesta.getId())
							.andIdTipoDocumentoEqualTo(idTipoDoc);
					rebuspRProcDocumentoDAO.deleteByExample(rebuspRProcDocumentoSelector);

					RebuspRProcDocumentoDTO rebuspRProcDocumentoDTO = new RebuspRProcDocumentoDTO();
					rebuspRProcDocumentoDTO.setIdProcedimento(dettaglioRichiesta.getId());
					rebuspRProcDocumentoDTO.setIdTipoDocumento(idTipoDoc);

					/*
					 * PROC_AAAATInn_tipo.pdf dove AAAATInn e l'attuale identificativo della
					 * richiesta (senza gli "/": AAAA e l'anno di competenza, TI e il tipo richiesta
					 * (es. PI per Prima Immatricolazione), nn il progressivo all'interno di qs due
					 ** tipo: e il tipo di file (Bozza per le anteprime, Richiesta e Autorizzazione
					 * per le rispettive versioni finali)
					 */

					String progressivo = dettaglioRichiesta.getNumProgressivo();
					if (progressivo != null) {
						progressivo = progressivo.replaceAll("/", "");
					}
					if (idTipoDoc.equals(new Long(7))) {
						rebuspRProcDocumentoDTO.setNomeFile("PROC_" + progressivo + "_Richiesta.pdf");
					} else if (idTipoDoc.equals(new Long(8))) {
						rebuspRProcDocumentoDTO.setNomeFile("PROC_" + progressivo + "_Autorizzazione.pdf");
					}
					rebuspRProcDocumentoDTO.setDataCaricamento(new Date());
					rebuspRProcDocumentoDTO.setNote("Documento generato automaticamente");
					rebuspRProcDocumentoDTO.setIdUtenteAggiornamento(SecurityUtils.getCurrentUserInfo().getIdUtente());
					rebuspRProcDocumentoDTO.setDocumento(result);
					rebuspRProcDocumentoDAO.insert(rebuspRProcDocumentoDTO);
				}
			} catch (SQLException e) {
				throw new ErroreGestitoException("Errore nella generazione del report Jasper - Connection error ");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// richiesta nulla osta 1
	private void setParametersScenario1(DettaglioRichiestaVO dettaglioRichiesta) throws Exception {
		parameters.put("id_procedimento", dettaglioRichiesta.getId());
		parameters.put("num_progressivo", dettaglioRichiesta.getNumProgressivo());
		if (dettaglioRichiesta.getIters() != null && dettaglioRichiesta.getIters().size() > 0) {
			IterProcedimentoVO iterCorrente = null;
			for (IterProcedimentoVO i : dettaglioRichiesta.getIters()) {
				if (i.getDataFineValidita() == null) {
					iterCorrente = i;
					break;
				}
			}

			if ((iterCorrente.getIdStato().equals(new Long(40L)) || ((iterCorrente.getIdStato().equals(new Long(10L))
					|| iterCorrente.getIdStato().equals(new Long(20L))
					|| iterCorrente.getIdStato().equals(new Long(30L))
					|| iterCorrente.getIdStato().equals(new Long(50L)))
					&& parameters.get("is_anteprima").equals(Boolean.TRUE)))) {

				String pattern = "dd/MM/yyyy";
				DateFormat df = new SimpleDateFormat(pattern);
				parameters.put("data_inizio_validita", df.format(iterCorrente.getDataInizioValidita()));
				List<TransizioneAutomaVO> transizioniAutoma = null;
				try {
					transizioniAutoma = procedimentiService.getTransizioniAutoma(dettaglioRichiesta.getId(),
							new Long(10L), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Long idGestoreContratto = null;
				IterProcedimentoVO iterCreazione = null;
				if (transizioniAutoma != null) {
					for (TransizioneAutomaVO t : transizioniAutoma) {
						if (t.getIdStatoIterA().equals(new Long(30L))) {
							idGestoreContratto = procedimentiService.getGestoreContratto(
									dettaglioRichiesta.getIdTipoProcedimento(),
									dettaglioRichiesta.getContratto().getIdContratto(), dettaglioRichiesta.getId());
						} else if (t.getIdStatoIterA().equals(new Long(40L))) {
							for (IterProcedimentoVO i : dettaglioRichiesta.getIters()) {
								if (i.getIdStato().equals(new Long(10L))) {
									if (iterCreazione == null || i.getDataInizioValidita()
											.before(iterCreazione.getDataInizioValidita())) {
										iterCreazione = i;
									}
								}
							}
						}
					}
				}
				Long idSogGiurid = null;
				RebusRUtenteAzEnteSelector rebusRUtenteAzEnteSelector = new RebusRUtenteAzEnteSelector();
				if (idGestoreContratto != null) {
					idSogGiurid = idGestoreContratto;
				} else if (iterCreazione != null) {
					rebusRUtenteAzEnteSelector.createCriteria().andIdRUtenteAzEnteEqualTo(rebuspTIterProcedimentoDAO
							.selectByPrimaryKey(iterCreazione.getId()).getIdUtenteSogGiurid());
					List<RebusRUtenteAzEnteDTO> rebusRUtenteAzEnteDTOs = rebusRUtenteAzEnteDAO
							.selectByExample(rebusRUtenteAzEnteSelector);
					if (rebusRUtenteAzEnteDTOs != null && rebusRUtenteAzEnteDTOs.size() > 0) {
						idSogGiurid = rebusRUtenteAzEnteDTOs.get(0).getFkAzienda() != null
								? rebusRUtenteAzEnteDTOs.get(0).getFkAzienda()
								: rebusRUtenteAzEnteDTOs.get(0).getFkEnte();
					}
				}
				if (idSogGiurid != null) {
					SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
					sirtplaTSoggettoGiuridicoSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(idSogGiurid);
					List<SirtplaTSoggettoGiuridicoDTO> sirtplaTSoggettoGiuridicoDTOs = sirtplaTSoggettoGiuridicoDAO
							.selectByExampleWithBLOBs(sirtplaTSoggettoGiuridicoSelector);
					if (sirtplaTSoggettoGiuridicoDTOs != null && sirtplaTSoggettoGiuridicoDTOs.size() > 0) {
						SirtplaTSoggettoGiuridicoDTO sogg = sirtplaTSoggettoGiuridicoDTOs.get(0);
						if (sogg.getLogo() != null) {
							try {
								InputStream in = new ByteArrayInputStream(sogg.getLogo());
								BufferedImage image = ImageIO.read(in);
								parameters.put("logo", image);
							} catch (Exception ignore) {
							}

						}
						parameters.put("denom_az", "<b>" + sogg.getDenomSoggettoGiuridico() + "</b>");

						SirtplDComuneDTO sirtplDComuneDTO = null;
						ProvinciaVO provinciaVO = null;
						if (sogg.getIdComuneSedeLegale() != null) {
							sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(sogg.getIdComuneSedeLegale());
							provinciaVO = luoghiService.getProvinciaByIdComune(sogg.getIdComuneSedeLegale());
						}
						String indAz = getIndirizzoCompleto(sogg.getToponimoSedeLegale(), sogg.getIndirizzoSedeLegale(),
								sogg.getNumCivicoSedeLegale(), sogg.getCapSedeLegale(),
								sogg.getIdComuneSedeLegale() != null ? sirtplDComuneDTO.getDenomComune() : null,
								provinciaVO != null ? provinciaVO.getSigla() : null);

						if (sogg.getCapitaleSociale() != null) {
							NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("da", "DK"));
							numberFormat.setCurrency(Currency.getInstance("EUR"));
							String currencyString = numberFormat.format(sogg.getCapitaleSociale());
							SirtplaDNaturaGiuridicaDTO flgPatrimonio = sirtplaDNaturaGiuridicaDAO
									.selectByPrimaryKey(sogg.getIdNaturaGiuridica());
							if (flgPatrimonio.getFlgPatrimonio() != null) {
								if (flgPatrimonio.getFlgPatrimonio().equals("C")) {
									indAz += " - Capitale sociale " + currencyString + "  i.v.";
								} else if (flgPatrimonio.getFlgPatrimonio().equals("F")) {
									indAz += " - Fondo consortile " + currencyString + "  i.v.";
								}
							}
						}

						if (indAz != null && indAz.length() > 0) {
							parameters.put("dati_az", indAz);
						}
						String dati2Az = getDatiSoggGiurid(sogg);
						dati2Az += " - R.I. di " + provinciaVO.getDenominazione() + " - Codice Fiscale "
								+ (sogg.getCodiceFiscale() != null ? sogg.getCodiceFiscale() : "ND") + " - P.IVA "
								+ (sogg.getPartitaIva() != null ? sogg.getPartitaIva() : "ND");
						parameters.put("dati2_az", dati2Az);

						if (sogg.getSocUnipersonale() != null) {
							String dati3Az = getSocUnipersonali(sogg);
							parameters.put("dati3_az", dati3Az);
						}
					}
				}
			}
		}
		ContrattoProcDatiVO contrattoProcDatiVO = procedimentiService
				.getDatiContratto(dettaglioRichiesta.getContratto().getIdContratto(), dettaglioRichiesta.getId());
		SirtplaTSoggettoGiuridicoDTO enteComm = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(contrattoProcDatiVO.getEnteComm().getId());
		parameters.put("denom_ente_comm", enteComm.getDenomSoggettoGiuridico());
		SirtplDComuneDTO sirtplDComuneDTO = null;
		ProvinciaVO provinciaVO = null;
		if (enteComm.getIdComuneSedeLegale() != null) {
			sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(enteComm.getIdComuneSedeLegale());
			provinciaVO = luoghiService.getProvinciaByIdComune(enteComm.getIdComuneSedeLegale());
		}
		String indEnteComm = getIndirizzoCompletoIntestazione(enteComm.getToponimoSedeLegale(),
				enteComm.getIndirizzoSedeLegale(), enteComm.getNumCivicoSedeLegale(), enteComm.getCapSedeLegale(),
				sirtplDComuneDTO != null ? sirtplDComuneDTO.getDenomComune() : null,
				provinciaVO != null ? provinciaVO.getSigla() : null);
		if (indEnteComm != null && indEnteComm.length() > 0) {
			parameters.put("ind_ente_comm", indEnteComm);
		}
		if (enteComm.getPecSedeLegale() != null && enteComm.getPecSedeLegale().length() > 0) {
			parameters.put("pec_ente_comm", enteComm.getPecSedeLegale());
		}

		setCommonParameters(dettaglioRichiesta, contrattoProcDatiVO);

		RebuspDMotorizzazioneDTO rebuspDMotorizzazioneDTO = rebuspDMotorizzazioneDAO
				.selectByPrimaryKey(dettaglioRichiesta.getIdMotorizzazione());
		parameters.put("desc_motorizzazione", rebuspDMotorizzazioneDTO.getDescMotorizzazione());
		parameters.put("ind_motorizzazione", rebuspDMotorizzazioneDTO.getIndirizzo());
		if (rebuspDMotorizzazioneDTO.getPec() != null) {
			parameters.put("pec_motorizzazione", rebuspDMotorizzazioneDTO.getPec());
		}
		RebuspDMotivazioneDTO rebuspDMotivazioneDTO = rebuspDMotivazioneDAO
				.selectByPrimaryKey(dettaglioRichiesta.getIdMotivazione());
		parameters.put("id_motivazione", dettaglioRichiesta.getIdMotivazione());
		parameters.put("desc_motivazione", rebuspDMotivazioneDTO.getDescMotivazione());
		if (rebuspDMotivazioneDTO.getFlgMotivAltro()) {
			parameters.put("note_motivazione",
					dettaglioRichiesta.getNoteMotivazione() != null
							&& dettaglioRichiesta.getNoteMotivazione().length() > 0
									? dettaglioRichiesta.getNoteMotivazione()
									: null);
		}

		parameters.put("id_tipo_proc", dettaglioRichiesta.getIdTipoProcedimento());
		parameters.put("tipo_proc", rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiesta.getIdTipoProcedimento()).getDescTipoProcedimento());

		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(contrattoProcDatiVO.getEsecTit().getId());
		parameters.put("denom_sg_tit_esec", sirtplaTSoggettoGiuridicoDTO.getDenomSoggettoGiuridico());

		String alls = "";
		for (VeicoloVO v : dettaglioRichiesta.getVeicoli()) {
			for (AllegatoVeicoloVO a : v.getAllegati()) {
				alls += "-  " + a.getTipoDocumento().getDescrizione() + " ("
						+ (dettaglioRichiesta.getIdTipoProcedimento() == 1 ? ("Telaio: " + v.getPrimoTelaio())
								: ("Targa: " + v.getnTarga()))
						+ ") <br>";
			}
		}
		if (alls.length() > 0) {
			alls = alls.substring(0, alls.lastIndexOf("<") - 1);
		}
		parameters.put("all", alls);
		parameters.put("note", dettaglioRichiesta.getNota());
		parameters.put("ruolo", dettaglioRichiesta.getRuoloFirma());
		parameters.put("rappr_leg", dettaglioRichiesta.getNominativoFirma());
		if (dettaglioRichiesta.getFlgFirmaDigitale() && dettaglioRichiesta.getRuoloFirma() != null) {
			parameters.put("firma",
					"Il presente documento \u00E8 sottoscritto con firma digitale \n ai sensi dell\u0027art. 21 del d.lgs 82/2005");
		}

		return;

	}

	// rilascio nulla osta 2
	private void setParametersScenario2(DettaglioRichiestaVO dettaglioRichiesta) throws Exception {

		try {
			parameters.put("id_procedimento", dettaglioRichiesta.getId());
			parameters.put("num_progressivo", dettaglioRichiesta.getNumProgressivo());
			if (dettaglioRichiesta.getIters() != null && dettaglioRichiesta.getIters().size() > 0) {
				for (IterProcedimentoVO i : dettaglioRichiesta.getIters()) {
					if (i.getDataFineValidita() == null
							&& (i.getIdStato().equals(new Long(60L)) || (i.getIdStato().equals(new Long(40L))
									&& parameters.get("is_anteprima").equals(Boolean.TRUE)))) {
						String pattern = "dd/MM/yyyy";
						DateFormat df = new SimpleDateFormat(pattern);
						parameters.put("data_inizio_validita", df.format(i.getDataInizioValidita()));
						break;
					}
				}
			}
			RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
			rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(dettaglioRichiesta.getId())
					.andIdStatoIterEqualTo(10L).andDataFineValiditaIsNotNull();
			rebuspTIterProcedimentoSelector.setOrderByClause("data_inizio_validita");

			List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
					.selectByExample(rebuspTIterProcedimentoSelector);
			if (rebuspTIterProcedimentoDTOs != null && rebuspTIterProcedimentoDTOs.size() > 0) {
				RebuspTIterProcedimentoDTO iterDto = rebuspTIterProcedimentoDTOs.get(0);
				SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDAO
						.selectByPrimaryKey(iterDto.getIdUtenteSogGiurid());

				if (sirtplRUtenteSogGiuridDTO != null) {

					SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
							.selectByPrimaryKey(sirtplRUtenteSogGiuridDTO.getIdSoggettoGiuridico());
					parameters.put("denom_sogg_creatore_richiesta",
							sirtplaTSoggettoGiuridicoDTO.getDenomSoggettoGiuridico());
				}
			}
			ContrattoProcDatiVO contrattoProcDatiVO = procedimentiService
					.getDatiContratto(dettaglioRichiesta.getContratto().getIdContratto(), dettaglioRichiesta.getId());
			SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
			sirtplaTSoggettoGiuridicoSelector.createCriteria()
					.andIdSoggettoGiuridicoEqualTo(contrattoProcDatiVO.getEnteComm().getId());

			List<SirtplaTSoggettoGiuridicoDTO> sirtplaTSoggettoGiuridicoDTOs = sirtplaTSoggettoGiuridicoDAO
					.selectByExampleWithBLOBs(sirtplaTSoggettoGiuridicoSelector);
			SirtplaTSoggettoGiuridicoDTO enteComm = null;
			if (sirtplaTSoggettoGiuridicoDTOs != null && sirtplaTSoggettoGiuridicoDTOs.size() > 0) {
				enteComm = sirtplaTSoggettoGiuridicoDTOs.get(0);
				if (enteComm.getLogo() != null) {
					InputStream in = new ByteArrayInputStream(enteComm.getLogo());
					BufferedImage image = ImageIO.read(in);
					parameters.put("logo", image);
				}

				parameters.put("denom_ente_comm", enteComm.getDenomSoggettoGiuridico());

				SirtplDComuneDTO sirtplDComuneDTO = null;
				ProvinciaVO provinciaVO = null;
				if (enteComm.getIdComuneSedeLegale() != null) {
					sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(enteComm.getIdComuneSedeLegale());
					provinciaVO = luoghiService.getProvinciaByIdComune(enteComm.getIdComuneSedeLegale());
				}
				String indAz = getIndirizzoCompleto(enteComm.getToponimoSedeLegale(), enteComm.getIndirizzoSedeLegale(),
						enteComm.getNumCivicoSedeLegale(), enteComm.getCapSedeLegale(),
						enteComm.getIdComuneSedeLegale() != null ? sirtplDComuneDTO.getDenomComune() : null,
						provinciaVO != null ? provinciaVO.getSigla() : null);

				if (enteComm.getCapitaleSociale() != null) {
					NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("da", "DK"));
					numberFormat.setCurrency(Currency.getInstance("EUR"));
					String currencyString = numberFormat.format(enteComm.getCapitaleSociale());

					SirtplaDNaturaGiuridicaDTO flgPatrimonio = sirtplaDNaturaGiuridicaDAO
							.selectByPrimaryKey(enteComm.getIdNaturaGiuridica());
					if (flgPatrimonio.getFlgPatrimonio() != null) {
						if (flgPatrimonio.getFlgPatrimonio().equals("C")) {
							indAz += " - Capitale sociale " + currencyString + "  i.v.";
						} else if (flgPatrimonio.getFlgPatrimonio().equals("F")) {
							indAz += " - Fondo consortile " + currencyString + "  i.v.";
						}
					}
				}

				if (indAz != null && indAz.length() > 0) {
					parameters.put("dati_ente_comm", indAz);
				}
				String dati2EnteComm = getDatiSoggGiurid(enteComm);

				dati2EnteComm += " - Codice Fiscale "
						+ (enteComm.getCodiceFiscale() != null ? enteComm.getCodiceFiscale() : "ND") + " - P.IVA "
						+ (enteComm.getPartitaIva() != null ? enteComm.getPartitaIva() : "ND");
				parameters.put("dati2_ente_comm", dati2EnteComm);

				if (enteComm.getSocUnipersonale() != null) {
					String dati3Az = getSocUnipersonali(enteComm);
					parameters.put("dati3_az", dati3Az);
				}
			}

			RebuspDMotorizzazioneDTO rebuspDMotorizzazioneDTO = rebuspDMotorizzazioneDAO
					.selectByPrimaryKey(dettaglioRichiesta.getIdMotorizzazione());
			if (rebuspDMotorizzazioneDTO != null) {
				parameters.put("denom_uff_motor", rebuspDMotorizzazioneDTO.getDescMotorizzazione());
				parameters.put("ind_uff_motor", rebuspDMotorizzazioneDTO.getIndirizzo());
				if (rebuspDMotorizzazioneDTO.getPec() != null) {
					parameters.put("pec_motorizzazione", rebuspDMotorizzazioneDTO.getPec());
				}
			}
			SirtplaTSoggettoGiuridicoDTO soggEsec = sirtplaTSoggettoGiuridicoDAO
					.selectByPrimaryKey(contrattoProcDatiVO.getEsecTit().getId());
			if (soggEsec != null) {
				parameters.put("denom_az_tit", soggEsec.getDenomSoggettoGiuridico());
				SirtplDComuneDTO sirtplDComuneDTO = null;
				ProvinciaVO provinciaVO = null;
				if (soggEsec.getIdComuneSedeLegale() != null) {
					sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(soggEsec.getIdComuneSedeLegale());
					provinciaVO = luoghiService.getProvinciaByIdComune(soggEsec.getIdComuneSedeLegale());
				}
				parameters.put("ind_az_tit",
						getIndirizzoCompletoIntestazione(soggEsec.getToponimoSedeLegale(),
								soggEsec.getIndirizzoSedeLegale(), soggEsec.getNumCivicoSedeLegale(),
								soggEsec.getCapSedeLegale(),
								soggEsec.getIdComuneSedeLegale() != null ? sirtplDComuneDTO.getDenomComune() : null,
								provinciaVO != null ? provinciaVO.getSigla() : null));
			}
			setCommonParameters(dettaglioRichiesta, contrattoProcDatiVO);
			parameters.put("id_tipo_proc", dettaglioRichiesta.getIdTipoProcedimento());
			parameters.put("tipo_proc", rebuspDTipoProcedimentoDAO
					.selectByPrimaryKey(dettaglioRichiesta.getIdTipoProcedimento()).getDescTipoProcedimento());

			RebuspDMotivazioneDTO rebuspDMotivazioneDTO = rebuspDMotivazioneDAO
					.selectByPrimaryKey(dettaglioRichiesta.getIdMotivazione());
			parameters.put("id_motivazione", dettaglioRichiesta.getIdMotivazione());
			parameters.put("desc_motivazione", rebuspDMotivazioneDTO.getDescMotivazione());
			if (rebuspDMotivazioneDTO.getFlgMotivAltro()) {
				parameters.put("note_motivazione",
						dettaglioRichiesta.getNoteMotivazione() != null
								&& dettaglioRichiesta.getNoteMotivazione().length() > 0
										? dettaglioRichiesta.getNoteMotivazione()
										: null);
			}

			if (dettaglioRichiesta.getPrescrizioni() != null && dettaglioRichiesta.getPrescrizioni().length() > 0) {
				parameters.put("prescrizioni", dettaglioRichiesta.getPrescrizioni());
			}
			parameters.put("premesse", dettaglioRichiesta.getPremesse());
			parameters.put("ruolo_ente", dettaglioRichiesta.getRuoloFirmaEnte());
			parameters.put("firma_ente", dettaglioRichiesta.getNominativoFirmaEnte());
			if (dettaglioRichiesta.getFlgFirmaDigitaleEnte() && dettaglioRichiesta.getRuoloFirmaEnte() != null) {
				parameters.put("firma",
						"Il presente documento \u00E8 sottoscritto con firma digitale \n ai sensi dell\u0027art. 21 del d.lgs 82/2005");
			}
		} catch (Exception e) {
			System.out.println("setParametersScenario2 error = " + e.getMessage());
		}

		return;
	}

	// richiesta nulla osta sostituzione 3
	private void setParametersScenario1Sostituzione(DettaglioRichiestaVO dettaglioRichiesta) throws Exception {
		parameters.put("id_procedimento_sostituzione", dettaglioRichiesta.getId());
		parameters.put("num_progressivo", dettaglioRichiesta.getNumProgressivo());
		if (dettaglioRichiesta.getIters() != null && dettaglioRichiesta.getIters().size() > 0) {
			IterProcedimentoVO iterCorrente = null;
			for (IterProcedimentoVO i : dettaglioRichiesta.getIters()) {
				if (i.getDataFineValidita() == null) {
					iterCorrente = i;
					break;
				}
			}

			if ((iterCorrente.getIdStato().equals(new Long(40L)) || ((iterCorrente.getIdStato().equals(new Long(10L))
					|| iterCorrente.getIdStato().equals(new Long(20L))
					|| iterCorrente.getIdStato().equals(new Long(30L))
					|| iterCorrente.getIdStato().equals(new Long(50L)))
					&& parameters.get("is_anteprima").equals(Boolean.TRUE)))) {

				String pattern = "dd/MM/yyyy";
				DateFormat df = new SimpleDateFormat(pattern);
				parameters.put("data_inizio_validita", df.format(iterCorrente.getDataInizioValidita()));
				List<TransizioneAutomaVO> transizioniAutoma = null;
				try {
					transizioniAutoma = procedimentiService.getTransizioniAutoma(dettaglioRichiesta.getId(),
							new Long(10L), null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Long idGestoreContratto = null;
				IterProcedimentoVO iterCreazione = null;
				if (transizioniAutoma != null) {
					for (TransizioneAutomaVO t : transizioniAutoma) {
						if (t.getIdStatoIterA().equals(new Long(30L))) {
							idGestoreContratto = procedimentiService.getGestoreContratto(
									dettaglioRichiesta.getIdTipoProcedimento(),
									dettaglioRichiesta.getContratto().getIdContratto(), dettaglioRichiesta.getId());
						} else if (t.getIdStatoIterA().equals(new Long(40L))) {
							for (IterProcedimentoVO i : dettaglioRichiesta.getIters()) {
								if (i.getIdStato().equals(new Long(10L))) {
									if (iterCreazione == null || i.getDataInizioValidita()
											.before(iterCreazione.getDataInizioValidita())) {
										iterCreazione = i;
									}
								}
							}
						}
					}
				}
				Long idSogGiurid = null;
				RebusRUtenteAzEnteSelector rebusRUtenteAzEnteSelector = new RebusRUtenteAzEnteSelector();
				if (idGestoreContratto != null) {
					idSogGiurid = idGestoreContratto;
				} else if (iterCreazione != null) {
					rebusRUtenteAzEnteSelector.createCriteria().andIdRUtenteAzEnteEqualTo(rebuspTIterProcedimentoDAO
							.selectByPrimaryKey(iterCreazione.getId()).getIdUtenteSogGiurid());
					List<RebusRUtenteAzEnteDTO> rebusRUtenteAzEnteDTOs = rebusRUtenteAzEnteDAO
							.selectByExample(rebusRUtenteAzEnteSelector);
					if (rebusRUtenteAzEnteDTOs != null && rebusRUtenteAzEnteDTOs.size() > 0) {
						idSogGiurid = rebusRUtenteAzEnteDTOs.get(0).getFkAzienda() != null
								? rebusRUtenteAzEnteDTOs.get(0).getFkAzienda()
								: rebusRUtenteAzEnteDTOs.get(0).getFkEnte();
					}
				}
				if (idSogGiurid != null) {
					SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
					sirtplaTSoggettoGiuridicoSelector.createCriteria().andIdSoggettoGiuridicoEqualTo(idSogGiurid);
					List<SirtplaTSoggettoGiuridicoDTO> sirtplaTSoggettoGiuridicoDTOs = sirtplaTSoggettoGiuridicoDAO
							.selectByExampleWithBLOBs(sirtplaTSoggettoGiuridicoSelector);
					if (sirtplaTSoggettoGiuridicoDTOs != null && sirtplaTSoggettoGiuridicoDTOs.size() > 0) {
						SirtplaTSoggettoGiuridicoDTO sogg = sirtplaTSoggettoGiuridicoDTOs.get(0);
						if (sogg.getLogo() != null) {
							InputStream in = new ByteArrayInputStream(sogg.getLogo());
							BufferedImage image = ImageIO.read(in);
							parameters.put("logo", image);
						}
						parameters.put("denom_az", "<b>" + sogg.getDenomSoggettoGiuridico() + "</b>");

						SirtplDComuneDTO sirtplDComuneDTO = null;
						ProvinciaVO provinciaVO = null;
						if (sogg.getIdComuneSedeLegale() != null) {
							sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(sogg.getIdComuneSedeLegale());
							provinciaVO = luoghiService.getProvinciaByIdComune(sogg.getIdComuneSedeLegale());
						}
						String indAz = getIndirizzoCompleto(sogg.getToponimoSedeLegale(), sogg.getIndirizzoSedeLegale(),
								sogg.getNumCivicoSedeLegale(), sogg.getCapSedeLegale(),
								sogg.getIdComuneSedeLegale() != null ? sirtplDComuneDTO.getDenomComune() : null,
								provinciaVO != null ? provinciaVO.getSigla() : null);

						if (sogg.getCapitaleSociale() != null) {
							NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("da", "DK"));
							numberFormat.setCurrency(Currency.getInstance("EUR"));
							String currencyString = numberFormat.format(sogg.getCapitaleSociale());

							SirtplaDNaturaGiuridicaDTO flgPatrimonio = sirtplaDNaturaGiuridicaDAO
									.selectByPrimaryKey(sogg.getIdNaturaGiuridica());
							if (flgPatrimonio.getFlgPatrimonio() != null) {
								if (flgPatrimonio.getFlgPatrimonio().equals("C")) {
									indAz += " - Capitale sociale " + currencyString + "  i.v.";
								} else if (flgPatrimonio.getFlgPatrimonio().equals("F")) {
									indAz += " - Fondo consortile " + currencyString + "  i.v.";
								}
							}
						}
						if (indAz != null && indAz.length() > 0) {
							parameters.put("dati_az", indAz);
						}
						String dati2Az = getDatiSoggGiurid(sogg);
						dati2Az += " - R.I. di " + provinciaVO.getDenominazione() + " - Codice Fiscale "
								+ (sogg.getCodiceFiscale() != null ? sogg.getCodiceFiscale() : "ND") + " - P.IVA "
								+ (sogg.getPartitaIva() != null ? sogg.getPartitaIva() : "ND");
						parameters.put("dati2_az", dati2Az);

						if (sogg.getSocUnipersonale() != null) {
							String dati3Az = getSocUnipersonali(sogg);
							parameters.put("dati3_az", dati3Az);
						}
					}
				}
			}

		}
		parameters.put("id_tipo_proc_sostituzione", dettaglioRichiesta.getIdTipoProcedimento());
		parameters.put("tipo_proc_sostituzione", rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiesta.getIdTipoProcedimento()).getDescTipoProcedimento());
		parameters.put("ruolo", dettaglioRichiesta.getRuoloFirma());
		parameters.put("rappr_leg", dettaglioRichiesta.getNominativoFirma());

		if (dettaglioRichiesta.getFlgFirmaDigitale() && dettaglioRichiesta.getRuoloFirma() != null) {
			parameters.put("firma",
					"Il presente documento \u00E8 sottoscritto con firma digitale \n ai sensi dell\u0027art. 21 del d.lgs 82/2005");
		}

		DettaglioRichiestaVO dettaglioRichiestaAlienazione = procedimentiService
				.dettaglioRichiesta(dettaglioRichiesta.getSubProcedimento().getIdSubProcedimento1(), "VIEW");
		DettaglioRichiestaVO dettaglioRichiestaImmOReImm = procedimentiService
				.dettaglioRichiesta(dettaglioRichiesta.getSubProcedimento().getIdSubProcedimento2(), "VIEW");

		parameters.put("id_procedimento_alienazione", dettaglioRichiestaAlienazione.getId());
		parameters.put("id_procedimento_imm_o_reimm", dettaglioRichiestaImmOReImm.getId());

		ContrattoProcDatiVO contrattoProcDatiVO = procedimentiService
				.getDatiContratto(dettaglioRichiesta.getContratto().getIdContratto(), dettaglioRichiesta.getId());
		SirtplaTSoggettoGiuridicoDTO enteComm = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(contrattoProcDatiVO.getEnteComm().getId());
		parameters.put("denom_ente_comm", enteComm.getDenomSoggettoGiuridico());
		SirtplDComuneDTO sirtplDComuneDTO = null;
		ProvinciaVO provinciaVO = null;
		if (enteComm.getIdComuneSedeLegale() != null) {
			sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(enteComm.getIdComuneSedeLegale());
			provinciaVO = luoghiService.getProvinciaByIdComune(enteComm.getIdComuneSedeLegale());
		}
		String indEnteComm = getIndirizzoCompletoIntestazione(enteComm.getToponimoSedeLegale(),
				enteComm.getIndirizzoSedeLegale(), enteComm.getNumCivicoSedeLegale(), enteComm.getCapSedeLegale(),
				sirtplDComuneDTO != null ? sirtplDComuneDTO.getDenomComune() : null,
				provinciaVO != null ? provinciaVO.getSigla() : null);
		if (indEnteComm != null && indEnteComm.length() > 0) {
			parameters.put("ind_ente_comm", indEnteComm);
		}
		if (enteComm.getPecSedeLegale() != null && enteComm.getPecSedeLegale().length() > 0) {
			parameters.put("pec_ente_comm", enteComm.getPecSedeLegale());
		}

		setCommonParameters(dettaglioRichiestaAlienazione, contrattoProcDatiVO);

		RebuspDMotorizzazioneDTO rebuspDMotorizzazioneDTO = rebuspDMotorizzazioneDAO
				.selectByPrimaryKey(dettaglioRichiestaAlienazione.getIdMotorizzazione());
		parameters.put("desc_motorizzazione", rebuspDMotorizzazioneDTO.getDescMotorizzazione());
		parameters.put("ind_motorizzazione", rebuspDMotorizzazioneDTO.getIndirizzo());
		if (rebuspDMotorizzazioneDTO.getPec() != null) {
			parameters.put("pec_motorizzazione", rebuspDMotorizzazioneDTO.getPec());
		}
		RebuspDMotivazioneDTO motivazione = rebuspDMotivazioneDAO
				.selectByPrimaryKey(dettaglioRichiestaImmOReImm.getIdMotivazione());
		parameters.put("id_motivazione", motivazione.getIdMotivazione());
		parameters.put("desc_motivazione_imm_o_reimm", motivazione.getDescMotivazione());

		if (motivazione.getFlgMotivAltro()) {
			parameters.put("note_motiv_imm_o_reimm",
					dettaglioRichiestaImmOReImm.getNoteMotivazione() != null
							&& dettaglioRichiestaImmOReImm.getNoteMotivazione().length() > 0
									? dettaglioRichiestaImmOReImm.getNoteMotivazione()
									: null);
		}
		parameters.put("id_tipo_proc_alienazione", dettaglioRichiestaAlienazione.getIdTipoProcedimento());
		parameters.put("tipo_proc_alienazione", rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiestaAlienazione.getIdTipoProcedimento()).getDescTipoProcedimento());
		parameters.put("id_tipo_proc_imm_o_reimm", dettaglioRichiestaImmOReImm.getIdTipoProcedimento());
		parameters.put("tipo_proc_imm_o_reimm", rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiestaImmOReImm.getIdTipoProcedimento()).getDescTipoProcedimento());

		SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(contrattoProcDatiVO.getEsecTit().getId());
		parameters.put("denom_sg_tit_esec", sirtplaTSoggettoGiuridicoDTO.getDenomSoggettoGiuridico());

		String allsImmOReImm = "";
		for (VeicoloVO v : dettaglioRichiestaImmOReImm.getVeicoli()) {
			for (AllegatoVeicoloVO a : v.getAllegati()) {
				allsImmOReImm += "- " + a.getTipoDocumento().getDescrizione() + " ("
						+ (dettaglioRichiestaImmOReImm.getIdTipoProcedimento() == 1 ? ("Telaio: " + v.getPrimoTelaio())
								: ("Targa: " + v.getnTarga()))
						+ ") <br>";
			}
		}
		if (allsImmOReImm.length() > 0) {
			allsImmOReImm = allsImmOReImm.substring(0, allsImmOReImm.lastIndexOf("<") - 1);
		}
		parameters.put("all_imm_o_reimm", allsImmOReImm);

		String allsAlienazione = "";
		for (VeicoloVO v : dettaglioRichiestaAlienazione.getVeicoli()) {
			for (AllegatoVeicoloVO a : v.getAllegati()) {
				allsAlienazione += "- " + a.getTipoDocumento().getDescrizione() + " ("
						+ (dettaglioRichiestaAlienazione.getIdTipoProcedimento() == 1
								? ("Telaio: " + v.getPrimoTelaio())
								: ("Targa: " + v.getnTarga()))
						+ ") <br>";
			}
		}
		if (allsAlienazione.length() > 0) {
			allsAlienazione = allsAlienazione.substring(0, allsAlienazione.lastIndexOf("<") - 1);
		}
		parameters.put("all_alienazione", allsAlienazione);
		parameters.put("note_imm_o_reimm", dettaglioRichiestaImmOReImm.getNota());
		parameters.put("note_alienazione", dettaglioRichiestaAlienazione.getNota());
		parameters.put("note", dettaglioRichiesta.getNota());

		return;
	}

	// rilascio nulla osta sostituzione 4
	private void setParametersScenario2Sostituzione(DettaglioRichiestaVO dettaglioRichiesta) throws Exception {

		parameters.put("id_procedimento_sostituzione", dettaglioRichiesta.getId());
		parameters.put("num_progressivo", dettaglioRichiesta.getNumProgressivo());
		if (dettaglioRichiesta.getIters() != null && dettaglioRichiesta.getIters().size() > 0) {
			for (IterProcedimentoVO i : dettaglioRichiesta.getIters()) {
				if (i.getDataFineValidita() == null
						&& (i.getIdStato().equals(new Long(60L)) || (i.getIdStato().equals(new Long(40L))
								&& parameters.get("is_anteprima").equals(Boolean.TRUE)))) {
					String pattern = "dd/MM/yyyy";
					DateFormat df = new SimpleDateFormat(pattern);
					parameters.put("data_inizio_validita", df.format(i.getDataInizioValidita()));
					break;
				}
			}
		}

		RebuspTIterProcedimentoSelector rebuspTIterProcedimentoSelector = new RebuspTIterProcedimentoSelector();
		rebuspTIterProcedimentoSelector.createCriteria().andIdProcedimentoEqualTo(dettaglioRichiesta.getId())
				.andIdStatoIterEqualTo(10L).andDataFineValiditaIsNotNull();
		rebuspTIterProcedimentoSelector.setOrderByClause("data_inizio_validita");
		List<RebuspTIterProcedimentoDTO> rebuspTIterProcedimentoDTOs = rebuspTIterProcedimentoDAO
				.selectByExample(rebuspTIterProcedimentoSelector);
		if (rebuspTIterProcedimentoDTOs != null && rebuspTIterProcedimentoDTOs.size() > 0) {
			RebuspTIterProcedimentoDTO iterDto = rebuspTIterProcedimentoDTOs.get(0);
			SirtplRUtenteSogGiuridDTO sirtplRUtenteSogGiuridDTO = sirtplRUtenteSogGiuridDAO
					.selectByPrimaryKey(iterDto.getIdUtenteSogGiurid());
			if (sirtplRUtenteSogGiuridDTO != null) {
				SirtplaTSoggettoGiuridicoDTO sirtplaTSoggettoGiuridicoDTO = sirtplaTSoggettoGiuridicoDAO
						.selectByPrimaryKey(sirtplRUtenteSogGiuridDTO.getIdSoggettoGiuridico());
				parameters.put("denom_sogg_creatore_richiesta",
						sirtplaTSoggettoGiuridicoDTO.getDenomSoggettoGiuridico());
			}
		}

		DettaglioRichiestaVO dettaglioRichiestaAlienazione = procedimentiService
				.dettaglioRichiesta(dettaglioRichiesta.getSubProcedimento().getIdSubProcedimento1(), "VIEW");
		if (dettaglioRichiestaAlienazione != null) {
			String n_alienati = String.valueOf(dettaglioRichiestaAlienazione.getVeicoli().size());
			parameters.put("n_alienati", n_alienati);
		}
		DettaglioRichiestaVO dettaglioRichiestaImmOReImm = procedimentiService
				.dettaglioRichiesta(dettaglioRichiesta.getSubProcedimento().getIdSubProcedimento2(), "VIEW");

		parameters.put("id_procedimento_alienazione", dettaglioRichiestaAlienazione.getId());
		parameters.put("id_procedimento_imm_o_reimm", dettaglioRichiestaImmOReImm.getId());

		ContrattoProcDatiVO contrattoProcDatiVO = procedimentiService
				.getDatiContratto(dettaglioRichiesta.getContratto().getIdContratto(), dettaglioRichiesta.getId());
		SirtplaTSoggettoGiuridicoSelector sirtplaTSoggettoGiuridicoSelector = new SirtplaTSoggettoGiuridicoSelector();
		sirtplaTSoggettoGiuridicoSelector.createCriteria()
				.andIdSoggettoGiuridicoEqualTo(contrattoProcDatiVO.getEnteComm().getId());
		List<SirtplaTSoggettoGiuridicoDTO> sirtplaTSoggettoGiuridicoDTOs = sirtplaTSoggettoGiuridicoDAO
				.selectByExampleWithBLOBs(sirtplaTSoggettoGiuridicoSelector);
		SirtplaTSoggettoGiuridicoDTO enteComm = null;
		if (sirtplaTSoggettoGiuridicoDTOs != null && sirtplaTSoggettoGiuridicoDTOs.size() > 0) {
			enteComm = sirtplaTSoggettoGiuridicoDTOs.get(0);
			if (enteComm.getLogo() != null) {
				InputStream in = new ByteArrayInputStream(enteComm.getLogo());
				BufferedImage image = ImageIO.read(in);
				parameters.put("logo", image);
			}
			parameters.put("denom_ente_comm", enteComm.getDenomSoggettoGiuridico());

			SirtplDComuneDTO sirtplDComuneDTO = null;
			ProvinciaVO provinciaVO = null;
			if (enteComm.getIdComuneSedeLegale() != null) {
				sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(enteComm.getIdComuneSedeLegale());
				provinciaVO = luoghiService.getProvinciaByIdComune(enteComm.getIdComuneSedeLegale());
			}
			String indAz = getIndirizzoCompleto(enteComm.getToponimoSedeLegale(), enteComm.getIndirizzoSedeLegale(),
					enteComm.getNumCivicoSedeLegale(), enteComm.getCapSedeLegale(),
					enteComm.getIdComuneSedeLegale() != null ? sirtplDComuneDTO.getDenomComune() : null,
					provinciaVO != null ? provinciaVO.getSigla() : null);

			if (enteComm.getCapitaleSociale() != null) {
				NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("da", "DK"));
				numberFormat.setCurrency(Currency.getInstance("EUR"));
				String currencyString = numberFormat.format(enteComm.getCapitaleSociale());
				SirtplaDNaturaGiuridicaDTO flgPatrimonio = sirtplaDNaturaGiuridicaDAO
						.selectByPrimaryKey(enteComm.getIdNaturaGiuridica());
				if (flgPatrimonio.getFlgPatrimonio() != null) {
					if (flgPatrimonio.getFlgPatrimonio().equals("C")) {
						indAz += " - Capitale sociale " + currencyString + "  i.v.";
					} else if (flgPatrimonio.getFlgPatrimonio().equals("F")) {
						indAz += " - Fondo consortile " + currencyString + "  i.v.";
					}
				}
			}

			if (indAz != null && indAz.length() > 0) {
				parameters.put("dati_ente_comm", indAz);
			}
			String dati2EnteComm = getDatiSoggGiurid(enteComm);
			dati2EnteComm += " - Codice Fiscale "
					+ (enteComm.getCodiceFiscale() != null ? enteComm.getCodiceFiscale() : "ND") + " - P.IVA "
					+ (enteComm.getPartitaIva() != null ? enteComm.getPartitaIva() : "ND");
			parameters.put("dati2_ente_comm", dati2EnteComm);

			if (enteComm.getSocUnipersonale() != null) {
				String dati3Az = getSocUnipersonali(enteComm);
				parameters.put("dati3_az", dati3Az);
			}
		}

		RebuspDMotorizzazioneDTO rebuspDMotorizzazioneDTO = rebuspDMotorizzazioneDAO
				.selectByPrimaryKey(dettaglioRichiestaAlienazione.getIdMotorizzazione());
		if (rebuspDMotorizzazioneDTO != null) {
			parameters.put("denom_uff_motor", rebuspDMotorizzazioneDTO.getDescMotorizzazione());
			parameters.put("ind_uff_motor", rebuspDMotorizzazioneDTO.getIndirizzo());
			if (rebuspDMotorizzazioneDTO.getPec() != null) {
				parameters.put("pec_motorizzazione", rebuspDMotorizzazioneDTO.getPec());
			}
		}
		SirtplaTSoggettoGiuridicoDTO soggEsec = sirtplaTSoggettoGiuridicoDAO
				.selectByPrimaryKey(contrattoProcDatiVO.getEsecTit().getId());
		if (soggEsec != null) {
			parameters.put("denom_az_tit", soggEsec.getDenomSoggettoGiuridico());
			SirtplDComuneDTO sirtplDComuneDTO = null;
			ProvinciaVO provinciaVO = null;
			if (soggEsec.getIdComuneSedeLegale() != null) {
				sirtplDComuneDTO = sirtplDComuneDAO.selectByPrimaryKey(soggEsec.getIdComuneSedeLegale());
				provinciaVO = luoghiService.getProvinciaByIdComune(soggEsec.getIdComuneSedeLegale());
			}
			parameters.put("ind_az_tit", getIndirizzoCompletoIntestazione(soggEsec.getToponimoSedeLegale(),
					soggEsec.getIndirizzoSedeLegale(), soggEsec.getNumCivicoSedeLegale(), soggEsec.getCapSedeLegale(),
					soggEsec.getIdComuneSedeLegale() != null ? sirtplDComuneDTO.getDenomComune() : null,
					provinciaVO != null ? provinciaVO.getSigla() : null));
		}
		setCommonParameters(dettaglioRichiestaAlienazione, contrattoProcDatiVO);
		parameters.put("tipo_proc_sostituzione", rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiesta.getIdTipoProcedimento()).getDescTipoProcedimento());
		parameters.put("id_tipo_proc_alienazione", dettaglioRichiestaAlienazione.getIdTipoProcedimento());
		parameters.put("tipo_proc_alienazione", rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiestaAlienazione.getIdTipoProcedimento()).getDescTipoProcedimento());
		parameters.put("id_tipo_proc_imm_o_reimm", dettaglioRichiestaImmOReImm.getIdTipoProcedimento());
		parameters.put("tipo_proc_imm_o_reimm", rebuspDTipoProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiestaImmOReImm.getIdTipoProcedimento()).getDescTipoProcedimento());
		parameters.put("id_motivazione", dettaglioRichiestaImmOReImm.getIdMotivazione());
		parameters.put("desc_motivazione_imm_o_reimm", rebuspDMotivazioneDAO
				.selectByPrimaryKey(dettaglioRichiestaImmOReImm.getIdMotivazione()).getDescMotivazione());
		parameters.put("note_motiv_imm_o_reimm",
				dettaglioRichiestaImmOReImm.getNoteMotivazione() != null
						&& dettaglioRichiestaImmOReImm.getNoteMotivazione().length() > 0
								? dettaglioRichiestaImmOReImm.getNoteMotivazione()
								: null);

		if (dettaglioRichiesta.getPrescrizioni() != null && dettaglioRichiesta.getPrescrizioni().length() > 0) {
			parameters.put("prescrizioni", dettaglioRichiesta.getPrescrizioni());
		}
		parameters.put("premesse", dettaglioRichiesta.getPremesse());
		parameters.put("ruolo_ente", dettaglioRichiesta.getRuoloFirmaEnte());
		parameters.put("firma_ente", dettaglioRichiesta.getNominativoFirmaEnte());
		// controllare
		if (dettaglioRichiesta.getFlgFirmaDigitaleEnte() != null) {
			if (dettaglioRichiesta.getFlgFirmaDigitaleEnte() == true
					&& dettaglioRichiesta.getRuoloFirmaEnte() != null) {
				parameters.put("firma",
						"Il presente documento \u00E8 sottoscritto con firma digitale \n ai sensi dell\u0027art. 21 del d.lgs 82/2005");
			}
		}
		return;
	}

	private void setCommonParameters(DettaglioRichiestaVO dettaglioRichiesta, ContrattoProcDatiVO contrattoProcDatiVO) {
		Collections.sort(dettaglioRichiesta.getIters(), new Comparator<IterProcedimentoVO>() {

			@Override
			public int compare(IterProcedimentoVO o1, IterProcedimentoVO o2) {
				return o1.getDataInizioValidita().compareTo(o2.getDataInizioValidita());
			}
		});

		RebuspTIterProcedimentoDTO rebuspTIterProcedimentoDTO = rebuspTIterProcedimentoDAO
				.selectByPrimaryKey(dettaglioRichiesta.getIters().get(0).getId());
		if (rebuspTIterProcedimentoDTO != null) {
			RebusRUtenteAzEnteSelector rebusRUtenteAzEnteSelector = new RebusRUtenteAzEnteSelector();
			rebusRUtenteAzEnteSelector.createCriteria()
					.andIdRUtenteAzEnteEqualTo(rebuspTIterProcedimentoDTO.getIdUtenteSogGiurid());
			List<RebusRUtenteAzEnteDTO> rebusRUtenteAzEnteDTOs = rebusRUtenteAzEnteDAO
					.selectByExample(rebusRUtenteAzEnteSelector);
			if (rebusRUtenteAzEnteDTOs != null && rebusRUtenteAzEnteDTOs.size() > 0) {
				// soggetto che ha creato la richiesta, proprietaria dei veicoli
				Long idSogGiurid = rebusRUtenteAzEnteDTOs.get(0).getFkAzienda() != null
						? rebusRUtenteAzEnteDTOs.get(0).getFkAzienda()
						: rebusRUtenteAzEnteDTOs.get(0).getFkEnte();
				Long idCapofilaRaggr = contrattoProcDatiVO.getCapofila() != null
						? contrattoProcDatiVO.getCapofila().getId()
						: null;
				// l azienda proprietaria dei veicoli non e ne il soggetto
				// esecutore del contratto legato al procedimento, ne la
				// capofila del raggruppamento che ne e eventualmente esecutore,
				if (!idSogGiurid.equals(contrattoProcDatiVO.getEsecTit().getId())
						&& (idCapofilaRaggr == null || !idSogGiurid.equals(idCapofilaRaggr))) {
					SirtplaTSoggettoGiuridicoDTO soggettoUtente = sirtplaTSoggettoGiuridicoDAO
							.selectByPrimaryKey(idSogGiurid);

					parameters.put("denom_az_propr", soggettoUtente.getDenomSoggettoGiuridico());
					SirtplDComuneDTO comuneAzPropr = null;
					ProvinciaVO provinciaVO = null;
					if (soggettoUtente.getIdComuneSedeLegale() != null) {
						comuneAzPropr = sirtplDComuneDAO.selectByPrimaryKey(soggettoUtente.getIdComuneSedeLegale());
						provinciaVO = luoghiService.getProvinciaByIdComune(soggettoUtente.getIdComuneSedeLegale());
					}
					String indAzPropr = getIndirizzoCompletoIntestazione(soggettoUtente.getToponimoSedeLegale(),
							soggettoUtente.getIndirizzoSedeLegale(), soggettoUtente.getNumCivicoSedeLegale(),
							soggettoUtente.getCapSedeLegale(),
							comuneAzPropr != null ? comuneAzPropr.getDenomComune() : null,
							provinciaVO != null ? provinciaVO.getSigla() : null);
					if (indAzPropr != null && indAzPropr.length() > 0) {
						parameters.put("ind_az_propr", indAzPropr);
					}
					if (contrattoProcDatiVO.getIsSubaffidataria() && contrattoProcDatiVO.getListaSubaffidatarie()
							.contains(soggettoUtente.getIdSoggettoGiuridico())) {
						String frase = "L'azienda " + soggettoUtente.getDenomSoggettoGiuridico()
								+ " \u00E8 subaffidataria dell'esecutore titolare nel contratto su indicato";
						parameters.put("frase", frase);
					}
					// non dovrebbe mai essere eseguito -> legalmente non esiste
					else if (contrattoProcDatiVO.getIsSubaffidatariaConsorziata()) {
						String frase = "L'azienda " + soggettoUtente.getDenomSoggettoGiuridico()
								+ " \u00E8 subaffidataria consorziata dell'esecutore titolare nel contratto su indicato";
						parameters.put("frase", frase);
					}

					else if (contrattoProcDatiVO.getIdTipoRaggruppamento() != null) {
						String assoc = contrattoProcDatiVO.getIdTipoRaggruppamento() == 1 ? "consorziata" : "mandante";
						String frase = "L'azienda " + soggettoUtente.getDenomSoggettoGiuridico() + " \u00E8 " + assoc
								+ " dell'esecutore titolare nel contratto su indicato";
						parameters.put("frase", frase);
					}
				}
			}
		}

		parameters.put("cod_regionale",
				"CDS" + String.format("%04d", dettaglioRichiesta.getContratto().getIdContratto()));
		parameters.put("desc_contratto", contrattoProcDatiVO.getDescrizione());
		SirtplcTContrattoDTO sirtplcTContrattoDTO = sirtplcTContrattoDAO
				.selectByPrimaryKey(dettaglioRichiesta.getContratto().getIdContratto());
		if (sirtplcTContrattoDTO.getIdBacino() != null) {
			parameters.put("bacino",
					sirtplcDBacinoDAO.selectByPrimaryKey(sirtplcTContrattoDTO.getIdBacino()).getDenomBacino());
		} else {
			parameters.put("bacino", " ");
		}
		if (contrattoProcDatiVO.getIdTipoRaggruppamento() != null) {
			SirtplcDTipoRaggruppamentoDTO sirtplcDTipoRaggruppamentoDTO = sirtplcDTipoRaggruppamentoDAO
					.selectByPrimaryKey(contrattoProcDatiVO.getIdTipoRaggruppamento());
			parameters.put("tipo_raggr", sirtplcDTipoRaggruppamentoDTO.getDescTipoRaggruppamento());
			if (contrattoProcDatiVO.getCapofila() != null) {
				SirtplaTSoggettoGiuridicoDTO capofilaDTO = sirtplaTSoggettoGiuridicoDAO
						.selectByPrimaryKey(contrattoProcDatiVO.getCapofila().getId());
				parameters.put("capofila", capofilaDTO.getDenomSoggettoGiuridico());
			}
		}
	}

	private String getDatiSoggGiurid(SirtplaTSoggettoGiuridicoDTO sogg) {
		String datiSogg = "";
		if (sogg.getTelefonoSedeLegale() != null && sogg.getTelefonoSedeLegale().length() > 0) {
			datiSogg += "Tel.: " + sogg.getTelefonoSedeLegale();
			if ((sogg.getFaxSedeLegale() != null && sogg.getFaxSedeLegale().length() > 0)
					|| (sogg.getEmailSedeLegale() != null && sogg.getEmailSedeLegale().length() > 0)) {
				datiSogg += " - ";
			}
		}
		if (sogg.getFaxSedeLegale() != null && sogg.getFaxSedeLegale().length() > 0) {
			datiSogg += "Fax: " + sogg.getFaxSedeLegale();
			if (sogg.getEmailSedeLegale() != null && sogg.getEmailSedeLegale().length() > 0) {
				datiSogg += " - ";
			}
		}
		if (sogg.getEmailSedeLegale() != null && sogg.getEmailSedeLegale().length() > 0) {
			datiSogg += "email: " + sogg.getEmailSedeLegale();
		}
		return datiSogg;
	}

	private String getSocUnipersonali(SirtplaTSoggettoGiuridicoDTO sogg) {
		String datiSoc = "";
		if (sogg.getSocUnipersonale() != null && sogg.getSocUnipersonale().length() > 0) {
			datiSoc += sogg.getSocUnipersonale();
		}

		return datiSoc;
	}

	private String getIndirizzoCompletoIntestazione(String top, String indirizzo, String civico, String cap,
			String denomComune, String siglaProvincia) {
		String s = "";
		boolean minus = false;
		if (top != null) {
			s += top + " ";
			minus = true;
		}
		if (indirizzo != null) {
			s += indirizzo + " ";
			minus = true;
		}
		if (civico != null) {
			s += civico + " ";
			minus = true;
		}
		if (minus && (cap != null || denomComune != null))
			s += "<br>";
		if (cap != null)
			s += cap + " ";
		if (denomComune != null)
			s += denomComune;
		if (siglaProvincia != null)
			s += " (" + siglaProvincia + ")";

		return s;
	}

	private String getIndirizzoCompleto(String top, String indirizzo, String civico, String cap, String denomComune,
			String siglaProvincia) {
		String s = "";
		boolean minus = false;
		if (top != null) {
			s += top + " ";
			minus = true;
		}
		if (indirizzo != null) {
			s += indirizzo + " ";
			minus = true;
		}
		if (civico != null) {
			s += civico + " ";
			minus = true;
		}
		if (minus && (cap != null || denomComune != null))
			s += "- ";
		if (cap != null)
			s += cap + " ";
		if (denomComune != null)
			s += denomComune;
		if (siglaProvincia != null)
			s += " (" + siglaProvincia + ")";

		return s;
	}

}
