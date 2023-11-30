package com.surveyking.apigateway.config;


import com.surveyking.apigateway.service.SecretKeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final SecretKeyService secretKeyService;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = getSignInKey();
    }

    public String extractUserId(String token) throws Exception{
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpirationDate(String token) throws Exception{
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUserAuthorities(String token) throws Exception{
        final Claims claims = extractAllClaims(token);
        if (claims.get("authorities") == null) return "";
        StringBuilder authsBuilder = new StringBuilder(claims.get("authorities").toString());
        authsBuilder.deleteCharAt(0);
        authsBuilder.deleteCharAt(authsBuilder.length() - 1);
        return authsBuilder.toString();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws Exception{
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) throws Exception{
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public boolean isInvalid(String token) throws Exception{
        return isTokenExpired(token);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyService.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
