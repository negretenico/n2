package com.n2.core.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.n2.core.model.Result;

import java.util.Map;
import java.util.Optional;

public class InMemory implements N2Store {
    private final Map<String, Object> inMemory;
    private final ObjectMapper objectMapper;
    private static final int SUCCESS = 1;
    private static final int FAILURE = 0;

    public InMemory(Map<String, Object> inMemory, ObjectMapper objectMapper) {
        this.inMemory = inMemory;
        this.objectMapper = objectMapper;
    }

    @Override
    public Result<Object> put(String key, Object value) {
        try {
            return Result.success(Optional.ofNullable(inMemory.put(key, value)));
        } catch (Exception e) {
            return Result.failure(e.getLocalizedMessage());
        }

    }

    @Override
    public Result<Integer> delete(String path) {
        try {
            return Result.success(Optional.ofNullable(inMemory.remove(path)).map(s -> SUCCESS).orElse(FAILURE));
        } catch (Exception e) {
            return Result.failure(e.getLocalizedMessage());
        }
    }

    @Override
    public <T> Result<T> query(String jsonPath, Class<T> clazz) {
        String asJsonString;
        try {
            asJsonString = objectMapper.writeValueAsString(inMemory);
            return Result.success(JsonPath.parse(asJsonString).read(jsonPath,
                    clazz));
        } catch (Exception e) {
            return Result.failure(e.getLocalizedMessage());
        }
    }
}
