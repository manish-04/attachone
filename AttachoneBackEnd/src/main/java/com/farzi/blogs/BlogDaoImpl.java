package com.farzi.blogs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("BlogDao")
@Qualifier("BlogDao")
@Component
public class BlogDaoImpl implements BlogDao {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public BlogDaoImpl() {
		super();
	
		System.out.println("HI");
	}

	final String COLLECTION = "blogs";
	
	public void create(Blog Blog) {
		// TODO Auto-generated method stub
		 mongoTemplate.insert(Blog);
	}

	public void update(Blog Blog) {
		// TODO Auto-generated method stub
		mongoTemplate.save(Blog);
	}

	public void delete(Blog Blog) {
		// TODO Auto-generated method stub
		mongoTemplate.remove(Blog);
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		 mongoTemplate.remove(new Query(), COLLECTION);
	}

	public Blog find(Blog blog) {
		Query query = new Query(Criteria.where("_id").is(blog.getId()));
        return mongoTemplate.findOne(query, Blog.class, COLLECTION);
	}

	public List<Blog> findAll() {
		// TODO Auto-generated method stub
		return (List <Blog> ) mongoTemplate.findAll(Blog.class);
	}

	
	
}
