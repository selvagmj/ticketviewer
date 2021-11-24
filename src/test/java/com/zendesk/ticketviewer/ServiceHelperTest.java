package com.zendesk.ticketviewer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zendesk.ticketviewer.config.Config;

public class ServiceHelperTest {
	
	@Test
	public void setCommandParameters() {
		String[] args = new String[] {"username=un", "password=pw", "domain=dm"};
		ServiceHelper.setCommandParameters(args);
		
		assertEquals("pw", Config.getPassword());
		assertEquals("un", Config.getUsername());
		assertEquals("dm", Config.getDomain());
	}

}
