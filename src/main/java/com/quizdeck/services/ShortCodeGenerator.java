package com.quizdeck.services;

import java.util.Random;

/**
 * Created by Cade on 5/2/2016.
 */
public class ShortCodeGenerator {

    private Random gen;

    public String generate(){
        gen = new Random(System.currentTimeMillis());
        int code;
        StringBuilder id = new StringBuilder();
        for(int i = 0; i < 8; i++){
            code = gen.nextInt(90-65)+65;
            id.append((char)code);
        }
        return id.toString();
    }

}
