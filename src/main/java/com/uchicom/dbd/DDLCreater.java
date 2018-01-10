package com.uchicom.dbd;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * (c) 2012 uchicom
 */

/**
 * @author Uchiyama Shigeki
 *
 */
public class DDLCreater {

	public static void main(String[] args) {
		DDLCreater creater  = new DDLCreater();
		creater.execute();
		JOptionPane.showMessageDialog(null, "処理が完了しました。");
	}
	
	public DDLCreater() {
		
	}
	
	public int execute() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileFilter(){

				@Override
				public boolean accept(File arg0) {
					return (arg0 != null && (arg0.isDirectory() || arg0.getName().endsWith("テーブル定義書.xls")));

				}

				@Override
				public String getDescription() {
					
					return "*テーブル定義書.xls";
				}
			
			});
			chooser.showOpenDialog(null);
			File selectFile = chooser.getSelectedFile();
//			if (selectFile != null && selectFile.canRead()) {
//				FileInputStream stream = null;
//				SchemaBean schemaBean = new SchemaBean();
//				TableBean tableBean = new TableBean();
//				try {
//					stream = new FileInputStream(selectFile);
//					HSSFWorkbook book = new HSSFWorkbook(stream);
//					HSSFSheet schemaSheet = book.getSheet(SHEET_SCHEMA);
//					List<SchemaBean> schemaList = schemaBean.getList(schemaSheet);
//					HSSFSheet tableSheet = book.getSheet(SHEET_TABLE);
//					List<TableBean> tableList = tableBean.getList(tableSheet);
//					//スキーマフォルダが存在するかを確認する。なければ作成番号、違いで存在する場合は無視するか。
//					//存在する場合は作成しない。フォルダは同じテーブル定義書配下に生成する。
//					//項番をフォルダ名の頭につけるのでkoubanもBean定義に追加する。
//					Map<String, String> dirMap = new HashMap<String, String>();
//					for (SchemaBean bean : schemaList) {
//						String dirName = bean.getKouban() + "_" + bean.getSchemaName();
//						dirMap.put(bean.getSchemaName(), dirName);
//						File file = new File(selectFile.getParentFile(), dirName);
//						if (!file.isDirectory()) {
//							file.mkdir();
//						} else {
//							System.out.println("すでに存在しています。" + file.getName());
//						}
//						
//					}
//					
//					for (TableBean bean : tableList) {
//						String dirName = dirMap.get(bean.getSchemaName());
//						if (dirName == null) {
//							System.out.println("テーブル一覧のスキーマ論理名が一致していません。");
//							continue;
//						}
//						File file = new File(selectFile.getParentFile(), dirName + File.separator + bean.getKouban() + "_テーブル定義書(" + bean.getTableName() + ").xls");
//						if (file.isDirectory() || !file.exists()) {
//							file.createNewFile();
//							FileOutputStream oStream = new FileOutputStream(file);
//							HSSFWorkbook workbook = new HSSFWorkbook();
//							bean.createSheet(workbook);
//							workbook.write(oStream);
//							oStream.flush();
//							oStream.close();
//						} else {
//							System.out.println("すでに存在しています。" + file.getName());
//							continue;
//						}
//						
//					}
//					
//					
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//					JOptionPane.showMessageDialog(null, "ファイルが見つかりませんでした。");
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//					JOptionPane.showMessageDialog(null, "ファイルの読み込みに失敗しました。");
//				} catch (Exception e) {
//					e.printStackTrace();
//					JOptionPane.showMessageDialog(null, e.getMessage());				
//				} finally {
//					if (stream != null) {
//						
//						try {
//							stream.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							stream = null;
//						}
//					}
//				}
//			} else {
//				JOptionPane.showMessageDialog(null, "有効なファイルが選択されませんでした。");
//			}
		} catch (Exception e) {
			//ファイナリー
		} finally {
			//ファイナリー
		}
		return 0;
	}
}
