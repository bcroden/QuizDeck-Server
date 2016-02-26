package com.quizdeck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test the analysis controller
 *
 * @author Alex
 */
public class QuizAccuracyAnalysisControllerTest extends AbstractQuizAnalysisControllerTest {

    @Test
    public void testQuizAccuracyResults() throws Exception {
        String result = mockMvc.perform(post("/rest/secure/analysis/accuracy/").content(this.json(accuracyInput))
                                                .contentType(MediaType.APPLICATION_JSON)
                                        ).andExpect(status().is2xxSuccessful())
                                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        QuizAnalysisData data = mapper.readValue(result, QuizAnalysisData.class);

        assertThat("Bad quiz owner ID", data.getOwnerID(), is(completeQuiz.getOwner()));
        assertThat("Bad quiz ID", data.getQuizID(), is(completeQuiz.getQuizId()));
    }
}