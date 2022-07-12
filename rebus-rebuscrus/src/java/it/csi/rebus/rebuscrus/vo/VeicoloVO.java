/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.List;

@SuppressWarnings("serial")
public class VeicoloVO extends ParentVO {
	private Long idVariazAutobus;
	private Long idProcedimento;
	private String primoTelaio;
	private String nTarga;
	private String descClasseAmbEuro;
	private String descTipoAllestimento;
	private Double lunghezza;
	private String marca;
	private String modello;
	private String dataPrimaImmatricolazione;
	private String dataUltimaImmatricolazione;
	private List<AllegatoVeicoloVO> allegati;
	private boolean selected;
	private String documento;
	private int idTipoDoc;

	public Long getIdVariazAutobus() {
		return idVariazAutobus;
	}

	public void setIdVariazAutobus(Long idVariazAutobus) {
		this.idVariazAutobus = idVariazAutobus;
	}

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio;
	}

	public String getnTarga() {
		return nTarga;
	}

	public void setnTarga(String nTarga) {
		this.nTarga = nTarga;
	}

	public String getDescClasseAmbEuro() {
		return descClasseAmbEuro;
	}

	public void setDescClasseAmbEuro(String descClasseAmbEuro) {
		this.descClasseAmbEuro = descClasseAmbEuro;
	}

	public String getDescTipoAllestimento() {
		return descTipoAllestimento;
	}

	public void setDescTipoAllestimento(String descTipoAllestimento) {
		this.descTipoAllestimento = descTipoAllestimento;
	}

	public Double getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Double lunghezza) {
		this.lunghezza = lunghezza;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getDataPrimaImmatricolazione() {
		return dataPrimaImmatricolazione;
	}

	public void setDataPrimaImmatricolazione(String dataPrimaImmatricolazione) {
		this.dataPrimaImmatricolazione = dataPrimaImmatricolazione;
	}

	public String getDataUltimaImmatricolazione() {
		return dataUltimaImmatricolazione;
	}

	public void setDataUltimaImmatricolazione(String dataUltimaImmatricolazione) {
		this.dataUltimaImmatricolazione = dataUltimaImmatricolazione;
	}

	/**
	 * @return the allegati
	 */
	public List<AllegatoVeicoloVO> getAllegati() {
		return allegati;
	}

	/**
	 * @param allegati
	 *            the allegati to set
	 */
	public void setAllegati(List<AllegatoVeicoloVO> allegati) {
		this.allegati = allegati;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public int getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(int idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
}
