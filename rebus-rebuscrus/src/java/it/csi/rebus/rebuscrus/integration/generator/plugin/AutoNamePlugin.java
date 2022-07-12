/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.rebus.rebuscrus.integration.generator.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class AutoNamePlugin extends PluginAdapter {

	private String placeholder;
	private Pattern patternPlaceholder;

	private Boolean log = false;
	
	private String dtoSuffix = "Dto";
	public AutoNamePlugin() {
	}

	public boolean validate(List<String> warnings) {

		placeholder = properties.getProperty("placeholder");
		dtoSuffix = properties.getProperty("dtoSuffix");

		if (!stringHasValue(placeholder)) placeholder = "autoName";
		if (!stringHasValue(dtoSuffix)) dtoSuffix = "Dto";

		patternPlaceholder = Pattern.compile(placeholder);

		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		
		java.io.PrintWriter writer = null;
		if (log) {
			try {
				writer = new java.io.PrintWriter("C:/UPAP/log.txt", "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	    
		String tableName = introspectedTable
				.getFullyQualifiedTable()
				.getIntrospectedTableName()
				.toLowerCase();
		
		if (writer != null) writer.println("TN : " + tableName);

		String calculated = calculateEntityFromTableName(tableName);
		
		if (writer != null) writer.println("calculated : " + calculated);
		if (writer != null) writer.println("matcher : " + placeholder);
		if (writer != null) writer.println("calculated + dtoSuffix : " + calculated + dtoSuffix);
		if (writer != null) writer.println("getBaseRecordType : " + introspectedTable.getBaseRecordType());
		if (writer != null) writer.println("contains pattern: " + introspectedTable.getBaseRecordType().contains(placeholder));
		if (writer != null) writer.println("matches: " + patternPlaceholder.matcher(introspectedTable.getBaseRecordType()).matches());
		
		introspectedTable.setBaseRecordType(
			patternPlaceholder.matcher(
				introspectedTable.getBaseRecordType()
			).replaceAll(
				calculated + dtoSuffix
			)
		);

		if (writer != null) writer.println("getBaseRecordType : " + introspectedTable.getBaseRecordType());
		
		introspectedTable.setExampleType(
			patternPlaceholder.matcher(
				introspectedTable.getExampleType()
			).replaceAll(
				calculated + dtoSuffix
			)
		);
		
		introspectedTable.setDAOInterfaceType(
			patternPlaceholder.matcher(
				introspectedTable.getDAOInterfaceType()
			).replaceAll(
				calculated + dtoSuffix
			)
		);

		introspectedTable.setDAOImplementationType(
			patternPlaceholder.matcher(
				introspectedTable.getDAOImplementationType()
			).replaceAll(
				calculated + dtoSuffix
			)
		);

		introspectedTable.setMyBatis3JavaMapperType(
			patternPlaceholder.matcher(
				introspectedTable.getMyBatis3JavaMapperType()
			).replaceAll(
				calculated + dtoSuffix
			)
		);

		introspectedTable.setRecordWithBLOBsType(
			patternPlaceholder.matcher(
				introspectedTable.getRecordWithBLOBsType()
			).replaceAll(
				calculated + dtoSuffix
			)
		);

		introspectedTable.setMyBatis3SqlProviderType(
			patternPlaceholder.matcher(
				introspectedTable.getMyBatis3SqlProviderType()
			).replaceAll(
				calculated + dtoSuffix 
			)
		);
		
		introspectedTable.setPrimaryKeyType(
			patternPlaceholder.matcher(
				introspectedTable.getPrimaryKeyType()
			).replaceAll(
				calculated + dtoSuffix
			)
		);
				
		introspectedTable.setMyBatis3XmlMapperFileName(
			patternPlaceholder.matcher(
				introspectedTable.getMyBatis3XmlMapperFileName()
			).replaceAll(
				calculated + dtoSuffix
			)
		);
		
		if (writer != null) writer.close();
	}

	private String calculateEntityFromTableName(String tn) {
		String o = "";
		for (String token : tn.split("_")) {
			o += token.substring(0, 1).toUpperCase() + token.substring(1);
		}
		return o;
	}
	
}