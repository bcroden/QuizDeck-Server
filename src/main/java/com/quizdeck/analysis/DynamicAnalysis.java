package com.quizdeck.analysis;

/**
 * An analysis algorithm which can add new data to the data initially given to it.
 * After the new data is added the analysis can be preformed again to reflect the addition.
 *
 * @param <T>   The type of data objects which may be added to the analysis
 * @author Alex
 */
public interface DynamicAnalysis<T> {
    public boolean isDirty();
    public void addData(T data);
}
