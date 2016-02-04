package com.quizdeck.analysis.outputs;

import java.util.Map;

/**
 * Generic interface which allows defines access to the results of any analysis algorithm.
 *
 * @param <DK>  Type of object which is used to key the access of sample data
 * @param <DV>  Type of object which is used to store the value of the sample data
 * @param <S>   Type of object which is used to store the calculated statistics
 */
public interface AnalysisResult<DK, DV, S> {
    /**
     * Returns a reference to a mapping from a key describing piece of sample data
     * to the value of that piece of sample data.
     *
     * @return A mapping from DK to DV
     */
    public Map<DK, DV> getData();

    /**
     * Returns a reference to a mapping from a String key describing a calculated statistic
     * to the value of that piece of sample data.
     *
     * @return A mapping from String to S
     */
    public Map<String, S> getStats();
}
