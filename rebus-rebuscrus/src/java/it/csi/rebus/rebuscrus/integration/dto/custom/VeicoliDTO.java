/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.dto.custom;

import java.util.Date;

//jira-144
public class VeicoliDTO {
	private Long idP;
	private String telaio;
	private String targa;
	private String marca;
	private String modello;
	private String classe;
	private String allestimento;
	private Double lunghezza;

	private String dataPrimaImmatricolazione;
	private String dataUltimaImmatricolazione;
	private String documento;
	private int idTipoDoc;
	private Long idVa;
	
	public Long getIdP() {
		return idP;
	}
	public void setIdP(Long idP) {
		this.idP = idP;
	}
	public String getTelaio() {
		return telaio;
	}
	public void setTelaio(String telaio) {
		this.telaio = telaio;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
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
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getAllestimento() {
		return allestimento;
	}
	public void setAllestimento(String allestimento) {
		this.allestimento = allestimento;
	}
	public Double getLunghezza() {
		return lunghezza;
	}
	public void setLunghezza(Double lunghezza) {
		this.lunghezza = lunghezza;
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
	public Long getIdVa() {
		return idVa;
	}
	public void setIdVa(Long idVa) {
		this.idVa = idVa;
	}

	

}
