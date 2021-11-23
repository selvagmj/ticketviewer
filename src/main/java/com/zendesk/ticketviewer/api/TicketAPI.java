package com.zendesk.ticketviewer.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.zendesk.ticketviewer.Ticket;
import com.zendesk.ticketviewer.Tickets;
import com.zendesk.ticketviewer.http.Response;
import com.zendesk.ticketviewer.http.TicketHttpClient;

public class TicketAPI {

	public static final Logger LOGGER = Logger.getLogger(TicketAPI.class.getName());
	
	public static Tickets getTickets(long page, long pageSize) {
		TicketHttpClient ticketHttpClient = new TicketHttpClient();
		ticketHttpClient.addQueryParams("per_page", Objects.toString(pageSize));
		ticketHttpClient.addQueryParams("page", Objects.toString(page));
		Response response = ticketHttpClient.get();
		if(response == null ||
				response.getHttpStatusCode() != 200 ||
				response.getResponse() == null) {
			LOGGER.log(Level.WARNING, "Cannot retrieve ticket details for given params");
			return null;
		}
		
		JSONObject responseJSON = new JSONObject(response.getResponse());
		if(responseJSON.has("error")) {
			LOGGER.log(Level.WARNING, "Cannot retrieve ticket details for given params");
			return null;
		}
		JSONArray ticketsJSONArray = responseJSON.getJSONArray("tickets");
		List<Ticket> tickets = new ArrayList<>();
		for(int i = 0; i < ticketsJSONArray.length(); i++) {
			tickets.add(Ticket.parse(ticketsJSONArray.getJSONObject(i)));
		}
		
		Long nextPage = getNextPageIdx(responseJSON);
		Long previousPage = getPreviousPageIdx(responseJSON);
		
		return new Tickets(tickets, nextPage, previousPage);
	}

	private static Long getPreviousPageIdx(JSONObject responseJSON) {
		return getPageIdx(responseJSON, "previous_page");
	}

	private static Long getNextPageIdx(JSONObject responseJSON) {
		return getPageIdx(responseJSON, "next_page");
	}

	private static Long getPageIdx(JSONObject responseJSON, String pageKey) {
		try {
			String pageUrl = responseJSON.optString(pageKey, null);
			if(pageUrl != null) {
				List<NameValuePair> params = URLEncodedUtils.parse(new URI(pageUrl), Charset.forName("UTF-8"));
				for(NameValuePair queryParam : params) {
					if(queryParam.getName().equals("page")) {
						return Long.parseLong(queryParam.getValue());
					}
				}
			}
		}
		catch(URISyntaxException e) {
			LOGGER.log(Level.WARNING, "Cannot parse page url", e);
		}
		return null;
	}
	
	public static Ticket getTicket(long ticketId) {
		TicketHttpClient ticketHttpClient = new TicketHttpClient();
		ticketHttpClient.addPath(ticketId);
		Response response = ticketHttpClient.get();
		
		if(response == null ||
				response.getResponse() == null) {
			LOGGER.log(Level.WARNING, "Cannot retrieve ticket details for given params");
			return null;
		}
		
		JSONObject responseJSON = new JSONObject(response.getResponse());
		if(responseJSON.has("error")) {
			String error = responseJSON.getString("error");
			if(error.equals("RecordNotFound")) {
				LOGGER.log(Level.WARNING, "Invalid Ticket Id given");
				return null;
			}
		}
		if(response.getHttpStatusCode() != 200) {
			LOGGER.log(Level.WARNING, "Cannot retrieve ticket details for given params");
			return null;
		}
		return Ticket.parse(responseJSON.getJSONObject("ticket"));
	}
	
}
