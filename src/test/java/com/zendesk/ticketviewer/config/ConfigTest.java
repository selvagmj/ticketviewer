package com.zendesk.ticketviewer.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigTest {

	@Test
	public void configTest() {
		Config.setAPIToken("apitoken");
		Config.setUsername("username");
		Config.setDomain("https://www.zendesk.com");

		assertEquals("apitoken", Config.getAPIToken());
		assertEquals("username", Config.getUsername());
		assertEquals("https://www.zendesk.com", Config.getDomain());
		
	}
}
