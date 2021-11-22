package com.zendesk.ticketviewer.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static ZonedDateTime getDate(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		return ZonedDateTime.parse("2017-04-26T20:55:00.000Z", formatter);
	}
}
