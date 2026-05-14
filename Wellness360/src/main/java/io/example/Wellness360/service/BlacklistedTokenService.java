package io.example.Wellness360.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.example.Wellness360.entity.BlacklistedToken;
import io.example.Wellness360.repository.BlacklistedTokenRepository;

//Implement Logout feature for Jwt Token
@Service
public class BlacklistedTokenService {
	BlacklistedTokenRepository repo;

	public void addToBlacklist(String token, Date expiry) {

		repo.save(new BlacklistedToken(token, expiry));

	}

	public boolean isBlacklisted(String token) {
		Optional<Date> expiry = repo.findExpiryByToken(token);
		if (expiry.isEmpty())
			return false;

		if (expiry.get().before(new Date())) {
			repo.deleteByToken(token);
			return false;
		}
		return true;
	}

	public int deleteExpiredTokens() {
		return repo.deleteByExpiryBefore(new Date());

	}

	public BlacklistedTokenService(BlacklistedTokenRepository repo) {
		super();
		this.repo = repo;
	}

}
