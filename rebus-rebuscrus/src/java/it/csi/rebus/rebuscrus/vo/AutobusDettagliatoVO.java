/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.Date;
import java.util.List;

public class AutobusDettagliatoVO {

	// Dati identificativi e d'immatricolazione
	public Long idAzienda;
	public String telaio;
	public Long idTipoImmatricolazione;
	public String primoTelaio;
	public Date dataPrimaImmatricolazione;
	public String targa;
	public Boolean daImmatricolare;
	public String enteAutorizzPrimaImm;
	public String matricola;
	public Date dataUltimaImmatricolazione;

	// Caratteristiche Fisiche e Tecnologiche
	public String marca;
	public Long idTipoAllestimento;
	public String modello;
	public Long idTipoAlimentazione;
	public String omologazioneDirettivaEuropea;
	public String altraAlimentazione;
	public Long idClasseAmbientale;
	public String caratteristicheParticolari;
	public Long idClasseVeicolo;
	public Double lunghezza;
	public Boolean flgDuePiani;
	public Boolean flgSnodato;
	public Boolean flgCabinaGuidaIsolata;
	public Long numeroPorte;
	public Long nPostiInPiedi;
	public Long nPostiSedere;
	public Long nPostiRiservati;
	public Long postiCarrozzina;

	// Dotazioni Specifiche
	public Long idDotazioneDisabili;
	public List<Long> idAltriDispositiviPrevInc;
	public Long idImpiantiAudio;
	public Long idImpiantiVisivi;
	public String altriDispositiviPrevenzInc;
	public Boolean flgImpiantoCondizionamento;
	public Boolean flgRilevatoreBip;
	public Boolean flgContapasseggeri;
	public Boolean flgOtx;
	public Boolean flgAvm;
	public Boolean flgFiltroFap;

	// Dati amministrativi ed Economici
	public Long idDeposito;
	public Double prezzoTotAcquisto;
	public Long idProprietaLeasing;
	public Double contributoPubblicoAcquisto;
	public Date dataUltimaRevisione;
	public String tipologiaDimensionale;
	public Boolean flgVeicoloAssicurato;
	public Boolean flgConteggiatoMiv;
	public Boolean flgRichiestaContr;
	public Boolean flagAlienato;
	public String flgAlienato;
	public Date dataAlienazione;
	public String dataScadVincoliNoAlien;
	public String annoSostProg;

	// Verifica e Note
	public Boolean flagVerificaAzienda;
	public String notaRiservataAzienda;
	public Boolean flagVerificaAmp;
	public String notaRiservataAmp;
	public String note;

	// documenti allegati
	public Long idDocumento;
	public String noteDocumento;
	public byte[] file;
	public Date dataCaricamentoDoc;
    public String nomeFile;
    public List<DocVariazAutobusVO> documentiAutobus;

	/*
	 * DA CONTROLLARE
	 */
	private Double fkPortabici;
	private Double fkSistemaVideosorveglianza;
	private Double fkSistemaLocalizzazione;
	private Boolean flgBipCablato;
	private Boolean flgContapasseggeriIntegrato;
	private Boolean flgSistemiProtezioneAutista;
	private String altriAllestimenti;
	private Boolean flgContribuzione;
	
	private Long idCategoriaVeicolo;
	private String categoriaVeicolo;
	private String statoTpl;

	@Override
	public String toString() {
		return "idAzienda: " + idAzienda + "	 telaio: " + telaio + "	 idTipoImmatricolazione: "
				+ idTipoImmatricolazione + "	 primoTelaio: " + primoTelaio + "	 dataPrimaImmatricolazione: "
				+ dataPrimaImmatricolazione + "	 targa: " + targa + "	 enteAutorizzPrimaImm: " + enteAutorizzPrimaImm
				+ "	 matricola: " + matricola + "	 dataUltimaImmatricolazione: " + dataUltimaImmatricolazione
				+ "	 marca: " + marca + "	 idTipoAllestimento: " + idTipoAllestimento + "	 modello: " + modello
				+ "	 idTipoAlimentazione: " + idTipoAlimentazione + "	 omologazioneDirettivaEuropea: "
				+ omologazioneDirettivaEuropea + "	 altraAlimentazione: " + altraAlimentazione
				+ "	 idClasseAmbientale: " + idClasseAmbientale + "	 caratteristicheParticolari: "
				+ caratteristicheParticolari + "	 idClasseVeicolo: " + idClasseVeicolo + "	 lunghezza: "
				+ lunghezza + "	 flgDuePiani: " + flgDuePiani + "	 flgSnodato: " + flgSnodato
				+ "	 flgCabinaGuidaIsolata: " + flgCabinaGuidaIsolata + "	 numeroPorte: " + numeroPorte
				+ "	 nPostiInPiedi: " + nPostiInPiedi + "	 nPostiSedere: " + nPostiSedere + "	 nPostiRiservati: "
				+ nPostiRiservati + "	 postiCarrozzina: " + postiCarrozzina + "	 idDotazioneDisabili: "
				+ idDotazioneDisabili + "	 idAltriDispositiviPrevInc: " + "	 idImpiantiAudio: " + idImpiantiAudio
				+ "	 idImpiantiVisivi: " + idImpiantiVisivi + "	 altriDispositiviPrevenzInc: "
				+ altriDispositiviPrevenzInc + "	 flgImpiantoCondizionamento: " + flgImpiantoCondizionamento
				+ "	 flgRilevatoreBip: " + flgRilevatoreBip + "	 flgContapasseggeri: " + flgContapasseggeri
				+ "	 flgOtx: " + flgOtx + "	 flgAvm: " + flgAvm + "	 flgFiltroFap: " + flgFiltroFap + "	 idDepositoe: "
				+ idDeposito + "	 prezzoTotAcquisto: " + prezzoTotAcquisto + "	 idProprietaLeasing: "
				+ idProprietaLeasing + "	 contributoPubblicoAcquisto: " + contributoPubblicoAcquisto
				+ "	 dataUltimaRevisione: " + dataUltimaRevisione + "	 tipologiaDimensionale: "
				+ tipologiaDimensionale + "	 flgVeicoloAssicurato: " + flgVeicoloAssicurato + "	 flgConteggiatoMiv: "
				+ flgConteggiatoMiv + "	 flgRichiestaContr: " + flgRichiestaContr + "	 flagAlienato: " + flagAlienato
				+ "	 dataAlienazione: " + dataAlienazione + "	 dataScadVincoliNoAlien: " + dataScadVincoliNoAlien
				+ "	 annoSostProg: " + annoSostProg + "	 flagVerificaAzienda: " + flagVerificaAzienda
				+ "	 notaRiservataAzienda: " + notaRiservataAzienda + "	 flagVerificaAmp: " + flagVerificaAmp
				+ "	 notaRiservataAmp: " + notaRiservataAmp + "	 note: " + note + "	 idDocumento: " + idDocumento
				+ "	 noteDocumento: " + noteDocumento + file + "	 file: ";

	}

	public List<Long> getIdAltriDispositiviPrevInc() {
		return idAltriDispositiviPrevInc;
	}

	public void setIdAltriDispositiviPrevInc(List<Long> idAltriDispositiviPrevInc) {
		this.idAltriDispositiviPrevInc = idAltriDispositiviPrevInc;
	}

	///////////

	// public Long getIdAltriDispositiviPrevInc() {
	// return idAltriDispositiviPrevInc;
	// }
	//
	//
	// public void setIdAltriDispositiviPrevInc(Long idAltriDispositiviPrevInc)
	// {
	// this.idAltriDispositiviPrevInc = idAltriDispositiviPrevInc;
	// }

	////////

	public Long getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Long idAzienda) {
		this.idAzienda = idAzienda;
	}

	public String getTelaio() {
		return telaio;
	}

	public void setTelaio(String telaio) {
		this.telaio = telaio;
	}

	public Long getIdTipoImmatricolazione() {
		return idTipoImmatricolazione;
	}

	public void setIdTipoImmatricolazione(Long idTipoImmatricolazione) {
		this.idTipoImmatricolazione = idTipoImmatricolazione;
	}

	public String getPrimoTelaio() {
		return primoTelaio;
	}

	public void setPrimoTelaio(String primoTelaio) {
		this.primoTelaio = primoTelaio;
	}

	public Date getDataPrimaImmatricolazione() {
		return dataPrimaImmatricolazione;
	}

	public void setDataPrimaImmatricolazione(Date dataPrimaImmatricolazione) {
		this.dataPrimaImmatricolazione = dataPrimaImmatricolazione;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public Boolean getDaImmatricolare() {
		return daImmatricolare;
	}

	public void setDaImmatricolare(Boolean daImmatricolare) {
		this.daImmatricolare = daImmatricolare;
	}

	public String getEnteAutorizzPrimaImm() {
		return enteAutorizzPrimaImm;
	}

	public void setEnteAutorizzPrimaImm(String enteAutorizzPrimaImm) {
		this.enteAutorizzPrimaImm = enteAutorizzPrimaImm;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public Date getDataUltimaImmatricolazione() {
		return dataUltimaImmatricolazione;
	}

	public void setDataUltimaImmatricolazione(Date dataUltimaImmatricolazione) {
		this.dataUltimaImmatricolazione = dataUltimaImmatricolazione;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Long getIdTipoAllestimento() {
		return idTipoAllestimento;
	}

	public void setIdTipoAllestimento(Long idTipoAllestimento) {
		this.idTipoAllestimento = idTipoAllestimento;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public Long getIdTipoAlimentazione() {
		return idTipoAlimentazione;
	}

	public void setIdTipoAlimentazione(Long idTipoAlimentazione) {
		this.idTipoAlimentazione = idTipoAlimentazione;
	}

	public String getOmologazioneDirettivaEuropea() {
		return omologazioneDirettivaEuropea;
	}

	public void setOmologazioneDirettivaEuropea(String omologazioneDirettivaEuropea) {
		this.omologazioneDirettivaEuropea = omologazioneDirettivaEuropea;
	}

	public String getAltraAlimentazione() {
		return altraAlimentazione;
	}

	public void setAltraAlimentazione(String altraAlimentazione) {
		this.altraAlimentazione = altraAlimentazione;
	}

	public Long getIdClasseAmbientale() {
		return idClasseAmbientale;
	}

	public void setIdClasseAmbientale(Long idClasseAmbientale) {
		this.idClasseAmbientale = idClasseAmbientale;
	}

	public String getCaratteristicheParticolari() {
		return caratteristicheParticolari;
	}

	public void setCaratteristicheParticolari(String caratteristicheParticolari) {
		this.caratteristicheParticolari = caratteristicheParticolari;
	}

	public Long getIdClasseVeicolo() {
		return idClasseVeicolo;
	}

	public void setIdClasseVeicolo(Long idClasseVeicolo) {
		this.idClasseVeicolo = idClasseVeicolo;
	}

	public Boolean getFlgDuePiani() {
		return flgDuePiani;
	}

	public void setFlgDuePiani(Boolean flgDuePiani) {
		this.flgDuePiani = flgDuePiani;
	}

	public Boolean getFlgSnodato() {
		return flgSnodato;
	}

	public void setFlgSnodato(Boolean flgSnodato) {
		this.flgSnodato = flgSnodato;
	}

	public Boolean getFlgCabinaGuidaIsolata() {
		return flgCabinaGuidaIsolata;
	}

	public void setFlgCabinaGuidaIsolata(Boolean flgCabinaGuidaIsolata) {
		this.flgCabinaGuidaIsolata = flgCabinaGuidaIsolata;
	}

	public Long getNumeroPorte() {
		return numeroPorte;
	}

	public void setNumeroPorte(Long numeroPorte) {
		this.numeroPorte = numeroPorte;
	}

	public Long getnPostiInPiedi() {
		return nPostiInPiedi;
	}

	public void setnPostiInPiedi(Long nPostiInPiedi) {
		this.nPostiInPiedi = nPostiInPiedi;
	}

	public Long getnPostiSedere() {
		return nPostiSedere;
	}

	public void setnPostiSedere(Long nPostiSedere) {
		this.nPostiSedere = nPostiSedere;
	}

	public Long getnPostiRiservati() {
		return nPostiRiservati;
	}

	public void setnPostiRiservati(Long nPostiRiservati) {
		this.nPostiRiservati = nPostiRiservati;
	}

	public Long getPostiCarrozzina() {
		return postiCarrozzina;
	}

	public void setPostiCarrozzina(Long postiCarrozzina) {
		this.postiCarrozzina = postiCarrozzina;
	}

	public Long getIdDotazioneDisabili() {
		return idDotazioneDisabili;
	}

	public void setIdDotazioneDisabili(Long idDotazioneDisabili) {
		this.idDotazioneDisabili = idDotazioneDisabili;
	}

	public Long getIdImpiantiAudio() {
		return idImpiantiAudio;
	}

	public void setIdImpiantiAudio(Long idImpiantiAudio) {
		this.idImpiantiAudio = idImpiantiAudio;
	}

	public Long getIdImpiantiVisivi() {
		return idImpiantiVisivi;
	}

	public void setIdImpiantiVisivi(Long idImpiantiVisivi) {
		this.idImpiantiVisivi = idImpiantiVisivi;
	}

	public String getAltriDispositiviPrevenzInc() {
		return altriDispositiviPrevenzInc;
	}

	public void setAltriDispositiviPrevenzInc(String altriDispositiviPrevenzInc) {
		this.altriDispositiviPrevenzInc = altriDispositiviPrevenzInc;
	}

	public Boolean getFlgImpiantoCondizionamento() {
		return flgImpiantoCondizionamento;
	}

	public void setFlgImpiantoCondizionamento(Boolean flgImpiantoCondizionamento) {
		this.flgImpiantoCondizionamento = flgImpiantoCondizionamento;
	}

	public Boolean getFlgRilevatoreBip() {
		return flgRilevatoreBip;
	}

	public void setFlgRilevatoreBip(Boolean flgRilevatoreBip) {
		this.flgRilevatoreBip = flgRilevatoreBip;
	}

	public Boolean getFlgContapasseggeri() {
		return flgContapasseggeri;
	}

	public void setFlgContapasseggeri(Boolean flgContapasseggeri) {
		this.flgContapasseggeri = flgContapasseggeri;
	}

	public Boolean getFlgOtx() {
		return flgOtx;
	}

	public void setFlgOtx(Boolean flgOtx) {
		this.flgOtx = flgOtx;
	}

	public Boolean getFlgAvm() {
		return flgAvm;
	}

	public void setFlgAvm(Boolean flgAvm) {
		this.flgAvm = flgAvm;
	}

	public Boolean getFlgFiltroFap() {
		return flgFiltroFap;
	}

	public void setFlgFiltroFap(Boolean flgFiltroFap) {
		this.flgFiltroFap = flgFiltroFap;
	}

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public Long getIdProprietaLeasing() {
		return idProprietaLeasing;
	}

	public void setIdProprietaLeasing(Long idProprietaLeasing) {
		this.idProprietaLeasing = idProprietaLeasing;
	}

	public Double getContributoPubblicoAcquisto() {
		return contributoPubblicoAcquisto;
	}

	public void setContributoPubblicoAcquisto(Double contributoPubblicoAcquisto) {
		this.contributoPubblicoAcquisto = contributoPubblicoAcquisto;
	}

	public Date getDataUltimaRevisione() {
		return dataUltimaRevisione;
	}

	public void setDataUltimaRevisione(Date dataUltimaRevisione) {
		this.dataUltimaRevisione = dataUltimaRevisione;
	}

	public String getTipologiaDimensionale() {
		return tipologiaDimensionale;
	}

	public void setTipologiaDimensionale(String tipologiaDimensionale) {
		this.tipologiaDimensionale = tipologiaDimensionale;
	}

	public Boolean getFlgVeicoloAssicurato() {
		return flgVeicoloAssicurato;
	}

	public void setFlgVeicoloAssicurato(Boolean flgVeicoloAssicurato) {
		this.flgVeicoloAssicurato = flgVeicoloAssicurato;
	}

	public Boolean getFlgConteggiatoMiv() {
		return flgConteggiatoMiv;
	}

	public void setFlgConteggiatoMiv(Boolean flgConteggiatoMiv) {
		this.flgConteggiatoMiv = flgConteggiatoMiv;
	}

	public Boolean getFlgRichiestaContr() {
		return flgRichiestaContr;
	}

	public void setFlgRichiestaContr(Boolean flgRichiestaContr) {
		this.flgRichiestaContr = flgRichiestaContr;
	}

	public Boolean getFlagAlienato() {
		return flagAlienato;
	}

	public void setFlagAlienato(Boolean flagAlienato) {
		this.flagAlienato = flagAlienato;
	}

	public Date getDataAlienazione() {
		return dataAlienazione;
	}

	public void setDataAlienazione(Date dataAlienazione) {
		this.dataAlienazione = dataAlienazione;
	}

	public String getDataScadVincoliNoAlien() {
		return dataScadVincoliNoAlien;
	}

	public void setDataScadVincoliNoAlien(String dataScadVincoliNoAlien) {
		this.dataScadVincoliNoAlien = dataScadVincoliNoAlien;
	}

	public String getAnnoSostProg() {
		return annoSostProg;
	}

	public void setAnnoSostProg(String annoSostProg) {
		this.annoSostProg = annoSostProg;
	}

	public Boolean getFlagVerificaAzienda() {
		return flagVerificaAzienda;
	}

	public void setFlagVerificaAzienda(Boolean flagVerificaAzienda) {
		this.flagVerificaAzienda = flagVerificaAzienda;
	}

	public String getNotaRiservataAzienda() {
		return notaRiservataAzienda;
	}

	public void setNotaRiservataAzienda(String notaRiservataAzienda) {
		this.notaRiservataAzienda = notaRiservataAzienda;
	}

	public Boolean getFlagVerificaAmp() {
		return flagVerificaAmp;
	}

	public void setFlagVerificaAmp(Boolean flagVerificaAmp) {
		this.flagVerificaAmp = flagVerificaAmp;
	}

	public String getNotaRiservataAmp() {
		return notaRiservataAmp;
	}

	public void setNotaRiservataAmp(String notaRiservataAmp) {
		this.notaRiservataAmp = notaRiservataAmp;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	public String getFlgAlienato() {
		return flgAlienato;
	}

	public void setFlgAlienato(String flgAlienato) {
		this.flgAlienato = flgAlienato;
	}
	
	public Double getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Double lunghezza) {
		this.lunghezza = lunghezza;
	}

	public Double getPrezzoTotAcquisto() {
		return prezzoTotAcquisto;
	}

	public void setPrezzoTotAcquisto(Double prezzoTotAcquisto) {
		this.prezzoTotAcquisto = prezzoTotAcquisto;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNoteDocumento() {
		return noteDocumento;
	}

	public void setNoteDocumento(String noteDocumento) {
		this.noteDocumento = noteDocumento;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	// public FileInputStream getFile() {
	// return file;
	// }
	//
	// public void setFile(FileInputStream file) {
	// this.file = file;
	// }

	public Date getDataCaricamentoDoc() {
		return dataCaricamentoDoc;
	}

	public void setDataCaricamentoDoc(Date dataCaricamentoDoc) {
		this.dataCaricamentoDoc = dataCaricamentoDoc;
	}

	public Double getFkPortabici() {
		return fkPortabici;
	}

	public void setFkPortabici(Double fkPortabici) {
		this.fkPortabici = fkPortabici;
	}

	public Double getFkSistemaVideosorveglianza() {
		return fkSistemaVideosorveglianza;
	}

	public void setFkSistemaVideosorveglianza(Double fkSistemaVideosorveglianza) {
		this.fkSistemaVideosorveglianza = fkSistemaVideosorveglianza;
	}

	public Double getFkSistemaLocalizzazione() {
		return fkSistemaLocalizzazione;
	}

	public void setFkSistemaLocalizzazione(Double fkSistemaLocalizzazione) {
		this.fkSistemaLocalizzazione = fkSistemaLocalizzazione;
	}

	public Boolean getFlgBipCablato() {
		return flgBipCablato;
	}

	public void setFlgBipCablato(Boolean flgBipCablato) {
		this.flgBipCablato = flgBipCablato;
	}

	public Boolean getFlgContapasseggeriIntegrato() {
		return flgContapasseggeriIntegrato;
	}

	public void setFlgContapasseggeriIntegrato(Boolean flgContapasseggeriIntegrato) {
		this.flgContapasseggeriIntegrato = flgContapasseggeriIntegrato;
	}

	public Boolean getFlgSistemiProtezioneAutista() {
		return flgSistemiProtezioneAutista;
	}

	public void setFlgSistemiProtezioneAutista(Boolean flgSistemiProtezioneAutista) {
		this.flgSistemiProtezioneAutista = flgSistemiProtezioneAutista;
	}

	public String getAltriAllestimenti() {
		return altriAllestimenti;
	}

	public void setAltriAllestimenti(String altriAllestimenti) {
		this.altriAllestimenti = altriAllestimenti;
	}

	public Boolean getFlgContribuzione() {
		return flgContribuzione;
	}

	public void setFlgContribuzione(Boolean flgContribuzione) {
		this.flgContribuzione = flgContribuzione;
	}

	public Long getIdCategoriaVeicolo() {
		return idCategoriaVeicolo;
	}

	public void setIdCategoriaVeicolo(Long idCategoriaVeicolo) {
		this.idCategoriaVeicolo = idCategoriaVeicolo;
	}

	public String getCategoriaVeicolo() {
		return categoriaVeicolo;
	}

	public void setCategoriaVeicolo(String categoriaVeicolo) {
		this.categoriaVeicolo = categoriaVeicolo;
	}

	public String getStatoTpl() {
		return statoTpl;
	}

	public void setStatoTpl(String statoTpl) {
		this.statoTpl = statoTpl;
	}

	public List<DocVariazAutobusVO> getDocumentiAutobus() {
		return documentiAutobus;
	}

	public void setDocumentiAutobus(List<DocVariazAutobusVO> documentiAutobus) {
		this.documentiAutobus = documentiAutobus;
	}

}
