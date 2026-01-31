package com.ums.server.utils;


import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.Optional;

public class HttpUtils {

    public static Cookie getCookie(Cookie[] cookies, String name){

        Optional<Cookie> sessionCookieOptional = Arrays.stream(cookies)
                .filter((cookie) -> cookie.getName().equals(name))
                .findFirst();

        return sessionCookieOptional.orElse(null);
    }
}
