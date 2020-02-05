package com.db.sttdemo;

import com.google.common.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuperTypeTokenTest {

    @Test
    void testString() {
        String str = "asdf";
        Class<?> clazz = str.getClass();
        assertEquals(String.class, clazz);
    }

    @Test
    void testStringArray() {
        String[] stringArray = {};
        Class<?> clazz = stringArray.getClass();
        assertEquals(String[].class, clazz);
    }

    @Test
    void testParameterizedType() {
        List<String> stringList = new ArrayList<>();
        Class<?> clazz = stringList.getClass();
        assertEquals(ArrayList.class, clazz);
    }

    @Test
    void testAnonymousClass() {
        Class<?> clazz = new SuperTypeToken<List<String>>() {
        }.getClass();
        printType(clazz);
    }

    @Test
    void testSubConcreteClass() {
        Class<?> clazz2 = ConcreteSubClass.class;
        printType(clazz2);
    }

    @Test
    void testSubGenericClass() {
        Class<?> clazz3 = new SubGeneric<List<String>>().getClass();
        printType(clazz3);
    }

    @Test
    void testParameterizedReferenceType() throws NoSuchMethodException {
        ParameterizedTypeReference<List<String>> ptr2 = new ParameterizedTypeReference<>() {
        };
//        Type genericSuperclass = new ArrayList<String>() {}.getClass().getGenericSuperclass();
        Type expected = getClass().getMethod("listMethod")
                                  .getGenericReturnType();

        printType(ptr2.getClass());

        assertEquals(expected, ptr2.getType());
    }

    @Test
    void testTypeToken() {

        TypeToken
    }

    public static List<String> listMethod() {
        return null;
    }

    private void printType(Class<?> clazz) {
        ParameterizedType t = (ParameterizedType) clazz.getGenericSuperclass();
        System.out.println(Arrays.toString(t.getActualTypeArguments()));
    }

    public static abstract class SuperTypeToken<T> {
    }

    public static class ConcreteSubClass extends SuperTypeToken<List<String>> {
    }

    public static class SubGeneric<T> extends SuperTypeToken<T> {
    }
}