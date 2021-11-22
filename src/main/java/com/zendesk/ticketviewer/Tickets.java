package com.zendesk.ticketviewer;

import java.util.List;

public class Tickets {

	private List<Ticket> tickets;
	private Long nextPage;
	private Long previousPage;
	
	public Tickets(List<Ticket> tickets, Long nextPage, Long previousPage) {
		this.tickets = tickets;
		this.nextPage = nextPage;
		this.previousPage = previousPage;
	}
	
	public List<Ticket> getTickets() {
		return this.tickets;
	}
	
	public Long getNextPage() {
		return this.nextPage;
	}
	
	public Long getPreviousPage() {
		return this.previousPage;
	}
	
	public boolean isNext() {
		return this.nextPage != null;
	}
	
	public boolean isPrevious() {
		return this.previousPage != null;
	}
}
