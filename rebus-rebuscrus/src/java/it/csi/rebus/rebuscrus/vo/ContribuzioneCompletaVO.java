/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

public class ContribuzioneCompletaVO {

	private AssegnazioneRisorseVO assegnazioneRisorse;
	private OrdineAcquistoVO ordineAcquisto;
	private CostoFornituraVO costoFornitura;
	private List<VoceCostoFornituraVO> vociCosto;
	private DatoSpesaVO datoSpesa;
	private DatoMessaServizioVO datoMessaServizio;
	private ContribuzioneVO contribuzione;
	private List<DatoFatturaVO> datiFattura;

	// DOCUMENTI
	private DocContribuzioneVO documentoContribuzione;
	private DocContribuzioneVO documentoCartaCircolazione;
	private DocContribuzioneVO documentoAttoObbligo;
	private DocContribuzioneVO documentoGaranzia;
	private DocContribuzioneVO documentoAlienazione;
	private DocContribuzioneVO documentoMisureEmissioni;

	public AssegnazioneRisorseVO getAssegnazioneRisorse() {
		return assegnazioneRisorse;
	}

	public void setAssegnazioneRisorse(AssegnazioneRisorseVO assegnazioneRisorse) {
		this.assegnazioneRisorse = assegnazioneRisorse;
	}

	public OrdineAcquistoVO getOrdineAcquisto() {
		return ordineAcquisto;
	}

	public void setOrdineAcquisto(OrdineAcquistoVO ordineAcquisto) {
		this.ordineAcquisto = ordineAcquisto;
	}

	public CostoFornituraVO getCostoFornitura() {
		return costoFornitura;
	}

	public void setCostoFornitura(CostoFornituraVO costoFornitura) {
		this.costoFornitura = costoFornitura;
	}

	public List<VoceCostoFornituraVO> getVociCosto() {
		return vociCosto;
	}

	public void setVociCosto(List<VoceCostoFornituraVO> vociCosto) {
		this.vociCosto = vociCosto;
	}

	public DatoSpesaVO getDatoSpesa() {
		return datoSpesa;
	}

	public void setDatoSpesa(DatoSpesaVO datoSpesa) {
		this.datoSpesa = datoSpesa;
	}

	public DatoMessaServizioVO getDatoMessaServizio() {
		return datoMessaServizio;
	}

	public void setDatoMessaServizio(DatoMessaServizioVO datoMessaServizio) {
		this.datoMessaServizio = datoMessaServizio;
	}

	public ContribuzioneVO getContribuzione() {
		return contribuzione;
	}

	public void setContribuzione(ContribuzioneVO contribuzione) {
		this.contribuzione = contribuzione;
	}

	public DocContribuzioneVO getDocumentoContribuzione() {
		return documentoContribuzione;
	}

	public void setDocumentoContribuzione(DocContribuzioneVO documentoContribuzione) {
		this.documentoContribuzione = documentoContribuzione;
	}

	public DocContribuzioneVO getDocumentoCartaCircolazione() {
		return documentoCartaCircolazione;
	}

	public void setDocumentoCartaCircolazione(DocContribuzioneVO documentoCartaCircolazione) {
		this.documentoCartaCircolazione = documentoCartaCircolazione;
	}

	public DocContribuzioneVO getDocumentoAttoObbligo() {
		return documentoAttoObbligo;
	}

	public void setDocumentoAttoObbligo(DocContribuzioneVO documentoAttoObbligo) {
		this.documentoAttoObbligo = documentoAttoObbligo;
	}

	public DocContribuzioneVO getDocumentoGaranzia() {
		return documentoGaranzia;
	}

	public void setDocumentoGaranzia(DocContribuzioneVO documentoGaranzia) {
		this.documentoGaranzia = documentoGaranzia;
	}

	public DocContribuzioneVO getDocumentoAlienazione() {
		return documentoAlienazione;
	}

	public void setDocumentoAlienazione(DocContribuzioneVO documentoAlienazione) {
		this.documentoAlienazione = documentoAlienazione;
	}

	public List<DatoFatturaVO> getDatiFattura() {
		return datiFattura;
	}

	public void setDatiFattura(List<DatoFatturaVO> datiFattura) {
		this.datiFattura = datiFattura;
	}

	public DocContribuzioneVO getDocumentoMisureEmissioni() {
		return documentoMisureEmissioni;
	}

	public void setDocumentoMisureEmissioni(DocContribuzioneVO documentoMisureEmissioni) {
		this.documentoMisureEmissioni = documentoMisureEmissioni;
	}

}
