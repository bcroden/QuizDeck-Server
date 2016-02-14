package com.quizdeck.repositories;

import com.quizdeck.model.database.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(@Param("userName") String userName);
    List<User> findByFName(@Param("fName")String fName);
}
