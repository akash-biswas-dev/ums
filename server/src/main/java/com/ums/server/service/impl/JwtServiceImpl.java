package com.ums.server.service.impl;

import com.ums.server.models.UmsUsers;
import com.ums.server.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final String issuer;
    private final String secret;
    private final int accessWindow;
    private final int sessionAge;
    private final int sessionMaxAge;
    private final int updateProfileSessionExpiry;

    private static final String PERMISSION = "permissions";


    @Override
    public UserDetails extractAuthentication(String token) throws RuntimeException {
        final Claims claims = extractAllClaims(token);
        String subject = claims.getSubject();
        return User.builder()
                .username(subject)
                .password("no-password")
                .authorities(List.of())
                .build();
    }

    @Override
    public String generateToken(UmsUsers umsUsers) {
        String subject = umsUsers.getId();

        Map<String, Object> map = new HashMap<>();

        return buildToken(map, subject, accessWindow * 1000L);
    }

    @Override
    public String generateSession(String userId, Boolean rememberMe) {
        int age = rememberMe ? sessionMaxAge : sessionAge;
        return buildToken(new HashMap<>(), userId, age * 1000L);
    }

    @Override
    public Integer getAge() {
        return sessionAge;
    }

    @Override
    public Integer getMaxAge() {
        return sessionMaxAge;
    }

    @Override
    public Integer getProfileUpdateSessionAge() {
        return updateProfileSessionExpiry;
    }

    @Override
    public String extractUserId(String session) throws RuntimeException {
        return extractClaim(session, Claims::getSubject);
    }

    @Override
    public String generateTemporaryToken(String userId) {
//       Build a token which have only subject with 1 hour expiration.
        return buildToken(new HashMap<>(), userId, updateProfileSessionExpiry * 1000L);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String buildToken(Map<String, Object> claims, String userId, Long ageInMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ageInMillis))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
