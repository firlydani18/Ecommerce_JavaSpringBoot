package com.firly.store.service;

import com.firly.store.config.JwtTokenProvider;
import com.firly.store.exception.UserException;
import com.firly.store.modal.User;
import com.firly.store.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
	
	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;
	
	public UserServiceImplementation(UserRepository userRepository,JwtTokenProvider jwtTokenProvider) {
		
		this.userRepository=userRepository;
		this.jwtTokenProvider=jwtTokenProvider;
		
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user=userRepository.findById(userId);
		
		if(user.isPresent()){
			return user.get();
		}
		throw new UserException("user not found with id "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		System.out.println("User service - findUserProfileByJwt");
		String email = jwtTokenProvider.getEmailFromJwtToken(jwt);

		System.out.println("Email extracted from JWT: " + email);

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("User not exist with email " + email);
		}

		System.out.println("User found: " + user.getEmail());
		return user;
	}

}
