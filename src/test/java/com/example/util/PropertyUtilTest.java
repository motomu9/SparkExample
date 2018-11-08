package com.example.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyUtilTest {

    @Test
    void getProperty() {
        assertEquals(PropertyUtil.getProperty("testProp"),"ABCS");
    }

//    @Test
//    void getProperty1() {
//    }
}