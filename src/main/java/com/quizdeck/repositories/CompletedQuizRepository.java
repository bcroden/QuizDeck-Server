package com.quizdeck.repositories;

import com.quizdeck.model.database.CompleteQuiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Cade on 2/17/2016.
 */
public interface CompletedQuizRepository extends MongoRepository<CompleteQuiz, String> {

    public CompleteQuiz findById(@Param("id") String id);

    public long removeById(@Param("id") String id);

    public CompleteQuiz findByTitleAndOwner(@Param("title") String title, @Param("owner") String owner);

}
