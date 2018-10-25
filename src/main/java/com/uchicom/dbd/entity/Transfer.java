// (c) 2018 uchicom
package com.uchicom.dbd.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class Transfer {

	public String transformId;

	public String fromDb;
	public String toDb;

	public Table from;

	public Table to;

	public List<TransferColumn> transferColumnList = new ArrayList<>();

	public String values(int index) {
		StringBuilder builder = new StringBuilder(1024);
		builder.append("('").append(transformId).append("', '").append(fromDb).append("', '").append(toDb)
				.append("', 'LOAD'," + "'").append(from.schema).append("', '").append(from.name).append("','")
				.append(to.schema).append("', '").append(to.name).append("'," + "'DEL_ROW', ").append(index)
				.append(", '").append(transferColumnList.isEmpty() ? "IMPLIED" : "SPECIFIED")
				.append("', 1," + "'dbd', current_timestamp, current_timestamp)");
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(1024);

		builder.append("insert into sym_transform_column"
				+ "(transform_id, include_on, source_column_name, target_column_name, pk,transform_type, transform_expression, transform_order,last_update_time, last_update_by, create_time)"
				+ "\nvalues");
		for (int i = 0; i < transferColumnList.size(); i++) {
			if (i != 0) {
				builder.append(",\n");
			}
			builder.append(transferColumnList.get(i).values(transformId, i + 1));
		}
		builder.append(";\n");
		return builder.toString();
	}
}
