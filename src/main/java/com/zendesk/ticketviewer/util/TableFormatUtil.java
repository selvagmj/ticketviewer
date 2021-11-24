package com.zendesk.ticketviewer.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableFormatUtil {
	
	private static final Logger LOGGER = Logger.getLogger(TableFormatUtil.class.getName());

	// The following function is used to get data in table format string.
	// headers - the headers of the table
	// widths - maximum width of every column
	// rows - rows of data
	// the length of headers, widths and every element of rows has to be same.
	public static String getTable(List<String> headers, List<Integer> widths, List<List<Object>> rows) {
		if(headers == null || widths == null || rows == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		
		addRow(headers, widths, sb);
		// After header is added the length of first row is used to add separator between header and other rows.
		int length = sb.length();
		sb.append("\n");
		for(int i = 0; i < length; i++) {
			sb.append("-");
		}
		sb.append("\n");
		
		// Every other row is added into the string builder
		for(List<Object> row : rows) {
			addRow(row, widths, sb);
			sb.append("\n");
		}
		return sb.toString();
	}

	// Add every row into string
	private static void addRow(List<? extends Object> row, List<Integer> widths, StringBuilder sb) {
		if(row.size() != widths.size()) {
			LOGGER.log(Level.SEVERE, "Row size mismatch");
			return;
		}
		sb.append("| ");
		for(int i = 0; i < row.size(); i++) {
			String header = Objects.toString(row.get(i), "");
			// The column is trimmed to max size mentioned in width
			String column = header.substring(0, Math.min(header.length(), widths.get(i)));
			sb.append(column);
			// Empty space padded if column size less than max width
			if(column.length() < widths.get(i)) {
				int spaceLength = widths.get(i) - column.length();
				char[] spaceArrs = new char[spaceLength];
				Arrays.fill(spaceArrs, ' ');
				sb.append(spaceArrs);
			}
			sb.append(" | ");
		}
	}
}
