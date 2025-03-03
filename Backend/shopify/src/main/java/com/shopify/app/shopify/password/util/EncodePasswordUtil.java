package com.shopify.app.shopify.password.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncodePasswordUtil {

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String encodePassword(String rawPassword) {
		String encodePassword = passwordEncoder.encode(rawPassword);
		return encodePassword;
	}
	
	public boolean matchPassword(String rawPassword,String encodePassword) {
		boolean matches = passwordEncoder.matches(rawPassword, encodePassword);
		return matches;
	}
}
