/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.rebus.rebuscrus.common.exception.ErroreGestitoException;
import it.csi.rebus.rebuscrus.common.exception.RebusException;
import it.csi.rebus.rebuscrus.excel.ExcelDto;
import it.csi.rebus.rebuscrus.excel.ExcelDto.BodyRow;
import it.csi.rebus.rebuscrus.excel.ExcelDto.ColBodyRow;
import it.csi.rebus.rebuscrus.excel.ExcelDto.ColHeaderRow;
import it.csi.rebus.rebuscrus.excel.ExcelUtil;
import it.csi.rebus.rebuscrus.excel.ExcelUtil.EXCEL_PATTERN;
import it.csi.rebus.rebuscrus.integration.dao.custom.ContribuzioneDAO;
import it.csi.rebus.rebuscrus.integration.dao.custom.ExcelDAO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaStoriaAutobusDTO;
import it.csi.rebus.rebuscrus.integration.dto.VExportRicercaContribuzioneDTO;
import it.csi.rebus.rebuscrus.util.Constants;
import it.csi.rebus.rebuscrus.util.DateUtils;
import it.csi.rebus.rebuscrus.util.UtilRebus;
import it.csi.rebus.rebuscrus.vo.FiltroAutobusVO;
import it.csi.rebus.rebuscrus.vo.FiltroContribuzioneVO;

@Component
public class ExcelReportManager {

	private static Logger LOGGER = Logger
			.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + ".ExcelReportManager");

	@Autowired
	private ExcelDAO excel;

	@Autowired
	private ContribuzioneDAO contribuzioneDAO;

	public byte[] exportRicercaAutobus(FiltroAutobusVO filtro) {
		try {
			LOGGER.info("ExcelReportManager::exportRicercaAutobus START");

			/*
			 * Recupero gli autobus da exportare
			 */
			List<VExportRicercaAutobusDTO> listaAutobus = excel.getExcelReport(filtro);

			ExcelDto excel = new ExcelDto();
			StringBuilder fileName = new StringBuilder();
			fileName.append("autobus_" + DateUtils.getTimeStamp());
			excel.setFileName(fileName.toString());
			excel.setSheetName("autobus");
			excel.setListColHeaderRow(getHeaderXlsExportaAutobus(false));
			List<BodyRow> listBodyRow = new ArrayList<ExcelDto.BodyRow>();

			for (VExportRicercaAutobusDTO autobus : listaAutobus) {
				BodyRow bodyRow = new BodyRow();

				List<ColBodyRow> listColBodyRow = new ArrayList<ExcelDto.ColBodyRow>();
				if (autobus.getPresenzaAllegati())
					listColBodyRow.add(new ColBodyRow('S')); // Presenza allegati
				else
					listColBodyRow.add(new ColBodyRow('N'));
				listColBodyRow.add(new ColBodyRow(
						autobus.getProcedimenti() != null ? autobus.getProcedimenti().replace(",", "\n") : null));// procedimenti
				listColBodyRow.add(new ColBodyRow(autobus.getDenominazione())); // Denominazione
				listColBodyRow.add(new ColBodyRow(autobus.getnTelaio())); // Numero telaio
				listColBodyRow.add(new ColBodyRow(autobus.getnTarga())); // Targa
				listColBodyRow.add(new ColBodyRow(autobus.getDataPrimaImmatricolazione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
																														// prima
																														// immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getDataUltimaImmatricolazione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
																															// ultima
																															// immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getAlimentazione())); // Alimentazione
				listColBodyRow.add(new ColBodyRow(autobus.getnPostiSedere(), EXCEL_PATTERN.INT_PATTERN)); // Numero
																											// posti a
																											// sedere
				listColBodyRow.add(new ColBodyRow(autobus.getnPostiInPiedi(), EXCEL_PATTERN.INT_PATTERN)); // Numero
																											// posti in
																											// piedi
				listColBodyRow.add(new ColBodyRow(autobus.getnPostiRiservati(), EXCEL_PATTERN.INT_PATTERN)); // Numero
																												// posti
																												// riservati
				listColBodyRow.add(new ColBodyRow(autobus.getOmologazioneDirettivaEuropea())); // Omologazione Direttiva
																								// europea
				listColBodyRow.add(new ColBodyRow(autobus.getOmologazioneClasse())); // Omologazione Classe Euro
				listColBodyRow.add(new ColBodyRow(autobus.getFlgFiltroFap())); // Filtro FAP
				listColBodyRow.add(new ColBodyRow(autobus.getFacilitazioneDisabili())); // Facilitazioni disabili
				listColBodyRow.add(new ColBodyRow(autobus.getImpiantiAudio())); // Impianti Audio
				listColBodyRow.add(new ColBodyRow(autobus.getImpiantiVisivi())); // Impianti Visivi
				listColBodyRow.add(new ColBodyRow(autobus.getFlgRilevatoreBip())); // Flag rilevatore Bip
				listColBodyRow.add(new ColBodyRow(autobus.getPrezzoTotAcquisto(), EXCEL_PATTERN.EURO_PATTERN)); // Prezzo
																												// totale
																												// acquisto
				listColBodyRow.add(new ColBodyRow(autobus.getContributoPubblicoAcquisto(), EXCEL_PATTERN.EURO_PATTERN)); // Contributo
																															// pubblico
																															// acquisto
				listColBodyRow.add(new ColBodyRow(autobus.getFlgVeicoloAssicurato())); // Veicolo assicurato
				listColBodyRow.add(new ColBodyRow(autobus.getDataUltimaRevisione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
																													// ultima
																													// revisione
				listColBodyRow.add(new ColBodyRow(autobus.getNote())); // Note
				listColBodyRow.add(new ColBodyRow(autobus.getPrimoTelaio())); // Primo telaio
				listColBodyRow.add(new ColBodyRow(autobus.getnMatricolaAziendale())); // Matricola aziendale
				listColBodyRow.add(new ColBodyRow(autobus.getTipoImmatricolazione())); // Tipo immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getEnteAutorizzPrimaImm())); // Ente prima immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getClasseVeicolo())); // Classe veicolo
				listColBodyRow.add(new ColBodyRow(autobus.getMarca())); // Marca
				listColBodyRow.add(new ColBodyRow(autobus.getModello())); // Modello
				listColBodyRow.add(new ColBodyRow(autobus.getFlgDuePiani())); // Due piani
				listColBodyRow.add(new ColBodyRow(autobus.getFlgSnodato())); // Snodato
				listColBodyRow.add(new ColBodyRow(autobus.getCaratteristicheParticolari())); // Caratteristiche
																								// particolati
				listColBodyRow.add(new ColBodyRow(autobus.getAltraAlimentazione())); // Altra alimentazione
				listColBodyRow.add(new ColBodyRow(autobus.getCategoriaVeicolo()));
				listColBodyRow.add(new ColBodyRow(autobus.getTipoAllestimento())); // Tipo allestimento
				listColBodyRow.add(new ColBodyRow(autobus.getLunghezza(), EXCEL_PATTERN.NUMBER_3_DIGIT_PATTERN)); // Lunghezza
				listColBodyRow.add(new ColBodyRow(autobus.getNumeroPorte(), EXCEL_PATTERN.INT_PATTERN)); // Numero porte
				listColBodyRow.add(new ColBodyRow(autobus.getPostiCarrozzina(), EXCEL_PATTERN.INT_PATTERN)); // Posti
																												// carrozzina
				listColBodyRow.add(new ColBodyRow(autobus.getFlgImpiantoCondizionamento())); // Impianto condizionamento
				listColBodyRow.add(new ColBodyRow(autobus.getFlgCabinaGuidaIsolata())); // Cabina guida isolata
				listColBodyRow.add(new ColBodyRow(autobus.getDispositiviPrevenz())); // Dispositivi di prevenzione
				listColBodyRow.add(new ColBodyRow(autobus.getAltriDispositiviPrevenzInc())); // Altri dispositivi di
																								// prevenzione incidenti
				listColBodyRow.add(new ColBodyRow(autobus.getFlgOtx())); // Flg Otx
				listColBodyRow.add(new ColBodyRow(autobus.getFlgAvm())); // Flg Avm
				listColBodyRow.add(new ColBodyRow(autobus.getFlgContapasseggeri())); // Contapasseggeri
				listColBodyRow.add(new ColBodyRow(autobus.getProprietaLeasing())); // Proprieta'/Leasing
				listColBodyRow.add(new ColBodyRow(autobus.getFlgConteggiatoMiv())); // Conteggiato Miv
				listColBodyRow.add(new ColBodyRow(autobus.getDeposito())); // Deposito
				listColBodyRow.add(new ColBodyRow(autobus.getTipologiaDimens())); // Tipologia dimensioni
				listColBodyRow.add(new ColBodyRow(autobus.getStatoTpl())); // Alienato
				listColBodyRow.add(new ColBodyRow(autobus.getDataAlienazione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
				// Alienazione

				listColBodyRow.add(new ColBodyRow(autobus.getContribuito())); // Contibuito
				listColBodyRow.add(new ColBodyRow(autobus.getScadVincoliNoAlien(), EXCEL_PATTERN.DATE_PATTERN)); // Scandenza
																													// vincoli
																													// no
																													// alienzione
				listColBodyRow.add(new ColBodyRow(autobus.getNotaRiservataAzienda())); // Nota riservata azienda()
				listColBodyRow.add(new ColBodyRow(autobus.getNotaRiservataAmp())); // Nota riservata Amp
				listColBodyRow.add(new ColBodyRow(autobus.getNotaRiservataRp())); // Nota riservata Rp
				listColBodyRow.add(new ColBodyRow(autobus.getFlgRichiestaContr())); // Richiesta controllo
				listColBodyRow.add(new ColBodyRow(autobus.getAnnoSostProg(), EXCEL_PATTERN.ANNO_PATTERN)); // Anno
																											// sostituzione
																											// programmata
				listColBodyRow.add(new ColBodyRow(autobus.getFlgVerificatoAzienda())); // Verificato Azienda
				listColBodyRow.add(new ColBodyRow(autobus.getFlgVerificatoAmp())); // Verificato AMP
				listColBodyRow.add(new ColBodyRow(autobus.getMotivazione())); // motivazione
				listColBodyRow.add(new ColBodyRow(autobus.getDataAggiornamento(), EXCEL_PATTERN.EXTEND_DATE_PATTERN));

				bodyRow.setListColBodyRow(listColBodyRow);

				listBodyRow.add(bodyRow);
			}

			excel.setListBodyRow(listBodyRow);

			return ExcelUtil.generaExcel(excel);

		} catch (Exception e) {
			LOGGER.error("ExcelReportManager::exportRicercaAutobus excpetion: " + e.getMessage() + " - "
					+ UtilRebus.getStackTrace(e));
			// Rilancio una RebusException
			throw new RebusException(e);
		} finally {
			LOGGER.info("ExcelReportManager::exportRicercaAutobus END");
		}

	}

	@Transactional(timeout = 240)
	public byte[] exportRicercaStoricoAutobus(FiltroAutobusVO filtro) {
		try {
			LOGGER.info("ExcelReportManager::exportRicercaAutobus START");

			/*
			 * Recupero gli autobus da exportare
			 */
			List<VExportRicercaStoriaAutobusDTO> listaAutobus;
			try {
				listaAutobus = excel.getExcelReportStorico(filtro);
			} catch (Exception e) {
				// throw new ErroreGestitoException("Errore nell'estrazione degli autobus.
				// Contattare l'assistenza");
				throw new ErroreGestitoException(e.getMessage());
			}

			ExcelDto excel = new ExcelDto();
			StringBuilder fileName = new StringBuilder();
			fileName.append("autobus_" + DateUtils.getTimeStamp());
			List<String> intestazioneList = new ArrayList<>();

			StringBuilder instestazione = new StringBuilder("Situazione Parco Autobus alla data: ");
			instestazione.append(DateUtils.dateToString(filtro.getSituazioneAl()));
			if (Constants.FLAG_SI.equals(filtro.getFlagIncludiVariazioniPre()))
				instestazione.append(" (incluso storico variazioni precedenti)");

			intestazioneList.add(instestazione.toString());

			excel.setIntestazioneRow(intestazioneList);
			excel.setFileName(fileName.toString());
			excel.setSheetName("autobus");
			excel.setListColHeaderRow(getHeaderXlsExportaAutobus(true));
			List<BodyRow> listBodyRow = new ArrayList<ExcelDto.BodyRow>();

			for (VExportRicercaStoriaAutobusDTO autobus : listaAutobus) {
				BodyRow bodyRow = new BodyRow();

				List<ColBodyRow> listColBodyRow = new ArrayList<ExcelDto.ColBodyRow>();

				listColBodyRow.add(new ColBodyRow(autobus.getDataAggiornamento(), EXCEL_PATTERN.EXTEND_DATE_PATTERN)); // Data
																														// prima
																														// immatricolazione
				listColBodyRow
						.add(new ColBodyRow(autobus.getDataAggiornamentoStoria(), EXCEL_PATTERN.EXTEND_DATE_PATTERN)); // Data
																														// ultima
																														// immatricolazione
//manca campo
				listColBodyRow.add(new ColBodyRow(autobus.getDenominazione())); // Denominazione
				listColBodyRow.add(new ColBodyRow(autobus.getnTelaio())); // Numero telaio
				listColBodyRow.add(new ColBodyRow(autobus.getnTarga())); // Targa
				listColBodyRow.add(new ColBodyRow(autobus.getDataPrimaImmatricolazione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
																														// prima
																														// immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getDataUltimaImmatricolazione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
																															// ultima
																															// immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getAlimentazione())); // Alimentazione
				listColBodyRow.add(new ColBodyRow(autobus.getnPostiSedere(), EXCEL_PATTERN.INT_PATTERN)); // Numero
																											// posti a
																											// sedere
				listColBodyRow.add(new ColBodyRow(autobus.getnPostiInPiedi(), EXCEL_PATTERN.INT_PATTERN)); // Numero
																											// posti in
																											// piedi
				listColBodyRow.add(new ColBodyRow(autobus.getnPostiRiservati(), EXCEL_PATTERN.INT_PATTERN)); // Numero
																												// posti
																												// riservati
				listColBodyRow.add(new ColBodyRow(autobus.getOmologazioneDirettivaEuropea())); // Omologazione Direttiva
																								// europea
				listColBodyRow.add(new ColBodyRow(autobus.getOmologazioneClasse())); // Omologazione Classe Euro
				listColBodyRow.add(new ColBodyRow(autobus.getFlgFiltroFap())); // Filtro FAP
				listColBodyRow.add(new ColBodyRow(autobus.getFacilitazioneDisabili())); // Facilitazioni disabili
				listColBodyRow.add(new ColBodyRow(autobus.getImpiantiAudio())); // Impianti Audio
				listColBodyRow.add(new ColBodyRow(autobus.getImpiantiVisivi())); // Impianti Visivi
				listColBodyRow.add(new ColBodyRow(autobus.getFlgRilevatoreBip())); // Flag rilevatore Bip
				listColBodyRow.add(new ColBodyRow(autobus.getPrezzoTotAcquisto(), EXCEL_PATTERN.EURO_PATTERN)); // Prezzo
																												// totale
																												// acquisto
				listColBodyRow.add(new ColBodyRow(autobus.getContributoPubblicoAcquisto(), EXCEL_PATTERN.EURO_PATTERN)); // Contributo
																															// pubblico
																															// acquisto
				listColBodyRow.add(new ColBodyRow(autobus.getFlgVeicoloAssicurato())); // Veicolo assicurato
				listColBodyRow.add(new ColBodyRow(autobus.getDataUltimaRevisione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
																													// ultima
																													// revisione
				listColBodyRow.add(new ColBodyRow(autobus.getNote())); // Note
				listColBodyRow.add(new ColBodyRow(autobus.getPrimoTelaio())); // Primo telaio
				listColBodyRow.add(new ColBodyRow(autobus.getnMatricolaAziendale())); // Matricola aziendale
				listColBodyRow.add(new ColBodyRow(autobus.getTipoImmatricolazione())); // Tipo immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getEnteAutorizzPrimaImm())); // Ente prima immatricolazione
				listColBodyRow.add(new ColBodyRow(autobus.getClasseVeicolo())); // Classe veicolo
				listColBodyRow.add(new ColBodyRow(autobus.getMarca())); // Marca
				listColBodyRow.add(new ColBodyRow(autobus.getModello())); // Modello
				listColBodyRow.add(new ColBodyRow(autobus.getFlgDuePiani())); // Due piani
				listColBodyRow.add(new ColBodyRow(autobus.getFlgSnodato())); // Snodato
				listColBodyRow.add(new ColBodyRow(autobus.getCaratteristicheParticolari())); // Caratteristiche
																								// particolati
				listColBodyRow.add(new ColBodyRow(autobus.getAltraAlimentazione())); // Altra alimentazione
				listColBodyRow.add(new ColBodyRow(autobus.getCategoriaVeicolo()));
				listColBodyRow.add(new ColBodyRow(autobus.getTipoAllestimento())); // Tipo allestimento
				listColBodyRow.add(new ColBodyRow(autobus.getLunghezza(), EXCEL_PATTERN.NUMBER_3_DIGIT_PATTERN)); // Lunghezza
				listColBodyRow.add(new ColBodyRow(autobus.getNumeroPorte(), EXCEL_PATTERN.INT_PATTERN)); // Numero porte
				listColBodyRow.add(new ColBodyRow(autobus.getPostiCarrozzina(), EXCEL_PATTERN.INT_PATTERN)); // Posti
																												// carrozzina
				listColBodyRow.add(new ColBodyRow(autobus.getFlgImpiantoCondizionamento())); // Impianto condizionamento
				listColBodyRow.add(new ColBodyRow(autobus.getFlgCabinaGuidaIsolata())); // Cabina guida isolata
				listColBodyRow.add(new ColBodyRow(autobus.getDispositiviPrevenz())); // Dispositivi di prevenzione
				listColBodyRow.add(new ColBodyRow(autobus.getAltriDispositiviPrevenzInc())); // Altri dispositivi di
																								// prevenzione incidenti
				listColBodyRow.add(new ColBodyRow(autobus.getFlgOtx())); // Flg Otx
				listColBodyRow.add(new ColBodyRow(autobus.getFlgAvm())); // Flg Avm
				listColBodyRow.add(new ColBodyRow(autobus.getFlgContapasseggeri())); // Contapasseggeri
				listColBodyRow.add(new ColBodyRow(autobus.getProprietaLeasing())); // Proprieta'/Leasing
				listColBodyRow.add(new ColBodyRow(autobus.getFlgConteggiatoMiv())); // Conteggiato Miv
				listColBodyRow.add(new ColBodyRow(autobus.getDeposito())); // Deposito
				listColBodyRow.add(new ColBodyRow(autobus.getDataAlienazione(), EXCEL_PATTERN.DATE_PATTERN)); // Data
																												// Alienazione
				listColBodyRow.add(new ColBodyRow(autobus.getTipologiaDimens())); // Tipologia dimensioni
				listColBodyRow.add(new ColBodyRow(autobus.getContribuito())); // Alienato
				listColBodyRow.add(new ColBodyRow(autobus.getContribuito())); // Contibuito
				listColBodyRow.add(new ColBodyRow(autobus.getScadVincoliNoAlien(), EXCEL_PATTERN.DATE_PATTERN)); // Scandenza
																													// vincoli
																													// no
																													// alienzione
				listColBodyRow.add(new ColBodyRow(autobus.getNotaRiservataAzienda())); // Nota riservata azienda()
				listColBodyRow.add(new ColBodyRow(autobus.getNotaRiservataAmp())); // Nota riservata Amp
				listColBodyRow.add(new ColBodyRow(autobus.getNotaRiservataRp())); // Nota riservata Rp
				listColBodyRow.add(new ColBodyRow(autobus.getFlgRichiestaContr())); // Richiesta controllo
				listColBodyRow.add(new ColBodyRow(autobus.getAnnoSostProg(), EXCEL_PATTERN.ANNO_PATTERN)); // Anno
																											// sostituzione
																											// programmata
				listColBodyRow.add(new ColBodyRow(autobus.getFlgVerificatoAzienda())); // Verificato Azienda
				listColBodyRow.add(new ColBodyRow(autobus.getFlgVerificatoAmp())); // Verificato AMP
				listColBodyRow.add(new ColBodyRow(autobus.getMotivazione())); // motivazione

				bodyRow.setListColBodyRow(listColBodyRow);

				listBodyRow.add(bodyRow);
			}

			excel.setListBodyRow(listBodyRow);

			return ExcelUtil.generaExcel(excel);

		} catch (Exception e) {
			LOGGER.error("ExcelReportManager::exportRicercaAutobus excpetion: " + e.getMessage() + " - "
					+ UtilRebus.getStackTrace(e));
			// Rilancio una RebusException
			throw new RebusException(e);
		} finally {
			LOGGER.info("ExcelReportManager::exportRicercaAutobus END");
		}

	}

	private static List<ColHeaderRow> getHeaderXlsExportaAutobus(boolean storico) {

		List<ColHeaderRow> listColHeaderRow = new ArrayList<ExcelDto.ColHeaderRow>();
		if (storico) {
			listColHeaderRow.add(new ColHeaderRow("Data Aggiornamento", 20));
			listColHeaderRow.add(new ColHeaderRow("Valido fino al", 20));
		} else {
			listColHeaderRow.add(new ColHeaderRow("Presenza\nAllegati", 12));
			listColHeaderRow.add(new ColHeaderRow("Richieste", 15));
		}

		listColHeaderRow.add(new ColHeaderRow("Azienda", 40));
		listColHeaderRow.add(new ColHeaderRow("Numero telaio", 20));
		listColHeaderRow.add(new ColHeaderRow("Targa", 12));
		listColHeaderRow.add(new ColHeaderRow("Data prima\nimmatricolazione", 20));
		listColHeaderRow.add(new ColHeaderRow("Data ultima\nimmatricolazione", 20));
		listColHeaderRow.add(new ColHeaderRow("Alimentazione", 15));
		listColHeaderRow.add(new ColHeaderRow("N. posti\na sedere", 10));
		listColHeaderRow.add(new ColHeaderRow("N. posti\nin piedi", 10));
		listColHeaderRow.add(new ColHeaderRow("N. posti\nriservati", 10));
		listColHeaderRow.add(new ColHeaderRow("Omologazione Direttiva europea", 45));
		listColHeaderRow.add(new ColHeaderRow("Classe \nambientale", 12));
		listColHeaderRow.add(new ColHeaderRow("Filtro FAP", 10));
		listColHeaderRow.add(new ColHeaderRow("Facilitazioni\ndisabili", 15));
		listColHeaderRow.add(new ColHeaderRow("Impianti audio", 20));
		listColHeaderRow.add(new ColHeaderRow("Impianti visivi", 20));
		listColHeaderRow.add(new ColHeaderRow("Rilevatore BIP", 15));
		listColHeaderRow.add(new ColHeaderRow("Prezzo totale\nacquisto", 15));
		listColHeaderRow.add(new ColHeaderRow("Contributo\npubblico acquisto", 20));
		listColHeaderRow.add(new ColHeaderRow("Veicolo assicurato", 15));
		listColHeaderRow.add(new ColHeaderRow("Data ultima revisione", 20));
		listColHeaderRow.add(new ColHeaderRow("Note", 60));
		listColHeaderRow.add(new ColHeaderRow("Primo telaio", 20));
		listColHeaderRow.add(new ColHeaderRow("Numero matricola aziendale", 30));
		listColHeaderRow.add(new ColHeaderRow("Tipo immatricolazione", 20));
		listColHeaderRow.add(new ColHeaderRow("Ente autorizzazione\nultima immatricolazione", 21));
		listColHeaderRow.add(new ColHeaderRow("Classe veicolo", 30));
		listColHeaderRow.add(new ColHeaderRow("Marca", 30));
		listColHeaderRow.add(new ColHeaderRow("Modello", 30));
		listColHeaderRow.add(new ColHeaderRow("Due piani", 15));
		listColHeaderRow.add(new ColHeaderRow("Snodato", 15));
		listColHeaderRow.add(new ColHeaderRow("Caratteristiche particolari", 30));
		listColHeaderRow.add(new ColHeaderRow("Altra alimentazione", 30));
		listColHeaderRow.add(new ColHeaderRow("Categoria veicolo", 30));
		listColHeaderRow.add(new ColHeaderRow("Tipo allestimento", 25));
		listColHeaderRow.add(new ColHeaderRow("Lunghezza (mt.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Numero porte", 15));
		listColHeaderRow.add(new ColHeaderRow("Posti carrozzina", 15));
		listColHeaderRow.add(new ColHeaderRow("Impianto di\ncondizionamento", 15));
		listColHeaderRow.add(new ColHeaderRow("Cabina guida\nisolata", 15));
		listColHeaderRow.add(new ColHeaderRow("Dispositivi di prevenzione\nincidenti", 25));
		listColHeaderRow.add(new ColHeaderRow("Altri dispositivi di\nprevenzione incidenti", 25));
		listColHeaderRow.add(new ColHeaderRow("Otx", 15));
		listColHeaderRow.add(new ColHeaderRow("Avm", 15));
		listColHeaderRow.add(new ColHeaderRow("Contapasseggeri", 15));
		listColHeaderRow.add(new ColHeaderRow("Proprieta'/Leasing", 15));
		listColHeaderRow.add(new ColHeaderRow("Conteggiato Miv", 20));
		listColHeaderRow.add(new ColHeaderRow("Deposito", 40));
		listColHeaderRow.add(new ColHeaderRow("Tipologia dimensionale", 30));
		listColHeaderRow.add(new ColHeaderRow("Stato TPL", 30));
		listColHeaderRow.add(new ColHeaderRow("Data alienazione", 20));
		listColHeaderRow.add(new ColHeaderRow("Contribuito", 30));
		listColHeaderRow.add(new ColHeaderRow("Scadenza vincoli non alienabilita'", 30));
		listColHeaderRow.add(new ColHeaderRow("Nota riservata azienda", 30));
		listColHeaderRow.add(new ColHeaderRow("Nota riservata amp", 30));
		listColHeaderRow.add(new ColHeaderRow("Nota riservata rp", 30));
		listColHeaderRow.add(new ColHeaderRow("Autobus gia' acquistato\nda contribuire", 30));
		listColHeaderRow.add(new ColHeaderRow("Anno sostituzione programmata", 30));
		listColHeaderRow.add(new ColHeaderRow("Verificato\nAzienda", 12));
		listColHeaderRow.add(new ColHeaderRow("Verificato\nAMP", 12));
		listColHeaderRow.add(new ColHeaderRow("Motivazione", 30));
		if (!storico) {
			listColHeaderRow.add(new ColHeaderRow("Data Aggiornamento", 20));
		}

		return listColHeaderRow;
	}

	// excel contribuzione
	public byte[] excelRicercaContribuzione(FiltroContribuzioneVO filtro) {
		try {
			LOGGER.info("ExcelReportManager::excelRicercaContribuzione START");

			List<VExportRicercaContribuzioneDTO> lista = contribuzioneDAO.getElencoContribuzione(filtro);

			ExcelDto excel = new ExcelDto();
			StringBuilder fileName = new StringBuilder();
			fileName.append("contribuzione_" + DateUtils.getTimeStamp());
			excel.setFileName(fileName.toString());
			excel.setSheetName("contribuzione");// nome foglio
			excel.setListColHeaderRow(getHeaderXlsExportaContribuzione(false)); // intestazione
			List<BodyRow> listBodyRow = new ArrayList<ExcelDto.BodyRow>();

			for (VExportRicercaContribuzioneDTO contribuzione : lista) {
				BodyRow bodyRow = new BodyRow();

				List<ColBodyRow> listColBodyRow = new ArrayList<ExcelDto.ColBodyRow>();
				listColBodyRow.add(new ColBodyRow(contribuzione.getAzienda()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getPrimoTelaio())); // primo telaio
				listColBodyRow.add(new ColBodyRow(contribuzione.getTarga()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDataPrimaImmatricolazione(),
						EXCEL_PATTERN.EXTEND_DATE_PATTERN)); // Data
				listColBodyRow.add(new ColBodyRow(contribuzione.getNrCartaCircolazione()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getMarca()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getModello()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getClasseAmbientale()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getClasseAmbientaleEuro()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getClasseVeicolo()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getLunghezza()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getTipologiaDimensionale()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getnPostiSedere(), EXCEL_PATTERN.INT_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getnPostiInPiedi(), EXCEL_PATTERN.INT_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getnPostiRiservati(), EXCEL_PATTERN.INT_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getPostiCarrozzina(), EXCEL_PATTERN.INT_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getTipoAllestimento()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getTipoAlimentazione()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getAvm()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getTipologiaAvm()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getFlgRilevatoreBip()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDescPortabici()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDescSistemaVideosorveglianza()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDotazioniDisabili()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getImpiantiAudio()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getImpiantiVideo()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getContapasseggeri()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getProtezioneAutista()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getImpiantoCondizionamento()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getFlgFiltroFap()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getAltriAllestimenti()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getFlgConteggiatoMiv()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getFonteFinanziamento()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDescBreve()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getContributoPubblico(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getContributoStatale(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getContributoRegionaleAggiuntivo(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow
						.add(new ColBodyRow(contribuzione.getContributoRegionaleFf(), EXCEL_PATTERN.EURO_PATTERN));

				listColBodyRow.add(new ColBodyRow(contribuzione.getNrDeterminaRpAmpAnt()));
				listColBodyRow
						.add(new ColBodyRow(contribuzione.getDataDeterminaRpAmpAnt(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getNrAttoLiquidazioneRpAmpAnt()));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getDataAttoLiquidazioneRpAmpAnt(), EXCEL_PATTERN.DATE_PATTERN));

				listColBodyRow.add(new ColBodyRow(contribuzione.getNrDeterminaRpAmpSal()));
				listColBodyRow
						.add(new ColBodyRow(contribuzione.getDataDeterminaRpAmpSal(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getNrAttoLiquidazioneRpAmpSal()));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getDataAttoLiquidazioneRpAmpSal(), EXCEL_PATTERN.DATE_PATTERN));

				listColBodyRow.add(new ColBodyRow(contribuzione.getNrDeterminaAmpAzAnt()));
				listColBodyRow
						.add(new ColBodyRow(contribuzione.getDataDeterminaAmpAzAnt(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getNrAttoLiquidazioneAmpAzAnt()));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getDataAttoLiquidazioneAmpAzAnt(), EXCEL_PATTERN.DATE_PATTERN));

				listColBodyRow.add(new ColBodyRow(contribuzione.getNrDeterminaAmpAzSal()));
				listColBodyRow
						.add(new ColBodyRow(contribuzione.getDataDeterminaAmpAzSal(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getNrAttoLiquidazioneAmpAzSal()));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getDataAttoLiquidazioneAmpAzSal(), EXCEL_PATTERN.DATE_PATTERN));

				listColBodyRow.add(new ColBodyRow(contribuzione.getCupMaster()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getCup()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getCig()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDataAggiudicazione(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDataStipula(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDataOrdine(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getNumeroOrdine()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getFornitore()));

				listColBodyRow
						.add(new ColBodyRow(contribuzione.getCostoAutobusClimatizzato(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getCostoSistemiPmr(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getCostoContapasseggeri(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getCostoIndicatoriLineaPercorso(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getCostoDispositiviLocalizzazione(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(
						new ColBodyRow(contribuzione.getCostoSistemiVideosorveglianza(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getCostoSistemiProtezioneConducente(),
						EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getCostoPortabici(), EXCEL_PATTERN.EURO_PATTERN));
				listColBodyRow
						.add(new ColBodyRow(contribuzione.getCostoPredisposizioniBip(), EXCEL_PATTERN.EURO_PATTERN));

				listColBodyRow.add(new ColBodyRow(contribuzione.getTipoDocQuietanza()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getNrQuietanza()));
				listColBodyRow.add(new ColBodyRow(contribuzione.getDataQuietanza(), EXCEL_PATTERN.DATE_PATTERN));
				listColBodyRow.add(new ColBodyRow(contribuzione.getTelaioVeicoloSostituito()));

				listColBodyRow.add(new ColBodyRow(contribuzione.getPresenzaPannello()));

				bodyRow.setListColBodyRow(listColBodyRow);

				listBodyRow.add(bodyRow);
			}

			excel.setListBodyRow(listBodyRow);

			return ExcelUtil.generaExcel(excel);

		} catch (Exception e) {
			LOGGER.error("ExcelReportManager::excelRicercaContribuzione excpetion: " + e.getMessage() + " - "
					+ UtilRebus.getStackTrace(e));
			// Rilancio una RebusException
			throw new RebusException(e);
		} finally {
			LOGGER.info("ExcelReportManager::excelRicercaContribuzione END");
		}

	}

	// intestazione contribuzione
	private static List<ColHeaderRow> getHeaderXlsExportaContribuzione(boolean storico) {

		List<ColHeaderRow> listColHeaderRow = new ArrayList<ExcelDto.ColHeaderRow>();
		listColHeaderRow.add(new ColHeaderRow("Azienda", 30));
		listColHeaderRow.add(new ColHeaderRow("Primo Telaio", 30));
		listColHeaderRow.add(new ColHeaderRow("Targa", 20));
		listColHeaderRow.add(new ColHeaderRow("Data prima immatricolazione", 20));
		listColHeaderRow.add(new ColHeaderRow("N. carta di circolazione", 20));
		listColHeaderRow.add(new ColHeaderRow("Marca", 10));
		listColHeaderRow.add(new ColHeaderRow("Modello", 20));
		listColHeaderRow.add(new ColHeaderRow("Classe ambientale (Direttiva UE)", 30));
		listColHeaderRow.add(new ColHeaderRow("Classe ambientale (Euro)", 20));
		listColHeaderRow.add(new ColHeaderRow("Classe veicolo", 10));
		listColHeaderRow.add(new ColHeaderRow("Lunghezza (mt.)", 10));
		listColHeaderRow.add(new ColHeaderRow("Classe di lunghezza", 20));
		listColHeaderRow.add(new ColHeaderRow("Posti a Sedere", 10));
		listColHeaderRow.add(new ColHeaderRow("Posti in Piedi", 10));
		listColHeaderRow.add(new ColHeaderRow("Posti Riservati", 10));
		listColHeaderRow.add(new ColHeaderRow("Posti Carrozzina", 10));
		listColHeaderRow.add(new ColHeaderRow("Allestimento", 15));
		listColHeaderRow.add(new ColHeaderRow("Alimentazione", 15));
		listColHeaderRow.add(new ColHeaderRow("AVM", 10));
		listColHeaderRow.add(new ColHeaderRow("Tipologia AVM", 10));
		listColHeaderRow.add(new ColHeaderRow("Sistema Bigliettazione elettronica (BIP)", 20));
		listColHeaderRow.add(new ColHeaderRow("Portabici", 15));
		listColHeaderRow.add(new ColHeaderRow("Sistemi videosorveglianza", 15));
		listColHeaderRow.add(new ColHeaderRow("Facilitazioni accesso disabili", 30));
		listColHeaderRow.add(new ColHeaderRow("Impianti audio", 20));
		listColHeaderRow.add(new ColHeaderRow("Impianti visivi", 20));
		listColHeaderRow.add(new ColHeaderRow("Conta Passeggeri", 10));
		listColHeaderRow.add(new ColHeaderRow("Sistemi protezione autista anche anti covid", 20));
		listColHeaderRow.add(new ColHeaderRow("Impianto Condizionamento", 15));
		listColHeaderRow.add(new ColHeaderRow("Filtro FAP", 15));
		listColHeaderRow.add(new ColHeaderRow("Altre dotazioni", 20));
		listColHeaderRow.add(new ColHeaderRow("Conteggiato MIV", 15));
		listColHeaderRow.add(new ColHeaderRow("Fonte di Finanziamento", 30));
		listColHeaderRow.add(new ColHeaderRow("Atto di assegnazione delle risorse alla Regione", 30));
		listColHeaderRow.add(new ColHeaderRow("Contributo pubblico erogato (Euro)", 20));
		listColHeaderRow.add(new ColHeaderRow("Contributo statale erogato (Euro)", 20));
		listColHeaderRow.add(new ColHeaderRow("Contributo regionale aggiuntivo erogato (Euro)", 20));
		listColHeaderRow
				.add(new ColHeaderRow("Costo complessivo ammissibile ai sensi dei criteri regionali (Euro)", 20));
		// anticipo
		listColHeaderRow.add(new ColHeaderRow("Numero determina (RP -> AMP - Anticipo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data determina (RP -> AMP - Anticipo)", 20));
		listColHeaderRow.add(new ColHeaderRow("Numero atto di liquidazione (RP -> AMP - Anticipo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data atto di liquidazione (RP -> AMP - Anticipo)", 20));
		// saldo
		listColHeaderRow.add(new ColHeaderRow("Numero determina (RP -> AMP - Saldo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data determina (RP -> AMP - Saldo)", 20));
		listColHeaderRow.add(new ColHeaderRow("Numero atto di liquidazione (RP -> AMP - Saldo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data atto di liquidazione (RP -> AMP - Saldo)", 20));
		// anticipo
		listColHeaderRow.add(new ColHeaderRow("Numero determina (AMP -> Azienda - Anticipo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data determina (AMP -> Azienda - Anticipo)", 20));
		listColHeaderRow.add(new ColHeaderRow("Numero atto di liquidazione (AMP -> Azienda - Anticipo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data atto di liquidazione (AMP -> Azienda - Anticipo)", 20));
		// saldo
		listColHeaderRow.add(new ColHeaderRow("Numero determina (AMP -> Azienda - Saldo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data determina (AMP -> Azienda - Saldo)", 20));
		listColHeaderRow.add(new ColHeaderRow("Numero atto di liquidazione (AMP -> Azienda - Saldo)", 30));
		listColHeaderRow.add(new ColHeaderRow("Data atto di liquidazione (AMP -> Azienda - Saldo)", 20));

		listColHeaderRow.add(new ColHeaderRow("CUP master", 15));
		listColHeaderRow.add(new ColHeaderRow("CUP", 15));
		listColHeaderRow.add(new ColHeaderRow("CIG", 15));
		listColHeaderRow.add(new ColHeaderRow("Data aggiudicazione", 15));
		listColHeaderRow.add(new ColHeaderRow("Data stipula", 15));
		listColHeaderRow.add(new ColHeaderRow("Data ordine", 15));
		listColHeaderRow.add(new ColHeaderRow("Numero ordine", 10));
		listColHeaderRow.add(new ColHeaderRow("Fornitore", 20));
		listColHeaderRow.add(new ColHeaderRow("Costo autobus climatizzato (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo sistemi accessibilita' PMR (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo contapasseggeri (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo indicatori di linea e percorso (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo dispositivi localizzazione (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo sistemi di videosorveglianza (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo sistemi protezione conducente (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo portabici (IVA escl.)", 15));
		listColHeaderRow.add(new ColHeaderRow("Costo predisposizioni BIP (IVA escl.)", 15));

		listColHeaderRow.add(new ColHeaderRow("Tipologia documento di quietanza", 15));
		listColHeaderRow.add(new ColHeaderRow("N. quietanza di pagamento", 15));
		listColHeaderRow.add(new ColHeaderRow("Data quietanza di pagamento", 15));
		listColHeaderRow.add(new ColHeaderRow("Telaio veicolo sostituito", 20));
		listColHeaderRow.add(new ColHeaderRow("Presenza pannello pubblicita' fonte finanziamento", 15));

		return listColHeaderRow;
	}

}