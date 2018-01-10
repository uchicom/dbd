package com.uchicom.dbd;
/**
 * (c) 2012 uchicom
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * DB定義書からテーブル定義書を生成する。
 * @author Uchiyama Shigeki
 *
 */
public class DesignGenerator {

	public static final String SHEET_SCHEMA = "スキーマ一覧";
	public static final String SHEET_TABLE = "テーブル一覧";
	public static final String ER_DIAGRAM = "ER図";
	public static final String AUTO_COLUMN = "自動追加カラム一覧";
	
	public static final String CURRENT_DIRECTORY = "currentDirectory";
	public static final String CONF_PROPERTIES = "./gen_conf.properies";
	public static final String CREATE_DATABASES_FILENAME = "create_databases.sql";
	
	private Properties prop = new Properties();
	public static void main(String[] args){
		DesignGenerator deGen = new DesignGenerator();
		deGen.execute();
		JOptionPane.showMessageDialog(null, "処理が完了しました。");
	}
	
	/**
	 * カレントファイル情報とかを設定ファイルから読み込む
	 */
	public void initProp() {
		FileInputStream fis = null;
		
		try {
			File file = new File(CONF_PROPERTIES);
			if (!file.exists()) {
				file.createNewFile();
			}
			fis = new FileInputStream(file);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					fis = null;
				}
			}
		}
	}
	
	/**
	 * カレントファイル情報とかを設定ファイルに保存する
	 */
	public void updateProp() {
		FileOutputStream fos = null;
		try {
			fos =  new FileOutputStream(CONF_PROPERTIES);
			prop.store(fos, "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					fos = null;
				}
			}
		}
	}
	
	public void execute() {
		initProp();
		String currentDirectory = prop.getProperty(CURRENT_DIRECTORY);
		File currentFile = null;
		if (currentDirectory != null && !"".equals(currentDirectory)) {
			currentFile = new File(currentDirectory);
		}
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				return (arg0 != null && (arg0.isDirectory() || arg0.getName().endsWith("DB定義書.xls")));
				
			}

			@Override
			public String getDescription() {
				
				return "*DB定義書.xls";
			}
		
		});
		if (currentFile != null && currentFile.exists()) {
			chooser.setSelectedFile(currentFile);
		}
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectFile = chooser.getSelectedFile();
			if (selectFile != null) {
				prop.setProperty(CURRENT_DIRECTORY, selectFile.getPath());
				updateProp();
			}
			if (selectFile != null && selectFile.canRead()) {
				FileInputStream stream = null;
				SchemaBean schemaBean = new SchemaBean();
				TableBean tableBean = new TableBean();
				ColumnBean columnBean = new ColumnBean();
				ErdBean erdBean = new ErdBean();
				FileOutputStream createDbStream = null;
				try {
					stream = new FileInputStream(selectFile);
					createDbStream = new FileOutputStream(new File(selectFile.getParentFile(),CREATE_DATABASES_FILENAME));
					HSSFWorkbook book = new HSSFWorkbook(stream);
					HSSFSheet schemaSheet = book.getSheet(SHEET_SCHEMA);
					List<SchemaBean> schemaList = schemaBean.getList(schemaSheet);
					HSSFSheet tableSheet = book.getSheet(SHEET_TABLE);
					List<TableBean> tableList = tableBean.getList(tableSheet);
					HSSFSheet columnSheet = book.getSheet(AUTO_COLUMN);
					List<ColumnBean> columnList = columnBean.getList(columnSheet);
					HSSFSheet erdSheet = book.getSheet(ER_DIAGRAM);
					Map<String, List<String>> erdMap = erdBean.getMap(erdSheet);
					//スキーマフォルダが存在するかを確認する。なければ作成番号、違いで存在する場合は無視するか。
					//存在する場合は作成しない。フォルダは同じテーブル定義書配下に生成する。
					//項番をフォルダ名の頭につけるのでkoubanもBean定義に追加する。
					Map<String, String> dirMap = new HashMap<String, String>();
					
					//スキーマの数だけ繰り返し
					for (SchemaBean bean : schemaList) {
						
						String dirName = bean.getKouban() + "_" + bean.getSchemaName();
						//create database SQLの書き出し
						if (bean.getSchemaId() != null &&
								bean.getCharCode() != null) {
							createDbStream.write("create database ".getBytes());
							createDbStream.write(bean.getSchemaId().getBytes());
							createDbStream.write(" character set ".getBytes());
							createDbStream.write(bean.getCharCode().getBytes());
							createDbStream.write(";\n".getBytes());
							createDbStream.flush();
						}
						dirMap.put(bean.getSchemaName(), dirName);
						File file = new File(selectFile.getParentFile(), dirName);
						if (!file.isDirectory()) {
							file.mkdir();
						} else {
							System.out.println("すでに存在しています。" + file.getName());
						}
					}
					
					for (TableBean bean : tableList) {
						//テーブルの数だけ繰り返し。
						String dirName = dirMap.get(bean.getSchemaName());
						if (dirName == null) {
							System.out.println("テーブル一覧のスキーマ論理名が一致していません。");
							continue;
						}
						//テーブル定義書の書き出し（すでに存在している場合は書き出さない）
						File file = new File(selectFile.getParentFile(), dirName + File.separator + bean.getKouban() + "_テーブル定義書(" + bean.getTableName() + ").xls");
						if (file.isDirectory() || !file.exists()) {
							file.createNewFile();
							FileOutputStream oStream = new FileOutputStream(file);
							HSSFWorkbook workbook = new HSSFWorkbook();
							bean.createSheet(workbook, columnList, tableList, file, erdMap);
							workbook.write(oStream);
							oStream.flush();
							oStream.close();
							oStream = null;
						} else {
							System.out.println("すでに存在しています。" + file.getName());
						}


						TableDefineBean tableDefineBean = new TableDefineBean();
						FileInputStream tableDefinestream = new FileInputStream(file);

						HSSFWorkbook tableBook = new HSSFWorkbook(tableDefinestream);
						
						HSSFSheet tableDefineSheet = tableBook.getSheetAt(0);
					
						List<TableDefineBean> tableDefineList = tableDefineBean.getList(tableDefineSheet);
						tableDefinestream.close();
						

						System.out.println(tableDefineList);
						//テーブル定義のDML作成、すでに存在していても最新のものに更新する。
						File dmlFile = new File(selectFile.getParentFile(), dirName + File.separator + bean.getKouban() + "_create_table_" + bean.getTableId() + ".sql");
						FileOutputStream dmlStream = new FileOutputStream(dmlFile);
						StringBuffer strBuff = new StringBuffer(1024);
					    strBuff.append("create table ");
					    strBuff.append(bean.getTableId());
					    strBuff.append(" (");
					    for (int i = 0; i < tableDefineList.size(); i++) {
					    	TableDefineBean defineBean = tableDefineList.get(i);
					    	if (i > 0) {
					    		strBuff.append(",\n");
					    	}
						    strBuff.append(defineBean.getColumnId());
					    	strBuff.append(" ");
						    strBuff.append(defineBean.getKata());
						    if (defineBean.getKeta() != null && !"".equals(defineBean.getKeta())) {
						    	strBuff.append("(");
						    	strBuff.append(defineBean.getKeta());
						    	strBuff.append(")");
						    }
					    	strBuff.append(" ");
						    strBuff.append(defineBean.getNotNull());
						    if (defineBean.getInitial() != null && !"".equals(defineBean.getInitial())) {
						    	strBuff.append(" default ");
						    	strBuff.append(defineBean.getInitial());
						    }
					    }
					    strBuff.append(" );");
						dmlStream.write(strBuff.toString().getBytes());
						dmlStream.flush();
						dmlStream.close();
						
					}
					
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "ファイルが見つかりませんでした。");
					
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "ファイルの読み込みに失敗しました。");
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage());				
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e) {
							e.printStackTrace();
							stream = null;
						}
					}
					if (createDbStream != null) {
						try {
							createDbStream.close();
						} catch (IOException e) {
							e.printStackTrace();
							createDbStream = null;
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "有効なファイルが選択されませんでした。");
			}
		}
	}	
	
}
