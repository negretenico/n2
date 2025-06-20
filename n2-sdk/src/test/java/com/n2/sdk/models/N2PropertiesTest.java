package com.n2.sdk.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test")
class N2PropertiesTest {
    @Autowired
    N2Properties n2Properties;
    @Test
    void setAutowiring(){
        assertEquals("data/n2-seed.json",n2Properties.getSeed());
    }
}