// (c) 2018 uchicom
package com.uchicom.dbd.entity;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class TransferColumn {

	private Column from;
	private Column to;

	public TransferColumn(Column from, Column to) {
		this.from = from;
		this.to = to;
	}

	public String values(String transformId, int order) {
		StringBuilder builder = new StringBuilder(1024);
		builder.append("('")
				.append(transformId)
				.append("', '*', '")
				.append(from.name == null ? "" : from.name)
				.append("', '")
				.append(to.name == null ? "" : to.name)
				.append("', ")
				.append(to.pk == null ? 0 : to.pk)
				.append(",'")
				.append(to.identity)
				.append("', null, ")
				.append(order)
				.append(", current_timestamp, 'dbd', current_timestamp)");
		return builder.toString();
	}
}
