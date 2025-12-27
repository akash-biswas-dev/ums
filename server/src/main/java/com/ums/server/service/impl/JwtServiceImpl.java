package com.ums.server.service.impl;

import com.ums.server.exceptions.JwtException;
import com.ums.server.exceptions.JwtTokenExpiredException;
import com.ums.server.models.UmsPermissions;
import com.ums.server.models.UmsUsers;
import com.ums.server.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.security.Permission;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final String issuer;
    private final String secret;
    private final Integer accessWindow;
    private final Long expiration;
    private final Long maxExpiration;

    private static final String PERMISSION = "permissions";


    @Override
    public UserDetails extractAuthentication(String token) {
        final Claims claims;
        try {
            claims = extractAllClaims(token);
        } catch (ExpiredJwtException e) {
            System.out.println(e.getHeader());
            throw new JwtTokenExpiredException("Token Expired.");
        } catch (Exception e) {
            throw new JwtException("Invalid token");
        }
        String subject = claims.getSubject();

        List<String> permissions = claims.get(PERMISSION, ArrayList.class);
        List<? extends GrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).toList();

        return User.builder()
                .username(subject)
                .password("no-password")
                .authorities(authorities)
                .build();
    }

    @Override
    public String generateToken(UmsUsers umsUsers) {
        List<UmsPermissions> authorities = umsUsers.getPermissions();
        List<String> permissions = authorities.stream().map(UmsPermissions::name).toList();
        String subject = umsUsers.getId();

        Map<String, Object> map = new HashMap<>();
        map.put(PERMISSION, permissions);

        return buildToken(map, subject, accessWindow * 1000L);
    }

    @Override
    public String generateSession(String userId, Boolean rememberMe) {
        Long age = rememberMe ? maxExpiration : expiration;
        return buildToken(new HashMap<>(), userId, age);
    }

    @Override
    public Integer getAge() {
        return (int) expiration.longValue() / 1000;
    }

    @Override
    public Integer getMaxAge() {
        return (int) maxExpiration.longValue() / 1000;
    }

    @Override
    public String extractUserId(String session) {
        return extractClaim(session, Claims::getSubject);
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

    private String buildToken(Map<String, Object> claims, String userId, Long age) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + age))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
