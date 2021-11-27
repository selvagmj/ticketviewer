package com.zendesk.ticketviewer.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TableFormatUtilTest {

	@Test
	public void getTableTest() {
		List<String> headers = new ArrayList<>();
		headers.add("id");
		headers.add("name");
		
		List<Integer> widths = new ArrayList<>();
		widths.add(5);
		widths.add(20);
		List<List<Object>> rows = new ArrayList<>();
		List<Object> row1 = new ArrayList<>();
		row1.add(1);
		row1.add("ron");
		rows.add(row1);
		List<Object> row2 = new ArrayList<>();
		row2.add(2);
		row2.add("swanson");
		rows.add(row2);
		String table = TableFormatUtil.getTable(headers, widths, rows);
		String tempTable = "| id    | name                 | \n"
				+ "---------------------------------\n"
				+ "| 1     | ron                  | \n"
				+ "| 2     | swanson              | \n"
				+ "";
		assertEquals(tempTable, table);
		assertEquals(null, TableFormatUtil.getTable(headers, null, rows));
	}
}
