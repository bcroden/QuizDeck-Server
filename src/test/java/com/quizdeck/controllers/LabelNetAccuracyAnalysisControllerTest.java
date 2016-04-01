package com.quizdeck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizdeck.analysis.outputs.group.GroupNetQuizAccuracyResults;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test the label net accuracy controller
 *
 * @author Alex
 */
public class LabelNetAccuracyAnalysisControllerTest extends AbstractLabelAnalysisControllerTest {
    @Test
    public void testForNetAccuracyAnalysisResult() throws Exception {
        String result = mockMvc.perform(post("/rest/secure/analysis/label/net-accuracy/").content(this.json(getInput()))
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                        ).andExpect(status().is2xxSuccessful())
                                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        GroupNetQuizAccuracyResults data = mapper.readValue(result, GroupNetQuizAccuracyResults.class);

        List<String> resultLabels = data.getLabels();

        assertThat("Incorrect number of labels", resultLabels.size(), is(getLabels().size()));

        getLabels().forEach(label -> assertTrue("Result label list is missing " + label, resultLabels.contains(label)));
    }
}
