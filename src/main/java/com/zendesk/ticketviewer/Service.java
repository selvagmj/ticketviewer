package com.zendesk.ticketviewer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.zendesk.ticketviewer.Ticket.TicketParams;
import com.zendesk.ticketviewer.api.TicketAPI;

public class Service {

	public static void main(String[] args) throws URISyntaxException {
		System.out.println("Welcome to Zendesk ticket viewer");
		int num = 0;
		Scanner scans = new Scanner(System.in);
		while(num != 3) {
			System.out.println("Enter 1 to view your tickets");
			System.out.println("Enter 2 to view ticket by ticket id");
			System.out.println("Enter 3 to exit");
			
			num = scans.nextInt();
			if(num == 3) {
				System.out.println("Thank you!");
				break;
			}
			else if(num == 2) {
				getTicketDetail(scans);
			}
			else if(num == 1) {
				getTickets(scans);
			}
		}
	}

	private static void getTickets(Scanner scans) throws URISyntaxException {
		int page = 0;
		String operation = "next";
		while(!operation.equals("main")) {
			if(operation.equals("next")) {
				page += 1;
			}
			else if(operation.equals("previous")) {
				page -= 1;
			}
			else {
				System.out.println("Invalid operation.");
				return;
			}
			
			Tickets tickets = TicketAPI.getTickets(page, 25);
			if(tickets.getTickets().isEmpty()) {
				System.out.println("You have reached the end of the list");
				return;
			}
			String ticketsPrint = getTicketsPrint(tickets);
			System.out.println(ticketsPrint);
			System.out.println("Current page: " + page);
			if(tickets.isPrevious()) {
				System.out.println("Enter \"previous\" for previous ");
			}
			if(tickets.isNext()) {
				System.out.println("Enter \"next\" for next");
			}
			System.out.println("Enter \"main\" for Main menu");
			operation = scans.next();
			if((!tickets.isNext() && operation.equals("next")) &&
					(!tickets.isPrevious() && operation.equals("previous"))) {
				System.out.println("Invalid operation.");
				return;
			}
		}
	}

	private static void getTicketDetail(Scanner scans) throws URISyntaxException {
		System.out.println("Enter \"main\" for main menu");
		System.out.println("Enter ticket id:");
		String operationStr = scans.next();
		if(operationStr.equals("main")) {
			return;
		}
		long operation = Long.parseLong(operationStr);
		if(operation < 0) {
			System.out.println("Invalid ticket id");
			return;
		}
		Ticket ticket = TicketAPI.getTicket(operation);
		if(ticket == null) {
			System.out.println("Ticket with given ticket id does not exist");
			return;
		}
		
		String ticketPrint = getFullTicketDetailsPrint(ticket);
		System.out.println(ticketPrint);
	}

	private static String getFullTicketDetailsPrint(Ticket ticket) {
		
		return ticket.toString();
	}

	private static String getTicketsPrint(Tickets tickets) {
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
