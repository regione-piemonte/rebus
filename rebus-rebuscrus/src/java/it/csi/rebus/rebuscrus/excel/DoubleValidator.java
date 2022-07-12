/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.excel;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import it.csi.rebus.rebuscrus.business.helper.ExcelHelper;

public class DoubleValidator extends Validator {

	public DoubleValidator(String itemName, boolean obbligatorio) {
		super(itemName, obbligatorio);
	}

	public DoubleValidator(String itemName) {
		super(itemName);
	}

	@Override
	public Cella validateString(HSSFCell cell, FormulaEvaluator evaluator, ExcelHelper excelHelper) {
		Cella cella = new Cella();
		try {
			String value = cell.getStringCellValue();

			value = value.replace("\u20ac.", "").replace("\u20ac", "").replace(" ", "");

			String decimalFormat = "###,##0.000";
			DecimalFormat decimalFormatter = new DecimalFormat(decimalFormat);
			cella.setDoubleValue(decimalFormatter.parse(value).doubleValue());
		} catch (Exception e) {
			cella.setError(true);
			cella.setComment(Validator.ERRORE_CAMPO_DOUBLE);
		}
		return cella;
	}

	@Override
	public Cella validateNumeric(HSSFCell cell, FormulaEvaluator evaluator) {
		Cella cella = new Cella();
		cella.setDoubleValue(cell.getNumericCellValue());
		return cella;
	}

	@Override
	public Cella validateBoolean(HSSFCell cell, FormulaEvaluator evaluator) {
		Cella cella = new Cella();
		cella.setError(true);
		cella.setComment(Validator.ERRORE_CAMPO_DOUBLE);
		return cella;
	}
}
