package com.quizdeck.analysis;

import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.outputs.AnalysisResult;

/**
 * Parent interface of all analysis routines.
 *
 * @param <T>    The type of results that this analysis routine will return
 * @author Alex
 */
public interface Analysis<T extends AnalysisResult> {
    /**
     * Internally performs the analysis on the data set and returns true when complete.
     * @return True if analysis was successful, false otherwise.
     */
    public boolean performAnalysis();

    public T getResults() throws AnalysisResultsUnavailableException;
}
