/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

public class DatoBonificoVO extends ParentVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDatoBonifico;
	private Date dataBonifico;
	private Double importoBonifico;
	private String cro;

	public Long getIdDatoBonifico() {
		return idDatoBonifico;
	}

	public void setIdDatoBonifico(Long idDatoBonifico) {
		this.idDatoBonifico = idDatoBonifico;
	}

	public Date getDataBonifico() {
		return dataBonifico;
	}

	public void setDataBonifico(Date dataBonifico) {
		this.dataBonifico = dataBonifico;
	}

	public Double getImportoBonifico() {
		return importoBonifico;
	}

	public void setImportoBonifico(Double importoBonifico) {
		this.importoBonifico = importoBonifico;
	}

	public String getCro() {
		return cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

}
