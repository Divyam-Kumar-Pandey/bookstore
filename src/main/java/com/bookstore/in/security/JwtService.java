package com.bookstore.in.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bookstore.in.User.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:3600000}")
    private long expirationMs;
		
		@Value("${jwt.refresh-expiration-ms:1209600000}") // 14 days default
		private long refreshExpirationMs;

    private SecretKey signingKey;

    @PostConstruct
    void init() {
        signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

		public String generateToken(User user) {
			return generateAccessToken(user);
		}

		public String generateAccessToken(User user) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + expirationMs);

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }
		
		public String generateRefreshToken(User user) {
			long now = System.currentTimeMillis();
			Date issuedAt = new Date(now);
			Date expiration = new Date(now + refreshExpirationMs);
			
			return Jwts.builder()
					.subject(user.getEmail())
					.issuedAt(issuedAt)
					.expiration(expiration)
					.signWith(signingKey)
					.compact();
		}

    public boolean isTokenValid(String token, User user) {
        final String email = extractUsername(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }
		
		public boolean isRefreshTokenValid(String refreshToken, User user) {
			final String email = extractUsername(refreshToken);
			return email.equals(user.getEmail()) && !isTokenExpired(refreshToken);
		}

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

