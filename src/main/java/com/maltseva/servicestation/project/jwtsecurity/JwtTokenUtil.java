package com.maltseva.servicestation.project.jwtsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Lazy
public class JwtTokenUtil
        implements Serializable {
    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60;//1 неделя
    @Serial
    private static final long serialVersionUID = -2550185165626007488L;

    //секрет для формирования токена
    private final String secret = "zdtlD3JK56m6wTTgsNFhqzjqP";

    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper();
    }

    //получение логина пользователя из jwt токена
    public String getUsernameFromToken(String token) {
        String subject = getClaimFromToken(token, Claims::getSubject);
        log.info("subject: " + subject);
        JsonNode subjectJSON = null;
        try {
            subjectJSON = objectMapper.readTree(subject);
        } catch (JsonProcessingException e) {
            log.error("JwtTokenUtil#getUsernameFromToken: " + e.getMessage());
        }
        if (subjectJSON != null) {
            return subjectJSON.get("username").asText();
        } else {
            return null;
        }
    }

    //получение id пользователя из jwt токена
    public String getUserIdFromToken(String token) {
        String subject = getClaimFromToken(token, Claims::getSubject);
        JsonNode subjectJSON = null;
        try {
            subjectJSON = objectMapper.readTree(subject);
        } catch (JsonProcessingException e) {
            log.error("JwtTokenUtil#getUserIdFromToken: " + e.getMessage());
        }
        if (subjectJSON != null) {
            return subjectJSON.get("user_id").asText();
        } else {
            return "";
        }
    }

    public String getUserRoleFromToken(String token) {
        String subject = getClaimFromToken(token, Claims::getSubject);
        JsonNode subjectJSON = null;
        try {
            subjectJSON = objectMapper.readTree(subject);
        } catch (JsonProcessingException e) {
            log.error("JwtTokenUtil#getUserRoleFromToken: " + e.getMessage());
        }
        if (subjectJSON != null) {
            return subjectJSON.get("user_role").asText();
        } else {
            return "";
        }
    }

    //получение даты истечения срока из jwt токена
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //получение фиксированной информации из токена
    public <T> T getClaimFromToken(String token,
                                   Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //для получения любой информации из токена, необходим секретный ключ
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //проверка истекло ли время действия токена
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //генерация токена для пользователя
    public String generateToken(UserDetails customUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerate(claims, customUserDetails.toString());
    }

    private String doGenerate(Map<String, Object> claims,
                              String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //подтверждение токена
    public Boolean validateToken(String token,
                                 UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

