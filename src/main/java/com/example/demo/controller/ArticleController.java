package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.article.Article;
import com.example.demo.model.article.ArticleRepository;

@CrossOrigin(origins = "http://localhost:8081")  // used for vue.js later
@RestController
@RequestMapping("/api/articles")
public class ArticleController {
	@Autowired
	ArticleRepository articleRepo;
	
	@GetMapping("/{id}")
	public ResponseEntity<Article> getArticle(@PathVariable Long id){
		try {
			Optional<Article> article = articleRepo.findById(id);
			if(article.isPresent()) {
				return new ResponseEntity<>(article.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Article> createArticle(@RequestBody Article article){
		try {
			if(articleRepo.findByUrl(article.getUrl()).isPresent()) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			Article newArticle = new Article();
			articleRepo.save(newArticle);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
