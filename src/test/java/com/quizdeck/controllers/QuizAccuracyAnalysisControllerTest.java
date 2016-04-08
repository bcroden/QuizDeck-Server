package com.quizdeck.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test the quiz accuracy analysis controller
 *
 * @author Alex
 */
public class QuizAccuracyAnalysisControllerTest extends AbstractQuizAnalysisControllerTest {

    @Test
    public void testQuizAccuracyResults() throws Exception {
        String result = mockMvc.perform(get("/rest/secure/analysis/accuracy/" + this.completeQuiz.getQuizId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}