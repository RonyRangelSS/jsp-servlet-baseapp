package br.mendonca.testemaven.model.entities;

public class Event {
	
	private String uuid;
	private String userId;
	private String eventName;
	private int date;
	private Boolean hasPassed;
	private Boolean isVisible;
	
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
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) { this.isVisible = isVisible; }
}
