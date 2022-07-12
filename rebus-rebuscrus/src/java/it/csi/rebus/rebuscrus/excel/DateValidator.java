/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.excel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import it.csi.rebus.rebuscrus.business.helper.ExcelHelper;

public class DateValidator extends Validator {
	
	public DateValidator(String itemName, boolean obbligatorio) {
		super(itemName,obbligatorio);
	}

	public DateValidator(String itemName) {
		super(itemName);
	}
	
	@Override
	public Cella validateString(HSSFCell cell,FormulaEvaluator evaluator,ExcelHelper excelHelper){
		Cella cella=new Cella();
	
		if (cell.getStringCellValue()!=null) {
			try {
				DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				cella.setDateValue(format.parse(cell.getStringCellValue()));
			} catch (ParseException e) {
				
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				try {
					cella.setDateValue(format.parse(cell.getStringCellValue()));
				} catch (ParseException e1) {
					cella.setError(true);
					cella.setComment(Validator.ERRORE_CAMPO_DATA);
				}
			}
		}
		return cella;
	}
	
	

	@Override
	public Cella validateNumeric(HSSFCell cell,FormulaEvaluator evaluator){
		Cella cella=new Cella();
		if (DateUtil.isCellDateFormatted(cell)) {
			cella.setDateValue(cell.getDateCellValue());
		} else {
			cella.setError(true);
			cella.setComment(Validator.ERRORE_CAMPO_DATA);
			return cella;
		}
		return cella;
	}

	@Override
	public Cella validateBoolean(HSSFCell cell,FormulaEvaluator evaluator){
		Cella cella=new Cella();
		cella.setError(true);
		cella.setComment(Validator.ERRORE_CAMPO_DATA);
		return cella;
	}
}
