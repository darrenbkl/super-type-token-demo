package com.db.sttdemo;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class User {
    String firstName;
    String lastName;
    int age;
}