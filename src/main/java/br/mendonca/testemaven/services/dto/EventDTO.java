package br.mendonca.testemaven.services.dto;

import br.mendonca.testemaven.model.entities.Event;

public class EventDTO {

	private String uuid;
	private String userId;
	private String eventName;
	private int date;
	private Boolean hasPassed;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public Boolean getHasPassed() {
		return hasPassed;
	}
	public void setHasPassed(Boolean hasPassed) {
		this.hasPassed = hasPassed;
	}
	
	
	public static EventDTO eventMapper(Event event) {
		EventDTO dto = new EventDTO();
		dto.setUuid(event.getUuid());
		dto.setUserId(event.getUserId());
		dto.setEventName(event.getEventName());
		dto.setDate(event.getDate());
		dto.setHasPassed(event.getHasPassed());
		
		return dto;
	}
}
