package com.quizdeck.analysis;

import com.quizdeck.Application.QuizDeckApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * I am using this until we have JUnit setup.
 *
 * @author Alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
public class JythonConfigTest {
    @Test
    public void exerciseJython() {
        //create factory
        JythonTestConfigFactory af = new JythonTestConfigFactory();

        //get Python algorithm
        JythonTestAlgorithm simpleAlgo = af.getSimpleAlgorithm();

        //generate data to be processed by algorithm
        LinkedList<String> strings = new LinkedList<String>();
        strings.add("1st");
        strings.add("second");
        strings.add("3rd");

        //use the Python algorithm
        simpleAlgo.processList(strings);
        simpleAlgo.processCustomData(new JythonTestData());
    }

    @Test
    public void testForXlsxwriter() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from JythonTest import can_find_xlsxwriter");
        PyObject function = interpreter.get("can_find_xlsxwriter");
        PyObject result = function.__call__();
        Boolean answer = (Boolean) result.__tojava__(Boolean.class);
        assertTrue("xlsxwriter could not be import by Jython", answer);
        if(answer)
            System.out.println("xlsxwriter is installed correctly");
        else
            System.out.println("xlsxwriter could not be found");
    }

    /**
     * Interface which a Python class can implement to be used by a Java object.
     *
     * @author Alex
     */
    public static interface JythonTestAlgorithm {
        /**
         * Passes an object from Java's built in types to ensure that objects can be passed to Python classes.
         * @param list A list of objects to be processed.
         * @return Returns true if the list was processed successfully, false otherwise.
         */
        public boolean processList(List list);
        /**
         * Passes an custom object to ensure that complex objects can be passed to Python classes.
         * @param data A custom object
         * @return Returns true if the data was processed successfully, false otherwise.
         */
        public boolean processCustomData(JythonTestData data);
    }

    /**
     * Factory which manufactures Python class which can be used by Java objects.
     * @author Alex
     */
    public static class JythonTestConfigFactory {
        /**
         * Setup factory and initialize Python objects.
         */
        public JythonTestConfigFactory() {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec("from JythonTest import SimpleAlgorithm");
            simpleAlgorithm = interpreter.get("SimpleAlgorithm");
        }

        /**
         * Constructs and returns an instance of the Python class SimpleAlgorithm using the JythonTestAlgorithm interface.
         * @return An instance of SimpleAlgorithm
         */
        public JythonTestAlgorithm getSimpleAlgorithm() {
            PyObject algorithm = simpleAlgorithm.__call__();
            return (JythonTestAlgorithm) algorithm.__tojava__(JythonTestAlgorithm.class);
        }

        private PyObject simpleAlgorithm;
    }

    /**
     * Represents some sort of custom data set to prove that Jython can handle it.
     *
     * @author Alex
     */
    public static class JythonTestData {
        /**
         * Returns a HashMap seeded with predetermined values.
         * @return A HashMap with predetermined values.
         */
        public HashMap getData() {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("first", "1st");
            map.put("second", "2nd");
            map.put("third", "3rd");
            return map;
        }
    }
}