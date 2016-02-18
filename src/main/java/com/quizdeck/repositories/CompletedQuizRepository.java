package com.quizdeck.repositories;

import com.quizdeck.model.database.CompleteQuiz;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Cade on 2/17/2016.
 */
public interface CompletedQuizRepository {

    public CompleteQuiz findById(@Param("id") String id);

    public long removeById(@Param("id") String id);

    public List<CompleteQuiz> findByTitleAndOwner(@Param("title") String title, @Param("owner") String owner);

}
