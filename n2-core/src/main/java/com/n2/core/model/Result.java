package com.n2.core.model;

import java.util.Objects;

public record Result<T>(T data, String errorMsg) {
    public static <T> Result<T> success(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> failure(String errorMessage) {
        return new Result<>(null, errorMessage);
    }

    public boolean isSuccess() {
        return Objects.nonNull(this.data);
    }

    public boolean isFailure() {
        return Objects.isNull(this.data);
    }
}