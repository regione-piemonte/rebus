/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

@SuppressWarnings("serial")
public class PortabiciVO extends ParentVO {

    private Long idPortabici;
    private Double idMisurazione;
    private Boolean flagSensoristicaPb;
  
    private String mesiUtilizzoPrevalente;
    private String giorniUtilizzoPrevalente;
    private Double numBiciTrasportabili;
    private Long totBiciTrasportate;

    
    private String desc_portabici;
    
	public Long getIdPortabici() {
		return idPortabici;
	}
	public void setIdPortabici(Long idPortabici) {
		this.idPortabici = idPortabici;
	}
	public Double getIdMisurazione() {
		return idMisurazione;
	}
	public void setIdMisurazione(Double idMisurazione) {
		this.idMisurazione = idMisurazione;
	}
	public Boolean getFlagSensoristicaPb() {
		return flagSensoristicaPb;
	}
	public void setFlagSensoristicaPb(Boolean flagSensoristicaPb) {
		this.flagSensoristicaPb = flagSensoristicaPb;
	}
	public String getMesiUtilizzoPrevalente() {
		return mesiUtilizzoPrevalente;
	}
	public void setMesiUtilizzoPrevalente(String mesiUtilizzoPrevalente) {
		this.mesiUtilizzoPrevalente = mesiUtilizzoPrevalente;
	}
	public String getGiorniUtilizzoPrevalente() {
		return giorniUtilizzoPrevalente;
	}
	public void setGiorniUtilizzoPrevalente(String giorniUtilizzoPrevalente) {
		this.giorniUtilizzoPrevalente = giorniUtilizzoPrevalente;
	}
	public Double getNumBiciTrasportabili() {
		return numBiciTrasportabili;
	}
	public void setNumBiciTrasportabili(Double numBiciTrasportabili) {
		this.numBiciTrasportabili = numBiciTrasportabili;
	}
	public Long getTotBiciTrasportate() {
		return totBiciTrasportate;
	}
	public void setTotBiciTrasportate(Long totBiciTrasportate) {
		this.totBiciTrasportate = totBiciTrasportate;
	}
	public String getDesc_portabici() {
		return desc_portabici;
	}
	public void setDesc_portabici(String desc_portabici) {
		this.desc_portabici = desc_portabici;
	}
   




}
