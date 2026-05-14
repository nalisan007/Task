package io.example.Wellness360.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.example.Wellness360.entity.Users;
import io.example.Wellness360.service.BlacklistedTokenService;
import io.example.Wellness360.service.JwtService;
import io.example.Wellness360.service.MyUserDetailService;
import io.example.Wellness360.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class AuthController {
	@Autowired
	UserService userService;
	@Autowired
	BlacklistedTokenService blacklistService;
	@Autowired
	JwtService jwtService;
	@Autowired
	MyUserDetailService myUserDetailsService;

	@PostMapping("auth/register")
	public ResponseEntity<String> register(@Valid @RequestBody Users user) {

		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
	}


	@PostMapping("auth/login")
	public ResponseEntity<String> login(@RequestBody Users user) {

		return ResponseEntity.status(HttpStatus.OK).body(userService.verify(user));
	}

	@GetMapping("/")
	public String greet() {
		return "Welcome. Please Login ('/auth/login') or Register ('/register'). See Github description for Documentation.";
	}

	@PostMapping("auth/logout")
	public ResponseEntity<String> logout(HttpServletRequest req, Authentication authentication) {
		String authHeader = req.getHeader("Authorization");
		String token = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			{
				token = authHeader.substring(7); // Ignore 'Bearer ' in Authorization Header
				username = jwtService.extractUsername(token);
				if (username != null) {
					UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
					if (jwtService.validateToken(token, userDetails)) {
						blacklistService.addToBlacklist(token, jwtService.extractClaim(token, Claims::getExpiration));

						return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
					}
				}
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");

	}

	// Manual Endpoint for admin [username must be admin@admin.com] to delete
	// expired token to reduce database growing
	// indefinetly
	@PreAuthorize("authentication.name == 'admin@admin.com'")
	@GetMapping("deleteExpiredToken")
	@Transactional
	public ResponseEntity<?> deleteExpiredToken() {
		blacklistService.deleteExpiredTokens();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
