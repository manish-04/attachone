package com.farzi.forums;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("ForumDao")
@Qualifier("ForumDao")
@Component
public class ForumDaoImpl implements ForumDao {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public ForumDaoImpl() {
		super();
	
		System.out.println("HI");
	}

	final String COLLECTION = "forums";
	
	public void create(Forum Forum) {
		// TODO Auto-generated method stub
		 mongoTemplate.insert(Forum);
	}

	public void update(Forum Forum) {
		// TODO Auto-generated method stub
		mongoTemplate.save(Forum);
	}

	public void delete(Forum Forum) {
		// TODO Auto-generated method stub
		mongoTemplate.remove(Forum);
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		 mongoTemplate.remove(new Query(), COLLECTION);
	}

	public Forum find(Forum Forum) {
		Query query = new Query(Criteria.where("_id").is(Forum.getId()));
        return mongoTemplate.findOne(query, Forum.class, COLLECTION);
	}

	public List<Forum> findAll() {
		// TODO Auto-generated method stub
		return (List <Forum> ) mongoTemplate.findAll(Forum.class);
	}

	
	
}
