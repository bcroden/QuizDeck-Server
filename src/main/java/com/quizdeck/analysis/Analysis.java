package com.quizdeck.analysis;

import com.quizdeck.analysis.outputs.AnalysisResult;

/**
 * Parent interface of all analysis routines.
 *
 * @author Alex
 */
interface Analysis {
    /**
     * Internally performs the analysis on the data set and returns true when complete.
     * @return True if analysis was successful, false otherwise.
     */
    public boolean performAnalysis();

    public AnalysisResult getResults();
}
