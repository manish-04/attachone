package com.farzi.blogs;

import java.util.List;

public interface BlogDao {

	public void create(Blog blog);
	 
    public void update(Blog blog);
 
    public void delete(Blog blog);
 
    public void deleteAll();
 
    public Blog find(Blog blog);
 
    public List<Blog> findAll();
	
}
