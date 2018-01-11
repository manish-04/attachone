package com.farzi.forums;

import java.util.List;

public interface ForumDao {

	public void create(Forum Forum);
	 
    public void update(Forum Forum);
 
    public void delete(Forum Forum);
 
    public void deleteAll();
 
    public Forum find(Forum Forum);
 
    public List<Forum> findAll();
	
}
