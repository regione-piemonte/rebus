/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.csi.rebus.rebuscrus.integration.dto.custom.CronologiaDTO;
import it.csi.rebus.rebuscrus.integration.dto.custom.ProcedimentiDTO;
import it.csi.rebus.rebuscrus.util.NumberUtil;

public class AutobusVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	// MODIFICA - DETTAGLIO
	private String azienda;
	private String telaio;
	private Long idTipoImmatricolazione;
	private String primoTelaio;
	private Date dataPrimaImmatricolazione;
	private String targa;
	private Boolean daImmatricolare;
	private String enteAutorizzPrimaImm;
	private String matricola;
	private Date dataUltimaImmatricolazione;
	private String marca;
	private Long idTipoAllestimento;
	private String modello;
	private Long idTipoAlimentazione;
	private String omologazioneDirettivaEuropea;
	private String altraAlimentazione;
	private Long idClasseAmbientale;
	private String caratteristicheParticolari;
	private Long idClasseVeicolo;
	private Double lunghezza;
	private String lunghezzaStr;
	private String flgDuePiani;
	private String flgSnodato;
	private String flgCabinaGuidaIsolata;
	private Long numeroPorte;
	private String nPostiSedere;
	private String nPostiInPiedi;
	private String nPostiRiservati;
	private String postiCarrozzina;
	private Long idDotazioneDisabili;
	private ArrayList<Long> dispositiviPrevIncidenti;
	private Long idImpiantiAudio;
	private Long idImpiantiVisivi;
	private Long idAltriDispositiviPrevInc;// TODO NON VALORIZZATO A FE
	private String flgImpiantoCondizionamento;
	private String flgRilevatoreBip;
	private String flgContapasseggeri;
	private String flgOtx;
	private String flgAvm;
	private String flgFiltroFap;
	private Double prezzoTotAcquisto;
	private String prezzoTotAcquistoStr;
	private Long idProprietaLeasing;
	private Date dataUltimaRevisione;
	private String tipologiaDimensionale;
	private String flgVeicoloAssicurato;
	private String flgConteggiatoMiv;
	private Double contributoPubblicoAcquisto;
	private String contributoPubblicoAcquistoStr;
	private String contributo;
	private String flagAlienato;
	private Date dataAlienazione;
	private String dataScadVincoliNoAlien;
	private Long annoSostProg;
	private String flagVerificaAzienda;
	private String flagVerificaAmp;
	private String note;
	private String notaRiservataAmp;
	private String notaRiservataAzienda;
	private String flgRichiestaContr;
	private String motivazione;

	/// SOLO DETTAGLIO
	private String tipoImmatricolazione;
	private String tipoAllestimento;
	private String tipoAlimentazione;
	private String classeAmbientale;
	private String classeVeicolo;
	private String dotazioneDisabili;
	private String dispositiviPrevenzInc;
	private String impiantiAudio;
	private String impiantiVisivi;
	private String altriDispositiviPrevenzInc;
	private String proprietaLeasing;

	// documenti allegati
	public Long idDocumento;
	public String noteDocumento;
	public byte[] file;
	public Date dataCaricamentoDoc;
	public String nomeFile;
	public String descrizioneDocumento;
	public List<DocVariazAutobusVO> documentiAutobus;

	public boolean isAllegato;
	public boolean isProcedimento;

	private Long id;
	private Long idAzienda;
	private Long bando;
	private Long idDeposito;
	private String depositoStr;
	private String notaRiservataRp;
	private String progrTipoDimens;
	private Date dataAggiornamento;

	private List<CronologiaDTO> cronologia;
	private List<ProcedimentiDTO> procedimenti;

	private List<CampagnaVO> campagna;
	private List<CampagnaVO> campagnaE;
	private List<CampagnaVO> campagnaP;

	private List<EmissioniVO> emissioni;
	private List<PortabiciVO> portabiciList;

	/*
	 * DA CONTROLLARE
	 */
	private Double fkPortabici;
	private String portabici;
	private Double fkSistemaVideosorveglianza;
	private String sistemaVideosorveglianza;
	private Double fkSistemaLocalizzazione;
	private String tipologiaAvm;
	private Boolean flgContapasseggeriIntegrato;
	private Boolean flgBipCablato;
	private Boolean flgSistemiProtezioneAutista;
	private String altriAllestimenti;

	private Boolean flgContribuzione;
	private Boolean flgDaImmatricolare;
	private Boolean sostituito;

	// da eliminare
	// contribuzione
	private Long idContribuzione;
	private Long idProcedimento;

	private Long idCategoriaVeicolo;
	private String categoriaVeicolo;
	private String flgAlienato;
	private String statoTpl;

	// Appoggio che definisce se il DTO del inserimento del documento nella
	// pagina di modifica e popolato o meno
	private String isFileUpload;

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

	public Date getDataCaricamentoDoc() {
		return dataCaricamentoDoc;
	}

	public void setDataCaricamentoDoc(Date dataCaricamentoDoc) {
		this.dataCaricamentoDoc = dataCaricamentoDoc;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getAzienda() {
		return azienda;
	}

	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}

	public String getStatoTpl() {
		return statoTpl;
	}

	public void setStatoTpl(String statoTpl) {
		this.statoTpl = statoTpl;
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

	public Double getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Double lunghezza) {
		this.lunghezza = lunghezza;
	}

	public String getFlgDuePiani() {
		return flgDuePiani;
	}

	public void setFlgDuePiani(String flgDuePiani) {
		this.flgDuePiani = flgDuePiani;
	}

	public String getFlgSnodato() {
		return flgSnodato;
	}

	public void setFlgSnodato(String flgSnodato) {
		this.flgSnodato = flgSnodato;
	}

	public String getFlgCabinaGuidaIsolata() {
		return flgCabinaGuidaIsolata;
	}

	public void setFlgCabinaGuidaIsolata(String flgCabinaGuidaIsolata) {
		this.flgCabinaGuidaIsolata = flgCabinaGuidaIsolata;
	}

	public Long getNumeroPorte() {
		return numeroPorte;
	}

	public void setNumeroPorte(Long numeroPorte) {
		this.numeroPorte = numeroPorte;
	}

	public String getnPostiSedere() {
		return nPostiSedere;
	}

	public void setnPostiSedere(String nPostiSedere) {
		this.nPostiSedere = nPostiSedere;
	}

	public String getnPostiInPiedi() {
		return nPostiInPiedi;
	}

	public void setnPostiInPiedi(String nPostiInPiedi) {
		this.nPostiInPiedi = nPostiInPiedi;
	}

	public String getnPostiRiservati() {
		return nPostiRiservati;
	}

	public void setnPostiRiservati(String nPostiRiservati) {
		this.nPostiRiservati = nPostiRiservati;
	}

	public String getPostiCarrozzina() {
		return postiCarrozzina;
	}

	public void setPostiCarrozzina(String postiCarrozzina) {
		this.postiCarrozzina = postiCarrozzina;
	}

	public Long getIdDotazioneDisabili() {
		return idDotazioneDisabili;
	}

	public void setIdDotazioneDisabili(Long idDotazioneDisabili) {
		this.idDotazioneDisabili = idDotazioneDisabili;
	}

	public ArrayList<Long> getDispositiviPrevIncidenti() {
		return dispositiviPrevIncidenti;
	}

	public void setDispositiviPrevIncidenti(ArrayList<Long> dispositiviPrevIncidenti) {
		this.dispositiviPrevIncidenti = dispositiviPrevIncidenti;
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

	public Long getIdAltriDispositiviPrevInc() {
		return idAltriDispositiviPrevInc;
	}

	public void setIdAltriDispositiviPrevInc(Long idAltriDispositiviPrevInc) {
		this.idAltriDispositiviPrevInc = idAltriDispositiviPrevInc;
	}

	public String getFlgImpiantoCondizionamento() {
		return flgImpiantoCondizionamento;
	}

	public void setFlgImpiantoCondizionamento(String flgImpiantoCondizionamento) {
		this.flgImpiantoCondizionamento = flgImpiantoCondizionamento;
	}

	public String getFlgRilevatoreBip() {
		return flgRilevatoreBip;
	}

	public void setFlgRilevatoreBip(String flgRilevatoreBip) {
		this.flgRilevatoreBip = flgRilevatoreBip;
	}

	public String getFlgContapasseggeri() {
		return flgContapasseggeri;
	}

	public void setFlgContapasseggeri(String flgContapasseggeri) {
		this.flgContapasseggeri = flgContapasseggeri;
	}

	public String getFlgOtx() {
		return flgOtx;
	}

	public void setFlgOtx(String flgOtx) {
		this.flgOtx = flgOtx;
	}

	public String getFlgAvm() {
		return flgAvm;
	}

	public void setFlgAvm(String flgAvm) {
		this.flgAvm = flgAvm;
	}

	public String getFlgFiltroFap() {
		return flgFiltroFap;
	}

	public void setFlgFiltroFap(String flgFiltroFap) {
		this.flgFiltroFap = flgFiltroFap;
	}

	public Double getPrezzoTotAcquisto() {
		return prezzoTotAcquisto;
	}

	public void setPrezzoTotAcquisto(Double prezzoTotAcquisto) {
		this.prezzoTotAcquisto = prezzoTotAcquisto;
	}

	public Long getIdProprietaLeasing() {
		return idProprietaLeasing;
	}

	public void setIdProprietaLeasing(Long idProprietaLeasing) {
		this.idProprietaLeasing = idProprietaLeasing;
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

	public String getFlgVeicoloAssicurato() {
		return flgVeicoloAssicurato;
	}

	public void setFlgVeicoloAssicurato(String flgVeicoloAssicurato) {
		this.flgVeicoloAssicurato = flgVeicoloAssicurato;
	}

	public String getFlgConteggiatoMiv() {
		return flgConteggiatoMiv;
	}

	public void setFlgConteggiatoMiv(String flgConteggiatoMiv) {
		this.flgConteggiatoMiv = flgConteggiatoMiv;
	}

	public Double getContributoPubblicoAcquisto() {
		return contributoPubblicoAcquisto;
	}

	public void setContributoPubblicoAcquisto(Double contributoPubblicoAcquisto) {
		this.contributoPubblicoAcquisto = contributoPubblicoAcquisto;
	}

	public String getContributo() {
		return contributo;
	}

	public void setContributo(String contributo) {
		this.contributo = contributo;
	}

	public String getFlagAlienato() {
		return flagAlienato;
	}

	public void setFlagAlienato(String flagAlienato) {
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

	public Long getAnnoSostProg() {
		return annoSostProg;
	}

	public void setAnnoSostProg(Long annoSostProg) {
		this.annoSostProg = annoSostProg;
	}

	public String getFlagVerificaAzienda() {
		return flagVerificaAzienda;
	}

	public void setFlagVerificaAzienda(String flagVerificaAzienda) {
		this.flagVerificaAzienda = flagVerificaAzienda;
	}

	public String getFlagVerificaAmp() {
		return flagVerificaAmp;
	}

	public void setFlagVerificaAmp(String flagVerificaAmp) {
		this.flagVerificaAmp = flagVerificaAmp;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNotaRiservataAmp() {
		return notaRiservataAmp;
	}

	public void setNotaRiservataAmp(String notaRiservataAmp) {
		this.notaRiservataAmp = notaRiservataAmp;
	}

	public String getNotaRiservataAzienda() {
		return notaRiservataAzienda;
	}

	public void setNotaRiservataAzienda(String notaRiservataAzienda) {
		this.notaRiservataAzienda = notaRiservataAzienda;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getTipoImmatricolazione() {
		return tipoImmatricolazione;
	}

	public void setTipoImmatricolazione(String tipoImmatricolazione) {
		this.tipoImmatricolazione = tipoImmatricolazione;
	}

	public String getTipoAllestimento() {
		return tipoAllestimento;
	}

	public void setTipoAllestimento(String tipoAllestimento) {
		this.tipoAllestimento = tipoAllestimento;
	}

	public String getTipoAlimentazione() {
		return tipoAlimentazione;
	}

	public void setTipoAlimentazione(String tipoAlimentazione) {
		this.tipoAlimentazione = tipoAlimentazione;
	}

	public String getClasseAmbientale() {
		return classeAmbientale;
	}

	public void setClasseAmbientale(String classeAmbientale) {
		this.classeAmbientale = classeAmbientale;
	}

	public String getClasseVeicolo() {
		return classeVeicolo;
	}

	public void setClasseVeicolo(String classeVeicolo) {
		this.classeVeicolo = classeVeicolo;
	}

	public String getDotazioneDisabili() {
		return dotazioneDisabili;
	}

	public void setDotazioneDisabili(String dotazioneDisabili) {
		this.dotazioneDisabili = dotazioneDisabili;
	}

	public String getDispositiviPrevenzInc() {
		return dispositiviPrevenzInc;
	}

	public void setDispositiviPrevenzInc(String dispositiviPrevenzInc) {
		this.dispositiviPrevenzInc = dispositiviPrevenzInc;
	}

	public String getImpiantiAudio() {
		return impiantiAudio;
	}

	public void setImpiantiAudio(String impiantiAudio) {
		this.impiantiAudio = impiantiAudio;
	}

	public String getImpiantiVisivi() {
		return impiantiVisivi;
	}

	public void setImpiantiVisivi(String impiantiVisivi) {
		this.impiantiVisivi = impiantiVisivi;
	}

	public String getAltriDispositiviPrevenzInc() {
		return altriDispositiviPrevenzInc;
	}

	public void setAltriDispositiviPrevenzInc(String altriDispositiviPrevenzInc) {
		this.altriDispositiviPrevenzInc = altriDispositiviPrevenzInc;
	}

	public String getProprietaLeasing() {
		return proprietaLeasing;
	}

	public void setProprietaLeasing(String proprietaLeasing) {
		this.proprietaLeasing = proprietaLeasing;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Long idAzienda) {
		this.idAzienda = idAzienda;
	}

	public Long getBando() {
		return bando;
	}

	public void setBando(Long bando) {
		this.bando = bando;
	}

	public Long getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}

	public String getNotaRiservataRp() {
		return notaRiservataRp;
	}

	public void setNotaRiservataRp(String notaRiservataRp) {
		this.notaRiservataRp = notaRiservataRp;
	}

	public String getProgrTipoDimens() {
		return progrTipoDimens;
	}

	public void setProgrTipoDimens(String progrTipoDimens) {
		this.progrTipoDimens = progrTipoDimens;
	}

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public String getFlgRichiestaContr() {
		return flgRichiestaContr;
	}

	public void setFlgRichiestaContr(String flgRichiestaContr) {
		this.flgRichiestaContr = flgRichiestaContr;
	}

	public String getLunghezzaStr() {
		return NumberUtil.customFormat("###.###", lunghezza);
	}

	public void setLunghezzaStr(String lunghezzaStr) {
		this.lunghezzaStr = lunghezzaStr;
	}

	public String getPrezzoTotAcquistoStr() {
		return NumberUtil.customFormat("###,###.00", prezzoTotAcquisto);
	}

	public void setPrezzoTotAcquistoStr(String prezzoTotAcquistoStr) {
		this.prezzoTotAcquistoStr = prezzoTotAcquistoStr;
	}

	public String getContributoPubblicoAcquistoStr() {
		return NumberUtil.customFormat("###,###.00", contributoPubblicoAcquisto);
	}

	public void setContributoPubblicoAcquistoStr(String contributoPubblicoAcquistoStr) {
		this.contributoPubblicoAcquistoStr = contributoPubblicoAcquistoStr;
	}

	public boolean isAllegato() {
		return isAllegato;
	}

	public void setAllegato(boolean isAllegato) {
		this.isAllegato = isAllegato;
	}

	public boolean isProcedimento() {
		return isProcedimento;
	}

	public void setProcedimento(boolean isProcedimento) {
		this.isProcedimento = isProcedimento;
	}

	public String getIsFileUpload() {
		return isFileUpload;
	}

	public void setIsFileUpload(String isFileUpload) {
		this.isFileUpload = isFileUpload;
	}

	public String getDescrizioneDocumento() {
		return descrizioneDocumento;
	}

	public void setDescrizioneDocumento(String descrizioneDocumento) {
		this.descrizioneDocumento = descrizioneDocumento;
	}

	public String getDepositoStr() {
		return depositoStr;
	}

	public void setDepositoStr(String depositoStr) {
		this.depositoStr = depositoStr;
	}

	public List<CronologiaDTO> getCronologia() {
		return cronologia;
	}

	public void setCronologia(List<CronologiaDTO> cronologia) {
		this.cronologia = cronologia;
	}

	public List<ProcedimentiDTO> getProcedimenti() {
		return procedimenti;
	}

	public void setProcedimenti(List<ProcedimentiDTO> procedimenti) {
		this.procedimenti = procedimenti;
	}

	public List<CampagnaVO> getCampagna() {
		return campagna;
	}

	public void setCampagna(List<CampagnaVO> campagna) {
		this.campagna = campagna;
	}

	public List<EmissioniVO> getEmissioni() {
		return emissioni;
	}

	public void setEmissioni(List<EmissioniVO> emissioni) {
		this.emissioni = emissioni;
	}

	public List<CampagnaVO> getCampagnaE() {
		return campagnaE;
	}

	public void setCampagnaE(List<CampagnaVO> campagnaE) {
		this.campagnaE = campagnaE;
	}

	public List<CampagnaVO> getCampagnaP() {
		return campagnaP;
	}

	public void setCampagnaP(List<CampagnaVO> campagnaP) {
		this.campagnaP = campagnaP;
	}

	public Long getIdContribuzione() {
		return idContribuzione;
	}

	public void setIdContribuzione(Long idContribuzione) {
		this.idContribuzione = idContribuzione;
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

	public Long getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Long idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public Boolean getFlgDaImmatricolare() {
		return flgDaImmatricolare;
	}

	public void setFlgDaImmatricolare(Boolean flgDaImmatricolare) {
		this.flgDaImmatricolare = flgDaImmatricolare;
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

	public String getFlgAlienato() {
		return flgAlienato;
	}

	public void setFlgAlienato(String flgAlienato) {
		this.flgAlienato = flgAlienato;
	}

	public List<DocVariazAutobusVO> getDocumentiAutobus() {
		return documentiAutobus;
	}

	public void setDocumentiAutobus(List<DocVariazAutobusVO> documentiAutobus) {
		this.documentiAutobus = documentiAutobus;
	}

	public Boolean getSostituito() {
		return sostituito;
	}

	public void setSostituito(Boolean sostituito) {
		this.sostituito = sostituito;
	}

	public String getSistemaVideosorveglianza() {
		return sistemaVideosorveglianza;
	}

	public void setSistemaVideosorveglianza(String sistemaVideosorveglianza) {
		this.sistemaVideosorveglianza = sistemaVideosorveglianza;
	}

	public String getTipologiaAvm() {
		return tipologiaAvm;
	}

	public void setTipologiaAvm(String tipologiaAvm) {
		this.tipologiaAvm = tipologiaAvm;
	}

	public Boolean getFlgContapasseggeriIntegrato() {
		return flgContapasseggeriIntegrato;
	}

	public void setFlgContapasseggeriIntegrato(Boolean flgContapasseggeriIntegrato) {
		this.flgContapasseggeriIntegrato = flgContapasseggeriIntegrato;
	}

	public Boolean getFlgBipCablato() {
		return flgBipCablato;
	}

	public void setFlgBipCablato(Boolean flgBipCablato) {
		this.flgBipCablato = flgBipCablato;
	}

	public Boolean getFlgSistemiProtezioneAutista() {
		return flgSistemiProtezioneAutista;
	}

	public void setFlgSistemiProtezioneAutista(Boolean flgSistemiProtezioneAutista) {
		this.flgSistemiProtezioneAutista = flgSistemiProtezioneAutista;
	}

	public String getPortabici() {
		return portabici;
	}

	public void setPortabici(String portabici) {
		this.portabici = portabici;
	}

	public List<PortabiciVO> getPortabiciList() {
		return portabiciList;
	}

	public void setPortabiciList(List<PortabiciVO> portabiciList) {
		this.portabiciList = portabiciList;
	}
	
	

}