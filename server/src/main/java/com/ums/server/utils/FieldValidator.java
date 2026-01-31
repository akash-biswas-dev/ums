package com.ums.server.utils;


@FunctionalInterface
public interface FieldValidator {
    boolean isFieldValid(String fieldName);
}
