package com.zendesk.ticketviewer;

import java.net.URISyntaxException;
import java.util.Scanner;

import com.zendesk.ticketviewer.api.TicketAPI;
import com.zendesk.ticketviewer.config.Config;
import com.zendesk.ticketviewer.util.TicketViewUtil;

public class Service {

	public static void main(String[] args) throws URISyntaxException {
		Scanner scans = new Scanner(System.in);
		System.out.println("Welcome to Zendesk ticket viewer");
		Config.initialize();
		int num = 0;
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
			String ticketsPrint = TicketViewUtil.getTicketsPrint(tickets);
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
		
		String ticketPrint = TicketViewUtil.getFullTicketDetailsPrint(ticket);
		System.out.println(ticketPrint);
	}
}
