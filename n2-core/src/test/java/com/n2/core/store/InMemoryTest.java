package com.n2.core.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n2.core.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class InMemoryTest {
    InMemory inMemory;

    @BeforeEach
    void setUp() {
        inMemory = new InMemory(new HashMap<>(), new ObjectMapper());
    }

    @Test
    void put() {
        Result res = inMemory.put("Nico", "key");
        assertTrue(res.isSuccess());
    }

    @Test
    void delete() {
        inMemory.put("Nico", "key");
        Result res = inMemory.delete("Nico");
        assertEquals(1, res.data());
        res = inMemory.delete("bye");
        assertEquals(0, res.data());
    }

    @Test
    void query() {
        inMemory.put("Nico", "key");
        Result<String> res = inMemory.query("$.Nico", String.class);
        assertTrue(res.isSuccess());
        assertEquals("key", res.data());
    }
}