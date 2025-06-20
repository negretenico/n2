package com.n2.core.store;

import com.n2.core.model.Result;

import java.util.List;

public interface N2Store {
    Result<Object> put(String key, Object value);
    Result<Object> get(String path);
    <T> Result<T> getAs(String key, Class<T> clazz);
    Result<Integer> delete(String path);
    Result<List<Object>> query(String jsonPath);}
