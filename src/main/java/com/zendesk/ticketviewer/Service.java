package com.zendesk.ticketviewer;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zendesk.ticketviewer.api.TicketAPI;
import com.zendesk.ticketviewer.config.Config;
import com.zendesk.ticketviewer.util.TicketViewUtil;

public class Service {
	
	private static final Logger LOGGER = Logger.getLogger(Service.class.getName());
	
	// Maximum Number of tickets displayed per page
	private static final int MAX_PAGE_SIZE = 25;

	// List of Operations accepted from console
	private static final int GET_TICKETS = 1;
	private static final int GET_TICKET = 2;
	private static final int EXIT = 3;
	private static final String NEXT = "next";
	private static final String MAIN = "main";
	private static final String PREVIOUS = "previous";

	public static void main(String[] args) {
		ServiceHelper.setCommandParameters(args);
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
		try {
			int page = 0;
			String operation = NEXT;
			while(!operation.equals(MAIN)) {
				// Page incremented and decremented based on operation.
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
					return;
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
				// User cannot enter "next" or "previous" when next or previous set of tickets are not available
				if((!tickets.isNext() && operation.equals(NEXT)) ||
						(!tickets.isPrevious() && operation.equals(PREVIOUS))) {
					System.out.println("Invalid operation.");
					return;
				}
			}
		}
		catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot get tickets", e);
			System.out.println("Cannot get tickets");
		}
	}

	private static void getTicketDetail(Scanner scans) {
		try {
			System.out.println("Enter \"" + MAIN + "\" for Main menu");
			System.out.println("Enter ticket id:");
			String operationStr = scans.next();
			if(operationStr.equals(MAIN)) {
				return;
			}
			Long operation = TicketViewUtil.parseIfLong(operationStr);
			if(operation == null) {
				System.out.println("Invalid operation.");
				return;
			}
			// Ticket Id cannot be less than 0
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
		catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Cannot get ticket", e);
			System.out.println("Cannot get ticket");
		}
	}
}
