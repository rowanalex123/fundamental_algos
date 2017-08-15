package com.example.project;

import java.util.Arrays;

public class Scratch {
    public static void main(String[] args) {
        String[] value = {"4", "3"};
        System.out.println("Ok " + Arrays.stream(value).mapToLong(o -> Long.valueOf(o)).sum());
    }
}
