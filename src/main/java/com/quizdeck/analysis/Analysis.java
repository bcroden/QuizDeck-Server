package com.quizdeck.analysis;

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

    /**
     * Indicates if the internal data set has already been analyzed.
     * @return True if the internal data set has been analyzed, false otherwise.
     */
    public boolean hasPerformedAnalysis();
}
