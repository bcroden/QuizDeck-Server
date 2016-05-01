package com.quizdeck.repositories;

import com.quizdeck.model.database.CompleteQuiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Cade on 2/17/2016.
 */
public interface CompletedQuizRepository extends MongoRepository<CompleteQuiz, String> {

    public CompleteQuiz findByQuizId(@Param("quizId") String quizId);

    public long removeById(@Param("id") String id);

    public List<CompleteQuiz> findByOwner(@Param("owner") String owner);

    public List<CompleteQuiz> findByTitleAndOwner(@Param("title") String title, @Param("owner") String owner);

    public List<CompleteQuiz> findByOwnerAndLabelsIn(@Param("owner") String owner, @Param("labels") List<String> labels);

}
