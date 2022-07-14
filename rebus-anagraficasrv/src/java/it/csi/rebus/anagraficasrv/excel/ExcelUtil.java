/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.excel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import it.csi.rebus.anagraficasrv.common.exception.RebusException;
import it.csi.rebus.anagraficasrv.excel.ExcelDto.BodyRow;
import it.csi.rebus.anagraficasrv.excel.ExcelDto.ColBodyRow;
import it.csi.rebus.anagraficasrv.excel.ExcelDto.ColHeaderRow;
import it.csi.rebus.anagraficasrv.util.DateUtils;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {

	// Logger appliativo
	private static final Logger LOGGER = Logger
			.getLogger(it.csi.rebus.anagraficasrv.util.Constants.COMPONENT_NAME + ".ExcelUtil");

	public static enum EXCEL_PATTERN {
		STRING_PATTERN, NUMBER_3_DIGIT_PATTERN, EURO_PATTERN, INT_PATTERN, DATE_PATTERN, ANNO_PATTERN, DATEHOURS_PATTERN
	}

	public static final String EURO_PATTERN = "\u20AC #,##0.00";
	public static final String NUMBER_PATTERN = "#,##0.000";
	public static final String INT_PATTERN = "#,##0";
	public static final String ANNO_PATTERN = "##0";

	public static WritableCellFormat getRightAlignedStyle(WritableFont font, String dataPattern) {
		WritableCellFormat cellFormat;
		if (dataPattern != null) {
			final NumberFormat numberFormat = new NumberFormat(dataPattern);
			cellFormat = new WritableCellFormat(numberFormat);
		} else {
			cellFormat = new WritableCellFormat();
		}
		try {
			cellFormat.setAlignment(Alignment.RIGHT);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (final WriteException we) {
			LOGGER.error("EcxelUtilGamOpera::WritableCellFormat: " + we.toString());
		}

		if (font != null) {
			cellFormat.setFont(font);
		}

		return cellFormat;
	}

	public static DateTime buildDateCell(WritableSheet sheet, WritableCellFormat format, Object data, int colonna,
			int riga) throws WriteException {
		DateTime cell = null;
		if (data != null) {
			if (data instanceof XMLGregorianCalendar) {
				XMLGregorianCalendar gc = (XMLGregorianCalendar) data;
				cell = new DateTime(colonna, riga, DateUtils.calendarToDate(gc), format);
				sheet.addCell(cell);
			}
			if (data instanceof Date) {
				cell = new DateTime(colonna, riga, (Date) data, format);
				sheet.addCell(cell);
			}
		}
		return cell;
	}

	public static Number buildNumericCell(WritableSheet sheet, WritableCellFormat format, Object number, int colonna,
			int riga) throws WriteException {
		return buildNumericCell(sheet, format, null, number, colonna, riga);
	}

	private static Number buildNumericCell(WritableSheet sheet, WritableCellFormat format,
			WritableCellFormat negativeFormat, Object number, int colonna, int riga) throws WriteException {
		Number cell = null;
		if (number != null) {
			Double d = 0d;
			if (number instanceof Double)
				d = (Double) number;
			if (number instanceof String)
				d = Double.parseDouble((String) number);
			if (number instanceof BigDecimal)
				d = ((BigDecimal) number).doubleValue();
			if (number instanceof Long)
				d = (double) (Long) number;
			if (number instanceof Integer)
				d = (double) (Integer) number;
			cell = new Number(colonna, riga, d);
			if (format != null) {
				if (negativeFormat != null && d < 0) {
					cell.setCellFormat(negativeFormat);
				} else {
					cell.setCellFormat(format);
				}
			}

			sheet.addCell(cell);
		}

		return cell;
	}

	public static byte[] generaExcel(List<ExcelDto> excelList, StringBuilder fileName) throws Exception {
		java.io.File file = null;
		try {
			file = new java.io.File(fileName + ".xls");
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			for (ExcelDto excel : excelList) {

				WritableSheet ws = workbook.createSheet(excel.getSheetName(), excelList.indexOf(excel));

				WritableFont fontInt = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
				fontInt.setColour(Colour.BLACK);
				WritableCellFormat formatInt = new WritableCellFormat(fontInt);
				formatInt.setBackground(new Colour(208, "unknown", 255, 255, 204) {
				});
				formatInt.setWrap(true);
				formatInt.setAlignment(Alignment.LEFT);
				formatInt.setVerticalAlignment(VerticalAlignment.CENTRE);

				WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD);
				font.setColour(Colour.BLACK);
				WritableCellFormat format = new WritableCellFormat(font);
				format.setBackground(new Colour(208, "unknown", 255, 255, 204) {
				});
				format.setWrap(true);
				format.setAlignment(Alignment.LEFT);
				format.setVerticalAlignment(VerticalAlignment.CENTRE);

				WritableFont fontDato = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
				fontDato.setColour(Colour.BLACK);
				WritableCellFormat stringFormat = new WritableCellFormat(fontDato);
				stringFormat.setWrap(true);
				stringFormat.setAlignment(Alignment.JUSTIFY);
				stringFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

				WritableCellFormat numberStyle = ExcelUtil.getRightAlignedStyle(null, ExcelUtil.NUMBER_PATTERN);
				WritableCellFormat euroStyle = ExcelUtil.getRightAlignedStyle(null, ExcelUtil.EURO_PATTERN);
				WritableCellFormat interoStyle = ExcelUtil.getRightAlignedStyle(null, ExcelUtil.INT_PATTERN);
				WritableCellFormat annoStyle = ExcelUtil.getRightAlignedStyle(null, ExcelUtil.ANNO_PATTERN);

				DateFormat df = new DateFormat("dd/MM/yyyy");
				WritableCellFormat dateStyle = new WritableCellFormat(df);
				dateStyle.setVerticalAlignment(VerticalAlignment.CENTRE);

				DateFormat dfH = new DateFormat("dd/MM/yyyy hh:mm:ss");
				WritableCellFormat dateHStyle = new WritableCellFormat(dfH);
				dateHStyle.setVerticalAlignment(VerticalAlignment.CENTRE);

				int iCol = 0;
				int row = 0;

				// Se ci sono creo le righe di intestazione
				if (excel.getIntestazioneRow() != null && !excel.getIntestazioneRow().isEmpty()) {
					for (String intestazione : excel.getIntestazioneRow()) {
						// Unisco le celle dell'intestazione
						ws.mergeCells(0, row, excel.getListColHeaderRow().size() - 1, row);
						ws.setRowView(row, 800);
						ws.addCell(new Label(0, row++, intestazione, formatInt));
					}
				}

				// Creo l'header
				for (ColHeaderRow colHeaderRow : excel.getListColHeaderRow()) {
					ws.addCell(new Label(iCol, row, colHeaderRow.getLabel(), format));
					ws.setColumnView(iCol++, colHeaderRow.getWidth());
				}

				// Ciclo sulle righe
				for (BodyRow bodyRow : excel.getListBodyRow()) {
					row++;
					// ciclo sulle colonne
					iCol = 0;
					for (ColBodyRow colBodyRow : bodyRow.getListColBodyRow()) {

						/*
						 * Verifico che tipo di colonan creare, stringa, numerica, data ecc
						 */
						switch (colBodyRow.getPattern()) {
						case STRING_PATTERN:
							ws.addCell(new Label(iCol++, row, String.valueOf(colBodyRow.getValue()), stringFormat));
							break;
						case NUMBER_3_DIGIT_PATTERN:
							ExcelUtil.buildNumericCell(ws, numberStyle, colBodyRow.getValue(), iCol++, row);
							break;
						case EURO_PATTERN:
							ExcelUtil.buildNumericCell(ws, euroStyle, colBodyRow.getValue(), iCol++, row);
							break;
						case INT_PATTERN:
							ExcelUtil.buildNumericCell(ws, interoStyle, colBodyRow.getValue(), iCol++, row);
							break;
						case ANNO_PATTERN:
							ExcelUtil.buildNumericCell(ws, annoStyle, colBodyRow.getValue(), iCol++, row);
							break;

						case DATE_PATTERN:
							/*
							 * Per ora accetta in input solo XMLGregorianCalendar e Date
							 */
							ExcelUtil.buildDateCell(ws, dateStyle, colBodyRow.getValue(), iCol++, row);
							break;
						case DATEHOURS_PATTERN:
							/*
							 * Per ora accetta in input solo XMLGregorianCalendar e Date
							 */
							ExcelUtil.buildDateCell(ws, dateHStyle, colBodyRow.getValue(), iCol++, row);
							break;
						default:
							ws.addCell(new Label(iCol++, row, String.valueOf(colBodyRow.getValue()), stringFormat));
							break;
						}
					}
				}

			}

			workbook.write();
			workbook.close();

			return FileUtils.readFileToByteArray(file);
		} catch (Exception e) {
			throw new RebusException(e);
		} finally {
			if (file != null) {
				file.delete();
			}

		}
	}

	public static String eraseNull(String str) {
		if (str == null)
			return "";
		return str;
	}

	/*
	 * public static Object eraseNull(Date dt){ if (dt==null) return ""; return dt;
	 * }
	 * 
	 * public static Object eraseNull(Long num){ if (num==null) return ""; return
	 * num; }
	 */
}
