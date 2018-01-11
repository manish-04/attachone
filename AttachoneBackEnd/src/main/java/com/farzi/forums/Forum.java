package com.farzi.forums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.farzi.blogs.BlogPost;

@Document(collection = "forums")
public class Forum {

	@Id
    private String id;
    private String title;
    private String description;
    private String date;
    private String ownerId;
    
    private boolean approved = false;
    
    public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	private List<ForumComment> comments = new ArrayList<ForumComment>();
    
	public List<ForumComment> getComments() {
		return comments;
	}
	public void setComments(List<ForumComment> comments) {
		this.comments = comments;
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
	
	public Forum() {
		super();
	}
	
    
    
}
