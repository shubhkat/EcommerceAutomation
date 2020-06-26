package utility;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel {
	private static Workbook book;
	private static Sheet sheet;
	private static Row row;
	public static String ReadExcel(String excelpath, String sheetname, int rownum, int cellnum) {
		
		//Open Excel
		FileInputStream file;
		try {
			
			file = new FileInputStream(excelpath);
			book = WorkbookFactory.create(file);
			
		} catch (Exception e) {
			
		}	
		//Identify Sheet
		sheet = book.getSheet(sheetname);
		
		//Read Excel Data
		row = sheet.getRow(rownum);
		return row.getCell(cellnum).toString();
	}
}
