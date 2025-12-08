package com.example.cams.mf.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration-ms}")
	private long jwtExpirationMs;
	
	
	public String generateToken(String userName, String roleType) {
		Date now = new Date();
		Date exp=new Date(now.getTime()+jwtExpirationMs); 
		Key key =Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		return Jwts.builder()
				.setSubject(userName)
				.claim("role", roleType)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(key)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Key key=Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			return false;
		}
		
	}

	public String extractuserName(String jwtToken) {
		Key key=Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		Claims claim=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
		return claim.getSubject();
	}
	 
}
