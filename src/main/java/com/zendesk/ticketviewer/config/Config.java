package com.zendesk.ticketviewer.config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
	
	private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
	
	private static final Map<String, String> PROPS = new HashMap<>();
	
	// Used to initialize the credentials from properties file. In case of different deployments we can change 
	// the properties file to support the corresponding deployments. This function can be called during server 
	// initialization explicitly or during the first time the credentials are accessed.
	public static void initialize() {
		try {
			Properties prop = new Properties();
			FileInputStream propsFile = new FileInputStream("src/main/resources/credentials.properties");
			prop.load(propsFile);
			// Used Put if absent to avoid replacing credentials given in command line while invoking jar 
			PROPS.putIfAbsent("domain", prop.getProperty("domain"));
			PROPS.putIfAbsent("username", prop.getProperty("username"));
			PROPS.putIfAbsent("password", prop.getProperty("password"));
		}
		catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot read credentials properties", e);
		}
	}

	public static String getSource() {
		return PROPS.get("domain");
	}
	
	public static String getUsername() {
		return PROPS.get("username");
	}
	
	public static String getPassword() {
		return PROPS.get("password");
	}
	
}
