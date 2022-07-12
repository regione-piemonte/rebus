/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class VoceDiCostoVeicoloVO extends ParentVO {
	private Long id;
	private Long idVoceCosto;
	private Long idProcedimento;
	private String primoTelaio;
	private Double importo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdVoceCosto() {
		return idVoceCosto;
	}

	public void setIdVoceCosto(Long idVoceCosto) {
		this.idVoceCosto = idVoceCosto;
	}

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}
}
