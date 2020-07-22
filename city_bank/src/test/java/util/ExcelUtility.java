package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class ExcelUtility {
	public static HSSFWorkbook workbook;
	public static HSSFSheet sheet;
	public static HSSFCell cell;
	public static HSSFRow row;
	// public static final String filePath = ".//test_data//";

	/**
	 * @author dishant.doshi
	 * @param filepath
	 * @param sheetName
	 *            to set excel file for perform read/write operations
	 */
	public static void setExcelFile(String fileName, String sheetName) {
		File file = new File(fileName);
		try {
			FileInputStream ExcelFile = new FileInputStream(file.getAbsolutePath());
			workbook = new HSSFWorkbook(ExcelFile);
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author dishant.doshi
	 * @param RowNum
	 * @param ColNum
	 * @return excel cell data
	 */
	@SuppressWarnings("deprecation")
	public static String getCellData(String fileName, String sheetName, int RowNum, int ColNum) {
		String cellData;
		try {
			setExcelFile(fileName, sheetName);
			cell = sheet.getRow(RowNum).getCell(ColNum);
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				cellData = String.valueOf((int) cell.getNumericCellValue());
			else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
				cellData = String.valueOf(cell.getBooleanCellValue());
			else
				cellData = cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @author dishant.doshi
	 * @param RowNum
	 * @param ColNum
	 * @return date type excel cell data
	 */
	public static String getDateCellData(String fileName, String sheetName, int RowNum, int ColNum) {
		String CellData = " ";
		try {
			setExcelFile(fileName, sheetName);
			DataFormatter dataFormatter = new DataFormatter();
			cell = sheet.getRow(RowNum).getCell(ColNum);
			if (HSSFDateUtil.isCellDateFormatted(cell))
				CellData = dataFormatter.formatCellValue(cell);
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * @author dishant.doshi
	 * @param RowNum
	 * @param ColNum
	 * @return numeric type excel cell data
	 */
	public static String getNumCellData(String fileName, String sheetName, int RowNum, int ColNum) {
		try {
			setExcelFile(fileName, sheetName);
			cell = sheet.getRow(RowNum).getCell(ColNum);
			int CellData = (int) cell.getNumericCellValue();
			String cellValue = String.valueOf(CellData);
			return cellValue;
		} catch (Exception e) {
			return " ";
		}
	}

	/**
	 * @author dishant.doshi
	 * @param fileName
	 * @param sheetName
	 * @return no of rows in excel
	 */
	public static int getRowCount(String fileName, String sheetName) {
		try {
			setExcelFile(fileName, sheetName);
			int RowCount = sheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @author dishant.doshi
	 * @param fileName
	 * @param sheetName
	 * @return no of columns in excel
	 */
	public static int getColCount(String fileName, String sheetName) {
		try {
			setExcelFile(fileName, sheetName);
			int ColCount = sheet.getRow(0).getPhysicalNumberOfCells();
			return ColCount;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param fileName
	 * @param sheetName
	 * @param RowNum
	 * @param ColNum
	 * @param Results
	 *            set @param Results value into excel cell
	 */
	public static void setCellData(String fileName, String sheetName, int RowNum, int ColNum, String Results) {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			cell.setCellValue(Results);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setCellDataWithFormate(String fileName, String sheetName, int RowNum, int ColNum,
			String Results) {
		File file = new File(fileName);
		try {
			int rows = getRowCount(fileName, sheetName);
			if (rows < RowNum)
				cell = sheet.createRow(RowNum).createCell(ColNum);
			else
				cell = sheet.getRow(RowNum).createCell(ColNum);
			HSSFCellStyle style = workbook.createCellStyle();
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.TOP);
			if (Results.length() > 50) {
				sheet.setColumnWidth(ColNum, 250000);
				style.setWrapText(true);
			}
			// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell.setCellValue(Results);
			FileOutputStream Outputstream = new FileOutputStream(file.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param path
	 * @param RowNum
	 * @param ColNum
	 *            set formula into excel cell
	 */
	public static void setCellFormula(File fileName, int RowNum, int ColNum) {
		try {
			row = sheet.getRow(RowNum);
			cell = row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			HSSFCellStyle style = cell.getCellStyle();
			cell.setCellFormula(cell.getCellFormula());
			cell.setCellStyle(style);
			FileOutputStream Outputstream = new FileOutputStream(fileName.getAbsolutePath());
			workbook.write(Outputstream);
			Outputstream.flush();
			Outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
