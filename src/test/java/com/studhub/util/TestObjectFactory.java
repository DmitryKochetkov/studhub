package com.studhub.util;

import java.util.Random;

public class TestObjectFactory {
    public static TestObject generateInstance() {
        Random random = new Random();
        int number = random.nextInt();
        return new TestObject(number, Integer.toString(number));
    }
}