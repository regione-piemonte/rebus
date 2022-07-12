/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class TransizioneAutomaVO extends ParentVO {
	private Long idTransizioneAutoma;
	private Long idStatoIterDa;
	private Long idStatoIterA;
	private String titolo;
	private String label;
	private String testo;
	private String condizioni;
	private Boolean flgNoteObbligatorie;
	private Long idTipoMessaggio;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private Boolean flagDefault;
	private Integer returnTransizione; // campo utilizzato nella rendicontazione per mostrare o non mostrare la parte dell'iter

	public Long getIdTransizioneAutoma() {
		return idTransizioneAutoma;
	}

	public void setIdTransizioneAutoma(Long idTransizioneAutoma) {
		this.idTransizioneAutoma = idTransizioneAutoma;
	}

	public Long getIdStatoIterDa() {
		return idStatoIterDa;
	}

	public void setIdStatoIterDa(Long idStatoIterDa) {
		this.idStatoIterDa = idStatoIterDa;
	}

	public Long getIdStatoIterA() {
		return idStatoIterA;
	}

	public void setIdStatoIterA(Long idStatoIterA) {
		this.idStatoIterA = idStatoIterA;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getCondizioni() {
		return condizioni;
	}

	public void setCondizioni(String condizioni) {
		this.condizioni = condizioni;
	}

	public Boolean getFlgNoteObbligatorie() {
		return flgNoteObbligatorie;
	}

	public void setFlgNoteObbligatorie(Boolean flgNoteObbligatorie) {
		this.flgNoteObbligatorie = flgNoteObbligatorie;
	}

	public Long getIdTipoMessaggio() {
		return idTipoMessaggio;
	}

	public void setIdTipoMessaggio(Long idTipoMessaggio) {
		this.idTipoMessaggio = idTipoMessaggio;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Boolean getFlagDefault() {
		return flagDefault;
	}

	public void setFlagDefault(Boolean flagDefault) {
		this.flagDefault = flagDefault;
	}

	public Integer getReturnTransizione() {
		return returnTransizione;
	}

	public void setReturnTransizione(Integer returnTransizione) {
		this.returnTransizione = returnTransizione;
	}

}
