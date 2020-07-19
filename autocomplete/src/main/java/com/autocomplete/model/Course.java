package com.autocomplete.model;

import lombok.Data;

@Data
public class Course {
	
	private String id; 
	private String instructor;
	private String topic;
	private String subject;
	private String title;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", instructor=" + instructor + ", topic=" + topic + ", subject=" + subject
				+ ", title=" + title + "]";
	}
	
	

}
