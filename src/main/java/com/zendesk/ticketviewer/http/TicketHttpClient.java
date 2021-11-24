package com.zendesk.ticketviewer.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zendesk.ticketviewer.config.Config;

public class TicketHttpClient {
	
	private static final Logger LOGGER = Logger.getLogger(TicketHttpClient.class.getName());
	
	private static final String TICKETS_API_PATH = "/api/v2/tickets";
	private static final String SEPARATOR = "/";
	
	private String url;
	private Map<String, String> queryParam = new HashMap<>();
	private Map<String, String> headers = new HashMap<>();
	
	public TicketHttpClient() {
		this.url = Config.getSource() + SEPARATOR + TICKETS_API_PATH;
		this.headers.put("Accept", "application/json");
	}
	
	public void addQueryParams(String key, String value) {
		this.queryParam.put(key, value);
	}
	
	public void addPath(Object path) {
		if(path != null) {
			this.url += SEPARATOR + Objects.toString(path);
		}
	}
	
	public Response get() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			URIBuilder uriBuilder = new URIBuilder(this.url + ".json");
			setQueryParams(uriBuilder);
			
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			setHeaders(httpGet);
	    	setAuthentication(httpGet);
	    	
	    	CloseableHttpResponse response = httpclient.execute(httpGet);
	        return getResponse(response);
		}
		catch(IOException e) {
			LOGGER.log(Level.SEVERE, "Cannot complete get request", e);
		}
		catch (URISyntaxException e) {
			LOGGER.log(Level.SEVERE, "Cannot generate request url", e);
		}
	    finally {
	    	try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Cannot close http client connection", e);
			}
	    }
		return null;
	}

	private void setHeaders(HttpGet httpGet) {
		for(Map.Entry<String, String> entry : this.headers.entrySet()) {
			httpGet.setHeader(entry.getKey(), entry.getValue());
		}
	}

	private void setQueryParams(URIBuilder uriBuilder) {
		for(Map.Entry<String, String> entry : this.queryParam.entrySet()) {
			uriBuilder.addParameter(entry.getKey(), entry.getValue());
		}
	}

	private void setAuthentication(HttpRequestBase httpGet) throws UnsupportedEncodingException {
		byte[] bytes = (Config.getUsername() + ":" + Config.getPassword()).getBytes("UTF-8");
		String encoding = Base64.getEncoder().encodeToString(bytes);
		httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
	}

	private Response getResponse(CloseableHttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder result = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
		    result.append(line);
		}
		Response ticketViewerResponse = new Response(response.getStatusLine().getStatusCode(), result.toString());
		EntityUtils.consume(entity);
		return ticketViewerResponse;
	}
	
}
