package com.example.demo.model.UserArticle;


import com.example.demo.model.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserArticleRepository extends JpaRepository<UserArticle, UserArticleKey> {
    List<UserArticle> findByArticle(Article article);
}
