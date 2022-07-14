/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

@SuppressWarnings("serial")
public class TipoDocumentoVO extends DropDownMenu {
	private String descrizioneEstesa;
	private Long ordinamento;

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public Long getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(Long ordinamento) {
		this.ordinamento = ordinamento;
	}
}
