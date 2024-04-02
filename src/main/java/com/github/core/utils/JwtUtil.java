package com.github.core.utils;

import com.github.core.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yayee
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${token.secret}")
    private String secret;
    @Value("${token.ttl}")
    private String ttl;

    public String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(JwtConstant.LOGIN_USER_KEY, userId);

        long expMillis = System.currentTimeMillis() + Long.parseLong(ttl);
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .setExpiration(exp)
                .compact();
    }

    public Long parseToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Integer o = (Integer) claims.get(JwtConstant.LOGIN_USER_KEY);
        return o.longValue();
    }
}
