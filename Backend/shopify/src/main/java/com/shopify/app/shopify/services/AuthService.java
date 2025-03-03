package com.shopify.app.shopify.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.app.shopify.dto.UserDTO;
import com.shopify.app.shopify.entity.User;
import com.shopify.app.shopify.password.util.EncodePasswordUtil;
import com.shopify.app.shopify.repositories.UserRepositories;

@Service
public class AuthService {

	@Autowired
	private UserRepositories repository;
	
	@Autowired
	private EncodePasswordUtil passwordUtil;
	
	public User login(UserDTO user,HttpSession session) {
		
		User exitingUser = repository.getUserByUserName(user.getUsername());
		
		if( exitingUser != null && passwordUtil.matchPassword(user.getPassword(), exitingUser.getPassword())) {
			
			session.setAttribute("user", exitingUser); // session code
			return exitingUser;
		}else {
			throw new RuntimeException("Entered Username or Password is incorrect");
		}
		
		
		
	}

	
}
