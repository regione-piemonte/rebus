/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;
import java.util.List;

public class DatoFatturaVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDatoFattura;
	private Long idDatoSpesa;
	private Long idDocContribuzione;
	private String numero;
	private Date data;
	private Double importo;
	private String nrQuietanzaAzForn;
	private Date dataQuietanzaAzForn;
	private Double idTipoDocQuietanza;
	private DocContribuzioneVO documento;
	private List<Long> listaIdVoceCosto;
	private List<DatoBonificoVO> listaBonifici;
	
	

	public Long getIdDatoFattura() {
		return idDatoFattura;
	}

	public void setIdDatoFattura(Long idDatoFattura) {
		this.idDatoFattura = idDatoFattura;
	}

	public Long getIdDatoSpesa() {
		return idDatoSpesa;
	}

	public void setIdDatoSpesa(Long idDatoSpesa) {
		this.idDatoSpesa = idDatoSpesa;
	}

	public Long getIdDocContribuzione() {
		return idDocContribuzione;
	}

	public void setIdDocContribuzione(Long idDocContribuzione) {
		this.idDocContribuzione = idDocContribuzione;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public String getNrQuietanzaAzForn() {
		return nrQuietanzaAzForn;
	}

	public void setNrQuietanzaAzForn(String nrQuietanzaAzForn) {
		this.nrQuietanzaAzForn = nrQuietanzaAzForn;
	}

	public Date getDataQuietanzaAzForn() {
		return dataQuietanzaAzForn;
	}

	public void setDataQuietanzaAzForn(Date dataQuietanzaAzForn) {
		this.dataQuietanzaAzForn = dataQuietanzaAzForn;
	}

	public Double getIdTipoDocQuietanza() {
		return idTipoDocQuietanza;
	}

	public void setIdTipoDocQuietanza(Double idTipoDocQuietanza) {
		this.idTipoDocQuietanza = idTipoDocQuietanza;
	}

	public DocContribuzioneVO getDocumento() {
		return documento;
	}

	public void setDocumento(DocContribuzioneVO documento) {
		this.documento = documento;
	}

	public List<Long> getListaIdVoceCosto() {
		return listaIdVoceCosto;
	}

	public void setListaIdVoceCosto(List<Long> listaIdVoceCosto) {
		this.listaIdVoceCosto = listaIdVoceCosto;
	}
	
	public List<DatoBonificoVO> getListaBonifici() {
		return listaBonifici;
	}

	public void setListaBonifici(List<DatoBonificoVO> listaBonifici) {
		this.listaBonifici = listaBonifici;
	}

	public boolean isPresentIn(List<DatoFatturaVO> list) {
		boolean result = false;
		if (idDatoFattura == null && idDatoSpesa == null) {
			return false;
		}
		for (DatoFatturaVO datoFatturaVO : list) {
			if (idDatoFattura.equals(datoFatturaVO.getIdDatoFattura())
					&& idDatoSpesa.equals(datoFatturaVO.getIdDatoSpesa())
					&& idDocContribuzione.equals(datoFatturaVO.getIdDocContribuzione())) {
				result = true;
				break;
			}
		}
		return result;
	}
}
