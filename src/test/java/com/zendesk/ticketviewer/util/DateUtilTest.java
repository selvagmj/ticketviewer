package com.zendesk.ticketviewer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void getDateTest() {
		String dateStr = "2020-11-19T22:16:40Z";
		ZonedDateTime zonedDateTime = DateUtil.getDate(dateStr);
		ZonedDateTime tempZonedDateTime = ZonedDateTime.of(2020, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		assertTrue(tempZonedDateTime.compareTo(zonedDateTime) == 0);
		assertEquals(null, DateUtil.getDate(null));
	}
	
	@Test
	public void getHumanReadableDateTest() {
		ZonedDateTime tempZonedDateTime = ZonedDateTime.of(2020, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		String humanReadableDate = DateUtil.getHumanReadableDate(tempZonedDateTime);
		assertEquals("Thu, Nov 19, 2020, 22:16:40", humanReadableDate);
		assertEquals(null, DateUtil.getHumanReadableDate(null));
	}
	
}
