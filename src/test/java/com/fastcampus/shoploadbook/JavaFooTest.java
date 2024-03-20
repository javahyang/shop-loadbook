package com.fastcampus.shoploadbook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaFooTest {

    private JavaFoo javaFoo = new JavaFoo();

    @Test
    public void partiallyCoveredHelloMethodTest() {
        String actual = javaFoo.hello("펭");
        assertEquals(actual, "하");
    }

    @Test
    @DisplayName("callMe 호출")
    void callMeTest() {
        javaFoo.callMe();
    }

    @Test
    @DisplayName("hello-hello 호출결과")
    void helloHello() {
        // when
        String actual = javaFoo.hello("hello");

        // then
        assertEquals(actual, "world");
    }

    @Test
    @DisplayName("hello-default 호출결과")
    void helloDefault() {
        // when
        String actual = javaFoo.hello("귄");

        // then
        assertEquals(actual, "no one");
    }
}
