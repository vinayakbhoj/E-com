package com.shopify.app.shopify.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.app.shopify.entity.User;
import com.shopify.app.shopify.services.UserService;

@CrossOrigin("**")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		try {
			service.registerUser(user);
			// used to show message in postman output
			return ResponseEntity.ok("User Registed !!!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
	@GetMapping("/get")
	public ResponseEntity<User> getUser(@RequestParam("id") long id) {
		try {
			User user = service.getUser(id);
			// used to show message in postman output
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
			
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> getUser(@RequestParam("id") long id, @RequestBody User user ) {
		try {
			service.updateUser(id,user);
			// used to show message in postman output
			return ResponseEntity.ok("User is updated");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("User not updated");
			
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestParam("id") long id) {
		try {
			service.deleteUser(id);
			// used to show message in postman output
			return ResponseEntity.ok("User is deleted");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("User not deleted");
			
		}
	}
	
}
