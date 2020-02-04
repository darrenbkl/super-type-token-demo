package com.db.sttdemo;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SuperTypeTokenTest {

    @Test
    void testStringArray() {
        Class<?> clazz = new String[10].getClass();
        assertEquals(String[].class, clazz);

    }

    @Test
    void testParameterizedType() {

        List<String> stringList = new ArrayList<>();
        Class<?> clazz = stringList.getClass();
        printType(clazz);
        printType(new ArrayList<String>(){}.getClass());

        assertEquals(ArrayList.class, clazz);
    }

    @Test
    void testParameterizedReferenceType() {

        List<String> stringList = new ArrayList<>();
        Class<?> clazz = stringList.getClass();
        ParameterizedTypeReference ptr = ParameterizedTypeReference.forType(stringList.getClass());
        printType(ptr.getClass());
        Type type = ptr.getType();
        System.out.println(type);

        assertEquals(List.class, clazz);
    }

    private void printType(Class<?> clazz) {
        ParameterizedType t = (ParameterizedType) clazz.getGenericSuperclass();
        System.out.println(t);
//        System.out.println(t.getOwnerType());
        System.out.println(t.getRawType());
        System.out.println(Arrays.toString(t.getActualTypeArguments()));
        System.out.println();
    }

//    private static class IntComparable<T> implements Comparable<T> {
//        @Override
//        public int compareTo(T o) {
//            throw new UnsupportedOperationException();
//        }
//    }


}