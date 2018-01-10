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
public class ColumnBean extends AbstractCellBean<ColumnBean> {

	public static final String[] titleString = new String[] { TITLE_KOUBAN,
			TITLE_COLUMN_MEI, TITLE_COLUMN_REAL, TITLE_KATA, TITLE_KETA,
			TITLE_COMMENT, TITLE_HISTORY };
	public static final int[] titleIndex = new int[] { -1, -1, -1, -1, -1, -1,
			-1 };

	/** 項番 */
	private String kouban = null;
	/** スキーマ論理名 */
	private String columnName = null;
	/** テーブル論理名 */
	private String columnId = null;
	/** テーブル物理名 */
	private String kata = null;
	/** 作成元テーブル論理名 */
	private String keta = null;
	/** コメント */
	private String comment = null;
	/** 履歴 */
	private boolean history = false;

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

	/**
	 * historyを取得します。
	 * 
	 * @return history
	 */
	public boolean isHistory() {
		return history;
	}

	/**
	 * historyを設定します。
	 * 
	 * @param history
	 */
	public void setHistory(boolean history) {
		this.history = history;
	}

	/**
	 * @param titleString
	 * @param titleIndex
	 */
	public ColumnBean() {
		super(titleString, titleIndex);
	}

	public ColumnBean(String kouban, String columnName, String columnId,
			String kata, String keta, String comment, boolean history) {
		this();
		this.kouban = kouban;
		this.columnName = columnName;
		this.columnId = columnId;
		this.kata = kata;
		this.keta = keta;
		this.history = history;
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
	public ColumnBean create(HSSFRow row) {
		return new ColumnBean(decimalFormat.format(Double.parseDouble(CellUtil
				.getCellValue(row.getCell(titleIndex[0])))),
				CellUtil.getCellValue(row.getCell(titleIndex[1])),
				CellUtil.getCellValue(row.getCell(titleIndex[2])),
				CellUtil.getCellValue(row.getCell(titleIndex[3])),
				CellUtil.getCellValue(row.getCell(titleIndex[4])),
				CellUtil.getCellValue(row.getCell(titleIndex[5])),
				"●".equals(CellUtil.getCellValue(row.getCell(titleIndex[6]))));
	}
}
