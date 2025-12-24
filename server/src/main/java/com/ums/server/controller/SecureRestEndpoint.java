package com.ums.server.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/secured")
public class SecureRestEndpoint {

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/permission")
    @PreAuthorize(value = "hasRole('user:read')")
    public ResponseEntity<String> helloWithPermission() {
        return ResponseEntity.ok("Hello with permission");
    }

    @GetMapping("/error")
    public ResponseEntity<String> helloWithError() {
        throw new RuntimeException("Unknown Exception");
    }
}
