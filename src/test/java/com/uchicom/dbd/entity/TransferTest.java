// (c) 2018 uchicom
package com.uchicom.dbd.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class TransferTest {

	/**
	 * 1件
	 */
	@Test
	public void toStringTest1() {
		Transfer transfer = new Transfer();
		transfer.from = new Table();
		transfer.from.name="customer";
		transfer.to = new Table();
		transfer.to.name = "m.customer";
		List<TransferColumn> transferColumnList = new ArrayList<>();
		Column from = new Column();
		from.name = "fa";
		Column to = new Column();
		to.name = "ta";
		transferColumnList.add(new TransferColumn(from, to));
		transfer.transferColumnList = transferColumnList;
		System.out.println(transfer);
	}
	/**
	 * 2件
	 */
	@Test
	public void toStringTest2() {
		Transfer transfer = new Transfer();
		transfer.from = new Table();
		transfer.from.name="customer";
		transfer.to = new Table();
		transfer.to.name = "m.customer";
		List<TransferColumn> transferColumnList = new ArrayList<>();
		Column from = new Column();
		from.name = "fa";
		Column to = new Column();
		to.name = "ta";
		transferColumnList.add(new TransferColumn(from, to));
		Column from2 = new Column();
		from2.name = "fb";
		Column to2 = new Column();
		to2.name = "tb";
		transferColumnList.add(new TransferColumn(from2, to2));
		transfer.transferColumnList = transferColumnList;
		System.out.println(transfer);
	}
}
