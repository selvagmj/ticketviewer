package com.zendesk.ticketviewer.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateUtil {

	public static ZonedDateTime getDate(String dateStr) {
		TemporalAccessor accessor = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(dateStr);
		return ZonedDateTime.from(accessor);
	}
	
	public static String getHumanReadableDate(ZonedDateTime zonedDateTime) {
		DateTimeFormatter readableDateFormat = DateTimeFormatter.ofPattern("E, MMM dd, uuuu, HH:mm:ss");
		return zonedDateTime.format(readableDateFormat);
	}
}
