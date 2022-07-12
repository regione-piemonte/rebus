/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.excel;

import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.RichTextString;

import it.csi.rebus.rebuscrus.business.helper.ExcelHelper;
import it.csi.rebus.rebuscrus.util.StringUtil;

public abstract class Validator {

	public static final String ERRORE_CAMPO_OBBLIGATORIO = "Cella obbligatoria";
	public static final String ERRORE_CAMPO_DATA = "La cella deve contenere una data valida (formato dd/mm/yyyy)";
	public static final String ERRORE_CAMPO_LONG = "La cella deve contenere un numero intero";
	public static final String ERRORE_CAMPO_DOUBLE = "La cella deve contenere un numero";
	public static final String ERRORE_CAMPO_FORMULA = "La cella non deve contenere una formula";
	public static final String ERRORE_CAMPO_BOOLEAN_STRING = "La cella non deve contenere una stringa";
	public static final String ERRORE_CAMPO_COMBO_STRING = "La cella contiene un valore diverso da quelli previsti";
	public static final String ERRORE_CAMPO_CHECK_S_N = "La cella deve contenere solo S o N";
	public static final HashMap<String, String> CAMPO_CHECK_S_N = new HashMap<String, String>();

	public static final String ERRORE_CAMPO_CHECK_S_N_U = "La cella deve contenere solo S o N o U";
	public static final HashMap<String, String> CAMPO_CHECK_S_N_U = new HashMap<String, String>();

	static {
		CAMPO_CHECK_S_N.put("S", "S");
		CAMPO_CHECK_S_N.put("N", "N");

		CAMPO_CHECK_S_N_U.put("S", "S");
		CAMPO_CHECK_S_N_U.put("N", "N");
		CAMPO_CHECK_S_N_U.put("U", "U");
	}

	public static enum Combo {
		NO_COMBO, PROPRIETA_LEASING, TIPO_DISPOSITIVI_PREVENZIONE, CATEGORIA_VEICOLO, CLASSE_VEICOLO,
		TIPO_IMMATRICOLAZIONE, TIPO_ALIMENTAZIONE, TIPO_ALLESTIMENTO, TIPO_EURO, TIPO_FACILITAZIONI, TIPO_AUDIO,
		TIPO_VIDEO, TIPO_FLAG
	}

	public Validator(String itemName, Combo combo) {
		this.itemName = itemName;
		this.obbligatorio = false;
		this.combo = combo;
	}

	public Validator(String itemName, boolean obbligatorio, Combo combo) {
		this.itemName = itemName;
		this.obbligatorio = obbligatorio;
		this.combo = combo;
	}

	public Validator(String itemName, boolean obbligatorio, HashMap<String, String> check, String erroreCheck) {
		this.itemName = itemName;
		this.obbligatorio = obbligatorio;
		this.combo = Combo.NO_COMBO;
		this.check = check;
		this.erroreCheck = erroreCheck;
	}

	public Validator(String itemName, HashMap<String, String> check, String erroreCheck) {
		this.itemName = itemName;
		this.obbligatorio = false;
		this.combo = Combo.NO_COMBO;
		this.check = check;
		this.erroreCheck = erroreCheck;
	}

	public Validator(String itemName, boolean obbligatorio) {
		this.itemName = itemName;
		this.obbligatorio = obbligatorio;
		this.combo = Combo.NO_COMBO;
	}

	public Validator(String itemName) {
		this.itemName = itemName;
		this.obbligatorio = false;
		this.combo = Combo.NO_COMBO;
	}

	private boolean obbligatorio;
	private String itemName;
	private Combo combo;
	private HashMap<String, String> check;
	private String erroreCheck;

	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public String getItemName() {
		return itemName;
	}

	public HashMap<String, String> getCheck() {
		return check;
	}

	public String getErroreCheck() {
		return erroreCheck;
	}

	public Combo getCombo() {
		return combo;
	}

	public Comment getComment(HSSFCell cell, String commento) {
		Drawing drawing = cell.getSheet().createDrawingPatriarch();
		CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();
		ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex() + 3);
		anchor.setRow1(cell.getRowIndex());
		anchor.setRow2(cell.getRowIndex() + 3);

		Comment comment = drawing.createCellComment(anchor);
		RichTextString str = factory.createRichTextString(commento);
		comment.setVisible(Boolean.FALSE);
		comment.setString(str);
		return comment;
	}

	public Cella validate(HSSFCell cell, FormulaEvaluator evaluator, ExcelHelper excelHelper) {
		Cella cella = null;

		switch (cell.getCellTypeEnum()) {
		case STRING:
			cella = validateString(cell, evaluator, excelHelper);
			break;
		case NUMERIC:
			cella = validateNumeric(cell, evaluator);
			break;
		case BOOLEAN:
			cella = validateBoolean(cell, evaluator);
			break;
		case FORMULA:
			cella = new Cella();
			cella.setError(true);
			cella.setComment(Validator.ERRORE_CAMPO_FORMULA);
			return cella;
		case _NONE:
			cella = new Cella();
			break;
		case ERROR:
			cella = new Cella();
			break;
		case BLANK:
			cella = new Cella();
			break;
		}

		if (cella.isError())
			return cella;

		if (isObbligatorio() && StringUtil.isEmpty(cella.getStrValue()) && cella.getDateValue() == null
				&& cella.getLongValue() == null && cella.getDoubleValue() == null) {
			cella.setError(true);
			cella.setComment(Validator.ERRORE_CAMPO_OBBLIGATORIO);
		}
		return cella;
	}

	public abstract Cella validateString(HSSFCell cell, FormulaEvaluator evaluator, ExcelHelper excelHelper);

	public abstract Cella validateNumeric(HSSFCell cell, FormulaEvaluator evaluator);

	public abstract Cella validateBoolean(HSSFCell cell, FormulaEvaluator evaluator);
}
