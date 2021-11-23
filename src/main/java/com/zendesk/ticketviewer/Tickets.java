package com.zendesk.ticketviewer;

import java.util.List;

// Tickets object mainly used for easy movement of data between Java APIs
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
	
	// When there is no next page number it means there are no more ticket. 
	public boolean isNext() {
		return this.nextPage != null;
	}
	
	public boolean isPrevious() {
		return this.previousPage != null;
	}
}
