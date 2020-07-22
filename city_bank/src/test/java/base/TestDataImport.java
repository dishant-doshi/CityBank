package base;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestDataImport extends SetUp {

	private static String SheetName;

	public static String getSheetName() {
		return SheetName;
	}

	public static void setSheetName(String sheetName) {
		SheetName = sheetName;
	}

	private static Object[][] readExcelFileTo2D(String filepath, String sheetname) {
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(new File(filepath));
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(sheetname);
		int lastRowNum = sheet.getRows();
		Object[][] object = new Object[lastRowNum - 1][1];
		for (int i = 1; i < lastRowNum; i++) {
			Map<Object, Object> map = new LinkedHashMap<Object, Object>();
			for (int j = 0; j < sheet.getColumns(); j++) {
				map.put(removeExtraSpaces(sheet.getCell(j, 0).getContents().toString().trim().replaceAll("  ", " ")),
						removeExtraSpaces(sheet.getCell(j, i).getContents().toString().trim().replaceAll("  ", " ")));
			}
			object[i - 1][0] = map;
		}
		return object;
	}

	private static String removeExtraSpaces(String string) {
		return string.replaceAll("\\s+", " ");
	}

	@DataProvider(name = "Login")
	public static Object[][] GetBalance() {
		return readExcelFileTo2D(LOGIN_DETAILS_FILE, "Login");
	}

	@DataProvider(name = "BalanceCheck")
	public static Object[][] BalanceCheck() {
		return readExcelFileTo2D(LOGIN_DETAILS_FILE, "BalanceCheck");
	}

	@DataProvider(name = "Flow")
	public static Object[][] Flow() {
		return readExcelFileTo2D(LOGIN_DETAILS_FILE, "Flow");
	}
}
