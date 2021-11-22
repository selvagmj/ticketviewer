package com.zendesk.ticketviewer;

import org.json.JSONObject;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TicketTest 
{
    @Test
    public void parseTest() {
    	JSONObject ticketJSON = new JSONObject();
    	ticketJSON.put("created_at", "");
    	ticketJSON.put("status", "");
    	ticketJSON.put("subject", "");
    	ticketJSON.put("updated_at", "");
    	ticketJSON.put("id", "");
    	ticketJSON.put("requester_id", "");
    }
}
