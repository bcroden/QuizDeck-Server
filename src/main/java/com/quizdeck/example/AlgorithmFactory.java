package com.quizdeck.example;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * Factory which manufactures Python class which can be used by Java objects.
 * @author Alex
 */
public class AlgorithmFactory {
    /**
     * Setup factory and initialize Python objects.
     */
    public AlgorithmFactory() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from Algorithm import SimpleAlgorithm");
        simpleAlgorithm = interpreter.get("SimpleAlgorithm");
    }

    /**
     * Constructs and returns an instance of the Python class SimpleAlgorithm using the Algorithm interface.
     * @return An instance of SimpleAlgorithm
     */
    public Algorithm getSimpleAlgorithm() {
        PyObject algorithm = simpleAlgorithm.__call__();
        return (Algorithm) algorithm.__tojava__(Algorithm.class);
    }

    private PyObject simpleAlgorithm;
}
