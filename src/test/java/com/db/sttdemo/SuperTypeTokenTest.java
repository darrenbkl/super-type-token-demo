package com.db.sttdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuperTypeTokenTest {

    private ObjectMapper mapper;

    //language=JSON
    String json = "[\n"
                  + "  "
                  + "{\n"
                  + "    \"firstName\": \"D\",\n"
                  + "    \"lastName\": \"B\",\n"
                  + "    \"age\": 11\n"
                  + "  "
                  +
                  "},\n"
                  + "  "
                  + "{\n"
                  + "    \"firstName\": \"E\",\n"
                  + "    \"lastName\": \"F\",\n"
                  + "    \"age\": 22\n"
                  + "  "
                  + "}\n"
                  +
                  "]";


    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

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
    void testParameterizedReferenceType1() throws NoSuchMethodException {
        ParameterizedTypeReference<List<String>> prt = new ParameterizedTypeReference<>() {
        };

        System.out.println(prt.getType()); // java.util.List<java.lang.String>
        ParameterizedType type = (ParameterizedType) prt.getType();
        System.out.println(type.getRawType()); // interface java.util.List
        System.out.println(Arrays.toString(type.getActualTypeArguments())); // [class java.lang.String]
    }

    @Test
    void testParameterizedReferenceType2() throws NoSuchMethodException {
        ParameterizedTypeReference<HashMap<Integer, String>> prt = new ParameterizedTypeReference<>() {
        };

        System.out.println(prt.getType()); // java.util.HashMap<java.lang.Integer, java.lang.String>
        ParameterizedType type = (ParameterizedType) prt.getType();
        System.out.println(type.getRawType()); // class java.util.HashMap
        System.out.println(
                Arrays.toString(type.getActualTypeArguments())); // [class java.lang.Integer, class java.lang.String]

        // THIS IS HOW YOU TEST, BY GETTING TYPE USING REFLECTION
//        Type genericSuperclass = new ArrayList<String>() {}.getClass().getGenericSuperclass();
//        Type expected = getClass().getMethod("listMethod")
//                                  .getGenericReturnType();
//        printType(prt.getClass());
//        assertEquals(expected, prt.getType());
    }


    @Test
    void testTypeTokenSimple() {
        TypeToken<String> typeToken = TypeToken.of(String.class);
        System.out.println(typeToken.getType());
    }

    @Test
    void testTypeTokenParameterised() {
        TypeToken<List<String>> typeToken = new TypeToken<>() {
        };
        System.out.println(typeToken.getType());
    }

    @Test
    void testGson() {
        Type type = new TypeToken<List<User>>() {
        }.getType();

        List<User> list = new Gson().fromJson(json, type);
        System.out.println(list);
    }


    @Test
    void testJacksonTypeReference1() throws JsonProcessingException {
        TypeReference<List<String>> typeReference = new TypeReference<>() {
        };
        System.out.println(typeReference.getType());

        //language=JSON
        String json = "[\"asdf\",\"zxcv\",\"qwer" + "\"\n"
                      + "]";
        ObjectMapper mapper = new ObjectMapper();
        List<String> list = mapper.readValue(json, new TypeReference<List<String>>() {
        });
        System.out.println(list);
    }

    @Test
    void testJacksonTypeReference2() throws JsonProcessingException {
        TypeReference<List<User>> typeRef = new TypeReference<>() {
        };
        List<User> list = mapper.readValue(json, typeRef);
        System.out.println(list);
    }

    @Test
    void testJacksonJavaType() throws JsonProcessingException {
        JavaType javaType = mapper.getTypeFactory()
                                  .constructCollectionType(List.class, User.class);
        List<User> list = mapper.readValue(json, javaType);
        System.out.println(list);
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