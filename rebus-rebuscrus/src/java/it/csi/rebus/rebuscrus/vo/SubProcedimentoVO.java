/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class SubProcedimentoVO extends ParentVO {
	private Long idProcedimento;
	private Long idSubProcedimento1;
	private Long idSubProcedimento2;

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public Long getIdSubProcedimento1() {
		return idSubProcedimento1;
	}

	public void setIdSubProcedimento1(Long idSubProcedimento1) {
		this.idSubProcedimento1 = idSubProcedimento1;
	}

	public Long getIdSubProcedimento2() {
		return idSubProcedimento2;
	}

	public void setIdSubProcedimento2(Long idSubProcedimento2) {
		this.idSubProcedimento2 = idSubProcedimento2;
	}
}
