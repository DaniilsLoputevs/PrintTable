package io.github.loputevs.printtable.formatter.impl;

import io.github.loputevs.printtable.formatter.TypeFormatter;

import java.util.Arrays;

public class ArrayFormatterImpl implements TypeFormatter<Object> {
    
    @Override public String apply(Object arrayExpected) {
        if (arrayExpected instanceof Object[]) return Arrays.toString((Object[]) arrayExpected);
        if (arrayExpected instanceof boolean[]) return Arrays.toString((boolean[]) arrayExpected);
        if (arrayExpected instanceof byte[]) return Arrays.toString((byte[]) arrayExpected);
        if (arrayExpected instanceof short[]) return Arrays.toString((short[]) arrayExpected);
        if (arrayExpected instanceof char[]) return Arrays.toString((char[]) arrayExpected);
        if (arrayExpected instanceof int[]) return Arrays.toString((int[]) arrayExpected);
        if (arrayExpected instanceof long[]) return Arrays.toString((long[]) arrayExpected);
        if (arrayExpected instanceof float[]) return Arrays.toString((float[]) arrayExpected);
        if (arrayExpected instanceof double[]) return Arrays.toString((double[]) arrayExpected);
        throw new IllegalArgumentException("expect array but actual was: " + arrayExpected.getClass().getCanonicalName());
    }
    
}
