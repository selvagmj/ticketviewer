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
	
	public static Tickets getTickets(long page, long pageSize) throws URISyntaxException {
		TicketHttpClient ticketHttpClient = new TicketHttpClient();
		ticketHttpClient.addQueryParams("per_page", Objects.toString(pageSize));
		ticketHttpClient.addQueryParams("page", Objects.toString(page));
		Response response = ticketHttpClient.get();
		String response2 = response.getResponse();
		JSONObject responseJSON = new JSONObject(response2);
		System.out.println(responseJSON);
		JSONArray ticketsJSONArray = responseJSON.getJSONArray("tickets");
		List<Ticket> tickets = new ArrayList<>();
		for(int i = 0; i < ticketsJSONArray.length(); i++) {
			tickets.add(Ticket.parse(ticketsJSONArray.getJSONObject(i)));
		}
		
		String nextPageUrl = responseJSON.optString("next_page", null);
		Long nextPage = null;
		if(nextPageUrl != null) {
			List<NameValuePair> params = URLEncodedUtils.parse(new URI(nextPageUrl), Charset.forName("UTF-8"));
			for(NameValuePair queryParam : params) {
				if(queryParam.getName().equals("page")) {
					nextPage = Long.parseLong(queryParam.getValue());
				}
			}
		}
		
		String previousPageUrl = responseJSON.optString("previous_page", null);
		Long previousPage = null;
		if(previousPageUrl != null) {
			List<NameValuePair> previousPageUrlParams = URLEncodedUtils.parse(new URI(previousPageUrl), Charset.forName("UTF-8"));
			for(NameValuePair queryParam : previousPageUrlParams) {
				if(queryParam.getName().equals("page")) {
					previousPage = Long.parseLong(queryParam.getValue());
				}
			}
		}
		
		return new Tickets(tickets, nextPage, previousPage);
	}
	
	public static Ticket getTicket(long ticketId) throws URISyntaxException {
		TicketHttpClient ticketHttpClient = new TicketHttpClient();
		ticketHttpClient.addPath(ticketId);
		Response response = ticketHttpClient.get();
		
		String response2 = response.getResponse();
		JSONObject responseJSON = new JSONObject(response2);
		if(responseJSON.has("error")) {
			String error = responseJSON.getString("error");
			if(error.equals("RecordNotFound")) {
				LOGGER.log(Level.WARNING, "Invalid Ticket Id given");
				return null;
			}
		}
		return Ticket.parse(responseJSON.getJSONObject("ticket"));
	}
	
}
