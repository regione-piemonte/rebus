/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import org.apache.ibatis.annotations.Param;



public interface DocumentiDAO {

	// METODO CREATO MANUALMENTE
	String elencoDocumentiDesc(@Param("idTipoDocumento") Long idTipoDocumento);
}
