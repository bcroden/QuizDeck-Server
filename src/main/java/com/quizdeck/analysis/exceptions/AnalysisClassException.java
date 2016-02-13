package com.quizdeck.analysis.exceptions;

/**
 * Thrown when an algorithm or analysis class could not be found.
 *
 * @author Alex
 */
public class AnalysisClassException extends AnalysisException {
    public AnalysisClassException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
