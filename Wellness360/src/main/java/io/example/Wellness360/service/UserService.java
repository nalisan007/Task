package io.example.Wellness360.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.example.Wellness360.entity.PasswordDto;
import io.example.Wellness360.entity.Users;
import io.example.Wellness360.exception.UserException;
import io.example.Wellness360.repository.UserRepository;

@Service
public class UserService {
	UserRepository repo;
	AuthenticationManager authManager;
	JwtService jwtService;



	public String registerUser(Users user) {
		if (!repo.existsByEmail(user.getEmail())) {
			user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
			repo.save(user);
			return "User created with email " + user.getEmail();
		}
		throw new UserException("User already exists with that email!");
	}

	public String findUserById(long id) {
		Optional<Users> u = repo.findById(id);

		if (u.isPresent()) {
			return u.get().getEmail();
		}
		throw new UserException("No User exist with id " + u.get().getUserId());
	}

	public long findUserByEmail(String email) {
		Optional<Users> u = repo.findByEmail(email);
		if (u.isPresent()) {
			return u.get().getUserId();
		}
		throw new UserException("User does not exist with email " + u.get().getEmail());
	}

	public String deleteById(long userId) {
		if (repo.existsById(userId)) {

			repo.deleteById(userId);

			return "User Deleted with Id " + userId;
		}
		throw new UserException("No User exist with User ID " + userId);
	}


	public String changePassword(PasswordDto pass) {
		if (!repo.existsById(pass.getUserId()))
			throw new UserException("User doesn't exist");
		Users u = repo.findById(pass.getUserId()).get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

		if (encoder.matches(pass.getOldPassword(), u.getPassword())) {
			u.setPassword(encoder.encode(pass.getNewPassword()));

			return "Password Changed";

		}
		return "Old Password don't match. Password change failed.";
	}

	public String verify(Users user) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		if (auth.isAuthenticated()) {
			return jwtService.generateToken(user);
		}
		return "Login Failed";
	}


	public UserService(UserRepository repo, AuthenticationManager authManager, JwtService jwtService) {
		super();
		this.repo = repo;
		this.authManager = authManager;
		this.jwtService = jwtService;
	}

}
