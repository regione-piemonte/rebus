/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class ProrogaVO extends ParentVO implements Comparable<ProrogaVO> {
	private Long idProroga;
	private Long idContratto;
	private String attoProroga;
	private Date dataFineProroga;

	public Long getIdProroga() {
		return idProroga;
	}

	public void setIdProroga(Long idProroga) {
		this.idProroga = idProroga;
	}

	public Long getIdContratto() {
		return idContratto;
	}

	public void setIdContratto(Long idContratto) {
		this.idContratto = idContratto;
	}

	public String getAttoProroga() {
		return attoProroga;
	}

	public void setAttoProroga(String attoProroga) {
		this.attoProroga = attoProroga;
	}

	public Date getDataFineProroga() {
		return dataFineProroga;
	}

	public void setDataFineProroga(Date dataFineProroga) {
		this.dataFineProroga = dataFineProroga;
	}

	@Override
	public int compareTo(ProrogaVO o) {
		if (this.dataFineProroga == null || o.getDataFineProroga() == null)
			return 0;
		return o.getDataFineProroga().compareTo(this.dataFineProroga);
	}

}
