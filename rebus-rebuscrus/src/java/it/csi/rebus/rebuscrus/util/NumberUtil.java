/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.util;

import java.text.DecimalFormat;

public class NumberUtil {

	public static Long parseLong(String val) {
		return (val != null && val.trim().length() > 0) ? Long.parseLong(val) : null;
	}

	static public String customFormat(String pattern, Double value) {
		if (value == null)
			return "";
		if (value.equals(new Double(0)))
			return "0,00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(value);
		return output;
	}
	
	public static boolean equals(Number number1, Number number2) {
		if (number1==null && number2==null)
			return true;
		if (number1!=null && number2!= null)
			return number1.equals(number2);
		
		return false;
	}
	
	
}