package com.clone.chat.service;

import com.clone.chat.code.UserRole;
import com.clone.chat.config.security.jwt.JwtConfig;
import com.clone.chat.domain.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    public JwtConfig jwtConfig;

    public String makeToken(String subject, Collection<? extends GrantedAuthority> bodyAuthorities) {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(jwtConfig.getTokenExpirationAfterDays());
//        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(1);
        Date expirationDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//        Key key = new SecretKeySpec(SignatureAlgorithm.HS512.getJcaName().getBytes());
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forSigningKey(Key);
//        byte[] keyBytes = this.jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8);;
//        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        String token = Jwts.builder()
                .setSubject(subject)
                .claim("authorities", bodyAuthorities)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
//                .signWith(secretKey)
                .signWith(SignatureAlgorithm.HS512, this.jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .compact();
        return token;
    }

    public Jws<Claims> parserJwt(String header) throws JwtException {
//        ,
        String token = header.replace(jwtConfig.getTokenPrefix(), "");
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(this.jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);
        return claimsJws;
    }

}
