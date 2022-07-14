/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.vo;

import java.util.List;

public class ExcelSoggettoVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	/*
	 * Indica che e' stata generata un eccezione durante il caricamento del file
	 * excel
	 */
	private boolean erroreException;

	/*
	 * Messaggio associato a erroreException
	 */
	private String msgErroreException;

	/*
	 * Indica che il file excel caricato non e' corretto e quindi in questo caso
	 * viene restituito il file excel
	 */
	private boolean erroreCampiExcel;

	/*
	 * Contiene il file excel quando deve essere visualizzato in output, e'
	 * associato a erroreCaricamentoExcel
	 */
	private byte[] excel;

	/*
	 * Indica che il file excel contiene degli errori di congruenza sui campi
	 */
	private boolean erroreCongruenzaCampiExcel;

	/*
	 * Contiene tutte le incongruenze associate a erroreCongruenzaCampiExcel
	 */
	private List<String> msgErroreCongruenzaCampiExcel;

	/*
	 * Indica che i dati sono stati letti e caricati su DB correttamente
	 */
	private boolean caricamentoDatiOK;

	private long recordInseriti;

	public boolean isErroreException() {
		return erroreException;
	}

	public void setErroreException(boolean erroreException) {
		this.erroreException = erroreException;
	}

	public String getMsgErroreException() {
		return msgErroreException;
	}

	public void setMsgErroreException(String msgErroreException) {
		this.msgErroreException = msgErroreException;
	}

	public boolean isErroreCampiExcel() {
		return erroreCampiExcel;
	}

	public void setErroreCampiExcel(boolean erroreCampiExcel) {
		this.erroreCampiExcel = erroreCampiExcel;
	}

	public byte[] getExcel() {
		return excel;
	}

	public void setExcel(byte[] excel) {
		this.excel = excel;
	}

	public boolean isErroreCongruenzaCampiExcel() {
		return erroreCongruenzaCampiExcel;
	}

	public void setErroreCongruenzaCampiExcel(boolean erroreCongruenzaCampiExcel) {
		this.erroreCongruenzaCampiExcel = erroreCongruenzaCampiExcel;
	}

	public List<String> getMsgErroreCongruenzaCampiExcel() {
		return msgErroreCongruenzaCampiExcel;
	}

	public void setMsgErroreCongruenzaCampiExcel(List<String> msgErroreCongruenzaCampiExcel) {
		this.msgErroreCongruenzaCampiExcel = msgErroreCongruenzaCampiExcel;
	}

	public boolean isCaricamentoDatiOK() {
		return caricamentoDatiOK;
	}

	public void setCaricamentoDatiOK(boolean caricamentoDatiOK) {
		this.caricamentoDatiOK = caricamentoDatiOK;
	}

	public long getRecordInseriti() {
		return recordInseriti;
	}

	public void setRecordInseriti(long recordInseriti) {
		this.recordInseriti = recordInseriti;
	}
}
