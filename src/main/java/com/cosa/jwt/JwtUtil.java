package com.cosa.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getKey())
                .compact();
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
    	String username = extractEmail(token);
    	return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
		return extractExpDate(token).before(new Date());
	}

	private Date extractExpDate(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token,Claims::getExpiration);
	}

	private String extractEmail(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}

    public <T>T extractClaim(String token, Function<Claims, T> claimsResolver){
    	final Claims claim = extractAllClaims(token);
    	return claimsResolver.apply(claim);
    }

	private SecretKey getKey() {
		byte [] ans = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(ans);
	}


    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email);
    }
    
    public Claims extractAllClaims(String token) {
    	return Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    	
    }
}