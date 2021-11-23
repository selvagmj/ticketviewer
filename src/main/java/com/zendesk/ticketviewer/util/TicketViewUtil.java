package com.zendesk.ticketviewer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.zendesk.ticketviewer.Ticket;
import com.zendesk.ticketviewer.Tickets;
import com.zendesk.ticketviewer.Ticket.TicketParams;

public class TicketViewUtil {

	public static String getFullTicketDetailsPrint(Ticket ticket) {
		
		return ticket.toString();
	}

	public static String getTicketsPrint(Tickets tickets) {
		List<String> headers = new ArrayList<>();
		List<Integer> widths = new ArrayList<>();
		
		headers.add(TicketParams.ID);
		widths.add(10);
		
		headers.add(TicketParams.PRIORITY);
		widths.add(10);
		
		headers.add(TicketParams.SUBJECT);
		widths.add(50);
		
		headers.add(TicketParams.REQUESTER_ID);
		widths.add(20);
		
		headers.add(TicketParams.UPDATED_AT);
		widths.add(20);
		
		headers.add(TicketParams.STATUS);
		widths.add(10);
		
		headers.add(TicketParams.ASSIGNEE_ID);
		widths.add(20);
		
		List<List<Object>> rows = new ArrayList<>();
		for(Ticket ticket : tickets.getTickets()) {
			List<Object> row = new ArrayList<>();
			row.add(ticket.getId());
			row.add(ticket.getPriority());
			row.add(ticket.getSubject());
			row.add(ticket.getRequesterId());
			row.add(ticket.getUpdatedAt());
			row.add(ticket.getStatus());
			row.add(ticket.getAssigneeId());
			rows.add(row);
		}
		
		return tablePrint(headers, widths, rows);
	}
	
	private static String tablePrint(List<String> headers, List<Integer> widths, List<List<Object>> rows) {
		StringBuilder sb = new StringBuilder();
		
		addRow(headers, widths, sb);
		int length = sb.length();
		sb.append("\n");
		for(int i = 0; i < length; i++) {
			sb.append("-");
		}
		sb.append("\n");
		
		for(List<Object> row : rows) {
			addRow(row, widths, sb);
			sb.append("\n");
		}
		return sb.toString();
	}

	private static void addRow(List<? extends Object> headers, List<Integer> widths, StringBuilder sb) {
		sb.append("| ");
		for(int i = 0; i < headers.size(); i++) {
			String header = Objects.toString(headers.get(i), "");
			String column = header.substring(0, Math.min(header.length(), widths.get(i)));
			sb.append(column);
			if(column.length() < widths.get(i)) {
				int spaceLength = widths.get(i) - column.length();
				char[] spaceArrs = new char[spaceLength];
				Arrays.fill(spaceArrs, ' ');
				sb.append(spaceArrs);
			}
			sb.append(" | ");
		}
	}
}
