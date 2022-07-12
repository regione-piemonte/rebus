/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

@SuppressWarnings("serial")
public class DettaglioRichiestaVO extends ParentVO {
	private Long id;
	private Long idTipoProcedimento;
	private List<VeicoloVO> veicoli;
	private Long idMotorizzazione;
	private Long idMotivazione;
	private String noteMotivazione;
	private String nota;
	private ContrattoProcVO contratto;
	private List<AllegatoProcVO> files;
	private List<IterProcedimentoVO> iters;
	private String ruoloFirma;
	private String nominativoFirma;
	private SubProcedimentoVO subProcedimento;
	private Long scenarioScaricaPDF;
	private Boolean isACaricoEnteVisible;
	private String ruoloFirmaEnte;
	private String nominativoFirmaEnte;
	private String premesse;
	private String prescrizioni;
	private List<VoceDiCostoVeicoloVO> vociDiCostoVeicolo;
	private String numProgressivo;
	private List<ContrattoProcVO> contratti;
	private Boolean flgFirmaDigitaleEnte; 
	private Boolean flgFirmaDigitale; 
	private Boolean flgAllegaLinea; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTipoProcedimento() {
		return idTipoProcedimento;
	}

	public void setIdTipoProcedimento(Long idTipoProcedimento) {
		this.idTipoProcedimento = idTipoProcedimento;
	}

	public List<VeicoloVO> getVeicoli() {
		return veicoli;
	}

	public void setVeicoli(List<VeicoloVO> veicoli) {
		this.veicoli = veicoli;
	}

	public Long getIdMotorizzazione() {
		return idMotorizzazione;
	}

	public void setIdMotorizzazione(Long idMotorizzazione) {
		this.idMotorizzazione = idMotorizzazione;
	}

	public Long getIdMotivazione() {
		return idMotivazione;
	}

	public void setIdMotivazione(Long idMotivazione) {
		this.idMotivazione = idMotivazione;
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

	public List<IterProcedimentoVO> getIters() {
		return iters;
	}

	public void setIters(List<IterProcedimentoVO> iters) {
		this.iters = iters;
	}

	public String getRuoloFirma() {
		return ruoloFirma;
	}

	public void setRuoloFirma(String ruoloFirma) {
		this.ruoloFirma = ruoloFirma;
	}

	public String getNominativoFirma() {
		return nominativoFirma;
	}

	public void setNominativoFirma(String nominativoFirma) {
		this.nominativoFirma = nominativoFirma;
	}

	public SubProcedimentoVO getSubProcedimento() {
		return subProcedimento;
	}

	public void setSubProcedimento(SubProcedimentoVO subProcedimento) {
		this.subProcedimento = subProcedimento;
	}

	public Long getScenarioScaricaPDF() {
		return scenarioScaricaPDF;
	}

	public void setScenarioScaricaPDF(Long scenarioScaricaPDF) {
		this.scenarioScaricaPDF = scenarioScaricaPDF;
	}

	public Boolean getIsACaricoEnteVisible() {
		return isACaricoEnteVisible;
	}

	public void setIsACaricoEnteVisible(Boolean isACaricoEnteVisible) {
		this.isACaricoEnteVisible = isACaricoEnteVisible;
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

	public List<VoceDiCostoVeicoloVO> getVociDiCostoVeicolo() {
		return vociDiCostoVeicolo;
	}

	public void setVociDiCostoVeicolo(List<VoceDiCostoVeicoloVO> vociDiCostoVeicolo) {
		this.vociDiCostoVeicolo = vociDiCostoVeicolo;
	}

	public String getNumProgressivo() {
		return numProgressivo;
	}

	public void setNumProgressivo(String numProgressivo) {
		this.numProgressivo = numProgressivo;
	}

	public List<ContrattoProcVO> getContratti() {
		return contratti;
	}

	public void setContratti(List<ContrattoProcVO> contratti) {
		this.contratti = contratti;
	}

	public Boolean getFlgFirmaDigitaleEnte() {
		return flgFirmaDigitaleEnte;
	}

	public void setFlgFirmaDigitaleEnte(Boolean flgFirmaDigitaleEnte) {
		this.flgFirmaDigitaleEnte = flgFirmaDigitaleEnte;
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
