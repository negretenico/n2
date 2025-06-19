package com.n2.n2_core.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n2.n2_core.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class InMemoryTest {
    InMemory inMemory;
    @Mock
    ObjectMapper objectMapper;
    @BeforeEach
    void setUp(){
        inMemory= new InMemory(new HashMap<>(),objectMapper);
    }
    @Test
    void put() {
        Result res  =inMemory.put("Nico","key");
        assertTrue(res.isSuccess());
    }

    @Test
    void get() {
        inMemory.put("Nico","key");
        Result res = inMemory.get("Nico");
        assertTrue(res.isSuccess());
        res = inMemory.get("bye");
        assertTrue(res.isFailure());
    }

    @Test
    void getAs() {
        inMemory.put("Nico","key");
        Result<String> res = inMemory.getAs("Nico",String.class);
        assertEquals(String.class, res.data().getClass());
    }

    @Test
    void delete() {
        inMemory.put("Nico","key");
        Result res = inMemory.delete("Nico");
        assertEquals(1, res.data());
        res = inMemory.delete("bye");
        assertEquals(0, res.data());
    }

    @Test
    void query() {
        assertTrue(inMemory.query("someQuery").isFailure());
    }
}