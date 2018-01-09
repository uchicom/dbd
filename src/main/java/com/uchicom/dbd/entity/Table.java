// (c) 2017 uchicom
package com.uchicom.dbd.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Table {

	public String name;
	public String logical;
	public String description;
	
	public List<Column> columnList = new ArrayList<>();

}
