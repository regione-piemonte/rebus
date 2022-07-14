/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import it.csi.rebus.anagraficasrv.integration.dto.SirtplcRContrattoRaggruppDTO;

@SuppressWarnings("serial")
public class ContrattoRaggruppamentoVO extends ParentVO {
	private Long idContrattoRaggruppamento;
	private Long idContratto;
	private Long idSoggettoGiuridico;
	private Boolean capofila;

	public Long getIdContrattoRaggruppamento() {
		return idContrattoRaggruppamento;
	}

	public void setIdContrattoRaggruppamento(Long idContrattoRaggruppamento) {
		this.idContrattoRaggruppamento = idContrattoRaggruppamento;
	}

	public Long getIdContratto() {
		return idContratto;
	}

	public void setIdContratto(Long idContratto) {
		this.idContratto = idContratto;
	}

	public Long getIdSoggettoGiuridico() {
		return idSoggettoGiuridico;
	}

	public void setIdSoggettoGiuridico(Long idSoggettoGiuridico) {
		this.idSoggettoGiuridico = idSoggettoGiuridico;
	}

	public Boolean getCapofila() {
		return capofila;
	}

	public void setCapofila(Boolean capofila) {
		this.capofila = capofila;
	}
	
	public boolean equals(SirtplcRContrattoRaggruppDTO obj) {
		
		if (obj == null) {
            return false;
        }

        if(this.idContratto!=obj.getIdContratto() || this.capofila!=obj.getCapofila() || !this.getIdSoggettoGiuridico().equals(obj.getIdSoggettoGiuridico())) {
        	return false;
        }
        
        return true;
		
	}
	
	

}
