/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.generator.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class VOGeneratorPlugin extends PluginAdapter {

	private String targetPackage;
	private String searchStringVO;
	private String replaceStringVO;
	private Pattern patternVO;
	
	public VOGeneratorPlugin() {

	}

	@Override
	public boolean validate(List<String> warnings) {

		patternVO = null;

		searchStringVO = properties.getProperty("searchStringVO");
		replaceStringVO = properties.getProperty("replaceStringVO");
		
		if (stringHasValue(searchStringVO))
			patternVO = Pattern.compile(searchStringVO);
		
		targetPackage = properties.getProperty("targetPackage");
		
		if (targetPackage.endsWith(".")) 
			targetPackage = targetPackage.substring(0,  targetPackage.length() - 1);
		
		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
		IntrospectedTable introspectedTable
	) {
		List<String> log = new ArrayList<String>();
		Path file = Paths.get("C:\\log.txt");

		List<GeneratedJavaFile> generate = new ArrayList<GeneratedJavaFile>();
		
		String name = introspectedTable.getBaseRecordType();
		String[] splittedName = name.split("\\.");
		
		name = patternVO.matcher(splittedName[splittedName.length - 1]).replaceAll(replaceStringVO);
		String fullName = targetPackage + "." + name;
		
		org.mybatis.generator.api.dom.java.TopLevelClass voFileCU = 
			new org.mybatis.generator.api.dom.java.TopLevelClass(fullName);
		
        JavaFormatter jf = new DefaultJavaFormatter();

		GeneratedJavaFile voFile = new GeneratedJavaFile(
			voFileCU, "UTF-8", jf
		);
		
//		voFileCU.setSuperClass("java.lang.Object"); // TODO
		voFileCU.addField(new Field("id", new FullyQualifiedJavaType("java.lang.Long")));
		
		log.add(fullName);
		
		try {
			log.add("------------------------------");
			Files.write(file, log, Charset.forName("UTF-8"), StandardOpenOption.WRITE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		generate.add(voFile);
		
		return generate;
	}
}