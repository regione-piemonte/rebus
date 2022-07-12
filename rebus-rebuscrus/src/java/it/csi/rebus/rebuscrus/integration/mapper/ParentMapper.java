/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.mapper;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ParentMapper {

	@Autowired
	private Mapper dozerMapper;

	public Mapper getDozerMapper() {
		return dozerMapper;
	}

	
}
