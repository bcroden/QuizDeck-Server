package com.quizdeck.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test the quiz indecisiveness controller
 *
 * @author Alex
 */
public class QuizIndecisivenessAnalysisControllerTest extends AbstractQuizAnalysisControllerTest {
    @Test
    public void testIndecisivenessResults() throws Exception {
        String result = mockMvc.perform(get("/rest/secure/analysis/indecisiveness/" + this.completeQuiz.getQuizId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
