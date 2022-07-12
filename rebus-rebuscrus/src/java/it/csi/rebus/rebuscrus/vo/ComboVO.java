/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

public class ComboVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	
	private String   identificativo;
	private List<DescrizioneVO> valori;

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public List<DescrizioneVO> getValori() {
		return valori;
	}

	public void setValori(List<DescrizioneVO> valori) {
		this.valori = valori;
	}

}
