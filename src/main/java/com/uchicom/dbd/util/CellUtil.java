package com.uchicom.dbd.util;

import org.apache.poi.ss.usermodel.Cell;

public class CellUtil {
	
	public static String getCellValue(Cell cell) {
		String cellValue = null;
		if (cell != null) {
			switch(cell.getCellType()) {
				case Cell.CELL_TYPE_STRING :
					cellValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_FORMULA :
					cellValue = Double.toString(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC :
					cellValue = Double.toString(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN :
					cellValue = Boolean.toString(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_BLANK :
					cellValue = "";
					break;
				case Cell.CELL_TYPE_ERROR :
					cellValue = Byte.toString(cell.getErrorCellValue());
					break;
				default :
					break;
			}
		}
		if (cellValue == null) {
			cellValue = "";
		}
		return cellValue;
	}

}
