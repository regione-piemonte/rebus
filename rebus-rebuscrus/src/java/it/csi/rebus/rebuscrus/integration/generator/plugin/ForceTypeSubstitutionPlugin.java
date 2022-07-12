/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.generator.plugin;

import java.util.List;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class ForceTypeSubstitutionPlugin extends PluginAdapter {

	private String from;
	private String to;

	public ForceTypeSubstitutionPlugin() {

	}

	public boolean validate(List<String> warnings) {

		from = properties.getProperty("from");
		to = properties.getProperty("to");

		boolean valid = stringHasValue(from) && stringHasValue(to);

		if (valid) {
			// NOP
		} else {
			if (!stringHasValue(from)) {
				warnings.add(getString("ValidationError.18", this.getClass().getSimpleName(), "from"));
			}
			if (!stringHasValue(to)) {
				warnings.add(getString("ValidationError.18", this.getClass().getSimpleName(), "to"));
			}
		}

		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {

		for (IntrospectedColumn o : introspectedTable.getAllColumns()) {
			if (o.getFullyQualifiedJavaType().getFullyQualifiedName().equals(from)) {
				FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType(to);
				o.setFullyQualifiedJavaType(fullyQualifiedJavaType);
			}
		}

	}
}