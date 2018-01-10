/**
 * (c) 2012 uchicom
 */
package com.uchicom.dbd;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.uchicom.dbd.util.CellUtil;

/**
 * @author Uchiyama Shigeki
 * 
 */
public class TableDefineBean extends AbstractCellBean<TableDefineBean> {

	/** 項番 */
	private String kouban = null;
	/** カラム論理名 */
	private String columnName = null;
	/** カラム物理名 */
	private String columnId = null;
	/** 型 */
	private String kata = null;
	/** 桁 */
	private String keta = null;
	/** NOT NULL */
	private String notNull = null;
	/** 初期値 */
	private String initial = null;
	/** プライマリーキー */
	private String primaryKey = null;
	/** インデックス */
	private String index = null;
	/** コメント */
	private String comment = null;

	/**
	 * 引数なしコンストラクタ
	 */
	public TableDefineBean() {
		super(new String[] { TITLE_KOUBAN, TITLE_COLUMN_MEI, TITLE_COLUMN_REAL,
				TITLE_KATA, TITLE_KETA, TITLE_NULL, TITLE_INITIAL, TITLE_PK,
				TITLE_INDEX, TITLE_COMMENT }, new int[] { -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1 });
	}

	/**
	 * 引数ありコンストラクタ
	 * 
	 * @param kouban
	 * @param columnName
	 * @param columnId
	 * @param keta
	 * @param kata
	 * @param notNull
	 * @param initial
	 * @param primaryKey
	 * @param index
	 * @param comment
	 */
	public TableDefineBean(String kouban, String columnName, String columnId,
			String kata, String keta, String notNull, String initial,
			String primaryKey, String index, String comment) {
		this();
		this.kouban = kouban;
		this.columnName = columnName;
		this.columnId = columnId;
		this.keta = keta;
		this.kata = kata;
		this.notNull = notNull;
		this.initial = initial;
		this.primaryKey = primaryKey;
		this.index = index;
		this.comment = comment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cymsgk.auto.bean.AbstractCellBean#create(org.apache.poi.hssf.usermodel
	 * .HSSFRow)
	 */
	@Override
	public TableDefineBean create(HSSFRow row) {
		TableDefineBean bean = new TableDefineBean(
				decimalFormat.format(Double.parseDouble(CellUtil
						.getCellValue(row.getCell(titleIndex[0])))),
				CellUtil.getCellValue(row.getCell(titleIndex[1])),
				CellUtil.getCellValue(row.getCell(titleIndex[2])),
				CellUtil.getCellValue(row.getCell(titleIndex[3])),
				CellUtil.getCellValue(row.getCell(titleIndex[4])),
				CellUtil.getCellValue(row.getCell(titleIndex[5])),
				CellUtil.getCellValue(row.getCell(titleIndex[6])),
				CellUtil.getCellValue(row.getCell(titleIndex[7])),
				CellUtil.getCellValue(row.getCell(titleIndex[8])),
				CellUtil.getCellValue(row.getCell(titleIndex[9])));
		return bean;
	}

	/**
	 * koubanを取得します。
	 * 
	 * @return kouban
	 */
	public String getKouban() {
		return kouban;
	}

	/**
	 * koubanを設定します。
	 * 
	 * @param kouban
	 */
	public void setKouban(String kouban) {
		this.kouban = kouban;
	}

	/**
	 * columnNameを取得します。
	 * 
	 * @return columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * columnNameを設定します。
	 * 
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * columnIdを取得します。
	 * 
	 * @return columnId
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * columnIdを設定します。
	 * 
	 * @param columnId
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 * kataを取得します。
	 * 
	 * @return kata
	 */
	public String getKata() {
		return kata;
	}

	/**
	 * kataを設定します。
	 * 
	 * @param kata
	 */
	public void setKata(String kata) {
		this.kata = kata;
	}

	/**
	 * ketaを取得します。
	 * 
	 * @return keta
	 */
	public String getKeta() {
		return keta;
	}

	/**
	 * ketaを設定します。
	 * 
	 * @param keta
	 */
	public void setKeta(String keta) {
		this.keta = keta;
	}

	/**
	 * notNullを取得します。
	 * 
	 * @return notNull
	 */
	public String getNotNull() {
		return notNull;
	}

	/**
	 * notNullを設定します。
	 * 
	 * @param notNull
	 */
	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}

	/**
	 * initialを取得します。
	 * 
	 * @return initial
	 */
	public String getInitial() {
		return initial;
	}

	/**
	 * initialを設定します。
	 * 
	 * @param initial
	 */
	public void setInitial(String initial) {
		this.initial = initial;
	}

	/**
	 * primaryKeyを取得します。
	 * 
	 * @return primaryKey
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * primaryKeyを設定します。
	 * 
	 * @param primaryKey
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * indexを取得します。
	 * 
	 * @return index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * indexを設定します。
	 * 
	 * @param index
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * commentを取得します。
	 * 
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * commentを設定します。
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
