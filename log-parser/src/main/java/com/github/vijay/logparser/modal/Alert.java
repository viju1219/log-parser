package com.github.vijay.logparser.modal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "alerts")
public class Alert {
	@Id
	@JsonProperty("id")
	private String id;

	@JsonProperty("duration")
	private long duration;

	@JsonProperty("type")
	private String type;

	@JsonProperty("host")
	private String host;

	@JsonProperty("alert")
	private boolean alert;
	
	public Alert() {}

	public Alert(Event event, long duration) {
		this.id = event.getId();
		this.type = event.getType();
		this.host = event.getHost();
		this.duration = duration;
		this.alert = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

}
