package com.example;

import java.util.LinkedList;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Exercising Jython...");
        exerciseJython();
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
}