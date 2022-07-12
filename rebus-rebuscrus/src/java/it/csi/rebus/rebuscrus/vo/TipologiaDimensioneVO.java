/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class TipologiaDimensioneVO extends ParentVO {
	private Long idTipoAllestimento;
	private Double lunghezzaMin;
	private Double lunghezzaMax;
	
	public Long getIdTipoAllestimento() {
		return idTipoAllestimento;
	}
	public void setIdTipoAllestimento(Long idTipoAllestimento) {
		this.idTipoAllestimento = idTipoAllestimento;
	}
	public Double getLunghezzaMin() {
		return lunghezzaMin;
	}
	public void setLunghezzaMin(Double lunghezzaMin) {
		this.lunghezzaMin = lunghezzaMin;
	}
	public Double getLunghezzaMax() {
		return lunghezzaMax;
	}
	public void setLunghezzaMax(Double lunghezzaMax) {
		this.lunghezzaMax = lunghezzaMax;
	}
	
}
