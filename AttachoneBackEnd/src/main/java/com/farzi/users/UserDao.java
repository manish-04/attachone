package com.farzi.users;

import java.util.List;

public interface UserDao {

	public void create(User user);
	 
    public void update(User user);
 
    public void delete(User user);
 
    public void deleteAll();
 
    public User find(User user);
 
    public List<User> findAll();
	
}
