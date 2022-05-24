package com.hanghae.justpotluck.global.security;

import com.hanghae.justpotluck.domain.user.dto.response.TokenResponse;
import com.hanghae.justpotluck.global.config.AppProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final AppProperties appProperties;
    private final Long refreshTokenExpiry = 7 * 24 * 60 * 60 * 1000L; // 14 day
//    private final Key key = appProperties.getAuth().getTokenSecret();
    private static final String AUTHORITIES_KEY = "role";
    private static final String GRANT_TYPE = "Bearer";
    private Key key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());
    }

    public TokenResponse createTokenResponse(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        String accessToken = Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
//                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + refreshTokenExpiry))
                .signWith(key, SignatureAlgorithm.HS512)
//                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();

        log.info("리프레시 토큰 = {}", refreshToken);
        log.info("액세스 토큰 = {}", accessToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpireDate(appProperties.getAuth().getTokenExpirationMsec())
                .refreshTokenExpireDate(refreshTokenExpiry)
                .build();
    }



    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}