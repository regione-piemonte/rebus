/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import it.csi.rebus.rebuscrus.vo.AziendaAutobusVO;

public interface CommonDAO {

	public Integer eseguiQuery(@Param("query") String query);

	public List<AziendaAutobusVO> getAziendaAutobusByIdVa(@Param("idVariazAutobus") Long idVariazAutobus);

	public List<Long> getSoggettiByIdProcedimento(@Param("idProcedimento") Long idProcedimento);


	public List<Long> getSoggettiById(@Param("id") Long id);
}
