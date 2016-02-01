package com.quizdeck.example;

import java.util.List;

/**
 * Interface which a Python class can implement to be used by a Java object.
 *
 * @author Alex
 */
public interface Algorithm {
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
    public boolean processCustomData(Data data);
}
