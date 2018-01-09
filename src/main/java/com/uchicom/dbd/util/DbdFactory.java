// (c) 2017 uchicom
package com.uchicom.dbd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.uchicom.dbd.entity.Column;
import com.uchicom.dbd.entity.Table;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class DbdFactory {

	public static List<Table> getTableList(File file) {
		List<Table> list = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			try (FileInputStream fis = new FileInputStream(file)) {
				Document document = builder.parse(fis);
				Element e = document.getDocumentElement();
				// tables
				NodeList tableList = e.getElementsByTagName("table");
				int tableCount = tableList.getLength();
				for (int i = 0; i < tableCount; i++) {
					Node tableNode = tableList.item(i);
					Table table = new Table();
					list.add(table);
					NamedNodeMap tableAttributes = tableNode.getAttributes();
					table.name = tableAttributes.getNamedItem("name").getNodeValue();
					table.logical = tableAttributes.getNamedItem("logical").getNodeValue();
					table.description = tableAttributes.getNamedItem("description").getNodeValue();
					NodeList columnList = tableNode.getChildNodes();
					int columnCount = columnList.getLength();
					for (int j = 0; j < columnCount; j++) {
						Node columnNode = columnList.item(j);
						if (!"column".equals(columnNode.getNodeName())) {
							continue;
						}
						Column column = new Column();
						NamedNodeMap columnAttributes = columnNode.getAttributes();
						column.name = columnAttributes.getNamedItem("name").getNodeValue();
						column.logical = columnAttributes.getNamedItem("logical").getNodeValue();
						column.description = columnAttributes.getNamedItem("description").getNodeValue();
						column.type = columnAttributes.getNamedItem("type").getNodeValue();
						column.order = columnAttributes.getNamedItem("order").getNodeValue();
						table.columnList.add(column);
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return list;
	}
}
