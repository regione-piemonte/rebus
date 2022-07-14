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
import it.csi.rebus.anagraficasrv.integration.dto.VExportRicercaContrattiDTO;
import it.csi.rebus.anagraficasrv.util.DateUtils;
import it.csi.rebus.anagraficasrv.vo.FiltroContrattoVO;

@Component
public class ExcelReportContrattiManager {

	@Autowired
	private ExcelDAO excel;

	public byte[] exportRicercaAutobus(FiltroContrattoVO filtro) {
		try {

			/*
			 * Recupero gli autobus da exportare
			 */
			List<VExportRicercaContrattiDTO> listaContratti;
			try {
				listaContratti = excel.getExcelContrattoReport(filtro);
			} catch (Exception e) {
				throw new ErroreGestitoException("Errore nell'estrazione dei contratti. Contattare l'assistenza");
			}

			// lista che consente di gestire i vari fogli
			List<ExcelDto> listaExcel = new ArrayList<ExcelDto>();

			// lista con i nomi dei fogli
			List<String> nameSheet = new ArrayList<String>();
			nameSheet.add("contratti");

			// nome file excel
			StringBuilder fileName = new StringBuilder();
			fileName.append("contratti_" + DateUtils.getTimeStamp());

			for (String sheet : nameSheet) {

				ExcelDto excel = new ExcelDto();

				// setto nome file
				excel.setFileName(fileName.toString());
				// setto nome foglio

				excel.setSheetName(sheet);
				if (sheet.equalsIgnoreCase("contratti")) {
					generaExcelContratti(excel, listaContratti);
				}

				listaExcel.add(excel);
			}

			return ExcelUtil.generaExcel(listaExcel, fileName);

		} catch (

		Exception e) {
			// Rilancio una RebusException
			throw new RebusException(e);
		} finally {
		}

	}

	// gestione colonne foglio Contratti
	private static List<ColHeaderRow> getHeaderXlsExportaAutobus() {

		List<ColHeaderRow> listColHeaderRow = new ArrayList<ExcelDto.ColHeaderRow>();

		listColHeaderRow.add(new ColHeaderRow("Codice regionale", 20));
		listColHeaderRow.add(new ColHeaderRow("Codice nazionale", 20));
		listColHeaderRow.add(new ColHeaderRow("Numero Repertorio", 20));
		listColHeaderRow.add(new ColHeaderRow("Codice nazionale \n Ente committente", 20));
		listColHeaderRow.add(new ColHeaderRow("Ente committente (Infocamere)", 40));
		listColHeaderRow.add(new ColHeaderRow("Codice nazionale \n Esecutore titolare", 20));
		listColHeaderRow.add(new ColHeaderRow("Esecutore titolare (Infocamere)", 40));
		listColHeaderRow.add(new ColHeaderRow("Descrizione contratto", 50));
		listColHeaderRow.add(new ColHeaderRow("Bacino", 40));
		listColHeaderRow.add(new ColHeaderRow("Tipologia / Ambito di servizio", 50));
		listColHeaderRow.add(new ColHeaderRow("CIG", 25));
		listColHeaderRow.add(new ColHeaderRow("Tipo Esecutore titolare", 40));
		listColHeaderRow.add(new ColHeaderRow("Tipo raggruppamento", 40));
		listColHeaderRow.add(new ColHeaderRow("Codice nazionale \n Azienda capofila", 20));
		listColHeaderRow.add(new ColHeaderRow("Azienda capofila (Infocamere)", 40));
		listColHeaderRow.add(new ColHeaderRow("Data stipula", 20));
		listColHeaderRow.add(new ColHeaderRow("Data inizio validita", 20));
		listColHeaderRow.add(new ColHeaderRow("Data scadenza", 20));
		listColHeaderRow.add(new ColHeaderRow("Data ultima proroga", 20));
		listColHeaderRow.add(new ColHeaderRow("Atto ultima proroga", 50));
		listColHeaderRow.add(new ColHeaderRow("Tipo affidamento", 40));
		listColHeaderRow.add(new ColHeaderRow("Modalita affidamento", 40));
		listColHeaderRow.add(new ColHeaderRow("Accordo di programma", 50));
		listColHeaderRow.add(new ColHeaderRow("Gross Cost", 15));
		// listColHeaderRow.add(new ColHeaderRow("NOTE", 40));
		listColHeaderRow.add(new ColHeaderRow("Data ultima modifica", 20));

		return listColHeaderRow;
	}

	// genera foglio Contratti
	private void generaExcelContratti(ExcelDto excel, List<VExportRicercaContrattiDTO> listaContratti) {

		excel.setListColHeaderRow(getHeaderXlsExportaAutobus());
		List<BodyRow> listBodyRow = new ArrayList<ExcelDto.BodyRow>();

		for (VExportRicercaContrattiDTO contratto : listaContratti) {
			BodyRow bodyRow = new BodyRow();

			List<ColBodyRow> listColBodyRow = new ArrayList<ExcelDto.ColBodyRow>();

			listColBodyRow.add(new ColBodyRow(contratto.getCodregionale()));
			listColBodyRow.add(new ColBodyRow(contratto.getCodIdNazionale()));
			listColBodyRow.add(new ColBodyRow(contratto.getNumRepertorio()));
			listColBodyRow.add(new ColBodyRow(contratto.getCodOssSogGiuridCommittente()));
			listColBodyRow.add(new ColBodyRow(contratto.getSogGiuridCommittenteAaep()));
			listColBodyRow.add(new ColBodyRow(contratto.getCodOssSogGiuridEsecutore()));
			listColBodyRow.add(new ColBodyRow(contratto.getSogGiuridEsecutoreAaep()));
			listColBodyRow.add(new ColBodyRow(contratto.getDescContratto()));
			listColBodyRow.add(new ColBodyRow(contratto.getBacino()));
			listColBodyRow.add(new ColBodyRow(contratto.getTipologiaAmbitoServizio() != null
					? contratto.getTipologiaAmbitoServizio().replace(",", "\n")
					: null));
			listColBodyRow.add(new ColBodyRow(contratto.getCig()));
			listColBodyRow.add(new ColBodyRow(contratto.getTipoSogGiuridEsec()));
			listColBodyRow.add(new ColBodyRow(contratto.getTipoRaggrSogGiuridEsec()));
			listColBodyRow.add(new ColBodyRow(contratto.getCodOssAziendaMandataria()));
			listColBodyRow.add(new ColBodyRow(contratto.getAziendaMandatariaAaep()));
			listColBodyRow.add(new ColBodyRow(contratto.getDataStipula(), EXCEL_PATTERN.DATE_PATTERN));
			listColBodyRow.add(new ColBodyRow(contratto.getDataInizioValidita(), EXCEL_PATTERN.DATE_PATTERN));
			listColBodyRow.add(new ColBodyRow(contratto.getDataFineValidita(), EXCEL_PATTERN.DATE_PATTERN));
			listColBodyRow.add(new ColBodyRow(contratto.getDataFineProroga(), EXCEL_PATTERN.DATE_PATTERN));
			listColBodyRow.add(new ColBodyRow(contratto.getAttoProroga()));
			listColBodyRow.add(new ColBodyRow(contratto.getTipoAffidamento()));
			listColBodyRow.add(new ColBodyRow(contratto.getModalitaAffidamento()));
			listColBodyRow.add(new ColBodyRow(contratto.getAccordoProgramma()));
			listColBodyRow.add(new ColBodyRow(contratto.getGrossCost() ? 'S' : 'N'));
			listColBodyRow.add(new ColBodyRow(contratto.getDataAggiornamento(), EXCEL_PATTERN.DATEHOURS_PATTERN));

			bodyRow.setListColBodyRow(listColBodyRow);

			listBodyRow.add(bodyRow);
		}
		excel.setListBodyRow(listBodyRow);
	}

}