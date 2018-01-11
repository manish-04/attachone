package com.farzi.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("jobDao")
@Qualifier("jobDao")
@Component
public class JobDaoImpl implements JobDao {

	@Autowired
    MongoTemplate mongoTemplate;
	
	
	
	public JobDaoImpl() {
		super();
	
		System.out.println("HI");
	}

	final String COLLECTION = "jobs";
	
	public void create(Job job) {
		// TODO Auto-generated method stub
		 mongoTemplate.insert(job);
	}

	public void update(Job job) {
		// TODO Auto-generated method stub
		mongoTemplate.save(job);
	}

	public void delete(Job job) {
		// TODO Auto-generated method stub
		mongoTemplate.remove(job);
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		 mongoTemplate.remove(new Query(), COLLECTION);
	}

	public Job find(Job job) {
		Query query = new Query(Criteria.where("_id").is(job.getId()));
        return mongoTemplate.findOne(query, Job.class, COLLECTION);
	}

	public List<Job> findAll() {
		// TODO Auto-generated method stub
		return (List <Job> ) mongoTemplate.findAll(Job.class);
	}

	
	
}
