package com.quizdeck.analysis.outputs;

import com.quizdeck.analysis.inputs.Selection;

import java.util.List;
import java.util.Map;

/**
 * Generic interface which allows defines access to the results of any analysis algorithm.
 *
 * @param <DK>  Type of object which is used to key the access of sample data
 * @param <DV>  Type of object which is used to store the value of the sample data
 * @param <S>   Type of object which is used to store the calculated statistics
 */
public interface AnalysisResult<DK, DV, S> {
    public Map<DK, DV> getData();
    public Map<String, S> getStats();
}
