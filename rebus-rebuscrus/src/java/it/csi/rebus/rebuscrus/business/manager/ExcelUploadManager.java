/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.business.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.rebus.rebuscrus.business.helper.ExcelHelper;
import it.csi.rebus.rebuscrus.excel.Cella;
import it.csi.rebus.rebuscrus.excel.DateValidator;
import it.csi.rebus.rebuscrus.excel.DoubleValidator;
import it.csi.rebus.rebuscrus.excel.LongValidator;
import it.csi.rebus.rebuscrus.excel.ReflectionUtil;
import it.csi.rebus.rebuscrus.excel.StringValidator;
import it.csi.rebus.rebuscrus.excel.Validator;
import it.csi.rebus.rebuscrus.excel.Validator.Combo;
import it.csi.rebus.rebuscrus.integration.dto.custom.AutobusDto;
import it.csi.rebus.rebuscrus.integration.dto.custom.ResponseExcelDto;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.util.SecurityUtils;

@Component
public class ExcelUploadManager {

	private static Logger logger = Logger
			.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + ".ExcelUploadManager");
	@Autowired
	private ExcelHelper excelHelper;

	/**
	 * Questo metodo legge il file excel caricato dall'utente ed esegue i seguenti
	 * passo: - crea un fiel excel speculare a quello passato in input - verifica
	 * che i campi siano compilati correttamente: date epr date, numeri per numeri,
	 * ecc - verifica i campi obbligatori - le celle che presentano problemi vengono
	 * evidenziati di gliallo e viene aggiunto un commento che spiega il problema
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	private ResponseExcelDto readXLSFile(InputStream excelFileToRead, InputStream excelFileTemplate) {
		ResponseExcelDto response = new ResponseExcelDto();
		ByteArrayOutputStream out = null;
		HSSFWorkbook wb = null;
		HSSFWorkbook template = null;
		try {

			final List<Validator> validators = getRowValidator();
			final List<AutobusDto> mezzi = new ArrayList<AutobusDto>();
			final ReflectionUtil reflectionUtil = new ReflectionUtil();

			wb = new HSSFWorkbook(excelFileToRead);
			template = new HSSFWorkbook(excelFileTemplate);
			/*
			 * Imposta il colore delle celle che hanno un problema
			 */
			final HSSFSheet sheet = wb.getSheetAt(0);
			final HSSFSheet sheetTemplate = template.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			HSSFRow rowTemplate;
			final FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

			Iterator<Row> rows = sheet.rowIterator();
			Iterator<Row> rowsTemplate = sheetTemplate.rowIterator();
			rowTemplate = sheetTemplate.getRow(0);
			row = sheet.getRow(0);
			if (row.getLastCellNum() - row.getFirstCellNum() != rowTemplate.getLastCellNum()
					- rowTemplate.getFirstCellNum()) {
				out = new ByteArrayOutputStream();
				template.write(out);
				template.close();
				wb.close();
				response.setExcel(out.toByteArray());
				response.setErroreException(true);
				response.setCaricamentoDatiOK(false);
				response.setMsgErroreException("Excel non aggiornato. Scaricare la versione aggiornata del file");
				out.close();
				return response;

			}
			Iterator<Cell> cellsIt = row.cellIterator();
			Iterator<Cell> cellsTemplate = rowTemplate.cellIterator();
			while (cellsIt.hasNext()) {
				if (!(cellsIt.next().getStringCellValue()
						.equalsIgnoreCase(cellsTemplate.next().getStringCellValue()))) {
					out = new ByteArrayOutputStream();
					template.write(out);
					template.close();
					wb.close();
					response.setExcel(out.toByteArray());
					response.setErroreException(true);
					response.setCaricamentoDatiOK(false);
					response.setMsgErroreException("Excel non aggiornato. Scaricare la versione aggiornata del file");
					out.close();
					return response;
				}
			}

			int numRow = 0;
			boolean errore = false;

			AutobusDto autobus = null;
			// scorro le righe
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				logger.debug("ExcelUploadManager::readXLSFile: elab row " + numRow + " start");
				// Salto la prima riga
				if (numRow > 0) {
					autobus = new AutobusDto();
					final Iterator<Cell> cells = row.cellIterator();
					final Iterator<Validator> iValidator = validators.iterator();

					// controllo se c'e' un'intera riga vuota, vuol dire che
					// devo terminare l'elaborazione
					if (isNotEmptyRow(row, validators)) {
						// scorro le colonne
						int i = 0;
						while (iValidator.hasNext()) {
							cell = (HSSFCell) row.getCell(i);
							final Validator validator = (Validator) iValidator.next();

							if (cell != null) {
								// Verifico se il contentuo della cella e'
								// corretto
								final Cella cella = validator.validate(cell, evaluator, this.excelHelper);
								cell.setCellComment(null);

								if (cella.isError()) {
									// Se ci sono problemi coloro la cella di
									// giallo e segnalo il problema
									cell.setCellComment(validator.getComment(cell, cella.getComment()));
									errore = true;
								} else {
									// con la reflection recupero il valore
									// della cella che sto analizzando
									// e lo imposto nel dto che usero per
									// inserire i dati sul db
									reflectionUtil.setParam(autobus, validator, cella);
								}
							}
							i++;
						}
						mezzi.add(autobus);
					} else {
						break;
					}
				}
				logger.debug("ExcelUploadManager::readXLSFile: elab row " + numRow + " end");
				numRow++;
			}

			response.setErroreCampiExcel(errore);
			if (errore) {
				out = new ByteArrayOutputStream();
				wb.write(out);
				wb.close();
				response.setExcel(out.toByteArray());
				out.close();
			} else {
				response.setMezzi(mezzi);
			}
		} catch (Exception e) {
			logger.error("ExcelUploadManager::readXLSFile exception: " + e.getMessage());
			response.setErroreException(true);
			response.setMsgErroreException(e.getMessage());
			try {
				if (wb != null)
					wb.close();
				if (out != null)
					out.close();
			} catch (Exception e2) {
				logger.error("ExcelUploadManager::readXLSFile error closing file: " + e2.getMessage());
			}
		}
		return response;
	}

	private boolean isNotEmptyRow(HSSFRow row, List<Validator> validators) {
		boolean result = false;
		final Iterator<Cell> cells = row.cellIterator();
		final Iterator<Validator> iValidator = validators.iterator();
		HSSFCell cell;
		while (cells.hasNext() && iValidator.hasNext()) {
			final Validator validator = (Validator) iValidator.next();
			cell = (HSSFCell) cells.next();
			if (!(cell == null || cell.getCellTypeEnum() == CellType.BLANK)) {
				result = true;
			}

		}
		return result;
	}

	/**
	 * Aggiungo tutte le colonne che dovranno essere lette e quindi validate
	 * passando: - nome dell'attributo contenuto nella colonna (lo usero' con la
	 * reflection per popolare il dto) - indico se il campo e' obbligatorio o meno -
	 * indico se e' una combo
	 * 
	 * @return
	 */
	private List<Validator> getRowValidator() {
		List<Validator> validator = new ArrayList<Validator>();
		/*
		 * Numero telaio * Numero di telaio
		 */
		validator.add(new StringValidator("nTelaio"));

		/*
		 * Targa * Se il veicolo e' da immatricolare, inserire il testo
		 * "Da immatricolare"
		 */
		validator.add(new StringValidator("nTarga"));

		/*
		 * Data prima immatricolazione (gg/mm/aaaa) Se il veicolo e' "Da immatricolare",
		 * non compilare. Se la data e' ipotetica: inserire
		 * "01/07/anno di immatricolazione"
		 */
		validator.add(new DateValidator("dtPrimaImmatricolazione"));

		/*
		 * Data prima immatricolazione (gg/mm/aaaa) Se il veicolo e' "Da immatricolare",
		 * non compilare. Se la data e' ipotetica: inserire
		 * "01/07/anno di immatricolazione"
		 */
		validator.add(new DateValidator("dataUltimaImmatricolazione"));

		/*
		 * Alimentazione * Se si seleziona "Altro", specificare nel campo
		 * "Altra alimentazione"
		 */
		validator.add(new StringValidator("fkTipoAlimentazione", true, Combo.TIPO_ALIMENTAZIONE));
		/*
		 * Numero posti a sedere * Da carta di circolazione, campo S1.
		 */
		validator.add(new LongValidator("nPostiSedere", true));
		/*
		 * Numero posti in piedi * Da carta di circolazione, campo S2
		 */
		validator.add(new LongValidator("nPostiInPiedi", true));
		/*
		 * Numero posti riservati Numero di posti a sedere per passeggeri con ridotta
		 * capacita' motoria
		 */
		validator.add(new LongValidator("nPostiRiservati"));

		/*
		 * Omologazione Direttiva europea Da carta di circolazione, campo V9
		 */
		validator.add(new StringValidator("omologazioneDirettivaEuropea"));

		/*
		 * Omologazione Classe Euro * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("fkClasseAmbientaleEuro", true, Combo.TIPO_EURO));

		/*
		 * Filtro FAP Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("flgFiltroFap", Validator.CAMPO_CHECK_S_N, Validator.ERRORE_CAMPO_CHECK_S_N));

		/*
		 * Facilitazioni disabili * Facilitazioni per l'accesso per i disabili
		 */
		validator.add(new StringValidator("fkDotazioneDisabili", true, Combo.TIPO_FACILITAZIONI));

		/*
		 * Impianti audio * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("fkImpiantiAudio", true, Combo.TIPO_AUDIO));

		/*
		 * Impianti visivi * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("fkImpiantiVisivi", true, Combo.TIPO_VIDEO));

		/*
		 * Presenza rilevatore BIP Selezionare dal menu a tendina
		 */
		validator.add(
				new StringValidator("flgRilevatoreBip", Validator.CAMPO_CHECK_S_N, Validator.ERRORE_CAMPO_CHECK_S_N));

		/*
		 * Prezzo totale d'acquisto (euro) Numero con due cifre decimali
		 */
		validator.add(new DoubleValidator("prezzoTotAcquisto"));

		/*
		 * Entita' contributo pubblico (euro) Numero con due cifre decimali
		 */
		validator.add(new DoubleValidator("contributoPubblicoAcquisto"));

		/*
		 * Veicolo assicurato Nel caso in cui il veicolo non sia assicurato precisare la
		 * motivazione nel campo "Note"
		 */
		validator.add(new StringValidator("flgVeicoloAssicurato", Validator.CAMPO_CHECK_S_N,
				Validator.ERRORE_CAMPO_CHECK_S_N));

		/*
		 * Data ultima revisione (gg/mm/aaaa) Se la data precisa non e' disponibile,
		 * scrivere "01/07/data ultima revisione"
		 */
		validator.add(new DateValidator("dataUltimaRevisione"));

		/*
		 * Note Campo disponibile per eventuali annotazioni.
		 */
		validator.add(new StringValidator("note"));

		/*
		 * Primo telaio * Se non variato, riportare il numero inserito nel campo
		 * "Telaio"
		 */
		validator.add(new StringValidator("primoTelaio", true));

		/*
		 * Matricola aziendale Matricola aziendale
		 */
		validator.add(new StringValidator("nMatricolaAziendale"));

		/*
		 * Tipo immatricolazione * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("fkTipoImmatricolazione", true, Combo.TIPO_IMMATRICOLAZIONE));

		/*
		 * Ente che ha autorizzato la prima immatricolazione * Ente che ha autorizzato
		 * la prima immatricolazione
		 */
		validator.add(new StringValidator("enteAutorizzPrimaImm"));

		/*
		 * Classe veicolo * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("fkClasseVeicolo", true, Combo.CLASSE_VEICOLO));

		/*
		 * Marca * Marca
		 */
		validator.add(new StringValidator("marca", true));

		/*
		 * Modello * Modello
		 */
		validator.add(new StringValidator("modello", true));

		/*
		 * Due piani * Selezionare dal menu a tendina
		 */
		validator.add(
				new StringValidator("flgDuePiani", Validator.CAMPO_CHECK_S_N_U, Validator.ERRORE_CAMPO_CHECK_S_N_U));

		/*
		 * Snodato * Selezionare dal menu a tendina
		 */
		validator.add(
				new StringValidator("flgSnodato", Validator.CAMPO_CHECK_S_N_U, Validator.ERRORE_CAMPO_CHECK_S_N_U));

		/*
		 * Caratteristiche particolari Per esempio: scoperto, ristobus, etc.
		 */
		validator.add(new StringValidator("caratteristicheParticolari"));

		/*
		 * Altra alimentazione Compilare nel caso in cui si sia selezionato 'Altro' dal
		 * menu a tendina del campo "Tipo di alimentazione"
		 */
		validator.add(new StringValidator("altraAlimentazione"));

		/*
		 * Tipo allestimento * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("fkTipoAllestimento", true, Combo.TIPO_ALLESTIMENTO)); // allestimento

		/*
		 * Lunghezza * (metri) Lunghezza in metri. Numerico con tre cifre decimali
		 */
		validator.add(new DoubleValidator("lunghezza", true));

		/*
		 * Numero porte Numero intero maggiore di 1
		 */
		validator.add(new LongValidator("numeroPorte"));

		/*
		 * Numero posti carrozzina * Numero posti carrozzina
		 */
		validator.add(new LongValidator("postiCarrozzina", true));

		/*
		 * Impianto di condizionamento * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("flgImpiantoCondizionamento", true, Validator.CAMPO_CHECK_S_N_U,
				Validator.ERRORE_CAMPO_CHECK_S_N_U));

		/*
		 * Cabina guida isolata Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("flgCabinaGuidaIsolata", Validator.CAMPO_CHECK_S_N,
				Validator.ERRORE_CAMPO_CHECK_S_N));

		/*
		 * Dispositivi prevenzione incidenti Selezionare dal menu a tendina. Se presenti
		 * piu' dispositivi tra quelli indicati in tendina, indicare gli altri
		 * dispositivi tramite applicativo web.
		 */
		validator.add(new StringValidator("fkDispositiviPrevenzInc", true, Combo.TIPO_DISPOSITIVI_PREVENZIONE));

		/*
		 * Altri dispositivi prevenzione incidenti Inserire dispositivi non previsti nel
		 * menu a tendina del campo "Dispositivi prevenzione incidenti"
		 */
		validator.add(new StringValidator("altriDispositiviPrevenzInc"));

		/*
		 * OTX * Selezionare dal menu a tendina
		 */
		validator.add(
				new StringValidator("flgOtx", true, Validator.CAMPO_CHECK_S_N_U, Validator.ERRORE_CAMPO_CHECK_S_N_U));

		/*
		 * AVM * Selezionare dal menu a tendina
		 */
		validator.add(
				new StringValidator("flgAvm", true, Validator.CAMPO_CHECK_S_N_U, Validator.ERRORE_CAMPO_CHECK_S_N_U));

		/*
		 * Contapasseggeri * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("flgContapasseggeri", true, Validator.CAMPO_CHECK_S_N_U,
				Validator.ERRORE_CAMPO_CHECK_S_N_U));

		/*
		 * Proprieta'/Leasing * Selezionare dal menu a tendina
		 */
		validator.add(new StringValidator("fkProprietaLeasing", true, Combo.PROPRIETA_LEASING));

		/*
		 * Conteggiato nel MIV Selezionare dal menu a tendina
		 */
		// REBUS-52 non piu' richiesto
		// validator.add(new StringValidator("flgConteggiatoMiv",
		// Validator.CAMPO_CHECK_S_N, Validator.ERRORE_CAMPO_CHECK_S_N));

		/*
		 * Data alienazione (gg/mm/aaaa) Data alienazione (gg/mm/aaaa). Da popolare se
		 * il veicolo e' alienato.
		 */
		validator.add(new DateValidator("dataAlienazione"));

		validator.add(new StringValidator("fkCategoriaVeicolo", true, Combo.CATEGORIA_VEICOLO));

		if (SecurityUtils.isAutorizzato(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO)) {
			/*
			 * Il servizio importa i dati di piu' aziende, quindi al fondo dell'excel c'e'
			 * uan colonna che indica su quale azienda si sta lavorando. Leggo la colonna
			 * con i dati dell'azienda.
			 */
			validator.add(new LongValidator("fkAzienda", true));
		}

		return validator;
	}

	/*
	 * Metodo che verifica l'excel passato in input e risponde. Se e' andato tutto
	 * bene salva i dati passati su DB altrimenti restituisce un file excel con
	 * l'indicazione del problema
	 */
	public ResponseExcelDto letturaControlloDatiExcel(InputStream excelFileToRead, InputStream excelFileTemplate) {
		ResponseExcelDto response = null;
		try {
			// Carico I dati delle tabelle di decodifica
			this.excelHelper.loadTable();
			// Leggo il file excel e controllo che siano rispettati i controlli
			// di obbligatorieta' ed i vari vincoli imposto (flag, valori
			// presenenti nelle tabelle)
			response = readXLSFile(excelFileToRead, excelFileTemplate);
			if (!response.isErroreCampiExcel() && !response.isErroreException()) {
				/*
				 * Per i campi Primo telaio, telaio e targa occorre in fase di caricamento su db
				 * eliminare gli eventuali spazi inseriti dall'utente in fase di compilazione
				 * del xls. Per ciascun autobus nuovo caricato tramite upload, il sistema dovra'
				 * valorizzare in automatico a N sia il campo flg_verificato_azienda sia il
				 * campo flg_verificato_AMP.
				 */
				this.excelHelper.inizializzazionePuliziaDatiAutobus(response);

				/*
				 * Il sistema dovra' verificare che ciascun numero di primo telaio e che ciascun
				 * numero di targa sia presente una sola volta nel file in fase di caricamento.
				 */
				if (checkPrimoTelaioAndTargaDup(response)) {
					response.setErroreCongruenzaCampiExcel(true);
					return response;
				}

				Boolean isPrimoImpianto = SecurityUtils
						.isAutorizzato(AuthorizationRoles.UPLOAD_FILE_XLS_PRIMO_IMPIANTO);// rebus
																							// 16
																							// //no
																							// id
																							// Azienda
				Boolean isUploadExcel = SecurityUtils.isAutorizzato(AuthorizationRoles.UPLOAD_FILE_XLS);// rebus
																										// 4
																										// //SI
																										// IDaZIENDA
																										// NEL
																										// PROFILO

				if (!isPrimoImpianto && isUploadExcel) {
					it.csi.rebus.rebuscrus.security.UserInfo userInfo = SecurityUtils.getCurrentUserInfo();
					// Sono un utente azienda, quindi verifico la congruenza con
					// idAzienda associato al profilo
					if (this.excelHelper.controlliIncrociatiFileDbExcel(response, userInfo.getIdAzienda(), null)) {
						response.setErroreCongruenzaCampiExcel(true);
					} else {
						response.setCaricamentoDatiOK(true);
					}
				} else {

					/*
					 * Sono il servizio applicativo, quindi vuol dire che ho piu' aziende associate,
					 * devo leggere gli idAzienda caricati dal file excel e fare la verifica
					 */

					/*
					 * devo leggere tutte le righe e spezzare a gruppi di idAziendaOmogenei
					 */
					if (response.getMezzi().get(0).getFkAzienda() == null) {
						// Avendo caricato i dati come servizio applicativo deve
						// essere presenta la colonna idAzienda
						// Se non e' presente segnalo l'errore
						response.setErroreCongruenzaCampiExcel(true);
						List<String> msgErroreCongruenzaCampiExcel = new ArrayList<String>();
						msgErroreCongruenzaCampiExcel
								.add("Il file excel non presenta la colonna idAzienda valorizzata");
						response.setMsgErroreCongruenzaCampiExcel(msgErroreCongruenzaCampiExcel);
					} else {
						long idAzienda = response.getMezzi().get(0).getFkAzienda();
						List<AutobusDto> mezzi = new ArrayList<AutobusDto>();
						for (AutobusDto mezzo : response.getMezzi()) {
							if (mezzo.getFkAzienda().longValue() == idAzienda) {
								mezzi.add(mezzo);
							} else {
								// Verifico per questo gruppo di mezzi la
								// congruenza
								// e non proseguo9 con ulteriori controlli
								if (this.excelHelper.controlliIncrociatiFileDbExcel(response, idAzienda, mezzi)) {
									response.setErroreCongruenzaCampiExcel(true);
									return response;
								}

								idAzienda = mezzo.getFkAzienda().longValue();
								mezzi = new ArrayList<AutobusDto>();
								mezzi.add(mezzo);
							}
						}

						if (this.excelHelper.controlliIncrociatiFileDbExcel(response, idAzienda, mezzi)) {
							response.setErroreCongruenzaCampiExcel(true);
						} else {
							response.setCaricamentoDatiOK(true);
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error("ExcelUploadManager::letturaControlloDatiExcel exception: " + e.getMessage());
			if (response == null)
				response = new ResponseExcelDto();
			response.setErroreException(true);
			response.setMsgErroreException(e.getMessage());
		}
		return response;
	}

	public void scrivoDatiExcelSuDB(ResponseExcelDto response) {
		/*
		 * Inserisco tutti gli autobus letti dal file excel
		 */
		for (AutobusDto autobus : response.getMezzi()) {
			this.excelHelper.insertAutobus(autobus);
		}
	}

	private boolean checkPrimoTelaioAndTargaDup(ResponseExcelDto response) {
		boolean dupKey = false;
		/*
		 * Il sistema dovra' verificare che ciascun numero di primo telaio e che ciascun
		 * numero di targa sia presente una sola volta nel file in fase di caricamento.
		 */
		HashMap<String, String> targa = new HashMap<String, String>();
		HashMap<String, String> primoTelaio = new HashMap<String, String>();

		List<String> msgErroreCongruenzaCampiExcel = new ArrayList<String>();

		for (AutobusDto autobus : response.getMezzi()) {
			if (autobus.getnTarga() != null) {
				if (targa.get(autobus.getnTarga()) != null) {
					msgErroreCongruenzaCampiExcel.add(autobus.getnTarga() + ": numero targa duplicato");
					dupKey = true;
				} else {
					targa.put(autobus.getnTarga(), autobus.getnTarga());
				}
			}

			if (primoTelaio.get(autobus.getPrimoTelaio()) != null) {
				msgErroreCongruenzaCampiExcel.add(autobus.getPrimoTelaio() + ": primo telaio duplicato");
				dupKey = true;
			} else {
				primoTelaio.put(autobus.getPrimoTelaio(), autobus.getPrimoTelaio());
			}
		}
		if (dupKey)
			response.setMsgErroreCongruenzaCampiExcel(msgErroreCongruenzaCampiExcel);
		return dupKey;
	}
}
