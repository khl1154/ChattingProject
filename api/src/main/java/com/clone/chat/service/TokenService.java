package com.clone.chat.service;

import com.clone.chat.code.UserRole;
import com.clone.chat.domain.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    private String secretKey;

    public String makeToken(User user) throws UnsupportedEncodingException {
        secretKey = "secret";
        String jwt = Jwts.builder()
                //.setIssuer("Stormpath")
                .setSubject(user.getId())
                .claim("authorities", UserRole.USER.getGrantedAuthorities())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
        return jwt;
    }

    public Jws<Claims> parserJwt(String token) throws JwtException {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);
        return claimsJws;
    }

}
