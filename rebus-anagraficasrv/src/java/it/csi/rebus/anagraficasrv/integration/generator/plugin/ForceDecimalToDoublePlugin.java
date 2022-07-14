/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.integration.generator.plugin;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class ForceDecimalToDoublePlugin extends PluginAdapter {

	public ForceDecimalToDoublePlugin() {

	}

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {

		List<String> toReplace = new ArrayList<String>();
		toReplace.add("java.math.BigDecimal");
		toReplace.add("java.lang.Float");
		String forceTo = "java.lang.Double";

		for (IntrospectedColumn o : introspectedTable.getAllColumns()) {
			if (toReplace.contains(o.getFullyQualifiedJavaType().getFullyQualifiedName())) {
				FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(forceTo);
				o.setFullyQualifiedJavaType(fullyQualifiedJavaType);
			}
		}

	}
}