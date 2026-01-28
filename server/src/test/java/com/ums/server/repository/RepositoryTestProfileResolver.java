package com.ums.server.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.test.context.ActiveProfilesResolver;

public class RepositoryTestProfileResolver implements ActiveProfilesResolver {
    @Override
    @NullMarked
    public String[] resolve(Class<?> testClass) {
        String activeProfile = System.getenv("UMS_REPOSITORY_TEST_PROFILE");
        if(activeProfile == null){
           return new String[]{"dev-test"};
        }
        return new String[]{activeProfile};
    }
}
