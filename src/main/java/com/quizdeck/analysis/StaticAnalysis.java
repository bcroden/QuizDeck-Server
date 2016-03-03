package com.quizdeck.analysis;

import com.quizdeck.analysis.outputs.AnalysisResult;

/**
 * Marker interface for an analysis algorithm which can only process the data set initially given to it.
 *
 * @param <T>    The type of results that this analysis routine will return
 * @author Alex
 */
public interface StaticAnalysis<T extends AnalysisResult> extends Analysis<T> {

}
