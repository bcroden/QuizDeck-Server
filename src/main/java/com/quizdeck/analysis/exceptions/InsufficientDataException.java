package com.quizdeck.analysis.exceptions;

/**
 * Thrown when an insufficient amount of data has been supplied for an algorithm or analysis
 *
 * @author Alex
 */
public class InsufficientDataException extends AnalysisException {
    public InsufficientDataException(String message)
    {
        super(message);
    }
}
