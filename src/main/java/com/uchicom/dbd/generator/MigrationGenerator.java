// (c) 2018 uchicom
package com.uchicom.dbd.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.uchicom.dbd.entity.Column;
import com.uchicom.dbd.entity.Table;
import com.uchicom.dbd.entity.Transfer;
import com.uchicom.dbd.entity.TransferColumn;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class MigrationGenerator {

	private static String LABEL_TABLE_NAME = "物理エンティティ名";
	private static String LABEL_COLUMN_LOGICAL_NAME = "論理名";

	public void generate(File file) {
		try (FileInputStream fis = new FileInputStream(file);
				HSSFWorkbook book = new HSSFWorkbook(fis);) {
			int size = book.getNumberOfSheets();
			for (int i = 0; i < size; i++) {
				HSSFSheet sheet = book.getSheetAt(i);
				boolean data = false;
				Transfer transfer = new Transfer();
				for (int iRow = sheet.getFirstRowNum(); iRow <= sheet.getLastRowNum(); iRow++) {
					HSSFRow row = sheet.getRow(iRow);
					if (row.getFirstCellNum() >= 0) {
						HSSFCell cell = row.getCell(0);
						if (LABEL_TABLE_NAME.equals(cell.getStringCellValue())) {
							Table from = new Table();
							from.name = row.getCell(1).getStringCellValue();
							Table to = new Table();
							to.name = row.getCell(7).getStringCellValue();
							transfer.from = from;
							transfer.to = to;
						} else if (LABEL_COLUMN_LOGICAL_NAME.equals(cell.getStringCellValue())) {
							//次の行からはデータからむ
							data = true;
						}
					} else if (data) {
						Column from = new Column();
						Column to = new Column();
						from.name = row.getCell(1).getStringCellValue();
						to.name = row.getCell(8).getStringCellValue();
						TransferColumn transferColumn = new TransferColumn(from, to);
						transferColumn.type = "copy";
						transfer.transferColumnList.add(transferColumn);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
