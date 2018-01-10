/**
 * (c) 2012 uchicom
 */
package com.uchicom.dbd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.uchicom.dbd.util.CellUtil;


/**
 * @author Uchiyama Shigeki
 *
 */
public class ErdBean {

	public Map<String, List<String>> getMap(HSSFSheet sheet) {

		int iStartRow = sheet.getFirstRowNum();
		int iLastRow = sheet.getLastRowNum();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map<Integer, List<String>> tmpMap = new HashMap<Integer, List<String>>();
		int iMaxCol = -1;
		for (int iRow = iStartRow; iRow <= iLastRow; iRow++) {
			HSSFRow row = sheet.getRow(iRow);
			if (row == null) continue;
			int iStartCol = row.getFirstCellNum();
			int iLastCol = row.getLastCellNum();
			if (iMaxCol < iLastCol) {
				iMaxCol = iLastCol;
			}
			for (int iCol = iStartCol; iCol <= iMaxCol; iCol++) {
				String cellValue = CellUtil.getCellValue(row.getCell(iCol));
				if ("".equals(cellValue)) {
					if (tmpMap.containsKey(iCol)) {
						tmpMap.remove(iCol);
					}
					continue;
				} else {
					if (tmpMap.containsKey(iCol)) {
						tmpMap.get(iCol).add(cellValue);
					} else {
						//初回発見時は空リストをMapに追加する。
						List<String> list = new ArrayList<String>();
						map.put(cellValue, list);
						tmpMap.put(iCol, list);
					}
				}
			}
		}
		return map;
	}

}
