package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Algorithm for accessing the indecisiveness of quiz participants.
 * The following outputs are returned through the AnalysisResults interface:
 * Participant Sample Data:
 * -> All guesses to all questions by all quiz participants
 * Participant Statistical Data:
 * -> Number of guesses a given participant submitted to each question
 * -> Number of guesses a given participant submitted in total
 * -> Overall 'Indecisiveness' score of each participant
 * Quiz Statistical Data:
 * -> Overall 'Indecisiveness' score for each question in the quiz
 * -> Overall 'Indecisiveness' score for the quiz
 *
 * Notes:
 * -> If an 'Indecisiveness' score is   < 0 then too few guesses were submitted (Participants did not know what to respond?)
 *                                      = 0 exactly enough guesses were submitted (Participants were confident or did not have time to think)
 *                                      > 0 too many guesses were submitted (Participants did not know what to respond?)
 * -> Indecisiveness at the quiz level may be misleading as one participant may have submitted enough guesses to cover
 *      up the fact that another participant submitted too few guesses. Therefore, it is possible for participants to
 *      be indecisive and the quiz level stats not show this.
 *          I am unsure if there is anything that I can do to resolve this.
 *
 * @author Alex
 */
public class QuizIndecisivenessAlgorithm extends AbstractQuizAlgorithm implements StaticAnalysis {
    protected QuizIndecisivenessAlgorithm(List<Response> responses, List<Question> questions, String quizID, String deckID, String ownerID) {
        super(responses, questions, quizID, deckID, ownerID);

        hasPerformedAnalysis = false;
    }

    @Override
    public boolean performAnalysis() {
        /* Fill in all of the sample data */

        //collect sample data for each user
        getQuizAnalysisData().getData().keySet().stream().forEach(username -> {

            //get all of the responses submitted by this user
            List<Response> allResponses = getResponses().stream()
                                                        .filter(response -> response.getUserName().equals(username))
                                                        .collect(Collectors.toList());

            getQuestions().stream().forEach(question -> {
                //get all of the guesses that the user submitted to this question
                List<Guess> guesses = allResponses.stream().filter(response -> response.getQuestion().equals(question))
                                                            .map(response -> response.getGuesses())
                                                            .reduce((acc, itr) -> { acc.addAll(itr); return acc; })
                                                            .get();
                //TODO: Test for the sample data
                //save all of the guesses
                getQuizAnalysisData().getData().get(username).addGuesses(question, guesses);
            });
        });

        /* Calculate all of the statistics for each participant */

        getQuizAnalysisData().getData().values().stream().forEach(qpad -> {
            //find the total number of guesses the participant submitted
            int totNumGuesses = getQuestions().stream().mapToInt(question -> {
                                    int numGuesses = qpad.getData().get(question.getQuestionNum()).size();
                                    //TODO: Test this stat
                                    //TODO: Should this be the number of guesses or indecisiveness?
                                    //save the number of guesses the user submitted to this question
                                    qpad.putStat(NUMBER_OF_GUESSES_PER_QUESTION_TAG + question.getQuestionNum(), Integer.toString(numGuesses));
                                    return numGuesses;
                                })
                                .sum();

            int indecisiveScore = totNumGuesses - getQuestions().size();
            qpad.putStat(TOTAL_NUMBER_OF_GUESSES_TAG, Integer.toString(totNumGuesses)); //TODO: Test this stat
            qpad.putStat(INDECISIVENESS_SCORE_TAG, Integer.toString(indecisiveScore));  //TODO: Test this stat
        });

        /* Calculate quiz level statistics */

        //Find the total number of guesses to each question
        getQuestions().stream().forEach(question -> {
            int numGuessesForQuestion = getQuizAnalysisData().getData().values().stream().mapToInt(qpad -> {
                                            return qpad.getData().get(question.getQuestionNum()).size();
                                        }).sum();

            //TODO: Test this stat
            //No question level indecisiveness will result in the number
            //of guesses being equal to the number of participants
            int numParticipants = getQuizAnalysisData().getData().keySet().size();
            getQuizAnalysisData().putStat(QUESTION_INDECISIVENESS_SCORE_TAG + question.getQuestionNum(),
                                            Integer.toString(numGuessesForQuestion-numParticipants));
        });

        //TODO: Test this stat
        //No quiz level indecisiveness will result in the number responses
        //being equal to the number of participants multiplied by the number of questions
        int totNumGuesses = getResponses().size();
        int targetNumGuesses = getQuizAnalysisData().getData().keySet().size() * getQuestions().size();
        getQuizAnalysisData().putStat(INDECISIVENESS_SCORE_TAG, Integer.toString(totNumGuesses - targetNumGuesses));

        hasPerformedAnalysis = true;

        return true;
    }

    @Override
    public boolean areResultsAvailable() {
        return hasPerformedAnalysis;
    }

    private boolean hasPerformedAnalysis;
    
    private final String NUMBER_OF_GUESSES_PER_QUESTION_TAG = "Num Guesses for Q";
    private final String TOTAL_NUMBER_OF_GUESSES_TAG = "Total Num Guesses";
    private final String INDECISIVENESS_SCORE_TAG = "Indecisiveness Score";
    private final String QUESTION_INDECISIVENESS_SCORE_TAG = "Indecisiveness Score for Q";
}
