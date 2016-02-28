package com.quizdeck.repositories;

import com.quizdeck.model.database.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Cade on 2/17/2016.
 */
public interface QuizRepository extends MongoRepository<Quiz, String> {

    public List<Quiz> findByOwner(@Param("owner") String owner);

    public Quiz findByTitleAndOwner(@Param("title") String title, @Param("owner") String owner);

    public List<Quiz> findByOwnerAndLabelsIn(@Param("owner") String owner, @Param("labels") List<String> labels);

    public long removeById(@Param("id") String id);

}
