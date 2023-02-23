package com.example.demo.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.model.userArticle.UserArticle;
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
    public ResponseEntity<User> signUpUser(@Validated @RequestBody User user) {
        try {
            if (userRepo.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
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
            if (userRepo.findByEmail(user.getEmail()).isPresent()) {
                User other = userRepo.findByEmail(user.getEmail()).get();
                if (other.getPassword().equals(user.getPassword())) {
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
    public ResponseEntity<List<Article>> getArticles(@PathVariable String id) {

        try {
            Optional<User> user1 = userRepo.findById(id);
            if (user1.isPresent()) {
                User newUser = user1.get();
                Set<UserArticle> res = newUser.getArticles();
                List<Article> finalRes = new LinkedList<>();
                for (UserArticle userArticle : res) {
                    finalRes.add(userArticle.getArticle());
                }

                return new ResponseEntity<>(finalRes, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //this method is  save post by user to read later.

    @PostMapping("/{id}/articles")
    public ResponseEntity<Article> savePost(@PathVariable String id, @Validated @RequestBody Article article) {
        try {
            Optional<User> user1 = userRepo.findById(id);
            if (user1.isPresent()) {
                User newUser = user1.get();
                Optional<Article> otherArticle = articleRepo.findByUrl(article.getUrl());
                if (!otherArticle.isPresent()) {
                    Article article1 = new Article(article.getTitle(), article.getDescription(), article.getSummary(), article.getUrl());
                    articleRepo.save(article1);
                    newUser.addArticle(article1);
                } else {
                    newUser.addArticle(otherArticle.get());
                }
                    userRepo.save(newUser);
                    return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //this method is to delete saved post by user to read
    @DeleteMapping("/{id1}/articles/{id2}")
    public ResponseEntity<Article> deleteSavedPost(@PathVariable("id1") String id, @PathVariable("id2") String articleId) {
        try {
            Optional<User> user1 = userRepo.findById(id);
            Optional<Article> articleOpt = articleRepo.findById(articleId);
            if (user1.isPresent() && articleOpt.isPresent()) {
                User newUser = user1.get();
                Article article = articleOpt.get();
                newUser.removeArticle(article);
                userRepo.save(newUser);
                return new ResponseEntity<>(article,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
