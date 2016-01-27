package com.example;

import java.util.HashMap;

/**
 * Represents some sort of custom data set to prove that Jython can handle it.
 *
 * @author Alex
 */
public class Data {
    /**
     * Returns a HashMap seeded with predetermined values.
     * @return A HashMap with predetermined values.
     */
    public HashMap getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("first", "1st");
        map.put("second", "2nd");
        map.put("third", "3rd");
        return map;
    }
}
