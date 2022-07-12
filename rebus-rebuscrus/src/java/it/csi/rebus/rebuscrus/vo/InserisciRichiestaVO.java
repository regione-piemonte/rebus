/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

@SuppressWarnings("serial")
public class InserisciRichiestaVO extends ParentVO {
	
	private TipoProcedimentoVO tipoProcedimento;
	private List<VeicoloVO> veicoli;
	private MotorizzazioneVO motorizzazione;
	private MotivazioneVO motivazione;
	private String noteMotivazione;
	private String nota;
	private String nominativoFirma;
	private String ruoloFirma;
	private ContrattoProcVO contratto;
	private List<AllegatoProcVO> files;
	private String ruoloFirmaEnte;
	private String nominativoFirmaEnte;
	private String premesse;
	private String prescrizioni;
	private List<VoceDiCostoVO> vociDiCosto;
	private Long numProcedimento;
	private Boolean flgFirmaDigitale;
	private Boolean flgAllegaLinea;

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

	public MotorizzazioneVO getMotorizzazione() {
		return motorizzazione;
	}

	public void setMotorizzazione(MotorizzazioneVO motorizzazione) {
		this.motorizzazione = motorizzazione;
	}

	public MotivazioneVO getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(MotivazioneVO motivazione) {
		this.motivazione = motivazione;
	}

	public String getNoteMotivazione() {
		return noteMotivazione;
	}

	public void setNoteMotivazione(String noteMotivazione) {
		this.noteMotivazione = noteMotivazione;
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

	public ContrattoProcVO getContratto() {
		return contratto;
	}

	public void setContratto(ContrattoProcVO contratto) {
		this.contratto = contratto;
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

	public List<VoceDiCostoVO> getVociDiCosto() {
		return vociDiCosto;
	}

	public void setVociDiCosto(List<VoceDiCostoVO> vociDiCosto) {
		this.vociDiCosto = vociDiCosto;
	}

	public Long getNumProcedimento() {
		return numProcedimento;
	}

	public void setNumProcedimento(Long numProcedimento) {
		this.numProcedimento = numProcedimento;
	}

	public Boolean getFlgFirmaDigitale() {
		return flgFirmaDigitale;
	}

	public void setFlgFirmaDigitale(Boolean flgFirmaDigitale) {
		this.flgFirmaDigitale = flgFirmaDigitale;
	}

	public Boolean getFlgAllegaLinea() {
		return flgAllegaLinea;
	}

	public void setFlgAllegaLinea(Boolean flgAllegaLinea) {
		this.flgAllegaLinea = flgAllegaLinea;
	}
}
