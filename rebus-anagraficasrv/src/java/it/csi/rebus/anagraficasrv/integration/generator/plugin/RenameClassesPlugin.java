/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.generator.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.List;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class RenameClassesPlugin extends PluginAdapter {

	private String searchStringExample;
	private String replaceStringExample;
	private Pattern patternExample;
	private String searchStringDto;
	private String replaceStringDto;
	private Pattern patternDto;
	private String searchStringMapper;
	private String replaceStringMapper;
	private Pattern patternMapper;
	private String searchStringBlob;
	private String replaceStringBlob;
	private Pattern patternBlob;
	private String searchStringSqlProvider;
	private String replaceStringSqlProvider;
	private Pattern patternSqlProvider;
	private String searchStringKey;
	private String replaceStringKey;
	private Pattern patternKey;
	private String searchStringXml;
	private String replaceStringXml;
	private Pattern patternXml;
	
	/**
	 * 
	 */
	public RenameClassesPlugin() {
	}

	public boolean validate(List<String> warnings) {

		patternExample = null;
		patternDto = null;
		patternMapper = null;
		patternBlob = null;
		patternSqlProvider = null;
		patternKey = null;
		patternXml = null;

		searchStringExample = properties.getProperty("searchStringExample");
		replaceStringExample = properties.getProperty("replaceStringExample");
		searchStringDto = properties.getProperty("searchStringDto");
		replaceStringDto = properties.getProperty("replaceStringDto");
		searchStringMapper = properties.getProperty("searchStringMapper");
		replaceStringMapper = properties.getProperty("replaceStringMapper");
		searchStringBlob = properties.getProperty("searchStringBlob");
		replaceStringBlob = properties.getProperty("replaceStringBlob");
		searchStringSqlProvider = properties.getProperty("searchStringSqlProvider");
		replaceStringSqlProvider = properties.getProperty("replaceStringSqlProvider");
		searchStringKey = properties.getProperty("searchStringKey");
		replaceStringKey = properties.getProperty("replaceStringKey");
		searchStringXml = properties.getProperty("searchStringXml");
		replaceStringXml = properties.getProperty("replaceStringXml");

		if (stringHasValue(searchStringExample))
			patternExample = Pattern.compile(searchStringExample);
		if (stringHasValue(searchStringDto))
			patternDto = Pattern.compile(searchStringDto);
		if (stringHasValue(searchStringMapper))
			patternMapper = Pattern.compile(searchStringMapper);
		if (stringHasValue(searchStringBlob))
			patternBlob = Pattern.compile(searchStringBlob);
		if (stringHasValue(searchStringSqlProvider))
			patternSqlProvider = Pattern.compile(searchStringSqlProvider);
		if (stringHasValue(searchStringKey))
			patternKey = Pattern.compile(searchStringKey);
		if (stringHasValue(searchStringXml))
			patternXml = Pattern.compile(searchStringXml);

		if (patternBlob == null) {
			patternBlob = patternDto;
			replaceStringBlob = replaceStringDto;
		}
		
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		
		if (patternDto != null) {
			introspectedTable.setBaseRecordType(
					patternDto.matcher(introspectedTable.getBaseRecordType()).replaceAll(replaceStringDto));
		}

		if (patternExample != null) {
			introspectedTable.setExampleType(
					patternExample.matcher(introspectedTable.getExampleType()).replaceAll(replaceStringExample));
		}

		if (patternMapper != null) {
			introspectedTable.setMyBatis3JavaMapperType(
					patternMapper.matcher(introspectedTable.getMyBatis3JavaMapperType()).replaceAll(replaceStringMapper));
		}

		if (patternBlob != null) {
			introspectedTable.setRecordWithBLOBsType(
					patternBlob.matcher(introspectedTable.getRecordWithBLOBsType()).replaceAll(replaceStringBlob));
		}

		if (patternSqlProvider != null) {
			introspectedTable.setMyBatis3SqlProviderType(patternSqlProvider
					.matcher(introspectedTable.getMyBatis3SqlProviderType()).replaceAll(replaceStringSqlProvider));
		}

		if (patternKey != null) {
			introspectedTable.setPrimaryKeyType(
					patternKey.matcher(introspectedTable.getPrimaryKeyType()).replaceAll(replaceStringKey));
		}

		if (patternXml != null) {
			introspectedTable.setMyBatis3XmlMapperFileName(
					patternXml.matcher(introspectedTable.getMyBatis3XmlMapperFileName()).replaceAll(replaceStringXml));
		}
	}

	/*
	 * Reference on types: 0 :
	 * it.csi.flaistd.attestaweb.dao.dto.AtstTLockAttestatoRawDto 1 :
	 * it.csi.flaistd.attestaweb.dao.AtstTLockAttestatoRawDtoDAOImpl 2 :
	 * it.csi.flaistd.attestaweb.dao.AtstTLockAttestatoRawDtoDAO 3 :
	 * it.csi.flaistd.attestaweb.dao.AtstTLockAttestatoRawDtoMapper 4 :
	 * it.csi.flaistd.attestaweb.dao.AtstTLockAttestatoRawDtoSqlProvider 5 :
	 * it.csi.flaistd.attestaweb.dao.dto.AtstTLockAttestatoRawDtoKey 6 :
	 * it.csi.flaistd.attestaweb.dao.dto.AtstTLockAttestatoRawDtoWithBLOBs 7 :
	 * TABLE
	 * 
	 * lines.add("0 : " + introspectedTable.getBaseRecordType()); lines.add(
	 * "1 : " + introspectedTable.getDAOImplementationType()); lines.add("2 : "
	 * + introspectedTable.getDAOInterfaceType()); lines.add("3 : " +
	 * introspectedTable.getMyBatis3JavaMapperType()); lines.add("4 : " +
	 * introspectedTable.getMyBatis3SqlProviderType()); lines.add("5 : " +
	 * introspectedTable.getPrimaryKeyType()); lines.add("6 : " +
	 * introspectedTable.getRecordWithBLOBsType()); lines.add("7 : " +
	 * introspectedTable.getTableType());
	 */
}