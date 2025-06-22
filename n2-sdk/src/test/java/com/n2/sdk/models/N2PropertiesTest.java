package com.n2.sdk.models;

import com.n2.core.model.StoreType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(value = "test")
class N2PropertiesTest {
    @Autowired
    N2Properties n2Properties;

    @Test
    void setAutowiring() {
        assertEquals("data/n2-seed.json", n2Properties.getSeed());
        assertEquals("data/schema.json", n2Properties.getSchema());
        assertEquals(StoreType.IN_MEMORY, n2Properties.getType());
    }
}