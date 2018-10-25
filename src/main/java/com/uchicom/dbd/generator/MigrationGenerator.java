// (c) 2018 uchicom
package com.uchicom.dbd.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
		try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook book = new XSSFWorkbook(fis);) {
			int size = book.getNumberOfSheets();
			List<Transfer> transferList = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				XSSFSheet sheet = book.getSheetAt(i);
				boolean data = false;
				Transfer transfer = new Transfer();
				for (int iRow = sheet.getFirstRowNum(); iRow <= sheet.getLastRowNum(); iRow++) {
					XSSFRow row = sheet.getRow(iRow);
					if (row == null)
						continue;
					if (row.getFirstCellNum() >= 0) {
						XSSFCell cell = row.getCell(0);
						if (LABEL_TABLE_NAME.equals(cell.getStringCellValue())) {
							Table from = new Table();
							from.name = row.getCell(7).getStringCellValue();
							String[] froms = from.name.split("\\.");
							if (froms.length > 1) {
								from.schema = froms[0];
								from.name = froms[1];
							} else {
								from.schema = "";
							}
							Table to = new Table();
							to.name = row.getCell(1).getStringCellValue();
							String[] tos = to.name.split("\\.");
							if (tos.length > 1) {
								to.schema = tos[0];
								to.name = tos[1];
							} else {
								to.schema = "";
							}
							transfer.from = from;
							transfer.to = to;
							transfer.fromDb = row.getCell(8).getStringCellValue();
							transfer.toDb = row.getCell(2).getStringCellValue();
							transfer.transformId = from.name + "_to_" + to.name;
						} else if (LABEL_COLUMN_LOGICAL_NAME.equals(cell.getStringCellValue())) {
							// 次の行からはデータからむ
							data = true;
						} else if (data) {
							XSSFCell cell1 = row.getCell(1);
							if (cell1 == null)
								continue;
							String toName = cell1.getStringCellValue();
							if ("".equals(toName))
								continue;
							Column from = new Column();
							Column to = new Column();

							TransferColumn transferColumn = new TransferColumn(from, to);
							XSSFCell cell8 = row.getCell(8);
							if (cell8 != null) {

								String fromName = cell8.getStringCellValue();
								from.name = fromName;
								to.name = toName;
								if ("".equals(fromName)) {
									transferColumn.type = "const";
									XSSFCell cell5 = row.getCell(5);
									if (cell5 != null) {
										switch (cell5.getCellType()) {
										case BOOLEAN:
											transferColumn.expression = String.valueOf(cell5.getBooleanCellValue());
											break;
										}
									}
									
								} else {
									transferColumn.type = "copy";
								}
							} else {

								transferColumn.type = "const";
								XSSFCell cell5 = row.getCell(5);
								if (cell5 != null) {
									switch (cell5.getCellType()) {
									case BOOLEAN:
										transferColumn.expression = String.valueOf(cell5.getBooleanCellValue());
										break;
									}
								}
								;
							}
							if (transfer.transferColumnList.isEmpty()) {
								transferColumn.pk = "1";
							}

							transfer.transferColumnList.add(transferColumn);
						}
					}
				}
				if (transfer.transformId != null) {
					transferList.add(transfer);
				}
			}
			System.out.println(values(transferList));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String values(List<Transfer> transferList) {
		StringBuilder builder = new StringBuilder(1024);

		builder.append("insert into sym_transform_table"
				+ "(transform_id, source_node_group_id, target_node_group_id, transform_point,"
				+ "source_schema_name, source_table_name," + "target_schema_name, target_table_name,"
				+ "delete_action, transform_order, column_policy, update_first,"
				+ "last_update_by, last_update_time, create_time)\n" + "values\n");
		for (int i = 0; i < transferList.size(); i++) {
			if (i != 0) {
				builder.append(",\n");
			}
			builder.append(transferList.get(i).values(i + 1));
		}
		builder.append(";\n");
		for (int i = 0; i < transferList.size(); i++) {
			// if (i != 0) {
			// builder.append(",\n");
			// }
			builder.append(transferList.get(i));
		}
		return builder.toString();
	}
}
