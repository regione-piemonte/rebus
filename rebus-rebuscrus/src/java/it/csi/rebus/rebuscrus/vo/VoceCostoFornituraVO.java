/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

public class VoceCostoFornituraVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idVoceCostoFornitura;
	private Long idVoceCosto;
	private Double importo;
	private Long idCostoFornitura;

	public Long getIdVoceCostoFornitura() {
		return idVoceCostoFornitura;
	}

	public void setIdVoceCostoFornitura(Long idVoceCostoFornitura) {
		this.idVoceCostoFornitura = idVoceCostoFornitura;
	}

	public Long getIdVoceCosto() {
		return idVoceCosto;
	}

	public void setIdVoceCosto(Long idVoceCosto) {
		this.idVoceCosto = idVoceCosto;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public Long getIdCostoFornitura() {
		return idCostoFornitura;
	}

	public void setIdCostoFornitura(Long idCostoFornitura) {
		this.idCostoFornitura = idCostoFornitura;
	}

	public boolean isPresentIn(List<VoceCostoFornituraVO> list) {
		boolean result = false;
		if (idCostoFornitura == null && idVoceCostoFornitura == null) {
			return false;
		}
		for (VoceCostoFornituraVO voceCostoFornituraVO : list) {
			if (idCostoFornitura.equals(voceCostoFornituraVO.getIdCostoFornitura())
					&& idVoceCosto.equals(voceCostoFornituraVO.getIdVoceCosto())
					&& idVoceCostoFornitura.equals(voceCostoFornituraVO.getIdVoceCostoFornitura())
					&& importo.equals(voceCostoFornituraVO.getImporto())) {
				result = true;
				break;
			}
		}
		return result;
	}
}
