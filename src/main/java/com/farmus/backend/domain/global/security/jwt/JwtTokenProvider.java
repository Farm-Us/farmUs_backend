package com.farmus.backend.domain.global.security.jwt;

import com.farmus.backend.domain.global.security.jwt.dto.TokenResponse;
import com.farmus.backend.domain.user.dto.UserDto;
import com.farmus.backend.domain.user.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-expiration-ms}")
    private long accessTokenValidityInMs;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshTokenValidityInMs;

    public TokenResponse generateTokens(User user) {
        UserDto userDto = UserDto.from(user);
        String accessToken = generateToken(userDto, accessTokenValidityInMs);
        String refreshToken = generateToken(userDto, refreshTokenValidityInMs);

        return new TokenResponse(accessToken, refreshToken, userDto);

    }

    private String generateToken(UserDto userDto, long validityMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMs);

        return Jwts.builder()
                .setSubject(String.valueOf(userDto.userId()))
                .claim("email", userDto.email())
                .claim("name", userDto.name())
                .claim("profileImage", userDto.profileImage())
                .claim("role", userDto.role())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    public Long extractUserId(String token) {
        return Long.valueOf(
                Jwts.parserBuilder()
                        .setSigningKey(jwtSecret.getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
