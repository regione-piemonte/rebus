/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.security;

import java.util.List;

import it.csi.rebus.rebuscrus.integration.iride.Identita;
import it.csi.rebus.rebuscrus.integration.iride.Ruolo;
import it.csi.rebus.rebuscrus.integration.iride.UseCase;
import it.csi.rebus.rebuscrus.vo.AziendaVO;

public class UserInfo implements java.io.Serializable {

	private static final long serialVersionUID = -7999400417528768575L;

	private String nome;
	private String cognome;
	private String codFisc;
	private String ente;
	private String aziendaDesc;
	private String idIride;
	private Long idFunzionario;
	private Long idEnte;
	private Long idAzienda;
	private Long idUtente;
	private Ruolo ruolo;
	private List<String> authority;
	private List<AziendaVO> aziende;
	private List<Ruolo> ruoli;
	private List<UseCase> usecase;
	private Identita identita;

	public Long getIdFunzionario() {
		return idFunzionario;
	}

	public void setIdFunzionario(Long idFunzionario) {
		this.idFunzionario = idFunzionario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getIdIride() {
		return idIride;
	}

	public void setIdIride(String idIride) {
		this.idIride = idIride;
	}

	public String getAziendaDesc() {
		return aziendaDesc;
	}

	public void setAziendaDesc(String aziendaDesc) {
		this.aziendaDesc = aziendaDesc;
	}

	public Long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Long idEnte) {
		this.idEnte = idEnte;
	}

	public Long getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Long idAzienda) {
		this.idAzienda = idAzienda;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public List<String> getAuthority() {
		return authority;
	}

	public void setAuthority(List<String> authority) {
		this.authority = authority;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public List<AziendaVO> getAziende() {
		return aziende;
	}

	public void setAziende(List<AziendaVO> aziende) {
		this.aziende = aziende;
	}

	public List<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public List<UseCase> getUsecase() {
		return usecase;
	}

	public void setUsecase(List<UseCase> usecase) {
		this.usecase = usecase;
	}

	public Identita getIdentita() {
		return identita;
	}

	public void setIdentita(Identita identita) {
		this.identita = identita;
	}

}