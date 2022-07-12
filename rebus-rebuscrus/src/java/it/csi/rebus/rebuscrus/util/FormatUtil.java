/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
	public static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	 
	public static String formatDate(Date date) {
		return formatter.format(date);
	}
	
	public static Date parseDate(String date) {
		if(date==null || date.trim().length()<=0)
			return null;
		
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
}