package com.zendesk.ticketviewer.util;

import static org.junit.Assert.assertEquals;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.zendesk.ticketviewer.Ticket;
import com.zendesk.ticketviewer.Ticket.TicketBuilder;
import com.zendesk.ticketviewer.Ticket.TicketStatus;
import com.zendesk.ticketviewer.Tickets;

public class TicketViewUtilTest {

	@Test
	public void parseIfLongTest() {
		String str = "num";
		String numStr = "55";
		Long expected = 55L;
		assertEquals(null, TicketViewUtil.parseIfLong(str));
		assertEquals(expected, TicketViewUtil.parseIfLong(numStr));
	}
	
	@Test
	public void getFullTicketDetailsPrintTest() {
		long id = 1234l;
		long requesterId = 12l;
		String priority = null;
		String subject = "new ticket";
		String description = "found an issue!";
		ZonedDateTime createdAt = ZonedDateTime.of(2020, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		ZonedDateTime updatedAt = ZonedDateTime.of(2021, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		TicketStatus ticketStatus = TicketStatus.CLOSED;
		Long assigneeId = null;
		ZonedDateTime dueAt = ZonedDateTime.of(2021, 12, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		List<String> tags = Arrays.asList("zendesk", "ticket");
		
		TicketBuilder ticketBuilder = new TicketBuilder()
				.setId(id)
				.setRequesterId(requesterId)
				.setPriority(priority)
				.setSubject(subject)
				.setDescription(description)
				.setCreatedAt(createdAt)
				.setUpdatedAt(updatedAt)
				.setTicketStatus(ticketStatus)
				.setAssigneeId(assigneeId)
				.setDueAt(dueAt)
				.setTags(tags);
		Ticket ticket = ticketBuilder.build();
		
		String fullTicketDetailsPrint = TicketViewUtil.getFullTicketDetailsPrint(ticket);
		
		String tempFullTicketDetailsPrint = "Id                  :1234\n"
				+ "Requester Id        :12\n"
				+ "Priority            :\n"
				+ "Subject             :new ticket\n"
				+ "Description         :found an issue!\n"
				+ "Created At          :Thu, Nov 19, 2020, 22:16:40\n"
				+ "Updated At          :Fri, Nov 19, 2021, 22:16:40\n"
				+ "Status              :closed\n"
				+ "Assignee Id         :\n"
				+ "Due At              :Sun, Dec 19, 2021, 22:16:40\n"
				+ "Tags                :zendesk,ticket\n"
				+ "";
		assertEquals(tempFullTicketDetailsPrint, fullTicketDetailsPrint);
		assertEquals(null, TicketViewUtil.getFullTicketDetailsPrint(null));
	}
	
	@Test
	public void getTicketsPrintTest() {
		long id = 1l;
		long requesterId = 12l;
		String priority = null;
		String subject = "new ticket";
		String description = "found an issue!";
		ZonedDateTime createdAt = ZonedDateTime.of(2020, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		ZonedDateTime updatedAt = ZonedDateTime.of(2021, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		TicketStatus ticketStatus = TicketStatus.CLOSED;
		Long assigneeId = null;
		ZonedDateTime dueAt = ZonedDateTime.of(2021, 12, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		List<String> tags = Arrays.asList("zendesk", "ticket");
		
		TicketBuilder ticketBuilder = new TicketBuilder()
				.setId(id)
				.setRequesterId(requesterId)
				.setPriority(priority)
				.setSubject(subject)
				.setDescription(description)
				.setCreatedAt(createdAt)
				.setUpdatedAt(updatedAt)
				.setTicketStatus(ticketStatus)
				.setAssigneeId(assigneeId)
				.setDueAt(dueAt)
				.setTags(tags);
		
		Ticket ticket1 = ticketBuilder.build();
		long id2 = 2l;
		long requesterId2 = 12l;
		String priority2 = null;
		String subject2 = "new second ticket";
		String description2 = "found another issue!";
		ZonedDateTime createdAt2 = ZonedDateTime.of(2010, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		ZonedDateTime updatedAt2 = ZonedDateTime.of(2011, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		TicketStatus ticketStatus2 = TicketStatus.OPEN;
		Long assigneeId2 = null;
		ZonedDateTime dueAt2 = ZonedDateTime.of(2021, 12, 19, 22, 16, 40, 0, ZoneId.of("Z"));
		List<String> tags2 = null;
		
		TicketBuilder ticketBuilder2 = new TicketBuilder()
				.setId(id2)
				.setRequesterId(requesterId2)
				.setPriority(priority2)
				.setSubject(subject2)
				.setDescription(description2)
				.setCreatedAt(createdAt2)
				.setUpdatedAt(updatedAt2)
				.setTicketStatus(ticketStatus2)
				.setAssigneeId(assigneeId2)
				.setDueAt(dueAt2)
				.setTags(tags2);
		Ticket ticket2 = ticketBuilder2.build();
		
		List<Ticket> ticketsList = new ArrayList<>();
		ticketsList.add(ticket1);
		ticketsList.add(ticket2);
		
		Tickets tickets = new Tickets(ticketsList, null, null);
		String ticketsPrint = TicketViewUtil.getTicketsPrint(tickets);
		

		String tempTicketsPrint = "| Id         | Priority   | Subject                                            | Requester Id         | Updated At                          | Status     | Assignee Id          | \n"
				+ "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"
				+ "| 1          |            | new ticket                                         | 12                   | Fri, Nov 19, 2021, 22:16:40         | closed     |                      | \n"
				+ "| 2          |            | new second ticket                                  | 12                   | Sat, Nov 19, 2011, 22:16:40         | open       |                      | \n"
				+ "";
		assertEquals(tempTicketsPrint, ticketsPrint);
		assertEquals(null, TicketViewUtil.getTicketsPrint(null));
	}
	
	
}
