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

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zendesk.ticketviewer.config.Config;

public class TicketHttpClient {
	
	private static final String TICKETS_API_PATH = "/api/v2/tickets";
	private static final String SEPARATOR = "/";
	
	private String url;
	private Map<String, String> queryParam = new HashMap<>();
	
	public TicketHttpClient() {
		this.url = Config.getSource() + SEPARATOR + TICKETS_API_PATH;
	}
	
	public void setPath(String path) {
		this.url = path;
	}
	
	public void addQueryParams(String key, String value) {
		this.queryParam.put(key, value);
	}
	
	public void addPath(Object path) {
		this.url += SEPARATOR + Objects.toString(path, null);
	}

	private Response getResponse(CloseableHttpResponse response1) throws IOException {
		Response response;
		HttpEntity entity = response1.getEntity();
		InputStream in = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder result = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
		    result.append(line);
		}
		response = new Response(response1.getStatusLine().getStatusCode(), result.toString());
		EntityUtils.consume(entity);
		return response;
	}
	
	public Response get() throws URISyntaxException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(this.url + ".json");
		for(Map.Entry<String, String> entry : this.queryParam.entrySet()) {
			uriBuilder.addParameter(entry.getKey(), entry.getValue());
		}
		HttpGet httpGet = new HttpGet(uriBuilder.build());
	    httpGet.setHeader("Accept", "*/*");
		CloseableHttpResponse response1 = null;
		Response response = null;
	    try {
	    	setAuthentication(httpGet);
			response1 = httpclient.execute(httpGet);
	        response = getResponse(response1);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    finally {
	    	
	    }
	    return response;
	}

	private void setAuthentication(HttpRequestBase httpGet) throws UnsupportedEncodingException {
		byte[] bytes = (Config.getUsername() + ":" + Config.getPassword()).getBytes("UTF-8");
		String encoding = Base64.getEncoder().encodeToString(bytes);
		httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
	}
	
}
