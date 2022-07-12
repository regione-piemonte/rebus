/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class EmissioniVO extends ParentVO {
    private Long idTipoAllestimento;
    private Long idMisurazione;
    private Double kmPercorsi;

    private Double  emissioniCo2;
    private Double  emissioniNox;
    private Double  emissioniPm10Numero;
    private Double  emissioniPm10Peso;
    
    //private String descrizione;
    

	public Long getIdMisurazione() {
		return idMisurazione;
	}
	public void setIdMisurazione(Long idMisurazione) {
		this.idMisurazione = idMisurazione;
	}
	public Double getKmPercorsi() {
		return kmPercorsi;
	}
	public void setKmPercorsi(Double kmPercorsi) {
		this.kmPercorsi = kmPercorsi;
	}
	public Double getEmissioniCo2() {
		return emissioniCo2;
	}
	public void setEmissioniCo2(Double emissioniCo2) {
		this.emissioniCo2 = emissioniCo2;
	}
	public Double getEmissioniNox() {
		return emissioniNox;
	}
	public void setEmissioniNox(Double emissioniNox) {
		this.emissioniNox = emissioniNox;
	}
	public Double getEmissioniPm10Numero() {
		return emissioniPm10Numero;
	}
	public void setEmissioniPm10Numero(Double emissioniPm10Numero) {
		this.emissioniPm10Numero = emissioniPm10Numero;
	}
	public Double getEmissioniPm10Peso() {
		return emissioniPm10Peso;
	}
	public void setEmissioniPm10Peso(Double emissioniPm10Peso) {
		this.emissioniPm10Peso = emissioniPm10Peso;
	}
	public Long getIdTipoAllestimento() {
		return idTipoAllestimento;
	}
	public void setIdTipoAllestimento(Long idTipoAllestimento) {
		this.idTipoAllestimento = idTipoAllestimento;
	}
	/*
	 * public String getDescrizione() { return descrizione; } public void
	 * setDescrizione(String descrizione) { this.descrizione = descrizione; }
	 */
    
}
