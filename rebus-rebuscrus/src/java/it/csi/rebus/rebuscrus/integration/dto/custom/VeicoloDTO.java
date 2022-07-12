/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto.custom;

import java.util.Date;

public class VeicoloDTO {
	private Long idVariazAutobus;
	private String primoTelaio;
	private String nTarga;
	private String descClasseAmbEuro;
	private String descTipoAllestimento;
	private Double lunghezza;
	private String marca;
	private String modello;
	private String dataPrimaImmatricolazione;
	private String dataUltimaImmatricolazione;
	private String idTipoDocumento;

	public Long getIdVariazAutobus() {
		return idVariazAutobus;
	}

	public void setIdVariazAutobus(Long idVariazAutobus) {
		this.idVariazAutobus = idVariazAutobus;
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

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

}
