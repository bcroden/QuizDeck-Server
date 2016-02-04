package com.quizdeck.analysis.exceptions;

/**
 * Thrown when the results of an analysis/algorithm are currently unavailable
 *
 * @author Alex
 */
public class AnalysisResultsUnavailableException extends AnalysisException {
    public AnalysisResultsUnavailableException(String message) {
        super(message);
    }
}
