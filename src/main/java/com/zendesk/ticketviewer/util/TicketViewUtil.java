package com.zendesk.ticketviewer.util;

import java.util.ArrayList;
import java.util.List;

import com.zendesk.ticketviewer.Ticket;
import com.zendesk.ticketviewer.Tickets;

public class TicketViewUtil {
	private static final List<String> HEADERS = new ArrayList<>();
	private static final List<Integer> WIDTHS = new ArrayList<>();
	
	private static void addKey(StringBuilder sb, String key, Object value) {
		if(value == null) {
			return;
		}
		sb.append(key)
		// We append empty spaces to maintain formatting as two columns. One column for keys and other for values. The max 
		// length is fixed at 20 and key length is subtracted from it. The same is performed for all keys.
			.append(String.format("%" + (20 - key.length()) + "s", ""))
			.append(":")
			.append(value)
			.append("\n");
	}

	public static String getFullTicketDetailsPrint(Ticket ticket) {
		if(ticket == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		addKey(sb, TicketViewParams.ID, ticket.getId());
		addKey(sb, TicketViewParams.REQUESTER_ID, ticket.getRequesterId());
		addKey(sb, TicketViewParams.PRIORITY, ticket.getPriority());
		addKey(sb, TicketViewParams.SUBJECT, ticket.getSubject());
		addKey(sb, TicketViewParams.DESCRIPTION, ticket.getDescription());
		addKey(sb, TicketViewParams.CREATED_AT, DateUtil.getHumanReadableDate(ticket.getCreatedAt()));
		addKey(sb, TicketViewParams.UPDATED_AT, DateUtil.getHumanReadableDate(ticket.getUpdatedAt()));
		addKey(sb, TicketViewParams.STATUS, ticket.getStatus());
		addKey(sb, TicketViewParams.ASSIGNEE_ID, ticket.getAssigneeId());
		addKey(sb, TicketViewParams.DUE_AT, DateUtil.getHumanReadableDate(ticket.getDueAt()));
		addKey(sb, TicketViewParams.TAGS, String.join(",", ticket.getTags()));
		return sb.toString();
	}

	public static String getTicketsPrint(Tickets tickets) {
		if(tickets == null || tickets.getTickets() == null) {
			return null;
		}
		initializeHeaders();
		List<List<Object>> rows = new ArrayList<>();
		for(Ticket ticket : tickets.getTickets()) {
			List<Object> row = new ArrayList<>();
			row.add(ticket.getId());
			row.add(ticket.getPriority());
			row.add(ticket.getSubject());
			row.add(ticket.getRequesterId());
			row.add(DateUtil.getHumanReadableDate(ticket.getUpdatedAt()));
			row.add(ticket.getStatus());
			row.add(ticket.getAssigneeId());
			rows.add(row);
		}
		
		return TableFormatUtil.getTable(HEADERS, WIDTHS, rows);
	}

	// Headers are initialized only for the first time
	private static void initializeHeaders() {
		if(HEADERS.isEmpty()) {
			HEADERS.add(TicketViewParams.ID);
			WIDTHS.add(10);
			
			HEADERS.add(TicketViewParams.PRIORITY);
			WIDTHS.add(10);
			
			HEADERS.add(TicketViewParams.SUBJECT);
			WIDTHS.add(50);
			
			HEADERS.add(TicketViewParams.REQUESTER_ID);
			WIDTHS.add(20);
			
			HEADERS.add(TicketViewParams.UPDATED_AT);
			WIDTHS.add(35);
			
			HEADERS.add(TicketViewParams.STATUS);
			WIDTHS.add(10);
			
			HEADERS.add(TicketViewParams.ASSIGNEE_ID);
			WIDTHS.add(20);
		}
	}

	
	private static final class TicketViewParams {
		private static final String CREATED_AT = "Created At";
		private static final String STATUS = "Status";
		private static final String SUBJECT = "Subject";
		private static final String DESCRIPTION = "Description";
		private static final String UPDATED_AT = "Updated At";
		private static final String ID = "Id";
		private static final String REQUESTER_ID = "Requester Id";
		private static final String PRIORITY = "Priority";
		private static final String ASSIGNEE_ID = "Assignee Id";
		private static final String DUE_AT = "Due At";
		private static final String TAGS = "Tags";
	}

	// Used to check if a given String is number or not. 
	public static Long parseIfLong(String numberStr) {
		try {
			return Long.parseLong(numberStr);
		}
		catch(Exception e) {
			return null;
		}
	}
}
