package com.zendesk.ticketviewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.zendesk.ticketviewer.Ticket.TicketStatus;

/**
 * Unit test for simple App.
 */
public class TicketTest 
{
    @Test
    public void parseTest() {
    	String ticketStr = "{\n"
    			+ "  \"url\": \"https://zccselvaganeshmuthu.zendesk.com/api/v2/tickets/12.json\",\n"
    			+ "  \"id\": 12,\n"
    			+ "  \"external_id\": null,\n"
    			+ "  \"via\": {\n"
    			+ "    \"channel\": \"api\",\n"
    			+ "    \"source\": {\n"
    			+ "      \"from\": {},\n"
    			+ "      \"to\": {},\n"
    			+ "      \"rel\": null\n"
    			+ "    }\n"
    			+ "  },\n"
    			+ "  \"created_at\": \"2021-11-19T23:02:39Z\",\n"
    			+ "  \"updated_at\": \"2021-11-19T23:02:39Z\",\n"
    			+ "  \"type\": null,\n"
    			+ "  \"subject\": \"tempor aliquip sint dolore incididunt\",\n"
    			+ "  \"raw_subject\": \"tempor aliquip sint dolore incididunt\",\n"
    			+ "  \"description\": \"Amet sint ea minim excepteur amet.\",\n"
    			+ "  \"priority\": null,\n"
    			+ "  \"status\": \"open\",\n"
    			+ "  \"recipient\": null,\n"
    			+ "  \"requester_id\": 1909816943725,\n"
    			+ "  \"submitter_id\": 1909816943725,\n"
    			+ "  \"assignee_id\": 1909816943725,\n"
    			+ "  \"organization_id\": 1500627834741,\n"
    			+ "  \"group_id\": 1500006565981,\n"
    			+ "  \"collaborator_ids\": [],\n"
    			+ "  \"follower_ids\": [],\n"
    			+ "  \"email_cc_ids\": [],\n"
    			+ "  \"forum_topic_id\": null,\n"
    			+ "  \"problem_id\": null,\n"
    			+ "  \"has_incidents\": false,\n"
    			+ "  \"is_public\": true,\n"
    			+ "  \"due_at\": null,\n"
    			+ "  \"tags\": [\n"
    			+ "    \"ad\",\n"
    			+ "    \"minim\",\n"
    			+ "    \"non\"\n"
    			+ "  ],\n"
    			+ "  \"custom_fields\": [],\n"
    			+ "  \"satisfaction_rating\": null,\n"
    			+ "  \"sharing_agreement_ids\": [],\n"
    			+ "  \"fields\": [],\n"
    			+ "  \"followup_ids\": [],\n"
    			+ "  \"ticket_form_id\": 1500003279201,\n"
    			+ "  \"brand_id\": 1500002328461,\n"
    			+ "  \"allow_channelback\": false,\n"
    			+ "  \"allow_attachments\": true\n"
    			+ "}";
    	JSONObject ticketJSON = new JSONObject(ticketStr);
    	Ticket ticket = Ticket.parse(ticketJSON);
    	testTicketEquality(ticket);
    }

	private void testTicketEquality(Ticket ticket) {
		assertEquals(12L, ticket.getId());
    	assertEquals(1909816943725L, ticket.getRequesterId());
    	ZonedDateTime createdAt = ZonedDateTime.of(2021, 11, 19, 23, 2, 39, 0, ZoneId.of("Z"));
    	assertTrue(createdAt.compareTo(ticket.getCreatedAt()) == 0);
    	ZonedDateTime updatedAt = ZonedDateTime.of(2021, 11, 19, 23, 2, 39, 0, ZoneId.of("Z"));
    	assertTrue(updatedAt.compareTo(ticket.getUpdatedAt()) == 0);
    	assertEquals("tempor aliquip sint dolore incididunt", ticket.getSubject());
    	assertEquals("Amet sint ea minim excepteur amet.", ticket.getDescription());
    	assertEquals(TicketStatus.OPEN, ticket.getStatus());
    	assertEquals(null, ticket.getPriority());
    	Long assigneeId = 1909816943725L;
    	assertEquals(assigneeId, ticket.getAssigneeId());
    	assertEquals(null, ticket.getDueAt());
    	List<String> tags = Arrays.asList("ad", "minim", "non");
    	for(int i = 0; i < ticket.getTags().size(); i++) {
    		assertEquals(tags.get(i), ticket.getTags().get(i));
    	}
	}
    
}
