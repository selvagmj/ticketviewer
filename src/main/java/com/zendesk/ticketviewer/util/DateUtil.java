package com.zendesk.ticketviewer.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateUtil {
	
	// Human readable date format e.g. Fri, Nov 19, 2021, 22:16:40
	private static final String READABLE_DATE_FORMAT = "E, MMM dd, uuuu, HH:mm:ss";
	private static final DateTimeFormatter READABLE_DATE_FORMATTER = DateTimeFormatter.ofPattern(READABLE_DATE_FORMAT);

	// Converts String date in ISO 8601 format to ZonedDateTime object 
	public static ZonedDateTime getDate(String dateStr) {
		TemporalAccessor accessor = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(dateStr);
		return ZonedDateTime.from(accessor);
	}
	
	// Converts ZonedDateTime object to human readable format
	public static String getHumanReadableDate(ZonedDateTime zonedDateTime) {
		if(zonedDateTime == null) {
			return null;
		}
		return zonedDateTime.format(READABLE_DATE_FORMATTER);
	}
}
