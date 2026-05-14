package io.example.Wellness360.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.example.Wellness360.entity.UserPrinciple;
import io.example.Wellness360.entity.Users;
import io.example.Wellness360.exception.UserException;
import io.example.Wellness360.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> usero = repo.findByEmail(username);
		Users user = usero.orElseThrow(() -> new UserException("Username not found " + username));
		return new UserPrinciple(user);
	}

}
