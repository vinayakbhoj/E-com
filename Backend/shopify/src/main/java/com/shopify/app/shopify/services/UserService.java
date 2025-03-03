package com.shopify.app.shopify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.shopify.app.shopify.entity.User;
import com.shopify.app.shopify.password.util.EncodePasswordUtil;
import com.shopify.app.shopify.repositories.UserRepositories;

@Service
public class UserService {

	@Autowired
	private UserRepositories repositories;
	
	@Autowired
	private EncodePasswordUtil passwordUtil;
	
	public void registerUser(User user) {
		
		String encodePassword = passwordUtil.encodePassword(user.getPassword());
		user.setPassword(encodePassword);
		
		User exitingUser = repositories.getUserByUserName(user.getUsername());
		
		if (exitingUser == null) {
			repositories.registerUser(user);
		} else {
			throw new RuntimeException("User with username is already present");
		}

	}

	public User getUser(long id) {
		
		User user = repositories.getUser(id);
		return user;
	}

	public void updateUser(long id, User user) {
	
		User exitingUser = repositories.getUser(id);
		exitingUser.setFirstName(user.getFirstName());
		exitingUser.setLastName(user.getLastName());
		exitingUser.setUsername(user.getUsername());
		exitingUser.setEmail(user.getEmail());
		exitingUser.setAddress(user.getAddress());
		
		repositories.updateUser(exitingUser);
	}

	public void deleteUser(long id) {
		
		repositories.deleteUser(id);
		
	}

}
