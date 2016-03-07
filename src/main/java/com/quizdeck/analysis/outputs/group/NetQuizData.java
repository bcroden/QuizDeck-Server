package com.quizdeck.analysis.outputs.group;

import lombok.Getter;
import lombok.Setter;

/**
 * Data object used for quiz accuracy in group analysis
 *
 * @author Alex
 */
@Getter
@Setter
public class NetQuizData {
    private int numberOfQuestions;
    private double netAccuracy;
}
