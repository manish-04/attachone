package com.farzi.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
public class Job {

	@Id
    private String id;
    private String title;
    private String description;
    private String date;
    private String ownerId;
    
    private List<String> applicants = new ArrayList<String>();
    
	public List<String> getApplicants() {
		return applicants;
	}
	public void setApplicants(List<String> applicants) {
		this.applicants = applicants;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public Job() {
		super();
	}
	
    
    
}
