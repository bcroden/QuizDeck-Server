package com.quizdeck.analysis.exceptions;

/**
 * Thrown when an analysis or algorithm could not be constructed.
 *
 * @author Alex
 */
public class AnalysisConstructionException extends AnalysisException {
    public AnalysisConstructionException(String message)
    {
        super(message);
    }
    public AnalysisConstructionException(Throwable throwable)
    {
        super(throwable);
    }
    public AnalysisConstructionException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

}
