/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

@SuppressWarnings("serial")
public class InserisciRichiestaUsoInLineaVO extends ParentVO {
	private TipoProcedimentoVO tipoProcedimento;
	private List<VeicoloVO> veicoli;
	private String nota;
	private String nominativoFirma;
	private String ruoloFirma;
	private Boolean flgFirmaDigitale;
	private List<AllegatoProcVO> files;
	private List<UtilizzoVO> utilizzi;
	private String ruoloFirmaEnte;
	private String nominativoFirmaEnte;
	private String premesse;
	private String prescrizioni;
	private Long numProcedimento;

	/**
	 * @return the tipoProcedimento
	 */
	public TipoProcedimentoVO getTipoProcedimento() {
		return tipoProcedimento;
	}

	/**
	 * @param tipoProcedimento
	 *            the tipoProcedimento to set
	 */
	public void setTipoProcedimento(TipoProcedimentoVO tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public List<VeicoloVO> getVeicoli() {
		return veicoli;
	}

	public void setVeicoli(List<VeicoloVO> veicoli) {
		this.veicoli = veicoli;
	}

	
	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getNominativoFirma() {
		return nominativoFirma;
	}

	public void setNominativoFirma(String nominativoFirma) {
		this.nominativoFirma = nominativoFirma;
	}

	public String getRuoloFirma() {
		return ruoloFirma;
	}

	public void setRuoloFirma(String ruoloFirma) {
		this.ruoloFirma = ruoloFirma;
	}


	public List<AllegatoProcVO> getFiles() {
		return files;
	}

	public void setFiles(List<AllegatoProcVO> files) {
		this.files = files;
	}

	public String getRuoloFirmaEnte() {
		return ruoloFirmaEnte;
	}

	public void setRuoloFirmaEnte(String ruoloFirmaEnte) {
		this.ruoloFirmaEnte = ruoloFirmaEnte;
	}

	public String getNominativoFirmaEnte() {
		return nominativoFirmaEnte;
	}

	public void setNominativoFirmaEnte(String nominativoFirmaEnte) {
		this.nominativoFirmaEnte = nominativoFirmaEnte;
	}

	public String getPremesse() {
		return premesse;
	}

	public void setPremesse(String premesse) {
		this.premesse = premesse;
	}

	public String getPrescrizioni() {
		return prescrizioni;
	}

	public void setPrescrizioni(String prescrizioni) {
		this.prescrizioni = prescrizioni;
	}


	public Long getNumProcedimento() {
		return numProcedimento;
	}

	public void setNumProcedimento(Long numProcedimento) {
		this.numProcedimento = numProcedimento;
	}

	public List<UtilizzoVO> getUtilizzi() {
		return utilizzi;
	}

	public void setUtilizzi(List<UtilizzoVO> utilizzi) {
		this.utilizzi = utilizzi;
	}

	public Boolean getFlgFirmaDigitale() {
		return flgFirmaDigitale;
	}

	public void setFlgFirmaDigitale(Boolean flgFirmaDigitale) {
		this.flgFirmaDigitale = flgFirmaDigitale;
	}
}
