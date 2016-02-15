package com.quizdeck.repositories;

import com.quizdeck.model.database.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(@Param("userName") String userName);
}
