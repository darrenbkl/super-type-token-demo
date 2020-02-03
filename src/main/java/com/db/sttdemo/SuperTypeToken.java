package com.db.sttdemo;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public class SuperTypeToken {

    public static void main(String[] args) {

        Class<?> clazz = new STT<String>() {}.getClass();
        printType(clazz);

        Class<?> clazz2 = SubConcrete.class;
        printType(clazz2);

        Class<?> clazz3 = SubGeneric.class;
        printType(clazz3);
    }

    private static void printType(Class<?> clazz) {
        ParameterizedType t = (ParameterizedType) clazz.getGenericSuperclass();
        System.out.println(t.getOwnerType());
        System.out.println(t.getRawType());
        System.out.println(Arrays.toString(t.getActualTypeArguments()));
    }


}
