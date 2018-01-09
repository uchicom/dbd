// (c) 2017 uchicom
package com.uchicom.dbd;

import java.io.File;

import com.uchicom.dbd.entity.Table;
import com.uchicom.dbd.util.DbdFactory;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (Table table : DbdFactory.getTableList(new File("sample/table.xml"))) {
			System.out.println(table.name);
		}
	}

}
