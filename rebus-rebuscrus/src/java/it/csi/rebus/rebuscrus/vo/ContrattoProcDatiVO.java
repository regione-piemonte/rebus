/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

@SuppressWarnings("serial")
public class ContrattoProcDatiVO extends ParentVO {
	private String codIdNazionale;
	private String codRegionale;
	private String descrizione;
	private SoggettoContrattoVO enteComm;
	private SoggettoContrattoVO esecTit;
	private SoggettoContrattoVO capofila;
	private SoggettoContrattoVO soggRichiedente;
	private SoggettoContrattoVO soggIntermediario;
	private Long idTipoRaggruppamento;
	private String descTipoRaggruppamento;
	private Boolean isSubaffidataria;
	private List<Long> ListaSubaffidatarie;
	private Boolean isSubaffidatariaConsorziata;

	public String getCodIdNazionale() {
		return codIdNazionale;
	}

	public void setCodIdNazionale(String codIdNazionale) {
		this.codIdNazionale = codIdNazionale;
	}

	public String getCodRegionale() {
		return codRegionale;
	}

	public void setCodRegionale(String codRegionale) {
		this.codRegionale = codRegionale;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public SoggettoContrattoVO getEnteComm() {
		return enteComm;
	}

	public void setEnteComm(SoggettoContrattoVO enteComm) {
		this.enteComm = enteComm;
	}

	public SoggettoContrattoVO getEsecTit() {
		return esecTit;
	}

	public void setEsecTit(SoggettoContrattoVO esecTit) {
		this.esecTit = esecTit;
	}

	public SoggettoContrattoVO getCapofila() {
		return capofila;
	}

	public void setCapofila(SoggettoContrattoVO capofila) {
		this.capofila = capofila;
	}

	public SoggettoContrattoVO getSoggRichiedente() {
		return soggRichiedente;
	}

	public void setSoggRichiedente(SoggettoContrattoVO soggRichiedente) {
		this.soggRichiedente = soggRichiedente;
	}

	public SoggettoContrattoVO getSoggIntermediario() {
		return soggIntermediario;
	}

	public void setSoggIntermediario(SoggettoContrattoVO soggIntermediario) {
		this.soggIntermediario = soggIntermediario;
	}

	public Long getIdTipoRaggruppamento() {
		return idTipoRaggruppamento;
	}

	public void setIdTipoRaggruppamento(Long idTipoRaggruppamento) {
		this.idTipoRaggruppamento = idTipoRaggruppamento;
	}

	public String getDescTipoRaggruppamento() {
		return descTipoRaggruppamento;
	}

	public void setDescTipoRaggruppamento(String descTipoRaggruppamento) {
		this.descTipoRaggruppamento = descTipoRaggruppamento;
	}


	public List<Long> getListaSubaffidatarie() {
		return ListaSubaffidatarie;
	}

	public void setListaSubaffidatarie(List<Long> listaSubaffidatarie) {
		ListaSubaffidatarie = listaSubaffidatarie;
	}

	public Boolean getIsSubaffidataria() {
		return isSubaffidataria;
	}

	public void setIsSubaffidataria(Boolean isSubaffidataria) {
		this.isSubaffidataria = isSubaffidataria;
	}

	public Boolean getIsSubaffidatariaConsorziata() {
		return isSubaffidatariaConsorziata;
	}

	public void setIsSubaffidatariaConsorziata(Boolean isSubaffidatariaConsorziata) {
		this.isSubaffidatariaConsorziata = isSubaffidatariaConsorziata;
	}

}
