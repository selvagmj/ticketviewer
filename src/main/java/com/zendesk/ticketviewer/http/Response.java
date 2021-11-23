package com.zendesk.ticketviewer.http;

// Wrapper for HTTP Response. Mainly used so that we can close the CloseableHttpResponse as the function gets over. 
public class Response {

	private int httpStatusCode;
	private String response;
	
	public Response(int httpStatusCode, String response) {
		this.httpStatusCode = httpStatusCode;
		this.response = response;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getResponse() {
		return response;
	}
	
}
