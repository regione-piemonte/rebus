/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.excel;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import it.csi.rebus.rebuscrus.business.helper.ExcelHelper;

public class StringValidator extends Validator {

	public StringValidator(String itemName, Combo combo) {
		super(itemName, combo);
	}
	
	public StringValidator(String itemName, boolean obbligatorio, Combo combo) {
		super(itemName, obbligatorio, combo);
	}
	
	public StringValidator(String itemName, boolean obbligatorio, HashMap<String, String> check, String erroreCheck){
		super(itemName, obbligatorio, check, erroreCheck);
	}
	
	public StringValidator(String itemName, HashMap<String, String> check, String erroreCheck){
		super(itemName, check, erroreCheck);
	}

	public StringValidator(String itemName, boolean obbligatorio) {
		super(itemName, obbligatorio);
	}

	public StringValidator(String itemName) {
		super(itemName);
	}

	@Override
	public Cella validateString(HSSFCell cell, FormulaEvaluator evaluator, ExcelHelper excelHelper) {
		Cella cella = new Cella();

		if (getCombo()==Combo.NO_COMBO){
			if (this.getCheck()!=null){
				//Controllo che il valore inserito sia fra quelli possibili
				if (this.getCheck().get(cell.getStringCellValue().toUpperCase())!=null){
					cella.setStrValue(cell.getStringCellValue().toUpperCase());
				} else {
					cella.setError(true);
					cella.setComment(Validator.ERRORE_CAMPO_CHECK_S_N);
				}
			} else {
				cella.setStrValue(cell.getStringCellValue());
			}
		} else {
			Long result=excelHelper.getIdFromDescription(cell.getStringCellValue(), getCombo());
			if (result!=null && result==-1){
				cella.setError(true);
				cella.setComment(Validator.ERRORE_CAMPO_COMBO_STRING);
			} else {
				cella.setLongValue(result);
			}
		}

		return cella;
	}

	@Override
	public Cella validateNumeric(HSSFCell cell, FormulaEvaluator evaluator) {
		Cella cella = new Cella();
		if (getCombo()==Combo.NO_COMBO){
			if (DateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				cella.setStrValue(dateFormat.format(cell.getDateCellValue()));
			} else {
				cella.setStrValue(Double.toString(cell.getNumericCellValue()));
			}
		} else {
			cella.setError(true);
			cella.setComment(Validator.ERRORE_CAMPO_COMBO_STRING);
		}
		return cella;
	}

	@Override
	public Cella validateBoolean(HSSFCell cell, FormulaEvaluator evaluator) {
		Cella cella = new Cella();
		cella.setError(true);
		cella.setComment(Validator.ERRORE_CAMPO_BOOLEAN_STRING);
		return cella;
	}

}
