package com.uchicom.dbd;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.uchicom.dbd.util.CellUtil;

public class SchemaBean extends AbstractCellBean<SchemaBean> {

	public static final String[] titleString = new String[]{
		TITLE_KOUBAN,
		TITLE_SCHEMA_MEI,
		TITLE_SCHEMA_REAL,
		TITLE_MOJI_CODE,
		TITLE_COMMENT};
	public static final int[] titleIndex = new int[]{-1,-1,-1,-1,-1};
	/** 項番 */
	private String kouban = null;
	/** スキーマ論理名 */
	private String schemaName = null;
	/** スキーマ物理名 */
	private String schemaId = null;
	/** 文字コード */
	private String charCode = null;
	/** コメント */
	private String comment = null;
	
	public SchemaBean() {
		super(titleString, titleIndex);
	}
	
	private SchemaBean(String kouban, String schemaName, String schemaId, String charCode, String comment) {
		this();
		this.kouban = kouban;
		this.schemaName = schemaName;
		this.schemaId = schemaId;
		this.charCode = charCode;
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
	 * schemaIdを取得します。
	 * @return schemaId
	 */
	public String getSchemaId() {
		return schemaId;
	}
	/**
	 * schemaIdを設定します。
	 * @param schemaId
	 */
	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}
	/**
	 * charCodeを取得します。
	 * @return charCode
	 */
	public String getCharCode() {
		return charCode;
	}
	/**
	 * charCodeを設定します。
	 * @param charCode
	 */
	public void setCharCode(String charCode) {
		this.charCode = charCode;
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

	@Override
	public SchemaBean create(HSSFRow row) {
		SchemaBean bean = new SchemaBean(
		decimalFormat.format(Double.parseDouble(CellUtil.getCellValue(row.getCell(titleIndex[0])))),
		CellUtil.getCellValue(row.getCell(titleIndex[1])),
		CellUtil.getCellValue(row.getCell(titleIndex[2])),
		CellUtil.getCellValue(row.getCell(titleIndex[3])),
		CellUtil.getCellValue(row.getCell(titleIndex[4])));
		return bean;
	}
	
	
}
