package com.yadav.journalApp.respository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yadav.journalApp.entity.User;

public interface UserRepository extends MongoRepository <User,ObjectId>{
	User findByUserName(String username);
}
