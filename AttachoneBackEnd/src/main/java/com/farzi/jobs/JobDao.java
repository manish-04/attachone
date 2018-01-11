package com.farzi.jobs;

import java.util.List;

public interface JobDao {

	public void create(Job job);
	 
    public void update(Job job);
 
    public void delete(Job job);
 
    public void deleteAll();
 
    public Job find(Job job);
 
    public List<Job> findAll();
	
}
