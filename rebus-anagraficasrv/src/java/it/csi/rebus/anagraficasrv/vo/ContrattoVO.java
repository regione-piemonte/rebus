/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class ContrattoVO extends ParentVO {
	private Long idContratto;
	private Long idContrattoPadre;
	private String codIdNazionale;
	private String numRepertorio;
	private String codRegionale;
	private Long idSogGiuridCommittente;
	private Long idNaturaGiuridicaCommittente;
	private Long idSogGiuridEsecutore;
	private Long idTipoSogGiuridEsec;
	private Long idNaturaGiuridicaEsec;
	private Long idTipoRaggrSogGiuridEsec;
	private Long idTipoAffidamento;
	private Long idModalitaAffidamento;
	private String accordoProgramma;
	private Boolean grossCost;
	private String CIG;
	private Date dataStipula;
	private String descContratto;
	private Long idBacino;
	private Date dataInizioValidita;
	private Date dataFineValidita;
	private List<AmbTipServizioVO> ambTipServizio;
	private List<ContrattoRaggruppamentoVO> contrattoRaggruppamentoVOs;
	private List<ProrogaVO> proroghe;
	private List<SubentroSubaffidamentoVO> subentriSubaffidamenti;
	private List<AllegatoVO> allegati;
	private List<SoggettoCoinvoltoVO> soggettiCoinvolti;
	private List<SoggettoCoinvoltoPeriodiVO> soggettiCoinvoltiPeriodi;
	private Date dataAggiornamento;
	private Date dataFiltroSoggetto;

	public Long getIdContratto() {
		return idContratto;
	}

	public void setIdContratto(Long idContratto) {
		this.idContratto = idContratto;
	}

	public Long getIdContrattoPadre() {
		return idContrattoPadre;
	}

	public void setIdContrattoPadre(Long idContrattoPadre) {
		this.idContrattoPadre = idContrattoPadre;
	}

	public String getCodIdNazionale() {
		return codIdNazionale;
	}

	public void setCodIdNazionale(String codIdNazionale) {
		this.codIdNazionale = codIdNazionale;
	}

	public String getNumRepertorio() {
		return numRepertorio;
	}

	public void setNumRepertorio(String numRepertorio) {
		this.numRepertorio = numRepertorio;
	}

	public String getCodRegionale() {
		return codRegionale;
	}

	public void setCodRegionale(String codRegionale) {
		this.codRegionale = codRegionale;
	}

	public Long getIdSogGiuridCommittente() {
		return idSogGiuridCommittente;
	}

	public void setIdSogGiuridCommittente(Long idSogGiuridCommittente) {
		this.idSogGiuridCommittente = idSogGiuridCommittente;
	}

	public Long getIdNaturaGiuridicaCommittente() {
		return idNaturaGiuridicaCommittente;
	}

	public void setIdNaturaGiuridicaCommittente(Long idNaturaGiuridicaCommittente) {
		this.idNaturaGiuridicaCommittente = idNaturaGiuridicaCommittente;
	}

	public Long getIdSogGiuridEsecutore() {
		return idSogGiuridEsecutore;
	}

	public void setIdSogGiuridEsecutore(Long idSogGiuridEsecutore) {
		this.idSogGiuridEsecutore = idSogGiuridEsecutore;
	}

	public Long getIdTipoSogGiuridEsec() {
		return idTipoSogGiuridEsec;
	}

	public void setIdTipoSogGiuridEsec(Long idTipoSogGiuridEsec) {
		this.idTipoSogGiuridEsec = idTipoSogGiuridEsec;
	}

	public Long getIdNaturaGiuridicaEsec() {
		return idNaturaGiuridicaEsec;
	}

	public void setIdNaturaGiuridicaEsec(Long idNaturaGiuridicaEsec) {
		this.idNaturaGiuridicaEsec = idNaturaGiuridicaEsec;
	}

	public Long getIdTipoRaggrSogGiuridEsec() {
		return idTipoRaggrSogGiuridEsec;
	}

	public void setIdTipoRaggrSogGiuridEsec(Long idTipoRaggrSogGiuridEsec) {
		this.idTipoRaggrSogGiuridEsec = idTipoRaggrSogGiuridEsec;
	}

	public Long getIdTipoAffidamento() {
		return idTipoAffidamento;
	}

	public void setIdTipoAffidamento(Long idTipoAffidamento) {
		this.idTipoAffidamento = idTipoAffidamento;
	}

	public Long getIdModalitaAffidamento() {
		return idModalitaAffidamento;
	}

	public void setIdModalitaAffidamento(Long idModalitaAffidamento) {
		this.idModalitaAffidamento = idModalitaAffidamento;
	}

	public String getAccordoProgramma() {
		return accordoProgramma;
	}

	public void setAccordoProgramma(String accordoProgramma) {
		this.accordoProgramma = accordoProgramma;
	}

	public Boolean getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(Boolean grossCost) {
		this.grossCost = grossCost;
	}

	public String getCIG() {
		return CIG;
	}

	public void setCIG(String cIG) {
		CIG = cIG;
	}

	public Date getDataStipula() {
		return dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public String getDescContratto() {
		return descContratto;
	}

	public void setDescContratto(String descContratto) {
		this.descContratto = descContratto;
	}

	public Long getIdBacino() {
		return idBacino;
	}

	public void setIdBacino(Long idBacino) {
		this.idBacino = idBacino;
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

	/**
	 * @return the ambTipServizio
	 */
	public List<AmbTipServizioVO> getAmbTipServizio() {
		return ambTipServizio;
	}

	/**
	 * @param ambTipServizio
	 *            the ambTipServizio to set
	 */
	public void setAmbTipServizio(List<AmbTipServizioVO> ambTipServizio) {
		this.ambTipServizio = ambTipServizio;
	}

	public List<ContrattoRaggruppamentoVO> getContrattoRaggruppamentoVOs() {
		return contrattoRaggruppamentoVOs;
	}

	public void setContrattoRaggruppamentoVOs(List<ContrattoRaggruppamentoVO> contrattoRaggruppamentoVOs) {
		this.contrattoRaggruppamentoVOs = contrattoRaggruppamentoVOs;
	}

	/**
	 * @return the proroghe
	 */
	public List<ProrogaVO> getProroghe() {
		return proroghe;
	}

	/**
	 * @param proroghe
	 *            the proroghe to set
	 */
	public void setProroghe(List<ProrogaVO> proroghe) {
		this.proroghe = proroghe;
	}

	public List<SubentroSubaffidamentoVO> getSubentriSubaffidamenti() {
		return subentriSubaffidamenti;
	}

	public void setSubentriSubaffidamenti(List<SubentroSubaffidamentoVO> subentriSubaffidamenti) {
		this.subentriSubaffidamenti = subentriSubaffidamenti;
	}

	public List<AllegatoVO> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatoVO> allegati) {
		this.allegati = allegati;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public List<SoggettoCoinvoltoVO> getSoggettiCoinvolti() {
		return soggettiCoinvolti;
	}

	public void setSoggettiCoinvolti(List<SoggettoCoinvoltoVO> soggettiCoinvolti) {
		this.soggettiCoinvolti = soggettiCoinvolti;
	}

	public List<SoggettoCoinvoltoPeriodiVO> getSoggettiCoinvoltiPeriodi() {
		return soggettiCoinvoltiPeriodi;
	}

	public void setSoggettiCoinvoltiPeriodi(List<SoggettoCoinvoltoPeriodiVO> soggettiCoinvoltiPeriodi) {
		this.soggettiCoinvoltiPeriodi = soggettiCoinvoltiPeriodi;
	}

	public Date getDataFiltroSoggetto() {
		return dataFiltroSoggetto;
	}

	public void setDataFiltroSoggetto(Date dataFiltroSoggetto) {
		this.dataFiltroSoggetto = dataFiltroSoggetto;
	}

}
