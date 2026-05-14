package io.example.Wellness360.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.example.Wellness360.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//Manage JWT related like generation , extracting claim ,validating
@Service
public class JwtService {
	private String secretKey;
	Map<String, Object> claim = new HashMap<>();

	public JwtService() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	public String generateToken(Users user) {
		return Jwts.builder().claims().add(claim).subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + (30 * 60 * 1000))).and().signWith(getKey()).compact();
	}

	private SecretKey getKey() {
		byte[] decodedKey = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claim = extractAllClaims(token);
		return claimResolver.apply(claim);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}

	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	public boolean validateToken(String token, UserDetails user) {
		return (user.getUsername().equals(extractUsername(token))) && !isTokenExpired(token);
	}

}
