package com.quizdeck.controllers;

/*
 *  Object created for quizzes submitted when completed
 */

import com.quizdeck.model.inputs.LabelUpdate;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.database.User;
import com.quizdeck.model.inputs.NewQuizInput;
import com.quizdeck.model.responses.QuizSubmissionResponse;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/quizdeck/")
public class QuizSubmissionController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="/quizsubmission", method= RequestMethod.POST)
    public QuizSubmissionResponse quizSubmissionResponse(@Valid @RequestBody NewQuizInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        quizRepository.save(new Quiz(input.getOwner(), input.getTitle(), input.getQuestions(), input.getLabels()));

        //add any new labels to the list associated with the user
        User user = userRepository.findByUserName(input.getOwner());
        for(String label: input.getLabels()){
            if(!user.getLabels().contains(label)){
                user.getLabels().add(label);
            }
        }
        userRepository.save(user);

        return new QuizSubmissionResponse();
    }

    @RequestMapping(value="/quizLabelUpdate", method = RequestMethod.POST)
    public QuizSubmissionResponse quizLabelsUpdate(@Valid @RequestBody LabelUpdate input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        Quiz quiz = quizRepository.findByTitleAndOwner(input.getQuizTitle(), input.getUserName());
        for(String label : input.getLabels()){
            if(quiz.getLabels() == null || quiz.getLabels().isEmpty()){
                quiz.getLabels().addAll(input.getLabels());
                break;
            }
            else if(!quiz.getLabels().contains(label)){
                quiz.getLabels().add(label);
            }
        }
        quizRepository.save(quiz);
        return new QuizSubmissionResponse();
    }
}
