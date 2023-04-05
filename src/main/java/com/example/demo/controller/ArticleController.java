package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.articleComment.ArticleComment;
import com.example.demo.model.articleComment.ArticleCommentRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class    ArticleController {
    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    ArticleCommentRepository articleCommentRepo;

    //This is for testing purpose, will not be used in the FE
    @GetMapping("")
    public ResponseEntity<List<Article>> getAllArticle() {
        try {
            List<Article> result = articleRepo.findAll();
            if (!result.isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        try {
            Optional<Article> article = articleRepo.findById(id);
            if (article.isPresent()) {
                return new ResponseEntity<>(article.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Call this before add comment on the FE to get the article ID
    @PostMapping("")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        try {
            Optional<Article> theArticle = articleRepo.findByUrl(article.getUrl());
            if (theArticle.isPresent()) {
                return new ResponseEntity<>(theArticle.get(), HttpStatus.OK);
            }
            Article newArticle = new Article(article.getTitle(), article.getUrlToImage(), article.getSummary(), article.getUrl());
            articleRepo.save(newArticle);
            return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(article.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ArticleComment> createArticleComment(@PathVariable("id") String articleId, @RequestBody ObjectNode objNode) {
        try {
            String commentStr = objNode.get("comment").asText();
            String userId = objNode.get("userId").asText();
            String userName = objNode.get("userName").asText();
            Optional<Article> theArticle = articleRepo.findById(articleId);
            if (theArticle.isPresent()) {
                ArticleComment comment = new ArticleComment(commentStr, userName, Long.parseLong(userId), theArticle.get());
                articleCommentRepo.save(comment);
                return new ResponseEntity<>(comment, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<ArticleComment>> getArticleComment(@PathVariable("id") String articleId) {
        try {
            List<ArticleComment> result = articleCommentRepo.findByArticleId(articleId);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
