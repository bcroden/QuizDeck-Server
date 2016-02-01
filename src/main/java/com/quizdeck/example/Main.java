package com.quizdeck.example;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.util.LinkedList;

/**
 * I am using this until we have JUnit setup.
 *
 * @author Alex
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Exercising Jython...");
        exerciseJython();
        testForXlsxwriter();
    }

    private static void exerciseJython() {
        //create factory
        AlgorithmFactory af = new AlgorithmFactory();

        //get Python algorithm
        Algorithm simpleAlgo = af.getSimpleAlgorithm();

        //generate data to be processed by algorithm
        LinkedList<String> strings = new LinkedList<>();
        strings.add("1st");
        strings.add("second");
        strings.add("3rd");

        //use the Python algorithm
        simpleAlgo.processList(strings);
        simpleAlgo.processCustomData(new Data());
    }

    private static void testForXlsxwriter() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from Algorithm import can_find_xlsxwriter");
        PyObject function = interpreter.get("can_find_xlsxwriter");
        PyObject result = function.__call__();
        Boolean answer = (Boolean) result.__tojava__(Boolean.class);
        if(answer)
            System.out.println("xlsxwriter is installed correctly");
        else
            System.out.println("xlsxwriter could not be found");
    }
}