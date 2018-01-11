package com.farzi;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.farzi.jobs.Job;
import com.farzi.jobs.JobDao;
import com.farzi.users.User;
import com.farzi.users.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {


	public static void main(String[] args) throws JsonProcessingException {
	
		ObjectMapper mapper = new ObjectMapper();
		
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
	    
		UserDao jd = (UserDao) context.getBean("userDao");
	    // Delete All cars
	    jd.deleteAll();

	    User user = new User();
	    user.setEnabled(true);
	    
	    jd.create(user);

	    System.out.println("Find All!!");

	    List<User> js = jd.findAll();
	    for (User j1: js) {
	    	
	        System.out.println(mapper.writeValueAsString(j1));
	    }
	    System.out.println();
	    
	    context.close();
		
	}
	
	
	
}
