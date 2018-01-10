package com.uchicom.dbd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

import com.uchicom.dbd.util.CellUtil;

public class TableBean extends AbstractCellBean<TableBean> {

	public static final String[] titleString = new String[]{
		TITLE_KOUBAN,
		TITLE_SCHEMA_MEI,
		TITLE_TABLE_MEI,
		TITLE_TABLE_REAL,
		TITLE_TABLE_BASE,
		TITLE_MST,
		TITLE_COMMENT
	};
	public static int[] titleIndex = new int[]{-1,-1,-1,-1,-1,-1};
	/** 項番 */
	private String kouban = null;
	/** スキーマ論理名 */
	private String schemaName = null;
	/** テーブル論理名 */
	private String tableName = null;
	/** テーブル物理名 */
	private String tableId = null;
	/** 作成元テーブル論理名 */
	private String baseTableName = null;
	/** コメント */
	private String comment = null;

	public TableBean() {
		super(titleString, titleIndex);
		
	}
	private TableBean(String kouban, String schemaName, String tableName, String tableId,
			String baseTableName, String comment) {
		this();
		this.kouban = kouban;
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.tableId = tableId;
		this.baseTableName = baseTableName;
		this.comment = comment;
	}

	/**
	 * koubanを取得します。
	 * @return kouban
	 */
	public String getKouban() {
		return kouban;
	}
	/**
	 * koubanを設定します。
	 * @param kouban
	 */
	public void setKouban(String kouban) {
		this.kouban = kouban;
	}
	/**
	 * schemaNameを取得します。
	 * @return schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}
	/**
	 * schemaNameを設定します。
	 * @param schemaName
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	/**
	 * tableNameを取得します。
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * tableNameを設定します。
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * tableIdを取得します。
	 * @return tableId
	 */
	public String getTableId() {
		return tableId;
	}
	/**
	 * tableIdを設定します。
	 * @param tableId
	 */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	/**
	 * baseTableNameを取得します。
	 * @return baseTableName
	 */
	public String getBaseTableName() {
		return baseTableName;
	}
	/**
	 * baseTableNameを設定します。
	 * @param baseTableName
	 */
	public void setBaseTableName(String baseTableName) {
		this.baseTableName = baseTableName;
	}
	/**
	 * commentを取得します。
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * commentを設定します。
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * テーブル一覧情報のレコードからテーブル情報を作成する。
	 */
	@Override
	public TableBean create(HSSFRow row) {
		TableBean bean = new TableBean(
				decimalFormat.format(Double.parseDouble(CellUtil.getCellValue(row.getCell(titleIndex[0])))),
				CellUtil.getCellValue(row.getCell(titleIndex[1])),
				CellUtil.getCellValue(row.getCell(titleIndex[2])),
				CellUtil.getCellValue(row.getCell(titleIndex[3])),
				CellUtil.getCellValue(row.getCell(titleIndex[4])),
				CellUtil.getCellValue(row.getCell(titleIndex[5]))
				);
		return bean;
	}
	
	/**
	 * テーブル定義書シートを作成する。
	 * @param workbook
	 */
	public void createSheet(HSSFWorkbook workbook, List<ColumnBean> columnList, List<TableBean> tableList, File file, Map<String, List<String>> erdMap) {
		HSSFSheet sheet = workbook.createSheet("テーブル定義書（" + tableName + "）");
		int iRow = 0;
		HSSFRow row = sheet.createRow(iRow);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("テーブル定義書");
		iRow += 2;
		//テーブル論理名
		row = sheet.createRow(iRow);
		cell = row.createCell(1);
		cell.setCellValue(TITLE_TABLE_MEI);
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setFillBackgroundColor((short)31);
		titleStyle.setFillForegroundColor((short)44);
		titleStyle.setFillPattern(FillPatternType.SQUARES);
		HSSFCellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setBorderTop(BorderStyle.THIN);
		cell.setCellStyle(titleStyle);
		cell = row.createCell(2);
		cell.setCellValue(tableName);
		iRow++;
		//物理名
		row = sheet.createRow(iRow);
		cell = row.createCell(1);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("テーブル物理名");
		cell = row.createCell(2);
		cell.setCellStyle(dataStyle);
		cell.setCellValue(tableId);
		iRow++;
		//コメント
		row = sheet.createRow(iRow);
		cell = row.createCell(1);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("コメント");
		cell = row.createCell(2);
		cell.setCellStyle(dataStyle);
		cell.setCellValue(comment);
		
		iRow += 2;
		//カラム情報行
		row = sheet.createRow(iRow);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("項番");
		cell = row.createCell(1);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("カラム論理名");
		cell = row.createCell(2);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("カラム物理名");

		cell = row.createCell(3);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("型");

		cell = row.createCell(4);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("桁");

		cell = row.createCell(5);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("ナル値");

		cell = row.createCell(6);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("初期値");


		cell = row.createCell(7);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("PK");

		cell = row.createCell(8);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("インデックス");

		cell = row.createCell(9);
		cell.setCellStyle(titleStyle);
		cell.setCellValue("コメント");

		iRow++;
		int startRow = iRow;
		//自動カラムを追加する。
		for (ColumnBean columnBean : columnList) {
			//作成元のテーブルがある場合
			if ((baseTableName == null || "".equals(baseTableName))) {
				if (columnBean.isHistory()) {
					continue;
				}
			} else {
				if (!columnBean.isHistory()) {
					continue;
				}
			}
			row = sheet.createRow(iRow);
			cell = row.createCell(0);
			cell.setCellFormula("row()-" + startRow);
			cell.setCellStyle(dataStyle);
			cell = row.createCell(1);
			cell.setCellValue(columnBean.getColumnName());
			cell.setCellStyle(dataStyle);
			cell = row.createCell(2);
			cell.setCellValue(columnBean.getColumnId());
			cell.setCellStyle(dataStyle);
			cell = row.createCell(3);
			cell.setCellValue(columnBean.getKata());
			cell.setCellStyle(dataStyle);
			cell = row.createCell(4);
			cell.setCellValue(columnBean.getKeta());
			cell.setCellStyle(dataStyle);
			cell = row.createCell(5);
			cell.setCellStyle(dataStyle);
			cell = row.createCell(6);
			cell.setCellStyle(dataStyle);
			cell = row.createCell(7);
			cell.setCellStyle(dataStyle);
			cell = row.createCell(8);
			cell.setCellStyle(dataStyle);
			cell = row.createCell(9);
			cell.setCellValue(columnBean.getComment());
			cell.setCellStyle(dataStyle);
			iRow++;
		}
		
		if ((baseTableName == null || "".equals(baseTableName))) {
			//現用テーブルの場合
			//10行程度先に先に作成しておく。
			List<String> list = erdMap.get(tableName);
			if (list != null) {
				for (String columnName : list) {
					row = sheet.createRow(iRow);
					cell = row.createCell(0);
					cell.setCellFormula("row()-" + startRow);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(1);
					cell.setCellValue(columnName);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(2);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(3);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(4);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(5);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(6);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(7);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(8);
					cell.setCellStyle(dataStyle);
					cell = row.createCell(9);
					cell.setCellStyle(dataStyle);
					iRow++;
				}
			}
		} else {
			//履歴の場合は正確に退避元テーブルの構成を追加すればよい。
			
			for (TableBean bean : tableList) {
				if (baseTableName.equals(bean.getTableName())) {
					File tableFile = new File(file.getParentFile(), bean.getKouban() + "_テーブル定義書(" + bean.getTableName() + ").xls");
					if (!tableFile.isDirectory() && tableFile.exists()) { 
						//退避元テーブルが存在すれば追加
						try {
							HSSFWorkbook tableWorkbook = new HSSFWorkbook(new FileInputStream(tableFile));
							HSSFSheet tableSheet = tableWorkbook.getSheet("テーブル定義書（" + bean.getTableName() + "）");
							int iMaxRow = tableSheet.getLastRowNum();
							for (int i = startRow; i < iMaxRow; i++) {
								HSSFRow tableRow = tableSheet.getRow(i);
								row = sheet.createRow(iRow);
								cell = row.createCell(0);
								cell.setCellFormula("row()-" + startRow);
								cell.setCellStyle(dataStyle);
								cell = row.createCell(1);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(1)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(2);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(2)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(3);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(3)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(4);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(4)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(5);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(5)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(6);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(6)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(7);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(7)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(8);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(8)));
								cell.setCellStyle(dataStyle);
								cell = row.createCell(9);
								cell.setCellValue(CellUtil.getCellValue(tableRow.getCell(9)));
								cell.setCellStyle(dataStyle);
								iRow++;
							}
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} else {
						//存在しない場合はこれ以上の処理は不要
					}
					break;	
				}
			}
		}
	}

	
}
