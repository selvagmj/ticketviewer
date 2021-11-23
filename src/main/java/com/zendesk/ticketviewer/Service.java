package com.zendesk.ticketviewer;

import java.util.Scanner;
import java.util.logging.LogManager;

import com.zendesk.ticketviewer.api.TicketAPI;
import com.zendesk.ticketviewer.config.Config;
import com.zendesk.ticketviewer.util.TicketViewUtil;
import com.zendesk.ticketviewer.util.TicketViewerUtil;

public class Service {
	

	private static final int MAX_PAGE_SIZE = 25;

	private static final int GET_TICKETS = 1;
	private static final int GET_TICKET = 2;
	private static final int EXIT = 3;
	private static final String NEXT = "next";
	private static final String MAIN = "main";
	private static final String PREVIOUS = "previous";

	public static void main(String[] args) {
		if(args == null || args.length != 1 || !args[0].equals("-l")) { 
			LogManager.getLogManager().reset();
		}
		Scanner scans = new Scanner(System.in);
		System.out.println("Welcome to Zendesk ticket viewer");
		Config.initialize();
		int num = 0;
		while(num != EXIT) {
			System.out.println("Enter " + GET_TICKETS + " to view your tickets");
			System.out.println("Enter " + GET_TICKET + " to view ticket by ticket id");
			System.out.println("Enter " + EXIT + " to exit");
			
			num = scans.nextInt();
			if(num == EXIT) {
				break;
			}
			else if(num == GET_TICKET) {
				getTicketDetail(scans);
			}
			else if(num == GET_TICKETS) {
				getTickets(scans);
			}
			else {
				System.out.println("Invalid operation. Use the following operations only.");
			}
		}
		System.out.println("Thank you!");
	}

	private static void getTickets(Scanner scans) {
		int page = 0;
		String operation = NEXT;
		while(!operation.equals(MAIN)) {
			if(operation.equals(NEXT)) {
				page += 1;
			}
			else if(operation.equals(PREVIOUS)) {
				page -= 1;
			}
			else {
				System.out.println("Invalid operation.");
				return;
			}
			
			Tickets tickets = TicketAPI.getTickets(page, MAX_PAGE_SIZE);
			if(tickets == null) {
				System.out.println("Cannot fetch tickets");	
			}
			if(tickets.getTickets().isEmpty()) {
				System.out.println("You have reached the end of the list");
				return;
			}
			String ticketsPrint = TicketViewUtil.getTicketsPrint(tickets);
			System.out.println(ticketsPrint);
			System.out.println("Current page: " + page);
			if(tickets.isPrevious()) {
				System.out.println("Enter \"" + PREVIOUS + "\" for previous ");
			}
			if(tickets.isNext()) {
				System.out.println("Enter \"" + NEXT + "\" for next");
			}
			System.out.println("Enter \"" + MAIN + "\" for Main menu");
			operation = scans.next();
			if((!tickets.isNext() && operation.equals(NEXT)) ||
					(!tickets.isPrevious() && operation.equals(PREVIOUS))) {
				System.out.println("Invalid operation.");
				return;
			}
		}
	}

	private static void getTicketDetail(Scanner scans) {
		System.out.println("Enter \"" + MAIN + "\" for Main menu");
		System.out.println("Enter ticket id:");
		String operationStr = scans.next();
		if(operationStr.equals(MAIN)) {
			return;
		}
		Long operation = TicketViewerUtil.parseIfLong(operationStr);
		if(operation == null) {
			System.out.println("Invalid operation.");
			return;
		}
		if(operation < 0) {
			System.out.println("Invalid ticket id");
			return;
		}
		Ticket ticket = TicketAPI.getTicket(operation);
		if(ticket == null) {
			System.out.println("Cannot fetch ticket with given ticket id");
			return;
		}
		
		String ticketPrint = TicketViewUtil.getFullTicketDetailsPrint(ticket);
		System.out.println(ticketPrint);
	}
}
