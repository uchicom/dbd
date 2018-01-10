package com.uchicom.dbd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;

import com.uchicom.dbd.util.CellUtil;

public abstract class AbstractCellBean <T> {

	protected DecimalFormat decimalFormat = new DecimalFormat("00");
	//文字系は外部定義にしたいなー
	public static final String TITLE_KOUBAN = "項番";
	public static final String TITLE_SCHEMA_MEI = "スキーマ論理名";
	public static final String TITLE_SCHEMA_REAL = "スキーマ物理名";
	public static final String TITLE_MOJI_CODE = "文字コード";
	public static final String TITLE_COMMENT = "コメント";
	public static final String TITLE_TABLE_MEI = "テーブル論理名";
	public static final String TITLE_TABLE_REAL = "テーブル物理名";
	public static final String TITLE_TABLE_BASE = "履歴作成元テーブル論理名";
	public static final String TITLE_COLUMN_MEI = "カラム論理名";
	public static final String TITLE_COLUMN_REAL = "カラム物理名";
	public static final String TITLE_KATA = "型";
	public static final String TITLE_KETA = "桁";
	public static final String TITLE_CURRENT = "現用";
	public static final String TITLE_HISTORY = "履歴";
	public static final String TITLE_NULL = "ナル値";
	public static final String TITLE_PK = "PK";
	public static final String TITLE_INITIAL = "初期値";
	public static final String TITLE_INDEX = "インデックス";
	public static final String TITLE_MST = "マスタ";
	
	protected String[] titleString = null;
	protected int[] titleIndex = null;
	
	protected Map<String, String> keyValueMap = new HashMap<String, String>();

	public AbstractCellBean(String[] titleString, int[] titleIndex) {
		this.titleString = titleString;
		this.titleIndex = titleIndex;
		
	}
	public String get(String key) {
		return keyValueMap.get(key);
	}
	
	public List<T> getList(HSSFSheet sheet) throws Exception {
		List<T> list = new ArrayList<T>();
		int iStartRow = sheet.getFirstRowNum();
		int iLastRow = sheet.getLastRowNum();
		
		//タイトルチェックフラグ
		boolean bTitle = true;
		for (int iRow = iStartRow; iRow <= iLastRow; iRow++) {
			HSSFRow row = sheet.getRow(iRow);
			String tmpCellValue = null;
			if (row == null) {
				continue;
			}
			if (bTitle) {
				Iterator<Cell> iterator = row.cellIterator();
				//タイトルチェック
				while (iterator.hasNext()) {
					Cell cell = iterator.next();
					String cellValue = cell.getStringCellValue();
					if (tmpCellValue == null) {
						tmpCellValue = cellValue;
					} else {
						//タイトルが見つかるまで、キーバリューマップに値を保持する。
						keyValueMap.put(tmpCellValue, cellValue);
					}
					//項番列を取得
					for (int iTitle = 0; iTitle < titleString.length; iTitle++) {
						if (titleIndex[iTitle] > -1) continue;//すでに検索済みのタイトルは無視。
						String title = titleString[iTitle];
						if (title.equals(cellValue)) {
							titleIndex[iTitle] = cell.getColumnIndex();
						} else {
							break;
						}
					}
				}
				if (titleIndex[0] > -1) {
					//タイトル行あり
					if (titleIndex[titleString.length - 1] > -1) {
						//最後のタイトルまで取得できた
						//次からは一覧データ
						bTitle = false;
					} else {
						//最後のタイトルまで取得できていない。エラー終了
						throw new Exception("項番タイトル行が不正です。");
					}
				}
			} else {
				//データ一覧の抽出処理
				if (!"".equals(CellUtil.getCellValue(row.getCell(0)))) {
					list.add(create(row));
				} else {
					break;
				}
			}
		}
		return list;
	}
	
	public abstract T create(HSSFRow row);

}
