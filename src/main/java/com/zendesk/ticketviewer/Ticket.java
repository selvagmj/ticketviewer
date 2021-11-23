package com.zendesk.ticketviewer;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zendesk.ticketviewer.util.DateUtil;

public class Ticket {

	private ZonedDateTime createdAt;
	private TicketStatus status;
	private String subject;
	private String description;
	private ZonedDateTime updatedAt;
	private long id;
	private long requesterId;
	private String priority;
	private Long assigneeId;
	private ZonedDateTime dueAt;
	private List<String> tags;
	
	private Ticket(TicketBuilder ticketBuilder) {
		this.createdAt = ticketBuilder.createdAt;
		this.status = ticketBuilder.ticketStatus;
		this.subject = ticketBuilder.subject;
		this.description = ticketBuilder.description;
		this.id = ticketBuilder.id;
		this.updatedAt = ticketBuilder.updatedAt;
		this.requesterId = ticketBuilder.requesterId;
		this.priority = ticketBuilder.priority;
		this.assigneeId = ticketBuilder.assigneeId;
		this.dueAt = ticketBuilder.dueAt;
		this.tags = ticketBuilder.tags;
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
	
	public String getDescritpion() {
		return this.description;
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
	
	public Long getAssigneeId() {
		return this.assigneeId;
	}
	
	public ZonedDateTime getDueAt() {
		return this.dueAt;
	}
	
	public List<String> getTags() {
		return this.tags;
	}

	// Use jackson in case of large scale project 
	public static Ticket parse(JSONObject ticketJSON) {
		TicketBuilder ticketBuilder = new TicketBuilder();
		
		ticketBuilder.setId(ticketJSON.getLong(TicketParams.ID));
		ticketBuilder.setRequesterId(ticketJSON.getLong(TicketParams.REQUESTER_ID));
		
		String createdAt = ticketJSON.getString(TicketParams.CREATED_AT);
		if(createdAt != null) {
			ticketBuilder.setCreatedAt(DateUtil.getDate(createdAt));
		}
		
		String updatedAt = ticketJSON.optString(TicketParams.UPDATED_AT, null);
		if(updatedAt != null) {
			ticketBuilder.setUpdatedAt(DateUtil.getDate(updatedAt));
		}
		
		String subject = ticketJSON.optString(TicketParams.SUBJECT, null);
		if(subject != null) {
			ticketBuilder.setSubject(subject);
		}
		
		String description = ticketJSON.optString(TicketParams.DESCRIPTION, null);
		if(description != null) {
			ticketBuilder.setDescription(description);
		}
		
		String statusStr = ticketJSON.optString(TicketParams.STATUS);
		if(statusStr != null) {
			ticketBuilder.setTicketStatus(TicketStatus.valueOf(statusStr.toUpperCase()));
		}
		
		String priority = ticketJSON.optString(TicketParams.PRIORITY, null);
		if(priority != null) {
			ticketBuilder.setPriority(priority);
		}
		
		if(ticketJSON.has(TicketParams.ASSIGNEE_ID)) {
			Long assigneeId = ticketJSON.getLong(TicketParams.ASSIGNEE_ID);
			ticketBuilder.setAssigneeId(assigneeId);
		}
		
		String dueAt = ticketJSON.optString(TicketParams.DUE_AT, null);
		if(dueAt != null) {
			ticketBuilder.setDueAt(DateUtil.getDate(dueAt));
		}
		
		JSONArray tagsArray = ticketJSON.optJSONArray(TicketParams.TAGS);
		if(tagsArray != null) {
			List<String> tags = new ArrayList<>();
			for(int i = 0; i < tagsArray.length(); i++) {
				tags.add(tagsArray.getString(i));
			}
			ticketBuilder.setTags(tags);
			
		}
		return ticketBuilder.build();
	}
	
	public static final class TicketParams {
		public static final String CREATED_AT = "created_at";
		public static final String STATUS = "status";
		public static final String SUBJECT = "subject";
		public static final String DESCRIPTION = "description";
		public static final String UPDATED_AT = "updated_at";
		public static final String ID = "id";
		public static final String REQUESTER_ID = "requester_id";
		public static final String PRIORITY = "priority";
		public static final String ASSIGNEE_ID = "assignee_id";
		public static final String DUE_AT = "due_at";
		public static final String TAGS = "tags";
	}
	
	private static class TicketBuilder {
		private ZonedDateTime createdAt;
		private TicketStatus ticketStatus;
		private String subject;
		private ZonedDateTime updatedAt;
		private long id;
		private long requesterId;
		private String priority;
		private Long assigneeId;
		private String description;
		private ZonedDateTime dueAt;
		private List<String> tags;
		
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
		
		public TicketBuilder setDescription(String description) {
			this.description = description;
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
		
		public TicketBuilder setDueAt(ZonedDateTime dueAt) {
			this.dueAt = dueAt;
			return this;
		}
		
		public TicketBuilder setTags(List<String> tags) {
			this.tags = tags;
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
		NEW(0),
		OPEN(1),
		PENDING(2),
		HOLD(3),
		SOLVED(4),
		CLOSED(5);
		
		private final int statusId;
		
		private TicketStatus(int statusId) {
			this.statusId = statusId;
		}
		
		public int getTicketStatusId() {
			return this.statusId;
		}
		
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}
}
