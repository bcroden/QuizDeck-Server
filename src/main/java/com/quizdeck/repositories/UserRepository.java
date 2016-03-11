package com.quizdeck.repositories;

import com.quizdeck.model.database.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(@Param("userName") String userName);
    Long removeByUserName(@Param("userName") String userName);

    /**
     * Created by Cade on 3/10/2016.
     */

    @Controller
    class SubmissionController {



    }
}
