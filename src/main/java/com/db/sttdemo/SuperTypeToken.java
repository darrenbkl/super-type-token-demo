package com.db.sttdemo;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class SuperTypeToken {

    private static Map<Class<?>, Object> lookup;

    static {
        lookup = new HashMap<>();
        lookup.put(Integer.class, 1);
        lookup.put(Double.class, 1.1d);
        lookup.put(BigDecimal.class, BigDecimal.valueOf(100));
        lookup.put(String.class, "Asdf");
        lookup.put(List.class, new ArrayList<Integer>());
    }

    public static void main(String[] args) {

//        //@formatter:off
//        Class<?> clazz = new STT<HashMap<Integer, String>>() {}.getClass();
//        printType(clazz);
//        //@formatter:on
//
//        Class<?> clazz2 = SubConcrete.class;
//        printType(clazz2);
//
//        Class<?> clazz3 = SubGeneric.class;
//        printType(clazz3);

        ParameterizedTypeReference<HashMap<Integer, String>> ptr = new ParameterizedTypeReference<>() {};
        System.out.println("ptr = " + ptr.toString());
        Type type = ptr.getType();
        System.out.println("type = " + type);

        lookup.forEach((k,v) -> {
            System.out.println(k);
            Object cast = k.cast(v);
            System.out.println(cast.getClass());
            System.out.println(cast);
        });
    }

    private static void printType(Class<?> clazz) {

        ParameterizedType t = (ParameterizedType) clazz.getGenericSuperclass();
        System.out.println(t.getOwnerType());
        System.out.println(t.getRawType());
        System.out.println(Arrays.toString(t.getActualTypeArguments()));
        System.out.println();
    }
}
