package com.assignment.kanban.util;

import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;

@Component
public class KanbanJwtUtil {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public KeyPair keyPair;
	
	@Value("${token.expiry.time}")
	private long tokenExpiryTime;

	@PostConstruct
	public void init() {

		keyPair = Jwts.SIG.RS256.keyPair().build();
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return buildToken(claims, userName);
	}

	private String buildToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().claims(claims).subject(userName).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * tokenExpiryTime))
				.signWith(keyPair.getPrivate(), Jwts.SIG.RS256).compact();
	}

	private KeyPair getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Jwts.SIG.RS256.keyPair().build();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(keyPair.getPublic()).build().parseSignedClaims(token).getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
