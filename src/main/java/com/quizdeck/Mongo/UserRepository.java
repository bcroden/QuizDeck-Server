package com.quizdeck.Mongo;

import com.quizdeck.QuizObjects.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUserName(@Param("userName") String userName);

    List<User> findByFName(@Param("fName")String fName);

}
