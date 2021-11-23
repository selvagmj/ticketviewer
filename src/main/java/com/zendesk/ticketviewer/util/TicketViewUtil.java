package com.zendesk.ticketviewer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.zendesk.ticketviewer.Ticket;
import com.zendesk.ticketviewer.Tickets;

public class TicketViewUtil {
	private static final List<String> HEADERS = new ArrayList<>();
	private static final List<Integer> WIDTHS = new ArrayList<>();

	public static String getFullTicketDetailsPrint(Ticket ticket) {
		StringBuilder sb = new StringBuilder();
		sb.append(TicketViewParams.ID)
			.append(String.format("%" + (20 - TicketViewParams.ID.length()) + "s", ""))
			.append(":")
			.append(ticket.getId())
			.append("\n");
		
		sb.append(TicketViewParams.REQUESTER_ID)
			.append(String.format("%" + (20 - TicketViewParams.REQUESTER_ID.length()) + "s", ""))
			.append(":")
			.append(ticket.getRequesterId())
			.append("\n");
		if(ticket.getPriority() != null) {
			sb.append(TicketViewParams.PRIORITY)
				.append(String.format("%" + (20 - TicketViewParams.PRIORITY.length()) + "s", ""))
				.append(":")
				.append(ticket.getPriority())
				.append("\n");
		}
		if(ticket.getSubject() != null) {
			sb.append(TicketViewParams.SUBJECT)
				.append(String.format("%" + (20 - TicketViewParams.SUBJECT.length()) + "s", ""))
				.append(":")
				.append(ticket.getSubject())
				.append("\n");
		}
		if(ticket.getDescritpion() != null) {
			sb.append(TicketViewParams.DESCRIPTION)
				.append(String.format("%" + (20 - TicketViewParams.DESCRIPTION.length()) + "s", ""))
				.append(":")
				.append(ticket.getDescritpion())
				.append("\n");
		}
		if(ticket.getCreatedAt() != null) {
			sb.append(TicketViewParams.CREATED_AT)
				.append(String.format("%" + (20 - TicketViewParams.CREATED_AT.length()) + "s", ""))
				.append(":")
				.append(DateUtil.getHumanReadableDate(ticket.getCreatedAt()))
				.append("\n");
		}
		if(ticket.getUpdatedAt() != null) {
			sb.append(TicketViewParams.UPDATED_AT)
				.append(String.format("%" + (20 - TicketViewParams.UPDATED_AT.length()) + "s", ""))
				.append(":")
				.append(DateUtil.getHumanReadableDate(ticket.getUpdatedAt()))
				.append("\n");
		}
		if(ticket.getStatus() != null) {
			sb.append(TicketViewParams.STATUS)
				.append(String.format("%" + (20 - TicketViewParams.STATUS.length()) + "s", ""))
				.append(":")
				.append(ticket.getStatus())
				.append("\n");
		}
		if(ticket.getAssigneeId() != null) {
			sb.append(TicketViewParams.ASSIGNEE_ID)
				.append(String.format("%" + (20 - TicketViewParams.ASSIGNEE_ID.length()) + "s", ""))
				.append(":")
				.append(ticket.getAssigneeId())
				.append("\n");
		}
		if(ticket.getDueAt() != null) {
			sb.append(TicketViewParams.DUE_AT)
				.append(String.format("%" + (20 - TicketViewParams.DUE_AT.length()) + "s", ""))
				.append(":")
				.append(DateUtil.getHumanReadableDate(ticket.getDueAt()))
				.append("\n");
		}
		if(ticket.getTags() != null && !ticket.getTags().isEmpty()) {
			sb.append(TicketViewParams.TAGS)
				.append(String.format("%" + (20 - TicketViewParams.TAGS.length()) + "s", ""))
				.append(":")
				.append(String.join(",", ticket.getTags()))
				.append("\n");
		}
		return sb.toString();
	}

	public static String getTicketsPrint(Tickets tickets) {
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
		
		return tablePrint(HEADERS, WIDTHS, rows);
	}

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

	
	public static final class TicketViewParams {
		public static final String CREATED_AT = "Created At";
		public static final String STATUS = "Status";
		public static final String SUBJECT = "Subject";
		public static final String DESCRIPTION = "Description";
		public static final String UPDATED_AT = "Updated At";
		public static final String ID = "Id";
		public static final String REQUESTER_ID = "Requester Id";
		public static final String PRIORITY = "Priority";
		public static final String ASSIGNEE_ID = "Assignee Id";
		public static final String DUE_AT = "Due At";
		public static final String TAGS = "Tags";
	}
}
