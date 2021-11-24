package com.zendesk.ticketviewer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.LogManager;

import com.zendesk.ticketviewer.config.Config;

public class ServiceHelper {
	
	private static final String LOGGING = "logging";
	private static final String USERNAME = "username";
	private static final String API_TOKEN = "apitoken";
	private static final String DOMAIN = "domain";
	
	private static final Set<String> CMD_PARAMETERS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(LOGGING, USERNAME, API_TOKEN, DOMAIN)));

	public static void setCommandParameters(String[] args) {
		Map<String, String> cmdParameters = getCommandParameters(args);
		
		// Users can enable logging by using logging option. Disabled by default
		if(!cmdParameters.getOrDefault(LOGGING, "false").equals("true")) { 
			LogManager.getLogManager().reset();
		}
		
		// Set the username, apitoken and domain of the account that has to be accessed. If not set data is retireved from
		// credentials.properties file
		if(cmdParameters.containsKey(USERNAME)) { 
			Config.setUsername(cmdParameters.get(USERNAME));
		}
		
		if(cmdParameters.containsKey(API_TOKEN)) { 
			Config.setAPIToken(cmdParameters.get(API_TOKEN));
		}
		
		if(cmdParameters.containsKey(DOMAIN)) { 
			Config.setDomain(cmdParameters.get(DOMAIN));
		}
		
	}

	private static Map<String, String> getCommandParameters(String[] args) {
		if(args == null || args.length == 0) {
			return Collections.emptyMap();
		}
		Map<String, String> parameters = new HashMap<>();
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			String[] argArr = arg.split("=", 2);
			if(CMD_PARAMETERS.contains(argArr[0])) {
				parameters.put(argArr[0], argArr[1]);
			}
		}
		return parameters;
	}
}
