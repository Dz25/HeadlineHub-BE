package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.article.Article;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")  // used for vue.js
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	ArticleRepository articleRepo;

	
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
			}
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//this method is to access saved post by user to read later 


	
	@GetMapping("/{id}/articles")
	public ResponseEntity<User> getArticles(@PathVariable Long id){

		try {
		  Optional<User> user1= userRepo.findById(id);
		  if(user1.isPresent()){
			  User newUser= user1.get();
			  newUser.getArticles();
			  return new ResponseEntity<>(newUser, HttpStatus.OK);
		  }
		  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//this method is  save post by user to read later.

	@PostMapping("/{id}/articles")
	public ResponseEntity<User> savePost(@PathVariable Long id , @Validated @RequestBody  Article article){
		try {
			  Optional<User> user1= userRepo.findById(id);
			  if(user1.isPresent()){
				  User newUser= user1.get();
				  Optional<Article> otherArticle = articleRepo.findByUrl(article.getUrl());
				  if (!otherArticle.isPresent()){
					  articleRepo.save(article);
				  }
				  newUser.addArticle(article);
				  return new ResponseEntity<>(newUser, HttpStatus.CREATED);
			  }
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
	//this method is to delete saved post by user to read 
	@DeleteMapping("/{id1}/articles/{id2}")
	public ResponseEntity<HttpStatus>deleteSavedPost(@PathVariable("id1") Long id ,@PathVariable("id2") Long articleId){
		try {
			  Optional<User> user1= userRepo.findById(id);
			  Optional<Article> articleOpt = articleRepo.findById(articleId);
			  if(user1.isPresent() && articleOpt.isPresent()){
				  User newUser= user1.get();
				  Article article = articleOpt.get();
				  newUser.removeArticle(article);
				  return new ResponseEntity<>( HttpStatus.OK);
			  }
			  return new ResponseEntity<>(HttpStatus.CONFLICT);
			  
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}
	

}
