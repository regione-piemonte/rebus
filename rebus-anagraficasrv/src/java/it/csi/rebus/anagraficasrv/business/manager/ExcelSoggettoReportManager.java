/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.business.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.anagraficasrv.common.exception.ErroreGestitoException;
import it.csi.rebus.anagraficasrv.common.exception.RebusException;
import it.csi.rebus.anagraficasrv.excel.ExcelDto;
import it.csi.rebus.anagraficasrv.excel.ExcelDto.BodyRow;
import it.csi.rebus.anagraficasrv.excel.ExcelDto.ColBodyRow;
import it.csi.rebus.anagraficasrv.excel.ExcelDto.ColHeaderRow;
import it.csi.rebus.anagraficasrv.excel.ExcelUtil;
import it.csi.rebus.anagraficasrv.excel.ExcelUtil.EXCEL_PATTERN;
import it.csi.rebus.anagraficasrv.integration.dao.custom.ExcelDAO;
import it.csi.rebus.anagraficasrv.integration.dto.VExportRicercaSoggGiurDTO;
import it.csi.rebus.anagraficasrv.security.CodeRoles;
import it.csi.rebus.anagraficasrv.security.UserInfo;
import it.csi.rebus.anagraficasrv.util.DateUtils;
import it.csi.rebus.anagraficasrv.util.SecurityUtils;
import it.csi.rebus.anagraficasrv.vo.FiltroSoggettoVO;

@Component
public class ExcelSoggettoReportManager {

	@Autowired
	private ExcelDAO excel;

	public byte[] exportRicercaSoggetto(FiltroSoggettoVO filtro) {
		try {

			UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
			// se ho ruolo azienda o ruolo ente
			if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_AZIENDA.getId())
					|| userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_PILOTA_AZIENDA.getId())) {
				filtro.setIdSoggettoGiuridico(userInfo.getIdAzienda());
			} else if (userInfo.getRuolo().getCodiceRuolo().equals(CodeRoles.RUOLO_ENTE.getId())) {
				filtro.setIdSoggettoGiuridico(userInfo.getIdEnte());
			}
			filtro.prepareSQL();
			List<VExportRicercaSoggGiurDTO> listaSoggetto;
			try {
				listaSoggetto = excel.getExcelSoggettoReport(filtro);
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'estrazione dei soggetti. Contattare l'assistenza");
			}

			// lista gestione fogli excel
			List<ExcelDto> listaExcel = new ArrayList<ExcelDto>();

			// lista nomi fogli
			List<String> nameSheet = new ArrayList<String>();
			nameSheet.add("soggetti");

			// nome file excel
			StringBuilder fileName = new StringBuilder();
			fileName.append("soggetti_" + DateUtils.getTimeStamp());

			for (String sheet : nameSheet) {
				if (sheet.equalsIgnoreCase("soggetti")) {

					ExcelDto excel = new ExcelDto();
					excel.setFileName(fileName.toString());
					excel.setSheetName(sheet);

					excel.setListColHeaderRow(getHeaderXlsExportaSoggetto(false));
					List<BodyRow> listBodyRow = new ArrayList<ExcelDto.BodyRow>();

					for (VExportRicercaSoggGiurDTO soggetto : listaSoggetto) {
						BodyRow bodyRow = new BodyRow();

						List<ColBodyRow> listColBodyRow = new ArrayList<ExcelDto.ColBodyRow>();
						listColBodyRow.add(new ColBodyRow(soggetto.getTipoSoggettoGiuridico()));
						listColBodyRow.add(new ColBodyRow(soggetto.getCodregionale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getCodOsservatorioNaz()));
						listColBodyRow.add(new ColBodyRow(soggetto.getCodCsrBip()));
						listColBodyRow.add(new ColBodyRow(soggetto.getCodiceFiscale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getPartitaIva()));
						listColBodyRow.add(new ColBodyRow(soggetto.getDenominazioneBreve()));
						listColBodyRow.add(new ColBodyRow(soggetto.getNaturaGiuridica()));
						listColBodyRow.add(new ColBodyRow(soggetto.getDenominazioneAaep()));
						listColBodyRow.add(new ColBodyRow(soggetto.getDenomSoggettoGiuridico()));
						listColBodyRow.add(new ColBodyRow(soggetto.getTipoEnte()));
						listColBodyRow.add(new ColBodyRow(soggetto.getRappresentanteLegale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getIndirizzoSedeLegale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getTelefonoSedeLegale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getFaxSedeLegale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getEmailSedeLegale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getPecSedeLegale()));
						listColBodyRow.add(new ColBodyRow(soggetto.getIndirizzoWeb()));
						listColBodyRow.add(new ColBodyRow(soggetto.getNumeroVerde()));
						if (filtro.getFlagIncludiNonAttive() == null || filtro.getFlagIncludiNonAttive().equals("N")) {
							listColBodyRow.add(new ColBodyRow("S"));
						} else {
							if (soggetto.getAttivoTpl()) {
								listColBodyRow.add(new ColBodyRow("S"));
							} else {
								listColBodyRow.add(new ColBodyRow("N"));
							}
						}
						listColBodyRow
								.add(new ColBodyRow(soggetto.getDataInizioAttivita(), EXCEL_PATTERN.DATE_PATTERN));
						if (soggetto.getCessato()) {
							listColBodyRow.add(new ColBodyRow("S"));
						} else {
							listColBodyRow.add(new ColBodyRow("N"));
						}

						listColBodyRow.add(new ColBodyRow(soggetto.getDataCessazione(), EXCEL_PATTERN.DATE_PATTERN));

						listColBodyRow.add(new ColBodyRow(soggetto.getNote()));
						listColBodyRow
								.add(new ColBodyRow(soggetto.getDataAggiornamento(), EXCEL_PATTERN.DATEHOURS_PATTERN));

						bodyRow.setListColBodyRow(listColBodyRow);

						listBodyRow.add(bodyRow);
					}

					excel.setListBodyRow(listBodyRow);
					listaExcel.add(excel);
				}
			}

			return ExcelUtil.generaExcel(listaExcel, fileName);

		} catch (Exception e) {
			// Rilancio una RebusException
			throw new RebusException(e);
		} finally {
		}

	}

	private static List<ColHeaderRow> getHeaderXlsExportaSoggetto(boolean storico) {

		List<ColHeaderRow> listColHeaderRow = new ArrayList<ExcelDto.ColHeaderRow>();

		listColHeaderRow.add(new ColHeaderRow("Tipo soggetto giuridico", 20));
		listColHeaderRow.add(new ColHeaderRow("Codice regionale", 20));
		listColHeaderRow.add(new ColHeaderRow("Codice nazionale", 20));
		listColHeaderRow.add(new ColHeaderRow("Codice CSR-BIP", 20));

		listColHeaderRow.add(new ColHeaderRow("Codice Fiscale", 30));
		listColHeaderRow.add(new ColHeaderRow("Partita IVA", 30));
		listColHeaderRow.add(new ColHeaderRow("Denominazione breve (Alias)", 40));
		listColHeaderRow.add(new ColHeaderRow("Natura giuridica Infocamere", 40));
		listColHeaderRow.add(new ColHeaderRow("Denominazione Infocamere", 40));
		listColHeaderRow.add(new ColHeaderRow("Denominazione Osservatorio Nazionale", 40));
		listColHeaderRow.add(new ColHeaderRow("Tipo ente", 40));
		listColHeaderRow.add(new ColHeaderRow("Rappresentante legale", 40));
		listColHeaderRow.add(new ColHeaderRow("Indirizzo sede legale", 60));
		listColHeaderRow.add(new ColHeaderRow("Telefono", 20));
		listColHeaderRow.add(new ColHeaderRow("Fax", 20));
		listColHeaderRow.add(new ColHeaderRow("Email", 20));
		listColHeaderRow.add(new ColHeaderRow("PEC", 40));
		listColHeaderRow.add(new ColHeaderRow("Indirizzo web", 40));
		listColHeaderRow.add(new ColHeaderRow("Numero verde", 20));
		listColHeaderRow.add(new ColHeaderRow("Soggetto attivo TPL", 20));
		listColHeaderRow.add(new ColHeaderRow("Data inizio attivit\u00E0", 20));
		listColHeaderRow.add(new ColHeaderRow("Soggetto cessato", 20));
		listColHeaderRow.add(new ColHeaderRow("Data cessazione (Infocamere)", 20));
		listColHeaderRow.add(new ColHeaderRow("Note", 40));
		listColHeaderRow.add(new ColHeaderRow("Data ultima modifica", 20));
		return listColHeaderRow;
	}
}
