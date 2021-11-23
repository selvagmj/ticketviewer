package com.zendesk.ticketviewer.util;

public class TicketViewerUtil {

	public static Long parseIfLong(String operationStr) {
		try {
			return Long.parseLong(operationStr);
		}
		catch(Exception e) {
			return null;
		}
	}
}
