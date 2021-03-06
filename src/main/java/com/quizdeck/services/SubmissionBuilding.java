package com.quizdeck.services;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.Answers;
import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.database.submission;
import com.quizdeck.model.inputs.SubmissionInput;
import com.quizdeck.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cade on 4/30/2016.
 */
@Service
public class SubmissionBuilding {

    @Autowired
    RedisSubmissions redisSubmissions;

    @Autowired
    RedisQuestion redisQuestion;

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @Autowired
    QuizRepository quizRepository;

    public ResponseEntity<String> buildSubmission(SubmissionInput input) throws InactiveQuizException {

        int currQuestionNum = redisQuestion.getEntry(input.getQuizID());
        input.setQuestionNum(currQuestionNum);
        //add newest submission for a specific active quiz only
        ActiveQuiz aQuiz = redisActiveQuiz.getEntry(input.getQuizID());
        if(aQuiz != null && aQuiz.isActive()) {
            List<submission> submissions = new ArrayList<>();
            for(int i = 0; i < redisSubmissions.getSize(input.getQuizID()); i++){
                submissions.add(((submission)(redisSubmissions.getAllSubmissions(input.getQuizID())).get(i)));
            }
            int counter = 0;
            for(Submissions sub : submissions) {
                if(sub.getUserName().equals(input.getUserName())){

                    if(sub.getQuestionNum() == input.getQuestionNum()){
                        long index = submissions.indexOf(sub);
                        submission editEntry = (submission) redisSubmissions.getSubmission(input.getQuizID(), index);
                        //remove sub from redis
                        redisSubmissions.removeIndex(input.getQuizID(), index, editEntry);
                        //add guess to entry
                        List<Guess> guesses = editEntry.getGuesses();

                        Guess newGuess = new Guess();
                        newGuess.setTimeStamp(System.currentTimeMillis());
                        newGuess.setQuestionNum(input.getQuestionNum());
                        newGuess.setSelection(new Answers(input.getChosenAnswerContent()==null ? "" : input.getChosenAnswerContent(), input.getChosenAnswer()));
                        guesses.add(newGuess);
                        editEntry.setGuesses(guesses);

                        redisSubmissions.addSubmissionLink(input.getQuizID(), editEntry);
                        break;
                    }
                }
                counter++;
            }
            if(counter == redisSubmissions.getSize(input.getQuizID())){
                submission newEntry = new submission();
                newEntry.setUserName(input.getUserName());
                Guess newGuess = new Guess();
                newGuess.setTimeStamp(System.currentTimeMillis());
                newGuess.setQuestionNum(input.getQuestionNum());
                newGuess.setSelection(new Answers(input.getChosenAnswerContent(), input.getChosenAnswer()));
                List<Guess> guesses = new ArrayList<>();
                guesses.add(newGuess);
                newEntry.setGuesses(guesses);
                if(quizRepository.findById(input.getQuizID())==null){
                    throw new InactiveQuizException();
                }
                newEntry.setQuestionNum(redisQuestion.getEntry(input.getQuizID()));
                newEntry.setQuestion(quizRepository.findById(input.getQuizID()).getQuestions().get(newEntry.getQuestionNum()));

                redisSubmissions.addSubmissionLink(input.getQuizID(), newEntry);
            }

            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else
            throw new InactiveQuizException();
    }

}
