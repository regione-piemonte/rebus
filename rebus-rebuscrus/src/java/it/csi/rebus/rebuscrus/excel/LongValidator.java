/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.excel;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import it.csi.rebus.rebuscrus.business.helper.ExcelHelper;

public class LongValidator extends Validator {

	public LongValidator(String itemName, boolean obbligatorio) {
		super(itemName, obbligatorio);
	}

	public LongValidator(String itemName) {
		super(itemName);
	}
	
	@Override
	public Cella validateString(HSSFCell cell,FormulaEvaluator evaluator,ExcelHelper excelHelper){
		Cella cella=new Cella();
		try{
			cella.setLongValue(Long.parseLong(cell.getStringCellValue()));
		} catch(Exception e){
			cella.setError(true);
			cella.setComment(Validator.ERRORE_CAMPO_LONG);
		}
		return cella;
	}

	@Override
	public Cella validateNumeric(HSSFCell cell,FormulaEvaluator evaluator){
		Cella cella=new Cella();
		cella.setLongValue((new Double(cell.getNumericCellValue())).longValue());
		return cella;
	}

	@Override
	public Cella validateBoolean(HSSFCell cell,FormulaEvaluator evaluator){
		Cella cella=new Cella();
		cella.setError(true);
		cella.setComment(Validator.ERRORE_CAMPO_LONG);
		return cella;
	}
}
