package com.zendesk.ticketviewer.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigTest {

	@Test
	public void configTest() {
		Config.setPassword("password");
		Config.setUsername("username");
		Config.setDomain("https://www.zendesk.com");

		assertEquals("password", Config.getPassword());
		assertEquals("username", Config.getUsername());
		assertEquals("https://www.zendesk.com", Config.getDomain());
		
	}
}
