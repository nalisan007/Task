package io.example.Wellness360.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.example.Wellness360.entity.PasswordDto;
import io.example.Wellness360.entity.Users;
import io.example.Wellness360.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(value = { "/api/v{version}/user", "/api/user" }, version = "1.0+")
public class UserController {
	@Autowired
	UserService service;

	@PostMapping
	public ResponseEntity<?> saveUser(@Valid @RequestBody Users user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(user));

	}

	@GetMapping
	public ResponseEntity<String> findUserById(@RequestParam @Positive long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findUserById(id));
	}

	@GetMapping("email")
	public ResponseEntity<Long> findUserByEmail(@RequestParam @Email String email) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findUserByEmail(email));
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUserById(@AuthenticationPrincipal UserDetails user,
			@RequestHeader HttpHeaders incomingHeader) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(service.deleteById(user.getUsername(), incomingHeader));
	}

	@PostMapping("changePassword")
	public ResponseEntity<String> changePassword(@RequestBody PasswordDto pass) {
		String response = service.changePassword(pass);
		if (response.equals("Password Changed"))
			return ResponseEntity.status(HttpStatus.OK).body(response);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
