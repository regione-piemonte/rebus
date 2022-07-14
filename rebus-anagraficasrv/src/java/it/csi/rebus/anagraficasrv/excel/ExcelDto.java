/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.excel;

import java.util.List;

import it.csi.rebus.anagraficasrv.excel.ExcelUtil.EXCEL_PATTERN;

/*
 * Classe che permette di fare excel indipendentemente dalal tecnologia utilizzata: es. poi o jxl
 * E' un contenitore dove possiamo inserire:
 * - nome del file
 * - nome delal cartella
 * - elenco con le colonne di header
 * - elenco di righe
 */
public class ExcelDto {
	/*
	 * memorizza il nome del file
	 */
	private String fileName;
	/*
	 * memorizza il nome dello sheet
	 */
	private String sheetName;
	/*
	 * memorizza, se ci sono le righe di intestazioen del fiel excel
	 */
	private List<String> intestazioneRow;
	/*
	 * memorizza l'elenco delle colonne di header
	 */
	private List<ColHeaderRow> listColHeaderRow;
	/*
	 * memorizza l'elenco delle righe
	 */
	private List<BodyRow> listBodyRow;

	public List<ColHeaderRow> getListColHeaderRow() {
		return listColHeaderRow;
	}

	public void setListColHeaderRow(List<ColHeaderRow> listColHeaderRow) {
		this.listColHeaderRow = listColHeaderRow;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<BodyRow> getListBodyRow() {
		return listBodyRow;
	}

	public void setListBodyRow(List<BodyRow> listBodyRow) {
		this.listBodyRow = listBodyRow;
	}

	public List<String> getIntestazioneRow() {
		return intestazioneRow;
	}

	public void setIntestazioneRow(List<String> intestazioneRow) {
		this.intestazioneRow = intestazioneRow;
	}

	/*
	 * classe che memorizza le colonne della riga di header
	 */
	public static class ColHeaderRow {

		public ColHeaderRow(String label, int width) {
			this.label = label;
			this.width = width;
		}

		/*
		 * nome visualizzato nelal colonna
		 */
		private String label;
		/*
		 * larghezza della colonna
		 */
		private int width;

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}
	}

	/*
	 * classe che memorizza la riga dell'excel, eccetto la riga di header
	 */
	public static class BodyRow {
		/*
		 * elenco di colonne
		 */
		private List<ColBodyRow> listColBodyRow;

		public List<ColBodyRow> getListColBodyRow() {
			return listColBodyRow;
		}

		public void setListColBodyRow(List<ColBodyRow> listColBodyRow) {
			this.listColBodyRow = listColBodyRow;
		}
	}

	/*
	 * Classe che contiene la singola cella della riga
	 */
	public static class ColBodyRow {
		public ColBodyRow(Object value, EXCEL_PATTERN pattern) {
			this.value = value;
			this.pattern = pattern;
		}

		// Dato molte colonne sono string se non viene passato nulla assumiamo
		// come default String
		public ColBodyRow(Object value) {
			if (value == null)
				this.value = "";
			else
				this.value = value;
			this.pattern = EXCEL_PATTERN.STRING_PATTERN;
		}

		/*
		 * Contenuto delal cella
		 */
		private Object value;
		/*
		 * Indica il tipo di contenuto - STRING - NUMBER_2_DIGIT - EURO - INT - DATE
		 */
		private EXCEL_PATTERN pattern;

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public EXCEL_PATTERN getPattern() {
			return pattern;
		}

		public void setPattern(EXCEL_PATTERN pattern) {
			this.pattern = pattern;
		}
	}
}
