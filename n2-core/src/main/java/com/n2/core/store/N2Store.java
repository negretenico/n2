package com.n2.core.store;

import com.n2.core.model.Result;

public interface N2Store {
    /**
     * Inserts or updates a value in the store under the specified key.
     *
     * @param key   Unique key to identify the value
     * @param value The value to store
     * @return {@link Result} containing the previous value, if any
     */
    Result<Object> put(String key, Object value);

    /**
     * Deletes a value from the store by its key.
     *
     * @param key The key of the value to delete (not a JsonPath expression)
     * @return {@link Result} indicating success (1) or failure (0)
     */
    Result<Integer> delete(String key);

    /**
     * Queries the store using a JsonPath expression across all stored documents.
     *
     * @param jsonPath A JsonPath string expression to filter or extract data
     * @param clazz    The type of results to deserialize to
     * @return {@link Result} containing the query results or an error
     */
    <T> Result<T> query(String jsonPath, Class<T> clazz);
}
