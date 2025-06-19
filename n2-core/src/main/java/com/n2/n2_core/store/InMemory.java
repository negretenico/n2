package com.n2.n2_core.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n2.n2_core.model.Result;

import java.util.List;
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
    public Result<Object> get(String path) {
        return Optional.ofNullable(inMemory.get(path)).map(Result::success).orElseGet(() -> Result.failure("Could not find this record"));
    }

    @Override
    public <T> Result<T> getAs(String key, Class<T> clazz) {
        Optional<Object> potential = Optional.ofNullable(inMemory.get(key));
        try {
            return potential.map(val -> clazz.isInstance(val) ? clazz.cast(val) : objectMapper.convertValue(val, clazz)).map(Result::success).orElse(Result.failure("Could not find record"));
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
    public Result<List<Object>> query(String jsonPath) {
        return Result.failure("THIS METHOD IS STILL NOT IMPLEMENTED");
    }
}
