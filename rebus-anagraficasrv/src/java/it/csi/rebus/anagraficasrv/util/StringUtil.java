/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.util;

public class StringUtil {
	public static boolean isEmpty(String str){
		if (str==null || "".equals(str.trim())) return true;
		return false;
	}
	
	public static boolean isNotEmpty(String str){
		if (str!=null && !"".equals(str.trim())) return true;
		return false;
	}
	
	public static String removedot0(String str){
		if (str!=null){
			try{
				return String.valueOf( new Double( Double.parseDouble(str)).longValue()) ;
			} catch (Exception e) {
				return str;
			}
		} else return null;
	}
	
	/**
	 * @formatter:off
	 * 
	 * @formatter:on
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (isEmpty(str1) && isEmpty(str2))
			return true;
		if (isNotEmpty(str1) && isNotEmpty(str2))
			return str1.equalsIgnoreCase(str2);
		else
			return false;
	}
}