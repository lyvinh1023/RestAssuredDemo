package utils;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;

	public ExcelUtils(String excelPath, String sheetName) {
		try {
			workbook = new XSSFWorkbook(excelPath);
			sheet = workbook.getSheet(sheetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExcelUtils excelUtils = new ExcelUtils("./src/test/resources/DataForPost.xlsx", "Sheet1");
		excelUtils.getRowCount();
		excelUtils.getCellData(1,0);
		excelUtils.getCellData(1,1);
		excelUtils.getCellData(2,0);
		excelUtils.getCellData(2,1);
	}

	public void getRowCount() {
		int rowCount = sheet.getPhysicalNumberOfRows();
		System.out.println(rowCount);
	}

	public void getCellData(int row, int column) {
		DataFormatter formatter = new DataFormatter();
		Object value = formatter.formatCellValue(sheet.getRow(row).getCell(column));
		System.out.println(value);
	}

}
