package com.example.demo.model.userArticle;


import com.example.demo.model.article.Article;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserArticleRepository extends JpaRepository<UserArticle, UserArticleKey> {
    List<UserArticle> findByArticle(Article article);

    List<UserArticle> findByUser(User user);
}
