package com.farzi.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
    MongoTemplate mongoTemplate;
	
	
	final String COLLECTION = "users";
	
	public void create(User User) {
		// TODO Auto-generated method stub
		 mongoTemplate.insert(User);
	}

	public void update(User User) {
		// TODO Auto-generated method stub
		mongoTemplate.save(User);
	}

	public void delete(User User) {
		// TODO Auto-generated method stub
		mongoTemplate.remove(User);
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		 mongoTemplate.remove(new Query(), COLLECTION);
	}

	public User find(User User) {
		Query query = new Query(Criteria.where("email").is(User.getEmail()));
        return mongoTemplate.findOne(query, User.class, COLLECTION);
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return (List <User> ) mongoTemplate.findAll(User.class);
	}

	
	
}
