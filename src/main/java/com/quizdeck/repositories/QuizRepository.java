package com.quizdeck.repositories;

import com.quizdeck.model.database.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Cade on 2/17/2016.
 */
public interface QuizRepository extends MongoRepository<Quiz, String> {

    public Quiz findByTitleAndOwner(@Param("title") String title, @Param("owner") String owner);

    public long removeById(@Param("id") String id);

}
