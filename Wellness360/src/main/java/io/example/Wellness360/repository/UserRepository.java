package io.example.Wellness360.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.example.Wellness360.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	public Optional<Users> findByEmail(String username);

	public boolean existsByEmail(String email);

}
