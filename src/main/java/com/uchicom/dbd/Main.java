// (c) 2017 uchicom
package com.uchicom.dbd;

import java.io.File;

import com.uchicom.dbd.entity.Table;
import com.uchicom.dbd.generator.MigrationGenerator;
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
		if (args.length > 0) {
			MigrationGenerator generator = new MigrationGenerator();
			generator.generate(new File(args[0]));
		} else {
			for (Table table : DbdFactory.getTableList(new File("sample/table.xml"))) {
				System.out.println(table.name);
			}
		}
	}

}
