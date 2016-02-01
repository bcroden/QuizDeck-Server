package com.quizdeck.analysis.exceptions;

/**
 * Parent of all exceptions from the analysis package.
 *
 * @author Alex
 */
public class AnalysisException extends Exception {
    public AnalysisException(String message)
    {
        super(message);
    }
    public AnalysisException(Throwable throwable)
    {
        super(throwable);
    }
    public AnalysisException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
