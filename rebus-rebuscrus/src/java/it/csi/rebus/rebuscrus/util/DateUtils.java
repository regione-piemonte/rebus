/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final SimpleDateFormat onlydata = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat onlytime = new SimpleDateFormat("HH:mm");

	static public Date calendarToDate(XMLGregorianCalendar calendar) {
		Date date = null;
		if (calendar != null) {
			date = calendar.toGregorianCalendar().getTime();
		}
		return date;
	}

	public static String getTimeStamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);

	}
	
	public static String dateToString(Date date) {
		if (date==null) return "";
		return onlydata.format(date);

	}
	
	public static String timeToString(Date date) {
		if (date==null) return "";
		return onlytime.format(date);

	}
	
	public static Date getDataSistema() {
		return new Date(System.currentTimeMillis());
	}
	
	public static boolean equals(Date date1, Date date2) {
		if (date1==null && date2==null)
			return true;
		if (date1!=null && date2!= null)
			return date1.equals(date2);
		
		return false;
	}
	
	
}
