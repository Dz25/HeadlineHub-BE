package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")  // used for vue.js
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signUpUser(@Validated @RequestBody User user){
		try {
			if(userRepo.findByEmail(user.getEmail()).isPresent()) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			else {
				User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
				userRepo.save(newUser);
				return new ResponseEntity<>(newUser, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@PostMapping("/signin")
	public ResponseEntity<User> signInUser(@Validated @RequestBody User user) {
		try {
			if(userRepo.findByEmail(user.getEmail()).isPresent()){
				User other = userRepo.findByEmail(user.getEmail()).get();
				if(other.getPassword().equals(user.getPassword()) ){
					return new ResponseEntity<>(other, HttpStatus.OK);
				}
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
