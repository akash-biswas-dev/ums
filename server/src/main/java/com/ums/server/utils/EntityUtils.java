package com.ums.server.utils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

public class EntityUtils {


    public static <T, K> void updateEntityFields(T object, K updatedValues)
            throws IllegalAccessException {
        updateEntityFields(object, updatedValues, (String name) -> false);
    }


    public static <T, K> void updateEntityFields(T object,
                                              K updatedValues, Predicate<String> validator) throws IllegalAccessException {
        Field[] fields = updatedValues.getClass().getDeclaredFields();

        for (Field field : fields) {
            Object fieldValue = field.get(object);
            final Object updatedValue;
            boolean isProfileIgnored = validator.test(field.getName());
            if (isProfileIgnored) {
                continue;
            }
            if (field.getName().equals("dateOfBirth")) {
                updatedValue = LocalDate.parse((String) fieldValue);
            } else {
                updatedValue = fieldValue;
            }
            field.set(object, updatedValue);
        }
    }
}
