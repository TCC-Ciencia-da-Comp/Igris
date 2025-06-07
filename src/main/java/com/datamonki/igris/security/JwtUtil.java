package com.datamonki.igris.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${spring.boot.security.jwt.secret}")
    private String secret;

    private Key secretKey;

    @Value("${spring.boot.security.jwt.expiration}")
    private long expirationTime;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(String login, String nome, String perfil, String nomeInstituicao, Integer idInstituicao, Integer id, String email) {
        return Jwts.builder()
                .setSubject(login)
                .claim("nome", nome)
                .claim("perfil", perfil)
                .claim("instituicao", nomeInstituicao)
                .claim("idInstituicao", idInstituicao) 
                .claim("id", id)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public <T> T extractClaim(String token, String claimName, Class<T> clazz) {
        Claims claims = getClaims(token);
        return claims.get(claimName, clazz);
    }

    public boolean validateToken(String token, String userName) {
        return userName.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
