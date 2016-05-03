package com.quizdeck.controllers;

import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.CompleteQuiz;
import com.quizdeck.model.database.Questions;
import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.inputs.ActiveQuizInput;
import com.quizdeck.model.inputs.CompleteQuizInput;
import com.quizdeck.model.responses.QuestionResponse;
import com.quizdeck.repositories.CompletedQuizRepository;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.repositories.UserRepository;
import com.quizdeck.services.*;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */

@RestController
@RequestMapping("/rest/secure/quiz/")
public class ActiveQuizRequestController {

    @Autowired
    CompletedQuizRepository completeQuizRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisSubmissions redisSubmissions;

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @Autowired
    RedisQuestion redisQuestion;

    @Autowired
    RedisShortCodes redisShortCodes;

    private Logger log = LoggerFactory.getLogger(ActiveQuizRequestController.class);

    @RequestMapping(value="/submit", method = RequestMethod.POST)
    public ResponseEntity<String> submitQuiz(@Valid @RequestBody CompleteQuizInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        //will also close the quiz on redis, and add it to the database.
        List<? extends Submissions> subs = redisSubmissions.getAllSubmissionsAndRemove(input.getQuizId());
        //get active quiz information
        ActiveQuiz temp = redisActiveQuiz.getEntry(input.getQuizId());

        //--------------------------------------------------
        CompleteQuiz quiz = new CompleteQuiz(input.getQuiz(), temp.getStart(), temp.getStop(), input.getQuiz().getTitle(), input.getQuiz().getOwner(), subs);

        completeQuizRepository.save(quiz);

        //remove the active quiz entry from redis
        redisActiveQuiz.removeEntry(input.getQuizId());
        redisQuestion.removeEntry(input.getQuizId());
        redisShortCodes.removeEntry(temp.getShortId());

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/activate/", method= RequestMethod.PUT)
    public String activateQuiz(@Valid @RequestBody ActiveQuizInput input, @ModelAttribute("claims") Claims claims, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        //generate shortcode
        String shortCode = "";
        ShortCodeGenerator shortCodeGenerator = new ShortCodeGenerator();

        do{
            shortCode = shortCodeGenerator.generate();
        }while(redisShortCodes.takenShortCode(shortCode));

        ActiveQuiz activeQuiz = new ActiveQuiz();
        activeQuiz.setOwner(claims.get("user").toString());
        activeQuiz.setQuizId(input.getQuizId());
        activeQuiz.setShortId(shortCode);
        activeQuiz.setPubliclyAvailable(input.isPublicAvailable());
        activeQuiz.setStart(new Date());
        activeQuiz.setActive(true);
        redisShortCodes.addEntry(shortCode, input.getQuizId());
        redisActiveQuiz.addEntry(input.getQuizId(), activeQuiz);
        redisQuestion.addEntry(input.getQuizId(), 0);

        return "\"" + shortCode + "\"";
    }

    @RequestMapping(value="/deactivate/{quizId}", method= RequestMethod.PUT)
    public ResponseEntity<String> deactivateQuiz(@PathVariable String quizId) throws InactiveQuizException{

        //update redis entry
        ActiveQuiz temp = redisActiveQuiz.getEntry(quizId);
        if(temp != null) {
            temp.setStop(new Date());
            temp.setActive(false);
            redisActiveQuiz.updateEntry(quizId, temp);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        throw new InactiveQuizException();
    }

    @RequestMapping(value="/getActiveQuiz/{quizId}", method = RequestMethod.GET)
    public ActiveQuiz getActive(@PathVariable String quizId){
        ActiveQuiz returned = redisActiveQuiz.getEntry(quizId);
        if(returned==null){
            log.info("quiz is not active");
        }
        return returned;
    }

    @RequestMapping(value="/questionIncrement/{quizId}", method = RequestMethod.PUT)
    public ResponseEntity<String> questionIncrement(@PathVariable String quizId){
        int questionNum = 0;

        if(redisQuestion.getEntry(quizId)==null){
            redisQuestion.addEntry(quizId, 0);
        }
        else{
            questionNum = redisQuestion.getEntry(quizId);
            redisQuestion.updateEntry(quizId, ++questionNum);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/questionDecrement/{quizId}", method=RequestMethod.PUT)
    public ResponseEntity<String> questionDecrement(@PathVariable String quizId){
        int questionNum = 0;

        if(redisQuestion.getEntry(quizId)==null){
            redisQuestion.addEntry(quizId, 0);
        }
        else{
            if(redisQuestion.getEntry(quizId) > 0)
                questionNum = redisQuestion.getEntry(quizId);
                redisQuestion.updateEntry(quizId, --questionNum);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/getQuestionNum/{quizId}", method = RequestMethod.GET)
    public int getQuestion(@PathVariable String quizId){
        return redisQuestion.getEntry(quizId);
    }

    @RequestMapping(value="/numActiveQuiz/", method = RequestMethod.GET)
    public long getActiveQuizNum(){
        return redisActiveQuiz.getKeySize();
    }

    @RequestMapping(value="/pollingQuizzes", method = RequestMethod.GET)
    public List<ActiveQuiz> pollForQuizzes(@ModelAttribute("claims") Claims claims){
        return redisActiveQuiz.getAllActiveQuizzes(userRepository.findByUserName(claims.get("user").toString()));
    }

    @RequestMapping(value="/getCompleteQuizzes", method = RequestMethod.GET)
    public List<CompleteQuiz> getCompleteQuizzes(@ModelAttribute("claims") Claims claims){
        return completeQuizRepository.findByOwner(claims.get("user").toString());
    }

    @RequestMapping(value="/getQuestionInfo/{quizId}", method = RequestMethod.GET)
    public QuestionResponse getQuestionInfo(@PathVariable String quizId){
        int num = redisQuestion.getEntry(quizId);
        Questions question = quizRepository.findById(quizId).getQuestions().get(num);

        return new QuestionResponse(question.getQuestion(), question.getQuestionFormat(), question.getAnswers());
    }

    @RequestMapping(value="/getIsActive/{quizId}", method = RequestMethod.GET)
    public boolean getIsActive(@PathVariable String quizId){
        return redisActiveQuiz.getEntry(quizId)==null ? false : true;
    }

    @RequestMapping(value="/delete", method = RequestMethod.PUT)
    public void delete(@ModelAttribute("claims") Claims claims){
        if(claims.get("user").toString().equalsIgnoreCase("cade")){
            redisActiveQuiz.deleteAll();
        }
    }
}
