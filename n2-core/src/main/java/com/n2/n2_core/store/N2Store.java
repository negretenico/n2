package com.n2.n2_core.store;

import com.n2.n2_core.model.Result;

import java.util.List;
import java.util.Map;

public interface N2Store {
    Result<Object> put(String key, Object value);
    Result<Object> get(String path);
    <T> Result<T> getAs(String key, Class<T> clazz);
    Result<Integer> delete(String path);
    Result<List<Object>> query(String jsonPath);}
