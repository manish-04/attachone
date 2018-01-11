package com.farzi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.farzi.blogs.Blog;
import com.farzi.blogs.BlogDao;
import com.farzi.blogs.BlogPost;
import com.farzi.forums.Forum;
import com.farzi.forums.ForumComment;
import com.farzi.forums.ForumDao;
import com.farzi.jobs.Job;
import com.farzi.jobs.JobDao;
import com.farzi.users.User;
import com.farzi.users.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TheRESTController {

	@Autowired
	UserDao udao;
	
	@Autowired
	JobDao jdao;
	
	@Autowired
	BlogDao bdao;
	
	@Autowired
	ForumDao fdao;
	
	 @Autowired
	 JavaMailSender mailSender;
	 
	 
	
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public ResponseEntity<String> addUser( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			
			u.setUsername(joObject.get("Username").toString());
			u.setEmail(joObject.get("Email").toString());
			u.setPassword(joObject.get("Password").toString());
			u.setPhone(joObject.get("Phone").toString());
			u.setGender(joObject.get("Gender").toString());
			
			u.setProfilePicUrl( u.getGender().equals("Male") ? "images/male.png" : "images/female.png" );
			
			System.out.println("Inserted");
			
			udao.create(u);
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/addFriend",method=RequestMethod.POST)
	public ResponseEntity<String> addFriend( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			u.setEmail(joObject.get("SourceEmail").toString());
			u = udao.find(u);
			List<String> ro = u.getRequestsOut();
			ro.add(joObject.get("TargetEmail").toString());
			u.setRequestsOut(ro);
			udao.update(u);
			
			u = new User();
			u.setEmail(joObject.get("TargetEmail").toString());
			u = udao.find(u);
			List<String> r = u.getRequests();
			r.add(joObject.get("SourceEmail").toString());
			u.setRequests(r);
			udao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/undoFriend",method=RequestMethod.POST)
	public ResponseEntity<String> undoFriend( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			u.setEmail(joObject.get("SourceEmail").toString());
			u = udao.find(u);
			List<String> ro = u.getRequestsOut();
			ro.remove(joObject.get("TargetEmail").toString());
			u.setRequestsOut(ro);
			udao.update(u);
			
			u = new User();
			u.setEmail(joObject.get("TargetEmail").toString());
			u = udao.find(u);
			List<String> r = u.getRequests();
			r.remove(joObject.get("SourceEmail").toString());
			u.setRequests(r);
			udao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptFriend",method=RequestMethod.POST)
	public ResponseEntity<String> acceptFriend( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			u.setEmail(joObject.get("SourceEmail").toString());
			u = udao.find(u);
			List<String> ro = u.getRequestsOut();
			ro.remove(joObject.get("TargetEmail").toString());
			u.setRequestsOut(ro);
			
			List<String> r = u.getRequests();
			r.remove(joObject.get("TargetEmail").toString());
			u.setRequests(r);
			
			List<String> f = u.getFriends();
			f.add( joObject.get("TargetEmail").toString() );
			udao.update(u);
			
			u = new User();
			u.setEmail(joObject.get("TargetEmail").toString());
			u = udao.find(u);
			
			ro = u.getRequestsOut();
			ro.remove(joObject.get("SourceEmail").toString());
			u.setRequestsOut(ro);
			
			r = u.getRequests();
			r.remove(joObject.get("SourceEmail").toString());
			u.setRequests(r);
			
			f = u.getFriends();
			f.add( joObject.get("SourceEmail").toString() );
			udao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/approveForum",method=RequestMethod.POST)
	public ResponseEntity<String> approveForum( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Forum u = new Forum();
			u.setId(joObject.get("id").toString());
			
			u = fdao.find(u);
			
			u.setApproved(true);
			
			fdao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectForum",method=RequestMethod.POST)
	public ResponseEntity<String> rejectForum( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Forum u = new Forum();
			u.setId(joObject.get("id").toString());
			
			u = fdao.find(u);
			
			u.setApproved(false);
			
			fdao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/approveBlog",method=RequestMethod.POST)
	public ResponseEntity<String> approveBlog( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Blog u = new Blog();
			u.setId(joObject.get("id").toString());
			
			u = bdao.find(u);
			
			u.setApproved(true);
			
			bdao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/applyJob",method=RequestMethod.POST)
	public ResponseEntity<String> applyJob( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Job u = new Job();
			u.setId(joObject.get("id").toString());
			
			u = jdao.find(u);
			
			List<String> a = u.getApplicants();
			
			a.add(joObject.get("applicant").toString());
			
			u.setApplicants(a);
			
			jdao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/withdrawJob",method=RequestMethod.POST)
	public ResponseEntity<String> withdrawJob( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Job u = new Job();
			u.setId(joObject.get("id").toString());
			
			u = jdao.find(u);
			
			List<String> a = u.getApplicants();
			
			a.remove(joObject.get("applicant").toString());
			
			u.setApplicants(a);
			
			jdao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/rejectBlog",method=RequestMethod.POST)
	public ResponseEntity<String> rejectBlog( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Blog u = new Blog();
			u.setId(joObject.get("id").toString());
			
			u = bdao.find(u);
			
			u.setApproved(false);
			
			bdao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/unFriend",method=RequestMethod.POST)
	public ResponseEntity<String> unFriend( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			u.setEmail(joObject.get("SourceEmail").toString());
			u = udao.find(u);
			
			List<String> f = u.getFriends();
			f.remove( joObject.get("TargetEmail").toString() );
			udao.update(u);
			
			u = new User();
			u.setEmail(joObject.get("TargetEmail").toString());
			u = udao.find(u);
			
			f = u.getFriends();
			f.remove( joObject.get("SourceEmail").toString() );
			udao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/sendMessage",method=RequestMethod.POST)
	public ResponseEntity<String> sendMessage( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			
			SimpleMailMessage sm = new SimpleMailMessage();
			
			sm.setTo( joObject.get("Email").toString() );
			sm.setFrom("TeamGraffitat@gmail.com");
			sm.setSubject("Thank you for contacting us");
			sm.setText("Thank you for contacting us");
			
			mailSender.send(sm);
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteJob",method=RequestMethod.POST)
	public ResponseEntity<String> deleteJob( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Job j = new Job();
			
			j.setId(joObject.get("id").toString());
			
			jdao.delete(j);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/tryLogin",method=RequestMethod.POST)
	public ResponseEntity<String> loginUser( @RequestBody String body  )
	{
		System.out.println(body);
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			
			u.setEmail(joObject.get("Email").toString());
			
			u = udao.find(u);
			
			if( u != null && u.getPassword().equals( joObject.get("Password").toString() ) )
			{
			
				ObjectMapper mapper = new ObjectMapper();
				
				try
				{
					return new ResponseEntity<String>(mapper.writeValueAsString(u), HttpStatus.OK);
				}
				catch( Exception e )
				{
					e.printStackTrace();
					return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
					
				}
				
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteForum",method=RequestMethod.POST)
	public ResponseEntity<String> deleteForum( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Forum j = new Forum();
			
			j.setId(joObject.get("id").toString());
			
			fdao.delete(j);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteBlog",method=RequestMethod.POST)
	public ResponseEntity<String> deleteBlog( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Blog j = new Blog();
			
			j.setId(joObject.get("id").toString());
			
			bdao.delete(j);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/editJob",method=RequestMethod.POST)
	public ResponseEntity<String> editJob( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Job j = new Job();
			
			j.setId(joObject.get("id").toString());
			
			j = jdao.find(j);
			
			j.setDate(new Date().toString());
			
			j.setTitle(joObject.get("Title").toString());
			j.setDescription(joObject.get("Description").toString());
			
			
			jdao.update(j);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/editBlog",method=RequestMethod.POST)
	public ResponseEntity<String> editBlog( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Blog j = new Blog();
			
			j.setId(joObject.get("id").toString());
			
			System.out.println( joObject.get("id").toString() );
			
			j = bdao.find(j);
			
			j.setDate(new Date().toString());
			
			j.setTitle(joObject.get("Title").toString());
			j.setDescription(joObject.get("Description").toString());
			
			
			bdao.update(j);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/editForum",method=RequestMethod.POST)
	public ResponseEntity<String> editForum( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Forum j = new Forum();
			
			j.setId(joObject.get("id").toString());
			
			System.out.println( joObject.get("id").toString() );
			
			j = fdao.find(j);
			
			j.setDate(new Date().toString());
			
			j.setTitle(joObject.get("Title").toString());
			j.setDescription(joObject.get("Description").toString());
			
			
			fdao.update(j);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/fetchAllJobs",method=RequestMethod.GET)
	public ResponseEntity<String> fetchAllJobs()
	{
		System.out.println("fetchAllJobs");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			System.out.println( mapper.writeValueAsString(udao.findAll()) );	
		
			return new ResponseEntity<String>(mapper.writeValueAsString(jdao.findAll()), HttpStatus.OK);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
			
		}
		
		
	}
	
	@RequestMapping(value="/fetchAllForums",method=RequestMethod.GET)
	public ResponseEntity<String> fetchAllForums()
	{
		System.out.println("fetchAllForums");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			System.out.println( mapper.writeValueAsString(fdao.findAll()) );	
		
			return new ResponseEntity<String>(mapper.writeValueAsString(fdao.findAll()), HttpStatus.OK);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
			
		}
		
		
	}
	
	@RequestMapping(value="/fetchAllBlogs",method=RequestMethod.GET)
	public ResponseEntity<String> fetchAllBlogs()
	{
		System.out.println("fetchAllBlogs");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			System.out.println( mapper.writeValueAsString(bdao.findAll()) );	
		
			return new ResponseEntity<String>(mapper.writeValueAsString(bdao.findAll()), HttpStatus.OK);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
			
		}
		
		
	}
	
	@RequestMapping(value="/addJob",method=RequestMethod.POST)
	public ResponseEntity<String> addJob( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Job j = new Job();
			
			j.setOwnerId(joObject.get("Email").toString());
			j.setTitle(joObject.get("Title").toString());
			j.setDescription(joObject.get("Description").toString());
			j.setDate( new Date().toString() );
			
			jdao.create(j);
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/addBlog",method=RequestMethod.POST)
	public ResponseEntity<String> addBlog( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Blog b = new Blog();
			
			b.setOwnerId(joObject.get("Email").toString());
			b.setTitle(joObject.get("Title").toString());
			b.setDescription(joObject.get("Description").toString());
			b.setDate( new Date().toString() );
			
			bdao.create(b);
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/postBlog",method=RequestMethod.POST)
	public ResponseEntity<String> postBlog( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Blog b = new Blog();
			
			b.setId(joObject.get("id").toString());
			
			b = bdao.find(b);
			
			List<BlogPost> l = b.getPosts();
			
			BlogPost bp = new BlogPost();
			
			bp.setDescription(joObject.get("Description").toString());
			bp.setOwnerId(joObject.get("ownerId").toString());
			
			bp.setDate(new Date().toString());
			
			l.add(bp);
			
			b.setPosts(l);
			
			bdao.update(b);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/addForumComment",method=RequestMethod.POST)
	public ResponseEntity<String> addForumComment( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Forum b = new Forum();
			
			b.setId(joObject.get("id").toString());
			
			b = fdao.find(b);
			
			List<ForumComment> l = b.getComments();
			
			ForumComment bp = new ForumComment();
			
			bp.setDescription(joObject.get("post").toString());
			bp.setOwnerId(joObject.get("ownerId").toString());
			
			bp.setDate(new Date().toString());
			
			l.add(bp);
			
			b.setComments(l);
			
			fdao.update(b);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteBlogPost",method=RequestMethod.POST)
	public ResponseEntity<String> deleteBlogPost( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Blog b = new Blog();
			
			b.setId(joObject.get("id").toString());
			
			b = bdao.find(b);
			
			List<BlogPost> l = b.getPosts();
			
			List<BlogPost> l1 = new ArrayList<BlogPost>();
			
			for( BlogPost bp : l )
			{
				if( !bp.getDate().equals(joObject.get("date").toString()) )
					l1.add(bp);
			}
			
			
			b.setPosts(l1);
			
			bdao.update(b);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteForumComment",method=RequestMethod.POST)
	public ResponseEntity<String> deleteForumComment( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Forum b = new Forum();
			
			b.setId(joObject.get("id").toString());
			
			b = fdao.find(b);
			
			List<ForumComment> l = b.getComments();
			
			List<ForumComment> l1 = new ArrayList<ForumComment>();
			
			for( ForumComment bp : l )
			{
				if( !bp.getDate().equals(joObject.get("date").toString()) )
					l1.add(bp);
			}
			
			
			b.setComments(l1);
			
			fdao.update(b);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/addForum",method=RequestMethod.POST)
	public ResponseEntity<String> addForum( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			Forum b = new Forum();
			
			b.setOwnerId(joObject.get("Email").toString());
			b.setTitle(joObject.get("Title").toString());
			b.setDescription(joObject.get("Description").toString());
			b.setDate( new Date().toString() );
			
			fdao.create(b);
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public ResponseEntity<String> updateUser( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			
			u.setEmail(joObject.get("Email").toString());
			
			u = udao.find(u);
			
			u.setId(joObject.get("_id").toString());
			u.setUsername(joObject.get("Username").toString());
			u.setEmail(joObject.get("Email").toString());
			u.setPhone(joObject.get("Phone").toString());
			u.setGender(joObject.get("Gender").toString());
			
			
			udao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateUserPassword",method=RequestMethod.POST)
	public ResponseEntity<String> updateUserPassword( @RequestBody String body  )
	{
		System.out.println("Hi");
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User u = new User();
			
			u.setEmail(joObject.get("Email").toString());
			
			u = udao.find(u);
			
			u.setPassword(joObject.get("Password").toString());
			
			
			udao.update(u);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@Autowired
	ServletContext context;
	
	@RequestMapping(value = "/updateProfilePicture/" , method = RequestMethod.POST)
	public ResponseEntity<String> updateProfilePicture(MultipartHttpServletRequest request,
			HttpServletResponse response, UriComponentsBuilder ucBuilder) {

		System.out.println(request.getHeader("user"));

		System.out.println(request.getFile("file").getName());
		System.out.println(request.getFile("file").getSize());
		System.out.println(request.getFile("file").getContentType());
		System.out.println(request.getFile("file").getOriginalFilename());

		JSONObject json = new JSONObject();

		BufferedOutputStream stream = null;

		try {
			
			String path = context.getRealPath("/");
			
			System.out.println(path);

			File directory = null;

			System.out.println(request.getFile("file"));

			if (request.getFile("file").getContentType().contains("image")) {
				directory = new File(path);

				System.out.println(directory);

				byte[] bytes = null;
				File file = null;
				bytes = request.getFile("file").getBytes();

				if (!directory.exists())
					directory.mkdirs();

				file = new File(directory.getAbsolutePath() + System.getProperty("file.separator")
						+ "temp.jpg");

				System.out.println(file.getAbsolutePath());

				stream = new BufferedOutputStream(new FileOutputStream(file));
				stream.write(bytes);
				
				stream.close();
				
				Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
						  "cloud_name", "dikd9evvg",
						  "api_key", "218654849534313",
						  "api_secret", "rSv6dMmSaadkKm9Ui4U597AgsGw"));
				
				File toUpload = new File(directory.getAbsolutePath() + System.getProperty("file.separator")
				+ "temp.jpg");
				Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
				
				User u = new User();
				
				u.setEmail(request.getHeader("user"));
				
				u = udao.find(u);
				
				u.setProfilePicUrl(uploadResult.get("secure_url").toString());
				
				udao.update(u);
				
				return new ResponseEntity<String>("{\"msg\": \"Success\",\"imageUrl\": \""+uploadResult.get("secure_url").toString()+"\"}", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}

		return new ResponseEntity<String>("{\"msg\": \"Success\"}", HttpStatus.OK);
	}
	
	@RequestMapping(value="/fetchUserData",method=RequestMethod.POST)
	public ResponseEntity<String> fetchUserData( @RequestBody String body  )
	{
		System.out.println("fetchUserData");
	
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JSONParser jp = new JSONParser();
			
			JSONObject joObject = (JSONObject)jp.parse(body);
		
			System.out.println(joObject);
		
			User user = new User();
			
			user.setEmail(joObject.get("Email").toString());
			
			System.out.println(udao.find(user));
			
			return new ResponseEntity<String>(mapper.writeValueAsString(udao.find(user)), HttpStatus.OK);
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>("{\"msg\": \"Failure\"}", HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/fetchAllUsers",method=RequestMethod.GET)
	public ResponseEntity<String> fetchAllUsers()
	{
		System.out.println("fetchAllUsers");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			System.out.println( mapper.writeValueAsString(udao.findAll()) );	
		
			return new ResponseEntity<String>(mapper.writeValueAsString(udao.findAll()), HttpStatus.OK);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<String>(new JSONObject().put("msg", "ERROR").toString(), HttpStatus.OK);
			
		}
		
		
	}
	
}
