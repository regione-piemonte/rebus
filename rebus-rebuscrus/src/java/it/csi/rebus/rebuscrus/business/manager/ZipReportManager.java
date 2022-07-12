/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.manager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.service.ContribuzioneService;
import it.csi.rebus.rebuscrus.business.service.DocumentoService;
import it.csi.rebus.rebuscrus.common.exception.RebusException;
import it.csi.rebus.rebuscrus.integration.dao.custom.ContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.DocumentiDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ExcelDAO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaContribuzioneDTO;
import it.csi.rebus.rebuscrus.util.UtilRebus;
import it.csi.rebus.rebuscrus.vo.ContribuzioneCompletaVO;
import it.csi.rebus.rebuscrus.vo.DatoFatturaVO;
import it.csi.rebus.rebuscrus.vo.DocContribuzioneVO;
import it.csi.rebus.rebuscrus.vo.DocumentoVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;

@Component
public class ZipReportManager {

	private static Logger LOGGER = Logger
			.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + ".ZipReportManager");

	@Autowired
	private ContribuzioneDAO contribuzioneDAO;

	@Autowired
	ContribuzioneService contribuzioneService;

	@Autowired
	private DocumentiDAO documentiDAO;

	public byte[] zipRicercaContribuzione(FiltroContribuzioneVO filtro) {
		try {
			LOGGER.info("ZipReportManager::zipRicercaContribuzione START");

			List<VExportRicercaContribuzioneDTO> lista = contribuzioneDAO.getElencoContribuzione(filtro);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(bos);

			for (VExportRicercaContribuzioneDTO telaio : lista) {

				if (telaio.getIdProcedimento() != null) {
					String filename = telaio.getPrimoTelaio();

					ContribuzioneCompletaVO documenti = contribuzioneService
							.getContribuzioneCompletaByIdProcedimento(telaio.getIdProcedimento());
					ZipEntry entryTelaio = new ZipEntry(filename + "/"); // creazione cartella telaio
					zos.putNextEntry(entryTelaio);

					if (documenti != null) {
						// contribuzione
						DocContribuzioneVO docContribuzione = documenti.getDocumentoContribuzione();
						String docName;
						if (docContribuzione != null && (docContribuzione.getIdTipoDocumento() !=null)) {
							String tipo = documentiDAO.elencoDocumentiDesc(docContribuzione.getIdTipoDocumento())
									.replace(" ", "_");
							docName = docContribuzione.getNomeFile();
							docContribuzione.getIdTipoDocumento();
							ZipEntry entry = new ZipEntry(filename + "/" + tipo + "/" + docName);
							zos.putNextEntry(entry);
							zos.write(contribuzioneService.getDocContribuzione(
									documenti.getDocumentoContribuzione().getIdDocContribuzione()));
						}

						// fatture e quietanze
						List<DatoFatturaVO> docFatture = documenti.getDatiFattura();
						if (docFatture != null) {
							String nomeFattura;
							int i = 1; // fatture
							int y = 1; // quietanza
							for (DatoFatturaVO f : docFatture) {
								String tipo = documentiDAO.elencoDocumentiDesc(f.getDocumento().getIdTipoDocumento())
										.replace(" ", "_");
								if (f.getIdTipoDocQuietanza() != null) {
									nomeFattura = f.getDocumento().getNomeFile().replace(".pdf", "");
									nomeFattura = nomeFattura + "_" + y + ".pdf";
									y++;
								} else {
									nomeFattura = f.getDocumento().getNomeFile().replace(".pdf", "");
									nomeFattura = nomeFattura + "_" + i + ".pdf";
									i++;
								}
								ZipEntry entry = new ZipEntry(filename + "/" + tipo + "/" + nomeFattura);
								zos.putNextEntry(entry);
								zos.write(contribuzioneService
										.getDocContribuzione(f.getDocumento().getIdDocContribuzione()));

							}
						}

						//alienazione
						DocContribuzioneVO docAlienazione = documenti.getDocumentoAlienazione();
						String nameAlienazione;
						if (docAlienazione != null) {
							nameAlienazione = docAlienazione.getNomeFile();
							String tipo = documentiDAO.elencoDocumentiDesc(docAlienazione.getIdTipoDocumento())
									.replace(" ", "_");
							ZipEntry entry = new ZipEntry(filename + "/" + tipo + "/" + nameAlienazione);
							zos.putNextEntry(entry);
							zos.write(contribuzioneService
									.getDocContribuzione(documenti.getDocumentoAlienazione().getIdDocContribuzione()));
						}

						//atto obbligo
						DocContribuzioneVO docAtto = documenti.getDocumentoAttoObbligo();
						String nameAtto;
						if (docAtto != null) {
							nameAtto = docAtto.getNomeFile();
							String tipo = documentiDAO.elencoDocumentiDesc(docAtto.getIdTipoDocumento()).replace(" ",
									"_");
							ZipEntry entry = new ZipEntry(filename + "/" + tipo + "/" + nameAtto);
							zos.putNextEntry(entry);
							zos.write(contribuzioneService
									.getDocContribuzione(documenti.getDocumentoAttoObbligo().getIdDocContribuzione()));
						}

						//carta circolazione
						DocContribuzioneVO docCartaCircolazione = documenti.getDocumentoCartaCircolazione();
						String nameCircolazione;
						if (docCartaCircolazione != null) {
							String tipo = documentiDAO.elencoDocumentiDesc(docCartaCircolazione.getIdTipoDocumento())
									.replace(" ", "_");
							nameCircolazione = docCartaCircolazione.getNomeFile();
							ZipEntry entry = new ZipEntry(filename + "/" + tipo + "/" + nameCircolazione);
							zos.putNextEntry(entry);
							zos.write(contribuzioneService.getDocContribuzione(
									documenti.getDocumentoCartaCircolazione().getIdDocContribuzione()));
						}

						//garanzia
						DocContribuzioneVO docGaranzia = documenti.getDocumentoGaranzia();
						String nameGaranzia;
						if (docGaranzia != null) {
							String tipo = documentiDAO.elencoDocumentiDesc(docGaranzia.getIdTipoDocumento())
									.replace(" ", "_").replace("/", "-");
							nameGaranzia = docGaranzia.getNomeFile();
							ZipEntry entry = new ZipEntry(filename + "/" + tipo + "/" + nameGaranzia);
							zos.putNextEntry(entry);
							zos.write(contribuzioneService
									.getDocContribuzione(documenti.getDocumentoGaranzia().getIdDocContribuzione()));
						}

						DocContribuzioneVO docMisure = documenti.getDocumentoMisureEmissioni();
						String nameMisure;
						if (docMisure != null) {
							String tipo = documentiDAO.elencoDocumentiDesc(docMisure.getIdTipoDocumento()).replace(" ",
									"_");
							nameMisure = docMisure.getNomeFile();
							ZipEntry entry = new ZipEntry(filename + "/" + tipo + "/" + nameMisure);
							zos.putNextEntry(entry);
							zos.write(contribuzioneService.getDocContribuzione(
									documenti.getDocumentoMisureEmissioni().getIdDocContribuzione()));
						}
					}
					zos.closeEntry();

				}
			}
			zos.close();

			return bos.toByteArray();

		} catch (Exception e) {
			LOGGER.error("ZipReportManager::zipRicercaContribuzione excpetion: " + e.getMessage() + " - "
					+ UtilRebus.getStackTrace(e));
			// Rilancio una RebusException
			throw new RebusException(e);
		} finally {
			LOGGER.info("ZipReportManager::zipRicercaContribuzione END");
		}

	}

}