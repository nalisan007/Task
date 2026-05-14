package io.example.Wellness360.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.example.Wellness360.entity.BlacklistedToken;

//To store JWT tokens after Logout.
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
	public Optional<BlacklistedToken> findByToken(String token);

	@Query("SELECT t.expiry FROM BlacklistedToken t WHERE t.token = :token")
	public Optional<Date> findExpiryByToken(@Param("token") String token);

	public void deleteByToken(String token);

	public int deleteByExpiryBefore(Date expiry);

}
