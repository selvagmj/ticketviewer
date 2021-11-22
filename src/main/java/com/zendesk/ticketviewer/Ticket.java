package com.zendesk.ticketviewer;

import java.time.ZonedDateTime;

import org.json.JSONObject;

import com.zendesk.ticketviewer.util.DateUtil;

public class Ticket {

	private ZonedDateTime createdAt;
	private TicketStatus status;
	private String subject;
	private ZonedDateTime updatedAt;
	private long id;
	private long requesterId;
	private String priority;
	private long assigneeId;
	
	private Ticket(TicketBuilder ticketBuilder) {
		this.createdAt = ticketBuilder.createdAt;
		this.status = ticketBuilder.ticketStatus;
		this.subject = ticketBuilder.subject;
		this.id = ticketBuilder.id;
		this.updatedAt = ticketBuilder.updatedAt;
		this.requesterId = ticketBuilder.requesterId;
		this.priority = ticketBuilder.priority;
		this.assigneeId = ticketBuilder.assigneeId;
	}
	
	public ZonedDateTime getCreatedAt() {
		return this.createdAt;
	}

	public TicketStatus getStatus() {
		return this.status;
	}

	public String getSubject() {
		return this.subject;
	}

	public ZonedDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	public long getId() {
		return this.id;
	}

	public long getRequesterId() {
		return this.requesterId;
	}
	
	public String getPriority() {
		return this.priority;
	}
	
	public long getAssigneeId() {
		return this.assigneeId;
	}

	public static Ticket parse(JSONObject ticketJSON) {
		TicketBuilder ticketBuilder = new TicketBuilder();
		String createdAt = ticketJSON.getString(TicketParams.CREATED_AT);
		ticketBuilder.setCreatedAt(DateUtil.getDate(createdAt));
		String updatedAt = ticketJSON.getString(TicketParams.UPDATED_AT);
		ticketBuilder.setUpdatedAt(DateUtil.getDate(updatedAt));
		ticketBuilder.setSubject(ticketJSON.getString(TicketParams.SUBJECT));
		ticketBuilder.setId(ticketJSON.getLong(TicketParams.ID));
		ticketBuilder.setRequesterId(ticketJSON.getLong(TicketParams.REQUESTER_ID));
		ticketBuilder.setTicketStatus(TicketStatus.valueOf(ticketJSON.getString(TicketParams.STATUS).toUpperCase()));
		ticketBuilder.setPriority(ticketJSON.optString(TicketParams.PRIORITY, null));
		ticketBuilder.setAssigneeId(ticketJSON.getLong(TicketParams.ASSIGNEE_ID));
		return ticketBuilder.build();
	}
	
	public static final class TicketParams {
		public static final String CREATED_AT = "created_at";
		public static final String STATUS = "status";
		public static final String SUBJECT = "subject";
		public static final String UPDATED_AT = "updated_at";
		public static final String ID = "id";
		public static final String REQUESTER_ID = "requester_id";
		public static final String PRIORITY = "priority";
		public static final String ASSIGNEE_ID = "assignee_id";
	}
	
	private static class TicketBuilder {
		private ZonedDateTime createdAt;
		private TicketStatus ticketStatus;
		private String subject;
		private ZonedDateTime updatedAt;
		private long id;
		private long requesterId;
		private String priority;
		private long assigneeId;
		
		public TicketBuilder setCreatedAt(ZonedDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}
		
		public TicketBuilder setTicketStatus(TicketStatus ticketStatus) {
			this.ticketStatus = ticketStatus;
			return this;
		}
		
		public TicketBuilder setSubject(String subject) {
			this.subject = subject;
			return this;
		}
		
		public TicketBuilder setUpdatedAt(ZonedDateTime updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}
		
		public TicketBuilder setId(long id) {
			this.id = id;
			return this;
		}
		
		public TicketBuilder setRequesterId(long requesterId) {
			this.requesterId = requesterId;
			return this;
		}
		
		public TicketBuilder setPriority(String priority) {
			this.priority = priority;
			return this;
		}
		
		public TicketBuilder setAssigneeId(long assigneeId) {
			this.assigneeId = assigneeId;
			return this;
		}
		
		public Ticket build() {
			return new Ticket(this);
		}
	}
	
	@Override
	public String toString() {
		return "Ticket [createdAt=" + createdAt + ", status=" + status + ", subject=" + subject + ", updatedAt="
				+ updatedAt + ", id=" + id + ", requesterId=" + requesterId + "]";
	}



	public enum TicketStatus {
		OPEN(0);
		
		private final int statusId;
		
		private TicketStatus(int statusId) {
			this.statusId = statusId;
		}
		
		public int getTicketStatusId() {
			return this.statusId;
		}
	}
}
