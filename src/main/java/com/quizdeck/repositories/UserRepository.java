package com.quizdeck.repositories;

import com.quizdeck.model.database.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(@Param("userName") String userName);
    Long removeByUserName(@Param("userName") String userName);
    ArrayList<User> findByUserNameLike(@Param("userName") String userName);
}
