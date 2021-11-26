package com.zendesk.ticketviewer.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.zendesk.ticketviewer.Ticket;
import com.zendesk.ticketviewer.Tickets;
import com.zendesk.ticketviewer.Ticket.TicketStatus;
import com.zendesk.ticketviewer.http.Response;

public class TicketAPITest {

	@Test
	public void getTickets() {
		Response responseError404 = new Response(404, null);
		assertEquals(null, TicketAPI.getTickets(responseError404));
		
		Response responseErrorNull = null;
		assertEquals(null, TicketAPI.getTickets(responseErrorNull));
		
		Response responseErrorEmpty = new Response(404, "{}");
		assertEquals(null, TicketAPI.getTickets(responseErrorEmpty));
		
		JSONObject errorJSON = new JSONObject();
		errorJSON.put("error", "Invalid query params");
		Response responseError = new Response(404, errorJSON.toString());
		assertEquals(null, TicketAPI.getTickets(responseError));
		
		String successStr = "{\n"
				+ "  \"next_page\": \"https://zccselvaganeshmuthu.zendesk.com/api/v2/tickets.json?page=2&per_page=25\",\n"
				+ "  \"tickets\": [\n"
				+ "    {\n"
				+ "      \"subject\": \"Sample ticket: Meet the ticket\",\n"
				+ "      \"email_cc_ids\": [],\n"
				+ "      \"created_at\": \"2021-11-19T22:16:40Z\",\n"
				+ "      \"description\": \"Hi there,I’m sending an email because I’m having a problem setting up your new product. Can you help me troubleshoot?\",\n"
				+ "      \"external_id\": null,\n"
				+ "      \"type\": \"incident\",\n"
				+ "      \"via\": {\n"
				+ "        \"channel\": \"sample_ticket\",\n"
				+ "        \"source\": {\n"
				+ "          \"rel\": null,\n"
				+ "          \"from\": {},\n"
				+ "          \"to\": {}\n"
				+ "        }\n"
				+ "      },\n"
				+ "      \"allow_attachments\": true,\n"
				+ "      \"updated_at\": \"2021-11-19T22:16:40Z\",\n"
				+ "      \"problem_id\": null,\n"
				+ "      \"follower_ids\": [],\n"
				+ "      \"due_at\": null,\n"
				+ "      \"id\": 1,\n"
				+ "      \"assignee_id\": 1909816943725,\n"
				+ "      \"raw_subject\": \"Sample ticket: Meet the ticket\",\n"
				+ "      \"forum_topic_id\": null,\n"
				+ "      \"custom_fields\": [],\n"
				+ "      \"allow_channelback\": false,\n"
				+ "      \"satisfaction_rating\": null,\n"
				+ "      \"submitter_id\": 1909816943725,\n"
				+ "      \"priority\": \"normal\",\n"
				+ "      \"collaborator_ids\": [],\n"
				+ "      \"url\": \"https://zccselvaganeshmuthu.zendesk.com/api/v2/tickets/1.json\",\n"
				+ "      \"tags\": [\n"
				+ "        \"sample\",\n"
				+ "        \"support\",\n"
				+ "        \"zendesk\"\n"
				+ "      ],\n"
				+ "      \"brand_id\": 1500002328461,\n"
				+ "      \"ticket_form_id\": 1500003279201,\n"
				+ "      \"sharing_agreement_ids\": [],\n"
				+ "      \"group_id\": 1500006565981,\n"
				+ "      \"organization_id\": null,\n"
				+ "      \"followup_ids\": [],\n"
				+ "      \"recipient\": null,\n"
				+ "      \"is_public\": true,\n"
				+ "      \"has_incidents\": false,\n"
				+ "      \"fields\": [],\n"
				+ "      \"status\": \"open\",\n"
				+ "      \"requester_id\": 1523685987621\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"subject\": \"velit eiusmod reprehenderit officia cupidatat\",\n"
				+ "      \"email_cc_ids\": [],\n"
				+ "      \"created_at\": \"2021-11-19T23:02:32Z\",\n"
				+ "      \"description\": \"Aute ex sunt culpa ex ea esse sint cupidatat aliqua ex consequat sit reprehenderit. Velit labore proident quis culpa ad duis adipisicing laboris voluptate velit incididunt minim consequat nulla. Laboris adipisicing reprehenderit minim tempor officia ullamco occaecat ut laborum.Aliquip velit adipisicing exercitation irure aliqua qui. Commodo eu laborum cillum nostrud eu. Mollit duis qui non ea deserunt est est et officia ut excepteur Lorem pariatur deserunt.\",\n"
				+ "      \"external_id\": null,\n"
				+ "      \"type\": null,\n"
				+ "      \"via\": {\n"
				+ "        \"channel\": \"api\",\n"
				+ "        \"source\": {\n"
				+ "          \"rel\": null,\n"
				+ "          \"from\": {},\n"
				+ "          \"to\": {}\n"
				+ "        }\n"
				+ "      },\n"
				+ "      \"allow_attachments\": true,\n"
				+ "      \"updated_at\": \"2021-11-19T23:02:32Z\",\n"
				+ "      \"problem_id\": null,\n"
				+ "      \"follower_ids\": [],\n"
				+ "      \"due_at\": null,\n"
				+ "      \"id\": 2,\n"
				+ "      \"assignee_id\": 1909816943725,\n"
				+ "      \"raw_subject\": \"velit eiusmod reprehenderit officia cupidatat\",\n"
				+ "      \"forum_topic_id\": null,\n"
				+ "      \"custom_fields\": [],\n"
				+ "      \"allow_channelback\": false,\n"
				+ "      \"satisfaction_rating\": null,\n"
				+ "      \"submitter_id\": 1909816943725,\n"
				+ "      \"priority\": null,\n"
				+ "      \"collaborator_ids\": [],\n"
				+ "      \"url\": \"https://zccselvaganeshmuthu.zendesk.com/api/v2/tickets/2.json\",\n"
				+ "      \"tags\": [\n"
				+ "        \"est\",\n"
				+ "        \"incididunt\",\n"
				+ "        \"nisi\"\n"
				+ "      ],\n"
				+ "      \"brand_id\": 1500002328461,\n"
				+ "      \"ticket_form_id\": 1500003279201,\n"
				+ "      \"sharing_agreement_ids\": [],\n"
				+ "      \"group_id\": 1500006565981,\n"
				+ "      \"organization_id\": 1500627834741,\n"
				+ "      \"followup_ids\": [],\n"
				+ "      \"recipient\": null,\n"
				+ "      \"is_public\": true,\n"
				+ "      \"has_incidents\": false,\n"
				+ "      \"fields\": [],\n"
				+ "      \"status\": \"open\",\n"
				+ "      \"requester_id\": 1909816943725\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"count\": 201,\n"
				+ "  \"previous_page\": null\n"
				+ "}";
		Response successResponse = new Response(200, successStr);
		Tickets tickets = TicketAPI.getTickets(successResponse);
		assertEquals(2, tickets.getTickets().size());
		testTicket1Equality(tickets.getTickets().get(0));
		testTicket2Equality(tickets.getTickets().get(1));
		assertEquals(null, tickets.getPreviousPage());
		Long nextPage = 2L;
		assertEquals(nextPage, tickets.getNextPage());
		assertFalse(tickets.isPrevious());
		assertTrue(tickets.isNext());
	}

	private void testTicket1Equality(Ticket ticket) {
		assertEquals(1L, ticket.getId());
    	assertEquals(1523685987621L, ticket.getRequesterId());
    	ZonedDateTime createdAt = ZonedDateTime.of(2021, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
    	assertTrue(createdAt.compareTo(ticket.getCreatedAt()) == 0);
    	ZonedDateTime updatedAt = ZonedDateTime.of(2021, 11, 19, 22, 16, 40, 0, ZoneId.of("Z"));
    	assertTrue(updatedAt.compareTo(ticket.getUpdatedAt()) == 0);
    	assertEquals("Sample ticket: Meet the ticket", ticket.getSubject());
    	assertEquals("Hi there,I’m sending an email because I’m having a problem setting up your new product. Can you help me troubleshoot?", ticket.getDescription());
    	assertEquals(TicketStatus.OPEN, ticket.getStatus());
    	assertEquals("normal", ticket.getPriority());
    	Long assigneeId = 1909816943725L;
    	assertEquals(assigneeId, ticket.getAssigneeId());
    	assertEquals(null, ticket.getDueAt());
    	List<String> tags = Arrays.asList("sample", "support", "zendesk");
    	for(int i = 0; i < ticket.getTags().size(); i++) {
    		assertEquals(tags.get(i), ticket.getTags().get(i));
    	}
	}

	private void testTicket2Equality(Ticket ticket) {
		assertEquals(2L, ticket.getId());
    	assertEquals(1909816943725L, ticket.getRequesterId());
    	ZonedDateTime createdAt = ZonedDateTime.of(2021, 11, 19, 23, 2, 32, 0, ZoneId.of("Z"));
    	assertTrue(createdAt.compareTo(ticket.getCreatedAt()) == 0);
    	ZonedDateTime updatedAt = ZonedDateTime.of(2021, 11, 19, 23, 2, 32, 0, ZoneId.of("Z"));
    	assertTrue(updatedAt.compareTo(ticket.getUpdatedAt()) == 0);
    	assertEquals("velit eiusmod reprehenderit officia cupidatat", ticket.getSubject());
    	assertEquals("Aute ex sunt culpa ex ea esse sint cupidatat aliqua ex consequat sit reprehenderit. Velit labore proident quis culpa ad duis adipisicing laboris voluptate velit incididunt minim consequat nulla. Laboris adipisicing reprehenderit minim tempor officia ullamco occaecat ut laborum.Aliquip velit adipisicing exercitation irure aliqua qui. Commodo eu laborum cillum nostrud eu. Mollit duis qui non ea deserunt est est et officia ut excepteur Lorem pariatur deserunt.", ticket.getDescription());
    	assertEquals(TicketStatus.OPEN, ticket.getStatus());
    	assertEquals(null, ticket.getPriority());
    	Long assigneeId = 1909816943725L;
    	assertEquals(assigneeId, ticket.getAssigneeId());
    	assertEquals(null, ticket.getDueAt());
    	List<String> tags = Arrays.asList("est", "incididunt", "nisi");
    	for(int i = 0; i < ticket.getTags().size(); i++) {
    		assertEquals(tags.get(i), ticket.getTags().get(i));
    	}
	}
	
	@Test
	public void getTicket() {
		Response responseError404 = new Response(404, null);
		assertEquals(null, TicketAPI.getTicket(responseError404));
		
		Response responseErrorNull = null;
		assertEquals(null, TicketAPI.getTicket(responseErrorNull));
		
		Response responseErrorEmpty = new Response(404, "{}");
		assertEquals(null, TicketAPI.getTicket(responseErrorEmpty));
		
		JSONObject errorJSON = new JSONObject();
		errorJSON.put("error", "RecordNotFound");
		Response responseError = new Response(500, errorJSON.toString());
		assertEquals(null, TicketAPI.getTicket(responseError));
		
		String successStr = "{\n"
				+ "  \"ticket\": {\n"
				+ "    \"subject\": \"in labore quis mollit mollit\",\n"
				+ "    \"email_cc_ids\": [],\n"
				+ "    \"created_at\": \"2021-11-19T23:02:47Z\",\n"
				+ "    \"description\": \"Dolor sit deserunt amet enim et in esse exercitation culpa ipsum adipisicing magna sunt. Commodo consequat magna irure enim dolor dolore consectetur est. Nisi do commodo consequat fugiat. Id cupidatat sit velit commodo. Sunt eu ex consectetur ut excepteur fugiat. Do ut amet ea fugiat esse cillum esse.Dolore ex ipsum eu consectetur qui Lorem ad ex consectetur quis culpa cupidatat. Veniam reprehenderit qui ea sint ullamco dolore voluptate dolor veniam culpa. Ex sint sit irure pariatur eu. Minim non ea officia officia. Magna irure esse do Lorem pariatur tempor fugiat voluptate tempor laboris cupidatat voluptate Lorem velit. Proident consectetur velit Lorem laboris dolore non velit do est.\",\n"
				+ "    \"external_id\": null,\n"
				+ "    \"type\": null,\n"
				+ "    \"via\": {\n"
				+ "      \"channel\": \"api\",\n"
				+ "      \"source\": {\n"
				+ "        \"rel\": null,\n"
				+ "        \"from\": {},\n"
				+ "        \"to\": {}\n"
				+ "      }\n"
				+ "    },\n"
				+ "    \"allow_attachments\": true,\n"
				+ "    \"updated_at\": \"2021-11-19T23:02:47Z\",\n"
				+ "    \"problem_id\": null,\n"
				+ "    \"follower_ids\": [],\n"
				+ "    \"due_at\": null,\n"
				+ "    \"id\": 26,\n"
				+ "    \"assignee_id\": 1909816943725,\n"
				+ "    \"raw_subject\": \"in labore quis mollit mollit\",\n"
				+ "    \"forum_topic_id\": null,\n"
				+ "    \"custom_fields\": [],\n"
				+ "    \"allow_channelback\": false,\n"
				+ "    \"satisfaction_rating\": null,\n"
				+ "    \"submitter_id\": 1909816943725,\n"
				+ "    \"priority\": null,\n"
				+ "    \"collaborator_ids\": [],\n"
				+ "    \"url\": \"https://zccselvaganeshmuthu.zendesk.com/api/v2/tickets/26.json\",\n"
				+ "    \"tags\": [\n"
				+ "      \"aliquip\",\n"
				+ "      \"amet\",\n"
				+ "      \"sint\"\n"
				+ "    ],\n"
				+ "    \"brand_id\": 1500002328461,\n"
				+ "    \"ticket_form_id\": 1500003279201,\n"
				+ "    \"sharing_agreement_ids\": [],\n"
				+ "    \"group_id\": 1500006565981,\n"
				+ "    \"organization_id\": 1500627834741,\n"
				+ "    \"followup_ids\": [],\n"
				+ "    \"recipient\": null,\n"
				+ "    \"is_public\": true,\n"
				+ "    \"has_incidents\": false,\n"
				+ "    \"fields\": [],\n"
				+ "    \"status\": \"open\",\n"
				+ "    \"requester_id\": 1909816943725\n"
				+ "  }\n"
				+ "}";
		Response responseSuccess = new Response(200, successStr);
		Ticket ticket = TicketAPI.getTicket(responseSuccess);
		testTicketEquality(ticket);
	}

	private void testTicketEquality(Ticket ticket) {
		assertEquals(26L, ticket.getId());
    	assertEquals(1909816943725L, ticket.getRequesterId());
    	ZonedDateTime createdAt = ZonedDateTime.of(2021, 11, 19, 23, 2, 47, 0, ZoneId.of("Z"));
    	assertTrue(createdAt.compareTo(ticket.getCreatedAt()) == 0);
    	ZonedDateTime updatedAt = ZonedDateTime.of(2021, 11, 19, 23, 2, 47, 0, ZoneId.of("Z"));
    	assertTrue(updatedAt.compareTo(ticket.getUpdatedAt()) == 0);
    	assertEquals("in labore quis mollit mollit", ticket.getSubject());
    	assertEquals("Dolor sit deserunt amet enim et in esse exercitation culpa ipsum adipisicing magna sunt. Commodo consequat magna irure enim dolor dolore consectetur est. Nisi do commodo consequat fugiat. Id cupidatat sit velit commodo. Sunt eu ex consectetur ut excepteur fugiat. Do ut amet ea fugiat esse cillum esse.Dolore ex ipsum eu consectetur qui Lorem ad ex consectetur quis culpa cupidatat. Veniam reprehenderit qui ea sint ullamco dolore voluptate dolor veniam culpa. Ex sint sit irure pariatur eu. Minim non ea officia officia. Magna irure esse do Lorem pariatur tempor fugiat voluptate tempor laboris cupidatat voluptate Lorem velit. Proident consectetur velit Lorem laboris dolore non velit do est.", ticket.getDescription());
    	assertEquals(TicketStatus.OPEN, ticket.getStatus());
    	assertEquals(null, ticket.getPriority());
    	Long assigneeId = 1909816943725L;
    	assertEquals(assigneeId, ticket.getAssigneeId());
    	assertEquals(null, ticket.getDueAt());
    	List<String> tags = Arrays.asList("aliquip", "amet", "sint");
    	for(int i = 0; i < ticket.getTags().size(); i++) {
    		assertEquals(tags.get(i), ticket.getTags().get(i));
    	}
	}
}
