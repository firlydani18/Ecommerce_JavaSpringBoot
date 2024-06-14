package com.firly.store.service;

import com.firly.store.exception.UserException;
import com.firly.store.modal.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;

}
