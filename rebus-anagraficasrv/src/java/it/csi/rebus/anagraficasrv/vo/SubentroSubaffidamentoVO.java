/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class SubentroSubaffidamentoVO extends ParentVO implements Comparable<SubentroSubaffidamentoVO> {
	private Long id;
	private SoggettoSubentroVO soggettoContraente;
	private String contraenteGroup;
	private SoggettoSubentroVO soggettoSubentrante;
	private TipoSostituzioneVO tipoSostituzione;
	private String atto;
	private Date data;
	private Date dataFine;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SoggettoSubentroVO getSoggettoContraente() {
		return soggettoContraente;
	}

	public void setSoggettoContraente(SoggettoSubentroVO soggettoContraente) {
		this.soggettoContraente = soggettoContraente;
	}

	public String getContraenteGroup() {
		return contraenteGroup;
	}

	public void setContraenteGroup(String contraenteGroup) {
		this.contraenteGroup = contraenteGroup;
	}

	public SoggettoSubentroVO getSoggettoSubentrante() {
		return soggettoSubentrante;
	}

	public void setSoggettoSubentrante(SoggettoSubentroVO soggettoSubentrante) {
		this.soggettoSubentrante = soggettoSubentrante;
	}

	public TipoSostituzioneVO getTipoSostituzione() {
		return tipoSostituzione;
	}

	public void setTipoSostituzione(TipoSostituzioneVO tipoSostituzione) {
		this.tipoSostituzione = tipoSostituzione;
	}

	public String getAtto() {
		return atto;
	}

	public void setAtto(String atto) {
		this.atto = atto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	@Override
	public int compareTo(SubentroSubaffidamentoVO o) {
		if (this.data == null)
			return -1;
		if (o.getData() == null)
			return 1;
		return this.data.compareTo(o.getData());
	}

}
